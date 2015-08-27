package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.constant.SwitchConstants;
import com.cinchwallet.core.dao.LoyaltyDao;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class LoyaltyReader implements TransactionParticipant, Configurable {

    LoyaltyDao loyaltyDao = new LoyaltyDao();

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
		String loyaltyNumber = loyaltyDao.getLoyaltyNumber(acquirerIMF.getOneIncNumber(), acquirerIMF.getMid());
		if(loyaltyNumber==null){
		    context.setIRC(IMFResponseCodes.LOYALTY_NUMBER_NOT_FOUND);
		    return ABORTED | NO_JOIN;
		}
		//Loyalty number should be captured in F62
		acquirerIMF.setLoyaltyNumber(loyaltyNumber);
		context.setIRC(IMFResponseCodes.APPROVED_TXN);
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
