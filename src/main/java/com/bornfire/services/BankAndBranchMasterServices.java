package com.bornfire.services;

import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAgentTableRep;
import com.bornfire.entity.BankAgentTmpTable;
import com.bornfire.entity.BankAgentTmpTableRep;
import com.bornfire.entity.IPSAccessRole;
import com.bornfire.entity.IPSAccessRoleModTable;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSAuditTableRep;
import com.bornfire.entity.MerchantQRRegistration;
import com.bornfire.entity.MerchantQrCodeRegRep;
import com.bornfire.entity.cimMerchantQRcodeResponse;

@Service
@ConfigurationProperties("output")
@Transactional

public class BankAndBranchMasterServices {
	
	private static final org.jboss.logging.Logger logger = LoggerFactory.logger(BankAndBranchMasterServices.class);

	@Autowired
	BankAgentTableRep bankAgentTableRep;
	@Autowired
	BankAgentTmpTableRep bankAgentTmpTableRep;
	
	@Autowired
	MerchantQrCodeRegRep merchantQrCodeRegRep;
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	IPSServices ipsServices;
	
	@Autowired
	SequenceGenerator sequence;
	
	
	@Autowired
	IPSAuditTableRep ipsAuditTableRep;

	public String addBank(BankAgentTable bankAgentTable, String formmode,String userID) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";

