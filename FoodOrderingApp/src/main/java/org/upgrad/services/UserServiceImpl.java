package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.repositories.UserAuthTokenRepository;
import org.upgrad.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


/*
    Service Implementation Layer: It contains all user related methods.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // To find the password of user.
    @Override
    public String findUserPassword(String contactNumber) {
        return userRepository.findUserPassword(contactNumber);
    }

    // To find the details of user
    @Override
    public User findUser(String contactNumber) { return userRepository.findUser(contactNumber); }

    // To find the details of user corresponding to userId
    @Override
    public User findUserId(Integer id) { return userRepository.findUserId(id); }

    // To get User details
    @Override
    public User getUserById(Integer id){ return userRepository.findUserPasswordId(id); }

    // To update user Passwords
    @Override
    public Integer updateUserPassword(String password, Integer id) { return userRepository.updatePassword(password,id);} ;

    // To add new User details
    @Override
    public void addUserDetails(User newuser) {
        userRepository.addUserCredentials(newuser.getFirstName(), newuser.getLastName(), newuser.getEmail(),  newuser.getContactNumber(), newuser.getPassword());
    }

    // To update user details
    @Override
    public User updateUser(String firstname, String lastname, Integer id)
    {
        if(null == lastname)
            userRepository.updateFirstName(firstname,id);
        else
            userRepository.updateDetails(firstname,lastname,id);


        User user  =  userRepository.findUserId(id);
        return user;
    }

}
