package com.bornfire.services;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.bornfire.configuration.EmailStatement;
import com.bornfire.configuration.PasswordEncryption;
import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSAuditTableRep;
import com.bornfire.entity.IPSUserPreofileMod;
import com.bornfire.entity.IPSUserProfileModRep;
import com.bornfire.entity.LdapUserList;
import com.bornfire.entity.LoginSecurity;
import com.bornfire.entity.LoginSecurityRepository;
import com.bornfire.entity.UserProfile;
import com.bornfire.entity.UserProfileRep;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
@ConfigurationProperties("output")
@Transactional
public class UserProfileService {

	@Autowired
	UserProfileRep userProfileRep;

	@Autowired
	IPSUserProfileModRep ipsUserProfileModRep;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	DataSource srcdataSource;

	@Autowired
	IPSAuditTableRep ipsAuditTableRep;
	
	@Autowired
	LoginSecurityRepository loginSecurityRep;
	
	@Autowired
	SequenceGenerator sequence;
	
	
	@NotNull
	private String exportpath;

	@Value("${default.password}")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExportpath() {
		return exportpath;
	}

	public void setExportpath(String exportpath) {
		this.exportpath = exportpath;
	}

	public String addUser(IPSUserPreofileMod userProfile, String formmode, String inputUser) {

		String msg = "";
		
		String audit_ref_no=sequence.generateRequestUUId();

		IPSAuditTable audit = new IPSAuditTable();

		String count = userProfileRep.getusercount(userProfile.getUserid());
		System.out.println(count + "insode lioop" + userProfile.getUserid());
		try {

			if (formmode.equals("add")) {

			
					if (count.equals("0")) {

						IPSUserPreofileMod up = userProfile;
						try {
							String encryptedPassword= PasswordEncryption.getEncryptedPassword("Bornfire@123");
							// System.out.println( PasswordEncryption.getEncryptedPassword("Bornfire@123"));
							//String encryptedPassword = PasswordEncryption.getEncryptedPassword(userProfile.getPassword());

							if (up.getLogin_status().equals("Active")) {
								up.setUser_locked_flg("N");
							} else {
								up.setUser_locked_flg("Y");
							}

							if (up.getUser_status().equals("Active")) {
								up.setDisable_flg("N");
							} else {
								up.setDisable_flg("Y");
							}

							up.setEntity_flg("N");
							up.setDel_flg("N");
							up.setModify_flg("N");
							up.setEntry_time(new Date());
							up.setEntry_user(inputUser);
							up.setModify_time(new Date());
							up.setModify_user(inputUser);
							up.setNew_user_flg("Y");

							up.setLogin_flg("N");
							up.setNo_of_attmp(0);
							up.setPassword(encryptedPassword);

							audit.setAudit_date(new Date());
							audit.setEntry_time(new Date());
							audit.setEntry_user(inputUser);
							audit.setFunc_code("USER CREATION");
							audit.setRemarks(userProfile.getUserid()+" : User Created Successfully");
							audit.setAudit_table("BIPS_USER_PROFILE");
							audit.setAudit_screen("USER PROFILE");
							audit.setEvent_id(up.getUserid());
							audit.setEvent_name(up.getUsername());
							audit.setModi_details("-");
							audit.setAudit_ref_no(audit_ref_no);
							
							ipsAuditTableRep.save(audit);
							
						
							

						} catch (Exception e) {
							e.printStackTrace();
						}

						ipsUserProfileModRep.save(up);

						msg = "User Created Successfully";
					} else {
						msg = "User Already existing";
					}
				}else {
					msg = "Invalid User.User Not Found";
				}

		

		} catch (Exception e) {
			msg = "Error Occured. Please contact Administrator";
			e.printStackTrace();
		}

		return msg;
	}

