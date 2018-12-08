package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Payment;
import org.upgrad.repositories.PaymentRepository;

import javax.transaction.Transactional;

/*  Controller ---> Service --> Service Implementation
    PaymentServiceImplementation contains Payment Service related methods.
 */

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Iterable<Payment> getPaymentMethods(){
        return paymentRepository.getPaymentDetails();
    }
}
