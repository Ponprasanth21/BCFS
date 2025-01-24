package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BankAgentTableRep extends JpaRepository<BankAgentTable, String> {
	Optional<BankAgentTable> findById(String directorId);

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where  bank_agent!=?1 and del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTable> findAllCustom(String bankAgent);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where  del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTable> findAllCustom1();
	
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE ", nativeQuery = true)
	List<BankAgentTable> findAllData();
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE  where entity_flg ='Y' and del_flg ='N' UNION ALL select * from BIPS_OTHER_BANK_AGENT_TMP_TABLE  where entity_flg ='N'", nativeQuery = true)
	List<BankAgentTable> findAllDataList();

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where BANK_CODE= ?1", nativeQuery = true)
	BankAgentTable findByIdCustom(String Id);
	

	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where BANK_AGENT= ?1", nativeQuery = true)
	BankAgentTable findByIdCustomAgent(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where bank_code= ?1", nativeQuery = true)
	List<BankAgentTable> findByIdCustomBankCode(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where bank_code= ?1 and del_flg<>'Y'", nativeQuery = true)
	List<BankAgentTable> findByIdCustomBankCode1(String Id);
	
	@Query(value = "select * from BIPS_OTHER_BANK_AGENT_TABLE where bank_agent= ?1", nativeQuery = true)
	List<BankAgentTable> findByIdCustomAgent1(String Id);


	
}
