package com.cinchwallet.core.constant;

/**
 * <code>CUPD8583Constants</code> contains the hard coded values of the MTI
 * and processing codes used at Processor End, in the application. If some new
 * MTI/Processing Code is added at processor side, it should be added in this
 * file and then referred from here in code.
 */
public interface ISO8583_1987_Constant {

    // Message Type Indicator as per ISO 8583 1987 Specification
    String ACTIVATION_REQUEST_MTI                                   = "0200";
    String ACTIVATION_RESPONSE_MTI                                  = "0210";

    String DEACTIVATION_REQUEST_MTI                                 = "0302";
    String DEACTIVATION_RESPONSE_MTI                                = "0312";

    String FIXED_LOAD_REQUEST_MTI                                   = "0200";
    String FIXED_LOAD_RESPONSE_MTI                                  = "0210";

    String VARIABLE_LOAD_REQUEST_MTI                                = "0200";
    String VARIABLE_LOAD_RESPONSE_MTI                               = "0210";

    String ACTIVATION_LOAD_REQUEST_MTI                              = "0200";
    String ACTIVATION_LOAD_RESPONSE_MTI                             = "0210";

    String REDUMPTION_REQUEST_MTI                                   = "0200";
    String REDUMPTION_RESPONSE_MTI                                  = "0210";

    String BALANCE_INQUIRY_REQUEST_MTI                              = "0200";
    String BALANCE_INQUIRY_RESPONSE_MTI                             = "0210";

    String NETWORK_MESSAGE_REQUEST_MTI                              = "0800";
    String NETWORK_MESSAGE_RESPONSE_MTI                             = "0810";

    String REVERSAL_REQUEST_MTI                                     = "0420";
    String REVERSAL_RESPONSE_MTI                                    = "0430";

    String LOAD_REVERSAL_REQUEST_MTI                                = "0200";
    String LOAD_REVERSAL_RESPONSE_MTI                               = "0210";

    String VOID_REQUEST_MTI                                         = "0200";
    String VOID_RESPONSE_MTI                                        = "0210";

    // Added for Huateng - Start
    String HUATENG_BALANCE_INQUIRY_REQUEST_MTI                      = "0200";
    String HUATENG_BALANCE_INQUIRY_RESPONSE_MTI                     = "0210";

    String SALE_REQUEST_MTI                                         = "0200";
    String SALE_RESPONSE_MTI                                        = "0210";

    String CARD_SELLING_REQUEST_MTI                                 = "0100";
    String CARD_SELLING_RESPONSE_MTI                                = "0110";

    String VALUE_REQUEST_MTI                                        = "0200";
    String VALUE_RESPONSE_MTI                                       = "0210";

    String AVOID_REQUEST_MTI                                        = "0200";
    String AVOID_RESPONSE_MTI                                       = "0210";

    String HUATENG_REVERSAL_REQUEST_MTI                             = "0220";
    String HUATENG_REVERSAL_RESPONSE_MTI                            = "0230";

    String HUATENG_SALE_REVERSAL_REQUEST_MTI                        = "0400";
    String HUATENG_SALE_REVERSAL_RESPONSE_MTI                       = "0410";

    String HUATENG_VOID_REVERSAL_REQUEST_MTI                        = "0400";
    String HUATENG_VOID_REVERSAL_RESPONSE_MTI                       = "0410";

    String REFUND_GOOD_REQUEST_MTI                                  = "0220";
    String REFUND_GOOD_RESPONSE_MTI                                 = "0230";

    String PRE_AUTH_REQUEST_MTI                                     = "0100";
    String PRE_AUTH_RESPONSE_MTI                                    = "0110";

    String PRE_AUTH_COMPLT_REQUEST_MTI                              = "0200";
    String PRE_AUTH_COMPLT_RESPONSE_MTI                             = "0210";

    String SALE_PC                                                  = "000000";
    String CARD_SELLING_PC                                          = "500000";
    String VALUE_PC                                                 = "210000";
    String BALANCE_INQUIRY_PC                                       = "310000";
    String AVOID_PC                                                 = "200000";
    String REFUND_GOOD_PC                                           = "200000";
    String PRE_AUTH_PC                                              = "030000";
    String PRE_AUTH_COMPLT_PC                                       = "000000";
    // Added for Huateng - End

    // Processing Code as per ISO 8583 2003 Specification
    String CUPD8583_ACTIVATION_PC                                   = "710000";
    String CUPD8583_DEACTIVATION_PC                                 = "730000";
    String CUPD8583_FIXED_LOAD_PC                                   = "740000";
    String CUPD8583_VARIABLE_LOAD_PC                                = "740000";
    String CUPD8583_ACTIVATION_LOAD_PC                              = "710000";
    String CUPD8583_REDUMPTION_PC                                   = "000000";
    String CUPD8583_BALANCE_INQUIRY_PC                              = "300000";
    String CUPD8583_VOID_PC                                         = "200000";
    String CUPD8583_LOAD_CANCEL_PC                                  = "780000";

    String SENDER_ID                                                = "11111111111";

    // Error Response Code

