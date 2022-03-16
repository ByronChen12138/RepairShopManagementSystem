package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;

@ExtendWith(MockitoExtension.class)
public class TestAppointmentService {
    @Mock
    private AppointmentRepository appointmentDao;
    @Mock
    private ShiftRepository shiftDao;
    @Mock
    private SpaceRepository spaceDao;
    @Mock
    private ServiceRepository serviceDao;
    @Mock
    private BillRepository billDao;
    @Mock
    private RepairShopManagementSystemRepository systemDao;

    @Mock
    private AssistantRepository assistantDao;

    @Mock
    private ScheduleRepository scheduleDao;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private CarRepository carDao;

    @InjectMocks
    private AppointmentService appointmentService;

    @InjectMocks
    private ScheduleService scheduleService;

    private static final String CUSTOMER_NAME = "TESTCUSTOMER";
    private static final Integer APPOINTMENT_ID = 1;
    private static final Integer APPOINTMENT_ID_2 = 2;
    private static final String ASSISTANT_NAME = "TESTASSISTANT";
    private static final String ASSISTANT_PASSWORD = "123456sdf";
    private static final String NAME = "TESTNAME";
    private static final String START_DATE = "2021-03-02";
    private static final String START_TIME = "10:00:00";
    private static final String END_TIME = "11:00:00";
    private static final boolean IS_PAID = false;
    private static final Integer PRICE = 100;
    private static final String TEST_USER_USERNAME = "TestUsername";

    private static final String TEST_CAR_PLATE_NO = "A123456";
    private static final String TEST_CAR_PLATE_NO2 = "A123459";

    private static final String SERVICE_TYPE = "Car Wash";

    private static final int WEEK_NO = 202201;

