package com.huake.saas.ernie.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huake.base.IdEntity;

/**
 * 抽奖记录
 * @author laidingqing
 *
 */
@Entity
@Table(name = "app_ernie_logs", indexes = {@Index(name="IDX_ERNIELOG_UID", columnList="uid"), @Index(name="IDX_ERNIELOG_MEMID", columnList="memberId")})
public class ErnieLog extends IdEntity {

	private Long memberId;
	
	private Long uid;
	
	private Date createdAt;
	
	private Long erineId;
	/**
	 * 中奖项，对应奖项编号
	 */
	private Long winning;
	
	/**
	 * 中奖人姓名
	 */
	private String memberName;
	/**
	 * 奖品名称
	 */
	private String prizeName;
	
	/**
	 * level 奖品级别
	 */
	private String level;
	
	@Transient
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Transient
	public String getMemberName() {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Transient
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getWinning() {
		return winning;
	}
	public void setWinning(Long winning) {
		this.winning = winning;
	}
	public Long getErineId() {
		return erineId;
	}
	public void setErineId(Long erineId) {
		this.erineId = erineId;
	}
	
	
}
