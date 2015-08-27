package com.cinchwallet.core.participant;

import java.io.Serializable;
import java.util.List;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.acquirer.http.msg.TxnHistory;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class MiniStatementGenerator implements TransactionParticipant, Configurable {
    @Override
    public void abort(long arg0, Serializable arg1) {
    }

    @Override
    public void commit(long arg0, Serializable arg1) {
    }

    @Override
    public int prepare(long arg0, Serializable ctx) {
	TransContext context = null;
	try {
		context = (TransContext) ctx;
		context.checkPoint(this.getClass().getSimpleName() + ":" + "prepare");
		TxnMsg acquirerIMF = context.getAcquirerIMF();
		//read last 5 txn from oltp and prepare statement
		List<TxnHistory> list = acquirerIMF.getMiniStmt();
		if(list!=null && list.size()>0){
		    acquirerIMF.setTxnList(list);
		}
		context.setIRC(SwitchConstants.SUCCESS);
		return PREPARED | NO_JOIN;
	} catch (Exception ex) {
		context.setIRC(IMFResponseCodes.SYSTEM_ERROR);
		CWLogger.appLog.error(ex.getMessage(), ex);
		return ABORTED | NO_JOIN;
	}
    }

    @Override
    public void setConfiguration(Configuration arg0) throws ConfigurationException {
    }

}
