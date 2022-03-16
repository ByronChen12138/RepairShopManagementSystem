package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;

import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
    Schedule findScheduleById(Integer id);

    Schedule findScheduleByWeekNo(Integer weekNo);
}