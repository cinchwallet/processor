package com.cinchwallet.issuer.msg;

public class IssuerRequest {

    private String cardNumber;
    private String txnDate;
    private Double txnAmount;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getTxnDate() {
        return txnDate;
    }
    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }
    public Double getTxnAmount() {
        return txnAmount;
    }
    public void setTxnAmount(Double txnAmount) {
        this.txnAmount = txnAmount;
    }

}