	public String editUser(IPSUserPreofileMod userProfile, String formmode, String inputUser) {

		String msg = "";

		String count = userProfileRep.getusercount(userProfile.getUserid());
		String audit_ref_no=sequence.generateRequestUUId();

		IPSAuditTable audit = new IPSAuditTable();
		try {

			if (formmode.equals("edit")) {
				System.out.println("edit mode");

				Optional<UserProfile> up = userProfileRep.findById(userProfile.getUserid());

				if (up.isPresent()) {
					UserProfile us1 = up.get();
					System.out.println("2");

					DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
					String acctexpold = dateFormat.format(us1.getAcc_exp_date());
					String acctexpnew = dateFormat.format(userProfile.getAcc_exp_date());
					String disableSold = dateFormat.format(us1.getDisable_start_date());
					String disableSnew = dateFormat.format(userProfile.getDisable_start_date());
					String disableEold = dateFormat.format(us1.getDisable_end_date());
					String disableEnew = dateFormat.format(userProfile.getDisable_end_date());
	
						System.out.println("4");
						
						userProfile.setPassword(up.get().getPassword());

						if (userProfile.getLogin_status().equals("Active")) {
							userProfile.setUser_locked_flg("N");
						} else {
							userProfile.setUser_locked_flg("Y");
						}

						if (userProfile.getUser_status().equals("Active")) {
							userProfile.setDisable_flg("N");
						} else {
							userProfile.setDisable_flg("Y");
						}

						userProfile.setNo_of_attmp(0);
						userProfile.setEntity_flg("N");
						userProfile.setModify_flg("Y");
						userProfile.setModify_user(inputUser);
						userProfile.setModify_time(new Date());

						Session session = sessionFactory.getCurrentSession();
						session.saveOrUpdate(userProfile);

						// userProfileModRep.save(userProfile)
						
						
						
						if ((us1.getEmail_id().equals(userProfile.getEmail_id()))
								&& (us1.getMob_number().equals(userProfile.getMob_number()))
								&& (us1.getLogin_low().equals(userProfile.getLogin_low()))
								&& (us1.getLogin_high().equals(userProfile.getLogin_high()))
								&& (acctexpold.equals(acctexpnew)) && (disableSold.equals(disableSnew))
								&& (disableEold.equals(disableEnew)) //&& (passOLD.equals(passNEW))
								&& (us1.getUser_status().equals(userProfile.getUser_status()))
								&& (us1.getLogin_status().equals(userProfile.getLogin_status()))
								//&& (us1.getRemarks().equals(userProfile.getRemarks()))
								&& (us1.getRemarks() == userProfile.getRemarks())
								&& (us1.getRole_id().equals(userProfile.getRole_id()))
							&& (us1.getCountry_code().equals(userProfile.getCountry_code()))){
							msg="No Modification is done";
							
						}else {
							UserProfile us = userProfileRep.findById(userProfile.getUserid()).get();
							us.setEntity_flg("N");
							userProfileRep.save(us);
							msg = "User Modified Successfully";

							
						}
						StringBuilder stringBuilder=new StringBuilder();
						if(!us1.getEmail_id().equals(userProfile.getEmail_id())) {
							stringBuilder=stringBuilder.append("Email ID+"+us1.getEmail_id()+"+"+userProfile.getEmail_id()+"||");
						}
						
						if(!us1.getMob_number().equals(userProfile.getMob_number())) {
							stringBuilder=stringBuilder.append("Mobile Number+"+us1.getMob_number()+"+"+userProfile.getMob_number()+"||");
						}
						
						if(!us1.getLogin_low().equals(userProfile.getLogin_low())) {
							stringBuilder=stringBuilder.append("Login Low Time+"+us1.getLogin_low()+"+"+userProfile.getLogin_low()+"||");
						}
						
						
						if(!us1.getLogin_high().equals(userProfile.getLogin_high())) {
							stringBuilder=stringBuilder.append("Login High Time+"+us1.getLogin_high()+"+"+userProfile.getLogin_high()+"||");
						}
						
						if((!acctexpold.equals(acctexpnew))) {
							stringBuilder=stringBuilder.append("Account Expiry Time+"+acctexpold+"+"+acctexpnew+"||");
						}
						
						if((!disableSold.equals(disableSnew))) {
							stringBuilder=stringBuilder.append("Disable Start Date+"+disableSold+"+"+disableSnew+"||");
						}
						
						if(!(disableEold.equals(disableEnew))) {
							stringBuilder=stringBuilder.append("Disable End Date+"+disableEold+"+"+disableEnew+"||");
						}
						
						if(!us1.getUser_status().equals(userProfile.getUser_status())) {
							stringBuilder=stringBuilder.append("User Status+"+us1.getUser_status()+"+"+userProfile.getUser_status()+"||");
						}
						
						if(!us1.getLogin_status().equals(userProfile.getLogin_status())) {
							stringBuilder=stringBuilder.append("Login Status+"+us1.getLogin_status()+"+"+userProfile.getLogin_status()+"||");
						}
						
						if(!us1.getRole_id().equals(userProfile.getRole_id())) {
							stringBuilder=stringBuilder.append("Role ID+"+us1.getRole_id()+"+"+userProfile.getRole_id()+"||");
						}
						
						
						 if(!us1.getRemarks().equals(userProfile.getRemarks())) { stringBuilder=stringBuilder.append("Remarks+"+us1.getRemarks()+"+"+
						 userProfile.getRemarks()); 
						 }
						
					
						
						audit.setAudit_date(new Date());
						audit.setEntry_time(new Date());
						audit.setEntry_user(inputUser);
						audit.setFunc_code("USER MODIFICATION");
						audit.setRemarks(userProfile.getUserid()+" : User Modified Successfully");
						audit.setAudit_table("BIPS_USER_PROFILE");
						audit.setAudit_screen("USER PROFILE");
						audit.setEvent_id(userProfile.getUserid());
						//audit.setEvent_name(up.getUsername());
						String modiDetails=stringBuilder.toString();
						audit.setModi_details(modiDetails);
						audit.setAudit_ref_no(audit_ref_no);
						
						ipsAuditTableRep.save(audit);
				}
			}
		} catch (Exception e) {
			msg = "Error Occured. Please contact Administrator";
			e.printStackTrace();
		}

		return msg;

	}

	public List<UserProfile> getUsersList(String Id) {

		List<UserProfile> users = userProfileRep.findByAll(Id);

		return users;

	}

	public UserProfile getUser(String id) {

		if (userProfileRep.existsById(id)) {
			UserProfile up = userProfileRep.findById(id).get();
			up.getEntity_flg();
			up.getDel_flg();
			return up;
		} else {
			return new UserProfile();
		}

	};

	public IPSUserPreofileMod getUser1(String id) {

		if (ipsUserProfileModRep.existsById(id)) {
			IPSUserPreofileMod up = ipsUserProfileModRep.findById(id).get();
			up.getEntity_flg();
			up.getDel_flg();
			return up;
		} else {
			return new IPSUserPreofileMod();
		}

	};

