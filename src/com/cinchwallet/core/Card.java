package com.cinchwallet.core;

import java.sql.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Card {

    private String cardNumber;
    private String status;
    private Double balance;
    private Integer points;
    private Date pointExpireOn;
    private String membershipId;

    public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Date getPointExpireOn() {
		return pointExpireOn;
	}
	public void setPointExpireOn(Date pointExpireOn) {
		this.pointExpireOn = pointExpireOn;
	}
	@Override
	public String toString() {
		return "Card [cardNumber=" + cardNumber + ", status=" + status + ", balance=" + balance + ", points=" + points
				+ ", pointExpireOn=" + pointExpireOn + ", membershipId=" + membershipId + "]";
	}

}
