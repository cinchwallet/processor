package com.cinchwallet.core;


public class CardProduct {

    private String earnPointRule;
    private String burnPointRule;
    private String loyaltyType;
    private Integer pointsPerTxn;
	public String getEarnPointRule() {
		return earnPointRule;
	}
	public void setEarnPointRule(String earnPointRule) {
		this.earnPointRule = earnPointRule;
	}
	public String getBurnPointRule() {
		return burnPointRule;
	}
	public void setBurnPointRule(String burnPointRule) {
		this.burnPointRule = burnPointRule;
	}
	public String getLoyaltyType() {
		return loyaltyType;
	}
	public void setLoyaltyType(String loyaltyType) {
		this.loyaltyType = loyaltyType;
	}
	public Integer getPointsPerTxn() {
		return pointsPerTxn;
	}
	public void setPointsPerTxn(Integer pointsPerTxn) {
		this.pointsPerTxn = pointsPerTxn;
	}

    
}
