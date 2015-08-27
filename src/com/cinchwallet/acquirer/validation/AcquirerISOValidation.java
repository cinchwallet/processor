package com.cinchwallet.acquirer.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.constant.ISO8583_2003_SpecConstant;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.exception.CWValidationException;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.validation.BaseValidation;
import com.cinchwallet.core.validation.RequestPojo;


/**
 *
 * The <code>OneIncValidation</code> class encapsulate the basic functionality
 * to perform OneInc Specific validations. This class uses the basic validation
 * rules defined in the
 * <code>BaseValidation<code>, and then applied its own business rules to validate the request.
 *
 * BaseValidation defines the following rules:
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
 * if so it throws the BaseValidationException with appropriate IRC in the response.
 *
 * <p>
 * To start with validation <code>OneIncValidation</code> first make the instance of RadicalPojo from the incoming ISOMsg
 * request, Invoke the validate method of the BaseValidation, which perform the generic validation as per rules defined in the validator-rules.xml.
 * <p>
 *
 *
 * @see BaseValidation
 * @see TransactionParticipant
 * @see Configurable
 *
 */
public class AcquirerISOValidation extends BaseValidation implements TransactionParticipant, Configurable {

    /**
         * Every Transaction request have a distinct set of mti, processing code
         * and function code, based on which processor processes the request.
         * <p>
         * e.g. Activation Request must have MTI = 2302, Processing Code =
         * 720000 and function Code = 301.
         * <p>
         *
         * <code>validFunctionCode</code> holds the function code against each
         * mti and processing code combination.
         * <p>
         * To validate the function code of the incoming request, this Map is
         * referred.
         *
         */
    private static Map<String, String> validFunctionCode = new HashMap<String, String>();
    private static Properties props ;
    static {
	validFunctionCode.put(ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_REQUEST_MTI + ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_PC, SwitchConstants.FUN_CODE_LOYALTY_NUMBER_INQUIRY);

	props = new Properties();
	try {
	    InputStream inputStream = AcquirerISOValidation.class.getClassLoader().getResourceAsStream("isomessagemapping.properties");
	    if (inputStream == null) {
	        CWLogger.appLog.error("property file isomessagemapping.properties not found in the classpath");
	    }
	    props.load(inputStream);
        } catch (IOException e) {
            CWLogger.appLog.error("Failed to load isomessagemapping.properties", e);
        }
    }

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
         * by invoking the BaseValidation's validate method. Several private
         * validate methods get invoked to validate the field's value against
         * the database or to ensure that they comply with business rule.
         * <p>
         * e.g. Provisioning request must contains the field 53 i.e.
         * SecurityRelatedControlInfo, so request should have
         * SecurityRelatedControlInfo, otherwise BaseValidationException is
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
	    RequestPojo radicalPojo = (RequestPojo) populatePOJO(ctx);
	    super.validate(radicalPojo);
	    //validate function code
	    validateFunctionCode(radicalPojo.getFunctionCode(), radicalPojo.getMti(), radicalPojo.getProcessingCode());

	} catch (CWValidationException ex) {
	    CWLogger.appLog.error(ex.toString(), ex);
	    context.setIRC(ex.getIRC());
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
    }

    /**
         * Create a new instance of the RadicalPojo and populate it with the
         * values in the ISOMsg.
         * <p>
         * radicalmessagemapping.properties defines the mapping of the field
         * element in the ISOMSg with the field name in the RadicalPojo.
         *
         * <p>
         * For example: following are the mapping defined in the
         * radicalmessagemapping.properties.
         * <p>
         * 0=mti - indicates that field 0 is mapped with the mti in the
         * RadicalPojo. 2=pan - indicates that field 2 is mapped with the pan in
         * the RadicalPojo.
         * <p>
         * This method read the radicalmessagemapping.properties and fetches the
         * 0th field value from the ISOMsg and populate it into the mti of the
         * RadicalPojo, and 2nd field's value in the pan of RadicalPojo. In the
         * same way, it populate the entire object.
         * <p>
         * If any new field needs to be declared, mapping should be made into
         * radicalmessagemapping.properties as follow:
         * <p>
         * 150=myField, and a member variable with name myField should be
         * created in RadicalPojo.
         *
         * @param Serializable ctx
         * @throws Exception
         *
         */
    @Override
    public Serializable populatePOJO(Serializable ctx) throws Exception {

	TransContext context = (TransContext) ctx;
	ISOMsg msg = (ISOMsg) context.getAcquirerRequest();
	RequestPojo radicalPojo = new RequestPojo();
	Set<Object> keySet = props.keySet();
	Iterator<Object> itr = keySet.iterator();
	String nextToken;
	while (itr.hasNext()) {
	    nextToken = (String) itr.next();
	    if (props.getProperty(nextToken) != null && !props.getProperty(nextToken).equals(""))
		PropertyUtils.setSimpleProperty(radicalPojo, props.getProperty(nextToken), msg.getString(nextToken));
	}
	return radicalPojo;
    }

    /**
         * Validate function Code by looking at the mti and processing code in
         * the request. Every combination of MTI+ProcCode and their Function
         * Code is defined in the Map <code>validFunctionCode</code>.
         *
         * Function Code in the request must match with the value of the
         * MTI+ProcCode of the validFunctionCode, otherwise
         * BaseValidationException is thrown with IRC of INVALID_FUNCTION_CODE.
         *
         * @param fieldData - Function Code of the request to be validated.
         * @param mti - MTI of the request.
         * @param procCode - Processing Code of the request.
         * @throws CWValidationException
         */
    private void validateFunctionCode(String fieldData, String mti, String procCode) throws CWValidationException {
	String key = mti + procCode;
	if (fieldData != null && !(fieldData.equalsIgnoreCase(validFunctionCode.get(key)))) {
	    throw new CWValidationException("INVALID FUNCTION CODE", IMFResponseCodes.INVALID_FUNCTION_CODE);
	}
    }

}