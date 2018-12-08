package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.repositories.StateRepository;
import org.upgrad.repositories.AddressRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/*  Controller ---> Service --> Service Implementation
    AddressServiceImplementation contains Address Service related methods.
 */

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private final StateRepository stateRepository ;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository, StateRepository stateRepository) {
        this.addressRepository = addressRepository;
        this.stateRepository = stateRepository;
    }

    //**To add address to the address table**
    @Override
    public Integer addAddress(Address address) { return addressRepository.addAddress(address.getFlatBuilNo(), address.getLocality(), address.getCity() , address.getZipcode() , address.getState().getId()); }

    //**To get details of state**
    @Override
    public States isValidState(Integer id) { return stateRepository.isValidState(id); }

    //**To return maximum addressId present in the table**
    @Override
    public Integer countAddress(){
        return addressRepository.countAddress() ;
    }

    //**To adds address to  User_Address table**
    @Override
    public Integer addUserAddress(String temp, Integer user_id, Integer address_id) { return addressRepository.addUserAddress(temp, user_id, address_id); }

    //**To get details of states**
    @Override
    public Iterable<States> getAllStates() {
        return stateRepository.getAllStates();
    }

    //**To get address using addressId**
    @Override
    public Address getaddressById( Integer addressId) { return addressRepository.findAddressById(addressId) ;}

    //**To update the address using addressId**
    @Override
    public Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id)
    {
        return addressRepository.updateAddressById(flat_build_num,locality,city,zipcode,state_id,id);
    }

    //**To deletes address using the given addressId**
    @Override
    public Integer deleteAddressById (Integer id )
    {
        return addressRepository.deleteAddressById(id);
    }

    //**To deletes user_address using given addressId**
    @Override
    public Integer deleteUserAddressById(Integer id) { return addressRepository.deleteUserAddressById(id); }

    //**To check if address is present or not**
    @Override
    public Boolean getAddress(Integer addressId){
        if (addressRepository.findAddressById(addressId) == null )
            return false;
        else
            return true ;
    }

    //**To get Permanent Address**
    @Override
    public  Iterable<Address>  getPermAddress(Integer userId)
    {
        List<Address> userList = new ArrayList<>();

        Iterable<Integer> premAddressIdList = addressRepository.getPermAdd(userId);

        if( premAddressIdList.iterator().hasNext() )
        {
            for (Integer addressId: premAddressIdList ) {
                Address  add = addressRepository.findAddressById(addressId) ;
                States state = stateRepository.getStatebyId(add.getState().getId());

                Address resp = new Address(add.getId(), add.getFlatBuilNo(), add.getLocality(), add.getCity(), add.getZipcode(), state);
                userList.add(resp);
            }
        }
        return userList;
    }
}
