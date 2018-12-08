package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.upgrad.models.Restaurant;
//import org.upgrad.models.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;
import org.upgrad.services.RestaurantService;
import org.upgrad.services.UserAuthTokenService;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    /** To read all restaurants info
     * @return returns all restaurants
     */
    @GetMapping("")
    public ResponseEntity<?> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurant(), HttpStatus.OK);
    }

    /**
     * This is GET API -->  Return all Restaurants by Restaurant Name
     * @param restaurantName --> name of restaurant
     * @return all restaurants by restaurantName along with categories
     */
    @GetMapping("/name/{restaurantName}")
    public ResponseEntity<?> getAllRestaurantsByRestaurantName(@PathVariable String restaurantName) {
        Iterable<RestaurantResponse> restaurant = restaurantService.getRestaurantByName(restaurantName);
        if (restaurant!=null && restaurant.iterator().hasNext()) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
        else {
                return new ResponseEntity<>("No Restaurant by this name!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This is GET API --> Return all Restaurants along with Categories
     * @param categoryName --> Category Name
     * @return all restaurants for input category name
     */
    @GetMapping("category/{categoryName}")
    public ResponseEntity<?> getRestaurantsByCategoryName(@PathVariable String categoryName) {
        List<RestaurantResponse> restaurant = (List<RestaurantResponse>) restaurantService.getRestaurantByCategory(categoryName);
        if (restaurant!=null && restaurant.size()!=0) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No Restaurant under this category!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * It is GET API --> to read restaurant details with Categories and Items from every category.
     * @param restaurantId --> restaurant id
     * @return restaurant details
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable int restaurantId) {
        RestaurantResponseCategorySet restaurant =  restaurantService.getRestaurantDetails(restaurantId);
        if (restaurant!=null) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No Restaurant by this id!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT API --> to update user rating
     * @param restaurantId --> restaurant id
     * @param rating       --> user rating to update
     * @param accessToken  --> access token by user
     * @return updated values of restaurant details
     */
    @PutMapping("/{restaurantId}")
    @CrossOrigin
    public ResponseEntity<?> updateRestaurantDetails(@PathVariable int restaurantId, @RequestParam int rating, @RequestHeader String accessToken) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            Restaurant restaurant = restaurantService.updateRating(restaurantId,rating);
            if (restaurant!=null) {
                return new ResponseEntity<>(restaurant, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("No Restaurant by this id!", HttpStatus.BAD_REQUEST);
            }
        }
      }
    }
