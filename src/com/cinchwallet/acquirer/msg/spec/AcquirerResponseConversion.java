package com.cinchwallet.acquirer.msg.spec;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.cinchwallet.core.constant.ISO8583_2003_SpecConstant;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.msg.IMFAdditionalAmount;
import com.cinchwallet.core.msg.IMFConstants;
import com.cinchwallet.core.msg.IMFMessageErrorIndicator;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.Utils;

public class AcquirerResponseConversion {

    private static Map<String, String>    MTI_RESPONSE                              = new HashMap<String, String>();
    private static final SimpleDateFormat TRANSMISSION_DATE_IMF_FORMAT              = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String           AMOUNT_TYPE_ACC_LEDGER_BAL                = "01";
    private static final String           AMOUNT_TYPE_ACC_AVAILABLE_BAL             = "02";
    private static final String           AMOUNT_TYPE_DEST_ACC_AVAILABLE_BAL        = "08";
    private static final String           AMOUNT_TYPE_DEST_ACC_LEDGER_BAL           = "07";
    private static final String           AMOUNT_TYPE_AMT_REMAINING_THIS_CYCLE      = "20";
    private static final String           AMOUNT_TYPE_AMT_CASH                      = "40";
    private static final String           AMOUNT_CREDIT                             = "C";
    private static final String           AMOUNT_DEBIT                              = "D";
    private static final String           MIN_CURRENCY_UNIT                         = "2";

    static {
	MTI_RESPONSE.put(ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_REQUEST_MTI + IMFConstants.COLON + ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_PC,
		ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_RESPONSE_MTI + IMFConstants.COLON
	        		+ ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_PC);

    }

    public static String getMessageErrorIndicatorForISOMsg(List<IMFMessageErrorIndicator> msgErrorIndList) {
	if (msgErrorIndList != null) {
	    StringBuffer mei = new StringBuffer();
	    for (IMFMessageErrorIndicator msgErr : msgErrorIndList) {
		mei.append(getMeiAsString(msgErr));
	    }
	    return mei.toString();
	}
	return null;
    }

