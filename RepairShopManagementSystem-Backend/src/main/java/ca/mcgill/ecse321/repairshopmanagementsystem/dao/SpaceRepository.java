package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Space;
import org.springframework.data.repository.CrudRepository;

public interface SpaceRepository extends CrudRepository<Space, Integer> {
	Space findSpaceBySpaceId(Integer spaceId);
}
