package com.cinchwallet.core.msg;

/**
 * <code>IMFResponseCodes</code> contains the IRC being used in the
 * application. For every scenario, either fail or pass some IRC is set in the
 * context object.
 * <p>
 * These IRC is mapped in against the user friendly message in the
 * oneinc_irc.properties. System takes the IRC and fetches the corresponding
 * message from the oneinc_irc.properties and includes in the response to the
 * client.
 * <p>
 *
 * Any constant declared here must be mapped in the oneinc_irc.properties for
 * showing appropriate message to the client corresponding to that IRC.
 *
 */
public class IMFResponseCodes {

    // APPROVALS (000 - 010)
    public static final String APPROVED_TXN                                                = "000";

    // SOFT DECLINES (010 - 100)
    public static final String ACQUIRER_TIMEOUT                                            = "072";
    public static final String PROCESSOR_DOWN                                              = "073";
    public static final String PROCESSOR_NO_RESPONSE                                       = "074";
    public static final String PROCESSOR_SYSTEM_ERROR                                      = "075";
    public static final String SYSTEM_ERROR                                                = "076";
    public static final String SERVER_BUSY                                                 = "077";

    // HARD DECLINES
    public static final String UNKNOWN_TRANSACTION_TYPE                                    = "301";
    public static final String INVALID_BIN                                                 = "302";
    public static final String INVALID_AUTHENTICATION                                      = "303";
    public static final String MISSING_PROCESSING_CODE                                     = "304";
    public static final String MISSING_TRANSMISSION_DATETIME                               = "305";
    public static final String MISSING_STAN                                                = "306";
    public static final String INV_PROCESSING_CODE                                         = "307";
    public static final String INV_TRANSMISSION_DATETIME                                   = "308";
    public static final String INV_STAN                                                    = "309";
    public static final String MISSING_LOCAL_TRANSACTION_DATE                              = "310";
    public static final String INV_LOCAL_TRANSACTION_DATE                                  = "311";
    public static final String MISSING_TRANSACTION_AMOUNT                                  = "312";
    public static final String MISSING_PAN                                                 = "313";
    public static final String INVALID_PAN                                                 = "314";
    public static final String MISSING_TERMINAL_ID                                         = "315";
    public static final String INV_TERMINAL_ID                                             = "316";
    public static final String MISSING_FUNCTION_CODE                                       = "317";
    public static final String INVALID_FUNCTION_CODE                                       = "318";
    public static final String MISSING_ACQ_INST_ID                                         = "319";
    public static final String INV_ACQ_INST_ID                                             = "320";
    public static final String RETRIVAL_REFRENCE_NBR_GENERATOR_ERROR                       = "321";
    public static final String MISSING_RETRIVAL_REFRENCE_NBR                               = "322";
    public static final String INV_RETRIVAL_REFRENCE_NBR                                   = "323";
    public static final String MISSING_MERCHANT_ID                                         = "324";
    public static final String INV_MERCHANT_ID                                             = "325";
    public static final String MISSING_MERCHANT_LOCATION                                   = "326";
    public static final String INV_MERCHANT_LOCATION                                       = "327";
    public static final String INV_MTI                                                     = "328";
    public static final String MISSING_EXPIRE_DATE                                         = "329";
    public static final String MISSING_POS_ENTRY_MODE_CODE                                 = "330";
    public static final String INV_POS_ENTRY_MODE_CODE                                     = "331";
    public static final String MISSING_MERCHANT_CAT_CODE                                   = "332";
    public static final String INV_MERCHANT_CAT_CODE                                       = "333";
    public static final String MISSING_TRACK_2_DATA                                        = "334";
    public static final String INV_TRACK_2_DATA                                            = "335";
    public static final String MISSING_TRACK_1_DATA                                        = "336";
    public static final String INV_TRACK_1_DATA                                            = "337";
    public static final String MISSING_PIN_DATA                                            = "338";
    public static final String INVALID_PIN_DATA                                            = "339";
    public static final String MISSING_UPC                                                 = "340";
    public static final String INVALID_UPC                                                 = "341";
    public static final String MISSING_SECURITY_RELATED_CONTROL_INFO                       = "342";
    public static final String MISSING_FILE_TRANSFER_MESSAGE_CONTROL_DATA                  = "343";
    public static final String INVALID_TRANSACTION_AMOUNT                                  = "344";
    public static final String LOYALTY_NUMBER_NOT_FOUND                                    = "345";
    public static final String MERCHANT_INACTIVE                                           = "346";
    public static final String INV_ONEINC_NUMBER                                           = "347";
    public static final String ONEINC_NUMBER_UNASSIGNED                                    = "348";

