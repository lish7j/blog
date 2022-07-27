/*
Navicat MariaDB Data Transfer
Source Server         : MariaDB
Source Server Version : 100210
Source Host           : localhost:3366
Source Database       : blog
Target Server Type    : MariaDB
Target Server Version : 100210
File Encoding         : 65001
Date: 2018-01-29 18:39:05
*/

-- 取消外键约束
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `authority` VALUES ('2', 'ROLE_USER');

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `comment_size` int(11) DEFAULT NULL,
                        `content` longtext NOT NULL,
                        `create_time` datetime NOT NULL,
                        `update_time` datetime NOT NULL,
                        `html_content` longtext NOT NULL,
                        `read_size` int(11) DEFAULT NULL,
                        `summary` varchar(300) NOT NULL,
                        `tags` varchar(100) DEFAULT NULL,
                        `title` varchar(50) NOT NULL,
                        `vote_size` int(11) DEFAULT NULL,
                        `catalog_id` bigint(20) DEFAULT NULL,
                        `user_id` bigint(20) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `catalog_id_index` (`catalog_id`),
                        KEY `user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------


-- ----------------------------
-- Table structure for catalog
-- ----------------------------
DROP TABLE IF EXISTS `catalog`;
CREATE TABLE `catalog` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `name` varchar(30) NOT NULL,
                           `user_id` bigint(20) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of catalog
-- ----------------------------


-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `content` varchar(500) NOT NULL,
                           `create_time` datetime NOT NULL,
                           `user_id` bigint(20) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `avatar` varchar(200) DEFAULT NULL,
                        `email` varchar(50) NOT NULL,
                        `name` varchar(20) NOT NULL,
                        `password` varchar(100) NOT NULL,
                        `username` varchar(20) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username_index` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, 'b2stry@163.com', '夏浅安', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'admin');
INSERT INTO `user` VALUES ('2', null, 'awesome@shallowan.cn', 'Shallow An', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'shallowan');


-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `create_time` datetime NOT NULL,
                        `user_id` bigint(20) DEFAULT NULL,
                        `blog_id` bigint(20),
                        PRIMARY KEY (`id`),
                        KEY `user_id_index` (`user_id`),
                        KEY `blog_id_index` (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vote
-- ----------------------------

-- 评论点赞表
DROP TABLE IF EXISTS `comment_vote`;
CREATE TABLE `comment_vote` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `create_time` datetime NOT NULL,
                        `user_id` bigint(20) DEFAULT NULL,
                        `comment_id` bigint(20),
                        PRIMARY KEY (`id`),
                        KEY `user_id_index` (`user_id`),
                        KEY `comment_id_index` (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `name` varchar(30) NOT NULL,
                                `count` bigint(20) NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


-- 关注实体表
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `follow_id` bigint(20) DEFAULT NULL,
                        `followed_id` bigint(20),
                        PRIMARY KEY (`id`),
                        KEY `follow_id_index` (`follow_id`),
                        KEY `followed_id_index` (`followed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


-- 外键约束
SET FOREIGN_KEY_CHECKS=1;