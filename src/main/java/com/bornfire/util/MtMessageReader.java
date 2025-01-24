package com.bornfire.util;

import org.springframework.stereotype.Component;

public class MtMessageReader {
	
private String inputMessage;
    
    public MtMessageReader(final String message) {
        this.inputMessage = message;
    }
    
    public String getSwiftMsgBlock(final int blockNumber) {
        String mtMsgBlock = "";
        switch (blockNumber) {
            case 1: {
                mtMsgBlock = this.getSwiftMessage(this.inputMessage.indexOf("{1:"), this.inputMessage.indexOf("{2:"));
                break;
            }
            case 2: {
                mtMsgBlock = this.getSwiftMessage(this.inputMessage.indexOf("{2:"), this.inputMessage.indexOf("{3:"));
                break;
            }
            case 3: {
                mtMsgBlock = this.getSwiftMessage(this.inputMessage.indexOf("{3:"), this.inputMessage.indexOf("{4:"));
                break;
            }
            case 4: {
//                mtMsgBlock = this.getSwiftMessage(this.inputMessage.indexOf("{4:"), this.inputMessage.indexOf("{5:"));
            	  mtMsgBlock = this.getSwiftMessage(this.inputMessage.indexOf("{4:"), this.inputMessage.indexOf("-}"));
                break;
            }
            default: {
                mtMsgBlock = "Invalid Message Block Numer Provided";
                break;
            }
        }
        return mtMsgBlock;
    }
    
    private String getSwiftMessage(final int startIndex, final int endIndex) {
    	
        return this.inputMessage.substring(startIndex + 3, endIndex - 1);
    }
}
