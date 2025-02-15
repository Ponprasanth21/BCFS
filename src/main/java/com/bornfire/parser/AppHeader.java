package com.bornfire.parser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.persistence.metamodel.SetAttribute;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.bornfire.mx.pacs_008_001_08.AccountIdentification4Choice1;
import com.bornfire.mx.pacs_008_001_08.ActiveOrHistoricCurrencyAndAmount;
import com.bornfire.mx.pacs_008_001_08.CashAccount381;
import com.bornfire.mx.pacs_008_001_08.FIToFICustomerCreditTransferV08;
import com.bornfire.mx.pacs_008_001_08.GenericAccountIdentification11;
import com.bornfire.mx.pacs_008_001_08.PartyIdentification1351;
import com.bornfire.entity.BIPS_SWIFT_MT_MSG;
import com.bornfire.mx.datapduHeader.Header;
import com.bornfire.mx.head_001_001_01.BusinessApplicationHeaderV01;
import com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61;
import com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391;
import com.bornfire.mx.pacs_008_001_08.Document;
import com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181;
import com.bornfire.mx.pacs_008_001_08.GroupHeader931;
import com.bornfire.mx.pacs_008_001_08.PostalAddress241;
import com.google.common.base.Strings;

@Component
public class AppHeader {

	private static String senderreference = "";
	private static String bankoperationcode = "";
	private static String instructioncode = "";
	private static String transactiontypecode = "";
	private static String interbanksettledamount = "";
	private static String instructedamount = "";
	private static String exchangerate = "";
	private static String orderingcustomer = "";
	private static String orderinginstitution = "";
	private static String senderscorrespondent = "";
	private static String receiverscorrespondent = "";
	private static String third_reimbursementinstitution = "";
	private static String intermediaryinstitution = "";
	private static String account_withinstitution = "";
	private static String beneficairycustomer = "";
	private static String remittanceinformation = "";
	private static String detailsofcharges = "";
	private static String senderscharges = "";
	private static String sendertoreceiverinformation = "";

	private static String s = "";
	private static String t = "";

