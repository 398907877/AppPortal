package com.huake.saas.invitation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huake.saas.user.entity.TenancyUser;

/**
 * 帖子信息管理
 * @author zhongshuhui
 */
@Entity
@Table(name = "app_invitation_reply")
public class InvitationReply {
	public static final String PARMS_INVITATION_REPLYS = "invitationReplys";
	public static final String PARMS_INVITATION_REPLY = "invitationReply";
	private Long id;
	
	/**
	 * 回复时间
	 */
	private Date crtDate;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 创建人名称
	 */
	private String crUser;
	
	/**
	 * 回复内容
	 */
	private String introduce;
	
	/**
	 * 论坛用户id
	 */
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 帖子关联id
	 */
	private Long invitationId;
	
	private TenancyUser user;

	@Transient
	public TenancyUser getUser() {
		return user;
	}

	public void setUser(TenancyUser user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	@Lob
	@NotBlank
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Long getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	
	
}
