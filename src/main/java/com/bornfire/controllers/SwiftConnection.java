package com.bornfire.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bornfire.entity.BIPS_SWIFT_MSG_MGT;
import com.bornfire.entity.BIPS_SWIFT_MT_MSG;
import com.bornfire.entity.BIPS_SWIFT_MX_MSG;
import com.bornfire.modal.MTtoMXresponse;
import com.bornfire.mt.MT_103;
import com.bornfire.mx.datapduHeader.Header;
import com.bornfire.mx.head_001_001_01.BusinessApplicationHeaderV01;
import com.bornfire.mx.pacs_008_001_08.AccountIdentification4Choice1;
import com.bornfire.mx.pacs_008_001_08.CashAccount381;
import com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391;
import com.bornfire.mx.pacs_008_001_08.Document;
import com.bornfire.mx.pacs_008_001_08.FIToFICustomerCreditTransferV08;
import com.bornfire.mx.pacs_008_001_08.GenericAccountIdentification11;
import com.bornfire.mx.pacs_008_001_08.GroupHeader931;
import com.bornfire.parser.AppHeader;
import com.bornfire.parser.DocumentPacks;
import com.bornfire.parser.SwiftParser;
import com.bornfire.util.MtMessageReader;

@Component
public class SwiftConnection {

	@Autowired
	DocumentPacks documentPacks;

	@Autowired
	BipsMsgConversionProcessRec bipsMsgConversionProcessRec;

	@Autowired
	AppHeader appheader;

