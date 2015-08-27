package com.cinchwallet.core.constant;

/**
 * <code>SwitchConstants</code> contains the entire hard coded/string literal
 * values used in the switch application. This is used to remove hard coding in
 * code. To change hard coded value, it require to modify value here only.
 * <p>
 * Keys used in the property files should also be defined here and any changes
 * in the property file would require to put the same key name in this file.
 * <p>
 * e.g. DB_URL represents the key name database.url in the database.properties.
 *
 */
public interface SwitchConstants {
    String PROCESOR_REQUEST                                            = "PROCESOR_REQUEST";
    String PROCESOR_RESPONSE                                           = "PROCESOR_RESPONSE";
    String SPECIFICATION_NAME                                          = "SPECIFICATION_NAME";
    String TXNNAME                                                     = "TXNNAME";
    String REQUEST                                                     = "REQUEST";
    String ISO_REQUEST                                                 = "ISOREQUEST";
    String ISOSOURCE                                                   = "ISOSOURCE";
    String RESPONSE                                                    = "RESPONSE";
    String FAILOVER_STATUS                                             = "FAILOVER_STATUS";
    String ISSUER_DISCONNECTED                                         = "ISSUER_DISCONNECTED";
    String YES                                                         = "YES";
    String GROUP_UNKNOWN                                               = "unknown";
    String GROUP_ISSUER_DISCONNECTED                                   = "issuerDisconnected";
    String INVALID_BIN_FOUND                                           = "INVALID_BIN_FOUND";
    String GROUP_INVALID_BIN_FOUND                                     = "invalidBinFound";
    String INVALID_REQUEST                                             = "INVALID_REQUEST";
    String PCODE                                                       = "PCODE";
    String PCODE_TXN_TYPE                                              = "PCODE_TXN_TYPE";
    String PCODE_ACCOUNT_TYPE                                          = "PCODE_ACCOUNT_TYPE";
    String PCODE_ACCOUNT2_TYPE                                         = "PCODE_ACCOUNT2_TYPE";
    String ADDITIONAL_AMOUNT                                           = "PCODE_ACCOUNT_TYPE";
    String INVALID_AMOUNT                                              = "INVALID_AMOUNT";
    String NETWORK_CAPTURE_DATE                                        = "NETWORK_CAPTURE_DATE";
    String LOCAL_TRANSACTION_TIMESTAMP                                 = "LOCAL_TRANSACTION_TIMESTAMP";
    String ORIGINAL_MTI                                                = "ORIGINAL_MTI";
    String ORIGINAL_STAN                                               = "ORIGINAL_STAN";
    String ORIGINAL_TIMESTAMP                                          = "ORIGINAL_TIMESTAMP";
    String TRANSMISSION_TIMESTAMP                                      = "TRANSMISSION_TIMESTAMP";
    String NETWORKMIT                                                  = "2800";
    String ISO8583_RAPS_RESPONSE_CODE                                  = "39";
    String ISO8583_RAPS_RESPONSE_CODE_APPROVED                         = "0000";
    String DOT                                                         = ".";
    String DASH                                                        = "-";
    String SPACE_PROP                                                  = "space";
    String QUEUE_PROP                                                  = "queue";
    String RESPONSE_TIMEOUT_PROP                                       = "response-timeout";
    String READ_QUEUE_PROP                                             = "readqueue";
    String MUX_PROP                                                    = "mux";
    String TIMEOUT_PROP                                                = "timeout";
    String SESSION_PROP                                                = "sessions";
    String MANDATORY_PROP                                              = "MANDATORY";
    String OPTIONAL_PROP                                               = "OPTIONAL";
    String DB_LOGGING_THREADS                                          = "db-logging-threads";
    String SOURCE_TYPE                                                 = "SOURCETYPE";
    String SOURCE_TYPE_HOST                                            = "host";
    String SOURCE_TYPE_DEVICE                                          = "device";
    String OUT_BOUND_CHANNELS                                          = "out-bound-channels";
    String IN_BOUND_CHANNELS                                           = "in-bound-channels";
    String DEVICE_MODEL_TYPE                                           = "device-type-";
    String WEB_TERMINAL_TYPE                                           = "device-type-6";

    String TID                                                         = "TID";
    String MID                                                         = "MID";
    String COMMA                                                       = ",";
    String PAD_ZERO                                                    = "0";
    String REPLACE_LEG                                                 = "LEG";

    /**
         * Error Message and error codes values assigned for the time being with
         * best guess could be changed later on if required.
         */

