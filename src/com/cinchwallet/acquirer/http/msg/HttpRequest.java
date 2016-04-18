package com.cinchwallet.acquirer.http.msg;

import java.util.Date;


public class HttpRequest {
	//common fields
    private String merchantID;
    private String terminalID;
    private String merchantTxnID;
    private String cardNumber;
    private Date   txnDate;
    private String merchantCatCode;
    private String txnType;
    private Double txnAmount;

    private String expiryDate;
    private String track2Data;
    
    //add point or burn point
    private Integer txnPoint;
    // cardprofile registration specific fields
    private Profile profile;
    //txn history specific field
    private Integer noOfTrans;
    //reissue card
    private String newCardNumber;
    //loyalty by phone number
    private String phoneNumber;
    
    //transaction done using promo code
    private String promoCode;
    
    private String userName;
    private String password;

    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getMerchantTxnID() {
        return merchantTxnID;
    }

    public void setMerchantTxnID(String merchantTxnID) {
        this.merchantTxnID = merchantTxnID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public String getTrack2Data() {
        return track2Data;
    }

    public void setTrack2Data(String track2Data) {
        this.track2Data = track2Data;
    }

    public String getMerchantCatCode() {
        return merchantCatCode;
    }

    public void setMerchantCatCode(String merchantCatCode) {
        this.merchantCatCode = merchantCatCode;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public Double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Integer getTxnPoint() {
		return txnPoint;
	}

	public void setTxnPoint(Integer txnPoint) {
		this.txnPoint = txnPoint;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Integer getNoOfTrans() {
		return noOfTrans;
	}

	public void setNoOfTrans(Integer noOfTrans) {
		this.noOfTrans = noOfTrans;
	}
	

	public String getNewCardNumber() {
		return newCardNumber;
	}

	public void setNewCardNumber(String newCardNumber) {
		this.newCardNumber = newCardNumber;
	}
	
	

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	@Override
    public String toString() {
	StringBuffer valueBuff = new StringBuffer();
	valueBuff.append(txnType==null?"":"txn type=" + txnType);
	valueBuff.append(merchantID==null?"":", merchantID=" + merchantID);
	valueBuff.append(terminalID==null?"":", terminalID=" + terminalID);
	valueBuff.append(merchantTxnID==null?"":", merchantTxnID=" + merchantTxnID);
	valueBuff.append(txnDate ==null?"":", transactionDate=" + txnDate);
	valueBuff.append(expiryDate==null?"":", expiryDate=" + expiryDate);
	valueBuff.append(track2Data==null?"":", track2Data=" + track2Data);
	valueBuff.append(merchantCatCode==null?"":", merchantCategoryCode=" + merchantCatCode);
	valueBuff.append(txnAmount==null?"":", txnAmount=" + txnAmount);
	valueBuff.append(txnPoint==null?"":", txnPoint=" + txnPoint);
	valueBuff.append(profile==null?"":", profile=" + profile);
	valueBuff.append(noOfTrans==null?"":", noOfTrans=" + noOfTrans);
	valueBuff.append(newCardNumber==null?"":", newCardNumber=" + newCardNumber);
	valueBuff.append(promoCode==null?"":", promoCode=" + promoCode);
	return valueBuff.toString();
    }

}
