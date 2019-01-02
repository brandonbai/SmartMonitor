/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : smartmonitor

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 02/01/2019 17:22:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of area
-- ----------------------------
BEGIN;
INSERT INTO `area` VALUES (1, '区域1', '区域');
COMMIT;

-- ----------------------------
-- Table structure for dataType
-- ----------------------------
DROP TABLE IF EXISTS `dataType`;
CREATE TABLE `dataType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `state` bit(1) DEFAULT NULL COMMENT '状态',
  `node_id` int(11) DEFAULT NULL COMMENT '节点id',
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `content` text COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
BEGIN;
INSERT INTO `log` VALUES (3, 5, '2018-10-24 16:50:19', '节点[id=1]异常恢复，数值为11.00', NULL);
INSERT INTO `log` VALUES (4, 5, '2018-10-24 16:50:19', '节点[id=1]数据异常，数值为112.00', NULL);
INSERT INTO `log` VALUES (5, 5, '2018-10-24 16:50:19', '节点[id=1]异常恢复，数值为13.00', NULL);
INSERT INTO `log` VALUES (6, 5, '2018-10-24 16:50:19', '节点[id=1]数据异常，数值为113.00', NULL);
INSERT INTO `log` VALUES (7, 5, '2018-10-24 16:50:19', '节点[id=1]异常恢复，数值为13.00', NULL);
INSERT INTO `log` VALUES (8, 5, '2018-10-24 16:50:00', '节点[id=1]数据异常，数值为22.00', NULL);
COMMIT;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sensor
-- ----------------------------
DROP TABLE IF EXISTS `sensor`;
CREATE TABLE `sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `device_id` int(11) DEFAULT NULL COMMENT '设备id',
  `node_id` int(11) DEFAULT NULL COMMENT '节点id',
  `threshold_id` int(11) DEFAULT NULL COMMENT '阈值id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor
-- ----------------------------
BEGIN;
INSERT INTO `sensor` VALUES (1, 'sensor1', 'L', 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sensor_value
-- ----------------------------
DROP TABLE IF EXISTS `sensor_value`;
CREATE TABLE `sensor_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) DEFAULT NULL COMMENT '传感器id',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor_value
-- ----------------------------
BEGIN;
INSERT INTO `sensor_value` VALUES (1, 1, '2018-10-18 12:05:42', 123);
INSERT INTO `sensor_value` VALUES (2, 1, '2018-10-18 12:05:52', 117);
INSERT INTO `sensor_value` VALUES (3, 1, '2018-10-18 14:47:39', 11);
INSERT INTO `sensor_value` VALUES (4, 1, '2018-10-18 14:49:10', 11);
INSERT INTO `sensor_value` VALUES (5, 1, '2018-10-18 14:49:41', 21);
INSERT INTO `sensor_value` VALUES (6, 1, '2018-10-18 14:52:45', 21);
INSERT INTO `sensor_value` VALUES (7, 1, '2018-10-18 14:53:16', 11);
INSERT INTO `sensor_value` VALUES (8, 1, '2018-10-18 14:54:21', 31);
INSERT INTO `sensor_value` VALUES (9, 1, '2018-10-24 16:05:14', 12);
INSERT INTO `sensor_value` VALUES (10, 1, '2018-10-24 16:12:30', 12);
INSERT INTO `sensor_value` VALUES (11, 1, '2018-10-24 16:15:00', 12);
INSERT INTO `sensor_value` VALUES (12, 1, '2018-10-24 16:17:34', 112);
INSERT INTO `sensor_value` VALUES (13, 1, '2018-10-24 16:26:28', 11);
INSERT INTO `sensor_value` VALUES (14, 1, '2018-10-24 16:31:04', 112);
INSERT INTO `sensor_value` VALUES (15, 1, '2018-10-24 16:31:21', 13);
INSERT INTO `sensor_value` VALUES (16, 1, '2018-10-24 16:31:50', 113);
INSERT INTO `sensor_value` VALUES (17, 1, '2018-10-24 16:31:52', 13);
INSERT INTO `sensor_value` VALUES (18, 1, '2018-10-24 16:31:54', 11);
INSERT INTO `sensor_value` VALUES (19, 1, '2018-10-24 16:33:52', 11);
INSERT INTO `sensor_value` VALUES (20, 1, '2018-10-24 16:49:59', 22);
COMMIT;

-- ----------------------------
-- Table structure for threshold
-- ----------------------------
DROP TABLE IF EXISTS `threshold`;
CREATE TABLE `threshold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `max` int(11) DEFAULT NULL COMMENT '最大值',
  `min` int(11) DEFAULT NULL COMMENT '最小值',
  `sensor_id` int(11) DEFAULT NULL COMMENT '传感器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of threshold
-- ----------------------------
BEGIN;
INSERT INTO `threshold` VALUES (1, 20, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `tel` varchar(11) DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'feihuhu', 1, 'e10adc3949ba59abbe56e057f20f883e', 'feihuhu', 1, '13294029161');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
