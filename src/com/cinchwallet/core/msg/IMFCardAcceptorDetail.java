package com.cinchwallet.core.msg;

public class IMFCardAcceptorDetail {
    private String name;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private String phone;
    private String customerServicePhone;
    private String additionalInfo;
    private String url;
    private String email;

    public IMFCardAcceptorDetail() {

    }

    public String getName() {
	return name != null ? name.trim() : name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address != null ? address.trim() : address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city != null ? city.trim() : city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state != null ? state.trim() : state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getPostalCode() {
	return postalCode != null ? postalCode.trim() : postalCode;
    }

    public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
    }

    public String getCountryCode() {
	return countryCode != null ? countryCode.trim() : countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getPhone() {
	return phone != null ? phone.trim() : phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getCustomerServicePhone() {
	return customerServicePhone != null ? customerServicePhone.trim() : customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
	this.customerServicePhone = customerServicePhone;
    }

    public String getAdditionalInfo() {
	return additionalInfo != null ? additionalInfo.trim() : additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
	this.additionalInfo = additionalInfo;
    }

    public String getUrl() {
	return url != null ? url.trim() : url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getEmail() {
	return email != null ? email.trim() : email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String toDBString() {
	StringBuffer dbString = new StringBuffer();

	dbString.append("name:");
	if (this.name != null)
	    dbString.append(this.name);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("address:");
	if (this.address != null)
	    dbString.append(String.valueOf(this.address));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("city:");
	if (this.city != null)
	    dbString.append(String.valueOf(this.city));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("state:");
	if (this.state != null)
	    dbString.append(String.valueOf(this.state));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("postalCode:");
	if (this.postalCode != null)
	    dbString.append(String.valueOf(this.postalCode));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("countryCode:");
	if (this.countryCode != null)
	    dbString.append(String.valueOf(this.countryCode));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("phone:");
	if (this.phone != null)
	    dbString.append(String.valueOf(this.phone));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("customerServicePhone:");
	if (this.customerServicePhone != null)
	    dbString.append(String.valueOf(this.customerServicePhone));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("additionalInfo:");
	if (this.additionalInfo != null)
	    dbString.append(String.valueOf(this.additionalInfo));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("url:");
	if (this.url != null)
	    dbString.append(String.valueOf(this.url));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("email:");
	if (this.email != null)
	    dbString.append(String.valueOf(this.email));
	else
	    dbString.append(IMFConstants.BLANK);

	return dbString.toString();
    }

    @Override
    public String toString() {
	return "[" + toDBString() + "]";
    }

}
