package com.huake.saas.access.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.base.IdEntity;

/**
 * 请求日志实体
 * @author laidingqing   jiajun-modefy body改成longtext
 *
 */
@Entity
@Table(name = "app_access_logs")
public class AccessLog extends IdEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 远程访问地址
	 */
	private String remoteHost;
	
	/**
	 * 访问路径上下文，不带参数
	 */
	private String path;
	
	/**
	 * 来源（客户端、微信、手机Web等。）
	 */
	private String channelName;
	
	/**
	 * 客户端类型（即）
	 */
	private String client;
	
	/**
	 * 请求时间
	 */
	private Date reqAt;
	
	/**
	 * 请求体，适用于微信消息及客户端接口
	 */
	private String body;
	
	
	private Date createdAt;

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getReqAt() {
		return reqAt;
	}

	public void setReqAt(Date reqAt) {
		this.reqAt = reqAt;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Column( columnDefinition="LONGTEXT")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
