package com.huake.saas.weixin.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;

/**
 * 微信自动回复
 * @author hxl
 *
 */
@WeixinTarget("weixinBase")
public interface WeixinBase extends WeixinTargetIn{
	@WeixinTargetName("自动回复功能")
	public ToWeixinNewsMessage getDetail(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id",type="hidden") String uid,
			@WeixinTargetParameter(enname="内容",zhname="detail",description="输入你需要回复的内容")  String detail);
	
}
