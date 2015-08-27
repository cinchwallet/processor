package com.cinchwallet.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cinchwallet.acquirer.http.msg.TxnHistory;
import com.cinchwallet.core.dao.TxnLogDao;
import com.cinchwallet.core.msg.IMFAdditionalAmount;
import com.cinchwallet.core.msg.IMFAmount;
import com.cinchwallet.core.msg.IMFCardAcceptorDetail;
import com.cinchwallet.core.msg.IMFConstants;
import com.cinchwallet.core.msg.IMFMessageErrorIndicator;
import com.cinchwallet.core.msg.TransactionType;

/**
 * <code>RadicalMsg</code> encapsulate the entire data used to perform a
 * transaction. It is a simple java object which represents the entire data for
 * a transaction in the application.
 * <p>
 * The <code>RadicalMsg</code> represents the internal message format of the
 * <code>ISOMsg</code>. ISOMsg received, got converted to RadicalMsg, which then
 * flows through the entire transaction cycle.
 * <p>
 * Instance of this class is used to create the AcquirerRequest/Response and
 * ProcessorRequest/Response and is prepared from the ISOMsg.
 **/
public class TxnMsg extends TxnLogDao implements Serializable {

	private static final long serialVersionUID = -6314085866899227384L;
	private String cardPin;
	private String originalTransType;
	private String originalMti;
	private String transSource;
	private String merchantKeyId;
	private String merchantKey;
	private String simulateReversal = "false";
	private String did;


	private List<IMFAdditionalAmount> additionalAmountList;
	private IMFCardAcceptorDetail cardAcceptorNameLocation;
	private IMFAmount transactionAmount;
	private List<IMFMessageErrorIndicator> messageErrorIndicator;
	private String track2Data;
	private String track1Data;
	private byte[] pinData;
	private String dateSettlement;
	private String addtionalResponseData;
	private String recevingInstIdCode;
	private String sourceType;
	private String reserveFieldData;
	private String cardIssuerRefData;
	private String couponCode;
	private List<TxnHistory> txnList;
	private byte[] header = null;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getSimulateReversal() {
		return simulateReversal;
	}

	public void setSimulateReversal(String simulateReversal) {
		this.simulateReversal = simulateReversal;
	}

	public String getOriginalMti() {
		return originalMti;
	}

	public void setOriginalMti(String originalMti) {
		this.originalMti = originalMti;
	}

	public String getOriginalTransType() {
		return originalTransType;
	}

	public void setOriginalTransType(String originalTransType) {
		this.originalTransType = originalTransType;
	}

