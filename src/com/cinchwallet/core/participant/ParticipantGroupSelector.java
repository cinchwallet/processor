package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.GroupSelector;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.utils.CWLogger;


/**
 * <code>ParticipantGroupSelector</code> implements GroupSelector in order to
 * switch the transaction to a new group of participants. It override the
 * <code>select(long id, Serializable ctx)</code> method of the GroupSelector,
 * which is executed once this participant is invoked and a particular group is
 * invoked to perform the task.
 * <p>
 * <code>ParticipantGroupSelector</code> is configured in the following
 * manner:
 * <p>
 * <blockquote>
 *
 * <pre>
 *     	&lt;property name=&quot;BALIQ&quot; value=&quot;Validation&quot; /&gt;
 *     	&lt;property name=&quot;ACTVN&quot; value=&quot;Validation&quot; /&gt;
 *     	&lt;property name=&quot;LOADF&quot; value=&quot;Validation&quot; /&gt;
 * </pre>
 *
 * </blockquote>
 * </p>
 * where property name show a key, and value shows a group name, defined in the
 * same transaction manager and contains a group of participants represent this
 * group. This entire configuration get loaded in a Map by
 * <code>setConfiguration</code> method. To get the required group, use the
 * below code:
 * <p>
 * String groups = groupSelectorMap.get(txnType.name(), null);
 * <p>
 * This will returns the group name as String, which is searched in the
 * transaction manager and invoked by the framework.
 * <p>
 * Group is required where a different set of participant needs to be executed
 * based on the some request parameter. This all can be done by just modifying
 * the transaction manager definition file, without changing any code.
 * <p>
 * e.g. if ACTVN needs to be handled by different set of participant, define a
 * new group as below:
 * <p>
 * <blockquote>
 *
 * <pre>
 *     	&lt;property name=&quot;ACTVN&quot; value=&quot;NewActivation&quot; /&gt;
 * </pre>
 *
 * </blockquote>
 * </p>
 * System would now fetch NewActivation group name for activation request and
 * execute the participants mentioned under NewActivation group.
 *
 *
 * @see TransactionParticipant
 * @see Configurable
 * @see GroupSelector
 *
 */
public class ParticipantGroupSelector implements TransactionParticipant, Configurable, GroupSelector {
    private Configuration groupSelectorMap;

    public void abort(long id, Serializable context) {

    }

    public void commit(long id, Serializable context) {

    }

    public int prepare(long arg0, Serializable context) {
	return PREPARED | NO_JOIN;

    }

    /**
     * Returns Group name based on the Transaction type of the request message.
     *
     * Search the GroupSelectorMap for group assigned for this transaction type.
     * Returns the group name and then its framework job to execute the
     * participants under this group. If no mapping found for given transaction
     * type, null returns and system aborts.
     *
     * @return String
     */
    public String select(long id, Serializable ctx) {
	TransContext context = (TransContext) ctx;
	String groups = null;
	try {
	    context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
	    if (context.getAcquirerIMF() != null && context.getAcquirerIMF().getTransactionTypeEnum() != null) {
		TransactionType txnType = context.getAcquirerIMF().getTransactionTypeEnum();
		groups = groupSelectorMap.get(txnType.name(), null);
	    }
	    return groups;
	} catch (Exception e) {
	    context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
	    CWLogger.appLog.error(e.getMessage(), e);
	    return groups;
	}
    }

    /**
     * Configuration method invoked by framework while initializing Class. It
     * defines a group name against the property in the transaction manager, as
     * shown below:
     * <p>
     * <blockquote>
     *
     * <pre>
     *     	&lt;property name=&quot;BALIQ&quot; value=&quot;Validation&quot; /&gt;
     *     	&lt;property name=&quot;ACTVN&quot; value=&quot;Validation&quot; /&gt;
     *     	&lt;property name=&quot;LOADF&quot; value=&quot;Validation&quot; /&gt;
     * </pre>
     *
     * </blockquote>
     * </p>
     * Value indicates a group, which is again a set of participants. Based on
     * the property name, selected group would be executed.
     * <p>
     * This method initialize the groupSelectorMap by reading the entire
     * property configuration.
     *
     * @param cfg - Configuration object holds the property defined in the XML.
     * @throws ConfigurationException
     */
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
	groupSelectorMap = cfg;
    }

}
