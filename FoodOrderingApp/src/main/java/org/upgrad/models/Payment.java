package org.upgrad.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;


/* Controller  ------> Services Layer ---> Data Access Layer (Model)
 * Address model: Map attributes  ----> columns in the PAYMENT table in the restaurant database.
 * Also,Contains Annotations, getters and setters. Annotations map the fields to table columns.
 */

@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_name")
    private String paymentName;

    public Payment(){}

    public Payment(String paymentName) {
        this.paymentName = paymentName;
    }

    //Getter & Setter methods
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}
