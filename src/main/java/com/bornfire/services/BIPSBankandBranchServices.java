
package com.bornfire.services;


import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAndBranchBean;
import com.bornfire.entity.BankAndBranchBeanMod;
import com.bornfire.entity.BankAndBranchModRepository;
import com.bornfire.entity.BankAndBranchRepository;
import com.bornfire.entity.IPSAccessRole;
import com.bornfire.entity.IPSAccessRoleModTable;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSAuditTableRep;
import com.bornfire.entity.IPSUserPreofileMod;
import com.bornfire.entity.MerchantQRRegistration;
import com.bornfire.entity.MerchantQrCodeRegRep;

@Service
@Transactional
@ConfigurationProperties("output")
public class BIPSBankandBranchServices {
	
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	BankAndBranchRepository bipsSolRepository;
	
	@Autowired
	BankAndBranchModRepository bipsSolModRepository;
	
	@Autowired
	MerchantQrCodeRegRep merchantQrCodeRegRep;
	
	@Autowired
	SequenceGenerator sequence;
	
	@Autowired
	IPSAuditTableRep ipsAuditTableRep;

	private static final Logger logger = LoggerFactory.getLogger(BIPSBankandBranchServices.class);

	public String modDetails(BankAndBranchBean BankAndBranchBean, String formmode,String user) {

		String msg = "";
		if (formmode.equals("edit")) {
			
			String audit_ref_no=sequence.generateRequestUUId();
			
		
			BankAndBranchBean up1 = bipsSolRepository.findByIdcustom(BankAndBranchBean.getSol_id());
			
			BankAndBranchBeanMod upMod=new BankAndBranchBeanMod(BankAndBranchBean);
			
			
			if(isNullCheck(BankAndBranchBean.getSol_desc()).equals(isNullCheck(up1.getSol_desc()))&&
					isNullCheck(BankAndBranchBean.getSol_type()).equals(isNullCheck(up1.getSol_type()))&&
					isNullCheck(BankAndBranchBean.getBank_code()).equals(isNullCheck(up1.getBank_code()))&&
					isNullCheck(BankAndBranchBean.getBank_name()).equals(isNullCheck(up1.getBank_name()))&&
					isNullCheck(BankAndBranchBean.getParm_1()).equals(isNullCheck(up1.getParm_1()))&&
					isNullCheck(BankAndBranchBean.getParm_2()).equals(isNullCheck(up1.getParm_2()))&&
					isNullCheck(BankAndBranchBean.getAddr_1()).equals(isNullCheck(up1.getAddr_1()))&&
					isNullCheck(BankAndBranchBean.getAddr_2()).equals(isNullCheck(up1.getAddr_2()))&&
					isNullCheck(BankAndBranchBean.getCity_code()).equals(isNullCheck(up1.getCity_code()))&&
					isNullCheck(BankAndBranchBean.getState_code()).equals(isNullCheck(up1.getState_code()))&&
					isNullCheck(BankAndBranchBean.getCountry_code()).equals(isNullCheck(up1.getCountry_code()))&&
					isNullCheck(BankAndBranchBean.getZip_code()).equals(isNullCheck(up1.getZip_code()))) {
				msg="No any modification done";
		
			}else {
				
				upMod.setEntity_flg("N");
				upMod.setModify_flg("Y");
				upMod.setDel_flg("N");
				upMod.setNew_bank_flg("N");
				upMod.setModify_time(new Date());
				upMod.setModify_user(user);
				upMod.setEntry_user(up1.getEntry_user());
				upMod.setEntry_time(up1.getEntry_time());
				Session session = sessionFactory.getCurrentSession();
				session.saveOrUpdate(upMod);
				
				up1.setEntity_flg("N");
				bipsSolRepository.save(up1);
				
				msg = "Organisation Branch Details Modified Sucessfully";
				
				StringBuilder stringBuilder=new StringBuilder();
				
				if(isNullCheck(BankAndBranchBean.getSol_desc()).equals(isNullCheck(up1.getSol_desc()))||
						isNullCheck(BankAndBranchBean.getSol_type()).equals(isNullCheck(up1.getSol_type()))||
						isNullCheck(BankAndBranchBean.getBank_code()).equals(isNullCheck(up1.getBank_code()))||
						isNullCheck(BankAndBranchBean.getBank_name()).equals(isNullCheck(up1.getBank_name()))||
						isNullCheck(BankAndBranchBean.getParm_1()).equals(isNullCheck(up1.getParm_1()))||
						isNullCheck(BankAndBranchBean.getParm_2()).equals(isNullCheck(up1.getParm_2()))||
						isNullCheck(BankAndBranchBean.getAddr_1()).equals(isNullCheck(up1.getAddr_1()))||
						isNullCheck(BankAndBranchBean.getAddr_2()).equals(isNullCheck(up1.getAddr_2()))||
						isNullCheck(BankAndBranchBean.getCity_code()).equals(isNullCheck(up1.getCity_code()))||
						isNullCheck(BankAndBranchBean.getState_code()).equals(isNullCheck(up1.getState_code()))||
						isNullCheck(BankAndBranchBean.getCountry_code()).equals(isNullCheck(up1.getCountry_code()))||
						isNullCheck(BankAndBranchBean.getZip_code()).equals(isNullCheck(up1.getZip_code()))) {
					
					if(!isNullCheck(BankAndBranchBean.getSol_desc()).equals(isNullCheck(up1.getSol_desc()))) {
						stringBuilder=stringBuilder.append("Sol Desc+"+up1.getSol_desc()+"+"+BankAndBranchBean.getSol_desc()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getSol_type()).equals(isNullCheck(up1.getSol_type()))) {
						stringBuilder=stringBuilder.append("Sol Type+"+up1.getSol_type()+"+"+BankAndBranchBean.getSol_type()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getBank_code()).equals(isNullCheck(up1.getBank_code()))) {
						stringBuilder=stringBuilder.append("Bank Code+"+up1.getBank_code()+"+"+BankAndBranchBean.getBank_code()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getBank_name()).equals(isNullCheck(up1.getBank_name()))) {
						stringBuilder=stringBuilder.append("Bank Name+"+up1.getBank_name()+"+"+BankAndBranchBean.getBank_name()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getParm_1()).equals(isNullCheck(up1.getParm_1()))) {
						stringBuilder=stringBuilder.append("Branch Code+"+up1.getParm_1()+"+"+BankAndBranchBean.getParm_1()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getParm_2()).equals(isNullCheck(up1.getParm_2()))) {
						stringBuilder=stringBuilder.append("Branch Name+"+up1.getParm_2()+"+"+BankAndBranchBean.getParm_2()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getAddr_1()).equals(isNullCheck(up1.getAddr_1()))) {
						stringBuilder=stringBuilder.append("Address 1+"+up1.getAddr_1()+"+"+BankAndBranchBean.getAddr_1()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getAddr_2()).equals(isNullCheck(up1.getAddr_2()))) {
						stringBuilder=stringBuilder.append("Address 2+"+up1.getAddr_2()+"+"+BankAndBranchBean.getAddr_2()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getCity_code()).equals(isNullCheck(up1.getCity_code()))) {
						stringBuilder=stringBuilder.append("City+"+up1.getCity_code()+"+"+BankAndBranchBean.getCity_code()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getState_code()).equals(isNullCheck(up1.getState_code()))) {
						stringBuilder=stringBuilder.append("State+"+up1.getState_code()+"+"+BankAndBranchBean.getState_code()+"||");
					}
		
					if(!isNullCheck(BankAndBranchBean.getCountry_code()).equals(isNullCheck(up1.getCountry_code()))) {
						stringBuilder=stringBuilder.append("Country+"+up1.getCountry_code()+"+"+BankAndBranchBean.getCountry_code()+"||");
					}
					
					if(!isNullCheck(BankAndBranchBean.getZip_code()).equals(isNullCheck(up1.getZip_code()))) {
						stringBuilder=stringBuilder.append("Zip Code+"+up1.getZip_code()+"+"+BankAndBranchBean.getZip_code()+"||");
					}
					
					IPSAuditTable audit = new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(user);
					audit.setFunc_code("ORGANISATION AND BRANCH MODIFICATION");
					audit.setRemarks(BankAndBranchBean.getSol_id()+" : Branch Modified Successfully");
					audit.setAudit_table("BIPS_BANK_AND_BRANCH_TABLE");
					audit.setAudit_screen("Organisation and Branch-Modify");
					audit.setEvent_id(BankAndBranchBean.getSol_id());
					//audit.setEvent_name(up.getUsername());
					String modiDetails=stringBuilder.toString();
					audit.setModi_details(modiDetails);
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
			
				}
			}
		

			
		}else if (formmode.equals("add")) {
			
			String count = bipsSolRepository.getusercount(BankAndBranchBean.getSol_id());
			
			if (count.equals("0")) {
				BankAndBranchBeanMod up =new BankAndBranchBeanMod(BankAndBranchBean);
				
				up.setEntity_flg("N");
				up.setModify_flg("N");
				up.setDel_flg("N");
				up.setNew_bank_flg("Y");
				up.setEntry_time(new Date());
				up.setEntry_user(user);
				bipsSolModRepository.save(up);
				msg = "Organisation Branch Details Added Sucessfully";
				
				
				String audit_ref_no=sequence.generateRequestUUId();
				IPSAuditTable audit=new IPSAuditTable();
				audit.setAudit_date(new Date());
				audit.setEntry_time(new Date());
				audit.setEntry_user(user);
				audit.setFunc_code("ORGANISATION AND BRANCH CREATION");
				audit.setRemarks(BankAndBranchBean.getSol_id()+" : Branch Created Successfully");
				audit.setAudit_table("BIPS_BANK_AND_BRANCH");
				audit.setAudit_screen("Organisation and Branch-Add");
				audit.setEvent_id(user);
				audit.setEvent_name(user);
				audit.setModi_details("-");
				audit.setAudit_ref_no(audit_ref_no);
				
				ipsAuditTableRep.save(audit);
			}else {
				msg = "Organisation SolID Already existing";
			}
			
			
		} else if (formmode.equals("verify")) {
		
		BankAndBranchBeanMod up1 = bipsSolModRepository.findByIdcustom(BankAndBranchBean.getSol_id());
	       String verifyOrgBranch=verifyOrgBranch(BankAndBranchBean.getSol_id(),user);
			
			if(verifyOrgBranch.equals("0")) {
				
				String audit_ref_no=sequence.generateRequestUUId();
				IPSAuditTable audit = new IPSAuditTable();
				audit.setAudit_date(new Date());
				audit.setEntry_time(new Date());
				audit.setEntry_user(user);
				audit.setFunc_code("ORGANISATION AND BRANCH VERIFICATION");
				audit.setRemarks(up1.getSol_id()+" : Branch Verified Successfully");
				audit.setAudit_table("BIPS_BANK_AND_BRANCH");
				audit.setAudit_screen("Organisation And Branch-Verify");
				audit.setEvent_id(up1.getSol_id());
				audit.setAuth_user(user);
				audit.setAuth_time(new Date());
				//audit.setEvent_name(up.getUsername());
				if(up1.getNew_bank_flg().equals("N")) {
					IPSAuditTable audit1 = ipsAuditTableRep.getModifyList1(up1.getSol_id(),"ORGANISATION AND BRANCH MODIFICATION");
					String modiDetails=audit1.getModi_details();
					audit.setModi_details(modiDetails);
				}else {
					audit.setModi_details("-");
				}
				
				audit.setAudit_ref_no(audit_ref_no);
				
				ipsAuditTableRep.save(audit);
				
				
				
				msg = "Organisation Branch Verified Successfully";
				bipsSolModRepository.deleteById(BankAndBranchBean.getSol_id());
				return msg;
			}
		}else if (formmode.equals("cancel")) {
			BankAndBranchBeanMod up2 = bipsSolModRepository.findByIdcustom(BankAndBranchBean.getSol_id());
			
			if(up2.getNew_bank_flg().equals("Y")) {
				msg = "Organisation Branch Cancelled Successfully";
				bipsSolModRepository.deleteById(BankAndBranchBean.getSol_id());
				return msg;
			}else {
				BankAndBranchBeanMod up1 = bipsSolModRepository.findByIdcustom(BankAndBranchBean.getSol_id());
				String status=cancelOrgBranchModification(BankAndBranchBean.getSol_id(),user);
				if(status.equals("0")) {
					
					String audit_ref_no=sequence.generateRequestUUId();
					IPSAuditTable audit = new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(user);
					audit.setFunc_code("ORGANISATION AND BRANCH CANCEL");
					audit.setRemarks(up1.getSol_id()+" : Branch Cancelled Successfully");
					audit.setAudit_table("BIPS_BANK_AND_BRANCH");
					audit.setAudit_screen("Organisation And Branch-Verify");
					audit.setEvent_id(up1.getSol_id());
					audit.setAuth_user(user);
					audit.setAuth_time(new Date());
					//audit.setEvent_name(up.getUsername());
					if(up1.getNew_bank_flg().equals("N")) {
						IPSAuditTable audit1 = ipsAuditTableRep.getModifyList1(up1.getSol_id(),"ORGANISATION AND BRANCH MODIFICATION");
						String modiDetails=audit1.getModi_details();
						audit.setModi_details(modiDetails);
					}else {
						audit.setModi_details("-");
					}
					
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
					
					
					msg = "Organisation Branch Cancelled Successfully";
					bipsSolModRepository.deleteById(BankAndBranchBean.getSol_id());
					return msg;
				}
			}
			}
		return msg;
	}
	
