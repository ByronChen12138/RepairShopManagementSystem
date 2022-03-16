package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RepairShopManagementSystem {
    private Integer id;

    public void setId(Integer value) {
        this.id = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    private Set<User> user;

    @OneToMany(mappedBy = "repairShopManagementSystem", cascade = {CascadeType.ALL})
    public Set<User> getUser() {
        return this.user;
    }

    public void setUser(Set<User> users) {
        this.user = users;
    }

    private Set<Space> space;

    @OneToMany(mappedBy = "repairShopManagementSystem", cascade = {CascadeType.ALL})
    public Set<Space> getSpace() {
        return this.space;
    }

    public void setSpace(Set<Space> spaces) {
        this.space = spaces;
    }

    private Set<Schedule> schedule;

    @OneToMany(mappedBy = "repairShopManagementSystem", cascade = {CascadeType.ALL})
    public Set<Schedule> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Set<Schedule> schedules) {
        this.schedule = schedules;
    }

    private String businessName;

    public void setBusinessName(String value) {
        this.businessName = value;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    private String businessPhoneNumber;

    public void setBusinessPhoneNumber(String value) {
        this.businessPhoneNumber = value;
    }

    public String getBusinessPhoneNumber() {
        return this.businessPhoneNumber;
    }

    private String businessAddress;

    public void setBusinessAddress(String value) {
        this.businessAddress = value;
    }

    public String getBusinessAddress() {
        return this.businessAddress;
    }
}