	public String createApplicationHeader(Document doc008, BusinessApplicationHeaderV01 appHeader008,Header header) {

		StringBuilder strBuilder = new StringBuilder();

		if(header.getMessage().getSubFormat().equals("Output")) {
			/***********************
			 * Basic Header Start
			 ***************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{1:");

			/// APP ID
			/*
			 * The Application Identifier identifies the application within which the
			 * message is being sent or received. The available options are: F = FIN All
			 * user-to-user, FIN system and FIN service messages A = GPA (General Purpose
			 * Application) Most GPA system and service messages L = GPA Certain GPA service
			 * messages, for example, LOGIN, LAKs, ABORT These values are automatically
			 * assigned by the SWIFT system and the user's CBT
			 */
			strBuilder.append("F");

			/// Service ID
			/*
			 * The Service Identifier consists of two numeric characters. It identifies the
			 * type of data that is being sent or received and, in doing so, whether the
			 * message which follows is one of the following: a user-to-user message, a
			 * system message, a service message, for example, a session control command,
			 * such as SELECT, or a logical acknowledgment, such as ACK/SAK/UAK. Possible
			 * values are 01 = FIN/GPA or 21 = ACK/NAK.
			 */

			strBuilder.append("01");

			/// LT Address
			/*
			 * The Logical Termial (LT) Address is a 12-character FIN address. It is the
			 * address of the sending LT for input messages or of the receiving LT for
			 * output messages, and includes the Branch Code. It consists of: - the BIC 8
			 * CODE (8 characters) - the Logical Terminal Code (1 upper case alphabetic
			 * character) - the BIC Branch Code (3 characters)
			 */
			

			
			strBuilder.append(Strings.padEnd(appHeader008.getTo().getFIId().getFinInstnId().getBICFI(), 12,'X'));

			/// Session Number
			/*
			 * The Session Number identifies the session in which the message was
			 * transmitted. It is a four digits number that is automatically generated by
			 * the user's computer and padded with zeros.
			 */
			strBuilder.append("1111");

			/// Sequence Bumber
			/*
			 * The sequence number always consists of six digits. It is the Input Sequence
			 * Number (ISN) of the sender's current input session or the Output Sequence
			 * Number (OSN) of the receiver's current output session. It is automatically
			 * generated by the user's computer and padded with zeros.
			 */
			strBuilder.append("987654");

			/// End of Block Indicator
			strBuilder.append("}");

			/****************************
			 * Basic HeaderEnd
			 ******************************************************/

			/****************************
			 * Application Header Start
			 *********************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{2:");

			/// Input/Output ID
			/*
			 * For an input message, the Input/Output Identifier consists of the single
			 * letter 'I'
			 */
			strBuilder.append("O");

			/// SWIFT Message Type
			/*
			 * The Message Type consists of 3 digits which define the MT number of the
			 * message being input
			 */
			strBuilder.append("103");
			
			///Swift LocalTime
			strBuilder.append(new SimpleDateFormat("HHmmyyMMdd").format(new Date()));
			


			/// Destination Address
			/*
			 * This address is the 12-character SWIFT address of the receiver of the
			 * message. It defines the destination to which the message should be sent.
			 */

			strBuilder.append(Strings.padEnd(appHeader008.getFr().getFIId().getFinInstnId().getBICFI(), 12,'X'));
			
			///Session Number
			strBuilder.append("0000");
			
			///Sequence Number
			strBuilder.append("111111");

			///The output date, local to the receiver	
			strBuilder.append(new SimpleDateFormat("yyMMddHHmm").format(new Date()));
			
			/// Priority
			/*
			 * This character, used within FIN Application Headers only, defines the
			 * priority with which a message is delivered. The possible values are S =
			 * System U = Urgent N = Normal
			 */
			if(header.getMessage().getNetworkInfo().getPriority().equals("Normal")) {
				strBuilder.append("N");
			}else if(header.getMessage().getNetworkInfo().getPriority().equals("Urgent")) {
				strBuilder.append("U");
			}else if(header.getMessage().getNetworkInfo().getPriority().equals("System")) {
				strBuilder.append("S");
			}

			/// Delivery Monitoring
			/*
			 * Delivery monitoring options apply only to FIN user-to-user messages. The
			 * chosen option is expressed as a single digit: 1 = Non-Delivery Warning 2 =
			 * Delivery Notification 3 = Non-Delivery Warning and Delivery Notification If
			 * the message has priority 'U', the user must request delivery monitoring
			 * option '1' or '3'. If the message has priority 'N', the user can request
			 * delivery monitoring option '2' or, by leaving the option blank, no delivery
			 * monitoring
			 */

			//strBuilder.append("3");

			/// Obsolescence Period
			/*
			 * The obsolescence period defines the period of time after which a Delayed
			 * Message (DLM) trailer is added to a FIN user-to-user message when the message
			 * is delivered. For urgent priority messages, it is also the period of time
			 * after which, if the message remains undelivered, a Non-Delivery Warning is
			 * generated. The values for the obsolescence period are: 003 (15 minutes) for
			 * 'U' priority, and 020 (100 minutes) for 'N' priority.
			 */

			//strBuilder.append("003");

			/// End of block indicator
			strBuilder.append("}");

			/****************************
			 * Application Header End
			 *********************************************/

			/****************************
			 * User Header Start
			 **************************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			 strBuilder.append("{3:");
			 
			 ///add 121
			 strBuilder.append("{121:"+doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getUETR()+"}");
			 
			 strBuilder.append("}");

			/// Tag103 - Service Identifier
			/**/
			/****************************
			 * User Header End
			 ****************************************************/


		}else {
			/***********************
			 * Basic Header Start
			 ***************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{1:");

			/// APP ID
			/*
			 * The Application Identifier identifies the application within which the
			 * message is being sent or received. The available options are: F = FIN All
			 * user-to-user, FIN system and FIN service messages A = GPA (General Purpose
			 * Application) Most GPA system and service messages L = GPA Certain GPA service
			 * messages, for example, LOGIN, LAKs, ABORT These values are automatically
			 * assigned by the SWIFT system and the user's CBT
			 */
			strBuilder.append("F");

			/// Service ID
			/*
			 * The Service Identifier consists of two numeric characters. It identifies the
			 * type of data that is being sent or received and, in doing so, whether the
			 * message which follows is one of the following: a user-to-user message, a
			 * system message, a service message, for example, a session control command,
			 * such as SELECT, or a logical acknowledgment, such as ACK/SAK/UAK. Possible
			 * values are 01 = FIN/GPA or 21 = ACK/NAK.
			 */

			strBuilder.append("01");

			/// LT Address
			/*
			 * The Logical Termial (LT) Address is a 12-character FIN address. It is the
			 * address of the sending LT for input messages or of the receiving LT for
			 * output messages, and includes the Branch Code. It consists of: - the BIC 8
			 * CODE (8 characters) - the Logical Terminal Code (1 upper case alphabetic
			 * character) - the BIC Branch Code (3 characters)
			 */
			strBuilder.append(appHeader008.getFr().getFIId().getFinInstnId().getBICFI() );

			/// Session Number
			/*
			 * The Session Number identifies the session in which the message was
			 * transmitted. It is a four digits number that is automatically generated by
			 * the user's computer and padded with zeros.
			 */
			strBuilder.append("1111");

			/// Sequence Bumber
			/*
			 * The sequence number always consists of six digits. It is the Input Sequence
			 * Number (ISN) of the sender's current input session or the Output Sequence
			 * Number (OSN) of the receiver's current output session. It is automatically
			 * generated by the user's computer and padded with zeros.
			 */
			strBuilder.append("987654");

			/// End of Block Indicator
			strBuilder.append("}");

			/****************************
			 * Basic HeaderEnd
			 ******************************************************/

			/****************************
			 * Application Header Start
			 *********************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{2:");

			/// Input/Output ID
			/*
			 * For an input message, the Input/Output Identifier consists of the single
			 * letter 'I'
			 */
			strBuilder.append("I");

			/// SWIFT Message Type
			/*
			 * The Message Type consists of 3 digits which define the MT number of the
			 * message being input
			 */
			strBuilder.append("103");

			/// Destination Address
			/*
			 * This address is the 12-character SWIFT address of the receiver of the
			 * message. It defines the destination to which the message should be sent.
			 */
			strBuilder.append(appHeader008.getTo().getFIId().getFinInstnId().getBICFI() );

			/// Priority
			/*
			 * This character, used within FIN Application Headers only, defines the
			 * priority with which a message is delivered. The possible values are S =
			 * System U = Urgent N = Normal
			 */
			strBuilder.append("U");

			/// Delivery Monitoring
			/*
			 * Delivery monitoring options apply only to FIN user-to-user messages. The
			 * chosen option is expressed as a single digit: 1 = Non-Delivery Warning 2 =
			 * Delivery Notification 3 = Non-Delivery Warning and Delivery Notification If
			 * the message has priority 'U', the user must request delivery monitoring
			 * option '1' or '3'. If the message has priority 'N', the user can request
			 * delivery monitoring option '2' or, by leaving the option blank, no delivery
			 * monitoring
			 */

			strBuilder.append("3");

			/// Obsolescence Period
			/*
			 * The obsolescence period defines the period of time after which a Delayed
			 * Message (DLM) trailer is added to a FIN user-to-user message when the message
			 * is delivered. For urgent priority messages, it is also the period of time
			 * after which, if the message remains undelivered, a Non-Delivery Warning is
			 * generated. The values for the obsolescence period are: 003 (15 minutes) for
			 * 'U' priority, and 020 (100 minutes) for 'N' priority.
			 */

			strBuilder.append("003");

			/// End of block indicator
			strBuilder.append("}");

			/****************************
			 * Application Header End
			 *********************************************/

			/****************************
			 * User Header Start
			 **************************************************/

			// add Start of Indicator+BlockIdentifier+Separator
			// strBuilder.append("{3:");

			/// Tag103 - Service Identifier
			/**/
			/****************************
			 * User Header End
			 ****************************************************/


		}
		return strBuilder.toString();
	}

