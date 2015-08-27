package com.cinchwallet.core.acquirer.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionManager;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;


/**
 * <code>AcquirerInit</code> is the first participant to handle the incoming
 * request which is verified by the validation framework.
 *  * <p>
 * It first checks for the instance of Specs class in the context object, if
 * found use that instance otherwise uses its local IAcquirerSpec instance being
 * initialized by the <code>setConfiguration</code> method and also populates
 * context object with this object for any future uses.
 * <p>
 * It creates the AcquirerRequestIMF i.e. LEG_1 by calling the
 * populateAcquirerRequestIMF() method of Specification class, and populates the
 * acquirerIMF variable of transContext object with this.
 * <p>
 * acquirerIMF of transContext holds the AcquirerRequestIMF as well as
 * AcquirerResponseIMF.
 *
 * @see TransactionParticipant
 * @see Configurable
 *
 */

public class AcquirerInit implements TransactionParticipant, Configurable {
    private IAcquirerSpec      acqSpec;
    private TransactionManager transactionManager = null;

    public void abort(long id, Serializable context) {

    }

    public void commit(long id, Serializable context) {

    }

    /**
     * Invokes the Specification's populateAcquirerRequestIMF method to create
     * the AcquirerRequestIMF. In exception scenario while populating
     * AcquirerRequestIMF, participant get aborted with IRC of SYSTEM_ERROR.
     *
     * @return int - execution status of the participant.
     *
     */
    public int prepare(long id, Serializable ctx) {
	TransContext context = null;
	try {
	    context = (TransContext) ctx;
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    IAcquirerSpec spec = (IAcquirerSpec) context.getAcquirerSpec();
	    if (spec == null) {
		spec = acqSpec;
		context.setAcquirerSpec(spec);
	    }
	    spec.populateAcquirerRequestIMF(context);
	    return PREPARED | NO_JOIN;
	} catch (Exception ex) {
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    CWLogger.appLog.error(ex.getMessage(), ex);
	    return ABORTED | NO_JOIN;
	}
    }

    /**
     * Configuration method invoked by framework while initializing Class.
     * Following are the parameters used :
     * <p>
     * message-specification-class - fully qualified name of the Specification
     * class.
     * <p>
     * This method initialize the acqSpec instance of IAcquirerSpec, by creating
     * the instance of given message-specification-class.
     * <p>
     * In transaction manager config, use the following tag to define the
     * message-specification-class property:
     *
     *
     *
     * @see org.jpos.core.Configurable#setConfiguration(org.jpos.core.Configuration)
     */
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	String specificationClassName = this.transactionManager.getConfiguration().get("message-specification-class");
	if (specificationClassName != null && specificationClassName.length() > 0) {
	    try {
		this.acqSpec = (IAcquirerSpec) Class.forName(specificationClassName).newInstance();
	    } catch (Exception _ex) {
		throw new ConfigurationException(_ex.getMessage(), _ex);
	    }
	}
    }

    public void setTransactionManager(TransactionManager txnMgr) {
	this.transactionManager = txnMgr;
    }
}
