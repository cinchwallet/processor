package com.cinchwallet.core.msg;

public class IMFAdditionalAmount {

    private IMFConstants.AMOUNT_TYPE amountType;
    private String                   accountType;
    private String                   currency;
    private Integer                  currencyMinorUnit;
    private IMFConstants.AMOUNT_SIGN amountSign;
    private Double                   amount;

    public IMFAdditionalAmount() {}

    public IMFConstants.AMOUNT_TYPE getAmountType() {
	return amountType;
    }

    public void setAmountType(IMFConstants.AMOUNT_TYPE amountType) {
	this.amountType = amountType;
    }

    public String getAccountType() {
	return accountType;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public Integer getCurrencyMinorUnit() {
	return currencyMinorUnit;
    }

    public void setCurrencyMinorUnit(Integer currencyMinorUnit) {
	this.currencyMinorUnit = currencyMinorUnit;
    }

    public IMFConstants.AMOUNT_SIGN getAmountSign() {
	return amountSign;
    }

    public void setAmountSign(IMFConstants.AMOUNT_SIGN amountSign) {
	this.amountSign = amountSign;
    }

    public Double getAmount() {
	return amount;
    }

    public void setAmount(Double amount) {
	this.amount = amount;
    }

    public String toDBString() {
	StringBuffer dbString = new StringBuffer();
	dbString.append("amountType:");
	if (this.amountType != null)
	    dbString.append(this.amountType);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("accountType:");
	if (this.accountType != null)
	    dbString.append(String.valueOf(this.accountType));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("currency:");
	if (this.currency != null)
	    dbString.append(String.valueOf(this.currency));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("currencyMinorUnit:");
	if (this.currencyMinorUnit != null)
	    dbString.append(String.valueOf(this.currencyMinorUnit));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("amountSign:");
	if (this.amountSign != null)
	    dbString.append(String.valueOf(this.amountSign));
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("amount:");
	if (this.amount != null)
	    dbString.append(String.valueOf(this.amount));
	else
	    dbString.append(IMFConstants.BLANK);

	return dbString.toString();
    }

    @Override
    public String toString() {
	return "[" + toDBString() + "]";
    }

}
