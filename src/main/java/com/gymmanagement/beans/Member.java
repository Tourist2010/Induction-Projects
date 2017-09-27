package com.gymmanagement.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

/**
 * Represents a member joined in the Gym.
 * A member can have the following attributes.
 *
 * @author Ajay.Srivas
 * @author Keerthana.Pai
 * @author Ashwini.D
 * @since 19/09/2017
 */

@Document
public class Member {

    /**
     * Primary Key.
     * Every member will have a unique ID after enrolling in the Gym.
     * Id cannot be null and should be an integer type with max 5 digits.
      */
    @Id
    @NotNull
    @Digits(integer = 5, fraction = 0)
    private int id;

    /**
     * First Name of the Member enrolled.
     * First Name should not be null and should be of type String only.
     */
    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    private String firstName;

    /**
     * Last Name of the Member enrolled.
     * Last Name should not be null and should be of type String only.
     */
    @Pattern(regexp = "[A-Za-z]*")
    private String lastName;

    /**
     * Address of the Member enrolled.
     * Address should not be null.
     */
    @NotNull
    private String address;

    /**
     * Age of the Member enrolled.
     * Age cannot be null and should be of type Integer with max 2 digits.
     * Min age allowed is 10 years.
     * Max age allowed is 70 years.
     */
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Min(10)
    @Max(70)
    private int age;

    /**
     * Contact No of the Member enrolled.
     * Contact No cannot be null and should be of 10 digits.
     */
    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "[0-9]+")
    private String contactNo;

    /**
     * Enrolls a Member with the following parameters into the Gym.
     * @param id id of the Member
     * @param firstName First Name of the Member
     * @param lastName Last Name of the Member
     * @param address Address of the Member
     * @param age Age of the Member
     * @param contactNo Contact No of the Member
     */
    public Member(int id, String firstName, String lastName, String address, int age, String contactNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.contactNo = contactNo;
    }

    /**
     * Default Constructor for the creation of the Member.
     */
    public Member(){

    }
    /**
     * Gets the Id of the Member.
     * @return this Member's Id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Id of the Member.
     * @param id this Member's new Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the First Name of the Member.
     * @return this Member's First Name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the Fist Name of the Member.
     * @param firstName this Member's new First Name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the Last Name of the Member.
     * @return this Member's Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the Last Name of the Member.
     * @param lastName this Member's new Last Name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the Address of the Member.
     * @return this Member's Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the Address of the Member.
     * @param address this Member's new Address
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * Gets the Age of the Member.
     * @return this Member's Age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the Age of the Member.
     * @param age this Member's new Age
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     * Gets the Contact Number of the Member.
     * @return this Member's Contact Number
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Sets the Contact Number of the Member.
     * @param contactNo this Member's new Contact Number
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
