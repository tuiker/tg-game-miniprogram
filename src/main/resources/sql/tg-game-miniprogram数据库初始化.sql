
create database `tg-game-miniprogram` character set utf8mb4;


-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`game`;
CREATE TABLE `tg-game-miniprogram`.`game`  (
                                               `id` bigint NOT NULL COMMENT '游戏ID',
                                               `game_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '游戏名称',
                                               `game_category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '游戏类型',
                                               `language_id` int NOT NULL DEFAULT 0 COMMENT '游戏语言ID',
                                               `game_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '游戏LOGO',
                                               `game_main_logo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '游戏主图',
                                               `game_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'APK地址',
                                               `create_id` bigint NULL DEFAULT NULL COMMENT '创建用户ID',
                                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                               `update_id` bigint NULL DEFAULT NULL COMMENT '更新用户ID',
                                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                               `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识，1：已删除，0：未删除',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '游戏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game
-- ----------------------------

-- ----------------------------
-- Table structure for game_trigger
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`game_trigger`;
CREATE TABLE `tg-game-miniprogram`.`game_trigger`  (
                                                       `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                       `game_id` bigint NOT NULL COMMENT '游戏ID',
                                                       `type` int NOT NULL COMMENT '类型 对应枚举1下载2打开',
                                                       `create_time` datetime NULL DEFAULT NULL,
                                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 722 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '游戏触发记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game_trigger
-- ----------------------------

-- ----------------------------
-- Table structure for language
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`language`;
CREATE TABLE `tg-game-miniprogram`.`language`  (
                                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '语言ID',
                                                   `language_name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '其他' COMMENT '语言',
                                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '语言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of language
-- ----------------------------
INSERT INTO `tg-game-miniprogram`.`language` VALUES (1, '英语');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (2, '葡萄牙语');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (3, '印度语');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (4, '其他');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (5, '日语');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (6, '韩语');
INSERT INTO `tg-game-miniprogram`.`language` VALUES (7, '印尼语');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`sys_menu`;
CREATE TABLE `tg-game-miniprogram`.`sys_menu`  (
                                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                                   `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
                                                   `type` tinyint NOT NULL COMMENT '权限类型：1:目录，2：菜单，3：按钮',
                                                   `parent_id` int NOT NULL DEFAULT 0 COMMENT '父级权限ID',
                                                   `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由地址',
                                                   `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
                                                   `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
                                                   `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
                                                   `hidden` tinyint NOT NULL DEFAULT 0 COMMENT '是否隐藏：0：否，1：是',
                                                   PRIMARY KEY (`id`) USING BTREE,
                                                   INDEX `idx_type`(`type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '权限表（包含菜单及按钮）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `tg-game-miniprogram`.`sys_menu` VALUES (2, '游戏管理', 1, 0, '/game_manage', NULL, 'el-icon-bangzhu', 2, 0);
INSERT INTO `tg-game-miniprogram`.`sys_menu` VALUES (3, '游戏列表', 2, 2, '/game-lists', '/game_manage/game-list.vue', NULL, 1, 0);
INSERT INTO `tg-game-miniprogram`.`sys_menu` VALUES (11, '添加游戏', 3, 3, NULL, 'addGame', NULL, 1, 1);
INSERT INTO `tg-game-miniprogram`.`sys_menu` VALUES (12, '删除游戏', 3, 3, NULL, 'deleteGame', NULL, 1, 1);
INSERT INTO `tg-game-miniprogram`.`sys_menu` VALUES (13, '编辑游戏', 3, 3, NULL, 'editGame', NULL, 1, 1);

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`sys_user_menu`;
CREATE TABLE `tg-game-miniprogram`.`sys_user_menu`  (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                                        `user_id` bigint NOT NULL COMMENT '用户ID',
                                                        `menu_id` int NOT NULL COMMENT '菜单或按钮权限ID',
                                                        PRIMARY KEY (`id`) USING BTREE,
                                                        INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户菜单权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `tg-game-miniprogram`.`user_info`;
CREATE TABLE `tg-game-miniprogram`.`user_info`  (
                                                    `id` bigint NOT NULL COMMENT '用户ID',
                                                    `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
                                                    `user_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
                                                    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
                                                    `recent_login_time` datetime NULL DEFAULT NULL COMMENT '最近登录时间',
                                                    `create_id` int NULL DEFAULT NULL,
                                                    `create_time` datetime NULL DEFAULT NULL,
                                                    `update_id` int NULL DEFAULT NULL,
                                                    `update_time` datetime NULL DEFAULT NULL,
                                                    `role_id` int NULL DEFAULT NULL COMMENT '角色ID',
                                                    `channel_id` int NULL DEFAULT NULL COMMENT '渠道ID',
                                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `tg-game-miniprogram`.`user_info` VALUES (1, 'Admin', 'https://h5.cajbook.com/image/head/1.png', '96e79218965eb72c92a549dd5a330112', '2023-12-04 13:14:27', 0, '2023-11-10 11:51:26', 0, '2023-11-10 11:51:32', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
