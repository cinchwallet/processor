package com.cinchwallet.core.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jpos.core.ReConfigurable;
import org.jpos.q2.QBeanSupport;

/**
 * A <code>OneIncLogger</code> is used to log messages for a specific system
 * or application component. It uses the log4j.xml to define different
 * appenders. Corresponding to each appender, Logger object has been created by
 * calling the getLogger factory methods as shown below:
 * <p>
 * Logger appLog = Logger.getLogger(APP_LOG);
 * <p>
 * Logging messages will be forwarded to registered Handler objects, which can
 * forward the messages to a variety of destinations, including consoles, files,
 * OS logs, etc. In our case its file only.
 * <p>
 * Each Logger has a "Level" associated with it. This reflects a minimum Level
 * that this logger cares about. If a Logger's level is set to null, then its
 * effective level is inherited from its parent, which may in turn obtain it
 * recursively from its parent, and so on up the tree.
 * <p>
 * The log level can be configured based on the properties from the logging
 * configuration file. However it may also be dynamically changed by calls on
 * the Logger.setLevel method.
 * <p>
 *
 * After creating the instance of Logger, following code can be used to log the
 * message:
 * <p>
 * OncIncLogger.appLog.info("First Radical Log Message");
 * <p>
 *
 * To create a new logger(say SYSTEM_LOG), make the appender/category entry in
 * the log4j.xml. Create a new instance of the Logger for that appender as follow:
 * <p>
 * Logger systemLog = Logger.getLogger(TXN_LOG);
 * <p>
 * Now, you are ready to use this newly created logger, using the following code:
 * <p>
 * OneIncLogger.systemLog.info("First System Log Message");
 *
 */
public class CWLogger extends QBeanSupport implements ReConfigurable {

    /**
     * property name corresponding to the location of log4j.xml.
     */
    private static final String CFG_LOG4J_PROP_FILE      = "log4j-prop-file";
    private static final String CFG_WATCH_TIME_MILLIS    = "watch-time-millis";
    private static final String APP_LOG                  = "APP_LOGGER";
    private static final String TXN_LOG                  = "TRANSACTION_LOGGER";
    private static final String HTTP_LOG                  = "HTTP_LOGGER";

    public static Logger       appLog                   = null;
    public static Logger       txnLog                   = null;
    public static Logger       httpLog                   = null;

    public CWLogger() {
	super();
    }

    protected void initService() throws Exception {
	String log4jxmlfileName = cfg.get(CFG_LOG4J_PROP_FILE);
	File log4jxmlFile = new File(log4jxmlfileName);
	if (log4jxmlFile.exists()) {
	    DOMConfigurator.configureAndWatch(log4jxmlfileName, cfg.getLong(CFG_WATCH_TIME_MILLIS, 86400000));
	    appLog = Logger.getLogger(APP_LOG);
	    txnLog = Logger.getLogger(TXN_LOG);
	    httpLog = Logger.getLogger(HTTP_LOG);
	} else {
	    throw new Exception("Log4jXml file not found at the location: " + log4jxmlfileName);
	}
    }
}
