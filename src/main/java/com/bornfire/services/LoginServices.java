package com.bornfire.services;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.bornfire.entity.LoginSecurity;
import com.bornfire.entity.ParameterRepository;
import com.bornfire.entity.UserProfile;

@Service
@ConfigurationProperties("output")
@Transactional
public class LoginServices {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServices.class);
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	SessionFactory sessionFactory;
	
	
	public String addParam(UserProfile param, String formmode) {
		// TODO Auto-generated method stub
		String msg = "";
		  
		/* try { */

		if (formmode.equals("add")) {

			UserProfile up = param;
			
			
			/*
			 * Session hs =sessionFactory.getCurrentSession();
			 * 
			 * BigDecimal billNumber = (BigDecimal)
			 * hs.createNativeQuery("SELECT SEQUENCE_2.NEXTVAL AS SRL_NO FROM DUAL").
			 * getSingleResult();
			 * 
			 * up.setSrl_no(billNumber.toString());
			 */
			/*
			 * if(billNumber.toString()!="") { DecimalFormat numformate = new
			 * DecimalFormat("000000"); billno="BILL"+numformate.format(billNumber);
			 * form.setBill_no(billno.toString()); hs.save(form); msg =
			 * "Added Successfully..!!"; }else { msg = "Something Went Wrong..!!"; }
			 */
			
			parameterRepository.save(up);

			msg = "Parameter Added Successfully";

		}
		return msg;

}


	public Object getSrlNoValue() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getUser(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	


	
}