package com.cinchwallet.core.listener;

import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.ReConfigurable;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.Utils;

/**
 *
 * The <code>AcquirerISOListener</code> class receives the ISORequest from the
 * Channel class. All the Transaction Request is passed through this Listener,
 * which puts the ISOMsg in pre-configured space/queue which is then picked by
 * one of the Transaction manager for further processing.
 *
 * Every Listener Class must implement ISORequestListener's process method.
 *
 * @see ISORequestListener
 * @see ReConfigurable
 *
 */
public class AcquirerISOListener implements ISORequestListener, ReConfigurable {
    private Configuration cfg;
    private long          timeout;
    private IAcquirerSpec specClass;
    private Integer       throttlerValue;

    /**
     * Default Constructor
     */
    public AcquirerISOListener() {
	super();
    }

    /**
     * Configuration Method.
     *
     * @throws ConfigurationException
     */
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	try {
	    this.cfg = cfg;
	    timeout = cfg.getLong(SwitchConstants.TIMEOUT_PROP);
	    String specificationClassName = cfg.get("message-specification");
	    if (specificationClassName != null) {
		this.specClass = (IAcquirerSpec) Class.forName(specificationClassName).newInstance();
	    }
	    throttlerValue = cfg.getInt("throttler-value");
	} catch (Exception _ex) {
	    throw new ConfigurationException(_ex.getMessage(), _ex);
	}

    }

    /**
     * Listener's only method. If queue size defined for space is already full
     * with sufficient number of request, it response back with message
     * SERVER_BUSY, otherwise gives the message to queue.
     *
     * @param source source where you optionally can reply
     * @param m the unmatched request
     * @return true if request was handled by this listener
     */
    @SuppressWarnings("unchecked")
    public boolean process(ISOSource source, ISOMsg msg) {
	try {
	    TransContext ctx = new TransContext();
	    ctx.setAcquirerSpec(specClass);
	    ctx.setAcquirerRequest(msg);
	    ctx.setSource(source);
	    String sourceType = cfg.get("source-type", null);
	    ctx.setSourceType(sourceType);
	    Space sp = SpaceFactory.getSpace(cfg.get("space"));
	    String queue = cfg.get("queue");
	    int queueSize = Utils.getAquirerQueueSize(sp, queue);
	    if (throttlerValue != null && queueSize != -1 && queueSize >= throttlerValue) {
		if (specClass != null) {
		    ctx.setIRC(IMFResponseCodes.SERVER_BUSY);
		    specClass.populateAcquirerResponse(ctx);
		}
		if (ctx.getAcquirerResponse() != null) {
		    source.send((ISOMsg) ctx.getAcquirerResponse());
		}
	    } else {
		sp.out(queue, ctx, timeout);
	    }
	} catch (Exception e) {
	    CWLogger.appLog.error("Error in AcquirerISOListener " + e);
	}
	return true;
    }
}
