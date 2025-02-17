package com.bornfire.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
@Entity
@Table(name="BIPS_SWIFT_FILE_MGT_TABLE")
@Component
public class BIPS_SWIFT_MSG_MGT implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String	srl_no;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date	date_of_process;
	private String	file_name;
	private String	swift_msg_type;
	private String	is_convert;
	private String	file_name_conv;
	private String	swift_mrg_type_conv;
	private String	status;
	private Date	entry_time;
	private String converted_file_name;
	private String mx_reference_no;
	private String mt_reference_no;
	private String time_of_conv;
	private String country_code;
	private String remarks;
	
	@Lob
	private String mx_file;
	@Lob
	private String mt_file;
	public String getSrl_no() {
		return srl_no;
	}
	public void setSrl_no(String srl_no) {
		this.srl_no = srl_no;
	}
	public Date getDate_of_process() {
		return date_of_process;
	}
	public void setDate_of_process(Date date_of_process) {
		this.date_of_process = date_of_process;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getSwift_msg_type() {
		return swift_msg_type;
	}
	public void setSwift_msg_type(String swift_msg_type) {
		this.swift_msg_type = swift_msg_type;
	}
	public String getIs_convert() {
		return is_convert;
	}
	public void setIs_convert(String is_convert) {
		this.is_convert = is_convert;
	}
	public String getFile_name_conv() {
		return file_name_conv;
	}
	public void setFile_name_conv(String file_name_conv) {
		this.file_name_conv = file_name_conv;
	}
	public String getSwift_mrg_type_conv() {
		return swift_mrg_type_conv;
	}
	public void setSwift_mrg_type_conv(String swift_mrg_type_conv) {
		this.swift_mrg_type_conv = swift_mrg_type_conv;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
	public String getConverted_file_name() {
		return converted_file_name;
	}
	public void setConverted_file_name(String converted_file_name) {
		this.converted_file_name = converted_file_name;
	}
	public String getMx_reference_no() {
		return mx_reference_no;
	}
	public void setMx_reference_no(String mx_reference_no) {
		this.mx_reference_no = mx_reference_no;
	}
	public String getMt_reference_no() {
		return mt_reference_no;
	}
	public void setMt_reference_no(String mt_reference_no) {
		this.mt_reference_no = mt_reference_no;
	}
	public String getTime_of_conv() {
		return time_of_conv;
	}
	public void setTime_of_conv(String time_of_conv) {
		this.time_of_conv = time_of_conv;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMx_file() {
		return mx_file;
	}
	public void setMx_file(String mx_file) {
		this.mx_file = mx_file;
	}
	public String getMt_file() {
		return mt_file;
	}
	public void setMt_file(String mt_file) {
		this.mt_file = mt_file;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BIPS_SWIFT_MSG_MGT(String srl_no, Date date_of_process, String file_name, String swift_msg_type,
			String is_convert, String file_name_conv, String swift_mrg_type_conv, String status, Date entry_time,
			String converted_file_name, String mx_reference_no, String mt_reference_no, String time_of_conv,
			String country_code, String remarks, String mx_file, String mt_file) {
		super();
		this.srl_no = srl_no;
		this.date_of_process = date_of_process;
		this.file_name = file_name;
		this.swift_msg_type = swift_msg_type;
		this.is_convert = is_convert;
		this.file_name_conv = file_name_conv;
		this.swift_mrg_type_conv = swift_mrg_type_conv;
		this.status = status;
		this.entry_time = entry_time;
		this.converted_file_name = converted_file_name;
		this.mx_reference_no = mx_reference_no;
		this.mt_reference_no = mt_reference_no;
		this.time_of_conv = time_of_conv;
		this.country_code = country_code;
		this.remarks = remarks;
		this.mx_file = mx_file;
		this.mt_file = mt_file;
	}
	public BIPS_SWIFT_MSG_MGT() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BIPS_SWIFT_MSG_MGT [srl_no=" + srl_no + ", date_of_process=" + date_of_process + ", file_name="
				+ file_name + ", swift_msg_type=" + swift_msg_type + ", is_convert=" + is_convert + ", file_name_conv="
				+ file_name_conv + ", swift_mrg_type_conv=" + swift_mrg_type_conv + ", status=" + status
				+ ", entry_time=" + entry_time + ", converted_file_name=" + converted_file_name + ", mx_reference_no="
				+ mx_reference_no + ", mt_reference_no=" + mt_reference_no + ", time_of_conv=" + time_of_conv
				+ ", country_code=" + country_code + ", mx_file=" + mx_file + ", mt_file=" + mt_file + "]";
	}
	
	
}
