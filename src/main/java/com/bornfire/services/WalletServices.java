package com.bornfire.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bornfire.entity.Merchant;
import com.bornfire.entity.WalletMasterRep;
import com.bornfire.entity.WalletMasterTable;
import com.bornfire.entity.WalletTranMasterRep;
import com.bornfire.entity.WalletTranMasterTable;

@Service
@ConfigurationProperties("output")
@Transactional
public class WalletServices {
	
	@Autowired
	WalletMasterRep walletMasterRep;
	
	@Autowired
	WalletTranMasterRep walletTranMasterRep;
	
	public List<WalletMasterTable> getWalletList() {
		List<WalletMasterTable> list=walletMasterRep.customFindByAll();
		return list;
	}
	
	public WalletMasterTable getWalletInd(String acctNo) {
		WalletMasterTable data=walletMasterRep.findByIdCustom(acctNo);
		return data;
	}

	public List<WalletTranMasterTable>  getTranList(String acctNo) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String date1=dateFormat.format(new Date());
		List<WalletTranMasterTable> list =walletTranMasterRep.findAllCustom1(acctNo);
		return list;
	}
	
	public List<WalletTranMasterTable>  getTranList1(String fromdate,String todate,String acctNo) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormatTr = new SimpleDateFormat("dd-MMM-yyyy");
		Date dateFrom=dateFormat.parse(fromdate);
		Date dateTo=dateFormat.parse(todate);
		String date1=dateFormatTr.format(dateFrom);
		String date2=dateFormatTr.format(dateTo);
		List<WalletTranMasterTable> list =walletTranMasterRep.findAllCustom(date1,date2,acctNo);
		return list;
	}

	public List<WalletTranMasterTable> getAllTranList() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String date1=dateFormat.format(new Date());
		List<WalletTranMasterTable> list =walletTranMasterRep.findAllCustom2();
		return list;
	}

	public List<WalletTranMasterTable> getTranList2(String fromdate, String todate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormatTr = new SimpleDateFormat("dd-MMM-yyyy");
		Date dateFrom=dateFormat.parse(fromdate);
		Date dateTo=dateFormat.parse(todate);
		String date1=dateFormatTr.format(dateFrom);
		String date2=dateFormatTr.format(dateTo);
		List<WalletTranMasterTable> list =walletTranMasterRep.findAllCustomList(date1,date2);
		return list;
	}
	
	public String createWallet(WalletMasterTable merchantReg, String userid) {
		String msg = "";
		try {
				merchantReg.setEntry_user(userid);
				merchantReg.setEntry_time(new Date());
				merchantReg.setDel_flg("N");
				merchantReg.setEntity_flg("Y");
				walletMasterRep.save(merchantReg);
				msg = "Wallet Added Successfully";

		} catch (Exception e) {
			msg = "Error Occured,Please Contact Administrator";
		}
		return msg;
	}


}