	private String verifyOrgBranch(String sol_id,String USERID) {
		Optional<BankAndBranchBeanMod> sol = bipsSolModRepository.findById(sol_id);
		BankAndBranchBeanMod sol1 = sol.get();
		sol1.setDel_flg("N");
		sol1.setModify_flg("N");
		sol1.setEntity_flg("Y");
		sol1.setNew_bank_flg("N");
		sol1.setEntry_user(sol1.getEntry_user());
		sol1.setEntry_time(sol1.getEntry_time());
		sol1.setVerify_user(USERID);
		sol1.setVerify_time(new Date());
		BankAndBranchBean verifiedData=new BankAndBranchBean(sol1);
		bipsSolRepository.save(verifiedData);
		return "0";
		
	}
	
	private String cancelOrgBranchModification(String sol_id, String USERID) {
		Optional<BankAndBranchBean> user = bipsSolRepository.findById(sol_id);
		BankAndBranchBean user1 = user.get();
		user1.setDel_flg("N");
		user1.setModify_flg("N");
		user1.setEntity_flg("Y");
		user1.setNew_bank_flg("N");
		user1.setVerify_user(USERID);
		user1.setVerify_time(new Date());
		
		bipsSolRepository.save(user1);
		return "0";
	}
	
	public BankAndBranchBean getSolID(String solId) {

		BankAndBranchBean up = bipsSolRepository.findByIdcustom(solId);

		return up;

	}
	

	
	public BankAndBranchBeanMod getModSolID(String solId) {

		BankAndBranchBeanMod up = bipsSolModRepository.findByIdcustom(solId);

		return up;

	}

	public String getqrcode(String acct_no) {

		MerchantQRRegistration up = merchantQrCodeRegRep.findByIdCustom(acct_no);
		String imageAsBase64 = Base64.getEncoder().encodeToString(up.getQr_code());

			imageAsBase64 = "data:image/png;base64,"+imageAsBase64;
				
		return imageAsBase64;

	}

	public List<BankAndBranchBean> BankandBranchList(PageRequest of) {
		// TODO Auto-generated method stub
		 
		
		List<BankAndBranchBean> data=bipsSolRepository.findAllByCustom();
		return data;
	}
	
	
	private String isNullCheck(String inpData) {
		String outData="";
		if(inpData!=null) {
			if(!String.valueOf(inpData).equals("null")){
				if(!String.valueOf(inpData).equals("")) {
					outData=String.valueOf(inpData);
					return outData;
				}
			}
		}
		return outData;
	}

}
