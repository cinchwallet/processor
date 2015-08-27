package com.cinchwallet.core.validation;

import java.io.Serializable;



/**
 * The <code>RequestPojo</code> represent the java object for
 * <code>ISOMsg</code>. Its scope is limited to Validation Framework. ISOMsg
 * received get converted to RequestPojo, over which entire validation rules are
 * applied.
 *
 * Member variable of RadicalPojo is mapped in isomessagemapping.properties
 * against the field number of the <code>ISOMsg<code> as shown below:
 *
 * <p>
 * <blockquote>
 * <pre>
 * 0=mti - 0th field of ISOMsg is mapped with the mti of RequestPojo
 * 2=pan - 2nd field of ISOMsg is mapped with the pan of RequestPojo
 * </pre>
 * </blockquote>
 * </p>
 * If any new field needs to be declared, mapping should be made into
 * isomessagemapping.properties as follow:
 * <p>
 * 150=myField, and a member variable with name myField should be created in
 * RequestPojo.
 *
 *
 *
 */
public class RequestPojo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            mti;
    private String            pan;
    private String            processingCode;
    private String            amountTransaction;
    private String            transmissionDate;
    private String            stan;
    private String            transmissionDateLocal;
    private String            effectiveDate;
    private String            expiryDate;
    private String            captureDate;
    private String            messageErrorIndicator;
    private String            posDataCode;
    private String            functionCode;
    private String            reasonCode;
    private String            merchantCategoryCode;
    private String            acquiringInstitutionIdentificationCode;
    private String            track2Data;
    private String            rrn;
    private String            approvalCode;
    private String            resultCode;
    private String            tid;
    private String            mid;
    private String            cardAcceptorNameLocation;
    private String            cardAcceptorName;
    private String            cardAcceptorAddr;
    private String            cardAcceptorCity;
    private String            cardAcceptorState;
    private String            cardAcceptorPostalCode;
    private String            cardAcceptorCountryCode;
    private String            cardAcceptorPhone;
    private String            cardAcceptorCustSrPhone;
    private String            cardAcceptorAdditionalInfo;
    private String            cardAcceptorUrl;
    private String            cardAcceptorEmail;
    private String            track1Data;
    private String            additionalAmount;
    private String            upc;
    private String            displayMessage;
    private String            oneincNumber;

    public String getMti() {
        return mti;
    }
    public void setMti(String mti) {
        this.mti = mti;
    }
    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getProcessingCode() {
        return processingCode;
    }
    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }
    public String getAmountTransaction() {
        return amountTransaction;
    }
    public void setAmountTransaction(String amountTransaction) {
        this.amountTransaction = amountTransaction;
    }
    public String getTransmissionDate() {
        return transmissionDate;
    }
    public void setTransmissionDate(String transmissionDate) {
        this.transmissionDate = transmissionDate;
    }
    public String getStan() {
        return stan;
    }
    public void setStan(String stan) {
        this.stan = stan;
    }
    public String getTransmissionDateLocal() {
        return transmissionDateLocal;
    }
    public void setTransmissionDateLocal(String transmissionDateLocal) {
        this.transmissionDateLocal = transmissionDateLocal;
    }
    public String getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getCaptureDate() {
        return captureDate;
    }
    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }
    public String getMessageErrorIndicator() {
        return messageErrorIndicator;
    }
    public void setMessageErrorIndicator(String messageErrorIndicator) {
        this.messageErrorIndicator = messageErrorIndicator;
    }
    public String getPosDataCode() {
        return posDataCode;
    }
    public void setPosDataCode(String posDataCode) {
        this.posDataCode = posDataCode;
    }
    public String getFunctionCode() {
        return functionCode;
    }
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
    public String getReasonCode() {
        return reasonCode;
    }
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }
    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }
    public String getAcquiringInstitutionIdentificationCode() {
        return acquiringInstitutionIdentificationCode;
    }
    public void setAcquiringInstitutionIdentificationCode(String acquiringInstitutionIdentificationCode) {
        this.acquiringInstitutionIdentificationCode = acquiringInstitutionIdentificationCode;
    }
    public String getTrack2Data() {
        return track2Data;
    }
    public void setTrack2Data(String track2Data) {
        this.track2Data = track2Data;
    }
    public String getRrn() {
        return rrn;
    }
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }
    public String getApprovalCode() {
        return approvalCode;
    }
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getTid() {
        return tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getCardAcceptorNameLocation() {
        return cardAcceptorNameLocation;
    }
    public void setCardAcceptorNameLocation(String cardAcceptorNameLocation) {
        this.cardAcceptorNameLocation = cardAcceptorNameLocation;
    }
    public String getCardAcceptorName() {
        return cardAcceptorName;
    }
    public void setCardAcceptorName(String cardAcceptorName) {
        this.cardAcceptorName = cardAcceptorName;
    }
    public String getCardAcceptorAddr() {
        return cardAcceptorAddr;
    }
    public void setCardAcceptorAddr(String cardAcceptorAddr) {
        this.cardAcceptorAddr = cardAcceptorAddr;
    }
    public String getCardAcceptorCity() {
        return cardAcceptorCity;
    }
    public void setCardAcceptorCity(String cardAcceptorCity) {
        this.cardAcceptorCity = cardAcceptorCity;
    }
    public String getCardAcceptorState() {
        return cardAcceptorState;
    }
    public void setCardAcceptorState(String cardAcceptorState) {
        this.cardAcceptorState = cardAcceptorState;
    }
    public String getCardAcceptorPostalCode() {
        return cardAcceptorPostalCode;
    }
    public void setCardAcceptorPostalCode(String cardAcceptorPostalCode) {
        this.cardAcceptorPostalCode = cardAcceptorPostalCode;
    }
    public String getCardAcceptorCountryCode() {
        return cardAcceptorCountryCode;
    }
    public void setCardAcceptorCountryCode(String cardAcceptorCountryCode) {
        this.cardAcceptorCountryCode = cardAcceptorCountryCode;
    }
    public String getCardAcceptorPhone() {
        return cardAcceptorPhone;
    }
    public void setCardAcceptorPhone(String cardAcceptorPhone) {
        this.cardAcceptorPhone = cardAcceptorPhone;
    }
    public String getCardAcceptorCustSrPhone() {
        return cardAcceptorCustSrPhone;
    }
    public void setCardAcceptorCustSrPhone(String cardAcceptorCustSrPhone) {
        this.cardAcceptorCustSrPhone = cardAcceptorCustSrPhone;
    }
    public String getCardAcceptorAdditionalInfo() {
        return cardAcceptorAdditionalInfo;
    }
    public void setCardAcceptorAdditionalInfo(String cardAcceptorAdditionalInfo) {
        this.cardAcceptorAdditionalInfo = cardAcceptorAdditionalInfo;
    }
    public String getCardAcceptorUrl() {
        return cardAcceptorUrl;
    }
    public void setCardAcceptorUrl(String cardAcceptorUrl) {
        this.cardAcceptorUrl = cardAcceptorUrl;
    }
    public String getCardAcceptorEmail() {
        return cardAcceptorEmail;
    }
    public void setCardAcceptorEmail(String cardAcceptorEmail) {
        this.cardAcceptorEmail = cardAcceptorEmail;
    }
    public String getTrack1Data() {
        return track1Data;
    }
    public void setTrack1Data(String track1Data) {
        this.track1Data = track1Data;
    }
    public String getAdditionalAmount() {
        return additionalAmount;
    }
    public void setAdditionalAmount(String additionalAmount) {
        this.additionalAmount = additionalAmount;
    }
    public String getUpc() {
        return upc;
    }
    public void setUpc(String upc) {
        this.upc = upc;
    }
    public String getDisplayMessage() {
        return displayMessage;
    }
    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
    public String getOneincNumber() {
        return oneincNumber;
    }
    public void setOneincNumber(String oneincNumber) {
        this.oneincNumber = oneincNumber;
    }



}