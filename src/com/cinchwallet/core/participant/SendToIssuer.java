package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.NameRegistrar;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.IProcessorSpecification;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.processor.communication.IServiceRequestor;
import com.cinchwallet.core.processor.communication.http.HTTPRequest;
import com.cinchwallet.core.processor.communication.http.HTTPResponse;
import com.cinchwallet.core.utils.CWLogger;


/**
 * @see TransactionParticipant
 * @see Configurable
 *
 */
public class SendToIssuer implements TransactionParticipant, Configurable {

    private int                         timeout;
    private IServiceRequestor           serviceRequestor     = null;
    private String                      serviceRequestorName = null;
    private String                      safQueue             = null;
    private Space<String, TransContext> space;
    private IProcessorSpecification issuerSpec;

    public void abort(long id, Serializable ctx) {}

    public void commit(long arg0, Serializable ctx) {}

    @SuppressWarnings("unchecked")
    public int prepare(long arg0, Serializable ctx) {
	TransContext context = (TransContext) ctx;
	Serializable responseCP = null;
	HTTPResponse response;
	try {
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    //context.getProcessorSpec().populateProcessorRequest(context);
	    issuerSpec.populateProcessorRequest(context);
	    serviceRequestor = (IServiceRequestor) NameRegistrar.get(serviceRequestorName);
	    HTTPRequest request = (HTTPRequest) context.getProcessorRequest();
	    try {
		CWLogger.appLog.info("Request sent : " + request.getScriptName() + " - " + request.getRequestBody());
		// Get the response from PiaoWuTong
		responseCP = serviceRequestor.request(request, timeout);
		response = (HTTPResponse) responseCP;
		CWLogger.appLog.info("Response received : " + response.getResponseBody().toString());
		context.setProcessorResponse(response);

		//capturing issuer fields
		issuerSpec.populateProcessorResponseIMF(context);
	    } catch (Exception _ex) {
		CWLogger.appLog.error("Issuer failed to respond.");
		context.setIRC(IMFResponseCodes.PROCESSOR_NO_RESPONSE);
		TxnMsg aquirerRequestIMF = context.getAcquirerIMF();
		if (TransactionType.isReversalAllowed(aquirerRequestIMF.getTransactionTypeEnum())) {
		    space.out(safQueue, createReversalTransactionContext(context));
		}

		return ABORTED | NO_JOIN;
	    }
	} catch (Exception e) {
	    CWLogger.appLog.error(e.getMessage(), e);
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    return ABORTED | NO_JOIN;
	}
	return PREPARED | NO_JOIN;
    }

    @SuppressWarnings("unchecked")
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	timeout = cfg.getInt("response-timeout", 26000);
	serviceRequestorName = cfg.get("service-requestor");
	space = SpaceFactory.getSpace("processor-space");
	safQueue = cfg.get("reversal-queue");

	String specificationClassName = cfg.get("issuer-specification-class");
	if (specificationClassName != null && specificationClassName.length() > 0) {
	    try {
		issuerSpec = (IProcessorSpecification) Class.forName(specificationClassName).newInstance();
	    } catch (Exception _ex) {
		throw new ConfigurationException(_ex.getMessage(), _ex);
	    }
	}


    }

    private TransContext createReversalTransactionContext(TransContext context) {
	TransContext reversalTansactionContext = new TransContext();
	reversalTansactionContext.setIRC(context.getIRC());
	reversalTansactionContext.setProcessorSpec(context.getProcessorSpec());
	//reversalTansactionContext.setProcessorIMF(context.getProcessorIMF().cloneRadicalMsg());
	//reversalTansactionContext.getProcessorIMF().setOrigUid(new Long(context.getProcessorIMF().getTxnId()));
	reversalTansactionContext.getProcessorIMF().setTransactionType(TransactionType.RVSAL.toString());
	//reversalTansactionContext.getProcessorIMF().setOriginalKargoTxnID(context.getProcessorIMF().getTxnId());
	reversalTansactionContext.setProcessorRequest(context.getProcessorRequest());
	return reversalTansactionContext;
    }

}