	public String getCardPin() {
		return cardPin;
	}

	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}



	public String getTransSource() {
		return transSource;
	}

	public void setTransSource(String transSource) {
		this.transSource = transSource;
	}

	public String getMerchantKeyId() {
		return merchantKeyId;
	}

	public void setMerchantKeyId(String merchantKeyId) {
		this.merchantKeyId = merchantKeyId;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public List<IMFAdditionalAmount> getAdditionalAmountList() {
		return additionalAmountList;
	}

	public void setAdditionalAmountList(
			List<IMFAdditionalAmount> p_additionalAmountList) {
		additionalAmountList = p_additionalAmountList;
		if (p_additionalAmountList != null) {
			super.setAdditionalAmount(getDBString(p_additionalAmountList));
		}
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(super.toString());
		strBuf.append(",transSource=" + transSource);
		strBuf.append(",merchantKey=" + merchantKey);
		strBuf.append(",merchantKeyId=" + merchantKeyId);
		return strBuf.toString();
	}


	public IMFCardAcceptorDetail getCardAcceptorNameLocation() {
		return cardAcceptorNameLocation;
	}

	public void setCardAcceptorNameLocation(
			IMFCardAcceptorDetail cardAcceptorNameLocation) {
		this.cardAcceptorNameLocation = cardAcceptorNameLocation;
		if (cardAcceptorNameLocation != null) {
			super.setMidNameLocation(cardAcceptorNameLocation.toDBString());
		}
	}

	public IMFAmount getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(IMFAmount transactionAmount) {
		this.transactionAmount = transactionAmount;
		if (transactionAmount != null) {
			super.setTxnAmount(transactionAmount.getAmount().doubleValue());
			super.setTxnCurrency(transactionAmount.getCurrency());
			super.setTxnAmountScale(transactionAmount.getAmountScale());
		}
	}

	public List<IMFMessageErrorIndicator> getMessageErrorIndicator() {
		return messageErrorIndicator;
	}

	public void setMessageErrorIndicator(
			List<IMFMessageErrorIndicator> messageErrorIndicator) {
		this.messageErrorIndicator = messageErrorIndicator;
		if (messageErrorIndicator != null) {
			super.setMsgEerrIndicator(getDBString(messageErrorIndicator));
		}
	}

	public void addMessageErrorIndicator(IMFMessageErrorIndicator mei) {
		if (mei != null) {
			if (messageErrorIndicator == null) {
				messageErrorIndicator = new ArrayList<IMFMessageErrorIndicator>();
			}
			messageErrorIndicator.add(mei);
			super.setMsgEerrIndicator(getDBString(messageErrorIndicator));
		}
	}


	public String getTrack2Data() {
		return track2Data;
	}

	public void setTrack2Data(String track2Data) {
		this.track2Data = track2Data;
	}

	public String getTrack1Data() {
		return track1Data;
	}

	public void setTrack1Data(String track1Data) {
		this.track1Data = track1Data;
	}

	public byte[] getPinData() {
		return pinData;
	}

	public void setPinData(byte[] pinData) {
		this.pinData = pinData;
	}

	public String getDateSettlement() {
		return dateSettlement;
	}

	public void setDateSettlement(String dateSettlement) {
		this.dateSettlement = dateSettlement;
	}

	public String getAddtionalResponseData() {
		return addtionalResponseData;
	}

	public void setAddtionalResponseData(String addtionalResponseData) {
		this.addtionalResponseData = addtionalResponseData;
	}

	public String getRecevingInstIdCode() {
		return recevingInstIdCode;
	}

	public void setRecevingInstIdCode(String recevingInstIdCode) {
		this.recevingInstIdCode = recevingInstIdCode;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getReserveFieldData() {
		return reserveFieldData;
	}

	public void setReserveFieldData(String reserveFieldData) {
		this.reserveFieldData = reserveFieldData;
	}

	public String getCardIssuerRefData() {
		return cardIssuerRefData;
	}

	public void setCardIssuerRefData(String cardIssuerRefData) {
		this.cardIssuerRefData = cardIssuerRefData;
	}

	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}



	@SuppressWarnings("unchecked")
	private String getDBString(Object obj) {
		StringBuffer bufferDBString = new StringBuffer();
		bufferDBString.append(IMFConstants.COLL_START);
		for (Iterator iterator = ((Collection) obj).iterator(); iterator
				.hasNext();) {
			Object imfObject = iterator.next();
			bufferDBString.append(imfObject.toString());
		}
		bufferDBString.append(IMFConstants.COLL_END);
		return bufferDBString.toString();
	}




	public String getCouponCode() {
            return couponCode;
        }

	public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

	public TxnMsg cloneMsg() {
		TxnMsg response = new TxnMsg();
		// if (this.getOltpTxnLogId() != null)
		// response.setOltpTxnLogId(new Long(this.getOltpTxnLogId()));
		if (this.getTxnId() != null)
			response.setTxnId(new String(this.getTxnId()));
		// if (this.getLeg() != null)
		// response.setLeg(new String(this.getLeg()));
		if (this.getMti() != null)
			response.setMti(new String(this.getMti()));
		if (this.getPan() != null)
			response.setPan(new String(this.getPan()));
		if (this.getProcCcode() != null)
			response.setProcCcode(new String(this.getProcCcode()));
		if (this.getTxnAmount() != null)
			response.setTxnAmount(new Double(this.getTxnAmount()));
		if (this.getTxnAmountScale() != null)
			response.setTxnAmountScale(this.getTxnAmountScale());
		if (this.getTxnCurrency() != null)
			response.setTxnCurrency(new String(this.getTxnCurrency()));
		if (this.getDtTransmission() != null)
			response.setDtTransmission(new Date(this.getDtTransmission()
					.getTime()));
		if (this.getStan() != null)
			response.setStan(new String(this.getStan()));
		if (this.getDtTransaction() != null)
			response.setDtTransaction(new Date(this.getDtTransaction()
					.getTime()));
		if (this.getDtEffective() != null)
			response.setDtEffective(new String(this.getDtEffective()));
		if (this.getDtExpiry() != null)
			response.setDtExpiry(new String(this.getDtExpiry()));
		if (this.getDtCapture() != null)
			response.setDtCapture(new String(this.getDtCapture()));
		if (this.getTrack2Data() != null)
			response.setTrack2Data(new String(this.getTrack2Data()));

		if (this.getMsgEerrIndicator() != null)
			response
					.setMsgEerrIndicator(new String(this.getMsgEerrIndicator()));
		if (this.getTxnLifeCycleId() != null)
			response.setTxnLifeCycleId(new String(this.getTxnLifeCycleId()));
		if (this.getPosDataCd() != null) {
			byte[] temp = new byte[this.getPosDataCd().length];
			System.arraycopy(this.getPosDataCd(), 0, temp, 0, this
					.getPosDataCd().length);
			response.setPosDataCd(temp);
		}
		if (this.getFunctionCode() != null)
			response.setFunctionCode(new String(this.getFunctionCode()));
		if (this.getReasonCode() != null)
			response.setReasonCode(new String(this.getReasonCode()));
		if (this.getMerchantCatCode() != null)
			response.setMerchantCatCode(new String(this.getMerchantCatCode()));
		if (this.getAcqInstitutionIdCd() != null)
			response.setAcqInstitutionIdCd(new String(this
					.getAcqInstitutionIdCd()));
		if (this.getRrn() != null)
			response.setRrn(new String(this.getRrn()));
		/*
		 * if (this.getApprovalCd() != null) response.setApprovalCd(new
		 * String(this.getApprovalCd())); if (this.getResultCd() != null)
		 * response.setResultCd(new String(this.getResultCd()));
		 */
		if (this.getTid() != null)
			response.setTid(new String(this.getTid()));
		if (this.getMid() != null)
			response.setMid(new String(this.getMid()));
		if (this.getMidNameLocation() != null)
			response.setMidNameLocation(new String(this.getMidNameLocation()));
		if (this.getAdditionalAmount() != null)
			response
					.setAdditionalAmount(new String(this.getAdditionalAmount()));
		if (this.getDisplayMessage() != null)
			response.setDisplayMessage(new String(this.getDisplayMessage()));
		if (this.getUpc() != null)
			response.setUpc(new String(this.getUpc()));
		if (this.getIRC() != null)
			response.setIRC(new String(this.getIRC()));
		if (this.getSpecificationName() != null)
			response.setSpecificationName(new String(this
					.getSpecificationName()));
		if (this.getTransactionType() != null)
			response.setTransactionType(new String(this.getTransactionType()));
		if (this.getCreationTs() != null)
			response.setCreationTs(new Date(this.getCreationTs().getTime()));

		if (this.getCardAcceptorNameLocation() != null) {
			IMFCardAcceptorDetail imfCardAcceptorDetail = new IMFCardAcceptorDetail();
			if (this.getCardAcceptorNameLocation().getName() != null)
				imfCardAcceptorDetail.setName(new String(this
						.getCardAcceptorNameLocation().getName()));
			if (this.getCardAcceptorNameLocation().getAddress() != null)
				imfCardAcceptorDetail.setAddress(new String(this
						.getCardAcceptorNameLocation().getAddress()));
			if (this.getCardAcceptorNameLocation().getCity() != null)
				imfCardAcceptorDetail.setCity(new String(this
						.getCardAcceptorNameLocation().getCity()));
			if (this.getCardAcceptorNameLocation().getState() != null)
				imfCardAcceptorDetail.setState(new String(this
						.getCardAcceptorNameLocation().getState()));
			if (this.getCardAcceptorNameLocation().getPostalCode() != null)
				imfCardAcceptorDetail.setPostalCode(new String(this
						.getCardAcceptorNameLocation().getPostalCode()));
			if (this.getCardAcceptorNameLocation().getCountryCode() != null)
				imfCardAcceptorDetail.setCountryCode(new String(this
						.getCardAcceptorNameLocation().getCountryCode()));
			if (this.getCardAcceptorNameLocation().getPhone() != null)
				imfCardAcceptorDetail.setPhone(new String(this
						.getCardAcceptorNameLocation().getPhone()));
			if (this.getCardAcceptorNameLocation().getCustomerServicePhone() != null)
				imfCardAcceptorDetail.setCustomerServicePhone(new String(this
						.getCardAcceptorNameLocation()
						.getCustomerServicePhone()));
			if (this.getCardAcceptorNameLocation().getAdditionalInfo() != null)
				imfCardAcceptorDetail.setAdditionalInfo(new String(this
						.getCardAcceptorNameLocation().getAdditionalInfo()));
			if (this.getCardAcceptorNameLocation().getUrl() != null)
				imfCardAcceptorDetail.setUrl(new String(this
						.getCardAcceptorNameLocation().getUrl()));
			if (this.getCardAcceptorNameLocation().getEmail() != null)
				imfCardAcceptorDetail.setEmail(new String(this
						.getCardAcceptorNameLocation().getEmail()));

			response.setCardAcceptorNameLocation(imfCardAcceptorDetail);
		}
		if (this.getAdditionalAmountList() != null) {
			List<IMFAdditionalAmount> listAdditionalAmount = new ArrayList<IMFAdditionalAmount>();
			for (IMFAdditionalAmount additionalAmount : this
					.getAdditionalAmountList()) {
				if (additionalAmount != null) {
					IMFAdditionalAmount imfAdditionalAmount = new IMFAdditionalAmount();
					if (additionalAmount.getAmountType() != null)
						imfAdditionalAmount.setAmountType(additionalAmount
								.getAmountType());
					if (additionalAmount.getAccountType() != null)
						imfAdditionalAmount.setAccountType(new String(
								additionalAmount.getAccountType()));
					if (additionalAmount.getCurrency() != null)
						imfAdditionalAmount.setCurrency(new String(
								additionalAmount.getCurrency()));
					if (additionalAmount.getCurrencyMinorUnit() != null)
						imfAdditionalAmount.setCurrencyMinorUnit(new Integer(
								additionalAmount.getCurrencyMinorUnit()));
					if (additionalAmount.getAmountSign() != null)
						imfAdditionalAmount.setAmountSign(additionalAmount
								.getAmountSign());
					if (additionalAmount.getAmount() != null)
						imfAdditionalAmount.setAmount(new Double(
								additionalAmount.getAmount()));
					listAdditionalAmount.add(imfAdditionalAmount);
				}
			}
			response.setAdditionalAmountList(listAdditionalAmount);
		}
		if (this.getTransactionAmount() != null) {
			IMFAmount amount = new IMFAmount();
			if (this.getTransactionAmount().getCurrency() != null)
				amount.setCurrency(new String(this.getTransactionAmount()
						.getCurrency()));
			if (this.getTransactionAmount().getAmount() != null)
				amount.setAmount(this.getTransactionAmount().getAmount());
			if (this.getTransactionAmount().getAmountScale() != null)
				amount.setAmountScale(this.getTransactionAmount()
						.getAmountScale());
			if (this.getTransactionAmount().getAmountString() != null)
				amount.setAmountString(new String(this.getTransactionAmount()
						.getAmountString()));
			response.setTransactionAmount(amount);
		}
		return response;
	}


	public TransactionType getTransactionTypeEnum() {
		return TransactionType.valueOf(super.getTransactionType());
	}

	public List<TxnHistory> getTxnList() {
            return txnList;
        }

	public void setTxnList(List<TxnHistory> txnList) {
            this.txnList = txnList;
        }



}
