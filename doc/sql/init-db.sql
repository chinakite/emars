DROP TABLE IF EXISTS `t_announcer`;

CREATE TABLE `t_announcer` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(200) DEFAULT NULL,
  `C_IDCARD` varchar(20) DEFAULT NULL,
  `C_PSEUDONYM` varchar(200) DEFAULT NULL,
  `C_DESC` varchar(200) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_author`;

CREATE TABLE `t_author` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(200) DEFAULT NULL,
  `C_IDCARD` varchar(20) DEFAULT NULL,
  `C_PSEUDONYM` varchar(200) DEFAULT NULL,
  `C_DESC` varchar(200) DEFAULT NULL,
  `C_FAMOUS` varchar(1) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_grantee`;

CREATE TABLE `t_grantee` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(128) NOT NULL,
  `c_desc` VARCHAR(512) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_granter`;

CREATE TABLE `t_granter` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(128) NOT NULL,
  `c_contact` VARCHAR(60) DEFAULT NULL,
  `c_phone` VARCHAR(20) DEFAULT NULL,
  `c_desc` VARCHAR(512) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_maker`;

CREATE TABLE `t_maker` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(128) NOT NULL,
  `c_contact` varchar(60) DEFAULT NULL,
  `c_phone` varchar(20) DEFAULT NULL,
  `c_desc` varchar(512) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_reservation_announcer`;

CREATE TABLE `t_reservation_announcer` (
  `c_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_product_id` INT(11) NOT NULL,
  `c_announcer_id` INT(11) NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_subject`;

