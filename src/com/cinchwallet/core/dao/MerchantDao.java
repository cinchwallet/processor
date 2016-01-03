package com.cinchwallet.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.Merchant;
import com.cinchwallet.core.utils.CWLogger;
import com.cinchwallet.core.utils.DBConnection;
import com.cinchwallet.core.utils.Utils;

/**
*
* Class responsible for all database operation from merchant table.
*
*/public class MerchantDao {

    private static String SELECT_VALID_MERCHANT = "select MERCHANT_ID from MERCHANT where MERCHANT_ID = ? and STATUS = ?";
    
    private static String SELECT_BY_USERNAME = "select * from USER_LOGIN where USER_NAME = ? and STATUS = true";

    private static String SELECT_STORE_MERCHANT = "select s.STORE_ID, s.NAME as  store_name, m.MERCHANT_ID, m.NAME as merchant_name from STORE s, MERCHANT m " +
    												"where m.UID = s.MERCHANT_ID and s.UID = ?";

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

    public Merchant getStoreDetail(String userName, String password){
	Connection lConnection = null;
	PreparedStatement lPreparedStatement = null;
	ResultSet lResultSet = null;
	String dbPassword = null;
	Merchant merchant = null;
	try {
	    lConnection = DBConnection.getPortalConnection();
	    lPreparedStatement = lConnection.prepareStatement(SELECT_BY_USERNAME);
	    lPreparedStatement.setString(1, userName);
	    lResultSet = lPreparedStatement.executeQuery();
	    Integer storeId = null;
	    if (lResultSet != null && lResultSet.next()) {
	    	dbPassword = lResultSet.getString("PASSWORD");
	    	storeId = lResultSet.getInt("PARENT_ID");
	    }
	    if(dbPassword!=null && Utils.getMD5(password).equals(dbPassword)){
	    	//fetch the store and merchant details
	    	lPreparedStatement.clearParameters();
		    lPreparedStatement = lConnection.prepareStatement(SELECT_STORE_MERCHANT);
		    lPreparedStatement.setInt(1, storeId);
		    lResultSet = lPreparedStatement.executeQuery();
		    if (lResultSet != null && lResultSet.next()) {
		    	merchant = new Merchant();
		    	merchant.setMerchantId(lResultSet.getString("MERCHANT_ID"));
		    	merchant.setStoreId(lResultSet.getString("STORE_ID"));
		    	merchant.setMerchantName(lResultSet.getString("merchant_name"));
		    	merchant.setStoreName(lResultSet.getString("store_name"));
		    }
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


    public static void main(String[] args) {
		MerchantDao merchantDao = new MerchantDao();
		Merchant merchant = merchantDao.getStoreDetail("test", "test1");
		System.out.println(merchant.getStoreId());
	}
}
