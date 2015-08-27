package com.cinchwallet.core.msg;

public interface IMFConstants {

    String DIRECTION_INCOMING                   = "Incoming";
    String DIRECTION_OUTGOING                   = "Outgoing";

    int    ADDITIONAL_AMOUNT_SINGLE_BLOCK_LEN   = 21;

    String COLON                                = ":";
    String UNDERSCORE                           = "_";
    String OBJ_START                            = "[";
    String OBJ_END                              = "]";
    String PROPERTY_DELIMITER                   = "@@@@";
    String COLL_START                           = "<<";
    String COLL_END                             = ">>";
    String BLANK                                = "";
    String PIPE                                 = "|";
    String DOUBLE_SLASH                         = "\\";
    String AT_THE_RATE_DELIMITER                = "@";
    String HASH_DELIMITER                       = "#";
    String EQUALS_DELIMITER                     = "=";
    String SPACE                                = " ";
    String ACK_TYPE_END_OF_FILE                 = "0";
    String ACK_TYPE_MORE_DATA                   = "1";
    String ACK_TYPE_ANOTHER_FILE_NEXT           = "2";
    String ACK_TYPE_POSITIVE                    = "3";
    String ACK_TYPE_NEGATIVE                    = "8";
    int    MODEL_TYP_BYTE_LEN                   = 5;

    enum AMOUNT_TYPE {
	ACCOUNT_LEDGER_BALANCE, ACCOUNT_AVAILABLE_BALANCE, DESTINATION_ACCOUNT_LEDGER_BALANCE, DESTINATION_ACCOUNT_AVAILABLE_BALANCE, AMOUNT_REMAINING_THIS_CYCLE, AMOUNT_CASH
    };

    enum AMOUNT_SIGN {
	C, D
    };

    enum FEE_TYPE {
	TRANSACTION_FEE, CARD_ACCEPTOR_SERVICE_FEE
    };

    enum LEG {
	LEG_1, LEG_2, LEG_3, LEG_4, LEG_DR, REV_RQ, REV_RS

    }

    enum ACKNOWLEDGEMENT_TYPE {
	MORE_DATA, ANOTHER_FILE_NEXT, END_OF_FILE, POSITIVE_ACK, NEGATIVE_ACK
    };

}
