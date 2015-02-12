package com.huake.saas.activity.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;

/**
 * 活动模块对外微信端接口定义
 * @author laidingqing
 *
 */
@WeixinTarget("activityWeixinTarget")
public interface ActivityWeixinTarget extends WeixinTargetIn {

	@WeixinTargetName("获取最新活动列表")
	public ToWeixinNewsMessage getAllActivities(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id" ,type="hidden") String uid
			,@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入0 ~9") String resultCount);
	
}