	public final static char CR = (char) 0x0D;
	public final static char LF = (char) 0x0A;

	public final static String CRLF = "" + CR + LF; // "" forces conversion to string

	public String createApplicationData(Document doc008, BusinessApplicationHeaderV01 appHeader008,Header header) {
		StringBuilder strBuilder = new StringBuilder();

		/****************************
		 * Data Start
		 *********************************************/
		CreditTransferTransaction391 creditTransferTran = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		GroupHeader931 groupHeader931 = doc008.getFIToFICstmrCdtTrf().getGrpHdr();

		if(header.getMessage().getSubFormat().equals("Output")) {
			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{4:" + CRLF);

			/// Sender Reference
			strBuilder.append(":20:" + creditTransferTran.getPmtId().getInstrId() + CRLF);

			senderreference = creditTransferTran.getPmtId().getInstrId();
			System.out.println("Sender's reference" + senderreference);

			/// Bank Operation Code
			strBuilder.append(":23B:CRED" + CRLF);
			bankoperationcode = "CRED" + CRLF;
			System.out.println("bankoperationcode" + bankoperationcode);

			/// Value Date,Currency,Amount
			String valueDate = "";
			try {
				//Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(groupHeader931.getIntrBkSttlmDt().toString());
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(groupHeader931.getCreDtTm().toString());
				valueDate = new SimpleDateFormat("yyMMdd").format(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String ccy = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy();
			BigDecimal amount =doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue();
		
		//	String amount = groupHeader931.getTtlIntrBkSttlmAmt().getValue().toString();
			//String ccy = groupHeader931.getTtlIntrBkSttlmAmt().getCcy();

			strBuilder.append(":32A:");
			strBuilder.append(valueDate);
			strBuilder.append(ccy);
			strBuilder.append(amount +","+ CRLF);

			interbanksettledamount = valueDate + ccy + amount +","+ CRLF;
			System.out.println("ttt" + interbanksettledamount);
			/// Currency,Instructed Amount

			String instdAmt = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getInstdAmt)
					.map(ActiveOrHistoricCurrencyAndAmount::getValue).orElse(new BigDecimal("0")).toString();

			if (!instdAmt.equals("0")) {

				String instdCcy = creditTransferTran.getInstdAmt().getCcy().toString();

				strBuilder.append(":33B:");
				strBuilder.append(instdCcy);
				strBuilder.append(instdAmt +","+ CRLF);
			}
			
			String DebitorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getDbtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			
			String CreditorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getCdtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			

			/// Ordering Customer
			strBuilder.append(":50K:");
			
			if(!DebitorAcc.equals("")) {
				strBuilder.append("/");
				strBuilder.append(DebitorAcc + CRLF);
			}
			
			
			  String[] array=creditTransferTran.getDbtr().getNm().split("(?<=\\G.{35})");
			  
			  for(int i=0;i<array.length;i++) {
				  if(i<2) {
					  if(i==1) {
						  if(array.length< 2) {
							  strBuilder.append(array[i] + CRLF); 
						  }else {

							  strBuilder.append(array[i].substring(0, array[i].length() - 1) +"+" +CRLF);  
						  }
					  }else {
					  strBuilder.append(array[i] + CRLF);
					  }
				  }
			  }
			//strBuilder.append(creditTransferTran.getDbtr().getNm() + CRLF);
			orderingcustomer = "/" + DebitorAcc + CRLF
					+ creditTransferTran.getDbtr().getNm() + CRLF;
			System.out.println("orderingcustomer" + orderingcustomer);

			String bldgNb = (Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getBldgNb).orElse(""));

			if (bldgNb != "") {
				strBuilder.append(bldgNb + " ");
			}

			String strtNm = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getStrtNm).orElse("");

			if (strtNm != "") {
				strBuilder.append(strtNm + CRLF);
			}

			String pstCd = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getPstCd).orElse("");

			if (pstCd != "") {
				strBuilder.append(pstCd);
			}

			String ctry = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getCtry).orElse("");

