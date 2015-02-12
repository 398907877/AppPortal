package com.huake.saas.news.service;

import com.huake.saas.weixin.annonation.WeixinTarget;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;
import com.huake.saas.weixin.model.ToWeixinNewsMessage;
import com.huake.saas.weixin.service.WeixinTargetIn;


@WeixinTarget("newsWeixinTarget")
public interface NewsWeixinTarget extends WeixinTargetIn{

	@WeixinTargetName("根据分类获取资讯列表")
	public ToWeixinNewsMessage getNewsByCategory(@WeixinTargetParameter(enname="uid-企业标识",zhname="uid",description="请输入id",type="hidden") String uid, 
			@WeixinTargetParameter(enname="分类",zhname="category",description="选择所在的分类",type="alert",handle="mgr/news/category/weixin") String category,
			@WeixinTargetParameter(enname="行数",zhname="resultCount",description="数量输入 0~9")  String resultCount);
	@WeixinTargetName("测试json")
	public ToWeixinNewsMessage gettest(@WeixinTargetParameter(enname="名字",zhname="name",description="请输入用户名字")String name);
	
	@WeixinTargetName("获取最新资讯列表")
	public ToWeixinNewsMessage getNews(@WeixinTargetParameter(enname="这个id",zhname="uid",description="请输入id",type="hidden") String uid, 
			@WeixinTargetParameter(enname="数量",zhname="resultCount",description="数量输入 0~9")  String resultCount);
}
