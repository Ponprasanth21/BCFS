package com.bornfire.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bornfire.entity.BusinessHours;
import com.bornfire.entity.BusinessHoursRep;
import com.bornfire.entity.SettlementAccount;
import com.bornfire.entity.SettlementAccountAmtRep;
import com.bornfire.entity.SettlementAccountAmtTable;
import com.bornfire.entity.SettlementAccountPojo;
import com.bornfire.entity.SettlementAccountRepository;

@Service
@ConfigurationProperties("output")
@Transactional
public class SettlementAccountServices {

	@Autowired
	SettlementAccountRepository settlementAccountRepository;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	BusinessHoursRep businessHoursRep;

	@Autowired
	SettlementAccountAmtRep settleAcctAmtRep;

	@Autowired
	IPSServices ipsServices;

	@Autowired
	Environment env;
	
	
	
	

	public String CreateSettlement(SettlementAccount settlementAccount, String formmode,String user) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		if (formmode.equals("addsubmit")) {
			System.out.println(formmode);
			SettlementAccount up = settlementAccount;
			up.setDel_flg("N");
			up.setEntity_flg("N");
			up.setModify_user(user);
			up.setEntry_user(user);
			settlementAccountRepository.save(up);
			msg = "Added Successfully";

		} else if (formmode.equals("edit")) {
			System.out.println(formmode);
			SettlementAccount up = settlementAccount;
			up.setDel_flg("N");
			up.setEntity_flg("N");
			up.setModify_user(user);
			up.setModify_flg("Y");
			hs.saveOrUpdate(up);
			msg = "Modified Successfully";

		} else if (formmode.equals("verify")) {
			System.out.println(formmode);
			SettlementAccount up = settlementAccount;
			up.setDel_flg("N");
			up.setEntity_flg("Y");
			up.setVerify_user(user);
			up.setModify_flg("N");
			hs.flush();
			hs.saveOrUpdate(up);
			msg = "Verified Successfully";

		}else if (formmode.equals("delete")) {
			System.out.println(formmode);
			SettlementAccount up = settlementAccount;
			up.setDel_flg("Y");
			up.setEntity_flg("N");
			up.setModify_flg("N");
			up.setModify_user(user);
			hs.flush();
			hs.saveOrUpdate(up);
			msg = "Deleted Successfully";

		}
		return msg;

	}

	public String Createbusiness(BusinessHours businessHours, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		if (formmode.equals("addsubmit")) {
			System.out.println(formmode);
			BusinessHours up = businessHours;
			up.setDel_flg("N");
			up.setEntity_flg("Y");
			businessHoursRep.save(up);
			msg = "Added Successfully";

		} else if (formmode.equals("edit")) {
			System.out.println(formmode);
			BusinessHours up = businessHours;
			up.setDel_flg("N");
			up.setEntity_flg("N");
			up.setModify_flg("Y");
			hs.saveOrUpdate(up);
			businessHoursRep.save(up);
			msg = "Modified Successfully";

		} else if (formmode.equals("verify")) {
			System.out.println(formmode);
			BusinessHours up = businessHours;
			up.setDel_flg("N");
			up.setEntity_flg("Y");
			up.setModify_flg("N");
			hs.flush();
			businessHoursRep.save(up);
			hs.saveOrUpdate(up);
			msg = "Verified Successfully";

		}
		return msg;

	}

	public List<SettlementAccountPojo> getSettleList() {
		//List<SettlementAccount> settlAcctList = settlementAccountRepository.findAllCustom();
		List<SettlementAccount> settlAcctList  =settlementAccountRepository.findAllCustom();
		List<SettlementAccountPojo> settlAcctListMod = new ArrayList<SettlementAccountPojo>();

		for (SettlementAccount settleAccount : settlAcctList) {
			SettlementAccountPojo SettlementAccount1 = new SettlementAccountPojo();
			SettlementAccount1.setAcc_code(settleAccount.getAcc_code());
			SettlementAccount1.setAcct_type(settleAccount.getAcct_type());
			SettlementAccount1.setCategory(settleAccount.getCategory());
			SettlementAccount1.setAccount_number(settleAccount.getAccount_number());
			SettlementAccount1.setName(settleAccount.getName());
			SettlementAccount1.setCrncy(settleAccount.getCrncy());
			SettlementAccount1.setAcct_bal(settleAccount.getAcct_bal()==null?new BigDecimal("0"):settleAccount.getAcct_bal());
			
			String datePre=new SimpleDateFormat("dd-MMM-yyyy").format(previousDay());
			if (settleAccount.getAcc_code().equals("01")) {
				
				Optional<SettlementAccountAmtTable> see=settleAcctAmtRep.customfindById(datePre);
				
				if(see.isPresent()) {
					
					BigDecimal amt =see.get()
							.getIncome_acct_bal();
					SettlementAccount1.setAcct_bal(amt==null?new BigDecimal("0"):amt);
					SettlementAccount1.setEntity_flg(see.get().getIncome_flg());
					SettlementAccount1.setEntry_user("");
					SettlementAccount1.setVerify_user("");
				}
				
			} else if (settleAccount.getAcc_code().equals("02")) {
				
				Optional<SettlementAccountAmtTable> see=settleAcctAmtRep.customfindById(datePre);
				if(see.isPresent()) {
					BigDecimal amt = see.get()
							.getExpense_acct_bal();
					SettlementAccount1.setAcct_bal(amt==null?new BigDecimal("0"):amt);
					SettlementAccount1.setEntity_flg(see.get().getExpense_flg());
					SettlementAccount1.setEntry_user("");
					SettlementAccount1.setVerify_user("");
				}
				
			} else if (settleAccount.getAcc_code().equals("03")) {
				
				Optional<SettlementAccountAmtTable> see=settleAcctAmtRep.customfindById(datePre);
				if(see.isPresent()) {
					BigDecimal amt =see.get()
							.getReceivable_acct_bal();
					SettlementAccount1.setAcct_bal(amt==null?new BigDecimal("0"):amt);
					SettlementAccount1.setEntity_flg(see.get().getReceivable_flg());
					
					if(see.get().getReceivable_flg()!=null) {
						SettlementAccount1.setEntry_user(see.get().getReceivable_entry_user());
						SettlementAccount1.setVerify_user(see.get().getReceivable_verify_user());
					}else {
						SettlementAccount1.setEntry_user("");
						SettlementAccount1.setVerify_user("");
					}
					
				}
				
			} else if (settleAccount.getAcc_code().equals("04")) {
				
				Optional<SettlementAccountAmtTable> see=settleAcctAmtRep.customfindById(datePre);
				if(see.isPresent()) {
					BigDecimal amt = see.get()
							.getPayable_acct_bal();
					SettlementAccount1.setAcct_bal(amt==null?new BigDecimal("0"):amt);
					SettlementAccount1.setEntity_flg(see.get().getPayable_flg());
					if(see.get().getPayable_flg()!=null) {
						SettlementAccount1.setEntry_user(see.get().getPayable_entry_user());
						SettlementAccount1.setVerify_user(see.get().getPayable_verify_user());
					}else {
						SettlementAccount1.setEntry_user("");
						SettlementAccount1.setVerify_user("");
					}
					
				}
				
			}else {
				SettlementAccount1.setEntity_flg("");
				SettlementAccount1.setEntry_user("");
				SettlementAccount1.setVerify_user("");
			}
			settlAcctListMod.add(SettlementAccount1);
		}
		return settlAcctListMod;
	}

	private Date previousDay() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	
	
	public String executeSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt, String tranType,
			String userid, String trAmtSP, String trAmtSR, String type, String trAmtP1, String trAmtR1) {

		String msg = "";
		try {
			if (type.equals("Payable")) {
				if (Double.parseDouble(trAmt) > 0) {

					if (Double.parseDouble(trAmt) <= Double.parseDouble(trAmtP1.replace(",", ""))) {
						if ((Double.parseDouble(trAmt) <= Double.parseDouble(trAmtSP.replace(",", "")))) {
							String response = ipsServices.createPayableSettlTransaction(frAcct, toAcct, tranDate, trAmt,
									tranType, userid);
							
							if(response.equals("Settlement Transaction Executed Successfully")) {
								Optional<SettlementAccountAmtTable> settlAmtOpt = settleAcctAmtRep
										.customfindById(new SimpleDateFormat("dd-MMM-yyyy").format(previousDay()));
								
								if (settlAmtOpt.isPresent()) {
									SettlementAccountAmtTable settlAmt = settlAmtOpt.get();
									settlAmt.setPayable_flg("Y");
									settlAmt.setPayable_verify_user(userid);
									settlAmt.setPayable_verify_time(new Date());
									settleAcctAmtRep.save(settlAmt);
								}
							}
							msg = response;
						} else {
							msg = "Amount must be less than or equal to settlement Amount";
						}
					} else {
						msg = "Amount must be less than or equal to Payable Amount";
					}

				} else {
					msg = "Transaction not permitted";
				}

			} else if (type.equals("Receivable")) {
				if (Double.parseDouble(trAmt) < 0) {
					if (Math.abs(Double.parseDouble(trAmt)) <= Math.abs(Double.parseDouble(trAmtR1.replace(",", "")))) {

						if ((Math.abs(Double.parseDouble(trAmt)) <= Double.parseDouble(trAmtSR.replace(",", "")))) {
							String response = ipsServices.createReceivableSettlTransaction(frAcct, toAcct, tranDate,
									String.valueOf(Math.abs(Double.parseDouble(trAmt))), tranType, userid);
							if(response.equals("Settlement Transaction Executed Successfully")) {
								Optional<SettlementAccountAmtTable> settlAmtOpt = settleAcctAmtRep
										.customfindById(new SimpleDateFormat("dd-MMM-yyyy").format(previousDay()));
								
								if (settlAmtOpt.isPresent()) {
									SettlementAccountAmtTable settlAmt = settlAmtOpt.get();
									settlAmt.setReceivable_flg("Y");
									settlAmt.setReceivable_verify_user(userid);
									settlAmt.setReceivable_verify_time(new Date());
									settleAcctAmtRep.save(settlAmt);
								}
							}
							msg = response;
						} else {

							msg = "Amount must be less than or equal to settlement Amount";

						}
					} else {
						msg = "Amount must be less than or equal to receivable Amount";
					}

				} else {
					msg = "Transaction not permitted";
				}

			} else if (type.equals("Income")) {
				if (Double.parseDouble(trAmt) > 0) {
					String response = ipsServices.createIncomeSettlTransaction(frAcct, toAcct, tranDate, trAmt,
							tranType, userid);
					msg = response;
				} else {
					msg = "Zero Amount not permitted";
				}

			} else if (type.equals("Expense")) {
				if (Double.parseDouble(trAmt) > 0) {
					String response = ipsServices.createExpenseSettlTransaction(frAcct, toAcct, tranDate, trAmt,
							tranType, userid);
					msg = response;
				} else {
					msg = "Zero Amount not permitted";
				}

			}
		} catch (Exception ex) {
			msg = "Error Occured. Please Contact Administrator";
		}

		return msg;
	}

	
	public String submitSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt, String tranType,
			String userid, String trAmtSP, String trAmtSR, String type, String trAmtP1, String trAmtR1) {
		Optional<SettlementAccountAmtTable> settlAmtOpt = settleAcctAmtRep
				.customfindById(new SimpleDateFormat("dd-MMM-yyyy").format(previousDay()));
		
		String msg = null;
		
		if (settlAmtOpt.isPresent()) {
			SettlementAccountAmtTable settlAmt = settlAmtOpt.get();
			
			if(type.equals("Payable")) {
				
				if (Double.parseDouble(trAmt) >0) {

					if (Double.parseDouble(trAmt) <= Double.parseDouble(trAmtP1.replace(",", ""))) {
						if ((Double.parseDouble(trAmt) == Double.parseDouble(trAmtSP.replace(",", "")))) {
							settlAmt.setPayable_flg("N");
							settlAmt.setApp_payable_acct_bal(new BigDecimal(trAmt));;
							settlAmt.setPayable_entry_user(userid);
							settlAmt.setPayable_entry_time(new Date());
							settleAcctAmtRep.save(settlAmt);
							msg = "Settlement Transaction Submitted Successfully";
						} else {
							msg = "Amount must be equal to settlement Amount";
						}
					} else {
						msg = "Amount must be less than or equal to Payable Amount";
					}

				}else if(Double.parseDouble(trAmt) ==0) {
					msg = "Zero Amount";
				} else {
					msg = "Transaction has not been submitted. Please check ";
				}
				
			
			} else if (type.equals("Receivable")) {
				
				if (Double.parseDouble(trAmt) < 0) {
					System.out.println(Math.abs(Double.parseDouble(trAmt)));
					System.out.println(trAmtR1.replace(",", ""));
					
					if (Math.abs(Double.parseDouble(trAmt)) <= Math.abs(Double.parseDouble(trAmtR1.replace(",", "")))) {

						if ((Math.abs(Double.parseDouble(trAmt)) == Double.parseDouble(trAmtSR.replace(",", "")))) {
							settlAmt.setApp_receivable_acct_bal(new BigDecimal(trAmt));;
							settlAmt.setReceivable_flg("N");
							settlAmt.setReceivable_entry_user(userid);
							settlAmt.setReceivable_entry_time(new Date());
							settleAcctAmtRep.save(settlAmt);
							msg = "Settlement Transaction Submitted Successfully";
						} else {

							msg = "Amount must be equal to settlement Amount";

						}
					} else {
						msg = "Amount must be less than or equal to receivable Amount";
					}

				} else if(Double.parseDouble(trAmt) == 0){
					msg = "Transaction has not been submitted. Please check ";
				} else {
					msg = "Transaction has not been submitted. Please check ";
				}
				
			
			}
		
		}
		return msg;
	}
	

}
