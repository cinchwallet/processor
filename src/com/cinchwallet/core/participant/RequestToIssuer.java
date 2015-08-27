package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.NameRegistrar;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.processor.communication.IServiceRequestor;
import com.cinchwallet.core.processor.communication.http.HTTPRequest;
import com.cinchwallet.core.processor.communication.http.HTTPResponse;
import com.cinchwallet.core.utils.CWLogger;


/**
 * @see TransactionParticipant
 * @see Configurable
 *
 */
public class RequestToIssuer implements TransactionParticipant, Configurable {

    private int              timeout;
    private IServiceRequestor serviceRequestor     = null;
    private String                      serviceRequestorName = null;

    public void abort(long id, Serializable ctx) {}

    public void commit(long arg0, Serializable ctx) {}

    @SuppressWarnings("unchecked")
    public int prepare(long arg0, Serializable ctx) {
	TransContext context = (TransContext) ctx;
	Serializable responseCP = null;
	HTTPResponse response;

	try {
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    // Prepare the appropriate request object based on the txn type.
	    context.getProcessorSpec().populateReversalRequest(context);
	    serviceRequestor = (IServiceRequestor) NameRegistrar.get(serviceRequestorName);
	    HTTPRequest request = (HTTPRequest) context.getProcessorRequest();
	    try {
		CWLogger.appLog.info("Request sent : "+request.getScriptName()+" - " + request.getRequestBody());
		responseCP = serviceRequestor.request(request, timeout);
		response = (HTTPResponse) responseCP;
		CWLogger.appLog.info("Response received : " + response.getResponseBody().toString());
		context.setProcessorResponse(response);
	    } catch (Exception _ex) {
		response = null;
		CWLogger.appLog.error("Uzai Processor failed to respond.");
		context.setIRC(IMFResponseCodes.PROCESSOR_NO_RESPONSE);
		return ABORTED | NO_JOIN;
	    }
	    return PREPARED | NO_JOIN;
	} catch (Exception e) {
	    CWLogger.appLog.error(e.getMessage(), e);
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    return ABORTED | NO_JOIN;
	}
    }

    @SuppressWarnings("unchecked")
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	timeout = cfg.getInt("response-timeout", 26000);
	serviceRequestorName = cfg.get("service-requestor");
    }
}