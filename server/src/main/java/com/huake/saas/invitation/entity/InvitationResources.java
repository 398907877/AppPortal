package com.huake.saas.invitation.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 帖子资源表
 * @author zhongshuhui
 */
@Entity
@Table(name = "app_invitation_resources")
public class InvitationResources {

    public static final String PARMS_INVITATION_RESOURCES= "invitationResources";
	
	public static final String PARMS_INVITATION_RESOURCESS= "invitationResourcess";
	
	private Long id;
	
	private Date crtDate;
	
	/**
	 * 帖子内容
	 */
	private String url;
	
	/**
	 * 关联帖子Id
	 */
	private long invitationId;
	
	
	
	@Lob
	@NotBlank
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(long invitationId) {
		this.invitationId = invitationId;
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

}
