package com.bornfire.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.print.attribute.standard.MediaSize.Other;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAgentTableRep;
import com.bornfire.entity.BulkTransaction;
import com.bornfire.entity.BulkTransactionPojo;
import com.bornfire.entity.BulkTransactionRepository;
import com.bornfire.entity.CustomException;
import com.bornfire.entity.MCCreditTransferResponse;
import com.bornfire.entity.ManualTransaction;
import com.bornfire.entity.ManualTransactionPojo;
import com.bornfire.entity.ManualTransactionRepository;
import com.bornfire.entity.RTPTransactionTable;
import com.bornfire.entity.RTPTransactionTablePojo;
import com.bornfire.entity.RTPTransactionTableRep;
import com.monitorjbl.xlsx.StreamingReader;

import au.com.bytecode.opencsv.CSVReader;

@Service
@ConfigurationProperties("output")

public class BulkServices {
	private static final Logger logger = LoggerFactory.getLogger(BulkServices.class);

	@Autowired
	BulkTransactionRepository bulkTransactionRepository;

	@Autowired
	ManualTransactionRepository manualTransactionRepository;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	SequenceGenerator sequence;

	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	RTPTransactionTableRep rtpTransactionTableRep;
	
	@Autowired
	Environment env;

	public MCCreditTransferResponse getDetailsCredit(BulkTransaction bulkTransaction, String formmode, String userID) {

		MCCreditTransferResponse res = new MCCreditTransferResponse();
		if (formmode.equals("submit")) {

			String[] list = bulkTransaction.getDoc_ref_id().split(",");

			String remName = bulkTransaction.getRem_name().split(",")[0];
			boolean isRemName = Arrays.asList(bulkTransaction.getRem_name().split(",")).stream()
					.allMatch(x -> x.equals(remName));

			String remAcctNum = bulkTransaction.getRemit_account_no().split(",")[0];
			boolean isRemAcctNum = Arrays.asList(bulkTransaction.getRemit_account_no().split(",")).stream()
					.allMatch(x -> x.equals(remAcctNum));

			
			if (isRemName && isRemAcctNum) {
				for (int i = 0; i < list.length; i++) {

					if (bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
							&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
							&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
							&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
							&& bulkTransaction.getTran_particular().split(",")[i] != "") {

						BulkTransaction bulkTran = new BulkTransaction();
						bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
						bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);
						bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

						if (bulkTransaction.getRemit_account_no().split(",")[i].length() >=3
								&&bulkTransaction.getRemit_account_no().split(",")[i].length() <=35
								&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
						} else {
							res.setStatus("You have entered an Invalid Remitter Account Number");
							res.setTranID("0");
							return res;

						}
						bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);
						if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
								&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
								&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
						} else {
							res.setStatus("You have entered an Invalid Beneficiary Account Number");
							res.setTranID("0");
							return res;

						}

						if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res.setStatus("Invalid Bank Code");
							res.setTranID("0");
							return res;
						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						

						if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
							bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
							
						} else {
							res.setStatus("Invalid Currency Code");
							res.setTranID("0");
							return res;
						}

						bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);
						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						if (bulkTransaction.getMaster_ref_no().split(",")[i]
								.matches("((\\d{1,7})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
							bulkTran.setTran_amt(new BigDecimal(trAmount));
						} else {
							res.setStatus("You have entered an Invalid Amount");
							res.setTranID("0");
							return res;

						}
						bulkTran.setBulk_type("BULK_CREDIT");
						bulkTran.setTran_date(new Date());
						bulkTran.setDel_flg("N");
						bulkTran.setEntity_flg("N");
						bulkTran.setSplit_flg("N");
						bulkTran.setEntry_user(userID);
						bulkTran.setEntry_time(new Date());

						bulkTransactionRepository.save(bulkTran);
					}

