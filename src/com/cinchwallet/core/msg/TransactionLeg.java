package com.cinchwallet.core.msg;

/**
 * Enum for all the leg values used in the application. Each enum value
 * represent a leg e.g. LEG_RQ i.e. Acquirer Request. To define a new leg the
 * application, first it should be declared in this enum and used from this
 * reference. e.g. RadicalTransactionLeg.LEG_RQ.name() will return string value
 * of "LEG_RQ".
 * <p>
 * Following are description of values defined here:
 * <p>
 * LEG_RQ - Acquirer Request.
 * <p>
 * LEG_RS - Acquirer Response
 */
public enum TransactionLeg {
    LEG_1, LEG_2, LEG_3, LEG_4, LEG_DR, REV_RQ, REV_RS
}
