package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.AssistantRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.CarRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Transactional
    public String getUserType(String username) {
        Owner o = ownerRepository.findOwnerByUsername(username);
        if (o != null)
            return "owner";
        Assistant a = assistantRepository.findAssistantByUsername(username);
        if (a != null)
            return "assistant";
        Customer c = customerRepository.findCustomerByUsername(username);
        if (c != null)
            return "customer";

        return "";
    }

    /*----------------------------------------------------------------
    ------------------------------ Owner -----------------------------
    ----------------------------------------------------------------*/

    @Transactional
    public Owner createOwner(String username, String password, String name, RepairShopManagementSystem system) throws IllegalArgumentException {

        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

        Owner newOwner = new Owner();
        newOwner.setName(name);
        newOwner.setUsername(username);
        newOwner.setPassword(password);
        newOwner.setRepairShopManagementSystem(system);
        system.getUser().add(newOwner);
        ownerRepository.save(newOwner);
        return newOwner;
    }

    @Transactional
    public Owner getOwner(String username) {
        return ownerRepository.findOwnerByUsername(username);
    }

    @Transactional
    public List<Owner> getAllOwners() {
        return toList(ownerRepository.findAll());
    }

    /*----------------------------------------------------------------
    ------------------------------ User -----------------------------
    ----------------------------------------------------------------*/
    @Transactional
    public User updateUserInformation(User user, String username, String password, String name) {
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("New username cannot be empty.");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("New password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("New name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        if (user instanceof Owner)
            ownerRepository.save((Owner) user);
        else if (user instanceof Assistant)
            assistantRepository.save((Assistant) user);
        else
            customerRepository.save((Customer) user);

        return user;
    }

    /*----------------------------------------------------------------
    -------------------------- Assistant -----------------------------
    ----------------------------------------------------------------*/

    @Transactional
    public Assistant createAssistant(String username, String password, String name, RepairShopManagementSystem system) {
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

        Assistant assistant = new Assistant();
        assistant.setUsername(username);
        assistant.setPassword(password);
        assistant.setName(name);
        assistant.setService(new HashSet<>());
        assistant.setShift(new HashSet<>());
        assistant.setRepairShopManagementSystem(system);
        system.getUser().add(assistant);
        assistantRepository.save(assistant);
        return assistant;
    }

    @Transactional
    public Assistant getAssistant(String username) {
        return assistantRepository.findAssistantByUsername(username);
    }

    @Transactional
    public List<Assistant> getAllAssistants() {
        return toList(assistantRepository.findAll());
    }

    /*----------------------------------------------------------------
    -------------------------- Customer ------------------------------
    ----------------------------------------------------------------*/

    @Transactional
    public Customer createCustomer(String username, String password, String name, RepairShopManagementSystem system, String phoneNo, String address, String email) {
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");
        if (phoneNo == null || phoneNo.equals(""))
            throw new IllegalArgumentException("Phone NO cannot be empty.");
        if (address == null || address.equals(""))
            throw new IllegalArgumentException("Address cannot be empty.");
        if (email == null || email.equals(""))
            throw new IllegalArgumentException("Email cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPhoneNoCorrect(phoneNo))
            throw new IllegalArgumentException("Phone NO must be a 10-digit number.");
        if (!Util.isEmailAddressCorrect(email))
            throw new IllegalArgumentException("Email must be of the form part_1@part_2.part_3.");

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);
        customer.setPhoneNo(phoneNo);
        customer.setEmail(email);
        customer.setHomeAddress(address);
        customer.setRepairShopManagementSystem(system);
        customer.setCar(new HashSet<>());
        customer.setAppointment(new HashSet<>());
        system.getUser().add(customer);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer getCustomer(String username) {

        Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer == null) throw new IllegalArgumentException("no customer find");
        return customer;
    }

    @Transactional
    public Customer addACarToCustomer(Customer customer, Car aCar) {
        Set<Car> cars = customer.getCar();
        cars.add(aCar);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    /**
     * @author Ao Shen
     */
    @Transactional
    public Customer updateCustomer(Customer customer, String email, String address, String phoneNo) {
        if (phoneNo == null || phoneNo.equals(""))
            throw new IllegalArgumentException("New phoneNo cannot be empty.");
        if (address == null || address.equals(""))
            throw new IllegalArgumentException("New address cannot be empty.");
        if (email == null || email.equals(""))
            throw new IllegalArgumentException("New email cannot be empty.");

        // Check for formats.
        if (!Util.isPhoneNoCorrect(phoneNo))
            throw new IllegalArgumentException("Invalid PhoneNo.");
        if (!Util.isAddressCorrect(address))
            throw new IllegalArgumentException("Invalid Address.");
        if (!Util.isEmailAddressCorrect(email))
            throw new IllegalArgumentException("Invalid Email address.");
        customer.setEmail(email);
        customer.setHomeAddress(address);
        customer.setPhoneNo(phoneNo);
        customerRepository.save(customer);
        return customer;
    }


    /*----------------------------------------------------------------
    ------------------------------- Car ------------------------------
    ----------------------------------------------------------------*/

    /**
     * @author Byron Chen
     */
    @Transactional
    public Car updateCar(Car car, String model, String year, String manufacturer, Customer customer) {

        String error = "";

        if (model == null || model.trim().length() == 0) {
            error = error + "New model cannot be empty! ";
        }

        if (year == null || year.trim().length() == 0) {
            error = error + "New year cannot be empty! ";
        } else if (!Util.isCarYearCorrect(year)) {
            error = error + "New year illegal! ";
        }

        if (manufacturer == null || manufacturer.trim().length() == 0) {
            error = error + "New manufacturer cannot be empty! ";
        }

        if (customer == null) {
            error = error + "New customer cannot be empty! ";
        }

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        car.getCustomer().getCar().remove(car);
        car.setCustomer(customer);
        car.getCustomer().getCar().add(car);
        car.setModel(model);
        car.setManufacturer(manufacturer);
        car.setYear(year);
        customerRepository.save(car.getCustomer());
        return car;
    }


    @Transactional
    public Car getCar(String plateNo) {
        return carRepository.findCarByPlateNo(plateNo);
    }

    @Transactional
    public List<Car> getCars() {
        return toList(carRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * @author Ao Shen
     */
    @Transactional
    public Car createCar(String plateNo, String model, String year, String manufacturer, Customer customer) {
        // Check for null or empty inputs.
        if (plateNo == null || plateNo.equals(""))
            throw new IllegalArgumentException("PlateNo cannot be empty!");
        if (model == null || model.equals(""))
            throw new IllegalArgumentException("Model cannot be empty!");
        if (year == null || year.equals(""))
            throw new IllegalArgumentException("Year cannot be empty!");
        if (manufacturer == null || manufacturer.equals(""))
            throw new IllegalArgumentException("Manufacturer cannot be empty!");
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be empty!");


        // Check for formats.
        if (!Util.isPlateNoCorrect(plateNo))
            throw new IllegalArgumentException("Invalid plate number.");
        if (!Util.isCarYearCorrect(year))
            throw new IllegalArgumentException("Invalid year.");

        Car aCar = new Car();
        aCar.setCustomer(customer);
        aCar.setModel(model);
        aCar.setManufacturer(manufacturer);
        aCar.setPlateNo(plateNo);
        aCar.setYear(year);
        customer.getCar().add(aCar);
        customerRepository.save(customer);
        return aCar;
    }


}

