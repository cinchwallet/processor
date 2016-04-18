package com.cinchwallet.core;

import java.sql.Date;

public class PromoOffer {

    private String promoCode;
    private String promoRule;
    private String cardProduct;
    private String membershipId;
    private boolean isValid;
    private Date redeemedOn;
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getPromoRule() {
		return promoRule;
	}
	public void setPromoRule(String promoRule) {
		this.promoRule = promoRule;
	}
	public String getCardProduct() {
		return cardProduct;
	}
	public void setCardProduct(String cardProduct) {
		this.cardProduct = cardProduct;
	}
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Date getRedeemedOn() {
		return redeemedOn;
	}
	public void setRedeemedOn(Date redeemedOn) {
		this.redeemedOn = redeemedOn;
	}
	@Override
	public String toString() {
		return "PromoOffer [promoCode=" + promoCode + ", promoRule=" + promoRule + ", cardProduct=" + cardProduct
				+ ", membershipId=" + membershipId + ", isValid=" + isValid + ", redeemedOn=" + redeemedOn + "]";
	}
}
