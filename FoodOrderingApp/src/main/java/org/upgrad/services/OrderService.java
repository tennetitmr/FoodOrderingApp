package org.upgrad.services;

import org.upgrad.models.Coupon;
import org.upgrad.models.Order;
import org.upgrad.models.OrderItem;
import org.upgrad.requestResponseEntity.PastOrderResponse;

import java.util.Date;
import java.util.List;

/*               interface
/*  Controller ------------------> Service Methods
 *  OrderService Interface : List of all the service methods that exist in the Order service implementation class.
 */

public interface OrderService {

    //**To read all coupon details by Name**
    Coupon getCoupon(String couponName);

    //**To read all orderItem details by orderId**
    Iterable<OrderItem> getOrderItemsByOrderId(Integer id);

    //**To read orders response details by UserId**
    Iterable<PastOrderResponse> getOrdersResponseByUser(Integer id);

    //**To read orders details by UserId**
    List<Order> getOrdersByUser(Integer id);

    //**To add order with temporary address**
    void addOrder(Integer orderId,double bill, Integer couponId, double discount, Date date, Integer paymentId,Integer userId, Integer addressId);

    //**To add order with permanent address**
    void addOrderWithPermAddress(Integer orderId,double bill, Integer couponId, double discount, Date date, Integer paymentId,Integer userId, Integer addressId);

    //**To add OrderItems**
    void addOrderItem(Integer id, Integer orderId,Integer itemId,Integer quantity,Integer price);

    //**To read/find the latest order id**
    Integer findLatestOrderId();

    Integer findLatestOrderItemId();
}

