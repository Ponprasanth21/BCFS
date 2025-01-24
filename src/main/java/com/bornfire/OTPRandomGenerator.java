package com.bornfire;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OTPRandomGenerator {
	private static final String CHAR_LIST = "1234567890";

	private static final int RANDOM_STRING_LENGTH = 6;

	public String generateRandomString() {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	public int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());

		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public String main() {

		String s1 = generateRandomString();

		return s1;
	}
}