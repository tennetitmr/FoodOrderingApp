package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

    // Read all restaurants in ascending order
    @Query(nativeQuery = true,value="SELECT * FROM RESTAURANT ORDER BY RESTAURANT_NAME ASC")
    Iterable<Restaurant> findAllSorted();

    // Read restaurant using the restaurant name
    @Query(nativeQuery = true,value="SELECT * FROM RESTAURANT WHERE RESTAURANT_NAME ILIKE %?1% ORDER BY RESTAURANT_NAME ASC")
    Iterable<Restaurant> getRestaurantsByRestName(String name);

    // Read category id using restaurant id
    @Query(nativeQuery = true,value="SELECT CATEGORY_ID FROM RESTAURANT_CATEGORY INNER JOIN CATEGORY on CATEGORY.ID= RESTAURANT_CATEGORY.CATEGORY_ID WHERE RESTAURANT_ID=?1 ORDER BY CATEGORY.CATEGORY_NAME ASC")
    Iterable<Integer> getCategoryId(int id);

    // Read all restaurant using id
    @Query(nativeQuery = true,value="SELECT * FROM RESTAURANT WHERE ID=?1")
    Restaurant getRestaurantById(int id);

    // Read restaurant Id using category name
    @Query(nativeQuery = true,value="SELECT RESTAURANT_ID from RESTAURANT_CATEGORY as res INNER JOIN CATEGORY on res.CATEGORY_ID = CATEGORY.ID WHERE UPPER(CATEGORY.CATEGORY_NAME)=UPPER(?1)")
    Iterable<Integer> getRestaurantIdByCategoryName(String name);

    // Update details for a given user.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE RESTAURANT SET USER_RATING =?2,NUMBER_OF_USERS_RATED=?3 WHERE id=?1")
    int updateRestaurantDetails(int id, double rating, int numberOfUsers);

}

