/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : multi_module_db

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-07-04 14:45:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_privilege
-- ----------------------------
DROP TABLE IF EXISTS `admin_privilege`;
CREATE TABLE `admin_privilege` (
  `uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限唯一标识',
  `privilege_type` int(11) NOT NULL COMMENT '权限类型 1：目录权限；2：页面权限；3：按钮权限',
  `privilege_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限名称',
  `privilege_path` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限路径',
  `sort` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '排序',
  `parent_uk` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '父标签标识',
  `create_time` datetime NOT NULL COMMENT '插入时间',
  `update_time` datetime NOT NULL COMMENT '最后更改时间',
  PRIMARY KEY (`uk`,`privilege_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_privilege
-- ----------------------------
INSERT INTO `admin_privilege` VALUES ('08417295ecfe436694a4acc724bd4210', '3', '查询用户信息', 'UserController.getPageAdminInfo', '01-01-03', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-02 10:19:18', '2020-07-02 10:19:21');
INSERT INTO `admin_privilege` VALUES ('2263d6407fce41be8db428f31ca47d3a', '3', '查看操作日志', 'AdminOperateLogController.getPageOperateLog', '01-05-01', '7207be3756b84502be62817adb7e516e', '2020-07-03 22:13:25', '2020-07-03 22:13:28');
INSERT INTO `admin_privilege` VALUES ('226b8ab287ac479780b44f4ae1981848', '2', '角色管理', '/amdin-manage/role-manage', '01-02', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-26 18:41:29', '2020-06-26 18:41:26');
INSERT INTO `admin_privilege` VALUES ('3353c636def0483192bac66ce5401349', '3', '用户登出', 'UserController.adminLogout', '01-01-02', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-06-30 21:50:33', '2020-06-30 21:50:36');
INSERT INTO `admin_privilege` VALUES ('34b40d3ef9334b50bde0cc7129189e86', '3', '更新用户信息', 'UserController.updateAdminInfo', '01-01-04', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 08:47:37', '2020-07-03 08:47:39');
INSERT INTO `admin_privilege` VALUES ('3b168c360b0d48eaa21d2a5cba7ac717', '3', '查看权限', 'PrivilegeController.getAdminPermissionTree', '01-03-01', '8c41b307eef140abb98182e23b8dcf1d', '2020-06-30 23:29:00', '2020-06-30 23:29:03');
INSERT INTO `admin_privilege` VALUES ('3c9ef7b90e5a43cc863941f0f2368021', '1', '后台人员管理', '/admin-manage', '01', '111222333444aaabbbcccddd99988877', '2020-06-26 18:39:26', '2020-06-26 18:39:29');
INSERT INTO `admin_privilege` VALUES ('53ad98f0ed58479ba19be620c64ac580', '3', '查看角色', 'RoleController.getRolesTree,RoleController.getAllSelfSubRole', '01-02-01', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:42:22', '2020-06-26 18:42:19');
INSERT INTO `admin_privilege` VALUES ('570ea0f82f8c40158065183f255b52cd', '3', '删除用户', 'UserController.delAdminInfo', '01-01-06', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 17:06:08', '2020-07-03 17:06:10');
INSERT INTO `admin_privilege` VALUES ('70d21e7631c64862b984b40abe41ad42', '2', '登陆日志', '/amdin-manage/admin-login-log', '01-04', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-03 19:32:51', '2020-07-03 19:32:54');
INSERT INTO `admin_privilege` VALUES ('7207be3756b84502be62817adb7e516e', '2', '操作日志', '/amdin-manage/admin-operate-log', '01-05', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-03 22:12:53', '2020-07-03 22:12:55');
INSERT INTO `admin_privilege` VALUES ('7893fd34a1644e6b94cdba9057407446', '3', '查询登陆日志', 'AdminLoginLogController.getPageLoginLog', '01-04-01', '70d21e7631c64862b984b40abe41ad42', '2020-07-03 19:33:58', '2020-07-03 19:34:01');
INSERT INTO `admin_privilege` VALUES ('8bc163d254fd4711a4ac8532f183492f', '3', '添加角色', 'RoleController.addRole', '01-02-02', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:42:42', '2020-06-26 18:42:38');
INSERT INTO `admin_privilege` VALUES ('8c41b307eef140abb98182e23b8dcf1d', '2', '权限管理', '/admin-magage/admin-permission', '01-03', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-30 23:27:13', '2020-06-30 23:27:17');
INSERT INTO `admin_privilege` VALUES ('a188d76e264244238c35616a179b087f', '3', '用户登陆', 'UserController.adminLogin,UserController.getAdminInfo', '01-01-01', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-06-30 17:03:36', '2020-06-30 17:03:39');
INSERT INTO `admin_privilege` VALUES ('b2a187f4306543ad8c6d014211f66cd9', '3', '删除角色', 'RoleController.deleteRole', '01-02-03', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:43:44', '2020-06-26 18:43:42');
INSERT INTO `admin_privilege` VALUES ('d0d75779f3f848c39683ea5147967050', '3', '更新权限', 'PrivilegeController.updateAdminRolePermissions', '01-03-02', '8c41b307eef140abb98182e23b8dcf1d', '2020-06-30 23:29:43', '2020-06-30 23:29:47');
INSERT INTO `admin_privilege` VALUES ('db1785bfcbf543ea9f27b255ea54971f', '3', '更改角色', 'RoleController.updateRole', '01-02-04', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:43:44', '2020-06-26 18:43:42');
INSERT INTO `admin_privilege` VALUES ('fa32475acdcf469ea6a58e2dc15ed40d', '2', '用户管理', '/amdin-manage/admin-manage', '01-01', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-30 17:02:11', '2020-06-30 17:02:16');
INSERT INTO `admin_privilege` VALUES ('ff1b8bcc23b54658a794f37a4a154170', '3', '添加用户', 'UserController.addAdminInfo', '01-01-05', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 16:50:11', '2020-07-03 16:50:13');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT 'uk',
  `role_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '角色描述',
  `parent_uk` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '父uk',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`uk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('6b03414592574b019fd1c0392ba9ffbf', '系统管理员2', '系统管理员，拥有所有权限', '111222333444aaabbbcccddd99988877', '2020-06-27 15:18:06', '2020-06-29 22:54:52');
INSERT INTO `admin_role` VALUES ('aef2878e79a248909e4d9b42be0de131', '11', '11', 'f3470e89a43d46018a6b82e3b50f692a', '2020-07-04 14:30:34', '2020-07-04 14:30:34');
INSERT INTO `admin_role` VALUES ('f3470e89a43d46018a6b82e3b50f692a', '1', '1', 'faee5041c0a64adb8f388e1a2e1af082', '2020-07-04 14:30:18', '2020-07-04 14:30:18');
INSERT INTO `admin_role` VALUES ('faee5041c0a64adb8f388e1a2e1af082', '系统管理员', '系统管理员，拥有所有权限', '111222333444aaabbbcccddd99988877', '2020-06-25 15:43:31', '2020-06-25 15:43:34');

-- ----------------------------
-- Table structure for admin_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_privilege`;
CREATE TABLE `admin_role_privilege` (
  `uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键',
  `role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色主键',
  `privilege_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限主键',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`),
  UNIQUE KEY `uk_01` (`role_uk`,`privilege_uk`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role_privilege
-- ----------------------------
INSERT INTO `admin_role_privilege` VALUES ('15a6164ea2f04ed2ae014e2896482591', 'aef2878e79a248909e4d9b42be0de131', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('1a7f5777cd0f4729b11bf4d203637dc7', 'aef2878e79a248909e4d9b42be0de131', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('2bd4de62c8b349c19860bb6398a4dfa9', 'aef2878e79a248909e4d9b42be0de131', 'a188d76e264244238c35616a179b087f', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('2ca3dc20c5484fb1a2ba9cca0ec53db7', 'f3470e89a43d46018a6b82e3b50f692a', '34b40d3ef9334b50bde0cc7129189e86', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('2e6ec061daf64e16babf5e764baf0d8f', 'f3470e89a43d46018a6b82e3b50f692a', '53ad98f0ed58479ba19be620c64ac580', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('588ea13f98104d4b9c85cf78d284eb18', 'f3470e89a43d46018a6b82e3b50f692a', '08417295ecfe436694a4acc724bd4210', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('5f22d2e8671d4d5d9e0fdba487819d6b', 'f3470e89a43d46018a6b82e3b50f692a', '226b8ab287ac479780b44f4ae1981848', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('68d719ee97fb499b9aa7c66bcc5006a2', 'aef2878e79a248909e4d9b42be0de131', '8c41b307eef140abb98182e23b8dcf1d', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('69a2eede498544c5aec3db34c6178fa3', 'aef2878e79a248909e4d9b42be0de131', '3b168c360b0d48eaa21d2a5cba7ac717', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('6e32d028bf02414cb804cf6ed35fcbc3', 'f3470e89a43d46018a6b82e3b50f692a', 'db1785bfcbf543ea9f27b255ea54971f', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('74c7ccbc4c6d442e942890784d2c4f1d', 'f3470e89a43d46018a6b82e3b50f692a', '8bc163d254fd4711a4ac8532f183492f', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('92ed929ae7b546e7955d4cec21e321af', 'f3470e89a43d46018a6b82e3b50f692a', 'a188d76e264244238c35616a179b087f', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('a647399cf7dd4941a5bc7e6a4d551bb8', 'f3470e89a43d46018a6b82e3b50f692a', 'ff1b8bcc23b54658a794f37a4a154170', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('b5ff0b4aa417495180e2d87205d1c2be', 'f3470e89a43d46018a6b82e3b50f692a', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('dcd54bcac4e34d91af381d83be82ea98', 'f3470e89a43d46018a6b82e3b50f692a', '3353c636def0483192bac66ce5401349', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('df00c436d4ff47388ce2529b7c80889f', 'f3470e89a43d46018a6b82e3b50f692a', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('e3a322f5d726468eaa1779d37186e691', 'aef2878e79a248909e4d9b42be0de131', 'ff1b8bcc23b54658a794f37a4a154170', '2020-07-04 14:30:47', '2020-07-04 14:30:47');
INSERT INTO `admin_role_privilege` VALUES ('eef09e970aa14c0a9f20460e112fd319', 'f3470e89a43d46018a6b82e3b50f692a', '570ea0f82f8c40158065183f255b52cd', '2020-07-04 14:30:27', '2020-07-04 14:30:27');
INSERT INTO `admin_role_privilege` VALUES ('f36265b7022e4792b942af65c7abb244', 'f3470e89a43d46018a6b82e3b50f692a', 'b2a187f4306543ad8c6d014211f66cd9', '2020-07-04 14:30:27', '2020-07-04 14:30:27');

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '唯一标识',
  `username` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户密码',
  `role_uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次信息更改时间',
  PRIMARY KEY (`uk`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('ca501d32c01e42ba8e0b6524e169f73a', 'admin', 'dc483e80a7a0bd9ef71d8cf973673924', 'faee5041c0a64adb8f388e1a2e1af082', '2020-06-27 11:25:21', '2020-07-03 15:57:27');
INSERT INTO `admin_user` VALUES ('faa8d9ee68504dbe90e0c35e8788805c', 'glory', 'dc483e80a7a0bd9ef71d8cf973673924', '6b03414592574b019fd1c0392ba9ffbf', '2020-07-03 17:23:56', '2020-07-03 17:23:56');

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `uk` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '唯一标识',
  `config_area` int(11) NOT NULL COMMENT '配置所属区域',
  `config_group` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '组',
  `config_key` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT 'key',
  `config_value` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '值',
  `config_desc` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`uk`),
  UNIQUE KEY `uk_001` (`config_area`,`config_group`,`config_key`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('990620aa62db4bcfae78e85ace22a337', '1', 'system_info', 'logo', '/favicon.ico', '平台LOGO', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
INSERT INTO `system_config` VALUES ('af9b6f1bd58e4196aebcda3a86fe0c7f', '1', 'system_info', 'copyright', 'Copyright 2020 © XXX.CN .Inc', '平台版权', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
INSERT INTO `system_config` VALUES ('d0aa986fabbf492284555271c9d28343', '1', 'system_info', 'platformName', 'Glory  后台操作系统', '平台名称', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
