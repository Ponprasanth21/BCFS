package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BipsSwiftMsgConversionRepo extends JpaRepository<BIPS_SWIFT_MSG_MGT, String> {

	@Query(value = "select * from BIPS_SWIFT_FILE_MGT_TABLE where status ='Completed' and date_of_process IS NOT NULL  order by date_of_process DESC", nativeQuery = true)
	List<BIPS_SWIFT_MSG_MGT> findAll();

	@Query(value = "select * from BIPS_SWIFT_FILE_MGT_TABLE where REFERENCE_NUMBER = '1'", nativeQuery = true)
	List<BIPS_SWIFT_MSG_MGT> findBYId();

	@Query(value = "SELECT SRL_NO.nextval FROM dual", nativeQuery = true)

	String getNextSeriesId();

	@Query(value = "SELECT REF_ID.nextval FROM dual", nativeQuery = true)

	String getNextrefId();
	
	@Query(value = "select swift_msg_type from BIPS_SWIFT_FILE_MGT_TABLE where srl_no =?1", nativeQuery = true)
	String findmsgtype(String ref_Num);
	
	@Query(value = "SELECT COUNT(*) FROM BIPS_SWIFT_FILE_MGT_TABLE where swift_msg_type = 'MT'", nativeQuery = true)
	String totalMTdata();

	@Query(value = "SELECT COUNT(*) FROM BIPS_SWIFT_FILE_MGT_TABLE where swift_msg_type = 'MT' AND status = 'Completed'", nativeQuery = true)
	String MTtotalsuccess();
	
	@Query(value = "SELECT COUNT(*) FROM BIPS_SWIFT_FILE_MGT_TABLE where swift_msg_type = 'MT' AND status = 'Inprogress'", nativeQuery = true)
	String MTtotalfailure();
	
	@Query(value = "SELECT COUNT(*) FROM BIPS_SWIFT_FILE_MGT_TABLE where swift_msg_type = 'MX' AND status = 'Inprogress'", nativeQuery = true)
	String Mxtotalfailure();
	
	@Query(value = "SELECT COUNT(*) FROM BIPS_SWIFT_FILE_MGT_TABLE where swift_msg_type = 'MX' AND status = 'Completed'", nativeQuery = true)
	String Mxtotalsuccess();
	
	@Query(value = "select * from BIPS_SWIFT_FILE_MGT_TABLE where status ='Completed'", nativeQuery = true)
	List<BIPS_SWIFT_MSG_MGT> findprocessedvalue();
	
	@Query(value = "select * from BIPS_SWIFT_FILE_MGT_TABLE where status ='Inprogress'", nativeQuery = true)
	List<BIPS_SWIFT_MSG_MGT> findfailuedvalue();
	
	@Query(value = "select * from BIPS_SWIFT_FILE_MGT_TABLE where status IS NULL", nativeQuery = true)
	List<BIPS_SWIFT_MSG_MGT> findpendingvalue();

}
