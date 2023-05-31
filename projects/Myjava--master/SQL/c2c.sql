/*
 Navicat Premium Data Transfer

 Source Server         : 服务器上的mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : c2c

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 29/05/2020 13:36:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `money` decimal(10, 2) DEFAULT NULL COMMENT '金额',
  `create_time` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recipient` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `rephone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `deaddress` tinyint(1) DEFAULT 0 COMMENT '默认地址',
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admininformation
-- ----------------------------
DROP TABLE IF EXISTS `admininformation`;
CREATE TABLE `admininformation`  (
  `id` int(11) NOT NULL,
  `ano` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for adminoperation
-- ----------------------------
DROP TABLE IF EXISTS `adminoperation`;
CREATE TABLE `adminoperation`  (
  `id` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  `operation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for allkinds
-- ----------------------------
DROP TABLE IF EXISTS `allkinds`;
CREATE TABLE `allkinds`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boughtshop
-- ----------------------------
DROP TABLE IF EXISTS `boughtshop`;
CREATE TABLE `boughtshop`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for classification
-- ----------------------------
DROP TABLE IF EXISTS `classification`;
CREATE TABLE `classification`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  `aid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goodscar
-- ----------------------------
DROP TABLE IF EXISTS `goodscar`;
CREATE TABLE `goodscar`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `sid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goodsoforderform
-- ----------------------------
DROP TABLE IF EXISTS `goodsoforderform`;
CREATE TABLE `goodsoforderform`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ofid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orderform
-- ----------------------------
DROP TABLE IF EXISTS `orderform`;
CREATE TABLE `orderform`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `display` int(11) NOT NULL DEFAULT 1 COMMENT '支付金额',
  `uid` int(11) NOT NULL COMMENT '买家id ',
  `seller_id` int(11) NOT NULL COMMENT '卖家id',
  `shop_id` int(11) NOT NULL COMMENT '商品id',
  `quantity` int(5) DEFAULT NULL COMMENT '数量',
  `context` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recipient` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收件人',
  `rephone` varchar(255) CHARACTER SET utf32 COLLATE utf32_general_ci DEFAULT NULL,
  `readdress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地址',
  `status` int(1) NOT NULL COMMENT '配送状态 1为未配送 2：已配送 3已完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recommended
-- ----------------------------
DROP TABLE IF EXISTS `recommended`;
CREATE TABLE `recommended`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `com` double(255, 0) UNSIGNED DEFAULT 0 COMMENT '相似度',
  `cnt` int(8) UNSIGNED ZEROFILL DEFAULT 00000000 COMMENT '购买该商品用户数量',
  `shopid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shopcontext
-- ----------------------------
DROP TABLE IF EXISTS `shopcontext`;
CREATE TABLE `shopcontext`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `sid` int(11) NOT NULL,
  `context` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  `uid` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shopinformation
-- ----------------------------
DROP TABLE IF EXISTS `shopinformation`;
CREATE TABLE `shopinformation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `level` int(11) NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `sort` int(11) NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  `quantity` int(11) NOT NULL,
  `transaction` int(11) NOT NULL DEFAULT 1,
  `sales` int(11) DEFAULT 0,
  `uid` int(11) NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `thumbnails` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_uid`(`uid`) USING BTREE,
  INDEX `index_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1121 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shoppicture
-- ----------------------------
DROP TABLE IF EXISTS `shoppicture`;
CREATE TABLE `shoppicture`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `sid` int(11) NOT NULL,
  `picture` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for specifickinds
-- ----------------------------
DROP TABLE IF EXISTS `specifickinds`;
CREATE TABLE `specifickinds`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for usercollection
-- ----------------------------
DROP TABLE IF EXISTS `usercollection`;
CREATE TABLE `usercollection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userinformation
-- ----------------------------
DROP TABLE IF EXISTS `userinformation`;
CREATE TABLE `userinformation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `clazz` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sno` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dormitory` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `createtime` datetime(0) DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `campus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `manager` tinyint(1) UNSIGNED ZEROFILL DEFAULT 0,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_id`(`id`) USING BTREE,
  INDEX `selectByPhone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinformation
-- ----------------------------
INSERT INTO `userinformation` VALUES (1, '2020-04-14 03:27:07', 'yzq', '18234232034', '杨志强', '物联网1603', '2016002166', '1-110', '男', '2020-04-14 03:27:07', NULL, '太原理工大学', 'yin', 1, '2391906522');

-- ----------------------------
-- Table structure for userpassword
-- ----------------------------
DROP TABLE IF EXISTS `userpassword`;
CREATE TABLE `userpassword`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `password` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `forgin_key`(`uid`) USING BTREE,
  CONSTRAINT `forgin_key` FOREIGN KEY (`uid`) REFERENCES `userinformation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userpassword
-- ----------------------------
INSERT INTO `userpassword` VALUES (1, '2020-04-14 03:27:07', 'dmfEaCijQLPfI3w3BQ6PKQ==', 1);

-- ----------------------------
-- Table structure for userrelease
-- ----------------------------
DROP TABLE IF EXISTS `userrelease`;
CREATE TABLE `userrelease`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  `uid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1029 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userstate
-- ----------------------------
DROP TABLE IF EXISTS `userstate`;
CREATE TABLE `userstate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `credit` int(11) NOT NULL DEFAULT 80,
  `balance` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `modified` datetime(0) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userwant
-- ----------------------------
DROP TABLE IF EXISTS `userwant`;
CREATE TABLE `userwant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort` int(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wantcontext
-- ----------------------------
DROP TABLE IF EXISTS `wantcontext`;
CREATE TABLE `wantcontext`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified` datetime(0) DEFAULT NULL,
  `uwid` int(11) NOT NULL,
  `context` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `display` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
