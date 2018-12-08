/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50525
Source Host           : 127.0.0.1:3306
Source Database       : p2p

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-12-08 15:28:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `tradePassword` varchar(255) DEFAULT NULL,
  `usableAmount` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  `borrowLimitAmount` decimal(18,4) NOT NULL,
  `version` int(11) NOT NULL,
  `unReceiveInterest` decimal(18,4) NOT NULL,
  `unReceivePrincipal` decimal(18,4) NOT NULL,
  `unReturnAmount` decimal(18,4) NOT NULL,
  `remainBorrowLimit` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', null, '9682.5266', '0.0000', '5000.0000', '9', '0.0000', '0.0000', '0.0000', '5000.0000');
INSERT INTO `account` VALUES ('2', null, '10050.2200', '0.0000', '5000.0000', '9', '0.0000', '-0.0001', '0.0000', '5000.0000');
INSERT INTO `account` VALUES ('3', null, '10044.9336', '0.0000', '5000.0000', '10', '0.0000', '-0.0001', '0.0000', '5000.0000');
INSERT INTO `account` VALUES ('5', null, '10010.5724', '0.0000', '5000.0000', '9', '0.0000', '0.0002', '0.0000', '5000.0000');
INSERT INTO `account` VALUES ('6', null, '0.0000', '0.0000', '5000.0000', '0', '0.0000', '0.0000', '0.0000', '5000.0000');

