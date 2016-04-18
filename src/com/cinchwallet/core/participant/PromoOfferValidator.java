package com.cinchwallet.core.participant;

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.TransactionParticipant;

import com.cinchwallet.core.PromoOffer;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.dao.CardDao;
import com.cinchwallet.core.dao.PromoOfferDao;
import com.cinchwallet.core.msg.IMFResponseCodes;
import com.cinchwallet.core.utils.CWLogger;

public class PromoOfferValidator implements TransactionParticipant, Configurable {
	PromoOfferDao offerDao = new PromoOfferDao();
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

			if (acquirerIMF.getPromoCode() != null && !ISOUtil.isBlank(acquirerIMF.getPromoCode())) {
				//Promo code has been passed into transaction. Run the rule.
				
				/*
				 * Check the validity of promo code. PROMOTIONAL_OFFER
				 * It can be extended to validate card product as well
				 */
				PromoOffer offer = offerDao.getPromoOffer(acquirerIMF.getPromoCode());
				if (offer == null) {
					context.setIRC(IMFResponseCodes.INV_PROMOCODE);
					CWLogger.appLog.error("Offer code not found for : "+acquirerIMF.getPromoCode());
					return ABORTED | NO_JOIN;
				}
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