    public static String getMeiAsString(IMFMessageErrorIndicator mei) {
	if (mei == null) {
	    return "";
	}
	StringBuffer meiStr = new StringBuffer();
	String data = null;
	if (mei.getErrorSeverityCode() != null) {
	    data = String.valueOf(mei.getErrorSeverityCode());
	    if (data.length() < 2) {
		try {
		    data = ISOUtil.padleft(data, 2, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_SEVERITY_ERROR_CODE;
		}
	    } else if (data.length() > 2) {
		data = data.substring(data.length() - 2);
	    }
	} else {
	    data = SwitchConstants.MEI_SEVERITY_ERROR_CODE;
	}
	meiStr.append(data);
	if (mei.getMessageErrorCode() != null) {
	    data = String.valueOf(mei.getMessageErrorCode());
	    if (data.length() < 4) {
		try {
		    data = ISOUtil.padleft(data, 4, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_UNKNOWN_ERROR;
		}
	    } else if (data.length() > 4) {
		data = data.substring(data.length() - 4);
	    }
	} else {
	    data = SwitchConstants.MEI_UNKNOWN_ERROR;
	}
	meiStr.append(data);
	if (mei.getDataElementInError() != null) {
	    data = String.valueOf(mei.getDataElementInError());
	    if (data.length() < 3) {
		try {
		    data = ISOUtil.padleft(data, 3, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_UNKNOWN_ELEMENT;
		}
	    } else if (data.length() > 3) {
		data = data.substring(data.length() - 3);
	    }
	} else {
	    data = SwitchConstants.MEI_UNKNOWN_ELEMENT;
	}
	meiStr.append(data);
	if (mei.getDataSubElementInError() != null) {
	    data = String.valueOf(mei.getDataSubElementInError());
	    if (data.length() < 2) {
		try {
		    data = ISOUtil.padleft(data, 2, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_UNKNOWN_SUB_ELEMENT;
		}
	    } else if (data.length() > 2) {
		data = data.substring(data.length() - 2);
	    }
	} else {
	    data = SwitchConstants.MEI_UNKNOWN_SUB_ELEMENT;
	}
	meiStr.append(data);
	if (mei.getDatasetIdentifierInError() != null) {
	    data = String.valueOf(mei.getDatasetIdentifierInError());
	    if (data.length() < 1) {
		try {
		    data = ISOUtil.padleft(data, 1, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_UNKNOWN_DATA_SET_IDENTIFIER;
		}
	    } else if (data.length() > 1) {
		data = data.substring(data.length() - 1);
	    }
	} else {
	    data = SwitchConstants.MEI_UNKNOWN_DATA_SET_IDENTIFIER;
	}
	meiStr.append(data);
	if (mei.getDataBitInError() != null) {
	    data = String.valueOf(mei.getDataBitInError());
	    if (data.length() < 2) {
		try {
		    data = ISOUtil.padleft(data, 2, '0');
		} catch (ISOException e) {
		    CWLogger.appLog.error(e);
		    data = SwitchConstants.MEI_UNKNOWN_DATA_BIT_IN_ERROR;
		}
	    } else if (data.length() > 2) {
		data = data.substring(data.length() - 2);
	    }
	} else {
	    data = SwitchConstants.MEI_UNKNOWN_DATA_BIT_IN_ERROR;
	}
	meiStr.append(data);
	return meiStr.toString();
    }


    private static StringBuffer checkLength(StringBuffer pBuffer) {
	int lResult = (pBuffer.toString().length()) % 8;
	if (lResult != 0) {
	    for (int i = 0; i < (8 - lResult); i++) {
		pBuffer.append(IMFConstants.SPACE);
	    }
	}
	return pBuffer;
    }



    public static int getCurrencyFromCode(String currency) {
	return Integer.parseInt(currency);
    }

    public static String getTransmissionDateTimeFromIMF(Date trmsDate) {
	if (trmsDate != null) {
	    String dt = TRANSMISSION_DATE_IMF_FORMAT.format(trmsDate);
	    return dt.substring(4);
	}
	return null;
    }

    public static String getTransactionDateTimeFromIMF(Date trmsDate) {
	if (trmsDate != null) {
	    return TRANSMISSION_DATE_IMF_FORMAT.format(trmsDate);
	}
	return null;
    }


    public static String getAdditionalAmountForISO(List<IMFAdditionalAmount> additionalAmountList) throws ISOException {
	if (additionalAmountList != null && additionalAmountList.size()>0) {
	    DecimalFormat formatter = null;
	    IMFAdditionalAmount additionalAmount = null;
	    StringBuffer additionalAmountStr = new StringBuffer();
	    for (int i = 0; i < additionalAmountList.size(); i++) {
		additionalAmount = additionalAmountList.get(i);
		String amtType = additionalAmount.getAccountType();
		if (amtType != null) {
		    amtType = Utils.fillChars(amtType, 2, '0');
		} else {
		    amtType = "00";
		}
		additionalAmountStr.append(amtType);
		IMFConstants.AMOUNT_TYPE amountType = additionalAmount.getAmountType();
		if (IMFConstants.AMOUNT_TYPE.ACCOUNT_LEDGER_BALANCE.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_ACC_LEDGER_BAL);
		} else if (IMFConstants.AMOUNT_TYPE.ACCOUNT_AVAILABLE_BALANCE.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_ACC_AVAILABLE_BAL);
		} else if (IMFConstants.AMOUNT_TYPE.DESTINATION_ACCOUNT_LEDGER_BALANCE.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_DEST_ACC_LEDGER_BAL);
		} else if (IMFConstants.AMOUNT_TYPE.DESTINATION_ACCOUNT_AVAILABLE_BALANCE.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_DEST_ACC_AVAILABLE_BAL);
		} else if (IMFConstants.AMOUNT_TYPE.AMOUNT_REMAINING_THIS_CYCLE.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_AMT_REMAINING_THIS_CYCLE);
		} else if (IMFConstants.AMOUNT_TYPE.AMOUNT_CASH.equals(amountType)) {
		    additionalAmountStr.append(AMOUNT_TYPE_AMT_CASH);
		} else {}
		String curr = additionalAmount.getCurrency();
		if (curr != null) {
		    curr = Utils.fillChars(curr, 3, ' ');
		} else {
		    curr = "   ";
		}
		additionalAmountStr.append(curr);
		String minUnit = String.valueOf(additionalAmount.getCurrencyMinorUnit());
		if (Utils.isEmpty(minUnit) || "null".equalsIgnoreCase(minUnit)) {
		    minUnit = MIN_CURRENCY_UNIT;
		}
		formatter = new DecimalFormat(currencyFormat(minUnit));
		additionalAmountStr.append(minUnit);
		IMFConstants.AMOUNT_SIGN amountSign = additionalAmount.getAmountSign();
		if (IMFConstants.AMOUNT_SIGN.C.equals(amountSign)) {
		    additionalAmountStr.append(AMOUNT_CREDIT);
		} else if (IMFConstants.AMOUNT_SIGN.D.equals(amountSign)) {
		    additionalAmountStr.append(AMOUNT_DEBIT);
		}
		String addAmt = formatter.format(additionalAmount.getAmount()).replaceAll("\\.", "");
		if (!Utils.isEmpty(addAmt) || !"null".equalsIgnoreCase(addAmt)) {
		    addAmt = Utils.fillChars(addAmt, 12, '0');
		} else {
		    addAmt = "000000000000";
		}
		additionalAmountStr.append(addAmt);
	    }
	    return additionalAmountStr.toString();
	}
	return null;
    }

