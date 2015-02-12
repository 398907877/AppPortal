package com.huake.saas.tcustomer.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;

/**
 * 会员企业模块 对外微信接口定义
 * @author Administrator
 *
 */
@WeixinTarget("tcustomerWeixinTarget")
public interface TcustomerWeixinTarget extends WeixinTargetIn{
	@WeixinTargetName("获取最新会员企业")
	public ToWeixinNewsMessage getTcustomer(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id" ,type="hidden") String uid
			,@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入 0~9") String resultCount);
	
}
