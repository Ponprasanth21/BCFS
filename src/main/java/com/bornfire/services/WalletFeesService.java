package com.bornfire.services;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAgentTableRep;
import com.bornfire.entity.Merchant;
import com.bornfire.entity.MerchantRep;
import com.bornfire.entity.WalletFees;
import com.bornfire.entity.WalletFeesRep;
@Service
@ConfigurationProperties("output")
@Transactional

public class WalletFeesService {
	
	@Autowired
	WalletFeesRep walletFeesRep;
	
	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	SequenceGenerator sequence;
	
	public List<WalletFees> getWalletList() {
		List<WalletFees> list=walletFeesRep.customFindByAll();
		return list;
	}

	public String createWallet(WalletFees walletfeesReg, String userid) {
		String msg = "";
		try {
			List<WalletFees> walletfeesList = walletFeesRep.customFindById(walletfeesReg.getFEE_SRL_NO().toString(),
					walletfeesReg.getWALLET_TRAN_TYPE().toString());
			if (walletfeesList.size() > 0) {
				msg = "The given parameter already exist";
			} else {
				walletfeesReg.setENTRY_USER (userid);
				walletfeesReg.setENTRY_TIME (new Date());
				walletfeesReg.setDEL_FLG("N");
				walletfeesReg.setENTITY_FLG("N");
				walletFeesRep.save(walletfeesReg);
				msg = "WalletFees Added Successfully";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public WalletFees getWalletFeesList(String feesSrlNo,String walletTranType) {
		WalletFees walletfeesReg=new WalletFees();
		try {
			List<WalletFees> mandateList=walletFeesRep.customFindById(feesSrlNo,walletTranType);
			if(mandateList.size()>0) {
				walletfeesReg=mandateList.get(0);
			}
			
		}catch(Exception e) {
			walletfeesReg=new WalletFees();
		}
		return walletfeesReg;
	}

	public String editWallet(WalletFees walletfeesReg, String userid) {
		String msg = "";
		
		System.out.println("walletfeesReg"+walletfeesReg);
		System.out.println("userid"+userid);
		try {
			
	          String parm1= walletfeesReg.getFEE_SRL_NO();
	          System.out.println(parm1);
	         
	          String parm2 =walletfeesReg.getWALLET_TRAN_TYPE();
	          System.out.println(parm2);
			List<WalletFees> walletfeesList = walletFeesRep.customFindById(parm1,parm2);
			
			System.out.println("walletfeesList"+walletfeesList);
			if (walletfeesList.size() > 0) {

				WalletFees walletfeesReg1 = walletfeesList.get(0);

				if (walletfeesReg.getFEE_SRL_NO().equals(walletfeesReg1.getFEE_SRL_NO())
						&& walletfeesReg.getFEE_FIXED().equals(walletfeesReg1.getFEE_FIXED())						
						&& walletfeesReg.getFEE_LOGIC().equals(walletfeesReg1.getFEE_LOGIC())
						&& walletfeesReg.getSTART_DATE().equals(walletfeesReg1.getSTART_DATE())
						&& walletfeesReg.getEND_DATE().equals(walletfeesReg1.getEND_DATE())					
						&& walletfeesReg.getWALLET_TRAN_TYPE().equals(walletfeesReg1.getWALLET_TRAN_TYPE())) {
					msg = "No Any Modification Done";
				} else {
					///merchantReg1.setAccount_no(merchantReg.getAccount_no());
					//merchantReg1.setMerchant_trade_name(merchantReg.getMerchant_trade_name());
					//merchantReg1.setMerchant_code(merchantReg.getMerchant_code());
					//merchantReg1.setCity(merchantReg.getCity());
					//merchantReg1.setAddress1(merchantReg.getAddress1());
					//merchantReg1.setEnd_date(merchantReg.getEnd_date());
					//merchantReg1.setStart_date(merchantReg.getStart_date());
					//merchantReg1.setAddress1(merchantReg.getAddress1());
					//merchantReg1.setRemarks(merchantReg.getRemarks());
					//merchantReg1.setMail_id(merchantReg.getMail_id());
					walletfeesReg.setENTRY_USER(walletfeesReg1.getENTRY_USER());
					walletfeesReg.setENTRY_TIME(walletfeesReg1.getENTRY_TIME());
					walletfeesReg.setDEL_FLG("N");
					walletfeesReg.setENTITY_FLG("N");
					walletfeesReg.setMODIFY_USER(userid);
					walletfeesReg.setMODIFY_TIME(new Date());
					walletFeesRep.save(walletfeesReg);
					msg = "WalletFees Modified Successfully";
				}

			} else {
				msg = "No Date Found";
			}

		} catch (Exception e) {
			msg = e+"Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public String verifyWallet(WalletFees walletfeesReg, String userid) {
		String msg = "";
		try {
			List<WalletFees> walletfeesList = walletFeesRep.customFindById(walletfeesReg.getFEE_SRL_NO().toString(),
					walletfeesReg.getWALLET_TRAN_TYPE().toString());
			if (walletfeesList.size() > 0) {

				WalletFees walletfeesReg1 = walletfeesList.get(0);

				walletfeesReg.setENTRY_USER(walletfeesReg1.getENTRY_USER());
				walletfeesReg.setENTRY_TIME(walletfeesReg1.getENTRY_TIME());
				walletfeesReg.setDEL_FLG("N");
				walletfeesReg.setENTITY_FLG("Y");
				walletfeesReg.setVERIFY_USER(userid);
				walletfeesReg.setVERIFY_TIME(new Date());
				walletFeesRep.save(walletfeesReg);
				msg = "WalletFees Verified Successfully";
				

			} else {
				msg = "No Date Found";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}
	


	private boolean invalidBankCode(String bankCode) {
		boolean valid = true;
		try {
			Optional<BankAgentTable> otm = bankAgentTableRep.findById(bankCode);

			if (otm.isPresent()) {
				valid = false;
			} else {
				valid = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return valid;

	}
	

	public Merchant BlobImage(String Merchant_code) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(Merchant_code);
		@SuppressWarnings("unchecked")
		List<Merchant> query = (List<Merchant>) session
				.createQuery("from Merchant where merchant_code=?1").setParameter(1, Merchant_code).getResultList();
		return query.get(0);

	};
}

	
	

