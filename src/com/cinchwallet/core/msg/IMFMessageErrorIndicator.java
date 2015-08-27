package com.cinchwallet.core.msg;

public class IMFMessageErrorIndicator {

    private Integer errorSeverityCode;
    private Integer messageErrorCode;
    private Integer dataElementInError;
    private Integer dataSubElementInError;
    private String  datasetIdentifierInError;
    private String  dataBitInError;

    public IMFMessageErrorIndicator() {

    }

    public IMFMessageErrorIndicator(Integer errorSeverityCode, Integer messageErrorCode, Integer dataElementInError, Integer dataSubElementInError, String datasetIdentifierInError, String dataBitInError) {
	this.errorSeverityCode = errorSeverityCode;
	this.messageErrorCode = messageErrorCode;
	this.dataElementInError = dataElementInError;
	this.dataSubElementInError = dataSubElementInError;
	this.datasetIdentifierInError = datasetIdentifierInError;
	this.dataBitInError = dataBitInError;
    }

    /**
     * @return the errorSeverityCode
     */
    public Integer getErrorSeverityCode() {
	return errorSeverityCode;
    }

    /**
     * @param pErrorSeverityCode the errorSeverityCode to set
     */
    public void setErrorSeverityCode(Integer pErrorSeverityCode) {
	errorSeverityCode = pErrorSeverityCode;
    }

    /**
     * @return the messageErrorCode
     */
    public Integer getMessageErrorCode() {
	return messageErrorCode;
    }

    /**
     * @param pMessageErrorCode the messageErrorCode to set
     */
    public void setMessageErrorCode(Integer pMessageErrorCode) {
	messageErrorCode = pMessageErrorCode;
    }

    /**
     * @return the dataElementInError
     */
    public Integer getDataElementInError() {
	return dataElementInError;
    }

    /**
     * @param pDataElementInError the dataElementInError to set
     */
    public void setDataElementInError(Integer pDataElementInError) {
	dataElementInError = pDataElementInError;
    }

    /**
     * @return the dataSubElementInError
     */
    public Integer getDataSubElementInError() {
	return dataSubElementInError;
    }

    /**
     * @param pDataSubElementInError the dataSubElementInError to set
     */
    public void setDataSubElementInError(Integer pDataSubElementInError) {
	dataSubElementInError = pDataSubElementInError;
    }

    /**
     * @return the datasetIdentifierInError
     */
    public String getDatasetIdentifierInError() {
	return datasetIdentifierInError;
    }

    /**
     * @param pDatasetIdentifierInError the datasetIdentifierInError to set
     */
    public void setDatasetIdentifierInError(String pDatasetIdentifierInError) {
	datasetIdentifierInError = pDatasetIdentifierInError;
    }

    /**
     * @return the dataBitInError
     */
    public String getDataBitInError() {
	return dataBitInError;
    }

    /**
     * @param pDataBitInError the dataBitInError to set
     */
    public void setDataBitInError(String pDataBitInError) {
	dataBitInError = pDataBitInError;
    }

    public String toDBString() {
	StringBuffer dbString = new StringBuffer();

	dbString.append("errorSeverityCode:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.errorSeverityCode);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("messageErrorCode:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.messageErrorCode);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("dataElementInError:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.dataElementInError);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("dataSubElementInError:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.dataSubElementInError);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("datasetIdentifierInError:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.datasetIdentifierInError);
	else
	    dbString.append(IMFConstants.BLANK);
	dbString.append(IMFConstants.PROPERTY_DELIMITER);

	dbString.append("dataBitInError:");
	if (this.errorSeverityCode != null)
	    dbString.append(this.dataBitInError);
	else
	    dbString.append(IMFConstants.BLANK);

	return dbString.toString();
    }

    @Override
    public String toString() {
	return "[" + toDBString() + "]";
    }

}
