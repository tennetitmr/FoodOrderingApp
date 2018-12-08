package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.OrderItem;

// Controller  ------> Services Layer ---> Data Access Layer (Repository) ----> Database
//                                                                       orderItemRepository
// This OrderItemRepository interface is used as:  orderItem Service ----------------------------> ORDER_ITEM Table

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    // Get orderItem details by OrderId.
    @Query(nativeQuery = true,value = "SELECT a.* FROM ORDER_ITEM a,ORDERS b WHERE b.ID=a.ORDER_ID and ORDER_ID=?1 ")
    Iterable<OrderItem> getOrderItemsByOrderId(Integer id);

    // For adding orderItem details in table.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO ORDER_ITEM (id,order_id,item_id,quantity,price) VALUES (?1,?2,?3,?4,?5)")
    void addOrderItem(Integer id,Integer orderId,Integer itemId,Integer quantity,Integer price);

    // Get last saved orderItem Id.
    @Query(nativeQuery = true,value = "SELECT max(ID) FROM ORDER_ITEM")
    Integer findLatestOrderItemId();
}