		if (formmode.equals("editsubmit")) {

			
			BankAgentTable bankAgentTableOrgl=bankAgentTableRep.findById(bankAgentTable.getBank_code()).get();
			
			
			if(bankAgentTableOrgl.getBank_name().equals(bankAgentTable.getBank_name())&&
					bankAgentTableOrgl.getBank_agent().equals(bankAgentTable.getBank_agent())&&
					bankAgentTableOrgl.getBank_agent_account().equals(bankAgentTable.getBank_agent_account())&&	
					bankAgentTableOrgl.getAddress().equals(bankAgentTable.getAddress())) {
				msg="No any modification done";
		
			}else {
				String audit_ref_no=sequence.generateRequestUUId();
				BankAgentTmpTable upTmp=new BankAgentTmpTable(bankAgentTable);
				
				upTmp.setDel_flg("N");
				upTmp.setModify_flg("Y");
				upTmp.setEntity_flg("N");
				upTmp.setNew_bank_flg("N");
				upTmp.setModify_time(new Date());
				upTmp.setModify_user(userID);
				
				Session session = sessionFactory.getCurrentSession();
				session.saveOrUpdate(upTmp);
				
				
				
				
				bankAgentTableOrgl.setEntity_flg("N");
				bankAgentTableRep.save(bankAgentTableOrgl);
				msg = "Modified Successfully";
				
				StringBuilder stringBuilder=new StringBuilder();
				
				if(bankAgentTableOrgl.getBank_name().equals(bankAgentTable.getBank_name())||
						bankAgentTableOrgl.getBank_agent().equals(bankAgentTable.getBank_agent())||
						bankAgentTableOrgl.getBank_agent_account().equals(bankAgentTable.getBank_agent_account())||
						bankAgentTableOrgl.getAddress().equals(bankAgentTable.getAddress())) {
					
					if(!isNullCheck(bankAgentTableOrgl.getBank_name()).equals(isNullCheck(bankAgentTable.getBank_name()))) {
						stringBuilder=stringBuilder.append("Bank Name+"+bankAgentTableOrgl.getBank_name()+"+"+bankAgentTable.getBank_name()+"||");
					}
					
					if(!isNullCheck(bankAgentTableOrgl.getBank_agent()).equals(isNullCheck(bankAgentTable.getBank_agent()))) {
						stringBuilder=stringBuilder.append("Bank Agent+"+bankAgentTableOrgl.getBank_agent()+"+"+bankAgentTable.getBank_agent()+"||");
					}
					
					if(!isNullCheck(bankAgentTableOrgl.getBank_agent_account()).equals(isNullCheck(bankAgentTable.getBank_agent_account()))) {
						stringBuilder=stringBuilder.append("Bank Agent Account+"+bankAgentTableOrgl.getBank_agent_account()+"+"+bankAgentTable.getBank_agent_account()+"||");
					}
					if(!isNullCheck(bankAgentTableOrgl.getAddress()).equals(isNullCheck(bankAgentTable.getAddress()))) {
						stringBuilder=stringBuilder.append("Address+"+bankAgentTableOrgl.getAddress()+"+"+bankAgentTable.getAddress()+"||");
					}
					
					
					IPSAuditTable audit = new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(userID);
					audit.setFunc_code("PARTICIPANT BANK MODIFICATION");
					audit.setRemarks(bankAgentTable.getBank_code()+" : Participant Bank Modified Successfully");
					audit.setAudit_table("BIPS_OTHER_BANK_AGENT_TABLE");
					audit.setAudit_screen("Participant Bank-Modify");
					audit.setEvent_id(bankAgentTable.getBank_code());
					//audit.setEvent_name(up.getUsername());
					String modiDetails=stringBuilder.toString();
					audit.setModi_details(modiDetails);
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
			
				}
				

			}
		

		} else if (formmode.equals("verifysubmit")) {

			Optional<BankAgentTmpTable>dat=bankAgentTmpTableRep.findById(bankAgentTable.getBank_code());
			

			String delRec=deletePreviDeleeteRec(dat,bankAgentTable.getBank_agent());
			
			
			if(delRec.equals("0")) {
				String verifyBankCode=verifyBankCode(bankAgentTable.getBank_code(),userID);

				
				if(verifyBankCode.equals("0")) {
					
					
					String audit_ref_no=sequence.generateRequestUUId();
					IPSAuditTable audit = new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(userID);
					audit.setFunc_code("Participant Bank VERIFICATION");
					audit.setRemarks(dat.get().getBank_code()+" : Participant Bank Verified Successfully");
					audit.setAudit_table("BIPS_OTHER_BANK_AGENT_TABLE");
					audit.setAudit_screen("Participant Bank-Verify");
					audit.setEvent_id(dat.get().getBank_code());
					audit.setAuth_user(userID);
					audit.setAuth_time(new Date());
					//audit.setEvent_name(up.getUsername());
					if(!dat.get().getNew_bank_flg().equals("Y")) {
						IPSAuditTable audit1 = ipsAuditTableRep.getModifyList1(dat.get().getBank_code(),"PARTICIPANT BANK MODIFICATION");
						String modiDetails=audit1.getModi_details();
						audit.setModi_details(modiDetails);
					}else {
						audit.setModi_details("-");
					}
					
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
					
					
					msg = "Verified Successfully";
					bankAgentTmpTableRep.deleteById(bankAgentTable.getBank_code());
					
					return msg;
				}
				
			}


		} else if (formmode.equals("deletesubmit")) {

			BankAgentTable up = bankAgentTable;
			up.setDel_flg("Y");
			///up.setDisable_flg("N");
			up.setDel_user(userID);
			up.setDel_time(new Date());
			
			bankAgentTableRep.save(up);
			msg = "Deleted Successfully";

		}else if (formmode.equals("disablesubmit")) {

			BankAgentTable up = bankAgentTableRep.findByIdCustom(bankAgentTable.getBank_code());
			up.setDisable_flg("Y");
			
			up.setDisable_time(new Date());
			up.setDisable_user(userID);
			bankAgentTableRep.save(up);
			msg = "Disabled Successfully";

		}else if (formmode.equals("enablesubmit")) {

			BankAgentTable up = bankAgentTableRep.findByIdCustom(bankAgentTable.getBank_code());
			up.setDisable_flg("N");
			
			up.setDisable_time(new Date());
			up.setDisable_user(userID);
			bankAgentTableRep.save(up);
			msg = "Enabled Successfully";

		} else if (formmode.equals("addsubmit")) {

			BankAgentTmpTable upTmp = new BankAgentTmpTable(bankAgentTable);
			Optional<BankAgentTable> bankList=bankAgentTableRep.findById(bankAgentTable.getBank_code());
			List<BankAgentTable> bankListAgent=bankAgentTableRep.findByIdCustomAgent1(bankAgentTable.getBank_agent());

			if(bankList.isPresent()) {
				
				
				if(bankList.get().getDel_flg().equals("Y")) {
					
					//BankAgentTable up = bankAgentTable;
					upTmp.setEntity_flg("N");
					upTmp.setModify_flg("N");
					upTmp.setDisable_flg("N");
					upTmp.setDel_flg("N");
					upTmp.setNew_bank_flg("Y");
					upTmp.setEntry_time(new Date());
					upTmp.setEntry_user(userID);
					bankAgentTmpTableRep.save(upTmp);
					msg = "Bank Added Successfully";
					
					String audit_ref_no=sequence.generateRequestUUId();
					IPSAuditTable audit=new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(userID);
					audit.setFunc_code("BANK CREATION");
					audit.setRemarks(upTmp.getBank_code()+" : Participant Bank Created Successfully");
					audit.setAudit_table("BIPS_OTHER_BANK_AGENT_TABLE");
					audit.setAudit_screen("Participant Bank-Add");
					audit.setEvent_id(upTmp.getBank_code());
					audit.setEvent_name(userID);
					audit.setModi_details("-");
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
				}else {
					
					if(bankList.get().getBank_agent().equals(bankAgentTable.getBank_agent())){
						msg = "Bank Agent Already Exist";
					}else {
						msg = "Bank Code Already Exist";
					}
				}
			}else if(bankListAgent.size()>0){
                  if(bankListAgent.get(0).getDel_flg().equals("Y")) {
					
					//BankAgentTable up = bankAgentTable;
					upTmp.setEntity_flg("N");
					upTmp.setModify_flg("N");
					upTmp.setDisable_flg("N");
					upTmp.setDel_flg("N");
					upTmp.setNew_bank_flg("Y");
					upTmp.setEntry_time(new Date());
					upTmp.setEntry_user(userID);
					bankAgentTmpTableRep.save(upTmp);
					msg = "Bank Added Successfully";
					
					String audit_ref_no=sequence.generateRequestUUId();
					IPSAuditTable audit=new IPSAuditTable();
					audit.setAudit_date(new Date());
					audit.setEntry_time(new Date());
					audit.setEntry_user(userID);
					audit.setFunc_code("BANK CREATION");
					audit.setRemarks(upTmp.getBank_code()+" : Participant Bank Created Successfully");
					audit.setAudit_table("BIPS_OTHER_BANK_AGENT_TABLE");
					audit.setAudit_screen("Participant Bank-Add");
					audit.setEvent_id(upTmp.getBank_code());
					audit.setEvent_name(userID);
					audit.setModi_details("-");
					audit.setAudit_ref_no(audit_ref_no);
					
					ipsAuditTableRep.save(audit);
				}else {
					
					if(bankListAgent.get(0).getBank_agent().equals(bankAgentTable.getBank_agent())){
						msg = "Bank Agent Already Exist";
					}else {
						msg = "Bank Code Already Exist";
					}
				}
			}else {
				
				//System.out.println("Entry User"+bankAgentTable.getEntry_user());
				upTmp.setEntity_flg("N");
				upTmp.setModify_flg("N");
				upTmp.setDisable_flg("N");
				upTmp.setDel_flg("N");
				upTmp.setNew_bank_flg("Y");
				upTmp.setEntry_time(new Date());
				upTmp.setEntry_user(userID);
				bankAgentTmpTableRep.save(upTmp);;

				msg = "Bank Added Successfully";
				
				String audit_ref_no=sequence.generateRequestUUId();
				IPSAuditTable audit=new IPSAuditTable();
				audit.setAudit_date(new Date());
				audit.setEntry_time(new Date());
				audit.setEntry_user(userID);
				audit.setFunc_code("BANK CREATION");
				audit.setRemarks(upTmp.getBank_code()+" : Participant Bank Created Successfully");
				audit.setAudit_table("BIPS_OTHER_BANK_AGENT_TABLE");
				audit.setAudit_screen("Participant Bank-Add");
				audit.setEvent_id(userID);
				audit.setEvent_name(userID);
				audit.setModi_details("-");
				audit.setAudit_ref_no(audit_ref_no);
				
				ipsAuditTableRep.save(audit);
			}
			

		}
		return msg;

	}
	
	private String verifyBankCode(String bankCode,String USERID) {
		Optional<BankAgentTmpTable> user = bankAgentTmpTableRep.findById(bankCode);
		BankAgentTmpTable user1 = user.get();
		BankAgentTable verifiedData=new BankAgentTable(user1);
		
		verifiedData.setDel_flg("N");
		verifiedData.setModify_flg("N");
		verifiedData.setEntity_flg("Y");
		verifiedData.setNew_bank_flg("N");
		verifiedData.setVerify_user(USERID);
		verifiedData.setVerify_time(new Date());
		
		Session hs = sessionFactory.getCurrentSession();
		hs.saveOrUpdate(verifiedData);
		//bankAgentTableRep.save(verifiedData);
		return "0";
		
	}
	
	private String deletePreviDeleeteRec(Optional<BankAgentTmpTable> dat,String bankAgent) {
		if(!dat.get().getNew_bank_flg().equals("N")) {
			///delete Previous Deleted Records.
			List<BankAgentTable>dataLi=bankAgentTableRep.findByIdCustomAgent1(bankAgent);
			
			if(dataLi.size()>0) {
				//System.out.println("donne->"+dataLi.get(0).getBank_code());
				bankAgentTableRep.deleteById(dataLi.get(0).getBank_code());
				bankAgentTableRep.flush();
				return "0";
			}else {
				return "0";
			}
		}else {
			return "0";
		}
		
		
	}
	
	
	
	
	public String addMerchantQRcode(MerchantQRRegistration merchantQRgenerator, String formmode,String userID) throws UnknownHostException {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		
		
		if (formmode.equals("editsubmit")) {

			/*ResponseEntity<cimMerchantQRcodeResponse> merchantQRResponse=ipsServices.getMerchantQrCode(merchantQRgenerator);

			if(merchantQRResponse.getStatusCode()==HttpStatus.OK) {
				String QrImg=merchantQRResponse.getBody().getBase64QR();
				
				byte[] decodedBytesQR = Base64.getDecoder().decode(QrImg);*/
				
				MerchantQRRegistration up = merchantQRgenerator;
				up.setDel_flg("N");
				up.setEntity_flg("N");
				up.setModify_flg("Y");
				up.setModify_time(new Date());
				up.setEntry_time(new Date());
				up.setVerify_time(new Date());
				up.setModify_user(userID);
				//up.setQr_code(decodedBytesQR);
				
				merchantQrCodeRegRep.save(up);
				msg = "Modified Successfully";
			/*}else {
				if(merchantQRResponse.getStatusCode()==HttpStatus.BAD_REQUEST) {
					msg=merchantQRResponse.getBody().getError().toString()+":"+
				merchantQRResponse.getBody().getError_Desc().get(0);
				}else {
					msg="Something went wrong at server end";
				}
			}*/
				
			}
		  else if (formmode.equals("verifysubmit")) {

			  MerchantQRRegistration up = merchantQRgenerator;
			up.setDel_flg("N");
			up.setEntity_flg("Y");
			up.setModify_flg("N");

			up.setVerify_time(new Date());
			
			merchantQrCodeRegRep.save(up);
			msg = "Verified Successfully";

		} else if (formmode.equals("deletesubmit")) {

			MerchantQRRegistration up = merchantQRgenerator;
			up.setDel_flg("Y");
			
			up.setModify_time(new Date());
			
			merchantQrCodeRegRep.save(up);
			msg = "Deleted Successfully";

		} else if (formmode.equals("addsubmit")) {

			Optional<MerchantQRRegistration> bankList=merchantQrCodeRegRep.findById(merchantQRgenerator.getMerchant_acct_no());
			if(bankList.isPresent()) {
				if(bankList.get().getDel_flg().equals("Y")) {
					MerchantQRRegistration up = merchantQRgenerator;
					up.setEntity_flg("Y");
					up.setModify_flg("N");
					up.setDel_flg("N");
					up.setEntry_time(new Date());

					merchantQrCodeRegRep.save(up);
					msg = "Merchant QR Added Successfully";
				}else {
					msg = "Merchant Account Already Exist";
				}
			}else {
				/*ResponseEntity<cimMerchantQRcodeResponse> merchantQRResponse=ipsServices.getMerchantQrCode(merchantQRgenerator);

				if(merchantQRResponse.getStatusCode()==HttpStatus.OK) {
					
					String QrImg=merchantQRResponse.getBody().getBase64QR();
					
					byte[] decodedBytesQR = Base64.getDecoder().decode(QrImg);*/

					MerchantQRRegistration up = merchantQRgenerator;
					System.out.println("Entry User"+merchantQRgenerator.getEntry_user());
					up.setEntity_flg("Y");
					up.setModify_flg("N");
					up.setDel_flg("N");
					up.setEntry_time(new Date());
					//up.setQr_code(decodedBytesQR);
					merchantQrCodeRegRep.save(up);
					msg = "Merchant QR Added Successfully";
				/*}else {
					if(merchantQRResponse.getStatusCode()==HttpStatus.BAD_REQUEST) {
						msg=merchantQRResponse.getBody().getError().toString()+":"+
					merchantQRResponse.getBody().getError_Desc().get(0);
					}else {
						msg="Something went wrong at server end";
					}
				}*/
				
				
			}
			

		}
		return msg;

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
