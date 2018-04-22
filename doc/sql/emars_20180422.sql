/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.13 : Database - emars
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`emars` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `emars`;

/*Table structure for table `t_author` */

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `t_copyright` */

DROP TABLE IF EXISTS `t_copyright`;

CREATE TABLE `t_copyright` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_code` varchar(20) NOT NULL,
  `c_type` varchar(2) NOT NULL,
  `c_granter_id` bigint(20) NOT NULL DEFAULT '0',
  `c_grantee_id` bigint(20) NOT NULL,
  `c_signdate` date NOT NULL,
  `c_operator` bigint(20) NOT NULL,
  `c_project_code` varchar(20) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `t_copyright_contract` */

DROP TABLE IF EXISTS `t_copyright_contract`;

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

/*Table structure for table `t_copyright_file` */

DROP TABLE IF EXISTS `t_copyright_file`;

CREATE TABLE `t_copyright_file` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(200) NOT NULL,
  `c_type` varchar(2) NOT NULL,
  `c_product_id` bigint(20) NOT NULL,
  `c_path` varchar(500) NOT NULL,
  `c_desc` varchar(500) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_copyright_product` */

DROP TABLE IF EXISTS `t_copyright_product`;

CREATE TABLE `t_copyright_product` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_copyright_id` bigint(20) NOT NULL,
  `c_product_id` bigint(20) NOT NULL,
  `c_price` varchar(20) NOT NULL,
  `c_privileges` varchar(10) NOT NULL,
  `c_grant` varchar(2) NOT NULL,
  `c_copyright_type` varchar(2) NOT NULL,
  `c_settlement_type` varchar(2) NOT NULL,
  `c_begin` date DEFAULT NULL,
  `c_end` date DEFAULT NULL,
  `c_desc` varchar(512) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_grantee` */

DROP TABLE IF EXISTS `t_grantee`;

CREATE TABLE `t_grantee` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(128) NOT NULL,
  `c_desc` varchar(512) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_granter` */

DROP TABLE IF EXISTS `t_granter`;

CREATE TABLE `t_granter` (
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `t_product` */

DROP TABLE IF EXISTS `t_product`;

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

/*Table structure for table `t_product_info` */

DROP TABLE IF EXISTS `t_product_info`;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `t_product_sample` */

DROP TABLE IF EXISTS `t_product_sample`;

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

/*Table structure for table `t_product_subject` */

DROP TABLE IF EXISTS `t_product_subject`;

CREATE TABLE `t_product_subject` (
  `C_ID` varchar(32) NOT NULL,
  `C_PRODUCT_ID` varchar(32) NOT NULL,
  `C_SUBJECT_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_subject` */

DROP TABLE IF EXISTS `t_subject`;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
