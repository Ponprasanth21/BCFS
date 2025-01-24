package com.bornfire.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface TranIPStableRep extends JpaRepository<TranIPSTable, String>{
	@Query(value = "select * from bips_tran_ips_table where trunc(msg_date)=?1 and msg_code='camt.053.001.08'", nativeQuery = true)
	List<TranIPSTable> findByIdCustomCamt53(String tranDate);

	@Query(value = "select * from bips_tran_ips_table where (sequence_unique_id=?1 or end_end_id=?1)", nativeQuery = true)
	List<TranIPSTable> findAllCustomSequeneID(String modSeqUniqueId);

	
	@Query(value = "select * from bips_tran_ips_table where trunc(msg_date)=?1 order by sequence_unique_id,msg_date asc", nativeQuery = true)
	List<TranIPSTable> findByCustomList(String tranDate);

	@Query(value = "select * from bips_tran_ips_table where msg_id=?1 and msg_sub_type=?2", nativeQuery = true)
	List<TranIPSTable> getMsMsg(String msg_id, String msg_sub_type);;
}


