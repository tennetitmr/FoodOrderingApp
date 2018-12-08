package org.upgrad.services;

import org.upgrad.models.User;

import java.util.Optional;

/*               interface
/*  Controller ------------------> Service Methods
 *  UserService Interface : List of all the service methods that exist in the user service implementation class.
 */
public interface UserService {

    // input:contactNumber    output: Password
    String findUserPassword(String contactNumber);

    // input: id  output: User details
    User getUserById(Integer id);

    // input: contactNumber output: User Details
    User findUser(String contactNumber);

    // input: id  output: User Details
    User findUserId(Integer id);

    // To save userDetails to users table.
    void addUserDetails(User newuser);

    // To updateUserDetails
    User updateUser(String firstname, String lastname, Integer id);

    // To UpdatePassword
    Integer updateUserPassword(String password, Integer id);
}
