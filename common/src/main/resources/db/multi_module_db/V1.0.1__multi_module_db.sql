/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : multi_module_db

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-02-26 11:13:14
*/

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `admin_privilege`;
CREATE TABLE `admin_privilege` (
  `uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限唯一标识',
  `privilege_type` int NOT NULL COMMENT '权限类型 1：目录权限；2：页面权限；3：按钮权限',
  `privilege_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `privilege_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限路径',
  `sort` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '排序',
  `parent_uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '父标签标识',
  `create_time` datetime NOT NULL COMMENT '插入时间',
  `update_time` datetime NOT NULL COMMENT '最后更改时间',
  PRIMARY KEY (`uk`,`privilege_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_privilege
-- ----------------------------
INSERT INTO `admin_privilege` VALUES ('064da5c7c57547128d77c3d17e8ff728', '3', '修改系统配置', 'SystemController.updateSystemConfig', '11-06-02', 'e82d39224c5347b69aca69e88ab0eb21', '2020-07-07 11:08:25', '2020-07-07 11:08:28');
INSERT INTO `admin_privilege` VALUES ('08417295ecfe436694a4acc724bd4210', '3', '查询用户', 'UserController.getPageAdminInfo', '11-01-03', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-02 10:19:18', '2020-07-02 10:19:21');
INSERT INTO `admin_privilege` VALUES ('194d4389af094da993e18828ed090283', '3', '绑定Google两步验证', 'UserController.getGoogleKey,UserController.bindGoogleKey,SystemController.getGoogleAuthenticationDownload,UserController.loginWithGooleAuthentication', '01-01-03', '6470dc3dcc0c4f1a9022e223ec41c749', '2020-07-08 20:58:29', '2020-07-08 20:58:34');
INSERT INTO `admin_privilege` VALUES ('2263d6407fce41be8db428f31ca47d3a', '3', '查看操作日志', 'AdminOperateLogController.getPageOperateLog', '11-05-01', '7207be3756b84502be62817adb7e516e', '2020-07-03 22:13:25', '2020-07-03 22:13:28');
INSERT INTO `admin_privilege` VALUES ('226b8ab287ac479780b44f4ae1981848', '2', '角色管理', '/amdin-manage/role-manage', '11-02', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-26 18:41:29', '2020-06-26 18:41:26');
INSERT INTO `admin_privilege` VALUES ('274ddc1cfe824c05b9e740581213bd76', '3', '查看系统配置', 'SystemController.getPageConfig', '11-06-01', 'e82d39224c5347b69aca69e88ab0eb21', '2020-07-07 10:51:25', '2020-07-07 10:51:28');
INSERT INTO `admin_privilege` VALUES ('32f09a0e442d4f54a8270e762a809d9a', '3', '修改个人密码', 'UserController.updateSelfPassword', '01-01-02', '6470dc3dcc0c4f1a9022e223ec41c749', '2020-07-07 23:02:40', '2020-07-07 23:02:43');
INSERT INTO `admin_privilege` VALUES ('3353c636def0483192bac66ce5401349', '3', '用户登出', 'UserController.adminLogout', '11-01-02', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-06-30 21:50:33', '2020-06-30 21:50:36');
INSERT INTO `admin_privilege` VALUES ('34b40d3ef9334b50bde0cc7129189e86', '3', '修改用户角色', 'UserController.updateAdminRole', '11-01-04', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 08:47:37', '2020-07-03 08:47:39');
INSERT INTO `admin_privilege` VALUES ('3b168c360b0d48eaa21d2a5cba7ac717', '3', '查看权限', 'PrivilegeController.getAdminPermissionTree', '11-03-01', '8c41b307eef140abb98182e23b8dcf1d', '2020-06-30 23:29:00', '2020-06-30 23:29:03');
INSERT INTO `admin_privilege` VALUES ('3c9ef7b90e5a43cc863941f0f2368021', '1', '后台人员管理', '/admin-manage', '11', '111222333444aaabbbcccddd99988877', '2020-06-26 18:39:26', '2020-06-26 18:39:29');
INSERT INTO `admin_privilege` VALUES ('53ad98f0ed58479ba19be620c64ac580', '3', '查看角色', 'RoleController.getRolesTree,RoleController.getAllSelfSubRole,RoleController.getAllSubRole', '11-02-01', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:42:22', '2020-06-26 18:42:19');
INSERT INTO `admin_privilege` VALUES ('570ea0f82f8c40158065183f255b52cd', '3', '删除用户', 'UserController.delAdminInfo', '11-01-06', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 17:06:08', '2020-07-03 17:06:10');
INSERT INTO `admin_privilege` VALUES ('57747d216fee41eaa677602a9515a525', '1', '个人主页', '/admin-info', '01', '111222333444aaabbbcccddd99988877', '2020-07-07 18:35:54', '2020-07-07 18:35:56');
INSERT INTO `admin_privilege` VALUES ('6470dc3dcc0c4f1a9022e223ec41c749', '2', '个人信息', '/admin-info/admin-self-info', '01-01', '57747d216fee41eaa677602a9515a525', '2020-07-07 18:40:36', '2020-07-07 18:40:38');
INSERT INTO `admin_privilege` VALUES ('66f01bad3c3542ff84d381ca589edad5', '3', '修改用户登陆密码', 'UserController.updateAdminPassword', '11-01-07', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-10 19:49:10', '2020-07-10 19:49:14');
INSERT INTO `admin_privilege` VALUES ('70d21e7631c64862b984b40abe41ad42', '2', '登陆日志', '/amdin-manage/admin-login-log', '11-04', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-03 19:32:51', '2020-07-03 19:32:54');
INSERT INTO `admin_privilege` VALUES ('7207be3756b84502be62817adb7e516e', '2', '操作日志', '/amdin-manage/admin-operate-log', '11-05', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-03 22:12:53', '2020-07-03 22:12:55');
INSERT INTO `admin_privilege` VALUES ('7893fd34a1644e6b94cdba9057407446', '3', '查询登陆日志', 'AdminLoginLogController.getPageLoginLog', '11-04-01', '70d21e7631c64862b984b40abe41ad42', '2020-07-03 19:33:58', '2020-07-03 19:34:01');
INSERT INTO `admin_privilege` VALUES ('8bc163d254fd4711a4ac8532f183492f', '3', '添加角色', 'RoleController.addRole', '11-02-02', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:42:42', '2020-06-26 18:42:38');
INSERT INTO `admin_privilege` VALUES ('8c41b307eef140abb98182e23b8dcf1d', '2', '权限管理', '/admin-magage/admin-permission', '11-03', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-30 23:27:13', '2020-06-30 23:27:17');
INSERT INTO `admin_privilege` VALUES ('8f6888649c55447e8db3a6e8fde9e756', '3', '删除Google绑定', 'UserController.deleteAdminGoogleKey', '11-01-09', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-10 23:22:35', '2020-07-10 23:22:37');
INSERT INTO `admin_privilege` VALUES ('a188d76e264244238c35616a179b087f', '3', '用户登陆', 'UserController.adminLogin,UserController.getAdminInfo', '11-01-01', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-06-30 17:03:36', '2020-06-30 17:03:39');
INSERT INTO `admin_privilege` VALUES ('b2a187f4306543ad8c6d014211f66cd9', '3', '删除角色', 'RoleController.deleteRole', '11-02-03', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:43:44', '2020-06-26 18:43:42');
INSERT INTO `admin_privilege` VALUES ('d0d75779f3f848c39683ea5147967050', '3', '修改权限', 'PrivilegeController.updateAdminRolePermissions', '11-03-02', '8c41b307eef140abb98182e23b8dcf1d', '2020-06-30 23:29:43', '2020-06-30 23:29:47');
INSERT INTO `admin_privilege` VALUES ('db1785bfcbf543ea9f27b255ea54971f', '3', '修改角色', 'RoleController.updateRole', '11-02-04', '226b8ab287ac479780b44f4ae1981848', '2020-06-26 18:43:44', '2020-06-26 18:43:42');
INSERT INTO `admin_privilege` VALUES ('e7abd1b0b8234ae5856f17af63b190d8', '3', '禁止或允许用户登陆', 'UserController.forbidAdminLogin', '11-01-08', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-10 22:22:04', '2020-07-10 22:22:06');
INSERT INTO `admin_privilege` VALUES ('e82d39224c5347b69aca69e88ab0eb21', '2', '系统配置', '/amdin-manage/admin-system-config', '11-06', '3c9ef7b90e5a43cc863941f0f2368021', '2020-07-07 10:50:14', '2020-07-07 10:50:17');
INSERT INTO `admin_privilege` VALUES ('e97df9fb710641a49cec2b1dd134ff4f', '3', '操作时间轴', 'UserController.getSelfOperateTimeline', '01-01-01', '6470dc3dcc0c4f1a9022e223ec41c749', '2020-07-07 19:22:32', '2020-07-07 19:22:36');
INSERT INTO `admin_privilege` VALUES ('fa32475acdcf469ea6a58e2dc15ed40d', '2', '用户管理', '/amdin-manage/admin-manage', '11-01', '3c9ef7b90e5a43cc863941f0f2368021', '2020-06-30 17:02:11', '2020-06-30 17:02:16');
INSERT INTO `admin_privilege` VALUES ('ff1b8bcc23b54658a794f37a4a154170', '3', '添加用户', 'UserController.addAdminInfo', '11-01-05', 'fa32475acdcf469ea6a58e2dc15ed40d', '2020-07-03 16:50:11', '2020-07-03 16:50:13');

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'uk',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色描述',
  `parent_uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '父uk',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`uk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('faee5041c0a64adb8f388e1a2e1af082', '系统管理员', '系统管理员，拥有所有权限', '111222333444aaabbbcccddd99988877', '2020-06-25 15:43:31', '2020-07-11 00:11:31');

DROP TABLE IF EXISTS `admin_role_privilege`;
CREATE TABLE `admin_role_privilege` (
  `uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `role_uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色主键',
  `privilege_uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限主键',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`uk`),
  UNIQUE KEY `uk_01` (`role_uk`,`privilege_uk`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role_privilege
-- ----------------------------

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `role_uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色',
  `now_token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `google_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `google_login` int NOT NULL COMMENT '使用google验证器，二步登陆',
  `forbid_login` int NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次信息更改时间',
  PRIMARY KEY (`uk`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('ca501d32c01e42ba8e0b6524e169f73a', 'admin', 'dc483e80a7a0bd9ef71d8cf973673924', 'faee5041c0a64adb8f388e1a2e1af082', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbi11c2VyIiwiYXVkIjoiYWRtaW4td2ViIiwiaXNzIjoid2ViLWFkbWluIiwiZXhwIjoxNTk0NjU3MTEwLCJpYXQiOjE1OTQzOTc5MTAsImFkbWluLXVrIjoiY2E1MDFkMzJjMDFlNDJiYThlMGI2NTI0ZTE2OWY3M2EifQ.U_SxTRxbV7jiJDjqZ0KGvJXLYnXkua_lC0mSMAgFnNg', null, '0', '0', '2020-06-27 11:25:21', '2020-07-11 00:18:31');

DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `uk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `config_area` int NOT NULL COMMENT '配置所属区域',
  `config_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组',
  `config_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'key',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `config_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`uk`),
  UNIQUE KEY `uk_001` (`config_area`,`config_group`,`config_key`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('1ee20f9de6c14a8c98ebbafad5e7689c', '1', 'google_authentication_download', 'android', '{\"name\":\"安卓 下载\",\"url\":\"https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2\"}', 'google验证器下载，安卓地址', '2020-07-09 10:46:01', '2020-07-09 10:46:03');
INSERT INTO `system_config` VALUES ('990620aa62db4bcfae78e85ace22a337', '1', 'system_info', 'logo', '/favicon.ico', '平台LOGO', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
INSERT INTO `system_config` VALUES ('af9b6f1bd58e4196aebcda3a86fe0c7f', '1', 'system_info', 'copyright', 'Copyright 2020 © XXX.CN .Inc', '平台版权', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
INSERT INTO `system_config` VALUES ('d0aa986fabbf492284555271c9d28343', '1', 'system_info', 'platformName', 'Glory  后台操作系统', '平台名称', '2020-06-27 11:21:39', '2020-06-27 11:21:41');
INSERT INTO `system_config` VALUES ('f97f79509c8343999e1951504d6ae7c2', '1', 'google_authentication_download', 'ios', '{\"name\":\"苹果 下载\",\"url\":\"https://apps.apple.com/cn/app/google-authenticator/id388497605\"}', 'google验证器下载，ios地址', '2020-07-09 10:46:50', '2020-07-10 19:50:33');