-- ----------------------------
-- Table structure for accountflow
-- ----------------------------
DROP TABLE IF EXISTS `accountflow`;
CREATE TABLE `accountflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actionType` tinyint(4) NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) NOT NULL,
  `usableAmount` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  `actionTime` datetime NOT NULL,
  `accountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accountflow
-- ----------------------------
INSERT INTO `accountflow` VALUES ('1', '0', '10000.0000', '线下充值:10000.00元', '10000.0000', '0.0000', '2018-01-13 20:14:33', '2');
INSERT INTO `accountflow` VALUES ('2', '0', '10000.0000', '线下充值:10000.00元', '10000.0000', '0.0000', '2018-01-13 20:15:03', '3');
INSERT INTO `accountflow` VALUES ('3', '0', '700.0000', '本次投资了700元', '10000.0000', '0.0000', '2018-01-13 20:15:46', '3');
INSERT INTO `accountflow` VALUES ('4', '0', '1000.0000', '本次投资了1000元', '9300.0000', '700.0000', '2018-01-13 20:15:57', '3');
INSERT INTO `accountflow` VALUES ('5', '0', '1900.0000', '本次投资了1900元', '10000.0000', '0.0000', '2018-01-13 20:17:03', '2');
INSERT INTO `accountflow` VALUES ('6', '0', '10000.0000', '线下充值:10000.00元', '10000.0000', '0.0000', '2018-01-13 20:18:28', '1');
INSERT INTO `accountflow` VALUES ('7', '0', '10000.0000', '线下充值:10000.00元', '10000.0000', '0.0000', '2018-01-13 20:19:28', '5');
INSERT INTO `accountflow` VALUES ('8', '0', '400.0000', '本次投资了400元', '10000.0000', '0.0000', '2018-01-13 20:19:51', '5');
INSERT INTO `accountflow` VALUES ('9', '11', '1900.0000', '满标二审成功,冻结金额减少:1900.0000元', '8100.0000', '0.0000', '2018-01-13 20:36:10', '2');
INSERT INTO `accountflow` VALUES ('10', '11', '700.0000', '满标二审成功,冻结金额减少:700.0000元', '8300.0000', '1000.0000', '2018-01-13 20:36:10', '3');
INSERT INTO `accountflow` VALUES ('11', '11', '1000.0000', '满标二审成功,冻结金额减少:1000.0000元', '8300.0000', '0.0000', '2018-01-13 20:36:10', '3');
INSERT INTO `accountflow` VALUES ('12', '11', '400.0000', '满标二审成功,冻结金额减少:400.0000元', '9600.0000', '0.0000', '2018-01-13 20:36:10', '5');
INSERT INTO `accountflow` VALUES ('13', '11', '4000.0000', '满标二审成功,账户可用余额增加:4000.0000元', '4000.0000', '0.0000', '2018-01-13 20:36:10', '1');
INSERT INTO `accountflow` VALUES ('14', '11', '200.0000', '满标二审成功,支付平台管理费:200.0000元', '3800.0000', '0.0000', '2018-01-13 20:36:10', '1');
INSERT INTO `accountflow` VALUES ('15', '11', '686.2456', '还款:686.2456元', '3113.7544', '0.0000', '2018-01-13 20:37:30', '1');
INSERT INTO `accountflow` VALUES ('16', '11', '325.9666', '收款时,账户可用余额添加:325.9666元', '8425.9666', '0.0000', '2018-01-13 20:37:30', '2');
INSERT INTO `accountflow` VALUES ('17', '11', '1.5833', '收款时,支付利息管理费:1.5833元', '8424.3833', '0.0000', '2018-01-13 20:37:30', '2');
INSERT INTO `accountflow` VALUES ('18', '11', '120.0930', '收款时,账户可用余额添加:120.0930元', '8420.0930', '0.0000', '2018-01-13 20:37:30', '3');
INSERT INTO `accountflow` VALUES ('19', '11', '0.5833', '收款时,支付利息管理费:0.5833元', '8419.5097', '0.0000', '2018-01-13 20:37:30', '3');
INSERT INTO `accountflow` VALUES ('20', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '8591.0711', '0.0000', '2018-01-13 20:37:30', '3');
INSERT INTO `accountflow` VALUES ('21', '11', '0.8333', '收款时,支付利息管理费:0.8333元', '8590.2378', '0.0000', '2018-01-13 20:37:30', '3');
INSERT INTO `accountflow` VALUES ('22', '11', '68.6246', '收款时,账户可用余额添加:68.6246元', '9668.6246', '0.0000', '2018-01-13 20:37:30', '5');
INSERT INTO `accountflow` VALUES ('23', '11', '0.3333', '收款时,支付利息管理费:0.3333元', '9668.2913', '0.0000', '2018-01-13 20:37:30', '5');
INSERT INTO `accountflow` VALUES ('24', '11', '686.2456', '还款:686.2456元', '2427.5088', '0.0000', '2018-01-13 20:37:33', '1');
INSERT INTO `accountflow` VALUES ('25', '11', '325.9667', '收款时,账户可用余额添加:325.9667元', '8750.3500', '0.0000', '2018-01-13 20:37:33', '2');
INSERT INTO `accountflow` VALUES ('26', '11', '1.3249', '收款时,支付利息管理费:1.3249元', '8749.0251', '0.0000', '2018-01-13 20:37:33', '2');
INSERT INTO `accountflow` VALUES ('27', '11', '120.0930', '收款时,账户可用余额添加:120.0930元', '8710.3308', '0.0000', '2018-01-13 20:37:33', '3');
INSERT INTO `accountflow` VALUES ('28', '11', '0.4881', '收款时,支付利息管理费:0.4881元', '8709.8427', '0.0000', '2018-01-13 20:37:33', '3');
INSERT INTO `accountflow` VALUES ('29', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '8881.4041', '0.0000', '2018-01-13 20:37:33', '3');
INSERT INTO `accountflow` VALUES ('30', '11', '0.6973', '收款时,支付利息管理费:0.6973元', '8880.7068', '0.0000', '2018-01-13 20:37:34', '3');
INSERT INTO `accountflow` VALUES ('31', '11', '68.6245', '收款时,账户可用余额添加:68.6245元', '9736.9158', '0.0000', '2018-01-13 20:37:34', '5');
INSERT INTO `accountflow` VALUES ('32', '11', '0.2789', '收款时,支付利息管理费:0.2789元', '9736.6369', '0.0000', '2018-01-13 20:37:34', '5');
INSERT INTO `accountflow` VALUES ('33', '11', '686.2456', '还款:686.2456元', '1741.2632', '0.0000', '2018-01-13 20:37:36', '1');
INSERT INTO `accountflow` VALUES ('34', '11', '325.9667', '收款时,账户可用余额添加:325.9667元', '9074.9918', '0.0000', '2018-01-13 20:37:36', '2');
INSERT INTO `accountflow` VALUES ('35', '11', '1.0643', '收款时,支付利息管理费:1.0643元', '9073.9275', '0.0000', '2018-01-13 20:37:36', '2');
INSERT INTO `accountflow` VALUES ('36', '11', '120.0930', '收款时,账户可用余额添加:120.0930元', '9000.7998', '0.0000', '2018-01-13 20:37:37', '3');
INSERT INTO `accountflow` VALUES ('37', '11', '0.3921', '收款时,支付利息管理费:0.3921元', '9000.4077', '0.0000', '2018-01-13 20:37:37', '3');
INSERT INTO `accountflow` VALUES ('38', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '9171.9691', '0.0000', '2018-01-13 20:37:37', '3');
INSERT INTO `accountflow` VALUES ('39', '11', '0.5602', '收款时,支付利息管理费:0.5602元', '9171.4089', '0.0000', '2018-01-13 20:37:37', '3');
INSERT INTO `accountflow` VALUES ('40', '11', '68.6245', '收款时,账户可用余额添加:68.6245元', '9805.2614', '0.0000', '2018-01-13 20:37:37', '5');
INSERT INTO `accountflow` VALUES ('41', '11', '0.2241', '收款时,支付利息管理费:0.2241元', '9805.0373', '0.0000', '2018-01-13 20:37:37', '5');
INSERT INTO `accountflow` VALUES ('42', '11', '686.2456', '还款:686.2456元', '1055.0176', '0.0000', '2018-01-13 20:37:40', '1');
INSERT INTO `accountflow` VALUES ('43', '11', '325.9667', '收款时,账户可用余额添加:325.9667元', '9399.8942', '0.0000', '2018-01-13 20:37:40', '2');
INSERT INTO `accountflow` VALUES ('44', '11', '0.8015', '收款时,支付利息管理费:0.8015元', '9399.0927', '0.0000', '2018-01-13 20:37:40', '2');
INSERT INTO `accountflow` VALUES ('45', '11', '120.0930', '收款时,账户可用余额添加:120.0930元', '9291.5019', '0.0000', '2018-01-13 20:37:40', '3');
INSERT INTO `accountflow` VALUES ('46', '11', '0.2953', '收款时,支付利息管理费:0.2953元', '9291.2066', '0.0000', '2018-01-13 20:37:40', '3');
INSERT INTO `accountflow` VALUES ('47', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '9462.7680', '0.0000', '2018-01-13 20:37:40', '3');
INSERT INTO `accountflow` VALUES ('48', '11', '0.4219', '收款时,支付利息管理费:0.4219元', '9462.3461', '0.0000', '2018-01-13 20:37:40', '3');
INSERT INTO `accountflow` VALUES ('49', '11', '68.6245', '收款时,账户可用余额添加:68.6245元', '9873.6618', '0.0000', '2018-01-13 20:37:40', '5');
INSERT INTO `accountflow` VALUES ('50', '11', '0.1687', '收款时,支付利息管理费:0.1687元', '9873.4931', '0.0000', '2018-01-13 20:37:40', '5');
INSERT INTO `accountflow` VALUES ('51', '11', '686.2456', '还款:686.2456元', '368.7720', '0.0000', '2018-01-13 20:37:44', '1');
INSERT INTO `accountflow` VALUES ('52', '11', '325.9667', '收款时,账户可用余额添加:325.9667元', '9725.0594', '0.0000', '2018-01-13 20:37:44', '2');
INSERT INTO `accountflow` VALUES ('53', '11', '0.5366', '收款时,支付利息管理费:0.5366元', '9724.5228', '0.0000', '2018-01-13 20:37:44', '2');
INSERT INTO `accountflow` VALUES ('54', '11', '120.0930', '收款时,账户可用余额添加:120.0930元', '9582.4391', '0.0000', '2018-01-13 20:37:44', '3');
INSERT INTO `accountflow` VALUES ('55', '11', '0.1977', '收款时,支付利息管理费:0.1977元', '9582.2414', '0.0000', '2018-01-13 20:37:44', '3');
INSERT INTO `accountflow` VALUES ('56', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '9753.8028', '0.0000', '2018-01-13 20:37:44', '3');
INSERT INTO `accountflow` VALUES ('57', '11', '0.2824', '收款时,支付利息管理费:0.2824元', '9753.5204', '0.0000', '2018-01-13 20:37:44', '3');
INSERT INTO `accountflow` VALUES ('58', '11', '68.6245', '收款时,账户可用余额添加:68.6245元', '9942.1176', '0.0000', '2018-01-13 20:37:44', '5');
INSERT INTO `accountflow` VALUES ('59', '11', '0.1130', '收款时,支付利息管理费:0.1130元', '9942.0046', '0.0000', '2018-01-13 20:37:44', '5');
INSERT INTO `accountflow` VALUES ('60', '0', '10000.0000', '线下充值:10000.00元', '10368.7720', '0.0000', '2018-01-13 20:38:08', '1');
INSERT INTO `accountflow` VALUES ('61', '11', '686.2454', '还款:686.2454元', '9682.5266', '0.0000', '2018-01-13 20:41:50', '1');
INSERT INTO `accountflow` VALUES ('62', '11', '325.9666', '收款时,账户可用余额添加:325.9666元', '10050.4894', '0.0000', '2018-01-13 20:41:50', '2');
INSERT INTO `accountflow` VALUES ('63', '11', '0.2694', '收款时,支付利息管理费:0.2694元', '10050.2200', '0.0000', '2018-01-13 20:41:50', '2');
INSERT INTO `accountflow` VALUES ('64', '11', '120.0929', '收款时,账户可用余额添加:120.0929元', '9873.6133', '0.0000', '2018-01-13 20:41:50', '3');
INSERT INTO `accountflow` VALUES ('65', '11', '0.0993', '收款时,支付利息管理费:0.0993元', '9873.5140', '0.0000', '2018-01-13 20:41:50', '3');
INSERT INTO `accountflow` VALUES ('66', '11', '171.5614', '收款时,账户可用余额添加:171.5614元', '10045.0754', '0.0000', '2018-01-13 20:41:50', '3');
INSERT INTO `accountflow` VALUES ('67', '11', '0.1418', '收款时,支付利息管理费:0.1418元', '10044.9336', '0.0000', '2018-01-13 20:41:50', '3');
INSERT INTO `accountflow` VALUES ('68', '11', '68.6245', '收款时,账户可用余额添加:68.6245元', '10010.6291', '0.0000', '2018-01-13 20:41:51', '5');
INSERT INTO `accountflow` VALUES ('69', '11', '0.0567', '收款时,支付利息管理费:0.0567元', '10010.5724', '0.0000', '2018-01-13 20:41:51', '5');

-- ----------------------------
-- Table structure for bid
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actualRate` decimal(8,4) NOT NULL,
  `availableAmount` decimal(18,4) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `bidUserId` bigint(20) NOT NULL,
  `bidTime` datetime NOT NULL,
  `bidRequestTitle` varchar(255) DEFAULT NULL,
  `bidRequestState` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bid
