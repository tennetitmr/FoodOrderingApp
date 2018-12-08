package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.PaymentService;
import org.upgrad.services.UserAuthTokenService;

@RestController
@RequestMapping("")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @GetMapping("/payment")
    @CrossOrigin
    ResponseEntity<?> getCouponByCouponName(@RequestHeader String accessToken) {

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        //**To check whether the user is logged in**
        if (usertoken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } //**To check whether the user is not logged out**//
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null)  {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(paymentService.getPaymentMethods(), HttpStatus.OK);
        }
    }
}
