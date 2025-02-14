package com.bornfire.entity;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface IPSAuditTableRep extends JpaRepository<IPSAuditTable, String> {

	@Query(value = "select * from BIPS_AUDIT_TABLE where trunc(audit_date)=?1  AND audit_table  in ('BIPS_USER PROFILE','BIPS_USER_PROFILE') and modi_details is not null order by entry_time desc", nativeQuery = true)
	List<IPSAuditTable> getauditListLocal1(String Fromdate);

	@Query(value = "select * from BIPS_AUDIT_TABLE where trunc(audit_date) between ?1 and ?2  AND audit_table  in ('BIPS_USER PROFILE','BIPS_USER_PROFILE') and modi_details is not null order by entry_time desc", nativeQuery = true)
	List<IPSAuditTable> getauditListLocal(Date Fromdate, Date Todate);

	@Query(value = "select * from BIPS_AUDIT_TABLE where trunc(audit_date) between ?1 and ?2  AND audit_table not in ('BIPS_USER_PROFILE_MOD_TABLE','BIPS_USER_PROFILE') order by audit_date desc", nativeQuery = true)
	List<IPSAuditTable> getauditListOpeartion(Date Fromdate,Date Todate);

	@Query(value = "select * from BIPS_AUDIT_TABLE where event_id=?1 and remarks in ('ADDED','MODIFIED') and audit_table ='BIPS_USER_PROFILE' ", nativeQuery = true)
	IPSAuditTable getAuditVerifyUser(String userid);

	@Query(value = "select * from BIPS_AUDIT_TABLE where event_id=?1 and func_code ='USER MODIFICATION' order by audit_ref_no desc fetch first 1 row only ", nativeQuery = true)
	IPSAuditTable getModifyList(String userid, String func_code);
	
	@Query(value = "select * from BIPS_AUDIT_TABLE where event_id=?1 and func_code =?2 order by audit_ref_no desc fetch first 1 row only ", nativeQuery = true)
	IPSAuditTable getModifyList1(String userid, String func_code);
	
    ////Generate Request_UUID
	@Query(value = "SELECT IPS_AUDIT_SEQ.NEXTVAL FROM dual", nativeQuery = true)
	Long getAuditRefUUID();

	

}
