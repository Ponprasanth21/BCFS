package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


	@Repository
	public interface SettlementAccountRepository extends JpaRepository<SettlementAccount, String> {
		Optional<SettlementAccount> findById( String directorId);

		@Query(value = "select * from BIPS_SETTL_ACCTS where del_flg='N' ", nativeQuery = true)
		List<SettlementAccount> findAllCustom();
		
		@Query(value = "select * from BIPS_SETTL_ACCTS where acc_code= ?1", nativeQuery = true)
		SettlementAccount findByAccountCode(String acc_code);
	}
