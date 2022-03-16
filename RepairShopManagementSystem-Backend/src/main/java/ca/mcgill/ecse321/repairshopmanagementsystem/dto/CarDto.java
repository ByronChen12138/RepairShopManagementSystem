package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.*;
import java.sql.Date;

public class CarDto {

    private String plateNo;
    private String model;
    private String year;
    private String manufacturer;
    private CustomerDto customer;
    private Set<AppointmentDto> appointments;
    private Date nextReminderDate;

    public CarDto() {
    }

    public CarDto(String plateNo) {
        this.plateNo = plateNo;
    }

    public CarDto(String plateNo, String model, String manufacturer, String year) {
        this.plateNo = plateNo;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
    }

    public CarDto(String plateNo, String model, String manufacturer, CustomerDto customer) {
        Calendar calendar = Calendar.getInstance();
        this.year = Integer.toString(calendar.get(Calendar.YEAR));
        this.plateNo = plateNo;
        this.model = model;
        this.manufacturer = manufacturer;
        this.customer = customer;
        this.appointments = new HashSet<>();
        calendar.add(Calendar.MONTH, 1);
        this.nextReminderDate = new Date(calendar.getTimeInMillis());
    }

    public CarDto(String plateNo, String model, String year, String manufacturer, CustomerDto customer, Set<AppointmentDto> appointments, Date nextReminderDate) {
        this.plateNo = plateNo;
        this.model = model;
        this.year = year;
        this.manufacturer = manufacturer;
        this.customer = customer;
        this.appointments = appointments;
        this.nextReminderDate = nextReminderDate;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public Set<AppointmentDto> getAppointments() {
        return appointments;
    }

    public Date getNextReminderDate() {
        return nextReminderDate;
    }
}
