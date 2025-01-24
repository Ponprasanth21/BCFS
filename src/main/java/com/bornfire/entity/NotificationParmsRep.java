package com.bornfire.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



	@Repository
	public interface NotificationParmsRep extends CrudRepository<NotificationParms,String>{
		
		@Query(value = "select * from NOTIFICATION_PARM_MASTER where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
		List<NotificationParms> customFindByAll();
		
		@Query(value = "select * from NOTIFICATION_PARM_MASTER where RECORD_SRL_NO=?1 and TRAN_CATEGORY=?2", nativeQuery = true)
		List<NotificationParms> customFindById(String recordSrlNo,String tranType);
	
	
	
}
