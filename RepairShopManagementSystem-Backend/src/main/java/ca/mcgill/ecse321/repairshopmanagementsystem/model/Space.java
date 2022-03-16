package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;

@Entity
public class Space {
    private Integer spaceId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getSpaceId() {
        return this.spaceId;
    }

    public void setSpaceId(Integer value) {
        this.spaceId = value;
    }

    private RepairShopManagementSystem repairShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }

    public void setRepairShopManagementSystem(RepairShopManagementSystem repariShopManagementSystem) {
        this.repairShopManagementSystem = repariShopManagementSystem;
    }

    private Integer maxWeightLoad;

    public void setMaxWeightLoad(Integer value) {
        this.maxWeightLoad = value;
    }

    public Integer getMaxWeightLoad() {
        return this.maxWeightLoad;
    }
}
