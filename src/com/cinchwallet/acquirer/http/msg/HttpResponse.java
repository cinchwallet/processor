package com.cinchwallet.acquirer.http.msg;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class HttpResponse {
    private String merchantTxnID;
    private String respCode;
    private String reasonCode;
    private String respMsg;
    private String expirationDate;
    private String cwTransID;
    private String cardHolderName;
    private Double prepaidbalance;
    private Integer pointBalance;
    private List<TxnHistory> txns;
    public String getMerchantTxnID() {
        return merchantTxnID;
    }
    public void setMerchantTxnID(String merchantTxnID) {
        this.merchantTxnID = merchantTxnID;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getReasonCode() {
        return reasonCode;
    }
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getCwTransID() {
        return cwTransID;
    }
    public void setCwTransID(String cwTransID) {
        this.cwTransID = cwTransID;
    }
    public String getCardHolderName() {
        return cardHolderName;
    }
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    public Double getPrepaidbalance() {
        return prepaidbalance;
    }
    public void setPrepaidbalance(Double prepaidbalance) {
        this.prepaidbalance = prepaidbalance;
    }
    public Integer getPointBalance() {
        return pointBalance;
    }
    public void setPointBalance(Integer pointBalance) {
        this.pointBalance = pointBalance;
    }
    public List<TxnHistory> getTxns() {
        return txns;
    }
    public void setTxns(List<TxnHistory> txns) {
        this.txns = txns;
    }

    @Override
    public String toString() {
	StringBuffer valueBuff = new StringBuffer();
	valueBuff.append(merchantTxnID==null?"":", merchantTxnID=" + merchantTxnID);
	return valueBuff.toString();
    }

}
