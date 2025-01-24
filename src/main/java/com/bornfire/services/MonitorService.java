package com.bornfire.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bornfire.entity.AuditTablePojo;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAgentTableRep;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSAuditTableRep;
import com.bornfire.entity.ParticipantFeesTable;
import com.bornfire.entity.RTPOutgoingHistRep;
import com.bornfire.entity.RTPOutgoingRep;
import com.bornfire.entity.RTP_Outgoing_entity;
import com.bornfire.entity.RTP_Outgoing_hist_entity;
import com.bornfire.entity.RegPublicKeyRep;
import com.bornfire.entity.RegPublicKeyTmp;
import com.bornfire.entity.SettlementHistReportRep;
import com.bornfire.entity.SettlementReport;
import com.bornfire.entity.SettlementReportRep;
import com.bornfire.entity.SettlementReportView;
import com.bornfire.entity.SnapShotElement;
import com.bornfire.entity.SnapShotPojo;
import com.bornfire.entity.TranCBSTable;
import com.bornfire.entity.TranCBSTableRep;
import com.bornfire.entity.TranCimCBSTable;
import com.bornfire.entity.TranCimCBSTablePojo;
import com.bornfire.entity.TranCimCBSTableRep;
import com.bornfire.entity.TranHistMonitorHistRep;
import com.bornfire.entity.TranIPSTable;
import com.bornfire.entity.TranIPStableRep;
import com.bornfire.entity.TranMonitoringHist;
import com.bornfire.entity.TransMoniRepositry;
import com.bornfire.entity.TransactionMonitoring;
import com.bornfire.entity.TransationMonitorPojo;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component
public class MonitorService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransMoniRepositry transMoniRepositry;

	@Autowired
	private TranHistMonitorHistRep tranHistMonitorRep;

	@Autowired
	private SettlementReportRep settelementReportRep;
	
	@Autowired
	private SettlementHistReportRep settelementHistReportRep;

	@Autowired
	private TranCBSTableRep tranCBSTableRep;

	@Autowired
	private TranCimCBSTableRep trancimCBSTableRep;
	
	@Autowired
	private IPSAuditTableRep iPSAuditTableRep;

	@Autowired
	DataSource srcdataSource;
	
	@Autowired
	RegPublicKeyRep regPublicKeyRep;
	
	@Autowired
	BankAgentTableRep bankAgentTableRep;
	
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private RTPOutgoingRep RTPoutgoingRep;
	
	@Autowired
	private RTPOutgoingHistRep RTPoutgoingHistRep;
	
	@Autowired
	TranIPStableRep tranIPSTableRep;
	
	@Autowired
	private TranCimCBSTableRep tranCimCbsTableRep;
	
	@Autowired
	IPSAccessRoleService AccessRoleService;
	
	@Autowired
	Environment env;
	
	public List<TransationMonitorPojo> getTranMonitorMCList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
			System.out.println("tggg");
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);

			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
				System.out.println("inn");
			} else {
				currentDate = false;
				System.out.println("out");
			}
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.findAllCustomoutward(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount( tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				transationMonitorPojo.setChannel(tranMonitor.get(i).getInit_channel_id());
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());
					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.findAllCustomoutward(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				transationMonitorPojo.setChannel(tranMonitor.get(i).getInit_channel_id());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}

				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());

				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());
					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> getTranMonitorRTPList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.findAllCustomRTP(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}

				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.findAllCustomRTP(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}

				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}

		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> getTranMonitorINCList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		if (currentDate) {
			List<Object[]> tranMonitor = transMoniRepositry.findAllCustomIncome1(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
//				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
//				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
//				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
//				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
//				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
//				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
//				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
//				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
//				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
//				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
//				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
//				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
//				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
//				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getInstr_id());
//				if(tranMonitor.get(i).getCdtr_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setBenBank("");
//					}
//				}else {
//					transationMonitorPojo.setBenBank("");
//				}
//				
//				if(tranMonitor.get(i).getDbtr_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getDbtr_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setRemBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setRemBank("");
//					}
//				}else {
//					transationMonitorPojo.setRemBank("");
//				}
//				
//				if(tranMonitor.get(i).getInstg_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getInstg_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setInitBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setInitBank("");
//					}
//				}else {
//					transationMonitorPojo.setInitBank("");
//				}
//				
//				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
//
//					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
//							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
//						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
//					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
//						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
//						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
//					} else {
//						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//						if (tranMonitor.get(i).getResponse_status() != (null)) {
//							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
//								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
//							}
//						} else {
//							transationMonitorPojo.setReason("");
//
//						}
//					}
//
//				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
//					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
//					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//				} else {
//					transationMonitorPojo.setReason("");
//					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//
//				}
//
//				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
//
//				transationMonitorPojoList.add(transationMonitorPojo);
				
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i)[0].toString());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i)[1].toString());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i)[2].toString());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i)[3].toString());
				transationMonitorPojo.setTran_date((Date) tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date) tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String) tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount( (BigDecimal) tranMonitor.get(i)[9]);
				transationMonitorPojo.setEntry_user((String) tranMonitor.get(i)[10]);
				transationMonitorPojo.setMaster_ref_id((String) tranMonitor.get(i)[11]);
				
				transationMonitorPojo.setBenBank((String) tranMonitor.get(i)[12]);
				
				transationMonitorPojo.setRemBank((String) tranMonitor.get(i)[13]);
				transationMonitorPojo.setInitBank((String) tranMonitor.get(i)[14]);
				//transationMonitorPojo.setChannel((String) tranMonitor.get(i)[14]);
				transationMonitorPojo.setTran_currency((String) tranMonitor.get(i)[15]);
				if ( tranMonitor.get(i)[16].toString().equals("FAILURE")) {

					if(tranMonitor.get(i)[17]!=null) {
						if (tranMonitor.get(i)[17].toString().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i)[17].toString().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						} else if (tranMonitor.get(i)[17].toString().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}else {
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
							if (tranMonitor.get(i)[19] != (null)) {
								if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
									transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
								}
							} else {
								transationMonitorPojo.setReason("");
	
							}
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						if (tranMonitor.get(i)[19] != (null)) {
							if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" +tranMonitor.get(i)[20].toString());
							}
						} else {
							if(tranMonitor.get(i)[20]!= (null)) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
							}else {
								transationMonitorPojo.setReason("");
							}
							

						}
					}

				} else if (tranMonitor.get(i)[16].toString().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i)[16].toString());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<Object[]> tranMonitor = tranHistMonitorRep.findAllCustomIncome1(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
//				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
//				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
//				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
//				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
//				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
//				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
//				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
//				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
//				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
//				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
//				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
//				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
//				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
//				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getInstr_id());	
//				if(tranMonitor.get(i).getCdtr_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setBenBank("");
//					}
//				}else {
//					transationMonitorPojo.setBenBank("");
//				}
//				
//				if(tranMonitor.get(i).getDbtr_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getDbtr_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setRemBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setRemBank("");
//					}
//				}else {
//					transationMonitorPojo.setRemBank("");
//				}
//				
//				if(tranMonitor.get(i).getInstg_agt()!=null) {
//					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getInstg_agt());
//					if(tc.size()>0) {
//						transationMonitorPojo.setInitBank(tc.get(0).getBank_name());
//					}else {
//						transationMonitorPojo.setInitBank("");
//					}
//				}else {
//					transationMonitorPojo.setInitBank("");
//				}
//				
//
//				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
//
//					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
//							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
//						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
//					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
//						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
//						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
//					} else {
//						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//						if (tranMonitor.get(i).getResponse_status() != (null)) {
//							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
//								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
//							}
//						} else {
//							transationMonitorPojo.setReason("");
//
//						}
//					}
//
//				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
//					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
//					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//				} else {
//					transationMonitorPojo.setReason("");
//					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
//
//				}
//
//				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
//
//				transationMonitorPojoList.add(transationMonitorPojo);
				
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i)[0].toString());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i)[1].toString());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i)[2].toString());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i)[3].toString());
				transationMonitorPojo.setTran_date((Date) tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date) tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String) tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount( (BigDecimal) tranMonitor.get(i)[9]);
				transationMonitorPojo.setEntry_user((String) tranMonitor.get(i)[10]);
				transationMonitorPojo.setMaster_ref_id((String) tranMonitor.get(i)[11]);
				
				transationMonitorPojo.setBenBank((String) tranMonitor.get(i)[12]);
				
				transationMonitorPojo.setRemBank((String) tranMonitor.get(i)[13]);
				transationMonitorPojo.setInitBank((String) tranMonitor.get(i)[14]);
				//transationMonitorPojo.setChannel((String) tranMonitor.get(i)[14]);
				transationMonitorPojo.setTran_currency((String) tranMonitor.get(i)[15]);
				if ( tranMonitor.get(i)[16].toString().equals("FAILURE")) {

					if(tranMonitor.get(i)[17]!=null) {
						if (tranMonitor.get(i)[17].toString().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i)[17].toString().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						} else if (tranMonitor.get(i)[17].toString().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}else {
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
							if (tranMonitor.get(i)[19] != (null)) {
								if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
									transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
								}
							} else {
								transationMonitorPojo.setReason("");
	
							}
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						if (tranMonitor.get(i)[19] != (null)) {
							if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" +tranMonitor.get(i)[20].toString());
							}
						} else {
							if(tranMonitor.get(i)[20]!= (null)) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
							}else {
								transationMonitorPojo.setReason("");
							}
							

						}
					}

				} else if (tranMonitor.get(i)[16].toString().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i)[16].toString());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		return transationMonitorPojoList;

	}
	
	public List<TransationMonitorPojo> getTranMonitorBulkList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.findAllCustomBulk(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				if(tranMonitor.get(i).getMsg_type().equals("BULK_DEBIT")) {
					transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id()+"/1");
				}else {
					transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.findAllCustomBulk(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getMsg_type().equals("BULK_DEBIT")) {
					transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id()+"/1");
				}else {
					transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		return transationMonitorPojoList;
	}


	public List<SettlementReport> getSettlementReport(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			//tranDate = dateFormat.format(new Date());
			currentDate = true;
			tranDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		} else {
			//tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			tranDate=new SimpleDateFormat("yyyy-MM-dd").format(enteredDate);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}

		List<SettlementReport> settlRepViewList = new ArrayList<>();

		if (currentDate) {
			List<Object[]> list = settelementReportRep.getSettleCurrentReport1(tranDate);

			for (Object[] item : list) {
				SettlementReport settlRepView = new SettlementReport();
				settlRepView.setValue_date((Date)item[0]);
				settlRepView.setMsg_date((String) item[1]);
				settlRepView.setMsg_id((String) item[2]);
				settlRepView.setEntry_entrydtl_btch_msg_id((String) item[3]);
				settlRepView.setEntry_acct_svcr_ref((String) item[4]);
				settlRepView.setEntry_amount((String) item[5]);
				settlRepView.setEntry_currency((String) item[6]);
				settlRepView.setEntry_entrydtl_btch_tot_amt((String) item[7]);
				settlRepView.setEntry_entrydtl_btch_cd_dbt_ind((String) item[8]);
				
				settlRepViewList.add(settlRepView);
			}
		} else {
			List<Object[]> list = settelementHistReportRep.getSettleCurrentReport1(tranDate);

			for (Object[] item : list) {
				SettlementReport settlRepView = new SettlementReport();
				settlRepView.setValue_date((Date)item[0]);
				settlRepView.setMsg_date((String) item[1]);
				settlRepView.setMsg_id((String) item[2]);
				settlRepView.setEntry_entrydtl_btch_msg_id((String) item[3]);
				settlRepView.setEntry_acct_svcr_ref((String) item[4]);
				settlRepView.setEntry_amount((String) item[5]);
				settlRepView.setEntry_currency((String) item[6]);
				settlRepView.setEntry_entrydtl_btch_tot_amt((String) item[7]);
				settlRepView.setEntry_entrydtl_btch_cd_dbt_ind((String) item[8]);
				settlRepViewList.add(settlRepView);
			}
		}

		return settlRepViewList;
	}

	public List<TransationMonitorPojo> getReversalTranList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		if (currentDate) {
			List<Object[]> tranMonitor = transMoniRepositry.findAllCustomReverse(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account((String)tranMonitor.get(i)[0]);
				transationMonitorPojo.setBob_account_name((String)tranMonitor.get(i)[1]);
				transationMonitorPojo.setIpsx_account((String)tranMonitor.get(i)[2]);
				transationMonitorPojo.setIpsx_account_name((String)tranMonitor.get(i)[3]);
				transationMonitorPojo.setTran_date((Date)tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date)tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String)tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String)tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String)tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount((BigDecimal)tranMonitor.get(i)[9]);
				transationMonitorPojo.setTran_currency((String)tranMonitor.get(i)[10]);
				transationMonitorPojo.setEntry_user((String)tranMonitor.get(i)[11]);
				transationMonitorPojo.setTranStatusSeq((String)tranMonitor.get(i)[12]);
				transationMonitorPojo.setReason("IPSX  :" + (String)tranMonitor.get(i)[16]);
				transationMonitorPojo.setTran_status((String)tranMonitor.get(i)[12]);

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<Object[]> tranMonitor = tranHistMonitorRep.findAllCustomReverse(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account((String)tranMonitor.get(i)[0]);
				transationMonitorPojo.setBob_account_name((String)tranMonitor.get(i)[1]);
				transationMonitorPojo.setIpsx_account((String)tranMonitor.get(i)[2]);
				transationMonitorPojo.setIpsx_account_name((String)tranMonitor.get(i)[3]);
				transationMonitorPojo.setTran_date((Date)tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date)tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String)tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String)tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String)tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount((BigDecimal)tranMonitor.get(i)[9]);
				transationMonitorPojo.setTran_currency((String)tranMonitor.get(i)[10]);
				transationMonitorPojo.setEntry_user((String)tranMonitor.get(i)[11]);
				transationMonitorPojo.setTranStatusSeq((String)tranMonitor.get(i)[12]);
				transationMonitorPojo.setReason("IPSX  :" + (String)tranMonitor.get(i)[16]);
				transationMonitorPojo.setTran_status((String)tranMonitor.get(i)[12]);

				transationMonitorPojoList.add(transationMonitorPojo);	
			}
		}
		return transationMonitorPojoList;

	}

	public List<TranCimCBSTable> findAllCustomReverse(String seqUniqueID, String type) {

		if (type.equals("0")) {
			List<TranCimCBSTable> tranMonitorCBS = trancimCBSTableRep.findAllCustomOutgoing(seqUniqueID);
			return tranMonitorCBS;
		} else if (type.equals("1")) {
			List<TranCimCBSTable> tranMonitorCBS = trancimCBSTableRep.findAllCustomIncoming(seqUniqueID);
			return tranMonitorCBS;
		} else if (type.equals("2")) {
			List<TranCimCBSTable> tranMonitorCBS = trancimCBSTableRep.findAllCustomOutgoing(seqUniqueID);
			return tranMonitorCBS;
		} else if (type.equals("3")) {
			List<TranCimCBSTable> tranMonitorCBS = trancimCBSTableRep.findAllCustomOutgoing(seqUniqueID.split("/")[0]);
			return tranMonitorCBS;
		} else if (type.equals("4")) {
			List<TranCimCBSTable> tranMonitorCBS = trancimCBSTableRep.findAllCustomOutgoing(seqUniqueID);
			return tranMonitorCBS;
		}
		return null;

	}

	public List<TransationMonitorPojo> getOutwardTranMonitor(String tranDate) {

		List<Object[]> tranMonitor = tranHistMonitorRep.findAllCustomTranOutwardRecon(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getOutwardTranSettl(String tranDate) {

		List<Object[]> tranMonitor = settelementReportRep.findAllCustomTranOutwardSuccess(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getInwardTranMonitor(String tranDate) {

		List<Object[]> tranMonitor = tranHistMonitorRep.findAllCustomTranInwardRecon(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getInwardTranSettl(String tranDate) {
		List<Object[]> tranMonitor = settelementReportRep.findAllCustomTranInwardSuccess(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getRTPTranMonitor(String tranDate) {
		List<Object[]> tranMonitor = tranHistMonitorRep.findAllCustomTranRTPRecon(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getRTPTranSettl(String tranDate) {
		List<Object[]> tranMonitor = settelementReportRep.findAllCustomTranRTPSuccess(tranDate);
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		for (Object[] item : tranMonitor) {
			TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
			transationMonitorPojo.setTran_date((Date) item[0]);
			transationMonitorPojo.setSequence_unique_id((String) item[1]);
			transationMonitorPojo.setBob_account((String) item[2]);
			transationMonitorPojo.setTran_currency((String) item[3]);
			transationMonitorPojo.setTran_amount((BigDecimal) item[4]);
			transationMonitorPojoList.add(transationMonitorPojo);
		}
		return transationMonitorPojoList;
	}

	public String getReconciliationInwardRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = transMoniRepositry.countInwardReconcillation(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}

		} else {
			String amt = tranHistMonitorRep.countInwardReconcillation(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}
		}
	}

	public String getReconciliationOutwardRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = RTPoutgoingRep.countOutwardReconcillation(tranDate);
			String amt1 = transMoniRepositry.countOutwardReconcillation(tranDate);
			Double totAmt=Double.parseDouble(amt)+Double.parseDouble(amt1);
			if (String.valueOf(totAmt) != null) {
				return String.valueOf(totAmt);
			} else {
				return "0";
			}

		} else {
			String amt = RTPoutgoingHistRep.countOutwardReconcillation(tranDate);
			String amt1 = tranHistMonitorRep.countOutwardReconcillation(tranDate);
			Double totAmt=Double.parseDouble(amt)+Double.parseDouble(amt1);
			if (String.valueOf(totAmt) != null) {
				return String.valueOf(totAmt);
			} else {
				return "0";
			}
		}
	}

	public String getReconciliationUnmatchRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = transMoniRepositry.countUnmatchReconcillation(tranDate);
			String amt1 = RTPoutgoingRep.countUnmatchReconcillation(tranDate);
			
			Double totAmt=Double.parseDouble(amt)+Double.parseDouble(amt1);
			if (String.valueOf(totAmt) != null) {
				return String.valueOf(totAmt);
			} else {
				return "0";
			}

		} else {
			String amt = tranHistMonitorRep.countUnmatchReconcillation(tranDate);
			String amt1 = RTPoutgoingHistRep.countUnmatchReconcillation(tranDate);
			Double totAmt=Double.parseDouble(amt)+Double.parseDouble(amt1);
			if (String.valueOf(totAmt) != null) {
				return String.valueOf(totAmt);
			} else {
				return "0";
			}
		}
	}

	public String getReconciliationOffsetRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = transMoniRepositry.countOffsetReconcillation(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}

		} else {
			String amt = tranHistMonitorRep.countOffsetReconcillation(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}
		}
	}

	public String getSettlInwardRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = transMoniRepositry.countSettlBOMInward(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}

		} else {
			String amt = tranHistMonitorRep.countSettlBOMInward(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}
		}

	}

	public String getSettlOutwardRecord(String tranDate) throws ParseException {
		boolean currentDate = false;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			String amt = transMoniRepositry.countSettlBOMOutward(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}

		} else {
			String amt = tranHistMonitorRep.countSettlBOMOutward(tranDate);
			if (amt != null) {
				return String.valueOf(amt);
			} else {
				return "0";
			}
		}
	}

	public String getReconciliationInwardRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.inwardReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.inwardReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		}

	}

	public String getReconciliationOutwardRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.outwardReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.outwardReconcillationList(tranDate);
			
			int totvalue=(tranMonitor.size())+(tranMonitor1.size());
			
			return String.valueOf(totvalue);
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.outwardReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.outwardReconcillationList(tranDate);
            
			int totvalue=(tranMonitor.size())+(tranMonitor1.size());
			
			return String.valueOf(totvalue);
		}
	}

	public String getSettlInwardRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<Object[]> tranMonitor = transMoniRepositry.inwardSettlList(tranDate);
			return String.valueOf(tranMonitor.size());
		} else {
			List<Object[]> tranMonitor = tranHistMonitorRep.inwardSettlList(tranDate);
			return String.valueOf(tranMonitor.size());
		}
	}

	public String getSettlOutwardRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<Object[]> tranMonitor = transMoniRepositry.outwardSettlList(tranDate);
			return String.valueOf(tranMonitor.size());
		} else {
			List<Object[]> tranMonitor = tranHistMonitorRep.outwardSettlList(tranDate);
			return String.valueOf(tranMonitor.size());
		}
	}

	public String getReconciliationOffesetRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.offsetReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.offsetReconcillationList(tranDate);

			int tot=(tranMonitor.size())+tranMonitor1.size();
			return String.valueOf(tot);
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.offsetReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.offsetReconcillationList(tranDate);
			int tot=(tranMonitor.size())+tranMonitor1.size();
			return String.valueOf(tot);
		}

	}

	public String getReconciliationUnmatchRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.unmatchReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.unmatchReconcillationList(tranDate);

		
			int TotAmt=(tranMonitor.size())+(tranMonitor1.size());
			return String.valueOf(TotAmt);
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.unmatchReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.unmatchReconcillationList(tranDate);

			int TotAmt=(tranMonitor.size())+(tranMonitor1.size());
			return String.valueOf(TotAmt);
		}
	}

	public String getReconciliationfailedRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.failedReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.failedReconcillationList(tranDate);

			int tot=(tranMonitor.size())+(tranMonitor1.size());
			return String.valueOf(tot);
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.failedReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.failedReconcillationList(tranDate);
			int tot=(tranMonitor.size())+(tranMonitor1.size());
			return String.valueOf(tot);
		}
	}

	public String getReconciliationBOBTotRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.totalReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.totalReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		}
	}

	public String getSettleBOBTotRecordNoTxs(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		if (currentDate) {
			List<SettlementReport> tranMonitor = settelementReportRep.totReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		} else {
			List<SettlementReport> tranMonitor = settelementReportRep.totReconcillationList(tranDate);
			return String.valueOf(tranMonitor.size());
		}
	}

	public List<TransationMonitorPojo> getReconciliationInwardRecordList(String tranDate) throws ParseException {

		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.inwardReconcillationList(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("CRDT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.inwardReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("CRDT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> getReconciliationOutwardRecordList(String tranDate) throws ParseException {

		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.outwardReconcillationList(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("DBIT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.outwardReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("DBIT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		
		if (currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.outwardReconcillationList(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("DBIT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.outwardReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_type("DBIT");
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		
		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> getSettlInwardRecordList(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<Object[]> tranMonitorList = transMoniRepositry.inwardSettlList(tranDate);
			for (int i = 0; i < tranMonitorList.size(); i++) {

				Object[] tranMonitor = tranMonitorList.get(i);
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date((Date) tranMonitor[0]);
				transationMonitorPojo.setValue_date((Date) tranMonitor[1]);
				transationMonitorPojo.setMsg_type((String) tranMonitor[2]);
				transationMonitorPojo.setTran_type((String) tranMonitor[3]);
				transationMonitorPojo.setBob_account((String) tranMonitor[4]);
				transationMonitorPojo.setBob_account_name((String) tranMonitor[5]);
				transationMonitorPojo.setIpsx_account((String) tranMonitor[6]);
				transationMonitorPojo.setIpsx_account_name((String) tranMonitor[7]);
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor[8]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor[9]);

				transationMonitorPojo.setTran_amount(new BigDecimal(tranMonitor[10].toString()));
				transationMonitorPojo.setTran_currency((String) tranMonitor[11]);
				transationMonitorPojo.setTran_status((String) tranMonitor[12]);
				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<Object[]> tranMonitorList = tranHistMonitorRep.inwardSettlList(tranDate);

			for (int i = 0; i < tranMonitorList.size(); i++) {
				Object[] tranMonitor = tranMonitorList.get(i);
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date((Date) tranMonitor[0]);
				transationMonitorPojo.setValue_date((Date) tranMonitor[1]);
				transationMonitorPojo.setMsg_type((String) tranMonitor[2]);
				transationMonitorPojo.setTran_type((String) tranMonitor[3]);
				transationMonitorPojo.setBob_account((String) tranMonitor[4]);
				transationMonitorPojo.setBob_account_name((String) tranMonitor[5]);
				transationMonitorPojo.setIpsx_account((String) tranMonitor[6]);
				transationMonitorPojo.setIpsx_account_name((String) tranMonitor[7]);
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor[8]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor[9]);

				transationMonitorPojo.setTran_amount(new BigDecimal(tranMonitor[10].toString()));
				transationMonitorPojo.setTran_currency((String) tranMonitor[11]);
				transationMonitorPojo.setTran_status((String) tranMonitor[12]);
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getSettlOutwardRecordList(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<Object[]> tranMonitorList = transMoniRepositry.outwardSettlList(tranDate);
			for (int i = 0; i < tranMonitorList.size(); i++) {

				Object[] tranMonitor = tranMonitorList.get(i);
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date((Date) tranMonitor[0]);
				transationMonitorPojo.setValue_date((Date) tranMonitor[1]);
				transationMonitorPojo.setMsg_type((String) tranMonitor[2]);
				transationMonitorPojo.setTran_type((String) tranMonitor[3]);
				transationMonitorPojo.setBob_account((String) tranMonitor[4]);
				transationMonitorPojo.setBob_account_name((String) tranMonitor[5]);
				transationMonitorPojo.setIpsx_account((String) tranMonitor[6]);
				transationMonitorPojo.setIpsx_account_name((String) tranMonitor[7]);
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor[8]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor[9]);

				transationMonitorPojo.setTran_amount(new BigDecimal(tranMonitor[10].toString()));
				transationMonitorPojo.setTran_currency((String) tranMonitor[11]);
				transationMonitorPojo.setTran_status((String) tranMonitor[12]);
				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<Object[]> tranMonitorList = tranHistMonitorRep.outwardSettlList(tranDate);

			for (int i = 0; i < tranMonitorList.size(); i++) {

				Object[] tranMonitor = tranMonitorList.get(i);
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date((Date) tranMonitor[0]);
				transationMonitorPojo.setValue_date((Date) tranMonitor[1]);
				transationMonitorPojo.setMsg_type((String) tranMonitor[2]);
				transationMonitorPojo.setTran_type((String) tranMonitor[3]);
				transationMonitorPojo.setBob_account((String) tranMonitor[4]);
				transationMonitorPojo.setBob_account_name((String) tranMonitor[5]);
				transationMonitorPojo.setIpsx_account((String) tranMonitor[6]);
				transationMonitorPojo.setIpsx_account_name((String) tranMonitor[7]);
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor[8]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor[9]);

				transationMonitorPojo.setTran_amount(new BigDecimal(tranMonitor[10].toString()));
				transationMonitorPojo.setTran_currency((String) tranMonitor[11]);
				transationMonitorPojo.setTran_status((String) tranMonitor[12]);
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getReconciliationOffesetRecordList(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.offsetReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.offsetReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
				transationMonitorPojoList.add(transationMonitorPojo);
			}
			
			
			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.offsetReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.offsetReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}

				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
				transationMonitorPojoList.add(transationMonitorPojo);
			}
			
			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				transationMonitorPojoList.add(transationMonitorPojo);
			}



		}
		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> getReconciliationUnmatchRecordList(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		
		
		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.unmatchReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.unmatchReconcillationList(tranDate);

			
			
			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
			
				
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}


				if(tranMonitor.get(i).getRmt_info()!=null) {
					transationMonitorPojo.setTran_particulars(tranMonitor1.get(i).getRmt_info());
				}
				
				transationMonitorPojoList.add(transationMonitorPojo);

						
			}
			
			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				
				if (tranMonitor1.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor1.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor1.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor1.get(i).getCbs_status_error());
					} else if (tranMonitor1.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor1.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						if (tranMonitor1.get(i).getResponse_status() != (null)) {
							if (tranMonitor1.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor1.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}

				if(tranMonitor1.get(i).getTran_rmks()!=null) {
					transationMonitorPojo.setTran_particulars(tranMonitor1.get(i).getTran_rmks());
				}
				
				transationMonitorPojoList.add(transationMonitorPojo);

						
			}

		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.unmatchReconcillationList(tranDate);

			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.unmatchReconcillationList(tranDate);

			
			
			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}
				
				if(tranMonitor.get(i).getRmt_info()!=null) {
					transationMonitorPojo.setTran_particulars(tranMonitor.get(i).getRmt_info());
				}
				
				transationMonitorPojoList.add(transationMonitorPojo);

						
			}
			
			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				if (tranMonitor1.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor1.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor1.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor1.get(i).getCbs_status_error());
					} else if (tranMonitor1.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor1.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						if (tranMonitor1.get(i).getResponse_status() != (null)) {
							if (tranMonitor1.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor1.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}
				
				if(tranMonitor1.get(i).getTran_rmks()!=null) {
					transationMonitorPojo.setTran_particulars(tranMonitor1.get(i).getTran_rmks());
				}
				
				transationMonitorPojoList.add(transationMonitorPojo);

						
			}
		}
		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> getReconciliationFailedRecordList(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<TransactionMonitoring> tranMonitor = transMoniRepositry.failedReconcillationList(tranDate);
			List<RTP_Outgoing_entity> tranMonitor1 = RTPoutgoingRep.failedReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}
				transationMonitorPojoList.add(transationMonitorPojo);
			}
			
			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				if (tranMonitor1.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor1.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor1.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor1.get(i).getCbs_status_error());
					} else if (tranMonitor1.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor1.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						if (tranMonitor1.get(i).getResponse_status() != (null)) {
							if (tranMonitor1.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor1.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		} else {
			List<TranMonitoringHist> tranMonitor = tranHistMonitorRep.failedReconcillationList(tranDate);
			List<RTP_Outgoing_hist_entity> tranMonitor1 = RTPoutgoingHistRep.failedReconcillationList(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				if (tranMonitor.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}

				transationMonitorPojo.setBob_account(tranMonitor.get(i).getBob_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getBob_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor.get(i).getCbs_response_time());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}
				transationMonitorPojoList.add(transationMonitorPojo);
			}
			

			for (int i = 0; i < tranMonitor1.size(); i++) {

				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setTran_date(tranMonitor1.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor1.get(i).getValue_date());
				transationMonitorPojo.setMsg_type(tranMonitor1.get(i).getMsg_type());
				if (tranMonitor1.get(i).getMsg_type().equals("INCOMING")) {
					transationMonitorPojo.setTran_type("CRDT");
				} else {
					transationMonitorPojo.setTran_type("DBIT");
				}
				transationMonitorPojo.setBob_account(tranMonitor1.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor1.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor1.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor1.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_particulars("");
				transationMonitorPojo.setSequence_unique_id(tranMonitor1.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor1.get(i).getTran_audit_number());
				transationMonitorPojo.setTran_amount(tranMonitor1.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor1.get(i).getTran_currency());
				transationMonitorPojo.setTran_status(tranMonitor1.get(i).getTran_status());
				transationMonitorPojo.setReversalDate(tranMonitor1.get(i).getCbs_response_time());
				if (tranMonitor1.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor1.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor1.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor1.get(i).getCbs_status_error());
					} else if (tranMonitor1.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor1.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor1.get(i).getTran_status());
						if (tranMonitor1.get(i).getResponse_status() != (null)) {
							if (tranMonitor1.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor1.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor1.get(i).getIpsx_status_error());
				} else {
					transationMonitorPojo.setReason("");
				}
				transationMonitorPojoList.add(transationMonitorPojo);
			}


		}
		return transationMonitorPojoList;

	}

	public List<SnapShotPojo> getSnapShotDetails(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<SnapShotPojo> snapShotPojoList = new ArrayList<SnapShotPojo>();

		if (currentDate) {
			List<Object[]> snapMonitor = transMoniRepositry.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotPojo snapShotPojo = new SnapShotPojo();
				snapShotPojo.setBank_code((String) sanpValue[0]);
				snapShotPojo.setBank_name((String) sanpValue[1]);
				snapShotPojo.setOut_no_of_txs((String) sanpValue[2].toString());
				snapShotPojo.setOut_tot_amt((String) sanpValue[3].toString());
				snapShotPojo.setIn_no_of_txs((String) sanpValue[4].toString());
				snapShotPojo.setIn_tot_amt((String) sanpValue[5].toString());
				snapShotPojoList.add(snapShotPojo);
			}
		} else {

			List<Object[]> snapMonitor = tranHistMonitorRep.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotPojo snapShotPojo = new SnapShotPojo();
				snapShotPojo.setBank_code((String) sanpValue[0]);
				snapShotPojo.setBank_name((String) sanpValue[1]);
				snapShotPojo.setOut_no_of_txs((String) sanpValue[2].toString());
				snapShotPojo.setOut_tot_amt((String) sanpValue[3].toString());
				snapShotPojo.setIn_no_of_txs((String) sanpValue[4].toString());
				snapShotPojo.setIn_tot_amt((String) sanpValue[5].toString());
				snapShotPojoList.add(snapShotPojo);

			}

		}

		return snapShotPojoList;

	}

	public List<SnapShotElement> getSnapShotChartOut(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<SnapShotElement> snapShotPojoList = new ArrayList<SnapShotElement>();

		if (currentDate) {
			List<Object[]> snapMonitor = transMoniRepositry.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotElement snapShotPojo = new SnapShotElement();
				snapShotPojo.setLabel((String) sanpValue[0]);
				snapShotPojo.setY(Integer.parseInt(sanpValue[2].toString()));
				snapShotPojoList.add(snapShotPojo);
			}
		} else {

			List<Object[]> snapMonitor = tranHistMonitorRep.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotElement snapShotPojo = new SnapShotElement();
				snapShotPojo.setLabel((String) sanpValue[0]);
				int count = Integer.parseInt(sanpValue[2].toString()) + Integer.parseInt(sanpValue[4].toString());
				snapShotPojo.setY(Integer.parseInt(sanpValue[2].toString()));
				snapShotPojoList.add(snapShotPojo);

			}

		}

		return snapShotPojoList;

	}

	public List<SnapShotElement> getSnapShotChartIn(String tranDate) throws ParseException {
		boolean currentDate = false;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);

		if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
			currentDate = true;
		} else {
			currentDate = false;
		}

		List<SnapShotElement> snapShotPojoList = new ArrayList<SnapShotElement>();

		if (currentDate) {
			List<Object[]> snapMonitor = transMoniRepositry.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotElement snapShotPojo = new SnapShotElement();
				snapShotPojo.setLabel((String) sanpValue[0]);
				snapShotPojo.setY(Integer.parseInt(sanpValue[4].toString()));
				snapShotPojoList.add(snapShotPojo);
			}
		} else {

			List<Object[]> snapMonitor = tranHistMonitorRep.snapShotRecord(tranDate);
			for (Object[] sanpValue : snapMonitor) {
				SnapShotElement snapShotPojo = new SnapShotElement();
				snapShotPojo.setLabel((String) sanpValue[0]);
				int count = Integer.parseInt(sanpValue[2].toString()) + Integer.parseInt(sanpValue[4].toString());
				snapShotPojo.setY(Integer.parseInt(sanpValue[4].toString()));
				snapShotPojoList.add(snapShotPojo);

			}

		}

		return snapShotPojoList;

	}

	public String getSettlOutwardRecord1(String tranDate) throws ParseException {
		System.out.println("Kalai" + tranDate);
		String amt = transMoniRepositry.countSettlBOMOutward1(tranDate);
		if (amt != null) {
			return String.valueOf(amt);
		} else {
			return "0";
		}
	}

	public Object getSettlInwardRecord1(String tranDate) throws ParseException {
		String amt = transMoniRepositry.countSettlBOMInward1(tranDate);
		if (amt != null) {
			return String.valueOf(amt);
		} else {
			return "0";
		}

	}

	public List<TranCimCBSTable> getTranMonitorCBSList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			List<TranCimCBSTable> tranMonitor = trancimCBSTableRep.findByCustomList(tranDate);
			return tranMonitor;
		} else {
			tranDate = date;
			List<TranCimCBSTable> tranMonitor = trancimCBSTableRep.findByCustomList(tranDate);
			return tranMonitor;
		}

	}
	
	public List<TranIPSTable> getTranMonitorIPSList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			List<TranIPSTable> tranMonitor = tranIPSTableRep.findByCustomList(tranDate);
			return tranMonitor;
		} else {
			tranDate = date;
			List<TranIPSTable> tranMonitor = tranIPSTableRep.findByCustomList(tranDate);
			return tranMonitor;
		}

	}

	public List<AuditTablePojo> getAuditInquries(Date date1, Date date2) {
		List<IPSAuditTable> auditList = iPSAuditTableRep.getauditListOpeartion(date1, date2);
		List<AuditTablePojo> auditPojoList = new ArrayList<>();
		for (IPSAuditTable ipsAudit : auditList) {
			AuditTablePojo auditTablePojo = new AuditTablePojo();
			auditTablePojo.setAudit_date(ipsAudit.getAudit_date());
			auditTablePojo.setAudit_table(ipsAudit.getAudit_table());
			auditTablePojo.setFunc_code(ipsAudit.getFunc_code());
			auditTablePojo.setEntry_user(ipsAudit.getEntry_user());
			auditTablePojo.setEntry_time(ipsAudit.getEntry_time());
			auditTablePojo.setAuth_user(ipsAudit.getAuth_user());
			auditTablePojo.setRemarks(ipsAudit.getRemarks());

			List<String> fieldName = new ArrayList<String>();
			List<String> oldvalue = new ArrayList<String>();
			List<String> newvalue = new ArrayList<String>();
			String[] dd = ipsAudit.getModi_details().split("\\|\\|");

			System.out.println("okk" + dd.length);
			for (String str : dd) {
				String[] str1 = str.split("\\+");
				if (str1.length > 0) {
					fieldName.add(str1[0]);
				}

				if (str1.length > 1) {
					oldvalue.add(str1[1]);
				}

				if (str1.length > 2) {
					newvalue.add(str1[2]);
				}
			}

			auditTablePojo.setFieldName(fieldName);
			auditTablePojo.setOldvalue(oldvalue);
			auditTablePojo.setNewvalue(newvalue);
			auditPojoList.add(auditTablePojo);
		}

		return auditPojoList;
	}

	public List<AuditTablePojo> getauditListLocal(Date fromdate1, Date todate1) {
		List<IPSAuditTable> auditList = iPSAuditTableRep.getauditListLocal(fromdate1, todate1);
		List<AuditTablePojo> auditPojoList = new ArrayList<>();
		for (IPSAuditTable ipsAudit : auditList) {
			AuditTablePojo auditTablePojo = new AuditTablePojo();
			auditTablePojo.setAudit_date(ipsAudit.getAudit_date());
			auditTablePojo.setAudit_table(ipsAudit.getAudit_table());
			auditTablePojo.setFunc_code(ipsAudit.getFunc_code());
			auditTablePojo.setEntry_user(ipsAudit.getEntry_user());
			auditTablePojo.setEntry_time(ipsAudit.getEntry_time());
			auditTablePojo.setAuth_user(ipsAudit.getAuth_user());
			auditTablePojo.setRemarks(ipsAudit.getRemarks());

			List<String> fieldName = new ArrayList<String>();
			List<String> oldvalue = new ArrayList<String>();
			List<String> newvalue = new ArrayList<String>();
			String[] dd = ipsAudit.getModi_details().split("\\|\\|");

			System.out.println("okk" + dd.length);
			for (String str : dd) {
				String[] str1 = str.split("\\+");
				if (str1.length > 0) {
					fieldName.add(str1[0]);
				}

				if (str1.length > 1) {
					oldvalue.add(str1[1]);
				}

				if (str1.length > 2) {
					newvalue.add(str1[2]);
				}
			}

			auditTablePojo.setFieldName(fieldName);
			auditTablePojo.setOldvalue(oldvalue);
			auditTablePojo.setNewvalue(newvalue);
			auditPojoList.add(auditTablePojo);
		}

		return auditPojoList;
	}

	public File getMconnectFile(String date,String reason,String status, String filetype, String type)
			throws FileNotFoundException, JRException, SQLException, ParseException {

		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		
		
		    
		String fileName = "";
		String Title = "";
		File outputFile;
		
		List<TransationMonitorPojo> list=new ArrayList<>();
		String totTr="";
		String sucTr="";
		String failTr="";
		String totAmt="";
		String sucAmt="";
		String failAmt="";
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
			
		
			

		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		if(currentDate) {
			if (type.equals("MC")) {
				totTr=RTPoutgoingRep.outwardMCListTotTxs(tranDate);
				sucTr= RTPoutgoingRep.outwardMCLisSucTxs(tranDate);
				failTr= RTPoutgoingRep.outwardMCListFailTxs(tranDate);
				totAmt= RTPoutgoingRep.outwardMCListTotAmt(tranDate);
				sucAmt= RTPoutgoingRep.outwardMCLisSucAmt(tranDate);
				failAmt=RTPoutgoingRep.outwardMCListFailAmt(tranDate);
				
			} else if (type.equals("INC")) {
				totTr=transMoniRepositry.outwardINCListTotTxs(tranDate);
				sucTr= transMoniRepositry.outwardINCLisSucTxs(tranDate);
				failTr= transMoniRepositry.outwardINCListFailTxs(tranDate);
				totAmt= transMoniRepositry.outwardINCListTotAmt(tranDate);
				sucAmt= transMoniRepositry.outwardINCLisSucAmt(tranDate);
				failAmt=transMoniRepositry.outwardINCListFailAmt(tranDate);
			} else if (type.equals("RTP")) {
				totTr=transMoniRepositry.outwardRTPListTotTxs(tranDate);
				sucTr= transMoniRepositry.outwardRTPLisSucTxs(tranDate);
				failTr= transMoniRepositry.outwardRTPListFailTxs(tranDate);
				totAmt= transMoniRepositry.outwardRTPListTotAmt(tranDate);
				sucAmt= transMoniRepositry.outwardRTPLisSucAmt(tranDate);
				failAmt=transMoniRepositry.outwardRTPListFailAmt(tranDate);
			} else if (type.equals("RTP-OUT")) {
				totTr=RTPoutgoingRep.outwardOUTListTotTxs(tranDate);
				sucTr= RTPoutgoingRep.outwardOUTLisSucTxs(tranDate);
				failTr= RTPoutgoingRep.outwardOUTListFailTxs(tranDate);
				totAmt= RTPoutgoingRep.outwardOUTListTotAmt(tranDate);
				sucAmt= RTPoutgoingRep.outwardOUTLisSucAmt(tranDate);
				failAmt=RTPoutgoingRep.outwardOUTListFailAmt(tranDate);
			}
			 else if (type.equals("BULK")) {
				
				}
			
		}else {
			if (type.equals("MC")) {
				totTr=RTPoutgoingHistRep.outwardMCListTotTxs(tranDate);
				sucTr= RTPoutgoingHistRep.outwardMCLisSucTxs(tranDate);
				failTr= RTPoutgoingHistRep.outwardMCListFailTxs(tranDate);
				totAmt= RTPoutgoingHistRep.outwardMCListTotAmt(tranDate);
				sucAmt= RTPoutgoingHistRep.outwardMCLisSucAmt(tranDate);
				failAmt=RTPoutgoingHistRep.outwardMCListFailAmt(tranDate);
				
			} else if (type.equals("INC")) {
				totTr=tranHistMonitorRep.outwardINCListTotTxs(tranDate);
				sucTr= tranHistMonitorRep.outwardINCLisSucTxs(tranDate);
				failTr= tranHistMonitorRep.outwardINCListFailTxs(tranDate);
				totAmt= tranHistMonitorRep.outwardINCListTotAmt(tranDate);
				sucAmt= tranHistMonitorRep.outwardINCLisSucAmt(tranDate);
				failAmt=tranHistMonitorRep.outwardINCListFailAmt(tranDate);
			} else if (type.equals("RTP")) {
				totTr=tranHistMonitorRep.outwardRTPListTotTxs(tranDate);
				sucTr= tranHistMonitorRep.outwardRTPLisSucTxs(tranDate);
				failTr= tranHistMonitorRep.outwardRTPListFailTxs(tranDate);
				totAmt= tranHistMonitorRep.outwardRTPListTotAmt(tranDate);
				sucAmt= tranHistMonitorRep.outwardRTPLisSucAmt(tranDate);
				failAmt=tranHistMonitorRep.outwardRTPListFailAmt(tranDate);
			}else if (type.equals("RTP-OUT")) {
				totTr=RTPoutgoingHistRep.outwardOUTListTotTxs(tranDate);
				sucTr= RTPoutgoingHistRep.outwardOUTLisSucTxs(tranDate);
				failTr= RTPoutgoingHistRep.outwardOUTListFailTxs(tranDate);
				totAmt= RTPoutgoingHistRep.outwardOUTListTotAmt(tranDate);
				sucAmt= RTPoutgoingHistRep.outwardOUTLisSucAmt(tranDate);
				failAmt=RTPoutgoingHistRep.outwardOUTListFailAmt(tranDate);
			}
			 else if (type.equals("BULK")) {
				
				}
			
		}

		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		
		
		try {
			InputStream jasperFile = null;

			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			
			
			if (type.equals("MC")) {
				fileName = "TransactionMonitor-MConnect"+curDate;
				Title = "TransactionMonitor-MConnect";
				jasperFile = this.getClass().getResourceAsStream(
						"/static/Jasper/IPS_Monitoring_Reports/MonitorMC.jrxml");
				list=getTranMonitorMCList(tranDate);
			} else if (type.equals("INC")) {
				fileName = "TransactionMonitor-Inward"+curDate;
				Title = "TransactionMonitor-Inward";
				jasperFile = this.getClass().getResourceAsStream(
						"/static/Jasper/IPS_Monitoring_Reports/MonitorINC.jrxml");
				list=getTranMonitorINCList(tranDate);
			} else if (type.equals("RTP")) {
				fileName = "TransactionMonitor-RTP"+curDate;
				Title = "TransactionMonitor-RTP";
				list=getTranMonitorRTPList(tranDate);
			}else if (type.equals("RTP-OUT")) {
				fileName = "TransactionMonitor-RTP"+curDate;
				Title = "TransactionMonitor-RTP";
				jasperFile = this.getClass().getResourceAsStream(
						"/static/Jasper/IPS_Monitoring_Reports/MonitorRTP-OUT.jrxml");
				list=getTranMonitoroutwardList(tranDate);
			}
			 else if (type.equals("BULK")) {
					fileName = "TransactionMonitor-Bulk"+curDate;
					Title = "Bulk Transaction Monitor";
					list=getTranMonitorBulkList(tranDate);
				}
			
			
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", Title);
			map.put("totTr", totTr);
			map.put("sucTr", sucTr);
			map.put("failTr", failTr);
			map.put("totAmt", String.valueOf(format1.format(new BigDecimal(totAmt))));
			map.put("sucAmt",String.valueOf( format1.format(new BigDecimal(sucAmt))));
			map.put("failAmt",String.valueOf(format1.format(new BigDecimal( failAmt))));
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;

	}
	
	public File getBOMSettlRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<SettlementReport> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		String totTr="";
		String totAmt="";
		
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");

		
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date enteredDate = dateFormat.parse(tranDate);
		String tranDate1=new SimpleDateFormat("yyyy-MM-dd").format(enteredDate);
		
		totTr= settelementReportRep.getSettleCurrentReport1TotTr(tranDate1);
		totAmt=settelementReportRep.getSettleCurrentReport1TotAmt(tranDate1);

		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "BOMSettlementReport"+curDate;
			list=getSettlementReport(tranDate);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/BOMSettlRptMonitor.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "BOM Settlement Report");
			map.put("totTr",totTr);
			map.put("totAmt", String.valueOf(format1.format(new BigDecimal(totAmt))));
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}
	
	public File getCBSRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<TranCimCBSTable> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		 
		/* String totPayAmt=tranCBSTableRep.findByCustomPayAmt(tranDate);
		 String totRecAmt=tranCBSTableRep.findByCustomRecAmt(tranDate);
		 String totSettPayAmt=tranCBSTableRep.findByCustomSettlPayAmt(tranDate);
		 String totSettRecAmt=tranCBSTableRep.findByCustomSettlRecAmt(tranDate);*/
		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "CBSMonitor"+curDate;
			list=getTranMonitorCBSList(date);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/MonitorCBS.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "CBS Monitor");
			/*map.put("totPayAmt", String.valueOf(format1.format(new BigDecimal(totPayAmt))));
			map.put("totRecAmt", String.valueOf(format1.format(new BigDecimal(totRecAmt))));
			map.put("totSettPayAmt", String.valueOf(format1.format(new BigDecimal(totSettPayAmt))));
			map.put("totSettRecAmt", String.valueOf(format1.format(new BigDecimal(totSettRecAmt))));*/
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}
	
	public File getIPSRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<TranIPSTable> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		 
		/* String totPayAmt=tranCBSTableRep.findByCustomPayAmt(tranDate);
		 String totRecAmt=tranCBSTableRep.findByCustomRecAmt(tranDate);
		 String totSettPayAmt=tranCBSTableRep.findByCustomSettlPayAmt(tranDate);
		 String totSettRecAmt=tranCBSTableRep.findByCustomSettlRecAmt(tranDate);*/
		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "IPSMonitor"+curDate;
			list=getTranMonitorIPSList(date);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/MonitorIPS.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "IPS Monitor");
			/*map.put("totPayAmt", String.valueOf(format1.format(new BigDecimal(totPayAmt))));
			map.put("totRecAmt", String.valueOf(format1.format(new BigDecimal(totRecAmt))));
			map.put("totSettPayAmt", String.valueOf(format1.format(new BigDecimal(totSettPayAmt))));
			map.put("totSettRecAmt", String.valueOf(format1.format(new BigDecimal(totSettRecAmt))));*/
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}

	public File getConsentAccessRegRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<Object[]> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		 
		/* String totPayAmt=tranCBSTableRep.findByCustomPayAmt(tranDate);
		 String totRecAmt=tranCBSTableRep.findByCustomRecAmt(tranDate);
		 String totSettPayAmt=tranCBSTableRep.findByCustomSettlPayAmt(tranDate);
		 String totSettRecAmt=tranCBSTableRep.findByCustomSettlRecAmt(tranDate);*/
		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "ConsentAccessReg"+curDate;
			list=AccessRoleService.getdetail(date);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/ConsentAccessReg.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "Consent Registration");
			/*map.put("totPayAmt", String.valueOf(format1.format(new BigDecimal(totPayAmt))));
			map.put("totRecAmt", String.valueOf(format1.format(new BigDecimal(totRecAmt))));
			map.put("totSettPayAmt", String.valueOf(format1.format(new BigDecimal(totSettPayAmt))));
			map.put("totSettRecAmt", String.valueOf(format1.format(new BigDecimal(totSettRecAmt))));*/
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}


	public File getConsentAISPRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<Object[]> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		 
		/* String totPayAmt=tranCBSTableRep.findByCustomPayAmt(tranDate);
		 String totRecAmt=tranCBSTableRep.findByCustomRecAmt(tranDate);
		 String totSettPayAmt=tranCBSTableRep.findByCustomSettlPayAmt(tranDate);
		 String totSettRecAmt=tranCBSTableRep.findByCustomSettlRecAmt(tranDate);*/
		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "ConsentAISP"+curDate;
			list=AccessRoleService.getdetailCoI(date);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/AISP_Request.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "AISP Request");
			/*map.put("totPayAmt", String.valueOf(format1.format(new BigDecimal(totPayAmt))));
			map.put("totRecAmt", String.valueOf(format1.format(new BigDecimal(totRecAmt))));
			map.put("totSettPayAmt", String.valueOf(format1.format(new BigDecimal(totSettPayAmt))));
			map.put("totSettRecAmt", String.valueOf(format1.format(new BigDecimal(totSettRecAmt))));*/
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}

	
	public File getConsentFeesRptFile(String date, String filetype, String type) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		File outputFile;
		
		List<ParticipantFeesTable> list=new ArrayList<>();
		
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		
		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		 
		/* String totPayAmt=tranCBSTableRep.findByCustomPayAmt(tranDate);
		 String totRecAmt=tranCBSTableRep.findByCustomRecAmt(tranDate);
		 String totSettPayAmt=tranCBSTableRep.findByCustomSettlPayAmt(tranDate);
		 String totSettRecAmt=tranCBSTableRep.findByCustomSettlRecAmt(tranDate);*/
		try {
			InputStream jasperFile = null;
			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "ParticipantFees"+curDate;
			list=AccessRoleService.getdetailParticipantFees(date);
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/ParticipantFees.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", "Participant Fees");
			/*map.put("totPayAmt", String.valueOf(format1.format(new BigDecimal(totPayAmt))));
			map.put("totRecAmt", String.valueOf(format1.format(new BigDecimal(totRecAmt))));
			map.put("totSettPayAmt", String.valueOf(format1.format(new BigDecimal(totSettPayAmt))));
			map.put("totSettRecAmt", String.valueOf(format1.format(new BigDecimal(totSettRecAmt))));*/
			

			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map, dataSrc);
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;
	}


	public List<TransationMonitorPojo> findAllCustomBulkCredit(String sequenceID, String date)
			throws ParseException {

		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if(currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.findAllCustomBulkCredit(sequenceID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.findAllCustomBulkCredit(sequenceID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		

		return transationMonitorPojoList;

	}

	public List<TransationMonitorPojo> findAllCustomBulkDebit(String seqUniqueID, String date) throws ParseException {
		
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if(currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.findAllCustomBulkDebit(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.findAllCustomBulkDebit(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		

		return transationMonitorPojoList;
	}

	public List<TransationMonitorPojo> findAllCustomBulkManual(String seqUniqueID, String date) throws ParseException {
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}

		if(currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.findAllCustomBulkManual(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					transationMonitorPojo.setBenBank(bankAgentTableRep.findByIdCustomAgent(tranMonitor.get(i).getCdtr_agt()).getBank_name());
				}

				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());
					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.findAllCustomBulkManual(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					transationMonitorPojo.setBenBank(bankAgentTableRep.findByIdCustomAgent(tranMonitor.get(i).getCdtr_agt()).getBank_name());
				}
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {
					transationMonitorPojo.setCbs_status(tranMonitor.get(i).getCbs_status());

					if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
							|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
					} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
						transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
					} else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());

				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		

		return transationMonitorPojoList;
	}
	
	public List<TransationMonitorPojo> findAllCustomBulkRTP(String seqUniqueID, String date) throws ParseException {
		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();
		
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);
			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}

		if(currentDate) {
			List<RTP_Outgoing_entity> tranMonitor = RTPoutgoingRep.findAllCustomBulkRTP(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount( tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				if(tranMonitor.get(i).getDbtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getDbtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setRemBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setRemBank("");
					}
				}else {
					transationMonitorPojo.setRemBank("");
				}
				
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if(tranMonitor.get(i).getCbs_status()!=null) {
						if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);

			}
		}else {
			List<RTP_Outgoing_hist_entity> tranMonitor = RTPoutgoingHistRep.findAllCustomBulkRTP(seqUniqueID, tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount( tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if(tranMonitor.get(i).getCbs_status()!=null) {
						if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		}
		

		return transationMonitorPojoList;
	}


	public  List<RegPublicKeyTmp> getMytUserList(String tranDate) {
		 List<Object[]> listReg=regPublicKeyRep.findAllCustom(tranDate);
		 
		 List<Object[]> listRegtmp=regPublicKeyRep.findAllCustom2(tranDate);
		 List<RegPublicKeyTmp> listRegTot=new ArrayList<RegPublicKeyTmp>();
		 for(Object[] obj:listRegtmp) {
			 RegPublicKeyTmp regPublicKey=new RegPublicKeyTmp();
			 regPublicKey.setDevice_id((String) obj[0]);
			 regPublicKey.setIp_address((String) obj[1]);
			 regPublicKey.setNational_id((String) obj[2]);
			 regPublicKey.setPhone_number((String) obj[3]);
			 regPublicKey.setAcct_number((String) obj[4]);
			 regPublicKey.setEntry_time((Date) obj[5]);
			 if((String) obj[6]!=null) {
				 regPublicKey.setAccount_status((String) obj[6]);
			 }else {
				 regPublicKey.setAccount_status("Waiting for OTP Verification");
			 }
			 
			 listRegTot.add(regPublicKey);
			 
		 }
		 
		 for(Object[] obj:listReg) {
			 RegPublicKeyTmp regPublicKey=new RegPublicKeyTmp();
			 regPublicKey.setDevice_id((String) obj[0]);
			 regPublicKey.setIp_address((String) obj[1]);
			 regPublicKey.setNational_id((String) obj[2]);
			 regPublicKey.setPhone_number((String) obj[3]);
			 regPublicKey.setAcct_number((String) obj[4]);
			 regPublicKey.setEntry_time((Date) obj[5]);

			if (obj[6].toString().equals("Y")) {
				regPublicKey.setAccount_status("Deleted");
			} else {
				regPublicKey.setAccount_status("Completed");
			}
			
			
			 
			
			 
			 listRegTot.add(regPublicKey);
			 
		 }
		 
		 Collections.sort(listRegTot, new Comparator<RegPublicKeyTmp>() {

			@Override
			public int compare(RegPublicKeyTmp o1, RegPublicKeyTmp o2) {
				 return o1.getEntry_time().compareTo(o2.getEntry_time());
			}
		});
			 
		 return listRegTot;
	
	}

	public void getDemo() {
		
		boolean response = false;
		Session session = sessionFactory.getCurrentSession();

		
		Query query=session.createNativeQuery("select * from bips_purpose_code");
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=query.list();
		
		//System.out.println(aliasToValueMapList);

		
			List<JSONObject> jsonObj = new ArrayList<JSONObject>();

			for(Map<String, Object> data : aliasToValueMapList) {
			    JSONObject obj = new JSONObject(data);
			    jsonObj.add(obj);
			}

			JSONArray test = new JSONArray(jsonObj);

			System.out.println(test.toString());
		}

	public File getBOMReconciliationRptFile(String tranDate, String filetype) throws ParseException {

		boolean currentDate = false;
		String path = env.getProperty("output.exportpath");

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		String fileName = "";
		String Title = "";
		File outputFile;
		
		
		
		String inwBOBAmt= getReconciliationInwardRecord(tranDate);
		String outBOBAmt=getReconciliationOutwardRecord(tranDate);
		String offBOBAmt="0";
		String unBOBAmt=getReconciliationUnmatchRecord(tranDate);
		String inwBOMAmt=getSettlInwardRecord(tranDate);
		String outBOMAmt=getSettlOutwardRecord(tranDate);
		String offBOMAmt= "0";
		String unBOMAmt= "0";
		String failBOBAmt="0";
		String failBOMAmt="0";

		String inwBOBTxs=getReconciliationInwardRecordNoTxs(tranDate);
		String outBOBTxs=getReconciliationOutwardRecordNoTxs(tranDate);
		String offBOBTxs=getReconciliationOffesetRecordNoTxs(tranDate);
		String unBOBTxs=getReconciliationUnmatchRecordNoTxs(tranDate);
		String inwBOMTxs=getSettlInwardRecordNoTxs(tranDate);
		String outBOMTxs=getSettlOutwardRecordNoTxs(tranDate);
		String failBOBTxs=getReconciliationfailedRecordNoTxs(tranDate);
		String offBOMTxs="0";
		String unBOMTxs= "0";
		String failBOMTxs="0";

		// md.addAttribute("totalBOBNoTxs",
		// monitorService.getReconciliationBOBTotRecordNoTxs(tranDate));
		String totBOBTxs=
				String.valueOf(Integer.parseInt(getReconciliationInwardRecordNoTxs(tranDate))
						+ Integer.parseInt(getReconciliationOutwardRecordNoTxs(tranDate))
						+ Integer.parseInt(getReconciliationUnmatchRecordNoTxs(tranDate)));

		String totBOMTxs=getSettleBOBTotRecordNoTxs(tranDate);

		String totBOBAmt=String.valueOf(
				Double.parseDouble(getReconciliationInwardRecord(tranDate))
						+ Double.parseDouble(getReconciliationOutwardRecord(tranDate))
						+ Double.parseDouble(getReconciliationUnmatchRecord(tranDate)));
		
		String totBOMAmt=String.valueOf(Double.parseDouble(getSettlInwardRecord(tranDate))
				+ Double.parseDouble(getSettlOutwardRecord(tranDate)) + Double.parseDouble("0"));

		 DecimalFormat format1 = new DecimalFormat("###,###.##");
		
		
		try {
			InputStream jasperFile = null;

			String curDate=new SimpleDateFormat("ddMMyyHHmm").format(new Date());
			fileName = "IPS Reconciliation"+curDate;
			Title = "IPS Reconciliation";
			
			
			
			jasperFile = this.getClass().getResourceAsStream(
					"/static/Jasper/IPS_Monitoring_Reports/IPSReconciliation1.jrxml");
			
			JasperReport jr =JasperCompileManager.compileReport(jasperFile);
			//JasperReport jr = (JasperReport) JRLoader.loadObject(jasperFile);
			HashMap<String, Object> map = new HashMap<String, Object>();

			System.out.println(tranDate);
			//map.put("tran_date1", tranDate);
			map.put("Title", Title);
			map.put("tranDate", tranDate);
			map.put("inwBOBTxs", inwBOBTxs);
			map.put("inwBOMTxs", inwBOMTxs);
			map.put("outBOBTxs", outBOBTxs);
			map.put("outBOMTxs", outBOMTxs);
			map.put("offBOBTxs", offBOBTxs);
			map.put("offBOMTxs", offBOMTxs);
			map.put("unBOBTxs", unBOBTxs);
			map.put("unBOMTxs", unBOMTxs);
			map.put("failBOBTxs", failBOBTxs);
			map.put("failBOMTxs", failBOMTxs);
			
			map.put("inwBOBAmt", String.valueOf(format1.format(new BigDecimal (inwBOBAmt))));
			map.put("inwBOMAmt", String.valueOf(format1.format(new BigDecimal (inwBOMAmt))));
			map.put("outBOBAmt", String.valueOf(format1.format(new BigDecimal (outBOBAmt))));
			map.put("outBOMAmt", String.valueOf(format1.format(new BigDecimal (outBOMAmt))));
			map.put("offBOBAmt", String.valueOf(format1.format(new BigDecimal (offBOBAmt))));
			map.put("offBOMAmt", String.valueOf(format1.format(new BigDecimal (offBOMAmt))));
			map.put("unBOBAmt", String.valueOf(format1.format(new BigDecimal (unBOBAmt))));
			map.put("unBOMAmt", String.valueOf(format1.format(new BigDecimal (unBOMAmt))));
			map.put("failBOBAmt", String.valueOf(format1.format(new BigDecimal (failBOBAmt))));
			map.put("failBOMAmt", String.valueOf(format1.format(new BigDecimal (failBOMAmt))));
			
			map.put("totBOBTxs", totBOBTxs);
			map.put("totBOBAmt", String.valueOf(format1.format(new BigDecimal (totBOBAmt))));
			map.put("totBOMTxs",totBOMTxs);
			map.put("totBOMAmt", String.valueOf(format1.format(new BigDecimal (totBOMAmt))));
			
			
			List list=new ArrayList<>();
			if (filetype.equals("pdf")) {
				fileName = fileName + ".pdf";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				
				JasperPrint jp = JasperFillManager.fillReport(jr, map,new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jp, path);
				logger.info("PDF File exported");
			} else {
				fileName = fileName + ".xlsx";
				path = path+"/"+fileName;
				JRBeanCollectionDataSource dataSrc=new JRBeanCollectionDataSource(list);
				JasperPrint jp = JasperFillManager.fillReport(jr, map,new JREmptyDataSource());
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jp));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
				exporter.exportReport();
			
				logger.info("Excel File exported");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		outputFile = new File(path);

		return outputFile;


	}

	public List<RegPublicKeyTmp> getMytUserListMonthly(String tranDate) {
 List<Object[]> listReg=regPublicKeyRep.findAllCustomMonthly(tranDate);
		 
		 List<Object[]> listRegtmp=regPublicKeyRep.findAllCustomMonthly1(tranDate);
		 List<RegPublicKeyTmp> listRegTot=new ArrayList<RegPublicKeyTmp>();
		 for(Object[] obj:listRegtmp) {
			 RegPublicKeyTmp regPublicKey=new RegPublicKeyTmp();
			 regPublicKey.setDevice_id((String) obj[0]);
			 regPublicKey.setIp_address((String) obj[1]);
			 regPublicKey.setNational_id((String) obj[2]);
			 regPublicKey.setPhone_number((String) obj[3]);
			 regPublicKey.setAcct_number((String) obj[4]);
			 regPublicKey.setEntry_time((Date) obj[5]);
			 if((String) obj[6]!=null) {
				 regPublicKey.setAccount_status((String) obj[6]);
			 }else {
				 regPublicKey.setAccount_status("Waiting for OTP Verification");
			 }
			 
			 listRegTot.add(regPublicKey);
			 
		 }
		 
		 for(Object[] obj:listReg) {
			 RegPublicKeyTmp regPublicKey=new RegPublicKeyTmp();
			 regPublicKey.setDevice_id((String) obj[0]);
			 regPublicKey.setIp_address((String) obj[1]);
			 regPublicKey.setNational_id((String) obj[2]);
			 regPublicKey.setPhone_number((String) obj[3]);
			 regPublicKey.setAcct_number((String) obj[4]);
			 regPublicKey.setEntry_time((Date) obj[5]);
			 
			 if(obj[6].toString()!=null) {
				 if(obj[6].toString().equals("Y")) {
					 regPublicKey.setAccount_status("Deleted");
				 }else {
					 regPublicKey.setAccount_status("Completed");
				 }
			 }else {
				 regPublicKey.setAccount_status("Completed");
			 }
			 
			 listRegTot.add(regPublicKey);
			 
		 }
		 
		 Collections.sort(listRegTot, new Comparator<RegPublicKeyTmp>() {

			@Override
			public int compare(RegPublicKeyTmp o1, RegPublicKeyTmp o2) {
				 return o1.getEntry_time().compareTo(o2.getEntry_time());
			}
		});
			 
		 return listRegTot;
	

	}
		
 

	
	public List<TransationMonitorPojo> getTranMonitoroutwardList(String date) throws ParseException {
		String tranDate = "";
		boolean currentDate = false;
		if (date == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			tranDate = dateFormat.format(new Date());
			currentDate = true;
		} else {
			tranDate = date;
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date enteredDate = dateFormat.parse(date);

			if (dateFormat.format(enteredDate).equals(dateFormat.format(new Date()))) {
				currentDate = true;
			} else {
				currentDate = false;
			}
		}

		List<TransationMonitorPojo> transationMonitorPojoList = new ArrayList<TransationMonitorPojo>();

		if (currentDate) {
			List<Object[]> tranMonitor = RTPoutgoingRep.findAllCustomoutwardrtp1(tranDate);
			for (int i = 0; i < tranMonitor.size(); i++) {
				/*TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount( tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());
				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}
				transationMonitorPojo.setBenBank(tranMonitor.get(i).getCdtr_agt());
				if(tranMonitor.get(i).getDbtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getDbtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setRemBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setRemBank("");
					}
				}else {
					transationMonitorPojo.setRemBank("");
				}
				transationMonitorPojo.setRemBank(tranMonitor.get(i).getDbtr_agt());
				transationMonitorPojo.setChannel(tranMonitor.get(i).getInit_channel_id());
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if(tranMonitor.get(i).getCbs_status()!=null) {
						if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							if(tranMonitor.get(i).getIpsx_status_error() != (null)) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}else {
								transationMonitorPojo.setReason("");
							}
							

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);*/
				
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i)[0].toString());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i)[1].toString());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i)[2].toString());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i)[3].toString());
				transationMonitorPojo.setTran_date((Date) tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date) tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String) tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount( (BigDecimal) tranMonitor.get(i)[9]);
				transationMonitorPojo.setEntry_user((String) tranMonitor.get(i)[10]);
				transationMonitorPojo.setMaster_ref_id((String) tranMonitor.get(i)[11]);
				
				transationMonitorPojo.setBenBank((String) tranMonitor.get(i)[12]);
				
				transationMonitorPojo.setRemBank((String) tranMonitor.get(i)[13]);
				transationMonitorPojo.setChannel((String) tranMonitor.get(i)[14]);
				transationMonitorPojo.setTran_currency((String) tranMonitor.get(i)[15]);
				if ( tranMonitor.get(i)[16].toString().equals("FAILURE")) {

					if(tranMonitor.get(i)[17]!=null) {
						if (tranMonitor.get(i)[17].toString().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i)[17].toString().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						} else if (tranMonitor.get(i)[17].toString().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						if (tranMonitor.get(i)[19] != (null)) {
							if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" +tranMonitor.get(i)[20].toString());
							}
						} else {
							if(tranMonitor.get(i)[20]!= (null)) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
							}else {
								transationMonitorPojo.setReason("");
							}
							

						}
					}

				} else if (tranMonitor.get(i)[16].toString().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				}

				if( tranMonitor.get(i)[21]!=null) {
					transationMonitorPojo.setReservedField1(tranMonitor.get(i)[21].toString());
				}else {
					transationMonitorPojo.setReservedField1("");
				}
				
				transationMonitorPojo.setTran_status(tranMonitor.get(i)[16].toString());

				transationMonitorPojoList.add(transationMonitorPojo);
			}
		} else {
			List<Object[]> tranMonitor = RTPoutgoingHistRep.findAllCustomoutwardrtp1(tranDate);

			for (int i = 0; i < tranMonitor.size(); i++) {
				/*TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i).getCim_account());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i).getCim_account_name());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i).getIpsx_account());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i).getIpsx_account_name());
				transationMonitorPojo.setTran_date(tranMonitor.get(i).getTran_date());
				transationMonitorPojo.setValue_date(tranMonitor.get(i).getValue_date());
				transationMonitorPojo.setSequence_unique_id(tranMonitor.get(i).getSequence_unique_id());
				transationMonitorPojo.setTran_audit_number(tranMonitor.get(i).getTran_audit_number());
				transationMonitorPojo.setMsg_type(tranMonitor.get(i).getMsg_type());
				transationMonitorPojo.setTran_amount(tranMonitor.get(i).getTran_amount());
				transationMonitorPojo.setMaster_ref_id(tranMonitor.get(i).getMaster_ref_id());

				if(tranMonitor.get(i).getCdtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getCdtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setBenBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setBenBank("");
					}
				}else {
					transationMonitorPojo.setBenBank("");
				}

				if(tranMonitor.get(i).getDbtr_agt()!=null) {
					List<BankAgentTable> tc=bankAgentTableRep.findByIdCustomAgent1(tranMonitor.get(i).getDbtr_agt());
					if(tc.size()>0) {
						transationMonitorPojo.setRemBank(tc.get(0).getBank_name());
					}else {
						transationMonitorPojo.setRemBank("");
					}
				}else {
					transationMonitorPojo.setRemBank("");
				}
				transationMonitorPojo.setChannel(tranMonitor.get(i).getInit_channel_id());
				
				transationMonitorPojo.setTran_currency(tranMonitor.get(i).getTran_currency());
				transationMonitorPojo.setEntry_user(tranMonitor.get(i).getEntry_user());

				
				if (tranMonitor.get(i).getTran_status().equals("FAILURE")) {

					if(tranMonitor.get(i).getCbs_status()!=null) {

						if (tranMonitor.get(i).getCbs_status().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i).getCbs_status().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						} else if (tranMonitor.get(i).getCbs_status().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i).getCbs_status_error());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						} 	
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
						if (tranMonitor.get(i).getResponse_status() != (null)) {
							if (tranMonitor.get(i).getResponse_status().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
							}
						} else {
							transationMonitorPojo.setReason("");

						}
					}

				} else if (tranMonitor.get(i).getTran_status().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i).getIpsx_status_error());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i).getTran_status());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i).getTran_status());

				transationMonitorPojoList.add(transationMonitorPojo);*/
				
				TransationMonitorPojo transationMonitorPojo = new TransationMonitorPojo();
				transationMonitorPojo.setBob_account(tranMonitor.get(i)[0].toString());
				transationMonitorPojo.setBob_account_name(tranMonitor.get(i)[1].toString());
				transationMonitorPojo.setIpsx_account(tranMonitor.get(i)[2].toString());
				transationMonitorPojo.setIpsx_account_name(tranMonitor.get(i)[3].toString());
				transationMonitorPojo.setTran_date((Date) tranMonitor.get(i)[4]);
				transationMonitorPojo.setValue_date((Date) tranMonitor.get(i)[5]);
				transationMonitorPojo.setSequence_unique_id((String) tranMonitor.get(i)[6]);
				transationMonitorPojo.setTran_audit_number((String) tranMonitor.get(i)[7]);
				transationMonitorPojo.setMsg_type((String) tranMonitor.get(i)[8]);
				transationMonitorPojo.setTran_amount( (BigDecimal) tranMonitor.get(i)[9]);
				transationMonitorPojo.setEntry_user((String) tranMonitor.get(i)[10]);
				transationMonitorPojo.setMaster_ref_id((String) tranMonitor.get(i)[11]);
				
				transationMonitorPojo.setBenBank((String) tranMonitor.get(i)[12]);
				
				transationMonitorPojo.setRemBank((String) tranMonitor.get(i)[13]);
				transationMonitorPojo.setChannel((String) tranMonitor.get(i)[14]);
				transationMonitorPojo.setTran_currency((String) tranMonitor.get(i)[15]);
				if ( tranMonitor.get(i)[16].toString().equals("FAILURE")) {

					if(tranMonitor.get(i)[17]!=null) {
						if (tranMonitor.get(i)[17].toString().equals("CBS_DEBIT_ERROR")
								|| tranMonitor.get(i)[17].toString().equals("CBS_CREDIT_ERROR")) {
							transationMonitorPojo.setReason("CBS  :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						} else if (tranMonitor.get(i)[17].toString().equals("VALIDATION_ERROR")) {
							transationMonitorPojo.setReason("BIPS :" + tranMonitor.get(i)[18].toString());
							transationMonitorPojo.setTranStatusSeq("VALIDATION_ERROR");
						}
					}else {
						transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
						if (tranMonitor.get(i)[19] != (null)) {
							if (tranMonitor.get(i)[19].toString().equals("RJCT")) {
								transationMonitorPojo.setReason("IPSX  :" +tranMonitor.get(i)[20].toString());
							}
						} else {
							if(tranMonitor.get(i)[20]!= (null)) {
								transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
							}else {
								transationMonitorPojo.setReason("");
							}
							

						}
					}

				} else if (tranMonitor.get(i)[16].toString().equals("REVERSE_FAILURE")) {
					transationMonitorPojo.setReason("IPSX  :" + tranMonitor.get(i)[20].toString());
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				} else {
					transationMonitorPojo.setReason("");
					transationMonitorPojo.setTranStatusSeq(tranMonitor.get(i)[16].toString());
				}

				transationMonitorPojo.setTran_status(tranMonitor.get(i)[16].toString());

				if( tranMonitor.get(i)[21]!=null) {
					transationMonitorPojo.setReservedField1(tranMonitor.get(i)[21].toString());
				}else {
					transationMonitorPojo.setReservedField1("");
				}
				transationMonitorPojoList.add(transationMonitorPojo);
			}

		}
		return transationMonitorPojoList;

	}

	public TranCimCBSTablePojo findTranCIMCBSTranAudit(String tranAuditNumber, String seqUniqueID) {
		
		TranCimCBSTable tranMonitorCBS =  tranCimCbsTableRep.findAllTranAudit(tranAuditNumber);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		BigDecimal tranAmount;
		if (dateFormat.format(tranMonitorCBS.getTran_date()).equals(dateFormat.format(new Date()))) {
			RTP_Outgoing_entity data=RTPoutgoingRep.findBySequenceUniqueID(seqUniqueID);
			tranAmount=data.getTran_amount();
		} else {
			RTP_Outgoing_hist_entity data=RTPoutgoingHistRep.findBySequenceUniqueID(seqUniqueID);
			tranAmount=data.getTran_amount();
		}
		
		TranCimCBSTablePojo tranCimCBSTablePojo=new TranCimCBSTablePojo(tranMonitorCBS);
		tranCimCBSTablePojo.setTran_amt(tranAmount);
		 
		return tranCimCBSTablePojo;
	}

}