-- ----------------------------
INSERT INTO `bid` VALUES ('1', '10.0000', '700.0000', '1', '3', '2018-01-13 20:15:46', '大保健', '8');
INSERT INTO `bid` VALUES ('2', '10.0000', '1000.0000', '1', '3', '2018-01-13 20:15:57', '大保健', '8');
INSERT INTO `bid` VALUES ('3', '10.0000', '1900.0000', '1', '2', '2018-01-13 20:17:03', '大保健', '8');
INSERT INTO `bid` VALUES ('4', '10.0000', '400.0000', '1', '5', '2018-01-13 20:19:51', '大保健', '8');

-- ----------------------------
-- Table structure for bidrequest
-- ----------------------------
DROP TABLE IF EXISTS `bidrequest`;
CREATE TABLE `bidrequest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `bidRequestType` tinyint(4) NOT NULL,
  `bidRequestState` tinyint(4) NOT NULL,
  `bidRequestAmount` decimal(18,4) NOT NULL,
  `currentRate` decimal(8,4) NOT NULL,
  `monthes2Return` tinyint(4) NOT NULL,
  `bidCount` int(11) NOT NULL,
  `totalRewardAmount` decimal(18,4) NOT NULL,
  `currentSum` decimal(18,4) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `disableDate` datetime DEFAULT NULL,
  `createUserId` bigint(20) NOT NULL,
  `disableDays` tinyint(4) NOT NULL,
  `minBidAmount` decimal(18,4) NOT NULL,
  `applyTime` datetime DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bidrequest
-- ----------------------------
INSERT INTO `bidrequest` VALUES ('1', '8', '0', '8', '4000.0000', '10.0000', '6', '4', '117.4734', '4000.0000', '大保健', '大保健', '通过', '2018-01-16 20:13:44', '1', '3', '500.0000', '2018-01-13 20:13:30', '2018-01-13 20:13:44');
INSERT INTO `bidrequest` VALUES ('2', '0', '0', '0', '1000.0000', '5.0000', '3', '0', '8.3449', '0.0000', '500', '500', null, null, '1', '1', '500.0000', '2018-01-13 21:08:50', null);

-- ----------------------------
-- Table structure for bidrequestaudithistory
-- ----------------------------
DROP TABLE IF EXISTS `bidrequestaudithistory`;
CREATE TABLE `bidrequestaudithistory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditorId` bigint(20) DEFAULT NULL,
  `applierId` bigint(20) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `auditType` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bidrequestaudithistory
-- ----------------------------
INSERT INTO `bidrequestaudithistory` VALUES ('1', '1', '通过', '2018-01-13 20:13:44', '2018-01-13 20:13:30', '4', '1', '1', '1');
INSERT INTO `bidrequestaudithistory` VALUES ('2', '1', '', '2018-01-13 20:35:58', '2018-01-13 20:13:30', '4', '1', '1', '4');
INSERT INTO `bidrequestaudithistory` VALUES ('3', '1', '', '2018-01-13 20:36:10', '2018-01-13 20:13:30', '4', '1', '1', '4');
INSERT INTO `bidrequestaudithistory` VALUES ('4', '2', '', '2018-01-13 21:10:10', '2018-01-13 21:08:50', '4', '1', '2', '1');

-- ----------------------------
-- Table structure for iplog
-- ----------------------------
DROP TABLE IF EXISTS `iplog`;
CREATE TABLE `iplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `loginTime` datetime NOT NULL,
  `usertype` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iplog
