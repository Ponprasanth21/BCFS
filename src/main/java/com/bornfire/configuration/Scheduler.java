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

	@Scheduled(fixedRate = 5000)
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

////////////////////////////////////////////////////////////////////////////////////// MX to MT//////////////////////////////////////////////////////////////////

		File directoryPath2 = new File(env.getProperty("auto.swift.mx.in.file.path"));
		usr = "Auto_MUS";

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

	}
}
