package com.bornfire.util;

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
            case 1: {
                startIndex = this.inputMessage.indexOf("{1:");
                endIndex = this.inputMessage.indexOf("{2:");
                break;
            }
            case 2: {
                startIndex = this.inputMessage.indexOf("{2:");
                endIndex = this.inputMessage.indexOf("{3:");
                break;
            }
            case 3: {
                startIndex = this.inputMessage.indexOf("{3:");
                endIndex = this.inputMessage.indexOf("{4:");
                break;
            }
            case 4: {
                startIndex = this.inputMessage.indexOf("{4:");
                endIndex = this.inputMessage.indexOf("-}");
                break;
            }
            case 5: {
                startIndex = this.inputMessage.indexOf("{5:");
                endIndex = this.inputMessage.indexOf("-}");
                break;
            }
            default: {
                mtMsgBlock = "Invalid Message Block Number Provided";
                return mtMsgBlock;
            }
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
}
