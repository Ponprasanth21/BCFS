package com.bornfire.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Transactional
@Repository
public interface RegPublicKeyRep extends JpaRepository<RegPublicKey, String> {

	@Query(value = "select device_id,ip_address,national_id,phone_number,acct_number,entry_time,del_flg from BIPS_REG_PUBLIC_KEY where trunc(entry_time)=?1 order by entry_time desc", nativeQuery = true)
	List<Object[]> findAllCustom(String date);
	
	@Query(value = "SELECT device_id,ip_address,national_id,phone_number,acct_number,entry_time,account_status FROM ( SELECT device_id,ip_address,national_id,phone_number,acct_number,entry_time,account_status  FROM bips_reg_public_key_tmp where  trunc(entry_time)=?1 UNION SELECT device_id, ip_address, national_id,phone_number,acct_number,entry_time,account_status FROM bips_reg_public_key  where trunc(entry_time)=?1) X ORDER BY X.entry_time desc", nativeQuery = true)
	List<Object[]> findAllCustom1(String date);
	
	@Query(value = "select device_id,ip_address,national_id,phone_number,acct_number,entry_time,account_status from BIPS_REG_PUBLIC_KEY_TMP where trunc(entry_time)=?1 order by entry_time desc", nativeQuery = true)
	List<Object[]> findAllCustom2(String date);

	@Query(value = "select device_id,ip_address,national_id,phone_number,acct_number,entry_time,del_flg from BIPS_REG_PUBLIC_KEY where trim(to_char(entry_time,'Mon-YYYY'))=?1 order by entry_time desc", nativeQuery = true)
	List<Object[]> findAllCustomMonthly(String tranDate);
	
	
	@Query(value = "select device_id,ip_address,national_id,phone_number,acct_number,entry_time,account_status from BIPS_REG_PUBLIC_KEY_TMP where trim(to_char(entry_time,'Mon-YYYY'))=?1 order by entry_time desc", nativeQuery = true)
	List<Object[]> findAllCustomMonthly1(String tranDate);

}