	public MTtoMXresponse mtToMxConverter(BIPS_SWIFT_MSG_MGT message,  String userID) throws IOException, ParseException {

		// String content = new String(file.getBytes(), StandardCharsets.UTF_8);
		String request = readFile(message.getFile_name());

		final MtMessageReader inputMessageReader = new MtMessageReader(request);
		final String inputMessageDataBlock = inputMessageReader.getSwiftMsgBlock(4);
		final String applicationHeaderBlock = inputMessageReader.getSwiftMsgBlock(2);
		final String applicationHeaderBlock1 = inputMessageReader.getSwiftMsgBlock(1);
		final String applicationHeaderBlock3 = inputMessageReader.getSwiftMsgBlock(3);
		final String applicationHeaderBlock5 = inputMessageReader.getSwiftMsgBlock(5);

		System.out.println("Application Header Block : " + applicationHeaderBlock);
		final String messageType = getMessageType(applicationHeaderBlock);
		System.out.println("Message Type : " + messageType);
		final SwiftParser swiftParser = new SwiftParser(inputMessageDataBlock);
		// MT_103 mt103 = null;
		final String s;
		String userid = userID;

		MTtoMXresponse response = null;

		switch (s = messageType) {
		case "103": {
			MT_103 mt103 = swiftParser.parseMT103();
			String data = documentPacks.getDataPDU008(mt103,applicationHeaderBlock1,applicationHeaderBlock,applicationHeaderBlock3,applicationHeaderBlock5,userid);
			// Mt msg here
			
            message.getDate_of_process();
			/// Mt table submit
			BIPS_SWIFT_MT_MSG mtmessagedata = new BIPS_SWIFT_MT_MSG();

			mtmessagedata.setSender_reference(mt103.getTag20());
			mtmessagedata.setBank_operation_code(mt103.getTag23B());
			mtmessagedata.setCurrency(mt103.getTag32A().substring(0, 3));
			String Amt = mt103.getTag32A();
			
			System.out.println(Amt);
			mtmessagedata.setTotal_transaction_amount(Amt.substring(9, Amt.indexOf(",")));
			if (mt103.getTag50K() != null && mt103.getTag50K().length() >= 13) {
			    mtmessagedata.setDebitor_account(mt103.getTag50K().substring(0, 13));
			} else {
			    // Skip setting debitor_account and continue processing
			    System.out.println("Tag50K is null or too short, skipping...");
			}
			mtmessagedata.setCreditor_account(mt103.getTag59().substring(1, 14));
			if (mt103.getTag50K() != null && mt103.getTag50K().length() >= 13) {
			    mtmessagedata.setDebitor_account(mt103.getTag50K().substring(0, 13));
			    if (mt103.getTag50K().length() > 13) {
			        mtmessagedata.setRemittance_address(mt103.getTag50K().substring(13));
			    } else {
			        mtmessagedata.setRemittance_address(""); // Set empty if no remittance address
			    }
			} else {
			    System.out.println("Tag50K is null or too short, skipping...");
			}
			mtmessagedata.setBeneficiary_address(mt103.getTag59().substring(1, 14));
			// Check if Tag50K is null or empty before attempting to split
			if (mt103.getTag50K() != null && !mt103.getTag50K().isEmpty()) {
			    String[] linesd = mt103.getTag50K().split("\\r?\\n");

			    // You can now safely use the 'lines' array
			    // For example, if you need to set a value:
			    if (linesd.length > 1) {
			    	mtmessagedata.setCreditor_name(linesd[1]);  // Set creditor name
			    } else {
			        // Handle case where there aren't enough lines in Tag50K
			        System.out.println("Tag50K does not contain enough lines.");
			    }
			} else {
			    // If Tag50K is null or empty, skip processing or handle the situation
			    System.out.println("Tag50K is null or empty, skipping this entry.");
			}
		
			String[] dbtname = mt103.getTag59().split("\\r?\\n");
			System.out.println("dbt" + dbtname[1]);
			mtmessagedata.setDebitor_name(dbtname[1]);
			mtmessagedata.setDate_of_process(message.getDate_of_process());
			String msg = bipsMsgConversionProcessRec.MtmsgMTtoMX(mtmessagedata, message);
			System.out.println(msg);

			BIPS_SWIFT_MX_MSG mxmessagedata = new BIPS_SWIFT_MX_MSG();

			mxmessagedata.setSender_reference(mt103.getTag20());
			mxmessagedata.setBank_operation_code(mt103.getTag23B());
			mxmessagedata.setCurrency(Amt.substring(6, 9));
			mxmessagedata.setTotal_transaction_amount(Amt.substring(9, Amt.indexOf(",")));
			if (mt103.getTag50K() != null && mt103.getTag50K().length() >= 13) {
			    mxmessagedata.setDebitor_account(mt103.getTag50K().substring(0, 13));
			} else {
			    mxmessagedata.setDebitor_account(""); // Set empty string if value is null or too short
			    System.out.println("Tag50K is null or too short, skipping...");
			}
			mxmessagedata.setCreditor_account(mt103.getTag59().substring(1, 14));
			if (mt103.getTag50K() != null && mt103.getTag50K().length() > 13) {
			    mxmessagedata.setRemittance_address(mt103.getTag50K().substring(13));
			} else {
			    mxmessagedata.setRemittance_address("");
			}
			// Check if Tag59 is valid and has enough length to avoid errors
			if (mt103.getTag59() != null && mt103.getTag59().length() >= 14) {
			    mxmessagedata.setBeneficiary_address(mt103.getTag59().substring(1, 14));
			} else {
			    // If Tag59 is null or too short, log or skip processing
			    System.out.println("Tag59 is either null or too short, skipping this entry.");
			}

			// Check if Tag50K is valid and non-empty before splitting
			if (mt103.getTag50K() != null && !mt103.getTag50K().isEmpty()) {
			    String[] linesX = mt103.getTag50K().split("\\r?\\n");

			    // Ensure that linesX has enough data to safely access lines
			    if (linesX.length > 1) {
			        mxmessagedata.setCreditor_name(linesX[1]);  // Set creditor name
			    } else {
			        // If linesX has insufficient lines, skip processing
			        System.out.println("Tag50K has insufficient lines, skipping this entry.");
			    }
			} else {
			    // If Tag50K is null or empty, skip processing
			    System.out.println("Tag50K is null or empty, skipping this entry.");
			}

			// Check if Tag59 is valid and split it for debitor name
			if (mt103.getTag59() != null && mt103.getTag59().split("\\r?\\n").length > 1) {
			    String[] dbtnameX = mt103.getTag59().split("\\r?\\n");
			    mxmessagedata.setDebitor_name(dbtnameX[1]);  // Set debitor name
			} else {
			    // If Tag59 is null or doesn't have enough lines, skip processing
			    System.out.println("Tag59 doesn't have enough lines, skipping this entry.");
			}
			mxmessagedata.setDate_of_process(message.getDate_of_process());
			String msgX = bipsMsgConversionProcessRec.MxmsgMTtoMX(mxmessagedata, message);
			System.out.println(msgX);

			System.out.println("herer" + data);
			response = new MTtoMXresponse();
			response.setStatus("SUCCESS");
			response.setData(data);

			// return "dhhd";
			break;
		}

		case "202": {
			break;
		}
		default:
			break;
		}

		return response;
	}

