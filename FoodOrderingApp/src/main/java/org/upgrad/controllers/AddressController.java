package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.AddressService;
import org.upgrad.services.UserAuthTokenService;

@RestController
@RequestMapping("")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;


    /**
     * POST API --> saves the address in database if address is valid or not; 
     * @param flatBuilNo -->  flat/building number
     * @param locality   -->  locality
     * @param city       -->  city name
     * @param zipcode    -->  valid 6 digit code
     * @param stateId    -->  state where the address belongs to
     * @Header accessToken to identify if the user is logged in or not
     * @return whether address is saved in DB or not.
     */
    @PostMapping("/address")
    @CrossOrigin
    public ResponseEntity<?> address(@RequestParam String flatBuilNo, @RequestParam String locality, @RequestParam String city, @RequestParam Integer stateId, @RequestParam String zipcode, @RequestParam(required = false) String type , @RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        //**To check if user is logged in.**
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } //**To check if user is not logged out.**
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);

            //**To Validate zipcode check**
            if (6 == zipcode.length() && zipcode.matches("[0-9]+")) {
                States state =   addressService.isValidState(stateId) ;

                //**To generate address Id**
                int addressId  = addressService.countAddress() + 1 ;
                String type1 = "temp" ;

                //**To add data in address table**
                Address address = new Address(addressId ,flatBuilNo, locality, city, zipcode, state);
                addressService.addAddress(address);

                //**To set value of type parameter**
                if ( null != type )
                    type1 = type ;

                //**To add data in UserAddress table**
                addressService.addUserAddress(type1, userId, addressId) ;

                message ="Address has been saved successfully!" ;
                httpStatus = HttpStatus.CREATED ;
            } else {
                message = "Invalid zipcode!" ;
                httpStatus = HttpStatus.BAD_REQUEST ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }

    /**
     * PUT API --> updates address in database if address already exists
     * @pathVariable addressId --> Id of address that is already present in table
     * @param flatBuilNo       --> flat/building number
     * @param locality         --> locality
     * @param city             --> city name
     * @param zipcode          --> valid 6 digit code
     * @param stateId          --> state where the address belongs to
     * @Header accessToken to identify if the user is logged in or not
     * @return whether address is updated in DB or not
     */
    @PutMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> updateAddressById(@PathVariable Integer addressId , @RequestParam(required = false) String flatBuilNo , @RequestParam(required = false) String locality , @RequestParam(required = false) String city , @RequestParam(required = false) String zipcode , @RequestParam(required = false) Integer stateId , @RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        //** To checking if user is logged in**
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } //** To check if user is not logged out**
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);

            //**To do  Zipcode Validation check**
            if (zipcode != null ){
                if (! ( zipcode.length() == 6 && zipcode.matches("[0-9]+") )) {
                    return new ResponseEntity<>("Invalid zipcode!" , HttpStatus.BAD_REQUEST);
                }
            }

            //**To check if address exists for supplied addressId**
            Boolean  astatus = addressService.getAddress(addressId);
            Address add = addressService.getaddressById(addressId);

            if (false == astatus) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                if (null == flatBuilNo)
                    flatBuilNo = add.getFlatBuilNo();

                if (null == locality)
                    locality = add.getLocality();

                if (null == city)
                    city = add.getCity();

                if (null == zipcode)
                    zipcode = add.getZipcode();

                if (null == stateId)
                    stateId = add.getState().getId() ;

                //**To Update Address**
                addressService.updateAddressById(flatBuilNo, locality, city, zipcode, stateId, addressId);

                message = "Address has been updated successfully!";
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }

    /**
     * DELETE API --> deletes address in database if address already exists.
     * @pathVariable addressId --> Id of address that is already present in DB.
     * @Header accessToken     --> identify if the user is logged in or not.
     * @return whether address is deleted in DB or not.
     */
    @DeleteMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> deleteAddressById(@PathVariable Integer addressId , @RequestHeader String accessToken) {

        String message = "";
        HttpStatus httpStatus = HttpStatus.OK;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        //**To Check if user is logged in**
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } //**To Check if user is not logged out**
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            //**To Check if address exists for supplied addressId**
            Boolean add = addressService.getAddress(addressId);

            if (false == add ) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {

                //**To delete address from both tables**
                addressService.deleteAddressById(addressId) ;
                addressService.deleteUserAddressById(addressId);

                message = "Address has been deleted successfully!" ;
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }

    /**
     * Get API--> returns permanent address for the logged in user
     * @Header accessToken to identify if the user is logged in or not.
     * @return returns permanent address for the logged in user
     */
    @GetMapping("/address/user")
    @CrossOrigin
    public ResponseEntity<?> getAllPermanentAddress(@RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        //**To check if user is logged in**
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } //**To check if user is not logged out**
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);

            if ( addressService.getPermAddress(userId)  == null )
            {
                message = "No permanent address found!" ;
                httpStatus = HttpStatus.BAD_REQUEST ;
            }
            else
            {
                if (null == addressService.getPermAddress(userId)) {
                    message = "No permanent address found!";
                    httpStatus = HttpStatus.BAD_REQUEST ;
                }
                else {
                    //**To return corresponding details in required JSON format**
                    return new ResponseEntity<>(addressService.getPermAddress(userId), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }


    /**
     * Get API --> returns all states that are present in DB.
     * @return returns all states.
     */
    @GetMapping("/states")
    public ResponseEntity<?> getAllPermanentAddress() {
        return new ResponseEntity<>( addressService.getAllStates() , HttpStatus.OK);
    }

}

