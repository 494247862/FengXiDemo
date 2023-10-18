/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : localhost:3306
 Source Schema         : fengxi_demo

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 18/10/2023 18:16:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for deyi_button
-- ----------------------------
DROP TABLE IF EXISTS `deyi_button`;
CREATE TABLE `deyi_button`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `button_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `button_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_button
-- ----------------------------
INSERT INTO `deyi_button` VALUES (1, '2023-01-30 17:13:18', 1, b'0', '2023-02-06 14:08:27', 1, 'testButton1', '测试按钮', 3);
INSERT INTO `deyi_button` VALUES (2, '2023-01-31 11:23:38', 1, b'0', NULL, NULL, 'testButton2', '测试按钮2', 3);
INSERT INTO `deyi_button` VALUES (5, '2023-02-06 14:09:16', 1, b'0', '2023-06-12 14:50:27', 1, 'add', '新增', 11);
INSERT INTO `deyi_button` VALUES (6, '2023-02-06 14:10:49', 1, b'0', '2023-06-12 14:50:30', 1, 'update', '编辑', 11);
INSERT INTO `deyi_button` VALUES (7, '2023-02-06 14:11:09', 1, b'0', '2023-06-12 14:50:31', 1, 'delete', '删除', 11);
INSERT INTO `deyi_button` VALUES (16, '2023-04-27 17:14:45', 1, b'0', NULL, NULL, 'addUser', '新增用户', 14);
INSERT INTO `deyi_button` VALUES (17, '2023-04-27 17:15:12', 1, b'0', NULL, NULL, 'update', '编辑', 14);
INSERT INTO `deyi_button` VALUES (18, '2023-04-27 17:15:52', 1, b'0', '2023-04-27 17:16:27', 1, 'resetPassword', '重置密码', 14);
INSERT INTO `deyi_button` VALUES (19, '2023-04-27 17:16:17', 1, b'0', NULL, NULL, 'delete', '删除', 14);
INSERT INTO `deyi_button` VALUES (20, '2023-04-27 17:24:04', 1, b'0', NULL, NULL, 'add', '新增角色', 13);
INSERT INTO `deyi_button` VALUES (21, '2023-04-27 17:24:14', 1, b'0', NULL, NULL, 'update', '编辑', 13);
INSERT INTO `deyi_button` VALUES (22, '2023-04-27 17:24:23', 1, b'0', NULL, NULL, 'delete', '删除', 13);
INSERT INTO `deyi_button` VALUES (23, '2023-04-27 17:34:28', 1, b'0', '2023-06-12 14:50:33', 1, 'addChild', '新增子菜单', 11);
INSERT INTO `deyi_button` VALUES (24, '2023-04-27 17:34:49', 1, b'0', '2023-06-12 14:50:34', 1, 'buttonSetting', '按钮配置', 11);

-- ----------------------------
-- Table structure for deyi_department
-- ----------------------------
DROP TABLE IF EXISTS `deyi_department`;
CREATE TABLE `deyi_department`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `department_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_department
-- ----------------------------
INSERT INTO `deyi_department` VALUES (1, '2023-06-13 13:53:09', 1, b'0', '2023-06-13 14:38:16', 1, 'test1111', '测试', 1, NULL);
INSERT INTO `deyi_department` VALUES (2, '2023-06-13 14:09:37', 1, b'0', '2023-06-13 14:39:00', 1, 'test1', '测试1··', 2, 1);
INSERT INTO `deyi_department` VALUES (3, '2023-07-14 17:21:44', 1, b'0', NULL, NULL, 'asd', '是的', 1, NULL);
INSERT INTO `deyi_department` VALUES (4, '2023-07-14 17:21:54', 1, b'0', NULL, NULL, 'asd1', '是的是的', 2, 3);

-- ----------------------------
-- Table structure for deyi_file
-- ----------------------------
DROP TABLE IF EXISTS `deyi_file`;
CREATE TABLE `deyi_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_old_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_file
-- ----------------------------

-- ----------------------------
-- Table structure for deyi_log
-- ----------------------------
DROP TABLE IF EXISTS `deyi_log`;
CREATE TABLE `deyi_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `exception_msg` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `is_success` bit(1) NULL DEFAULT NULL,
  `request_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `request_response` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1368 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_log
