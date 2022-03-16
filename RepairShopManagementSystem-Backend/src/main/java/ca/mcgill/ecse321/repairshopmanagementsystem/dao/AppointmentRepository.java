package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Appointment;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Customer;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Shift;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
	Appointment findAppointmentByAppointmentId(Integer appointmentId);
	List<Appointment> findByCustomer(Customer customer);
}
