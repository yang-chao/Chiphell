CREATE DATABASE  IF NOT EXISTS `cral_chh` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cral_chh`;
-- MySQL dump 10.13  Distrib 5.6.17, for osx10.6 (i386)
--
-- Host: localhost    Database: cral_chh
-- ------------------------------------------------------
-- Server version	5.5.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `link` varchar(100) NOT NULL,
  `time` datetime DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `message_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idnews_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (197,'9 月，手机界注定又是一片“血雨腥风”','http://www.chiphell.com/thread-1112910-1-1.html','2014-08-24 19:57:00','通讯科技','jasonhu924',31),(198,'美国男子盗版《速度与激情6》被判处33个月监禁','http://www.chiphell.com/thread-1112774-1-1.html','2014-08-24 16:35:00','其他','hcj19890820',14),(199,'好玩不贵”华为荣耀平板登场：799元起','http://www.chiphell.com/thread-1112978-1-1.html','2014-08-24 21:23:00','PC硬件','napan',18),(200,'魅族MX4真机清晰照曝光！','http://www.chiphell.com/thread-1112767-1-1.html','2014-08-24 16:26:00','通讯科技','lipha',29),(201,'苹果iPhone6充电器谍照曝光：变化巨大','http://www.chiphell.com/thread-1112623-1-1.html','2014-08-24 12:25:00','通讯科技','napan',24),(202,'EA公司会对玩家在《FIFA 15》的作弊行为进行封号处理','http://www.chiphell.com/thread-1112852-1-1.html','2014-08-24 18:01:00','游戏电玩','lipha',8),(203,'599元起售！华为荣耀3C畅玩版正式发布','http://www.chiphell.com/thread-1112977-1-1.html','2014-08-24 21:23:00','通讯科技','napan',10),(204,'真心罕见！AMD APU要大批降价 神器降临','http://www.chiphell.com/thread-1112811-1-1.html','2014-08-24 17:09:00','PC硬件','lipha',43),(205,'探访全美最大的电子游戏厅 15元随便玩','http://www.chiphell.com/thread-1112981-1-1.html','2014-08-24 21:27:00','游戏电玩','napan',20),(206,'最新数据显示iOS 7安装率达到空前的高度','http://www.chiphell.com/thread-1111910-1-1.html','2014-08-23 08:59:00','系统软件','中华图腾',17),(207,'部分Surface Pro 3 i7型号出现过热问题','http://www.chiphell.com/thread-1112890-1-1.html','2014-08-24 19:14:00','其他','117117',24),(208,'AMD官方确认！GCN架构显卡都支持DX12','http://www.chiphell.com/thread-1112588-1-1.html','2014-08-24 11:32:00','PC硬件','天际星辰',41),(209,'MX4 官方彩色后盖最新曝光','http://www.chiphell.com/thread-1112502-1-1.html','2014-08-24 08:30:00','通讯科技','stojaba',21),(210,'关于iPhone 6屏幕分辨率 哪种推测更靠谱？','http://www.chiphell.com/thread-1112982-1-1.html','2014-08-24 21:28:00','通讯科技','napan',5),(211,'有图有真相:拿手摸一摸电脑 黑客盗走数据','http://www.chiphell.com/thread-1112781-1-1.html','2014-08-24 16:40:00','其他','lipha',14),(212,'谷歌模块化手机更换处理器 瑞芯微中标','http://www.chiphell.com/thread-1112884-1-1.html','2014-08-24 19:06:00','通讯科技','lipha',11),(213,'这些图片告诉你Android操作系统的最大悲剧','http://www.chiphell.com/thread-1112787-1-1.html','2014-08-24 16:47:00','系统软件','lipha',36),(214,'仅路由器大小的PC：Acer Veriton N4630G','http://www.chiphell.com/thread-1112889-1-1.html','2014-08-24 19:14:00','PC硬件','117117',15),(215,'AMD R9 285正式发布：功耗怒赞啊！','http://www.chiphell.com/thread-1112564-1-1.html','2014-08-24 10:43:00','PC硬件','天际星辰',59),(216,'高通同意就涉嫌违法行为作出改进','http://www.chiphell.com/thread-1111760-1-1.html','2014-08-22 22:07:00','通讯科技','napan',13),(217,'富士康泄密：iPhone6更长更宽 厚度最薄6.9mm','http://www.chiphell.com/thread-1112881-1-1.html','2014-08-24 19:01:00','其他','一叶孤城',25),(218,'央视曝光移动电源容量虚标：小米上榜','http://www.chiphell.com/thread-1112806-1-1.html','2014-08-24 17:04:00','其他','lipha',59),(219,'iPhone 6屏幕分辨率竟然这么夸张？','http://www.chiphell.com/thread-1112601-1-1.html','2014-08-24 11:50:00','通讯科技','napan',53),(220,'有史以来最恐怖的10款游戏','http://www.chiphell.com/thread-1102089-1-1.html','2014-08-10 10:36:00','游戏电玩','napan',47),(221,'iPhone 6基带确认：你失望吗？','http://www.chiphell.com/thread-1112571-1-1.html','2014-08-24 10:57:00','通讯科技','napan',47),(222,'高达破坏者2（Gundam Breaker 2）公布！今冬登陆PSV、PS3双...','http://www.chiphell.com/thread-1112847-1-1.html','2014-08-24 17:56:00','游戏电玩','lipha',12),(223,'育碧怒批任天堂：好好做游戏出什么DLC','http://www.chiphell.com/thread-1112849-1-1.html','2014-08-24 17:58:00','游戏电玩','lipha',24),(224,'Windows Phone：更多高端设备即将亮相','http://www.chiphell.com/thread-1112538-1-1.html','2014-08-24 10:01:00','通讯科技','117117',15),(225,'华为Ascend P7摊上事了：为跑分而“优化”','http://www.chiphell.com/thread-1111385-1-1.html','2014-08-22 12:47:00','通讯科技','canbe',22),(226,'WP8.1 GDR2要来？WP8.1新版本跳跃','http://www.chiphell.com/thread-1112642-1-1.html','2014-08-24 12:57:00','通讯科技','天际星辰',12),(227,'诺基亚Lumia 820正式开启Cyan全球推送','http://www.chiphell.com/thread-1112611-1-1.html','2014-08-24 12:05:00','通讯科技','117117',16),(228,'B&O发布豪华2.1声道无线音响 售价超万元','http://www.chiphell.com/thread-1112518-1-1.html','2014-08-24 09:24:00','PC硬件','stojaba',14),(229,'狂飙3GB/s 浦科特M6 Pro固态硬盘评测','http://www.chiphell.com/thread-1105493-1-1.html','2014-08-14 15:17:00','PC硬件','117117',58),(230,'九月智能手机大战在即：苹果三星拼大屏','http://www.chiphell.com/thread-1112796-1-1.html','2014-08-24 16:56:00','其他','hcj19890820',9),(231,'华为荣耀新品曝光：真是好玩不贵','http://www.chiphell.com/thread-1112655-1-1.html','2014-08-24 13:21:00','通讯科技','napan',13),(232,'Win9，逼出来的','http://www.chiphell.com/thread-1112378-1-1.html','2014-08-23 22:29:00','通讯科技','hcj19890820',94),(233,'没有谷歌和iPhone：十年前的数码产品长啥样？','http://www.chiphell.com/thread-1112504-1-1.html','2014-08-24 08:37:00','其他','stojaba',25),(234,'意想不到：索尼已成专业表情符外包制作商','http://www.chiphell.com/thread-1112634-1-1.html','2014-08-24 12:46:00','通讯科技','天际星辰',11),(235,'Windows 9完工时间曝光：马上就会泄露','http://www.chiphell.com/thread-1112801-1-1.html','2014-08-24 17:02:00','系统软件','lipha',14),(236,'盛大元老谭群钊再创业：构建线上私人博物馆','http://www.chiphell.com/thread-1112792-1-1.html','2014-08-24 16:52:00','其他','hcj19890820',4),(237,'爱奇艺诉优酷风行侵权要求赔偿','http://www.chiphell.com/thread-1112794-1-1.html','2014-08-24 16:54:00','其他','hcj19890820',8),(238,'各大手机厂商都是如何改造Android系统的','http://www.chiphell.com/thread-1112784-1-1.html','2014-08-24 16:44:00','通讯科技','lipha',7),(239,'云游戏也可缓存，看看微软Delorean预测 技术怎么做的？','http://www.chiphell.com/thread-1112843-1-1.html','2014-08-24 17:44:00','其他','lipha',4),(240,'尼康将发新款全幅单反D750 继任D700提升对焦','http://www.chiphell.com/thread-1112217-1-1.html','2014-08-23 17:16:00','其他','hcj19890820',44),(241,'最新盘古越狱有问题 大神建议用户暂停更新','http://www.chiphell.com/thread-1112006-1-1.html','2014-08-23 11:28:00','系统软件','false',10),(242,'最强Galaxy S5国际版发布：游戏神器','http://www.chiphell.com/thread-1110665-1-1.html','2014-08-21 13:37:00','通讯科技','天际星辰',18),(243,'中移动摊派员工销售手机：每人最少6台','http://www.chiphell.com/thread-1112775-1-1.html','2014-08-24 16:36:00','其他','hcj19890820',19);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-28 23:53:36
