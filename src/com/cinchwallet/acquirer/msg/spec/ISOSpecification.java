package com.cinchwallet.acquirer.msg.spec;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jpos.core.Configuration;
import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.ClientChannel;
import org.jpos.iso.ISOAmount;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.constant.CWConstant;
import com.cinchwallet.core.constant.ISO8583_1987_Constant;
import com.cinchwallet.core.constant.ISO8583_2003_SpecConstant;
import com.cinchwallet.core.constant.SequenceEnum;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.exception.SwitchException;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFAdditionalAmount;
import com.cinchwallet.core.msg.IMFAmount;
import com.cinchwallet.core.msg.IMFCardAcceptorDetail;
import com.cinchwallet.core.msg.IMFConstants;
import com.cinchwallet.core.msg.IMFMessageErrorIndicator;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.TransactionLeg;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.sequence.SequenceManager;
import com.cinchwallet.core.utils.CWLogger;

public class ISOSpecification implements IAcquirerSpec {

    private static Map<String, String>          responseMTIMap         = new HashMap<String, String>();
    private static Map<String, TransactionType> txnTypeMap             = new HashMap<String, TransactionType>();
    private Configuration                       ircConfiguration;

    static {
	responseMTIMap.put(ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_REQUEST_MTI, ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_RESPONSE_MTI);

	txnTypeMap.put(ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_REQUEST_MTI + ISO8583_2003_SpecConstant.LOYALTY_NUMBER_INQUIRY_PC, TransactionType.LOY_NO_IQ);
    }

