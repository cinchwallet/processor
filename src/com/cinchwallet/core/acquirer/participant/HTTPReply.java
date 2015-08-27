package com.cinchwallet.core.acquirer.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.AbortParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class HTTPReply implements AbortParticipant, Configurable {
    public int prepareForAbort(long id, Serializable context) {
	return prepare(id, context);
    }

    public void abort(long id, Serializable context) {

    }

    public void commit(long id, Serializable context) {

    }

    public int prepare(long id, Serializable ctx) {
	TransContext context = null;
	try {
	    context = (TransContext) ctx;
	    context.getAcquirerSpec().populateAcquirerResponse(context);
	} catch (Exception ex) {
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    CWLogger.appLog.error(ex.getMessage(), ex);
	    return ABORTED | NO_JOIN;
	} finally {
	    synchronized (context) {
		context.notify();
	    }
	}
	return PREPARED | NO_JOIN;

    }

    public void setConfiguration(Configuration arg0) throws ConfigurationException {

    }
}
