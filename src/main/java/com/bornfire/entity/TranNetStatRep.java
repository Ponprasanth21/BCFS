package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TranNetStatRep extends JpaRepository<TranNetState, String> {

	@Query(value = "select * from BIPS_TRAN_NET_STAT_TABLE order by msg_id desc", nativeQuery = true)
	List<TranNetState> findAllCustom();

}