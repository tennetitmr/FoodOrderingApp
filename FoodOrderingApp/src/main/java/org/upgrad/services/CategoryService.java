package org.upgrad.services;

import org.upgrad.models.Category;

/*
 * This CategoryService interface gives the list of all the service that exist in the category service implementation class.
 * Controller class will be calling the service methods by this interface.
 */
public interface CategoryService {

    // Get all category details.
    Iterable<Category> getAllCategories();

    //Get category details by name.
    Category getCategory(String categoryName);

    //Get category count by name.
    Integer getCategoryCount(String categoryName);

}