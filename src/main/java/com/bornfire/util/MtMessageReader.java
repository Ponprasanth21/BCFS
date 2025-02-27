package com.bornfire.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

public class MtMessageReader {

	private String inputMessage;

	public MtMessageReader(final String message) {
		this.inputMessage = message;
	}

	public String getSwiftMsgBlock(final int blockNumber) {
		String mtMsgBlock = "";
		int startIndex = -1;
		int endIndex = -1;

		switch (blockNumber) {
		case 1:
			startIndex = inputMessage.indexOf("{1:");
			endIndex = inputMessage.indexOf("{4:") != -1 ? inputMessage.indexOf("{4:") : inputMessage.indexOf("{2:");
			break;
		case 2:
			startIndex = inputMessage.indexOf("{2:");
			endIndex = inputMessage.indexOf("{3:") != -1 ? inputMessage.indexOf("{3:") : inputMessage.indexOf("{4:");
			break;
		case 3:
			startIndex = inputMessage.indexOf("{3:");
			if (startIndex != -1) {
				endIndex = inputMessage.indexOf("{4:", startIndex);
				if (endIndex == -1)
					endIndex = inputMessage.indexOf("{5:", startIndex);
				if (endIndex == -1)
					endIndex = inputMessage.indexOf("-}", startIndex);
				if (endIndex == -1)
					endIndex = inputMessage.length();
			}
			break;
		case 4:
			return getSwiftMsgBlock4().stream().collect(Collectors.joining("\n"));
		case 5:
			startIndex = inputMessage.indexOf("{5:");
			if (startIndex != -1) {
				endIndex = inputMessage.lastIndexOf("}"); // Ensure it includes the full block
				if (endIndex == -1) {
					endIndex = inputMessage.length();
				} else {
					endIndex++; // Include closing brace
				}
			}
			break;
		default:
			return "Invalid Message Block Number Provided";
		}
		// Check for valid indices before calling substring
		if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
			mtMsgBlock = this.getSwiftMessage(startIndex, endIndex);
		} else {
			mtMsgBlock = "Invalid indices for block " + blockNumber;
		}
		return mtMsgBlock;
	}

	private String getSwiftMessage(final int startIndex, final int endIndex) {
		return this.inputMessage.substring(startIndex + 3, endIndex - 1);
	}

	public List<String> getSwiftMsgBlock4() {
		List<String> block4Messages = new ArrayList<>();
		int startIndex = inputMessage.indexOf("{4:");

		while (startIndex != -1) {
			int endIndex1 = inputMessage.indexOf("}{1:", startIndex);
			int endIndex2 = inputMessage.indexOf("{5:", startIndex);
			int endIndex3 = inputMessage.indexOf("-}", startIndex);

			int endIndex = Integer.MAX_VALUE;
			if (endIndex1 != -1)
				endIndex = Math.min(endIndex, endIndex1);
			if (endIndex2 != -1)
				endIndex = Math.min(endIndex, endIndex2);
			if (endIndex3 != -1)
				endIndex = Math.min(endIndex, endIndex3 + 2); // Include "-}"

			if (endIndex == Integer.MAX_VALUE) {
				endIndex = inputMessage.length();
			}

			block4Messages.add(inputMessage.substring(startIndex, endIndex).trim());
			startIndex = inputMessage.indexOf("{4:", endIndex);
		}

		return block4Messages;
	}
}