    String ERROR_SYSTEM_ERROR_RESPONSE_CODE                            = "8001";
    String ERROR_SYSTEM_ERROR_MESSAGE                                  = "System Error";

    String ERROR_UNSUPPORTED_TRANSACTION_FOR_TERMINAL_RESPONSE_CODE    = "8002";
    String ERROR_UNSUPPORTED_TRANSACTION_FOR_TERMINAL_RESPONSE_MESSAGE = "Txn not supported at Termnl";              // Terminal
    // doesn't
    // support
    // this
    // transaction

    String ERROR_UNSUPPORTED_TRANSACTION_FOR_MERCHANT_RESPONSE_CODE    = "8003";
    String ERROR_UNSUPPORTED_TRANSACTION_FOR_MERCHANT_RESPONSE_MESSAGE = "Txn not supported at Mrchnt";              // Merchant
    // doesn't
    // support
    // this
    // transaction

    String ERROR_INVALID_AMOUNT_RESPONSE_CODE                          = "8004";
    String ERROR_INVALID_AMOUNT_RESPONSE_MESSAGE                       = "Invalid amount";

    String ERROR_INVALID_CARD_NUMBER_RESPONSE_CODE                     = "8005";
    String ERROR_INVALID_CARD_NUMBER_RESPONSE_MESSAGE                  = "Invalid card number";

    String ERROR_INVALID_CARD_EXPIRY_RESPONSE_CODE                     = "8006";
    String ERROR_INVALID_CARD_EXPIRY_RESPONSE_MESSAGE                  = "Invalid card expiry date";

    String ERROR_UNKNOWN_TERMINAL_RESPONSE_CODE                        = "8007";
    String ERROR_UNKNOWN_TERMINAL_RESPONSE_MESSAGE                     = "Unknown terminal id";

    String ERROR_UNKNOWN_MERCHANT_RESPONSE_CODE                        = "8008";
    String ERROR_UNKNOWN_MERCHANT_RESPONSE_MESSAGE                     = "Unknown merchant id";

    String ERROR_UNKNOWN_ACQUIER_RESPONSE_CODE                         = "8009";
    String ERROR_UNKNOWN_ACQUIER_RESPONSE_MESSAGE                      = "Unknown Acquirer id";

    String ERROR_BAD_DATA_RESPONSE_CODE                                = "8010";
    String ERROR_BAD_DATA_RESPONSE_MESSAGE                             = "Bad data in request";

    String ERROR_TIMEOUT_RESPONSE_CODE                                 = "8011";
    String ERROR_TIMEOUT_RESPONSE_MESSAGE                              = "Timeout";

    String ERROR_INVALID_RESPONSE_CODE                                 = "8012";
    String ERROR_INVALID_RESPONSE_MESSAGE                              = "Invalid response code";

    String ERROR_VELOCITY_RESPONSE_CODE                                = "8013";
    String ERROR_VELOCITY_RESPONSE_MESSAGE                             = "Velocity check failed ";

    String ERROR_INVALID_TRANSACTION_CODE                              = "8014";
    String ERROR_INVALID_TRANSACTION_MESSAGE                           = "Invalid Trancation";

    String ERROR_INVALID_DATA_CODE                                     = "8015";
    String ERROR_INVALID_DATA_MESSAGE                                  = "Invalid Request";

    String ERROR_UNSUPPORTED_CURRENCY_FOR_MERCHANT_RESPONSE_CODE       = "8016";
    String ERROR_UNSUPPORTED_CURRENCY_FOR_MERCHANT_RESPONSE_MESSAGE    = "Unsupported Mrchnt currency";              // Merchant
    // doesn't
    // support
    // this
    // currency

    String ERROR_UNSUPPORTED_TERMINAL_FOR_MERCHANT_RESPONSE_CODE       = "8017";
    String ERROR_UNSUPPORTED_TERMINAL_FOR_MERCHANT_RESPONSE_MESSAGE    = "No Termnl Mrchnt assocation ";             // Terminal
    // is
    // not
    // attached
    // with
    // this
    // merchant.

    String ERROR_LOGON_FAILURE_RESPONSE_CODE                           = "8018";
    String ERROR_LOGON_FAILURE_RESPONSE_MESSAGE                        = "Terminal not logged on";

    String ERROR_HARD_DECLINE_CODE                                     = "9999";
    String ERROR_HARD_DECLINE_MESSAGE                                  = "Transaction declined";

