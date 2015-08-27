package com.cinchwallet.core.constant;

/**
 * <code>ISO8583_2003_SpecConstant</code> contains the hard coded values of
 * the MTI and processing codes used at Acquirer End, in the application. If
 * some new MTI/Processing Code is added at acquirer side, it should be added in
 * this file and then referred from here in code.
 */
public interface ISO8583_2003_SpecConstant {

    int    FIELD_BITMAP                         = 1;
    int    FIELD_PAN                            = 2;
    int    FIELD_PROCESSING_CODE                = 3;
    int    FIELD_TR_AMOUNT                      = 4;
    int    FIELD_AMOUNT_CARDHOLDER_BILLING      = 6;
    int    FIELD_TRANSMISSION_DT_TIM            = 7;
    int    FIELD_AMOUNT_CARDHOLDER_BILLING_FEE  = 8;
    int    FIELD_STAN                           = 11;
    int    FIELD_TRANSACTION_DT_TIM             = 12;
    int    FIELD_EXP                            = 14;
    int    FIELD_DATE_OF_SETTLEMENT             = 15;
    int    FIELD_DT_EFFECTIVE                   = 13;
    int    FIELD_CAPTURE_DT                     = 17;
    int    FIELD_MESSAGE_ERROR_INDICATOR        = 18;
    int    FIELD_TR_LIFE_CYCLE_ID               = 21;
    int    FIELD_POS_DATA_CODE                  = 22;
    int    FIELD_CARD_SEQ_NUM                   = 23;
    int    FIELD_FUNCTION_CODE                  = 24;
    int    FIELD_REASON_CODE                    = 25;
    int    FIELD_MERCHANT_CAT_CODE              = 26;
    int    FIELD_AMOUNT_ORIGINAL                = 30;
    int    FIELD_ACQID                          = 32;
    int    FIELD_TRACK2_DATA                    = 35;
    int    FIELD_RETRIVAL_REF_NUM               = 37;
    int    FIELD_APPROVAL_CODE                  = 38;
    int    FIELD_RESULT_CODE                    = 39;
    int    FIELD_SERVICE_CODE                   = 40;
    int    FIELD_CATID                          = 41;
    int    FIELD_CATID_CODE                     = 42;
    int    FIELD_CARD_ACCEPT0R_NAME_LOC         = 43;
    int    FIELD_TRACK1_DATA                    = 45;
    int    FIELD_COUPON_CODE                    = 46;
    int    FIELD_ADDITIONAL_DATA                = 48;
    int    FIELD_VERIFICATION_CODE              = 49;
    int    FIELD_PIN_DATA                       = 52;
    int    FIELD_SECURITY_CONTROL_INFO          = 53;
    int    FIELD_ADDITIONAL_AMOUNT              = 54;
    int    FIELD_ORIGINAL_DATA                  = 56;
    int    FIELD_TRANSPORT_DATA                 = 59;
    int    FIELD_UPC                            = 62;
    int    FIELD_DISPLAY_MESSAGE                = 63;
    int    FIELD_FILE_TRANSFER_MSG_CONTROL_DATA = 68;
    int    FIELD_FILE_TRANSFER_CONTROL_DATA     = 69;
    int    FIELD_FILE_TRANSFER_DESC_DATA        = 70;
    int    FIELD_DATA_RECORD                    = 72;
    int    FIELD_CARD_ISSUER_REF_DATA           = 95;
    int    FIELD_FILE_NAME                      = 101;
    int    FIELD_ACC_ID1                        = 102;
    int    FIELD_ACC_ID2                        = 103;
    int    FIELD_DISCRETIONARY_USER_DATA        = 111;
    int    FIELD_RESERVE_FIELD                  = 123;

    // TODO - This needs to be check. in which field we can send the data of
    // field 60 and 61 in case of void and reversal for HT.
    // One way is to take the data of F60 and F61 in F56 from terminal and
    // then
    // break in switch code.
    int    FIELD_RESERVED_PRIVATE               = 60;

    String FIELD_CAD_BITMAP                     = "43.0";
    String FIELD_CARD_ACCEPTOR_NAME             = "43.2";
    String FIELD_CARD_ACCEPTOR_ADDR             = "43.3";
    String FIELD_CARD_ACCEPTOR_CITY             = "43.4";
    String FIELD_CARD_ACCEPTOR_STATE            = "43.5";
    String FIELD_CARD_ACCEPTOR_POSTAL_CODE      = "43.6";
    String FIELD_CARD_ACCEPTOR_COUNTRY_CODE     = "43.7";
    String FIELD_CARD_ACCEPTOR_PHONE            = "43.8";
    String FIELD_CARD_ACCEPTOR_CUST_SR_PHONE    = "43.9";
    String FIELD_CARD_ACCEPTOR_ADDITIONAL_INFO  = "43.10";
    String FIELD_CARD_ACCEPTOR_URL              = "43.11";
    String FIELD_CARD_ACCEPTOR_EMAIL            = "43.12";

    // Message Type Indicator as per ISO 8583 2003 Specification

    String LOYALTY_NUMBER_INQUIRY_REQUEST_MTI   = "2100";
    String LOYALTY_NUMBER_INQUIRY_RESPONSE_MTI  = "2110";

    String NETWORK_MESSAGE_REQUEST_MTI          = "2800";
    String NETWORK_MESSAGE_RESPONSE_MTI         = "2810";

    String LOYALTY_NUMBER_INQUIRY_PC            = "300040";

}
