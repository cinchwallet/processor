package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.utils.DBConnection;

public class LoyaltyDao {

    static String SELECT_QUERY = "select LOYALTY_NUMBER from CUSTOMER_LOYALTY cl " +
    				"inner join CUSTOMER_UNIVERSAL_NUMBER cun on cl.CUSTOMER_ID = cun.CUSTOMER_ID " +
    				"where cl.MERCHANT_ID = ? " +
    				"and cun.UNIVERSAL_NUMBER = ?";

    public String getLoyaltyNumber(String oneIncCardNumber, String mid) {
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	String loyaltyNumber = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_QUERY);
	    lPreparedStatement.setString(1, mid);
	    lPreparedStatement.setString(2, oneIncCardNumber);
	    lResultSet = lPreparedStatement.executeQuery();
	    if (lResultSet != null) {
		while (lResultSet.next()) {
		    loyaltyNumber = lResultSet.getString("LOYALTY_NUMBER");
		}
	    }
	} catch (SQLException _sqlException) {
	    _sqlException.printStackTrace();
	} catch (Exception _Exception) {
	    _Exception.printStackTrace();
	} finally {

	    DBConnection.closeAll(lResultSet, lPreparedStatement, lConnection);
	}
	return loyaltyNumber;
    }
}
