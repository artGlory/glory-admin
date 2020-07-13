/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : multi_module_db_01

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-07-11 00:19:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_admin_login_01
-- ----------------------------
DROP TABLE IF EXISTS `log_admin_login_01`;
CREATE TABLE `log_admin_login_01` (
  `uk` varchar(32) NOT NULL COMMENT '标识',
  `user_uk` varchar(32) NOT NULL COMMENT '用户标识',
  `user_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名称',
  `user_role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色标识',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登陆ip',
  `remote_address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登陆地址',
  `remark` text CHARACTER SET utf8mb4 COMMENT '附加信息',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_admin_login_01
-- ----------------------------

-- ----------------------------
-- Table structure for log_admin_login_02
-- ----------------------------
DROP TABLE IF EXISTS `log_admin_login_02`;
CREATE TABLE `log_admin_login_02` (
  `uk` varchar(32) NOT NULL COMMENT '标识',
  `user_uk` varchar(32) NOT NULL COMMENT '用户标识',
  `user_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名称',
  `user_role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色标识',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登陆ip',
  `remote_address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登陆地址',
  `remark` text CHARACTER SET utf8mb4 COMMENT '附加信息',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_admin_login_02
-- ----------------------------
INSERT INTO `log_admin_login_02` VALUES ('effb6aff7fc34dffb9b2627e6f7136d6', 'ca501d32c01e42ba8e0b6524e169f73a', 'admin', 'faee5041c0a64adb8f388e1a2e1af082', '192.168.254.158', '[局域网, 局域网]', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36', '2020-07-11 00:18:31', '2020-07-11 00:18:31');

-- ----------------------------
-- Table structure for log_operate_01
-- ----------------------------
DROP TABLE IF EXISTS `log_operate_01`;
CREATE TABLE `log_operate_01` (
  `uk` varchar(32) NOT NULL COMMENT '唯一标识',
  `operator` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作人',
  `operator_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户标识',
  `operator_role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户角色标识',
  `operate_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作名称',
  `operate_path` text CHARACTER SET utf8mb4 COMMENT '操作路径',
  `params` text CHARACTER SET utf8mb4 COMMENT '参数',
  `result` int(11) DEFAULT NULL COMMENT '操作结果；1正常，0异常',
  `fail_reason` text CHARACTER SET utf8mb4 COMMENT '失败原因',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`),
  KEY `nk_001` (`operate_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_operate_01
-- ----------------------------

-- ----------------------------
-- Table structure for log_operate_02
-- ----------------------------
DROP TABLE IF EXISTS `log_operate_02`;
CREATE TABLE `log_operate_02` (
  `uk` varchar(32) NOT NULL COMMENT '唯一标识',
  `operator` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作人',
  `operator_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户标识',
  `operator_role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户角色标识',
  `operate_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作名称',
  `operate_path` text CHARACTER SET utf8mb4 COMMENT '操作路径',
  `params` text CHARACTER SET utf8mb4 COMMENT '参数',
  `result` int(11) DEFAULT NULL COMMENT '操作结果；1正常，0异常',
  `fail_reason` text CHARACTER SET utf8mb4 COMMENT '失败原因',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`),
  KEY `nk_001` (`operate_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log_operate_02
-- ----------------------------