-- ----------------------------
INSERT INTO `iplog` VALUES ('1', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 20:05:20', '0');
INSERT INTO `iplog` VALUES ('2', '0:0:0:0:0:0:0:1', '0', 'admin', '2018-01-13 20:08:15', '1');
INSERT INTO `iplog` VALUES ('3', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-01-13 20:08:54', '1');
INSERT INTO `iplog` VALUES ('4', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-13 20:13:54', '0');
INSERT INTO `iplog` VALUES ('5', '0:0:0:0:0:0:0:1', '1', 'luan2', '2018-01-13 20:14:43', '0');
INSERT INTO `iplog` VALUES ('6', '0:0:0:0:0:0:0:1', '1', 'luan2', '2018-01-13 20:16:17', '0');
INSERT INTO `iplog` VALUES ('7', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-13 20:16:38', '0');
INSERT INTO `iplog` VALUES ('8', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 20:18:02', '0');
INSERT INTO `iplog` VALUES ('9', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 20:18:48', '0');
INSERT INTO `iplog` VALUES ('10', '0:0:0:0:0:0:0:1', '1', 'luan3', '2018-01-13 20:18:52', '0');
INSERT INTO `iplog` VALUES ('11', '0:0:0:0:0:0:0:1', '1', 'luan3', '2018-01-13 20:19:40', '0');
INSERT INTO `iplog` VALUES ('12', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 20:39:18', '0');
INSERT INTO `iplog` VALUES ('13', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 21:08:04', '0');
INSERT INTO `iplog` VALUES ('14', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-01-13 21:10:00', '1');
INSERT INTO `iplog` VALUES ('15', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 21:10:20', '0');
INSERT INTO `iplog` VALUES ('16', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-13 21:10:44', '0');
INSERT INTO `iplog` VALUES ('17', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-14 10:48:32', '0');
INSERT INTO `iplog` VALUES ('18', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-14 09:49:44', '0');
INSERT INTO `iplog` VALUES ('19', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 13:21:55', '0');
INSERT INTO `iplog` VALUES ('20', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-14 14:35:09', '0');
INSERT INTO `iplog` VALUES ('21', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:35:16', '0');
INSERT INTO `iplog` VALUES ('22', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:37:50', '0');
INSERT INTO `iplog` VALUES ('23', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:42:17', '0');
INSERT INTO `iplog` VALUES ('24', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:43:48', '0');
INSERT INTO `iplog` VALUES ('25', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:46:04', '0');
INSERT INTO `iplog` VALUES ('26', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 14:47:20', '0');
INSERT INTO `iplog` VALUES ('27', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 15:00:01', '0');
INSERT INTO `iplog` VALUES ('28', '0:0:0:0:0:0:0:1', '1', 'luan1', '2018-01-14 15:03:13', '0');
INSERT INTO `iplog` VALUES ('29', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-15 10:49:18', '0');
INSERT INTO `iplog` VALUES ('30', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-01-15 11:09:02', '1');
INSERT INTO `iplog` VALUES ('31', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-15 11:14:55', '0');
INSERT INTO `iplog` VALUES ('32', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-01-15 11:15:33', '1');
INSERT INTO `iplog` VALUES ('33', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-15 11:16:31', '0');
INSERT INTO `iplog` VALUES ('34', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-01-15 11:17:04', '1');
INSERT INTO `iplog` VALUES ('35', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-17 14:51:11', '0');
INSERT INTO `iplog` VALUES ('36', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-01-26 23:45:00', '0');
INSERT INTO `iplog` VALUES ('37', '0:0:0:0:0:0:0:1', '1', 'luaa', '2018-01-27 08:59:51', '0');
INSERT INTO `iplog` VALUES ('38', '0:0:0:0:0:0:0:1', '0', null, '2018-02-27 22:46:49', '1');
INSERT INTO `iplog` VALUES ('39', '0:0:0:0:0:0:0:1', '1', 'admin', '2018-02-27 22:47:19', '1');
INSERT INTO `iplog` VALUES ('40', '0:0:0:0:0:0:0:1', '1', 'luan', '2018-02-27 22:48:53', '0');
INSERT INTO `iplog` VALUES ('41', '0:0:0:0:0:0:0:1', '1', 'luan6666', '2018-02-27 22:52:34', '0');
INSERT INTO `iplog` VALUES ('42', '0:0:0:0:0:0:0:1', '1', 'luan6666', '2018-02-27 22:56:49', '0');

-- ----------------------------
-- Table structure for logininfo
-- ----------------------------
DROP TABLE IF EXISTS `logininfo`;
CREATE TABLE `logininfo` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `userType` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logininfo
-- ----------------------------
INSERT INTO `logininfo` VALUES ('1', 'luan', '3A7FE37C8118452A07769D8A856F63D7', '1', '0');
INSERT INTO `logininfo` VALUES ('2', 'luan1', '5AFA1697515742740F231A0E77DE4A97', '1', '0');
INSERT INTO `logininfo` VALUES ('3', 'luan2', 'A6CB74F161296A7E1C5DD540654A9BC8', '1', '0');
INSERT INTO `logininfo` VALUES ('4', 'admin', '53F139E187E60F8376343990737B8FEC', '1', '1');
INSERT INTO `logininfo` VALUES ('5', 'luan3', '37EC4CCCF8C4294066D9B5F7D2BD8E33', '1', '0');
INSERT INTO `logininfo` VALUES ('6', 'luan6666', 'AE3AC7327912870C432B4619211D36D6', '1', '0');

-- ----------------------------
-- Table structure for mailverify
-- ----------------------------
DROP TABLE IF EXISTS `mailverify`;
CREATE TABLE `mailverify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `sendTime` datetime NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mailverify
-- ----------------------------
INSERT INTO `mailverify` VALUES ('1', '1', '2018-01-13 20:06:05', '27d83173-7e63-43ad-9b95-6132ff694307', '1279754008@qq.com', '<a href=http://localhost/bindEmail?uuid=27d83173-7e63-43ad-9b95-6132ff694307>点击这里</a>');

-- ----------------------------
-- Table structure for paymentschedule
-- ----------------------------
DROP TABLE IF EXISTS `paymentschedule`;
CREATE TABLE `paymentschedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadLine` datetime NOT NULL,
  `payDate` datetime DEFAULT NULL,
  `totalAmount` decimal(18,4) NOT NULL,
  `principal` decimal(18,4) NOT NULL,
  `interest` decimal(18,4) NOT NULL,
  `monthIndex` tinyint(4) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `bidRequestType` tinyint(4) NOT NULL,
  `returnType` tinyint(4) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `createUserId` bigint(20) NOT NULL,
  `bidRequestTitle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paymentschedule
-- ----------------------------
INSERT INTO `paymentschedule` VALUES ('1', '2018-02-13 20:13:44', '2018-01-13 20:37:30', '686.2456', '652.9123', '33.3333', '1', '1', '0', '0', '1', '1', '大保健');
INSERT INTO `paymentschedule` VALUES ('2', '2018-03-13 20:13:44', '2018-01-13 20:37:33', '686.2456', '658.3532', '27.8924', '2', '1', '0', '0', '1', '1', '大保健');
INSERT INTO `paymentschedule` VALUES ('3', '2018-04-13 20:13:44', '2018-01-13 20:37:36', '686.2456', '663.8395', '22.4061', '3', '1', '0', '0', '1', '1', '大保健');
INSERT INTO `paymentschedule` VALUES ('4', '2018-05-13 20:13:44', '2018-01-13 20:37:40', '686.2456', '669.3715', '16.8741', '4', '1', '0', '0', '1', '1', '大保健');
INSERT INTO `paymentschedule` VALUES ('5', '2018-06-13 20:13:44', '2018-01-13 20:37:44', '686.2456', '674.9496', '11.2960', '5', '1', '0', '0', '1', '1', '大保健');
INSERT INTO `paymentschedule` VALUES ('6', '2018-07-13 20:13:44', '2018-01-13 20:41:50', '686.2454', '680.5739', '5.6715', '6', '1', '0', '0', '1', '1', '大保健');

-- ----------------------------
-- Table structure for paymentscheduledetail
-- ----------------------------
DROP TABLE IF EXISTS `paymentscheduledetail`;
CREATE TABLE `paymentscheduledetail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bidAmount` decimal(18,4) NOT NULL,
  `bidId` bigint(20) NOT NULL,
  `totalAmount` decimal(18,4) NOT NULL,
  `principal` decimal(18,4) NOT NULL,
  `interest` decimal(18,4) NOT NULL,
  `monthIndex` tinyint(4) NOT NULL,
  `deadline` datetime NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `payDate` datetime DEFAULT NULL,
  `returnType` tinyint(4) NOT NULL,
  `paymentScheduleId` bigint(20) NOT NULL,
  `fromLoginInfoId` bigint(20) DEFAULT NULL,
  `toLoginInfoId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paymentscheduledetail
-- ----------------------------
INSERT INTO `paymentscheduledetail` VALUES ('1', '1900.0000', '3', '325.9666', '310.1333', '15.8333', '1', '2018-02-13 20:13:44', '1', '2018-01-13 20:37:30', '0', '1', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('2', '700.0000', '1', '120.0930', '114.2597', '5.8333', '2', '2018-02-13 20:13:44', '1', '2018-01-13 20:37:30', '0', '1', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('3', '1000.0000', '2', '171.5614', '163.2281', '8.3333', '3', '2018-02-13 20:13:44', '1', '2018-01-13 20:37:30', '0', '1', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('4', '400.0000', '4', '68.6246', '65.2912', '3.3334', '4', '2018-02-13 20:13:44', '1', '2018-01-13 20:37:30', '0', '1', '1', '5');
INSERT INTO `paymentscheduledetail` VALUES ('5', '1900.0000', '3', '325.9667', '312.7178', '13.2489', '1', '2018-03-13 20:13:44', '1', '2018-01-13 20:37:33', '0', '2', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('6', '700.0000', '1', '120.0930', '115.2118', '4.8812', '2', '2018-03-13 20:13:44', '1', '2018-01-13 20:37:33', '0', '2', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('7', '1000.0000', '2', '171.5614', '164.5883', '6.9731', '3', '2018-03-13 20:13:44', '1', '2018-01-13 20:37:34', '0', '2', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('8', '400.0000', '4', '68.6245', '65.8353', '2.7892', '4', '2018-03-13 20:13:44', '1', '2018-01-13 20:37:34', '0', '2', '1', '5');
INSERT INTO `paymentscheduledetail` VALUES ('9', '1900.0000', '3', '325.9667', '315.3238', '10.6429', '1', '2018-04-13 20:13:44', '1', '2018-01-13 20:37:37', '0', '3', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('10', '700.0000', '1', '120.0930', '116.1719', '3.9211', '2', '2018-04-13 20:13:44', '1', '2018-01-13 20:37:37', '0', '3', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('11', '1000.0000', '2', '171.5614', '165.9599', '5.6015', '3', '2018-04-13 20:13:44', '1', '2018-01-13 20:37:37', '0', '3', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('12', '400.0000', '4', '68.6245', '66.3839', '2.2406', '4', '2018-04-13 20:13:44', '1', '2018-01-13 20:37:37', '0', '3', '1', '5');
INSERT INTO `paymentscheduledetail` VALUES ('13', '1900.0000', '3', '325.9667', '317.9515', '8.0152', '1', '2018-05-13 20:13:44', '1', '2018-01-13 20:37:40', '0', '4', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('14', '700.0000', '1', '120.0930', '117.1400', '2.9530', '2', '2018-05-13 20:13:44', '1', '2018-01-13 20:37:40', '0', '4', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('15', '1000.0000', '2', '171.5614', '167.3429', '4.2185', '3', '2018-05-13 20:13:44', '1', '2018-01-13 20:37:40', '0', '4', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('16', '400.0000', '4', '68.6245', '66.9371', '1.6874', '4', '2018-05-13 20:13:44', '1', '2018-01-13 20:37:40', '0', '4', '1', '5');
INSERT INTO `paymentscheduledetail` VALUES ('17', '1900.0000', '3', '325.9667', '320.6011', '5.3656', '1', '2018-06-13 20:13:44', '1', '2018-01-13 20:37:44', '0', '5', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('18', '700.0000', '1', '120.0930', '118.1162', '1.9768', '2', '2018-06-13 20:13:44', '1', '2018-01-13 20:37:44', '0', '5', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('19', '1000.0000', '2', '171.5614', '168.7374', '2.8240', '3', '2018-06-13 20:13:44', '1', '2018-01-13 20:37:44', '0', '5', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('20', '400.0000', '4', '68.6245', '67.4949', '1.1296', '4', '2018-06-13 20:13:44', '1', '2018-01-13 20:37:44', '0', '5', '1', '5');
INSERT INTO `paymentscheduledetail` VALUES ('21', '1900.0000', '3', '325.9666', '323.2726', '2.6940', '1', '2018-07-13 20:13:44', '1', '2018-01-13 20:41:50', '0', '6', '1', '2');
INSERT INTO `paymentscheduledetail` VALUES ('22', '700.0000', '1', '120.0929', '119.1004', '0.9925', '2', '2018-07-13 20:13:44', '1', '2018-01-13 20:41:50', '0', '6', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('23', '1000.0000', '2', '171.5614', '170.1435', '1.4179', '3', '2018-07-13 20:13:44', '1', '2018-01-13 20:41:50', '0', '6', '1', '3');
INSERT INTO `paymentscheduledetail` VALUES ('24', '400.0000', '4', '68.6245', '68.0574', '0.5671', '4', '2018-07-13 20:13:44', '1', '2018-01-13 20:41:51', '0', '6', '1', '5');

-- ----------------------------
-- Table structure for platformbankinfo
-- ----------------------------
DROP TABLE IF EXISTS `platformbankinfo`;
CREATE TABLE `platformbankinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(255) DEFAULT NULL,
  `accountName` varchar(255) DEFAULT NULL,
  `accountNumber` varchar(255) DEFAULT NULL,
  `bankForkName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platformbankinfo
-- ----------------------------
INSERT INTO `platformbankinfo` VALUES ('1', '1', '栾', '100000', '100000');
INSERT INTO `platformbankinfo` VALUES ('2', '2', '111', '1111', '1111');

-- ----------------------------
-- Table structure for realauth
-- ----------------------------
DROP TABLE IF EXISTS `realauth`;
CREATE TABLE `realauth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `realname` varchar(50) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `bornDate` varchar(50) DEFAULT NULL,
  `idNumber` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `image1` varchar(255) NOT NULL,
  `image2` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditorId` bigint(20) DEFAULT NULL,
  `applierId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of realauth
-- ----------------------------
INSERT INTO `realauth` VALUES ('1', '栾', '1', '2018-01-13 00:00:00', '51000000000000', '51000000000', '1', '4cd3d154-68ef-4f3a-aaaa-348f3a6adbda.png', 'a95ba56c-3e31-4ff3-b583-2bdafaea0c56.png', '通过', '2018-01-13 20:09:16', '2018-01-13 20:08:02', '4', '1');
INSERT INTO `realauth` VALUES ('2', 'luan', '1', '2018-02-21 00:00:00', '111', '111', '1', '9689d0fc-6ddc-4bfb-90ee-41258750ed8f.jpg', 'd381908c-b130-4149-8b13-b47745662486.jpg', '444', '2018-02-27 22:56:15', '2018-02-27 22:53:57', null, '6');

-- ----------------------------
-- Table structure for recharge
-- ----------------------------
DROP TABLE IF EXISTS `recharge`;
CREATE TABLE `recharge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditorId` bigint(20) DEFAULT NULL,
  `applierId` bigint(20) NOT NULL,
  `tradeCode` varchar(255) NOT NULL,
  `tradeTime` datetime NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `bankInfoId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recharge
-- ----------------------------
INSERT INTO `recharge` VALUES ('1', '1', '通过', '2018-01-13 20:14:33', '2018-01-13 20:14:22', '4', '2', '10000', '2018-01-13 00:00:00', '10000.0000', '10000', '1');
INSERT INTO `recharge` VALUES ('2', '1', '通过', '2018-01-13 20:15:03', '2018-01-13 20:14:56', '4', '3', '10000', '2018-01-13 00:00:00', '10000.0000', '10000', '1');
INSERT INTO `recharge` VALUES ('3', '1', '通过', '2018-01-13 20:18:28', '2018-01-13 20:18:12', '4', '1', '10000', '2018-01-13 00:00:00', '10000.0000', '10000', '1');
INSERT INTO `recharge` VALUES ('4', '1', '', '2018-01-13 20:19:28', '2018-01-13 20:19:23', '4', '5', '10000', '2018-01-13 00:00:00', '10000.0000', '10000', '1');
INSERT INTO `recharge` VALUES ('5', '1', '', '2018-01-13 20:38:08', '2018-01-13 20:38:02', '4', '1', '1000', '2018-01-13 00:00:00', '10000.0000', '10000', '1');
INSERT INTO `recharge` VALUES ('6', '0', null, null, '2018-02-27 22:50:01', null, '1', '00001', '2018-02-14 00:00:00', '1000.0000', '10000', '1');

-- ----------------------------
-- Table structure for systemaccount
-- ----------------------------
DROP TABLE IF EXISTS `systemaccount`;
CREATE TABLE `systemaccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `usableAmount` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemaccount
-- ----------------------------
INSERT INTO `systemaccount` VALUES ('1', '0', '211.7473', '0.0000');

-- ----------------------------
-- Table structure for systemaccountflow
-- ----------------------------
DROP TABLE IF EXISTS `systemaccountflow`;
CREATE TABLE `systemaccountflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NOT NULL,
  `actionType` tinyint(4) NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `usableAmount` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemaccountflow
-- ----------------------------
INSERT INTO `systemaccountflow` VALUES ('1', '2018-01-13 20:36:37', '6', '200.0000', '平台收取管理费200.0000元', '200.0000', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('2', '2018-01-13 20:37:31', '6', '3.3333', '平台收取利息管理费3.3333元', '203.3333', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('3', '2018-01-13 20:37:34', '6', '2.7892', '平台收取利息管理费2.7892元', '206.1225', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('4', '2018-01-13 20:37:37', '6', '2.2406', '平台收取利息管理费2.2406元', '208.3631', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('5', '2018-01-13 20:37:40', '6', '1.6874', '平台收取利息管理费1.6874元', '210.0505', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('6', '2018-01-13 20:37:44', '6', '1.1296', '平台收取利息管理费1.1296元', '211.1801', '0.0000');
INSERT INTO `systemaccountflow` VALUES ('7', '2018-01-13 20:41:51', '6', '0.5672', '平台收取利息管理费0.5672元', '211.7473', '0.0000');

-- ----------------------------
-- Table structure for systemdictionary
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionary`;
CREATE TABLE `systemdictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemdictionary
-- ----------------------------
INSERT INTO `systemdictionary` VALUES ('1', 'incomeGrade', '月收入');
INSERT INTO `systemdictionary` VALUES ('2', 'educationBackground', '学历');
INSERT INTO `systemdictionary` VALUES ('3', 'marriage', '婚姻');
INSERT INTO `systemdictionary` VALUES ('4', 'kidCount', '子女');
INSERT INTO `systemdictionary` VALUES ('5', 'houseCondition', '住房条件');
INSERT INTO `systemdictionary` VALUES ('6', 'userFileType', '风控资料类型');

-- ----------------------------
-- Table structure for systemdictionaryitem
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionaryitem`;
CREATE TABLE `systemdictionaryitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `sequence` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemdictionaryitem
-- ----------------------------
INSERT INTO `systemdictionaryitem` VALUES ('1', '1', '3000以下', '1');
INSERT INTO `systemdictionaryitem` VALUES ('2', '1', '3000~5000', '2');
INSERT INTO `systemdictionaryitem` VALUES ('3', '2', '大专以下', '1');
INSERT INTO `systemdictionaryitem` VALUES ('4', '2', '大专', '2');
INSERT INTO `systemdictionaryitem` VALUES ('5', '3', '已婚', '1');
INSERT INTO `systemdictionaryitem` VALUES ('6', '3', '未婚', '2');
INSERT INTO `systemdictionaryitem` VALUES ('7', '4', '有子女', '1');
INSERT INTO `systemdictionaryitem` VALUES ('8', '4', '无子女', '2');
INSERT INTO `systemdictionaryitem` VALUES ('9', '5', '有自有房', '1');
INSERT INTO `systemdictionaryitem` VALUES ('10', '5', '无自有房', '2');
INSERT INTO `systemdictionaryitem` VALUES ('11', '5', '租房', '3');
INSERT INTO `systemdictionaryitem` VALUES ('12', '6', '房产证正面', '1');
INSERT INTO `systemdictionaryitem` VALUES ('13', '6', '房产证反面', '2');
INSERT INTO `systemdictionaryitem` VALUES ('14', '6', '户口本', '3');
INSERT INTO `systemdictionaryitem` VALUES ('15', '6', '结婚证', '4');
INSERT INTO `systemdictionaryitem` VALUES ('16', '6', '银行流水证明', '5');
INSERT INTO `systemdictionaryitem` VALUES ('17', '6', '学位证', '6');

-- ----------------------------
-- Table structure for t_org_bank
-- ----------------------------
DROP TABLE IF EXISTS `t_org_bank`;
CREATE TABLE `t_org_bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bank_code` varchar(8) NOT NULL COMMENT '银行名称缩写',
  `bank_name` varchar(30) NOT NULL COMMENT '银行名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行列表';

-- ----------------------------
-- Records of t_org_bank
-- ----------------------------

-- ----------------------------
-- Table structure for userfile
-- ----------------------------
DROP TABLE IF EXISTS `userfile`;
CREATE TABLE `userfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditorId` bigint(20) DEFAULT NULL,
  `applierId` bigint(20) NOT NULL,
  `score` tinyint(4) NOT NULL,
  `image` varchar(255) NOT NULL,
  `fileTypeId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userfile
-- ----------------------------
INSERT INTO `userfile` VALUES ('1', '1', '通过', '2018-01-13 20:11:00', '2018-01-13 20:09:27', '4', '1', '5', '34fdf495-3b6a-404e-97a1-4d85276a0ea9.png', '12');
INSERT INTO `userfile` VALUES ('2', '1', '通过', '2018-01-13 20:11:10', '2018-01-13 20:09:37', '4', '1', '5', '44c75b25-a516-4afd-9e92-9d55a37125a8.png', '13');
INSERT INTO `userfile` VALUES ('3', '1', '通过', '2018-01-13 20:11:17', '2018-01-13 20:09:44', '4', '1', '5', '6a9b2102-0619-4de7-91c5-7b71c1beb95c.png', '14');
INSERT INTO `userfile` VALUES ('4', '1', '通过', '2018-01-13 20:11:24', '2018-01-13 20:09:49', '4', '1', '5', 'e7152e49-f5a1-4616-8616-e90fa2f1e5dc.png', '15');
INSERT INTO `userfile` VALUES ('5', '1', '通过', '2018-01-13 20:11:36', '2018-01-13 20:09:56', '4', '1', '5', '18d79557-d842-488d-919f-8522f412cb02.png', '15');
INSERT INTO `userfile` VALUES ('6', '1', '通过', '2018-01-13 20:11:43', '2018-01-13 20:10:03', '4', '1', '5', 'a5d38d05-4b78-4caa-a6ea-c92f848eb496.png', '16');
INSERT INTO `userfile` VALUES ('7', '1', '通过', '2018-01-13 20:11:49', '2018-01-13 20:10:28', '4', '1', '5', '679449a3-1053-41a5-97d1-fca9f35fce75.png', '17');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `bitState` bigint(20) NOT NULL,
  `realName` varchar(30) DEFAULT NULL,
  `idNumber` varchar(30) DEFAULT NULL,
  `phoneNumber` varchar(30) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `score` tinyint(4) DEFAULT NULL,
  `realAuthId` bigint(20) DEFAULT NULL,
  `incomeGradeId` bigint(20) DEFAULT NULL,
  `marriageId` bigint(20) DEFAULT NULL,
  `kidCountId` bigint(20) DEFAULT NULL,
  `educationBackgroundId` bigint(20) DEFAULT NULL,
  `houseConditionId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '16', '63', '栾', '51000000000000', '17724802551', '1279754008@qq.com', '35', '1', '2', '6', '8', '4', '11');
INSERT INTO `userinfo` VALUES ('2', '0', '0', null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('3', '0', '0', null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('5', '0', '0', null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('6', '2', '8', 'luan', '111', null, null, '0', '2', null, null, null, null, null);

-- ----------------------------
-- Table structure for videoauth
-- ----------------------------
DROP TABLE IF EXISTS `videoauth`;
CREATE TABLE `videoauth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditorId` bigint(20) DEFAULT NULL,
  `applierId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of videoauth
-- ----------------------------
INSERT INTO `videoauth` VALUES ('1', '1', '通过', '2018-01-13 20:12:14', '2018-01-13 20:12:14', '4', '1');
