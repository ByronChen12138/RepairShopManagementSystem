package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "schedules")
public class ScheduleController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private ScheduleService scheduleService;

    /*
    ----------------------------------------------------------------------------
    ------------------------------------Schedule-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "")
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return convertToDtoListForSchedule(scheduleList);
    }

    @PostMapping(value = "create")
    public ScheduleDto createSchedule(@RequestParam Integer weekNo) {
        Schedule newSchedule = scheduleService.createSchedule(systemService.getMostRecentSystem(), weekNo);
        return convertToDto(newSchedule);
    }

    @GetMapping(value = "{weekNo}")
    public ScheduleDto getSpecificSchedule(@PathVariable Integer weekNo) {
        Schedule schedule = scheduleService.getScheduleByWeekNo(weekNo);
        return convertToDto(schedule);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Shifts-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping("shifts/get_for_dates")
    public List<ShiftDto> getShifts(@RequestParam String dates) throws ParseException {
        String[] temp = dates.split(",");
        List<Date> datesArray = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String date : temp) {
            java.util.Date ud = sdf.parse(date);
            datesArray.add(new Date(ud.getTime()));
        }
        return convertToDtoListForShift(scheduleService.getShiftsForDates(datesArray));
    }

    @GetMapping(value = "shifts")
    public List<ShiftDto> getAllShifts() {
        List<Shift> shiftList = scheduleService.getAllShifts();
        return convertToDtoListForShift(shiftList);
    }

    @GetMapping(value = "shifts/assistant")
    public List<ShiftDto> getAllShiftsForAnAssistant(@RequestParam String username) {
        List<Shift> shifts = scheduleService.getAllShiftsByAssistant(accountService.getAssistant(username));
        return convertToDtoListForShift(shifts);
    }

    @PostMapping(value = "shifts/create")
    public ShiftDto createShift(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
                                @RequestParam String username) {
        Shift newShift = scheduleService.createShift(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), accountService.getAssistant(username));
        return convertToDto(newShift);
    }

    @PostMapping( "shifts/delete")
    public ShiftDto deleteShift(@RequestParam Integer shiftId) {
        Shift s = scheduleService.deleteShift(scheduleService.getShiftById(shiftId));
        return convertToDto(s);
    }

    @PutMapping(value = "shifts/change")
    public ShiftDto changeShift(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate newDate,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime newStartTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime newEndTime,
                                @RequestParam Integer shiftId) {
        Shift s = scheduleService.changeShift(scheduleService.getShiftById(shiftId), Date.valueOf(newDate), Time.valueOf(newStartTime), Time.valueOf(newEndTime));
        return convertToDto(s);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Convertors----------------------------------
    ----------------------------------------------------------------------------
     */

    private ShiftDto convertToDto(Shift shift) {
        return new ShiftDto(shift.getDate(), shift.getStartTime(), shift.getEndTime(),
                convertToDto(shift.getAssistant()), shift.getShiftId());
    }

    private List<ShiftDto> convertToDtoListForShift(Iterable<Shift> shifts) {
        List<ShiftDto> result = new ArrayList<>();
        for (Shift s : shifts) {
            result.add(convertToDto(s));
        }
        return result;
    }

    private ScheduleDto convertToDto(Schedule schedule) {

        return new ScheduleDto(schedule.getId(), convertToDtoListForShift(schedule.getTimeSlot()),
                convertToDto(schedule.getRepairShopManagementSystem()));
    }

    private List<ScheduleDto> convertToDtoListForSchedule(List<Schedule> ScheduleList) {
        List<ScheduleDto> result = new ArrayList<>();
        for (Schedule o : ScheduleList)
            result.add(convertToDto(o));
        return result;
    }

    private AssistantDto convertToDto(Assistant a) {
        return new AssistantDto(a.getUsername(), a.getName(), a.getPassword(),
                convertToDto(a.getRepairShopManagementSystem()));
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessPhoneNumber());
    }

}