    @BeforeEach
    public void setMockOutput() {

        lenient().when(appointmentDao.findAppointmentByAppointmentId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(APPOINTMENT_ID)) {
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentId(APPOINTMENT_ID);
                        Shift shift = new Shift();
                        shift.setAppointment(appointment);
                        shift.setDate(Date.valueOf(START_DATE));
                        shift.setStartTime(Time.valueOf(START_TIME));
                        shift.setEndTime(Time.valueOf(END_TIME));

                        Assistant assistant = new Assistant();
                        assistant.setName(ASSISTANT_NAME);
                        assistant.setPassword(ASSISTANT_PASSWORD);
                        assistant.setUsername(NAME);
                        Schedule schedule = new Schedule();

                        shift.setAssistant(assistant);
                        shift.setSchedule(schedule);
                        appointment.setShift(shift);

                        Bill b = new Bill();
                        b.setAppointment(appointment);
                        b.setIsPaid(IS_PAID);
                        b.setPrice(PRICE);
                        Set<Bill> TB = new HashSet<Bill>();
                        TB.add(b);
                        Set<Assistant> AT = new HashSet<Assistant>();
                        AT.add(assistant);

                        Car carA = new Car();
                        Set<Car> carSet = new HashSet<>();
                        carSet.add(carA);
                        Set<Appointment> appointmentSet = new HashSet<>();
                        appointmentSet.add(appointment);
                        carA.setAppointment(appointmentSet);
                        appointment.setCar(carSet);


                        Service service = new Service();
                        service.setAppointment(appointment);
                        service.setServiceType(SERVICE_TYPE);
                        service.setAssistant(AT);
                        appointment.setBill(TB);
                        appointment.setService(service);
                        return appointment;
                    } else if (invocation.getArgument(0).equals(APPOINTMENT_ID_2)) {
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentId(APPOINTMENT_ID_2);
                        Shift shift = new Shift();
                        shift.setAppointment(appointment);
                        shift.setDate(Date.valueOf("2021-04-01"));
                        shift.setStartTime(Time.valueOf("10:00:00"));
                        shift.setEndTime(Time.valueOf("11:00:00"));

                        Assistant assistant = new Assistant();
                        assistant.setName(ASSISTANT_NAME);
                        assistant.setPassword(ASSISTANT_PASSWORD);
                        assistant.setUsername(NAME);
                        Schedule schedule = new Schedule();

                        shift.setAssistant(assistant);
                        shift.setSchedule(schedule);
                        appointment.setShift(shift);

                        Bill b = new Bill();
                        b.setAppointment(appointment);
                        b.setIsPaid(IS_PAID);
                        b.setPrice(PRICE);
                        Set<Bill> TB = new HashSet<Bill>();
                        TB.add(b);
                        Set<Assistant> AT = new HashSet<Assistant>();
                        AT.add(assistant);

                        Car carA = new Car();
                        Set<Car> carSet = new HashSet<>();
                        carSet.add(carA);
                        Set<Appointment> appointmentSet = new HashSet<>();
                        appointmentSet.add(appointment);
                        carA.setAppointment(appointmentSet);
                        appointment.setCar(carSet);


                        Service service = new Service();
                        service.setAppointment(appointment);
                        service.setServiceType(SERVICE_TYPE);
                        service.setAssistant(AT);
                        appointment.setBill(TB);
                        appointment.setService(service);
                        return appointment;


                    } else {
                        return null;
                    }
                });

        lenient().when(scheduleDao.findScheduleByWeekNo(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(WEEK_NO)) {
                Schedule schedule = new Schedule();
                schedule.setWeekNo(WEEK_NO);
                Shift shift = new Shift();
                shift.setDate(Date.valueOf("2022-01-01"));
                shift.setStartTime(Time.valueOf("10:00:00"));
                shift.setEndTime(Time.valueOf("11:00:00"));
                Set<Shift> shiftSet = new HashSet<>();
                Assistant assistant = new Assistant();
                assistant.setName(ASSISTANT_NAME);
                Set<Assistant> assistantSet = new HashSet<>();
                assistantSet.add(assistant);
                shift.setAssistant(assistant);
                shiftSet.add(shift);
                schedule.setTimeSlot(shiftSet);

                return schedule;
            } else {
                return null;
            }
        });

        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_NAME)) {
                Customer customer = new Customer();
                customer.setUsername(CUSTOMER_NAME);
                customer.setAppointment(new HashSet<>());
                return customer;
            } else {
                return null;
            }
        });

        lenient().when(carDao.findCarByPlateNo(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_CAR_PLATE_NO)) {
                Car car = new Car();
                car.setPlateNo(TEST_CAR_PLATE_NO);
                Customer customer = new Customer();
                customer.setUsername(CUSTOMER_NAME);
                car.setCustomer(customer);
                car.setAppointment(new HashSet<>());
                Car car2 = new Car();
                car2.setPlateNo(TEST_CAR_PLATE_NO2);
                Customer customer2 = new Customer();
                customer2.setUsername(TEST_USER_USERNAME);
                car2.setCustomer(customer2);
                return car;
            } else if (invocation.getArgument(0).equals(TEST_CAR_PLATE_NO2)) {
                Car car2 = new Car();
                car2.setPlateNo(TEST_CAR_PLATE_NO2);
                Customer customer2 = new Customer();
                customer2.setUsername(TEST_USER_USERNAME);
                car2.setCustomer(customer2);
                return car2;
            } else
                return null;
        });

        lenient().when(systemDao.findFirstByOrderByIdDesc()).thenAnswer((InvocationOnMock invocation) -> {

            RepairShopManagementSystem system = new RepairShopManagementSystem();
            Space space = new Space();
            space.setMaxWeightLoad(10);
            space.setSpaceId(10);
            Set<Space> spaceSet = new HashSet<>();
            spaceSet.add(space);
            system.setSpace(spaceSet);
            return system;

        });

        lenient().when(carDao.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shiftDao.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(spaceDao.save(any(Space.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(billDao.save(any(Bill.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(serviceDao.save(any(Service.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(assistantDao.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(scheduleDao.save(any(Schedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * @author kevinli
     * Test make appointment sucessfully
     */
    @Test
    public void testMakeAppointment() {
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, TEST_CAR_PLATE_NO, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(appointment);
        assertEquals(appointment.getCustomer().getUsername(), CUSTOMER_NAME);
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedWithNoShiftAvailable() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("11:10:01");
        Time endTime = Time.valueOf("11:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, TEST_CAR_PLATE_NO, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The appointment time has conflicts!");
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedWithNoSpaceAvailable() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 11;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, TEST_CAR_PLATE_NO, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The appointment time has conflicts!");
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedNotCarOfCustomer() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, TEST_CAR_PLATE_NO2, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "This car does not belongs to this customer.");
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedCarNotFound() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, "B123456", date, startTime,
                    endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "Cannot find the car of the specified plateNo.");
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedCustomerNotFound() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-01");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, "customer", TEST_CAR_PLATE_NO, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "Cannot find the customer of that username.");
    }

    /**
     * @author kevinli
     * Test make appointment unsuccessfully
     */
    @Test
    public void testMakeAppointmentFailedScheduleNotFound() {
        String error = "";
        Appointment appointment = null;
        Date date = Date.valueOf("2022-01-10");
        Time startTime = Time.valueOf("10:10:01");
        Time endTime = Time.valueOf("10:55:01");
        Integer weight = 5;

        try {
            appointment = appointmentService.makeAppointment(SERVICE_TYPE, CUSTOMER_NAME, TEST_CAR_PLATE_NO, date,
                    startTime, endTime, weight);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "There is no shifts for that date.");
    }

    /**
     * @author kevinli
     * Test register a shift to an appointment successfully
     */
    @Test
    public void testRegisterAShiftToAppointment() {
        Appointment testAppointment = new Appointment();
        Customer testCustomer = new Customer();
        testCustomer.setName(CUSTOMER_NAME);
        testAppointment.setCustomer(testCustomer);
        Shift testShift = new Shift();
        Assistant assistant = new Assistant();
        assistant.setName(ASSISTANT_NAME);
        Date date = Date.valueOf("2021-03-12");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("11:00:00");
        testShift.setAssistant(assistant);
        testShift.setDate(date);
        testShift.setStartTime(startTime);
        testShift.setEndTime(endTime);
        testShift.setAssistant(assistant);

        try {
            appointmentService.registerAnAppointmentToAShift(testAppointment, testShift);
        } catch (IllegalArgumentException e) {
            fail();
        }
        // assertNotNull(testAppointment.getShift());
        assertNotNull(testShift.getAppointment());
        assertEquals(testShift.getAppointment().getCustomer().getName(), CUSTOMER_NAME);
    }

  

 

    /**
     * test make payment
     * @author Xiang Li
     */
    @Test
    public void testMakePayment() {
        Bill bill = new Bill();
        bill.setPrice(100);
        bill.setIsPaid(false);
        try {
            bill = appointmentService.makePayment(bill);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertEquals(bill.getIsPaid(), true);

    }

    /**
     * test make payment
     * @author Xiang Li
     */
    @Test
    public void testMakePaymentFailedBillNotExist() {
        Bill bill = null;
        String error = "";
        try {
            bill = appointmentService.makePayment(bill);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(bill);
        assertEquals(error, "Cannot find the specified bill.");

    }

    /**
     * test make payment
     * @author Xiang Li
     */
    @Test
    public void testCreateSpace() {
        Space space = null;
        RepairShopManagementSystem system = new RepairShopManagementSystem();
        try {
            space = appointmentService.createSpace(10, system);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(space);
        assertEquals(space.getMaxWeightLoad(), 10);

    }

    /**
     * test make payment
     * @author Xiang Li
     */
    @Test
    public void testCreateSpaceInvalidMaxWeight() {
        Space space = null;
        String error = "";
        RepairShopManagementSystem system = new RepairShopManagementSystem();
        try {
            space = appointmentService.createSpace(0, system);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(space);
        assertEquals(error, "invalid maxweight");

    }

    /**
     * test create Bill
     * @author Xiang Li
     */
    @Test
    public void testCreateBill() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = 114514;
        Bill bill = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(bill);
        assertEquals(price, bill.getPrice());
        assertEquals(false, bill.getIsPaid());
    }

    /**
     * test createBill failed
     * @author Xiang Li
     */
    @Test
    public void testCreateBillPriceNull() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = null;
        Bill bill = null;
        String error = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(bill);
        assertEquals("Price cannot be empty!", error);
    }

    /**
     * test createBill failed
     * @author Xiang Li
     */
    @Test
    public void testCreateBillPriceNotPositive() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = -11514;
        Bill bill = null;
        String error = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(bill);
        assertEquals("Price cannot be negative or zero!", error);
    }

    /**
     * Case1: test the change of the service that can pass
     *
     * @author Xiang Li
     */

    @Test
    public void testChangeServiceType() {
        Appointment appointment = new Appointment();
        Shift shift = new Shift();
        Service service = new Service();

        service.setServiceType("Car Wash");
        shift.setDate(Date.valueOf("2999-9-9"));
        appointment.setShift(shift);
        appointment.setService(service);

        String error = "";
        try {
            appointment = appointmentService.changeServiceType(appointment, "Maintenance");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(appointment);
        assertEquals("", error);
        assertEquals("Maintenance", appointment.getService().getServiceType());
    }

    /**
     * Case2: test the change of the service that not satisfy the date condition
     *
     * @author Xiang Li
     */

    @Test
    public void testChangeServiceTypeWithBadDate() {
        Appointment appointment = new Appointment();
        Shift shift = new Shift();
        Service service = new Service();

        service.setServiceType("Car Wash");
        shift.setDate(Date.valueOf("1999-9-9"));
        appointment.setShift(shift);
        appointment.setService(service);

        String error = "";
        try {
            appointment = appointmentService.changeServiceType(appointment, "Maintenance");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Service type can only be changed before the appointment! ", error);
        assertEquals("Car Wash", appointment.getService().getServiceType());
    }

    /**
     * Case3: test the change of the service that not satisfy the new type
     *
     * @author Xiang Li
     */

    @Test
    public void testChangeServiceTypeWithBadNewType() {
        Appointment appointment = new Appointment();
        Shift shift = new Shift();
        Service service = new Service();

        service.setServiceType("Maintenance");
        shift.setDate(Date.valueOf("2999-9-9"));
        appointment.setShift(shift);
        appointment.setService(service);

        String error = "";
        try {
            appointment = appointmentService.changeServiceType(appointment, "asdadsasd");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Service type must be Car Wash, Maintenance, Change Tire or Repair! ", error);
        assertEquals("Maintenance", appointment.getService().getServiceType());
    }

    /**
     * Case1: delete a shift without an appointment
     *
     * @author Xiang Li
     */

    @Test
    public void testDeleteShift() {
        String error = "";

        Shift shift = new Shift();
        try {
            shift = scheduleService.deleteShift(shift);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNotNull(shift);
        assertEquals("", error);
    }

    /**
     * Case2: delete a shift with an appointment which will not pass
     *
     * @author Xiang Li
     */

    @Test
    public void testDeleteShiftNotPass() {
        Appointment appointment = new Appointment();
        String error = "";
        Shift shift = new Shift();
        shift.setAppointment(appointment);
        try {
            scheduleService.deleteShift(shift);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals("Cannot delete the shift with an appointment!", error);
    }

    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        return invocation.getArgument(0);
    };

}
