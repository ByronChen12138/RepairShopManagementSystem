package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Appointment {
    private Integer appointmentId;

    public void setAppointmentId(Integer value) {
        this.appointmentId = value;
    }

    @Id
    public Integer getAppointmentId() {
        return this.appointmentId;
    }

    private Set<Bill> bill;

    @OneToMany(mappedBy = "appointment", cascade = {CascadeType.ALL})
    public Set<Bill> getBill() {
        return this.bill;
    }

    public void setBill(Set<Bill> bills) {
        this.bill = bills;
    }

    private Service service;

    @OneToOne(mappedBy = "appointment", cascade = {CascadeType.ALL})
    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private Shift shift;

    @OneToOne()
    public Shift getShift() {
        return this.shift;
    }

    public void setShift(Shift timeSlot) {
        this.shift = timeSlot;
    }

    private Customer customer;

    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Set<Car> car;

    @ManyToMany(mappedBy = "appointment")
    public Set<Car> getCar() {
        return this.car;
    }

    public void setCar(Set<Car> cars) {
        this.car = cars;
    }

    private Space space;

    @OneToOne
    public Space getSpace() {
        return this.space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

}