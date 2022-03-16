package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Schedule {

    public Integer getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(Integer yearAndWeekNo) {
        this.weekNo = yearAndWeekNo;
    }

    private Integer weekNo;

    private Integer id;

    public void setId(Integer value) {
        this.id = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    private Set<Shift> timeSlot;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.ALL})
    public Set<Shift> getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(Set<Shift> timeSlots) {
        this.timeSlot = timeSlots;
    }

    private RepairShopManagementSystem repairShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }

    public void setRepairShopManagementSystem(RepairShopManagementSystem repariShopManagementSystem) {
        this.repairShopManagementSystem = repariShopManagementSystem;
    }

}
