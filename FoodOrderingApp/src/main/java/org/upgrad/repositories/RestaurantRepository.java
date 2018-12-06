package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

    // Retrieves restaurant based on input restaurant match
    @Query(nativeQuery = true,value="SELECT * FROM RESTAURANT WHERE RESTAURANT_NAME ILIKE %?1% ORDER BY USER_RATING")
    Iterable<Restaurant> getRestaurantsByRestName(String name);

    // Retrieves category id based on restaurant id
    @Query(nativeQuery = true,value="SELECT CATEGORY_ID FROM RESTAURANT_CATEGORY WHERE RESTAURANT_ID=?1")
    Iterable<Integer> getCategoryId(int id);

    // retrieves all restaurant based on id
    @Query(nativeQuery = true,value="SELECT * FROM RESTAURANT WHERE ID=?1")
    Restaurant getRestaurantById(int id);

    // retrieves restaurant Id according to category name
    @Query(nativeQuery = true,value="SELECT RESTAURANT_ID from RESTAURANT_CATEGORY as res INNER JOIN CATEGORY on res.CATEGORY_ID = CATEGORY.ID WHERE CATEGORY.CATEGORY_NAME=?1")
    Iterable<Integer> getRestaurantIdByCategoryName(String name);

    // Method to update details for particular user.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE RESTAURANT SET USER_RATING =?2 WHERE id=?1")
    Restaurant updateRestaurantDetails(int id, Double rating);

}
