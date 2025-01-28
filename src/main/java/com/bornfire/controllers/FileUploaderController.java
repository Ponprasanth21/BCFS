package com.bornfire.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.entity.BIPS_SWIFT_MSG_MGT;
import com.bornfire.entity.BipsSwiftMsgConversionRepo;
import com.bornfire.entity.BulkTransaction;
import com.bornfire.entity.CustomException;
import com.bornfire.entity.FolderRespModel;
import com.bornfire.entity.MandateMaster;
import com.bornfire.entity.ManualTransaction;
import com.bornfire.entity.MerchantCategoryCodeEntity;
import com.bornfire.entity.RTPTransactionTable;
import com.bornfire.entity.UserProfileRep;
import com.bornfire.services.UploadServices;

@RestController
public class FileUploaderController {
	// Save the uploaded file to this folder
	// private static String UPLOADED_FOLDER = "F://temp//";
	private final Logger logger = LoggerFactory.getLogger(FileUploaderController.class);

	@Autowired
	UploadServices uploadServices;

	@Autowired
	BipsSwiftMsgConversionRepo bipsSwiftMsgConversionRepo;

	@Autowired
	Environment env;

	@Autowired
	FolderRespModel folderRespModel;

	@Autowired
	UserProfileRep userProfileRep;

	private static String Srl;
	private static String Ref;

	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping(value = "/ws/fileUploadCredit")
	@ResponseBody
	public List<BulkTransaction> uploadFileCredit(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq)
			throws FileNotFoundException, SQLException, IOException {

		System.out.println("fileSize" + file.getSize());

		if (file.getSize() < 500000) {
			String userid = (String) rq.getSession().getAttribute("USERID");
			List<BulkTransaction> msg = uploadServices.processUploadFilesCredit(screenId, file, userid);
			return msg;
		} else {
			throw new CustomException("File has not been successfully uploaded. Requires less than 128 KB size.");
		}

	}

	@PostMapping(value = "/ws/fileUploadDebit")
	@ResponseBody
	public List<BulkTransaction> fileUploadDebit(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq)
			throws FileNotFoundException, SQLException, IOException {

		String userid = (String) rq.getSession().getAttribute("USERID");
		List<BulkTransaction> msg = uploadServices.processUploadFilesDebit(screenId, file, userid);
		return msg;
	}

