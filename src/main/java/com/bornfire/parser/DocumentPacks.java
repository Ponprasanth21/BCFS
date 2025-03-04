package com.bornfire.parser;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.bornfire.entity.UserProfileRep;
import com.bornfire.mt.MT_103;
import com.bornfire.mx.datapduHeader.Header;
import com.bornfire.mx.head_001_001_01.BusinessApplicationHeaderV01;
import com.bornfire.mx.pacs_008_001_08.AccountIdentification4Choice1;
import com.bornfire.mx.pacs_008_001_08.ActiveCurrencyAndAmount;
import com.bornfire.mx.pacs_008_001_08.ActiveOrHistoricCurrencyAndAmount;
import com.bornfire.mx.pacs_008_001_08.BranchAndFinancialInstitutionIdentification61;
import com.bornfire.mx.pacs_008_001_08.CashAccount381;
import com.bornfire.mx.pacs_008_001_08.ChargeBearerType1Code;
import com.bornfire.mx.pacs_008_001_08.CreditTransferTransaction391;
import com.bornfire.mx.pacs_008_001_08.Document;
import com.bornfire.mx.pacs_008_001_08.FIToFICustomerCreditTransferV08;
import com.bornfire.mx.pacs_008_001_08.FinancialInstitutionIdentification181;
import com.bornfire.mx.pacs_008_001_08.GenericAccountIdentification11;
import com.bornfire.mx.pacs_008_001_08.GroupHeader931;
import com.bornfire.mx.pacs_008_001_08.LocalInstrument2Choice1;
import com.bornfire.mx.pacs_008_001_08.PartyIdentification1351;
import com.bornfire.mx.pacs_008_001_08.PaymentIdentification71;
import com.bornfire.mx.pacs_008_001_08.PaymentTypeInformation281;
import com.bornfire.mx.pacs_008_001_08.PostalAddress241;
import com.bornfire.mx.pacs_008_001_08.Purpose2Choice1;
import com.bornfire.mx.pacs_008_001_08.RemittanceInformation161;
import com.bornfire.mx.pacs_008_001_08.SettlementInstruction71;
import com.bornfire.mx.pacs_008_001_08.SettlementMethod1Code1;
import com.bornfire.util.JaxbCharacterEscapeHandler;
import com.bornfire.util.SwiftParserMT103Util;

@Component
public class DocumentPacks {

	private static JAXBContext jaxbContextDocPacs008;
	private static XMLInputFactory factoryPacs008;
	private static String MtMsgPAth = "";
	private static String MxMsgPath = "";
	private static String GrpHdr = "";
	private static String CdtTrfTxInf = "";
	private static String localInstrm = "";
	private static String InterBkSttlmAmt = "";
	private static String DbtrAcct = "";
	private static String CdtrAcct = "";
	private static String MTmsgname = "";
	private static String Mxmsgname = "";

	@Autowired
	AppHeader appHeader;

	@Autowired
	Environment env;

	@Autowired
	UserProfileRep userProfileRep;