	public String verifyUser(IPSUserPreofileMod up, String inputUser) {
		String msg = "";

		Optional<UserProfile> user1 = userProfileRep.findById(up.getUserid());

		IPSAuditTable audit = ipsAuditTableRep.getAuditVerifyUser(up.getUserid());
		String audit_ref_no=sequence.generateRequestUUId();

		try {

			if (up.getLogin_status().equals("Active")) {
				up.setUser_locked_flg("N");
				up.setNo_of_attmp(0);
			} else {
				up.setUser_locked_flg("Y");
			}

			if (up.getUser_status().equals("Active")) {
				up.setDisable_flg("N");
			} else {
				up.setDisable_flg("Y");
			}

			up.setEntity_flg("Y");
			up.setAuth_time(new Date());
			up.setAuth_user(inputUser);
			up.setDel_flg("N");
			up.setModify_flg("N");
			up.setLogin_flg("Y");// To prompt the user for changing the password at first login
			up.setNo_of_attmp(0);
			
			System.out.println(up.getCountry_code()+"hiiiw23");
			if (user1.isPresent()) {
				up.setPassword(user1.get().getPassword());
				Optional<IPSUserPreofileMod> us = ipsUserProfileModRep.findById(up.getUserid());
				//String encryptedPassword = PasswordEncryption.getEncryptedPassword(this.password);
				up.setPassword(us.get().getPassword());
				up.setPhoto(us.get().getPhoto());
				//up.setPhoto(user1.get().getPhoto());
			} else {
				Optional<IPSUserPreofileMod> us = ipsUserProfileModRep.findById(up.getUserid());
				//String encryptedPassword = PasswordEncryption.getEncryptedPassword(this.password);
				up.setPassword(us.get().getPassword());
				up.setPhoto(us.get().getPhoto());
				
				
				
			}

			UserProfile user = new UserProfile(up);
			user.setEntity_flg("Y");
			user.setCountry_code(up.getCountry_code());

			userProfileRep.save(user);
			
			
			//session.remove(up);

			if (!user1.isPresent()) {
				
				IPSAuditTable auditTable=new IPSAuditTable();
				auditTable.setAudit_date(new Date());
				auditTable.setAudit_table("BIPS_USER_PROFILE");
				auditTable.setAudit_screen("USER PROFILE - VERIFICATION");
				auditTable.setFunc_code("USER VERIFICATION");
				auditTable.setRemarks(up.getUserid()+" : Verified Successfully");
				auditTable.setEvent_id(up.getUserid());
				auditTable.setEvent_name(up.getUsername());
				auditTable.setModi_details("-");
				auditTable.setAuth_user(inputUser);
				auditTable.setAuth_time(new Date());
				auditTable.setEntry_time(new Date());
				auditTable.setEntry_user(inputUser);
				auditTable.setAudit_ref_no(audit_ref_no);
				ipsAuditTableRep.save(auditTable);
				String alertcode = "USER-ADD";

			} else {
				IPSAuditTable audit1 = ipsAuditTableRep.getModifyList(up.getUserid(),"USER MODIFICATION");
			
				IPSAuditTable auditTable=new IPSAuditTable();
				auditTable.setAudit_date(new Date());
				auditTable.setAudit_table("BIPS_USER_PROFILE");
				auditTable.setAudit_screen("USER PROFILE - VERIFICATION");
				auditTable.setFunc_code("USER VERIFICATION");
				auditTable.setRemarks(up.getUserid()+" : Verified Successfully");
				auditTable.setEvent_id(up.getUserid());
				auditTable.setEvent_name(up.getUsername());
				auditTable.setModi_details(audit1.getModi_details());
				auditTable.setAuth_user(inputUser);
				auditTable.setAuth_time(new Date());
				auditTable.setEntry_time(new Date());
				auditTable.setEntry_user(inputUser);
				auditTable.setAudit_ref_no(audit_ref_no);
				ipsAuditTableRep.save(auditTable);
				
			}
			
			msg = "User Verified Successfully";

		} catch (Exception e) {
		
			e.printStackTrace();
		}

		return msg;
	}

	public String verifyUser1(UserProfile userProfile, IPSUserPreofileMod userProfile1, String userid) {

		String msg = "";

		Optional<UserProfile> up = userProfileRep.findById(userProfile.getUserid());
		Optional<IPSUserPreofileMod> up1 = ipsUserProfileModRep.findById(userProfile1.getUserid());

		try {

			if (up.isPresent() && up1.isPresent()) {

				userProfile.setPassword(up.get().getPassword());
				userProfile1.setPassword(up1.get().getPassword());

				if (userProfile.getLogin_status().equals("Active")) {
					userProfile.setUser_locked_flg("N");
				} else {
					userProfile.setUser_locked_flg("Y");
				}
				if (userProfile1.getLogin_status().equals("Active")) {
					userProfile1.setUser_locked_flg("N");
				} else {
					userProfile1.setUser_locked_flg("Y");
				}

				if (userProfile.getUser_status().equals("Active")) {
					userProfile.setDisable_flg("N");
				} else {
					userProfile.setDisable_flg("Y");
				}
				if (userProfile1.getUser_status().equals("Active")) {
					userProfile1.setDisable_flg("N");
				} else {
					userProfile1.setDisable_flg("Y");
				}

				userProfile.setNo_of_attmp(0);
				userProfile.setEntity_flg("Y");
				userProfile.setDel_flg("N");
				userProfile.setLogin_flg("N");
				userProfile.setAuth_user(userid);
				userProfile.setAuth_time(new Date());
				userProfile1.setNo_of_attmp(0);
				userProfile1.setEntity_flg("Y");
				userProfile1.setDel_flg("N");
				userProfile1.setLogin_flg("N");
				userProfile1.setAuth_user(userid);
				userProfile1.setAuth_time(new Date());
				userProfile1.setModify_flg("N");

				userProfileRep.save(userProfile);
				ipsUserProfileModRep.save(userProfile1);
			}

			msg = "User Verified Successfully3543543";
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			msg = "Error Occured. Please contact Administrator";
		}

		return msg;
	}

