package org.upgrad.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* Controller  ------> Services Layer ---> Data Access Layer (Model)
 * Item model: Map attributes  ----> columns in the Item table in the restaurant database.
 * Also,Contains Annotations, getters and setters. Annotations map the fields to table columns.
 */

@Entity
@Table(name="item")
public class Item {

    @Id
    private int id;

    @Column(name="item_name")
    private String itemName;

    private int price;

    private String type;

    // Getters & Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
