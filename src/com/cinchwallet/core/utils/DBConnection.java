package com.cinchwallet.core.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.cinchwallet.core.db.DBConstants;


/**
 *
 * The <code>DBConnection</code> is the utility class to create and holds the
 * connections. This class is responsible for making database connection
 * available to the resource whoever request for it.
 * <p>
 * Switch application interacts with the following two schemas:
 * <p>
 * <blockquote>
 *
 * <pre>
 * ks_transaction - contains the table used by the switch application.
 * ofbiz - contains the table of MMS application and switch uses this data for validation purpose.
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * database.properties contains the entire information related to database like
 * url, login credential and connection settings.
 * <p>
 * <blockquote>
 *
 * <pre>
 * txnLog.database.url - defines the url for ks_transaction schema
 * ofbiz.database.url - - defines the url for ofbiz
 * database.userName - username to connect to database
 * database.password - password to connect to database
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * To point to new database/schema, simply modify the respective information in
 * the database.properties and this class would do entire process to make the
 * connection available.
 * <p>
 * <code>DBConnection</code> uses two BasicDataSource Object, one for each
 * schemas to hold the connections.
 * <p>
 * txnLogdataSource - DataSource for ks_transaction schema.
 * <p>
 * ofbizDataSource - DataSource for ofbiz schema.
 *
 * <p>
 * Two static public methods have been defined to get the connection for each
 * schemas.
 * <p>
 * <blockquote>
 *
 * <pre>
 * getConnection - returns the connection for ofbiz.
 * getTxnConnection - returns the connection for ks_transaction.
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * @see BasicDataSource
 *
 */
public class DBConnection {
    private static Properties             driverInfo       = new Properties();
    private static BasicDataSource        dataSource       = null;
    private static BasicDataSource        txnLogdataSource = null;
    private static BasicDataSource        portalDataSource  = null;
    private static final Logger           logger           = Logger.getLogger(DBConnection.class);

    /**
     * Initialize the DataSource by reading the database.properties file. Read
     * the database.properties file and load the entire content into
     * driverConfig instance of {@link RPSSimpleConfiguration}. driverInfo,
     * instance of {@link Properties} is initialized by reading the
     * driverConfig.
     *
     * <code>setupDB</code> method is now invoked to initialize the DataSource
     * for ks_transaction/ofbiz.
     *
     * @param propertyFile - contains the entire database related property.
     * @throws Exception
     */
    public synchronized static void init() throws Exception {
	if (dataSource == null) {
	     InputStream inp = DBConnection.class.getClassLoader().getResourceAsStream("database.properties");
	     //InputStream inp  = new FileInputStream(fileName);
	     driverInfo.load(inp);
	     inp.close();
	     setupTxnDataSource();
	     setupPoartalDataSource();
	}
    }


