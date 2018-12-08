package org.upgrad.services;

import org.upgrad.models.Address;
import org.upgrad.models.States;

/*               interface
/*  Controller ------------------> Service Methods
 *  AddressService Interface : List of all the service methods that exist in the Address service implementation class.
 */

public interface AddressService {

    //**To check whether the stateid/state is valid**
    States isValidState(Integer id);

    //**To adds new address**
    Integer addAddress(Address address);

    //**To count the address i.e. by using highest address Id, present in table**
    Integer countAddress();

    //**To add user Address**
    Integer addUserAddress(String temp, Integer user_id, Integer address_id) ;

    //**To read all states**
    Iterable<States> getAllStates() ;

    //**To read address details corresponding to addressId**
    Address getaddressById( Integer addressId);

    //**To check whether the address is present**
    Boolean getAddress( Integer addressId);

    //**To Update address**
    Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id);

    //**To Delete Address already present**
    Integer deleteAddressById (Integer id );

    //**ToDelete Address already present**
    Integer deleteUserAddressById(Integer id);

    //**To get permanent address details of the user**
    Iterable<Address>  getPermAddress(Integer userId) ;

}
