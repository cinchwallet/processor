package com.cinchwallet.issuer.msg;

public class IssuerResponse {

    private String responseCode;
    private Double balance;
    private String cardHolderName;

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

    public static IssuerResponse getInstance(String jsonStr) {
	//Response c = (Response) JSONObject.toBean(JSONObject.fromObject(jsonStr), Response.class);
	//custom parsing, will be removed later. Field starting with UpperCase cant be de-serialised.
	IssuerResponse response = new IssuerResponse();
	if(jsonStr == null)
	    return null;
	jsonStr = jsonStr.substring(1, jsonStr.length()-1);
	String[] fields = jsonStr.split(",");
	for (int i = 0; i < fields.length; i++) {

	    String key;
            String value = null;
            try {
	        key = fields[i].substring(0, fields[i].indexOf(":"));
	        key = key.substring(1,key.length()-1 );
	        value = fields[i].substring(fields[i].indexOf(":")+1);
            } catch (RuntimeException e) {
        	continue;
            }
	    if(value.startsWith("\""))
		value = value.substring(1,value.length()-1 );
	    if("responseCode".equals(key)){
		response.setResponseCode(value);
	    }else if("balance".equals(key)){
		response.setBalance((Double.parseDouble(value)));
	    }else if("cardHolderName".equals(key)){
		response.setCardHolderName(value);
	    }
        }
	return response;
    }
}
