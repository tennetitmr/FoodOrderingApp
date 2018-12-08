package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Coupon;
import org.upgrad.models.Order;
import org.upgrad.models.OrderItem;
import org.upgrad.repositories.CouponRepository;
import org.upgrad.repositories.OrderItemRepository;
import org.upgrad.repositories.OrderRepository;
import org.upgrad.requestResponseEntity.OrderItemResponse;
import org.upgrad.requestResponseEntity.PastOrderResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(CouponRepository couponRepository,OrderRepository orderRepository,OrderItemRepository orderItemRepository) {
        this.couponRepository = couponRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository=orderItemRepository;
    }

    @Override
    public Coupon getCoupon(String couponName){
        return couponRepository.getCouponByName(couponName);
    }

    @Override
    public Iterable<OrderItem> getOrderItemsByOrderId(Integer id){ return orderItemRepository.getOrderItemsByOrderId(id); }

    @Override
    public Iterable<PastOrderResponse> getOrdersResponseByUser(Integer id){

        List<PastOrderResponse> ordersByUser = new ArrayList<>();
        //Getting the order details by user id
        Iterable<Order> order = orderRepository.getOrderByUserId(id);

        for (Order ord : order) {
            //Getting the orderitem details for each order id
            Iterable<OrderItem> orderItem = orderItemRepository.getOrderItemsByOrderId(ord.getId());
            List<OrderItemResponse> orderItemList = new ArrayList<>();
            if (orderItem!=null) {
                for (OrderItem oItemList: orderItem) {
                    //Add the orderitem details to the response list
                    OrderItemResponse orderItemResponse = new OrderItemResponse(oItemList.getId(),oItemList.getItem(),oItemList.getQuantity(),
                            oItemList.getPrice());
                    orderItemList.add(orderItemResponse);
                }
            }
            //Add the complete order details to PastOrderResponse
            PastOrderResponse orderResponse = new PastOrderResponse(ord.getId(), ord.getBill(), ord.getCoupon(),
                    ord.getDiscount(),ord.getDate(), ord.getPayment(), ord.getUser(), ord.getAddress(), orderItemList);
            ordersByUser.add(orderResponse);
        }

        return ordersByUser;
    }

    // Save the order with temporary address
    @Override
    public void addOrder(Integer orderId,double bill, Integer couponId, double discount, Date date, Integer paymentId, Integer userId, Integer addressId) {
        orderRepository.addOrder(orderId,bill,couponId,discount,date,paymentId,userId,addressId);

    }

    // Save the order with perm address
    @Override
    public void addOrderWithPermAddress(Integer orderId,double bill, Integer couponId, double discount, Date date, Integer paymentId, Integer userId, Integer addressId) {
        orderRepository.addOrder(orderId,bill,couponId,discount,date,paymentId,userId,addressId);

    }

    // Get the last saved order id
    @Override
    public Integer findLatestOrderId(){
        return orderRepository.findLatestOrderId();
    }

    // Get the last saved orderItem id
    @Override
    public Integer findLatestOrderItemId(){
        return orderItemRepository.findLatestOrderItemId();
    }

    // save the orderItem
    @Override
    public void addOrderItem(Integer id, Integer orderId,Integer itemId,Integer quantity,Integer price) {

        orderItemRepository.addOrderItem(id,orderId,itemId,quantity,price);
    }

    //Get the order details without response format
    @Override
    public List<Order> getOrdersByUser(Integer id){
        return orderRepository.getOrderByUser(id);
    }

}
