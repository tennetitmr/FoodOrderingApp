package org.upgrad.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.UserAuthTokenService;
import org.upgrad.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;


    /*** POST API --> signs up the new user
     * @param firstName      --> firstname of the user
     * @param lastName       --> lastname of the user
     * @param email          -->email of the user
     * @param contactNumber  --> contact number of user
     * @param password       --> password of user
     */
    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<?> signup(@RequestParam String firstName, @RequestParam(required = false) String lastName, @RequestParam String email, @RequestParam String contactNumber, @RequestParam String password) {

        //**To call to Service to find user with corresponding contactNumber exists**
        User user = userService.findUser(contactNumber);

        //**To check If the given user already exists, throw message**
        if (null != user) {
            return new ResponseEntity<>("Try any other contact number, the contact number has already been registered!", HttpStatus.BAD_REQUEST);
        } else {
            //**To check for valid email id -> x@x.co using regex patten**
            String EMAIL_PATTERN =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if (!email.matches(EMAIL_PATTERN))
                return new ResponseEntity<>("Invalid email-id format!", HttpStatus.BAD_REQUEST);

            //**To check whether the contactNumber is valid using appropriate regex**
            if (!(contactNumber.length() == 10 && contactNumber.matches("[0-9]+"))) {
                return new ResponseEntity<>("Invalid contact number!", HttpStatus.BAD_REQUEST);
            }

            //**To check the strength of password**
            String passpattern = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";

            if (!password.matches(passpattern))
                return new ResponseEntity<>("Weak password!", HttpStatus.BAD_REQUEST);
        }

        String passwordsha = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();

        User newuser = new User(firstName, lastName, contactNumber, email, passwordsha);

        userService.addUserDetails(newuser);
        String message = "User with contact number " + contactNumber + " successfully registered!";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * POST API --> login a user if it is valid user
     * @param contactNumber --> contact number of user
     * @param password      --> password of user
     * @return accessToken (unique identifier) which identifies whether user is logged in or not.
     */
    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String contactNumber, @RequestParam String password) {
        String passwordByUser = String.valueOf(userService.findUserPassword(contactNumber));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        // to check if the user already exists
        if (userService.findUserPassword(contactNumber) == null)
            return new ResponseEntity<>("This contact number has not been registered!", HttpStatus.OK);
        else if (!(passwordByUser.equalsIgnoreCase(sha256hex))) {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        } else {
            // to check if user is valid and return its access token.
            User user = userService.findUser(contactNumber);
            String accessToken = UUID.randomUUID().toString();
            userAuthTokenService.addAccessToken(user.getId(), accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", accessToken);
            List<String> header = new ArrayList<>();
            header.add("access-token");
            headers.setAccessControlExposeHeaders(header);
            return new ResponseEntity<>(user, headers, HttpStatus.OK);
        }
    }


    /**PUT API that logout a user if already login */
    @PutMapping("/logout")
    @CrossOrigin
    public ResponseEntity<String> logout(@RequestHeader String accessToken) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            //**To remove AccessToken from Database**
            userAuthTokenService.removeAccessToken(accessToken);
            return new ResponseEntity<>("You have logged out successfully!", HttpStatus.OK);
        }
    }


    /**
     * PUT API --> updates details of already existing user
     * @param firstName --> firstname of the user
     * @param lastName --> lastname of the user
     * @Header accessToken that identifies if user is logged in or not
     * @return whether user details are updated.
     */
    @PutMapping("")
    @CrossOrigin
    public ResponseEntity<?> userUpdate(@RequestParam String firstName, @RequestParam(required = false) String lastName, @RequestHeader String accessToken) {

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        //**To Check if user is logged in**
        if (usertoken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } //**To check if user is not logged out**
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null)  {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {

            Integer userId =  userAuthTokenService.getUserId(accessToken) ;
            User user = userService.updateUser(firstName, lastName, userId) ;

            if ( user != null) ;
            {
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
        }
    }

    /**
     * PUT API --> changes password of the user
     * @param oldPassword  --> oldpassword of the user
     * @param  newPassword --> newpassword of the user
     * @Header accessToken that identifies if user is logged in or not
     * @return whether password of user is changed or not
     */
    @PutMapping("/password")
    @CrossOrigin
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String accessToken) {

        boolean flag = false;
        String passpattern = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        //**To Check whether the user is logged in**
        if (null == usertoken) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } //**To Check whether the user is not logged out**
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {

            Integer userId =  userAuthTokenService.getUserId(accessToken) ;

            //**To check whether the previous password matches with password stored in table**
            User user = userService.getUserById(userId);

            String passwordByUser = user.getPassword() ;

            String oldpasswordsha = Hashing.sha256()
                    .hashString(oldPassword, Charsets.US_ASCII)
                    .toString();
            if (!(passwordByUser.equalsIgnoreCase(oldpasswordsha))) {
                return new ResponseEntity<>("Your password did not match to your old password!", HttpStatus.UNAUTHORIZED);
            } else if (!newPassword.matches(passpattern)) {
                return new ResponseEntity<>("Weak password!", HttpStatus.BAD_REQUEST);
            } else {

                String newpasswordsha = Hashing.sha256()
                        .hashString(newPassword, Charsets.US_ASCII)
                        .toString();

                if (null != userService.updateUserPassword(newpasswordsha, userId))
                    flag = true;
            }

            return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
        }
    }
}
