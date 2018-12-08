package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/* Controller  ------> Services Layer ---> Data Access Layer (Model)
 * Category model: Map attributes  ----> columns in the Category table in the restaurant database.
 * Also,Contains Annotations, getters and setters. Annotations map the fields to table columns.
 */

@Entity
@Table(name="category")
public class Category {

    @Id
    private int id;

    @Column(name = "category_name")
    private String categoryName;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Item item;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
