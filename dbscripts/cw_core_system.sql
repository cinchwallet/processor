CREATE SCHEMA cw_transaction;

CREATE TABLE cw_transaction.sequence_item (
  sequence_item_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key of The Table. Auto Generated',
  seq_name varchar(50) DEFAULT NULL COMMENT 'Sequence name - txn_id ',
  start_nbr int(11) ,
  cache_size int(11) ,
  suffix_id int(4) ,
  creation_ts timestamp NULL DEFAULT NULL COMMENT 'creation timestamp',
    PRIMARY KEY (sequence_item_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

INSERT INTO cw_transaction.sequence_item ( sequence_item_id, seq_name, start_nbr , cache_size, suffix_id ) VALUES ( 1, 'TXN_ID_SEQ' , 51,100 , 11) ;


CREATE TABLE cw_transaction.TXN_LOG (
  txn_log_id int(11) NOT NULL AUTO_INCREMENT,
  txn_id varchar(20) DEFAULT NULL,
  leg varchar(10) DEFAULT NULL,
  pan VARCHAR(200) DEFAULT NULL,
  pan_hint VARCHAR(20) DEFAULT NULL,
  txn_amount double DEFAULT NULL,
  dt_transmission timestamp NULL DEFAULT NULL,
  stan varchar(12) DEFAULT NULL,
  dt_transaction timestamp NULL DEFAULT NULL,
  approval_cd varchar(6) DEFAULT NULL,
  reason_cd varchar(4) DEFAULT NULL,
  result_cd varchar(4) DEFAULT NULL,
  tid varchar(16) DEFAULT NULL,
  mid varchar(35) DEFAULT NULL,
  transaction_type varchar(16) DEFAULT NULL,
  PRIMARY KEY (txn_log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

