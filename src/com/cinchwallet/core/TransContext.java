package com.cinchwallet.core;

import org.jpos.transaction.Context;

import com.cinchwallet.core.msg.IAcquirerSpec;
import com.cinchwallet.core.msg.IProcessorSpecification;
import com.cinchwallet.core.msg.TransactionLeg;

/**
 *
 * <code>TransContext</code> represent a session data for a transaction. Every
 * transaction has its own context object. Any value or object required to be
 * used through out the transaction cycle should be put in this
 * <code>TransContext</code> object. Framework maintains this object
 * corresponding to each transaction and floats between every participants
 * in the transaction manager.
 * <p>
 * Participant defined in the transaction manager uses this object to
 * communicate with each other.
 *
 */
public class TransContext extends Context {

    private TxnMsg              acquirerIMF             = null;
    private TxnMsg              processorIMF            = null;

    private Object                  acquirerRequest         = null;
    private Object                  acquirerResponse        = null;

    private IAcquirerSpec           acquirerSpec            = null;

    private Object                  processorRequest         = null;
    private Object                  processorResponse        = null;

    private IProcessorSpecification processorSpec            = null;

    private String                  IRC;
    private Object                  source;

    private String                  sourceType              = null;
    private Object                  listenerMonitor         = null;

    private TxnMsg              reversalIMF             = null;
    /**
     * Returns the current status of the transaction based on the leg formed in
     * the system.
     * <p>
     * If LEG_1 is not prepared, means no request has been sent to switch or
     * some problem has occur while creating LEG_1. In that case null is
     * returned.
     * <p>
     * If ProcessorIMF is null and AcquirerIMF is not null, indicates that
     * LEG_2/LEG_3 could not be created and only LEG_1 or LEG_4 exist in the
     * system.
     *
     * @return RadicalTransactionLeg - enum of legs.
     */
    public TransactionLeg getCurrentState() {
	if (acquirerIMF == null) {
	    return null;
	}else {
	    return TransactionLeg.valueOf(acquirerIMF.getLeg());
	}
    }

    public boolean isAcquirerResponseIMFValid() {
	return false;
    }

    public boolean isProcessorRequestIMFValid() {
	return false;
    }

    public boolean isProcessorResponseIMFValid() {
	return false;
    }

    public Object getListenerMonitor() {
	return listenerMonitor;
    }

    public void setListenerMonitor(Object listenerMonitor) {
	this.listenerMonitor = listenerMonitor;
    }

    public Object getAcquirerRequest() {
	return acquirerRequest;
    }

    public void setAcquirerRequest(Object request) {
	this.acquirerRequest = request;
    }

    public Object getAcquirerResponse() {
	return acquirerResponse;
    }

    public void setAcquirerResponse(Object response) {
	this.acquirerResponse = response;
    }

    public IAcquirerSpec getAcquirerSpec() {
	return acquirerSpec;
    }

    public void setAcquirerSpec(IAcquirerSpec acquirerSpec) {
	this.acquirerSpec = acquirerSpec;
    }

    public String getIRC() {
	return IRC;
    }

    public void setIRC(String irc) {
	IRC = irc;
    }

    public Object getSource() {
	return source;
    }

    public void setSource(Object source) {
	this.source = source;
    }

    public String getSourceType() {
	return sourceType;
    }

    public void setSourceType(String sourceType) {
	this.sourceType = sourceType;
    }


    /**
     * Returns appropriate instance of acquirerIMF/processorIMF based on the
     * given leg name.
     * <p>
     * LEG_1 and LEG_4 is represented by the acquirerIMF and LEG_2 and LEG_3 is
     * shown by processorIMF.
     * <p>
     * Delayed response is represented by the LEG_DR and REV_RQ/REV_RQ shows the
     * reversalIMF.
     *
     * @param leg - Leg name
     * @return RadicalMsg - IMF instance.
     */
    public TxnMsg getImfForLeg(TransactionLeg leg) {
	if (leg.equals(TransactionLeg.LEG_1) || leg.equals(TransactionLeg.LEG_4)) {
	    return acquirerIMF;
	}
	return null;
    }

    public void notifyListner() {
	if (listenerMonitor != null) {
	    synchronized (listenerMonitor) {
		listenerMonitor.notify();
	    }
	}
    }

    public TxnMsg getAcquirerIMF() {
	return acquirerIMF;
    }

    public void setAcquirerIMF(TxnMsg acquirerIMF) {
	this.acquirerIMF = acquirerIMF;
    }

    public Object getProcessorRequest() {
        return processorRequest;
    }

    public void setProcessorRequest(Object processorRequest) {
        this.processorRequest = processorRequest;
    }

    public Object getProcessorResponse() {
        return processorResponse;
    }

    public void setProcessorResponse(Object processorResponse) {
        this.processorResponse = processorResponse;
    }

    public IProcessorSpecification getProcessorSpec() {
        return processorSpec;
    }

    public void setProcessorSpec(IProcessorSpecification processorSpec) {
        this.processorSpec = processorSpec;
    }

    public TxnMsg getProcessorIMF() {
        return processorIMF;
    }

    public void setProcessorIMF(TxnMsg processorIMF) {
        this.processorIMF = processorIMF;
    }

    public TxnMsg getReversalIMF() {
        return reversalIMF;
    }

    public void setReversalIMF(TxnMsg reversalIMF) {
        this.reversalIMF = reversalIMF;
    }



}
