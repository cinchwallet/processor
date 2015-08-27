package com.cinchwallet.acquirer.http.msg.spec;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;

import org.jpos.core.Configuration;
import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.ISOUtil;

import com.cinchwallet.acquirer.http.constant.HTTPResponseCode;
import com.cinchwallet.acquirer.http.msg.HttpRequest;
import com.cinchwallet.acquirer.http.msg.HttpResponse;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.constant.CWConstant;
import com.cinchwallet.core.constant.SequenceEnum;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.exception.CWValidationException;
import com.cinchwallet.core.exception.SwitchException;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFAmount;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.TransactionLeg;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.sequence.SequenceManager;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.Utils;

public class HttpSpecification implements IAcquirerSpec {

    private Configuration ircConfiguration;

    public HttpSpecification() {
	try {
	    InputStream inp = HttpSpecification.class.getClassLoader().getResourceAsStream(CWConstant.IRC_FILE_NAME);
	    //InputStream inp  = new FileInputStream(fileName);
	    Properties properties = new Properties();
	    properties.load(inp);

	    ircConfiguration = new SimpleConfiguration(properties);
	} catch (FileNotFoundException e) {
	    throw new RuntimeException(e.getMessage(), e);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public String getSpecificationName() {
	return "HttpSpecification";
    }

    @Override
    public void populateAcquirerRequestIMF(TransContext transContext) throws SwitchException {
	// convert HTTPMSg to RadicalMsg
	TxnMsg acquirerIMF = null;
	try {
	    // populate acquirerIMF from httpMsg.
	    HttpRequest httpMsg = (HttpRequest) transContext.getAcquirerRequest();
	    acquirerIMF = new TxnMsg();
	    acquirerIMF.setLeg(TransactionLeg.LEG_1.name());
	    acquirerIMF.setTransactionType(httpMsg.getTxnType());
	    acquirerIMF.setSpecificationName(getSpecificationName());
	    acquirerIMF.setTxnId(ISOUtil.zeropad(SequenceManager.getInstance().getSequenceIdLong(SequenceEnum.TXN_ID_SEQ.name()).toString(), 12));
	    acquirerIMF.setMid(httpMsg.getMerchantID());
	    acquirerIMF.setTid(httpMsg.getTerminalID());
	    acquirerIMF.setStan(httpMsg.getMerchantTxnID());
	    acquirerIMF.setPan(httpMsg.getCardNumber());
	    acquirerIMF.setDtExpiry(httpMsg.getExpiryDate());
	    acquirerIMF.setDtTransaction(Utils.getTransactionDateTimeAsCurrentTime());
	    acquirerIMF.setDtTransmission(httpMsg.getTransDate());
	    acquirerIMF.setMerchantCatCode(httpMsg.getMerchantCatCode());
	    acquirerIMF.setTxnAmount(httpMsg.getTxnAmount());
	    //TODO - probably need to populate the pan number using track 2 data.

	} catch (CWValidationException _ex) {
	    transContext.setIRC(_ex.getIRC());
	    throw new SwitchException(_ex.getMessage(), _ex);
	} catch (Exception e) {
	    transContext.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    throw new SwitchException(e.getMessage(), e);
	}
	transContext.setAcquirerIMF(acquirerIMF);
	CWLogger.appLog.info(SwitchConstants.MESSAGE_VALIDATE_SUCCESS);
    }

    @Override
    public void populateAcquirerResponse(TransContext transContext) throws SwitchException {
	// convert RadicalMsg to HTTPMSg
	TxnMsg acquirerIMF = transContext.getAcquirerIMF();
	HttpRequest request = (HttpRequest) transContext.getAcquirerRequest();
	HttpResponse responseMsg = new HttpResponse();
	// populate responseMsg from acquirerIMF.
	transContext.setAcquirerResponse(responseMsg);
	responseMsg.setMerchantTxnID(request.getMerchantTxnID());
	try {
	    if (acquirerIMF == null) {
		updateAcquirerResponse(transContext);
	    } else {
		responseMsg.setReasonCode(acquirerIMF.getReasonCode());
		responseMsg.setRespCode(acquirerIMF.getResultCd());
		responseMsg.setRespMsg(acquirerIMF.getDisplayMessage());
		responseMsg.setMerchantTxnID(acquirerIMF.getStan());
		responseMsg.setExpirationDate(acquirerIMF.getDtExpiry());
		responseMsg.setPrepaidbalance(acquirerIMF.getBalance());
		responseMsg.setCwTransID(acquirerIMF.getTxnId());
		responseMsg.setCardHolderName(acquirerIMF.getCardHolderName());
		//placeholder
		if(acquirerIMF.getTransactionType().equals(TransactionType.BALIQ.name())){
		    responseMsg.setPointBalance(100);
		}
		responseMsg.setTxns(acquirerIMF.getTxnList());
	    }

	} catch (Exception _ex) {
	    throw new SwitchException(_ex.getMessage(), _ex);
	}

    }

    @Override
    public void populateAcquirerResponseIMF(TransContext transContext) throws SwitchException {
	try {
	    TxnMsg acquirerIMF = (TxnMsg) transContext.getAcquirerIMF();
	    updateAcquirerIMF(transContext);
	    acquirerIMF.setIRC(transContext.getIRC());
	    acquirerIMF.setLeg(TransactionLeg.LEG_4.name());
	} catch (Exception _ex) {
	    throw new SwitchException(_ex.getMessage(), _ex);
	}
    }

    private void updateAcquirerIMF(TransContext context) {
	TxnMsg acquirerIMF = context.getAcquirerIMF();
	try {
	    String[] responseValues = ircConfiguration.get(context.getIRC()).split(SwitchConstants.COMMA);
	    String messageErrorCode = responseValues[0];
	    String responseCode = responseValues[1];
	    String messageDisplay = responseValues[2];
	    acquirerIMF.setDisplayMessage(messageDisplay);
	    acquirerIMF.setReasonCode(messageErrorCode);
	    acquirerIMF.setResultCd(responseCode);
	} catch (Exception _ex) {
	    acquirerIMF.setReasonCode(HTTPResponseCode.ERROR_SYSTEM_ERROR_RESPONSE_CODE);
	    acquirerIMF.setResultCd(HTTPResponseCode.HTTP_ERROR);
	    acquirerIMF.setDisplayMessage(HTTPResponseCode.ERROR_SYSTEM_ERROR_MESSAGE);
	}
    }

    private void updateAcquirerResponse(TransContext context) {
	HttpResponse responseMsg = (HttpResponse) context.getAcquirerResponse();
	try {
	    String[] responseValues = ircConfiguration.get(context.getIRC()).split(SwitchConstants.COMMA);
	    String messageErrorCode = responseValues[0];
	    String responseCode = responseValues[1];
	    String messageDisplay = responseValues[2];
	    responseMsg.setReasonCode(messageErrorCode);
	    responseMsg.setRespCode(responseCode);
	    responseMsg.setRespMsg(messageDisplay);
	} catch (Exception _Exp) {
	    try {
		responseMsg.setReasonCode(HTTPResponseCode.ERROR_SYSTEM_ERROR_RESPONSE_CODE);
		responseMsg.setRespCode(HTTPResponseCode.HTTP_ERROR);
		responseMsg.setRespMsg(HTTPResponseCode.ERROR_SYSTEM_ERROR_MESSAGE);
	    } catch (Exception e) {}
	}
    }


    private static IMFAmount getZeroTxnAmount() {
	IMFAmount amount = new IMFAmount();
	amount.setAmountScale(SwitchConstants.ACQUIRER_AMOUNT_SCALE_VALUE);
	amount.setCurrency(SwitchConstants.ACQUIRER_CURRENCY_VALUE);
	String amountStr = "0";
	amount.setAmount(new BigInteger(amountStr));
	amount.setAmountString(amountStr);
	return amount;
    }

}
