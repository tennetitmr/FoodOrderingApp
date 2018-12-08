package org.upgrad.services;

import org.upgrad.models.Restaurant;
//import org.upgrad.models.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;

/*               interface
/*  Controller ------------------> Service Methods
 *  RestaurantService Interface : List of all the service methods that exist in the Restaurant service implementation class.
 */

public interface RestaurantService {

    Iterable<RestaurantResponse> getAllRestaurant();
    Iterable<RestaurantResponse> getRestaurantByName(String name);
    Iterable<RestaurantResponse> getRestaurantByCategory(String category);
    RestaurantResponseCategorySet getRestaurantDetails(int id);
    Restaurant updateRating(int restaurantId, int rating);
}
