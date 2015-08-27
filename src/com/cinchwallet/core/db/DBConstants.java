package com.cinchwallet.core.db;

public interface DBConstants {

    // DB Constants
    String TXN_DB_URL                = "txnLog.database.url";
    String TXN_USER_NAME             = "txnLog.database.userName";
    String TXN_PASSWORD              = "txnLog.database.password";

    String PORTAL_DB_URL             = "portal.database.url";
    String PORTAL_USER_NAME          = "portal.database.userName";
    String PORTAL_PASSWORD           = "portal.database.password";

    String DB_PASSWORD_ENCRYPTED     = "database.password_encrypted";
    String DB_DRIVER_CLASS           = "database.driver_class";
    String DB_INITIAL_SIZE           = "database.initial_size";
    String DB_MAX_WAIT               = "database.max_wait";
    String DB_MAX_IDLE               = "database.max_idle";
    String DB_MIN_IDLE               = "database.min_idle";
    String DB_TESTONBORROW           = "database.testonborrow";
    String DB_TESTWHILEIDLE          = "database.testwhileidle";
    String DB_VALIDATION_QUERY       = "";
    String DB_MAX_ACTIVE             = "database.max_active";
    String DB_REWRITEBATCHSTATEMENTS = "rewriteBatchedStatements";
    String DB_SCHEMA                 = "SCHEMA";
}
