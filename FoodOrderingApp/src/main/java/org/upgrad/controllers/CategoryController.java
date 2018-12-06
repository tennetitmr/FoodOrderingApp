package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.Category;
import org.upgrad.services.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /*
        This is used to get all categories that are stored in DB.
     */
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    /*
        This function returns the category details corresponding to category name.
     */
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getCategoriesByName(@PathVariable("categoryName") String categoryName) {
        Category category = categoryService.getCategory(categoryName);
        if (category != null) {
            return new ResponseEntity<>(categoryService.getCategory(categoryName), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Category by this name!", HttpStatus.NOT_FOUND);
        }
    }

}