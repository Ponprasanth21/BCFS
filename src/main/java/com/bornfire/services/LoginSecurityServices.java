package com.bornfire.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.bornfire.entity.BusinessHours;
import com.bornfire.entity.BusinessHoursRep;
import com.bornfire.entity.IPSChargesAndFees;
import com.bornfire.entity.IPSChargesAndFeesRepository;
import com.bornfire.entity.LoginSecurity;
import com.bornfire.entity.LoginSecurityRepository;

@Service
@ConfigurationProperties("output")
@Transactional
public class LoginSecurityServices {

	@Autowired
	LoginSecurityRepository loginSecurityRepository;

	@Autowired
	IPSChargesAndFeesRepository ipsChargesAndFeesRep;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	BusinessHoursRep businessHoursRep;

	public String addUser(LoginSecurity loginSecurity, String formmode) {
		System.out.println("hihihih");
		String msg = "";
		Session hs = sessionFactory.getCurrentSession();
		if (formmode.equals("submit")) {
			LoginSecurity up = loginSecurity;
			up.setRef_num("1");
			hs.saveOrUpdate(up);
			up.setEntity_flg("N");
			loginSecurityRepository.save(up);
			msg = "Login Security Submitted Successfully";

		}
		return msg;

	}

	public String addFees(IPSChargesAndFees ipsChargesAndFees, String formmode) {
		String msg = "";
		try {
		if (formmode.equals("add")) {

			ipsChargesAndFees.setEntity_flg("N");
			ipsChargesAndFees.setDel_flg("N");
			ipsChargesAndFees.setModify_flg("Y");
			ipsChargesAndFeesRep.save(ipsChargesAndFees);
			msg = "Added Successfully";

		} else if (formmode.equals("edit")) {
			IPSChargesAndFees up = ipsChargesAndFees;
			Optional<IPSChargesAndFees> chargeList = ipsChargesAndFeesRep
					.findById(ipsChargesAndFees.getReference_number());

			if (chargeList.isPresent()) {
				IPSChargesAndFees ipsCharge = chargeList.get();

				if (ipsChargesAndFees.getDescription() == (ipsCharge.getDescription())
						&& ipsChargesAndFees.getType() == (ipsCharge.getType())
						&& ipsChargesAndFees.getCriteria() == (ipsCharge.getCriteria())
						&& ipsChargesAndFees.getPercentage() == (ipsCharge.getPercentage())
						&& ipsChargesAndFees.getFees() == (ipsCharge.getFees())
						&& ipsChargesAndFees.getMin() == (ipsCharge.getMin())
						&& ipsChargesAndFees.getMax() == (ipsCharge.getMax())
						&& ipsChargesAndFees.getPeriodicity() == (ipsCharge.getPeriodicity())
						&& ipsChargesAndFees.getLast_tried_date() == (ipsCharge.getLast_tried_date())
						&& ipsChargesAndFees.getNext_due_date() == (ipsCharge.getNext_due_date())
						&& ipsChargesAndFees.getPayable_to() == (ipsCharge.getPayable_to())
						&& ipsChargesAndFees.getAmount() == (ipsCharge.getAmount())
						&& ipsChargesAndFees.getCurrency() == (ipsCharge.getCurrency())
						&& ipsChargesAndFees.getScript_name() == (ipsCharge.getScript_name())) {
					msg = "No Any Modification Done";
				} else {
					ipsCharge.setDescription(ipsChargesAndFees.getDescription());
					
					System.out.println(ipsChargesAndFees.getType());
					ipsCharge.setType(ipsChargesAndFees.getType());
					ipsCharge.setCriteria(ipsChargesAndFees.getCriteria());
					if (ipsChargesAndFees.getFees() == null) {
						ipsCharge.setFees(ipsChargesAndFees.getFees());
					} else {
						ipsCharge.setFees(new BigDecimal(ipsChargesAndFees.getFees().toString().replace(",", "")));
					}

					if (ipsChargesAndFees.getPercentage() == null) {
						ipsCharge.setPercentage(ipsChargesAndFees.getPercentage());
					} else {
						ipsCharge.setPercentage(
								new BigDecimal(ipsChargesAndFees.getPercentage().toString().replace(",", "")));
					}

					if (ipsChargesAndFees.getMin() == null) {
						ipsCharge.setMin(ipsChargesAndFees.getMin());
					} else {
						ipsCharge.setMin(new BigDecimal(ipsChargesAndFees.getMin().toString().replace(",", "")));
					}

					if (ipsChargesAndFees.getMax() == null) {
						ipsCharge.setMax(ipsChargesAndFees.getMax());
					} else {
						ipsCharge.setMax(new BigDecimal(ipsChargesAndFees.getMax().toString().replace(",", "")));
					}

					ipsCharge.setPeriodicity(ipsChargesAndFees.getPeriodicity());
					ipsCharge.setLast_tried_date(ipsChargesAndFees.getLast_tried_date());
					ipsCharge.setNext_due_date(ipsChargesAndFees.getNext_due_date());
					ipsCharge.setPayable_to(ipsChargesAndFees.getPayable_to());

					if (ipsChargesAndFees.getAmount() == null) {
						ipsCharge.setAmount(ipsChargesAndFees.getAmount());
					} else {
						ipsCharge.setAmount(new BigDecimal(ipsChargesAndFees.getAmount().toString().replace(",", "")));
					}

					ipsCharge.setCurrency(ipsChargesAndFees.getCurrency());
					ipsCharge.setScript_name(ipsChargesAndFees.getScript_name());

					ipsCharge.setEntity_flg("N");
					ipsCharge.setDel_flg("N");
					ipsCharge.setModify_flg("Y");
					ipsChargesAndFeesRep.save(ipsCharge);
					msg = "Modified Successfully";
				}

			} else {
				msg = "Error Occured. Please Contact Administrator";
			}

		} else if (formmode.equals("verify")) {

			IPSChargesAndFees up = ipsChargesAndFees;
			up.setEntity_flg("Y");
			up.setDel_flg("N");
			up.setModify_flg("N");
			ipsChargesAndFeesRep.save(up);
			msg = "Verified Successfully";
		}else if (formmode.equals("delete")) {

			ipsChargesAndFees.setEntity_flg("Y");
			ipsChargesAndFees.setDel_flg("Y");
			ipsChargesAndFees.setModify_flg("N");
			ipsChargesAndFeesRep.save(ipsChargesAndFees);
			msg = "Deleted Successfully";

		} 
		}catch(Exception e) {
			return "Something went wrong";
		}
		return msg;

	}

	public String addHours(BusinessHours businessHours, String formmode, String srlno) {
		String msg = "";
		Session hs = sessionFactory.getCurrentSession();

		if (formmode.equals("edit")) {

			System.out.println(srlno);

			BusinessHours up = businessHours;
			System.out.println(businessHours.getSrl_no());
			up.setEntity_flg("N");
			up.setDel_flg("N");
			up.setModify_flg("Y");
			hs.saveOrUpdate(up);
			businessHoursRep.save(up);
			msg = "Modified Successfully";

		} else if (formmode.equals("verify")) {
			System.out.println(formmode);

			BusinessHours up = businessHours;
			up.setEntity_flg("Y");
			up.setDel_flg("N");
			up.setModify_flg("N");
			hs.saveOrUpdate(up);
			businessHoursRep.save(up);

			msg = "Verified Successfully";
		}

		return msg;

	}

	public LoginSecurity getLoginSec() {
		LoginSecurity loginSecurity = new LoginSecurity();

		List<LoginSecurity> loginSecurityList = loginSecurityRepository.findAll();

		if (loginSecurityList.size() > 0) {
			loginSecurity = loginSecurityRepository.findAll().get(0);
		}
		return loginSecurity;
	}

}