    String ERROR_SYSTEM_ERROR_RESPONSE_CODE                         = "86";
    String ERROR_UNSUPPORTED_TRANSACTION_FOR_TERMINAL_RESPONSE_CODE = "87";
    String ERROR_UNSUPPORTED_TRANSACTION_FOR_MERCHANT_RESPONSE_CODE = "88";
    String ERROR_INVALID_AMOUNT_RESPONSE_CODE                       = "89";
    String ERROR_INVALID_CARD_NUMBER_RESPONSE_CODE                  = "90";
    String ERROR_INVALID_CARD_EXPIRY_RESPONSE_CODE                  = "91";
    String ERROR_UNKNOWN_MERCHANT_RESPONSE_CODE                     = "92";
    String ERROR_UNKNOWN_TERMINAL_RESPONSE_CODE                     = "93";
    String ERROR_BAD_DATA_RESPONSE_CODE                             = "94";
    String ERROR_TIMEOUT_RESPONSE_CODE                              = "95";
    String ERROR_INVALID_RESPONSE_CODE                              = "96";
    String ERROR_UNKNOW_RESPONSE_CODE                               = "97";
    String ERROR_VELOCITY_RESPONSE_CODE                             = "98";
    String ERROR_HARD_DECLINE_CODE                                  = "99";
    String SYSTEM_RESPONSE_CODE                                     = "00";

    String ERR_SEVRITY                                              = "01";

    // CUPD RESPONSE CODE as per CUPD ISO 8583 1987 Specification

    // CUPD Constants fields Added.

    int    FIELD_BITMAP                                             = 1;
    int    FIELD_PAN                                                = 2;
    int    FIELD_PROCESSING_CODE                                    = 3;
    int    FIELD_TR_AMOUNT                                          = 4;
    int    FIELD_AMOUNT_SETTLEMENT                                  = 5;
    int    FIELD_AMOUNT_CARDHOLDER_BILLING                          = 6;
    int    FIELD_TRANSMISSION_DT_TIM                                = 7;
    int    FIELD_AMOUNT_CARDHOLDER_BILLING_FEE                      = 8;
    int    FIELD_STAN                                               = 11;
    int    FIELD_TRANSACTION_TIM                                    = 12;
    int    FIELD_TRANSACTION_DT                                     = 13;
    int    FIELD_EXP                                                = 14;
    int    FIELD_DATE_OF_SETTLEMENT                                 = 15;
    int    FIELD_CAPTURE_DT                                         = 17;
    int    FIELD_MERCHANT_TYPE                                      = 18;
    int    FIELD_ACQ_COUNTRY_CODE                                   = 19;
    int    FIELD_POS_DATA_CODE                                      = 22;
    int    FIELD_POS_CONDITION_CODE                                 = 25;
    int    FIELD_POS_PIN_CAPTURE_CODE                               = 26;
    int    FIELD_CARD_SEQ_NUM                                       = 23;
    int    FIELD_ACQID                                              = 32;
    int    FIELD_SENDER_ID                                          = 33;
    int    FIELD_TRACK2_DATA                                        = 35;
    int    FIELD_RETRIVAL_REF_NUM                                   = 37;
    int    FIELD_AUTH_ID_RESPONSE                                   = 38;
    int    FIELD_RESPONSE_CODE                                      = 39;
    int    FIELD_CATID                                              = 41;
    int    FIELD_CATID_CODE                                         = 42;
    int    FIELD_CARD_ACCEPT0R_NAME_LOC                             = 43;
    int    FIELD_TRACK1_DATA                                        = 45;
    int    FIELD_CURRENCY_CODE_TNX                                  = 49;
    int    FIELD_CURRENCY_CODE_SETTLEMENT                           = 50;
    int    FIELD_CURRENCY_CODE_CARDHOLDER_BILLING                   = 51;
    int    FIELD_PIN_DATA                                           = 52;
    int    FIELD_SECURITY_CONTROL_INFORMATION                       = 53;
    int    FIELD_ADDITIONAL_AMOUNT                                  = 54;
    int    FIELD_SELF_DETERMINED                                    = 60;
    // first
    // 4
    // byte
    // is
    // reserved
    // for
    // REASON_CODE;
    int    FIELD_ORIGINAL_MESSAGE                                   = 61;
    int    FIELD_FUNCTION_CODE                                      = 70;
    int    FIELD_ORIGINAL_DATA                                      = 90;
    int    FIELD_RECEIVING_INST_ID_CODE                             = 100;
    int    FIELD_FILE_NAME                                          = 101;
    int    FIELD_ACC_ID1                                            = 102;
    int    FIELD_ACC_ID2                                            = 103;
    int    FIELD_ACQUIRING_INSTITUTION_RESERVED                     = 122;
    int    FIELD_MESSAGE_AUTHENTICATION_CODE                        = 128;

    // FOLLOWING FIELDS ARE NOT AVAILABLE IN 1987

    int    FIELD_DISPLAY_MESSAGE                                    = 63;
    int    FIELD_MAC                                                = 64;
    int    FIELD_UPC                                                = 62;

    String FIELD_CAD_BITMAP                                         = "43.0";
    String FIELD_CARD_ACCEPTOR_NAME                                 = "43.2";
    String FIELD_CARD_ACCEPTOR_ADDR                                 = "43.3";
    String FIELD_CARD_ACCEPTOR_CITY                                 = "43.4";
    String FIELD_CARD_ACCEPTOR_STATE                                = "43.5";
    String FIELD_CARD_ACCEPTOR_POSTAL_CODE                          = "43.6";
    String FIELD_CARD_ACCEPTOR_COUNTRY_CODE                         = "43.7";
    String FIELD_CARD_ACCEPTOR_PHONE                                = "43.8";
    String FIELD_CARD_ACCEPTOR_CUST_SR_PHONE                        = "43.9";
    String FIELD_CARD_ACCEPTOR_ADDITIONAL_INFO                      = "43.10";
    String FIELD_CARD_ACCEPTOR_URL                                  = "43.11";
    String FIELD_CARD_ACCEPTOR_EMAIL                                = "43.12";

}
