/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.50_3306
Source Server Version : 50151
Source Host           : 192.168.1.50:3306
Source Database       : appportal

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2014-07-10 18:04:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_industry_category
-- ----------------------------
DROP TABLE IF EXISTS `app_industry_category`;
CREATE TABLE `app_industry_category` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '业务无关主键',
  `name` varchar(25) NOT NULL COMMENT '分类的名字',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '分类的父类，负一表示顶级节点',
  `path_code` varchar(255) NOT NULL COMMENT '路径码，表示节点在整个分类树中的路径如 1/2/3',
  `usefor` varchar(50) DEFAULT NULL COMMENT '用途，比如板块一，板块二等',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_category_pathCode` (`path_code`),
  KEY `IDX_INDUSTRYCATEGORY_PID` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=147 DEFAULT CHARSET=utf8 COMMENT='分类表，用途多样用useFor标识';

-- ----------------------------
-- Records of app_industry_category
-- ----------------------------
INSERT INTO `app_industry_category` VALUES ('1', '软件与计算机服务', '0', '1', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('2', '硬件技术与设备', '0', '2', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('3', '商务服务', '0', '3', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('4', '汽车与汽车零部件', '0', '4', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('5', '食品、饮料', '0', '5', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('6', '个人和家庭用品', '0', '6', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('7', '消费服务', '0', '7', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('8', '媒体', '0', '8', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('9', '旅游与休闲', '0', '9', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('10', '电信', '0', '10', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('11', '公共事业', '0', '11', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('12', '金融、保险与地产', '0', '12', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('13', '卫生保健', '0', '13', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('14', '工业产品与建材', '0', '14', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('15', '工业运输与服务', '0', '15', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('16', '石油与天然气工业', '0', '16', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('17', '原材料', '0', '17', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('18', '政府/非赢利机构', '0', '18', null, null, '1');
INSERT INTO `app_industry_category` VALUES ('19', '计算机服务', '1', '1/19', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('20', '互联网服务', '1', '1/20', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('21', '互联网资讯', '1', '1/21', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('22', '软件', '1', '1/22', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('23', '网络游戏', '1', '1/23', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('24', '其他', '1', '1/24', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('25', '计算机硬件', '2', '2/25', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('26', '办公电子设备', '2', '2/26', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('27', '半导体', '2', '2/27', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('28', '电信设备', '2', '2/28', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('29', '其他', '2', '2/29', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('30', '商业服务', '3', '3/30', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('31', '商务培训、职业介绍机构', '3', '3/31', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('32', '财务管理', '3', '3/32', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('33', '其他', '3', '3/33', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('34', '汽车', '4', '4/34', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('35', '汽车零配件', '4', '4/35', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('36', '汽车轮胎', '4', '4/36', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('37', '其他', '4', '4/37', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('38', '食品', '5', '5/38', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('39', '种植和捕鱼', '5', '5/39', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('40', '啤酒酿造商', '5', '5/40', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('41', '烈酒和葡萄酒酿造商', '5', '5/41', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('42', '软饮料', '5', '5/42', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('43', '其他', '5', '5/43', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('44', '耐用家居产品', '6', '6/44', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('45', '非耐用家居产品', '6', '6/45', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('46', '家具', '6', '6/46', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('47', '室内设计/装潢', '6', '6/47', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('48', '家用电子产品', '6', '6/48', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('49', '休闲产品', '6', '6/49', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('50', '玩具', '6', '6/50', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('51', '服装和配件', '6', '6/51', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('52', '鞋类', '6', '6/52', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('53', '化妆/护肤品', '6', '6/53', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('54', '其他个人用品', '6', '6/54', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('55', '烟草制品', '6', '6/55', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('56', '其他', '6', '6/56', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('57', '药品、保健品批发零售', '7', '7/57', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('58', '食品批发零售', '7', '7/58', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('59', '服装批发零售', '7', '7/59', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('60', '化妆/护肤品批发零售', '7', '7/60', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('61', '成人用品批发零售', '7', '7/61', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('62', '点卡等虚拟物品', '7', '7/62', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('63', '其他批发零售', '7', '7/63', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('64', '家装批发零售', '7', '7/64', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('65', '专卖店', '7', '7/65', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('66', '教育培训', '7', '7/66', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('67', '影楼工作室', '7', '7/67', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('68', '特殊消费服务', '7', '7/68', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('69', '其他', '7', '7/69', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('70', '广播和娱乐', '8', '8/70', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('71', '媒体机构', '8', '8/71', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('72', '出版业', '8', '8/72', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('73', '其他', '8', '8/73', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('74', '航空业', '9', '9/74', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('75', '酒店业', '9', '9/75', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('76', '娱乐服务', '9', '9/76', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('77', '餐馆和酒吧', '9', '9/77', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('78', '旅游', '9', '9/78', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('79', '其他', '9', '9/79', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('80', '固话通讯', '10', '10/80', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('81', '移动通讯', '10', '10/81', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('82', '其他', '10', '10/82', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('83', '电力', '11', '11/83', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('84', '燃气', '11', '11/84', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('85', '自来水', '11', '11/85', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('86', '综合公用事业', '11', '11/86', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('87', '其他', '11', '11/87', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('88', '银行', '12', '12/88', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('89', '保险', '12', '12/89', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('90', '房地产', '12', '12/90', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('91', '资产管理', '12', '12/91', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('92', '消费信贷', '12', '12/92', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('93', '投资服务', '12', '12/93', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('94', '抵押融资', '12', '12/94', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('95', '产权投资公司', '12', '12/95', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('96', '非产权投资公司', '12', '12/96', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('97', '其他', '12', '12/97', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('98', '医疗保健提供商', '13', '13/98', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('99', '医疗设备', '13', '13/99', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('100', '医疗用品', '13', '13/100', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('101', '整形美容', '13', '13/101', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('102', '成人用品', '13', '13/102', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('103', '生物科技', '13', '13/103', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('104', '制药', '13', '13/104', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('105', '保健品', '13', '13/105', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('106', '其他', '13', '13/106', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('107', '建筑材料和固定设备', '14', '14/107', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('108', '重大建筑工程', '14', '14/108', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('109', '航空航天与国防', '14', '14/109', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('110', '工业容器与包装', '14', '14/110', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('111', '电气部件与设备', '14', '14/111', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('112', '电子设备', '14', '14/112', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('113', '商业运输工具、卡车', '14', '14/113', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('114', '工业机械', '14', '14/114', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('115', '其他', '14', '14/115', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('116', '快递', '15', '15/116', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('117', '海运', '15', '15/117', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('118', '铁路运输', '15', '15/118', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('119', '运输服务', '15', '15/119', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('120', '公路运输', '15', '15/120', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('121', '废品处理', '15', '15/121', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('122', '供应厂商', '15', '15/122', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('123', '其他', '15', '15/123', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('124', '石油、天然气制造商', '16', '16/124', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('125', '能源设备与服务', '16', '16/125', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('126', '石油与天然气的储存和运输', '16', '16/126', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('127', '商品化工', '17', '17/127', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('128', '特种化学制品', '17', '17/128', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('129', '林业产品', '17', '17/129', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('130', '纸制品', '17', '17/130', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('131', '工业金属', '17', '17/131', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('132', '采矿', '17', '17/132', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('133', '其他', '17', '17/133', null, null, '2');
INSERT INTO `app_industry_category` VALUES ('134', '政府/非赢利机构', '18', '18/134', null, null, '2');
