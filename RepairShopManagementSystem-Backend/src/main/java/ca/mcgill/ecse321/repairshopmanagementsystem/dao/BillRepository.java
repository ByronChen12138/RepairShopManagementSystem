package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Appointment;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Bill;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Integer> {
	Bill findBillByBillNo(Integer billNo);
	List<Bill> findByAppointment(Appointment appointment);
}
