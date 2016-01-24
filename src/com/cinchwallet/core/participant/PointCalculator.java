package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.CardProduct;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.dao.CardDao;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class PointCalculator implements TransactionParticipant, Configurable {
	CardDao cardDao = new CardDao();

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
			String earnPoint = null;
			CardProduct cardProduct = null;
			int pointEarned = 0;
			if (acquirerIMF.getPan() != null) {
				cardProduct = cardDao.getCardProduct(acquirerIMF.getPan(), null);
			} else {
				// read the rule using mobile number, MID given in the request
				cardProduct = cardDao.getCardProduct(null, acquirerIMF.getMid());
			}
			earnPoint = cardProduct.getEarnPointRule();
			if (cardProduct.getLoyaltyType().equals("SPEND")) {
				if (earnPoint != null) {
					String[] token = earnPoint.split(":");
					String earnPointAmt = token[0];
					String earnPointPnt = token[1];
					pointEarned = (int) (acquirerIMF.getTxnAmount() / Double.parseDouble(earnPointAmt)) * (Integer.parseInt(earnPointPnt));
				}
			} else {
				pointEarned = cardProduct.getPointsPerTxn();
			}
			acquirerIMF.setTxnPoints(pointEarned);
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
