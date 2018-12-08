package org.upgrad.models;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/* Controller  ------> Services Layer ---> Data Access Layer (Model)
 * ORDER model: Map attributes  ----> columns in the ORDER table in the restaurant database.
 * Also,Contains Annotations, getters and setters. Annotations map the fields to table columns.
 */

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bill",nullable = false)
    private double bill;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Coupon coupon;

    @Column(name = "discount")
    private double discount;

    @Column(name = "date")
    private Date date = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Payment payment;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

    public Order(){}

    public Order(double bill, Coupon coupon, double discount, Date date, User user, Payment payment, Address address) {
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.user = user;
        this.payment = payment;
        this.address = address;
    }

    //Getter & Setter methods
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
