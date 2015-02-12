package com.huake.saas.supply.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;

@WeixinTarget("supplyDemandWeixinTarget")
public interface SupplyDemandWeixinTarget extends WeixinTargetIn{
	
	@WeixinTargetName("获取最新供应信息列表")
	public ToWeixinNewsMessage getSupply(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id",type="hidden") String uid, 
			@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量 输入 0~9")  String resultCount);
	
	@WeixinTargetName("获取最新需求信息列表")
	public ToWeixinNewsMessage getDemand(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id",type="hidden") String uid, 
			@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量 输入 0~9")  String resultCount);
}
