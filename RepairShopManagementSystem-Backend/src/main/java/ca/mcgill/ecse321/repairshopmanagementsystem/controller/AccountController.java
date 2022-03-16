package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.AccountService;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemService systemService;


    @GetMapping(value = "get_user_info")
    public String getUserInformation(@RequestParam String username) {
        return accountService.getUserType(username);
    }

    /*
    ----------------------------------------------------------------------------
    ------------------------------------Owner-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "owners")
    public List<OwnerDto> getAllOwners() {
        List<Owner> ownerList = accountService.getAllOwners();
        return convertToDtoListForOwner(ownerList);
    }

    @PostMapping(value = "owners/create_to_most_recent_system")
    public OwnerDto createOwnerToMostRecentSystem(@RequestBody OwnerDto o) {
        Owner owner = accountService.createOwner(o.getUsername(), o.getPassword(), o.getName(), systemService.getMostRecentSystem());
        return convertToDto(owner);
    }

    @PutMapping(value = "owners/update_info")
    public OwnerDto updateOwnerInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestBody OwnerDto o) {
        Owner newOwner = (Owner) accountService.updateUserInformation(accountService.getOwner(o.getUsername()), newUsername, newPassword, newName);
        return convertToDto(newOwner);
    }

    @GetMapping(value = "owners/get_by_username")
    public OwnerDto getOwnerByUsername(String username) {
        return convertToDto(accountService.getOwner(username));
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Assistant-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "assistants")
    public List<AssistantDto> getAllAssistants() {
        List<Assistant> assistantList = accountService.getAllAssistants();
        return convertToDtoListForAssistant(assistantList);
    }

    @GetMapping(value = "assistants/get_by_username")
    public AssistantDto getAssistantByUsername(@RequestParam String username) {
        Assistant assistant = accountService.getAssistant(username);
        return convertToDto(assistant);
    }

    @PostMapping(value = "assistants/create_to_most_recent_system")
    public AssistantDto createAssistantToMostRecentSystem(@RequestBody AssistantDto a) {
        Assistant assistant = accountService.createAssistant(a.getUsername(), a.getPassword(), a.getName(), systemService.getMostRecentSystem());
        return convertToDto(assistant);
    }

    @PutMapping(value = "assistants/update_info")
    public AssistantDto updateAssistantInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestBody AssistantDto o) {
        Assistant newAssistant = (Assistant) accountService.updateUserInformation(accountService.getAssistant(o.getUsername()), newUsername, newPassword, newName);
        return convertToDto(newAssistant);
    }

    /*
    ----------------------------------------------------------------------------
    ---------------------------------Customer-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "customers")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = accountService.getAllCustomers();
        return convertToDtoListForCustomer(customerList);
    }

    @GetMapping(value = "customers/get_by_username")
    public CustomerDto getCustomerByUsername(@RequestParam String username) {
        Customer customer = accountService.getCustomer(username);
        return convertToDto(customer);
    }

    @PutMapping(value = "customers/update_info")
    public CustomerDto updateCustomerInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestParam String newPhoneNo, @RequestParam String newAddress, @RequestParam String newEmail, @RequestBody CustomerDto o) {
        Customer newCustomer = (Customer) accountService.updateUserInformation(accountService.getCustomer(o.getUsername()), newUsername, newPassword, newName);
        accountService.updateCustomer(newCustomer, newEmail, newAddress, newPhoneNo);
        return convertToDto(newCustomer);
    }

    @PostMapping(value = "customers/create_to_most_recent_system")
    public CustomerDto createCustomerToMostRecentSystem(@RequestBody CustomerDto a) {
        if (!accountService.getUserType(a.getUsername()).equals(""))
            throw new IllegalArgumentException("The username entered has already been used.");
        Customer customer = accountService.createCustomer(a.getUsername(), a.getPassword(), a.getName(), systemService.getMostRecentSystem(), a.getPhoneNo(), a.getHomeAddress(), a.getEmail());
        return convertToDto(customer);
    }

    /*
    ----------------------------------------------------------------------------
    -----------------------------------Car--------------------------------------
    ----------------------------------------------------------------------------
     */

    @PostMapping(value = "cars/create")
    public CarDto createCarForACustomer(@RequestParam String username, @RequestBody CarDto car) {
        Car c = accountService.createCar(car.getPlateNo(), car.getModel(), car.getYear(), car.getManufacturer(), accountService.getCustomer(username));
        return convertToDto(c);
    }

    @PutMapping(value = "cars/update")
    public CarDto updateCarInfo(@RequestParam String newUsername, @RequestParam String newModel, @RequestParam String newYear, @RequestParam String newManufacturer, @RequestBody CarDto car) {
        Car c = accountService.updateCar(accountService.getCar(car.getPlateNo()), newModel, newYear, newManufacturer, accountService.getCustomer(newUsername));
        return convertToDto(c);
    }

    private OwnerDto convertToDto(Owner owner) {
        return new OwnerDto(owner.getUsername(), owner.getPassword(), owner.getName(), convertToDto(owner.getRepairShopManagementSystem()));
    }

    private AssistantDto convertToDto(Assistant a) {
        return new AssistantDto(
                a.getUsername(),
                a.getPassword(),
                a.getName(),
                convertToDtoListForShift(a.getShift())
        );
    }

    private CustomerDto convertToDto(Customer a) {
        return new CustomerDto(
                a.getUsername(),
                a.getPassword(),
                a.getName(),
                a.getPhoneNo(),
                a.getHomeAddress(),
                a.getEmail(),
                convertToDtoListForCar(a.getCar()),
                convertToDtoListForAppointment(a.getAppointment())
        );
    }

    private ServiceDto convertToDto(Service service) {
        return new ServiceDto(service.getServiceType());
    }

    private Set<AppointmentDto> convertToDtoListForAppointment(Iterable<Appointment> appointments) {
        Set<AppointmentDto> appointmentDtos = new HashSet<>();
        for (Appointment appointment : appointments)
            appointmentDtos.add(new AppointmentDto(
                    appointment.getAppointmentId(),
                    convertToDtoListForBill(appointment.getBill()),
                    convertToDto(appointment.getShift()),
                    new ArrayList<>(convertToDtoListForCar(appointment.getCar())),
                    convertToDto(appointment.getSpace()),
                    convertToDto(appointment.getService())
            ));
        return appointmentDtos;
    }

    private List<BillDto> convertToDtoListForBill(Iterable<Bill> bills) {
        List<BillDto> billDtos = new ArrayList<>();
        for (Bill bill : bills)
            billDtos.add(new BillDto(bill.getBillNo(), bill.getPrice(), bill.getIsPaid()));
        return billDtos;
    }

    private Set<CarDto> convertToDtoListForCar(Iterable<Car> cars) {
        Set<CarDto> carDtos = new HashSet<>();
        for (Car car : cars)
            carDtos.add(new CarDto(car.getPlateNo(), car.getModel(), car.getManufacturer(), car.getYear()));
        return carDtos;
    }

    private ShiftDto convertToDto(Shift shift) {
        return new ShiftDto(shift.getDate(), shift.getStartTime(), shift.getEndTime(), shift.getShiftId());
    }

    private Set<ShiftDto> convertToDtoListForShift(Iterable<Shift> shifts) {
        Set<ShiftDto> shiftDtos = new HashSet<>();
        for (Shift shift : shifts)
            shiftDtos.add(convertToDto(shift));
        return shiftDtos;
    }

    private SpaceDto convertToDto(Space space) {
        return new SpaceDto(space.getSpaceId(), space.getMaxWeightLoad());
    }

    private CarDto convertToDto(Car c) {
        return new CarDto(c.getPlateNo(), c.getModel(), c.getManufacturer(), c.getYear());
    }

    private List<OwnerDto> convertToDtoListForOwner(List<Owner> ownerList) {
        List<OwnerDto> result = new ArrayList<>();
        for (Owner o : ownerList)
            result.add(convertToDto(o));
        return result;
    }

    private List<AssistantDto> convertToDtoListForAssistant(List<Assistant> user) {
        List<AssistantDto> result = new ArrayList<>();
        for (Assistant o : user)
            result.add(convertToDto(o));
        return result;
    }

    private List<CustomerDto> convertToDtoListForCustomer(List<Customer> user) {
        List<CustomerDto> result = new ArrayList<>();
        for (Customer o : user)
            result.add(convertToDto(o));
        return result;
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessPhoneNumber());
    }

}
