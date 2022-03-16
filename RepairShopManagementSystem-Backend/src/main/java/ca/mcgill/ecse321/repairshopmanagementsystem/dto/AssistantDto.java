package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.*;

public class AssistantDto extends UserDto {
    private Set<ServiceDto> services;
    private Set<ShiftDto> shifts;

    public AssistantDto() {
        super();
    }

    public AssistantDto(String username, String password, String name) {
        super(username, password, name);
    }

    public AssistantDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
        this.services = new HashSet<>();
        this.shifts = new HashSet<>();
    }

    public AssistantDto(String username, String password, String name, Set<ShiftDto> shifts) {
        super(username, password, name);
        this.shifts = shifts;
    }

    public AssistantDto(String username, String password, String name, RepairShopManagementSystemDto system, Set<ServiceDto> services, Set<ShiftDto> shifts) {
        super(username, password, name, system);
        this.services = services;
        this.shifts = shifts;
    }

    public Set<ServiceDto> getServices() {
        return services;
    }

    public Set<ShiftDto> getShifts() {
        return shifts;
    }
}