package com.cinchwallet.core.msg;

import com.cinchwallet.core.constant.SwitchConstants;


/**
 * Enum for all the supported transaction type by the application. This
 * represents the transaction type short name used throughout the application.
 * <p>
 * All the transaction types are listed here and if future demands for more
 * transaction type, it must be listed here and should be referenced from here
 * only.
 *
 *
 */
public enum TransactionType {
    BALIQ, ACTVN, RVSAL, REDMP_VOID, LOADF, LOADF_VOID, LOADV, REDMP, ACTNL, VOIDX, DACTN, ACTVN_RVSAL, LOADF_RVSAL, LOADV_RVSAL, REDMP_RVSAL, ACTNL_RVSAL, VOIDX_RVSAL, DACTN_RVSAL, LOY_NO_IQ, USR_REG, GET_USR, MINI_STMT, UNKNOWN;

    /**
     * Returns the reversal transaction name for the given transaction name.
     * <p>
     * e.g. ACTVN_RVSAL i.e. activation reversal would be returned for ACTVN
     * i.e. activation.
     *
     * @param originalTxnType - transaction type for which corresponding
     *                reversal transaction name is required.
     * @return reversal transaction type.
     */
    public static TransactionType getReversalTxnType(TransactionType originalTxnType) {
	switch (originalTxnType) {
	case ACTVN:
	    return ACTVN_RVSAL;
	case LOADF:
	    return LOADF_RVSAL;
	case LOADV:
	    return LOADV_RVSAL;
	case REDMP:
	    return REDMP_RVSAL;
	case ACTNL:
	    return ACTNL_RVSAL;
	case VOIDX:
	    return VOIDX_RVSAL;
	case DACTN:
	    return DACTN_RVSAL;
	default:
	    return UNKNOWN;
	}
    }

    /**
     * Checks if reversal of the given transaction is allowed/required.
     * <p>
     * e.g. reversal of balance inquiry is not required, so false would be
     * returned for balance inquiry.
     *
     * @param originalTxnType - transaction type
     * @return is reversal of given transaction is allowed or not.
     */
    public static boolean isReversalAllowed(TransactionType originalTxnType) {
	if (originalTxnType.equals(BALIQ) || originalTxnType.toString().endsWith(SwitchConstants.DB_REVERSAL_TYPE_CODE)) {
	    return false;
	}
	return true;
    }
}
