package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.sql.Date;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;


@ExtendWith(MockitoExtension.class)
public class TestScheduleService {

    @Mock
    private AssistantRepository assistantDao;
    @Mock
    private ShiftRepository shiftDao;
    @Mock
    private RepairShopManagementSystemRepository systemDao;
    @Mock
    private ScheduleRepository scheduleDao;

    @InjectMocks
    private ScheduleService scheduleService;
    @InjectMocks
    private SystemService systemService;

    private static final String TEST_USER_USERNAME = "TestUsername";
    private static final String TEST_USER_PASSWORD = "TestPassword";
    private static final String TEST_USER_NAME = "Someone";
    private static final Integer TEST_USER_ID = 1;

    private static final Integer TEST_SYSTEM_ID = 1;
    private static final String TEST_SYSTEM_NAME = "A test shop";
    private static final String TEST_SYSTEM_ADDRESS = "1234 Sunshine Street, Palm Beach, Florida";
    private static final String TEST_SYSTEM_PHONE_NO = "1002003000";

    private static final Integer TEST_SCHEDULE_ID = 123456789;
    private static final Integer TEST_SHIFT_ID = 111;

    private static final Date TEST_DATE = Date.valueOf("2001-01-01");
    private static final Time TEST_START_TIME = Time.valueOf("11:00:00");
    private static final Time TEST_END_TIME = Time.valueOf("12:00:00");


