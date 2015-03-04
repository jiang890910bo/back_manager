/*
SQLyog Ultimate v8.71 
MySQL - 5.6.19 : Database - cjf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cjf` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `cnvp_sys_dept` */

DROP TABLE IF EXISTS `cnvp_sys_dept`;

CREATE TABLE `cnvp_sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `cname` varchar(200) NOT NULL COMMENT '名称',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `cnvp_sys_dept` */

insert  into `cnvp_sys_dept`(`id`,`cname`,`desc`,`pid`) values (1,'杭州捷点',NULL,0),(16,'电商',NULL,1),(14,'技术部',NULL,15),(15,'科技',NULL,1),(8,'客服部',NULL,15),(9,'商务部',NULL,15),(17,'A部门',NULL,16),(18,'B部门',NULL,16);

/*Table structure for table `cnvp_sys_nav` */

DROP TABLE IF EXISTS `cnvp_sys_nav`;

CREATE TABLE `cnvp_sys_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  `title` varchar(100) NOT NULL COMMENT '文字',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `url` varchar(100) NOT NULL COMMENT '链接',
  `target` varchar(20) NOT NULL DEFAULT 'mainFrame' COMMENT '目标',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级id',
  `orderid` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='导航';

/*Data for the table `cnvp_sys_nav` */

insert  into `cnvp_sys_nav`(`id`,`title`,`icon`,`url`,`target`,`pid`,`orderid`) values (1,'开始','icon-home','javascript:;','mainFrame',0,10),(3,'系统','icon-cog','javascript:;','mainFrame',0,10),(33,'组织结构','icon-sitemap','/Dept','mainFrame',3,2),(20,'模型生成','icon-code','Generator/model','mainFrame',2,10),(17,'欢迎使用','icon-send','welcome','mainFrame',1,10),(18,'个人资料','icon-user','profile','mainFrame',1,10),(19,'修改密码','icon-key','password','mainFrame',1,10),(2,'开发','icon-codepen','javascript:;','mainFrame',0,10),(15,'12312312','312','3123123','mainFrame',123,10),(21,'视图生成','icon-code','Generator/view','mainFrame',2,10),(22,'控制器生成','icon-code','Generator/controller','mainFrame',2,10),(23,'系统设置','icon-desktop','System','mainFrame',3,1),(24,'导航菜单','icon-th-list','System/nav','mainFrame',3,6),(26,'文件管理','icon-file','System/file','mainFrame',3,7),(25,'权限配置','icon-asterisk','System/permission','mainFrame',3,8),(27,'日志查询','icon-angle-double-right','System/log','mainFrame',3,9),(32,'用户管理','icon-user','User','mainFrame',3,3),(34,'角色管理','icon-group','Role','mainFrame',3,4),(35,'资源管理','icon-unlock-alt','Resource','mainFrame',3,5);

/*Table structure for table `cnvp_sys_res` */

DROP TABLE IF EXISTS `cnvp_sys_res`;

