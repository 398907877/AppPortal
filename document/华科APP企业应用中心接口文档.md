# 总体描述
应用中心所有接口以Http协议通讯，报文档格式采用JSON

# 认证

华科应用客户端接口认证采用OAuth实现，需要了解什么是OAuth请问问度娘。
1. 每个客户端（租户）由后台管理生成各自公钥及私钥
2. 客户端根据私钥HMAC512加密上下文路径、请求方法、日期，具体参考API
3. 服务端根据接口租户标识以相同方式对比加密串完成认证访问校验
4. 所有接口必要字段增加参数：业务模块名称, uid, 客户机系统名称

# 图文资讯

## 获取图文资讯列表

Request URI: /api/v1/news/list/?category=[category]&page=[page]&uid=[uid]&bizcode=news

Request Method: GET

Request Paramater:

	category：分类编号
	page：页数
	uid：租户编号
	bizcode: 调用业务类型，用作权限校验

Request Body: none

Response Body:

	[
		{
			"id" : "编号",
			"uid" : "租户编号",
			"status" : "状态",
			"crtDate" : "创建时间",
			"upDate" : "更新时间",
			"url" : "图文资讯链接",
			"publish" : "是否发布",
			"title": "标题",
			"intro": "简介",
			"created_at" : "创建时间",
			"pic" : "标题图片",
			"contents" : [{
							"id" : "内容编号",
							"detail" : "内容详细",
							"video" : "内容视频",
							"photo" : "内容图片",
							"videoTitle" : "视频标题"，
							"photoTitle" : "图片标题"
							"newsId" : "图文资讯编号"
						},{...
						}...]
		},
		{
		
		}
		...
	]

## 图文资讯详细信息

Request URI: /api/v1/news/detail/?id=[id]

Request Method: GET

Request Paramater:

    id : 新闻编号

Request Body: none

Response Body:

	{
		"id" : "编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upDate" : "更新时间",
		"url" : "图文资讯链接",
		"publish" : "是否发布",
		"title": "标题",
		"intro": "简介",
		"created_at" : "创建时间",
		"pic" : "标题图片",
		"contents" : [{
						"id" : "内容编号",
						"detail" : "内容详细",
						"video" : "内容视频",
						"photo" : "内容图片",
						"videoTitle" : "视频标题"，
						"photoTitle" : "图片标题"
						"newsId" : "图文资讯编号"
					},{...
					}...]
	}

# 通讯录

##通讯录列表

Request URI: /api/v1/addressList/list/?dept_id=[dept_id]&page=[page]&uid=[uid]

Request Method: GET

Request Paramater:

    dept_id : 分组编号
	page :　页数
	uid : 租户编号

Request Body: none

Response Body:

	[
		{
			"id" : "通讯录编号",
			"uid" : "租户编号",
			"status" : "状态",
			"crtDate" : "创建时间",
			"upDate" : "更新时间",
			"name" : "姓名",
			"deptId" : "分组id"
			"position" : "职位",
			"officePhone" : "办公电话"
			"tel" : "私人电话",
			"content" : "备注"
		},
		{...}...
	]

##通讯录详细

Request URI: /api/v1/addressList/detail/?id=[id]

Request Method: GET

Request Paramater:

    id : 通讯录编号

Request Body: none

Response Body:

	{
		"id" : "通讯录编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upDate" : "更新时间",
		"name" : "姓名",
		"deptId" : "分组id"
		"position" : "职位",
		"officePhone" : "办公电话"
		"tel" : "私人电话",
		"content" : "备注"
	}

# 会员企业名录

##会员企业列表

Request URI: /api/v1/company/list?category=[category]&page=[page]

Request Method: GET

Request Paramater:

	category：分类编号
	page：页数

Request Body: none

Response Body:

	[
		{
			"id" : "企业编号",
			"uid" : "租户编号",
			"status" : "状态",
			"crtDate" : "创建时间",
			"upDate" : "更新时间",
			"title": "企业名称",
			"intro": "企业简介",
			"detail" : "企业详情",
			"category" : "企业类型",
			"pic" : "企业图片",
			"pictures" : [{
							"pid" : "图片编号",
							"uid" : "企业编号",
							"url" : "图片链接"
						},{...
						}...]
		}
	]

##会员企业详细信息

Request URI: /api/v1/company/detail?id=[id]

