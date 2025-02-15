package com.bornfire.configuration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bornfire.controllers.IPSRestController;
import com.bornfire.entity.BipsSwiftMsgConversionRepo;

@Component
public class Scheduler {

	@Autowired
	Environment env;

	@Autowired
	IPSRestController iPSRestController;

	@Autowired
	BipsSwiftMsgConversionRepo bipsSwiftMsgConversionRepo;

	/* @Scheduled(fixedRate = 5000) */
	public void run() throws IOException, ParseException {

		String usr = "Auto";
		File directoryPath = new File(env.getProperty("auto.swift.mt.out.file.path"));

		File filesList[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesList.length != 0) {
			for (File file : filesList) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
				//
				//
				//
				// MT to MX
				//
				//
				//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				// file.renameTo(new File("E:\\MessageConversion\\Out\\" +
				// file.getName()+file.lastModified()));
				// file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));
				// file.delete();
				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////
		try {
			String directoryPathStr = env.getProperty("auto.swift.mx.in.file.path");
			System.out.println("Directory Path: " + directoryPathStr);
			if (directoryPathStr == null) {
				System.err.println("Property 'auto.mus.swift.mt.out.file.path' is not set.");
				return; // or handle the error appropriately
			}

			directoryPath = new File(directoryPathStr);
			if (!directoryPath.exists() || !directoryPath.isDirectory()) {
				System.err.println("Directory does not exist or is not accessible: " + directoryPath.getAbsolutePath());
				return; // or handle the error appropriately
			}

			System.out.println("dddd" + directoryPath);
			usr = "Auto_MUS";

			File filesListAuto_MUS[] = directoryPath.listFiles();
			if (filesListAuto_MUS == null || filesListAuto_MUS.length == 0) {
				System.out.println("No Files Present in this source");
				return;
			}

			for (File fileAuto_MUS : filesListAuto_MUS) {
				System.out.println("File name: " + fileAuto_MUS.getName());
				String filename = fileAuto_MUS.getName();

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				fileAuto_MUS.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));
				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		directoryPath = new File(env.getProperty("auto.bwa.swift.mt.out.file.path"));
		usr = "Auto_BWA";

		File filesListAuto_BWA[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesListAuto_BWA.length != 0) {
			for (File file : filesListAuto_BWA) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
//
//
//
// MT to MX
//
//
//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));

				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath = new File(env.getProperty("auto.moz.swift.mt.out.file.path"));
		usr = "Auto_MOZ";

		File filesListAuto_MOZ[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesListAuto_MOZ.length != 0) {
			for (File file : filesListAuto_MOZ) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
//
//
//
// MT to MX
//
//
//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));

				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath = new File(env.getProperty("auto.mwi.swift.mt.out.file.path"));
		usr = "Auto_MWI";

		File filesListAuto_MWI[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesListAuto_MWI.length != 0) {
			for (File file : filesListAuto_MWI) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
//
//
//
// MT to MX
//
//
//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));

				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath = new File(env.getProperty("auto.zmb.swift.mt.out.file.path"));
		usr = "Auto_ZMB";

		File filesListAuto_ZMB[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesListAuto_ZMB.length != 0) {
			for (File file : filesListAuto_ZMB) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
//
//
//
// MT to MX
//
//
//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));

				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath = new File(env.getProperty("auto.zwe.swift.mt.out.file.path"));
		usr = "Auto_ZWE";

		File filesListAuto_ZWE[] = directoryPath.listFiles();
		// System.out.println("List of files :");
		if (filesListAuto_ZWE.length != 0) {
			for (File file : filesListAuto_ZWE) {

				System.out.println("File name: " + file.getName());
				String filename = file.getName();
//
//
//
//MT to MX
//
//
//

				String msg = iPSRestController.AutoFilepicker(usr, filename);
				System.out.println(msg);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename2 = "Completed_MT" + date + ".IN";
				file.renameTo(new File(env.getProperty("after.conv.mt.file.output") + filename2));

				System.out.println("delete" + date);
				System.out.println(" ");
			}
		} else {
			System.out.println("No Files Present in this source");

		}

////////////////////////////////////////////////////////////////////////////////////// MX to MT//////////////////////////////////////////////////////////////////

		File directoryPath2 = new File(env.getProperty("auto.swift.mx.in.file.path"));
		usr = "Auto";

		File filesList1[] = directoryPath2.listFiles();
		System.out.println(filesList1.length);
		// System.out.println("List of files :");

		if (filesList1.length != 0) {
			for (File file1 : filesList1) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
				//
				//
				//
				// MX to MT
				//
				//
				//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename22 = "Completed_MX" + date + ".OUT";
				// file.renameTo(new File("E:\\MessageConversion\\Out\\" +
				// file.getName()+file.lastModified()));
				// file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
				// file1.delete();
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		directoryPath2 = new File(env.getProperty("auto.mus.swift.mx.in.file.path"));
		usr = "Auto_MUS";

		File filesList1Auto_MUS[] = directoryPath2.listFiles();
		System.out.println(filesList1.length);
		// System.out.println("List of files :");

		if (filesList1Auto_MUS.length != 0) {
			for (File file1 : filesList1Auto_MUS) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
				//
				//
				//
				// MX to MT
				//
				//
				//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename22 = "Completed_MX" + date + ".OUT";
				// file.renameTo(new File("E:\\MessageConversion\\Out\\" +
				// file.getName()+file.lastModified()));
				// file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
				// file1.delete();
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath2 = new File(env.getProperty("auto.bwa.swift.mx.in.file.path"));
		usr = "Auto_BWA";

		File filesList1Auto_BWA[] = directoryPath2.listFiles();
		System.out.println(filesList1.length);
		// System.out.println("List of files :");

		if (filesList1Auto_BWA.length != 0) {
			for (File file1 : filesList1Auto_BWA) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
				//
				//
				//
				// MX to MT
				//
				//
				//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename22 = "Completed_MX" + date + ".OUT";
				// file.renameTo(new File("E:\\MessageConversion\\Out\\" +
				// file.getName()+file.lastModified()));
				// file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
				// file1.delete();
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath2 = new File(env.getProperty("auto.moz.swift.mx.in.file.path"));
		usr = "Auto_MOZ";

		File filesList1Auto_MOZ[] = directoryPath2.listFiles();
		System.out.println(filesList1.length);
		// System.out.println("List of files :");

		if (filesList1Auto_MOZ.length != 0) {
			for (File file1 : filesList1Auto_MOZ) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
//
//
//
// MX to MT
//
//
//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename22 = "Completed_MX" + date + ".OUT";
// file.renameTo(new File("E:\\MessageConversion\\Out\\" +
// file.getName()+file.lastModified()));
// file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
// file1.delete();
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath2 = new File(env.getProperty("auto.mwi.swift.mx.in.file.path"));
		usr = "Auto_MWI";

		File filesList1Auto_MWI[] = directoryPath2.listFiles();
		System.out.println(filesList1.length);
		// System.out.println("List of files :");

		if (filesList1Auto_MWI.length != 0) {
			for (File file1 : filesList1Auto_MWI) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
//
//
//
//MX to MT
//
//
//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);

				String filename22 = "Completed_MX" + date + ".OUT";
//file.renameTo(new File("E:\\MessageConversion\\Out\\" +
//file.getName()+file.lastModified()));
//file.renameTo((new File("E:\\MessageConversion\\Out\\newFile1.txt"+Count)));
//file1.delete();
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath2 = new File(env.getProperty("auto.zmb.swift.mx.in.file.path"));
		usr = "Auto_ZMB";

		File filesList1Auto_ZMB[] = directoryPath2.listFiles();

		if (filesList1Auto_ZMB.length != 0) {
			for (File file1 : filesList1Auto_ZMB) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
//
//
//
//MX to MT
//
//
//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);
				String filename22 = "Completed_MX" + date + ".OUT";
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		directoryPath2 = new File(env.getProperty("auto.zwe.swift.mx.in.file.path"));
		usr = "Auto_ZWE";

		File filesList1Auto_ZWE[] = directoryPath2.listFiles();

		if (filesList1Auto_ZWE.length != 0) {
			for (File file1 : filesList1Auto_ZWE) {

				System.out.println("File name: " + file1.getName());
				String filename2 = file1.getName();
				String msg2 = iPSRestController.AutoFilepicker2(usr, filename2);
//
//
//
//MX to MT
//
//
//
				System.out.println(msg2);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
				LocalDateTime now = LocalDateTime.now();
				String date = dtf.format(now);
				String filename22 = "Completed_MX" + date + ".OUT";
				file1.renameTo(new File(env.getProperty("after.conv.mx.file.output") + filename22));
				System.out.println("delete");
				System.out.println(" ");

			}
		} else {
			System.out.println("No Files Present in this source2");
		}

	}
}
