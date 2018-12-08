package org.upgrad.services;

import org.upgrad.models.Item;
import java.util.List;

public interface ItemService {

    List<Item> getItemByPopularity(int restaurantId);

    //Get the item by id
    Item getItemById(int id);
}
