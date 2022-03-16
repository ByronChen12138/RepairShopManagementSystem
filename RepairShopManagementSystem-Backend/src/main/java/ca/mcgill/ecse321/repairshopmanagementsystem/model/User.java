package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public abstract class User {
    private String username;

    public void setUsername(String value) {
        this.username = value;
    }

    public String getUsername() {
        return this.username;
    }

    private String password;

    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }

    private String name;

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    private RepairShopManagementSystem repairShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }

    public void setRepairShopManagementSystem(RepairShopManagementSystem repairShopManagementSystem) {
        this.repairShopManagementSystem = repairShopManagementSystem;
    }

    private Integer userId;

    public void setUserId(Integer value) {
        this.userId = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getUserId() {
        return this.userId;
    }
}