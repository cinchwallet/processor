package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.AbortParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.msg.TransactionLeg;
import com.cinchwallet.core.utils.CWLogger;


/**
 * <code>DBLogger</code> object is used to log transaction messages into
 * database under table oltp_txn_log. This is the only interface for writing the
 * transaction request/response into oltp_txn_log.
 * <p>
 * It is one of the participant in the transaction manager. In entire
 * transaction flow, where request/response needs to be logged, configure
 * <code>DBLogger</code> as participant in the txn manager accordingly.
 * <p>
 * <code>DBLogger</code> override both <code>prepare</code> and
 * <code>prepareForAbort</code> method to log the data in both pass/fail cases.
 * prepare is executed in success scenario, where none of the participant abort,
 * while prepareForAbort get executed in case of any participant get aborted
 * declared before <code>DBLogger</code> in the txn manager.
 *
 *
 * @see AbortParticipant
 * @see Configurable
 *
 */
public class DBLogger implements AbortParticipant, Configurable {
	private TransactionLeg leg = null;

	public void abort(long id, Serializable ctx) {

	}

	public void commit(long arg0, Serializable ctx) {

	}

	/**
	 * Get the RadicalMsg from the context, and invoke save method on the
	 * RadicalMsg to insert/update the record into database.
	 * <p>
	 * For LEG_1 and LEG_2, record get inserted into database. LEG_3 and LEG_4
	 * updates LEG_2 and LEG_1 respectively in the database.
	 *
	 * If insert/update fails, <code>DBLogger</code> set IRC as SYSTEM_ERROR and
	 * aborts.
	 *
	 */
	public int prepare(long arg0, Serializable ctx) {
		TransContext context = null;
		try {
			context = (TransContext) ctx;
			context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
			TxnMsg imf = context.getImfForLeg(leg);
			if (imf != null) {
				imf.save();
			}
			return PREPARED | NO_JOIN;
		} catch (Exception ex) {
			context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
			CWLogger.appLog.error(ex.getMessage(), ex);
			return ABORTED | NO_JOIN;
		}
	}

	/**
	 * Configuration method invoked by framework while initialising Class.
	 * Single parameter leg is used for initialisation.
	 * <p>
	 * leg - leg value while initialising <code>DBLogger</code> object. e.g.
	 * LEG_1, LEG_2 etc.
	 * <p>
	 * Based on the leg parameter, RadicalMsg is being fetched from the context
	 * by using following code:
	 * <p>
	 * context.getImfForLeg(leg); e.g. AcquirerIMF would be returned by
	 * context.getImfForLeg(leg) for leg = LEG_2.
	 * <p>
	 * In transaction manager config, use the following tag to define the leg
	 * property:
	 * <p>
	 * &lt;property name="leg" value="LEG_1" /&gt;
	 *
	 *
	 * @see org.jpos.core.Configurable#setConfiguration(org.jpos.core.Configuration)
	 */
	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		try {
			leg = TransactionLeg.valueOf(cfg.get("leg"));
		} catch (Exception _ex) {
			throw new ConfigurationException(_ex.getMessage(), _ex);
		}
	}

	/**
	 * <code>prepareForAbort</code> is called when any of the participant
	 * declared before <code>DBLogger</code> in the transaction manager got
	 * ABORTED. It logs the records into database in case of failure.
	 *
	 * It internally calls the <code>prepare</code> method to save the records.
	 */
	public int prepareForAbort(long id, Serializable context) {
		prepare(id, context);
		return PREPARED | NO_JOIN;
	}
}
