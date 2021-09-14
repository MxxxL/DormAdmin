/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : dormadmin

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/06/2021 19:49:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `building_type` tinyint unsigned NOT NULL COMMENT '宿舍楼类型：1男生宿舍楼 ，0女生宿舍楼',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 275 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES (260, '清枫阁', 1, 'M');
INSERT INTO `building` VALUES (261, '红棉楼', 1, 'R');
INSERT INTO `building` VALUES (262, '黄槐楼', 1, 'Y');
INSERT INTO `building` VALUES (263, '绿杨楼', 1, 'G\\P');
INSERT INTO `building` VALUES (264, '书新楼', 1, 'Q');
INSERT INTO `building` VALUES (265, '碧波楼', 1, 'B');
INSERT INTO `building` VALUES (266, '紫薇楼', 0, 'J');
INSERT INTO `building` VALUES (267, '金桂园', 0, 'K');
INSERT INTO `building` VALUES (268, '橙萱居', 0, 'T');
INSERT INTO `building` VALUES (269, '蓝楹楼', 0, 'F');
INSERT INTO `building` VALUES (270, '书院楼', 0, 'V');

-- ----------------------------
-- Table structure for dorm
-- ----------------------------
DROP TABLE IF EXISTS `dorm`;
CREATE TABLE `dorm`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '宿舍名',
  `building_id` int(0) NULL DEFAULT NULL COMMENT '宿舍楼id',
  `max_num` int(0) NULL DEFAULT 4 COMMENT '最大人数',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dorm_ibfk_1`(`building_id`) USING BTREE,
  CONSTRAINT `dorm_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 267 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dorm
-- ----------------------------
INSERT INTO `dorm` VALUES (260, 'M2317', 260, 4, NULL);
INSERT INTO `dorm` VALUES (261, 'G239', 263, 4, NULL);
INSERT INTO `dorm` VALUES (263, 'K212', 267, 4, NULL);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `iconCls` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `keepAlive` tinyint(1) NULL DEFAULT NULL,
  `requireAuth` tinyint(1) NULL DEFAULT NULL,
  `parentId` int(0) NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parentId`(`parentId`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/', NULL, NULL, '所有', NULL, NULL, NULL, NULL, 1);
INSERT INTO `menu` VALUES (2, '/', '/home', 'Home', '学生管理', 'fa fa-graduation-cap', NULL, 1, 1, 1);
INSERT INTO `menu` VALUES (3, '/', '/home', 'Home', '楼层管理', 'fa fa-building', NULL, 1, 1, 1);
INSERT INTO `menu` VALUES (4, '/', '/home', 'Home', '宿舍管理', 'fa fa-hotel', NULL, 1, 1, 1);
INSERT INTO `menu` VALUES (5, '/', '/home', 'Home', '用户管理', 'fa fa-users', NULL, 1, 1, 1);
INSERT INTO `menu` VALUES (6, '/', '/home', 'Home', '系统管理', 'fa fa-wrench', NULL, 1, 1, 1);
INSERT INTO `menu` VALUES (7, '/api/student/**', '/student/profile', 'StudentProfile', '学生资料', NULL, NULL, 1, 2, 1);
INSERT INTO `menu` VALUES (8, '/api/building/**', '/building/info', 'BuildingInfo', '楼层信息', NULL, NULL, 1, 3, 1);
INSERT INTO `menu` VALUES (9, '/api/dorm/**', '/dorm/details', 'DormDetails', '宿舍详情', NULL, NULL, 1, 4, 1);
INSERT INTO `menu` VALUES (10, '/api/user/**', '/user/info', 'UserInfo', '用户信息', NULL, NULL, 1, 5, 1);
INSERT INTO `menu` VALUES (11, '/api/system/**', '/system/profile', 'SystemProfile', '系统资料', NULL, NULL, 1, 6, 1);

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `mid` int(0) NULL DEFAULT NULL,
  `rid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mid`(`mid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (283, 7, 1);
INSERT INTO `menu_role` VALUES (284, 8, 1);
INSERT INTO `menu_role` VALUES (285, 9, 1);
INSERT INTO `menu_role` VALUES (286, 10, 1);
INSERT INTO `menu_role` VALUES (287, 11, 1);
INSERT INTO `menu_role` VALUES (288, 7, 2);
INSERT INTO `menu_role` VALUES (289, 8, 2);
INSERT INTO `menu_role` VALUES (290, 9, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nameZh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_admin', '超级管理员');
INSERT INTO `role` VALUES (2, 'ROLE_manager', '管理员');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `student_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` tinyint(1) NOT NULL DEFAULT 1,
  `dorm_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '1840706342', '钟开瑜', 1, 260);
INSERT INTO `student` VALUES (2, '2040706098', '张三丰', 1, 260);
INSERT INTO `student` VALUES (3, '2040706099', '卢西奥', 1, 260);
INSERT INTO `student` VALUES (4, '2040706100', '李华', 1, 260);
INSERT INTO `student` VALUES (5, '2040706101', '林悦', 0, 263);
INSERT INTO `student` VALUES (6, '2040706102', '黄晓华', 0, 263);
INSERT INTO `student` VALUES (7, '2040706103', '王晓红', 0, 263);
INSERT INTO `student` VALUES (8, '2040706104', '韩梅梅', 0, 263);
INSERT INTO `student` VALUES (9, '2040706105', '黄飞宇', 1, 261);
INSERT INTO `student` VALUES (10, '2040706106', '王五', 1, 261);
INSERT INTO `student` VALUES (11, '2040706107', '赵喜宇', 1, 261);
INSERT INTO `student` VALUES (12, '2040706108', '黄叶华', 1, 261);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `enabled` tinyint(0) NULL DEFAULT 1,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (13, '超级管理员', '13511112222', 1, 'admin', '$2a$10$lQkDrNOYHqcelxfjxkD0MukyLeC54IPnomtvwN1EnJL1ts/xlA/8O', '超级管理员');
INSERT INTO `user` VALUES (14, '管理员', '13522221111', 1, 'mxxxl', '$2a$10$H8U41CIf4IV.YqzcGXLPFO/jj9FEUlHiAHcoy3EGy.EHkjZZpPbu2', '管理员');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NULL DEFAULT NULL,
  `rid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  INDEX `user_role_ibfk_1`(`uid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (75, 13, 1);
INSERT INTO `user_role` VALUES (76, 14, 2);

SET FOREIGN_KEY_CHECKS = 1;
