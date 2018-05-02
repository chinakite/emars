# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.17)
# Database: emars
# Generation Time: 2018-05-02 22:26:15 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_make_contract
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_make_contract`;

CREATE TABLE `t_make_contract` (
  `C_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `C_CODE` varchar(20) DEFAULT NULL,
  `C_TARGET_TYPE` varchar(20) DEFAULT NULL,
  `C_OWNER` varchar(255) DEFAULT NULL,
  `C_MAKER` varchar(255) DEFAULT NULL,
  `C_TOTAL_PRICE` decimal(10,0) DEFAULT NULL,
  `C_TOTAL_SECTION` int(11) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_make_contract` WRITE;
/*!40000 ALTER TABLE `t_make_contract` DISABLE KEYS */;

INSERT INTO `t_make_contract` (`C_ID`, `C_CODE`, `C_TARGET_TYPE`, `C_OWNER`, `C_MAKER`, `C_TOTAL_PRICE`, `C_TOTAL_SECTION`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(12,'M20180427-001','0','北京悦库时光文化传媒有限公司','北京金诺佳音国际文件传媒股份公司',23,34,1,'2018-04-27 17:28:31',NULL,NULL),
	(20,'M20180501-001','0','北京悦库时光文化传媒有限公司','北京星华秀文化传媒有限公司',444,444,1,'2018-05-01 16:41:31',NULL,NULL),
	(23,'M20180503-001','0','北京悦库时光文化传媒有限公司','1000',1000,1000,1,'2018-05-03 06:22:12',NULL,NULL);

/*!40000 ALTER TABLE `t_make_contract` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_make_contract_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_make_contract_product`;

CREATE TABLE `t_make_contract_product` (
  `C_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `C_MAKE_CONTRACT_ID` bigint(20) DEFAULT NULL,
  `C_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `C_PRICE` decimal(10,0) DEFAULT NULL,
  `C_SECTION` int(20) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  `C_WORKER` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_make_contract_product` WRITE;
/*!40000 ALTER TABLE `t_make_contract_product` DISABLE KEYS */;

INSERT INTO `t_make_contract_product` (`C_ID`, `C_MAKE_CONTRACT_ID`, `C_PRODUCT_ID`, `C_PRICE`, `C_SECTION`, `C_CREATOR`, `C_CREATETIME`, `C_MODIFIER`, `C_MODIFYTIME`, `C_WORKER`)
VALUES
	(7,12,2,2000000,20,1,'2018-04-27 17:28:31',NULL,NULL,'步腾飞（迷茫人）'),
	(8,12,1,8000080,20,1,'2018-04-27 17:28:31',NULL,NULL,'晏积瑄（晓晏）'),
	(9,20,1,28807379,10,1,'2018-05-01 16:41:31',NULL,NULL,'晏积瑄（晓晏）'),
	(13,23,1,1000,1000,1,'2018-05-03 06:22:12',NULL,NULL,'小花、小草');

/*!40000 ALTER TABLE `t_make_contract_product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_make_ctrt_doc
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_make_ctrt_doc`;

CREATE TABLE `t_make_ctrt_doc` (
  `C_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `C_MAKE_CONTRACT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `C_TYPE` varchar(2) DEFAULT NULL,
  `C_CREATOR` bigint(20) DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_NAME` varchar(500) DEFAULT NULL,
  `C_PATH` varchar(500) DEFAULT NULL,
  `C_DESC` varchar(500) DEFAULT NULL,
  `C_MODIFIER` bigint(20) DEFAULT NULL,
  `C_MODIFYTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_make_ctrt_doc` WRITE;
/*!40000 ALTER TABLE `t_make_ctrt_doc` DISABLE KEYS */;

INSERT INTO `t_make_ctrt_doc` (`C_ID`, `C_MAKE_CONTRACT_PRODUCT_ID`, `C_TYPE`, `C_CREATOR`, `C_CREATETIME`, `C_NAME`, `C_PATH`, `C_DESC`, `C_MODIFIER`, `C_MODIFYTIME`)
VALUES
	(2,7,'1',1,'2018-05-02 19:34:36','demo_thumb_1.jpg',NULL,NULL,NULL,NULL),
	(4,8,'1',1,'2018-05-02 20:29:11','demo_thumb_3.jpg',NULL,NULL,NULL,NULL),
	(6,7,'2',1,'2018-05-03 03:04:22','demo_thumb_1.jpg','http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180503/9d8aa898-d1f4-4dbd-80be-a97997d1df6b',NULL,NULL,NULL),
	(7,8,'1',1,'2018-05-03 03:08:10','demo_thumb_2.jpg','http://emars-dev.oss-cn-hangzhou.aliyuncs.com/20180503/2d58d16b-6709-4a28-b801-f838db06275b',NULL,NULL,NULL);

/*!40000 ALTER TABLE `t_make_ctrt_doc` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
