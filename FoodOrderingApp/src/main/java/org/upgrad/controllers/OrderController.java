package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.*;
import org.upgrad.requestResponseEntity.ItemQuantity;
import org.upgrad.requestResponseEntity.PastOrderResponse;
import org.upgrad.services.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserAuthTokenService userAuthTokenService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    //If the coupon name entered by the user matches any coupon in the database, retrieve the coupon details

    @GetMapping("/coupon/{couponName}")
    @CrossOrigin
    ResponseEntity<?> getCouponByCouponName(@PathVariable String couponName, @RequestHeader String accessToken) {

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        // Checking if user is logged in.
        if (usertoken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } // checking if user is not logged out.
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null)  {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else {

            Coupon coupon = orderService.getCoupon(couponName);

            if (coupon != null){
                return new ResponseEntity<>(coupon, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Invalid Coupon!", HttpStatus.NOT_FOUND);
            }
        }
    }

    //Retrieve all the past orders from the user sorted by their order date, with the newest order first

    @GetMapping("")
    @CrossOrigin
    ResponseEntity<?> getPastOrdersOfUser(@RequestHeader String accessToken) {

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        // Checking if user is logged in.
        if (usertoken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } // checking if user is not logged out.
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null)  {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);
            Iterable<PastOrderResponse> orderResponse = orderService.getOrdersResponseByUser(userId);
            if (orderResponse != null && orderResponse.iterator().hasNext()) {
                // if user is ordered then display order details
                return new ResponseEntity<>(orderResponse, HttpStatus.OK);
            }
            else { // if user is not ordered anything display message.
                return new ResponseEntity<>("No orders have been made yet!", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /*
     * This endpoint is used to save order.
     *
     */
    @PostMapping("")
    @CrossOrigin
    public ResponseEntity<?> saveOrder(@RequestParam Integer addressId, @RequestParam(required = false) String flatBuilNo,
                                       @RequestParam(required = false) String locality, @RequestParam(required = false) String city, @RequestParam(required = false) String zipcode,
                                       @RequestParam(required = false) Integer stateId, @RequestParam(required = false) String type ,
                                       @RequestParam(required = false) Integer paymentId, @RequestBody List<ItemQuantity> itemQuantity, @RequestParam double bill,
                                       @RequestParam(required = false) Integer couponId, @RequestParam(defaultValue = "0") double discount, @RequestHeader String accessToken) {

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        // Checking if user is logged in.
        if (usertoken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } // checking if user is not logged out.
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null)  {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } // Below condition executed when temp address details provided , though fields are optional,
        // condition made for all fields are required without all fields no meaning of address
        else if (String.valueOf(flatBuilNo) != null && locality != null && city != null &&
                String.valueOf(zipcode) != null && stateId != null) {

            Integer userId = userAuthTokenService.getUserId(accessToken);

            //Zip code format check
            if (zipcode.length() == 6 && zipcode.matches("[0-9]+")) {

                States state = addressService.isValidState(stateId);
                Integer addId = addressService.countAddress() + 1;
                String type1 = "temp";

                // Save data in address table.
                Address address = new Address(addId, flatBuilNo, locality, city, zipcode, state);
                addressService.addAddress(address);

                // Save data in userAddress table
                addressService.addUserAddress(type1, userId, addId);

                Date date = new Date();

                //Get last saved orderId
                Integer orderId = orderService.findLatestOrderId();

                if (paymentId == null) {
                    paymentId = 1; //default value, its required because violates foreign key constraint "orders_payment_id_fkey"
                }

                if (couponId == null) {
                    couponId = 1; //default value, its required because violates foreign key constraint "orders_coupon_id_fkey"
                }

                //Save order
                orderService.addOrder(orderId+1,bill, couponId, discount, date,  paymentId,userId, addId);

                //Get the last saved orderItem id from order_item table
                Integer orderItemId = orderService.findLatestOrderItemId();

                for (ItemQuantity iq : itemQuantity ){
                    orderItemId = orderItemId + 1;
                    Item item = itemService.getItemById(iq.getItemId());
                    //price calculation based on quantity
                    Integer price = (item.getPrice()*iq.getQuantity());
                    //save orderItem
                    orderService.addOrderItem(orderItemId,orderId,iq.getItemId(),iq.getQuantity(),price);
                }

                //To return saved orderId
                orderId = orderService.findLatestOrderId();

                return new ResponseEntity<>(orderId, HttpStatus.OK);

            } else {

                return new ResponseEntity<>("Invalid zipcode!", HttpStatus.BAD_REQUEST);
            }
        }
        else if (addressService.getAddress(addressId)) {

            Date date = new Date();

            Integer userId = userAuthTokenService.getUserId(accessToken);

            //Get last saved orderId
            Integer orderId = orderService.findLatestOrderId();

            if (paymentId == null) {
                paymentId = 1; //default value, its required because violates foreign key constraint "orders_payment_id_fkey"
            }

            if (couponId == null) {
                couponId = 1; //default value, its required because violates foreign key constraint "orders_coupon_id_fkey"
            }
            orderService.addOrderWithPermAddress(orderId+1,bill, couponId, discount, date, paymentId,userId,addressId);

            Integer orderItemId = orderService.findLatestOrderItemId();

            for (ItemQuantity iq : itemQuantity ) {
                orderItemId = orderItemId + 1;
                Item item = itemService.getItemById(iq.getItemId());
                //price calculation based on quantity
                Integer price = (item.getPrice()* iq.getQuantity());
                //save orderItem
                orderService.addOrderItem(orderItemId,orderId,iq.getItemId(),iq.getQuantity(),price);
            }

            //To return saved orderId
            orderId = orderService.findLatestOrderId();

            return new ResponseEntity<>(orderId, HttpStatus.OK);
        }
        else {
                return new ResponseEntity<>("Order not saved", HttpStatus.BAD_REQUEST);
            }
        }
}
