package ca.mcgill.ecse321.repairshopmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
@EnableJpaRepositories("ca.mcgill.ecse321.repairshopmanagementsystem.dao")
@EntityScan("ca.mcgill.ecse321.repairshopmanagementsystem.model")
public class RepairShopManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairShopManagementSystemApplication.class, args);
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hello world!";
    }


}
