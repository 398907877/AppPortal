 /* 角色 */
 insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ROLE','SYSADMIN', '平台管理员', 1);
 insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ROLE','ADMIN', '企业管理员', 2);
 insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ROLE','OPERATOR', '操作员', 3);
 
 insert into app_user (login_name, name, password, salt, roles, register_date, status, uid) values('admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','SYSADMIN','2012-06-04 01:00:00', '1', '20140417122551');
 INSERT INTO `app_tenancy` (`address`, `app_id`, `app_secret`, `crt_date`, `logo`, `name`, `status`, `tel`, `uid`, `upt_date`) VALUES ('福州市鼓楼区梅峰支路118号恒升大厦5楼', 'c7a019545998a9fe3d743a110dab9a7d', '62c6c2c4f385d78054c7944a4b72fea5', '2014-04-17 12:25:51', NULL, '福州华科信息科技有限公司', '1', '83928188', 20140417122551, '2014-04-17 12:25:51');
 
 
 /*微信消息 数据字典  -----请求报文类型  -jiajun*/
INSERT INTO `hk_dictionaries` (`class_name`,`class_key`,`seq`,`dict_value`) VALUES ('WEIXINREQ_CODE','text',1,'文本请求');
INSERT INTO `hk_dictionaries` (`class_name`,`class_key`,`seq`,`dict_value`) VALUES ('WEIXINREQ_CODE','event',2,'事件请求');
/*微信消息 数据字典  -----请求渠道类型 -jiajun*/
INSERT INTO `hk_dictionaries` (`class_name`,`class_key`,`seq`,`dict_value`) VALUES ('CHANNEL_CODE','weixin',1,'微信');
 
 
 /*业务模块BizCode*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'news', '图文资讯', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'product_dir', '产品展示', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'payment', '支付服务', 3);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'book', '产品预订', 4);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'vip', '电子会员卡', 5);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'coupon', '电子优惠券', 6);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'activity', '活动', 7);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'supply', '供求服务', 8);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'checkin', '签到服务', 9);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'question', '调查问卷', 10);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'point', '积分系统', 11);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'location', '位置服务', 12);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'feedback', '意见反馈', 13);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'address_list', '通讯录', 14);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'tqhplace', '台球会附近球房', 15);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BIZ_CODE', 'forum', '微论坛', 16);
INSERT INTO hk_dictionaries (`class_name`,`class_key`,`seq`,`dict_value`) VALUES ('BIZ_CODE','tcustomer',17,'会员企业');
/*营销互动种类*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ERNIE_CATE', '1', '抽奖', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ERNIE_CATE', '2', '砸蛋', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('ERNIE_CATE', '3', '博饼', 3);
/*网站模板 */
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('TEMPLATE', 'default', 'default', 1);

/*博饼结果种类  暂定这么多种*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'zhuangyuan', '状元', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'duitang', '对堂', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'sanhong', '三红', 3);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'sijin', '四进', 4);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'erju', '二举', 5);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('BOBING', 'yixiu', '一秀', 6);
/*微信回复类型*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('WP_REPLY_CATE', 'text', '文本', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('WP_REPLY_CATE', 'url', '链接', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('WP_REPLY_CATE', 'biz', '业务', 2);

insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('SupplyDemandType', '1', '供应', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('SupplyDemandType', '2', '需求', 2);
  
/*业务模块供求记录状态*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('SD_STATUS', '0', '无效', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('SD_STATUS', '1', '有效', 2);
/* 优惠券种类 */
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('COUPON_TYPE', 'discount', '折扣券', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('COUPON_TYPE', 'cash', '现金券', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('COUPON_TYPE', 'free', '免费体验券', 3);

insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('CHANNEL_NAME', 'mobile', '手机网页', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('CHANNEL_NAME', 'ios', '苹果客户端', 2);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('CHANNEL_NAME', 'android', '安卓客户端', 3);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('CHANNEL_NAME', 'weixin', '微信', 4);

/* 对外微信内容接口 */  
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('activityWeixinTarget','activity','活动');
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('newsWeixinTarget','news','图文资讯');
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('tcustomerWeixinTarget','tcustomer','会员企业');
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('supplyDemandWeixinTarget','supply','供求信息');
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('productWeixinTarget','product','产品展示');
INSERT INTO `app_biz_mapping_cfg` (`bean_name`,`biz_code`,`description`) VALUES ('invitationWeixinTarget','invitation','微论坛');


/*数据记录有效无效状态*/
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('STATUS', '0', '无效', 1);
insert into hk_dictionaries (class_name, class_key, dict_value, seq) values('STATUS', '1', '有效', 2);
/*商城 - 订单状态*/
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '0', '买家未付款',1);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '1', '买家已付款',2);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '2', '卖家已收款',3);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '3', '卖家已发货',4);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '4', '买家已确认收货',5);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '5', '订单已完成',6);
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('ORDER_STATE', '6', '订单已过期',7);
/*Mobile页面登记*/
INSERT INTO hk_dictionaries (class_name, class_key, dict_value,seq) VALUES ('MOBILE_URL', '/m/events/', '活动页面',7);

/** 手机网页资源模块 */

INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('news','none','/m/news','none','新闻资讯首页','none');
INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('supply','none','/m/supply','none','供求信息','none');
INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('invitation','none','/m/invitation','none','论坛信息','none');
INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('book','none','/m/addressList','none','通讯录信息','none');
INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('customer','none','/m/supply','none','会员企业信息','none');
INSERT INTO `app_mobile_resource` (`biz_code`,`role`,`target`,`template`,`title`,`uid`) VALUES ('activity','none','/m/event','none','活动信息','none');


  
  
  