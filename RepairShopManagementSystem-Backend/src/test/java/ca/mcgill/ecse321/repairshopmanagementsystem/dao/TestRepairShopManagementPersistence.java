package ca.mcgill.ecse321.repairshopmanagementsystem.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRepairShopManagementPersistence {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RepairShopManagementSystemRepository repairShopManagementSystemRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private SpaceRepository spaceRepository;


    @AfterEach
    @BeforeEach
    public void clearAll() {

    	serviceRepository.deleteAll();
        repairShopManagementSystemRepository.deleteAll();
    }

    /**
     * @author Byron Chen
     */
    @Test
    public void testCustomerRepositoryPersistenceAndLoad() {
        String phoneNo = "3321-321";
        String address = "mcgill";
        Customer customer = new Customer();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        customer.setRepairShopManagementSystem(sys);
        customer.setPhoneNo(phoneNo);
        customer.setHomeAddress(address);
        customerRepository.save(customer);
        Integer id = customer.getUserId();
        customer = null;
        customer = customerRepository.findCustomerByUserId(id);
        assertNotNull(customer);
        assertEquals(customer.getUserId(), id);
        assertEquals(customer.getPhoneNo(), phoneNo);
        assertEquals(customer.getHomeAddress(), address);
    }

    /**
     * @author Byron Chen
     */
    @Test
    public void testAssistantRepositoryPersistenceAndLoad() {
        String username = "testuser";
        Assistant assistant = new Assistant();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        assistant.setRepairShopManagementSystem(sys);
        assistant.setName(username);
        assistantRepository.save(assistant);
        Integer id = assistant.getUserId();

        assistant = null;
        assistant = assistantRepository.findAssistantByUserId(id);
        assertNotNull(assistant);
        assertEquals(assistant.getUserId(), id);
        assertEquals(assistant.getName(), username);
    }


    /**
     * @author Ing Tian
     */
    @Test
    public void testScheduleRepositoryPersistenceAndLoad() {
        //Create and save a system
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        scheduleRepository.save(schedule);
        Integer id = schedule.getId();
        schedule = null;

        schedule = scheduleRepository.findScheduleById(id);


        assertNotNull(schedule);
        assertEquals(schedule.getId(), id);

    }

    /**
     * @author Ing Tian
     */
    @Test
    public void testAppointmentRepositoryPersistenceAndLoad() {

        // Save RepairShopManagementSystem.
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        scheduleRepository.save(schedule);

        // Create assistant.
        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        // Create shift.
        Integer shiftId = 5;
        Shift shift = new Shift();
        shift.setShiftId(shiftId);
        shift.setSchedule(schedule);
        shift.setAssistant(assistant);
        shiftRepository.save(shift);

        // Create space.

        Space testSpace = new Space();
        testSpace.setRepairShopManagementSystem(sys);

        spaceRepository.save(testSpace);

        // Create customer.
        Customer testCustomer = new Customer();
        testCustomer.setRepairShopManagementSystem(sys);
        customerRepository.save(testCustomer);

        // Create service.
        Service testService = new Service();

        // Create appointment.
        Integer appointmentId = 100;
        Appointment testAppointment = new Appointment();
        testAppointment.setAppointmentId(appointmentId);
        shift.setAppointment(testAppointment);
        testAppointment.setShift(shift);
        testService.setAppointment(testAppointment);
        testAppointment.setService(testService);
        testAppointment.setCustomer(testCustomer);
        testAppointment.setSpace(testSpace);
        appointmentRepository.save(testAppointment);


        testAppointment = null;
        testAppointment = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        assertNotNull(testAppointment);
        assertEquals(testAppointment.getAppointmentId(), appointmentId);
    }
    /**
     * @author Ao Shen
     */
    @Test
    public void testServiceRepositoryPersistenceAndLoad() {
        // Save RepairShopManagementSystem.
        String serviceName = "test";
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        scheduleRepository.save(schedule);

        // Create assistant.
        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        // Create shift.
        Integer shiftId = 5;
        Shift shift = new Shift();
        shift.setShiftId(shiftId);
        shift.setSchedule(schedule);
        shift.setAssistant(assistant);
        shiftRepository.save(shift);

        // Create space.
        Space testSpace = new Space();
        testSpace.setRepairShopManagementSystem(sys);
        spaceRepository.save(testSpace);

        // Create customer.
        Customer testCustomer = new Customer();
        testCustomer.setRepairShopManagementSystem(sys);
        customerRepository.save(testCustomer);


        // Create appointment.
        Integer appointmentId = 100;
        Appointment testAppointment = new Appointment();

        // Create service.
        Service testService = new Service();

        testAppointment.setAppointmentId(appointmentId);
        shift.setAppointment(testAppointment);
        testAppointment.setShift(shift);
        testService.setAppointment(testAppointment);
        testAppointment.setCustomer(testCustomer);
        testAppointment.setSpace(testSpace);
        appointmentRepository.save(testAppointment);

        testService.setAppointment(testAppointment);
        testService.setServiceType("A");
        testService.setServiceType(serviceName);
        serviceRepository.save(testService);
        Integer serviceid = testService.getServiceId();

        testService = null;
        testService = serviceRepository.findServiceByServiceId(serviceid);

        assertNotNull(testService);
        assertEquals(testService.getServiceId(), serviceid);
        assertEquals(testService.getServiceType(), serviceName);
    }
    
    /**
     * @author Ao Shen
     */
    @Test
    public void testCarRepositoryPersistenceAndLoad() {
        String model = "test";
        Customer customer = new Customer();
        //Create and Save a  RepairShopManagementSystem in the database
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        //Create and save a customer
        customer.setRepairShopManagementSystem(sys);
        customerRepository.save(customer);

        //Create and save a car
        Car A = new Car();
        String test = "testcar";
        A.setPlateNo(test);
        A.setCustomer(customer);
        A.setModel(model);
        carRepository.save(A);
        //find the corresponding car
        A = carRepository.findCarByPlateNo(test);

        assertNotNull(A);
        assertEquals(test, A.getPlateNo());
        assertEquals(model, A.getModel());


    }
    /**
     * @author kevinli
     */
    @Test
    public void testSpaceRepistoryPersistenceAndLoad() {
       //Create and Save repairshopmanagement system
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);
       //Create and Save Space in the database
        Integer maxWeight=90;
        Space space = new Space();
        space.setRepairShopManagementSystem(sys);
        space.setMaxWeightLoad(maxWeight);
        spaceRepository.save(space);
        
        //get the generated id
        Integer id=space.getSpaceId();
        space = null;
        
        //find the corresponding space from database table
        space = spaceRepository.findSpaceBySpaceId(id);

        assertNotNull(space);
        assertEquals(space.getSpaceId(), id);
        assertEquals(space.getMaxWeightLoad(),maxWeight);
    }
    /**
     * @author kevinli
     */

    @Test
    public void testShiftRepistoryPersistenceAndLoad() {
    	
    	// Save RepairShopManagementSystem.
    	
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        scheduleRepository.save(schedule);

        // Create assistant.
        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        // Create shift.
        Integer shiftId = 5;
        Shift shift = new Shift();
        shift.setShiftId(shiftId);
        shift.setSchedule(schedule);
        shift.setAssistant(assistant);
        shiftRepository.save(shift);
        
        shift = null;
        
        //get the corresponding shift from database table
        shift = shiftRepository.findShiftByShiftId(shiftId);

        assertNotNull(shift);
        assertEquals(shift.getShiftId(), shiftId);


    }
    /**
     * @author Xiang Li
     */
    @Test
    public void testBillRepositoryPersistenceAndLoad() {
        Integer price = 10000;
        // Save RepairShopManagementSystem.
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        scheduleRepository.save(schedule);

        // Create assistant.
        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        // Create shift.
        Integer shiftId = 5;
        Shift shift = new Shift();
        shift.setShiftId(shiftId);
        shift.setSchedule(schedule);
        shift.setAssistant(assistant);
        shiftRepository.save(shift);

        // Create space.
        Space testSpace = new Space();
        testSpace.setRepairShopManagementSystem(sys);
        spaceRepository.save(testSpace);

        // Create customer.
        Customer testCustomer = new Customer();
        testCustomer.setRepairShopManagementSystem(sys);
        customerRepository.save(testCustomer);

        // Create service.
        Service testService = new Service();

        // Create appointment.
        Integer appointmentId = 100;
        Appointment testAppointment = new Appointment();
        testAppointment.setAppointmentId(appointmentId);
        shift.setAppointment(testAppointment);
        testAppointment.setShift(shift);
        testService.setAppointment(testAppointment);
        testAppointment.setService(testService);
        testAppointment.setCustomer(testCustomer);
        testAppointment.setSpace(testSpace);
        appointmentRepository.save(testAppointment);

        //Create Bill
        Integer billId;
        Bill bill = new Bill();
        bill.setAppointment(testAppointment);
        bill.setPrice(price);
        bill.setIsPaid(true);
        billRepository.save(bill);
        billId = bill.getBillNo();


        bill = null;
        bill = billRepository.findBillByBillNo(billId);
        assertNotNull(bill);
        assertEquals(bill.getBillNo(), billId);
        assertEquals(bill.getPrice(), price);
    }

    /**
     * @author Xiang Li
     */
    @Test
    public void testOwnerRepositoryPersistenceAndLoad() {
        String userName = "testuser";
        Owner owner = new Owner();
        //Create and Save a system in the database table
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        repairShopManagementSystemRepository.save(sys);
        //Create and save an owner in the system
        owner.setRepairShopManagementSystem(sys);
        owner.setName(userName);
        ownerRepository.save(owner);
        Integer id = owner.getUserId();
        owner = null;


        owner = ownerRepository.findOwnerByUserId(id);
        assertNotNull(owner);
        assertEquals(owner.getUserId(), id);
        assertEquals(owner.getName(), userName);

    }

}