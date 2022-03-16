package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import java.sql.Time;
import java.sql.Date;
import java.util.*;

import javax.transaction.Transactional;

import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RepairShopManagementSystemRepository systemRepository;

    /*
    ----------------------------------------------------------------------------
    ----------------------------------Schedules---------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public List<Schedule> getAllSchedules() {
        return toList(scheduleRepository.findAll());
    }

    @Transactional
    public Schedule findSchedule(Integer id) {
        return scheduleRepository.findScheduleById(id);
    }

    @Transactional
    public Schedule getScheduleByWeekNo(Integer weekNo) {
        return scheduleRepository.findScheduleByWeekNo(weekNo);
    }

    @Transactional
    public Schedule createSchedule(RepairShopManagementSystem system, Integer weekNo) {
        if (scheduleRepository.findScheduleByWeekNo(weekNo) != null)
            throw new IllegalArgumentException("Schedule of the specific week No has already been created.");

        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(system);
        schedule.setWeekNo(weekNo);
        schedule.setTimeSlot(new HashSet<>());
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Transactional
    public Schedule updateSchedule(Schedule schedule, Shift shift) {
        if (!schedule.getTimeSlot().remove(shift)) {
            Set<Shift> shiftNew = schedule.getTimeSlot();
            shiftNew.add(shift);
            schedule.setTimeSlot(shiftNew);
        }
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Transactional
    public Schedule findScheduleById(Integer id) {
        return scheduleRepository.findScheduleById(id);
    }

    /*
    ----------------------------------------------------------------------------
    -----------------------------------Shifts-----------------------------------
    ----------------------------------------------------------------------------
     */

    @Transactional
    public Set<Shift> getShiftsBySchedule(Schedule schedule) {
        return shiftRepository.findShiftsBySchedule(schedule);
    }

    @Transactional
    public Shift getShiftById(Integer id) {
        return shiftRepository.findShiftByShiftId(id);
    }

    @Transactional
    public List<Shift> getAllShifts() {
        return toList(shiftRepository.findAll());
    }

    @Transactional
    public List<Shift> getAllShiftsByAssistant(Assistant assistant) {
        if (assistant == null)
            throw new IllegalArgumentException("Assistant specified of the ID does not exist.");

        return toList(shiftRepository.findShiftsByAssistant(assistant));
    }

    @Transactional
    public Shift createShift(Date date, Time startTime, Time endTime, Assistant assistant) {

        if (assistant == null)
            throw new IllegalArgumentException("Assistant must exist to create a shift!");

        // Get the correct week no.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR), week = calendar.get(Calendar.WEEK_OF_YEAR), weekNo = year * 100 + week;

        Schedule schedule = scheduleRepository.findScheduleByWeekNo(weekNo);

        if (schedule == null)
            schedule = createSchedule(systemRepository.findFirstByOrderByIdDesc(), weekNo);

        Shift shift = new Shift();
        shift.setAssistant(assistant);
        shift.setDate(date);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setSchedule(schedule);
        shift.setShiftId(shift.hashCode());
        schedule.getTimeSlot().add(shift);

        Set<Shift> shifts = shiftRepository.findShiftsByAssistant(assistant);
        if (Util.hasShiftConflicts(shifts, shift))
            throw new IllegalArgumentException("New shift has conflicts with existing shifts.");

        assistant.getShift().add(shift);

        shiftRepository.save(shift);

        return shift;
    }

    @Transactional
    public Set<Shift> getShiftsForDates(List<Date> dates) {
        return shiftRepository.findShiftsByDateIn(dates);
    }

    @Transactional
    public Shift deleteShift(Shift shift) {
        if (shift.getAppointment() != null)
            throw new IllegalArgumentException("Cannot delete the shift with an appointment!");

        shiftRepository.delete(shift);

        return shift;
    }

    @Transactional
    public Shift changeShift(Shift shift, Date newDate, Time startTime, Time endTime) {
        if (shift.getAppointment() != null)
            throw new IllegalArgumentException("Cannot change a shift with an appointment!");

        deleteShift(shift);

        Shift s;
        try {
            s = createShift(newDate, startTime, endTime, shift.getAssistant());
        } catch (IllegalArgumentException e) {
            shift.getAssistant().getShift().add(shift);
            shiftRepository.save(shift);
            throw new IllegalArgumentException("Cannot change shift to a conflicting time!");
        }

        return s;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}