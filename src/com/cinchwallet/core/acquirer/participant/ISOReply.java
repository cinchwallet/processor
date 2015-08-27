package com.cinchwallet.core.acquirer.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.AbortParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

/**
 * The <code>ISOReply</code> acts as sender of the response to the
 * client/source. Context object stores the source, from where request has been
 * sent and the ISO response need to be sent to source. <code>ISOReply</code>
 * retrieves these two information and sends the ISO Response to the source.
 *
 * Its job is to reply to client, so it must also handle the failure scenario.
 * To do so, it override <code>prepareForAbort</code> method, where
 * <code>prepare</code> method is called which contains the piece of code to
 * send the response to client.
 *
 * @see AbortParticipant
 * @see Configurable
 *
 */
public class ISOReply implements AbortParticipant, Configurable {

    /**
     * <code>prepareForAbort</code> is defined to handle the failure scenario
     * of the transaction. When any of the participant aborts, means transaction
     * is not successful. Invoke <code>prepare</code> method internally, which
     * replies to client.
     *
     * @return int - execution status of the participant.
     */
    public int prepareForAbort(long id, Serializable context) {
	return prepare(id, context);
    }

    public void abort(long id, Serializable context) {

    }

    public void commit(long id, Serializable context) {

    }

    /**
     * Get the source and ISO Response from the context object and sends the
     * response to client/source.
     *
     * @return int - execution status of the participant.
     */
    public int prepare(long id, Serializable ctx) {
	int status = PREPARED | NO_JOIN;
	TransContext context = null;
	try {
	    context = (TransContext) ctx;
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    ISOChannel source = (ISOChannel) context.getSource();
	    source.send((ISOMsg) context.getAcquirerResponse());
	} catch (Exception ex) {
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    CWLogger.appLog.error(ex.getMessage(), ex);
	    return ABORTED | NO_JOIN;
	}
	return status;
    }

    public void setConfiguration(Configuration arg0) throws ConfigurationException {

    }
}
