package org.upgrad.services;

import org.upgrad.models.Restaurant;
//import org.upgrad.models.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;

public interface RestaurantService {

    Iterable<RestaurantResponse> getAllRestaurant();
    Iterable<RestaurantResponse> getRestaurantByName(String name);
    Iterable<RestaurantResponse> getRestaurantByCategory(String category);
    Restaurant getRestaurantDetails(int id);
    Restaurant updateRating(int restaurantId, Double rating);
}