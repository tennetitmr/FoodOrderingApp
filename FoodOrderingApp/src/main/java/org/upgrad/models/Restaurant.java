package org.upgrad.models;

import javax.persistence.*;

/*
 * Restaurant model class contain all the attributes to be mapped to all the fields in the users table in the database.
 * Annotations are used to specify all the constraints to the table and table-columns in the database.
 * Here getter, setter and constructor are defined for this model class.
 */

@Entity
@Table(name="RESTAURANT")
public class Restaurant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "user_rating", nullable = false)
    private Double userRating;

    @Column(name = "average_price_for_two", nullable = false)
    private int avgPrice;

    @Column(name = "number_of_users_rated", nullable = false)
    private int numberUsersRated;

    @OneToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)

    @Transient
    private Iterable<Category> categories;

    // Getters and setters for variables

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurant_name) {
        this.restaurantName = restaurantName;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double user_rating) {
        this.userRating = userRating;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(int avgPrice) {
        this.avgPrice = avgPrice;
    }

    public int getNumberUsersRated() {
        return numberUsersRated;
    }

    public void setNumberUsersRated(int numberUsersRated) {
        this.numberUsersRated = numberUsersRated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Iterable<Category> getCategories() {
        return categories;
    }

    public void setCategories(Iterable<Category> category) {
        this.categories = category;
    }

}