			if (ctry != "") {
				strBuilder.append(ctry + CRLF);
			}

			/// Ordering Agent

			String debtorAgent008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getDbtrAgt)
					.map(com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61::getFinInstnId)
					.map(com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181::getBICFI).orElse("");

			String debtorAgentAcc008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getDbtrAgtAcct)
					.map(com.bornfire.mx.pacs_008_001_08.CashAccount381::getId)
					.map(com.bornfire.mx.pacs_008_001_08.AccountIdentification4Choice1::getOthr)
					.map(com.bornfire.mx.pacs_008_001_08.GenericAccountIdentification11::getId).orElse("");
			
			String interAgentAcc008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getIntrmyAgt1)
					.map(com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61::getFinInstnId)
					.map(com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181::getBICFI).orElse("");
			
			String creditorAgent008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getCdtrAgt)
					.map(com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61::getFinInstnId)
					.map(com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181::getBICFI).orElse("");

			if (!debtorAgent008.equals("")) {
				strBuilder.append(":52A:");
				strBuilder.append(debtorAgent008 + CRLF);

				if (!debtorAgentAcc008.equals(""))
					strBuilder.append(debtorAgentAcc008 + CRLF);

			}
			
			if(!interAgentAcc008.equals("")) {
				strBuilder.append(":56A:");
				strBuilder.append(interAgentAcc008 + CRLF);
			}
			
			
			if(!creditorAgent008.equals("")) {
				strBuilder.append(":57A:");
				strBuilder.append(creditorAgent008 + CRLF);
			}

			/// Beneficiary

			strBuilder.append(":59:");
			strBuilder.append("/");
			
			if(!CreditorAcc.equals("")) {
				strBuilder.append("/");
				strBuilder.append(CreditorAcc + CRLF);
			}
			//strBuilder.append(creditTransferTran.getCdtrAcct().getId().getOthr().getId() + CRLF);
			
			 String[] array1=creditTransferTran.getCdtr().getNm().split("(?<=\\G.{35})");
			  
			 for(int i=0;i<array1.length;i++) {
				  if(i<2) {
					  if(i==1) {
						  if(array1.length< 2) {
							  strBuilder.append(array1[i] + CRLF); 
						  }else {

							  strBuilder.append(array1[i].substring(0, array1[i].length() - 1) +"+" +CRLF);  
						  }
					  }else {
					  strBuilder.append(array1[i] + CRLF);
					  }
				  }
			  }

			//strBuilder.append(creditTransferTran.getCdtr().getNm() + CRLF);

			//beneficairycustomer = "/" + creditTransferTran.getCdtrAcct().getId().getOthr().getId() + CRLF
			beneficairycustomer = "/" + CreditorAcc + CRLF
					+ creditTransferTran.getCdtr().getNm() + CRLF;
			System.out.println("beneficairycustomer" + beneficairycustomer);

			String bldgNb1 = (Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getBldgNb).orElse(""));

			if (bldgNb1 != "") {
				strBuilder.append(bldgNb1 + " ");
			}

			String strtNm1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getStrtNm).orElse("");

			if (strtNm1 != "") {
				strBuilder.append(strtNm1 + CRLF);
			}

			String pstCd1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getPstCd).orElse("");

			if (pstCd1 != "") {
				strBuilder.append(pstCd1);
			}

			String ctry1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getCtry).orElse("");

			if (ctry1 != "") {
				strBuilder.append(ctry1 + CRLF);
			}

			////Remittance Information
			String purp = (Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getPurp)
					.map(com.bornfire.mx.pacs_008_001_08.Purpose2Choice1::getCd).orElse((Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getPurp)
							.map(com.bornfire.mx.pacs_008_001_08.Purpose2Choice1::getPrtry).orElse(""))));
			
		////Remittance Information
					String ustrid = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getRmtInf)
									.map(com.bornfire.mx.pacs_008_001_08.RemittanceInformation161::getUstrd)
									.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0)).orElse("");
									
								

					
			strBuilder.append(":70:");

			
			StringBuilder remitterData=new StringBuilder();
			
			if(!purp.equals("")) {
				remitterData.append("/PURP/"+purp+"//");
			}
			remitterData.append("/ROC/"+creditTransferTran.getPmtId().getEndToEndId());
			if(!ustrid.equals("")) {
				remitterData.append("///URI/"+ustrid);
			}
			
			 String[] array2=remitterData.toString().split("(?<=\\G.{35})");
			  
			  for(int i=0;i<array2.length;i++) {
				  if(i<2) {
					  strBuilder.append(array2[i] + CRLF);
				  }
			  }

			
			
			strBuilder.append(":71A:OUR"+ CRLF);
			/// End Block tag
			strBuilder.append("-}");
			
			strBuilder.append("{5:{CHK:7C5E91ACFC4E}{TNG:}}");

		}else {
			// add Start of Indicator+BlockIdentifier+Separator
			strBuilder.append("{4:" + CRLF);

			/// Sender Reference
			strBuilder.append(":20:" + creditTransferTran.getPmtId().getInstrId() + CRLF);

			senderreference = creditTransferTran.getPmtId().getInstrId();
			System.out.println("Sender's reference" + senderreference);

			/// Bank Operation Code
			strBuilder.append(":23B:CRED" + CRLF);
			bankoperationcode = "CRED" + CRLF;
			System.out.println("bankoperationcode" + bankoperationcode);

			/// Value Date,Currency,Amount
			String valueDate = "";
			try {
				//Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(groupHeader931.getIntrBkSttlmDt().toString());
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(groupHeader931.getCreDtTm().toString());
				valueDate = new SimpleDateFormat("yyMMdd").format(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String ccy = doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy();
			BigDecimal amount =doc008.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue();
		
		//	String amount = groupHeader931.getTtlIntrBkSttlmAmt().getValue().toString();
			//String ccy = groupHeader931.getTtlIntrBkSttlmAmt().getCcy();

			strBuilder.append(":32A:");
			strBuilder.append(valueDate);
			strBuilder.append(ccy);
			strBuilder.append(amount+"," + CRLF);

			interbanksettledamount = valueDate + ccy + amount+"," + CRLF;
			System.out.println("ttt" + interbanksettledamount);
			/// Currency,Instructed Amount

			String instdAmt = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getInstdAmt)
					.map(ActiveOrHistoricCurrencyAndAmount::getValue).orElse(new BigDecimal("0")).toString();

			if (!instdAmt.equals("0")) {

				String instdCcy = creditTransferTran.getInstdAmt().getCcy().toString();

				strBuilder.append(":33B:");
				strBuilder.append(instdCcy);
				strBuilder.append(instdAmt+"," + CRLF);
			}
			
			String DebitorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getDbtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			
			String CreditorAcc = Optional.ofNullable(doc008).map(Document::getFIToFICstmrCdtTrf)
					.map(FIToFICustomerCreditTransferV08::getCdtTrfTxInf)
					.filter(indAliasList -> !indAliasList.isEmpty()).map(indAliasList -> indAliasList.get(0))
					.map(CreditTransferTransaction391::getCdtrAcct).map(CashAccount381::getId)
					.map(AccountIdentification4Choice1::getOthr).map(GenericAccountIdentification11::getId).orElse("");
			
			

			/// Ordering Customer
			strBuilder.append(":50K:");
			
			if(!DebitorAcc.equals("")) {
				strBuilder.append("/");
				strBuilder.append(DebitorAcc + CRLF);
			}
			
			strBuilder.append(creditTransferTran.getDbtr().getNm() + CRLF);
			orderingcustomer = "/" + DebitorAcc + CRLF
					+ creditTransferTran.getDbtr().getNm() + CRLF;
			System.out.println("orderingcustomer" + orderingcustomer);

			String bldgNb = (Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getBldgNb).orElse(""));

			if (bldgNb != "") {
				strBuilder.append(bldgNb + " ");
			}

			String strtNm = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getStrtNm).orElse("");

			if (strtNm != "") {
				strBuilder.append(strtNm + CRLF);
			}

			String pstCd = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getPstCd).orElse("");

			if (pstCd != "") {
				strBuilder.append(pstCd);
			}

			String ctry = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getDbtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getCtry).orElse("");

			if (ctry != "") {
				strBuilder.append(ctry + CRLF);
			}

			/// Ordering Agent

			String debtorAgent008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getDbtrAgt)
					.map(com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61::getFinInstnId)
					.map(com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181::getBICFI).orElse("");

			String debtorAgentAcc008 = Optional.ofNullable(creditTransferTran)
					.map(com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391::getDbtrAgtAcct)
					.map(com.bornfire.mx.pacs_008_001_08.CashAccount381::getId)
					.map(com.bornfire.mx.pacs_008_001_08.AccountIdentification4Choice1::getOthr)
					.map(com.bornfire.mx.pacs_008_001_08.GenericAccountIdentification11::getId).orElse("");

			if (debtorAgent008.equals("")) {
				strBuilder.append(":52A:");
				strBuilder.append(debtorAgent008 + CRLF);

				if (!debtorAgentAcc008.equals(""))
					strBuilder.append(debtorAgentAcc008 + CRLF);

			}

			/// Beneficiary

			strBuilder.append(":59:");
			strBuilder.append("/");
			
			if(!CreditorAcc.equals("")) {
				strBuilder.append("/");
				strBuilder.append(CreditorAcc + CRLF);
			}
			//strBuilder.append(creditTransferTran.getCdtrAcct().getId().getOthr().getId() + CRLF);
			strBuilder.append(CreditorAcc + CRLF);

			strBuilder.append(creditTransferTran.getCdtr().getNm() + CRLF);

			//beneficairycustomer = "/" + creditTransferTran.getCdtrAcct().getId().getOthr().getId() + CRLF
			beneficairycustomer = "/" + CreditorAcc + CRLF
					+ creditTransferTran.getCdtr().getNm() + CRLF;
			System.out.println("beneficairycustomer" + beneficairycustomer);

			String bldgNb1 = (Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getBldgNb).orElse(""));

			if (bldgNb1 != "") {
				strBuilder.append(bldgNb1 + " ");
			}

			String strtNm1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getStrtNm).orElse("");

			if (strtNm1 != "") {
				strBuilder.append(strtNm1 + CRLF);
			}

			String pstCd1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getPstCd).orElse("");

			if (pstCd1 != "") {
				strBuilder.append(pstCd1);
			}

			String ctry1 = Optional.ofNullable(creditTransferTran).map(CreditTransferTransaction391::getCdtr)
					.map(com.bornfire.mx.pacs_008_001_08.PartyIdentification1351::getPstlAdr)
					.map(com.bornfire.mx.pacs_008_001_08.PostalAddress241::getCtry).orElse("");

			if (ctry1 != "") {
				strBuilder.append(ctry1 + CRLF);
			}

			/// End Block tag
			strBuilder.append("}");
			
			strBuilder.append("{5:{CHK:7C5E91ACFC4E}{TNG:}}");

		}
		
		return strBuilder.toString();
	}

	public static String SenderReference() {

		return senderreference;
	}

	public static String Interbanksettlementamount() {

		return interbanksettledamount;
	}

	public static String BankOperationcode() {

		return bankoperationcode;
	}

	public static String OrderingCustomer() {

		return orderingcustomer;
	}

	public static String BeneficairyCustomer() {

		return beneficairycustomer;
	}

}