    String SYSTEM_RESPONSE_CODE                                        = "0000";
    String SYSTEM_DISPLAY_MESSAGE                                      = "Transaction successful";

    String ERROR_UNKNOW_RESPONSE_CODE                                  = "0911";
    String ERROR_UNKNOW_RESPONSE_MESSAGE                               = "Unknown response code";

    String ERROR_MSG                                                   = "ErrorMsg";
    String CONTEXT_MISMATCH                                            = "ContextMismatch";
    String CONTEXT_MISMATCH_MSG                                        = "invalidate Context";
    String RESPONSE_MISMATCH_MSG                                       = "Request Response Mismatch";
    /**
         * Fields defined for use in message validation. NOTE : DONOT CHANGE THE
         * NAME OF VARIABLE OR THEIR VALUE AS USED BY REFLECTION TO VALIDATE
         * CORRESPONDING FIELD DATA.
         */

    String PROCESSING_CODE                                             = "ProcessingCode";
    String TRANSMISSION_DT_TIM                                         = "TransmissionDateTime";
    String STAN                                                        = "STAN";
    String TRANSACTION_DT_TIM                                          = "TransactionDateTime";
    String DT_EFFECTIVE                                                = "DateEffective";
    String TR_AMOUNT                                                   = "TransactionAmount";
    String PAN                                                         = "Pan";
    String REASON_CODE                                                 = "ReasonCode";
    String APPROVAL_CODE                                               = "ApprovalCode";
    String CATID                                                       = "CardAccepTermId";
    String ORIGINAL_DATA                                               = "OriginalData";
    String CAPTURE_DT                                                  = "CaptureDate";
    String FUNCTION_CODE                                               = "FunctionCode";
    String ACQID                                                       = "AcqId";
    String RETRIVAL_REF_NUM                                            = "RetrivalRefNum";
    String CATID_CODE                                                  = "CardAccepIdentificationCode";
    String CARD_ACCEPT0R_NAME_LOC                                      = "CardAcceptorNameLoc";
    String AMOUNT_FEE                                                  = "AmountFee";
    String DISPLAY_MESSAGE                                             = "DisplayMessage";
    String DISCRETIONARY_USER_DATA                                     = "DiscretionaryUserData";
    // new validation fields
    String UPC                                                         = "Upc";
    String EXPIRE_DATE                                                 = "ExpireDate";
    String POS_DATA_CODE                                               = "PosDataCode";
    String MERCHANT_CAT_CODE                                           = "MerchantCatCode";
    String TRACK2_DATA                                                 = "Track2Data";
    String TRACK1_DATA                                                 = "Track1Data";
    String PIN_DATA                                                    = "PinData";
    String SECURITY_CONTROL_INFO                                       = "SecurityRelatedControlInfo";
    String FILE_TRANS_MSG_CONTROL_DATA                                 = "FileTransMsgControlData";

    String MESSAGE_VALIDATE_SUCCESS                                    = "Message Validated Successfully";
    String NO_RESPONSE_RECEIVE                                         = "Acquirer did not get response";
    String NO_ISSUER_RESPONSE_RECEIVE                                  = "Issuer Response doesn't received";
    String NO_ISSUER__REV_RESPONSE_RECEIVE                             = "Issuer Reversal Response didn't received.";
    String LOGON_VALIDATE_SUCCESS                                      = "Logon Status Validated Successfully";
    String ISSUER_STATUS_DISCONNECTED                                  = "Issuer Connection status :: Disconnected";
    String ISSUER_STATUS_CONNECTED                                     = "Issuer Connection status :: Connected";
    String INVALID_BIN_EXIST                                           = "Invalid Bin Information Exist";

    int    PROCESSING_CODE_LEN                                         = 6;
    int    TRANSMISSION_DT_TIM_LEN                                     = 10;
    int    STAN_LEN                                                    = 12;
    int    TRANSACTION_DT_TIM_LEN                                      = 14;
    int    DT_EFFECTIVE_LEN                                            = 6;
    int    PAN_LEN_19                                                  = 19;
    int    PAN_LEN_16                                                  = 16;
    int    REASON_CODE_LEN                                             = 4;
    int    APPROVAL_CODE_LEN                                           = 6;
    int    ORIGINAL_DATA_LEN                                           = 30;
    int    ORIGINAL_DATA_LEN_VOID_REV                                  = 41;
    int    FUNCTION_CODE_LEN                                           = 3;
    int    ACQID_LEN                                                   = 11;
    int    MTI_LEN                                                     = 4;
    int    POS_DATA_CODE_LEN                                           = 16;
    int    MERCHANT_CAT_CODE_LEN                                       = 4;
    int    CATID_LEN                                                   = 11;
    int    CATID_CODE_LEN                                              = 11;
    int    EXPIRE_DATE_LEN                                             = 4;
    int    TRACK2_DATA_LEN                                             = 37;
    int    RETRIVAL_REF_NUM_LEN                                        = 12;
    int    TRACK1_DATA_LEN                                             = 76;
    int    PIN_DATA_LEN                                                = 8;
    int    UPC_LEN_12                                                  = 12;
    int    UPC_LEN_13                                                  = 13;
    int    COUNTRY_CODE_LEN                                            = 3;


