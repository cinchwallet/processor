package com.cinchwallet.acquirer.http.validation;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionManager;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.acquirer.http.msg.HttpRequest;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.exception.CWValidationException;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.validation.BaseValidation;


/**
 *
 * The <code>OneIncHttpValidation</code> class encapsulate the basic functionality
 * to perform OneInc Specific validations. This class uses the basic validation
 * rules defined in the
 * <code>RPSValidation<code>, and then applied its own business rules to validate the request.
 *
 * RPSValidation defines the following rules:
 * <p>
 * <blockquote>
 *
 * <pre>
 *  required - validation fails if field is not present in the request.
 *  constant - validation fails if field's length is not of a given fixed length.
 *  enum - field's value must lies within the value configured in the validation-rule.xml.
 *  panValidation - validate PAN number, by applying some business rules for PAN.
 *  dateValidation - validate date against the provided date format.
 *  optionalfield - if field is not in request, validation passes. But if field is given in the request, its length must be of given fixed length.
 * </pre>
 *
 * </blockquote>
 * </p>
 * These rules are static in the way that, they just check whether field has some value or not, what is the length of field.
 * OneInc Validation goes one level up where incoming values are validated against the data configured
 * in the database (from MMS) for some of the fields like TID/MID etc.
 *
 * Several private validateXXX() methods have been defined, to perform the task of validation.
 * e.g. <code>validateTxnAmount</code> method ensure that amount should not be zero in case of Redemption,
 * if so it throws the RPSValidationException with appropriate IRC in the response.
 *
 * <p>
 * To start with validation <code>OneIncHttpValidation</code> first make the instance of RadicalPojo from the incoming ISOMsg
 * request, Invoke the validate method of the RPSValidation, which perform the generic validation as per rules defined in the validator-rules.xml.
 * <p>
 * Then, OneInc specific validation being done by invoking the private validate methods.
 *
 *
 * @see RPSValidation
 * @see TransactionParticipant
 * @see Configurable
 *
 */
public class HttpValidation extends BaseValidation implements TransactionParticipant, Configurable {

    private IAcquirerSpec      acqSpec;
    private TransactionManager transactionManager = null;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
         * @see TransactionParticipant#abort(long, Serializable)
         */
    public void abort(long id, Serializable ctx) {

    }

    /**
         * @see TransactionParticipant#commit(long, Serializable)
         */
    public void commit(long id, Serializable context) {

    }

    /**
         * Validate the incoming request to ensure that all the required fields
         * are there to process this request. Generic validation is being done
         * by invoking the RPSValidation's validate method. Several private
         * validate methods get invoked to validate the field's value against
         * the database or to ensure that they comply with business rule.
         * <p>
         * e.g. Provisioning request must contains the field 53 i.e.
         * SecurityRelatedControlInfo, so request should have
         * SecurityRelatedControlInfo, otherwise RPSValidationException is
         * thrown with appropriate IRC for missing SecurityRelatedControlInfo.
         * <p>
         * MerchantCategoryCode in request must be the same, what is configured
         * in the MMS while creating that merchant. These kind of validation are
         * being done here.
         *
         * @see TransactionParticipant#prepare(long, Serializable)
         */
    public int prepare(long id, Serializable ctx) {
	TransContext context = null;
	try {
	    context = (TransContext) ctx;
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    IAcquirerSpec spec = (IAcquirerSpec) context.getAcquirerSpec();
	    if (spec == null) {
		spec = acqSpec;
		context.setAcquirerSpec(spec);
	    }

	    HttpRequest request = (HttpRequest) context.getAcquirerRequest();
	    super.validate(request);


	} catch (CWValidationException ex) {
	    CWLogger.appLog.error(ex.toString(), ex);
	    context.setIRC(ex.getIRC());
	    String errorMsg = ex.getMessage();
	    //context.setErrorMessage(errorMsg.substring(errorMsg.indexOf(",")+1));
	    return ABORTED | NO_JOIN;
	} catch (Exception ex) {
	    CWLogger.appLog.error(ex.toString(), ex);
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    return ABORTED | NO_JOIN;
	}

	return PREPARED | NO_JOIN;
    }

    /**
         * Configuration method used by the Q2 framework to initialize the
         * member-variable of the class. In this case, it just invokes the super
         * class setConfiguration to initialize the class.
         */
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	super.setConfiguration(cfg);
	String specificationClassName = this.transactionManager.getConfiguration().get("message-specification-class");
	if (specificationClassName != null && specificationClassName.length() > 0) {
	    try {
		this.acqSpec = (IAcquirerSpec) Class.forName(specificationClassName).newInstance();
	    } catch (Exception _ex) {
		throw new ConfigurationException(_ex.getMessage(), _ex);
	    }
	}
    }

    public void setTransactionManager(TransactionManager txnMgr) {
	this.transactionManager = txnMgr;
    }

/*

    private void populateCardInfo(HttpRequest httpMsg, String trackData) throws RPSValidationException {
	try {
	    RadicalLogger.appLog.info("TrackData Received :" + trackData);
	    //String trackData = httpMsg.getTrackData();
	    int indexCap = trackData.indexOf(SwitchConstants.CAP);
	    String upc = trackData.substring(indexCap + 1, indexCap + 14);
	    int indexCapNext = trackData.indexOf("^", indexCap+1);
	    if(indexCapNext!=-1){
		//expiry date in present in track1 data
		String expiryDate = trackData.substring(indexCapNext+1, indexCapNext + 5);
		//acquirerIMF.setDtExpiry(expiryDate);
	    }
	    String cardNumber = null;
	    if (trackData.startsWith("%99")) {
		cardNumber = trackData.substring(3, indexCap);
	    } else if (trackData.startsWith("99")) {
		cardNumber = trackData.substring(2, indexCap);
	    }

	    httpMsg.setPrimaryAccountNumber(cardNumber.trim());
	    httpMsg.setUpc(upc.trim());
	    httpMsg.setTrackData(null);
	} catch (Exception e) {
	    throw new RPSValidationException("INVALID TRACK DATA", IMFResponseCodes.INV_TRACK_DATA);
	}
    }*/

}