# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.17)
# Database: emars
# Generation Time: 2018-05-07 17:57:07 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


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
  `c_production_state` varchar(2) DEFAULT NULL,
  `c_section` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_product_info` WRITE;
/*!40000 ALTER TABLE `t_product_info` DISABLE KEYS */;

INSERT INTO `t_product_info` (`c_id`, `c_name`, `c_author_id`, `c_wordcount`, `c_subject_id`, `c_publish_state`, `c_isbn`, `c_press`, `c_type`, `c_stockin`, `c_desc`, `c_creator`, `c_createtime`, `c_modifier`, `c_modifytime`, `c_production_state`, `c_section`)
VALUES
	(1,'女心理师',42,'20',1,'1','978-7-5520-1609-3','上海社会科学院出版社','wz','0','',1,'2018-04-04 14:55:03',NULL,NULL,NULL,NULL),
	(2,'木乃伊',43,'32',1,'1','978-7-550-1609-21','','wz','1',NULL,1,'2018-04-11 11:14:43',1,'2018-04-24 15:15:28','5',NULL);

/*!40000 ALTER TABLE `t_product_info` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
