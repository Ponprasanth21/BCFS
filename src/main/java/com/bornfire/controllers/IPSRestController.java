package com.bornfire.controllers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.AccessandRolesRepository;
import com.bornfire.entity.BIPS_SWIFT_MSG_MGT;
import com.bornfire.entity.BIPS_SWIFT_MT_MSG;
import com.bornfire.entity.BIPS_SWIFT_MX_MSG;
import com.bornfire.entity.BankAgentTable;
import com.bornfire.entity.BankAndBranchBean;
import com.bornfire.entity.BipsSwiftMsgConversionRepo;
import com.bornfire.entity.BulkTransaction;
import com.bornfire.entity.BulkTransactionRepository;
import com.bornfire.entity.BusinessHours;
import com.bornfire.entity.IPSAuditTable;
import com.bornfire.entity.IPSChargesAndFees;
import com.bornfire.entity.IPSUserPreofileMod;
import com.bornfire.entity.IPSUserProfileModRep;
import com.bornfire.entity.LdapUserList;
import com.bornfire.entity.MCCreditTransferResponse;
import com.bornfire.entity.MandateMaster;
import com.bornfire.entity.ManualTransaction;
import com.bornfire.entity.ManualTransactionRepository;
import com.bornfire.entity.MerchantQRRegistration;
import com.bornfire.entity.MerchantQrCodeRegRep;
import com.bornfire.entity.RTPTransactionTable;
import com.bornfire.entity.RTPTransactionTableRep;
import com.bornfire.entity.SettlementAccount;

import com.bornfire.entity.TranCBSTable;
import com.bornfire.entity.TranCimCBSTable;
import com.bornfire.entity.TranIPSTable;
import com.bornfire.entity.TranIPStableRep;
import com.bornfire.entity.TransMoniRepositry;
import com.bornfire.entity.TransactionAdmin;
import com.bornfire.entity.TransationMonitorPojo;
import com.bornfire.entity.UserProfile;
import com.bornfire.entity.UserProfileRep;
import com.bornfire.entity.cimMerchantQRcodeResponse;
import com.bornfire.modal.MTtoMXresponse;
import com.bornfire.parser.AppHeader;
import com.bornfire.parser.DocumentPacks;
import com.bornfire.services.BIPSBankandBranchServices;
import com.bornfire.services.BankAndBranchMasterServices;
import com.bornfire.services.BulkServices;
import com.bornfire.services.IPSAccessRoleService;
import com.bornfire.services.IPSServices;
import com.bornfire.services.LoginSecurityServices;
import com.bornfire.services.MandateServices;
import com.bornfire.services.MonitorService;
import com.bornfire.services.SettlementAccountServices;
import com.bornfire.services.TransactionService;
import com.bornfire.services.UserProfileService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@RestController
public class IPSRestController {

	@Autowired
	TransactionService newOtherTransaction;

	@Autowired
	IPSServices ipsServices;

	@Autowired
	TransMoniRepositry tranMonitorRep;

	@Autowired
	BulkTransactionRepository bulkTranRep;

	@Autowired
	ManualTransactionRepository manualTranRep;

	@Autowired
	BulkServices bulkServices;

	@Autowired
	SettlementAccountServices settlementAccountServices;

	@Autowired
	BankAndBranchMasterServices bankAndBranchMasterServices;

	@Autowired
	IPSAccessRoleService AccessRoleService;

	@Autowired
	LoginSecurityServices loginSecurityServices;

	@Autowired
	MonitorService monitorService;

	@Autowired
	MandateServices mandateServices;

	@Autowired
	AccessandRolesRepository accessandRolesRepository;

	@Autowired
	MerchantQrCodeRegRep merchantQrCodeRegRep;

	@Autowired
	RTPTransactionTableRep rtpTransactionTableRep;

	@Autowired
	TranIPStableRep tranIPStableRep;

	@Autowired
	Environment env;

	@Autowired
	SwiftConnection swiftConnection;

	@Autowired
	BipsMsgConversionProcessRec bipsMsgConversionProcessRec;

	@Autowired
	BipsSwiftMsgConversionRepo bipsSwiftMsgConversionRepo;

	@Autowired
	UserProfileRep userProfileRep;

