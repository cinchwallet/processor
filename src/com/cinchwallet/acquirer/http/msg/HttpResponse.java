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
    private String pointExpireOn;
    private Integer pointBalance;
    private List<TxnHistory> txns;
    private Card card;
    private Profile profile;
    
    //for webpos login
    private String merchantId;
    private String storeId;
    private String merchantName;
    private String storeName;
    
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

    public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public String getPointExpireOn() {
		return pointExpireOn;
	}
	public void setPointExpireOn(String pointsExpireOn) {
		this.pointExpireOn = pointsExpireOn;
	}
	
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	@Override
	public String toString() {
		return "HttpResponse [merchantTxnID=" + merchantTxnID + ", respCode=" + respCode + ", reasonCode=" + reasonCode
				+ ", respMsg=" + respMsg + ", expirationDate=" + expirationDate + ", cwTransID=" + cwTransID
				+ ", cardHolderName=" + cardHolderName + ", prepaidbalance=" + prepaidbalance + ", pointExpireOn="
				+ pointExpireOn + ", pointBalance=" + pointBalance + ", txns=" + txns + ", card=" + card + ", profile="
				+ profile + ", merchantId=" + merchantId + ", storeId=" + storeId + ", merchantName=" + merchantName
				+ ", storeName=" + storeName + "]";
	}

}
