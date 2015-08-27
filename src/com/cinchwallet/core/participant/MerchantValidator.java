package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.Merchant;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.dao.MerchantDao;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class MerchantValidator implements TransactionParticipant, Configurable {
    MerchantDao merchantDao = new MerchantDao();
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
		Merchant merchant = merchantDao.getMerchant(acquirerIMF.getMid());
		if(merchant==null){
		    //Either merchant is invalid.
		    context.setIRC(IMFResponseCodes.INV_MERCHANT_ID);
		    return ABORTED | NO_JOIN;
		}else if(!merchant.isActive()){
		    //Merchant is inactive
		    context.setIRC(IMFResponseCodes.MERCHANT_INACTIVE);
		    return ABORTED | NO_JOIN;
		}

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
