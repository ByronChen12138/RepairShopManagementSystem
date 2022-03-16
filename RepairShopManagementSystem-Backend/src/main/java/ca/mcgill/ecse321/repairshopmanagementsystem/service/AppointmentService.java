package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RepairShopManagementSystemRepository systemRepository;

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


    /*
    ----------------------------------------------------------------------------
    -------------------------------------Shift----------------------------------
    ----------------------------------------------------------------------------
     */
    private Shift getShift(Integer shiftId) {
        return shiftRepository.findShiftByShiftId(shiftId);
    }

    /*
    ----------------------------------------------------------------------------
    -------------------------------Appointment----------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public Appointment makeAppointment(String serviceType, String username, String plateNo, Date date,
                                       Time startTime, Time endTime, Integer weight) {
        int weekNo = Util.getWeekNo(date);
        Schedule schedule = scheduleRepository.findScheduleByWeekNo(weekNo);
        if (schedule == null)
            throw new IllegalArgumentException("There is no shifts for that date.");

        Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer == null)
            throw new IllegalArgumentException("Cannot find the customer of that username.");

        Car car = carRepository.findCarByPlateNo(plateNo);
        if (car == null)
            throw new IllegalArgumentException("Cannot find the car of the specified plateNo.");
        if (!car.getCustomer().getUsername().equals(customer.getUsername()))
            throw new IllegalArgumentException("This car does not belongs to this customer.");

        List<Shift> availableShifts = new ArrayList<>();
        List<Space> availableSpace = new ArrayList<>();
        RepairShopManagementSystem system = systemRepository.findFirstByOrderByIdDesc();
        if (!Util.canMakeAppointment(weight, date, startTime, endTime, schedule, system.getSpace(), availableSpace, availableShifts))
            throw new IllegalArgumentException("The appointment time has conflicts!");

        Shift shift = availableShifts.get(0);
        Space space = availableSpace.get(0);

        // Set space and shift.
        Appointment appointment = new Appointment();
        appointment.setSpace(space);
        appointment.setShift(shift);

        // Set a car.
        car.getAppointment().add(appointment);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        appointment.setCar(cars);

        // Set a customer.
        appointment.setCustomer(customer);
        customer.getAppointment().add(appointment);

        // Create a new bill.
        Set<Bill> bills = new HashSet<>();
        Bill aBill = new Bill();
        aBill.setAppointment(appointment);
        aBill.setBillNo(appointment.hashCode());
        int price=0;
        if(serviceType.equalsIgnoreCase("car wash")) {
        	price=30;
        }
        else if(serviceType.equalsIgnoreCase("maintenance")){
        	price=80;
        }
        else if(serviceType.equalsIgnoreCase("repair")) price=150;
        else if(serviceType.equalsIgnoreCase("changeTire")) price=30;
        aBill.setPrice(price);
        aBill.setIsPaid(false);
        bills.add(aBill);
        appointment.setBill(bills);

        // Create a new service.
        ca.mcgill.ecse321.repairshopmanagementsystem.model.Service newService = new ca.mcgill.ecse321.repairshopmanagementsystem.model.Service();
        Set<Assistant> assistants = new HashSet<>();
        assistants.add(shift.getAssistant());
        newService.setAssistant(assistants);
        newService.setAppointment(appointment);
        newService.setServiceType(serviceType);
        appointment.setService(newService);

        appointment.setAppointmentId(appointment.hashCode());

        customer = customerRepository.save(customer);
        

        // Reload the updated appointment.
        for(Appointment a : customer.getAppointment())
            if(a.getAppointmentId().equals(appointment.getAppointmentId())){
                appointment = a;
                break;
            }

        return appointment;
    }

    @Transactional
    public Shift registerAnAppointmentToAShift(Appointment appointment, Shift shift){
        shift.setAppointment(appointment);
        shiftRepository.save(shift);
        return shift;
    }

    @Transactional
    public Appointment UpdateAppointmentTime(Integer shiftId, Integer appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        List<Space> freeSpaces = new ArrayList<Space>();
        List<Shift> freeShifts = new ArrayList<Shift>();
        List<Space> listSpace = toList(spaceRepository.findAll());
        Set<Space> setSpace = new HashSet<Space>(listSpace);
        if (Util.canMakeAppointment(0,
                appointmentRepository.findAppointmentByAppointmentId(appointmentId).getShift().getDate(),
                appointmentRepository.findAppointmentByAppointmentId(appointmentId).getShift().getStartTime(),
                appointmentRepository.findAppointmentByAppointmentId(appointmentId).getShift().getEndTime(),
                scheduleRepository.findScheduleByWeekNo(Util.getWeekNo(getShift(shiftId).getDate())),
                setSpace,
                freeSpaces,
                freeShifts)) {
            appointment.setShift(shiftRepository.findShiftByShiftId(shiftId));
        }
        return appointment;
    }

    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findAppointmentByAppointmentId(id);
    }

    @Transactional
    public List<Appointment> findAppointmentsOfCustomer(String username) {
        Customer customer = customerRepository.findCustomerByUsername(username);
        return appointmentRepository.findByCustomer(customer);
    }

    @Transactional
    public List<Appointment> getAllAppointments() {
        return toList(appointmentRepository.findAll());
    }

    @Transactional
    public Appointment changeServiceType(Appointment appointment, String newServiceType) {
        String error = "";

        if (!newServiceType.equals("Car Wash") && !newServiceType.equals("Maintenance") && !newServiceType.equals("Change Tire")
                && !newServiceType.equals("Repair")) {
            error = error + "Service type must be Car Wash, Maintenance, Change Tire or Repair! ";
        }

        if (appointment.getShift().getDate().before(Date.valueOf(LocalDate.now()))) {
            error = error + "Service type can only be changed before the appointment! ";
        }

        if (error.length() > 0)
            throw new IllegalArgumentException(error);

        appointment.getService().setServiceType(newServiceType);

        appointmentRepository.save(appointment);
        return appointment;

    }

    @Transactional
    public Appointment deleteAppointment(Appointment appointment) {
        Date now = new Date(System.currentTimeMillis());
        long timeDifference = appointment.getShift().getDate().getTime() - now.getTime();
        if (timeDifference < 24 * 3600 * 1000)
            throw new IllegalArgumentException("This appointment will start in 1 day!");
        for(Car c : appointment.getCar())
            c.getAppointment().remove(appointment);
        appointment.setCar(new HashSet<>());
        appointmentRepository.delete(appointment);
        return appointment;
    }

    /*
    ----------------------------------------------------------------------------
    -----------------------------------Bill-------------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public Bill makePayment(Bill bill) {
        if (bill == null)
            throw new IllegalArgumentException("Cannot find the specified bill.");
        bill.setIsPaid(true);
        billRepository.save(bill);
        return bill;
    }

    public Bill getBillByBillNo(Integer id) {
        return billRepository.findBillByBillNo(id);
    }

    @Transactional
    public Bill createBill(Integer price, Appointment appointment, boolean isPaid) {
        if (price == null)
            throw new IllegalArgumentException("Price cannot be empty!");
        if (price <= 0)
            throw new IllegalArgumentException("Price cannot be negative or zero!");
        Bill newBill = new Bill();
        newBill.setAppointment(appointment);
        newBill.setIsPaid(isPaid);
        newBill.setPrice(price);
        billRepository.save(newBill);

        return newBill;
    }

    @Transactional
    public List<Bill> getBill(Appointment appointment) {
        return billRepository.findByAppointment(appointment);
    }

    @Transactional
    public List<Bill> getAllBills() {
        return toList(billRepository.findAll());
    }

    /*
    ----------------------------------------------------------------------------
    ----------------------------------Space-------------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public Space createSpace(Integer maxWeightLoad, RepairShopManagementSystem RepairShopManagementSystem) {
        String error = "";
        if (maxWeightLoad == 0) {
            error = "invalid maxweight";
        }

        error = error.trim();
        if (error.length() > 0)
            throw new IllegalArgumentException(error);
        Space newSpace = new Space();
        newSpace.setMaxWeightLoad(maxWeightLoad);
        newSpace.setRepairShopManagementSystem(RepairShopManagementSystem);
        spaceRepository.save(newSpace);
        return newSpace;

    }

    @Transactional
    public List<Space> getAllSpace() {
        return toList(spaceRepository.findAll());
    }

    /*
    ----------------------------------------------------------------------------
    -------------------------------Services-------------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public List<ca.mcgill.ecse321.repairshopmanagementsystem.model.Service> getAllServices() {
        return toList(serviceRepository.findAll());
    }

}