	@PostMapping(value = "/ws/fileUploadMandate")
	@ResponseBody
	public List<MandateMaster> fileUploadMandate(@RequestParam("file") MultipartFile file,
			@RequestParam("mandateServices") String screenId, HttpServletRequest rq)
			throws FileNotFoundException, SQLException, IOException {

		String userid = (String) rq.getSession().getAttribute("USERID");
		List<MandateMaster> msg = uploadServices.processUploadFilesMandate(screenId, file, userid);
		return msg;
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	@PostMapping(value = "/ws/fileUploadManual")
	@ResponseBody
	public List<ManualTransaction> uploadFileManual(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq)
			throws FileNotFoundException, SQLException, IOException {

		System.out.println("fileSize" + file.getSize());

		if (file.getSize() < 500000) {
			String userid = (String) rq.getSession().getAttribute("USERID");
			List<ManualTransaction> msg = uploadServices.processUploadFilesManual(screenId, file, userid);
			return msg;
		} else {
			throw new CustomException("File has not been successfully uploaded. Requires less than 128 KB size.");
		}

	}

	@PostMapping(value = "/ws/fileUploadRTP")
	@ResponseBody
	public List<RTPTransactionTable> uploadFileRTP(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq)
			throws FileNotFoundException, SQLException, IOException {

		System.out.println("fileSize" + file.getSize());

		if (file.getSize() < 500000) {
			String userid = (String) rq.getSession().getAttribute("USERID");
			List<RTPTransactionTable> msg = uploadServices.processUploadFilesRTP(screenId, file, userid);
			return msg;
		} else {
			throw new CustomException("File has not been successfully uploaded. Requires less than 128 KB size.");
		}

	}

	@PostMapping(value = "/ws/fileUploadMX")
	@ResponseBody
	public List<BIPS_SWIFT_MSG_MGT> fileUploadMX(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq, Model md)
			throws FileNotFoundException, SQLException, IOException {
		System.out.println("fileSize" + file.getSize());

		System.out.println("fileSize" + file.getName());
		System.out.println(file.getOriginalFilename());

		System.out.println("fileSize" + file.getSize());
		Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		System.out.println("Srl number" + Srl);
		List<BIPS_SWIFT_MSG_MGT> dataList = new ArrayList<>();
		BIPS_SWIFT_MSG_MGT bipsData = new BIPS_SWIFT_MSG_MGT();
		bipsData.setDate_of_process(new Date());
		bipsData.setFile_name(env.getProperty("swift.mx.out.file.path") + file.getOriginalFilename());
		bipsData.setSwift_msg_type("MX");
		bipsData.setSrl_no(Srl);
		bipsData.setSwift_mrg_type_conv("MT");
		bipsData.setStatus("Inprogress");
		dataList.add(bipsData);

		return dataList;

	}

	@PostMapping(value = "/ws/fileUploadMT")
	@ResponseBody
	public List<BIPS_SWIFT_MSG_MGT> fileUploadMT(@RequestParam("file") MultipartFile file,
			@RequestParam("bulkServices") String screenId, HttpServletRequest rq, Model md)
			throws FileNotFoundException, SQLException, IOException {
		System.out.println("fileSize1" + file.getSize());

		System.out.println("fileSize1" + file.getName());
		System.out.println(file.getOriginalFilename());

		System.out.println("fileSize1" + file.getSize());
		Srl = bipsSwiftMsgConversionRepo.getNextSeriesId();
		// Ref = bipsSwiftMsgConversionRepo.getNextrefId();
		System.out.println("Srl number" + Srl);

		List<BIPS_SWIFT_MSG_MGT> dataList = new ArrayList<>();
		BIPS_SWIFT_MSG_MGT bipsData = new BIPS_SWIFT_MSG_MGT();
		bipsData.setDate_of_process(new Date());
		bipsData.setFile_name(env.getProperty("swift.mt.out.file.path") + file.getOriginalFilename());
		bipsData.setSwift_msg_type("MT");

		bipsData.setSrl_no(Srl);
		bipsData.setSwift_mrg_type_conv("MX");
		bipsData.setStatus("Inprogress");
		dataList.add(bipsData);

		System.out.println("vla +++" + dataList);

		return dataList;

	}

	@PostMapping(value = "/ws/Finaclefoldervalue")
	@ResponseBody
	public String[] Folderread() {

		File file = new File("D:\\Source\\Finacle MT");
		// List of all files and directories

		String[] fileList = file.list();
		System.out.println("List of files  in the specified directory:");
		for (String name : fileList) {
			System.out.println(name);
		}

		return fileList;

	}

	@PostMapping(value = "/ws/Finaclefoldervalue51")
	@ResponseBody
	public List<FolderRespModel> Folderread51(@ModelAttribute MerchantCategoryCodeEntity bankAgentTable, Model md,
			HttpServletRequest req) {
		String userid1 = (String) req.getSession().getAttribute("USERID");

		String Country_code = userProfileRep.getCountrycode(userid1);

		String path = "";

		switch (Country_code) {
		// Case statements
		case "BWA":
			path = env.getProperty("bwa.swift.mt.out.file.path");

			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mt.out.file.path");

			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mt.out.file.path");

			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mt.out.file.path");

			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mt.out.file.path");

			break;
		case "MUS":
			path = env.getProperty("mus.swift.mt.out.file.path");
			// System.out.println(path+" KKKKKK");

			break;

		}

		File file = new File(path);
		// List of all files and directories

		String[] fileList = file.list();
		System.out.println("List of files  in the specified directory:");
//	      for(String name:fileList){
//	            System.out.println(name);
//	        }
		int i = 1;

		System.out.println(fileList[1]);
		System.out.println(fileList[3]);

		FolderRespModel files = new FolderRespModel();

		List<FolderRespModel> dataList = new ArrayList<>();
		for (i = 0; i < file.list().length; i++) {
			System.out.println("I :" + i);
			files.setFile_name(fileList[i]);
			files.setMsg_type("MT");
			files.setSource("Finacle MT");
			System.out.println("files :" + files.toString());
			// dataList.set(i, files);
			dataList.add(new FolderRespModel(fileList[i], "MT", "Finacle MT"));
		}
		System.out.println("fff" + dataList);

		return dataList;

	}

	@PostMapping(value = "/ws/Finaclefoldervalue22")
	@ResponseBody
	public List<BIPS_SWIFT_MSG_MGT> Folderread21() {

		File file = new File("D:\\Source\\Finacle MT");
		// List of all files and directories

		String[] fileList = file.list();
		List<BIPS_SWIFT_MSG_MGT> dataList = new ArrayList<>();
		BIPS_SWIFT_MSG_MGT bipsData = new BIPS_SWIFT_MSG_MGT();
		System.out.println("List of files  in the specified directory:");
		ArrayList<String> arr = new ArrayList<>();
		for (String name : fileList) {

			arr.add(name);
			bipsData.setDate_of_process(new Date());
			// bipsData.setFile_name(env.getProperty("swift.mt.out.file.path")+file.getOriginalFilename());
			bipsData.setFile_name(name);
			bipsData.setSwift_msg_type("MT");

			bipsData.setSrl_no(Srl);
			bipsData.setSwift_mrg_type_conv("MX");
			bipsData.setStatus("Inprogress");

			dataList.add(bipsData);
			System.out.println(name);
		}
		System.out.println("oi" + arr);
		for (int i = 0; i < dataList.size(); i++) {
			System.out.println(dataList.get(i).toString());
		}

		return dataList;

	}

	@PostMapping(value = "/ws/SwiftfolderDetails")
	@ResponseBody
	public String[] Folderread2() {

		File file = new File("D:\\Source\\Swift Mx");
		// List of all files and directories

		String[] fileList = file.list();
		System.out.println("List of files  in the specified directory:");
		for (String name : fileList) {
			System.out.println(name);
		}

		return fileList;

	}

	@PostMapping(value = "/ws/SwiftfolderDetails2")
	@ResponseBody
	public List<FolderRespModel> Folderread53(@ModelAttribute MerchantCategoryCodeEntity bankAgentTable, Model md,
			HttpServletRequest req) {
		String userid1 = (String) req.getSession().getAttribute("USERID");

		String Country_code = userProfileRep.getCountrycode(userid1);

		String path = "";

		env.getProperty("bwa.swift.mx.in.file.path");
		switch (Country_code) {
		// Case statements
		case "BWA":
			path = env.getProperty("bwa.swift.mx.in.file.path");

			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mx.in.file.path");

			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mx.in.file.path");

			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mx.in.file.path");

			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mx.in.file.path");

			break;
		case "MUS":
			path = env.getProperty("mus.swift.mx.in.file.path");
			// System.out.println(path+ " LLLLL");

			break;

		}

		File file = new File(path);
		// List of all files and directories

		String[] fileList = file.list();
		System.out.println("List of files  in the specified directory:");
//	      for(String name:fileList){
//	            System.out.println(name);
//	        }
		int i = 1;

		FolderRespModel files = new FolderRespModel();

		List<FolderRespModel> dataList = new ArrayList<>();
		for (i = 0; i < file.list().length; i++) {
			System.out.println("I :" + i);
			files.setFile_name(fileList[i]);
			files.setMsg_type("MX");
			files.setSource("Swift MX");
			System.out.println("files :" + files.toString());
			// dataList.set(i, files);
			dataList.add(new FolderRespModel(fileList[i], "MX", "Finacle MT"));
		}
		System.out.println("fff" + dataList);

		return dataList;

	}

	public static String Serial() {
		return Srl;
	}

	public static String Reference() {
		return Ref;
	}



	@GetMapping("/get-files")
	public Map<String, Object> getFilesAndFolders(@RequestParam(required = false) String folderPath) {
		System.out.println(folderPath);
		Map<String, Object> response = new HashMap<>();
		List<Map<String, Object>> files = new ArrayList<>();

		try {
			File rootFolder = new File(folderPath);

			if (rootFolder.isDirectory()) {
				for (File file : rootFolder.listFiles()) {
					if (file.isFile()) {
						// File details
						Map<String, Object> fileDetails = new HashMap<>();
						fileDetails.put("name", file.getName());
						fileDetails.put("size", file.length() / 1024.0); // Size in KB
						fileDetails.put("modified", file.lastModified());
						files.add(fileDetails);
					} 
				}
				response.put("status", "success");
				response.put("files", files);
			} else {
				response.put("status", "error");
				response.put("message", "The specified path is not a folder.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "error");
			response.put("message", "An error occurred while reading the folder: " + e.getMessage());
		}

		return response;
	}
}