					else {

						res.setStatus("Info Missing.. Please Checkout All the Fields");
						res.setTranID("0");
						return res;
					}

				}
				res.setStatus("BulkTransaction Submitted Successfully");
				res.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			} else {
				//res.setStatus("Info Missing.. Please Checkout All the Fields");
				res.setStatus("Please check remitter details must be Same");
				res.setTranID("0");
				return res;
			}

		}

		else if (formmode.equals("addsubmit")) {

			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");

			String remName = bulkTransaction.getRem_name().split(",")[0];
			boolean isRemName = Arrays.asList(bulkTransaction.getRem_name().split(",")).stream()
					.allMatch(x -> x.equals(remName));

			String remAcctNum = bulkTransaction.getRemit_account_no().split(",")[0];
			boolean isRemAcctNum = Arrays.asList(bulkTransaction.getRemit_account_no().split(",")).stream()
					.allMatch(x -> x.equals(remAcctNum));

			if (isRemName && isRemAcctNum) {
				BulkTransaction bulkTran = new BulkTransaction();
				for (int i = 0; i < list1.length; i++) {

					if (!bulkTransaction.getRem_name().equals(",") && !bulkTransaction.getRemit_account_no().equals(",")
							&& !bulkTransaction.getBen_acct_name().equals(",")
							&& !bulkTransaction.getBen_acct_no().equals(",")
							&& !bulkTransaction.getBen_bank_code().equals(",")
							&& !bulkTransaction.getTran_crncy_code().equals(",")
							&& !bulkTransaction.getMaster_ref_no().equals(",")
							&& !bulkTransaction.getTran_particular().equals(",")) {

						if (bulkTransaction.getRem_name().split(",")[i] != ""
								&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
								&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
								&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
								&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
								&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
								&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
								&& bulkTransaction.getTran_particular().split(",")[i] != "") {

							bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
							bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

							if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
									&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
									&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
								bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
							} else {
								res.setStatus("You have entered an Invalid Remitter Account Number");
								res.setTranID("0");
								return res;

							}
							bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

							if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
									&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
									&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
								bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
							} else {
								res.setStatus("You have entered an Invalid Beneficiary Account Number");
								res.setTranID("0");
								return res;

							}


							if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
								res.setStatus("Invalid Bank Code");
								res.setTranID("0");
								return res;
							} else {
								bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
							}
							

							if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
								bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
								
							} else {
								res.setStatus("Invalid Currency Code");
								res.setTranID("0");
								return res;
							}
							

							bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

							bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

							bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);
							if (bulkTransaction.getMaster_ref_no().split(",")[i]
									.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
								String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
								bulkTran.setTran_amt(new BigDecimal(trAmount));
							} else {
								res.setStatus("You have entered an Invalid Amount");
								res.setTranID("0");
								return res;

							}

							bulkTran.setBulk_type("BULK_CREDIT");
							bulkTran.setTran_date(new Date());

							bulkTran.setDel_flg("N");
							bulkTran.setEntity_flg("N");
							bulkTran.setSplit_flg("N");
							bulkTran.setEntry_user(userID);
							bulkTran.setEntry_time(new Date());
							bulkTransactionRepository.save(bulkTran);

						} else {

							res.setStatus("Info Missing.. Please Checkout All the Fields");
							res.setTranID("0");
							return res;
						}
					} else {

						res.setStatus("Info Missing.. Please Checkout All the Fields");
						res.setTranID("0");
						return res;
					}

				}
				res.setStatus("BulkTransaction Submitted Successfully");
				res.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			} else {
				//res.setStatus("Info Missing.. Please Checkout All the Fields");
				res.setStatus("Please check remitter details must be Same");
				res.setTranID("0");
				return res;
			}

		}
		return res;
	}

	private boolean invalidBankCode(String bankCode) {
		boolean valid = true;
		try {
			List<BankAgentTable> otm = bankAgentTableRep.findByIdCustomBankCode1(bankCode);

			if (otm.size()>0) {
				valid = false;
			} else {
				valid = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return valid;

	}
	
	private boolean invalidBankCodeExceptBank(String bankCode) {
		boolean valid = true;
		try {
			List<BankAgentTable> otm = bankAgentTableRep.findByIdCustomBankCode1(bankCode);

			
			if (otm.size()>0) {
				if(otm.get(0).getBank_agent().equals(env.getProperty("ipsx.dbtragt"))) {
					valid = true;
				}else {
					valid = false;
				}
				
			} else {
				valid = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			valid = true;
		}

		return valid;

	}

	public MCCreditTransferResponse getDetailsDebit(BulkTransaction bulkTransaction, String formmode, String userID) {
		MCCreditTransferResponse res1 = new MCCreditTransferResponse();

		if (formmode.equals("submit")) {

			String[] list = bulkTransaction.getDoc_ref_id().split(",");

			String benName = bulkTransaction.getBen_acct_name().split(",")[0];
			boolean isBenName = Arrays.asList(bulkTransaction.getBen_acct_name().split(",")).stream()
					.allMatch(x -> x.equals(benName));

			String benAcctNum = bulkTransaction.getBen_acct_no().split(",")[0];
			boolean isBenAcctNum = Arrays.asList(bulkTransaction.getBen_acct_no().split(",")).stream()
					.allMatch(x -> x.equals(benAcctNum));

			String benBank = bulkTransaction.getBen_bank_code().split(",")[0];
			boolean isBenBank = Arrays.asList(bulkTransaction.getBen_bank_code().split(",")).stream()
					.allMatch(x -> x.equals(benBank));

			if (isBenName && isBenAcctNum && isBenBank) {
				for (int i = 0; i < list.length; i++) {

					if (!bulkTransaction.getRem_name().split(",")[i].equals("")
							&& !bulkTransaction.getRemit_account_no().split(",")[i].equals("")
							&& !bulkTransaction.getBen_acct_name().split(",")[i].equals("")
							&& !bulkTransaction.getBen_acct_no().split(",")[i].equals("")
							&& !bulkTransaction.getBen_bank_code().split(",")[i].equals("")
							&& !bulkTransaction.getTran_crncy_code().split(",")[i].equals("")
							&& !bulkTransaction.getMaster_ref_no().split(",")[i].equals("")
							&& !bulkTransaction.getTran_particular().split(",")[i].equals("")) {

						BulkTransaction bulkTran = new BulkTransaction();
						bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
						bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

						if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
								&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
								&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Remitter Account Number");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

						if (bulkTransaction.getBen_acct_no().split(",")[i].length() >=3
								&&bulkTransaction.getBen_acct_no().split(",")[i].length() <=35
								&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Beneficiary Account Number");
							res1.setTranID("0");
							return res1;

						}

						
						if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						

						if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
							bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
							
						} else {
							res1.setStatus("Invalid Currency Code");
							res1.setTranID("0");
							return res1;
						}
						
						/*if (invalidBankCode(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Bank Code");
							res1.setTranID("0");
							return res1;

						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
						*/
						bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);
						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);
						bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);
						if (bulkTransaction.getMaster_ref_no().split(",")[i]
								.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
							bulkTran.setTran_amt(new BigDecimal(trAmount));
						} else {
							res1.setStatus("You have entered an Invalid Amount");
							res1.setTranID("0");
							return res1;

						}
						bulkTran.setDel_flg("N");
						bulkTran.setEntity_flg("N");
						bulkTran.setSplit_flg("N");
						bulkTran.setBulk_type("BULK_DEBIT");
						bulkTran.setTran_date(new Date());
						bulkTran.setEntry_user(userID);
						bulkTran.setEntry_time(new Date());

						bulkTransactionRepository.save(bulkTran);
					} else {

						res1.setStatus("Info Missing.. Please Checkout All the Fields");
						res1.setTranID("0");
						return res1;
					}
				}
				res1.setStatus("BulkTransaction Submitted Successfully");
				res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			} else {
				res1.setStatus("Please Check Beneficiary details must be same");
				res1.setTranID("0");
				return res1;
			}

		} else if (formmode.equals("addsubmit")) {

			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");

			String benName = bulkTransaction.getBen_acct_name().split(",")[0];
			boolean isBenName = Arrays.asList(bulkTransaction.getBen_acct_name().split(",")).stream()
					.allMatch(x -> x.equals(benName));

			String benAcctNum = bulkTransaction.getBen_acct_no().split(",")[0];
			boolean isBenAcctNum = Arrays.asList(bulkTransaction.getBen_acct_no().split(",")).stream()
					.allMatch(x -> x.equals(benAcctNum));

			String benBank = bulkTransaction.getBen_bank_code().split(",")[0];
			boolean isBenBank = Arrays.asList(bulkTransaction.getBen_bank_code().split(",")).stream()
					.allMatch(x -> x.equals(benBank));

			if (isBenName && isBenAcctNum && isBenBank) {
				for (int i = 0; i < list1.length; i++) {

					if (bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
							&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
							&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
							&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
							&& bulkTransaction.getTran_particular().split(",")[i] != "") {

						BulkTransaction bulkTran = new BulkTransaction();

						bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
						bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

						if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
								&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
								&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Remitter Account Number");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

						if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
								&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
								&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Beneficiary Account Number");
							res1.setTranID("0");
							return res1;

						}

						/*bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);

						bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
*/
						if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						

						if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
							bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
							
						} else {
							res1.setStatus("Invalid Currency Code");
							res1.setTranID("0");
							return res1;
						}

						bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

						if (bulkTransaction.getMaster_ref_no().split(",")[i]
								.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
							bulkTran.setTran_amt(new BigDecimal(trAmount));
						} else {
							res1.setStatus("You have entered an Invalid Amount");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setDel_flg("N");
						bulkTran.setEntity_flg("N");
						bulkTran.setSplit_flg("N");
						bulkTran.setBulk_type("BULK_DEBIT");
						bulkTran.setTran_date(new Date());
						bulkTran.setEntry_user(userID);
						bulkTran.setEntry_time(new Date());

						bulkTransactionRepository.save(bulkTran);
					} else {

						res1.setStatus("Info Missing.. Please Checkout All the Fields");
						res1.setTranID("0");
						return res1;
					}
				}
				res1.setStatus("BulkTransaction Submitted Successfully");
				res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			} else {
				res1.setStatus("Please Check Beneficiary details must be same");
				res1.setTranID("0");
				return res1;
			}

		}
		return res1;
	}

	public MCCreditTransferResponse getDetailsManualDebit(ManualTransaction bulkTransaction, String formmode,
			String userID) {
		MCCreditTransferResponse res1 = new MCCreditTransferResponse();

		
		if (formmode.equals("addsubmit")) {
			System.out.println("add");

			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");

			for (int i = 0; i < list1.length; i++) {

				if (bulkTransaction.getRem_name().split(",")[i] != ""
						&& bulkTransaction.getRem_name().split(",")[i] != ""
						&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
						&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
						&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
						&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
						&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
						&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
						&& bulkTransaction.getTran_particular().split(",")[i] != "") {

					ManualTransaction bulkTran = new ManualTransaction();

					bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
					bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

					if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
							&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
							&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
						bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
					} else {
						res1.setStatus("You have entered an Invalid Remitter Account Number");
						res1.setTranID("0");
						return res1;

					}

					bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

					if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
							&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
							&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
						bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
					} else {
						res1.setStatus("You have entered an Invalid Beneficiary Account Number");
						res1.setTranID("0");
						return res1;

					}

					/*bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);

					bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);*/
					
					if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
						res1.setStatus("Invalid Bank Code");
						res1.setTranID("0");
						return res1;
					} else {
						bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
					}
					

					if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
						bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
						
					} else {
						res1.setStatus("Invalid Currency Code");
						res1.setTranID("0");
						return res1;
					}

					bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

					bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

					bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

					bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);
					if (bulkTransaction.getMaster_ref_no().split(",")[i]
							.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
						String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
						bulkTran.setTran_amt(new BigDecimal(trAmount));
					} else {
						res1.setStatus("You have entered an Invalid Amount");
						res1.setTranID("0");
						return res1;

					}

					bulkTran.setDel_flg("N");
					bulkTran.setEntity_flg("N");
					bulkTran.setSplit_flg("N");
					bulkTran.setTran_date(new Date());
					bulkTran.setEntry_user(userID);
					bulkTran.setEntry_time(new Date());
					manualTransactionRepository.save(bulkTran);
				} else {

					res1.setStatus("Info Missing.. Please Checkout All the Fields");
					res1.setTranID("0");
					return res1;
				}
			}
			res1.setStatus("BulkTransaction Submitted Successfully");
			res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
		}else if(formmode.equals("submit")) {
			System.out.println("add");

			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");

			for (int i = 0; i < list1.length; i++) {

				if (bulkTransaction.getRem_name().split(",")[i] != ""
						&& bulkTransaction.getRem_name().split(",")[i] != ""
						&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
						&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
						&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
						&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
						&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
						&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
						&& bulkTransaction.getTran_particular().split(",")[i] != "") {

					ManualTransaction bulkTran = new ManualTransaction();

					bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
					bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

					if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
							&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
							&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
						bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
					} else {
						res1.setStatus("You have entered an Invalid Remitter Account Number");
						res1.setTranID("0");
						return res1;

					}

					bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

					if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
							&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
							&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
						bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
					} else {
						res1.setStatus("You have entered an Invalid Beneficiary Account Number");
						res1.setTranID("0");
						return res1;

					}

					/*bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);

					if(bulkTransaction.getBen_bank_code().split(",")[i]!=null) {
						List<BankAgentTable> list= bankAgentTableRep.findByIdCustomBankCode(bulkTransaction.getBen_bank_code().split(",")[i]);
						if(list.size()==0) {
							res1.setStatus("Invalid Beneficiary Bank Code");
							res1.setTranID("0");
							return res1;
						}
					}
					
					bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);*/
					
					if (invalidBankCodeExceptBank(bulkTransaction.getBen_bank_code().split(",")[i])) {
						res1.setStatus("Invalid Bank Code");
						res1.setTranID("0");
						return res1;
					} else {
						bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
					}
					

					if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
						bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
						
					} else {
						res1.setStatus("Invalid Currency Code");
						res1.setTranID("0");
						return res1;
					}

					bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

					bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

					bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

					bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);
					if (bulkTransaction.getMaster_ref_no().split(",")[i]
							.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
						String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
						bulkTran.setTran_amt(new BigDecimal(trAmount));
					} else {
						res1.setStatus("You have entered an Invalid Amount");
						res1.setTranID("0");
						return res1;

					}

					bulkTran.setDel_flg("N");
					bulkTran.setEntity_flg("N");
					bulkTran.setSplit_flg("N");
					bulkTran.setTran_date(new Date());
					bulkTran.setEntry_user(userID);
					bulkTran.setEntry_time(new Date());
					manualTransactionRepository.save(bulkTran);
				} else {

					res1.setStatus("Info Missing.. Please Checkout All the Fields");
					res1.setTranID("0");
					return res1;
				}
			}
			res1.setStatus("Manual Transaction Submitted Successfully");
			res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
		}
		return res1;
	}

	public String getManual(ManualTransaction manualTransaction, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		if (formmode.equals("submit")) {

			ManualTransaction up = manualTransaction;

			hs.saveOrUpdate(up);
			up.setDel_flg("N");
			up.setEntity_flg("N");
			up.setSplit_flg("N");

			manualTransactionRepository.save(up);
			msg = "Manual Transaction Successfully";

		}
		return msg;
	}

	public BulkTransaction gettranrefId(String id) {

		if (bulkTransactionRepository.existsById(id)) {
			System.out.println("getsrlno");
			BulkTransaction up = bulkTransactionRepository.findByIdCustom(id);

			return up;
		} else {
			return new BulkTransaction();
		}

	};

	public String deleteParameter(String id) {
		System.out.println("hhhhhh");
		String msg = "";
		Optional<BulkTransaction> user = bulkTransactionRepository.findById(id);
		System.out.println(id);
		BulkTransaction reg = user.get();
		reg.setDel_flg("N");
		/* montParameterRepository.save(reg); */
		msg = "User Deleted Successfully";
		return msg;
	}

	public static File multipartToFile(MultipartFile multipart, String fileName)
			throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
		multipart.transferTo(convFile);
		return convFile;
	}

	@SuppressWarnings("deprecation")
	public List<BulkTransaction> processUploadCredit(String screenId, MultipartFile file, String userid)
			throws SQLException, FileNotFoundException, IOException {

		String fileName = file.getOriginalFilename();

		String fileExt = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileExt = fileName.substring(i + 1);
		}

		List<BulkTransaction> result = new ArrayList<BulkTransaction>();
		if (fileExt.equals("xlsx") || fileExt.equals("xls")) {

			if (fileName.contains("BulkCredit")) {

				try {

					/*Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
							.open(file.getInputStream());*/
					Workbook workbook = WorkbookFactory.create(file.getInputStream());

					List<HashMap<Integer, String>> mapList = new ArrayList<>();
					for (Sheet s : workbook) {
						for (Row r : s) {
							
							if(!isRowEmpty(r)) {
								System.out.println(r.getRowNum());
								if (r.getRowNum() == 0) {
									continue;
								}
								String val = null;

								HashMap<Integer, String> map = new HashMap<>();
								for (int j = 0; j < 9; j++) {
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
							
					}

					/// Generate Document Ref ID
					String Doc_Ref_ID = sequence.generateDocRefID();
					int SrlNo = 1;

					for (HashMap<Integer, String> item : mapList) {
						BulkTransaction bulkTransaction = new BulkTransaction();
						bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
						bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
						bulkTransaction.setTran_ref_id(item.get(0));
						bulkTransaction.setRem_name(item.get(1));
						bulkTransaction.setRemit_account_no(item.get(2));
						bulkTransaction.setBen_acct_name(item.get(3));
						bulkTransaction.setBen_acct_no(item.get(4));
						bulkTransaction.setBen_bank_code(item.get(5));
						bulkTransaction.setTran_crncy_code(item.get(6));
						bulkTransaction.setMaster_ref_no(item.get(7));
						bulkTransaction.setTran_particular(item.get(8));

						SrlNo++;
						result.add(bulkTransaction);
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
					throw new CustomException("File has not been successfully uploaded");
				}
			} else {
				throw new CustomException("Invalid File Name");
			}
		} else if (fileExt.equals("txt")) {
			try {
				// File file = new File(fileName);
				// FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

				String line;

				int count = 0;
				String receiving_bank = null;
				String send_bank;
				String currency = null;
				String type;
				String benParkingAcctNum = null;

				/// Generate Document Ref ID
				String Doc_Ref_ID = sequence.generateDocRefID();
				int SrlNo = 1;

				while ((line = br.readLine()) != null) {

					if (count == 0) {
						System.out.println("Header : " + line);
						System.out.println("Header Length: " + line.length());
						if (line.length() == 38) {

							receiving_bank = line.substring(1, 3).trim();
							send_bank = line.substring(3, 5).trim();
							String file_seq = line.substring(13, 16).trim();
							currency = line.substring(16, 19).trim();
							String tot_rec = line.substring(19, 24).trim();
							String tot_amount = line.substring(24, 37).trim();
							type = line.substring(37, 38).trim();

							System.out.println("receiving_bank : " + receiving_bank);
							System.out.println("send_bank : " + send_bank);
							System.out.println("FileNameSeq : " + file_seq);
							System.out.println("currency : " + currency);
							System.out.println("tot_rec : " + tot_rec);
							System.out.println("tot_amount : " + tot_amount);
							System.out.println("type : " + type);

							Optional<BankAgentTable> bankList = bankAgentTableRep.findById(receiving_bank);
							if (bankList.isPresent()) {
								benParkingAcctNum = bankList.get().getParking_acct_num();
							} else {
								throw new CustomException("File has not been successfully uploaded. Bank is not participated in IPS");
							}
							
							if(!type.equals("1")) {
								throw new CustomException("File has not been successfully uploaded. Please check file");
							}

						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
					} else {
						System.out.println("Body : " + line);

						System.out.println("Body : " + line.length());
						if (line.length() == 189) {
							String ref_no = line.substring(0, 8).trim();
							String trDate = line.substring(18, 26).trim();
							// String payeeAcctNumber = line.substring(26, 44).trim();
							String payeeAcctNumber = benParkingAcctNum;
							String payeeReference = line.substring(44, 49).trim();
							String payeeName = line.substring(49, 89).trim();
							String payeeRef = line.substring(89, 119).trim();
							String benAcctNumber = line.substring(119, 137).trim();
							String benName = line.substring(137, 177).trim();
							String amount = line.substring(177, 189).trim();

							System.out.println("Ref Num :" + ref_no);
							System.out.println("Tr Date :" + trDate);
							System.out.println("payeeAcctNumber :" + payeeAcctNumber);
							System.out.println("payeeReference :" + payeeReference);
							System.out.println("payeeName :" + payeeName);
							System.out.println("payeeRef :" + payeeRef);
							System.out.println("benAcctNumber :" + benAcctNumber);
							System.out.println("benName :" + benName);
							System.out.println("amount :" + amount);

							BulkTransaction bulkTransaction = new BulkTransaction();
							bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
							bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
							bulkTransaction.setTran_ref_id(fileName);
							bulkTransaction.setRem_name(payeeName);
							bulkTransaction.setRemit_account_no(payeeAcctNumber);
							bulkTransaction.setBen_acct_name(benName);
							bulkTransaction.setBen_acct_no(benAcctNumber);
							bulkTransaction.setBen_bank_code(receiving_bank);
							bulkTransaction.setTran_crncy_code(currency);
							bulkTransaction.setMaster_ref_no(amount);
							bulkTransaction.setTran_particular(payeeRef);

							result.add(bulkTransaction);
							System.out.println("------------");
							SrlNo++;
						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
						
					}
					count++;
				}

				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e.getLocalizedMessage());
			}
		} else {
			System.out.println("dsfjhf");
			throw new CustomException("File has not been successfully uploaded. Invalid File Name");
		}

	}
	
	
	@SuppressWarnings("deprecation")
	public List<ManualTransaction> processUploadManual(String screenId, MultipartFile file, String userid)
			throws SQLException, FileNotFoundException, IOException {

		String fileName = file.getOriginalFilename();

		String fileExt = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileExt = fileName.substring(i + 1);
		}

		List<ManualTransaction> result = new ArrayList<ManualTransaction>();
		if (fileExt.equals("xlsx") || fileExt.equals("xls")) {

			System.out.println("FileNAme Of Manual:"+fileName);
			if (fileName.contains("Manual")) {

				try {

					//OPCPackage pkg = OPCPackage.open(file.getInputStream());

					/*Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
							.open(file.getInputStream());*/
					
					Workbook workbook = WorkbookFactory.create(file.getInputStream());
					

					List<HashMap<Integer, String>> mapList = new ArrayList<>();
					for (Sheet s : workbook) {
						for (Row r : s) {
							if(!isRowEmpty(r)) {
								System.out.println(r.getRowNum());
								if (r.getRowNum() == 0) {
									continue;
								}
								String val = null;

								HashMap<Integer, String> map = new HashMap<>();
								for (int j = 0; j < 9; j++) {
									Cell cell = r.getCell(j);
									
									//if(cell.getCellType()) {
										if (cell.getCellType() == 0) {
											double val1 = cell.getNumericCellValue();
											val = String.valueOf(val1);
											map.put(j, val);
										} else {
											val = cell.getStringCellValue();
											map.put(j, val);
										}
									//}
									
								}

								mapList.add(map);
							}

						}

					}

					/// Generate Document Ref ID
					String Doc_Ref_ID = sequence.generateDocRefID();
					int SrlNo = 1;

					for (HashMap<Integer, String> item : mapList) {
						ManualTransaction bulkTransaction = new ManualTransaction();
						bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
						bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
						bulkTransaction.setTran_ref_id(item.get(0));
						bulkTransaction.setRem_name(item.get(1));
						bulkTransaction.setRemit_account_no(item.get(2));
						bulkTransaction.setBen_acct_name(item.get(3));
						bulkTransaction.setBen_acct_no(item.get(4));
						bulkTransaction.setBen_bank_code(item.get(5));
						bulkTransaction.setTran_crncy_code(item.get(6));
						bulkTransaction.setMaster_ref_no(item.get(7));
						bulkTransaction.setTran_particular(item.get(8));

						SrlNo++;
						result.add(bulkTransaction);
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
					throw new CustomException("File has not been successfully uploaded");
				}
			} else {
				throw new CustomException("Invalid File Name");
			}
		} else if (fileExt.equals("txt")) {
			try {
				// File file = new File(fileName);
				// FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

				String line;

				int count = 0;
				String receiving_bank = null;
				String send_bank;
				String currency = null;
				String type;
				String benParkingAcctNum = null;

				/// Generate Document Ref ID
				String Doc_Ref_ID = sequence.generateDocRefID();
				int SrlNo = 1;

				while ((line = br.readLine()) != null) {

					if (count == 0) {
						System.out.println("Header : " + line);
						System.out.println("Header Length: " + line.length());
						if (line.length() == 38) {

							receiving_bank = line.substring(1, 3).trim();
							send_bank = line.substring(3, 5).trim();
							String file_seq = line.substring(13, 16).trim();
							currency = line.substring(16, 19).trim();
							String tot_rec = line.substring(19, 24).trim();
							String tot_amount = line.substring(24, 37).trim();
							type = line.substring(37, 38).trim();

							System.out.println("receiving_bank : " + receiving_bank);
							System.out.println("send_bank : " + send_bank);
							System.out.println("FileNameSeq : " + file_seq);
							System.out.println("currency : " + currency);
							System.out.println("tot_rec : " + tot_rec);
							System.out.println("tot_amount : " + tot_amount);
							System.out.println("type : " + type);

							Optional<BankAgentTable> bankList = bankAgentTableRep.findById(receiving_bank);
							if (bankList.isPresent()) {
								benParkingAcctNum = bankList.get().getParking_acct_num();
							} else {
								throw new CustomException("File has not been successfully uploaded. Bank is not participated in IPS");
							}
							
							if(!type.equals("1")) {
								throw new CustomException("File has not been successfully uploaded. Please check file");
							}

						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
					} else {
						System.out.println("Body : " + line);

						System.out.println("Body : " + line.length());
						if (line.length() == 189) {
							String ref_no = line.substring(0, 8).trim();
							String trDate = line.substring(18, 26).trim();
							// String payeeAcctNumber = line.substring(26, 44).trim();
							String payeeAcctNumber = benParkingAcctNum;
							String payeeReference = line.substring(44, 49).trim();
							String payeeName = line.substring(49, 89).trim();
							String payeeRef = line.substring(89, 119).trim();
							String benAcctNumber = line.substring(119, 137).trim();
							String benName = line.substring(137, 177).trim();
							String amount = line.substring(177, 189).trim();

							System.out.println("Ref Num :" + ref_no);
							System.out.println("Tr Date :" + trDate);
							System.out.println("payeeAcctNumber :" + payeeAcctNumber);
							System.out.println("payeeReference :" + payeeReference);
							System.out.println("payeeName :" + payeeName);
							System.out.println("payeeRef :" + payeeRef);
							System.out.println("benAcctNumber :" + benAcctNumber);
							System.out.println("benName :" + benName);
							System.out.println("amount :" + amount);

							ManualTransaction bulkTransaction = new ManualTransaction();
							bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
							bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
							bulkTransaction.setTran_ref_id(fileName);
							bulkTransaction.setRem_name(payeeName);
							bulkTransaction.setRemit_account_no(payeeAcctNumber);
							bulkTransaction.setBen_acct_name(benName);
							bulkTransaction.setBen_acct_no(benAcctNumber);
							bulkTransaction.setBen_bank_code(receiving_bank);
							bulkTransaction.setTran_crncy_code(currency);
							bulkTransaction.setMaster_ref_no(amount);
							bulkTransaction.setTran_particular(payeeRef);

							result.add(bulkTransaction);
							System.out.println("------------");
							SrlNo++;
						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
						
					}
					count++;
				}

				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e.getLocalizedMessage());
			}
		} else {
			System.out.println("dsfjhf");
			throw new CustomException("File has not been successfully uploaded. Invalid File Name");
		}

	}

	@SuppressWarnings("deprecation")
	public List<RTPTransactionTable> processUploadRTP(String screenId, MultipartFile file, String userid)
			throws SQLException, FileNotFoundException, IOException {

		String fileName = file.getOriginalFilename();

		String fileExt = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileExt = fileName.substring(i + 1);
		}

		List<RTPTransactionTable> result = new ArrayList<RTPTransactionTable>();
		if (fileExt.equals("xlsx") || fileExt.equals("xls")) {

			if (fileName.contains("RTP")) {

				try {


					/*Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
							.open(file.getInputStream());*/
					
					Workbook workbook = WorkbookFactory.create(file.getInputStream());
					

					List<HashMap<Integer, String>> mapList = new ArrayList<>();
					for (Sheet s : workbook) {
						for (Row r : s) {
							if(!isRowEmpty(r)) {
								System.out.println(r.getRowNum());
								if (r.getRowNum() == 0) {
									continue;
								}
								String val = null;

								HashMap<Integer, String> map = new HashMap<>();
								for (int j = 0; j < 10; j++) {
									Cell cell = r.getCell(j);
									
									//if(cell.getCellType()) {
										if (cell.getCellType() == 0) {
											double val1 = cell.getNumericCellValue();
											val = String.valueOf(val1);
											map.put(j, val);
										} else {
											val = cell.getStringCellValue();
											map.put(j, val);
										}
									//}
									
								}

								mapList.add(map);
							}

						}

					}

					/// Generate Document Ref ID
					String Doc_Ref_ID = sequence.generateDocRefID();
					int SrlNo = 1;

					for (HashMap<Integer, String> item : mapList) {
						RTPTransactionTable bulkTransaction = new RTPTransactionTable();
						bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
						bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
						bulkTransaction.setTran_ref_id(item.get(0));
						bulkTransaction.setRem_name(item.get(1));
						bulkTransaction.setRemit_account_no(item.get(2));
						bulkTransaction.setRem_bank_code(item.get(3));
						bulkTransaction.setBen_acct_name(item.get(4));
						bulkTransaction.setBen_acct_no(item.get(5));
						bulkTransaction.setBen_bank_code(item.get(6));
						bulkTransaction.setTran_crncy_code(item.get(7));
						bulkTransaction.setMaster_ref_no(item.get(8));
						bulkTransaction.setTran_particular(item.get(9));

						SrlNo++;
						result.add(bulkTransaction);
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
					throw new CustomException("File has not been successfully uploaded");
				}
			} else {
				throw new CustomException("Invalid File Name");
			}
		} else if (fileExt.equals("txt")) {
			try {
				// File file = new File(fileName);
				// FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

				String line;

				int count = 0;
				String receiving_bank = null;
				String send_bank;
				String currency = null;
				String type;
				String benParkingAcctNum = null;

				/// Generate Document Ref ID
				String Doc_Ref_ID = sequence.generateDocRefID();
				int SrlNo = 1;

				while ((line = br.readLine()) != null) {

					if (count == 0) {
						System.out.println("Header : " + line);
						System.out.println("Header Length: " + line.length());
						if (line.length() == 38) {

							receiving_bank = line.substring(1, 3).trim();
							send_bank = line.substring(3, 5).trim();
							String file_seq = line.substring(13, 16).trim();
							currency = line.substring(16, 19).trim();
							String tot_rec = line.substring(19, 24).trim();
							String tot_amount = line.substring(24, 37).trim();
							type = line.substring(37, 38).trim();

							System.out.println("receiving_bank : " + receiving_bank);
							System.out.println("send_bank : " + send_bank);
							System.out.println("FileNameSeq : " + file_seq);
							System.out.println("currency : " + currency);
							System.out.println("tot_rec : " + tot_rec);
							System.out.println("tot_amount : " + tot_amount);
							System.out.println("type : " + type);

							Optional<BankAgentTable> bankList = bankAgentTableRep.findById(receiving_bank);
							if (bankList.isPresent()) {
								benParkingAcctNum = bankList.get().getParking_acct_num();
							} else {
								throw new CustomException("File has not been successfully uploaded. Bank is not participated in IPS");
							}
							
							if(!type.equals("1")) {
								throw new CustomException("File has not been successfully uploaded. Please check file");
							}

						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
					} else {
						System.out.println("Body : " + line);

						System.out.println("Body : " + line.length());
						if (line.length() == 189) {
							String ref_no = line.substring(0, 8).trim();
							String trDate = line.substring(18, 26).trim();
							// String payeeAcctNumber = line.substring(26, 44).trim();
							String payeeAcctNumber = benParkingAcctNum;
							String payeeReference = line.substring(44, 49).trim();
							String payeeName = line.substring(49, 89).trim();
							String payeeRef = line.substring(89, 119).trim();
							String benAcctNumber = line.substring(119, 137).trim();
							String benName = line.substring(137, 177).trim();
							String amount = line.substring(177, 189).trim();

							System.out.println("Ref Num :" + ref_no);
							System.out.println("Tr Date :" + trDate);
							System.out.println("payeeAcctNumber :" + payeeAcctNumber);
							System.out.println("payeeReference :" + payeeReference);
							System.out.println("payeeName :" + payeeName);
							System.out.println("payeeRef :" + payeeRef);
							System.out.println("benAcctNumber :" + benAcctNumber);
							System.out.println("benName :" + benName);
							System.out.println("amount :" + amount);

							RTPTransactionTable bulkTransaction = new RTPTransactionTable();
							bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
							bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
							bulkTransaction.setTran_ref_id(fileName);
							bulkTransaction.setRem_name(payeeName);
							bulkTransaction.setRemit_account_no(payeeAcctNumber);
							bulkTransaction.setBen_acct_name(benName);
							bulkTransaction.setBen_acct_no(benAcctNumber);
							bulkTransaction.setBen_bank_code(receiving_bank);
							bulkTransaction.setTran_crncy_code(currency);
							bulkTransaction.setMaster_ref_no(amount);
							bulkTransaction.setTran_particular(payeeRef);

							result.add(bulkTransaction);
							System.out.println("------------");
							SrlNo++;
						}else {
							throw new CustomException("File has not been successfully uploaded. Please check file");
						}
						
					}
					count++;
				}

				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(e.getLocalizedMessage());
			}
		} else {
			System.out.println("dsfjhf");
			throw new CustomException("File has not been successfully uploaded. Invalid File Name");
		}

	}


	
	@SuppressWarnings("deprecation")
	public List<BulkTransaction> processUploadDebit(String screenId, MultipartFile file, String userid)
			throws SQLException, FileNotFoundException, IOException {

		String fileName = file.getOriginalFilename();

		String fileExt = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileExt = fileName.substring(i + 1);
		}

		List<BulkTransaction> result = new ArrayList<BulkTransaction>();
		if (fileName.contains("BulkDebit")) {
			if (fileExt.equals("xlsx") || fileExt.equals("xls")) {

				try {

					/*Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
							.open(file.getInputStream());*/
					Workbook workbook = WorkbookFactory.create(file.getInputStream());

					List<HashMap<Integer, String>> mapList = new ArrayList<>();
					for (Sheet s : workbook) {
						for (Row r : s) {
							if(!isRowEmpty(r)) {
								System.out.println(r.getRowNum());
								if (r.getRowNum() == 0) {
									continue;
								}
								String val = null;

								HashMap<Integer, String> map = new HashMap<>();
								for (int j = 0; j < 9; j++) {
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

					}

					/// Generate Document Ref ID
					String Doc_Ref_ID = sequence.generateDocRefID();
					int SrlNo = 1;

					for (HashMap<Integer, String> item : mapList) {
						BulkTransaction bulkTransaction = new BulkTransaction();
						bulkTransaction.setDoc_ref_id(Doc_Ref_ID);
						bulkTransaction.setDoc_sub_id(String.valueOf(SrlNo));
						bulkTransaction.setTran_ref_id(item.get(0));
						bulkTransaction.setRem_name(item.get(1));
						bulkTransaction.setRemit_account_no(item.get(2));
						bulkTransaction.setBen_acct_name(item.get(3));
						bulkTransaction.setBen_acct_no(item.get(4));
						bulkTransaction.setBen_bank_code(item.get(5));
						bulkTransaction.setTran_crncy_code(item.get(6));
						bulkTransaction.setMaster_ref_no(item.get(7));
						bulkTransaction.setTran_particular(item.get(8));

						SrlNo++;
						result.add(bulkTransaction);
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
					throw new CustomException("File has not been successfully uploaded");
				}
			} else {
				throw new CustomException("File has not been successfully uploaded. Invalid File Name");
			}
		} else {
			throw new CustomException("File has not been successfully uploaded. Invalid File Name");
		}

	}

	public Object addDoc(String alertparam, String formmode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateIPSRefID(String docRefID, String ips_ref_no, String userID) {
		if (!ips_ref_no.equals("0")) {
			List<BulkTransaction> bulkTranList = bulkTransactionRepository.findByIdDocRefID(docRefID);
			for (BulkTransaction bulkTran : bulkTranList) {
				bulkTran.setIps_ref_no(ips_ref_no);
				bulkTran.setEntity_flg("Y");
				bulkTran.setVerify_user(userID);
				bulkTran.setVerify_time(new Date());
				bulkTransactionRepository.save(bulkTran);
			}
		}
	}

	public void updateIPSRefIDManual(String docRefID, String ips_ref_no,String userID) {
		if (!ips_ref_no.equals("0")) {
			List<ManualTransaction> bulkTranList = manualTransactionRepository.findByIdDocRefID(docRefID);
			for (ManualTransaction bulkTran : bulkTranList) {
				bulkTran.setIps_ref_no(ips_ref_no);
				bulkTran.setEntity_flg("Y");
				bulkTran.setVerify_user(userID);
				bulkTran.setVerify_time(new Date());
				manualTransactionRepository.save(bulkTran);
			}
		}
	}

	public List<BulkTransaction> getBulkCreditList(String tran_date) throws ParseException {
		List<Object[]> listobj = bulkTransactionRepository.getCustomBulkCreditList(tran_date);

		List<BulkTransaction> listBulk = new ArrayList<>();

		for (Object[] a : listobj) {
			BulkTransaction bulk = new BulkTransaction();
			SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
			bulk.setTran_date(dateF.parse((String) a[0]));
			bulk.setDoc_ref_id((String) a[1]);
			// bulk.setRem_name((String) (a[2]));
			bulk.setRemit_account_no((String) a[2]);
			bulk.setTran_amt((BigDecimal) a[3]);
			bulk.setIps_ref_no((String) a[4]);
			bulk.setEntity_flg(a[5].toString());
			bulk.setEntry_user(a[6].toString());
			bulk.setVerify_user((String) a[7]);
			listBulk.add(bulk);
		}

		return listBulk;
	}

	public List<BulkTransactionPojo> getBulkCreditDocRefList(String docRefID) throws ParseException {
		List<BulkTransaction> listobj = bulkTransactionRepository.findByIdDocRefID(docRefID);
		
		List<BulkTransactionPojo> listPojoobj=new ArrayList<>(); 
		for(BulkTransaction manual:listobj) {
			BulkTransactionPojo pojo=new BulkTransactionPojo(manual);
			List<BankAgentTable> dd1=bankAgentTableRep.findByIdCustomBankCode(pojo.getBen_bank_code());
			if(dd1.size()>0) {
				pojo.setBen_bank_code(dd1.get(0).getBank_name());
			}
			listPojoobj.add(pojo);
		}
		
		return listPojoobj;
		
	}

	public List<BulkTransaction> getBulkDebitList(String tran_date) throws ParseException {
		List<Object[]> listobj = bulkTransactionRepository.getCustomBulkDebitList(tran_date);

		List<BulkTransaction> listBulk = new ArrayList<>();

		for (Object[] a : listobj) {
			BulkTransaction bulk = new BulkTransaction();
			SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
			bulk.setTran_date(dateF.parse((String) a[0]));
			bulk.setDoc_ref_id((String) a[1]);
			bulk.setRem_name((String) (a[2]));
			bulk.setRemit_account_no((String) a[3]);
			bulk.setTran_amt((BigDecimal) a[4]);
			bulk.setIps_ref_no((String) a[5]);
			bulk.setEntity_flg(a[6].toString());
			bulk.setEntry_user(a[7].toString());
			bulk.setVerify_user((String) a[8]);
			listBulk.add(bulk);
		}

		return listBulk;
	}

	public List<BulkTransactionPojo> getBulkDebitDocRefList(String docRefID) throws ParseException {
		List<BulkTransaction> listobj = bulkTransactionRepository.findByIdDocRefID(docRefID);
		List<BulkTransactionPojo> listPojoobj=new ArrayList<>(); 
		for(BulkTransaction manual:listobj) {
			BulkTransactionPojo pojo=new BulkTransactionPojo(manual);
			List<BankAgentTable> dd1=bankAgentTableRep.findByIdCustomBankCode(pojo.getBen_bank_code());
			if(dd1.size()>0) {
				pojo.setBen_bank_code(dd1.get(0).getBank_name());
			}
			listPojoobj.add(pojo);
		}
		
		return listPojoobj;
	}

	public List<ManualTransaction> getManualTranList(String tranDate) throws ParseException {
		List<Object[]> listobj = manualTransactionRepository.getCustomManualList(tranDate);

		List<ManualTransaction> listBulk = new ArrayList<>();

		for (Object[] a : listobj) {
			ManualTransaction bulk = new ManualTransaction();
			SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
			bulk.setTran_date(dateF.parse((String) a[0]));
			bulk.setDoc_ref_id((String) a[1]);
			//bulk.setRem_name((String) (a[2]));
			//bulk.setRemit_account_no((String) a[3]);
			bulk.setTran_amt((BigDecimal) a[2]);
			bulk.setIps_ref_no((String) a[3]);
			bulk.setEntity_flg(a[4].toString());
			bulk.setEntry_user(a[5].toString());
			bulk.setVerify_user((String) a[6]);
			listBulk.add(bulk);
		}

		return listBulk;
	}

	public List<ManualTransactionPojo> getBulkManualDocRefList(String docRefID) throws ParseException {
		List<ManualTransaction> listobj = manualTransactionRepository.findByIdDocRefID(docRefID);
		List<ManualTransactionPojo> listPojoobj=new ArrayList<>(); 
		for(ManualTransaction manual:listobj) {
			ManualTransactionPojo pojo=new ManualTransactionPojo(manual);
			List<BankAgentTable> dd1=bankAgentTableRep.findByIdCustomBankCode(pojo.getBen_bank_code());
			if(dd1.size()>0) {
				pojo.setBen_bank_code(dd1.get(0).getBank_name());
			}
			listPojoobj.add(pojo);
		}
		
		return listPojoobj;
	}

	
	public List<RTPTransactionTable> getRTPTranList(String tranDate) throws ParseException {
		List<Object[]> listobj = rtpTransactionTableRep.getCustomManualList(tranDate);

		List<RTPTransactionTable> listBulk = new ArrayList<>();

		for (Object[] a : listobj) {
			RTPTransactionTable bulk = new RTPTransactionTable();
			SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
			bulk.setTran_date(dateF.parse((String) a[0]));
			bulk.setDoc_ref_id((String) a[1]);
			bulk.setRem_name((String) (a[2]));
			bulk.setRemit_account_no((String) a[3]);
			bulk.setTran_amt((BigDecimal) a[4]);
			bulk.setIps_ref_no((String) a[5]);
			bulk.setEntity_flg(a[6].toString());
			bulk.setEntry_user(a[7].toString());
			bulk.setVerify_user((String) a[8]);
			listBulk.add(bulk);
		}

		return listBulk;
	}
	
	public String getRTP(RTPTransactionTable rtpTransaction, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		if (formmode.equals("submit")) {

			RTPTransactionTable up = rtpTransaction;

			hs.saveOrUpdate(up);
			up.setDel_flg("N");
			up.setEntity_flg("N");
			up.setSplit_flg("N");

			rtpTransactionTableRep.save(up);
			msg = "Manual Transaction Successfully";

		}
		return msg;
	}
	
	
	public List<RTPTransactionTable> getBulkRTPDocRefList(String docRefID) throws ParseException {
		List<RTPTransactionTable> listobj = rtpTransactionTableRep.findByIdDocRefID(docRefID);
		
		List<RTPTransactionTable> list = new ArrayList<>();

		for(RTPTransactionTable data:listobj) {
			
			RTPTransactionTable ss=data;
			
			
			List<BankAgentTable> dd1=bankAgentTableRep.findByIdCustomBankCode(data.getRem_bank_code());
			if(dd1.size()>0) {
				ss.setRem_bank_code(dd1.get(0).getBank_name());
			}
			
			
			List<BankAgentTable> dd=bankAgentTableRep.findByIdCustomBankCode(data.getBen_bank_code());
			if(dd.size()>0) {
				ss.setBen_bank_code(dd.get(0).getBank_name());
				
			}
			
			
			list.add(ss);
		}
		
		return list;
	}
	
	public MCCreditTransferResponse getDetailsRTPDebit(RTPTransactionTable bulkTransaction, String formmode,
			String userID) {
		MCCreditTransferResponse res1 = new MCCreditTransferResponse();

		System.out.println("manual");
		if (formmode.equals("addsubmit")) {
			System.out.println("add");

			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");
			
			String remName = bulkTransaction.getRem_name().split(",")[0];
			boolean isRemName = Arrays.asList(bulkTransaction.getRem_name().split(",")).stream()
					.allMatch(x -> x.equals(remName));

			String remAcctNum = bulkTransaction.getRemit_account_no().split(",")[0];
			boolean isRemAcctNum = Arrays.asList(bulkTransaction.getRemit_account_no().split(",")).stream()
					.allMatch(x -> x.equals(remAcctNum));
			
			String remBank = bulkTransaction.getRem_bank_code().split(",")[0];
			boolean isRemBank = Arrays.asList(bulkTransaction.getRem_bank_code().split(",")).stream()
					.allMatch(x -> x.equals(remBank));

			
			if(isRemName&&isRemAcctNum&&isRemBank) {
				for (int i = 0; i < list1.length; i++) {

					if (bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
							&& bulkTransaction.getRem_bank_code().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
							&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
							&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
							&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
							&& bulkTransaction.getTran_particular().split(",")[i] != "") {

						RTPTransactionTable bulkTran = new RTPTransactionTable();

						bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
						bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

						if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
								&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
								&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Remitter Account Number");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

						if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
								&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
								&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Beneficiary Account Number");
							res1.setTranID("0");
							return res1;

						}

						//bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						//bulkTran.setRem_bank_code(bulkTransaction.getRem_bank_code().split(",")[i]);

						if (invalidBankCodeExceptBank(bulkTransaction.getRem_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Remitter Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setRem_bank_code(bulkTransaction.getRem_bank_code().split(",")[i]);
						}
						
						if (invalidBankCode(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Beneficiary Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						

						if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
							bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
							
						} else {
							res1.setStatus("Invalid Currency Code");
							res1.setTranID("0");
							return res1;
						}
						
						//bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);

						bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);
						if (bulkTransaction.getMaster_ref_no().split(",")[i]
								.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
							bulkTran.setTran_amt(new BigDecimal(trAmount));
						} else {
							res1.setStatus("You have entered an Invalid Amount");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setDel_flg("N");
						bulkTran.setEntity_flg("N");
						bulkTran.setSplit_flg("N");
						bulkTran.setTran_date(new Date());
						bulkTran.setEntry_user(userID);
						bulkTran.setEntry_time(new Date());
						rtpTransactionTableRep.save(bulkTran);
					} else {

						res1.setStatus("Info Missing.. Please Checkout All the Fields");
						res1.setTranID("0");
						return res1;
					}
				}
				res1.setStatus("BulkTransaction Submitted Successfully");
				res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			}else {
				//res.setStatus("Info Missing.. Please Checkout All the Fields");
				res1.setStatus("Please check remitter details must be Same");
				res1.setTranID("0");
				return res1;
			}

		}else if (formmode.equals("submit")) {
			String[] list1 = bulkTransaction.getDoc_ref_id().split(",");
			
			String remName = bulkTransaction.getRem_name().split(",")[0];
			boolean isRemName = Arrays.asList(bulkTransaction.getRem_name().split(",")).stream()
					.allMatch(x -> x.equals(remName));

			String remAcctNum = bulkTransaction.getRemit_account_no().split(",")[0];
			boolean isRemAcctNum = Arrays.asList(bulkTransaction.getRemit_account_no().split(",")).stream()
					.allMatch(x -> x.equals(remAcctNum));
			
			String remBank = bulkTransaction.getRem_bank_code().split(",")[0];
			boolean isRemBank = Arrays.asList(bulkTransaction.getRem_bank_code().split(",")).stream()
					.allMatch(x -> x.equals(remBank));

			
			if(isRemName&&isRemAcctNum&&isRemBank) {
				for (int i = 0; i < list1.length; i++) {

					if (bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRem_name().split(",")[i] != ""
							&& bulkTransaction.getRemit_account_no().split(",")[i] != ""
							&& bulkTransaction.getRem_bank_code().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_name().split(",")[i] != ""
							&& bulkTransaction.getBen_acct_no().split(",")[i] != ""
							&& bulkTransaction.getBen_bank_code().split(",")[i] != ""
							&& bulkTransaction.getTran_crncy_code().split(",")[i] != ""
							&& bulkTransaction.getMaster_ref_no().split(",")[i] != ""
							&& bulkTransaction.getTran_particular().split(",")[i] != "") {

						RTPTransactionTable bulkTran = new RTPTransactionTable();

						bulkTran.setTran_ref_id(bulkTransaction.getTran_ref_id().split(",")[i]);
						bulkTran.setRem_name(bulkTransaction.getRem_name().split(",")[i]);

						if (bulkTransaction.getRemit_account_no().split(",")[i].length() >= 3
								&&bulkTransaction.getRemit_account_no().split(",")[i].length() <= 35
								&& bulkTransaction.getRemit_account_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setRemit_account_no(bulkTransaction.getRemit_account_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Remitter Account Number");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setBen_acct_name(bulkTransaction.getBen_acct_name().split(",")[i]);

						if (bulkTransaction.getBen_acct_no().split(",")[i].length() >= 3
								&&bulkTransaction.getBen_acct_no().split(",")[i].length() <= 35
								&& bulkTransaction.getBen_acct_no().split(",")[i].matches("^[A-Za-z0-9_.]+$")) {
							bulkTran.setBen_acct_no(bulkTransaction.getBen_acct_no().split(",")[i]);
						} else {
							res1.setStatus("You have entered an Invalid Beneficiary Account Number");
							res1.setTranID("0");
							return res1;

						}

						//bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						//bulkTran.setRem_bank_code(bulkTransaction.getRem_bank_code().split(",")[i]);

						if (invalidBankCodeExceptBank(bulkTransaction.getRem_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Remitter Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setRem_bank_code(bulkTransaction.getRem_bank_code().split(",")[i]);
						}
						
						if (invalidBankCode(bulkTransaction.getBen_bank_code().split(",")[i])) {
							res1.setStatus("Invalid Beneficiary Bank Code");
							res1.setTranID("0");
							return res1;
						} else {
							bulkTran.setBen_bank_code(bulkTransaction.getBen_bank_code().split(",")[i]);
						}
						

						if (bulkTransaction.getTran_crncy_code().split(",")[i].equals("MUR")) {
							bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);
							
						} else {
							res1.setStatus("Invalid Currency Code");
							res1.setTranID("0");
							return res1;
						}
						
						//bulkTran.setTran_crncy_code(bulkTransaction.getTran_crncy_code().split(",")[i]);

						bulkTran.setDoc_ref_id(bulkTransaction.getDoc_ref_id().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);

						bulkTran.setTran_particular(bulkTransaction.getTran_particular().split(",")[i]);

						bulkTran.setDoc_sub_id(bulkTransaction.getDoc_sub_id().split(",")[i]);
						if (bulkTransaction.getMaster_ref_no().split(",")[i]
								.matches("((\\d{1,6})(((\\.)(\\d{0,2})){0,1}))")) {
							String trAmount = bulkTransaction.getMaster_ref_no().split(",")[i];
							bulkTran.setTran_amt(new BigDecimal(trAmount));
						} else {
							res1.setStatus("You have entered an Invalid Amount");
							res1.setTranID("0");
							return res1;

						}

						bulkTran.setDel_flg("N");
						bulkTran.setEntity_flg("N");
						bulkTran.setSplit_flg("N");
						bulkTran.setTran_date(new Date());
						bulkTran.setEntry_user(userID);
						bulkTran.setEntry_time(new Date());
						rtpTransactionTableRep.save(bulkTran);
					} else {

						res1.setStatus("Info Missing.. Please Checkout All the Fields");
						res1.setTranID("0");
						return res1;
					}
				}
				res1.setStatus("BulkTransaction Submitted Successfully");
				res1.setTranID(bulkTransaction.getDoc_ref_id().split(",")[0]);
			}else {
				//res.setStatus("Info Missing.. Please Checkout All the Fields");
				res1.setStatus("Please check remitter details must be Same");
				res1.setTranID("0");
				return res1;
			}	
			}

		return res1;
	}
	
	public void updateIPSRefIDRTP(String docRefID, String ips_ref_no,String userID) {
		
		
		
		if (!ips_ref_no.equals("0")) {
			
			List<RTPTransactionTable> bulkTranList = rtpTransactionTableRep.findByIdDocRefID(docRefID);
			for (RTPTransactionTable bulkTran : bulkTranList) {
				bulkTran.setIps_ref_no(ips_ref_no);
				bulkTran.setEntity_flg("Y");
				bulkTran.setVerify_user(userID);
				bulkTran.setVerify_time(new Date());
				rtpTransactionTableRep.save(bulkTran);
			}
		}
	}

	
	private static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();

		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}

}
