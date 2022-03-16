package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User {
    private String phoneNo;

    public void setPhoneNo(String value) {
        this.phoneNo = value;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    private String homeAddress;

    public void setHomeAddress(String value) {
        this.homeAddress = value;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    private String email;

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }

    private Set<Car> car;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    public Set<Car> getCar() {
        return this.car;
    }

    public void setCar(Set<Car> cars) {
        this.car = cars;
    }

    private Set<Appointment> appointment;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    public Set<Appointment> getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Set<Appointment> appointments) {
        this.appointment = appointments;
    }

}