CREATE TABLE `t_subject` (
  `C_ID` BIGINT(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` VARCHAR(100) DEFAULT NULL,
  `C_DESC` VARCHAR(500) DEFAULT NULL,
  `C_ORDER` INT(11) DEFAULT NULL,
  `C_TYPE` VARCHAR(256) DEFAULT NULL,
  `C_RATIO` VARCHAR(10) DEFAULT NULL,
  `C_CREATOR` VARCHAR(32) DEFAULT NULL,
  `C_CREATETIME` DATETIME DEFAULT NULL,
  `C_MODIFIER` VARCHAR(32) DEFAULT NULL,
  `C_MODIFYTIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `C_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(60) DEFAULT NULL,
  `C_ACCOUNT` varchar(60) DEFAULT NULL,
  `C_EMAIL` varchar(60) DEFAULT NULL,
  `C_PASSWORD` varchar(40) DEFAULT NULL,
  `C_MOBILE` varchar(20) DEFAULT NULL,
  `C_ROLE` varchar(60) DEFAULT NULL,
  `C_GENDER` varchar(1) DEFAULT NULL,
  `C_HONORIFIC` varchar(60) DEFAULT NULL,
  `C_LOGOURL` varchar(500) DEFAULT NULL,
  `C_STATUS` varchar(1) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_email_setting`;

CREATE TABLE `t_email_setting` (
  `C_ID` VARCHAR(32) NOT NULL,
  `C_HOSTNAME` VARCHAR(200) DEFAULT NULL,
  `C_PORT` VARCHAR(5) DEFAULT NULL,
  `C_TYPE` VARCHAR(2) DEFAULT NULL,
  `C_FROM_EMAIL` VARCHAR(200) DEFAULT NULL,
  `C_FROM_NAME` VARCHAR(200) DEFAULT NULL,
  `C_USER_NAME` VARCHAR(200) DEFAULT NULL,
  `C_PASSWORD` VARCHAR(50) DEFAULT NULL,
  `C_SSL` VARCHAR(2) DEFAULT NULL,
  `C_CREATOR` VARCHAR(32) DEFAULT NULL,
  `C_CREATETIME` DATETIME DEFAULT NULL,
  `C_MODIFIER` VARCHAR(32) DEFAULT NULL,
  `C_MODIFYTIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_copyright`;

CREATE TABLE `t_copyright` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_code` VARCHAR(20) NOT NULL,
  `c_type` VARCHAR(2) NOT NULL,
  `c_granter_id` BIGINT(20) NOT NULL DEFAULT '0',
  `c_grantee_id` BIGINT(20) NOT NULL,
  `c_signdate` DATE NOT NULL,
  `c_operator` BIGINT(20) NOT NULL,
  `c_project_code` VARCHAR(20) DEFAULT NULL,
  `c_state` VARCHAR(2) DEFAULT '0',
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_copyright_file`;

CREATE TABLE `t_copyright_file` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(200) NOT NULL,
  `c_type` VARCHAR(2) NOT NULL,
  `c_product_id` BIGINT(20) NOT NULL,
  `c_path` VARCHAR(500) NOT NULL,
  `c_desc` VARCHAR(500) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_copyright_product`;

CREATE TABLE `t_copyright_product` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_copyright_id` BIGINT(20) NOT NULL,
  `c_product_id` BIGINT(20) NOT NULL,
  `c_price` VARCHAR(20) NOT NULL,
  `c_privileges` VARCHAR(10) NOT NULL,
  `c_grant` VARCHAR(2) NOT NULL,
  `c_copyright_type` VARCHAR(2) NOT NULL,
  `c_settlement_type` VARCHAR(2) NOT NULL,
  `c_proportions` VARCHAR(10) DEFAULT NULL,
  `c_begin` DATE DEFAULT NULL,
  `c_radio_trans` VARCHAR(10) DEFAULT NULL,
  `c_end` DATE DEFAULT NULL,
  `c_desc` VARCHAR(512) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product_info`;

CREATE TABLE `t_product_info` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(256) NOT NULL,
  `c_author_id` BIGINT(20) DEFAULT NULL,
  `c_wordcount` VARCHAR(10) DEFAULT NULL,
  `c_subject_id` BIGINT(20) DEFAULT NULL,
  `c_publish_state` VARCHAR(2) DEFAULT NULL,
  `c_isbn` VARCHAR(20) DEFAULT NULL,
  `c_type` VARCHAR(10) DEFAULT NULL,
  `c_production_state` VARCHAR(10) DEFAULT NULL,
  `c_press` VARCHAR(128) DEFAULT NULL,
  `c_sort` INT(11) DEFAULT NULL,
  `c_section` VARCHAR(10) DEFAULT NULL,
  `c_stockin` VARCHAR(10) DEFAULT NULL,
  `c_desc` VARCHAR(512) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product_picture`;

CREATE TABLE `t_product_picture` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(200) NOT NULL,
  `c_type` VARCHAR(2) NOT NULL,
  `c_product_id` BIGINT(20) NOT NULL,
  `c_path` VARCHAR(500) NOT NULL,
  `c_logo` VARCHAR(2) NOT NULL,
  `c_desc` VARCHAR(500) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product_author`;

CREATE TABLE `t_product_author` (
  `c_id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_product_id` BIGINT(11) NOT NULL,
  `c_author_id` BIGINT(20) NOT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_make_contract`;

CREATE TABLE `t_make_contract` (
  `C_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `C_CODE` varchar(20) DEFAULT NULL,
  `C_TARGET_TYPE` varchar(20) DEFAULT NULL,
  `C_OWNER` varchar(255) DEFAULT NULL,
  `C_MAKER_ID` bigint(20) DEFAULT NULL,
  `C_MAKER` varchar(255) DEFAULT NULL,
  `C_TOTAL_PRICE` decimal(10,0) DEFAULT NULL,
  `C_SIGNDATE` date DEFAULT NULL,
  `C_TOTAL_SECTION` int(11) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  `c_state` varchar(2) DEFAULT '0',
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_make_contract_product`;

CREATE TABLE `t_make_contract_product` (
  `C_ID` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `C_MAKE_CONTRACT_ID` BIGINT(20) DEFAULT NULL,
  `C_PRODUCT_ID` BIGINT(20) DEFAULT NULL,
  `C_PRICE` DECIMAL(10,0) DEFAULT NULL,
  `C_SECTION` INT(20) DEFAULT NULL,
  `C_CREATOR` BIGINT(20) DEFAULT NULL,
  `C_CREATETIME` DATETIME DEFAULT NULL,
  `C_MODIFIER` BIGINT(20) DEFAULT NULL,
  `C_MODIFYTIME` DATETIME DEFAULT NULL,
  `C_WORKER` VARCHAR(255) DEFAULT NULL,
  `C_ANNOUNCER_ID` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_make_ctrt_doc`;

CREATE TABLE `t_make_ctrt_doc` (
  `C_ID` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `C_MAKE_CONTRACT_PRODUCT_ID` BIGINT(20) DEFAULT NULL,
  `C_TYPE` VARCHAR(2) DEFAULT NULL,
  `C_CREATOR` BIGINT(20) DEFAULT NULL,
  `C_CREATETIME` DATETIME DEFAULT NULL,
  `C_NAME` VARCHAR(500) DEFAULT NULL,
  `C_PATH` VARCHAR(500) DEFAULT NULL,
  `C_DESC` VARCHAR(500) DEFAULT NULL,
  `C_MODIFIER` BIGINT(20) DEFAULT NULL,
  `C_MODIFYTIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product_announcer`;

CREATE TABLE `t_product_announcer` (
  `c_id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_product_id` BIGINT(11) NOT NULL,
  `c_announcer_id` BIGINT(11) NOT NULL,
  `c_mc_id` BIGINT(20) NOT NULL,
  `c_creator` BIGINT(11) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

#====================

CREATE TABLE `t_user` (
  `C_ID` bigint NOT NULL,
  `C_NAME` varchar(60) DEFAULT NULL,
  `C_ACCOUNT` varchar(60) DEFAULT NULL,
  `C_EMAIL` varchar(60) DEFAULT NULL,
  `C_PASSWORD` varchar(40) DEFAULT NULL,
  `C_MOBILE` varchar(20) DEFAULT NULL,
  `C_ROLE` varchar(60) DEFAULT NULL,
  `C_GENDER` varchar(1) DEFAULT NULL,
  `C_HONORIFIC` varchar(60) DEFAULT NULL,
  `C_LOGOURL` varchar(500) DEFAULT NULL,
  `C_STATUS` varchar(1) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_email_setting` (
  `C_ID` bigint NOT NULL,
  `C_HOSTNAME` varchar(200) DEFAULT NULL,
  `C_PORT` varchar(5) DEFAULT NULL,
  `C_TYPE` varchar(2) DEFAULT NULL,
  `C_FROM_EMAIL` varchar(200) DEFAULT NULL,
  `C_FROM_NAME` varchar(200) DEFAULT NULL,
  `C_USER_NAME` varchar(200) DEFAULT NULL,
  `C_PASSWORD` varchar(50) DEFAULT NULL,
  `C_SSL` varchar(2) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_author` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(200) DEFAULT NULL,
  `C_IDCARD` varchar(20) DEFAULT NULL,
  `C_PSEUDONYM` varchar(200) DEFAULT NULL,
  `C_DESC` varchar(200) DEFAULT NULL,
  `C_FAMOUS` varchar(1) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_copyright` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_code` varchar(20) NOT NULL,
  `c_type` varchar(2) NOT NULL,
  `c_granter` varchar(256) NOT NULL,
  `c_grantee` varchar(256) NOT NULL,
  `c_signdate` date NOT NULL,
  `c_operator` bigint(20) NOT NULL,
  `c_project_code` varchar(20) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_product_info` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(256) NOT NULL,
  `c_author_id` bigint(20) DEFAULT NULL,
  `c_wordcount` varchar(10) DEFAULT NULL,
  `c_subject_id` bigint(20) DEFAULT NULL,
  `c_publish_state` varchar(2) DEFAULT NULL,
  `c_isbn` varchar(20) DEFAULT NULL,
  `c_press` varchar(128) DEFAULT NULL,
  `c_desc` varchar(512) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_copyright_contract` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_CODE` varchar(20) DEFAULT NULL,
  `C_OWNER` varchar(300) DEFAULT NULL,
  `C_OWNER_CONTACT` varchar(60) DEFAULT NULL,
  `C_OWNER_CONTACT_PHONE` varchar(20) DEFAULT NULL,
  `C_OWNER_CONTACT_ADDRESS` varchar(300) DEFAULT NULL,
  `C_OWNER_CONTACT_EMAIL` varchar(60) DEFAULT NULL,
  `C_BUYER` varchar(300) DEFAULT NULL,
  `C_BUYER_CONTACT` varchar(60) DEFAULT NULL,
  `C_BUYER_CONTACT_PHONE` varchar(20) DEFAULT NULL,
  `C_BUYER_CONTACT_ADDRESS` varchar(300) DEFAULT NULL,
  `C_BUYER_CONTACT_EMAIL` varchar(60) DEFAULT NULL,
  `C_PRIVILEGES` varchar(20) DEFAULT NULL,
  `C_PRIVILEGE_TYPE` varchar(1) DEFAULT NULL,
  `C_PRIVILEGE_RANGE` varchar(1) DEFAULT NULL,
  `C_PRIVILEGE_DEADLINE` varchar(3) DEFAULT NULL,
  `C_BANK_ACCOUNT_NAME` varchar(300) DEFAULT NULL,
  `C_BANK_ACCOUNT_NO` varchar(30) DEFAULT NULL,
  `C_BANK` varchar(300) DEFAULT NULL,
  `C_TOTAL_PRICE` decimal(10,0) DEFAULT NULL,
  `C_AUDIT_STATE` varchar(2) DEFAULT NULL,
  `C_FINISH_TIME` datetime DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_product` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(60) DEFAULT NULL,
  `C_SUBJECT_ID` bigint(32) DEFAULT NULL,
  `C_AUTHOR_ID` bigint(32) DEFAULT NULL,
  `C_TYPE` varchar(1) DEFAULT NULL,
  `C_PUBLISH_STATE` varchar(4) DEFAULT NULL,
  `C_PUBLISH_YEAR` varchar(10) DEFAULT NULL,
  `C_FINISH_YEAR` varchar(10) DEFAULT NULL,
  `C_STATE` varchar(4) DEFAULT NULL,
  `C_WORD_COUNT` decimal(11,1) DEFAULT NULL,
  `C_SECTION_COUNT` int(11) DEFAULT NULL,
  `C_SECTION_LENGTH` int(11) DEFAULT NULL,
  `C_PRESS` varchar(200) DEFAULT NULL,
  `C_WEBSITE` varchar(200) DEFAULT NULL,
  `C_SUMMARY` varchar(2000) DEFAULT NULL,
  `C_HAS_AUDIO` varchar(1) DEFAULT NULL,
  `C_AUDIO_COPYRIGHT` varchar(1) DEFAULT NULL,
  `C_AUDIO_DESC` varchar(500) DEFAULT NULL,
  `C_ISBN` varchar(100) DEFAULT NULL,
  `C_LOGO_URL` varchar(100) DEFAULT NULL,
  `C_RESERVED` varchar(1) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  `C_IN_MEDIARES` varchar(1) DEFAULT '0',
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_product_sample` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_PRODUCT_ID` bigint(32) DEFAULT NULL,
  `C_FILE_URL` varchar(500) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_product_subject` (
  `C_ID` varchar(32) NOT NULL,
  `C_PRODUCT_ID` varchar(32) NOT NULL,
  `C_SUBJECT_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_subject` (
  `C_ID` bigint(32) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(100) DEFAULT NULL,
  `C_DESC` varchar(500) DEFAULT NULL,
  `C_ORDER` int(11) DEFAULT NULL,
  `C_TYPE` varchar(256) DEFAULT NULL,
  `C_RATIO` varchar(10) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_customer`;

CREATE TABLE `t_customer` (
  `c_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(128) NOT NULL,
  `c_contact` VARCHAR(60) DEFAULT NULL,
  `c_phone` VARCHAR(20) DEFAULT NULL,
  `c_desc` VARCHAR(512) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_platform`;

CREATE TABLE `t_platform` (
  `c_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `c_name` varchar(128) NOT NULL DEFAULT '',
  `c_customer_id` bigint(20) NOT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_sale_contract`;

CREATE TABLE `t_sale_contract` (
  `c_id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_code` VARCHAR(20) NOT NULL DEFAULT '',
  `c_type` VARCHAR(2) NOT NULL DEFAULT '',
  `c_granter_id` BIGINT(20) NOT NULL,
  `c_customer_id` BIGINT(11) NOT NULL,
  `c_privileges` VARCHAR(10) NOT NULL DEFAULT '',
  `c_signdate` DATE NOT NULL,
  `c_operator` BIGINT(20) NOT NULL,
  `c_total_section` INT(11) DEFAULT NULL,
  `c_total_price` INT(11) DEFAULT NULL,
  `c_begin` DATE DEFAULT NULL,
  `c_end` DATE DEFAULT NULL,
  `c_project_code` VARCHAR(60) DEFAULT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  `c_modifier` BIGINT(20) DEFAULT NULL,
  `c_modifytime` DATETIME DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_sale_file`;

CREATE TABLE `t_sale_file` (
  `c_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `c_sale_product_id` bigint(20) NOT NULL,
  `c_name` varchar(128) NOT NULL DEFAULT '',
  `c_type` varchar(10) NOT NULL DEFAULT '',
  `c_path` varchar(512) NOT NULL DEFAULT '',
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_sale_product`;

CREATE TABLE `t_sale_product` (
  `c_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `c_sale_contract_id` bigint(20) NOT NULL,
  `c_product_id` bigint(20) NOT NULL,
  `c_section` int(11) NOT NULL,
  `c_price` int(11) NOT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sale_customer_platform`;

CREATE TABLE `t_sale_customer_platform` (
  `c_id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_sale_contract_id` BIGINT(11) DEFAULT NULL,
  `c_customer_id` BIGINT(20) NOT NULL,
  `c_platform_id` BIGINT(20) NOT NULL,
  `c_creator` BIGINT(20) NOT NULL,
  `c_createtime` DATETIME NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;