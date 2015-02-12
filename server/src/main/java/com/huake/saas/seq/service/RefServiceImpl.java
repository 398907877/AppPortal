package com.huake.saas.seq.service;

import java.util.Date;
import java.util.Locale;

import org.springframework.format.datetime.DateFormatter;

import com.huake.seq.service.SeqService;

/**
 * 序列服务生成配置 
 * @author skylai
 *
 */
public class RefServiceImpl implements SeqRefService {

	private SeqService seqServ;
	
	private String seqMCardNoName;
	
	private String seqOrderNoName;
	

	public SeqService getSeqServ() {
		return seqServ;
	}

	public void setSeqServ(SeqService seqServ) {
		this.seqServ = seqServ;
	}

	public String getSeqMCardNoName() {
		return seqMCardNoName;
	}

	public void setSeqMCardNoName(String seqMCardNoName) {
		this.seqMCardNoName = seqMCardNoName;
	}

	public String getSeqOrderNoName() {
		return seqOrderNoName;
	}

	public void setSeqOrderNoName(String seqOrderNoName) {
		this.seqOrderNoName = seqOrderNoName;
	}

	public String getMCardNO(){
		DateFormatter formatter = new DateFormatter();
		formatter.setPattern("yy");
		return this.seqServ.genSeq(this.seqMCardNoName, formatter.print(new Date(), Locale.SIMPLIFIED_CHINESE));
	}
	
	public String getOrderNO(){
		return this.seqServ.genSeq(this.seqOrderNoName);
	}
	
}
