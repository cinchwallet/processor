package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.CardProduct;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.DBConnection;

/**
 * 
 * Class responsible for all database operation from merchant table.
 * 
 */
public class CardDao {

	private static String SELECT_CARD_PRODUCT = "select cp.* from CARD_PRODUCT cp, CARD c where cp.UPC = c.CARD_PRODUCT and c.NUMBER = ?";

	private static String SELECT_CARD_PRODUCT_BY_MID = "select * from CARD_PRODUCT where ISSUING_MERCHANT = ?";

	public CardProduct getCardProduct(String cardNumber, String merchantId) {
		Connection lConnection = null;
		PreparedStatement lPreparedStatement = null;
		ResultSet lResultSet = null;
		CardProduct cardProduct = null;
		try {
			lConnection = DBConnection.getPortalConnection();
			if (cardNumber != null) {
				lPreparedStatement = lConnection.prepareStatement(SELECT_CARD_PRODUCT);
				lPreparedStatement.setString(1, cardNumber);
			} else {
				lPreparedStatement = lConnection.prepareStatement(SELECT_CARD_PRODUCT_BY_MID);
				lPreparedStatement.setString(1, merchantId);
			}

			lResultSet = lPreparedStatement.executeQuery();
			if (lResultSet != null) {
				cardProduct = new CardProduct();
				while (lResultSet.next()) {
					cardProduct.setEarnPointRule(lResultSet.getString("EARN_POINT_RULE"));
					cardProduct.setBurnPointRule(lResultSet.getString("BURN_POINT_RULE"));
					cardProduct.setLoyaltyType(lResultSet.getString("LOYALTY_TYPE"));
					cardProduct.setPointsPerTxn(lResultSet.getInt("POINTS_PER_TXN"));
				}
			}

		} catch (SQLException _sqlException) {
			CWLogger.appLog.error("Exception ::", _sqlException);
		} catch (Exception _Exception) {
			CWLogger.appLog.error("Exception ::", _Exception);
		} finally {
			DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
		}
		return cardProduct;
	}

}
