package com.huake.saas.product.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;

/**
 * 产品对外微信接口
 * @author Administrator
 *
 */
@WeixinTarget("productWeixinTarget")
public interface ProductWeixinTarget extends WeixinTargetIn{

	@WeixinTargetName("根据分类查询展示产品")
	public ToWeixinNewsMessage getProductByCategory(
		@WeixinTargetParameter(enname="用户id",zhname="uid",description="请输入id",type="hidden") String uid,
	    @WeixinTargetParameter(enname="分类",zhname="category",description="请选择分类" ,type="alert",handle="#")String category,
	    @WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入 0~9 ")String resultCount);

	@WeixinTargetName("查询最新产品")
	public ToWeixinNewsMessage getProduct(@WeixinTargetParameter(enname="用户id",zhname="uid",description="请输入id",type="hidden") String uid,
			@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入 0~9 ")String resultCount);


}
