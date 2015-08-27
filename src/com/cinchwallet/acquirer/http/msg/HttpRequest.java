package com.cinchwallet.acquirer.http.msg;

import java.util.Date;

public class HttpRequest {
    private String merchantID;
    private String terminalID;
    private String merchantTxnID;
    private String cardNumber;
    private Date   regDate;
    private String expiryDate;
    private Date   transDate;
    private String track2Data;
    private String merchantCatCode;
    private String txnType;
    private Double txnAmount;

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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
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

    @Override
    public String toString() {
	StringBuffer valueBuff = new StringBuffer();
	valueBuff.append(txnType==null?"":"txn type=" + txnType);
	valueBuff.append(merchantID==null?"":", merchantID=" + merchantID);
	valueBuff.append(terminalID==null?"":", terminalID=" + terminalID);
	valueBuff.append(merchantTxnID==null?"":", merchantTxnID=" + merchantTxnID);
	valueBuff.append(transDate ==null?"":", transactionDate=" + transDate);
	valueBuff.append(regDate ==null?"":", registrationDate=" + regDate);
	valueBuff.append(expiryDate==null?"":", expiryDate=" + expiryDate);
	valueBuff.append(track2Data==null?"":", track2Data=" + track2Data);
	valueBuff.append(merchantCatCode==null?"":", merchantCategoryCode=" + merchantCatCode);
	valueBuff.append(txnAmount==null?"":", txnAmount=" + txnAmount);
	return valueBuff.toString();
    }

}
