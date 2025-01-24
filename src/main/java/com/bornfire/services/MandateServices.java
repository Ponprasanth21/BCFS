package com.bornfire.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAgentTableRep;
import com.bornfire.entity.CustomException;
import com.bornfire.entity.IPSUserPreofileMod;
import com.bornfire.entity.IPSUserProfileModRep;
import com.bornfire.entity.MCCreditTransferResponse;
import com.bornfire.entity.MandateMaster;
import com.bornfire.entity.MandateMasterRep;
import com.bornfire.entity.UserProfile;
import com.monitorjbl.xlsx.StreamingReader;

@Service
@ConfigurationProperties("output")
@Transactional
public class MandateServices {

	
	@Autowired
	MandateMasterRep mandateMasterRep;
	
	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	SequenceGenerator sequence;
	
	
	
	public List<MandateMaster> getMandateList() {
		List<MandateMaster> list=mandateMasterRep.customFindByAll();
		return list;
	}

	public String createMandate(MandateMaster mandateMaster, String userid) {
		String msg = "";
		try {
			List<MandateMaster> mandateList = mandateMasterRep.customFindById(mandateMaster.getMand_account_no(),
					mandateMaster.getBen_account_no());
			if (mandateList.size() > 0) {
				msg = "The given parameter already exist";
			} else {
				mandateMaster.setEntry_user(userid);
				mandateMaster.setEntry_time(new Date());
				mandateMaster.setDel_flg("N");
				mandateMaster.setEntity_cre_flg("Y");
				mandateMasterRep.save(mandateMaster);
				msg = "Mandate Added Successfully";
			}

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public MandateMaster getMandateIndList(String remAcctNumber,String benAcctNumber) {
		MandateMaster mandateMaster=new MandateMaster();
		try {
			List<MandateMaster> mandateList=mandateMasterRep.customFindById(remAcctNumber,benAcctNumber);
			if(mandateList.size()>0) {
				mandateMaster=mandateList.get(0);
			}
			
		}catch(Exception e) {
			mandateMaster=new MandateMaster();
		}
		return mandateMaster;
	}

	public String editMandate(MandateMaster mandateMaster, String userid) {
		String msg = "";
		try {
			List<MandateMaster> mandateList = mandateMasterRep.customFindById(mandateMaster.getMand_account_no(),
					mandateMaster.getBen_account_no());
			
			System.out.println();
			if (mandateList.size() > 0) {

				MandateMaster mandateMaster1 = mandateList.get(0);

				/*if (mandateMaster.getMand_account_no().equals(mandateMaster1.getMand_account_no())
						&& mandateMaster.getMand_account_no().equals(mandateMaster1.getMand_account_no())
						&& mandateMaster.getMand_account_name().equals(mandateMaster1.getMand_account_name())
						&& mandateMaster.getPeriodicity().equals(mandateMaster1.getPeriodicity())
						&& mandateMaster.getMand_account_type().equals(mandateMaster1.getMand_account_type())
						&& mandateMaster.getBen_account_no().equals(mandateMaster1.getBen_account_no())
						&& mandateMaster.getBen_account_name().equals(mandateMaster1.getBen_account_name())
						&& mandateMaster.getBen_bank_code().equals(mandateMaster1.getBen_bank_code())
						&& mandateMaster.getAuth_amount().equals(mandateMaster1.getAuth_amount())
						&& mandateMaster.getStart_date().equals(mandateMaster1.getStart_date())
						&& mandateMaster.getEnd_date().equals(mandateMaster1.getEnd_date())
						//&& mandateMaster.getMand_doc_image().equals(mandateMaster1.getMand_doc_image())
						&& mandateMaster.getPurpose().equals(mandateMaster1.getPurpose())
						&& mandateMaster.getRemarks().equals(mandateMaster1.getRemarks())) {
					msg = "No Any Modification Done";
				} else {*/
					mandateMaster1.setMand_account_name(mandateMaster.getMand_account_name());
					mandateMaster1.setBen_account_name(mandateMaster.getBen_account_name());
					mandateMaster1.setPeriodicity(mandateMaster.getPeriodicity());
					mandateMaster1.setMand_account_type(mandateMaster.getMand_account_type());
					mandateMaster1.setBen_bank_code(mandateMaster.getBen_bank_code());
					mandateMaster1.setAuth_amount(mandateMaster.getAuth_amount());
					mandateMaster1.setEnd_date(mandateMaster.getEnd_date());
					mandateMaster1.setStart_date(mandateMaster.getStart_date());
					mandateMaster1.setPurpose(mandateMaster.getPurpose());
					mandateMaster1.setRemarks(mandateMaster.getRemarks());
					mandateMaster1.setMand_doc_image(mandateMaster.getMand_doc_image());
					mandateMaster1.setModify_user(userid);
					mandateMaster1.setModify_time(new Date());
					
					mandateMaster1.setMandate_lodge_date(mandateMaster.getMandate_lodge_date());
					mandateMaster1.setRecord_status(mandateMaster.getRecord_status());
					mandateMaster1.setMand_type(mandateMaster.getMand_type());
					mandateMaster1.setServices(mandateMaster.getServices());
					
					if(mandateMaster.getMin_amount()!=null) {
						mandateMaster1.setMin_amount(mandateMaster.getMin_amount());
					}
					
					if(mandateMaster.getMax_amount()!=null) {
						mandateMaster1.setMax_amount(mandateMaster.getMax_amount());
					}
					mandateMaster1.setMand_remarks(mandateMaster.getMand_remarks());
					
					mandateMaster1.setSuspend_flg(mandateMaster.getSuspend_flg());
					mandateMaster1.setSuspend_date(mandateMaster.getSuspend_date());
					mandateMaster1.setLodged_by(mandateMaster.getLodged_by());
					mandateMaster1.setLodger_remarks(mandateMaster.getLodger_remarks());
					
					mandateMasterRep.save(mandateMaster1);
					msg = "Mandate Modified Successfully";
				}

			/*} else {
				msg = "No Date Found";
			}*/

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	public String deleteMandate(MandateMaster mandateMaster, String userid) {
		String msg="";
		try {
			List<MandateMaster> mandateList=mandateMasterRep.customFindById(mandateMaster.getMand_account_no(), mandateMaster.getBen_account_no());
			if(mandateList.size()>0) {
				
				MandateMaster mandateMaster1=mandateList.get(0);
			
					mandateMaster1.setModify_user(userid);
					mandateMaster1.setModify_time(new Date());
					mandateMaster1.setDel_flg("Y");
					mandateMasterRep.save(mandateMaster1);
					msg="Mandate Deleted Successfully";
				
			
			}else {
				msg="No Date Found";
			}
			
		}catch(Exception e) {
			msg="Error Occured,Please Contact Administrator";
		}
		return msg;
	}

	@SuppressWarnings("deprecation")
	public List<MandateMaster> processUploadMandate(String screenId, MultipartFile file, String userid)
			throws SQLException, FileNotFoundException, IOException {

		String fileName = file.getOriginalFilename();

		String fileExt = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileExt = fileName.substring(i + 1);
		}

		List<MandateMaster> result = new ArrayList<MandateMaster>();

		if (fileName.contains("Mandate_Management")) {
			if (fileExt.equals("xlsx") || fileExt.equals("xls")) {

				try {

					Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
							.open(file.getInputStream());

					List<HashMap<Integer, String>> mapList = new ArrayList<>();
					for (Sheet s : workbook) {
						System.out.println(s.getLastRowNum());
						for (Row r : s) {
							System.out.println(r.getRowNum());
							if (r.getRowNum() == 0) {
								continue;
							}
							String val = null;

							HashMap<Integer, String> map = new HashMap<>();
							for (int j = 0; j < 11; j++) {
								Cell cell = r.getCell(j);

								if (cell.getCellType() == 0) {
									double val1 = cell.getNumericCellValue();
									val = String.valueOf(val1);
									map.put(j, val);
								} else {
									val = cell.getStringCellValue();
									map.put(j, val);
								}
							}

							mapList.add(map);

						}

					}

					/// Generate Document Ref ID

					for (HashMap<Integer, String> item : mapList) {
						MandateMaster mandateMaster = new MandateMaster();

						mandateMaster.setMand_account_no(item.get(0));
						mandateMaster.setMand_account_name(item.get(1));
						mandateMaster.setPeriodicity(item.get(2));
						mandateMaster.setMand_account_type(item.get(3));
						mandateMaster.setBen_account_no(item.get(4));
						mandateMaster.setBen_account_name(item.get(5));
						mandateMaster.setBen_bank_name(item.get(6));
						mandateMaster.setMand_cust_ref_no(item.get(7));
						mandateMaster.setPurpose(item.get(8));
						mandateMaster.setRemarks(item.get(9));
						mandateMaster.setMand_bank_code(item.get(10));
						result.add(mandateMaster);
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("error" + e);
					throw new CustomException("File Not Uploaded");
				}
			} else {
				System.out.println("sgsdgf");
				throw new CustomException("Invalid File Name");
			}
		} else {
			System.out.println("dsfjhf");
			throw new CustomException("Invalid File Name");
		}
	}
	public MCCreditTransferResponse getDetailsMandate(MandateMaster mandateMaster, String formmode) {

		MCCreditTransferResponse res = new MCCreditTransferResponse();
		if (formmode.equals("submit")) {
			
			

			String[] list = mandateMaster.getMand_account_no().split(",");

			String remName = mandateMaster.getMand_account_name().split(",")[0];
			boolean isRemName = Arrays.asList(mandateMaster.getMand_account_name().split(",")).stream()
					.allMatch(x -> x.equals(remName));

			String remAcctNum = mandateMaster.getMand_account_no().split(",")[0];
			boolean isRemAcctNum = Arrays.asList(mandateMaster.getMand_account_no().split(",")).stream()
					.allMatch(x -> x.equals(remAcctNum));

			System.out.println("kalai" + isRemName + isRemAcctNum);
			if (isRemName && isRemAcctNum) {
				for (int i = 0; i < list.length; i++) {

					if (mandateMaster.getMand_account_no().split(",")[i] != ""
							&& mandateMaster.getMand_account_name().split(",")[i] != ""
							&& mandateMaster.getPeriodicity().split(",")[i] != ""
							&& mandateMaster.getMand_account_type().split(",")[i] != ""
							&& mandateMaster.getBen_account_no().split(",")[i] != ""
							&& mandateMaster.getBen_account_name().split(",")[i] != ""
							&& mandateMaster.getBen_bank_name().split(",")[i] != ""
							&& mandateMaster.getMand_cust_ref_no().split(",")[i] != ""
							&& mandateMaster.getPurpose().split(",")[i] != ""
							&& mandateMaster.getRemarks().split(",")[i] != ""
							&& mandateMaster.getMand_bank_code().split(",")[i] != "") {

						MandateMaster mandateupload = new MandateMaster();
						// mandateupload.setTran_ref_id(mandateMaster.getTran_ref_id().split(",")[i]);
						mandateupload.setMand_account_name(mandateMaster.getMand_account_name().split(",")[i]);
						mandateupload.setMand_account_type(mandateMaster.getMand_account_type().split(",")[i]);
                        mandateupload.setPeriodicity(mandateMaster.getPeriodicity().split(",")[i]);
						mandateupload.setPurpose(mandateMaster.getPurpose().split(",")[i]);
						mandateupload.setRemarks(mandateMaster.getRemarks().split(",")[i]);
						mandateupload.setMandate_ref_no(sequence.generateMandateRefNo());

						if (mandateMaster.getMand_account_no().split(",")[i].length() == 14
								&& mandateMaster.getMand_account_no().split(",")[i].matches("[0-9]+")) {
							mandateupload.setMand_account_no(mandateMaster.getMand_account_no().split(",")[i]);
						} else {
							res.setStatus("You have entered an Invalid Remitter Account Number");
							res.setTranID("0");
							return res;

						}
						mandateupload.setBen_account_name(mandateMaster.getBen_account_name().split(",")[i]);
						if (mandateMaster.getBen_account_no().split(",")[i].length() > 9
								&& mandateMaster.getBen_account_no().split(",")[i].matches("[0-9]+")) {
							mandateupload.setBen_account_no(mandateMaster.getBen_account_no().split(",")[i]);
						} else {
							res.setStatus("You have entered an Invalid Beneficiary Account Number");
							res.setTranID("0");
							return res;

						}

						// mandateupload.setTran_crncy_code(mandateMaster.getTran_crncy_code().split(",")[i]);
						// mandateupload.setDoc_ref_id(mandateMaster.getDoc_ref_id().split(",")[i]);
						// mandateupload.setDoc_sub_id(mandateMaster.getDoc_sub_id().split(",")[i]);

						if (mandateMaster.getMand_cust_ref_no().split(",")[i]
								.matches("((\\d{1,4})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = mandateMaster.getMand_cust_ref_no().split(",")[i];
							mandateupload.setAuth_amount(new BigDecimal(trAmount));
						} else {
							res.setStatus("You have entered an Invalid Amount");
							res.setTranID("0");
							return res;

						}
						if (invalidBankCode(mandateMaster.getMand_bank_code().split(",")[i])) {
							res.setStatus("Invalid Bank Code");
							res.setTranID("0");
							return res;

						} else {
							mandateupload.setMand_bank_code(mandateMaster.getMand_bank_code().split(",")[i]);
						}
						mandateupload.setStart_date(new Date());
						mandateupload.setDel_flg("N");
						mandateupload.setEntity_cre_flg("Y");
						// mandateupload.setSplit_flg("N");
						mandateupload.setEntry_time(new Date());

						mandateMasterRep.save(mandateupload);
					}

					else {

						res.setStatus("Info Missing.. Please Checkout All the Fields");
						res.setTranID("0");
						return res;
					}

				}
				res.setStatus("Mandate Submitted Successfully");
				res.setTranID("0");
			} else {
				res.setStatus("Info Missing.. Please Checkout All the Fields");
				res.setTranID("0");
				return res;
			}

		}
		return res;

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
	

	public MandateMaster BlobImage(String Mandate_ref_no) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(Mandate_ref_no);
		@SuppressWarnings("unchecked")
		List<MandateMaster> query = (List<MandateMaster>) session
				.createQuery("from MandateMaster where mandate_ref_no=?1").setParameter(1, Mandate_ref_no).getResultList();
		return query.get(0);

	};
	
	public IPSUserPreofileMod UserBlobImage(String userID) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(userID);
		
		@SuppressWarnings("unchecked")
		
		
		List<IPSUserPreofileMod> query = (List<IPSUserPreofileMod>) session
				.createQuery("from IPSUserPreofileMod where userid=?1").setParameter(1, userID).getResultList();
		return query.get(0);

	};

	public UserProfile UserBlobImage1(String userID) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(userID);
		
		@SuppressWarnings("unchecked")
		
		
		List<UserProfile> query2 = (List<UserProfile>) session
				.createQuery("from UserProfile where userid=?1").setParameter(1, userID).getResultList();
		return query2.get(0);
		
		
	};
}
