package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Assistant;

import org.springframework.data.repository.CrudRepository;

public interface AssistantRepository extends CrudRepository<Assistant, Integer> {
    Assistant findAssistantByUserId(Integer userId);

    Assistant findAssistantByUsername(String username);
}