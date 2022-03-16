package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.RepairShopManagementSystemDto;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepairShopManagementSystem;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/system")
public class RepairShopManagementSystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping(value = "create")
    public RepairShopManagementSystemDto createSystem(@RequestBody RepairShopManagementSystemDto system) {
        RepairShopManagementSystem s = systemService.createSystem(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress());
        return convertToDto(s);
    }
   @PutMapping(value="update_most_recent")
   public  RepairShopManagementSystemDto updateMostRecent(@RequestParam String updatedAddress, @RequestParam String updatedPhoneNo) {
	   return convertToDto(systemService.update_most_recent(updatedAddress, updatedPhoneNo));
   }
    @GetMapping(value = "most_recent")
    public RepairShopManagementSystemDto getMostRecentSystem() {
        return convertToDto(systemService.getMostRecentSystem());
    }

    @GetMapping(value = "GutenTag")
    public String sayHello() {
        return "Salve, quis agis?";
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress());
    }
}

