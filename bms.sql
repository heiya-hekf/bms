/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 5.7.37 : Database - bms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bms`;

/*Table structure for table `book_info` */

DROP TABLE IF EXISTS `book_info`;

CREATE TABLE `book_info` (
  `bookId` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(50) NOT NULL,
  `bookAuthor` varchar(50) NOT NULL,
  `bookPrice` decimal(10,2) NOT NULL,
  `bookTypeId` int(11) NOT NULL,
  `bookDesc` varchar(255) NOT NULL COMMENT '书籍描述',
  `isBorrowed` tinyint(4) NOT NULL COMMENT '1表示借出，0表示已还',
  `bookImg` varchar(255) DEFAULT NULL COMMENT '书籍图片',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifyTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`bookId`) USING BTREE,
  KEY `fk_book_info_book_type_1` (`bookTypeId`) USING BTREE,
  CONSTRAINT `book_info_ibfk_1` FOREIGN KEY (`bookTypeId`) REFERENCES `book_type` (`bookTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `book_info` */

insert  into `book_info`(`bookId`,`bookName`,`bookAuthor`,`bookPrice`,`bookTypeId`,`bookDesc`,`isBorrowed`,`bookImg`,`createTime`,`modifyTime`) values 
(1,'乌合之众','古斯塔夫·勒庞','88.80',3,'《乌合之众：大众心理研究》是法国社会心理学家古斯塔夫·勒庞创作的社会心理学著作，首次出版于1895年。\n《乌合之众：大众心理研究》是一本研究大众心理学的著作。在书中，勒庞阐述了群体以及群体心理的特征，指出了当个人是一个孤立的个体时，他有着自己鲜明的个性化特征，而当这个人融入了群体后，他的所有个性都会被这个群体所淹没，他的思想立刻就会被群体的思想所取代。而当一个群体存在时，他就有着情绪化、无异议、低智商等特征。',1,'http://localhost:6001/bms/pictures/1724595679105ff29a746927f1865df6513c0c7cfda6c(1).jpeg','2024-08-25 22:27:50','2024-08-26 10:23:50'),
(2,'黑悟空传','悟空','66.60',6,'黑悟空打怪升级',0,'http://localhost:6001/bms/pictures/17245955276366cb2d5723cf8e680628b66abbaac09ec.jpg','2024-08-25 22:27:50','2024-08-26 10:40:37'),
(75,'Java程序设计','耿祥义','10.00',1,'yyds',1,'http://localhost:6001/bms/pictures/17245969204710047c123647126879e39d7e65b40c87b.jpeg','2024-08-25 22:42:01','2024-08-26 09:08:51'),
(76,'红楼梦','曹雪芹1','35.50',1,'为闺阁立传',0,'http://wangpeng-imgsubmit.oss-cn-hangzhou.aliyuncs.com/img/202111131322401.jpg','2024-08-26 10:31:02','2024-08-26 10:40:42');

/*Table structure for table `book_type` */

DROP TABLE IF EXISTS `book_type`;

CREATE TABLE `book_type` (
  `bookTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `bookTypeName` varchar(20) NOT NULL,
  `bookTypeDesc` varchar(255) NOT NULL COMMENT '书籍类型描述',
  PRIMARY KEY (`bookTypeId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `book_type` */

insert  into `book_type`(`bookTypeId`,`bookTypeName`,`bookTypeDesc`) values 
(1,'计算机科学','计算机相关'),
(2,'历史','历史相关'),
(3,'文学','文学相关'),
(4,'科幻','科幻相关'),
(6,'小说','小说相关'),
(7,'外语','外语相关'),
(8,'校园','校园故事');

/*Table structure for table `borrow` */

DROP TABLE IF EXISTS `borrow`;

CREATE TABLE `borrow` (
  `borrowId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `borrowTime` datetime NOT NULL,
  `returnTime` datetime DEFAULT NULL,
  PRIMARY KEY (`borrowId`) USING BTREE,
  KEY `fk_borrow_user_1` (`userId`) USING BTREE,
  KEY `fk_borrow_book_info_1` (`bookId`) USING BTREE,
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book_info` (`bookId`),
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `borrow` */

insert  into `borrow`(`borrowId`,`userId`,`bookId`,`borrowTime`,`returnTime`) values 
(69,1,75,'2024-08-26 09:08:52',NULL),
(70,1,1,'2024-08-26 10:23:51',NULL),
(72,1,76,'2024-08-26 10:38:44','2024-08-26 10:40:42'),
(73,1,2,'2024-08-26 10:40:30','2024-08-26 10:40:37');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL,
  `userPassword` varchar(20) NOT NULL,
  `isAdmin` tinyint(4) NOT NULL COMMENT '1是管理员，0非管理员',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `user` */

insert  into `user`(`userId`,`userName`,`userPassword`,`isAdmin`) values 
(1,'admin','admin',1),
(2,'hekf','123456',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
