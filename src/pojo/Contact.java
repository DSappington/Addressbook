package pojo;

import java.io.Serializable;

public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public Contact(String name, String street, String city, String state, String zip, String phoneNumber) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + " " + street + ", " + city + " " + state + " " + zip + " " + phoneNumber;
    }
}
