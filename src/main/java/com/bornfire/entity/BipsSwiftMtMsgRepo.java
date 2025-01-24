package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BipsSwiftMtMsgRepo extends JpaRepository<BIPS_SWIFT_MT_MSG, String> {

	@Query(value = "select * from BIPS_SWIFT_MT_MSG_TABLE where date_of_process is not null  order by date_of_process DESC", nativeQuery = true)
	List<BIPS_SWIFT_MT_MSG> findAllMt();

	@Query(value = "select * from BIPS_SWIFT_MT_MSG_TABLE Where srl_no=?1", nativeQuery = true)
	BIPS_SWIFT_MT_MSG findmtbySrl(String srl_no);

}
