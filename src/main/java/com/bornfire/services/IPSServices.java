package com.bornfire.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.bornfire.configuration.SequenceGenerator;
import com.bornfire.entity.BenAccount;
import com.bornfire.entity.BukCreditTransferRequest;
import com.bornfire.entity.BulkDebitFndTransferRequest;
import com.bornfire.entity.BulkDebitRemitterAccount;
import com.bornfire.entity.BulkTransaction;
import com.bornfire.entity.CIMMerchantQRAddlInfo;
import com.bornfire.entity.CIMMerchantQRcodeAcctInfo;
import com.bornfire.entity.CIMMerchantQRcodeRequest;
import com.bornfire.entity.FrAccount;
import com.bornfire.entity.FrAccountBulkCredit;
import com.bornfire.entity.MCCreditTransferRequest;
import com.bornfire.entity.MCCreditTransferResponse;
import com.bornfire.entity.ManualFndTransferRequest;
import com.bornfire.entity.ManualTransaction;
import com.bornfire.entity.MerchantQRRegistration;
import com.bornfire.entity.RTPTransactionTable;
import com.bornfire.entity.RTPbulkTransferRequest;
import com.bornfire.entity.RemitterAccount;
import com.bornfire.entity.ToAccount;
import com.bornfire.entity.ToAccountBulkCredit;
import com.bornfire.entity.TranCBSTable;
import com.bornfire.entity.TranCimCBSTable;
import com.bornfire.entity.cimMerchantQRcodeResponse;
import com.bornfire.mcib.Mcib;
import com.google.gson.Gson;

@Component
public class IPSServices {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Environment env;
	
	@Autowired
	SequenceGenerator sequence;

	public String ManualDebitReversalTransaction(TranCimCBSTable tranCBStable, String userID) throws UnknownHostException {

		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU-Device-ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address",  InetAddress.getLocalHost().getHostAddress());

		HttpEntity<TranCimCBSTable> entity = new HttpEntity<>(tranCBStable, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/" + userID + "/reverseDebit", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Reversal Transaction Successfully";
			} else {
				return "Something went wrong at server end";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				System.out.println("dfjhgkuy");
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}

	}

	public String ManualBulkCreditReversalTransaction(TranCimCBSTable tranCBStable, String userID) throws UnknownHostException {

		System.out.println("IPSServices");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU-Device-ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address",  InetAddress.getLocalHost().getHostAddress());

		HttpEntity<TranCimCBSTable> entity = new HttpEntity<>(tranCBStable, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/" + userID + "/reverseBulkCredit", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Reversal Transaction Successfully";
			} else {
				return "Something went wrong at server end";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				System.out.println("dfjhgkuy");
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}

	}

	public String ManualBulkDebitReversalTransaction(TranCimCBSTable tranCBStable, String userID) throws UnknownHostException {
		System.out.println("IPSServices");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU-Device-ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address",  InetAddress.getLocalHost().getHostAddress());

		HttpEntity<TranCimCBSTable> entity = new HttpEntity<>(tranCBStable, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/" + userID + "/reverseBulkDebit", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Reversal Transaction Successfully";
			} else {
				return "Something went wrong at server end";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				System.out.println("dfjhgkuy");
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}

	}

	public String ManualCreditReversalTransaction(TranCimCBSTable tranCBStable, String userID) throws UnknownHostException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU-Device-ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address",  InetAddress.getLocalHost().getHostAddress());

