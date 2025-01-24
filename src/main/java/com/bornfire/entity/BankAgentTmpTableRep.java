package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAgentTmpTableRep extends JpaRepository<BankAgentTmpTable, String> {
	Optional<BankAgentTmpTable> findById(String directorId);

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where  bank_agent!=?1 and del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTmpTable> findAllCustom(String bankAgent);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where  del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTmpTable> findAllCustom1();
	
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE ", nativeQuery = true)
	List<BankAgentTmpTable> findAllData();

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where BANK_CODE= ?1", nativeQuery = true)
	BankAgentTmpTable findByIdCustom(String Id);
	

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where BANK_AGENT= ?1", nativeQuery = true)
	BankAgentTmpTable findByIdCustomAgent(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where bank_code= ?1", nativeQuery = true)
	List<BankAgentTmpTable> findByIdCustomBankCode(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where bank_code= ?1 and del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTmpTable> findByIdCustomBankCode1(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE where bank_agent= ?1", nativeQuery = true)
	List<BankAgentTmpTable> findByIdCustomAgent1(String Id);

}
