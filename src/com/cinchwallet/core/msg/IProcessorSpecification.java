package com.cinchwallet.core.msg;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.exception.SwitchException;


/**
 *
 * The IProcessorSpecification interface should be implemented by Processor
 * Specification class.
 *
 * This interface is designed to provide a common protocol for Specification
 * objects. For example, IProcessorSpecification is implemented by
 * CUPDSpecification Class.
 * <p>
 * <code>IProcessorSpecification</code> defines the Processor's Specification.
 * The class must define following methods.
 * <p>
 * {@code public void getSpecificationName();} - returns the Specification Class
 * name. It tells the exact name of the Processor.
 * <p>
 * {@code public void populateProcessorRequestIMF(TransContext transContext) throws RPSException;} -
 * responsible for preparing ProcessorRequestIMF i.e. LEG_2 from
 * AcquirerRequesrIMF i.e. LEG_1.
 * <p>
 * {@code public void populateProcessorRequest(TransContext transContext) throws RPSException;} -
 * responsible for preparing ISORequest to be send to processor from the
 * ProcessorRequestIMF.
 * <p>
 * {@code public void populateProcessorResponseIMF(TransContext transContext) throws RPSException;} -
 * responsible for preparing ProcessorResponseIMF from ISO Response received
 * from the PRocessor.
 * <p>
 * {@code public void populateReversalProcessorRequestIMF(TransContext transContext) throws RPSException;} -
 * responsible for preparing ReversalRequestIMF from ProcessorIMF. This leg is
 * called as REV_RQ and send to processor for any reversal transaction.
 * <p>
 * {@code public void populateReversalRequest(TransContext transContext) throws RPSException;} -
 * responsible for preparing ISORequest for reversal from ReversalRequestIMF.
 * <p>
 * {@code public void populateReversalProcessorResponseIMF(TransContext transContext) throws RPSException;} -
 * responsible for preparing ReversalResponseIMF from ISO Response received from
 * the processor for reversal transaction.
 */

public interface IProcessorSpecification {

    public String getSpecificationName();

    public void populateProcessorRequestIMF(TransContext transContext) throws SwitchException;

    public void populateProcessorRequest(TransContext context) throws SwitchException;

    public void populateProcessorResponseIMF(TransContext transContext) throws SwitchException;

    public void populateReversalProcessorRequestIMF(TransContext context) throws SwitchException;

    public void populateReversalRequest(TransContext context) throws SwitchException;

    public void populateReversalProcessorResponseIMF(TransContext context) throws SwitchException;

}