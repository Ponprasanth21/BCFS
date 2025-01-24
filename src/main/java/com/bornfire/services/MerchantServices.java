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
import com.bornfire.entity.MerchantFees;
import com.bornfire.entity.MerchantFeesRep;
import com.bornfire.entity.MerchantRep;
@Service
@ConfigurationProperties("output")
@Transactional

public class MerchantServices {

	@Autowired
	MerchantRep merchantRep;
	
	@Autowired
	MerchantFeesRep merchantFeesRep;
	
	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	SequenceGenerator sequence;
	
	public List<Merchant> getMerchantList() {
		List<Merchant> list=merchantRep.customFindByAll();
		return list;
	}

	public String createMerchant(Merchant merchantReg, String userid) {
		String msg = "";
		try {
			List<Merchant> merchantList = merchantRep.customFindById(merchantReg.getMerchant_code().toString(),
					merchantReg.getAccount_no().toString());
			if (merchantList.size() > 0) {
				msg = "The given parameter already exist";
			} else {
				merchantReg.setEntry_user(userid);
				merchantReg.setEntry_time(new Date());
				merchantReg.setDel_flg("N");
				merchantReg.setEntity_flg("Y");
				merchantRep.save(merchantReg);
				msg = "Merchant Added Successfully";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public Merchant getMerchantIndList(String merchantCode,String menAcctNumber) {
		Merchant merchantReg=new Merchant();
		try {
			List<Merchant> mandateList=merchantRep.customFindById(merchantCode,menAcctNumber);
			if(mandateList.size()>0) {
				merchantReg=mandateList.get(0);
			}
			
		}catch(Exception e) {
			merchantReg=new Merchant();
		}
		return merchantReg;
	}

	public String editMerchant(Merchant merchantReg, String userid) {
		String msg = "";
		try {
			List<Merchant> merchantList = merchantRep.customFindById(merchantReg.getMerchant_code().toString(),
					merchantReg.getAccount_no().toString());
			if (merchantList.size() > 0) {

				Merchant merchantReg1 = merchantList.get(0);

				if (merchantReg.getAccount_no().equals(merchantReg1.getAccount_no())
						&& merchantReg.getMerchant_trade_name().equals(merchantReg1.getMerchant_trade_name())
						&& merchantReg.getMerchant_code().equals(merchantReg1.getMerchant_code())
						&& merchantReg.getMail_id().equals(merchantReg1.getMail_id())
						&& merchantReg.getStart_date().equals(merchantReg1.getStart_date())
						&& merchantReg.getEnd_date().equals(merchantReg1.getEnd_date())
						&& merchantReg.getAddress1().equals(merchantReg1.getAddress1())
						&& merchantReg.getCity().equals(merchantReg1.getCity())) {
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
					merchantReg.setEntry_user(merchantReg1.getEntry_user());
					merchantReg.setEntry_time(merchantReg1.getEntry_time());
					merchantReg.setDel_flg("N");
					merchantReg.setEntity_flg("Y");
					merchantReg.setModify_user(userid);
					merchantReg.setModify_time(new Date());
					merchantRep.save(merchantReg);
					msg = "Merchant Modified Successfully";
				}

			} else {
				msg = "No Data Found";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public String deleteMandate(Merchant merchantReg, String userid) {
		String msg="";
		try {
			List<Merchant> merchantList=merchantRep.customFindById(merchantReg.getMerchant_code().toString(),
					merchantReg.getAccount_no().toString());
			if(merchantList.size()>0) {
				
				Merchant merchantReg1=merchantList.get(0);
			
					merchantReg1.setModify_user(userid);
					merchantReg1.setModify_time(new Date());
					merchantReg1.setDel_flg("Y");
					merchantRep.save(merchantReg1);
					msg="Merchant Deleted Successfully";
				
			
			}else {
				msg="No Date Found";
			}
			
		}catch(Exception e) {
			msg="Error Occured,Please Contact Administrator";
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

	}

	public List<MerchantFees> getMerchantFeeList() {
		List<MerchantFees> list=merchantFeesRep.customFindByAll();
		return list;
	}

	public MerchantFees getMerchantFeeIndList(String acctNumber) {
		MerchantFees merchantReg=new MerchantFees();
		try {
			List<MerchantFees> mandateList=merchantFeesRep.customFindById(acctNumber);
			if(mandateList.size()>0) {
				merchantReg=mandateList.get(0);
			}
			
		}catch(Exception e) {
			merchantReg=new MerchantFees();
		}
		return merchantReg;
	}

	public String createMerchantFee(MerchantFees merchantReg, String userid) {
		String msg = "";
		try {
			List<MerchantFees> merchantList = merchantFeesRep.customFindById(merchantReg.getAccount_no().toString());
			if (merchantList.size() > 0) {
				msg = "The given parameter already exist";
			} else {
				merchantReg.setEntry_user(userid);
				merchantReg.setEntry_time(new Date());
				merchantReg.setDel_flg("N");
				merchantReg.setEntity_flg("Y");
				merchantFeesRep.save(merchantReg);
				msg = "Merchant Fees Added Successfully";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;

	}

	public String editMerchantFees(MerchantFees merchantReg, String userid) {
		String msg = "";
		try {
			List<MerchantFees> merchantList = merchantFeesRep.customFindById(merchantReg.getAccount_no().toString());
			if (merchantList.size() > 0) {

				MerchantFees merchantReg1 = merchantList.get(0);

				
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
					merchantReg.setEntry_user(merchantReg1.getEntry_user());
					merchantReg.setEntry_time(merchantReg1.getEntry_time());
					merchantReg.setDel_flg("N");
					merchantReg.setEntity_flg("Y");
					merchantReg.setModify_user(userid);
					merchantReg.setModify_time(new Date());
					merchantFeesRep.save(merchantReg);
					msg = "Merchant Fees Modified Successfully";
				}

			

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;

	};
}

	
	

