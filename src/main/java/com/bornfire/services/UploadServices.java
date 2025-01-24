package com.bornfire.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.entity.BulkTransaction;
import com.bornfire.entity.MandateMaster;
import com.bornfire.entity.ManualTransaction;
import com.bornfire.entity.RTPTransactionTable;

@Service
public class UploadServices {
	@Autowired
	BulkServices bulkServices;
	
	@Autowired
	MandateServices mandateServices;
	/*
	 * public String uploadPreCheck(String screenId, String tranDate) {
	 * 
	 * String msg = "";
	 * 
	 * switch (screenId) { case "bulkService": //msg =
	 * BulkServices.uploadPreCheck(screenId, reportDate); break;
	 * 
	 * 
	 * 
	 * 
	 * default: System.out.println("default -> no report matched in switch");
	 * 
	 * }
	 * 
	 * return msg; }
	 */

	public List<BulkTransaction> processUploadFilesCredit(String screenId, MultipartFile file, String userid)
			throws FileNotFoundException, SQLException, IOException {

		List<BulkTransaction> msg = bulkServices.processUploadCredit(screenId, file, userid);

		return msg;

	}

	public List<BulkTransaction> processUploadFilesDebit(String screenId, MultipartFile file, String userid)
			throws FileNotFoundException, SQLException, IOException {

		List<BulkTransaction> msg = bulkServices.processUploadDebit(screenId, file, userid);

		return msg;

	}
	
	public List<MandateMaster> processUploadFilesMandate(String screenId, MultipartFile file, String userid)
			throws FileNotFoundException, SQLException, IOException {

		List<MandateMaster> msg = mandateServices.processUploadMandate(screenId, file, userid);

		return msg;

	}

	
	
	public List<ManualTransaction> processUploadFilesManual(String screenId, MultipartFile file, String userid)
			throws FileNotFoundException, SQLException, IOException {

		List<ManualTransaction> msg = bulkServices.processUploadManual(screenId, file, userid);

		return msg;

	}
	
	public List<RTPTransactionTable> processUploadFilesRTP(String screenId, MultipartFile file, String userid)
			throws FileNotFoundException, SQLException, IOException {

		List<RTPTransactionTable> msg = bulkServices.processUploadRTP(screenId, file, userid);

		return msg;

	}


}
