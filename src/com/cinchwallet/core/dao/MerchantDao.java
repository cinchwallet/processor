package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.Merchant;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.DBConnection;

/**
*
* Class responsible for all database operation from merchant table.
*
*/public class MerchantDao {

    private static String SELECT_VALID_MERCHANT = "select MERCHANT_ID from MERCHANT where MERCHANT_ID = ? and STATUS = ?";

    public Merchant getMerchant(String mid){
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	Merchant merchant = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_VALID_MERCHANT);
	    lPreparedStatement.setString(1, mid);
	    lPreparedStatement.setBoolean(2, true);
	    lResultSet = lPreparedStatement.executeQuery();
	    if (lResultSet != null && lResultSet.next()) {
		merchant = new Merchant();
		merchant.setActive(true);
	    }
	} catch (SQLException _sqlException) {
	    CWLogger.appLog.error("Exception ::", _sqlException);
	} catch (Exception _Exception) {
	    CWLogger.appLog.error("Exception ::", _Exception);
	} finally {
	    DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
	}
	return merchant;
    }
}
