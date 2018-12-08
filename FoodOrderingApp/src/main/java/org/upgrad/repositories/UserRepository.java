package org.upgrad.repositories;

import org.upgrad.models.States;
import org.upgrad.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.UserAddress;

// This repository interface is responsible for the interaction between the user service with the user database
// Controller  ------> Services Layer ---> Data Access Layer (Repository) ----> Database
//                                                  UserRepository
// This UserRepository interface: User Service ---------------------> Users Table

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // Read Password using Contact Number.
    @Query(nativeQuery = true,value="SELECT PASSWORD FROM USERS WHERE contact_number=?1")
    String findUserPassword(String contactNumber);

    // Read Password using Contact Number.
    @Query(nativeQuery = true,value="SELECT * FROM USERS WHERE id=?1")
    User findUserPasswordId(Integer id);

    // Read User details using Contact Number.
    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE contact_number=?1")
    User findUser(String contactNumber);

    // Read user details using UserId.
    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE id=?1")
    User findUserId(Integer id);

    // Add user details to Users table.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO USERS (firstname,lastname,contact_number,email,password) VALUES (?1,?2,?3,?4,?5)")
    void addUserCredentials(String fname, String lname, String contactnumber,String email, String password );

    // Update details int User Table for given user.
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value="UPDATE USERS SET firstname =?1 , lastname=?2  WHERE id=?3")
    Integer updateDetails( String firstname, String lastname, Integer id);

    // Update firstname in Users table for given user.
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value="UPDATE USERS SET firstname =?1  WHERE id=?2")
    Integer updateFirstName( String firstname, Integer id);

    // Update password in Users table for the given user.
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value="UPDATE USERS SET password =?1  WHERE id=?2")
    Integer updatePassword( String password, Integer id);

}