    /**
     * Create a new instance of BasicDataSource and assign it to variable
     * txnLogdataSource, and initialize it with the configuration provided in
     * the database.properties.
     * <p>
     * Following piece of code should be used to get the connection from
     * txnLogdataSource.
     * <p>
     * Connection connection = txnLogdataSource.getConnection()
     *
     * @throws Exception
     */
    private static void setupTxnDataSource() throws Exception {

	txnLogdataSource = new BasicDataSource();
	txnLogdataSource.setDriverClassName(driverInfo.getProperty(DBConstants.DB_DRIVER_CLASS));
	txnLogdataSource.setUsername(driverInfo.getProperty(DBConstants.TXN_USER_NAME));
	txnLogdataSource.setPassword(driverInfo.getProperty(DBConstants.TXN_PASSWORD));
	txnLogdataSource.setUrl(driverInfo.getProperty(DBConstants.TXN_DB_URL));
	txnLogdataSource.setInitialSize(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_INITIAL_SIZE)));
	txnLogdataSource.setMaxWait(Long.parseLong(driverInfo.getProperty(DBConstants.DB_MAX_WAIT)));
	txnLogdataSource.setMaxActive(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MAX_ACTIVE)));
	txnLogdataSource.setMaxIdle(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MAX_IDLE)));
	txnLogdataSource.setMinIdle(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MIN_IDLE)));
	txnLogdataSource.setValidationQuery(driverInfo.getProperty(DBConstants.DB_VALIDATION_QUERY));
	txnLogdataSource.setTestOnBorrow(Boolean.parseBoolean(driverInfo.getProperty(DBConstants.DB_TESTONBORROW)));
	txnLogdataSource.setTestWhileIdle(Boolean.parseBoolean(driverInfo.getProperty(DBConstants.DB_TESTWHILEIDLE)));
	txnLogdataSource.addConnectionProperty(DBConstants.DB_REWRITEBATCHSTATEMENTS, "true");
	txnLogdataSource.setPoolPreparedStatements(true);
	txnLogdataSource.setDefaultTransactionIsolation(java.sql.Connection.TRANSACTION_READ_UNCOMMITTED);

    }

    private static void setupPoartalDataSource() throws Exception {

	portalDataSource = new BasicDataSource();
	portalDataSource.setDriverClassName(driverInfo.getProperty(DBConstants.DB_DRIVER_CLASS));
	portalDataSource.setUsername(driverInfo.getProperty(DBConstants.PORTAL_USER_NAME));
	portalDataSource.setPassword(driverInfo.getProperty(DBConstants.PORTAL_PASSWORD));
	portalDataSource.setUrl(driverInfo.getProperty(DBConstants.PORTAL_DB_URL));
	portalDataSource.setInitialSize(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_INITIAL_SIZE)));
	portalDataSource.setMaxWait(Long.parseLong(driverInfo.getProperty(DBConstants.DB_MAX_WAIT)));
	portalDataSource.setMaxActive(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MAX_ACTIVE)));
	portalDataSource.setMaxIdle(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MAX_IDLE)));
	portalDataSource.setMinIdle(Integer.parseInt(driverInfo.getProperty(DBConstants.DB_MIN_IDLE)));
	portalDataSource.setValidationQuery(driverInfo.getProperty(DBConstants.DB_VALIDATION_QUERY));
	portalDataSource.setTestOnBorrow(Boolean.parseBoolean(driverInfo.getProperty(DBConstants.DB_TESTONBORROW)));
	portalDataSource.setTestWhileIdle(Boolean.parseBoolean(driverInfo.getProperty(DBConstants.DB_TESTWHILEIDLE)));
	portalDataSource.addConnectionProperty(DBConstants.DB_REWRITEBATCHSTATEMENTS, "true");
	portalDataSource.setPoolPreparedStatements(true);
	portalDataSource.setDefaultTransactionIsolation(java.sql.Connection.TRANSACTION_READ_UNCOMMITTED);

    }


    /**
     * Returns the connection over for ks_transaction schema. If
     * txnLogdataSource is null, <code>setupDB</code> is invoked to initialize
     * the DataSource and then execute
     * <code>txnLogdataSource.getConnection()</code> to return the connection.
     *
     * @return java.sql.Connection
     * @throws Exception
     */
    public static java.sql.Connection getTxnConnection() throws Exception {
	if (txnLogdataSource == null) {
	    init();
	}
	Connection connection = txnLogdataSource.getConnection();
	setSchemaOnConnection(connection);
	return connection;
    }


    public static java.sql.Connection getPortalConnection() throws Exception {
	if (portalDataSource == null) {
	    init();
	}
	Connection connection = portalDataSource.getConnection();
	return connection;
    }

    /*
     * public static java.sql.Connection getOfbizConnection() throws
     * SQLException { Connection connection = ofbizDataSource.getConnection();
     * setSchemaOnConnection(connection); return connection; }
     */

    private static void setSchemaOnConnection(Connection con) throws SQLException {
	Statement stmt = null;
	try {
	    // DB2 specific SQL. Oracle uses different syntax.
	    String schema = driverInfo.getProperty(DBConstants.DB_SCHEMA);
	    if (schema != null && !"".equals(schema)) {
		stmt = con.createStatement();
		stmt.execute("set current schema = " + schema);
	    }
	} finally {
	    if (stmt != null)
		stmt.close();
	}
    }

    /**
     * Use to reinitialize the txnLogdataSource/ofbizDataSource. It first
     * destroys already created datasource by calling
     * <code>shutdownDataSource</code> and then calls <code>setupDB</code>
     * to create again.
     *
     * @throws Exception
     */
    protected static synchronized void reinitializeDatasource() throws Exception {
	logger.warn("DBConnection:: reinitializeDatasource()");
	shutdownDataSource();
	setupTxnDataSource();
    }

    /**
     * Destroy all the DataSource objects.
     *
     * @throws SQLException
     */
    public static void shutdownDataSource() throws SQLException {
	dataSource.close();
	txnLogdataSource.close();
	portalDataSource.close();
    }

    /**
     * Utility method to close Connection, Statements and Resultset. Application
     * calls this method to close the Connection/Statement/Resultset after done
     * with these object.
     *
     * Connection get returned to its DataSource, once close is called on it.
     * Doing this make the same connection available to some other resource.
     *
     * @param pResultSet - ResultSet to close
     * @param pPrepStmt - Statement to close
     * @param pCon - Connection to close
     */
    public static void closeAll(ResultSet pResultSet, PreparedStatement pPrepStmt, Connection pCon) {
	if (pResultSet != null) {
	    try {
		pResultSet.close();
	    } catch (Exception _Ex) {
		CWLogger.appLog.error("Exception ::", _Ex);
	    }
	}
	if (pPrepStmt != null) {
	    try {
		pPrepStmt.close();
	    } catch (Exception _Ex) {
		CWLogger.appLog.error("Exception ::", _Ex);
	    }
	}
	if (pCon != null) {
	    try {
		pCon.close();
	    } catch (Exception _Ex) {
		CWLogger.appLog.error("Exception ::", _Ex);
	    }
	}
    }
}