	@Autowired
	SequenceGenerator sequence;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	IPSUserProfileModRep iPSUserProfileModRep;

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "/getTransaction/{acct_num}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode13(@PathVariable("acct_num") String acct_num, Model md) {
		System.out.println(acct_num);
		return newOtherTransaction.getTransaction(acct_num);

	}

	@RequestMapping(value = "/getTransaction/", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode3(Model md) {

		return newOtherTransaction.getTransaction();

	}

	@RequestMapping(value = "/getTransaction/{acct_num}/{from_date}/{to_date}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode1(@PathVariable("from_date") String from_date,
			@PathVariable("to_date") String to_date, @PathVariable("acct_num") String acct_num, Model md) {
		System.out.println(acct_num);
		System.out.println(from_date);
		System.out.println(to_date);

		return newOtherTransaction.getTransactionDetail(from_date, to_date, acct_num);

	}

	@RequestMapping(value = "/getTransaction/{from_date}/{to_date}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode(@PathVariable("from_date") String from_date,
			@PathVariable("to_date") String to_date, Model md) {
		System.out.println(from_date);
		System.out.println(to_date);

		return newOtherTransaction.getTransactionDetail(from_date, to_date);

	}

	/************************************************
	 * FAILED WITHOUT ACCOUNT NUMBER
	 ********************************************************************/
	@RequestMapping(value = "/getTransactionFailed/", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode7(Model md) {
		System.out.println("kkkkkk");
		return newOtherTransaction.getTransactionFailed();

	}

	@RequestMapping(value = "/getTransactionFailed/{from_date}/{to_date}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode11(@PathVariable("from_date") String from_date,
			@PathVariable("to_date") String to_date, Model md) {
		System.out.println(from_date);
		System.out.println(to_date);

		return newOtherTransaction.getTransactionFailed(from_date, to_date);
	}

	/*****************************************
	 * WITH ACCOUNT NUMBER
	 ********************************************/

	@RequestMapping(value = "/getTransactionFailed/{acct_num}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode10(@PathVariable("acct_num") String acct_num, Model md) {
		System.out.println(acct_num);
		return newOtherTransaction.getTransactionFailed(acct_num);

	}

	@RequestMapping(value = "/getTransactionFailed/{acct_num}/{from_date}/{to_date}", method = RequestMethod.GET)
	public List<TransactionAdmin> refcode12(@PathVariable("from_date") String from_date,
			@PathVariable("to_date") String to_date, @PathVariable("acct_num") String acct_num, Model md) {
		System.out.println(from_date);
		System.out.println(to_date);

		return newOtherTransaction.getTransactionFailed(from_date, to_date, acct_num);
	}

	/*
	 * @RequestMapping(value = "/getTranList/", method = RequestMethod.GET) public
	 * List<BulkTransaction> tranList( Model md) {
	 * 
	 * return
	 * 
	 * }
	 */

	@RequestMapping(value = "/ReversalTransaction", consumes = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public String ReverseTran(@RequestBody TranCimCBSTable tranCBStable, Model md,
			@RequestParam(value = "userID", required = false) String userID) throws UnknownHostException {

		if (tranCBStable.getTran_type().equals("0")) {
			System.out.println("DEBIT_REVERSE");
			String response = ipsServices.ManualDebitReversalTransaction(tranCBStable, userID);
			return response;
		} else if (tranCBStable.getTran_type().equals("1")) {
			System.out.println("CREDIT_REVERSE");
			String response = ipsServices.ManualCreditReversalTransaction(tranCBStable, userID);
			return response;
		} else if (tranCBStable.getTran_type().equals("2")) {
			System.out.println("RTP_REVERSE");
			String response = ipsServices.ManualDebitReversalTransaction(tranCBStable, userID);
			return response;
		} else if (tranCBStable.getTran_type().equals("3")) {
			System.out.println("BULK_CREDIT_REVERSE");
			String response = ipsServices.ManualBulkCreditReversalTransaction(tranCBStable, userID);
			return response;
		} else if (tranCBStable.getTran_type().equals("4")) {
			System.out.println("BULK_DEBIT_REVERSE");
			String response = ipsServices.ManualBulkDebitReversalTransaction(tranCBStable, userID);
			return response;
		} else {
			return "Wrong Request";
		}
	}

	@RequestMapping(value = "IPSBulkTransactionAdd", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MCCreditTransferResponse IPSBulkTransactionAdd(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID,
			@ModelAttribute BulkTransaction bulkTransaction, Model md, HttpServletRequest req) throws IOException {

		MCCreditTransferResponse msg = bulkServices.getDetailsCredit(bulkTransaction, formmode, userID);
		return msg;
	}

	@RequestMapping(value = "IPSBulkDebitTransactionAdd", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MCCreditTransferResponse IPSBulkDebitTransactionAdd(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID,
			@ModelAttribute BulkTransaction bulkTransaction, Model md, HttpServletRequest req) throws IOException {

		System.out.println("Welcome");
		MCCreditTransferResponse msg = bulkServices.getDetailsDebit(bulkTransaction, formmode, userID);
		return msg;
	}

	@RequestMapping(value = "IPSManualTransactionAdd", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MCCreditTransferResponse IPSManualTransactionAdd(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID,
			@ModelAttribute ManualTransaction bulkTransaction, Model md, HttpServletRequest req) throws IOException {

		System.out.println(userID);
		MCCreditTransferResponse msg = bulkServices.getDetailsManualDebit(bulkTransaction, formmode, userID);

		return msg;
	}

	@RequestMapping(value = "IPSRTPTransactionAdd", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MCCreditTransferResponse IPSRTPTransactionAdd(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID,
			@ModelAttribute RTPTransactionTable bulkTransaction, Model md, HttpServletRequest req) throws IOException {

		System.out.println(userID);
		MCCreditTransferResponse msg = bulkServices.getDetailsRTPDebit(bulkTransaction, formmode, userID);

		return msg;
	}

	@RequestMapping(value = "/IPSBulkCreditTransactionList/{seqUniqueID}", method = RequestMethod.POST)
	@ResponseBody
	public List<TransationMonitorPojo> IPSBulkCreditTransactionList(
			@PathVariable(value = "seqUniqueID", required = true) String seqUniqueID, Model md) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String tranDate = dateFormat.format(new Date());
		List<TransationMonitorPojo> moniService = monitorService.findAllCustomBulkCredit(seqUniqueID, tranDate);
		// List<TransactionMonitoring> tranMonitor =
		// tranMonitorRep.findAllCustomBulkCredit(seqUniqueID, tranDate);
		return moniService;
	}

	@RequestMapping(value = "/IPSBulkDebitTransactionList/{seqUniqueID}", method = RequestMethod.POST)
	@ResponseBody
	public List<TransationMonitorPojo> IPSBulkDebitTransactionList(
			@PathVariable(value = "seqUniqueID", required = true) String seqUniqueID, Model md) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String tranDate = dateFormat.format(new Date());
		List<TransationMonitorPojo> moniService = monitorService.findAllCustomBulkDebit(seqUniqueID, tranDate);
		// List<TransactionMonitoring> tranMonitor =
		// tranMonitorRep.findAllCustomBulkDebit(seqUniqueID, tranDate);
		return moniService;
	}

	@RequestMapping(value = "/IPSBulkManualTransactionList/{seqUniqueID}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public List<TransationMonitorPojo> IPSBulkManualTransactionList(
			@PathVariable(value = "seqUniqueID", required = true) String seqUniqueID, Model md) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String tranDate = dateFormat.format(new Date());
		List<TransationMonitorPojo> moniService = monitorService.findAllCustomBulkManual(seqUniqueID, tranDate);
		// List<TransactionMonitoring> tranMonitor =
		// tranMonitorRep.findAllCustomBulkManual(seqUniqueID, tranDate);
		return moniService;
	}

	@RequestMapping(value = "/IPSBulkRTPTransactionList/{seqUniqueID}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public List<TransationMonitorPojo> IPSBulkRTPTransactionList(
			@PathVariable(value = "seqUniqueID", required = true) String seqUniqueID, Model md) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String tranDate = dateFormat.format(new Date());
		List<TransationMonitorPojo> moniService = monitorService.findAllCustomBulkRTP(seqUniqueID, tranDate);
		// List<TransactionMonitoring> tranMonitor =
		// tranMonitorRep.findAllCustomBulkManual(seqUniqueID, tranDate);
		return moniService;
	}

	@RequestMapping(value = "/IPSBulkCreditTranExe/{docRefID}", method = RequestMethod.POST)
	@ResponseBody
	public String IPSBulkCreditTranExe(@PathVariable(value = "docRefID", required = true) String docRefID,
			@RequestParam(value = "userID", required = false) String userID, Model md) throws UnknownHostException {
		List<BulkTransaction> bulkTranList = bulkTranRep.findByIdDocRefID(docRefID);
		String response = ipsServices.CreateBulkCreditTran(bulkTranList, userID);

		bulkServices.updateIPSRefID(docRefID, response, userID);
		return response;
	}

	@RequestMapping(value = "/IPSBulkDebitTranExe/{docRefID}", method = RequestMethod.POST)
	@ResponseBody
	public String IPSBulkDebitTranExe(@PathVariable(value = "docRefID", required = true) String docRefID,
			@RequestParam(value = "userID", required = false) String userID, Model md) throws UnknownHostException {
		List<BulkTransaction> bulkTranList = bulkTranRep.findByIdDocRefID(docRefID);
		String response = ipsServices.CreateBulkDebitTran(bulkTranList, userID);
		bulkServices.updateIPSRefID(docRefID, response, userID);
		return response;
	}

	@RequestMapping(value = "/IPSManualTranExe/{docRefID}", method = RequestMethod.POST)
	@ResponseBody
	public String IPSManualTranExe(@PathVariable(value = "docRefID", required = true) String docRefID, Model md,
			@RequestParam(value = "userID", required = false) String userID) throws UnknownHostException {
		List<ManualTransaction> bulkTranList = manualTranRep.findByIdDocRefID(docRefID);
		String response = ipsServices.CreateManualBulkTran(bulkTranList, userID);
		System.out.println("responseresponse" + response);
		bulkServices.updateIPSRefIDManual(docRefID, response, userID);
		return response;
	}

	@RequestMapping(value = "/IPSRTPTranExe/{docRefID}", method = RequestMethod.POST)
	@ResponseBody
	public String IPSRTPTranExe(@PathVariable(value = "docRefID", required = true) String docRefID, Model md,
			@RequestParam(value = "userID", required = false) String userID) throws UnknownHostException {
		List<RTPTransactionTable> bulkTranList = rtpTransactionTableRep.findByIdDocRefID(docRefID);
		String response = ipsServices.CreateRTPBulkTran(bulkTranList, userID);
		System.out.println("responseresponse" + response);
		bulkServices.updateIPSRefIDRTP(docRefID, response, userID);
		return response;
	}

	@RequestMapping(value = "createSettlementAccount", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String createSettlementAccount(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute SettlementAccount settlementAccount, Model md, HttpServletRequest req) throws IOException {
		String userID = (String) req.getSession().getAttribute("USERID");
		String msg = settlementAccountServices.CreateSettlement(settlementAccount, formmode, userID);
		return msg;

	}

	@Autowired
	BIPSBankandBranchServices bankandBranchServices;

	/*
	 * @RequestMapping(value = "ModBankBranchMaster", method = { RequestMethod.GET,
	 * RequestMethod.POST })
	 * 
	 * @ResponseBody public String ModBankBranchMaster1(@RequestParam("formmode")
	 * String formmode,
	 * 
	 * @ModelAttribute BankAndBranchBean bamlSolEntity, Model md, HttpServletRequest
	 * rq) { String userID = (String) rq.getSession().getAttribute("USERID"); String
	 * msg = bankandBranchServices.modDetails(bamlSolEntity, formmode,userID);
	 * md.addAttribute("adminflag", "adminflag");
	 * 
	 * return msg;
	 * 
	 * }
	 */
	@RequestMapping(value = "createBusHours", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String createBusHours(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute BusinessHours businessHours, Model md, HttpServletRequest req) throws IOException {

		String msg = settlementAccountServices.Createbusiness(businessHours, formmode);
		return msg;

	}

	@RequestMapping(value = "addBankandBranch", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addBankandBranch(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute BankAgentTable bankAgentTable, Model md, HttpServletRequest req) throws IOException {

		System.out.println("Formmode---->" + formmode);

		String userID = (String) req.getSession().getAttribute("USERID");
		String msg = bankAndBranchMasterServices.addBank(bankAgentTable, formmode, userID);

		return msg;

	}

	@RequestMapping(value = "addFeesAndServices", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addFeesandServices(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute IPSChargesAndFees ipsChargesAndFees, Model md, HttpServletRequest req) throws IOException {

		System.out.println("Formmode---->" + formmode);

		String userID = (String) req.getSession().getAttribute("USERID");
		String msg = loginSecurityServices.addFees(ipsChargesAndFees, formmode);

		return msg;

	}

	@RequestMapping(value = "deleteRuleType/{ruleid}", method = { RequestMethod.GET, RequestMethod.POST })
	public String RuleType(@PathVariable("ruleid") String ruleid, Model md) {

		System.out.println("Ajax");
		return AccessRoleService.deleteRole(ruleid);
	}
	/*
	 * @RequestMapping(value = "CANCELINUSER/{ruleid}", method = {
	 * RequestMethod.GET, RequestMethod.POST }) public String
	 * CANCELINUSER(@PathVariable("ruleid") String ruleid, Model md) {
	 * 
	 * System.out.println("Ajax"); return usermodservice.cancel(ruleid); }
	 */

	@RequestMapping(value = "/getRoleDetails/{roleid}", method = { RequestMethod.GET, RequestMethod.POST })
	public String gettingpatvisitdetail(@PathVariable(value = "roleid", required = true) String roleid) {

		String roleiddetails = accessandRolesRepository.rulelistCODE(roleid);
		System.out.println("roleid = " + roleid);

		return roleiddetails;
	}

	@RequestMapping(value = "editBusinessHours", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String editBusinessHours(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String srlno, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute BusinessHours businessHours, Model md, HttpServletRequest req) throws IOException {
		System.out.println(businessHours);
		String roleId = (String) req.getSession().getAttribute("ROLEID");
		md.addAttribute("IPSRoleMenu", AccessRoleService.getRoleMenu(roleId));
		System.out.println(srlno);
		String msg = loginSecurityServices.addHours(businessHours, formmode, srlno);
		md.addAttribute("BusinessHours", msg);
		return msg;

	}

	@RequestMapping(value = "getBlobImage/{Mandate_ref_no}", method = RequestMethod.GET)
	@ResponseBody
	public String BlobImage(@PathVariable("Mandate_ref_no") String Mandate_ref_no, Model md) {
		System.out.println("Mandate_ref_no" + Mandate_ref_no);

		MandateMaster mandateMaster = mandateServices.BlobImage(Mandate_ref_no);
		System.out.println(mandateMaster.getMand_doc_image());
		return Base64.getEncoder().encodeToString(mandateMaster.getMand_doc_image());
	}

	@RequestMapping(value = "getUserBlobImage/{userID}", method = RequestMethod.GET)
	@ResponseBody
	public String BlobImageUser(@PathVariable("userID") String userID, Model md) {
		System.out.println("userID" + userID);
		int usercount = iPSUserProfileModRep.getnum(userID);
		System.out.println(usercount + "fgfg");
		if (usercount == 0) {
			UserProfile userProfile = mandateServices.UserBlobImage1(userID);
			System.out.println(userProfile.getPhoto());
			return Base64.getEncoder().encodeToString(userProfile.getPhoto());
		} else {
			IPSUserPreofileMod userProfile = mandateServices.UserBlobImage(userID);
			System.out.println(userProfile.getPhoto());
			return Base64.getEncoder().encodeToString(userProfile.getPhoto());
		}
		// IPSUserPreofileMod userProfile = mandateServices.UserBlobImage(userID);

	}

	@RequestMapping(value = "getQRImage/{QrMsg}", method = RequestMethod.GET)
	@ResponseBody
	public String BlobQRImage(@PathVariable("QrMsg") String QrMsg, Model md) throws WriterException, IOException {
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(QrMsg, BarcodeFormat.QR_CODE, 125, 125, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, "png", output);
		String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());

		return imageAsBase64;

	}

	@RequestMapping(value = "addMerchantQRREG", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addMerchantQRREG(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@ModelAttribute MerchantQRRegistration bankAgentTable, Model md, HttpServletRequest req)
			throws IOException {

		System.out.println("Formmode---->" + formmode);

		String userID = (String) req.getSession().getAttribute("USERID");
		String paycode = env.getProperty("ipsx.qr.payeecode");
		String globalUnique = env.getProperty("ipsx.qr.globalUnique");
		String payload = env.getProperty("ipsx.qr.payload");
		String poiMethod_static = env.getProperty("ipsx.qr.poiMethod_static");
		bankAgentTable.setPoi_method(poiMethod_static);
		bankAgentTable.setPayee_participant_code(paycode);
		bankAgentTable.setGlobal_unique_id(globalUnique);
		bankAgentTable.setPayload_format_indicator(payload);

		String msg = bankAndBranchMasterServices.addMerchantQRcode(bankAgentTable, formmode, userID);

		return msg;

	}

	@RequestMapping(value = "getdynamicqrcode", method = RequestMethod.GET)
	public String getdynamicqrcode(@RequestParam(required = false) String acct_num,
			@RequestParam(required = false) String tran_amt, @RequestParam(required = false) String mob_num,
			@RequestParam(required = false) String loy_num, @RequestParam(required = false) String sto_label,
			@RequestParam(required = false) String cust_label, @RequestParam(required = false) String ref_label,
			@RequestParam(required = false) String ter_label, @RequestParam(required = false) String pur_tran,
			@RequestParam(required = false) String add_det, @RequestParam(required = false) String bill_num, Model md)
			throws UnknownHostException {
		System.out.println();
		System.out.println(tran_amt);
		MerchantQRRegistration merchantQRgenerator = merchantQrCodeRegRep.findByIdCustom(acct_num);
		merchantQRgenerator.setTransaction_amt(tran_amt);
		merchantQRgenerator.setBill_number(bill_num);
		merchantQRgenerator.setMobile(mob_num);
		merchantQRgenerator.setLoyalty_number(loy_num);
		merchantQRgenerator.setStore_label(sto_label);
		merchantQRgenerator.setCustomer_label(cust_label);
		merchantQRgenerator.setReference_label(ref_label);
		merchantQRgenerator.setTerminal_label(ter_label);
		merchantQRgenerator.setPurpose_of_tran(pur_tran);
		merchantQRgenerator.setAdditional_details(add_det);
		String paycode = env.getProperty("ipsx.qr.payeecode");
		String globalUnique = env.getProperty("ipsx.qr.globalUnique");
		String payload = env.getProperty("ipsx.qr.payload");
		String poiMethod_dynamic = env.getProperty("ipsx.qr.poiMethod_dynamic");
		merchantQRgenerator.setPoi_method(poiMethod_dynamic);
		merchantQRgenerator.setPayee_participant_code(paycode);
		merchantQRgenerator.setGlobal_unique_id(globalUnique);
		merchantQRgenerator.setPayload_format_indicator(payload);
		ResponseEntity<cimMerchantQRcodeResponse> merchantQRResponse = ipsServices
				.getMerchantQrCode(merchantQRgenerator);
		String QrImg;
		String imageAsBase64;
		if (merchantQRResponse.getStatusCode() == HttpStatus.OK) {
			QrImg = merchantQRResponse.getBody().getBase64QR();
			imageAsBase64 = "data:image/png;base64," + QrImg;

		} else {
			if (merchantQRResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
				imageAsBase64 = merchantQRResponse.getBody().getError().toString() + ":"
						+ merchantQRResponse.getBody().getError_Desc().get(0);
			} else {
				imageAsBase64 = "Something went wrong at server end";
			}
		}
		return imageAsBase64;
	}

	@RequestMapping(value = "/getstaticqrcode/{acct_num}", method = RequestMethod.GET)
	public String getstaticqrcode(@PathVariable("acct_num") String acct_num, Model md) throws UnknownHostException {

		MerchantQRRegistration merchantQRgenerator = merchantQrCodeRegRep.findByIdCustom(acct_num);
		String paycode = env.getProperty("ipsx.qr.payeecode");
		String globalUnique = env.getProperty("ipsx.qr.globalUnique");
		String payload = env.getProperty("ipsx.qr.payload");
		String poiMethod_static = env.getProperty("ipsx.qr.poiMethod_static");
		merchantQRgenerator.setPoi_method(poiMethod_static);
		merchantQRgenerator.setPayee_participant_code(paycode);
		merchantQRgenerator.setGlobal_unique_id(globalUnique);
		merchantQRgenerator.setPayload_format_indicator(payload);

		ResponseEntity<cimMerchantQRcodeResponse> merchantQRResponse = ipsServices
				.getMerchantQrCode(merchantQRgenerator);
		String QrImg;
		String imageAsBase64;
		if (merchantQRResponse.getStatusCode() == HttpStatus.OK) {
			QrImg = merchantQRResponse.getBody().getBase64QR();
			imageAsBase64 = "data:image/png;base64," + QrImg;

		} else {
			if (merchantQRResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
				imageAsBase64 = merchantQRResponse.getBody().getError().toString() + ":"
						+ merchantQRResponse.getBody().getError_Desc().get(0);
			} else {
				imageAsBase64 = "Something went wrong at server end";
			}
		}
		return imageAsBase64;
	}

	@RequestMapping(value = "getMxMsg", method = RequestMethod.GET)
	public String getMxMsg(@RequestParam("msgid") String msg_id, @RequestParam("subtype") String msg_sub_type,
			Model md) {
		System.out.println("DataIPS");

		String imageAsMxMsg = "0";
		List<TranIPSTable> list = tranIPStableRep.getMsMsg(msg_id, msg_sub_type);
		if (list.size() > 0) {
			System.out.println("DataIPS" + list.size());
			if (list.get(0).getMx_msg() != null) {
				imageAsMxMsg = list.get(0).getMx_msg();
			} else {
				imageAsMxMsg = "0";
			}

		}
		return imageAsMxMsg;
	}

	/// Get User Filter List
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value ="/searchLdapList", method = RequestMethod.GET,produces
	 * = "application/json;charset=utf-8")
	 * 
	 * @ResponseBody public List<LdapUserList>
	 * searchLdapList(@RequestParam(value="userId")String userid) {
	 * 
	 * List<LdapUserList> ldapUsers=ldapService.getSearchLdapList(userid);
	 * 
	 * return ldapUsers;
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(value ="/ldapListGrp", method = RequestMethod.GET,produces =
	 * "application/json;charset=utf-8")
	 * 
	 * @ResponseBody public List<LdapUserList>
	 * ldapListGrp(@RequestParam(value="userId")String userid) {
	 * 
	 * List<LdapUserList> ldapUsers = new ArrayList<LdapUserList>(); try { // List
	 * search = ldapTemplate.search("", "(objectClass=organizationalUnit)", new
	 * LdapUserAttributesMapper()); // ldapUsers.addAll(search);
	 * 
	 * LdapQuery query = query() .searchScope(SearchScope.SUBTREE) .timeLimit(20000)
	 * .countLimit(10) //.attributes("cn") //.base("OU=CIM FINANCE") //
	 * .base("OU=CONSULTANT,OU=CIM FINANCE")
	 * //.base("OU=Users,OU=IT,OU=CIM FINANCE") .where("objectclass").is("top")
	 * .and("objectclass").is("person")
	 * .and("objectclass").is("organizationalPerson") //
	 * .and("objectclass").is("user") // .and("sn").not().is(lastName)
	 * .and("sAMAccountName").like(userid+"*") .and("sAMAccountName").isPresent() ;
	 * 
	 * ldapUsers= ldapTemplate.search(query, new LdapUserAttributesMapper());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { System.out.println("Error: " + e); }
	 * 
	 * return ldapUsers;
	 * 
	 * }
	 */

//	@GetMapping(path="api/mtTomx",produces = "application/json;charset=utf-8")
//	private ResponseEntity<MTtoMXresponse> MTtoMXconverter(@RequestParam("file") MultipartFile file) throws IOException {
//		
//		MTtoMXresponse status=swiftConnection.mtToMxConverter(file);
//		return new ResponseEntity<>(status, HttpStatus.OK);
//	}

//	@GetMapping(path="api/MXtoMT",produces = "application/json;charset=utf-8")
//	private ResponseEntity<MTtoMXresponse> MXtoMtconverter(@RequestParam("file") MultipartFile file) throws IOException {
//		System.out.println("inside");
//		MTtoMXresponse status=swiftConnection.mxToMtConverter(file);
//		return new ResponseEntity<>(status, HttpStatus.OK);
//	}

	@RequestMapping(value = "BIPSMXMessageform", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MTtoMXresponse BIPSMXMessageform(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID, @ModelAttribute BIPS_SWIFT_MSG_MGT message,
			BIPS_SWIFT_MT_MSG reference, BIPS_SWIFT_MX_MSG mxmessage, Model md, HttpServletRequest req)
			throws IOException {
		System.out.println("inside rest");
		System.out.println("response" + message.toString());
		String oi = message.getFile_name();
		String srl = message.getSrl_no();
		System.out.println("reed" + srl);
		System.out.println("mxtoMtfile" + oi);
		/// Insert BIPS_SWIFT_MSG_MGT

		formmode = "add";
		String res = bipsMsgConversionProcessRec.MessageProcessSubmit(message, formmode);
		System.out.println(res);

		MTtoMXresponse status = swiftConnection.mxToMtConverter(message, null, res);

		formmode = "update";

		message.setConverted_file_name(DocumentPacks.MtMessgaePath());

		String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
		System.out.println(statusupt);

		return status;
	}

	@RequestMapping(value = "BIPSMTMessageform", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MTtoMXresponse BIPSMTMessageform(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String userid, @RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID, @ModelAttribute BIPS_SWIFT_MSG_MGT message,
			BIPS_SWIFT_MT_MSG reference, BIPS_SWIFT_MX_MSG mxmessage, Model md, HttpServletRequest req)
			throws IOException, ParseException {
		System.out.println("inside rest");
		System.out.println("response" + message.toString());

		/// Insert BIPS_SWIFT_MSG_MGT
		String oi = message.getFile_name();
		System.out.println("mtToMxfile" + oi);
		String srl = message.getSrl_no();
		System.out.println("reed" + srl);
		formmode = "add";
		String res = bipsMsgConversionProcessRec.MessageProcessSubmit(message, formmode);
		System.out.println(res);
		MTtoMXresponse status = swiftConnection.mtToMxConverter(message, null, res);

		formmode = "update";
		message.setConverted_file_name(DocumentPacks.MxMessagePath());

		String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
		System.out.println(statusupt);

		return status;
	}

	@RequestMapping(value = "BIPSFinacleMTMessageform", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<BIPS_SWIFT_MSG_MGT> BIPSFnacleMTMessageform(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String filename, @RequestParam(required = false) String userid,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID, @ModelAttribute BIPS_SWIFT_MSG_MGT message,
			Model md, HttpServletRequest req) throws IOException, ParseException {

		System.out.println("Inside finacle");
		System.out.println("Inside rest");
		System.out.println("Response: " + message.toString());
		
		System.out.println("The converted filename is "+message.getConverted_file_name());

		String Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		System.out.println("The getting file name is " + filename);

		List<BIPS_SWIFT_MSG_MGT> dataList = new ArrayList<>();

		if (filename != null) {
			message.setSrl_no(Srl);
			message.setDate_of_process(new Date());
			message.setSwift_msg_type("MT");
			message.setSwift_mrg_type_conv("MX");

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			message.setTime_of_conv(dtf.format(now));

			String userid1 = (String) req.getSession().getAttribute("USERID");
			String username = userProfileRep.findNameById(userid1);
			String Country_code = userProfileRep.getCountrycode(userid1);
			System.out.println("Country Code: " + Country_code);

			String path = "";
			switch (Country_code) {
			case "BWA":
				path = env.getProperty("bwa.swift.mt.out.file.path");
				message.setCountry_code("BWA");
				break;
			case "MOZ":
				path = env.getProperty("moz.swift.mt.out.file.path");
				message.setCountry_code("MOZ");
				break;
			case "MWI":
				path = env.getProperty("mwi.swift.mt.out.file.path");
				message.setCountry_code("MWI");
				break;
			case "ZMB":
				path = env.getProperty("zmb.swift.mt.out.file.path");
				message.setCountry_code("ZMB");
				break;
			case "ZWE":
				path = env.getProperty("zwe.swift.mt.out.file.path");
				message.setCountry_code("ZWE");
				break;
			case "MUS":
				path = env.getProperty("mus.swift.mt.out.file.path");
				message.setCountry_code("MUS");
				break;
			}

			message.setFile_name(path + filename);
			System.out.println(path + filename + " Mt to Mx path");

			if (filename.endsWith(".txt") || filename.endsWith(".out")) {
				System.out.println(".Txt and .Out files present");
				
				System.out.println("Processing SWIFT file: " + filename);
				System.out.println("File Path: " + message.getFile_name());
				System.out.println("Converted File Name: " + message.getConverted_file_name());

				formmode = "add";
				String res = bipsMsgConversionProcessRec.FinacleMessageProcessSubmit(message, formmode);
				System.out.println(res);

				MTtoMXresponse status = swiftConnection.mtToMxConverter(message, userid1,filename);

				formmode = "update";
				String audit = userProfileService.uploadAuditsubmitMttomx(userid1, username);
				System.out.println("Audit result: " + audit);

				message.setConverted_file_name(DocumentPacks.MxMessagePath());

				String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
				message.setMx_reference_no(BipsMsgConversionProcessRec.mxref());

				System.out.println(statusupt);

				BIPS_SWIFT_MSG_MGT result = new BIPS_SWIFT_MSG_MGT();
				result.setFile_name(DocumentPacks.Mxmsgname());
				result.setMx_reference_no(BipsMsgConversionProcessRec.mxref());
				result.setSrl_no(Srl);

				dataList.add(result);
			} else if (filename.endsWith(".outt") || filename.endsWith(".inn")) {
			    System.out.println(".outt and .inn files detected, saving record only");
			    message.setStatus("Inprogress");
			    message.setRemarks("File Format Mismatched (.outt & .inn))");

			    String res = bipsMsgConversionProcessRec.FinacleMessageProcessSubmitfailure(message);
			    System.out.println(res);

			    // Return an error response
			    BIPS_SWIFT_MSG_MGT errorResponse = new BIPS_SWIFT_MSG_MGT();
			    errorResponse.setFile_name("Conversion Failed");
			    errorResponse.setMx_reference_no("Error");
			    errorResponse.setSrl_no(Srl);

			    dataList.add(errorResponse);
			} else {
			    // Handle unsupported file formats
			    System.out.println("Unsupported file format detected");
			    BIPS_SWIFT_MSG_MGT errorResponse = new BIPS_SWIFT_MSG_MGT();
			    errorResponse.setFile_name("Unsupported File");
			    errorResponse.setMx_reference_no("Error");
			    errorResponse.setSrl_no(Srl);

			    dataList.add(errorResponse);
			}
		}

		return dataList;
	}

	@RequestMapping(value = "BIPSSwiftMxFileSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<BIPS_SWIFT_MSG_MGT> BIPSSwiftMxFileSubmit(@RequestParam(required = false) String formmode,
			@RequestParam(required = false) String filename, @RequestParam(required = false) String userid,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(value = "size", required = false) Optional<Integer> size,
			@RequestParam(value = "userID", required = false) String userID, @ModelAttribute BIPS_SWIFT_MSG_MGT message,
			BIPS_SWIFT_MT_MSG reference, BIPS_SWIFT_MX_MSG mxmessage, Model md, HttpServletRequest req)
			throws IOException {
		System.out.println("inside rest");
		System.out.println("response" + message.toString());
		String Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		message.setSrl_no(Srl);
		message.setSrl_no(Srl);
		message.setDate_of_process(new Date());
		message.setSwift_msg_type("MX");
		message.setSwift_mrg_type_conv("MT");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		message.setTime_of_conv(dtf.format(now));
		System.out.println(dtf.format(now));
		String userid1 = (String) req.getSession().getAttribute("USERID");
		String Country_code = userProfileRep.getCountrycode(userid1);
		
		System.out.println("THE GETTING FILENAME IS HRE "+filename);

		String path = "";

		env.getProperty("bwa.swift.mx.in.file.path");
		switch (Country_code) {
		// Case statements
		case "BWA":
			path = env.getProperty("bwa.swift.mx.in.file.path");
			message.setCountry_code("BWA");
			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mx.in.file.path");
			message.setCountry_code("MOZ");
			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mx.in.file.path");
			message.setCountry_code("MWI");
			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mx.in.file.path");
			message.setCountry_code("ZMB");
			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mx.in.file.path");
			message.setCountry_code("ZWE");
			break;
		case "MUS":
			path = env.getProperty("mus.swift.mx.in.file.path");
			// System.out.println(path+ " LLLLL");
			message.setCountry_code("MUS");
			break;

		}
		System.out.println(path + filename + "MXtomtPath");
		message.setFile_name(path + filename);

		List<BIPS_SWIFT_MSG_MGT> dataList = new ArrayList<>();
		/// Insert BIPS_SWIFT_MSG_MGT

		if (filename.endsWith(".txt") || filename.endsWith(".IN") || filename.endsWith(".OUT")) {
			System.out.println(".Txt and .Out files present");

			formmode = "add";
			String res = bipsMsgConversionProcessRec.SwiftMessageProcessSubmit(message, formmode);
			System.out.println(res);

			MTtoMXresponse status = swiftConnection.mxToMtConverter(message, userid1,filename);

			String username = userProfileRep.findNameById(userid1);
			String audit = userProfileService.uploadAuditsubmitMxtomt(userid1, username);
			System.out.println("audit+mxtomt" + audit);
			formmode = "update";

			message.setConverted_file_name(DocumentPacks.MtMessgaePath());

			String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
			System.out.println(statusupt);

			BIPS_SWIFT_MSG_MGT result = new BIPS_SWIFT_MSG_MGT();
			result.setFile_name(DocumentPacks.MTmsgname());
			result.setMt_reference_no(bipsMsgConversionProcessRec.mtref());
			result.setSrl_no(Srl);
			dataList.add(result);
		} else if (filename.endsWith(".xmll") || filename.endsWith(".INN")) {
		    System.out.println(".outt and .inn files detected, saving record only");
		    message.setStatus("Inprogress");
		    message.setRemarks("File Format Mismatched (.xmll & .INN))");
		    
		    String res = bipsMsgConversionProcessRec.SwiftMessageProcessSubmitfailure(message);
		    System.out.println(res);

		    // Return an error response
		    BIPS_SWIFT_MSG_MGT errorResponse = new BIPS_SWIFT_MSG_MGT();
		    errorResponse.setFile_name("Conversion Failed");
		    errorResponse.setMx_reference_no("Error");
		    errorResponse.setSrl_no(Srl);

		    dataList.add(errorResponse);
		} else {
		    // Handle unsupported file formats
		    System.out.println("Unsupported file format detected");
		    BIPS_SWIFT_MSG_MGT errorResponse = new BIPS_SWIFT_MSG_MGT();
		    errorResponse.setFile_name("Unsupported File");
		    errorResponse.setMx_reference_no("Error");
		    errorResponse.setSrl_no(Srl);

		    dataList.add(errorResponse);
		}
		
		return dataList;
	}

	public String AutoFilepicker(String usr, String filename) throws IOException, ParseException {
		BIPS_SWIFT_MSG_MGT message = new BIPS_SWIFT_MSG_MGT();
		String formmode = "";
		System.out.println("inside fincale");
		System.out.println("inside rest");
		// System.out.println("response" + message.toString());
		String Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		/// Insert BIPS_SWIFT_MSG_MGT
		// message.setFile_name("SWIFTfxe9EyI1e.MSG");

		message.setSrl_no(Srl);
		message.setDate_of_process(new Date());
		message.setSwift_msg_type("MT");
		message.setSwift_mrg_type_conv("MX");
		message.setStatus("Inprogress");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		message.setTime_of_conv(dtf.format(now));
		String userid1 = usr;

		String Country_code = usr;
		System.out.println("Contrycodeeee" + Country_code);
		String path = "";

		switch (Country_code) {
		// Case statements
		case "Auto_BWA":
			path = env.getProperty("auto.bwa.swift.mt.out.file.path");
			message.setCountry_code("BWA");
			break;
		case "Auto_MOZ":
			path = env.getProperty("auto.moz.swift.mt.out.file.path");
			message.setCountry_code("MOZ");
			break;
		case "Auto_MWI":
			path = env.getProperty("auto.mwi.swift.mt.out.file.path");
			message.setCountry_code("MWI");
			break;
		case "Auto_ZMB":
			path = env.getProperty("auto.zmb.swift.mt.out.file.path");
			message.setCountry_code("ZMB");
			break;
		case "Auto_ZWE":
			path = env.getProperty("auto.zwe.swift.mt.out.file.path");
			message.setCountry_code("ZWE");
			break;
		case "Auto_MUS":
			path = env.getProperty("auto.mus.swift.mt.out.file.path");
			// System.out.println(path+" KKKKKK");
			message.setCountry_code("MUS");
			break;
		case "Auto":
			path = env.getProperty("auto.swift.mt.out.file.path");
			// System.out.println(path+" KKKKKK");
			message.setCountry_code("AUTO");
			break;

		}
		System.out.println(path + filename + "Mt to Mxpath ");
		message.setFile_name(path + filename);
		// System.out.println("mtToMxfile" + oi);
		String srl = message.getSrl_no();
		System.out.println("reed" + srl);
		formmode = "add";
		String res = bipsMsgConversionProcessRec.FinacleMessageProcessSubmit(message, formmode);
		System.out.println(res);
		MTtoMXresponse status = swiftConnection.mtToMxConverter(message, userid1, filename);

		formmode = "update";
		message.setConverted_file_name(DocumentPacks.MxMessagePath());

		String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
		message.setMx_reference_no(BipsMsgConversionProcessRec.mxref());

		System.out.println(statusupt);

		String msg = "success";
		return msg;

	}

	public String AutoFilepicker2(String usr, String filename) throws IOException, ParseException {
		String formmode = "";
		BIPS_SWIFT_MSG_MGT message = new BIPS_SWIFT_MSG_MGT();
		System.out.println("inside rest");
		System.out.println("response" + message.toString());
		String Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		message.setSrl_no(Srl);
		message.setSrl_no(Srl);
		message.setDate_of_process(new Date());
		message.setSwift_msg_type("MX");
		message.setSwift_mrg_type_conv("MT");
		message.setStatus("Inprogress");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		message.setTime_of_conv(dtf.format(now));
		String userid1 = usr;
		String Country_code = usr;

		String path = "";

		env.getProperty("bwa.swift.mx.in.file.path");
		switch (Country_code) {
		// Case statements
		case "Auto_BWA":
			path = env.getProperty("auto.bwa.swift.mx.in.file.path");
			message.setCountry_code("BWA");
			break;
		case "Auto_MOZ":
			path = env.getProperty("auto.moz.swift.mx.in.file.path");
			message.setCountry_code("MOZ");
			break;
		case "Auto_MWI":
			path = env.getProperty("auto.mwi.swift.mx.in.file.path");
			message.setCountry_code("MWI");
			break;
		case "Auto_ZMB":
			path = env.getProperty("auto.zmb.swift.mx.in.file.path");
			message.setCountry_code("ZMB");
			break;
		case "Auto_ZWE":
			path = env.getProperty("auto.zwe.swift.mx.in.file.path");
			message.setCountry_code("ZWE");
			break;
		case "Auto_MUS":
			path = env.getProperty("auto.swift.mx.in.file.path");
			// System.out.println(path+ " LLLLL");
			message.setCountry_code("MUS");
			break;

		case "Auto":
			path = env.getProperty("auto.swift.mx.in.file.path");
			// System.out.println(path+ " LLLLL");
			message.setCountry_code("AUTO");
			break;
		}
		System.out.println("The getting path value is here " + path);
		System.out.println(path + filename + "MXtomtPath");
		message.setFile_name(path + filename);

		/// Insert BIPS_SWIFT_MSG_MGT

		formmode = "add";
		String res = bipsMsgConversionProcessRec.SwiftMessageProcessSubmit(message, formmode);
		System.out.println(res);

		MTtoMXresponse status = swiftConnection.mxToMtConverter(message, userid1, filename);

		formmode = "update";

		message.setConverted_file_name(DocumentPacks.MtMessgaePath());

		String statusupt = bipsMsgConversionProcessRec.MessageStatusUpdate(message, formmode);
		System.out.println(statusupt);
		String msg = "success2";
		return msg;

	}

}
