package com.cinchwallet.core.msg;

import java.math.BigInteger;

import org.jpos.iso.ISOAmount;
import org.jpos.iso.ISOComponent;

import com.cinchwallet.core.utils.CWLogger;

public class IMFAmount {
    private String currency;
    private BigInteger amount;
    private String amountString;
    private String amountScale;

    public IMFAmount() {

    }

    public IMFAmount(ISOComponent txnAmount) {
	if (txnAmount instanceof ISOAmount) {
	    try {
		if (txnAmount.getValue() != null) {
		    amountString = txnAmount.getValue().toString().substring(4);
		}
	    } catch (Exception e) {
		CWLogger.appLog.error("Exception ::", e);
	    }
	    ISOAmount amount = (ISOAmount) txnAmount;
	    //this.amount = amount.getAmount().doubleValue();
	    this.amount = amount.getAmount().unscaledValue();
	    this.currency = getCurrencyForCode(amount.getCurrencyCode());
	    this.amountScale = amount.getScaleAsString();
	}
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public BigInteger getAmount() {
	return amount;
    }

    public void setAmount(BigInteger amount) {
	this.amount = amount;
    }

    public String toDBString() {
	StringBuffer dbString = new StringBuffer();
	dbString.append("currency:");
	if (this.currency != null)
	    dbString.append(this.currency);
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

    public String getAmountString() {
	return amountString;
    }

    public void setAmountString(String p_amountString) {
	amountString = p_amountString;
    }

    private String getCurrencyForCode(int currencyCode) {
	return "" + currencyCode;
    }

	public String getAmountScale() {
		return amountScale;
	}

	public void setAmountScale(String amountScale) {
		this.amountScale = amountScale;
	}


}
