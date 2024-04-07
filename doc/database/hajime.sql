/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36-0ubuntu0.20.04.1)
 Source Host           : localhost:3306
 Source Schema         : hajime

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36-0ubuntu0.20.04.1)
 File Encoding         : 65001

 Date: 24/03/2024 13:09:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for indexed
-- ----------------------------
DROP TABLE IF EXISTS `indexed`;
CREATE TABLE `indexed`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'name',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'desc',
  `enable` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '0 disable 1 enable',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'CreateTime',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
  `is_delete` int NULL DEFAULT 0 COMMENT '1 del 0 normal',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of indexed
-- ----------------------------
INSERT INTO `indexed` VALUES (1, 'solana', 'About solana chain.', '1', '2024-03-23 13:28:09', '2024-03-23 13:28:09', 0);
INSERT INTO `indexed` VALUES (2, 'jokes', 'Let\'s laugth.', '1', '2024-03-23 13:37:36', '2024-03-23 13:37:36', 0);
INSERT INTO `indexed` VALUES (3, 'biography', 'Celebrity biographies.', '1', '2024-03-23 13:30:12', '2024-03-23 13:30:12', 0);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Log Type',
  `content` varchar(1280) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Log Content',
  `op` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Operators',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 306 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for my_device
-- ----------------------------
DROP TABLE IF EXISTS `my_device`;
CREATE TABLE `my_device`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `uid` bigint NULL DEFAULT NULL COMMENT 'User ID',
  `nid` bigint NULL DEFAULT NULL COMMENT 'Device ID',
  `imei` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Device Identifier',
  `create_time` timestamp NULL DEFAULT NULL COMMENT 'CreateTime',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'name',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'desc',
  `healthy` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT 'healthy',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'indexed ID',
  `device_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0 node2,1 center server, 2 p2p tracker',
  `imei` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Device Identifier',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip address',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'CreateTime',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
  `detail` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'detail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for node_send
-- ----------------------------
DROP TABLE IF EXISTS `node_send`;
CREATE TABLE `node_send`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `nid` bigint NULL DEFAULT NULL COMMENT 'Device ID',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Deliver content',
  `is_delete` int UNSIGNED NULL DEFAULT 0 COMMENT '1 del 0 normal',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'password',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'roles',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT 'status',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;



-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `kind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Event Type',
  `node_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Device ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Event Content',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'CreateTime',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;


CREATE TABLE `ip_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip address',
  `status` int NULL DEFAULT 0 COMMENT 'spare',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `ip_config` (`id`, `ip`, `status`) VALUES (1, '10.10.10.10', 0);
INSERT INTO `ip_config` (`id`, `ip`, `status`) VALUES (2, '10.10.10.11', 0);
INSERT INTO `ip_config` (`id`, `ip`, `status`) VALUES (3, '10.10.10.12', 0);
INSERT INTO `ip_config` (`id`, `ip`, `status`) VALUES (4, '10.10.10.13', 0);
INSERT INTO `ip_config` (`id`, `ip`, `status`) VALUES (5, '10.10.10.14', 0);

SET FOREIGN_KEY_CHECKS = 1;