		HttpEntity<TranCimCBSTable> entity = new HttpEntity<>(tranCBStable, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/" + userID + "/reverseCredit", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Reversal Transaction Successfully";
			} else {
				return "Something went wrong at server end";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}

	}

	public String CreateBulkCreditTran(List<BulkTransaction> tranList,String userID) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("P-ID", tranList.get(0).getDoc_ref_id());
		httpHeaders.set("PSU-Device-ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address", InetAddress.getLocalHost().getHostAddress());
		//httpHeaders.set("PSU-ID", "");
		//httpHeaders.set("Participant_BIC", "012");
		httpHeaders.set("USER-ID", userID);
		httpHeaders.set("PSU-Channel", "BIPS");
		//httpHeaders.set("Participant_SOL", tranList.get(0).getRemit_account_no().substring(0, 4));

		BukCreditTransferRequest bulkTranRequest = new BukCreditTransferRequest();

		//FrAccount frAccount = new FrAccount();
		//frAccount.setAcctName(tranList.get(0).getRem_name());
		//frAccount.setAcctNumber(tranList.get(0).getRemit_account_no());
		//frAccount.setSchmType("");
	
		
		FrAccountBulkCredit frAccount = new FrAccountBulkCredit();
		frAccount.setAcctName(tranList.get(0).getRem_name());
		frAccount.setAcctNumber(tranList.get(0).getRemit_account_no());
		frAccount.setSchmType("");
		bulkTranRequest.setFrAccount(frAccount);

		List<ToAccountBulkCredit> toAccountList = new ArrayList<ToAccountBulkCredit>();
		double totAmt = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (BulkTransaction bulkTran : tranList) {
			ToAccountBulkCredit toAccount = new ToAccountBulkCredit();
			toAccount.setAcctName(bulkTran.getBen_acct_name());
			toAccount.setAcctNumber(bulkTran.getBen_acct_no());
			toAccount.setTrAmt(bulkTran.getTran_amt().toString());
			toAccount.setBankCode(bulkTran.getBen_bank_code());
			toAccount.setTrRmks(bulkTran.getTran_particular());
			toAccount.setReqUniqueID(bulkTran.getDoc_sub_id());
			toAccountList.add(toAccount);

			totAmt = totAmt + Double.parseDouble(bulkTran.getTran_amt().toString());

		}
		bulkTranRequest.setToAccountList(toAccountList);
		
		bulkTranRequest.setCurrencyCode(tranList.get(0).getTran_crncy_code());
		bulkTranRequest.setTotAmt(formatter.format(totAmt));
		bulkTranRequest.setTrRmks(tranList.get(0).getTran_particular());

		HttpEntity<BukCreditTransferRequest> entity = new HttpEntity<>(bulkTranRequest, httpHeaders);
		ResponseEntity<MCCreditTransferResponse> response = null;
		try {

			response = restTemplate.postForEntity(env.getProperty("ipsx.url")+"api/ws/bulkCreditFndTransfer",
					entity, MCCreditTransferResponse.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody().getTranID();
			} else {
				return "0";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "0";
			} else {
				return "0";
			}

		} catch (HttpServerErrorException ex) {
			return "0";
		}
	}

	public String CreateBulkDebitTran(List<BulkTransaction> tranList,String userID) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("P-ID", tranList.get(0).getDoc_ref_id());
		httpHeaders.set("PSU-Device-ID", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("USER-ID", userID);
		httpHeaders.set("PSU-Channel", "BIPS");

		BulkDebitFndTransferRequest bulkTranRequest = new BulkDebitFndTransferRequest();

		ToAccount toAcctount = new ToAccount();
		toAcctount.setAcctName(tranList.get(0).getBen_acct_name());
		toAcctount.setAcctNumber(tranList.get(0).getBen_acct_no());
		toAcctount.setBankCode(tranList.get(0).getBen_bank_code());
		bulkTranRequest.setBenAccount(toAcctount);

		List<BulkDebitRemitterAccount> remitterList = new ArrayList<BulkDebitRemitterAccount>();
		double totAmt = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (BulkTransaction bulkTran : tranList) {
			BulkDebitRemitterAccount remAcct = new BulkDebitRemitterAccount();
			remAcct.setRemitterName(bulkTran.getRem_name());
			remAcct.setRemitterAcctNumber(bulkTran.getRemit_account_no());
			remAcct.setTrAmt(bulkTran.getTran_amt().toString());
			remAcct.setCurrencyCode(bulkTran.getTran_crncy_code());
			remAcct.setReqUniqueID(bulkTran.getDoc_sub_id());
			/*
			 * remAcct.setTrRmks("BT/"+tranList.get(0).getBen_bank_code()+"/"+tranList.get(0
			 * ).getBen_acct_no()+"/"+ tranList.get(0).getBen_acct_name());
			 */
			
			remAcct.setTrRmks(bulkTran.getTran_particular());
			remitterList.add(remAcct);

		}
		bulkTranRequest.setRemitterAccount(remitterList);
		

		HttpEntity<BulkDebitFndTransferRequest> entity = new HttpEntity<>(bulkTranRequest, httpHeaders);
		ResponseEntity<MCCreditTransferResponse> response = null;
		try {

			response = restTemplate.postForEntity(env.getProperty("ipsx.url")+"api/ws/bulkDebitFndTransfer",
					entity, MCCreditTransferResponse.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody().getTranID();
			} else {
				return "0";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "0";
			} else {
				return "0";
			}

		} catch (HttpServerErrorException ex) {
			return "0";
		}
	}

	public String createPayableSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt,
			String tranType,String userID) throws UnknownHostException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU_Device_ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("USER_ID", userID);
		
		MCCreditTransferRequest mcCreditTransferRequest=new MCCreditTransferRequest();
		FrAccount frAccount = new FrAccount();
		frAccount.setAcctName("PAYABLE");
		frAccount.setAcctNumber(frAcct);
		mcCreditTransferRequest.setFrAccount(frAccount);
		
		ToAccount toAccount =new ToAccount();
		toAccount.setAcctName("SETTLEMENT");
		toAccount.setAcctNumber(toAcct);
		mcCreditTransferRequest.setToAccount(toAccount);
		
		mcCreditTransferRequest.setTrAmt(trAmt);
		
		
		System.out.println("t:"+mcCreditTransferRequest.toString());
		HttpEntity<MCCreditTransferRequest> entity = new HttpEntity<>(mcCreditTransferRequest, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/payableAccount", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Settlement Transaction Executed Successfully";
			} else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
				return "Something went wrong at server end";
			}else {
				return "Transaction Failed";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}
	}

	public String createReceivableSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt,
			String tranType, String userID) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("PSU_Device_ID",  InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("USER_ID", userID);
		
		MCCreditTransferRequest mcCreditTransferRequest=new MCCreditTransferRequest();
		FrAccount frAccount = new FrAccount();
		frAccount.setAcctName("RECEIVABLE");
		frAccount.setAcctNumber(frAcct);
		mcCreditTransferRequest.setFrAccount(frAccount);
		
		ToAccount toAccount =new ToAccount();
		toAccount.setAcctName("SETTLEMENT");
		toAccount.setAcctNumber(toAcct);
		mcCreditTransferRequest.setToAccount(toAccount);
		
		mcCreditTransferRequest.setTrAmt(trAmt);
		System.out.println("t:"+mcCreditTransferRequest.toString());
		HttpEntity<MCCreditTransferRequest> entity = new HttpEntity<>(mcCreditTransferRequest, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/receivableAccount", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Settlement Transaction Executed Successfully";
			} else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
				return "Something went wrong at server end";
			}else {
				return "Transaction Failed";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}
	}

	public String createIncomeSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt,
			String tranType, String userID) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("USER_ID", userID);
		
		MCCreditTransferRequest mcCreditTransferRequest=new MCCreditTransferRequest();
		FrAccount frAccount = new FrAccount();
		frAccount.setAcctName("INCOME");
		frAccount.setAcctNumber(frAcct);
		mcCreditTransferRequest.setFrAccount(frAccount);
		
		ToAccount toAccount =new ToAccount();
		toAccount.setAcctName("SETTLEMENT");
		toAccount.setAcctNumber(toAcct);
		mcCreditTransferRequest.setToAccount(toAccount);
		
		mcCreditTransferRequest.setTrAmt(trAmt);
		
		HttpEntity<MCCreditTransferRequest> entity = new HttpEntity<>(mcCreditTransferRequest, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/incomeAccount", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Settlement Transaction Successfully";
			} else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
				return "Something went wrong at server end";
			}else {
				return "Transaction Failed";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}
	}

	public String createExpenseSettlTransaction(String frAcct, String toAcct, String tranDate, String trAmt,
			String tranType, String userID) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("USER_ID", userID);
		
		MCCreditTransferRequest mcCreditTransferRequest=new MCCreditTransferRequest();
		FrAccount frAccount = new FrAccount();
		frAccount.setAcctName("EXPENSE");
		frAccount.setAcctNumber(frAcct);
		mcCreditTransferRequest.setFrAccount(frAccount);
		
		ToAccount toAccount =new ToAccount();
		toAccount.setAcctName("SETTLEMENT");
		toAccount.setAcctNumber(toAcct);
		mcCreditTransferRequest.setToAccount(toAccount);
		
		mcCreditTransferRequest.setTrAmt(trAmt);
		
		HttpEntity<MCCreditTransferRequest> entity = new HttpEntity<>(mcCreditTransferRequest, httpHeaders);
		ResponseEntity<String> response = null;
		try {

			response = restTemplate.postForEntity(
					env.getProperty("ipsx.url")+"api/ws/expenseAccount", entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return "Settlement Transaction Successfully";
			} else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
				return "Something went wrong at server end";
			}else {
				return "Transaction Failed";
			}

		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "Something went wrong at server end";

			} else {
				return "Transaction Failed";
			}

		} catch (HttpServerErrorException ex) {
			return "Something went wrong at server end";
		}
	}

	public String CreateManualBulkTran(List<ManualTransaction> bulkTranList,String userID) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		httpHeaders.set("P-ID", bulkTranList.get(0).getDoc_ref_id());
		httpHeaders.set("PSU-Device-ID", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("USER-ID", userID);
		httpHeaders.set("PSU-Channel", "BIPS");

		List<ManualFndTransferRequest> manualList = new ArrayList<>();
		
		for(ManualTransaction manual:bulkTranList) {
			ManualFndTransferRequest manReq=new ManualFndTransferRequest();
			manReq.setRemitterName(manual.getRem_name());
			manReq.setRemitterAcctNumber(manual.getRemit_account_no());
			manReq.setBeneficiaryName(manual.getBen_acct_name());
			manReq.setBeneficiaryAcctNumber(manual.getBen_acct_no());
			manReq.setBeneficiaryBankCode(manual.getBen_bank_code());
			manReq.setCurrencyCode(manual.getTran_crncy_code());
			manReq.setTrAmt(manual.getTran_amt().toString());
			manReq.setReqUniqueID(manual.getDoc_ref_id()+"/"+manual.getDoc_sub_id());
			manReq.setTrRmks(manual.getTran_particular());
			manualList.add(manReq);
		}
		

		HttpEntity<List<ManualFndTransferRequest>> entity = new HttpEntity<>(manualList, httpHeaders);
		ResponseEntity<MCCreditTransferResponse> response = null;
		try {

			response = restTemplate.postForEntity(env.getProperty("ipsx.url")+"api/ws/manualCreditFndTransfer",
					entity, MCCreditTransferResponse.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody().getTranID();
			} else {
				return "0";
			}

		}catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "0";
			} else {
				return "0";
			}

		} catch (HttpServerErrorException ex) {
			return "0";
		}
	}
	
	
	public String CreateRTPBulkTran(List<RTPTransactionTable> bulkTranList,String userID) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		httpHeaders.set("P-ID", bulkTranList.get(0).getDoc_ref_id());
		httpHeaders.set("PSU-Device-ID", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address", InetAddress.getLocalHost().getHostAddress());
		//httpHeaders.set("USER-ID", userID);
		httpHeaders.set("PSU-Channel", "BIPS");

		
		RTPbulkTransferRequest rtpBulkTransferRequest=new RTPbulkTransferRequest();
		
		
		RemitterAccount remitterAccount=new RemitterAccount();
		remitterAccount.setAcctName(bulkTranList.get(0).getRem_name());
		remitterAccount.setAcctNumber(bulkTranList.get(0).getRemit_account_no());
		remitterAccount.setCurrencyCode(bulkTranList.get(0).getTran_crncy_code());
		remitterAccount.setBankCode(bulkTranList.get(0).getRem_bank_code());
		rtpBulkTransferRequest.setRemitterAccount(remitterAccount);
		
		List<BenAccount> benList = new ArrayList<>();

		for(RTPTransactionTable manual:bulkTranList) {
			BenAccount manReq=new BenAccount();
			manReq.setBenName(manual.getBen_acct_name());
			manReq.setBenAcctNumber(manual.getBen_acct_no());
			manReq.setBankCode(manual.getBen_bank_code());
			manReq.setCurrencyCode(manual.getTran_crncy_code());
			manReq.setTrAmt(manual.getTran_amt().toString());
			manReq.setReqUniqueId(manual.getDoc_sub_id());
			manReq.setTrRmks(manual.getTran_particular());
			benList.add(manReq);
		}
		rtpBulkTransferRequest.setBenAccount(benList);

		HttpEntity<RTPbulkTransferRequest> entity = new HttpEntity<>(rtpBulkTransferRequest, httpHeaders);
		ResponseEntity<MCCreditTransferResponse> response = null;
		try {

			response = restTemplate.postForEntity(env.getProperty("ipsx.url")+"api/ws/bulkRTPTransfer",
					entity, MCCreditTransferResponse.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody().getTranID();
			} else {
				
				return "0";
			}

		}catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return "0";
			} else {
				MCCreditTransferResponse errorRestResponse = new Gson().fromJson(ex.getResponseBodyAsString().toString(), MCCreditTransferResponse.class);

				System.out.println(errorRestResponse.getError_Desc().get(0));
				return "0";
			}

		} catch (HttpServerErrorException ex) {
			return "0";
		}
	}


	public void getMCIBresponse() {
		
		
		//HttpEntity<String> entity = new HttpEntity<>("");
		
		ResponseEntity<Mcib> response = null;
		try {

			response = restTemplate.postForEntity(
					"http://10.103.2.128:7778/mcibws/servlet/validate?username=BOB&pass=ABC123$$&group=BARB&reporttype=I&residentflag=R&countrycode=MU&entitytype=I&entitycode=B2407671403372",
					"", Mcib.class);
			

			
			//ResponseEntity<String> response1 = restTemplate.exchange("http://10.103.2.128:7778/mcibws/servlet/validate?username=BOB&pass=ABC123$$&group=BARB&reporttype=I&residentflag=R&countrycode=MU&entitytype=I&entitycode=B2407671403372",
				//	HttpMethod.POST, null, String.class);
			
			//String responseBody = response1.getBody();
			
			//System.out.println(responseBody);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				System.out.println("MCIB Response->"+response.toString());
				System.out.println("MCIB Response->"+response.getBody().getCREDIT_PROFILE_RECORDS().getCREDIT_PROFILE().get(0).getADDRESS1());
				
				System.out.println("MCIB Response->"+response.getBody().getCREDIT_PROFILE_RECORDS().getCREDIT_PROFILE().get(0).getADDRESS2());

			} else {
				System.out.println("MCIB Response err->"+response.getStatusCode());
			}

		} catch (HttpClientErrorException ex) {
			System.out.println("MCIB Response erroe->"+ex.getStatusCode()+"ok"+ex.getLocalizedMessage());

		} catch (HttpServerErrorException ex) {
			System.out.println("MCIB Response erroer1->"+ex.getStatusCode()+"ok"+ex.getLocalizedMessage());
		}

		
	}

	
	
	public ResponseEntity<cimMerchantQRcodeResponse> getMerchantQrCode(MerchantQRRegistration merchantQRgenerator) throws UnknownHostException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("P-ID", sequence.generateMerchantQRPID());
		httpHeaders.set("PSU-Device-ID", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-IP-Address", InetAddress.getLocalHost().getHostAddress());
		httpHeaders.set("PSU-Channel", "BIPS");

		
		CIMMerchantQRcodeRequest  cimMerchantQRcodeRequest=new CIMMerchantQRcodeRequest();
		System.out.println(merchantQRgenerator.getPayload_format_indicator().toString());
		cimMerchantQRcodeRequest.setPayloadFormatIndiator(merchantQRgenerator.getPayload_format_indicator().toString());
		cimMerchantQRcodeRequest.setPointOfInitiationFormat(merchantQRgenerator.getPoi_method().toString());
	
		CIMMerchantQRcodeAcctInfo merchantQRAcctInfo=new CIMMerchantQRcodeAcctInfo();
		merchantQRAcctInfo.setGlobalID(merchantQRgenerator.getGlobal_unique_id());
		merchantQRAcctInfo.setPayeeParticipantCode(merchantQRgenerator.getPayee_participant_code());
		merchantQRAcctInfo.setMerchantAcctNumber(merchantQRgenerator.getMerchant_acct_no());
		merchantQRAcctInfo.setMerchantID(merchantQRgenerator.getMerchant_id());
		cimMerchantQRcodeRequest.setMerchantAcctInformation(merchantQRAcctInfo);
		
		cimMerchantQRcodeRequest.setMCC(merchantQRgenerator.getMerchant_category_code().toString());
		cimMerchantQRcodeRequest.setCurrency(merchantQRgenerator.getTransaction_crncy().toString());
		
		
		if(!String.valueOf(merchantQRgenerator.getTransaction_amt()).equals("null")&&
				!String.valueOf(merchantQRgenerator.getTransaction_amt()).equals("")) {
			cimMerchantQRcodeRequest.setTrAmt(merchantQRgenerator.getTransaction_amt().toString());
		}
		
		
		/*if(merchantQRgenerator.getTip_or_conv_indicator().toString().equals("0")){
			cimMerchantQRcodeRequest.setConvenienceIndicator(false);
		}else {
			cimMerchantQRcodeRequest.setConvenienceIndicator(true);
			cimMerchantQRcodeRequest.setConvenienceIndicatorFeeType(merchantQRgenerator.getConv_fees_type().toString());
			cimMerchantQRcodeRequest.setConvenienceIndicatorFee(merchantQRgenerator.getValue_conv_fees());
		}*/
		
		if(merchantQRgenerator.getTip_or_conv_indicator()!=null) {
			if(!merchantQRgenerator.getTip_or_conv_indicator().toString().equals("")){

				if(merchantQRgenerator.getTip_or_conv_indicator().toString().equals("01")) {
					cimMerchantQRcodeRequest.setTipOrConvenienceIndicator("01");
				}else if(merchantQRgenerator.getTip_or_conv_indicator().toString().equals("02")) {
					if(merchantQRgenerator.getConv_fees_type().equals("Fixed")) {
						cimMerchantQRcodeRequest.setTipOrConvenienceIndicator("02");
						cimMerchantQRcodeRequest.setConvenienceIndicatorFee(merchantQRgenerator.getValue_conv_fees());
					}else if(merchantQRgenerator.getConv_fees_type().equals("Percentage")) {
						cimMerchantQRcodeRequest.setTipOrConvenienceIndicator("03");
						cimMerchantQRcodeRequest.setConvenienceIndicatorFee(merchantQRgenerator.getValue_conv_fees());

					}
				}
				
			}
		}
		
		
		cimMerchantQRcodeRequest.setCountryCode(merchantQRgenerator.getCountry().toString());
		cimMerchantQRcodeRequest.setMerchantName(merchantQRgenerator.getMerchant_name().toString());
		cimMerchantQRcodeRequest.setCity(merchantQRgenerator.getCity().toString());
		
		if(!String.valueOf(merchantQRgenerator.getZip_code()).equals("null")&&
				!String.valueOf(merchantQRgenerator.getZip_code()).equals("")) {
			cimMerchantQRcodeRequest.setPostalCode(merchantQRgenerator.getZip_code().toString());
		}
		
		
		
		CIMMerchantQRAddlInfo cimMercbantQRAddlInfo=new CIMMerchantQRAddlInfo();
		cimMercbantQRAddlInfo.setBillNumber(merchantQRgenerator.getBill_number());
		cimMercbantQRAddlInfo.setMobileNumber(merchantQRgenerator.getMobile());
		cimMercbantQRAddlInfo.setStoreLabel(merchantQRgenerator.getStore_label());
		cimMercbantQRAddlInfo.setLoyaltyNumber(merchantQRgenerator.getLoyalty_number());
		cimMercbantQRAddlInfo.setCustomerLabel(merchantQRgenerator.getCustomer_label());
		cimMercbantQRAddlInfo.setTerminalLabel(merchantQRgenerator.getTerminal_label());
		cimMercbantQRAddlInfo.setReferenceLabel(merchantQRgenerator.getReference_label());
		cimMercbantQRAddlInfo.setPurposeOfTransaction(merchantQRgenerator.getPurpose_of_tran());
		cimMercbantQRAddlInfo.setAddlDataRequest(merchantQRgenerator.getAdditional_details());
		
		cimMerchantQRcodeRequest.setAdditionalDataInformation(cimMercbantQRAddlInfo);
		
		HttpEntity<CIMMerchantQRcodeRequest> entity = new HttpEntity<>(cimMerchantQRcodeRequest, httpHeaders);
		ResponseEntity<cimMerchantQRcodeResponse> response = null;
		try {

			response = restTemplate.postForEntity(env.getProperty("ipsx.url")+"api/ws/generateMerchantQRcode",
					entity, cimMerchantQRcodeResponse.class);

			System.out.println("data-->");
			
			if (response.getStatusCode() == HttpStatus.OK) {
				System.out.println("data-->OK");
				return new ResponseEntity<cimMerchantQRcodeResponse>(response.getBody(), HttpStatus.OK);

			} else {
				return response;
			}

		}catch (HttpClientErrorException ex) {
			
			if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				cimMerchantQRcodeResponse errorRestResponse = new Gson().fromJson(ex.getResponseBodyAsString().toString(), cimMerchantQRcodeResponse.class);
				return new ResponseEntity<cimMerchantQRcodeResponse>(errorRestResponse, HttpStatus.BAD_REQUEST);
			} else {
				cimMerchantQRcodeResponse c24ftResponse = new cimMerchantQRcodeResponse("500",Arrays.asList("Something went wrong at server end"));
				return new ResponseEntity<cimMerchantQRcodeResponse>(c24ftResponse, ex.getStatusCode());
			}



		} catch (HttpServerErrorException ex) {
			cimMerchantQRcodeResponse c24ftResponse = new cimMerchantQRcodeResponse("500",Arrays.asList("Something went wrong at server end"));
			return new ResponseEntity<cimMerchantQRcodeResponse>(c24ftResponse, ex.getStatusCode());
		}

	}

}
