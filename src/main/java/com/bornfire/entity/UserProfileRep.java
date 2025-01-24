package com.bornfire.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRep extends CrudRepository<UserProfile,String>{
	

	public Optional<UserProfile> findByusername(String userName);
	
	@Query(value = "select * from BIPS_USER_PROFILE where USER_ID= ?1", nativeQuery = true)
	UserProfile findByIdCustom(String Id);
	
	@Query(value = "select user_name from BIPS_USER_PROFILE where USER_ID= ?1", nativeQuery = true)
	String findNameById(String Id);
	
	@Query(value = "select * from BIPS_USER_PROFILE where NVL(DEL_FLG,1) <> 'Y'", nativeQuery = true)
	List<UserProfile> findByAll(String Id);
	
	@Query(value = "select count(*) from BIPS_USER_PROFILE where del_flg='N'  and user_id=?1 ", nativeQuery = true)
	String getusercount(String custId);
	
	
	@Query(value = "select country_code from BIPS_USER_PROFILE where del_flg='N'  and user_id=?1 ", nativeQuery = true)
	String getCountrycode(String custId);
	
}