    // For Error message Indicator Fields Error Codes
    String MEI_SEVERITY_ERROR_CODE                                     = "00";
    String MEI_SEVERITY_WARNING_CODE                                   = "01";
    String MEI_SUB_ELEMENT_ERROR_CODE                                  = "00";
    String MEI_DATASET_IDETIFIER_ERROR_CODE                            = "0";
    String MEI_DATA_BIT_ERROR_CODE                                     = "00";

    // For Error message Indicator Fields Message Error Codes
    String MEI_UNKNOWN_ERROR                                           = "0000";
    String MEI_UNKNOWN_ELEMENT                                         = "000";
    String MEI_UNKNOWN_SUB_ELEMENT                                     = "00";
    String MEI_UNKNOWN_DATA_SET_IDENTIFIER                             = "0";
    String MEI_UNKNOWN_DATA_BIT_IN_ERROR                               = "00";
    String MEI_REQUIRED_DATA_ELEMENT_MISSING                           = "0001";
    String MEI_INVALID_LENGTH                                          = "0002";
    String MEI_INVALID_VALUE                                           = "0003";
    String MEI_AMOUNT_FORMAT_ERROR                                     = "0004";
    String MEI_DATE_FORMAT_ERROR                                       = "0005";
    String MEI_ACCOUNT_FORMAT_ERROR                                    = "0006";
    String MEI_NAME_FORMAT_ERROR                                       = "0007";
    String MEI_OTHER_FORMAT_ERROR                                      = "0008";
    String MEI_DATA_INCONSISTENT_WITH_POS_DATA_CODE                    = "0009";
    String MEI_INCONSISTENT_DATA_DOES_NOT_MATCH_ORIGINAL_REQUEST       = "0010";
    String MEI_OTHER_INCONSISTENT_DATA                                 = "0011";
    String MEI_RECURRING_DATA_ERROR                                    = "0012";
    String MEI_CUSTOMER_VENDOR_FORMAT_ERROR                            = "0013";
    String MEI_INVALID_MTI                                             = "6001";
    String MEI_INVALID_PROCESSING_CODE                                 = "6002";
    String MEI_TIME_OUT_OCCUR                                          = "6003";
    String MEI_VALIDATION_FAILED                                       = "6004";
    String MEI_TERMINAL_TXN_TYPE_NOT_SUPP                              = "6005";
    String MEI_MERCHANT_TXN_TYPE_NOT_SUPP                              = "6006";

    String MEI_FIELD_ERROR_POSITION_MTI                                = "000";
    String MEI_FIELD_ERROR_POSITION_PCODE                              = "003";
    String MEI_FIELD_ERROR_POSITION_TERMINALID                         = "041";
    String MEI_FIELD_ERROR_POSITION_MERCHANTID                         = "042";

    // Processing Code Mapping with DB Transaction Type fields

    String DB_ACTIVATION_TYPE_CODE                                     = "ACTVN";
    String DB_DEACTIVATION_TYPE_CODE                                   = "DACTN";
    String DB_FIXED_LOAD_TYPE_CODE                                     = "LOADF";
    String DB_VARIABLE_LOAD_TYPE_CODE                                  = "LOADV";
    String DB_ACTIVATION_LOAD_TYPE_CODE                                = "ACTNL";
    String DB_REDUMPTION_TYPE_CODE                                     = "REDMP";
    String DB_BALANCE_INQUIRY_TYPE_CODE                                = "BALIQ";
    String DB_VOID_TYPE_CODE                                           = "VOIDX";
    String DB_REVERSAL_TYPE_CODE                                       = "RVSAL";

    // for velocity
    String ENTITY_MERCHANT_CODE                                        = "MCHNT";
    String ENTITY_TERMINAL_CODE                                        = "TRMNL";