	public String passwordReset(UserProfile userprofile, String userid) {

		String msg = "";

		try {
			String encryptedPassword = PasswordEncryption.getEncryptedPassword(this.password);

			Optional<UserProfile> up = userProfileRep.findById(userprofile.getUserid());

			if (up.isPresent()) {
				UserProfile user = up.get();
				user.setPassword(encryptedPassword);
				user.setNo_of_attmp(0);
				user.setLogin_flg("N");
				user.setUser_locked_flg("N");
				user.setNew_user_flg("Y");
				userProfileRep.save(user);
			}

			msg = "Password Resetted Successfully";

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			e.printStackTrace();

			msg = "Error Occured. Please contact Administrator";
		}

		return msg;
	}

	/*
	 * Getting LoginFlg -
	 * 
	 * If loginFlg = 'N' - User should be prompted to change password. else thats
	 * not required.
	 * 
	 * Loginflg ='N' will be updated at the time of new user creation and at the
	 * time of password reset by admin.
	 * 
	 */
	public String checkPasswordChangeReq(String userid) {

		Optional<UserProfile> up = userProfileRep.findById(userid);
		String loginflg = up.get().getNew_user_flg();

		return loginflg;
	}

	public int checkAcctexpirty(String userid) {

		Optional<UserProfile> up = userProfileRep.findById(userid);
		Date expDate = up.get().getAcc_exp_date();
		Date currDate = new Date();

		DateTime dt1 = new DateTime(currDate);
		DateTime dt2 = new DateTime(expDate);

		int remaindays = Days.daysBetween(dt1, dt2).getDays();

		return remaindays;
	}

	public int checkpassexpirty(String userid) {

		Optional<UserProfile> up = userProfileRep.findById(userid);
		Date expDate = up.get().getPass_exp_date();
		Date currDate = new Date();

		DateTime dt1 = new DateTime(currDate);
		DateTime dt2 = new DateTime(expDate);

		int remaindays = Days.daysBetween(dt1, dt2).getDays();

		return remaindays;
	}

