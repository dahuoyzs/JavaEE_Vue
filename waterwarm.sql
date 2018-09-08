/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : waterwarm

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-31 09:12:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goodsid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goodsclassname` varchar(255) DEFAULT NULL,
  `goodsname` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`goodsid`)
) ENGINE=InnoDB AUTO_INCREMENT=10046 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('10001', '水龙头', '青铜水龙头', '15.00', '个', '小米');
INSERT INTO `goods` VALUES ('10003', '水龙头', '黄金水龙头', '25.00', '个', '小米');
INSERT INTO `goods` VALUES ('10004', '水龙头', '钻石水龙头', '30.00', '个', '小米');
INSERT INTO `goods` VALUES ('10005', '管制品', '塑料管', '10.00', '米', '华为');
INSERT INTO `goods` VALUES ('10006', '管制品', '青铜管', '15.00', '米', '华为');
INSERT INTO `goods` VALUES ('10007', '管制品', '白银管', '20.00', '米', '华为');
INSERT INTO `goods` VALUES ('10008', '管制品', '黄金管', '25.00', '米', '华为');
INSERT INTO `goods` VALUES ('10009', '管制品', '钻石管', '30.00', '米', '华为');
INSERT INTO `goods` VALUES ('10011', '配件', '塑料水箱', '40.00', '个', '魅族');
INSERT INTO `goods` VALUES ('10012', '配件', '青铜水箱', '45.00', '个', '魅族');
INSERT INTO `goods` VALUES ('10013', '配件', '白银水箱', '50.00', '个', '魅族');
INSERT INTO `goods` VALUES ('10014', '配件', '黄金水箱', '55.00', '个', '魅族');
INSERT INTO `goods` VALUES ('10016', '11', '11', '11.00', '11', '11');
INSERT INTO `goods` VALUES ('10019', '111', '11', '1.00', '1', null);
INSERT INTO `goods` VALUES ('10020', '11', '11', '11.00', '11', '11');
INSERT INTO `goods` VALUES ('10021', '1', '11', '1.00', '1', '1');
INSERT INTO `goods` VALUES ('10023', '111', '111', '11.00', null, null);
INSERT INTO `goods` VALUES ('10024', '水龙头', '新水龙头', '15.00', '个', '华为');
INSERT INTO `goods` VALUES ('10025', '水龙头', '新添加水龙头', '30.00', '个', '小米');
INSERT INTO `goods` VALUES ('10026', '水龙头', 'Hiber水龙头1', '15.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10027', '水龙头', 'Hiber水龙头0', '15.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10028', '水龙头', 'Hiber水龙头1', '16.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10029', '水龙头', 'Hiber水龙头2', '17.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10030', '水龙头', 'Hiber水龙头3', '18.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10031', '水龙头', 'Hiber水龙头4', '19.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10032', '水龙头', 'Hiber水龙头5', '20.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10033', '水龙头', 'Hiber水龙头6', '21.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10034', '水龙头', 'Hiber水龙头7', '22.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10035', '水龙头', 'Hiber水龙头8', '23.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10036', '水龙头', 'Hiber水龙头9', '24.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10037', '水龙头', 'Hiber水龙头0', '15.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10038', '水龙头', 'Hiber水龙头1', '16.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10039', '水龙头', 'Hiber水龙头2', '17.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10040', '水龙头', 'Hiber水龙头3', '18.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10041', '水龙头', 'Hiber水龙头4', '19.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10042', '水龙头', 'Hiber水龙头5', '20.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10043', '水龙头', 'Hiber水龙头6', '21.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10044', '水龙头', 'Hiber水龙头7', '22.00', '个', '华硕');
INSERT INTO `goods` VALUES ('10045', '水龙头', 'Hiber水龙头8', '23.00', '个', '华硕');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `orderid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ordercode` varchar(255) DEFAULT NULL,
  `confirmdate` datetime DEFAULT NULL,
  `countprice` decimal(10,2) DEFAULT NULL,
  `salename` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  UNIQUE KEY `ordercode` (`ordercode`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('14', '201808051002', '2018-08-05 20:34:35', '45.00', 'admin');
INSERT INTO `order` VALUES ('15', '201808051003', '2018-08-05 20:35:02', '45.00', 'admin');
INSERT INTO `order` VALUES ('20', '201808051004', '2018-08-05 20:58:01', '30.00', 'admin');
INSERT INTO `order` VALUES ('21', '201808051005', '2018-08-05 21:08:06', '70.00', 'admin');
INSERT INTO `order` VALUES ('22', '201808061001', '2018-08-06 16:09:02', '60.00', 'admin');
INSERT INTO `order` VALUES ('23', '201808061002', '2018-08-06 16:09:05', '140.00', 'admin');
INSERT INTO `order` VALUES ('24', '201808061003', '2018-08-06 16:11:17', '95.00', 'admin');
INSERT INTO `order` VALUES ('25', '201808061004', '2018-08-06 17:48:19', '255.00', 'admin');
INSERT INTO `order` VALUES ('26', '201808061005', '2018-08-06 17:48:39', '472.00', 'admin');
INSERT INTO `order` VALUES ('27', '201808071001', '2018-08-07 16:38:32', '200.00', 'admin');
INSERT INTO `order` VALUES ('28', '201808071002', '2018-08-07 16:38:38', '125.00', 'admin');
INSERT INTO `order` VALUES ('29', '201808091001', '2018-08-09 09:51:20', '55.00', 'admin');
INSERT INTO `order` VALUES ('30', '201808141001', '2018-08-14 10:43:00', '170.00', 'admin');
INSERT INTO `order` VALUES ('31', '201808141002', '2018-08-14 11:11:49', '135.00', 'admin');

-- ----------------------------
-- Table structure for orderdetails
-- ----------------------------
DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE `orderdetails` (
  `orderdetailsid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ordercode` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderdetailsid`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderdetails
