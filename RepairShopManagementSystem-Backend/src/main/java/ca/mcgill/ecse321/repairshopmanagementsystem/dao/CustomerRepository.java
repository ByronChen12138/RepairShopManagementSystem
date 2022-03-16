package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
  Customer findCustomerByUserId(Integer userId);

  Customer findCustomerByUsername(String username);
}