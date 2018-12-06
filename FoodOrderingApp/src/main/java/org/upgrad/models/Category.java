package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

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