CREATE TABLE `cnvp_sys_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统Id',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级Id',
  `cname` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '权限识别码',
  `code_route` varchar(500) DEFAULT NULL COMMENT '权限路径',
  `des` varchar(200) DEFAULT NULL COMMENT '描述',
  `ak` varchar(200) DEFAULT NULL COMMENT 'actionKey',
  `seq` int(11) DEFAULT '10' COMMENT '排序',
  `type` tinyint(2) DEFAULT NULL COMMENT '1、actionKey；',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Data for the table `cnvp_sys_res` */

insert  into `cnvp_sys_res`(`id`,`pid`,`cname`,`code`,`code_route`,`des`,`ak`,`seq`,`type`) values (1,0,'开始','start','start','开始',NULL,10,0),(2,0,'系统','system','system',NULL,NULL,10,0),(3,2,'系统设置','config','system:config',NULL,'/System',10,1),(47,2,'组织机构','dept','system:dept',NULL,NULL,10,0),(48,47,'首页','index','system:dept:index',NULL,'/Dept',10,1),(49,47,'抽取json数据','getlist','system:dept:getlist',NULL,'/Dept/getlist',10,1),(50,47,'创建','create','system:dept:create',NULL,'/Dept/create',10,1),(51,47,'更新','update','system:dept:update',NULL,'/Dept/update',10,1),(52,47,'删除','delete','system:dept:delete',NULL,'/Dept/delete',10,1),(53,2,'用户管理','user','system:user',NULL,NULL,10,0),(54,53,'列表','index','system:user:index',NULL,'/User',10,1),(55,53,'添加','create','system:user:create',NULL,'/User/create',10,1),(56,53,'修改','update','system:user:update',NULL,'/User/update',10,1),(57,53,'删除','delete','system:user:delete',NULL,'/User/delete',10,1),(58,53,'批量删除','deleteall','system:user:deleteall',NULL,'/User/deleteAll',10,1),(59,2,'角色管理','role','system:role',NULL,NULL,10,0),(60,59,'列表','index','system:role:index',NULL,'/Role',10,1),(61,59,'添加','create','system:role:create',NULL,'/Role/create',10,1),(62,59,'更新','update','system:role:update',NULL,'/Role/update',10,1),(63,59,'删除','delete','system:role:delete',NULL,'/Role/delete',10,1),(64,59,'批量删除','deleteAll','system:role:deleteAll',NULL,'/Role/deleteAll',10,1),(65,59,'抽取JSON数据','getlist','system:role:getlist',NULL,'/Role/getlist',10,1),(66,2,'资源管理','res','system:res',NULL,NULL,10,0),(67,66,'首页','index','system:res:index',NULL,'/Resource',10,1),(68,66,'添加','create','system:res:create',NULL,'/Role/create',10,1),(69,66,'更新','update','system:res:update',NULL,'/Resource/update',10,1),(70,66,'删除','delete','system:res:delete',NULL,'/Resource/delete',10,1),(71,66,'抽取JSON数据','getlist','system:res:getlist',NULL,'/Resource/getlist',10,1),(72,2,'导航管理','sysnav','system:sysnav',NULL,NULL,10,0),(73,72,'首页','index','system:sysnav:index',NULL,'/System/nav',10,1),(74,72,'添加','create','system:sysnav:create',NULL,'/System/nav_create',10,1),(75,72,'更新','update','system:sysnav:update',NULL,'/System/nav_update',10,1),(76,72,'删除','delete','system:sysnav:delete',NULL,'/System/nav_delete',10,1),(77,72,'保存排序','saveorder','system:sysnav:saveorder',NULL,'/System/save_order',10,1),(78,1,'欢迎使用','welcome','start:welcome',NULL,'/welcome',10,1),(79,1,'个人资料','profile','start:profile',NULL,'/profile',10,1),(80,1,'修改密码','password','start:password',NULL,'/password',10,1),(81,0,'开发','dev','dev',NULL,NULL,10,0),(82,81,'首页','index','dev:index',NULL,'/Generator',10,1),(83,81,'模型代码','model','dev:model',NULL,'/Generator/model_code',10,1),(84,81,'控制器代码','controller','dev:controller',NULL,'/Generator/controller_code',10,1),(85,81,'视图代码','view','dev:view',NULL,'/Generator/view_code',10,1),(86,59,'配置资源权限','set_res','system:role:set_res',NULL,'/Role/set_res',10,1),(87,0,'管理首页','frame','frame',NULL,'/',0,1);

/*Table structure for table `cnvp_sys_role` */

DROP TABLE IF EXISTS `cnvp_sys_role`;

CREATE TABLE `cnvp_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `cname` varchar(20) NOT NULL COMMENT '角色名称',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cnvp_sys_role` */

insert  into `cnvp_sys_role`(`id`,`cname`,`pid`) values (1,'普通用户',0),(4,'系统管理员',0);

/*Table structure for table `cnvp_sys_role_res` */

DROP TABLE IF EXISTS `cnvp_sys_role_res`;

CREATE TABLE `cnvp_sys_role_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1328 DEFAULT CHARSET=utf8;

/*Data for the table `cnvp_sys_role_res` */

insert  into `cnvp_sys_role_res`(`id`,`role_id`,`res_id`) values (1322,4,86),(1321,4,85),(1320,4,84),(1319,4,83),(1318,4,82),(1317,4,81),(1316,4,80),(1315,4,79),(1314,4,78),(1313,4,77),(1312,4,76),(1311,4,75),(1310,4,74),(1309,4,73),(1308,4,72),(1307,4,71),(1306,4,70),(1305,4,69),(1304,4,68),(1303,4,67),(1302,4,66),(1301,4,65),(1300,4,64),(1299,4,63),(1298,4,62),(1297,4,61),(1296,4,60),(1295,4,59),(1294,4,58),(1293,4,57),(1292,4,56),(1291,4,55),(1290,4,54),(1289,4,53),(1288,4,52),(1287,4,51),(1286,4,50),(1285,4,49),(1284,4,48),(1283,4,47),(1282,4,3),(1281,4,2),(1280,4,1),(1327,1,47),(1326,1,1),(1325,1,87),(1324,4,87);

/*Table structure for table `cnvp_sys_user` */

DROP TABLE IF EXISTS `cnvp_sys_user`;

CREATE TABLE `cnvp_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号',
  `account` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `sex` enum('男','女') DEFAULT NULL COMMENT '性别',
  `cname` varchar(10) DEFAULT NULL COMMENT '中文名',
  `ename` varchar(50) DEFAULT NULL COMMENT '英文名',
  `dept_id` int(11) DEFAULT NULL COMMENT '所在部门',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `flg` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `last_login_time` bigint(13) DEFAULT NULL COMMENT '最后登录时间',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户';

/*Data for the table `cnvp_sys_user` */

insert  into `cnvp_sys_user`(`id`,`account`,`password`,`sex`,`cname`,`ename`,`dept_id`,`mobile`,`flg`,`remark`,`login_count`,`last_login_time`,`create_time`,`create_user_id`,`update_time`,`update_user_id`) values (1,'superadmin','a8f5f167f44f4964e6c998dee827110c','男','超级管理员-章宵','michael.z',14,'15990061612',1,NULL,60,1413779275,0,1,1425458171011,1),(4,'admin','a8f5f167f44f4964e6c998dee827110c','男','章宵','mic',1,'15990061612',1,NULL,2,1406045477,0,1,1425461391895,1);

/*Table structure for table `cnvp_sys_user_role` */

DROP TABLE IF EXISTS `cnvp_sys_user_role`;

CREATE TABLE `cnvp_sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `cnvp_sys_user_role` */

insert  into `cnvp_sys_user_role`(`user_id`,`role_id`) values (1,1),(1,4),(4,1),(9,1),(9,4),(10,1),(11,1),(11,4),(12,1),(13,1),(14,1),(15,1),(16,1),(18,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
