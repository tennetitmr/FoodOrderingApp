package org.upgrad.models;



import com.fasterxml.jackson.annotation.JsonIgnore;



import javax.persistence.*;



/*

 * ADDRESS model class contain all the attributes to be mapped to all the fields in the ADDRESS table in the database.

 * Annotations are used to specify all the constraints to the table and table-columns in the database.

 * Here getter, setter and constructor are defined for this model class.

 */

@Entity

@Table(name = "ADDRESS")

public class Address {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;



    public String getFlatBuilNo() {

        return flatBuilNo;

    }



    public void setFlatBuilNo(String flatBuilNo) {

        this.flatBuilNo = flatBuilNo;

    }



    @Column(name = "flat_buil_number", nullable = false)

    private String flatBuilNo;



    @Column(name = "locality",nullable = false)

    private String locality;



    @Column(name = "city",nullable = false)

    private String city;



    @Column(name = "zipcode", nullable = false)

    private String zipcode;



    @OneToOne(fetch = FetchType.EAGER)

    private States state;





    // Default constructor

    public Address(){

    }



    // Parameterized Constructor

    public Address(Integer id ,String flat_buil_number, String locality, String city, String zipcode, States state) {

        this.id = id ;

        this.flatBuilNo = flat_buil_number ;

        this.city = city ;

        this.locality = locality ;

        this.zipcode = zipcode ;

        this.state = state ;

    }



    //Getter & Setters

    public Integer getId() {

        return id;

    }



    public void setId(Integer id) {

        this.id = id;

    }



    public String getLocality() {

        return locality;

    }



    public void setLocality(String locality) {

        this.locality = locality;

    }



    public String getCity() {

        return city;

    }



    public void setCity(String city) {

        this.city = city;

    }



    public String getZipcode() {

        return zipcode;

    }



    public void setZipcode(String zipcode) {

        this.zipcode = zipcode;

    }



    public States getState() {

        return state;

    }



    public void setState(States state) {

        this.state = state;

    }



}