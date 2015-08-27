package com.cinchwallet.core.msg;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.exception.SwitchException;

/**
 *
 * The IAcquirerSpec interface should be implemented by Acquirer Specification
 * class.
 *
 * This interface is designed to provide a common protocol for Specification
 * objects. For example, IAcquirerSpec is implemented by OneIncSpecification
 * Class.
 *
 * <code>IAcquirerSpec</code> defines the Acquirer's Specification. The class
 * must define following methods.
 *
 * <p>
 * {@code public void populateAcquirerRequestIMF(TransContext transContext) throws SwitchException;} -
 * responsible for preparing SwitchTxnMsg from ISOMsg in the request.
 * <p>
 * {@code public void populateAcquirerResponseIMF(TransContext transContext) throws SwitchException;} -
 * responsible for preparing SwitchTxnMsg from Processor's response.
 * <p>
 * {@code public void populateAcquirerResponse(TransContext transContext) throws SwitchException;} -
 * responsible for preparing ISOMsg from SwitchTxnMsg to send to source.
 *
 */
public interface IAcquirerSpec {

    public void populateAcquirerRequestIMF(TransContext transContext) throws SwitchException;

    public void populateAcquirerResponseIMF(TransContext transContext) throws SwitchException;

    public void populateAcquirerResponse(TransContext transContext) throws SwitchException;
}
