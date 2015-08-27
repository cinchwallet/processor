package com.cinchwallet.acquirer.http.msg;

public class TxnHistory {

    private String date;
    private String merchantName;
    private String amount;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
	StringBuffer valueBuff = new StringBuffer();
	valueBuff.append(date==null?"":", date=" + date);
	valueBuff.append(merchantName==null?"":", merchantName=" + merchantName);
	valueBuff.append(amount==null?"":", amount=" + amount);
	return valueBuff.toString();
    }

}
