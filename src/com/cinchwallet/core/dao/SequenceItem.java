package com.cinchwallet.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinchwallet.core.db.SequenceDBManager;


/**
 * <code>SequenceItem</code> is the value object corresponding to the the
 * table <i>sequence_item</i>. Each column in the table is mapped with some
 * field in the class.
 * <p>
 * It define a populate method, which takes the ResultSet object and populates
 * the instance of <code>SequenceItem</code>.
 * <p>
 * To get the next sequence number for this sequence item, method
 * <code>getNextSequenceIdLong</code> should be invoked.
 * <p>
 * e.g. If SequenceItem is of type STAN_ID_SEQ, this method will return the next
 * unique number for the STAN. Whereas if this object is of type OLTP_TXN_LOG_ID_SEQ,
 * this would return the next available oltp_txn_log_id.
 *
 */
public class SequenceItem {

    long   sequenceItemId;
    String seqName = null;
    long   currentNbr;
    long   cacheSize;
    long   startNbr;
    long   suffixId;

    public long getStartNbr() {
	return startNbr;
    }

    public void setStartNbr(long startNbr) {
	this.startNbr = startNbr;
    }

    public long getSuffixId() {
	return suffixId;
    }

    public void setSuffixId(long suffixId) {
	this.suffixId = suffixId;
    }

    public long getSequenceItemId() {
	return sequenceItemId;
    }

    public void setSequenceItemId(long sequenceItemId) {
	this.sequenceItemId = sequenceItemId;
    }

    public String getSeqName() {
	return seqName;
    }

    public void setSeqName(String seqName) {
	this.seqName = seqName;
    }

    public long getCurrentNbr() {
	return currentNbr;
    }

    public void setCurrentNbr(long currentNbr) {
	this.currentNbr = currentNbr;
    }

    public long getCacheSize() {
	return cacheSize;
    }

    public void setCacheSize(long cacheSize) {
	this.cacheSize = cacheSize;
    }

    /**
     * Populates the instance of SequenceItem from the given ResultSet.
     *
     * @param rs - ResultSet
     * @throws SQLException
     */
    public void populate(ResultSet rs) throws SQLException {
	if (rs != null) {
	    sequenceItemId = rs.getLong("sequence_item_id");
	    seqName = rs.getString("seq_name");
	    startNbr = rs.getLong("start_nbr");
	    cacheSize = rs.getLong("cache_size");
	    currentNbr = startNbr - cacheSize;
	    suffixId = rs.getLong("suffix_id");
	}
    }

    /**
     * Returns the next sequence number of this type.
     * <p>
     * e.g. Invoking <code>getNextSequenceIdLong</code> over instance of
     * <code>SequenceItem</code> for OLTP_TXN_LOG_ID_SEQ, would returns the
     * next sequence number for oltp_txn_log_id.
     *
     * @return next sequence number.
     * @throws Exception
     */
    public synchronized Long getNextSequenceIdLong() throws Exception {
	currentNbr = currentNbr + 1;
	// Cache finished.. Get next numbers from DB
	if (currentNbr > startNbr) {
	    SequenceItem nextSeq = SequenceDBManager.getNextSequence(seqName);
	    this.startNbr = nextSeq.startNbr;
	    this.cacheSize = nextSeq.cacheSize;
	    this.suffixId = nextSeq.suffixId;
	    this.currentNbr = nextSeq.currentNbr;
	    currentNbr = currentNbr + 1;
	}
	return new Long(Long.toString(currentNbr) + Long.toString(suffixId));
    }

}
