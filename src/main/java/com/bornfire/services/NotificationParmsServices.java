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
import com.bornfire.entity.IPSAccessRole;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSUserPreofileMod;
import com.bornfire.entity.Merchant;
import com.bornfire.entity.MerchantCategoryCodeEntity;
import com.bornfire.entity.MerchantCategoryRep;
import com.bornfire.entity.MerchantRep;
import com.bornfire.entity.NotificationParms;
import com.bornfire.entity.NotificationParmsRep;
import com.bornfire.entity.UserProfile;
import com.bornfire.entity.WalletFees;
import com.bornfire.entity.WalletFeesRep;
@Service
@ConfigurationProperties("output")
@Transactional

public class NotificationParmsServices {
	
	@Autowired
	NotificationParmsRep notificationParmsRep;
	
	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	SequenceGenerator sequence;
	
	@Autowired
	MerchantCategoryRep merchantCategoryRep;
	
	public List<NotificationParms> getNotificationParmsList() {
		List<NotificationParms> list=notificationParmsRep.customFindByAll();
		return list;
	}

	public String createNotificationParms(NotificationParms notificationParmsReg, String userid) {
		String msg = "";
		try {
			List<NotificationParms> notificationParmsList = notificationParmsRep.customFindById(notificationParmsReg.getRECORD_SRL_NO().toString(),
					notificationParmsReg.getTRAN_CATEGORY().toString());
			if (notificationParmsList.size() > 0) {
				msg = "The given parameter already exist";
			} else {
				notificationParmsReg.setENTRY_USER (userid);
				notificationParmsReg.setENTRY_TIME (new Date());
				notificationParmsReg.setDEL_FLG("N");
				notificationParmsReg.setENTITY_FLG("N");
				notificationParmsRep.save(notificationParmsReg);
				msg = "Notification Parms Added Successfully";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public NotificationParms getNotificationParmsList(String recordSrlNo,String tranType) {
		NotificationParms notificationParmsReg=new NotificationParms();
		try {
			List<NotificationParms> mandateList=notificationParmsRep.customFindById(recordSrlNo,tranType);
			if(mandateList.size()>0) {
				notificationParmsReg=mandateList.get(0);
			}
			
		}catch(Exception e) {
			notificationParmsReg=new NotificationParms();
		}
		return notificationParmsReg;
	}

	public String editNotificationParms(NotificationParms notificationParmsReg, String userid) {
		String msg = "";
		try {
			String parm1 = notificationParmsReg.getRECORD_SRL_NO();
			String parm2 = notificationParmsReg.getTRAN_CATEGORY();
			List<NotificationParms> notificationParmsList = notificationParmsRep.customFindById(parm1,parm2);
			if (notificationParmsList.size() > 0) {

				NotificationParms notificationParmsReg1 = notificationParmsList.get(0);

				if (notificationParmsReg.getRECORD_SRL_NO().equals(notificationParmsReg1.getRECORD_SRL_NO())
						&& notificationParmsReg.getNOTIFICATION_EVENT_NO().equals(notificationParmsReg1.getNOTIFICATION_EVENT_NO())						
						&& notificationParmsReg.getNOTIFICATION_EVENT_DESC().equals(notificationParmsReg1.getNOTIFICATION_EVENT_DESC())
						//&& notificationParmsReg.getSTART_DATE().equals(notificationParmsReg1.getSTART_DATE())
						//&& notificationParmsReg.getEND_DATE().equals(notificationParmsReg1.getEND_DATE())
						&& notificationParmsReg.getRECORD_DATE().equals(notificationParmsReg1.getRECORD_DATE())
						&& notificationParmsReg.getNOTIFICATION_EMAIL_1().equals(notificationParmsReg1.getNOTIFICATION_EMAIL_1())
						&& notificationParmsReg.getNOTIFICATION_EMAIL_2().equals(notificationParmsReg1.getNOTIFICATION_EMAIL_2())
						&& notificationParmsReg.getNOTIFICATION_EMAIL_3().equals(notificationParmsReg1.getNOTIFICATION_EMAIL_3())
						&& notificationParmsReg.getNOTIFICATION_MOBILE_1().equals(notificationParmsReg1.getNOTIFICATION_MOBILE_1())
						&& notificationParmsReg.getNOTIFICATION_MOBILE_2().equals(notificationParmsReg1.getNOTIFICATION_MOBILE_2())
						&& notificationParmsReg.getNOTIFICATION_MOBILE_3().equals(notificationParmsReg1.getNOTIFICATION_MOBILE_3())
						&& notificationParmsReg.getNOTIFICATION_USER_1().equals(notificationParmsReg1.getNOTIFICATION_USER_1())
						&& notificationParmsReg.getNOTIFICATION_USER_2().equals(notificationParmsReg1.getNOTIFICATION_USER_2())
						&& notificationParmsReg.getNOTIFICATION_USER_3().equals(notificationParmsReg1.getNOTIFICATION_USER_3())
						&& notificationParmsReg.getTRAN_CATEGORY().equals(notificationParmsReg1.getTRAN_CATEGORY())) {
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
					notificationParmsReg.setENTRY_USER(notificationParmsReg1.getENTRY_USER());
					notificationParmsReg.setENTRY_TIME(notificationParmsReg1.getENTRY_TIME());
					notificationParmsReg.setDEL_FLG("N");
					notificationParmsReg.setENTITY_FLG("N");
					notificationParmsReg.setMODIFY_USER(userid);
					notificationParmsReg.setMODIFY_TIME(new Date());
					notificationParmsRep.save(notificationParmsReg);
					msg = "NotificationParms Modified Successfully ";
				}

			} else {
				msg = "No Date Found";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public String verifyNotificationParms(NotificationParms notificationParmsReg, String userid) {
		String msg = "";
		try {
			List<NotificationParms> notificationParmsList = notificationParmsRep.customFindById(notificationParmsReg.getRECORD_SRL_NO().toString(),
					notificationParmsReg.getTRAN_CATEGORY().toString());
			if (notificationParmsList.size() > 0) {

				NotificationParms notificationParmsReg1 = notificationParmsList.get(0);


					notificationParmsReg.setENTRY_USER(notificationParmsReg1.getENTRY_USER());
					notificationParmsReg.setENTRY_TIME(notificationParmsReg1.getENTRY_TIME());
					notificationParmsReg.setDEL_FLG("N");
					notificationParmsReg.setENTITY_FLG("Y");
					notificationParmsReg.setVERIFY_USER(userid);
					notificationParmsReg.setVERIFY_TIME(new Date());
					notificationParmsRep.save(notificationParmsReg);
					msg = "NotificationParms Verified Successfully";

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
	
	public String createmerchantcate(String refcode,String refdesc,String userid,String Formmode) {
		String msg = "";
		try {
			String notificationParmsList = merchantCategoryRep.getlistcount(refcode);
			MerchantCategoryCodeEntity merchantCategoryCodeEntity =  new MerchantCategoryCodeEntity();
			if(Formmode.equals("add")) {
				System.out.println("Formmode1:"+Formmode);
			if (!notificationParmsList.equals("0")) {
				msg = "The given Merchant Code already exist";
			} else {
				merchantCategoryCodeEntity.setMerchant_code(refcode);
				merchantCategoryCodeEntity.setMerchant_desc(refdesc);
				merchantCategoryCodeEntity.setEntry_user(userid);
				merchantCategoryCodeEntity.setEntry_time(new Date());
				merchantCategoryCodeEntity.setDel_flg("N");
				merchantCategoryCodeEntity.setEntity_cre_flg("N");
				merchantCategoryRep.save(merchantCategoryCodeEntity);
				msg = "Merchant Category Code Added Successfully";
			}

		}else if(Formmode.equals("edit")){
			System.out.println("Formmode:"+Formmode);
			MerchantCategoryCodeEntity notificationParmsListedit = merchantCategoryRep.getlist(refcode);
			merchantCategoryCodeEntity.setEntry_user(notificationParmsListedit.getEntry_user());
			merchantCategoryCodeEntity.setMerchant_code(refcode);
			merchantCategoryCodeEntity.setMerchant_desc(refdesc);
			merchantCategoryCodeEntity.setEntry_time(notificationParmsListedit.getEntry_time());
			merchantCategoryCodeEntity.setModify_user(userid);
			merchantCategoryCodeEntity.setModify_time(new Date());
			merchantCategoryCodeEntity.setDel_flg("N");
			merchantCategoryCodeEntity.setModify_flg("Y");
			merchantCategoryCodeEntity.setEntity_cre_flg("N");
			merchantCategoryRep.save(merchantCategoryCodeEntity);
			msg = "Merchant Category Code Edited Successfully";
			}else if(Formmode.equals("delete")){
				System.out.println("Formmode:"+Formmode);
				MerchantCategoryCodeEntity notificationParmsListedit = merchantCategoryRep.getlist(refcode);
				merchantCategoryCodeEntity.setEntry_user(notificationParmsListedit.getEntry_user());
				merchantCategoryCodeEntity.setMerchant_code(refcode);
				merchantCategoryCodeEntity.setMerchant_desc(refdesc);
				merchantCategoryCodeEntity.setEntry_time(notificationParmsListedit.getEntry_time());
				merchantCategoryCodeEntity.setModify_user(userid);
				merchantCategoryCodeEntity.setModify_time(new Date());
				merchantCategoryCodeEntity.setDel_flg("Y");
				merchantCategoryCodeEntity.setModify_flg("Y");
				merchantCategoryCodeEntity.setEntity_cre_flg("N");
				merchantCategoryRep.save(merchantCategoryCodeEntity);
				msg = "Merchant Category Code Deleted Successfully";
				}
		}catch (Exception e) {
			msg = e.toString();
		}
			
		 
		return msg;
	}
	
		
public String cancle(NotificationParms notificationParmsReg, String userid) {
		
		
		System.out.println("user"+notificationParmsReg.getRECORD_SRL_NO());
			String msg = "";
			//Session hs = sessionFactory.getCurrentSession();
			//hs.createNativeQuery("delete from BIPS_USER_PROFILE_MOD_TABLE where user_id=?1").setParameter(1,userprofile.getUserid());
			
			notificationParmsRep.deleteById(notificationParmsReg.getRECORD_SRL_NO());
			msg = " NotificationParms Cancel Successfully";
			return msg;
		}

	
	
	
public String delete(NotificationParms notificationParmsReg, String userid) {
		
		
		System.out.println("user"+notificationParmsReg.getRECORD_SRL_NO());
			String msg = "";
			//Session hs = sessionFactory.getCurrentSession();
			//hs.createNativeQuery("delete from BIPS_USER_PROFILE_MOD_TABLE where user_id=?1").setParameter(1,userprofile.getUserid());
			
			notificationParmsRep.deleteById(notificationParmsReg.getRECORD_SRL_NO());
			msg = " NotificationParms Delete Successfully";
			return msg;
		}


}

	
	

