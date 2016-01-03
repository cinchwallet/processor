package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.DBConnection;

/**
*
* Class responsible for all database operation from merchant table.
*
*/public class CardDao {

    private static String SELECT_CARD_PRODUCT = "select EARN_POINT_RULE, BURN_POINT_RULE from CARD_PRODUCT cp, CARD c where cp.UPC = c.CARD_PRODUCT and c.NUMBER = ?";

    private static String SELECT_CARD_PRODUCT_BY_MID = "select EARN_POINT_RULE, BURN_POINT_RULE from CARD_PRODUCT where ISSUING_MERCHANT = ?";

    public String getEarnPointRule(String cardNumber){
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	String earnPointRule = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_CARD_PRODUCT);
	    lPreparedStatement.setString(1, cardNumber);
	    //lPreparedStatement.setBoolean(2, true);
	    lResultSet = lPreparedStatement.executeQuery();
	    if (lResultSet != null && lResultSet.next()) {
	    	earnPointRule = lResultSet.getString("EARN_POINT_RULE");
	    }
	} catch (SQLException _sqlException) {
	    CWLogger.appLog.error("Exception ::", _sqlException);
	} catch (Exception _Exception) {
	    CWLogger.appLog.error("Exception ::", _Exception);
	} finally {
	    DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
	}
	return earnPointRule;
    }
    

    public String getEarnPointRuleByMID(String merchantId){
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	String earnPointRule = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_CARD_PRODUCT_BY_MID);
	    lPreparedStatement.setString(1, merchantId);
	    lResultSet = lPreparedStatement.executeQuery();
	    if (lResultSet != null && lResultSet.next()) {
	    	earnPointRule = lResultSet.getString("EARN_POINT_RULE");
	    }
	} catch (SQLException _sqlException) {
	    CWLogger.appLog.error("Exception ::", _sqlException);
	} catch (Exception _Exception) {
	    CWLogger.appLog.error("Exception ::", _Exception);
	} finally {
	    DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
	}
	return earnPointRule;
    }

    public String getBurnPointRule(String cardNumber){
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	String burnPointRule = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_CARD_PRODUCT);
	    lPreparedStatement.setString(1, cardNumber);
	    //lPreparedStatement.setBoolean(2, true);
	    lResultSet = lPreparedStatement.executeQuery();
	    if (lResultSet != null && lResultSet.next()) {
	    	burnPointRule = lResultSet.getString("BURN_POINT_RULE");
	    }
	} catch (SQLException _sqlException) {
	    CWLogger.appLog.error("Exception ::", _sqlException);
	} catch (Exception _Exception) {
	    CWLogger.appLog.error("Exception ::", _Exception);
	} finally {
	    DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
	}
	return burnPointRule;
    }

}