-- ----------------------------
INSERT INTO `orderdetails` VALUES ('1', '201807190001', '10003', '3');
INSERT INTO `orderdetails` VALUES ('8', '201808051002', '10001', '2');
INSERT INTO `orderdetails` VALUES ('9', '201808051002', '10002', '1');
INSERT INTO `orderdetails` VALUES ('10', '201808051003', '10001', '2');
INSERT INTO `orderdetails` VALUES ('11', '201808051003', '10002', '1');
INSERT INTO `orderdetails` VALUES ('18', '201808051004', '10001', '2');
INSERT INTO `orderdetails` VALUES ('19', '201808051005', '10002', '3');
INSERT INTO `orderdetails` VALUES ('20', '201808051005', '10003', '1');
INSERT INTO `orderdetails` VALUES ('21', '201808061001', '10001', '3');
INSERT INTO `orderdetails` VALUES ('22', '201808061001', '10002', '1');
INSERT INTO `orderdetails` VALUES ('23', '201808061002', '10001', '3');
INSERT INTO `orderdetails` VALUES ('24', '201808061002', '10002', '1');
INSERT INTO `orderdetails` VALUES ('25', '201808061002', '10003', '2');
INSERT INTO `orderdetails` VALUES ('26', '201808061002', '10004', '1');
INSERT INTO `orderdetails` VALUES ('27', '201808061003', '10002', '2');
INSERT INTO `orderdetails` VALUES ('28', '201808061003', '10003', '1');
INSERT INTO `orderdetails` VALUES ('29', '201808061003', '10004', '1');
INSERT INTO `orderdetails` VALUES ('30', '201808061003', '10005', '1');
INSERT INTO `orderdetails` VALUES ('31', '201808061004', '10003', '4');
INSERT INTO `orderdetails` VALUES ('32', '201808061004', '10004', '3');
INSERT INTO `orderdetails` VALUES ('33', '201808061004', '10007', '2');
INSERT INTO `orderdetails` VALUES ('34', '201808061004', '10008', '1');
INSERT INTO `orderdetails` VALUES ('35', '201808061005', '10011', '2');
INSERT INTO `orderdetails` VALUES ('36', '201808061005', '10009', '1');
INSERT INTO `orderdetails` VALUES ('37', '201808061005', '10001', '3');
INSERT INTO `orderdetails` VALUES ('38', '201808061005', '10002', '2');
INSERT INTO `orderdetails` VALUES ('39', '201808061005', '10023', '1');
INSERT INTO `orderdetails` VALUES ('40', '201808061005', '10019', '1');
INSERT INTO `orderdetails` VALUES ('41', '201808061005', '10007', '2');
INSERT INTO `orderdetails` VALUES ('42', '201808061005', '10006', '1');
INSERT INTO `orderdetails` VALUES ('43', '201808061005', '10003', '2');
INSERT INTO `orderdetails` VALUES ('44', '201808061005', '10004', '2');
INSERT INTO `orderdetails` VALUES ('45', '201808061005', '10024', '1');
INSERT INTO `orderdetails` VALUES ('46', '201808061005', '10013', '1');
INSERT INTO `orderdetails` VALUES ('47', '201808061005', '10012', '1');
INSERT INTO `orderdetails` VALUES ('48', '201808071001', '10006', '3');
INSERT INTO `orderdetails` VALUES ('49', '201808071001', '10005', '3');
INSERT INTO `orderdetails` VALUES ('50', '201808071001', '10004', '2');
INSERT INTO `orderdetails` VALUES ('51', '201808071001', '10003', '1');
INSERT INTO `orderdetails` VALUES ('52', '201808071001', '10007', '2');
INSERT INTO `orderdetails` VALUES ('53', '201808071002', '10001', '3');
INSERT INTO `orderdetails` VALUES ('54', '201808071002', '10002', '2');
INSERT INTO `orderdetails` VALUES ('55', '201808071002', '10003', '2');
INSERT INTO `orderdetails` VALUES ('56', '201808091001', '10001', '1');
INSERT INTO `orderdetails` VALUES ('57', '201808091001', '10002', '1');
INSERT INTO `orderdetails` VALUES ('58', '201808091001', '10003', '1');
INSERT INTO `orderdetails` VALUES ('59', '201808141001', '10001', '3');
INSERT INTO `orderdetails` VALUES ('60', '201808141001', '10004', '3');
INSERT INTO `orderdetails` VALUES ('61', '201808141001', '10003', '1');
INSERT INTO `orderdetails` VALUES ('62', '201808141001', '10005', '1');
INSERT INTO `orderdetails` VALUES ('63', '201808141002', '10001', '2');
INSERT INTO `orderdetails` VALUES ('64', '201808141002', '10003', '3');
INSERT INTO `orderdetails` VALUES ('65', '201808141002', '10004', '1');

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
  `saleid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `salename` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`saleid`),
  UNIQUE KEY `salename` (`salename`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sale
-- ----------------------------
INSERT INTO `sale` VALUES ('11', '张三', '123456');
INSERT INTO `sale` VALUES ('12', '李四', '123');
INSERT INTO `sale` VALUES ('13', 'admin', 'admin');

-- ----------------------------
-- View structure for odview
-- ----------------------------
DROP VIEW IF EXISTS `odview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `odview` AS select `orderdetails`.`number` AS `number`,`order`.`confirmdate` AS `confirmdate`,`order`.`countprice` AS `countprice`,`order`.`salename` AS `salename`,`orderdetails`.`ordercode` AS `ordercode`,`goods`.`goodsname` AS `goodsname`,`goods`.`price` AS `price`,`goods`.`goodsid` AS `goodsid` from ((`order` join `orderdetails` on((`order`.`ordercode` = `orderdetails`.`ordercode`))) join `goods` on((`orderdetails`.`goodsid` = `goods`.`goodsid`))) ;
