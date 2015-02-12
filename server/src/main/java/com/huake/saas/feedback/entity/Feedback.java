package com.huake.saas.feedback.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.huake.saas.base.entity.BaseEntity;
/**
 *意见反馈
 */
@Entity
@Table(name = "app_feedback")
public class Feedback extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**普通*/
	public static final String CATEGORY_COMMON = "com";
	/**客户端反馈*/
	public static final String CATEGORY_CLIENT_CALLBACK = "c_back";
	/**后台回复反馈*/
	public static final String CATEGORY_SERVICE_REPLY = "s_reply";
	/**后台回复反馈  推送的信息类型*/
	public static final String CATEGORY_FEEBACK_REPLY = "feeback_reply";
	/**
	 * 已读
	 */
	public static final String STATUS_READ = "2";
	
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 消息类别
	 */
	private String category;
	/**
	 * 接受会员Id
	 */
	private Long memberId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
