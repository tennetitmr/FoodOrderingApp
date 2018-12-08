package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.Order;
import org.upgrad.models.OrderItem;

import java.util.Date;
import java.util.List;

// Controller  ------> Services Layer ---> Data Access Layer (Repository) ----> Database
//                                                  OrderRepository
// This OrderRepository interface: Order Service ---------------------> Order Table

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    // Read order details by Id.
    @Query(nativeQuery = true,value = "SELECT * FROM ORDERS WHERE USER_ID=?1 ORDER BY DATE DESC")
    Iterable<Order> getOrderByUserId(Integer id);

    // Read order details by Id.
    @Query(nativeQuery = true,value = "SELECT * FROM ORDERS WHERE USER_ID=?1 ORDER BY DATE DESC")
    List<Order> getOrderByUser(Integer id);

    // Adding order 
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO ORDERS (id,bill,coupon_id,discount,date,payment_id,user_id,address_id) VALUES (?1,?2,?3,?4,?5,?6,?7,?8)")
    void addOrder(Integer orderId,double bill, Integer couponId, double discount, Date date, Integer paymentId,Integer userId, Integer addressId);

    // Read last saved order Id.
    @Query(nativeQuery = true,value = "SELECT max(ID) FROM ORDERS")
    Integer findLatestOrderId();

}