	public String changePassword(UserProfile userProfile, String oldpass, String newpass, String userid) {
		String msg = "";

		Optional<UserProfile> up = userProfileRep.findById(userid);
		List<LoginSecurity> dataList = loginSecurityRep.findAll();


		try {
			if(oldpass.equals(newpass)){
				

				msg = "New password cannot be Same as Old password";
			}
			else if (up.isPresent()) {
				UserProfile user = up.get();
				
				if(dataList.get(0).getPre_pw_chk_flg().equals("Y")) {
					if (PasswordEncryption.validatePassword(oldpass, user.getPassword())) {

						if (!PasswordEncryption.validatePassword(newpass, user.getPassword())) {

							
							String encryptedPassword = PasswordEncryption.getEncryptedPassword(newpass);
							user.setPassword(encryptedPassword);
							user.setLogin_flg("Y");
							user.setNew_user_flg("N");
							
							if(dataList.size()>0) {
								final Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MONTH,dataList.get(0).getPw_life().intValue());
								user.setPass_exp_date(cal.getTime());
							}else {
								final Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MONTH,1);
								user.setPass_exp_date(cal.getTime());
							}

							LocalDateTime localDateTime = user.getPass_exp_date().toInstant().atZone(ZoneId.systemDefault())
									.toLocalDateTime();
							user.setPass_exp_date(
									Date.from(localDateTime.plusDays(365).atZone(ZoneId.systemDefault()).toInstant()));

							userProfileRep.save(user);
							msg = "Password Changed Successfully";

						} else {

							msg = "New password cannot be Same as Old password";
						}

					} else {
						msg = "Incorrect Old Password!";
					}
				}else {
					
					if (PasswordEncryption.validatePassword(oldpass, user.getPassword())) {

							
							String encryptedPassword = PasswordEncryption.getEncryptedPassword(newpass);
							user.setPassword(encryptedPassword);
							user.setLogin_flg("Y");
							user.setNew_user_flg("N");
							
							if(dataList.size()>0) {
								final Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MONTH,dataList.get(0).getPw_life().intValue());
								user.setPass_exp_date(cal.getTime());
							}else {
								final Calendar cal = Calendar.getInstance();
								cal.add(Calendar.MONTH,1);
								user.setPass_exp_date(cal.getTime());
							}

							LocalDateTime localDateTime = user.getPass_exp_date().toInstant().atZone(ZoneId.systemDefault())
									.toLocalDateTime();
							user.setPass_exp_date(
									Date.from(localDateTime.plusDays(365).atZone(ZoneId.systemDefault()).toInstant()));

							userProfileRep.save(user);
							msg = "Password Changed Successfully";

						

					} else {
						msg = "Incorrect Old Password!";
					}

				}

			}
		} catch (Exception e) {

			msg = "Error Occured. Please contact Administrator";
		}

		return msg;
	};

	public void SessionLogging(String menuname, String menuid, String sessionid, String userid, String ip,
			String status) {
		Session hs = sessionFactory.getCurrentSession();

		try {

			if (menuname.equals("LOGOUT")) {

				hs.createQuery("update XBRLSession set status='IN-ACTIVE' where session_id = ?1")
						.setParameter(1, sessionid).executeUpdate();

			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public File getUserLogFile(Date fromdate, Date todate) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		String path = exportpath;
		String fileName = "USER_LOGS_" + dateFormat.format(new Date()) + ".xlsx";
		File outputFile;

		File jasperFile;

		File folders = new File(path);
		if (!folders.exists()) {
			folders.mkdirs();
		}

		try {
			jasperFile = ResourceUtils.getFile("classpath:static/jasper/USER_LOGS/UserLogs.jasper");
			JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("FromDate", dateFormat.format(fromdate));
			map.put("ToDate", dateFormat.format(todate));

			path = path + "/" + fileName;
			JasperPrint jp = JasperFillManager.fillReport(jr, map, srcdataSource.getConnection());
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jp));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
			exporter.exportReport();

		} catch (FileNotFoundException | JRException | SQLException e) {

			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}

	public IPSUserPreofileMod getUser2(String id) {

		if (ipsUserProfileModRep.existsById(id)) {
			IPSUserPreofileMod up = ipsUserProfileModRep.findById(id).get();

			return up;
		} else {

		}
		return new IPSUserPreofileMod();

	};

	public String deleteUser1(IPSUserPreofileMod userProfilemoden, String inputUser) {

		String msg = "";

		/*
		 * System.out.println("Kalai"+userProfilemoden.getUserid()); Session hs =
		 * sessionFactory.getCurrentSession(); NativeQuery q1 = hs.
		 * createNativeQuery("delete from BIPS_USER_PROFILE_MOD_TABLE where user_id=?1 "
		 * ) .setParameter(1, userProfilemoden.getUserid());
		 * System.out.println(inputUser);
		 */

		ipsUserProfileModRep.deleteById(userProfilemoden.getUserid());
		return inputUser;
	}

	public String deleteUser(UserProfile userprofile, String userid) {

		String msg = "";
		String audit_ref_no=sequence.generateRequestUUId();

		try {

			Optional<UserProfile> up = userProfileRep.findById(userprofile.getUserid());

			if (up.isPresent()) {

				userprofile.setPassword(up.get().getPassword());

				if (userprofile.getLogin_status().equals("Active")) {
					userprofile.setUser_locked_flg("N");
				} else {
					userprofile.setUser_locked_flg("Y");
				}

				if (userprofile.getUser_status().equals("Active")) {
					userprofile.setDisable_flg("N");
				} else {
					userprofile.setDisable_flg("Y");
				}

				userprofile.setNo_of_attmp(0);
				userprofile.setEntity_flg("N");
				userprofile.setModify_user(userid);
				userprofile.setModify_time(new Date());

				Session session = sessionFactory.getCurrentSession();
				session.saveOrUpdate(userprofile);
			}
			msg = "User Deleted Successfully";

		} catch (Exception e) {
			msg = "Error Occured. Please contact Administrator";
			e.printStackTrace();
		}

		return msg;
	}

	public List<Object> getUsersList() throws SQLException {

		Session hs = sessionFactory.getCurrentSession();

		Query<Object[]> q1;

		Query<Object[]> q2;

		List<Object> userProfileList = new ArrayList<Object>();

		q1 = hs.createNativeQuery("select * from BIPS_USER_PROFILE where entity_flg ='Y' and del_flg ='N' ");

		q2 = hs.createNativeQuery("select * from BIPS_USER_PROFILE_MOD_TABLE WHERE entity_flg ='N'  ");

		List<Object[]> result1 = q1.getResultList();
		List<Object[]> result2 = q2.getResultList();

		for (Object[] a : result1) {

			String bank_code = (String) a[0];
			String bank_name = (String) a[1];
			String branch_code = (String) a[2];
			String branch_name = (String) a[3];
			String emp_id = (String) a[4];
			String emp_name = (String) a[5];
			String user_id = (String) a[6];
			String user_name = (String) a[7];
			String inactive_time = (String) a[8];
			Date acc_exp_date = (Date) a[9];
			String login_low = (String) a[10];
			String login_high = (String) a[11];
			Date disable_start_date = (Date) a[12];
			Date disable_end_date = (Date) a[13];
			String password = (String) a[14];
			Date pass_exp_date = (Date) a[15];
			String user_status = (String) a[16];
			String login_status = (String) a[17];

			Character virtual_flg = (Character) a[18];
			String virtualFlg = String.valueOf(virtual_flg);

			String work_class = (String) a[19];
			BigDecimal mob_number = (BigDecimal) a[20];
			String mobNum = mob_number.toString();

			String email_id = (String) a[21];
			String role_id = (String) a[22];
			String role_desc = (String) a[23];
			String permissions = (String) a[24];
			String per_effctive_date = (String) a[25];
			String admin = (String) a[26];
			String xbrl_configuration = (String) a[27];
			String xbrl_report = (String) a[28];
			String scheduler = (String) a[29];
			String execution = (String) a[30];
			String mis_reports = (String) a[31];
			String xml_reports = (String) a[32];
			String archivel = (String) a[33];
			String general_inq = (String) a[34];
			String audit_inq = (String) a[35];
			String channel = (String) a[36];
			String entry_user = (String) a[37];
			Date entry_time = (Date) a[38];
			String auth_user = (String) a[39];
			Date auth_time = (Date) a[40];
			String modify_user = (String) a[41];
			Date modify_time = (Date) a[42];
			Character entity_flg = (Character) a[43];
			String entityFlag = String.valueOf(entity_flg);

			String auth_flg = (String) a[44];
			Character modify_flg = (Character) a[45];
			String modifyFlg = String.valueOf(modify_flg);

			Character del_flg = (Character) a[46];
			String delFlg = String.valueOf(del_flg);

			String session_id = (String) a[47];

			Character login_flg = (Character) a[48];
			String loginFlag = String.valueOf(login_flg);

			Character user_locked_flg = (Character) a[49];
			String userLockedFlag = String.valueOf(user_locked_flg);

			BigDecimal no_of_attmp = (BigDecimal) a[50];
			Integer noOfAttmp = no_of_attmp.intValue();

			Character disable_flg = (Character) a[51];
			String disableFlag = String.valueOf(disable_flg);
			
			System.out.println("ValueData"+a[52]);
			Blob photo1 = (Blob) a[52];
			byte [] photo=null;
			if(photo1!=null) {
				photo = photo1.getBytes(1l, (int)photo1.length());
				xbrl_report=Base64.getEncoder().encodeToString(photo);

			}
			 
			
			String domain_id = (String) a[53];

			Character new_user_flg = (Character) a[54];
			String newUserFlag = String.valueOf(new_user_flg);

			String remarks = (String) a[55];
			String country_code = (String) a[56];

			UserProfile userProfile = new UserProfile(bank_code, bank_name, branch_code, branch_name, emp_id, emp_name,
					user_id, user_name, inactive_time, acc_exp_date, login_low, login_high, disable_start_date,
					disable_end_date, password, pass_exp_date, user_status, login_status, virtualFlg, work_class,
					mobNum, email_id, role_id, role_desc, permissions, per_effctive_date, admin, xbrl_configuration,
					xbrl_report, scheduler, execution, mis_reports, xml_reports, archivel, general_inq, audit_inq,
					channel, entry_user, entry_time, auth_user, auth_time, modify_user, modify_time, entityFlag,
					auth_flg, modifyFlg, delFlg, session_id, loginFlag, userLockedFlag, noOfAttmp, disableFlag,
					photo, domain_id, newUserFlag, remarks, country_code);

			userProfileList.add(userProfile);

		}

		for (Object[] a : result2) {
			String bank_code = (String) a[0];
			String bank_name = (String) a[1];
			String branch_code = (String) a[2];
			String branch_name = (String) a[3];
			String emp_id = (String) a[4];
			String emp_name = (String) a[5];
			String user_id = (String) a[6];
			String user_name = (String) a[7];
			String inactive_time = (String) a[8];
			Date acc_exp_date = (Date) a[9];
			String login_low = (String) a[10];
			String login_high = (String) a[11];
			Date disable_start_date = (Date) a[12];
			Date disable_end_date = (Date) a[13];
			String password = (String) a[14];
			Date pass_exp_date = (Date) a[15];
			String user_status = (String) a[16];
			String login_status = (String) a[17];

			Character virtual_flg = (Character) a[18];
			String virtualFlg = String.valueOf(virtual_flg);

			String work_class = (String) a[19];
			BigDecimal mob_number = (BigDecimal) a[20];
			System.out.println("here234"+ mob_number);
			String mobNum = mob_number.toString();

			String email_id = (String) a[21];
			String role_id = (String) a[22];
			String role_desc = (String) a[23];
			String permissions = (String) a[24];
			String per_effctive_date = (String) a[25];
			String admin = (String) a[26];
			String xbrl_configuration = (String) a[27];
			String xbrl_report = (String) a[28];
			String scheduler = (String) a[29];
			String execution = (String) a[30];
			String mis_reports = (String) a[31];
			String xml_reports = (String) a[32];
			String archivel = (String) a[33];
			String general_inq = (String) a[34];
			String audit_inq = (String) a[35];
			String channel = (String) a[36];
			String entry_user = (String) a[37];
			Date entry_time = (Date) a[38];
			String auth_user = (String) a[39];
			Date auth_time = (Date) a[40];
			String modify_user = (String) a[41];
			Date modify_time = (Date) a[42];
			Character entity_flg = (Character) a[43];

			String entityFlag = String.valueOf(entity_flg);

			String auth_flg = (String) a[44];
			Character modify_flg = (Character) a[45];
			String modifyFlg = String.valueOf(modify_flg);

			Character del_flg = (Character) a[46];
			String delFlg = String.valueOf(del_flg);
			String session_id = (String) a[47];
			Character login_flg = (Character) a[48];
			String loginFlag = String.valueOf(login_flg);

			Character user_locked_flg = (Character) a[49];
			String userLockedFlag = String.valueOf(user_locked_flg);

			BigDecimal no_of_attmp = (BigDecimal) a[50];
			Integer noOfAttmp = no_of_attmp.intValue();

			Character disable_flg = (Character) a[51];
			String disableFlag = String.valueOf(disable_flg);
			
			Blob photo1 = (Blob) a[52];
			byte [] photo=null;
			if(photo1!=null) {
				photo = photo1.getBytes(1l, (int)photo1.length());
				xbrl_report=Base64.getEncoder().encodeToString(photo);
			}
			//String photo1 = String.valueOf(photo);

			String domain_id = (String) a[53];

			Character new_user_flg = (Character) a[54];
			String newUserFlag = String.valueOf(new_user_flg);

			String remarks = (String) a[55];
			String country_code = (String) a[56];
			IPSUserPreofileMod userProfileModEn = new IPSUserPreofileMod(bank_code, bank_name, branch_code, branch_name,
					emp_id, emp_name, user_id, user_name, inactive_time, acc_exp_date, login_low, login_high,
					disable_start_date, disable_end_date, password, pass_exp_date, user_status, login_status,
					virtualFlg, work_class, mobNum, email_id, role_id, role_desc, permissions, per_effctive_date, admin,
					xbrl_configuration, xbrl_report, scheduler, execution, mis_reports, xml_reports, archivel,
					general_inq, audit_inq, channel, entry_user, entry_time, auth_user, auth_time, modify_user,
					modify_time, entityFlag, auth_flg, modifyFlg, delFlg, session_id, loginFlag, userLockedFlag,
					noOfAttmp, disableFlag, photo, newUserFlag, domain_id, remarks, country_code);

			userProfileList.add(userProfileModEn);

		}

		Iterable<UserProfile> users = userProfileRep.findAll();

		return userProfileList;

	}

	public String addUserentity(UserProfile userProfile, IPSUserPreofileMod USERMOD, String formmode, String inputUser,
			String user1) throws ParseException {
		String msg = "";
		String count = userProfileRep.getusercount(user1);
		Optional<UserProfile> up = userProfileRep.findById(userProfile.getUserid());

		if (count.equals("1")) {
			UserProfile up1 = up.get();
			DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
			String acctexpold = dateFormat.format(up1.getAcc_exp_date());
			String acctexpnew = dateFormat.format(USERMOD.getAcc_exp_date());
			String disableSold = dateFormat.format(up1.getDisable_start_date());
			String disableSnew = dateFormat.format(USERMOD.getDisable_start_date());
			String disableEold = dateFormat.format(up1.getDisable_end_date());
			String disableEnew = dateFormat.format(USERMOD.getDisable_end_date());
			String passOLD = dateFormat.format(up1.getPass_exp_date());
			String passNEW = dateFormat.format(USERMOD.getPass_exp_date());

			if ((up1.getEmail_id().equals(USERMOD.getEmail_id()))
					&& (up1.getMob_number().equals(USERMOD.getMob_number()))
					&& (up1.getLogin_low().equals(USERMOD.getLogin_low()))
					&& (up1.getLogin_high().equals(USERMOD.getLogin_high())) && (acctexpold.equals(acctexpnew))
					&& (disableSold.equals(disableSnew)) && (disableEold.equals(disableEnew))
					&& (passOLD.equals(passNEW)) && (up1.getUser_status().equals(USERMOD.getUser_status()))
					&& (up1.getLogin_status().equals(USERMOD.getLogin_status()))
					&& (up1.getRemarks().equals(USERMOD.getRemarks()))
					&& (up1.getRole_id().equals(USERMOD.getRole_id()))) {

			} else {
				if (up.isPresent()) {
					userProfile.setPassword(up.get().getPassword());

					if (userProfile.getLogin_status().equals("Active")) {
						userProfile.setUser_locked_flg("N");
					} else {
						userProfile.setUser_locked_flg("Y");
					}

					if (userProfile.getUser_status().equals("Active")) {
						userProfile.setDisable_flg("N");
					} else {
						userProfile.setDisable_flg("Y");
					}

					UserProfile user = up.get();
					System.out.println("+++++++++++++++++++++++" + user);

					user.setNo_of_attmp(0);
					user.setEntity_flg("N");
					user.setModify_user(inputUser);
					user.setModify_time(new Date());
					user.setLogin_flg("Y");

					Session session = sessionFactory.getCurrentSession();
					session.saveOrUpdate(user);

					String modi = "";

				}
			}
		}
		return msg;
	}

	public String cancelUserentity(UserProfile userProfile, String userID,String inputUser) {
		String msg = "";
		Optional<UserProfile> up = userProfileRep.findById(userProfile.getUserid());
		if (up.isPresent()) {
			String audit_ref_no=sequence.generateRequestUUId();
			
			userProfile.setPassword(up.get().getPassword());

			/*
			 * if (userProfile.getLogin_status().equals("Active")) {
			 * userProfile.setUser_locked_flg("N"); } else {
			 * userProfile.setUser_locked_flg("Y"); }
			 * 
			 * if (userProfile.getUser_status().equals("Active")) {
			 * userProfile.setDisable_flg("N"); } else { userProfile.setDisable_flg("Y"); }
			 */

			UserProfile user = up.get();
			System.out.println("+++++++++++++++++++++++" + user);

			user.setNo_of_attmp(0);
			user.setEntity_flg("Y");
			user.setModify_user(inputUser);
			user.setModify_time(new Date());
			user.setLogin_flg("Y");

			userProfileRep.save(user);
			
			IPSAuditTable audit1 = ipsAuditTableRep.getModifyList(userProfile.getUserid(),"USER MODIFICATION");
			
			IPSAuditTable auditTable=new IPSAuditTable();
			auditTable.setAudit_date(new Date());
			auditTable.setAudit_table("BIPS_USER_PROFILE");
			auditTable.setAudit_screen("USER PROFILE - Cancel");
			auditTable.setFunc_code("USER MODIFICATION CANCEL");
			auditTable.setRemarks(userProfile.getUserid()+" : Cancelled Successfully");
			auditTable.setEvent_id(userProfile.getUserid());
			auditTable.setEvent_name(userProfile.getUsername());
			auditTable.setModi_details(audit1.getModi_details());
			auditTable.setAuth_user(inputUser);
			auditTable.setAuth_time(new Date());
			auditTable.setEntry_time(new Date());
			auditTable.setEntry_user(inputUser);
			auditTable.setAudit_ref_no(audit_ref_no);
			ipsAuditTableRep.save(auditTable);
		}
		return msg;
	}

	public String cancel(IPSUserPreofileMod userProfilemoden, UserProfile userprofile, String inputUser) {
		
		
		System.out.println("user"+userprofile.getUserid());
			String msg = "";
			//Session hs = sessionFactory.getCurrentSession();
			//hs.createNativeQuery("delete from BIPS_USER_PROFILE_MOD_TABLE where user_id=?1").setParameter(1,userprofile.getUserid());
			
			ipsUserProfileModRep.deleteById(userprofile.getUserid());
			msg = "Last changes are removed ";
			return msg;
		}

	public String passwordReset1(UserProfile userProfile, String newpass, String loginuser, String changeuserID) {
		String msg = "";

		try {
			String audit_ref_no=sequence.generateRequestUUId();
			String encryptedPassword = PasswordEncryption.getEncryptedPassword(newpass);

			Optional<UserProfile> up = userProfileRep.findById(changeuserID);
			List<LoginSecurity> dataList = loginSecurityRep.findAll();

			if (up.isPresent()) {
				UserProfile user = up.get();
				user.setPassword(encryptedPassword);
				user.setModify_user(loginuser);
				user.setModify_time(new Date());
				user.setNo_of_attmp(0);
				user.setLogin_flg("N");
				user.setUser_locked_flg("N");
				user.setNew_user_flg("Y");
				
				if(dataList.size()>0) {
					final Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH,dataList.get(0).getPw_life().intValue());
					user.setPass_exp_date(cal.getTime());
				}else {
					final Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH,1);
					user.setPass_exp_date(cal.getTime());
				}
				
				userProfileRep.save(user);
				
				
				IPSAuditTable auditTable=new IPSAuditTable();
				auditTable.setAudit_date(new Date());
				auditTable.setAudit_table("BIPS_USER_PROFILE");
				auditTable.setAudit_screen("USER PROFILE -Reset Password");
				auditTable.setFunc_code("USER MODIFICATION RESET PASSWORD");
				auditTable.setRemarks(changeuserID+" : Password Resetted Successfully");
				//auditTable.setEvent_id(userProfile.getUserid());
				//auditTable.setEvent_name(userProfile.getUsername());
				auditTable.setModi_details("-");
				auditTable.setAuth_user(loginuser);
				auditTable.setAuth_time(new Date());
				auditTable.setEntry_time(new Date());
				auditTable.setEntry_user(loginuser);
				auditTable.setAudit_ref_no(audit_ref_no);
				ipsAuditTableRep.save(auditTable);
				
			
			
			}

			msg = "Password Resetted Successfully";

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			e.printStackTrace();

			msg = "Error Occured. Please contact Administrator";
		}

		return msg;
	}

	

	public  String uploadAuditsubmitMttomx( String userId, String username) {
		
		String msg = "Audit submit successfull";
		String audit_ref_no=sequence.generateRequestUUId();

		IPSAuditTable audit = new IPSAuditTable();
		audit.setAudit_ref_no(audit_ref_no);
		audit.setAudit_date(new Date());
		audit.setEntry_time(new Date());
		audit.setEntry_user(userId);
		audit.setFunc_code("MT to MX");
		audit.setRemarks("Mt to Mx Conversion");
		audit.setAudit_table("BIPS_SWIFT_FILE_MGT_TABLE");
		audit.setAudit_screen("MESSAGE_CONVERSION");
		audit.setEvent_id(userId);
		audit.setEvent_name(username);
		audit.setModi_details("-");
		//IPSAuditTableRep 
		
		ipsAuditTableRep.save(audit);
		return msg;
	}
	
	public  String uploadAuditsubmitMxtomt( String userId, String username) {
		
		String msg = "Audit submit successfull";
		String audit_ref_no=sequence.generateRequestUUId();

		IPSAuditTable audit = new IPSAuditTable();
		audit.setAudit_ref_no(audit_ref_no);
		audit.setAudit_date(new Date());
		audit.setEntry_time(new Date());
		audit.setEntry_user(userId);
		audit.setFunc_code("Mx to Mt");
		audit.setRemarks("Mx to Mt Conversion");
		audit.setAudit_table("BIPS_SWIFT_FILE_MGT_TABLE");
		audit.setAudit_screen("MESSAGE_CONVERSION");
		audit.setEvent_id(userId);
		audit.setEvent_name(username);
		audit.setModi_details("-");
		//IPSAuditTableRep 
		
		ipsAuditTableRep.save(audit);
		return msg;
	}
	

}
