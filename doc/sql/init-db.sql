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