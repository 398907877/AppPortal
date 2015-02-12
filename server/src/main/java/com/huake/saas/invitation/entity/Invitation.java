package com.huake.saas.invitation.entity;

import java.util.Date;
import java.util.List;

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
@Table(name = "app_invitation")
public class Invitation{

	public static final String PARMS_INVITATIONS = "invitations";
	public static final String PARMS_INVITATION = "invitation";
	
	/**
	 * 已经审核
	 */
	public static final Integer INVITATION_IS_CHECK = 1;
	
	/**
	 * 未审核
	 */
	public static final Integer INVITATION_NOT_CHECK = 0;
	private Long id;
	
	private String uid;
	
	protected String status;
	
	private Date crtDate;
	
	private Date upTime;
	
	private int replyNum;
	

	/**
	 * 论坛用户Id
	 */
	private Long userId;
	
	/**
	 * 资源表
	 */
	private List<InvitationResources> resources;
	
	private TenancyUser user;
//	private NoPwdUser user;
	
	/**
	 * 创建人名称
	 */
	private String crUser;
	


	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}
	@Transient
	public List<InvitationReply> getReplys() {
		return replys;
	}

	public void setReplys(List<InvitationReply> replys) {
		this.replys = replys;
	}

	/**
	 * 评论列表
	 */
	private List<InvitationReply> replys;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	/**
	 * 帖子标题
	 */
	private String title;
	
	/**
	 * 帖子内容
	 */
	private String introduce;
	
	/**
	 * 是否审核
	 */
	private Integer ischeck; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Lob
	@NotBlank
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public Integer getIscheck() {
		return ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}
	

	

	
	
	@Transient
	public List<InvitationResources> getResources() {
		return resources;
	}
	@Transient
	public TenancyUser getUser() {
		return user;
	}

	public void setUser(TenancyUser user) {
		this.user = user;
	}
	@Transient
	public void setResources(List<InvitationResources> resources) {
		this.resources = resources;
	}
}
