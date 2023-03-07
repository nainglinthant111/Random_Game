-- MariaDB dump 10.19  Distrib 10.6.8-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Random_Game
-- ------------------------------------------------------
-- Server version	10.6.8-MariaDB-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gold`
--

DROP TABLE IF EXISTS `gold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gold` (
  `id` varchar(30) NOT NULL,
  `game_coil` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gold`
--

LOCK TABLES `gold` WRITE;
/*!40000 ALTER TABLE `gold` DISABLE KEYS */;
INSERT INTO `gold` VALUES ('naing1',103653),('phoo',127),('phoophoo',100),('zin111',1520),('zinzin',100);
/*!40000 ALTER TABLE `gold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_coin`
--

DROP TABLE IF EXISTS `order_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_coin` (
  `oderid` int(11) NOT NULL AUTO_INCREMENT,
  `mmk` varchar(30) DEFAULT NULL,
  `commend` varchar(50) DEFAULT NULL,
  `id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`oderid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_coin`
--

LOCK TABLES `order_coin` WRITE;
/*!40000 ALTER TABLE `order_coin` DISABLE KEYS */;
INSERT INTO `order_coin` VALUES (1,'500','ok','zin111'),(4,'500','ok','zin111'),(10,'23','kpay','zin111'),(11,'30','kpay','zin111'),(13,'77','kkl;','phoo');
/*!40000 ALTER TABLE `order_coin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `result` (
  `resultid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `result` enum('win','lose') DEFAULT NULL,
  PRIMARY KEY (`resultid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` VALUES (1,'zin111','Zin  Phoo Mon','win'),(2,'zin111','Zin Phoo Mon','win'),(3,'zin111','','lose'),(4,'zin111','','win'),(5,'zin111','Zin Phoo Mon','lose'),(6,'zin111','Zin Phoo Mon','lose'),(7,'zin111','Zin Phoo Mon','lose'),(8,'zin111','Zin Phoo Mon','lose'),(9,'zin111','Zin Phoo Mon','lose'),(10,'phoo','phoo','lose'),(11,'phoo','phoo','lose'),(12,'phoo','phoo','lose'),(13,'phoo','phoo','lose'),(14,'phoo','phoo','lose');
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `u_type` enum('USER','ADMIN') DEFAULT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `phoneno` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('naing1','Naing Lin Thant','123456','ADMIN','MALE','nainglin@gmail.com','123456789'),('phoo','phoo','phoo66','USER','FEMALE','phoo@gmail.com','09 789 224 227'),('phoophoo','Zin Phoo Mon','123456','USER','FEMALE','phoophoo@gmail.com','12345678654'),('zin111','Zin Phoo Mon','123456','USER','FEMALE','zin@gmail.com','23456787654'),('zinzin','zin zin','123456','USER','FEMALE','zin23@gmail.com','345678987');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-19 23:18:14
