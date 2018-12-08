package org.upgrad.services;

import org.upgrad.models.Payment;

public interface PaymentService {

    //Get all payment methods
    Iterable<Payment> getPaymentMethods();
}
