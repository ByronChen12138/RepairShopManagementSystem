package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

public class OwnerDto extends UserDto {

    public OwnerDto() {
        super();
    }

    public OwnerDto(String username, String password, String name) {
        super(username, password, name);
    }

    public OwnerDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
    }

}