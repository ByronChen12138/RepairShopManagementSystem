package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerDto extends UserDto {
    private String phoneNo;
    private String homeAddress;
    private String email;
    private Set<CarDto> cars;
    private Set<AppointmentDto> appointments;

    public CustomerDto() {
        super();
    }

    public CustomerDto(String username, String password, String name, String phoneNo, String homeAddress, String email) {
        super(username, password, name);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
    }

    public CustomerDto(String username, String password, String name, String phoneNo, String homeAddress, String email,
                       Set<CarDto> Cars, Set<AppointmentDto> appointments, RepairShopManagementSystemDto repairShopManagementSystemDto) {
        super(username, password, name, repairShopManagementSystemDto);
        this.homeAddress = homeAddress;
        this.email = email;
        this.cars = Cars;
        this.appointments = appointments;
        this.phoneNo = phoneNo;
    }

    public CustomerDto(RepairShopManagementSystemDto repairShopManagementSystemDto, String username, String password, String name, String phoneNo, String homeAddress, String email,
                       Set<CarDto> Cars) {
        super(username, password, name, repairShopManagementSystemDto);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
        this.cars = Cars;
        this.appointments = new HashSet<>();

    }

    public CustomerDto(RepairShopManagementSystemDto repairShopManagementSystemDto, String username, String password, String name, String phoneNo, String homeAddress, String email
    ) {

        super(username, password, name, repairShopManagementSystemDto);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
        this.cars = new HashSet<>();
        this.appointments = new HashSet<>();

    }

    public CustomerDto(
            String username,
            String password,
            String name,
            String phoneNo,
            String homeAddress,
            String email,
            Set<CarDto> cars,
            Set<AppointmentDto> appointments
    ) {
        super(username, password, name);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
        this.cars = cars;
        this.appointments = appointments;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getEmail() {
        return email;
    }

    public Set<CarDto> getCars() {
        return cars;
    }

    public Set<AppointmentDto> getAppointments() {
        return appointments;
    }

}