package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.CardProduct;
import com.cinchwallet.core.PromoOffer;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.DBConnection;

/**
 * 
 * Class responsible for all database operation from merchant table.
 * 
 */
public class PromoOfferDao {

	private static String SELECT_PROMO_OFFER = "select * from PROMOTIONAL_OFFER where PROMO_CODE = ? and START_DATE <= ? and END_DATE >= ? ";

	public PromoOffer getPromoOffer(String promoCode) {
		Connection lConnection = null;
		PreparedStatement lPreparedStatement = null;
		ResultSet lResultSet = null;
		PromoOffer offer = null;
		int index = 1;
		try {
			lConnection = DBConnection.getPortalConnection();
			lPreparedStatement = lConnection.prepareStatement(SELECT_PROMO_OFFER);
			lPreparedStatement.setString(index++, promoCode);
			lPreparedStatement.setDate(index++, new Date(new java.util.Date().getTime()));
			lPreparedStatement.setDate(index++, new Date(new java.util.Date().getTime()));

			lResultSet = lPreparedStatement.executeQuery();
			if (lResultSet != null) {
				offer = new PromoOffer();
				while (lResultSet.next()) {
					offer.setCardProduct(lResultSet.getString("CARD_PRODUCT"));
					offer.setPromoRule(lResultSet.getString("PROMO_RULE"));
					offer.setPromoCode(promoCode);
				}
			}
		} catch (SQLException _sqlException) {
			CWLogger.appLog.error("Exception ::", _sqlException);
		} catch (Exception _Exception) {
			CWLogger.appLog.error("Exception ::", _Exception);
		} finally {
			DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
		}
		return offer;
	}
	
}