	public String readFile(final String filePath) {
		String swiftInputMessage = "";
		try {
			swiftInputMessage = new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return swiftInputMessage;
	}

	private static String getMessageType(final String messageBlock) {
		return messageBlock.substring(1, 4);
	}

	public MTtoMXresponse mxToMtConverter(BIPS_SWIFT_MSG_MGT message, String userID) throws IOException {

		// String request = new String(message.getFile_name().getBytes(),
		// StandardCharsets.UTF_8);

		String request = readFile(message.getFile_name());

		BusinessApplicationHeaderV01 header008 = documentPacks.getPacs_008_001_01UnMarshalAppHeader(request);

		// GroupHeader931 groupHeader931 = doc008.getFIToFICstmrCdtTrf().getGrpHdr();
         System.out.println("message type"+header008.toString());
		String messageType = header008.getMsgDefIdr();
		String data = "";
		MTtoMXresponse response = null;

		// List<CreditTransferTransaction391>
		// creditTransLst=doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf();

		switch (messageType) {
		case "pacs.008.001.08":
			Document doc008 = documentPacks.getPacs_008_001_01UnMarshalDoc(request);
			
			Header headerDataPDU008 = documentPacks.getPacs_008_001_01UnMarshalDataPDUHeader(request);

			System.out.println("headerPart"+headerDataPDU008);
			/// Sender Reference
			String MsgId = doc008.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId();
			String DebtorName = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtr().getNm();
//			String DebitorAcc = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr()
//					.getId();
			
			String DebitorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getDbtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			
			
			
			String CreditorName = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getNm();
//			String CreditorAcc = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr()
//					.getId();
			
			String CreditorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getCdtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			
			
			String CreditorAgent = null;
			String DebitorAgent = null;

			if (doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt() != null &&
			    doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId() != null &&
			    doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getBICFI() != null) {
			    CreditorAgent = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getBICFI();
			}

			if (doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt() != null &&
			    doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId() != null &&
			    doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getBICFI() != null) {
			    DebitorAgent = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getBICFI();
			}

			// Process next fields if values are null
			System.out.println("Creditor Agent: " + (CreditorAgent != null ? CreditorAgent : "Skipped"));
			System.out.println("Debitor Agent: " + (DebitorAgent != null ? DebitorAgent : "Skipped"));

			//String Currency = doc008.getFIToFICstmrCdtTrf().getGrpHdr().getTtlIntrBkSttlmAmt().getCcy();
			String Currency = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy();
			String SenderReference = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getInstrId();
			//BigDecimal TotaltansAmount = doc008.getFIToFICstmrCdtTrf().getGrpHdr().getTtlIntrBkSttlmAmt().getValue();
			BigDecimal TotaltansAmount =doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue();
		
			//String BankoperatioCode = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm().getPrtry();
			String BankoperatioCode ="CRED";

			String bigdecicvt = TotaltansAmount.toString();
			data = documentPacks.getMT_100(doc008, header008,headerDataPDU008,userID);

			BIPS_SWIFT_MX_MSG mxmessgedata = new BIPS_SWIFT_MX_MSG();
			mxmessgedata.setDebitor_name(DebtorName);
			mxmessgedata.setDebitor_agent(DebitorAgent);
			mxmessgedata.setCreditor_name(CreditorName);
			mxmessgedata.setCreditor_agent(CreditorAgent);
			mxmessgedata.setCurrency(Currency);
			mxmessgedata.setSender_reference(SenderReference);
			mxmessgedata.setCreditor_account(CreditorAcc);
			mxmessgedata.setDebitor_account(DebitorAcc);
			mxmessgedata.setMsg_id(MsgId);
			mxmessgedata.setTotal_transaction_amount(bigdecicvt);
			mxmessgedata.setBank_operation_code(BankoperatioCode);
			mxmessgedata.setDate_of_process(message.getDate_of_process());

			String Msg = bipsMsgConversionProcessRec.savingMxMsgDetail(mxmessgedata, message);
			System.out.println(Msg);

			// System.out.println("Something" + data);
			/// Mt table submit
			BIPS_SWIFT_MT_MSG mtmessagedata = new BIPS_SWIFT_MT_MSG();
			mtmessagedata.setDebitor_name(DebtorName);
			mtmessagedata.setDebitor_agent(DebitorAgent);
			mtmessagedata.setCreditor_name(CreditorName);
			mtmessagedata.setCreditor_agent(CreditorAgent);
			mtmessagedata.setCurrency(Currency);
			mtmessagedata.setSender_reference(SenderReference);
			mtmessagedata.setCreditor_account(CreditorAcc);
			mtmessagedata.setDebitor_account(DebitorAcc);
			mtmessagedata.setMsg_id(MsgId);
			mtmessagedata.setTotal_transaction_amount(bigdecicvt);
			mtmessagedata.setBank_operation_code(BankoperatioCode);
			mtmessagedata.setDate_of_process(message.getDate_of_process());

			String mtsubmsg = bipsMsgConversionProcessRec.savingMtmsgdetail(mtmessagedata, message);
			System.out.println(mtsubmsg);

			response = new MTtoMXresponse();
			response.setStatus("SUCCESS");
			response.setData(data);
			System.out.println("down" + data);
			break;

		}

		return response;
	}



}
