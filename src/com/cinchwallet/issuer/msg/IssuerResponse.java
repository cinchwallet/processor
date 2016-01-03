package com.cinchwallet.issuer.msg;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssuerResponse {

	private String responseCode;
	private String responseMsg;
	private Double balance;
	private String cardHolderName;
	private Integer pointsAvailable;
	private String pointsExpireOn;
	private Card card;
	private CardHolder cardHolder;
	
	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Integer getPointsAvailable() {
		return pointsAvailable;
	}

	public void setPointsAvailable(Integer pointsAvailable) {
		this.pointsAvailable = pointsAvailable;
	}

	public String getPointsExpireOn() {
		return pointsExpireOn;
	}

	public void setPointsExpireOn(String pointsExpireOn) {
		this.pointsExpireOn = pointsExpireOn;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public CardHolder getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(CardHolder cardHolder) {
		this.cardHolder = cardHolder;
	}

	public static IssuerResponse getInstance(String jsonStr) {
		
		IssuerResponse response = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(jsonStr, IssuerResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;
		/*
		// Response c = (Response)
		// JSONObject.toBean(JSONObject.fromObject(jsonStr), Response.class);
		// custom parsing, will be removed later. Field starting with UpperCase
		// cant be de-serialised.
		IssuerResponse response = new IssuerResponse();
		if (jsonStr == null)
			return null;
		jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
		String[] fields = jsonStr.split(",");
		for (int i = 0; i < fields.length; i++) {

			String key;
			String value = null;
			try {
				key = fields[i].substring(0, fields[i].indexOf(":"));
				key = key.substring(1, key.length() - 1);
				value = fields[i].substring(fields[i].indexOf(":") + 1);
			} catch (RuntimeException e) {
				continue;
			}
			if (value.startsWith("\""))
				value = value.substring(1, value.length() - 1);
			if ("responseCode".equals(key)) {
				response.setResponseCode(value);
			} else if ("balance".equals(key)) {
				response.setBalance((Double.parseDouble(value)));
			} else if ("cardHolderName".equals(key)) {
				response.setCardHolderName(value);
			} else if ("pointsAvailable".equals(key)) {
				response.setPointsAvailable(value==null?null:Integer.parseInt(value));
			} else if ("pointsExpireOn".equals(key)) {
				response.setPointsExpireOn(value);
			}
		}
		return response;
	*/}
}
