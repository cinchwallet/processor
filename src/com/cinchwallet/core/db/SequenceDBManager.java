package com.cinchwallet.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cinchwallet.core.dao.SequenceItem;
import com.cinchwallet.core.utils.DBConnection;
import com.cinchwallet.core.utils.CWLogger;


/**
 * <code>SequenceDBManager</code> generates the next sequence by reading the
 * table <i>sequence_item</i> and create the instance of SequenceItem for given
 * sequence type.
 * <p>
 * This class is used by the <code>SequenceManager</code> when it do not find
 * required sequence object in the cache.
 * <p>
 * This class will read the <i>sequence_item</i> table and loads the whole
 * information in the SequenceItem instance. It is then used to give the next
 * sequence by suffixing suffixId with the currentNbr and increasing currentNbr
 * by one for the next sequence.
 *
 */

public class SequenceDBManager {

    /**
     * Method to return sequence master record for the passed sequence name
     *
     * @param uid
     * @return
     */
    public static SequenceItem getSequenceByName(String seqName) {
	Connection conn = null;
	ResultSet res = null;
	PreparedStatement prep = null;
	SequenceItem seq = new SequenceItem();
	try {
	    String sel = " select * from sequence_item where seq_name = ? ";
	    conn = DBConnection.getTxnConnection();
	    prep = conn.prepareStatement(sel);
	    prep.setString(1, seqName);
	    res = prep.executeQuery();
	    if (res.next()) {
		seq = new SequenceItem();
		seq.populate(res);
	    }
	} catch (Exception e) {
	    CWLogger.appLog.error("Exception executing getTxnLogByUid method " + e.getMessage());
	} finally {
	    DBConnection.closeAll(res, prep, conn);
	}
	return seq;
    }

    /**
     * Method to return sequence master record for the passed sequence name. It
     * first update the start_nbr by adding it with the cache_size. This will
     * set the start_nbr to the next available starting point.
     * <p>
     * currentNbr, which is actually the next sequence number is initialized by
     * the start_nbr-cache_size. By doing this currentNbr can be increased by
     * cache_size times and system do not have to hit the database for that
     * number of times to get the next sequence.
     * <p>
     * As soon as currentNbr become greater then the start_nbr, cache is
     * refreshed with the new value by updating the start_nbr and process go on.
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public static SequenceItem getNextSequence(String seqName) throws Exception {
	Connection conn = null;
	ResultSet res = null;
	PreparedStatement prep = null;
	PreparedStatement updPrep = null;
	SequenceItem seq = new SequenceItem();
	try {
	    String upd = " update sequence_item set start_nbr  = start_nbr + cache_size where seq_name = ? ";
	    conn = DBConnection.getTxnConnection();
	    CWLogger.appLog.info("Connection..."+conn);
	    conn.setAutoCommit(false);

	    updPrep = conn.prepareStatement(upd);
	    updPrep.setString(1, seqName);
	    int updCount = updPrep.executeUpdate();
	    if (updCount != 1) {
		return null;// Something went wrong.
	    }

	    String sel = " select * from sequence_item where seq_name = ? ";
	    prep = conn.prepareStatement(sel);
	    prep.setString(1, seqName);
	    res = prep.executeQuery();
	    if (res.next()) {
		seq = new SequenceItem();
		seq.populate(res);
	    }
	    conn.commit();
	} catch (Exception e) {
	    CWLogger.appLog.error("Exception fetching next sequence for " + seqName + e.getMessage());
	    if (conn != null) {
		conn.rollback();
	    }
	} finally {
	    // Need to maintain list in database context.
	    if (conn != null) {
		conn.setAutoCommit(true);
	    }
	    if (updPrep != null) {
		updPrep.close();
	    }
	    if (prep != null) {
		prep.close();
	    }
	    if (res != null) {
		res.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	CWLogger.appLog.info("SequenceItem..."+seq);
	return seq;
    }

}