    /**
     * @author Ao Shen
     */
    @BeforeEach
    public void setMockOutput() {
        // findScheduleById
        lenient().when(scheduleDao.findScheduleById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SHIFT_ID)) {
                Shift shift = new Shift();
                shift.setShiftId(TEST_SHIFT_ID);
                return shift;
            } else {
                return null;
            }
        });

        // findScheduleById
        lenient().when(shiftDao.findShiftByShiftId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SCHEDULE_ID)) {
                Schedule schedule = new Schedule();
                schedule.setId(TEST_SCHEDULE_ID);
                return schedule;
            } else {
                return null;
            }
        });


        // Whenever tries to find assistant, just return the default.
        lenient().when(assistantDao.findAssistantByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_USER_USERNAME)) {
                Assistant assistant = new Assistant();
                assistant.setUsername(TEST_USER_USERNAME);
                assistant.setPassword(TEST_USER_PASSWORD);
                assistant.setName(TEST_USER_NAME);
                assistant.setUserId(TEST_USER_ID);
                return assistant;
            } else {
                return null;
            }
        });

        // system
        lenient().when(systemDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SYSTEM_ID)) {
                RepairShopManagementSystem system = new RepairShopManagementSystem();
                system.setId(TEST_SYSTEM_ID);
                system.setBusinessName(TEST_SYSTEM_NAME);
                system.setBusinessAddress(TEST_SYSTEM_ADDRESS);
                system.setBusinessPhoneNumber(TEST_SYSTEM_PHONE_NO);
                system.setUser(new HashSet<User>());
                return system;
            } else {
                return null;
            }
        });
        // Whenever tries to find assistant, just return the default.
        lenient().when(assistantDao.findAssistantByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_USER_USERNAME)) {
                Assistant assistant = new Assistant();
                assistant.setUsername(TEST_USER_USERNAME);
                assistant.setPassword(TEST_USER_PASSWORD);
                assistant.setName(TEST_USER_NAME);
                assistant.setUserId(TEST_USER_ID);
                return assistant;
            } else {
                return null;
            }
        });
        // shift
        lenient().when(shiftDao.findShiftByShiftId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SHIFT_ID)) {
                Shift shift = new Shift();
                Schedule TEST_SCHEDULE = new Schedule();
                Assistant TEST_ASSISTANT = new Assistant();
                shift.setStartTime(TEST_START_TIME);
                shift.setEndTime(TEST_END_TIME);
                shift.setDate(TEST_DATE);
                shift.setSchedule(TEST_SCHEDULE);
                shift.setAssistant(TEST_ASSISTANT);
                shift.setShiftId(TEST_SHIFT_ID);
                return shift;
            } else {
                return null;
            }
        });

        // shift
        lenient().when(shiftDao.findShiftsByAssistant(any(Assistant.class))).thenAnswer((InvocationOnMock invocation) -> {
                    Shift shift = new Shift();
                    Schedule TEST_SCHEDULE = new Schedule();
                    Assistant TEST_ASSISTANT = new Assistant();
                    shift.setStartTime(TEST_START_TIME);
                    shift.setEndTime(TEST_END_TIME);
                    shift.setDate(TEST_DATE);
                    shift.setSchedule(TEST_SCHEDULE);
                    shift.setAssistant(TEST_ASSISTANT);
                    shift.setShiftId(TEST_SHIFT_ID);
                    Set<Shift> shifts = new HashSet<Shift>();
                    shifts.add(shift);
                    return shifts;
                }
        );
        // Whenever tries to find system, just return the default.
        lenient().when(systemDao.findRepairShopManagementSystemByBusinessNameAndBusinessAddressAndBusinessPhoneNumber(anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SYSTEM_NAME) && invocation.getArgument(1).equals(TEST_SYSTEM_ADDRESS) && invocation.getArgument(2).equals(TEST_SYSTEM_PHONE_NO)) {
                RepairShopManagementSystem system = new RepairShopManagementSystem();
                system.setId(TEST_SYSTEM_ID);
                system.setBusinessName(TEST_SYSTEM_NAME);
                system.setBusinessAddress(TEST_SYSTEM_ADDRESS);
                system.setBusinessPhoneNumber(TEST_SYSTEM_PHONE_NO);
                return system;
            } else {
                return null;
            }
        });


        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(scheduleDao.save(any(Schedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(assistantDao.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shiftDao.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testCreateSchedule() {
        assertEquals(0, scheduleService.getAllSchedules().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Schedule schedule = new Schedule();
        try {
            schedule = scheduleService.createSchedule(defaultSystem, 11);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(schedule);
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testUpdateSchedule() {
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Schedule schedule = new Schedule();
        Shift shiftNew = new Shift();
        shiftNew.setShiftId(111);
        Set<Shift> shiftSet = new HashSet<Shift>();
        shiftSet.add(shiftNew);
        try {
            schedule = scheduleService.createSchedule(defaultSystem, 11);
            scheduleService.updateSchedule(schedule, shiftNew);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(schedule);
        assertEquals(shiftSet, schedule.getTimeSlot());
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testCreateShift() {
        Shift shift = null;
        Date date = Date.valueOf("2021-01-01");
        Time startTime = Time.valueOf("14:00:00");
        Time endTime = Time.valueOf("15:00:00");
        Assistant assistant = new Assistant();
        Set<Shift> shiftSet = new HashSet<>();
        assistant.setShift(shiftSet);

        try {
            shift = scheduleService.createShift(date, startTime, endTime, assistant);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(shift);
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testCreateShiftWithConflictShift() {
        String error = "";
        Shift shift = null;
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Time startTime = Time.valueOf("11:01:00");
        Time endTime = Time.valueOf("11:30:00");
        Assistant assistant = new Assistant();
        Set<Shift> shifts = new HashSet<Shift>();
        assistant.setUsername(TEST_USER_USERNAME);
        assistant.setPassword(TEST_USER_PASSWORD);
        assistant.setName(TEST_USER_NAME);
        assistant.setUserId(111);
        assistant.setRepairShopManagementSystem(defaultSystem);
        assistant.setShift(shifts);
        Shift shiftOld = new Shift();
        shiftOld.setDate(TEST_DATE);
        shiftOld.setStartTime(TEST_START_TIME);
        shiftOld.setEndTime(TEST_END_TIME);
        shiftOld.setAssistant(assistant);
        shifts.add(shiftOld);
        try {
            shift = scheduleService.createShift(TEST_DATE, startTime, endTime, assistant);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals(error, "New shift has conflicts with existing shifts.");
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testDeleteShift() {
        Shift shift = new Shift();
        Shift shiftNew = new Shift();
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Set<Shift> shifts = new HashSet<Shift>();
        Assistant assistant = new Assistant();
        assistant.setUsername(TEST_USER_USERNAME);
        assistant.setPassword(TEST_USER_PASSWORD);
        assistant.setName(TEST_USER_NAME);
        assistant.setUserId(111);
        assistant.setRepairShopManagementSystem(defaultSystem);
        assistant.setShift(shifts);
        try {
            shiftNew = scheduleService.deleteShift(shift);
        } catch (Exception e) {
            fail();
        }
        assertEquals(shift, shiftNew);
    }

    /**
     * @author Ao Shen
     */
    @Test
    public void testDeleteShiftWithAppointment() {
        String error = "";
        Shift shift = new Shift();
        Shift shiftNew = null;
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Set<Shift> shifts = new HashSet<Shift>();
        Assistant assistant = new Assistant();
        assistant.setUsername(TEST_USER_USERNAME);
        assistant.setPassword(TEST_USER_PASSWORD);
        assistant.setName(TEST_USER_NAME);
        assistant.setUserId(111);
        assistant.setRepairShopManagementSystem(defaultSystem);
        assistant.setShift(shifts);
        Appointment appointment = new Appointment();
        shift.setAppointment(appointment);
        try {
            shiftNew = scheduleService.deleteShift(shift);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(shiftNew);
        assertEquals(error, "Cannot delete the shift with an appointment!");
    }


}
