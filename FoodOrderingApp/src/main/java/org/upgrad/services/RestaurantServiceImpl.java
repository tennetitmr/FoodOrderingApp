package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.Item;
import org.upgrad.models.Restaurant;
//import org.upgrad.models.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.repositories.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        Iterable<Restaurant> rest = restaurantRepository.findAll();
        for (Restaurant restaurant: rest) {
            List<String> categoryList = new ArrayList<>();
            List<Integer> catIds = (List<Integer>) restaurantRepository.getCategoryId(restaurant.getId());
            if (catIds.size()!=0) {
                for (Integer catid: catIds) {
                    String restCategory = categoryRepository.getCategoryNameById(catid);
                    categoryList.add(restCategory);
                }
            }
            RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString());
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
                RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString());
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
                RestaurantResponse resp = new RestaurantResponse(restaurant.getId(),restaurant.getRestaurantName(),restaurant.getPhotoUrl(),restaurant.getUserRating(),restaurant.getAvgPrice(),restaurant.getNumberUsersRated(),restaurant.getAddress(),categoryList.toString());
                restaurants.add(resp);
            }
        }
        return restaurants;
    }

    @Override
    public Restaurant getRestaurantDetails(int id) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(id);
        Iterable<Category> categories = categoryRepository.getCategoriesByRestId(id);
        if (categories.iterator().hasNext()) {
            for(Category category: categories) {
                Iterable<Item> items = itemRepository.getRestaurantItems(category.getId(),id);
                for (Item item: items) {
                    category.setItem(item);
                }
            }
        }
        restaurant.setCategories(categories);
        return restaurant;
    }

    @Override
    public Restaurant updateRating(int id,Double rating) {
        return restaurantRepository.updateRestaurantDetails(id,rating);
    }
}