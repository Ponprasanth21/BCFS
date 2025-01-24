package com.bornfire.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bornfire.mt.MT_103;
import com.bornfire.util.MtMessageReader;

public class SwiftParser {
	private String swiftMessage;

	public SwiftParser(final String message) {
		this.swiftMessage = message;
	}

	public MT_103 parseMT103() {
		String tag20Value = "";
		String tag23BValue = "";
		String tag32AValue = "";
		String tag33BValue="";
		String tag50aValue = "";
		String tag50kValue = "";
		String tag50fValue = "";
		String tag52aValue = "";
		String tag53bValue = "";
		String tag57aValue = "";
		String tag59Value = "";
		String tag70Value="";
		String tag71AValue = "";
		final MT_103 mt103 = new MT_103();
		
		System.out.println("swift->"+this.swiftMessage);
		
		final String[] arrOfStr = this.swiftMessage.split(":");
		System.out.println("lensth->"+arrOfStr.length);
		for (int i = 0; i < arrOfStr.length; ++i) {
			if (arrOfStr[i].equals("20")) {
				tag20Value = arrOfStr[i + 1];
				mt103.setTag20(tag20Value);
				System.out.println("Tag 20 Value - " + tag20Value);
			} else if (arrOfStr[i].equals("23B")) {
				tag23BValue = arrOfStr[i + 1];
				mt103.setTag23B(tag23BValue);
				System.out.println("Tag 23B Value - " + tag23BValue);
			} else if (arrOfStr[i].equals("32A")) {
				tag32AValue = arrOfStr[i + 1];
				mt103.setTag32A(tag32AValue);
				System.out.println("Tag 32A Value - " + tag32AValue);
			} else if (arrOfStr[i].equals("33B")) {
				tag33BValue = arrOfStr[i + 1];
				mt103.setTag33B(tag33BValue);
				System.out.println("Tag 32A Value - " + tag32AValue);
			} else if (arrOfStr[i].equals("50K")) {
				tag50kValue = arrOfStr[i + 1];
				mt103.setTag50K(tag50kValue);
				System.out.println("Tag 50 Value - " + tag50kValue);
			}else if (arrOfStr[i].equals("50A")) {
				tag50aValue = arrOfStr[i + 1];
				mt103.setTag50K(tag50aValue);
				System.out.println("Tag 50 Value - " + tag50aValue);
			}else if (arrOfStr[i].equals("50F")) {
				tag50fValue = arrOfStr[i + 1];
				mt103.setTag50F(tag50fValue);
				System.out.println("Tag 50 Value - " + tag50fValue);
			} else if (arrOfStr[i].equals("52A")) {
				tag52aValue = arrOfStr[i + 1];
				mt103.setTag52A(tag52aValue);
				System.out.println("Tag 52A Value - " + tag52aValue);
			}else if (arrOfStr[i].equals("53B")) {
				tag53bValue = arrOfStr[i + 1];
				mt103.setTag53B(tag53bValue);
				System.out.println("Tag 53B Value - " + tag53bValue);
			}else if (arrOfStr[i].equals("57A")) {
				tag57aValue = arrOfStr[i + 1];
				mt103.setTag57A(tag57aValue);
				System.out.println("Tag 57A Value - " + tag57aValue);
			} else if (arrOfStr[i].equals("59")) {
				tag59Value = arrOfStr[i + 1];
				mt103.setTag59(tag59Value);
				System.out.println("Tag 59 Value - " + tag59Value);
			} else if (arrOfStr[i].equals("70")) {
				tag70Value = arrOfStr[i + 1];
				mt103.setTag70(tag70Value);
				System.out.println("Tag 70 Value - " + tag70Value);
			} else if (arrOfStr[i].equals("71A")) {
				tag71AValue = arrOfStr[i + 1];
				mt103.setTag71A(tag71AValue);
				System.out.println("Tag 71A Value - " + tag71AValue);
			}
		}
		return mt103;
	}

}