    public static Double getAdditionalAmountForHTTP(List<IMFAdditionalAmount> additionalAmountList) throws ISOException {
	if (additionalAmountList != null && additionalAmountList.size()>0) {
	    DecimalFormat formatter = null;
	    IMFAdditionalAmount additionalAmount = null;
	    formatter = new DecimalFormat(currencyFormat("2"));
	    for (int i = 0; i < additionalAmountList.size(); i++) {
		additionalAmount = additionalAmountList.get(i);
		String addAmt = formatter.format(additionalAmount.getAmount()).replaceAll("\\.", "");
		if (!Utils.isEmpty(addAmt) || !"null".equalsIgnoreCase(addAmt)) {
		    addAmt = Utils.fillChars(addAmt, 12, '0');
		} else {
		    addAmt = "000000000000";
		}
		return Double.valueOf(addAmt)/100;
	    }
	}
	return null;
    }

    private static String currencyFormat(String digit) {
	String format = "#.00";
	int d = Integer.parseInt(digit);
	if (d > 0) {
	    format = "#.";
	    for (int cnt = 0; cnt < d; cnt++) {
		format += "0";
	    }
	}
	return format;
    }

    @SuppressWarnings("unchecked")
    public static String getMtiFor2003Response(String mti87) {
	Set set = MTI_RESPONSE.entrySet();
	for (Iterator l_iterator = set.iterator(); l_iterator.hasNext();) {
	    Map.Entry<String, String> entry = (Map.Entry) l_iterator.next();
	    if (entry.getValue().equals(mti87)) {
		return (entry.getKey().substring(0, 4));
	    }
	}
	return null;
    }

    public static String getReasonCode(String pSelfdetermined) throws ISOException {
	if (pSelfdetermined != null) {
	    if (pSelfdetermined.length() >= 4) {
		return pSelfdetermined.substring(0, 4);
	    } else {
		return ISOUtil.padleft(pSelfdetermined, 4, '0');
	    }
	}
	return null;
    }

    public static String getDateSettlementAcquirerFormat(String dateSettlementCupdFormat) {
	if (dateSettlementCupdFormat != null && dateSettlementCupdFormat.trim().length() > 0) {
	    String century = null;
	    String yearLast2Digit = null;
	    String yearFirst2Digit = null;
	    java.util.Calendar cal = Calendar.getInstance();
	    int year = cal.get(java.util.Calendar.YEAR);
	    yearLast2Digit = String.valueOf(year).substring(2);
	    yearFirst2Digit = String.valueOf(year).substring(0, 2);
	    if (Integer.parseInt(yearLast2Digit) != 0) {} else {
		century = String.valueOf(Integer.parseInt(yearFirst2Digit));
	    }
	    return century + yearLast2Digit + dateSettlementCupdFormat;
	}
	return null;
    }

}
