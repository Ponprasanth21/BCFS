package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAndBranchModRepository extends JpaRepository<BankAndBranchBeanMod, String>{
	
	
	 Optional<BankAndBranchBeanMod> findById( String directorId); 
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH_MOD ", nativeQuery = true) 
	 Page<BankAndBranchBeanMod> BankandBranchList(Pageable page);
	 
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH_MOD where sol_id=?1 ", nativeQuery = true) 
	 BankAndBranchBeanMod findByIdcustom(String solId);
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH_MOD ORDER BY SOL_ID ASC", nativeQuery = true) 
	 List<BankAndBranchBeanMod> BankandBranchList();
	
}
