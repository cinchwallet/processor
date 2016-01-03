package com.cinchwallet.core.acquirer.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

/**
 * <code>AcquirerEnd</code> is the last participant in term of processing the
 * request. Till now, request has been processed by the processor and response
 * has been received by the switch from processor side.
 * <p>
 * Its main job is to prepare the AcquirerResponseIMF and AcquirerResponse.
 * AcquirerResponseIMF i.e. LEG_4 is prepared from ProcessorResponseIMF i.e.
 * LEG_3 by invoking <code>populateAcquirerRequestIMF()</code> method of Spec
 * class, and AcquirerResponse is prepared from the AcquirerResponseIMF.
 *
 * AcquirerResponse is object of ISOMsg, is the actual message which would be
 * sent to client
 * <p>
 * As AcquirerResponse is prepared in this participant, this is responsible to
 * handle the both success/fail scenario. Described above is the success
 * scenario where processor has responded from which, AcquirerResponse has been
 * prepared and after this, it would be send to client.
 * <p>
 * In case of any participant aborts or processor fails to respond, to handle
 * failure scenario and ensure to respond client in all cases,
 * <code>AcquirerEnd</code> also override the <code>prepareForAbort</code>
 * method, which is invoked in this case. In abort case system only prepare the
 * AcquirerResponse, which is sent to client.
 *
 * @see TransactionParticipant
 * @see Configurable
 *
 */
public class AcquirerEnd implements AbortParticipant, Configurable {

    public void abort(long id, Serializable ctx) {

    }

    public void commit(long id, Serializable context) {

    }

    /**
     * Prepares AcquirerResponseIMF i.e. LEG_4 from ProcessorResponseIMF i.e.
     * LEG_3. AcquirerResponseIMF, instance of RadicalMsg is now used to create
     * the Response in ISOMsg format to respond to client.
     *
     * @return int - execution status of the participant.
     */
    public int prepare(long id, Serializable ctx) {
	TransContext context = null;
	try {
	    context = (TransContext) ctx;
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    IAcquirerSpec spec = context.getAcquirerSpec();
	    spec.populateAcquirerResponseIMF(context);
	    //spec.populateAcquirerResponse(context);
	} catch (Exception ex) {
	    CWLogger.appLog.error(ex.toString(), ex);
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    return ABORTED | NO_JOIN;
	}
	return PREPARED | NO_JOIN;
    }

    public void setConfiguration(Configuration arg0) throws ConfigurationException {

    }

    /**
     * <code>prepareForAbort</code> is defined to handle the failure scenario
     * of the transaction. When any of the participant aborts, means
     * transaction could not be done successfully. This method prepare the
     * AcquirerResponse to send to client, to make sure that client get to know
     * the actual status of the transaction by looking at the response.
     *
     * @return int - execution status of the participant.
     */
    public int prepareForAbort(long id, Serializable context) {
	TransContext transContext = null;
	try {
	    transContext = (TransContext) context;
	    if(transContext.getAcquirerIMF()==null){
		transContext.getAcquirerSpec().populateAcquirerResponse(transContext);
	    }else{
		return prepare(id, context);
	    }
	} catch (Exception ex) {
	    CWLogger.appLog.error(ex.getMessage(), ex);
	}
	return PREPARED | NO_JOIN;
    }
}
