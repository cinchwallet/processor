package com.cinchwallet.core.constant;


/**
 * <code>SequenceEnum</code> is directly mapped with the column <i>seq_name</i>
 * in the sequence_item table. This column must contains the value defined here.
 * <p>
 * <i>sequence_item</i> table is used to generate the next sequence number for all the
 * IDs defines under column name <i>seq_name</i>.
 * <p>
 * To make the new row entry in the <i>sequence_item</i> table, enum value should be
 * defined here first and the same value must be inserted into the
 * <i>sequence_item</i>. In the code to generate the next sequence number, Id should be
 * referred from this enum only.
 * <p>
 * e.g. SequenceManager.getInstance().getSequenceIdLong(SequenceEnum.TXN_ID_SEQ.name())
 * 
 * 
 */
public enum SequenceEnum {
    OLTP_TXN_LOG_ID_SEQ, TXN_ID_SEQ, STAN_ID_SEQ, RRN_ID_SEQ
}
