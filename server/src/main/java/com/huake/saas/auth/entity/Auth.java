package com.huake.saas.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 授权书，用于授权企业功能与配置,名称对应功能模块的名称
 * @author laidingqing
 */
@Entity
@Table(name="app_auth")
public class Auth implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static int STATUS_STALE  = 0;	// 删除
	public final static int STATUS_ACTIVE = 1;	// 激活
	public final static int STATUS_LOCK   = 2;	// 锁定
	
	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 企业号
	 */
	@Column(name = "uid")
	private String uid;
	
	/**
	 * 生效日期
	 */
	@Column(name="issue_at")
	private Date issueDate;
	
	/**
	 * 失效日期
	 */
	@Column(name="due_at")
	private Date dueDate;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 更新日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateDate;
	
	/**
	 * 创建日期
	 */
	@Column(name="create_at", updatable=false)
	private Date createDate;
	
    @Version
	@Column(name="version")
	private Integer version;
    
    /**
	 * 备注
	 */
	@Column(name="memo")
	private String memo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
    
}
