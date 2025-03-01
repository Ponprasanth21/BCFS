package com.bornfire.configuration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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

    //@Scheduled(fixedRate = 5000)
    public void run() throws IOException, ParseException {
        processMTOutDirectory();
        processMXInDirectory();
    }

    private void processMTOutDirectory() throws IOException, ParseException {
        String usr = "Auto";
        File directoryPath = new File(env.getProperty("auto.swift.mt.out.file.path"));

        if (directoryPath.exists() && directoryPath.isDirectory()) {
            File[] filesList = directoryPath.listFiles();

            if (filesList != null) {
                boolean hasValidFiles = true;

                for (File file : filesList) {
                    if (!file.getName().endsWith(".DONE")) {
                        hasValidFiles = true;
                        break;
                    }
                }

                if (hasValidFiles) {
                    for (File file : filesList) {
                        if (file.getName().endsWith(".DONE")) {
                            continue;
                        }

                        System.out.println("Processing File: " + file.getName());
                        String filename = file.getName();

                        // MT to MX Processing
                        String msg = iPSRestController.AutoFilepicker(usr, filename);
                        System.out.println(msg);

                        // Rename the processed file
                        File renamedFile = new File(file.getParent(), filename + ".DONE");
                        if (file.renameTo(renamedFile)) {
                            System.out.println("File renamed to: " + renamedFile.getName());
                        }
                    }
                } else {
                    System.out.println("No Files Present in this source");
                }
            }
        } else {
            System.out.println("Invalid MT Out source directory: " + env.getProperty("auto.swift.mt.out.file.path"));
        }
    }

    private void processMXInDirectory() throws IOException, ParseException {
        String usr = "Auto_MUS";
        File directoryPath = new File(env.getProperty("auto.swift.mx.in.file.path"));

        if (directoryPath.exists() && directoryPath.isDirectory()) {
            File[] filesList = directoryPath.listFiles();

            if (filesList != null) {
                boolean hasValidFiles = false;

                for (File file : filesList) {
                    if (!file.getName().endsWith(".DONE")) {
                        hasValidFiles = true;
                        break;
                    }
                }

                if (hasValidFiles) {
                    for (File file : filesList) {
                        if (file.getName().endsWith(".DONE")) {
                            continue;
                        }

                        System.out.println("Processing File: " + file.getName());
                        String filename = file.getName();

                        // MX to MT Processing
                        String msg2 = iPSRestController.AutoFilepicker2(usr, filename);
                        System.out.println(msg2);

                        // Rename the processed file
                        File renamedFile = new File(file.getParent(), filename + ".DONE");
                        if (file.renameTo(renamedFile)) {
                            System.out.println("File renamed to: " + renamedFile.getName());
                        }
                    }
                } else {
                    System.out.println("No Files Present in this source2");
                }
            }
        } else {
            System.out.println("Invalid MX In source directory: " + env.getProperty("auto.swift.mx.in.file.path"));
        }
    }
}