	static {
		try {
			// one time instance creation
			jaxbContextDocPacs008 = JAXBContext.newInstance(Document.class);
			factoryPacs008 = XMLInputFactory.newInstance();
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	public String getDataPDU008Old(MT_103 mt103, String block1, String block2, String block3, String block5,
			String userID) throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<DataPDU xmlns:Saa=\"urn:swift:xsd:saa.2.0\" xmlns:Sw=\"urn:swift:snl:ns.Sw\" xmlns:SwInt=\"urn:swift:snl:ns.SwInt\" xmlns:SwGbl=\"urn:swift:snl:ns.SwGbl\" xmlns:SwSec=\"urn:swift:snl:ns.SwSec\">"
						+ "<Body>\r\n");
		sb.append(getAppHeader008(mt103, block1, block2, block5));
		sb.append(getPacs_008_001_01Doc(mt103, block3));
		sb.append("</Body>\r\n" + "</DataPDU>");

		String filename = new SimpleDateFormat("YYYYMMDDhhmmss").format(new Date()) + ".OUT";
		String userid1 = userID;
		String Country_code = "";
		if (userid1 != null) {
			if (userID.equals("Auto")) {
				Country_code = "Auto";
				System.out.println("countrysssss");
			} else if (userID.equals("Auto_MUS")) {
				Country_code = "Auto_MUS";
			} else if (userID.equals("Auto_BWA")) {
				Country_code = "Auto_BWA";
			} else if (userID.equals("Auto_MOZ")) {
				Country_code = "Auto_MOZ";
			} else if (userID.equals("Auto_MWI")) {
				Country_code = "Auto_MWI";
			} else if (userID.equals("Auto_ZMB")) {
				Country_code = "Auto_ZMB";
			} else if (userID.equals("Auto_ZWE")) {
				Country_code = "Auto_ZWE";
			} else {
				Country_code = userProfileRep.getCountrycode(userid1);

			}

			String path = "";

			env.getProperty("bwa.swift.mx.in.file.path");
			switch (Country_code) {
			// Case statements
			case "BWA":
				path = env.getProperty("bwa.swift.mx.out.file.path");

				break;
			case "MOZ":
				path = env.getProperty("moz.swift.mx.out.file.path");

				break;
			case "MWI":
				path = env.getProperty("mwi.swift.mx.out.file.path");

				break;
			case "ZMB":
				path = env.getProperty("zmb.swift.mx.out.file.path");

				break;
			case "ZWE":
				path = env.getProperty("zwe.swift.mx.out.file.path");

				break;
			case "MUS":
				path = env.getProperty("mus.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto":
				path = env.getProperty("auto.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto_MUS":
				path = env.getProperty("auto.mus.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto_BWA":
				path = env.getProperty("auto.bwa.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto_MOZ":
				path = env.getProperty("auto.moz.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;

			case "Auto_MWI":
				path = env.getProperty("auto.mwi.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto_ZMB":
				path = env.getProperty("auto.zmb.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;
			case "Auto_ZWE":
				path = env.getProperty("auto.zwe.swift.mx.out.file.path");
				// System.out.println(path+ " LLLLL");

				break;

			}
			System.out.println("mtTOMXPath" + path + filename);

			OutputStream outputStream = new FileOutputStream(path + filename);

			MxMsgPath = path + filename;
			Mxmsgname = filename;
			String s = new String(sb.toString().getBytes(StandardCharsets.UTF_8));
			byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
			String s1 = new String(b, "windows-1252");

			outputStream.write(s1.getBytes());

			return s1;
		}
		return Country_code;
	}

	private String getAppHeader008(MT_103 mt103, String black1, String black2, String black5) {
		BusinessApplicationHeaderV01 appHeader = new BusinessApplicationHeaderV01();

		com.bornfire.mx.head_001_001_01.FinancialInstitutionIdentification81 finInstnId = new com.bornfire.mx.head_001_001_01.FinancialInstitutionIdentification81();
		// finInstnId.setBICFI(env.getProperty("ipsx.bicfi"));
		System.out.println("THE GETTING BLOCK2 VALUE IS HERE " + black2);
		finInstnId.setBICFI(black1.substring(3, 14));
		com.bornfire.mx.head_001_001_01.BranchAndFinancialInstitutionIdentification51 fIId = new com.bornfire.mx.head_001_001_01.BranchAndFinancialInstitutionIdentification51();
		fIId.setFinInstnId(finInstnId);
		com.bornfire.mx.head_001_001_01.Party9Choice1 fr = new com.bornfire.mx.head_001_001_01.Party9Choice1();
		fr.setFIId(fIId);

		com.bornfire.mx.head_001_001_01.FinancialInstitutionIdentification81 finInstnIdTo = new com.bornfire.mx.head_001_001_01.FinancialInstitutionIdentification81();

		if (black2.substring(0).equals("O")) {
			finInstnIdTo.setBICFI(black2.substring(14, 24));
		} else {
			finInstnIdTo.setBICFI(black2.substring(4, 16));
		}

		// finInstnIdTo.setBICFI(black2.substring(14, 24));
		com.bornfire.mx.head_001_001_01.BranchAndFinancialInstitutionIdentification51 fIIdTo = new com.bornfire.mx.head_001_001_01.BranchAndFinancialInstitutionIdentification51();
		fIIdTo.setFinInstnId(finInstnIdTo);
		com.bornfire.mx.head_001_001_01.Party9Choice1 to = new com.bornfire.mx.head_001_001_01.Party9Choice1();
		to.setFIId(fIIdTo);

		appHeader.setTo(to);
		appHeader.setFr(fr);
		appHeader.setBizMsgIdr(black2);
		appHeader.setMsgDefIdr("pacs.008.001.08");
		appHeader.setBizSvc(black1);

		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		if (black5 != null && black5.contains("CHK:")) {
			String prtyValue = black5.substring(black5.indexOf("CHK:") + 4).split("}")[0];
			appHeader.setPrty(prtyValue);
		}

		appHeader.setCreDt(xgc);

		JAXBContext jaxbContext;
		Marshaller jaxbMarshaller;
		StringWriter sw = null;
		try {
			jaxbContext = JAXBContext.newInstance(BusinessApplicationHeaderV01.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			JaxbCharacterEscapeHandler jaxbCharHandler = new JaxbCharacterEscapeHandler();
			jaxbMarshaller.setProperty("com.sun.xml.bind.characterEscapeHandler", jaxbCharHandler);

			com.bornfire.mx.head_001_001_01.ObjectFactory obj = new com.bornfire.mx.head_001_001_01.ObjectFactory();
			JAXBElement<BusinessApplicationHeaderV01> jaxbElement = obj.createAppHdr(appHeader);
			sw = new StringWriter();
			jaxbMarshaller.marshal(jaxbElement, sw);
			// OutputStreamWriter os = new OutputStreamWriter(new ByteArrayOutputStream());
			// jaxbMarshaller.marshal(jaxbElement, os);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sw.toString();

	}

	/****
	 * Create Document of Pacs.008.001.08
	 * 
	 * @throws ParseException
	 ****/
	public String getPacs_008_001_01Doc(MT_103 mt103, String block3) throws ParseException {
		String dataFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(dataFormat);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		GroupHeader931 grpHdr = new GroupHeader931();
		String msg_seq_id = mt103.getTag20(); /// Message Identification ID
		grpHdr.setMsgId(msg_seq_id); /// Credit Date Time
		grpHdr.setCreDtTm(xgc); /// Number Of Transaction
		grpHdr.setNbOfTxs("1"); /// Total Inter Bank Settlement Amount
		SettlementInstruction71 sttlmInf = new SettlementInstruction71();
		sttlmInf.setSttlmMtd(SettlementMethod1Code1.INDA);/// Settlement method (CLRG)
		System.out.println("53B+++" + mt103.getTag53B());
		if (mt103.getTag53B() != null && !mt103.getTag53B().isEmpty()) {
			AccountIdentification4Choice1 settlacc = new AccountIdentification4Choice1();
			GenericAccountIdentification11 othr = new GenericAccountIdentification11();
			othr.setId(mt103.getTag53B());
			settlacc.setOthr(othr);
			CashAccount381 cc = new CashAccount381();
			cc.setId(settlacc);
			sttlmInf.setSttlmAcct(cc);
		} else {
			// If mt103.getTag53B() is empty or null, just skip without any error
			// Optionally, you can log or handle this case differently if needed
		}
		grpHdr.setSttlmInf(sttlmInf);
		System.out.println("crdagt+++" + mt103.getTag57A());
		///// creditTransaction Information
		List<CreditTransferTransaction391> cdtTrfTxInf = new ArrayList<CreditTransferTransaction391>();
		CreditTransferTransaction391 creditTransferTransaction391 = new CreditTransferTransaction391();
		/// Payment Identification
		PaymentIdentification71 pmtId = new PaymentIdentification71();
		pmtId.setInstrId(mt103.getTag20().trim());/// Instruction ID
		
		pmtId.setTxId(mt103.getTag20());
		if (block3.contains("{121:")) {
			String uetr = block3.replaceAll(".*\\{121:([a-fA-F0-9\\-]+)\\}.*", "$1");
			pmtId.setUETR(uetr);
		}

		creditTransferTransaction391.setPmtId(pmtId);
		System.out.println("crettxn" + mt103.getTag20());
		CdtTrfTxInf = mt103.getTag20();
		System.out.println("jer45" + CdtTrfTxInf);
		RemittanceInformation161 rmtinf = new RemittanceInformation161();
		System.out.println("3343" + mt103.getTag70());
		List<String> ww = null;
		ww = Arrays.asList(mt103.getTag70());
		rmtinf.setUstrd(ww);
		creditTransferTransaction391.setRmtInf(rmtinf);
		/// Payment Type Information
		PaymentTypeInformation281 pmtTpInf = new PaymentTypeInformation281();
		localInstrm = mt103.getTag23B();
		ActiveCurrencyAndAmount intrBkSttlmAmt = new ActiveCurrencyAndAmount();
		intrBkSttlmAmt.setCcy(SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[1]);/// Currency
		String balanceData1 = SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[2].trim();
		intrBkSttlmAmt.setValue(new BigDecimal(balanceData1.replace(",", ".")));/// Amount
		creditTransferTransaction391.setIntrBkSttlmAmt(intrBkSttlmAmt);
		Date dataFormat1 = new SimpleDateFormat("yyMMdd").parse(SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[0]);
		String dataFormat2 = new SimpleDateFormat("yyyy-MM-dd").format(dataFormat1);
		XMLGregorianCalendar xgc1 = null;
		try {
			xgc1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dataFormat2);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		creditTransferTransaction391.setIntrBkSttlmDt(xgc1);
		ChargeBearerType1Code chrgbr = null;
		String ss = mt103.getTag23B().trim();
		System.out.println(ss + "value5555");
		try {
			if (ss.equals("CRED")) {
				chrgbr = ChargeBearerType1Code.CRED;
			} else if (ss.equals("DEBT")) {
				chrgbr = ChargeBearerType1Code.DEBT;
			} else if (ss.equals("SHA")) {
				chrgbr = ChargeBearerType1Code.SHA;
			} else {
				chrgbr = ChargeBearerType1Code.SLEV;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		creditTransferTransaction391.setChrgBr(chrgbr);

		System.out.println(mt103.getTag23B().trim() + "55655");
		InterBkSttlmAmt = mt103.getTag32A();
		//// Settlement Amount
		if (mt103.getTag33B() != null) {
			ActiveOrHistoricCurrencyAndAmount instdAmt = new ActiveOrHistoricCurrencyAndAmount();
			instdAmt.setCcy(SwiftParserMT103Util.parseTag33B(mt103.getTag33B())[0].trim());
			String balanceData = SwiftParserMT103Util.parseTag33B(mt103.getTag33B())[1].trim();
			instdAmt.setValue(new BigDecimal(balanceData.replace(",", ".")));
			creditTransferTransaction391.setInstdAmt(instdAmt);
		}
		//// Exchange Rate
		if (mt103.getTag36() != null) {
			creditTransferTransaction391.setXchgRate(new BigDecimal(mt103.getTag36()));
		}
		if (mt103.getTag50K() != null) {
			/// Debtor name
			final String[] tag50k = SwiftParserMT103Util.parseTag50K(mt103.getTag50K());
			DbtrAcct = mt103.getTag50K();
			PartyIdentification1351 dbtr = new PartyIdentification1351();
			dbtr.setNm(tag50k[1]);
			if (tag50k.length > 2) {
				final List<String> adrLine = new ArrayList<String>();
				adrLine.add(String.valueOf(tag50k[2]) + "\n" + tag50k[3]);
				PostalAddress241 postalAddress241 = new PostalAddress241();
				postalAddress241.setAdrLine((List<String>) adrLine);
				dbtr.setPstlAdr(postalAddress241);
			}
			creditTransferTransaction391.setDbtr(dbtr);
			CashAccount381 dbtrAcct = new CashAccount381();
			AccountIdentification4Choice1 acc1 = new AccountIdentification4Choice1();
			GenericAccountIdentification11 id = new GenericAccountIdentification11();
			String Dbt = GenericAccountIdentification11.debtacc();
			System.out.println("hereId" + Dbt);
			id.setId((tag50k[0].replace("/", "")));
			acc1.setOthr(id);
			dbtrAcct.setId(acc1);
			creditTransferTransaction391.setDbtrAcct(dbtrAcct);
			/// Debtor Agent
			if (mt103.getTag52A() != null) {
				BranchAndFinancialInstitutionIdentification61 dbtrAgt = new BranchAndFinancialInstitutionIdentification61();
				FinancialInstitutionIdentification181 fin2 = new FinancialInstitutionIdentification181();
				fin2.setBICFI(mt103.getTag52A().trim());
				dbtrAgt.setFinInstnId(fin2);
				creditTransferTransaction391.setDbtrAgt(dbtrAgt);
			}
		}
		/// Creditor Agent
		if (mt103.getTag57() != null) {
			BranchAndFinancialInstitutionIdentification61 cdtrAgt = new BranchAndFinancialInstitutionIdentification61();
			FinancialInstitutionIdentification181 fin3 = new FinancialInstitutionIdentification181();
			fin3.setBICFI(mt103.getTag57().trim());
			cdtrAgt.setFinInstnId(fin3);
			creditTransferTransaction391.setCdtrAgt(cdtrAgt);
		}
		/// Creditor Name
		final String[] tag59 = SwiftParserMT103Util.parseTag50K(mt103.getTag59());
		CdtrAcct = mt103.getTag59();
		PartyIdentification1351 cdtr = new PartyIdentification1351();
		cdtr.setNm(tag59[1]);
		if (tag59.length > 3) {
			final List<String> adrLine1 = new ArrayList<String>();
			adrLine1.add(String.valueOf(tag59[2]) + "\n" + tag59[3]);
			PostalAddress241 postalAddress241_1 = new PostalAddress241();
			postalAddress241_1.setAdrLine(adrLine1);
			cdtr.setPstlAdr(postalAddress241_1);
		} else {
			// Handle the case when tag59 doesn't have enough elements
			// You can log the error or take alternative action
		}
		creditTransferTransaction391.setCdtr(cdtr);
		/// Creditor Account Number
		CashAccount381 cdtrAcct = new CashAccount381();
		AccountIdentification4Choice1 id4 = new AccountIdentification4Choice1();
		GenericAccountIdentification11 gen4 = new GenericAccountIdentification11();
		gen4.setId(tag59[0].replace("/", ""));
		id4.setOthr(gen4);
		cdtrAcct.setId(id4);
		creditTransferTransaction391.setCdtrAcct(cdtrAcct);
		if ((!mt103.getTag70().equals(""))) {
			String str = mt103.getTag70().replace(System.getProperty("line.separator"), "");
			Pattern pattern = Pattern.compile("PURP/(.*?)//");
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				Purpose2Choice1 purpose1 = new Purpose2Choice1();
				purpose1.setCd(matcher.group(1));
				creditTransferTransaction391.setPurp(purpose1);
			}
		}
		if ((!mt103.getTag70().equals(""))) {
			String str = mt103.getTag70().replace(System.getProperty("line.separator"), "" + "//");
			Pattern pattern = Pattern.compile("URI/(.*?)//");
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				RemittanceInformation161 rmtInf = new RemittanceInformation161();
				rmtInf.setUstrd(Arrays.asList(matcher.group(1)));
				creditTransferTransaction391.setRmtInf(rmtInf);
			}
		}

		ChargeBearerType1Code chrgbr1 = null;
		String tag71A = mt103.getTag71A();

		if (tag71A != null) {
		    switch (tag71A) {
		        case "BEN":
		            chrgbr1 = ChargeBearerType1Code.BEN;
		            break;
		        case "OUR":
		            chrgbr1 = ChargeBearerType1Code.OUR;
		            break;
		        case "SHA":
		            chrgbr1 = ChargeBearerType1Code.SHA;
		            break;
		    }
		}

		if (tag71A != null) {
		    tag71A = tag71A.replace("-}", "").trim();
		    if (!tag71A.isEmpty()) {
		        pmtId.setEndToEndId(tag71A);
		    }
		}

		cdtTrfTxInf.add(creditTransferTransaction391);
		/// Financial Customer Credit Transfer
		FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf = new FIToFICustomerCreditTransferV08();
		fiToFICstmrCdtTrf.setGrpHdr(grpHdr);
		fiToFICstmrCdtTrf.setCdtTrfTxInf(cdtTrfTxInf);
		System.out.println("CreditTxn" + cdtTrfTxInf);
		/// Document
		Document document = new Document();
		document.setFIToFICstmrCdtTrf(fiToFICstmrCdtTrf);
		/// Convert Document XMl element to String JAXBContext jaxbContext;
		Marshaller jaxbMarshaller;
		StringWriter sw = null;
		try {
			jaxbMarshaller = jaxbContextDocPacs008.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			JaxbCharacterEscapeHandler jaxbCharHandler = new JaxbCharacterEscapeHandler();
			jaxbMarshaller.setProperty("com.sun.xml.bind.characterEscapeHandler", jaxbCharHandler);
			com.bornfire.mx.pacs_008_001_08.ObjectFactory obj = new com.bornfire.mx.pacs_008_001_08.ObjectFactory();
			JAXBElement<Document> jaxbElement = obj.createDocument(document);
			sw = new StringWriter();
			jaxbMarshaller.marshal(jaxbElement, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	public Document getPacs_008_001_01UnMarshalDoc(String block4) {
		final int start = block4.indexOf("<Document");
		final int end = block4.indexOf("</Document>");
		InputStream stream = null;
		JAXBContext jaxBContext;
		JAXBElement<Document> jaxbElement = null;
		try {
			stream = new ByteArrayInputStream(block4.substring(start, end + 11).getBytes("UTF-8"));
			jaxBContext = JAXBContext.newInstance(Document.class);
			Unmarshaller unMarshaller = jaxBContext.createUnmarshaller();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader xmlEventReader = factory.createXMLEventReader(stream);
			jaxbElement = unMarshaller.unmarshal(xmlEventReader, Document.class);
		} catch (JAXBException | UnsupportedEncodingException | XMLStreamException e) {
			e.printStackTrace();
		}
		return jaxbElement.getValue();
	}

	public List<Document> getPacs_008_001_01UnMarshalDocs1(String block4) {
		List<Document> documents = new ArrayList<>();
		Pattern pattern = Pattern.compile("<Document[\\s\\S]*?</Document>"); // Regex to find multiple
																				// <Document>...</Document> blocks
		Matcher matcher = pattern.matcher(block4);

		try {
			JAXBContext jaxBContext = JAXBContext.newInstance(Document.class);
			Unmarshaller unMarshaller = jaxBContext.createUnmarshaller();

			while (matcher.find()) { // Iterate through all matches
				String documentXml = matcher.group();
				InputStream stream = new ByteArrayInputStream(documentXml.getBytes(StandardCharsets.UTF_8));
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLEventReader xmlEventReader = factory.createXMLEventReader(stream);
				JAXBElement<Document> jaxbElement = unMarshaller.unmarshal(xmlEventReader, Document.class);
				documents.add(jaxbElement.getValue()); // Add each unmarshalled Document to the list
			}
		} catch (JAXBException | XMLStreamException e) {
			e.printStackTrace();
		}
		return documents;
	}

	public BusinessApplicationHeaderV01 getPacs_008_001_01UnMarshalAppHeader(String block4) {
		final int start = block4.indexOf("<AppHdr");
		final int end = block4.indexOf("</AppHdr>");

		System.out.println("block4" + block4);
		InputStream stream = null;
		JAXBContext jaxBContext;
		JAXBElement<BusinessApplicationHeaderV01> jaxbElement = null;
		System.out.println("Block4" + block4.substring(start, end + 9));
		try {
			stream = new ByteArrayInputStream(block4.substring(start, end + 9).getBytes("UTF-8"));
			jaxBContext = JAXBContext.newInstance(BusinessApplicationHeaderV01.class);
			Unmarshaller unMarshaller = jaxBContext.createUnmarshaller();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader xmlEventReader = factory.createXMLEventReader(stream);
			jaxbElement = unMarshaller.unmarshal(xmlEventReader, BusinessApplicationHeaderV01.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		BusinessApplicationHeaderV01 document = jaxbElement.getValue();

		return document;

	}

	public Header getPacs_008_001_01UnMarshalDataPDUHeader(String request) {
		String block4 = request.replaceAll("<Saa:", "<").replaceAll("<Sw:", "<").replaceAll("<SwInt:", "<")
				.replaceAll("<SwGbl:", "").replaceAll("<SwSec:", "<").replaceAll("</Saa:", "</")
				.replaceAll("</Sw:", "</").replaceAll("</SwInt:", "</").replaceAll("</SwGbl:", "</")
				.replaceAll("</SwSec:", "</");

		System.out.println("Block4Content" + block4);
		final int start = block4.indexOf("<Header");
		final int end = block4.indexOf("</Header>");
		System.out.println("start++++" + start);
		System.out.println("end++++" + end);

		System.out.println("block4" + block4);
		InputStream stream = null;
		JAXBContext jaxBContext;
		// JAXBElement<Header> jaxbElement = null;
		System.out.println("Block4" + block4.substring(start, end + 9));
		Header header = null;
		try {
			stream = new ByteArrayInputStream(block4.substring(start, end + 9).getBytes("UTF-8"));
			jaxBContext = JAXBContext.newInstance(Header.class);
			Unmarshaller unMarshaller = jaxBContext.createUnmarshaller();
			// XMLInputFactory factory = XMLInputFactory.newInstance();
			// XMLEventReader xmlEventReader = factory.createXMLEventReader(stream);
			// jaxbElement = unMarshaller.unmarshal(xmlEventReader, Header.class);
			header = (Header) unMarshaller.unmarshal(stream);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} /*
			 * catch (XMLStreamException e) { e.printStackTrace(); }
			 */
		// Header header = jaxbElement.getValue();

		return header;

	}

	public String getMT_100(Document doc008, BusinessApplicationHeaderV01 appHeader008, String userID, String filename)
			throws IOException {

		// Create MT Application Header
		String headerParam = appHeader.createApplicationHeader(doc008, appHeader008);
		String dataParam = appHeader.createApplicationData(doc008, appHeader008);

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(headerParam);
		strBuilder.append(dataParam);

		filename = filename.replace(".OUT", "").replace(".IN", "") + ".IN";

		String userid1 = userID;
		String Country_code = "";
		if (userID.equals("Auto")) {
			Country_code = "Auto";
		} else if (userID.equals("Auto_MUS")) {
			Country_code = "Auto_MUS";
		} else if (userID.equals("Auto_BWA")) {
			Country_code = "Auto_BWA";
		} else if (userID.equals("Auto_MOZ")) {
			Country_code = "Auto_MOZ";
		} else if (userID.equals("Auto_MWI")) {
			Country_code = "Auto_MWI";
		} else if (userID.equals("Auto_ZMB")) {
			Country_code = "Auto_ZMB";
		} else if (userID.equals("Auto_ZWE")) {
			Country_code = "Auto_ZWE";
		} else {
			Country_code = userProfileRep.getCountrycode(userid1);
		}
		// String Country_code = userProfileRep.getCountrycode(userid1);

		String path = "";

		env.getProperty("bwa.swift.mx.in.file.path");
		switch (Country_code) {
		// Case statements
		case "BWA":
			path = env.getProperty("bwa.swift.mt.in.file.path");

			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mt.in.file.path");

			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mt.in.file.path");

			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mt.in.file.path");

			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mt.in.file.path");

			break;
		case "MUS":
			path = env.getProperty("mus.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto":
			path = env.getProperty("auto.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MUS":
			path = env.getProperty("auto.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_BWA":
			path = env.getProperty("auto.bwa.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MOZ":
			path = env.getProperty("auto.moz.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MWI":
			path = env.getProperty("auto.mwi.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_ZMB":
			path = env.getProperty("auto.zmb.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_ZWE":
			path = env.getProperty("auto.zwe.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;

		}
		OutputStream outputStream = new FileOutputStream(path + filename);
		MtMsgPAth = path + filename;
		MTmsgname = filename;
		outputStream.write(strBuilder.toString().getBytes());

		return strBuilder.toString();
	}

	public String getMT_1001(List<Document> doc008, BusinessApplicationHeaderV01 appHeader008, String userID,
			String filename) throws IOException {

		// Initialize StringBuilder
		StringBuilder strBuilder = new StringBuilder();

		// Create MT Application Header
		for (Document doc : doc008) {
			String headerParam = appHeader.createApplicationHeader1(Collections.singletonList(doc), appHeader008);
			String dataParam = appHeader.createApplicationData1(Collections.singletonList(doc), appHeader008);
			strBuilder.append("$").append("\n").append(headerParam);
			strBuilder.append(dataParam).append("\n");
		}

		filename = filename.replace(".OUT", "").replace(".IN", "") + ".IN";

		String Country_code;
		if (userID.startsWith("Auto")) {
			Country_code = userID;
		} else {
			Country_code = userProfileRep.getCountrycode(userID);
		}

		String path = "";

		env.getProperty("bwa.swift.mx.in.file.path");
		switch (Country_code) {
		// Case statements
		case "BWA":
			path = env.getProperty("bwa.swift.mt.in.file.path");

			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mt.in.file.path");

			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mt.in.file.path");

			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mt.in.file.path");

			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mt.in.file.path");

			break;
		case "MUS":
			path = env.getProperty("mus.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto":
			path = env.getProperty("auto.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MUS":
			path = env.getProperty("auto.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_BWA":
			path = env.getProperty("auto.bwa.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MOZ":
			path = env.getProperty("auto.moz.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_MWI":
			path = env.getProperty("auto.mwi.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_ZMB":
			path = env.getProperty("auto.zmb.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;
		case "Auto_ZWE":
			path = env.getProperty("auto.zwe.swift.mt.in.file.path");
			System.out.println(path + "    LLLLL");

			break;

		}

		if (path.isEmpty()) {
			throw new IOException("Invalid country code or path not found for: " + Country_code);
		}

		try (OutputStream outputStream = new FileOutputStream(path + filename)) {
			MtMsgPAth = path + filename;
			MTmsgname = filename;
			outputStream.write(strBuilder.toString().getBytes());
		}

		return strBuilder.toString();
	}

	public static String MtMessgaePath() {

		return MtMsgPAth;
	}

	public static String MxMessagePath() {

		return MxMsgPath;
	}

	public static String CreditTranferInf() {

		return CdtTrfTxInf;
	}

	public static String LocalInstrm() {

		return localInstrm;
	}

	public static String InterBkSttlmAmount() {

		return InterBkSttlmAmt;
	}

	public static String DebitorAcc() {

		return DbtrAcct;
	}

	public static String CreditorAcc() {

		return CdtrAcct;
	}

	public static String MTmsgname() {

		return MTmsgname;
	}

	public static String Mxmsgname() {

		return Mxmsgname;
	}

	// New Logics

	@SuppressWarnings("resource")
	public String singleGetDataPDU008(MT_103 mt103, String block1, String block2, String block3, String block5,
			String userID, String filename) throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		sb.append("<DataPDU xmlns:Saa=\"urn:swift:xsd:saa.2.0\" xmlns:Sw=\"urn:swift:snl:ns.Sw\" "
				+ "xmlns:SwInt=\"urn:swift:snl:ns.SwInt\" xmlns:SwGbl=\"urn:swift:snl:ns.SwGbl\" "
				+ "xmlns:SwSec=\"urn:swift:snl:ns.SwSec\"><Body>\r\n");
		sb.append(getAppHeader008(mt103, block1, block2, block5));
		sb.append(getPacs_008_001_01Doc(mt103, block3));
		sb.append("</Body>\r\n</DataPDU>");

		filename = filename.replace(".txt", ".OUT");

		String Country_code = "";
		String path = "";

		if (userID.equals("Auto")) {
			Country_code = "Auto";
			System.out.println("countrysssss");
		} else if (userID.equals("Auto_MUS")) {
			Country_code = "Auto_MUS";
		} else if (userID.equals("Auto_BWA")) {
			Country_code = "Auto_BWA";
		} else if (userID.equals("Auto_MOZ")) {
			Country_code = "Auto_MOZ";
		} else if (userID.equals("Auto_MWI")) {
			Country_code = "Auto_MWI";
		} else if (userID.equals("Auto_ZMB")) {
			Country_code = "Auto_ZMB";
		} else if (userID.equals("Auto_ZWE")) {
			Country_code = "Auto_ZWE";
		} else {
			Country_code = userProfileRep.getCountrycode(userID);
		}

		switch (Country_code) {
		case "BWA":
			path = env.getProperty("bwa.swift.mx.out.file.path");
			break;
		case "MOZ":
			path = env.getProperty("moz.swift.mx.out.file.path");
			break;
		case "MWI":
			path = env.getProperty("mwi.swift.mx.out.file.path");
			break;
		case "ZMB":
			path = env.getProperty("zmb.swift.mx.out.file.path");
			break;
		case "ZWE":
			path = env.getProperty("zwe.swift.mx.out.file.path");
			break;
		case "MUS":
			path = env.getProperty("mus.swift.mx.out.file.path");
			break;
		case "Auto":
			path = env.getProperty("auto.swift.mx.out.file.path");
			break;
		case "Auto_MUS":
			path = env.getProperty("auto.mus.swift.mx.out.file.path");
			break;
		case "Auto_BWA":
			path = env.getProperty("auto.bwa.swift.mx.out.file.path");
			break;
		case "Auto_MOZ":
			path = env.getProperty("auto.moz.swift.mx.out.file.path");
			break;
		case "Auto_MWI":
			path = env.getProperty("auto.mwi.swift.mx.out.file.path");
			break;
		case "Auto_ZMB":
			path = env.getProperty("auto.zmb.swift.mx.out.file.path");
			break;
		case "Auto_ZWE":
			path = env.getProperty("auto.zwe.swift.mx.out.file.path");
			break;
		}
		System.out.println("mtTOMXPath" + path + filename);
		OutputStream outputStream = new FileOutputStream(path + filename);
		MxMsgPath = path + filename;
		Mxmsgname = filename;
		String s = new String(sb.toString().getBytes(StandardCharsets.UTF_8));
		byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
		String s1 = new String(b, "windows-1252");
		outputStream.write(s1.getBytes());
		return s1;
	}

	public String multipleGetDataPDU008(MT_103 mt103, List<MT_103> mt103List, String block1, String block2,
			String block3, String block5, String userID, String filename, boolean isMultiple)
			throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		sb.append("<DataPDU xmlns:Saa=\"urn:swift:xsd:saa.2.0\" xmlns:Sw=\"urn:swift:snl:ns.Sw\" "
				+ "xmlns:SwInt=\"urn:swift:snl:ns.SwInt\" xmlns:SwGbl=\"urn:swift:snl:ns.SwGbl\" "
				+ "xmlns:SwSec=\"urn:swift:snl:ns.SwSec\"><Body>\r\n");

		sb.append(getAppHeader008(mt103, block1, block2, block5));
		if (isMultiple) {
			for (MT_103 txn : mt103List) {
				sb.append(MultiplegetPacs_008_001_01Doc(txn, block3));
			}
		}
		sb.append("</Body>\r\n</DataPDU>");

		filename = filename.replace(".txt", ".OUT");
		String Country_code = "";
		String userid1 = userID;
		if (userid1 != null) {
			if (userID.equals("Auto")) {
				Country_code = "Auto";
				System.out.println("countrysssss");
			} else if (userID.equals("Auto_MUS")) {
				Country_code = "Auto_MUS";
			} else if (userID.equals("Auto_BWA")) {
				Country_code = "Auto_BWA";
			} else if (userID.equals("Auto_MOZ")) {
				Country_code = "Auto_MOZ";
			} else if (userID.equals("Auto_MWI")) {
				Country_code = "Auto_MWI";
			} else if (userID.equals("Auto_ZMB")) {
				Country_code = "Auto_ZMB";
			} else if (userID.equals("Auto_ZWE")) {
				Country_code = "Auto_ZWE";
			} else {
				Country_code = userProfileRep.getCountrycode(userid1);
			}
			String path = "";
			env.getProperty("bwa.swift.mx.in.file.path");
			switch (Country_code) {
			case "BWA":
				path = env.getProperty("bwa.swift.mx.out.file.path");
				break;
			case "MOZ":
				path = env.getProperty("moz.swift.mx.out.file.path");
				break;
			case "MWI":
				path = env.getProperty("mwi.swift.mx.out.file.path");
				break;
			case "ZMB":
				path = env.getProperty("zmb.swift.mx.out.file.path");
				break;
			case "ZWE":
				path = env.getProperty("zwe.swift.mx.out.file.path");
				break;
			case "MUS":
				path = env.getProperty("mus.swift.mx.out.file.path");
				break;
			case "Auto":
				path = env.getProperty("auto.swift.mx.out.file.path");
				break;
			case "Auto_MUS":
				path = env.getProperty("auto.mus.swift.mx.out.file.path");
				break;
			case "Auto_BWA":
				path = env.getProperty("auto.bwa.swift.mx.out.file.path");
				break;
			case "Auto_MOZ":
				path = env.getProperty("auto.moz.swift.mx.out.file.path");
				break;
			case "Auto_MWI":
				path = env.getProperty("auto.mwi.swift.mx.out.file.path");
				break;
			case "Auto_ZMB":
				path = env.getProperty("auto.zmb.swift.mx.out.file.path");
				break;
			case "Auto_ZWE":
				path = env.getProperty("auto.zwe.swift.mx.out.file.path");
				break;
			}
			System.out.println("mtTOMXPath" + path + filename);
			OutputStream outputStream = new FileOutputStream(path + filename);
			MxMsgPath = path + filename;
			Mxmsgname = filename;
			String s = new String(sb.toString().getBytes(StandardCharsets.UTF_8));
			byte[] b = s.getBytes(StandardCharsets.ISO_8859_1);
			String s1 = new String(b, "windows-1252");
			outputStream.write(s1.getBytes());
			return s1;
		}
		return Country_code;
	}

	public String MultiplegetPacs_008_001_01Doc(MT_103 mt103, String block3) throws ParseException {
		String dataFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
		XMLGregorianCalendar xgc = null;
		try {
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(dataFormat);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		GroupHeader931 grpHdr = new GroupHeader931();
		String msg_seq_id = mt103.getTag20(); /// Message Identification ID
		grpHdr.setMsgId(msg_seq_id); /// Credit Date Time
		grpHdr.setCreDtTm(xgc); /// Number Of Transaction
		grpHdr.setNbOfTxs("1"); /// Total Inter Bank Settlement Amount
		SettlementInstruction71 sttlmInf = new SettlementInstruction71();
		sttlmInf.setSttlmMtd(SettlementMethod1Code1.INDA);/// Settlement method (CLRG)
		System.out.println("53B+++" + mt103.getTag53B());
		if (mt103.getTag53B() != null && !mt103.getTag53B().isEmpty()) {
			AccountIdentification4Choice1 settlacc = new AccountIdentification4Choice1();
			GenericAccountIdentification11 othr = new GenericAccountIdentification11();
			othr.setId(mt103.getTag53B());
			settlacc.setOthr(othr);
			CashAccount381 cc = new CashAccount381();
			cc.setId(settlacc);
			sttlmInf.setSttlmAcct(cc);
		} else {
			// If mt103.getTag53B() is empty or null, just skip without any error
			// Optionally, you can log or handle this case differently if needed
		}
		grpHdr.setSttlmInf(sttlmInf);
		System.out.println("crdagt+++" + mt103.getTag57A());
		///// creditTransaction Information
		List<CreditTransferTransaction391> cdtTrfTxInf = new ArrayList<CreditTransferTransaction391>();
		CreditTransferTransaction391 creditTransferTransaction391 = new CreditTransferTransaction391();
		/// Payment Identification
		PaymentIdentification71 pmtId = new PaymentIdentification71();
		pmtId.setInstrId(mt103.getTag20().trim());/// Instruction ID
		if ((!mt103.getTag70().equals(""))) {
			String str = mt103.getTag70().replace(System.getProperty("line.separator"), "");
			Pattern pattern = Pattern.compile("ROC/(.*?)//");
			Matcher matcher = pattern.matcher(str);
			String endToEndID = "";
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				pmtId.setEndToEndId(matcher.group(1));/// End to End ID
				endToEndID = matcher.group(1);
			}
			if (endToEndID.equals("")) {
				pmtId.setEndToEndId("NOTPROVIDED");/// End to End ID
			}
		}
		pmtId.setTxId(mt103.getTag20());
		if (block3.contains("{121:")) {
			String uetr = block3.replaceAll(".*\\{121:([a-fA-F0-9\\-]+)\\}.*", "$1");
			pmtId.setUETR(uetr);
		}
		creditTransferTransaction391.setPmtId(pmtId);
		System.out.println("crettxn" + mt103.getTag20());
		CdtTrfTxInf = mt103.getTag20();
		System.out.println("jer45" + CdtTrfTxInf);
		RemittanceInformation161 rmtinf = new RemittanceInformation161();
		System.out.println("3343" + mt103.getTag70());
		List<String> ww = null;
		ww = Arrays.asList(mt103.getTag70());
		rmtinf.setUstrd(ww);
		creditTransferTransaction391.setRmtInf(rmtinf);
		/// Payment Type Information
		PaymentTypeInformation281 pmtTpInf = new PaymentTypeInformation281();
		localInstrm = mt103.getTag23B();
		ActiveCurrencyAndAmount intrBkSttlmAmt = new ActiveCurrencyAndAmount();
		intrBkSttlmAmt.setCcy(SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[1]);/// Currency
		String balanceData1 = SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[2].trim();
		intrBkSttlmAmt.setValue(new BigDecimal(balanceData1.replace(",", ".")));/// Amount
		creditTransferTransaction391.setIntrBkSttlmAmt(intrBkSttlmAmt);
		Date dataFormat1 = new SimpleDateFormat("yyMMdd").parse(SwiftParserMT103Util.parseTag32A(mt103.getTag32A())[0]);
		String dataFormat2 = new SimpleDateFormat("yyyy-MM-dd").format(dataFormat1);
		XMLGregorianCalendar xgc1 = null;
		try {
			xgc1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dataFormat2);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		creditTransferTransaction391.setIntrBkSttlmDt(xgc1);
		ChargeBearerType1Code chrgbr = null;
		String ss = mt103.getTag23B().trim();
		System.out.println(ss + "value5555");
		try {
			if (ss.equals("CRED")) {
				chrgbr = ChargeBearerType1Code.CRED;
			} else if (ss.equals("DEBT")) {
				chrgbr = ChargeBearerType1Code.DEBT;
			} else if (ss.equals("SHA")) {
				chrgbr = ChargeBearerType1Code.SHA;
			} else {
				chrgbr = ChargeBearerType1Code.SLEV;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		creditTransferTransaction391.setChrgBr(chrgbr);
		System.out.println(mt103.getTag23B().trim() + "55655");
		InterBkSttlmAmt = mt103.getTag32A();
		//// Settlement Amount
		if (mt103.getTag33B() != null) {
			ActiveOrHistoricCurrencyAndAmount instdAmt = new ActiveOrHistoricCurrencyAndAmount();
			instdAmt.setCcy(SwiftParserMT103Util.parseTag33B(mt103.getTag33B())[0].trim());
			String balanceData = SwiftParserMT103Util.parseTag33B(mt103.getTag33B())[1].trim();
			instdAmt.setValue(new BigDecimal(balanceData.replace(",", ".")));
			creditTransferTransaction391.setInstdAmt(instdAmt);
		}
		//// Exchange Rate
		if (mt103.getTag36() != null) {
			creditTransferTransaction391.setXchgRate(new BigDecimal(mt103.getTag36()));
		}
		if (mt103.getTag50K() != null) {
			/// Debtor name
			final String[] tag50k = SwiftParserMT103Util.parseTag50K(mt103.getTag50K());
			DbtrAcct = mt103.getTag50K();
			PartyIdentification1351 dbtr = new PartyIdentification1351();
			dbtr.setNm(tag50k[1]);
			if (tag50k.length > 2) {
				final List<String> adrLine = new ArrayList<String>();
				adrLine.add(String.valueOf(tag50k[1]) + "\n" + tag50k[2]);
				PostalAddress241 postalAddress241 = new PostalAddress241();
				postalAddress241.setAdrLine((List<String>) adrLine);
				dbtr.setPstlAdr(postalAddress241);
			}
			creditTransferTransaction391.setDbtr(dbtr);
			CashAccount381 dbtrAcct = new CashAccount381();
			AccountIdentification4Choice1 acc1 = new AccountIdentification4Choice1();
			GenericAccountIdentification11 id = new GenericAccountIdentification11();
			String Dbt = GenericAccountIdentification11.debtacc();
			System.out.println("hereId" + Dbt);
			id.setId((tag50k[0].replace("/", "")));
			acc1.setOthr(id);
			dbtrAcct.setId(acc1);
			creditTransferTransaction391.setDbtrAcct(dbtrAcct);
			/// Debtor Agent
			if (mt103.getTag52A() != null) {
				BranchAndFinancialInstitutionIdentification61 dbtrAgt = new BranchAndFinancialInstitutionIdentification61();
				FinancialInstitutionIdentification181 fin2 = new FinancialInstitutionIdentification181();
				fin2.setBICFI(mt103.getTag52A().trim());
				dbtrAgt.setFinInstnId(fin2);
				creditTransferTransaction391.setDbtrAgt(dbtrAgt);
			}
		}
		/// Creditor Agent
		if (mt103.getTag57() != null) {
			BranchAndFinancialInstitutionIdentification61 cdtrAgt = new BranchAndFinancialInstitutionIdentification61();
			FinancialInstitutionIdentification181 fin3 = new FinancialInstitutionIdentification181();
			fin3.setBICFI(mt103.getTag57().trim());
			cdtrAgt.setFinInstnId(fin3);
			creditTransferTransaction391.setCdtrAgt(cdtrAgt);
		}
		/// Creditor Name
		final String[] tag59 = SwiftParserMT103Util.parseTag50K(mt103.getTag59());
		CdtrAcct = mt103.getTag59();
		PartyIdentification1351 cdtr = new PartyIdentification1351();
		cdtr.setNm(tag59[1]);
		if (tag59.length > 3) {
			final List<String> adrLine1 = new ArrayList<String>();
			adrLine1.add(String.valueOf(tag59[2]) + "\n" + tag59[3]);
			PostalAddress241 postalAddress241_1 = new PostalAddress241();
			postalAddress241_1.setAdrLine(adrLine1);
			cdtr.setPstlAdr(postalAddress241_1);
		} else {
			// Handle the case when tag59 doesn't have enough elements
			// You can log the error or take alternative action
		}
		creditTransferTransaction391.setCdtr(cdtr);
		/// Creditor Account Number
		CashAccount381 cdtrAcct = new CashAccount381();
		AccountIdentification4Choice1 id4 = new AccountIdentification4Choice1();
		GenericAccountIdentification11 gen4 = new GenericAccountIdentification11();
		gen4.setId(tag59[0].replace("/", ""));
		id4.setOthr(gen4);
		cdtrAcct.setId(id4);
		creditTransferTransaction391.setCdtrAcct(cdtrAcct);
		if ((!mt103.getTag70().equals(""))) {
			String str = mt103.getTag70().replace(System.getProperty("line.separator"), "");
			Pattern pattern = Pattern.compile("PURP/(.*?)//");
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				Purpose2Choice1 purpose1 = new Purpose2Choice1();
				purpose1.setCd(matcher.group(1));
				creditTransferTransaction391.setPurp(purpose1);
			}
		}
		if ((!mt103.getTag70().equals(""))) {
			String str = mt103.getTag70().replace(System.getProperty("line.separator"), "" + "//");
			Pattern pattern = Pattern.compile("URI/(.*?)//");
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				RemittanceInformation161 rmtInf = new RemittanceInformation161();
				rmtInf.setUstrd(Arrays.asList(matcher.group(1)));
				creditTransferTransaction391.setRmtInf(rmtInf);
			}
		}

		ChargeBearerType1Code chrgbr1 = null;
		String tag71A = mt103.getTag71A();

		if (tag71A != null) {
		    switch (tag71A) {
		        case "BEN":
		            chrgbr1 = ChargeBearerType1Code.BEN;
		            break;
		        case "OUR":
		            chrgbr1 = ChargeBearerType1Code.OUR;
		            break;
		        case "SHA":
		            chrgbr1 = ChargeBearerType1Code.SHA;
		            break;
		    }
		}

		if (tag71A != null) {
		    tag71A = tag71A.replace("-}", "").trim();
		    if (!tag71A.isEmpty()) {
		        pmtId.setEndToEndId(tag71A);
		    }
		}
		
		cdtTrfTxInf.add(creditTransferTransaction391);
		/// Financial Customer Credit Transfer
		FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf = new FIToFICustomerCreditTransferV08();
		fiToFICstmrCdtTrf.setGrpHdr(grpHdr);
		fiToFICstmrCdtTrf.setCdtTrfTxInf(cdtTrfTxInf);
		System.out.println("CreditTxn" + cdtTrfTxInf);
		/// Document
		Document document = new Document();
		document.setFIToFICstmrCdtTrf(fiToFICstmrCdtTrf);
		/// Convert Document XMl element to String JAXBContext jaxbContext;
		Marshaller jaxbMarshaller;
		StringWriter sw = null;
		try {
			jaxbMarshaller = jaxbContextDocPacs008.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			JaxbCharacterEscapeHandler jaxbCharHandler = new JaxbCharacterEscapeHandler();
			jaxbMarshaller.setProperty("com.sun.xml.bind.characterEscapeHandler", jaxbCharHandler);
			com.bornfire.mx.pacs_008_001_08.ObjectFactory obj = new com.bornfire.mx.pacs_008_001_08.ObjectFactory();
			JAXBElement<Document> jaxbElement = obj.createDocument(document);
			sw = new StringWriter();
			jaxbMarshaller.marshal(jaxbElement, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

}