    /**
     * It loads the oneinc.properties and oneinc_irc.properties which contains the
     * oneinc related properties and IRCs. These properties files are loaded in a
     * <code>Configuration</code> object, which is then used to get all
     * properties.
     *
     */
    public ISOSpecification() {
	try {
	     InputStream inp = ISOSpecification.class.getClassLoader().getResourceAsStream(CWConstant.IRC_FILE_NAME);
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

    /**
     * Overridden method of <code>IAcquirerSpec</code>. It populates the
     * AcquirerRequestIMF, also referred as LEG_1. RadicalMsg have the fields
     * mapped with each field in the ISOMsg, System iterate over the ISOMsg and
     * populate the instance of RadicalMsg. Some conversion also needs to be
     * done while populating AcquirerRequestIMF. e.g. DE-43 Card Acceptor
     * Name/Location from ISOMSg which is binary, get converted into object
     * IMFCardAcceptorDetail. Dates are transformed into their required date
     * format from long value in request.
     * <p>
     * Txn_id is generated from the system by reading the next sequence from
     * sequence_item table and assigned to AcquirerRequestIMF.
     * <p>
     * RRN is discarded if it is already in request and txn_id is assigned to
     * RRN to make it unique for each transaction.
     * <p>
     * If request do not contains STAN, System generates a new one.
     * <p>
     *
     *
     * @throws SwitchException - if some problem occur while populating
     *                 AcquirerRequestIMF, with IRC of SYSTEM_ERROR.
     */

    public void populateAcquirerRequestIMF(TransContext context) throws SwitchException {
	TxnMsg acquirerIMF = null;
	try {
	    ISOMsg isoMsg = (ISOMsg) context.getAcquirerRequest();
	    acquirerIMF = new TxnMsg();

	    TransactionType txnType = getTransactionType(isoMsg);
	    acquirerIMF.setTransactionType(txnType.name());
	    acquirerIMF.setSpecificationName(getSpecificationName());
	    acquirerIMF.setMti(isoMsg.getMTI());
	    acquirerIMF.setTxnId(ISOUtil.zeropad(SequenceManager.getInstance().getSequenceIdLong(SequenceEnum.TXN_ID_SEQ.name()).toString(), 12));
	    acquirerIMF.setPan(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_PAN));
	    acquirerIMF.setProcCcode(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_PROCESSING_CODE));
	    acquirerIMF.setTransactionAmount(new IMFAmount(isoMsg.getComponent(ISO8583_2003_SpecConstant.FIELD_TR_AMOUNT)));
	    acquirerIMF.setDtTransmission(getTransmissionDateTimeFromISOMsg(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_TRANSMISSION_DT_TIM)));
	    acquirerIMF.setDtTransaction(new Date());
	    acquirerIMF.setStan(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_STAN));
	    //not capturing F12 Date & Time, Local Transaction
	    acquirerIMF.setDtExpiry(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_EXP));
	    acquirerIMF.setPosDataCd(isoMsg.getBytes(ISO8583_2003_SpecConstant.FIELD_POS_DATA_CODE));
	    acquirerIMF.setFunctionCode(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_FUNCTION_CODE));
	    acquirerIMF.setMerchantCatCode(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_MERCHANT_CAT_CODE));
	    acquirerIMF.setAcqInstitutionIdCd(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_ACQID));
	    acquirerIMF.setTrack2Data(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_TRACK2_DATA));
	    acquirerIMF.setTid(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_CATID));
	    acquirerIMF.setMid(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_CATID_CODE));
	    //F48, capturing oneinc number in F48
	    acquirerIMF.setOneIncNumber(isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_ADDITIONAL_DATA));
	    acquirerIMF.setLeg(TransactionLeg.LEG_1.name());
	} catch (Exception e) {
	    throw new SwitchException(e.getMessage(), e);
	}
	context.setAcquirerIMF(acquirerIMF);
	CWLogger.appLog.info("Message validated successfully");

    }

    /**
     * Overridden method of <code>IAcquirerSpec</code>. It populates the
     * AcquirerResponseIMF, also referred as LEG_4. LEG_4 is prepared from LEG_1
     * and response received from Processor i.e. LEG_3
     * <p>
     * Context contains the LEG_1 and LEG_4 object in acquirerIMF, while LEG_2
     * and LEG_3 is contained in processorIMF. System get the LEG_1 info from
     * the context and override some values from the LEG_3 to prepare LEG_4.
     *
     * Populating Acquirer Response has two part:
     *
     * <p>
     * <blockquote>
     *
     * <pre>
     * 1. Provisioning Response contains data related to application data only,
     * so this needs to be handled in a separate way where application data is
     * prepared and send it to client.
     * 2. All other Transaction response, which
     * contains the info regarding the transaction made. Info from the
     * processorIMF is read and populated into AcquirerIMF.
     * </pre>
     *
     * </blockquote>
     * </p>
     * Updating function code for update is the important part of Response,
     * based on which client get to know, if some update is available for him
     * and he can update his terminal by sending update request. If some updates
     * are available, function code is set to 900 in the response.
     *
     * @throws SwitchException - if some problem occur while populating
     *                 AcquirerResponseIMF, with IRC of SYSTEM_ERROR.
     */
    public void populateAcquirerResponseIMF(TransContext transContext) throws SwitchException {
	try {
	    TxnMsg acquirerIMF = (TxnMsg) transContext.getAcquirerIMF();
	    updateAcquirerIMF(transContext);

	    acquirerIMF.setIRC(transContext.getIRC());
	    acquirerIMF.setLeg(TransactionLeg.LEG_4.name());
	    acquirerIMF.setMti(responseMTIMap.get(acquirerIMF.getMti()));

	} catch (Exception _ex) {
	    throw new SwitchException(_ex.getMessage(), _ex);
	}
    }

    @SuppressWarnings("unchecked")
    public void populateAcquirerResponse(TransContext context) throws SwitchException {
	try {
	    TxnMsg acquirerIMF = context.getAcquirerIMF();
	    ISOMsg request = (ISOMsg) context.getAcquirerRequest();
	    ISOMsg responseISOMsg = (ISOMsg) ((ISOMsg) request).clone();
	    context.setAcquirerResponse(responseISOMsg);

	    if (acquirerIMF==null) {
		responseISOMsg.setResponseMTI();
		responseISOMsg.setSource(((ISOMsg) request).getSource());
		updateAcquirerResponse(context);
		logInValidRequest(context);
	    } else {

		responseISOMsg.setMTI(acquirerIMF.getMti());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_PAN, acquirerIMF.getPan());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_PROCESSING_CODE, acquirerIMF.getProcCcode());
		if (acquirerIMF.getTransactionAmount() != null) {
		    responseISOMsg.set(new ISOAmount(ISO8583_2003_SpecConstant.FIELD_TR_AMOUNT, AcquirerResponseConversion.getCurrencyFromCode(acquirerIMF.getTransactionAmount().getCurrency()), new BigDecimal(String.valueOf(acquirerIMF
			    .getTransactionAmount().getAmount()))));
		}
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_TRANSMISSION_DT_TIM, AcquirerResponseConversion.getTransmissionDateTimeFromIMF(acquirerIMF.getDtTransmission()));
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_STAN, acquirerIMF.getStan());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_TRANSACTION_DT_TIM, AcquirerResponseConversion.getTransactionDateTimeFromIMF(acquirerIMF.getDtTransaction()));
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_EXP, acquirerIMF.getDtExpiry());
		if (acquirerIMF.getMessageErrorIndicator() != null && !acquirerIMF.getMessageErrorIndicator().isEmpty()) {
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_MESSAGE_ERROR_INDICATOR, AcquirerResponseConversion.getMessageErrorIndicatorForISOMsg(acquirerIMF.getMessageErrorIndicator()));
		}
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_REASON_CODE, acquirerIMF.getReasonCode());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_ACQID, acquirerIMF.getAcqInstitutionIdCd());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_RETRIVAL_REF_NUM, acquirerIMF.getRrn());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_APPROVAL_CODE, acquirerIMF.getApprovalCd());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_RESULT_CODE, acquirerIMF.getResultCd());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CATID, acquirerIMF.getTid());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CATID_CODE, acquirerIMF.getMid());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_DISPLAY_MESSAGE, acquirerIMF.getDisplayMessage());
		if (acquirerIMF.getCardAcceptorNameLocation() != null) {
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_NAME, acquirerIMF.getCardAcceptorNameLocation().getName());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_ADDR, acquirerIMF.getCardAcceptorNameLocation().getAddress());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_CITY, acquirerIMF.getCardAcceptorNameLocation().getCity());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_STATE, acquirerIMF.getCardAcceptorNameLocation().getState());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_POSTAL_CODE, acquirerIMF.getCardAcceptorNameLocation().getPostalCode());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_COUNTRY_CODE, acquirerIMF.getCardAcceptorNameLocation().getCountryCode());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_PHONE, acquirerIMF.getCardAcceptorNameLocation().getPhone());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_CUST_SR_PHONE, acquirerIMF.getCardAcceptorNameLocation().getCustomerServicePhone());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_ADDITIONAL_INFO, acquirerIMF.getCardAcceptorNameLocation().getAdditionalInfo());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_URL, acquirerIMF.getCardAcceptorNameLocation().getUrl());
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_EMAIL, acquirerIMF.getCardAcceptorNameLocation().getEmail());
		}
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_POS_DATA_CODE, acquirerIMF.getPosDataCd());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_FUNCTION_CODE, acquirerIMF.getFunctionCode());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_MERCHANT_CAT_CODE, acquirerIMF.getMerchantCatCode());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_TRACK2_DATA, acquirerIMF.getTrack2Data());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_TRACK1_DATA, acquirerIMF.getTrack1Data());
		responseISOMsg.setMTI(acquirerIMF.getMti());
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_UPC, acquirerIMF.getLoyaltyNumber());
	    }

	} catch (Exception _ex) {
	    throw new SwitchException(_ex.getMessage(), _ex);
	}
    }

    /**
     * Logs invalid request with some basic information like, clientIP, IRC and
     * transaction date and entire ISOMsg in human readable format.
     *
     * @param transContext - Context Object
     */
    private void logInValidRequest(TransContext context) {
	try {
	    StringBuilder logger = new StringBuilder();
	    logger.append("clientIP=" + getSource(context.getSource()));
	    logger.append(",aquirerSpecName=" + getSpecificationName());
	    logger.append(",IRC=" + context.getIRC());
	    //logger.append(",isoMsg=" + LogUtil.getPrintableISOMessage(context.getAcquirerRequest()));
	    logger.append(",transDate=" + new Date());
	    CWLogger.txnLog.info(logger.toString());
	} catch (Exception _ex) {
	    CWLogger.appLog.error(_ex.toString(), _ex);
	}
    }

    /**
     * Returns the source of the message originator.
     *
     * @param source - ISO Channel object
     * @return clientId - source of the message
     */
    private String getSource(Object source) {
	String clientId = null;
	if (source instanceof ClientChannel) {
	    ClientChannel client = (ClientChannel) source;
	    clientId = client.getName();
	}
	return clientId;
    }

    public String getSpecificationName() {
	return "OneInc_SPECIFICATION";
    }

    /**
     * Prepare the IMFMessageErrorIndicator object based on the IRC. It takes
     * the IRC from the context and fetches the appropriate message by reading
     * the message against that IRC from oneinc_irc.properties.
     * <p>
     * If IRC for success is found in the context, 0000 is set in the result
     * code and response will contain success message. Otherwise
     * IMFMessageErrorIndicator object would be prepared corresponding to the
     * IRC and set in the acquirerResponseIMF.
     *
     * @param context - Context Object
     */
    private void updateAcquirerIMF(TransContext context) {
	IMFMessageErrorIndicator errorMsg = new IMFMessageErrorIndicator();
	TxnMsg acquirerIMF = context.getAcquirerIMF();
	try {
	    String[] responseValues = ircConfiguration.get(context.getIRC()).split(",");
	    String messageErrorCode = responseValues[0];
	    String responseCode = responseValues[1];
	    String messageDisplay = responseValues[2];
	    acquirerIMF.setResultCd(responseCode);
	    acquirerIMF.setDisplayMessage(messageDisplay);
	    acquirerIMF.setReasonCode(messageErrorCode);
	    if (!ISO8583_1987_Constant.SYSTEM_RESPONSE_CODE.equals(responseCode)) {
		errorMsg.setErrorSeverityCode(Integer.parseInt(SwitchConstants.MEI_SEVERITY_ERROR_CODE));
		errorMsg.setMessageErrorCode(Integer.parseInt(messageErrorCode));
		errorMsg.setDataBitInError(null);
		errorMsg.setDataElementInError(null);
		errorMsg.setDatasetIdentifierInError(null);
		errorMsg.setDataSubElementInError(null);
		acquirerIMF.addMessageErrorIndicator(errorMsg);
	    }
	} catch (Exception _ex) {
	    errorMsg.setDataBitInError(SwitchConstants.MEI_DATA_BIT_ERROR_CODE);
	    errorMsg.setDataElementInError(Integer.parseInt(SwitchConstants.MEI_FIELD_ERROR_POSITION_MTI));
	    errorMsg.setDatasetIdentifierInError(SwitchConstants.MEI_DATASET_IDETIFIER_ERROR_CODE);
	    errorMsg.setDataSubElementInError(Integer.parseInt(SwitchConstants.MEI_SUB_ELEMENT_ERROR_CODE));
	    errorMsg.setErrorSeverityCode(Integer.parseInt(SwitchConstants.MEI_SEVERITY_ERROR_CODE));
	    errorMsg.setMessageErrorCode(Integer.parseInt(SwitchConstants.ERROR_SYSTEM_ERROR_RESPONSE_CODE));
	    acquirerIMF.addMessageErrorIndicator(errorMsg);
	    acquirerIMF.setResultCd(SwitchConstants.ERROR);
	    acquirerIMF.setDisplayMessage(SwitchConstants.ERROR_SYSTEM_ERROR_MESSAGE);
	}
    }

    /**
     * Update the ISO Response Object with success/failure response code.
     * <p>
     * If IRC for success is found in the context, 0000 is set in the result
     * code with success message in the response. Otherwise appropriate IRC and
     * display message is being set in the ISO Response Object.
     *
     * @param context - Context Object
     */
    private void updateAcquirerResponse(TransContext context) {
	ISOMsg responseISOMsg = (ISOMsg) context.getAcquirerResponse();
	try {
	    String[] responseValues = ircConfiguration.get(context.getIRC()).split(",");
	    String messageErrorCode = responseValues[0];
	    String responseCode = responseValues[1];
	    String messageDisplay = responseValues[2];
	    if (!ISO8583_1987_Constant.SYSTEM_RESPONSE_CODE.equals(responseCode)) {
		    IMFMessageErrorIndicator mei = new IMFMessageErrorIndicator();
		    mei.setDataBitInError(SwitchConstants.MEI_DATA_BIT_ERROR_CODE);
		    mei.setDataElementInError(Integer.parseInt(SwitchConstants.MEI_FIELD_ERROR_POSITION_MTI));
		    mei.setDatasetIdentifierInError(SwitchConstants.MEI_DATASET_IDETIFIER_ERROR_CODE);
		    mei.setDataSubElementInError(Integer.parseInt(SwitchConstants.MEI_SUB_ELEMENT_ERROR_CODE));
		    mei.setErrorSeverityCode(Integer.parseInt(SwitchConstants.MEI_SEVERITY_ERROR_CODE));
		    mei.setMessageErrorCode(Integer.parseInt(messageErrorCode));
		    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_MESSAGE_ERROR_INDICATOR, AcquirerResponseConversion.getMeiAsString(mei));
	    }

	    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_RESULT_CODE, (String) responseCode);
	    responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_DISPLAY_MESSAGE, messageDisplay);

	} catch (Exception _Exp) {
	    try {
		IMFMessageErrorIndicator mei = new IMFMessageErrorIndicator();
		mei.setDataBitInError(SwitchConstants.MEI_DATA_BIT_ERROR_CODE);
		mei.setDataElementInError(Integer.parseInt(SwitchConstants.MEI_FIELD_ERROR_POSITION_MTI));
		mei.setDatasetIdentifierInError(SwitchConstants.MEI_DATASET_IDETIFIER_ERROR_CODE);
		mei.setDataSubElementInError(Integer.parseInt(SwitchConstants.MEI_SUB_ELEMENT_ERROR_CODE));
		mei.setErrorSeverityCode(Integer.parseInt(SwitchConstants.MEI_SEVERITY_ERROR_CODE));
		mei.setMessageErrorCode(Integer.parseInt(SwitchConstants.ERROR_SYSTEM_ERROR_RESPONSE_CODE));
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_MESSAGE_ERROR_INDICATOR, AcquirerResponseConversion.getMeiAsString(mei));
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_RESULT_CODE, (String) SwitchConstants.ERROR);
		responseISOMsg.set(ISO8583_2003_SpecConstant.FIELD_DISPLAY_MESSAGE, SwitchConstants.ERROR_SYSTEM_ERROR_MESSAGE);
	    } catch (ISOException e) {}
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
     * @throws RPSValidationException
     */
    private TransactionType getTransactionType(Serializable message) throws SwitchException {
	ISOMsg rapRequest = (ISOMsg) message;
	TransactionType txnType = null;
	try {
	    if (txnTypeMap != null && !txnTypeMap.isEmpty() && rapRequest != null) {
		String txnTypeKey = null;
		txnTypeKey = rapRequest.getMTI() + rapRequest.getString(ISO8583_2003_SpecConstant.FIELD_PROCESSING_CODE);
		txnType = txnTypeMap.get(txnTypeKey);
	    }
	    if (txnType == null) {
		throw new SwitchException("InValid Transaction Type" + IMFResponseCodes.UNKNOWN_TRANSACTION_TYPE);
	    }
	} catch (Exception _ex) {
	    throw new SwitchException("InValid Transaction Type" + IMFResponseCodes.UNKNOWN_TRANSACTION_TYPE);
	}
	return txnType;
    }

    /**
     * Converts <code>long</code> date into yyyyMMddHHmmss format.
     * Transmission date should be of length 10. To convert it into
     * yyyyMMddHHmmss, current year is prefixed in the given string to make it
     * of length 14 to make it compatible for yyyyMMddHHmmss format.
     *
     * @param dateFromISOMsg - Date in <code>long</code> form.
     * @return Date - in yyyyMMddHHmmss format.
     * @throws RPSValidationException - if given date could not be converted
     *                 into yyyyMMddHHmmss format.
     */
    private static Date getTransmissionDateTimeFromISOMsg(String dateFromISOMsg) throws SwitchException {
	if (dateFromISOMsg != null) {
	    try {
		SimpleDateFormat transmissionDateIMFFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		long date = Long.parseLong(dateFromISOMsg);
		if (date <= 0) {
		    return null;
		}
		java.util.Calendar cal = java.util.Calendar.getInstance();
		dateFromISOMsg = cal.get(Calendar.YEAR) + dateFromISOMsg;
		return transmissionDateIMFFormat.parse(dateFromISOMsg);
	    } catch (Exception e) {
		throw new SwitchException("INVALID TRANSMISSION DATE TIME" + IMFResponseCodes.INV_TRANSMISSION_DATETIME);
	    }
	}
	return null;
    }

    private static IMFCardAcceptorDetail getIMFCardAcceptorDetail(ISOMsg msg) {
	IMFCardAcceptorDetail cardAcceptorDetail = new IMFCardAcceptorDetail();
	cardAcceptorDetail.setName(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_NAME));
	cardAcceptorDetail.setAddress(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_ADDR));
	cardAcceptorDetail.setCity(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_CITY));
	cardAcceptorDetail.setState(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_STATE));
	cardAcceptorDetail.setPostalCode(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_POSTAL_CODE));
	cardAcceptorDetail.setCountryCode(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_COUNTRY_CODE));
	cardAcceptorDetail.setPhone(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_PHONE));
	cardAcceptorDetail.setCustomerServicePhone(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_CUST_SR_PHONE));
	cardAcceptorDetail.setAdditionalInfo(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_ADDITIONAL_INFO));
	cardAcceptorDetail.setUrl(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_URL));
	cardAcceptorDetail.setEmail(msg.getString(ISO8583_2003_SpecConstant.FIELD_CARD_ACCEPTOR_EMAIL));
	return cardAcceptorDetail;
    }

    private static List<IMFMessageErrorIndicator> getMessageErrorIndicatorForIMF(String msgErrIndFromIsoMsg) {
	if (msgErrIndFromIsoMsg != null) {
	    char[] allMsgErrInd = msgErrIndFromIsoMsg.toCharArray();
	    List<String> msgErrIndList = new ArrayList<String>();
	    StringBuffer MsgErr = null;
	    for (int i = 0; i < allMsgErrInd.length; i++) {
		if (MsgErr == null) {
		    MsgErr = new StringBuffer();
		}
		MsgErr.append(allMsgErrInd[i]);
		if ((i + 1) % 14 == 0) {
		    msgErrIndList.add(MsgErr.toString());
		    MsgErr = null;
		}
	    }
	    List<IMFMessageErrorIndicator> msgErrorIndList = new ArrayList<IMFMessageErrorIndicator>();
	    for (String error : msgErrIndList) {
		IMFMessageErrorIndicator imfMsgError = new IMFMessageErrorIndicator();
		Integer errorSeverityCode = Integer.valueOf(error.substring(0, 2));
		imfMsgError.setErrorSeverityCode(errorSeverityCode);
		Integer msgErrorCode = Integer.valueOf(error.substring(2, 6));
		imfMsgError.setMessageErrorCode(msgErrorCode);
		Integer dataElement = Integer.valueOf(error.substring(6, 9));
		imfMsgError.setDataElementInError(dataElement);
		Integer dataSubElement = Integer.valueOf(error.substring(9, 11));
		imfMsgError.setDataSubElementInError(dataSubElement);
		imfMsgError.setDatasetIdentifierInError(error.substring(11, 12));
		imfMsgError.setDataBitInError(error.substring(12));
		msgErrorIndList.add(imfMsgError);
	    }
	    return msgErrorIndList;
	}
	return null;
    }

    private static List<IMFAdditionalAmount> getAdditionalAmountListForIMF(ISOMsg isoMsg) {
	if (isoMsg != null) {
	    List<IMFAdditionalAmount> additionalAmountList = new ArrayList<IMFAdditionalAmount>();
	    IMFAdditionalAmount additionalAmount = new IMFAdditionalAmount();
	    String additionalAmountString = isoMsg.getString(ISO8583_2003_SpecConstant.FIELD_ADDITIONAL_AMOUNT);
	    if (additionalAmountString != null && additionalAmountString.trim().length() > 0) {
		int len = additionalAmountString.trim().length();
		int addAmtSingleBlockLen = IMFConstants.ADDITIONAL_AMOUNT_SINGLE_BLOCK_LEN;
		int aditionalAmoutpart = len / addAmtSingleBlockLen;
		int rem = len % addAmtSingleBlockLen;
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0, j = 1; i < aditionalAmoutpart; i++, j++) {
		    startIndex = addAmtSingleBlockLen * i;
		    endIndex = addAmtSingleBlockLen * j;
		    additionalAmount = getAdditionalAmountForIMF(additionalAmountString.substring(startIndex, endIndex));
		    additionalAmountList.add(additionalAmount);
		}
		if (rem != 0) {
		    additionalAmount = getAdditionalAmountForIMF(additionalAmountString.substring(endIndex, endIndex + rem));
		    additionalAmountList.add(additionalAmount);
		}
		return additionalAmountList;
	    }
	}
	return null;
    }

    private static IMFAdditionalAmount getAdditionalAmountForIMF(String additionalAmountString) {
	IMFAdditionalAmount additionalAmount = new IMFAdditionalAmount();
	final String AMOUNT_TYPE_ACC_LEDGER_BAL = "01";
	final String AMOUNT_TYPE_ACC_AVAILABLE_BAL = "02";
	final String AMOUNT_TYPE_DEST_ACC_LEDGER_BAL = "07";
	final String AMOUNT_TYPE_DEST_ACC_AVAILABLE_BAL = "08";
	final String AMOUNT_TYPE_AMT_REMAINING_THIS_CYCLE = "20";
	final String AMOUNT_TYPE_AMT_CASH = "40";
	final String AMOUNT_CREDIT = "C";
	final String AMOUNT_DEBIT = "D";
	if (additionalAmountString != null && additionalAmountString.trim().length() > 0) {
	    int len = additionalAmountString.trim().length();
	    String accountType;
	    String amountType;
	    int currencyMinorUnit;
	    if (len >= 2) {
		accountType = additionalAmountString.substring(0, 2);
		additionalAmount.setAccountType(accountType);
	    }
	    if (len >= 4) {
		amountType = additionalAmountString.substring(2, 4);
		if (AMOUNT_TYPE_ACC_LEDGER_BAL.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.ACCOUNT_LEDGER_BALANCE);
		} else if (AMOUNT_TYPE_ACC_AVAILABLE_BAL.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.ACCOUNT_AVAILABLE_BALANCE);
		} else if (AMOUNT_TYPE_DEST_ACC_LEDGER_BAL.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.DESTINATION_ACCOUNT_LEDGER_BALANCE);
		} else if (AMOUNT_TYPE_DEST_ACC_AVAILABLE_BAL.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.DESTINATION_ACCOUNT_AVAILABLE_BALANCE);
		} else if (AMOUNT_TYPE_AMT_REMAINING_THIS_CYCLE.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.AMOUNT_REMAINING_THIS_CYCLE);
		} else if (AMOUNT_TYPE_AMT_CASH.equals(amountType)) {
		    additionalAmount.setAmountType(IMFConstants.AMOUNT_TYPE.AMOUNT_CASH);
		}
	    }
	    if (len >= 7) {
		additionalAmount.setCurrency(additionalAmountString.substring(4, 7));
	    }
	    if (len >= 8) {
		currencyMinorUnit = Integer.parseInt(additionalAmountString.substring(7, 8));
		additionalAmount.setCurrencyMinorUnit(currencyMinorUnit);
	    }
	    if (len >= 9) {
		String amountSign = additionalAmountString.substring(8, 9);
		if (AMOUNT_CREDIT.equalsIgnoreCase(amountSign)) {
		    additionalAmount.setAmountSign(IMFConstants.AMOUNT_SIGN.C);
		} else if (AMOUNT_DEBIT.equalsIgnoreCase(amountSign)) {
		    additionalAmount.setAmountSign(IMFConstants.AMOUNT_SIGN.D);
		}
	    }
	    if (len >= 10) {
		if (len >= 21) {
		    BigDecimal decimalValue = new BigDecimal(additionalAmountString.substring(9, 21)).movePointLeft(2);
		    additionalAmount.setAmount(decimalValue.doubleValue());
		} else if (len < 21) {
		    BigDecimal decimalValue = new BigDecimal(additionalAmountString.substring(9, len)).movePointLeft(2);
		    additionalAmount.setAmount(decimalValue.doubleValue());
		}
	    }
	    return additionalAmount;
	}
	return null;
    }

}
