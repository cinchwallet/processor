package com.cinchwallet.core.validation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import com.cinchwallet.acquirer.http.msg.HttpRequest;
import com.cinchwallet.core.constant.ISO8583_2003_SpecConstant;
import com.cinchwallet.core.exception.CWValidationException;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.TransactionType;

/**
 * Abstract Class <code>RPSValidation</code> defines the generic validation
 * rule, which would be applied on every request before being processed by
 * switch. Entire validation rules needs to be defined in an XML configuration
 * file named as validation-rule.xml.
 *
 * Following are the generic validation rules which have been defined:
 * <p>
 * <blockquote>
 *
 * <pre>
 * required - validation fails if field is not present in the request.
 * constant - validation fails if field's length is not of a given fixed length.
 * enum - field's value must lies within the value configured in the validation-rule.xml.
 * panValidation - validate PAN number, by applying some business rules for PAN.
 * dateValidation - validate date against the provided date format.
 * optionalfield - if field is not in request, validation passes. But if field is given in the request, its length must be of given fixed length.
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * It create object of <code>org.apache.commons.validator.Validation</code>
 * with validation-rule.xml as parameter and call its validate method to
 * validate the request. After validation, it checks the result and report the
 * validation failure if any, to the system.
 * <p>
 * oneinc-resource.properties contains the user friendly message for each field
 * defined in the validation-rule file. For any validation failure, system picks
 * the message from this property file for that field and prepare the exact
 * message to be shown to the user.
 *
 * There are two validation forms defined in validation-rules.xml as follow:
 * <p>
 * <blockquote>
 *
 * <pre>
 * ValidateProvBean - mapped the field against their validation rule for Provisioning. For any Provisioning related validation, configuration needs to be made in this form definition.
 *
 * ValidateBean - mapped the field against their validation rule for all the transactions other than Provisioning. Configuration is required to be made here to modify/add any validation.
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * To define a new validation rule, make a new entry under global tag in
 * validation-rules.xml as follow:
 * <p>
 * <blockquote>
 *
 * <pre>
 * &lt;validator name=&quot;validationName&quot;
 * 		classname=&quot;fully qualified Class name where rule is defined&quot;
 * 		method=&quot;exact method name to be executed to validate rule&quot;
 * 		methodParams=&quot;java.lang.Object,org.apache.commons.validator.Field&quot;
 * 		msg=&quot;key in the oneinc-resource.properties, corresponding value would be shown in case of validation fails&quot; /&gt;
 * </pre>
 *
 * </blockquote>
 * <p>
 *
 * To create a new Validation Form, make a new form entry in
 * validation-rules.xml as defined below.
 * <p>
 * <blockquote>
 *
 * <pre>
 * &lt;form name=&quot;ValidateBean&quot;&gt;
 * 			&lt;field property=&quot;yourfieldname&quot; depends=&quot;vaidationname defined above&quot;&gt;
 * 			&lt;arg key=&quot;yourfieldname defined in oneinc-resource.properties&quot; /&gt;
 * 		&lt;/field&gt;
 * </pre>
 *
 * </blockquote>
 * <p>
 *
 * To Create the instance of newly defined form above, use the following
 * <p>
 * newForm = resources.getForm(Locale.getDefault(), <NewFormNameDefinedinXML>);
 * in the <code>setConfiguration</code> method.
 * <p>
 * Any Specific Validation Class need to extends <code>RPSValidation</code>
 * and {@link Override} populatePOJO(Serializable context) method to create its
 * specific Pojo object, on which entire validation rule would be applied.
 *
 * @see Configurable
 *
 *
 */
public abstract class BaseValidation implements Configurable {

    private String                              validationFileName = null;
    private ValidatorResources                  resources          = null;
    private static ResourceBundle               apps               = null;
    private Form                                form               = null;
    private Form                                provForm           = null;
    private static Map<String, TransactionType> txnTypeMap         = new HashMap<String, TransactionType>();

    public Serializable populatePOJO(Serializable context) throws Exception {
	return null;
    }

    /**
     * Validate the given bean. It internally invoke the
     * <code>checkValidation</code> method to validate the request.
     *
     * @param bean - Bean to be validated
     * @throws CWValidationException - if validation gets fail.
     */
    public void validate(Serializable bean) throws CWValidationException {
	checkValidation(bean);

    }

    /**
     * Validate the given bean and throws Exception if some validation get
     * fails. Given bean is type casted in RadicaPojo. Validator's validate
     * method is invoked to validate the given bean.
     * <p>
     * Appropriate IRC is being set if validation fails, which is used to track
     * the actual problem in the request data. If Exception occur while
     * executing validate method, SYSTEM_ERROR is being set in IRC and error is
     * reported.
     *
     * @param bean - Bean to be validated
     * @throws CWValidationException - if validation gets fail.
     */
    private void checkValidation(Serializable bean) throws CWValidationException {
	RequestPojo radicalPojo = (RequestPojo) bean;
	TransactionType txnType = getTransactionType(radicalPojo);
	Validator validator = new Validator(resources, "ValidateBean");
	validator.setParameter(Validator.BEAN_PARAM, bean);
	ValidatorResults results = null;
	try {
	    results = validator.validate();
	} catch (ValidatorException e) {
	    throw new CWValidationException(e.getLocalizedMessage(), IMFResponseCodes.SYSTEM_ERROR);
	}
	checkResults(bean, results, resources, form);
    }


