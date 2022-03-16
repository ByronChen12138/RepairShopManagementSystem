package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.HashSet;
import java.util.Set;

public class RepairShopManagementSystemDto {
    private Set<UserDto> user;
    private Set<SpaceDto> space;
    private Set<ScheduleDto> schedules;
    private String businessName;
    private String businessPhoneNumber;
    private String businessAddress;

    public RepairShopManagementSystemDto() {
    }

    public RepairShopManagementSystemDto(String businessName, String businessPhoneNumber, String businessAddress) {
        this.businessName = businessName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessAddress = businessAddress;
        this.user = new HashSet<>();
        this.space = new HashSet<>();
        this.schedules = new HashSet<>();
    }

    public RepairShopManagementSystemDto(Set<UserDto> user, Set<SpaceDto> space, Set<ScheduleDto> schedule, String businessName, String businessPhoneNumber, String businessAddress) {
        this.user = user;
        this.space = space;
        this.schedules = schedule;
        this.businessName = businessName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessAddress = businessAddress;
    }

    public Set<UserDto> getUser() {
        return user;
    }

    public Set<SpaceDto> getSpace() {
        return space;
    }

    public Set<ScheduleDto> getSchedules() {
        return schedules;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }
}