Request Method: GET

Request Paramater:

	id：企业编号

Request Body: none

Response Body:

	{
		"id" : "企业编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upDate" : "更新时间",
		"title": "企业名称",
		"intro": "企业简介",
		"detail" : "企业详情",
		"category" : "企业类型",
		"pic" : "企业图片",
		"pictures" : [{
						"pid" : "图片编号",
						"uid" : "企业编号",
						"url" : "图片链接"
					},{...
					}...]
	}



# 论坛

##帖子列表

Request URI: /api/v1/invitation/list/?page=[page]&uid=[uid]

Request Method: GET

Request Paramater:

	page：页数
	uid：租户编号

Request Body: none

Response Body:

	[
		{
			"id" : "企业编号",
			"uid" : "租户编号",
			"status" : "状态",
			"crtDate" : "创建时间",
			"upTime" : "更新时间",
			"title": "",
			"introduce": "",
			"ischeck" : "",
			"crUser" : "",
			"replyNum" : "",
			"getReplys" : ""
		}
	]

##帖子详细

Request URI: /api/v1/invitation/detail/id=[id]

Request Method: GET

Request Paramater:

	id：帖子编号

Request Body: none

Response Body:

	{
		"id" : "企业编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upTime" : "更新时间",
		"title": "",
		"introduce": "",
		"ischeck" : "",
		"crUser" : "",
		"replyNum" : "",
		"getReplys" : ""
	}

##发帖

Request URI: /api/v1/invitation/create

Request Method: POST

Request Paramater: none

Request Body:
	
	{
		
		"id" : "企业编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upTime" : "更新时间",
		"title": "",
		"introduce": "",
		"ischeck" : "",
		"crUser" : ""	

	}

Response Body:
	{
		"status" : "创建帖子状态"
	}

##回复

Request URI: /api/v1/invitation/reply?id=[id]

Request Method: POST

Request Paramater: 
	
	id : 帖子编号

Request Body:
	
	{
		"id" : "企业编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upTime" : "更新时间",
		"introduce" : "回复内容"
		"crUser" : "回复人"
	}

Response Body:
	
	{
		"status" : "回复状态"
	}

# 商城

##商品列表

Request URI: /api/v1/mall/productList/?category=[]&page=[page]&uid=[uid]

Request Method: GET

Request Paramater:
	
	category : 分类
	page：页数
	uid：租户编号

Request Body: none

Response Body:

	[
		{
			"id" : "企业编号",
			"uid" : "租户编号",
			"status" : "状态",
			"crtDate" : "创建时间",
			"upDate" : "更新时间",
			"title": "产品名称",
			"intro": "产品简介",
			"detail" : "产品详细",
			"price" : "价格",
			"categoryId" : "分类编号"
		}
	]

##商品详细

Request URI: /api/v1/mall/productDetail/id=[id]

Request Method: GET

Request Paramater:

	id：商品编号

Request Body: none

Response Body:

	{
		"id" : "企业编号",
		"uid" : "租户编号",
		"status" : "状态",
		"crtDate" : "创建时间",
		"upDate" : "更新时间",
		"title": "产品名称",
		"intro": "产品简介",
		"detail" : "产品详细",
		"price" : "价格",
		"categoryId" : "分类编号"

	}

##下订单

Request URI: /api/v1/invitation/order?id=[id]

Request Method: POST

Request Paramater: 
	
	id : 产品编号

Request Body: none

Response Body:
	
	{
		"status" : "订单状态"
	}

##付款

Request URI: /api/v1/mall/pay?id=[id]

Request Method: POST

Request Paramater: 
	
	id : 订单编号

Request Body:none

Response Body:

	{
		"status" : "付款状态"
	}

##订单列表

Request URI: /api/v1/mall/orderList?uid=[uid]

Request Method: POST

Request Paramater: 
	
	uid : 租户编号

Request Body:none

Response Body:
	
	{

	}
	

##订单详细

Request URI: /api/v1/mall/orderDetail?id=[id]

Request Method: POST

Request Paramater: 
	
	id : 订单编号

Request Body:none

Response Body:
	
	{

	}


##取消订单

Request URI: /api/v1/mall/cancelOrder?id=[id]

Request Method: POST

Request Paramater: 
	
	id : 订单编号

Request Body:none

Response Body:

	{ "status" : ""}
