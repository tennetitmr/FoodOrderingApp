package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.Item;
import org.upgrad.models.Restaurant;
import org.upgrad.requestResponseEntity.CategoryResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.repositories.*;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*  Controller ---> Service --> Service Implementation
    RestaurantServiceImplementation contains Restaurant Service related methods.
 */

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;



    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Iterable<RestaurantResponse> getAllRestaurant() {
        List<RestaurantResponse> restaurants = new ArrayList<>();
        Iterable<Restaurant> rest = restaurantRepository.findAllSorted();
        for (Restaurant restaurant: rest) {
            List<String> categoryList = new ArrayList<>();
            List<Integer> catIds = (List<Integer>) restaurantRepository.getCategoryId(restaurant.getId());
            if (catIds.size()!=0) {
                for (Integer catid: catIds) {
                    String restCategory = categoryRepository.getCategoryNameById(catid);
                    categoryList.add(restCategory);
                }
            }
            RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString().replace("[", "").replace("]", ""));
            restaurants.add(resp);
            }
        return restaurants;
    }


    @Override
    public Iterable<RestaurantResponse> getRestaurantByName(String name) {
        List<RestaurantResponse> restaurants = new ArrayList<>();
        Iterable<Restaurant> restaurantList = restaurantRepository.getRestaurantsByRestName(name);

        if (restaurantList.iterator().hasNext()) {
            for (Restaurant restaurant: restaurantList) {
                List<Integer> catIds = (List<Integer>) restaurantRepository.getCategoryId(restaurant.getId());
                List<String> categoryList = new ArrayList<>();
                if (catIds.size()!=0) {
                    for (Integer catid: catIds) {
                        String restCategory = categoryRepository.getCategoryNameById(catid);
                        categoryList.add(restCategory);
                    }
                }
                RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString().replace("[", "").replace("]", ""));
                restaurants.add(resp);
            }
        }
        return restaurants;
    }

    @Override
    public Iterable<RestaurantResponse> getRestaurantByCategory(String categoryName) {
        List<RestaurantResponse> restaurants = new ArrayList<>();
        Iterable<Integer> restaurant_ids= restaurantRepository.getRestaurantIdByCategoryName(categoryName);
        for (Integer resId : restaurant_ids) {
            Restaurant restaurant = restaurantRepository.getRestaurantById(resId);
            Iterable<Integer> catIds = restaurantRepository.getCategoryId(resId);
            List<String> categoryList = new ArrayList<>();
            if (restaurant != null) {
                for (Integer catId : catIds) {
                    String restCategory = categoryRepository.getCategoryNameById(catId);
                    categoryList.add(restCategory);
                }
                RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString().replace("[", "").replace("]", ""));
                restaurants.add(resp);
            }
        }
        return restaurants;
    }

    @Override
    public RestaurantResponseCategorySet getRestaurantDetails(int id) {
       Restaurant restaurant = restaurantRepository.getRestaurantById(id);
       RestaurantResponseCategorySet restaurantResponseCategorySet = null;
       Set<Category> categories = categoryRepository.getCategoriesByRestId(id);
       Set<CategoryResponse> categorySet = new HashSet<>();
       if (categories.iterator().hasNext()) {
           for(Category category: categories) {
               Set<Item> items = itemRepository.getRestaurantItems(category.getId(),id);
               CategoryResponse categoryResponse = new CategoryResponse(category.getId(),category.getCategoryName(),items);
               categorySet.add(categoryResponse);
           }
       }
       if (restaurant!=null) {
           restaurantResponseCategorySet = new RestaurantResponseCategorySet(id,restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categorySet);
       }
       return restaurantResponseCategorySet;
    }

    @Override
    public Restaurant updateRating(int id,int rating) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(id);
        if (restaurant!=null) {
            restaurant.setNumberUsersRated(restaurant.getNumberUsersRated() + 1);
            Double newRating = ((restaurant.getUserRating() * restaurant.getNumberUsersRated()) + rating) / restaurant.getNumberUsersRated();
            Double roundOff = (double) Math.round(newRating * 100.0) / 100.0;
            restaurant.setUserRating(roundOff);
            restaurantRepository.updateRestaurantDetails(id, roundOff, restaurant.getNumberUsersRated());
        }
        return restaurant;
    }
}
