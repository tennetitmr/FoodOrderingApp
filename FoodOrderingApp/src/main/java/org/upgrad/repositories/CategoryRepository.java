package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Category;
import org.upgrad.models.Item;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {

    // Get all category details.
    @Query(nativeQuery = true,value = "SELECT * FROM CATEGORY")
    Iterable<Category> getCategories();

    // Get category details by name.
    @Query(nativeQuery = true,value = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME=?1")
    Category getCategoryByName(String categoryName);

    // Get category count by name.
    @Query(nativeQuery = true,value = "SELECT count(*) FROM CATEGORY WHERE CATEGORY_NAME=?1")
    Integer getCategoryCountByName(String categoryName);

    // Get category count by id.
    @Query(nativeQuery = true,value = "SELECT * FROM CATEGORY WHERE ID=?1")
    Category getCategoryById(int id);

    @Query(nativeQuery = true,value = "SELECT CATEGORY_NAME FROM CATEGORY WHERE ID=?1 ORDER BY CATEGORY_NAME ASC")
    String getCategoryNameById(int id);


    @Query(nativeQuery = true,value = "SELECT CATEGORY.* FROM CATEGORY INNER JOIN RESTAURANT_CATEGORY ON CATEGORY.ID=RESTAURANT_CATEGORY.CATEGORY_ID WHERE RESTAURANT_ID=?1")
    Iterable<Category> getCategoriesByRestId(int id);

}