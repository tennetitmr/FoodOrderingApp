package org.upgrad.repositories;

import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Controller  ------> Services Layer ---> Data Access Layer (Repository)  --> Database
//                                                  AddressRepository
// This AddressRepository interface: User Service ---------------------> Address table & User_Address table

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    //Add Address
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO Address (flat_buil_number,locality,city,zipcode,state_id) VALUES (?1,?2,?3,?4,?5)")
    Integer addAddress(String flat_buil_number, String locality, String city,String zipcode, Integer state_id );

    // Add User Details
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO User_Address (type,user_id,address_id) VALUES (?1,?2,?3)")
    Integer addUserAddress(String type, Integer user_id , Integer address_id);

    //  Read max(id) of the address.
    @Query(nativeQuery = true,value = "SELECT max(id) FROM ADDRESS ")
    Integer countAddress();

    //  Read State Name for the state_id.
    @Query(nativeQuery = true,value = "SELECT *  FROM ADDRESS where id = ?1 ")
    Address findAddressById(Integer id);

    //  Update details of the user in Address Table
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE ADDRESS SET flat_buil_number =?1 , locality=?2  , city=?3 , zipcode=?4, state_id=?5 WHERE id=?6")
    Integer updateAddressById( String flat_buil_number, String locality, String city , String zipcode, Integer state_id, Integer id);

    //  Delete the  details for particular user from Address Table.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM ADDRESS WHERE id =?1")
    Integer deleteAddressById( Integer id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM User_Address WHERE address_id =?1")
    Integer deleteUserAddressById( Integer id);

    // Read Permanent Address Details
    @Query(nativeQuery = true,value = "SELECT address_id  FROM USER_ADDRESS where type = 'prem' and user_id = ?1 ")
    Iterable<Integer> getPermAdd(Integer id);

}
