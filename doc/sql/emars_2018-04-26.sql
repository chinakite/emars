# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: emars
# Generation Time: 2018-04-26 13:04:40 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_author
# ------------------------------------------------------------

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

LOCK TABLES `t_author` WRITE;
/*!40000 ALTER TABLE `t_author` DISABLE KEYS */;

INSERT INTO `t_author` (`C_ID`, `C_NAME`, `C_IDCARD`, `C_PSEUDONYM`, `C_DESC`, `C_FAMOUS`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(1,'马未都','110105198105050310',NULL,'擅长古风写作','1','1','2016-01-01 17:43:10',NULL,NULL),
	(2,'于妈','110105198105050310',NULL,'擅长都市言情、宫斗','0','1','2016-01-01 17:43:10',NULL,NULL),
	(3,'海宴','110105198105050310',NULL,'擅长谋略，代表作《琅琊榜》','1','1','2016-01-01 17:43:10',NULL,NULL),
	(4,'不知名作者','110105198105050310',NULL,'这个肯定不知名','0','1','2016-01-01 17:43:10','1','2016-01-02 00:43:33'),
	(5,'作者5','110105198105050310','作者五','疯了...','0','1','2016-01-01 17:43:10','1','2016-01-02 02:10:27'),
	(6,'作者6','110105198105050310',NULL,'还是不行啊啊啊','0','1','2016-01-01 17:43:10','1','2016-01-02 14:17:02'),
	(7,'作者7','110105198105050310',NULL,'','0','1','2016-01-01 17:43:10',NULL,NULL),
	(8,'作者8','110105198105050310',NULL,'这是第8个了','0','1','2016-01-01 17:43:10','1','2016-01-02 14:17:30'),
	(9,'唐家三少','110105198105050310',NULL,'大神还用说吗？','0','1','2016-01-02 00:21:31',NULL,NULL),
	(10,'南派三叔','110105198105050310',NULL,'盗墓笔记作者','1','1','2016-01-01 17:43:10','1','2016-01-02 00:26:11'),
	(11,'老猪','110105198105050310',NULL,'《紫川》，哈哈','1','1','2016-01-01 17:43:10','1','2016-01-02 00:39:00'),
	(12,'猫腻','110105198105050310','晓峰','新浪原创文学奖玄幻类金奖','1','1','2016-01-01 17:43:10','1','2016-01-02 00:37:21'),
	(13,'洛水','110105198105050310',NULL,'大道残篇出洛水，半部天书知北游。','0','1','2016-01-02 00:31:34',NULL,NULL),
	(15,'test',NULL,'test',NULL,'0','1','2016-10-07 07:27:46','1','2016-10-07 07:27:46'),
	(16,'一一',NULL,'一一',NULL,'0','1','2016-06-02 06:27:45','1','2016-06-02 06:27:45'),
	(17,'二二',NULL,'二二',NULL,'0','1','2016-06-02 06:33:41','1','2016-06-02 06:33:41'),
	(18,'傅传松',NULL,'傅传松',NULL,'0','1','2016-08-09 11:26:02','1','2016-08-09 11:26:02'),
	(19,'保存',NULL,'保存',NULL,'0','1','2016-06-06 11:23:20','1','2016-06-06 11:23:20'),
	(20,'今何在','110105198105050310','今何在',NULL,'0','1','2016-01-17 23:35:42','1','2016-01-17 23:35:42'),
	(21,'刘强东','110105198105050310','',NULL,'0','1','2016-02-28 12:11:32','1','2016-02-28 12:11:32'),
	(22,'吴承恩','110105198105050310','',NULL,'0','1','2016-01-17 23:45:24','1','2016-01-17 23:45:24'),
	(23,'周德东','110105198105050310','',NULL,'0','1','2016-03-07 00:39:10','1','2016-03-07 00:39:10'),
	(24,'太子','110105198105050310','',NULL,'0','1','2016-01-17 23:37:35','1','2016-01-17 23:37:35'),
	(25,'宁余心',NULL,'宁余心',NULL,'0','1','2016-05-19 09:35:16','1','2016-05-19 09:35:16'),
	(26,'忘语','110105198105050310','',NULL,'0','1','2016-01-05 01:37:43','1','2016-01-05 01:37:43'),
	(27,'曹建伟',NULL,'曹建伟',NULL,'0','1','2016-06-13 15:47:33','1','2016-06-13 15:47:33'),
	(28,'李佩甫','110105198105050310','',NULL,'0','1','2016-03-06 23:28:14','1','2016-03-06 23:28:14'),
	(29,'李捷','110105198105050310','',NULL,'1','1','2016-03-06 23:24:38','1','2016-03-06 23:24:38'),
	(30,'江城','110105198105050310','',NULL,'0','1','2016-03-07 00:31:17','1','2016-03-07 00:31:17'),
	(31,'测试','110105198105050310','小测',NULL,'0','1','2016-02-16 00:18:02','1','2016-02-16 00:18:02'),
	(32,'王寞',NULL,'王寞',NULL,'0','1','2016-06-06 17:10:18','1','2016-06-06 17:10:18'),
	(33,'管浒','110105198105050310','管虎',NULL,'0','1','2016-03-06 23:20:13','1','2016-03-06 23:20:13'),
	(34,'萧潜','110105198105050310','',NULL,'0','1','2016-01-12 23:18:44','1','2016-01-12 23:18:44'),
	(35,'虹影',NULL,'虹影',NULL,'0','1','2016-06-23 11:29:23','1','2016-06-23 11:29:23'),
	(36,'迟子建','110105198105050310','',NULL,'0','1','2016-03-07 00:27:21','1','2016-03-07 00:27:21'),
	(37,'陈保才',NULL,'陈保才',NULL,'0','1','2016-06-08 13:41:54','1','2016-06-08 13:41:54'),
	(38,'随便',NULL,'随便',NULL,'0','1','2016-06-13 15:44:17','1','2016-06-13 15:44:17'),
	(39,'风笑','110105198105050310','',NULL,'0','1','2016-01-10 21:45:04','1','2016-01-10 21:45:04'),
	(40,'高晓松','110105198105050310','',NULL,'0','1','2016-01-17 23:42:00','1','2016-01-17 23:42:00'),
	(41,'佚名','110105198105050310','',NULL,'0','1','2016-01-12 23:20:30','1','2016-01-12 23:20:30'),
	(42,'程怡',NULL,'拾月',NULL,NULL,'1','2018-04-04 14:55:03',NULL,NULL),
	(43,'张三丰',NULL,'水心沙',NULL,NULL,'1','2018-04-11 11:14:43',NULL,NULL),
	(44,'胡说',NULL,'胡汉三','',NULL,'1','2018-04-26 11:52:04',NULL,NULL),
	(45,'常舒新',NULL,'常书欣','',NULL,'1','2018-04-26 13:58:12',NULL,NULL),
	(46,'AAA',NULL,'aaa','',NULL,'1','2018-04-26 14:03:49',NULL,NULL),
	(47,'BBB',NULL,'bbb','',NULL,'1','2018-04-26 14:07:50',NULL,NULL),
	(48,'CCC',NULL,'ccc','',NULL,'1','2018-04-26 14:12:45',NULL,NULL);

/*!40000 ALTER TABLE `t_author` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_copyright
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_copyright`;

CREATE TABLE `t_copyright` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_code` varchar(20) NOT NULL,
  `c_type` varchar(2) NOT NULL,
  `c_granter` varchar(256) DEFAULT NULL,
  `c_granter_id` int(11) DEFAULT NULL,
  `c_grantee` varchar(256) DEFAULT NULL,
  `c_grantee_id` bigint(20) DEFAULT NULL,
  `c_signdate` date NOT NULL,
  `c_operator` bigint(20) NOT NULL,
  `c_project_code` varchar(20) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_copyright` WRITE;
/*!40000 ALTER TABLE `t_copyright` DISABLE KEYS */;

INSERT INTO `t_copyright` (`c_id`, `c_code`, `c_type`, `c_granter`, `c_granter_id`, `c_grantee`, `c_grantee_id`, `c_signdate`, `c_operator`, `c_project_code`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)
VALUES
	(1,'2017-wz-001','wz','上海社会科学院出版社有限公司',44,'悦库时光',43,'2018-04-04',2,'aaa-b-c-d',1,'2018-04-04 14:55:03',1,'2018-04-11 11:14:43');

/*!40000 ALTER TABLE `t_copyright` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_copyright_contract
# ------------------------------------------------------------

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
  `C_CONTACT_TYPE` varchar(2) DEFAULT NULL,
  `C_CREATOR` varchar(32) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` varchar(32) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_copyright_contract` WRITE;
/*!40000 ALTER TABLE `t_copyright_contract` DISABLE KEYS */;

INSERT INTO `t_copyright_contract` (`C_ID`, `C_CODE`, `C_OWNER`, `C_OWNER_CONTACT`, `C_OWNER_CONTACT_PHONE`, `C_OWNER_CONTACT_ADDRESS`, `C_OWNER_CONTACT_EMAIL`, `C_BUYER`, `C_BUYER_CONTACT`, `C_BUYER_CONTACT_PHONE`, `C_BUYER_CONTACT_ADDRESS`, `C_BUYER_CONTACT_EMAIL`, `C_PRIVILEGES`, `C_PRIVILEGE_TYPE`, `C_PRIVILEGE_RANGE`, `C_PRIVILEGE_DEADLINE`, `C_BANK_ACCOUNT_NAME`, `C_BANK_ACCOUNT_NO`, `C_BANK`, `C_TOTAL_PRICE`, `C_AUDIT_STATE`, `C_FINISH_TIME`, `C_CONTACT_TYPE`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(1,'C20160205-001','猫腻','猫腻','13111111111','中国好作家协会','mn@mn.com','北京悦库时光文化传媒有限公司','齐诗诗','13222222222','北广','qss@em.com','01,03,04','0','0','1','mao腻','1234567890','中国银行',20000,'99','2016-02-21 00:37:49',NULL,'1','2016-02-05 16:59:17','1','2016-02-05 16:59:17'),
	(2,'C20160219-001','AA公司','大A','13777777777','A市','da@da.com','北京悦库时光文化传媒有限公司','马菲菲','13888888888','北广','mf@em.com','01,02','1','0','2','AA公司',NULL,'招商银行',300000,'1',NULL,NULL,'1','2016-02-19 00:12:05','1','2016-02-19 00:12:05'),
	(3,'C20160228-001','忘语','wangyu','13222222222','凡人谷','wy@em.com','北京悦库时光文化传媒有限公司','刘订订','13444444444','北广','ldd@em.com','01,02,03,04','0','0','1','王宇',NULL,'招商银行',1000,'4',NULL,NULL,'1','2016-02-28 13:48:10','1','2016-02-28 13:48:10'),
	(4,'C20160301-001','猫腻','moni','13444444444','猫家大院','moni@mn.com','北京悦库时光文化传媒有限公司','版权专员','13555555555','北广大厦','bqzy@em.com','01,02,03,04','0','0','1','猫腻','1234567890','中国银行',80000,'99',NULL,NULL,'1','2016-03-01 23:44:48','1','2016-03-01 23:44:48'),
	(5,'C20160309-001','管浒','管浒','13423433444','北京朝阳区北辰西路8号','gh@media.cn','北京广播有限公司','刘成斌','18654323456','北京市东城区建国门外大街6号','lcb@rbc.cn','01,02,03,04','0','0','3','管浒',NULL,'招行银行北京中关村支行',3000,'99',NULL,NULL,'1','2016-03-09 12:15:33','1','2016-03-09 12:15:33'),
	(6,'C20160309-002','北京如意欣欣文化发展有限公司','张梦月','13810364456','北京市朝阳区八里庄东路1号12层','zhangmengyue@ruyibooks.com','北京广播公司','刘成斌','13567892431','北京市东城区建国门外大街甲14号605','liuchengbin@rbc.cn','01,02,03,04','0','0','5','北京如意欣欣文化发展有限公司',NULL,'中国工商银行',30000,'99',NULL,NULL,'1','2016-03-09 23:46:30','1','2016-03-09 23:46:30'),
	(7,'C20160310-001','江城','江城','15875349273','北京市石景山区苹果园东路218号','jcheng618@163.com','北京广播公司','刘成斌','13587652346','北京市东城区建国门外大街6号','liuchengbin@rbc.cn','01,02,03','1','0','3','江城',NULL,'民生银行中关村支行',12000,'51',NULL,NULL,'1','2016-03-10 00:08:35','1','2016-03-10 00:08:35');

/*!40000 ALTER TABLE `t_copyright_contract` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_copyright_file
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_copyright_file` WRITE;
/*!40000 ALTER TABLE `t_copyright_file` DISABLE KEYS */;

INSERT INTO `t_copyright_file` (`c_id`, `c_name`, `c_type`, `c_product_id`, `c_path`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)
VALUES
	(2,'yunke_jdecc.jpg','1',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180523/9ebca343-2c2d-4a0e-bcb9-d191a337868b',NULL,1,'2018-04-23 10:05:21',NULL,NULL),
	(3,'旗正商业规则定制平台产品白皮书.pdf','1',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180423/4de08a2b-e423-4d06-9811-593d06b652fa',NULL,1,'2018-04-23 14:15:28',NULL,NULL),
	(4,'theming-5.png','2',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180423/c24205f0-2da0-48c5-af97-c31fae31422b',NULL,1,'2018-04-23 16:00:10',NULL,NULL),
	(5,'fresh.png','4',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180423/1fa041d0-59c4-4bdd-a0d8-1fdcd716c70f',NULL,1,'2018-04-23 16:29:34',NULL,NULL),
	(6,'silver.png','3',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180423/f467879c-dc62-45be-93ba-4c8326664d7f',NULL,1,'2018-04-23 16:44:50',NULL,NULL),
	(7,'darklight-blue.png','5',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180423/e2bef8b5-611b-4648-aa20-faabd42e9cc2',NULL,1,'2018-04-23 16:59:35',NULL,NULL);

/*!40000 ALTER TABLE `t_copyright_file` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_copyright_product
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_copyright_product` WRITE;
/*!40000 ALTER TABLE `t_copyright_product` DISABLE KEYS */;

INSERT INTO `t_copyright_product` (`c_id`, `c_copyright_id`, `c_product_id`, `c_price`, `c_privileges`, `c_grant`, `c_copyright_type`, `c_settlement_type`, `c_begin`, `c_end`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)
VALUES
	(1,1,1,'12000','1100','1','1','0','2018-04-01','2018-04-30','',1,'2018-04-04 14:55:03',NULL,NULL),
	(2,1,2,'8000','1100','1','1','0','2018-04-01','2018-04-30','',1,'2018-04-11 11:14:43',NULL,NULL);

/*!40000 ALTER TABLE `t_copyright_product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_email_setting
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_email_setting`;

CREATE TABLE `t_email_setting` (
  `C_ID` bigint(20) NOT NULL,
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

LOCK TABLES `t_email_setting` WRITE;
/*!40000 ALTER TABLE `t_email_setting` DISABLE KEYS */;

INSERT INTO `t_email_setting` (`C_ID`, `C_HOSTNAME`, `C_PORT`, `C_TYPE`, `C_FROM_EMAIL`, `C_FROM_NAME`, `C_USER_NAME`, `C_PASSWORD`, `C_SSL`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(1,'smtp.163.com','25','1','piaostudio@163.com','北京广播公司','piaostudio','Kn4944428','0','1','2018-02-23 15:03:33','1','2018-02-24 10:12:00');

/*!40000 ALTER TABLE `t_email_setting` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_grantee
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_grantee`;

CREATE TABLE `t_grantee` (
  `C_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(128) DEFAULT NULL,
  `C_DESC` varchar(512) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_grantee` WRITE;
/*!40000 ALTER TABLE `t_grantee` DISABLE KEYS */;

INSERT INTO `t_grantee` (`C_ID`, `C_NAME`, `C_DESC`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(43,'悦库时光','EuphonyMedia',1,'2018-04-08 11:12:30',NULL,NULL);

/*!40000 ALTER TABLE `t_grantee` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_granter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_granter`;

CREATE TABLE `t_granter` (
  `C_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C_NAME` varchar(128) DEFAULT NULL,
  `C_CONTACT` varchar(128) DEFAULT NULL,
  `C_PHONE` varchar(20) DEFAULT NULL,
  `C_DESC` varchar(512) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_granter` WRITE;
/*!40000 ALTER TABLE `t_granter` DISABLE KEYS */;

INSERT INTO `t_granter` (`C_ID`, `C_NAME`, `C_CONTACT`, `C_PHONE`, `C_DESC`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(43,'上海社会科学院出版社有限公司','','','合作愉快',1,'2018-04-08 11:13:20',NULL,NULL),
	(44,'人民东方（北京）书业有限公司','','','',1,'2018-04-08 15:39:04',NULL,NULL);

/*!40000 ALTER TABLE `t_granter` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_product
# ------------------------------------------------------------

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

LOCK TABLES `t_product` WRITE;
/*!40000 ALTER TABLE `t_product` DISABLE KEYS */;

INSERT INTO `t_product` (`C_ID`, `C_NAME`, `C_SUBJECT_ID`, `C_AUTHOR_ID`, `C_TYPE`, `C_PUBLISH_STATE`, `C_PUBLISH_YEAR`, `C_FINISH_YEAR`, `C_STATE`, `C_WORD_COUNT`, `C_SECTION_COUNT`, `C_SECTION_LENGTH`, `C_PRESS`, `C_WEBSITE`, `C_SUMMARY`, `C_HAS_AUDIO`, `C_AUDIO_COPYRIGHT`, `C_AUDIO_DESC`, `C_ISBN`, `C_LOGO_URL`, `C_RESERVED`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`, `C_IN_MEDIARES`)
VALUES
	(1,'侯卫东官场笔记5',2,10,'0','0','2011.2',NULL,'1',300000.0,NULL,NULL,NULL,NULL,'沙州市成津县县委书记章永泰车祸身亡，这究竟是意外事故，还是一起精心策划的人为谋杀？侯卫东临危受命，被市委书记周昌全派至成津县任县委副书记，查处章永泰真实死因，整治成津县乱局。以常务副县长李太忠为代表的地方势力，插手矿业生产，损公肥私，对侯卫东进行了孤立。侯卫东经过详细谋划，在一番合情合理的正常公务中，不动声色地替换和提拔了20名干部。在各项整顿工作初见成效时，周昌全离开沙州到省里工作，新书记降临沙州。侯卫东的官场命运，又将如何再生波澜……','0',NULL,NULL,'978-7-5506-0055-3',NULL,'1','1','2016-01-02 17:25:27',NULL,NULL,'0'),
	(2,'凡人修仙传',1,2,'0','0','2014','2016','5',120.0,NULL,NULL,'创想时刻出版社','','凡人修仙传，韩立','0','1','',NULL,NULL,'0','1','2016-01-05 01:37:43','1','2016-01-05 01:37:43','0'),
	(3,'官仙',1,3,'0','1','2016','2016','5',300.0,NULL,NULL,'','起点中文网','一个仙人穿越做官','0','1','',NULL,NULL,'0','1','2016-01-10 21:45:04','1','2016-01-10 21:45:04','0'),
	(4,'庆余年',1,12,'0','0','2013','2016','7',100.0,NULL,NULL,'不记得了','','庆余年','0','1','','1234567','/img/default_product_logo.jpg','1','1','2016-01-10 21:48:20','1','2016-01-25 23:36:57','0'),
	(5,'斗破苍穹',1,4,'0','0','2016','2016','1',200.0,NULL,NULL,'不知道什么出版社','','斗破苍穹，萧炎','0','1','',NULL,NULL,'0','1','2016-01-12 23:18:44','1','2016-01-12 23:18:44','0'),
	(6,'悟空传',1,5,'0','0','2016','2016','5',5.0,NULL,NULL,'中信出版社','','悟空传悟空传悟空传','0','1','',NULL,NULL,'0','1','2016-01-17 23:35:42','1','2016-01-17 23:35:42','0'),
	(7,'睡在我上铺的兄弟',1,5,'0','0','2016','2016','5',14.0,NULL,NULL,'什么什么出版社','','睡在我上铺的兄弟','0','1','',NULL,NULL,'0','1','2016-01-17 23:42:00','1','2016-01-17 23:42:00','0'),
	(8,'太子妃升职记',1,6,'0','1','2016','2015','0',28.0,NULL,NULL,'','起点','太子妃升职记，穿越','0','1','','',NULL,'1','1','2016-01-17 23:37:35','1','2016-02-14 23:44:59','0'),
	(9,'七侠五义',1,6,'0','0','2016','2016','8',80.0,NULL,NULL,'不知道什么出版社','','七侠五义','0','1','',NULL,NULL,'0','1','2016-01-12 23:20:30','1','2016-06-16 14:13:41','0'),
	(10,'三国演义',1,7,'0','0','2014','2016','2',80.0,NULL,NULL,'松花出版集团','','三国演义一','0','1','','1230987654901',NULL,'0','1','2016-01-17 23:43:07','1','2016-02-27 12:24:29','0'),
	(11,'测试作品',3,8,'0','3','2016','2016','0',100.0,NULL,NULL,'','','写的啥都不知道','0','1','',NULL,NULL,'0','1','2016-02-16 00:49:18','1','2016-02-16 00:49:18','0'),
	(12,'将夜',4,12,'0','0','2016','2016','0',60.0,NULL,NULL,'','','','0','1','','',NULL,NULL,'1','2016-02-27 12:09:16','1','2016-02-27 12:15:44','0'),
	(13,'创京东',5,3,'0','0','2016','2016','50',22.0,NULL,NULL,'中信','','创京东','0','1','',NULL,NULL,NULL,'1','2016-02-28 12:11:32','1','2016-02-28 23:47:55','0'),
	(14,'择天记',1,12,'0','1','2016','2016','13',8.0,NULL,NULL,'','起点中文网','择天记，猫腻新作','0','1','','',NULL,NULL,'1','2016-03-01 00:10:25','1','2016-03-03 00:59:03','0'),
	(15,'老炮儿',6,8,'0','0','2016','2016','14',10.0,NULL,NULL,'北京长江新世纪文化传媒有限公司','','曾经风光四九城的老炮六爷，难以适应社会巨变，蛰伏于胡同深处，过着溜鸟、管闲事、发牢骚的无聊日子。\n\n某日，六爷和“小炮儿”儿子晓波父子间产生巨大情感冲突，导致小炮儿负气离家出走，不想遭遇暗算，被新崛起一代的“小爷”小飞非法拘禁。为了解救儿子，并偿还当年对儿子的愧疚之情，六爷重出江湖。\n\n六爷用自己的规矩，试图摆平事件，却无奈的发现，无论是这个时代，还是自己的身体，早已今非昔比。一场父子恩仇、新旧势力的对决无法避免。\n\n在解决烂摊子的过程当中，老炮发现自己身体也出现了问题，真正力不从心。但同时也在抽丝剥茧的查询过程中抓住了“三环十二郎”的把柄，两人下了对战书决定来一场单挑。\n','0','1','','928-7-5354-8244-0','/uploadTmp/img/laopaoer_fxg.jpg',NULL,'1','2016-03-06 23:20:13','1','2016-03-09 23:28:24','1'),
	(16,'克拉恋人',7,3,'0','0','2015','2016','11',35.0,NULL,NULL,'作家出版社','','米朵爱上了钻石公司总裁萧亮，条件的悬殊使米朵望而却步，不敢表白。然而，一场车祸彻底改变了她的命运，车祸整容后的米朵变得苗条漂亮，她应聘进入钻石公司工作，成为了一名设计师助理。经历了失恋失业的双重打击后，米朵意识到美貌并不是获得爱情的通行证，她不再被美貌和丑陋所困扰，她要成为一名合格的珠宝设计师。米朵在职场上经历了种种挫折，她屡败屡战、不言放弃。米朵的乐观积极深深吸引了萧亮，她终于收获了梦想与爱情。与此同时，好友雷奕明始终以朋友的身份守护着米朵，在帮助米朵实现梦想的过程中也情不自禁的喜欢上她，米朵面临着朋友与爱人之间的选择。最终，米朵远走比利时，去完成设计师的梦想……\n','0','1','','978-7-5063-8193-2','/uploadTmp/img/kelalianren_cover.jpg',NULL,'1','2016-03-06 23:24:38','1','2016-03-09 23:59:17','0'),
	(17,'生命册',8,7,'0','0','2013','2016','3',38.0,NULL,NULL,'作家出版社','','“我”是从乡村走入省城的大学教师，希望摆脱农村成为一个完完整整的“城里人”，无奈老姑父不时传来的要求“我”为村人办事的指示性纸条让“我”很是为难，在爱情的憧憬与困顿面前，“我”毅然接受大学同学“骆驼”的召唤，辞去稳定的工作成为一个北漂。北京的模样完全不是我们当初预想的那般美好，在地下室里当了几个月的“枪手”挖到一桶金后，为了更宏大的理想，“我”和“骆驼”分别奔赴上海和深圳开辟新的商业战场。\n　　“骆驼”虽有残疾，却凭借超出常人的智力和果断杀入股票市场并赢得了巨额财富。而在追逐金钱的过程中，“骆驼”的欲望和贪婪也日益膨胀，他使出浑身解数攀附进官场名利场，不惜用金钱和美色将他人拉下水，而自己也在对欲望的追逐中逐渐走失了最初的理想，最终身陷囹圄，人财两空。\n　　生“我”养“我”的无梁村，有“我”极力摆脱却终挥之不去的记忆。哺育“我”十多年的老姑父为了爱情放弃了军人的身份，却在之后的几十年生活中深陷家庭矛盾无法自拔；上访户梁五方青年时凭借倔强的干劲打下了一片基业，却在运动中成为人们打击的目标，后半生困在无休止的上访漩涡里；为了拉扯大三个孩子，如草芥般的虫嫂沦为小偷，陷入人人可唾的悲剧命运；村里的能手春才，在青春期性的诱惑和村人的闲言碎语中自宫……在时代与土地的变迁中，似乎每个人都不可避免地走向了自己的反面……\n','0','1','','978-7-5063-6243-6','/uploadTmp/img/shengmingce_cover.jpg',NULL,'1','2016-03-06 23:28:14','1','2016-03-09 02:31:40','0'),
	(18,'群山之巅',9,4,'0','0','2015','2016','8',21.0,NULL,NULL,'人民文学出版社','','《群山之巅》是著名作家迟子建暌违五年之后，最新长篇小说。写作历时两年，是呕心沥血、大气磅礴之作。\n《群山之巅》比《额尔古纳河右岸》更苍茫雄浑，比《白雪乌鸦》更跌宕精彩。\n小说分“斩马刀”、“制碑人”、“龙山之翼”、“两双手”、“白马月光”、“生长的声音”、“追捕”、“格罗江英雄曲”、“从黑夜到白天”、“旧货节”、“肾源”、“暴风雪”、“毛边纸船坞”、“花老爷洞”、“黑珍珠”、“土地祠”等十七章，笔触如史诗般波澜壮阔，却又诗意而抒情。\n中国北方苍茫的龙山之翼，一个叫龙盏的小镇，屠夫辛七杂、能预知生死的精灵“小仙”安雪儿、击毙犯人的法警安平、殡仪馆理容师李素贞、绣娘、金素袖等，一个个身世性情迥异的小人物，在群山之巅各自的滚滚红尘中浮沉，爱与被爱，逃亡与复仇，他们在诡异与未知的命运中努力寻找出路；怀揣着各自不同的伤残的心，努力活出人的尊严，觅寻爱的幽暗之火……\n','0','1','','978-7-02-010693-6','/uploadTmp/img/qunshanzhidian_cover.jpg',NULL,'1','2016-03-07 00:27:21','1','2016-03-10 13:15:37','0'),
	(19,'历史深入的民国-晚清',10,7,'0','0','2014','2016','5',29.0,NULL,NULL,'华文出版社','','这是一套关于中国近代百年正史的彪悍史书。\n\n是中国历史上第一套全面解读民国正史的长篇历史力作。作者在精研民国史料的基础上，以尊重史实的严谨态度创作，以年代和具体人物为主线，用通俗易懂、幽默风趣的语言风格行笔，全新讲述了1840～1945这一百年间的一些我们熟悉的人做过的一些我们并不熟悉的事……\n\n从来没有一本关于民国的书籍，能像《历史深处的民国》这样，全面、真实、透彻、有趣地将民国这段历史讲明白说清楚。因此，《历史深处的民国》是当下中国数千万“民国迷”了解民国、认识民国的首选读本。\n\n第一部《晚清》全景勾勒了从1840到1911前后近七十年波澜壮阔的历史，重点描述了清王朝内部改革力量曾国藩、李鸿章、袁世凯，清朝统治者慈禧、光绪，在野力量革命派、立宪派三方，围绕该挽救清朝还是推倒清朝重来而展开的一系列惊心动魄的故事。\n\n重点突出了鸦片战争，太平天国运动，洋务运动，八国联军侵华，晚清革命，晚清立宪改革等重大历史事件，以轻松的笔调、严谨的态度认真探究了为何封建帝制已经走到了穷途末路，以及共和的曙光在中国显现的原因。','0','1','','987-7-5075-4229-5','/uploadTmp/img/lsscdmg_cover.jpg',NULL,'1','2016-03-07 00:31:17','1','2016-03-09 02:36:10','0'),
	(20,'三减一等于几',1,5,'0','0','2013','2016','8',29.0,NULL,NULL,'中国电影出版社','','773恐怖系列小说。讲述被抛弃的畸形儿对社会的报复.他的身体是个孩子可是思想已经是个成年人了,这是一种病,具体叫什么我就忘记了.他怕被人嘲笑,怕被人当猴耍.他的身体、外貌永远停留在婴儿时期的状态.\n　　他身体内里的一切都在正常成长.\n　　他洞晓人情世故,但是他的眼神永远是婴儿的纯净.\n　　他懂得男欢女爱,他有成熟的欲望,但是他的阳具永远是婴儿的弱小.\n　　他嫉妒雄壮的男人和漂亮的女人；他沉迷母性；他仇恨幸福的孩子,仇恨跟他争夺爱的真正的孩子.\n　　他不想向世人吐露真相,他害怕承担生活责任.\n　　他怕被人看成是怪物,当猴耍.他怕遭到这个世界的歧视和利用.\n　　他躲在婴儿的世界里,享受这个世界的母爱.\n　　由于外型和内心的日久天长的冲突,他极度变态.\n　　他小肚鸡肠,他阴险毒辣,他嗜杀成性,他恐怖异常.\n　　他被母亲揭穿秘密后,骗来另一个孪生兄弟,把他害死做替罪羊……全镇人都静静等待大难降临自己.\n那个收破烂的老太太就是他的母亲,最后他把母亲杀了,把她的肚子剖开了,自己钻进了母腹.\n','0','1','','7-106-01776-0','/uploadTmp/img/3jian1dengyuji_cover.jpg',NULL,'1','2016-03-07 00:39:10','1','2016-03-09 02:27:13','0'),
	(21,'天惶惶地惶惶',1,4,'0','0','2013','2016','4',20.0,NULL,NULL,'中国电影出版社','','就像你们一直在研究猩猩一样，我也一直在考察你们这种动物的特性，智商到底有多高，还考察你们人性中的东西。在这个地球上，我选一个人，选到了你。通过你，我对人类了如指掌。你为什么感到我熟悉呢？因为我跟着你20多年了。某年某月某天，一个女人出现在你的旁边，那天的天气很好，只是当时谁都没有朝上看，那一刻，太阳是黑色的……','0','1','','7-106-01856-2','/uploadTmp/img/thhdhh_cover.jpg',NULL,'1','2016-03-07 00:53:23','1','2016-03-07 10:43:46','0'),
	(22,'test作品',1,5,'0','3',NULL,'2016','1',12.0,NULL,NULL,'','','testetst','0','1','','','/uploadTmp/doc/20161007/1010_709870835aea48019c492b4cfecd9c90.png',NULL,'1','2016-10-07 07:27:46','1','2016-10-07 07:27:46',NULL),
	(23,'搜神传',1,4,'0','0','2015','2016','14',14.0,NULL,NULL,'什么什么出版社','','搜神传','0','1','','',NULL,'0','1','2016-01-17 23:43:07','1','2016-02-13 15:09:14','1'),
	(24,'相亲纪',1,3,'0','0','2013','2016','1',31.0,NULL,NULL,'西安交通大学出版社','','结婚，为什么那么难呢？ 想婚的时候，是该跟着感觉走，还是凑合过一生？不是青梅竹马，也非一见钟情，经不起爱情长跑，也闪不了火花。 想与一个人相亲相爱，白头到老，难道只能是童话？各色男人满眼过，竟无一人是知音？ 繁华都市，与无数相亲之人擦肩而过，也想披荆斩棘，找到属于自己的那个王子。那个Mr.Right会在何时出现？ 《相亲纪》的作者是宁余心。','0','1','','9787560539225',NULL,NULL,'1','2016-05-19 09:35:16','1','2016-05-19 09:35:16',NULL),
	(25,'测试暂存作品',1,8,'0','3','2016','2016','0',12.0,NULL,NULL,'','','测试暂存作品','0','1','','',NULL,NULL,'1','2016-06-02 06:27:45','1','2016-06-02 06:27:45',NULL),
	(26,'再测试暂存作品',1,7,'0','0','2016','2016','0',22.0,NULL,NULL,'中信出版社','','再测试暂存作品','0','1','','IS-899764253',NULL,NULL,'1','2016-06-02 06:33:41','1','2016-06-03 02:29:43',NULL),
	(27,'不能保存吗',1,3,'0','0','2016','2016','0',20.0,NULL,NULL,'中国出版公司','','不能保存吗','0','1','','ISBN000111',NULL,NULL,'1','2016-06-06 11:23:20','1','2016-06-06 11:23:20',NULL),
	(28,'犯罪侧写师2',1,3,'0','0','2016','2016','8',21.0,NULL,NULL,'现代出版社','','郑岩在失去挚爱后，远走美国避世，慕雪不离不弃，一直追随，努力指引他走出抑郁症，但与此同时，Z小组也失去主心骨，濒临解散。然而半年后，一系列匪夷所思罪案再度爆发，提线木偶杀人、烂尾楼藏尸、名为狩猎之鹰的凶手设下迷局挑战警方底线……危急时刻，郑岩毅然回国，重组Z小组，他凭借强大的推理天赋和犯罪侧写的专业知识，再度踏入黑暗，于凶险叵测中触摸罪犯的心理和真凶的踪迹，捍卫法律尊严。而这一次，郑岩又将面对怎样的对手和挑战？','0','1','','9787514344912',NULL,NULL,'1','2016-06-06 17:10:18','1','2016-06-13 09:21:56',NULL),
	(29,'亲密是孤独最好的解药',2,9,'0','0','2016','2016','4',16.0,NULL,NULL,'文汇出版社','','女人需要这本书，去参照出真爱的模样；男人需要这本书，去解读女人心藏的玄机。本书从男女两性的角度出发，告诉你，女人都是奢侈品，男人都是消费品，女人是更高级的动物，男人进化未完。怎样消费男人这种生物，秘诀就是：男人要性感，女人要勇敢，男人要做君王，女人要做“妖孽”。要么温柔似水，要么百炼成钢。当你成为女王（妖孽）的时候，幸福自然尾随而来！\n','0','1','','978-7-5496-1348-9',NULL,NULL,'1','2016-06-08 13:41:54','1','2016-06-16 14:18:06',NULL),
	(30,'随便建一个',2,11,'0','3',NULL,'2014','3',21.0,NULL,NULL,'','','随便建一个','0','1','','','/uploadTmp/doc/20160811/1_a62af9baa4b1495da4bd215c6f06e3d8.jpg',NULL,'1','2016-06-13 15:44:17','1','2016-08-17 02:00:23',NULL),
	(31,'上海王',2,11,'0','0','2016','2016','4',25.0,NULL,NULL,'四川文艺出版社','','十里洋场的老上海不知道发生过多少离奇古怪的故事，如果写到女人也多是小女人气，而这部小说却写了一个如男人般豪气强大的女人。主人公小月桂从小父母双亡，在舅舅舅妈家长大，不甘心在乡村里、在舅妈的苛刻中过日子。她挣脱了乡村，来到大上海，阴差阳错地与上海黑帮老大发生牵连，并与三代上海王产生复杂的情怨恩仇。小说摆脱了近年出版的上海背景小说大多是小姐小打算，小资小情调的套路。作者笔下的小月桂敢想敢做，有气魄，是个读之令人难忘的角色。','0','1','','9787541142772','/uploadTmp/doc/20160623/上海王封面_91209ce9f64d46e1919896f4ffd504b2.jpg',NULL,'1','2016-06-23 11:43:10','1','2016-06-23 11:46:23',NULL),
	(32,'大明首辅',2,11,'0','0','2016','2016','3',150.0,NULL,NULL,'长江文艺出版社','','“嘚嘚嘚……”一阵急促的马蹄声打破了清晨的宁静，只见十余骑快马从荆州府方向向石首县城绣林镇疾驰而来。\n　　这时是明朝第二个皇帝、明太祖朱元璋的嫡长孙朱允炆建文元年七月二十八日的早晨。刚刚升起的太阳红艳艳的，照得那山川大地郁郁葱葱，可是不到一会儿，一片灰色的云飘过来掩住了日头，那朝阳顿时失去了夺目的光彩，只剩下一张惨淡的白脸，悬挂在东方天空上。接着，那地平线上涌起了一方乌云，迅速地向天空升起，向四周漫延，显然一场风暴正由远处向这里刮来。看来，天要变了。\n　　望着那风云突起的天空和越来越近的快马，石首知县方自新忧心忡忡地对走在一队生员前列的一位年轻人说道：“弘济，等这场风暴过了再走行么？”\n　　“是啊，天要变了。”走在方知县旁边的石首县学宫教谕龚顺不禁担心地对那位年轻人说道，“风大浪大，行船危险，澹庵还是改日启程吧，反正乡试还有几天呢。”\n　　那位被称为“弘济”和“澹庵”的年轻人姓杨名溥，字弘济，号澹庵，是石首县生员，家住石首县西乡高陵岗，北距藕池镇仅二里之遥。今天，他和同窗几位应试生员一早起来，准备在县城北门口长江码头乘船，赴布政司所在地武昌府去参加湖广己卯科乡试。\n　　“请老爷、先生放心，这风暴不过一时而已。”杨溥望了望天空，又望了望快到近前的快马，淡定地说道，“乡试日期已近，学生等人不能再耽误了。”\n　　“吁——”杨溥话音刚落，只见那支马队奔到了眼前。那跑在最前面的穿戴着一身宫中内侍衣帽的年轻者，他勒住马，用鞭捎指着杨溥等人尖着公鸭嗓子喝问道：“你们是什么人？年纪轻轻的怎么还不应征入伍？”\n　　一听那人说话的声音，再看看那人的衣着，杨溥明白了：原来那人果真是皇宫中的一名内侍，他到石首来做什么？','0','1','','978-7-5354-7297-7','/uploadTmp/doc/20160809/大明首辅-书模_9446c74489e64f28861daa11237bbcc9.jpg',NULL,'1','2016-08-09 11:26:02','1','2016-08-09 11:30:12',NULL);

/*!40000 ALTER TABLE `t_product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_product_author
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product_author`;

CREATE TABLE `t_product_author` (
  `c_id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `c_product_id` bigint(11) NOT NULL,
  `c_author_id` bigint(20) NOT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_product_author` WRITE;
/*!40000 ALTER TABLE `t_product_author` DISABLE KEYS */;

INSERT INTO `t_product_author` (`c_id`, `c_product_id`, `c_author_id`, `c_creator`, `c_createtime`)
VALUES
	(1,1,42,1,'2018-04-26 00:00:00'),
	(2,2,43,1,'2018-04-26 00:00:00'),
	(3,1,35,1,'2018-04-26 00:00:00');

/*!40000 ALTER TABLE `t_product_author` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_product_info
# ------------------------------------------------------------

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
  `c_type` varchar(10) DEFAULT NULL,
  `c_stockin` varchar(2) DEFAULT NULL,
  `c_desc` varchar(512) DEFAULT NULL,
  `c_creator` bigint(20) NOT NULL,
  `c_createtime` datetime NOT NULL,
  `c_modifier` bigint(20) DEFAULT NULL,
  `c_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_product_info` WRITE;
/*!40000 ALTER TABLE `t_product_info` DISABLE KEYS */;

INSERT INTO `t_product_info` (`c_id`, `c_name`, `c_author_id`, `c_wordcount`, `c_subject_id`, `c_publish_state`, `c_isbn`, `c_press`, `c_type`, `c_stockin`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)
VALUES
	(1,'女心理师',42,'20',1,'1','978-7-5520-1609-3','上海社会科学院出版社','wz','0','aaa',1,'2018-04-04 14:55:03',1,'2018-04-25 17:05:27'),
	(2,'木乃伊',43,'32',1,'1','978-7-550-1609-21','','wz','1',NULL,1,'2018-04-11 11:14:43',1,'2018-04-24 15:15:28');

/*!40000 ALTER TABLE `t_product_info` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_product_picture
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product_picture`;

CREATE TABLE `t_product_picture` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_product_picture` WRITE;
/*!40000 ALTER TABLE `t_product_picture` DISABLE KEYS */;

INSERT INTO `t_product_picture` (`c_id`, `c_name`, `c_type`, `c_product_id`, `c_path`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`)
VALUES
	(1,'5.jpg','0',1,'http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180424/539fe4d5-0fbe-44e8-8011-9d47db8ad78f',NULL,1,'2018-04-24 13:46:30',NULL,NULL);

/*!40000 ALTER TABLE `t_product_picture` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_product_sample
# ------------------------------------------------------------

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



# Dump of table t_product_subject
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_product_subject`;

CREATE TABLE `t_product_subject` (
  `C_ID` varchar(32) NOT NULL,
  `C_PRODUCT_ID` varchar(32) NOT NULL,
  `C_SUBJECT_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_subject
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_subject` WRITE;
/*!40000 ALTER TABLE `t_subject` DISABLE KEYS */;

INSERT INTO `t_subject` (`C_ID`, `C_NAME`, `C_DESC`, `C_ORDER`, `C_TYPE`, `C_RATIO`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(1,'悬疑推理','悬疑推理',2,'0','1.0','1','2015-12-29 01:03:28','1','2016-05-12 00:42:57'),
	(2,'官场商战','官场商战',3,'0','1.0','1','2016-01-02 15:26:20',NULL,NULL),
	(3,'世情社会','世情社会',4,'0','1.0','1','2016-05-11 11:57:11','1','2016-05-12 00:43:09'),
	(4,'历史军事','历史军事',5,'0','1.0','1','2016-05-12 00:43:20',NULL,NULL),
	(5,'恐怖玄幻','恐怖玄幻',6,'0','1.0','1','2016-05-12 00:43:30',NULL,NULL),
	(6,'纪实探秘','纪实探秘',7,'0','1.0','1','2016-05-12 00:43:39',NULL,NULL),
	(7,'人物传记','人物传记',8,'0','1.0','1','2016-05-12 00:43:49',NULL,NULL),
	(8,'古风武侠','古风武侠',9,'0','1.0','1','2016-05-12 00:43:59',NULL,NULL),
	(9,'刑侦反腐','刑侦反腐',10,'0','1.0','1','2016-05-12 00:44:08',NULL,NULL),
	(10,'边塞农村','边塞农村',11,'0','1.0','1','2016-05-12 00:44:18',NULL,NULL),
	(11,'科普养生','科普养生',12,'0','1.0','1','2016-05-12 00:44:28',NULL,NULL),
	(12,'人文哲学','人文哲学',13,'0','1.0','1','2016-05-12 00:44:43',NULL,NULL),
	(13,'校园青春','校园青春',14,'0','1.0','1','2016-05-12 00:44:56',NULL,NULL),
	(14,'儿童文学','儿童文学',16,'0','1.0','1','2016-05-12 00:45:05','1','2018-02-22 23:26:37'),
	(15,'都市情感','都市情感',1,'0','1.6','1','2015-12-29 00:36:48','1','2016-11-02 00:09:17');

/*!40000 ALTER TABLE `t_subject` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_user
# ------------------------------------------------------------

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

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`C_ID`, `C_NAME`, `C_ACCOUNT`, `C_EMAIL`, `C_PASSWORD`, `C_MOBILE`, `C_ROLE`, `C_GENDER`, `C_HONORIFIC`, `C_LOGOURL`, `C_STATUS`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(1,'超级管理员','superman','9654589@qq.com','645A578B69FEB4C79AF2FD069F0E94BA','13581700369',NULL,'1','张老师',NULL,'1','1','2018-02-22 10:54:44',NULL,NULL),
	(2,'zzz','zzz','zzz@163.com','645A578B69FEB4C79AF2FD069F0E94BA','18500611339',NULL,'1','张老师',NULL,'1','1','2018-02-22 10:54:44','1','2018-02-22 18:54:16'),
	(3,'Yuki Wang','yyy','yyy@126.com','645A578B69FEB4C79AF2FD069F0E94BA','13012345678',NULL,'0','王老师',NULL,'0','1','2018-02-22 10:54:44',NULL,NULL);

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
