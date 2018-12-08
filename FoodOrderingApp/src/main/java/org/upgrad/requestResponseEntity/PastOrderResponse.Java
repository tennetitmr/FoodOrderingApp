package org.upgrad.requestResponseEntity;

import org.upgrad.models.*;

import java.util.Date;
import java.util.List;

/*
 * PastOrderResponse : All attributes to be returned as a response.
 *                  : Contains Constructor + Getter +  Setter.
 */

 public class PastOrderResponse {

    private Integer id;

    private double bill;

    private Coupon coupon;

    private double discount;

    private Date date = new Date();

    private Payment payment;

    private User user;

    private Address address;

    private List<OrderItemResponse> orderItems;

    public PastOrderResponse() {
    }

    public PastOrderResponse(Integer id, double bill, Coupon coupon, double discount, Date date, Payment payment, User user, Address address, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.payment = payment;
        this.user = user;
        this.address = address;
        this.orderItems = orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItemResponse> getOrderItem() {
        return orderItems;
    }

    public void setOrderItem(List<OrderItemResponse> orderItems) {
        this.orderItems = orderItems;
    }
}