    public void validate(HttpRequest httpRequest) throws CWValidationException {
	    Validator validator = new Validator(resources, "ValidateBean");
	    validator.setParameter(Validator.BEAN_PARAM, httpRequest);
	    ValidatorResults results = null;
	    try {
		results = validator.validate();
	    } catch (ValidatorException e) {
		throw new CWValidationException(e.getLocalizedMessage(), IMFResponseCodes.SYSTEM_ERROR);
	    }
	    checkResults(null, results, resources, form);
    }

    /**
     * Iterate over the validation result to check for any validation failure.
     * For the very first validation failure, it reads the
     * oneinc-resource.properties encapsulated in member variable apps and set
     * the user friendly message along with the IRC and throws the
     * OneIncValidationException.
     *
     * @param bean - Bean to be validated
     * @param results - contains the validation result
     * @param resources2 - ValidatorResources object prepared for
     *                validation-rules.xml
     * @param form - contains the mapping for field and their validation rule.
     * @throws CWValidationException - if validation gets fail.
     */
    @SuppressWarnings( { "unchecked", "deprecation" })
    private void checkResults(Serializable bean, ValidatorResults results, ValidatorResources resources2, Form form) throws CWValidationException {
	Iterator propertyNames = results.getPropertyNames().iterator();
	while (propertyNames.hasNext()) {
	    String propertyName = (String) propertyNames.next();
	    ValidatorResult result = results.getValidatorResult(propertyName);
	    Map actionMap = result.getActionMap();
	    Iterator keys = actionMap.keySet().iterator();
	    Field field = form.getField(propertyName);
	    String prettyFieldName = apps.getString(field.getArg(0).getKey());
	    while (keys.hasNext()) {
		String actName = (String) keys.next();
		ValidatorAction action = resources.getValidatorAction(actName);
		if (!result.isValid(actName)) {
		    String message = apps.getString(action.getMsg());
		    Object[] args = { prettyFieldName };
		    String formatMsg = MessageFormat.format(message, args);
		    int colonIndex = formatMsg.indexOf(',');
		    String imfResponseCode = null;
		    String msg = null;
		    if (colonIndex > 0) {
			imfResponseCode = formatMsg.substring(0, colonIndex);
			msg = formatMsg.substring(colonIndex);
		    }
		    throw new CWValidationException(msg, imfResponseCode);
		}
	    }
	}
    }

    /**
     * Configuration method invoked by framework while initializing Class.
     * Following are the parameters used :
     * <p>
     * <blockquote>
     *
     * <pre>
     * resource-file - oneinc-resource.properties
     * validation-file - validation-rules.xml
     * </pre>
     *
     * </blockquote>
     * <p>
     * This method initialize the ValidatorResources by reading the
     * validation-file passed and then initialize different forms defined in
     * this file. It also initialize the validation resource bundle i.e.
     * oneinc-resource.properties from where user friendly message would be
     * picked in case of validation failure.
     * <p>
     * Member-variable needs to be initialized/assigned once while initializing
     * the class, and is being passed from the XML is initialized in this
     * method.
     *
     * In XML, use the following tag to define the property:
     * <p>
     * &lt;property name="resource-file" value="oneinc-resource" /&gt;
     * <p>
     * corresponding code to get the property is as follow:
     * <p>
     * String resourceName = cfg.get("resource-file");
     *
     *
     * @param cfg - Configuration object holds the property defined in the XML.
     *
     * @throws ConfigurationException
     */
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	try {
	    String resourceName = cfg.get("resource-file");
	    apps = ResourceBundle.getBundle(resourceName);
	    validationFileName = cfg.get("validation-file");
	    InputStream in = null;
	    in = BaseValidation.class.getClassLoader().getResourceAsStream(validationFileName);
	    try {
		resources = new ValidatorResources(in);
		form = resources.getForm(Locale.getDefault(), "ValidateBean");
		provForm = resources.getForm(Locale.getDefault(), "ValidateProvBean");
	    } finally {
		if (in != null) {
		    in.close();
		}
	    }
	    prepareTxnMap();
	} catch (FileNotFoundException e) {
	    throw new ConfigurationException(e.getLocalizedMessage(), e);
	} catch (IOException e) {
	    throw new ConfigurationException(e.getLocalizedMessage(), e);
	} catch (Exception e) {
	    throw new ConfigurationException(e.getLocalizedMessage(), e);
	}
    }

    /**
     * Returns the enum TransactionType by introspecting the message's MTI and
     * processing code. It returns TransactionType in String format which make
     * it simple to identify the transaction type by just looking at it instead
     * of getting it from the combination of MTI and processing code.
     *
     * @param Serializable message
     * @return TransactionType
     * @throws CWValidationException
     */
    public TransactionType getTransactionType(Serializable message) throws CWValidationException {
	RequestPojo rapRequest = (RequestPojo) message;
	TransactionType txnType = null;
	try {
	    String txnTypeKey = null;
	    txnTypeKey = rapRequest.getMti() + rapRequest.getProcessingCode();
	    txnType = txnTypeMap.get(txnTypeKey);
	    if (txnType == null) {
		throw new CWValidationException("InValid Transaction Type", IMFResponseCodes.UNKNOWN_TRANSACTION_TYPE);
	    }
	} catch (Exception _ex) {
	    throw new CWValidationException("InValid Transaction Type", IMFResponseCodes.UNKNOWN_TRANSACTION_TYPE);
	}
	return txnType;
    }

    /**
     * Prepare a Map<String, TransactionType>, holds the MTI + Processing Code
     * as key and enum TransactionType as the value. This is used by the code to
     * get the transaction type based on the MTI and Processing Code. This avoid
     * calculating transaction type based on MTI and Processing Code every time
     * required.
     */
    private void prepareTxnMap() {
	txnTypeMap.put(ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_REQUEST_MTI + ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_PC, TransactionType.LOY_NO_IQ);
    }
}
