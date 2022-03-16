package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Assistant;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Shift;

import java.util.Set;
import java.util.List;
import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {
    Shift findShiftByShiftId(Integer id);

    Set<Shift> findShiftsBySchedule(Schedule schedule);

    Set<Shift> findShiftsByAssistant(Assistant assistant);

    Set<Shift> findShiftsByDateIn(List<Date> dates);
}
