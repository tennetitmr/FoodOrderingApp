package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Restaurant;
import org.upgrad.models.States;

@Repository
public interface StateRepository extends CrudRepository<States, Integer> {

    //Read the names of the state using state id 
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    States isValidState(Integer id);

    //Read all states
    @Query(nativeQuery = true,value="select * from states")
    Iterable<States> getAllStates();

    //Read the name of the state for a given state_id.
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    States getStatebyId(Integer id);

}