    String HOURLY                                                      = "HOURLY";
    String DAILY                                                       = "DAILY";
    String WEEKLY                                                      = "WEEKLY";
    String MONTHLY                                                     = "MONTHLY";
    String YEARLY                                                      = "YEARLY";

    String COUNT                                                       = "COUNT";
    String AMOUNT                                                      = "AMOUNT";

    String MERCHANT                                                    = "MerchantId";
    String TERMINAL                                                    = "TerminalId";
    String VALUE_TRUE                                                  = "TRUE";
    String VALUE_FALSE                                                 = "FALSE";
    int    MERCHANT_ENTITY_ID                                          = 1;
    int    TERMINAL_ENTITY_ID                                          = 3;

    // Monitoring constants

    String STATUS_CONNECTED                                            = "Connected";
    String STATUS_DISCONNECTED                                         = "DisConnected";

    // bin Routnig

    String VALID_BIN                                                   = "validBin";

    String SUCCESS                                                     = "000";
    String DISPLAY_MSG_SUCCESS                                         = "Txn completed successfully";
    String ERROR                                                       = "8888";

    String FUN_CODE_LOYALTY_NUMBER_INQUIRY                                            = "108";

    String DISPLAY_MSG_TERMINAL_LOGON                                  = "Terminal logged on";
    String DISPLAY_MSG_TERMINAL_LOGOFF                                 = "Terminal logged off";
    String ERROR_TERMINAL_ALREADY_LOGON                                = "8019";
    String DISPLAY_MSG_TERMINAL_ALREADY_LOGON                          = "Terminal already logged on";
    String ERROR_TERMINAL_ALREADY_LOGOFF                               = "8020";
    String DISPLAY_MSG_TERMINAL_ALREADY_LOGOFF                         = "Terminal already logged off";
    String ERROR_ISSUER_DISCONNECTED                                   = "8021";
    String DISPLAY_MSG_ISSUER_DISCONNECTED                             = "Issuer Not Available";
    String ERROR_INVALID_MERCHANT_TERMINAL                             = "8022";
    String DISPLAY_MSG_INVALID_MERCHANT_TERMINAL                       = "Invalid Merchant/Terminal";
    String ERROR_INVALID_USERNAME_PASSWORD                             = "8023";
    String DISPLAY_MSG_INVALID_USERNAME_PASSWORD                       = "Invalid Authentication";
    String ERROR_NO_TERMINAL_CONFIG_EXIST                              = "8024";
    String DISPLAY_MSG_NO_TERMINAL_CONFIG_EXIST                        = "Terminal Config Not Exist";
    String ERROR_INVALID_DOWNLOAD_CONFIG_REQ                           = "8025";
    String DISPLAY_MSG_INVALID_DOWNLOAD_CONFIG_REQ                     = "Invalid Config Download Req";
    String ERROR_NO_DOWNLOAD_CONFIG_EXIST                              = "8026";
    String DISPLAY_MSG_NO_DOWNLOAD_CONFIG_EXIST                        = "No Config Download Exist";
    String ERROR_INVALID_PROV_REQ                                      = "8027";
    String DISPLAY_MSG_INVALID_PROV_REQ                                = "Invalid Provisioning Req";
    String ERROR_INVALID_BIN                                           = "8028";
    String DISPLAY_MSG_INVALID_BIN                                     = "Card not suported at Issuer";

    String DISPLAY_MSG_END_OF_TRANSMISSION                             = "End of Transmission";

    String LOGON_STATUS_LOGGED_ON                                      = "ON";
    String LOGON_STATUS_LOGGED_OFF                                     = "OFF";

    String DCT_CARD_ACCEPTOR_IDENTIFY_STR                              = "deactiv";
    String DEACTIVE_CUSTOM_RESPONSE_CODE                               = "9050";
    String DEACTIVE_CUSTOM_RESPONSE_MSG                                = "Used Card. No Deactivation";

    String INVALID_DATA_SIX_ZERO                                       = "000000";
    String INVALID_DATA_EIGHT_ZERO                                     = "00000000";

    // Terminal provisioning
    String FILE_NAME_TERMINAL_CONFIG                                   = "Config.txt";
    String FILE_NAME_USER_CONFIG                                       = "Users.txt";
    String FILE_SEQ_NUM_ONE                                            = "00000001";
    int    FILE_SEQ_NUM_TWO                                            = 2;

    String ACQUIRER_CURRENCY_VALUE                                     = "156";
    String ACQUIRER_AMOUNT_SCALE_VALUE                                 = "2";

}
