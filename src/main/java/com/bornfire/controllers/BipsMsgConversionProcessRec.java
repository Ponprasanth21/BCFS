package com.bornfire.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bornfire.entity.BIPS_SWIFT_MSG_MGT;
import com.bornfire.entity.BIPS_SWIFT_MT_MSG;
import com.bornfire.entity.BIPS_SWIFT_MX_MSG;
import com.bornfire.entity.BipsSwiftMsgConversionRepo;
import com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61;
import com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391;
import com.bornfire.parser.AppHeader;
import com.bornfire.parser.DocumentPacks;

@Service
@Transactional
public class BipsMsgConversionProcessRec {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	BipsSwiftMsgConversionRepo bipsSwiftMsgConversionRepo;
	
	private static String MxRef = "";
	private static String MtRef="";

	public String MessageProcessSubmit(BIPS_SWIFT_MSG_MGT message, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String res = "";
		if (formmode.equals("add")) {
			System.out.println(formmode);
			String srl_no = FileUploaderController.Serial();
			message.setSrl_no(srl_no);

			BIPS_SWIFT_MSG_MGT up = message;

			hs.save(up);

			res = "Added Successfully";

		}
		return res;

	}

	public String MessageStatusUpdate(BIPS_SWIFT_MSG_MGT message, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String statusupt = "";
		// bipsSwiftMsgConversionRepo.findBYId();
		if (formmode.equals("update")) {
			System.out.println(formmode);

			message.setStatus("Completed");

			BIPS_SWIFT_MSG_MGT up = message;

			// hs.saveOrUpdate(up);
			hs.update(up);

			statusupt = "Status Updated Successfully";
		}

		return statusupt;

	}

	
	public String savingMxMsgDetail(BIPS_SWIFT_MX_MSG mxmessgedata, BIPS_SWIFT_MSG_MGT message) {

		Session hs = sessionFactory.getCurrentSession();
		String refno = bipsSwiftMsgConversionRepo.getNextrefId();
		

		mxmessgedata.setSrl_no(message.getSrl_no());
		mxmessgedata.setFile_mgt_ref_no(refno);
		mxmessgedata.setMsg_type("MX");
		SwiftConnection swiftConection = new SwiftConnection();
		String request = swiftConection.readFile(message.getFile_name());
		byte[] encodeBase64 = Base64.getEncoder().encode(request.getBytes());
		mxmessgedata.setMx_message_file(request);

		BIPS_SWIFT_MX_MSG up = mxmessgedata;
		hs.save(up);
		String msg = "MxSubitted";
		return msg;
	}

	public String savingMtmsgdetail(BIPS_SWIFT_MT_MSG mtmessagedata, BIPS_SWIFT_MSG_MGT message) {
		Session hs = sessionFactory.getCurrentSession();
		mtmessagedata.setSrl_no(message.getSrl_no());
		String refno=bipsSwiftMsgConversionRepo.getNextrefId();
		MtRef = refno;
		mtmessagedata.setFile_mgt_ref_no(refno);

		mtmessagedata.setMsg_type("MT");
		String msgPath = DocumentPacks.MtMessgaePath();
		SwiftConnection swiftConection = new SwiftConnection();
		String request = swiftConection.readFile(msgPath);
		byte[] encodeBase64 = Base64.getEncoder().encode(request.getBytes());
		mtmessagedata.setMt_message_file(request);

		BIPS_SWIFT_MT_MSG up = mtmessagedata;
		hs.save(up);

		String msg = "MTSubitted";
		return msg;
	}
	
	public String MtmsgMTtoMX(BIPS_SWIFT_MT_MSG mtmessagedata, BIPS_SWIFT_MSG_MGT message) {
		Session hs = sessionFactory.getCurrentSession();
		String refno=bipsSwiftMsgConversionRepo.getNextrefId();
		MtRef = refno;
		mtmessagedata.setSrl_no(message.getSrl_no());
		mtmessagedata.setFile_mgt_ref_no(refno);
		

		mtmessagedata.setMsg_type("MT");
		SwiftConnection swiftConection = new SwiftConnection();
		String request = swiftConection.readFile(message.getFile_name());
		
		byte[] encodeBase64 = Base64.getEncoder().encode(request.getBytes());
		mtmessagedata.setMt_message_file(request);

		BIPS_SWIFT_MT_MSG up = mtmessagedata;
		hs.save(up);

		String msg = "MTSubitted";
		return msg;
	}

	public String MxmsgMTtoMX(BIPS_SWIFT_MX_MSG mxmessagedata, BIPS_SWIFT_MSG_MGT message) {
		Session hs = sessionFactory.getCurrentSession();
		String refno=bipsSwiftMsgConversionRepo.getNextrefId();
		mxmessagedata.setSrl_no(message.getSrl_no());
		mxmessagedata.setFile_mgt_ref_no(refno);
		
		MxRef = refno;

		mxmessagedata.setMsg_type("MX");
		
		String path = DocumentPacks.MxMessagePath();
		
		SwiftConnection swiftConection = new SwiftConnection();
		String request = swiftConection.readFile(path);
		byte[] encodeBase64 = Base64.getEncoder().encode(request.getBytes());
		mxmessagedata.setMx_message_file(request);
		BIPS_SWIFT_MX_MSG up = mxmessagedata;
		hs.save(up);

		String msg = "MTSubitted";
		
		return msg;
	}
	
	public String FinacleMessageProcessSubmit(BIPS_SWIFT_MSG_MGT message, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String res = "";
		if (formmode.equals("add")) {
			System.out.println(formmode);
			String srl_no = FileUploaderController.Serial();
			

			BIPS_SWIFT_MSG_MGT up = message;

			hs.save(up);

			res = "Added Successfully";

		}
		return res;

	}
	
	public String FinacleMessageProcessSubmitfailure(BIPS_SWIFT_MSG_MGT message) {
		Session hs = sessionFactory.getCurrentSession();
		String res = "";
		
			String srl_no = FileUploaderController.Serial();
			

			BIPS_SWIFT_MSG_MGT up = message;

			hs.save(up);

			res = "Failed Message Successfully";

		return res;

	}
	
	public String SwiftMessageProcessSubmit(BIPS_SWIFT_MSG_MGT message, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String res = "";
		if (formmode.equals("add")) {
			System.out.println(formmode);
			//String srl_no = FileUploaderController.Serial();
			

			BIPS_SWIFT_MSG_MGT up = message;

			hs.save(up);

			res = "Added Successfully";

		}
		return res;

	}
	
	public String SwiftMessageProcessSubmitfailure(BIPS_SWIFT_MSG_MGT message) {
		Session hs = sessionFactory.getCurrentSession();
		String res = "";
		
			BIPS_SWIFT_MSG_MGT up = message;

			hs.save(up);

			res = "Failure Message Added Successfully";
			
		return res;

	}
	
	public static String mxref() {

		return MxRef;
	}
public static String mtref() {
	return MtRef;
}
}