-- ----------------------------
INSERT INTO `deyi_log` VALUES (1342, '2023-10-18 18:09:43', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', '{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"password\":\"$2a$10$IkUqq/FDN3A3hE0J24.8gengWg.VWZencdLGaeYWgXrci7UxuMLkS\",\"phone\":\"110\",\"roleIds\":\"1\",\"sex\":\"男\",\"updateTime\":\"2023-07-14T18:00:13\",\"updateUserId\":1}', 'http://localhost:8080/user/getCurrentUser', '获取当前用户信息');
INSERT INTO `deyi_log` VALUES (1343, '2023-10-18 18:09:43', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/button/getButtonByCurrent', '获取当前用户的权限按钮');
INSERT INTO `deyi_log` VALUES (1344, '2023-10-18 18:09:43', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuTreesByCurrent', '获取当前用户的权限菜单树');
INSERT INTO `deyi_log` VALUES (1345, '2023-10-18 18:09:43', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuByCurrent', NULL);
INSERT INTO `deyi_log` VALUES (1346, '2023-10-18 18:09:47', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{}]', NULL, 'http://localhost:8080/menu/QueryMenuData', '查询菜单数据');
INSERT INTO `deyi_log` VALUES (1347, '2023-10-18 18:09:48', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"account\":\"\",\"nickName\":\"\",\"page\":0,\"pageSize\":10,\"phone\":\"\",\"roleIds\":[],\"sex\":\"\"}]', '{\"count\":1,\"data\":[{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"password\":\"$2a$10$IkUqq/FDN3A3hE0J24.8gengWg.VWZencdLGaeYWgXrci7UxuMLkS\",\"phone\":\"110\",\"roleIds\":\"1\",\"roleList\":[\"管理员\"],\"sex\":\"男\",\"updateTime\":\"2023-07-14T18:00:13\",\"updateUserId\":1}]}', 'http://localhost:8080/user/queryUserData', '查询用户数据');
INSERT INTO `deyi_log` VALUES (1348, '2023-10-18 18:09:52', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', '[{\"children\":[{\"children\":[],\"departmentCode\":\"test1\",\"departmentName\":\"测试1··\",\"id\":2,\"level\":2,\"parentId\":1}],\"departmentCode\":\"test1111\",\"departmentName\":\"测试\",\"id\":1,\"level\":1},{\"children\":[{\"children\":[],\"departmentCode\":\"asd1\",\"departmentName\":\"是的是的\",\"id\":4,\"level\":2,\"parentId\":3}],\"departmentCode\":\"asd\",\"departmentName\":\"是的\",\"id\":3,\"level\":1}]', 'http://localhost:8080/department/getDepartmentTree', '获取部门树');
INSERT INTO `deyi_log` VALUES (1349, '2023-10-18 18:10:37', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"page\":0,\"pageSize\":10}]', '{\"count\":1,\"data\":[{\"buttonIds\":\"5,6,7,23,24,16,17,18,19,20,21,22\",\"createTime\":\"2023-01-29T09:59:23\",\"createUserId\":1,\"id\":1,\"isDeleted\":false,\"menuIds\":\"31,10,11,13,14,27,29,30\",\"roleCode\":\"admin\",\"roleName\":\"管理员\",\"updateTime\":\"2023-09-15T14:01:52\",\"updateUserId\":1}]}', 'http://localhost:8080/role/QueryRolePage', '分页获取角色数据');
INSERT INTO `deyi_log` VALUES (1350, '2023-10-18 18:10:37', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuTrees', '获取菜单树');
INSERT INTO `deyi_log` VALUES (1351, '2023-10-18 18:10:37', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/button/getMenuButtonTree', '获取菜单按钮结构');
INSERT INTO `deyi_log` VALUES (1352, '2023-10-18 18:14:39', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', '{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"password\":\"$2a$10$IkUqq/FDN3A3hE0J24.8gengWg.VWZencdLGaeYWgXrci7UxuMLkS\",\"phone\":\"110\",\"roleIds\":\"1\",\"sex\":\"男\",\"updateTime\":\"2023-07-14T18:00:13\",\"updateUserId\":1}', 'http://localhost:8080/user/getCurrentUser', '获取当前用户信息');
INSERT INTO `deyi_log` VALUES (1353, '2023-10-18 18:14:39', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/button/getButtonByCurrent', '获取当前用户的权限按钮');
INSERT INTO `deyi_log` VALUES (1354, '2023-10-18 18:14:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuTreesByCurrent', '获取当前用户的权限菜单树');
INSERT INTO `deyi_log` VALUES (1355, '2023-10-18 18:14:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuByCurrent', NULL);
INSERT INTO `deyi_log` VALUES (1356, '2023-10-18 18:14:46', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"page\":0,\"pageSize\":10}]', '{\"count\":1,\"data\":[{\"buttonIds\":\"5,6,7,23,24,16,17,18,19,20,21,22\",\"createTime\":\"2023-01-29T09:59:23\",\"createUserId\":1,\"id\":1,\"isDeleted\":false,\"menuIds\":\"31,10,11,13,14,27,29,30\",\"roleCode\":\"admin\",\"roleName\":\"管理员\",\"updateTime\":\"2023-09-15T14:01:52\",\"updateUserId\":1}]}', 'http://localhost:8080/role/QueryRolePage', '分页获取角色数据');
INSERT INTO `deyi_log` VALUES (1357, '2023-10-18 18:14:46', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuTrees', '获取菜单树');
INSERT INTO `deyi_log` VALUES (1358, '2023-10-18 18:14:46', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/button/getMenuButtonTree', '获取菜单按钮结构');
INSERT INTO `deyi_log` VALUES (1359, '2023-10-18 18:14:47', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"account\":\"\",\"nickName\":\"\",\"page\":0,\"pageSize\":10,\"phone\":\"\",\"roleIds\":[],\"sex\":\"\"}]', '{\"count\":1,\"data\":[{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"password\":\"$2a$10$IkUqq/FDN3A3hE0J24.8gengWg.VWZencdLGaeYWgXrci7UxuMLkS\",\"phone\":\"110\",\"roleIds\":\"1\",\"roleList\":[\"管理员\"],\"sex\":\"男\",\"updateTime\":\"2023-07-14T18:00:13\",\"updateUserId\":1}]}', 'http://localhost:8080/user/queryUserData', '查询用户数据');
INSERT INTO `deyi_log` VALUES (1360, '2023-10-18 18:15:29', 1, b'0', NULL, NULL, '用户名或密码错误', b'0', '0:0:0:0:0:0:0:1', '[{\"newPassword\":\"admin\",\"oldPassword\":\"Deyi@2023\"}]', NULL, 'http://localhost:8080/user/updatePassword', '修改密码');
INSERT INTO `deyi_log` VALUES (1361, '2023-10-18 18:15:35', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"newPassword\":\"admin\",\"oldPassword\":\"deyi@2023\"}]', '\"Success\"', 'http://localhost:8080/user/updatePassword', '修改密码');
INSERT INTO `deyi_log` VALUES (1362, '2023-10-18 18:15:36', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', '\"Success\"', 'http://localhost:8080/user/logout', '登出');
INSERT INTO `deyi_log` VALUES (1363, '2023-10-18 18:15:40', NULL, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '[{\"account\":\"admin\",\"password\":\"admin\"}]', '{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"phone\":\"110\",\"roleIds\":\"1\",\"sex\":\"男\",\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE2OTc2NTI5Mzl9.kDZWkysCV_SJh5npeg9W8sPZ2OOV9v9JZTBpE4s4dcg\",\"updateTime\":\"2023-10-18T18:15:35\",\"updateUserId\":1}', 'http://localhost:8080/user/login', '登录');
INSERT INTO `deyi_log` VALUES (1364, '2023-10-18 18:15:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', '{\"account\":\"admin\",\"createTime\":\"2023-01-28T15:28:53\",\"id\":1,\"isDeleted\":false,\"nickName\":\"管理员\",\"password\":\"$2a$10$v1UXTaVUguQGo3MGTqWSzeoWGsXhEpVc7QGG0bigPTHt7Y1FzD6.y\",\"phone\":\"110\",\"roleIds\":\"1\",\"sex\":\"男\",\"updateTime\":\"2023-10-18T18:15:35\",\"updateUserId\":1}', 'http://localhost:8080/user/getCurrentUser', '获取当前用户信息');
INSERT INTO `deyi_log` VALUES (1365, '2023-10-18 18:15:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/button/getButtonByCurrent', '获取当前用户的权限按钮');
INSERT INTO `deyi_log` VALUES (1366, '2023-10-18 18:15:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuTreesByCurrent', '获取当前用户的权限菜单树');
INSERT INTO `deyi_log` VALUES (1367, '2023-10-18 18:15:40', 1, b'0', NULL, NULL, NULL, b'1', '0:0:0:0:0:0:0:1', '', NULL, 'http://localhost:8080/menu/getMenuByCurrent', NULL);

-- ----------------------------
-- Table structure for deyi_menu
-- ----------------------------
DROP TABLE IF EXISTS `deyi_menu`;
CREATE TABLE `deyi_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  `menu_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `number` int NULL DEFAULT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_menu
-- ----------------------------
INSERT INTO `deyi_menu` VALUES (10, '2023-02-04 15:12:53', 1, b'0', '2023-03-31 14:38:38', 1, 'el-icon-s-custom', 1, 'AuthManage', '权限管理', '/', 10, NULL);
INSERT INTO `deyi_menu` VALUES (11, '2023-02-04 15:13:54', 1, b'0', NULL, NULL, NULL, 2, 'MenuManage', '菜单管理', '/MenuManage', NULL, 10);
INSERT INTO `deyi_menu` VALUES (13, '2023-02-04 15:24:49', 1, b'0', NULL, NULL, NULL, 2, 'RoleManage', '角色管理', '/RoleManage', NULL, 10);
INSERT INTO `deyi_menu` VALUES (14, '2023-02-04 16:00:11', 1, b'0', '2023-02-06 15:28:54', 1, '', 2, 'UserManage', '用户管理', '/UserManage', NULL, 10);
INSERT INTO `deyi_menu` VALUES (27, '2023-06-12 17:41:45', 1, b'0', '2023-06-12 17:41:53', 1, NULL, 2, 'DepartmentManage', '部门管理', '/DepartmentManage', NULL, 10);
INSERT INTO `deyi_menu` VALUES (29, '2023-08-18 17:08:57', 1, b'0', '2023-08-18 17:09:11', 1, NULL, 1, 'ViewExample', '页面样例', '/', 20, NULL);
INSERT INTO `deyi_menu` VALUES (30, '2023-08-18 17:15:55', 1, b'0', NULL, NULL, NULL, 2, 'ExcelExample', '报表例子', '/ExcelExample', 10, 29);
INSERT INTO `deyi_menu` VALUES (31, '2023-09-15 14:01:42', 1, b'0', '2023-09-19 15:29:20', 1, 'el-icon-s-home', 1, 'First', '首页', '/First', 1, NULL);

-- ----------------------------
-- Table structure for deyi_role
-- ----------------------------
DROP TABLE IF EXISTS `deyi_role`;
CREATE TABLE `deyi_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `button_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_role
-- ----------------------------
INSERT INTO `deyi_role` VALUES (1, '2023-01-29 09:59:23', 1, b'0', '2023-09-15 14:01:52', 1, '5,6,7,23,24,16,17,18,19,20,21,22', '31,10,11,13,14,27,29,30', 'admin', '管理员');

-- ----------------------------
-- Table structure for deyi_user
-- ----------------------------
DROP TABLE IF EXISTS `deyi_user`;
CREATE TABLE `deyi_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `head_picture_id` bigint NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of deyi_user
-- ----------------------------
INSERT INTO `deyi_user` VALUES (1, '2023-01-28 15:28:53', NULL, b'0', '2023-10-18 18:15:35', 1, 'admin', NULL, NULL, '管理员', '$2a$10$v1UXTaVUguQGo3MGTqWSzeoWGsXhEpVc7QGG0bigPTHt7Y1FzD6.y', '110', '1', '男');
INSERT INTO `deyi_user` VALUES (20, '2023-07-14 17:42:13', 1, b'1', '2023-07-14 17:42:18', 1, '', NULL, NULL, '', '$2a$10$OqEdRD7FQ7WWyJ59Y7l0quK66VZgT9cUaLbmdm/AHav8uSlM7Jx4i', '', NULL, '');
INSERT INTO `deyi_user` VALUES (21, '2023-07-14 17:46:53', 1, b'1', '2023-07-14 17:46:55', 1, '', NULL, NULL, '', '$2a$10$Bpno8d/AF81hn6yj3K09a.ZUnRQA6EdrSX9mleHTwUxfNUFNQhKK6', '', NULL, '');
INSERT INTO `deyi_user` VALUES (22, '2023-07-14 17:46:59', 1, b'1', '2023-07-14 17:47:01', 1, '', NULL, NULL, '', '$2a$10$g0K5vRGO0kJtcrzanws8v.HtirkhflIONzAnQDScIMLTefUevFlje', '', NULL, '');
INSERT INTO `deyi_user` VALUES (23, '2023-07-14 17:55:42', 1, b'1', '2023-07-14 17:55:45', 1, '', NULL, NULL, '', '$2a$10$HN.gSRW5JrPwhzYqhV4Dyen5r.CcSRR1/g25rb2Sh/RbJUkqH/Nyu', '', NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
