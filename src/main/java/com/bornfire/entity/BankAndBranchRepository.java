package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface BankAndBranchRepository extends JpaRepository<BankAndBranchBean, String>{
	
	
	 Optional<BankAndBranchBean> findById( String directorId); 
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH ", nativeQuery = true) 
	 Page<BankAndBranchBean> BankandBranchList(Pageable page);
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH where sol_id=?1 ", nativeQuery = true) 
	 BankAndBranchBean findByIdcustom(String solId);
	 
	 @Query(value = "select * from BIPS_BANK_AND_BRANCH ORDER BY SOL_ID ASC", nativeQuery = true) 
	 List<BankAndBranchBean> BankandBranchList();
	 
	 @Query(value = "select count(*) from BIPS_BANK_AND_BRANCH where del_flg='N'  and sol_id=?1 ", nativeQuery = true)
	 String getusercount(String sol_id);

	 @Query(value = "select * from BIPS_BANK_AND_BRANCH  where entity_flg ='Y' and del_flg ='N' UNION ALL select * from BIPS_BANK_AND_BRANCH_MOD  where entity_flg ='N'", nativeQuery = true)
	 List<BankAndBranchBean> findAllByCustom();
	
	
}
