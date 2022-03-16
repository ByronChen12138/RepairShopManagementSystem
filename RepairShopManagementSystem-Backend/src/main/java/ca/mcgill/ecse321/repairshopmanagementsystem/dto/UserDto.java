package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

public abstract class UserDto {
    private String username;
    private String password;
    private String name;
    private RepairShopManagementSystemDto system;

    public UserDto() {

    }

    public UserDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public UserDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.system = system;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }

    public RepairShopManagementSystemDto getSystem() {
        return system;
    }


}