package com.huake.saas.invitation.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;

@WeixinTarget("invitationWeixinTarget")
public interface InvitationWeixinTarget extends WeixinTargetIn{
	
	@WeixinTargetName("获取最新帖子列表")
	public ToWeixinNewsMessage getInvitations(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id",type="hidden") String uid, 
			@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入 0~9" )  String resultCount);
}
