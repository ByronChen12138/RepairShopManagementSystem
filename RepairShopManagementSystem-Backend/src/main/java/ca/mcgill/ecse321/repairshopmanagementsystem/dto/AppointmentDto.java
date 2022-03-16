package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.List;

public class AppointmentDto {
    private Integer appointmentId;
    private List<BillDto> bill;
    private ServiceDto service;
    private ShiftDto shift;
    private CustomerDto customer;
    private List<CarDto> cars;
    private SpaceDto space;

    public AppointmentDto() {

    }

    public AppointmentDto(Integer id) {
        this.appointmentId = id;
    }

    public AppointmentDto(Integer appointmentId, List<BillDto> bill, ServiceDto service, ShiftDto shift, CustomerDto customer, List<CarDto> car, SpaceDto space) {
        this.appointmentId = appointmentId;
        this.cars = car;
        this.bill = bill;
        this.space = space;
        this.service = service;
        this.shift = shift;
        this.customer = customer;
    }

    public AppointmentDto(Integer appointmentId, List<BillDto> bill, ShiftDto shift, CustomerDto customer, List<CarDto> cars, SpaceDto space) {
        this.appointmentId = appointmentId;
        this.cars = cars;
        this.bill = bill;
        this.space = space;

        this.shift = shift;
        this.customer = customer;
    }

    public AppointmentDto(Integer appointmentId, List<BillDto> bill, ShiftDto shift, List<CarDto> cars, SpaceDto space, ServiceDto service) {
        this.appointmentId = appointmentId;
        this.bill = bill;
        this.shift = shift;
        this.cars = cars;
        this.space = space;
        this.service = service;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public List<BillDto> getBill() {
        return bill;
    }

    public ServiceDto getService() {
        return service;
    }

    public ShiftDto getShift() {
        return shift;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public List<CarDto> getCar() {
        return cars;
    }

    public SpaceDto getSpace() {
        return space;
    }

}
