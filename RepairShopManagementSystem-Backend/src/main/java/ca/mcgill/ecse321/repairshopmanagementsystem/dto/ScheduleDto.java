package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDto {
    private List<ShiftDto> shifts;
    private int id;
    private RepairShopManagementSystemDto repairShopManagementSystem;

    public ScheduleDto() {
    }

    public ScheduleDto(int id,RepairShopManagementSystemDto system) {
        this.id=id;
        this.shifts = new ArrayList<>();
        this.repairShopManagementSystem = system;
    }

    public ScheduleDto(int id,List<ShiftDto> timeSlot, RepairShopManagementSystemDto repairShopManagementSystem) {
        this.shifts = timeSlot;
        this.id=id;
        this.repairShopManagementSystem = repairShopManagementSystem;
    }

    public List<ShiftDto> getShifts() {
        return this.shifts;
    }
    public int getId() {
        return this.id;
    }
   

    public RepairShopManagementSystemDto getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }
}