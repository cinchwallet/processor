package com.cinchwallet.core;


public class Cardholder {

    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String membershipId;
    
    public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
	StringBuffer valueBuff = new StringBuffer();
	valueBuff.append(firstName==null?"":", firstName=" + firstName);
	valueBuff.append(lastName==null?"":", lastName=" + lastName);
	valueBuff.append(email==null?"":", email=" + email);
	valueBuff.append(gender==null?"":", gender=" + gender);
	valueBuff.append(address==null?"":", address=" + address);
	valueBuff.append(phoneNumber==null?"":", phoneNumber=" + phoneNumber);
	valueBuff.append(city==null?"":", city=" + city);
	valueBuff.append(state==null?"":", state=" + state);
	valueBuff.append(country==null?"":", country=" + country);
	valueBuff.append(zip==null?"":", zip=" + zip);
	valueBuff.append(dateOfBirth==null?"":", dateOfBirth=" + dateOfBirth);
	return valueBuff.toString();
    }
}
