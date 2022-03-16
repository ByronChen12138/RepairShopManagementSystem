package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SystemService systemService;

    /*
    ----------------------------------------------------------------------------
    ------------------------------Appointment-----------------------------------
    ----------------------------------------------------------------------------
    */

    @PostMapping(value = "find_appointments_of_customer")
    public List<AppointmentDto> findAppointmentsOfACustomer(@RequestParam String username) {
        List<Appointment> aps = appointmentService.findAppointmentsOfCustomer(username);
        return convertToDtoListForAppointment(aps);
    }

    @GetMapping(value = "")
    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> aps = appointmentService.getAllAppointments();
        return convertToDtoListForAppointment(aps);
    }

    @PostMapping(value = "make_appointment")
    public AppointmentDto makeAppointment(@RequestParam String serviceType,
                                          @RequestParam String username,
                                          @RequestParam String plateNo,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
                                          @RequestParam Integer weight) {
        Appointment aps = appointmentService.makeAppointment(serviceType, username, plateNo, Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), weight);
        appointmentService.registerAnAppointmentToAShift(aps, aps.getShift());
        return convertToDto(appointmentService.getAppointmentById(aps.getAppointmentId()));
    }

    @PutMapping(value = "update_service_type")
    public AppointmentDto updateServiceType(@RequestParam Integer appointmentId, @RequestParam String newServiceType) {
        return convertToDto(appointmentService.changeServiceType(appointmentService.getAppointmentById(appointmentId), newServiceType));
    }

    @PostMapping(value = "delete")
    public AppointmentDto deleteAppointment(@RequestParam Integer id) {
        Appointment a = appointmentService.deleteAppointment(appointmentService.getAppointmentById(id));
        return new AppointmentDto(a.getAppointmentId());
    }

    /*
    ----------------------------------------------------------------------------
    -----------------------------------Bill-------------------------------------
    ----------------------------------------------------------------------------
    */

    @PutMapping(value = "make_payment")
    public BillDto makePayment(@RequestParam Integer id) {
        Bill newBill = appointmentService.makePayment(appointmentService.makePayment(appointmentService.getBillByBillNo(id)));
        return convertToDto(newBill);
    }

    @GetMapping(value = "bills")
    public List<BillDto> getAllBills() {
        List<Bill> billDtoList = appointmentService.getAllBills();
        return convertToDtoListForBill(billDtoList);
    }

    /*
    ----------------------------------------------------------------------------
    ------------------------------ Service -------------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "services")
    public List<ServiceDto> getAllServices() {
        List<Service> service = appointmentService.getAllServices();
        return convertToDtoListForService(service);
    }

    /*
    ----------------------------------------------------------------------------
    -------------------------------- Space -------------------------------------
    ----------------------------------------------------------------------------
     */
    @PostMapping(value = "space/create")
    public SpaceDto createSpace(@RequestParam Integer weight) {
        return convertToDto(appointmentService.createSpace(weight, systemService.getMostRecentSystem()));
    }

    @GetMapping(value = "space/get_all")
    public List<SpaceDto> getAllSpace() {
        return convertToDtoListForSpace(appointmentService.getAllSpace());
    }

    private List<SpaceDto> convertToDtoListForSpace(Iterable<Space> spaces) {
        List<SpaceDto> result = new ArrayList<>();
        for (Space s : spaces)
            result.add(convertToDto(s));
        return result;
    }

    private List<ServiceDto> convertToDtoListForService(List<Service> service) {
        List<ServiceDto> result = new ArrayList<>();
        for (Service s : service) {
            result.add(convertToDto(s));
        }
        return result;
    }


    private AppointmentDto convertToDto(Appointment appointment) {

        // Convert a list of bill DTOs.
        List<BillDto> bills = new ArrayList<>();
        for (Bill b : appointment.getBill())
            bills.add(new BillDto(b.getBillNo(), b.getPrice(), b.getIsPaid()));

        // Convert a list of assistants.
        List<AssistantDto> assistants = new ArrayList<>();
        for (Assistant a : appointment.getService().getAssistant())
            assistants.add(new AssistantDto(a.getUsername(), a.getPassword(), a.getName()));

        // Convert the service DTO.
        ServiceDto service = new ServiceDto(appointment.getService().getServiceType(), assistants);

        // Convert the list of cars.
        List<CarDto> cars = new ArrayList<>();
        for (Car c : appointment.getCar())
            cars.add(new CarDto(c.getPlateNo(), c.getModel(), c.getManufacturer(), c.getYear()));

        // Convert the shift.
        ShiftDto shift = new ShiftDto(appointment.getShift().getDate(), appointment.getShift().getStartTime(), appointment.getShift().getEndTime());

        // Convert the space.
        SpaceDto space = new SpaceDto(appointment.getSpace().getSpaceId(), appointment.getSpace().getMaxWeightLoad());

        // Convert the customer.
        CustomerDto customer = new CustomerDto(
                appointment.getCustomer().getUsername(),
                appointment.getCustomer().getPassword(),
                appointment.getCustomer().getName(),
                appointment.getCustomer().getPhoneNo(),
                appointment.getCustomer().getHomeAddress(),
                appointment.getCustomer().getEmail());

        return new AppointmentDto(
                appointment.getAppointmentId(),
                bills,
                service,
                shift,
                customer,
                cars,
                space
        );
    }

    private List<AppointmentDto> convertToDtoListForAppointment(List<Appointment> appointments) {
        List<AppointmentDto> result = new ArrayList<>();
        for (Appointment a : appointments) {
            result.add(convertToDto(a));

        }
        return result;

    }

    private ServiceDto convertToDto(Service service) {
        List<Assistant> assistants = new ArrayList<>(service.getAssistant());
        return new ServiceDto(service.getServiceType(), convertToDtoListForAssistant(assistants),
                convertToDto(service.getAppointment()));

    }

    private BillDto convertToDto(Bill bill) {

        return new BillDto(bill.getBillNo(), bill.getPrice(), convertToDto(bill.getAppointment()), bill.getIsPaid());
    }


    private SpaceDto convertToDto(Space space) {
        return new SpaceDto(space.getSpaceId(), space.getMaxWeightLoad(), convertToDto(space.getRepairShopManagementSystem()));
    }

    private List<BillDto> convertToDtoListForBill(List<Bill> billList) {
        List<BillDto> result = new ArrayList<>();
        for (Bill b : billList) {
            result.add(convertToDto(b));
        }

        return result;
    }

    // ----------------------------all the dto from account
    // service-----------------------------------
    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(),
                system.getBusinessPhoneNumber());
    }

    private List<AssistantDto> convertToDtoListForAssistant(List<Assistant> user) {
        List<AssistantDto> result = new ArrayList<>();
        for (Assistant o : user)
            result.add(convertToDto(o));
        return result;
    }

    private AssistantDto convertToDto(Assistant a) {
        return new AssistantDto(a.getUsername(), a.getName(), a.getPassword(),
                convertToDto(a.getRepairShopManagementSystem()));
    }
}
