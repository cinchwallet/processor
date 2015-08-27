package com.cinchwallet.core.sequence;

import java.util.HashMap;
import java.util.Map;

import com.cinchwallet.core.dao.SequenceItem;
import com.cinchwallet.core.db.SequenceDBManager;


/**
 * A sequence is an object used to generate a number sequence. This can be
 * useful when you need to create a unique number to act as a primary key.
 * <p>
 * <code>SequenceManager</code> serves the same purpose and generates the next
 * available unique sequence number. This uses table <i>sequence_item</i> as
 * base to create the unique numbers. Only those type of sequence number would
 * be created, which are defined in the column <i>seq_name</i> in the table
 * <i>sequence_item</i>.
 * <p>
 * <code>SequenceManager</code> read the table and maintains the Map with
 * seq_name as key and corresponding instance of SequenceItem as value. Way to
 * get the next sequence are as follow:
 * <p>
 * SequenceManager.getInstance().getSequenceIdLong(SequenceEnum.TXN_ID_SEQ.name());
 * <p>
 * will returns the next available txn_id and update the next available txn_id
 * by increasing last by one.
 * <p>
 * currentNbr in the SequenceItem is suffixed with the suffixId to make it as
 * sequence. After that currentNbr is increased by one.
 *
 */
public class SequenceManager {

    Map<String, SequenceItem>      sequenceMap = new HashMap<String, SequenceItem>();
    private static SequenceManager seqMgr      = new SequenceManager();

    /**
     * Make constructor private
     */
    private SequenceManager() {

    }

    /**
     * Returns the only instance of SequenceManager. It is made singleton to
     * make that only one object of SequenceManager is responsible for
     * generating the sequence to make it unique.
     *
     * @return instance of SequenceManager
     */
    public static SequenceManager getInstance() {
	return seqMgr;
    }

    /**
     * Returns the next sequence for the given sequence name. It first look into
     * cache for corresponding SequenceItem object, otherwise get it fresh from
     * database.
     * <p>
     * Once instance of SequenceItem is received, calling
     * <code>getNextSequenceIdLong</code> over SequenceItem would give the
     * next sequence.
     *
     * @param seqName - ID for which next sequence is required.
     * @return next sequence for given ID.
     * @throws Exception
     */
    public Long getSequenceIdLong(String seqName) throws Exception {
	// Need to change Sequence Master DAO name... :) didn't like it..
	SequenceItem seqItem = sequenceMap.get(seqName);
	if (seqItem == null) {
	    seqItem = getSequenceItemFromDB(seqName);
	}
	return seqItem.getNextSequenceIdLong();
    }

    /**
     * Calls the SequenceDBManager to create the instance of SequenceItem for
     * given ID. Populate the cache and returns the newly created instance.
     *
     * @param seqName - ID for which next sequence is required.
     * @return instance of SequenceItem for given ID.
     * @throws Exception
     */
    /* Move this to Sequence Item so that we don't block initially.... */
    private synchronized SequenceItem getSequenceItemFromDB(String seqName) throws Exception {
	SequenceItem seqItem = sequenceMap.get(seqName);
	if (seqItem != null) {
	    return seqItem;
	} else {
	    seqItem = SequenceDBManager.getNextSequence(seqName);
	    sequenceMap.put(seqName, seqItem);
	}
	return seqItem;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stubtD
	try {
	    System.out.println(SequenceManager.getInstance().getSequenceIdLong("txn_id"));
	    SequenceManager.getInstance().test();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void test() {
	TestThread t1 = new TestThread("T-A");
	TestThread t2 = new TestThread("T-B");
	t1.start();
	t2.start();
    }

    public class TestThread extends Thread {
	String id = null;

	public TestThread(String id) {
	    this.id = id;
	}

	public void run() {
	    while (true) {
		try {
		    System.out.println("Generated Seq from thread" + id + ":" + SequenceManager.getInstance().getSequenceIdLong("txn_id"));
		    Thread.currentThread().sleep(1000);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }

	}
    }

}
