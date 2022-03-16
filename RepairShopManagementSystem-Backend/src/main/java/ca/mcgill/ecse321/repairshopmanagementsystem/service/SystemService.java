package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.RepairShopManagementSystemRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepairShopManagementSystem;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class SystemService {

    @Autowired
    private RepairShopManagementSystemRepository systemRepository;

    @Transactional
    public RepairShopManagementSystem createSystem(String businessName, String phoneNo, String address) {
        RepairShopManagementSystem system = new RepairShopManagementSystem();

        String error = "";

        if (businessName == null || businessName.trim().length() == 0) {
            error = error + "Business name cannot be empty! ";
        } else if (!Util.isBusinessNameCorrect(businessName)) {
            error = error + "Business name illegal! ";
        }

        if (phoneNo == null || phoneNo.trim().length() == 0) {
            error = error + "Business phone number cannot be empty! ";
        } else if (!Util.isPhoneNoCorrect(phoneNo)) {
            error = error + "Business phone number illegal! ";
        }

        if (address == null || address.trim().length() == 0) {
            error = error + "Business address cannot be empty! ";
        } else if (!Util.isAddressCorrect(address)) {
            error = error + "Business address illegal! ";
        }

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        system.setBusinessName(businessName);
        system.setBusinessPhoneNumber(phoneNo);
        system.setBusinessAddress(address);
        system.setSpace(new HashSet<>());
        system.setSchedule(new HashSet<>());
        system.setUser(new HashSet<>());
        systemRepository.save(system);
        return system;
    }

    @Transactional
    public RepairShopManagementSystem update_most_recent(String address, String phoneNo) {
        if (address == null || address.equals(""))
            throw new IllegalArgumentException("New address cannot be null!");
        if (phoneNo == null || phoneNo.equals(""))
            throw new IllegalArgumentException("New phone no cannot be null!");
        if (!Util.isPhoneNoCorrect(phoneNo))
            throw new IllegalArgumentException("Phone number format is not correct. It must be a 10-digit number.");
        RepairShopManagementSystem system = systemRepository.findFirstByOrderByIdDesc();
        system.setBusinessAddress(address);
        system.setBusinessPhoneNumber(phoneNo);
        systemRepository.save(system);
        return system;
    }

    @Transactional
    public RepairShopManagementSystem getMostRecentSystem() {
        return systemRepository.findFirstByOrderByIdDesc();
    }

    @Transactional
    public List<RepairShopManagementSystem> getAllSystems() {
        return toList(systemRepository.findAll());
    }

    @Transactional
    public RepairShopManagementSystem getSystem(String businessName, String phoneNo, String address) {
        return systemRepository.findRepairShopManagementSystemByBusinessNameAndBusinessAddressAndBusinessPhoneNumber(businessName, address, phoneNo);
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}

