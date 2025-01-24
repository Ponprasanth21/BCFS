package com.bornfire.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bornfire.entity.TransactionAdmin;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
@Transactional
public class TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	DataSource srcdataSource;

	public List<TransactionAdmin> getTransaction(String acct_num) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num,tran_amt,tran_status,tran_report_code,part_tran_id from TRANS_DETAILS where acct_num ='"
						+ acct_num + "' and rownum<=10 order by tran_date desc");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			/* String acct_num = (String) a[0]; */
			BigDecimal tran_amt = (BigDecimal) a[1];
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = (BigDecimal) a[4];

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransaction() {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num,tran_amt,tran_status,tran_report_code,part_tran_id from TRANS_DETAILS where   rownum<=10 order by tran_date desc");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			String acct_num = (String) a[0];
			BigDecimal tran_amt = (BigDecimal) a[1];
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = (BigDecimal) a[4];

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionFailed() {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num,tran_amt,tran_status,tran_report_code,part_tran_id from TRANS_DETAILS where tran_status IS NULL and rownum<=10 order by tran_date desc");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			String acct_num = (String) a[0];
			BigDecimal tran_amt = (BigDecimal) a[1];
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = (BigDecimal) a[4];

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionFailed(String acct_num) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num,tran_amt,tran_status,tran_report_code,part_tran_id from TRANS_DETAILS where acct_num ='"
						+ acct_num + "' and tran_status IS NULL and rownum<=10 order by tran_date desc");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			/* String acct_num = (String) a[0]; */
			BigDecimal tran_amt = (BigDecimal) a[1];
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = (BigDecimal) a[4];

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionFailed(String from_date, String to_date, String acct_num) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num, tran_amt,tran_status,tran_report_code,part_tran_id from trans_details where acct_num='"
						+ acct_num + "' and tran_status IS NULL and tran_date BETWEEN TO_DATE('" + from_date
						+ "','YYYY-MM-DD') AND TO_DATE('" + to_date + "','YYYY-MM-DD')");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			/* String acct_num = (String) a[0]; */
			BigDecimal tran_amt = new BigDecimal(a[1].toString());
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = new BigDecimal(a[4].toString());

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionDetail(String from_date, String to_date) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num, tran_amt,tran_status,tran_report_code,part_tran_id from trans_details where  tran_date BETWEEN TO_DATE('"
						+ from_date + "','YYYY-MM-DD') AND TO_DATE('" + to_date + "','YYYY-MM-DD')");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			String acct_num = (String) a[0];
			BigDecimal tran_amt = new BigDecimal(a[1].toString());
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = new BigDecimal(a[4].toString());

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionDetail(String from_date, String to_date, String acct_num) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num, tran_amt,tran_status,tran_report_code,part_tran_id from trans_details where acct_num='"
						+ acct_num + "' and tran_date BETWEEN TO_DATE('" + from_date + "','YYYY-MM-DD') AND TO_DATE('"
						+ to_date + "','YYYY-MM-DD')");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			/* String acct_num = (String) a[0]; */
			BigDecimal tran_amt = new BigDecimal(a[1].toString());
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = new BigDecimal(a[4].toString());

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	public List<TransactionAdmin> getTransactionFailed(String from_date, String to_date) {
		Session ms = sessionFactory.getCurrentSession();

		List<TransactionAdmin> genRefvalList = new ArrayList<TransactionAdmin>();

		Query qs = ms.createNativeQuery(
				"select acct_num, tran_amt,tran_status,tran_report_code,part_tran_id from trans_details where tran_status IS NULL and tran_date BETWEEN TO_DATE('"
						+ from_date + "','YYYY-MM-DD') AND TO_DATE('" + to_date + "','YYYY-MM-DD') ");

		List<Object[]> result = qs.getResultList();

		for (Object[] a : result) {
			String acct_num = (String) a[0];
			BigDecimal tran_amt = new BigDecimal(a[1].toString());
			String tran_status = (String) a[2];
			String tran_report_code = (String) a[3];
			BigDecimal part_tran_id = new BigDecimal(a[4].toString());

			TransactionAdmin genRefval = new TransactionAdmin(acct_num, tran_amt, tran_status, tran_report_code,
					part_tran_id);

			genRefvalList.add(genRefval);
		}

		return genRefvalList;
	}

	
}