    public static final String CRES_DC_REFER_TO_CARD_ISSER                                 = "501";
    public static final String CRES_DC_REFER_TO_CARD_ISSUER_SPECIAL                        = "502";
    public static final String CRES_DC_INVALID_MERCHANT                                    = "503";
    public static final String CRES_PK_PICK_UP                                             = "504";
    public static final String CRES_DC_DO_NOT_HONOR                                        = "505";
    public static final String CRES_DC_ERROR                                               = "506";
    public static final String CRES_PK_PICK_UP_SPECIAL                                     = "507";
    public static final String CRES_DC_REQUEST_IN_PROGRESS                                 = "509";
    public static final String CRES_AP_APPROVED_FOR_PARTIAL_AMOUNT                         = "510";
    public static final String CRES_AP_APPROVED_VIP                                        = "511";
    public static final String CRES_DC_INVALID_TXN                                         = "512";
    public static final String CRES_DC_INVALID_AMOUNT                                      = "513";
    public static final String CRES_DC_INVALID_CARD_NUMBER                                 = "514";
    public static final String CRES_DC_NO_SUCH_ISSUER                                      = "515";
    public static final String CRES_AP_APPROVED_UPDATE_TRACK_3                             = "516";
    public static final String CRES_DC_CUSTOMER_CANCELLATION                               = "517";
    public static final String CRES_DC_RE_ENTER_TRANSACTION                                = "519";
    public static final String CRES_DC_INVALID_RESPONSE                                    = "520";
    public static final String CRES_DC_NO_ACTION_TAKEN                                     = "521";
    public static final String CRES_DC_SUSPECTED_MALFUNCTION                               = "522";
    public static final String CRES_DC_UNACCEPTABLE_TRANSACTION_FEE                        = "523";
    public static final String CRES_DC_UNABLE_TO_LOCATE_ORIGINAL_TXN                       = "525";
    public static final String CRES_DC_FORMAT_ERROR                                        = "530";
    public static final String CRES_DC_BANK_NOT_SUPPORTED_BY_SWITCH                        = "531";
    public static final String CRES_DC_EXPIRED_CARD                                        = "533";
    public static final String CRES_PK_SUSPECTED_FRAUD                                     = "534";
    public static final String CRES_PK_CARD_ACCEPTOR_CONTACT_SECURITY                      = "535";
    public static final String CRES_PK_RESTRICTED_CARD                                     = "536";
    public static final String CRES_PK_CARD_ACCEPTOR_CALL_ACQUIRER_SECURITY                = "537";
    public static final String CRES_DC_ALLOWABLE_PIN_TRIES_EXCEEDED                        = "538";
    public static final String CRES_DC_NO_CREDIT_ACCOUNT                                   = "539";
    public static final String CRES_DC_REQUESTED_FUNCTION_NOT_SUPPORTED                    = "540";
    public static final String CRES_PK_LOST_CARD                                           = "541";
    public static final String CRES_DC_NO_UNIVERSAL_ACCOUNT                                = "542";
    public static final String CRES_PK_STOLEN_CARD_PICK_UP                                 = "543";
    public static final String CRES_DC_NO_INVESTMENT_ACCOUNT                               = "544";
    public static final String CRES_DC_NOT_SUFFICIENT_FUNDS                                = "551";
    public static final String CRES_DC_NO_CHECKING_ACCOUNT                                 = "552";
    public static final String CRES_DC_NO_SAVING_ACCOUNT                                   = "553";
    public static final String CRES_DC_EXPIRED_CARD_ERROR                                  = "554";
    public static final String CRES_DC_INCORRECT_PERSONAL_ID_NUMBER                        = "555";
    public static final String CRES_DC_NO_CARD_RECORD                                      = "556";
    public static final String CRES_DC_TXN_NOT_PERMITTED_TO_CARDHOLDER                     = "557";
    public static final String CRES_DC_TXN_NOT_PERMITTED_TO_TERMINAL                       = "558";
    public static final String CRES_DC_SUSPECTED_FRAUD_ERROR                               = "559";
    public static final String CRES_DC_CARD_ACCEPTOR_CONTACT_ACQUIRER                      = "560";
    public static final String CRES_DC_EXCEEDS_AMOUNT_LIMIT                                = "561";
    public static final String CRES_DC_RESTRICTED_CARD_ERROR                               = "562";
    public static final String CRES_DC_SECURITY_VOILATION                                  = "563";
    public static final String CRES_DC_ORIGINAL_AMOUNT_INCORRECT                           = "564";
    public static final String CRES_DC_EXCEEDS_WITHDRAWL_FREQUENCY                         = "565";
    public static final String CRES_DC_CARD_ACCEPTOR_CALL_ACQUIRER_SECURITY_DEPARTMENT     = "566";
    public static final String CRES_PK_HARD_CAPTURE                                        = "567";
    public static final String CRES_DC_RESPONSE_RECEIVED_TOO_LATE                          = "568";
    public static final String CRES_DC_ALLOWABLE_NUMBER_OF_PIN_TRIES_EXCEEDED              = "575";
    public static final String CRES_DC_INVALID_AMOUNT_ERROR                                = "576";
    public static final String CRES_DC_CUTOFF_IS_IN_PROCESS                                = "590";
    public static final String CRES_DC_ISSUED_IS_INOPERATIVE                               = "591";
    public static final String CRES_DC_FINANCIAL_INSTITUTION_NETWORK_NOT_FOUND_FOR_ROUTING = "592";
    public static final String CRES_DC_TXN_CANNOT_BE_COMPLETED_LAW_VOILATION               = "593";
    public static final String CRES_DC_DUPLICATE_TXN                                       = "594";
    public static final String CRES_DC_RECONCILE_ERROR                                     = "595";
    public static final String CRES_DC_SWITCH_SYSTEM_MALFUNCTION                           = "596";
    public static final String CRES_DC_INVALID_ATM_POS_ID                                  = "597";
    public static final String CRES_DC_COULD_NOT_GET_REPLY_FROM_ISSUER                     = "598";
    public static final String CRES_DC_PIN_BLOCK_ERROR                                     = "599";
    public static final String CRES_DC_MAC_FAILED                                          = "A0";
    public static final String CRES_DC_A1_ERROR                                            = "A1";
    public static final String CRES_AP_A2_SUCCESSFUL_TXN_WITH_FAULT                        = "A2";
    public static final String CRES_DC_A3_ERROR                                            = "A3";
    public static final String CRES_AP_A4_SUCCESSFUL_TXN_WITH_FAULT                        = "A4";
    public static final String CRES_AP_A5_SUCCESSFUL_TXN_WITH_FAULT                        = "A5";
    public static final String CRES_AP_A6_SUCCESSFUL_TXN_WITH_FAULT                        = "A6";
    public static final String CRES_DC_SECURITY_PROCESSING_ERROR                           = "A7";
    public static final String CRES_DC_FEES_NOT_PAID                                       = "B1";
    public static final String CRES_DC_ILLEGAL_ACQUIRER_STATUS                             = "C1";
    public static final String CRES_DC_INSTITUTION_ID_ERROR                                = "D1";
    public static final String CRES_DC_DATE_ERROR                                          = "D2";
    public static final String CRES_DC_INVALID_FILE_TYPE                                   = "D3";
    public static final String CRES_DC_FILE_PROCESSED                                      = "D4";
    public static final String CRES_DC_FILE_NOT_FOUND                                      = "D5";
    public static final String CRES_DC_RECEIVER_NOT_SUPPORTED                              = "D6";
    public static final String CRES_DC_FILE_LOCKED                                         = "D7";
    public static final String CRES_DC_UNSUCCESSFUL                                        = "D8";
    public static final String CRES_DC_INCONSISTENT_FILE_LENGTH                            = "D9";
    public static final String CRES_DC_FILE_DECOMPRESSION_ERROR                            = "DA";
    public static final String CRES_DC_FILE_NAME_ERROR                                     = "DB";
    public static final String CRES_DC_FILE_NOT_RECEIVABLE                                 = "DC";
    public static final String CRES_DC_UNREGISTERED_AUTHENTICATION                         = "N1";
    public static final String CRES_DC_CARD_AUTHENTICATION_FAILED                          = "Q1";
    public static final String CRES_AP_OFFLINE_APPROVED                                    = "Y1";
    public static final String CRES_AP_UNABLE_TO_GO_ONLINE_OFFLINE                         = "Y3";
    public static final String CRES_DC_OFFLINE                                             = "Z1";
    public static final String CRES_DC_UNABLE_TO_GO_ONLINE_OFFLINE                         = "Z3";

    /**
     * Tells if transaction is soft declined. Soft Decline shows error from the
     * processor, like network problem or server busy.
     *
     * @param responseCode - IRC value to be checked.
     * @return boolean
     */
    public static boolean isSoftDecline(String responseCode) {
	int respCode = Integer.parseInt(responseCode);
	return (respCode > 10 && respCode <= 100);
    }

    /**
     * Tells if transaction is hard declined. Soft Decline shows error in the
     * request, like invalid amount.
     *
     * @param responseCode - IRC value to be checked.
     * @return boolean
     */
    public static boolean isHardDecline(String responseCode) {
	int respCode = Integer.parseInt(responseCode);
	return (respCode > 100);
    }

    /**
     * Tells if transaction is successful.
     *
     * @param responseCode - IRC value to be checked.
     * @return boolean
     */
    public static boolean isSuccess(String responseCode) {
	int respCode = Integer.parseInt(responseCode);
	return (respCode == 0);
    }

}
