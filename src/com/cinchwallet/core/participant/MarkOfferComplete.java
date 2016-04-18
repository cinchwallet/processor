package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.NameRegistrar;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.processor.communication.IServiceRequestor;
import com.cinchwallet.core.processor.communication.http.HTTPRequest;
import com.cinchwallet.core.processor.communication.http.HTTPResponse;
import com.cinchwallet.core.processor.communication.http.HttpMethods;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.issuer.msg.IssuerConstant;

/**
 * @see TransactionParticipant
 * @see Configurable
 * 
 */
public class MarkOfferComplete implements TransactionParticipant, Configurable {

	private int timeout;
	private IServiceRequestor serviceRequestor = null;
	private String serviceRequestorName = null;

	public void abort(long id, Serializable ctx) {
	}

	public void commit(long arg0, Serializable ctx) {
	}

	public int prepare(long arg0, Serializable ctx) {
		TransContext context = (TransContext) ctx;
		Serializable responseCP = null;
		HTTPResponse response;
		try {
			TxnMsg txnMsg = context.getAcquirerIMF();
			if (txnMsg != null && txnMsg.getPromoCode() == null)
				return PREPARED | NO_JOIN;
			
			context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
			serviceRequestor = (IServiceRequestor) NameRegistrar.get(serviceRequestorName);

			try {
				HTTPRequest httpRequest = new HTTPRequest();
				String request = createRequest(context.getAcquirerIMF());
				httpRequest.setScriptName(IssuerConstant.TxnType.OFFER_COMPLETE.getEndPoint());
				httpRequest.setRequestBody(request);
				httpRequest.setMethod(HttpMethods.JSONOVERHTTP);
				CWLogger.cwIssuerLog.info("Request sent : " + httpRequest.getScriptName() + " - " + httpRequest.getRequestBody());
				responseCP = serviceRequestor.request(httpRequest, timeout);
				// responseCP = getLocalResponse();
				response = (HTTPResponse) responseCP;
				CWLogger.cwIssuerLog.info("Response received : " + response.getResponseBody().toString());
			} catch (Exception _ex) {
				CWLogger.cwIssuerLog.error("Issuer failed to respond.");
				context.setIRC(IMFResponseCodes.PROCESSOR_NO_RESPONSE);
				return ABORTED | NO_JOIN;
			}
		} catch (Exception e) {
			CWLogger.cwIssuerLog.error(e.getMessage(), e);
			context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
			return ABORTED | NO_JOIN;
		}
		return PREPARED | NO_JOIN;
	}

	public void setConfiguration(Configuration cfg) throws ConfigurationException {
		timeout = cfg.getInt("response-timeout", 26000);
		serviceRequestorName = cfg.get("service-requestor");
	}
	
	private String createRequest(TxnMsg processorRequestIMF) {
		StringBuilder request = new StringBuilder();
		request.append("{\"txnId\":\"").append(processorRequestIMF.getTxnId()).append("\"");
		if(processorRequestIMF.getCardholder()!=null && processorRequestIMF.getCardholder().getMembershipId()!=null){
			request.append(",\"membershipId\":\"").append(processorRequestIMF.getCardholder().getMembershipId()).append("\"");
		}
		if(processorRequestIMF.getPromoCode()!=null){
			request.append(",\"promoCode\":\"").append(processorRequestIMF.getPromoCode()).append("\"");	
		}
		request.append("}");
		return request.toString();
	}


}