-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: PC_Shop
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `street_address` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Không rõ hãng','Unknown'),(2,'Hãng sản xuất CPU và công nghệ máy tính','AMD'),(3,'Hãng sản xuất GPU chuyên dụng cho đồ họa và AI','NVIDIA'),(4,'Hãng sản xuất CPU và các linh kiện điện tử','Intel'),(5,'Hãng sản xuất các linh kiện máy tính và phần cứng điện tử','ASUS'),(6,NULL,'Macbook'),(7,NULL,'Acer'),(9,NULL,'Msi'),(10,NULL,'Dell'),(11,NULL,'Hp');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` varchar(255) NOT NULL,
  `add_at` datetime(6) DEFAULT NULL,
  `quantity` smallint NOT NULL,
  `cart_id` bigint DEFAULT NULL,
  `color_id` tinyint DEFAULT NULL,
  `laptop_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  KEY `FKg4qoihenxb0iroyhs566xehur` (`color_id`),
  KEY `FKhw3dg54fccf85rf3qil5bcdgi` (`laptop_id`),
  CONSTRAINT `FKg4qoihenxb0iroyhs566xehur` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`),
  CONSTRAINT `FKhw3dg54fccf85rf3qil5bcdgi` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`),
  CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK64t7ox312pqal3p7fg9o503c2` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (3,'Đồ họa'),(2,'Gaming'),(1,'Văn Phòng');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` VALUES (1,'Đen'),(2,'Trắng'),(3,'Vàng'),(4,'Xám');
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversations`
--

DROP TABLE IF EXISTS `conversations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `last_message_at` datetime(6) DEFAULT NULL,
  `admin_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsj26737n7qbrh551amdiwmw72` (`admin_id`),
  KEY `FKpltqvfcbkql9svdqwh0hw4g1d` (`user_id`),
  CONSTRAINT `FKpltqvfcbkql9svdqwh0hw4g1d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsj26737n7qbrh551amdiwmw72` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversations`
--

LOCK TABLES `conversations` WRITE;
/*!40000 ALTER TABLE `conversations` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cpu_techs`
--

DROP TABLE IF EXISTS `cpu_techs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cpu_techs` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgoc7yjyxhe6wkdyphuvq1dd8w` (`brand_id`),
  CONSTRAINT `FKgoc7yjyxhe6wkdyphuvq1dd8w` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cpu_techs`
--

LOCK TABLES `cpu_techs` WRITE;
/*!40000 ALTER TABLE `cpu_techs` DISABLE KEYS */;
INSERT INTO `cpu_techs` VALUES (1,'Ryzen 9 9950X',2),(2,'Intel Core i3-12100F',4),(3,'Intel Core i3',4),(4,'Intel Core i5-12400F',4),(5,'Apple M5',6),(6,'Intel Core i5 Raptor Lake - 13420H',9);
/*!40000 ALTER TABLE `cpu_techs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cpus`
--

DROP TABLE IF EXISTS `cpus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cpus` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `cache` float DEFAULT NULL,
  `core` tinyint DEFAULT NULL,
  `max_speed` float DEFAULT NULL,
  `model` varchar(255) NOT NULL,
  `speed` float DEFAULT NULL,
  `thread` tinyint DEFAULT NULL,
  `tops` smallint DEFAULT NULL,
  `technology_id` smallint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3wcel6sk5b9swtrc99xqsa534` (`technology_id`),
  CONSTRAINT `FK3wcel6sk5b9swtrc99xqsa534` FOREIGN KEY (`technology_id`) REFERENCES `cpu_techs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cpus`
--

LOCK TABLES `cpus` WRITE;
/*!40000 ALTER TABLE `cpus` DISABLE KEYS */;
INSERT INTO `cpus` VALUES (1,64,16,5.7,'Ryzen 9 9950X',3.5,32,1200,1),(2,12,4,4.3,'i3-12100F',3.3,8,600,2),(3,12,4,4.3,'i3-10105F',3,4,600,3),(4,18,6,4.4,'Core i5-12400F',2.5,12,800,4),(5,4,4,2,'Apple M5',4,4,0,5),(6,14320,8,12,'Intel Core i5 Raptor Lake - 13420H',8,12,3,6);
/*!40000 ALTER TABLE `cpus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gpus`
--

DROP TABLE IF EXISTS `gpus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gpus` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `memory` varchar(255) DEFAULT NULL,
  `model` varchar(255) NOT NULL,
  `tops` smallint DEFAULT NULL,
  `type` tinyint NOT NULL,
  `brand_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqlrryrtiosfvbmshoyhv2mi4b` (`brand_id`),
  CONSTRAINT `FKqlrryrtiosfvbmshoyhv2mi4b` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `gpus_chk_1` CHECK ((`type` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gpus`
--

LOCK TABLES `gpus` WRITE;
/*!40000 ALTER TABLE `gpus` DISABLE KEYS */;
INSERT INTO `gpus` VALUES (1,'24GB','RTX 5090',2000,0,3),(2,'4GB','RX 6500XT',1500,0,5),(3,'2GB','GT1030',1000,0,3),(4,'8GB','RTX 4060',1700,0,3);
/*!40000 ALTER TABLE `gpus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laptop_category`
--

DROP TABLE IF EXISTS `laptop_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laptop_category` (
  `laptop_id` int NOT NULL,
  `category_id` tinyint NOT NULL,
  PRIMARY KEY (`laptop_id`,`category_id`),
  KEY `FKsyliq2wfw7urfgs07b7kfescp` (`category_id`),
  CONSTRAINT `FKsyliq2wfw7urfgs07b7kfescp` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FKtlp6kds8vt66jqdm9ieg6cjji` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laptop_category`
--

LOCK TABLES `laptop_category` WRITE;
/*!40000 ALTER TABLE `laptop_category` DISABLE KEYS */;
INSERT INTO `laptop_category` VALUES (2,1),(5,1),(7,1),(8,1),(4,2),(9,2),(1,3),(3,3),(6,3),(8,3);
/*!40000 ALTER TABLE `laptop_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laptop_colors`
--

DROP TABLE IF EXISTS `laptop_colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laptop_colors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` smallint NOT NULL,
  `color_id` tinyint NOT NULL,
  `laptop_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj89d42efhm1jsp1m9iwtevu52` (`color_id`),
  KEY `FK2pklaap6u2i74wlpf8n7kkq9c` (`laptop_id`),
  CONSTRAINT `FK2pklaap6u2i74wlpf8n7kkq9c` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`),
  CONSTRAINT `FKj89d42efhm1jsp1m9iwtevu52` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laptop_colors`
--

LOCK TABLES `laptop_colors` WRITE;
/*!40000 ALTER TABLE `laptop_colors` DISABLE KEYS */;
INSERT INTO `laptop_colors` VALUES (1,12,1,1),(2,0,1,2),(3,6,1,3),(4,7,1,4),(5,3,1,5),(6,15,1,6),(7,99,1,7),(8,8,1,8),(9,40,1,9),(10,96,1,10),(11,94,1,11),(12,30,1,12),(13,18,1,13),(14,5,1,14),(15,27,1,15),(16,12,1,1),(17,11,2,1),(18,49,2,2),(19,29,2,3),(20,50,1,4),(21,20,3,5),(22,50,2,6),(23,99,1,7),(24,15,4,8);
/*!40000 ALTER TABLE `laptop_colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laptop_gpu`
--

DROP TABLE IF EXISTS `laptop_gpu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laptop_gpu` (
  `laptop_id` int NOT NULL,
  `gpu_id` smallint NOT NULL,
  PRIMARY KEY (`laptop_id`,`gpu_id`),
  KEY `FK6seg409eedjj0rvh5mq1c08yp` (`gpu_id`),
  CONSTRAINT `FK6seg409eedjj0rvh5mq1c08yp` FOREIGN KEY (`gpu_id`) REFERENCES `gpus` (`id`),
  CONSTRAINT `FKn5y2j6x0mhi0ilhe8psi6vrd5` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laptop_gpu`
--

LOCK TABLES `laptop_gpu` WRITE;
/*!40000 ALTER TABLE `laptop_gpu` DISABLE KEYS */;
INSERT INTO `laptop_gpu` VALUES (1,1),(8,1),(2,2),(3,2),(6,2),(7,2),(4,4),(5,4),(9,4);
/*!40000 ALTER TABLE `laptop_gpu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laptop_images`
--

DROP TABLE IF EXISTS `laptop_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laptop_images` (
  `laptop_id` int NOT NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  KEY `FKmnmdullw7da8qdn5vl0k5uyhd` (`laptop_id`),
  CONSTRAINT `FKmnmdullw7da8qdn5vl0k5uyhd` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laptop_images`
--

LOCK TABLES `laptop_images` WRITE;
/*!40000 ALTER TABLE `laptop_images` DISABLE KEYS */;
INSERT INTO `laptop_images` VALUES (2,'/images/laptop/2/1767012738488_macbook-pro-14-inch-m5-5-638962510932462355-180x125.jpg'),(2,'/images/laptop/2/1767012738489_macbook-pro-14-inch-m5-6-638962510938833801-180x125.jpg'),(2,'/images/laptop/2/1767012738484_macbook-pro-14-inch-m5-2-638962510920205187-180x125.jpg'),(2,'/images/laptop/2/1767012738482_macbook-pro-14-inch-m5-1-638962510951839253-750x500.jpg'),(2,'/images/laptop/2/1767012738486_macbook-pro-14-inch-m5-3-638962510926152622-180x125.jpg'),(2,'/images/laptop/2/1767012738490_macbook-pro-14-inch-m5-7-638962510945086528-180x125.jpg'),(2,'/images/laptop/2/1767012738487_macbook-pro-14-inch-m5-4-638962510914066680-180x125.jpg'),(3,'/images/laptop/3/1767013133906_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-1-638814317424656396-180x125.jpg'),(3,'/images/laptop/3/1767013133909_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-3-638814317337081832-180x125.jpg'),(3,'/images/laptop/3/1767013133911_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-6-638814317418661291-180x125.jpg'),(3,'/images/laptop/3/1767013133910_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-4-638814317449693918-180x125.jpg'),(3,'/images/laptop/3/1767013133908_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-2-638814317430721635-750x500.jpg'),(3,'/images/laptop/3/1767013133911_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-5-638814317411390250-180x125.jpg'),(3,'/images/laptop/3/1767013133912_acer-aspire-lite-15-al15-71p-517d-i5-nxj7ksv001-15-638814317404948715-750x500.jpg'),(4,'/images/laptop/4/1767013502511_msi-thin-15-b13ucx-i5-2080vn-2-638618208629517524-750x500.jpg'),(4,'/images/laptop/4/1767013502516_msi-thin-15-b13ucx-i5-2080vn-4-638618208645399450-750x500.jpg'),(4,'/images/laptop/4/1767013502509_msi-thin-15-b13ucx-i5-2080vn-1-638618208621642210-180x125.jpg'),(4,'/images/laptop/4/1767013502514_msi-thin-15-b13ucx-i5-2080vn-3-638618208637971404-750x500.jpg'),(4,'/images/laptop/4/1767013502517_msi-thin-15-b13ucx-i5-2080vn-5-638618208651716574-750x500.jpg'),(4,'/images/laptop/4/1767013502520_vi-vn-msi-thin-15-b13ucx-i5-2080vn-slider-1.jpg'),(5,'/images/laptop/5/1767013795164_macbook-air-15-inch-m4-tgdd-8-638768973223690833-750x500.jpg'),(5,'/images/laptop/5/1767013795156_macbook-air-15-inch-m4-tgdd-1-638768973171263878-750x500.jpg'),(5,'/images/laptop/5/1767013795163_macbook-air-15-inch-m4-tgdd-5-638768973201611750-750x500.jpg'),(5,'/images/laptop/5/1767013795157_macbook-air-15-inch-m4-tgdd-2-638768973181643538-750x500.jpg'),(5,'/images/laptop/5/1767013795162_macbook-air-15-inch-m4-tgdd-4-638768973195912746-750x500.jpg'),(5,'/images/laptop/5/1767013795160_macbook-air-15-inch-m4-tgdd-3-638768973188135309-750x500.jpg'),(5,'/images/laptop/5/1767013795163_macbook-air-15-inch-m4-tgdd-7-638768973213227493-750x500.jpg'),(6,'/images/laptop/6/1767014158545_dell-inspiron-15-3530-i5-p16wd22-3-638874794999708433-750x500.jpg'),(6,'/images/laptop/6/1767014158543_dell-inspiron-15-3530-i5-p16wd22-1-638874795005949105-750x500.jpg'),(6,'/images/laptop/6/1767014158547_dell-inspiron-15-3530-i5-p16wd22-5-638874795024856660-750x500.jpg'),(6,'/images/laptop/6/1767014158547_dell-inspiron-15-3530-i5-p16wd22-4-638874794993026339-750x500.jpg'),(6,'/images/laptop/6/1767014158549_vi-vn-dell-inspiron-15-3530-i5-p16wd22-slider-1.jpg'),(6,'/images/laptop/6/1767014158548_dell-inspiron-15-3530-i5-p16wd22-6-638874795033544130-750x500.jpg'),(6,'/images/laptop/6/1767014158544_dell-inspiron-15-3530-i5-p16wd22-2-638874795012333019-750x500.jpg'),(7,'/images/laptop/7/1767014766726_1734948382689_acer_nitro_5_tiger_an515_58_2_a5385bfde2.png'),(7,'/images/laptop/7/1767014766727_1734948382691_acer_nitro_5_tiger_an515_58_2a10078adb.png'),(7,'/images/laptop/7/1767014766729_1734948382693_acer_nitro_5_tiger_an515_58_3_6c3ef189b6.png'),(7,'/images/laptop/7/1767014766734_1734948382699_acer_nitro_5_tiger_an515_58_6_bc97c6b2ec.png'),(7,'/images/laptop/7/1767014766724_1734948382686_acer_nitro_5_tiger_an515_58_1_9235f94842.png'),(7,'/images/laptop/7/1767014766731_1734948382695_acer_nitro_5_tiger_an515_58_4_a60c9e8c34.png'),(7,'/images/laptop/7/1767014766732_1734948382697_acer_nitro_5_tiger_an515_58_5_57c244f8c2.png'),(8,'/images/laptop/8/1767015010163_1736051982556_2022_3_1_637817435460381731_acer-nitro-gaming-an515-58-den-5.jpg'),(8,'/images/laptop/8/1767015010161_1736051982548_2022_3_1_637817435459600384_acer-nitro-gaming-an515-58-den-6.jpg'),(8,'/images/laptop/8/1767015010162_1736051982553_2022_3_1_637817435459912672_acer-nitro-gaming-an515-58-den-2.jpg'),(8,'/images/laptop/8/1767015010164_1736051982559_2022_3_1_637817435462881696_acer-nitro-gaming-an515-58-den-4.jpg'),(8,'/images/laptop/8/1767015010165_1736051982562_2022_3_1_637817435465226004_acer-nitro-gaming-an515-58-den-3.jpg'),(8,'/images/laptop/8/1767015010166_1736051982564_2022_3_1_637817435466475076_acer-nitro-gaming-an515-58-den-1.jpg'),(9,'/images/laptop/9/1767016148193_1734785824067_asus_tuf_gaming_a15_2023_jaeger_gray_3_32c5f07d59.png'),(9,'/images/laptop/9/1767016148195_1734785824072_asus_tuf_gaming_a15_2023_jaeger_gray_4_fa6ce482eb.png'),(9,'/images/laptop/9/1767016148189_1734785824064_asus_tuf_gaming_a15_2023_jaeger_gray_2_a8ae42d3a3.png'),(9,'/images/laptop/9/1767016148197_1734785824075_asus_tuf_gaming_a15_2023_jaeger_gray_22b3a74a4b.png'),(9,'/images/laptop/9/1767016148196_1734785824073_asus_tuf_gaming_a15_2023_jaeger_gray_5_a548ec117e.png'),(9,'/images/laptop/9/1767016148194_1734785824072_asus_tuf_gaming_a15_2023_jaeger_gray_1_fa6ce482eb.png'),(1,'/images/laptop/1/1766940824066_1734785204227_2022_12_7_638060331277213439_hp-14s-dq-bac-2.jpg'),(1,'/images/laptop/1/1766940823989_1734785204092_2022_12_7_638060331276179591_hp-14s-dq-bac-5.jpg'),(1,'/images/laptop/1/1766940824064_1734785204225_2022_12_7_638060331276811554_hp-14s-dq-bac-3.jpg'),(1,'/images/laptop/1/1766940824079_1734785204233_2022_12_7_638060331277536556_hp-14s-dq-bac-1.jpg'),(1,'/images/laptop/1/1766940824076_1734785204230_2022_12_7_638060331277213439_hp-14s-dq-bac-4.jpg');
/*!40000 ALTER TABLE `laptop_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laptops`
--

DROP TABLE IF EXISTS `laptops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laptops` (
  `id` int NOT NULL AUTO_INCREMENT,
  `battery_charger` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `design` varchar(255) DEFAULT NULL,
  `discount_percent` float DEFAULT NULL,
  `disk_capacity` smallint DEFAULT NULL,
  `disk_detail` varchar(255) NOT NULL,
  `keyboard_type` varchar(255) DEFAULT NULL,
  `model` varchar(255) NOT NULL,
  `num_ratings` int DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `ram_detail` varchar(255) DEFAULT NULL,
  `ram_memory` tinyint DEFAULT NULL,
  `screen_detail` varchar(255) NOT NULL,
  `screen_size` float DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  `warranty` tinyint NOT NULL,
  `brand_id` tinyint DEFAULT NULL,
  `cpu_id` smallint DEFAULT NULL,
  `os_version_id` smallint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7irxajlrgve0t7ikhvfewo67c` (`model`),
  KEY `FKdlhqcqspi56tg5x5wnvkls8jm` (`brand_id`),
  KEY `FK5syaojbppw8nx8lynhcgwp0ww` (`cpu_id`),
  KEY `FK4k5hg3pjmi09q0w3bml0taasb` (`os_version_id`),
  CONSTRAINT `FK4k5hg3pjmi09q0w3bml0taasb` FOREIGN KEY (`os_version_id`) REFERENCES `os_versions` (`id`),
  CONSTRAINT `FK5syaojbppw8nx8lynhcgwp0ww` FOREIGN KEY (`cpu_id`) REFERENCES `cpus` (`id`),
  CONSTRAINT `FKdlhqcqspi56tg5x5wnvkls8jm` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laptops`
--

LOCK TABLES `laptops` WRITE;
/*!40000 ALTER TABLE `laptops` DISABLE KEYS */;
INSERT INTO `laptops` VALUES (1,'abc','2025-12-28 23:53:42.444059','abc',0,128,'abc','abc','sản phẩm test',0,'Trung Quốc',3000,'abc',64,'abv',24,1,12,2,1,2),(2,'Li-Po, 72.4 Wh','2025-12-29 19:47:50.274037','Vỏ nhôm tái chế 100%',10,512,'10','không có','Laptop MacBook Pro 14 inch M5 16GB/512GB',0,'Trung Quốc',40000000,'16',16,'16',14.2,1,12,6,5,3),(3,'3-cell Li-ion, 58 Wh','2025-12-29 19:58:53.656152','Vỏ nhưa',15,512,'512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB)','không có','Laptop Acer Aspire Lite 15 AL15 71P 517D - NX.J7KSV.001 (i5 12450H, 16GB, 512GB, Full HD, Win11) ',0,'Trung Quốc',14500000,'DDR5 (1 khe 16 GB + 1 khe rời)',16,'TFT LED Backlit Acer ComfyView',15.6,1,6,7,4,2),(4,'3-cell Li-ion, 52.4 Wh','2025-12-29 20:05:02.250281','Nắp lưng và chiếu nghỉ tay bằng kim loại',8,512,'512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 4 TB)','không có','Laptop MSI Gaming Thin 15 B13UCX - 2080VN (i5 13420H, 16GB, 512GB, RTX 2050 4GB, Full HD 144Hz, Win11) ',0,'Trung Quốc',16700000,'DDR4 (1 khe 8 GB + 1 khe 8 GB)',16,' Full HD (1920 x 1080)',15.6,1,12,9,6,2),(5,'Li-Po, 66.5 Wh','2025-12-29 20:09:54.944814','Vỏ nhôm tái chế 100%',12,512,' 256 GB SSD','không có','Laptop MacBook Air 15 inch M4 16GB/256GB ',0,'Trung Quốc',28990000,'Hãng không công bố',16,'Liquid Retina',15.3,1,12,6,5,3),(6,'3-cell Li-ion, 41 Wh','2025-12-29 20:15:58.245985','Vỏ nhựa',17,512,'512 GB SSD NVMe PCIe','không có','Laptop Dell Inspiron 15 3530 - P16WD22 (i5 1334U, 16GB, 512GB, Full HD 120Hz, OfficeH24+365, Win11) ',0,'Trung Quốc',17500000,' DDR4 (8 GB onboard + 1 khe 8 GB)',16,'Chống chói Anti Glare LED Backlit 250 nits WVA',15.6,1,12,10,4,2),(7,'3-cell Li-ion, 58 Wh','2025-12-29 20:26:06.419791','Vỏ nhưa',10,512,'512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB)','không có','Laptop Acer Aspire Lite 16 G2 AL16 52P 572A - NX.J2SSV.004 (i5 1334U, 16GB, 512GB, Full HD+, Win11) ',0,'Trung Quốc',17000000,'DDR5 (1 khe 16 GB + 1 khe rời)',16,'TFT LED Backlit Acer ComfyView',16,1,6,7,4,2),(8,'2400W','2025-12-29 20:30:09.883355','Vỏ nhưa',5,64,'512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB)','không có','Laptop HP Probook 455 G10 - B8PG7AT (R5 7530U, 16GB, 512GB, Full HD, Win11) ',0,'Trung Quốc',17500000,'DDR5 (1 khe 16 GB + 1 khe rời)',16,'TFT LED Backlit Acer ComfyView',14,1,6,11,2,2),(9,'Li-Po, 72.4 Wh','2025-12-29 20:49:07.809882','Vỏ nhưa',8,512,'512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB)','không có','Laptop Asus Vivobook 15 OLED A1505VA - MA467W (i5 13500H, 16GB, 512GB, 2.8K OLED 120Hz, Win11) ',0,'Trung Quốc',24500000,'DDR5 (1 khe 16 GB + 1 khe rời)',16,'TFT LED Backlit Acer ComfyView',15.6,1,6,5,4,2);
/*!40000 ALTER TABLE `laptops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `status` enum('DELIVERED','READ','SENT') DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `conversation_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt492th6wsovh1nush5yl5jj8e` (`conversation_id`),
  KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`),
  CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt492th6wsovh1nush5yl5jj8e` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` smallint NOT NULL,
  `color_id` tinyint DEFAULT NULL,
  `laptop_id` int DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq4wvof0qrtimcy2gpqpist101` (`color_id`),
  KEY `FKntwt12snw188o34x2m01na7jm` (`laptop_id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKntwt12snw188o34x2m01na7jm` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`),
  CONSTRAINT `FKq4wvof0qrtimcy2gpqpist101` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (5,1,2,1,1),(8,1,2,3,2),(14,1,1,5,3),(15,1,1,3,4),(16,1,1,8,5),(17,1,1,5,6),(18,1,1,6,7);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `order_status` tinyint DEFAULT NULL,
  `payment_method` tinyint NOT NULL,
  `payment_status` tinyint NOT NULL,
  `total_discounted_price` float DEFAULT NULL,
  `total_item` int DEFAULT NULL,
  `total_price` float DEFAULT NULL,
  `shipping_address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmk6q95x8ffidq82wlqjaq7sqc` (`shipping_address_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmk6q95x8ffidq82wlqjaq7sqc` FOREIGN KEY (`shipping_address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`order_status` between 0 and 5)),
  CONSTRAINT `orders_chk_2` CHECK ((`payment_method` between 0 and 6)),
  CONSTRAINT `orders_chk_3` CHECK ((`payment_status` between 0 and 3))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `os_versions`
--

DROP TABLE IF EXISTS `os_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `os_versions` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `version` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `os_versions`
--

LOCK TABLES `os_versions` WRITE;
/*!40000 ALTER TABLE `os_versions` DISABLE KEYS */;
INSERT INTO `os_versions` VALUES (1,'Windows 10'),(2,'Windows 11'),(3,'IOS');
/*!40000 ALTER TABLE `os_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `short_description` varchar(500) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'<p><strong>Nguồn gốc xuất thân</strong></p><p>Héc-quyn (Hercules trong thần thoại La Mã, Heracles trong thần thoại Hy Lạp) là con trai của thần tối cao&nbsp;<strong>Zeus</strong>&nbsp;và nàn&nbsp;<strong>Alcmene</strong>&nbsp;- một phụ nữ phàm trần xinh đẹp. Chính dòng máu lai này đã ban cho chàng sức mạnh phi thường ngay từ khi mới lọt lòng. Tuy nhiên, nó cũng khiến chàng trở thành cái gai trong mắt nữ thần Hera, vợ của Zeus.</p><p><strong>Bi kịch và Sự khởi đầu</strong></p><p>Do sự ghen tuông của Hera, Héc-quyn đã bị trúng một lời nguyền khiến chàng mất trí trong chốc lát và gây ra thảm kịch cho chính gia đình mình. Để chuộc lại lỗi lầm, chàng phải phục vụ vua Eurystheus trong 12 năm và hoàn thành những nhiệm vụ bất khả thi.</p><p><strong>12 Chiến công huyền thoại</strong></p><p>Để trở thành bất tử và rửa sạch tội lỗi, Héc-quyn đã phải vượt qua 12 thử thách khủng khiếp:</p><ol><li>Giết con sư tử Nemea có lớp da đao thương bất nhập.</li><li>Tiêu diệt quái vật rắn 9 đầu Hydra ở Lerna.</li><li>Bắt sống con hươu Ceryneian của nữ thần Artemis.</li><li>Bắt sống con heo rừng Erymanthian.</li><li>Dọn sạch chuồng ngựa của vua Augeas trong một ngày.</li><li>Đuổi đàn chim ăn thịt ở hồ Stymphalian.</li><li>Bắt con bò mộng ở đảo Crete.</li><li>Đoạt bầy ngựa ăn thịt người của vua Diomedes.</li><li>Đoạt chiếc thắt lưng của nữ hoàng Amazon Hippolyta.</li><li>Đánh cắp đàn bò của người khổng lồ Geryon.</li><li>Đoạt những quả táo vàng của chị em Hesperides.</li><li>Bắt con chó ba đầu Cerberus canh giữ địa ngục.</li></ol><p><strong>Ý nghĩa biểu tượng</strong></p><p>Héc-quyn không chỉ là biểu tượng của sức mạnh vật lý vô song, mà còn là hiện thân của lòng dũng cảm, sự kiên trì và khát vọng vươn lên của con người trước những thử thách nghiệt ngã của số phận. Cuối cùng, sau khi rời bỏ thế giới trần tục, chàng đã được đón lên đỉnh Olympus và trở thành một vị thần bất tử.</p><p><br></p>','2025-12-27 00:21:44.551828','Khám phá hành trình đầy chông gai của Héc-quyn, con trai thần Zeus. Từ cơn cuồng nộ do Hera gây ra đến hành trình chuộc lỗi qua 12 kỳ công chấn động tam giới, đưa anh trở thành vị thần sức mạnh vĩ đại nhất đỉnh Olympus.','/images/blog/1766769704551_hinh-nen-laptop-4k-1719.webp','Huyền thoại Héc-quyn (Hercules): Vị bán thần vĩ đại và 12 Chiến công bất tử','2025-12-27 00:21:44.551828',1),(2,'<h3>1. Tổng quan về thị trường laptop hiện nay</h3><p>Trong những năm gần đây, thị trường laptop chứng kiến sự phát triển mạnh mẽ cả về hiệu năng lẫn thiết kế. Các hãng công nghệ lớn như <strong>Apple, Dell, ASUS, HP, Lenovo</strong> liên tục cho ra mắt những mẫu laptop mới với vi xử lý mạnh mẽ hơn, pin bền bỉ hơn và thiết kế ngày càng mỏng nhẹ.</p><p>Đặc biệt, năm 2025 đánh dấu sự phổ biến của:</p><ul><li>Chip <strong>Intel Core Ultra</strong></li><li><strong>AMD Ryzen AI</strong></li><li>Dòng <strong>Apple M3/M4</strong></li><li>Laptop tích hợp <strong>AI hỗ trợ công việc và sáng tạo</strong></li></ul><h3>2. Phân loại laptop theo nhu cầu sử dụng</h3><h4>? Laptop cho học tập – văn phòng</h4><p>Dòng laptop này phù hợp với sinh viên, nhân viên văn phòng với các nhu cầu cơ bản như:</p><ul><li>Soạn thảo văn bản</li><li>Lướt web</li><li>Học online</li><li>Làm việc với Excel, PowerPoint</li></ul><p><strong>Đặc điểm:</strong></p><ul><li>CPU: Intel Core i3 / i5 hoặc Ryzen 5</li><li>RAM: 8GB</li><li>Ổ cứng: SSD 256GB</li><li>Giá thành: hợp lý, dễ tiếp cận</li></ul><p><strong>Gợi ý:</strong> ASUS Vivobook, Dell Inspiron, HP Pavilion</p><h4>? Laptop cho lập trình &amp; đồ họa</h4><p>Đây là lựa chọn dành cho lập trình viên, designer, editor video.</p><p><strong>Đặc điểm:</strong></p><ul><li>CPU mạnh (Intel i7 / Ryzen 7 trở lên)</li><li>RAM: 16GB – 32GB</li><li>Card đồ họa rời (RTX 3050/4060)</li><li>Màn hình chuẩn màu cao (sRGB, AdobeRGB)</li></ul><p><strong>Gợi ý:</strong> MacBook Pro, ASUS ROG Zephyrus, Lenovo Legion</p><h4>? Laptop gaming &amp; giải trí</h4><p>Dành cho game thủ và người yêu thích hiệu năng cao.</p><p><strong>Đặc điểm:</strong></p><ul><li>GPU mạnh</li><li>Hệ thống tản nhiệt tốt</li><li>Màn hình tần số quét cao (144Hz – 240Hz)</li></ul><p><strong>Nhược điểm:</strong></p><ul><li>Trọng lượng nặng</li><li>Pin không quá lâu</li></ul><h3>3. Những tiêu chí quan trọng khi chọn mua laptop</h3><h4>✅ Hiệu năng</h4><p>Chọn CPU và RAM phù hợp với nhu cầu, tránh mua quá yếu hoặc quá dư thừa gây lãng phí.</p><h4>✅ Màn hình</h4><ul><li>Full HD trở lên</li><li>IPS hoặc OLED</li><li>Tần số quét cao giúp trải nghiệm mượt mà</li></ul><h4>✅ Pin và tính di động</h4><p>Nếu thường xuyên di chuyển, hãy ưu tiên laptop nhẹ, pin trên 8 tiếng.</p><h4>✅ Hệ điều hành</h4><ul><li><strong>Windows:</strong> linh hoạt, phổ biến</li><li><strong>macOS:</strong> ổn định, tối ưu phần cứng</li><li><strong>Linux:</strong> phù hợp lập trình viên</li></ul><h3>4. Xu hướng laptop trong tương lai</h3><p>Trong thời gian tới, laptop sẽ ngày càng:</p><ul><li>Tích hợp <strong>AI cá nhân hóa</strong></li><li>Tối ưu hiệu năng trên mỗi watt điện</li><li>Không quạt, không tiếng ồn</li><li>Kết nối mạnh mẽ với hệ sinh thái (cloud, điện thoại, tablet)</li></ul><h3>5. Kết luận</h3><p>Laptop không còn đơn thuần là thiết bị công nghệ, mà đã trở thành công cụ không thể thiếu trong cuộc sống hiện đại. Việc lựa chọn đúng chiếc laptop sẽ giúp bạn <strong>làm việc hiệu quả hơn, học tập tốt hơn và giải trí trọn vẹn hơn</strong>.</p><p>Hãy xác định rõ nhu cầu, ngân sách và mục đích sử dụng trước khi quyết định mua để có được sự lựa chọn tối ưu nhất.</p>','2025-12-27 03:05:25.777641','Trong bối cảnh công nghệ phát triển nhanh chóng, laptop không chỉ là công cụ làm việc mà còn là người bạn đồng hành trong học tập và giải trí. Bài viết này sẽ giúp bạn hiểu rõ các dòng laptop phổ biến hiện nay và cách lựa chọn chiếc laptop phù hợp nhất với nhu cầu của mình.','/images/blog/1766779525777_laptop.jpg','Laptop năm 2025: Nên chọn laptop nào phù hợp cho học tập, làm việc và giải trí?','2025-12-27 03:05:25.777641',1),(3,'<h3>1. Node.js là gì?</h3><p>Node.js là một <strong>môi trường runtime</strong> cho phép chạy <strong>JavaScript ở phía server</strong>, được xây dựng dựa trên <strong>V8 JavaScript Engine</strong> của Google Chrome. Trước khi Node.js ra đời, JavaScript chủ yếu chỉ được dùng trong trình duyệt. Sự xuất hiện của Node.js đã mở ra kỷ nguyên mới cho phát triển backend bằng JavaScript.</p><p>Nói đơn giản:</p><blockquote><strong>Node.js cho phép bạn dùng JavaScript để xây dựng server, API và hệ thống backend.</strong></blockquote><h3>2. Node.js hoạt động như thế nào?</h3><p>Node.js sử dụng mô hình <strong>event-driven, non-blocking I/O</strong>, nghĩa là:</p><ul><li>Không chờ đợi tác vụ hoàn thành</li><li>Xử lý nhiều request cùng lúc</li><li>Tối ưu tài nguyên hệ thống</li></ul><p>Khác với mô hình truyền thống (mỗi request một thread), Node.js chạy trên <strong>single-thread</strong> nhưng có khả năng xử lý hàng nghìn kết nối đồng thời nhờ <strong>event loop</strong>.</p><p><strong>Ưu điểm nổi bật:</strong></p><ul><li>Tốc độ xử lý nhanh</li><li>Hiệu suất cao</li><li>Phù hợp với ứng dụng realtime</li></ul><h3>3. Ưu điểm nổi bật của Node.js</h3><h4>? Hiệu năng cao</h4><p>Node.js rất mạnh trong các hệ thống:</p><ul><li>API REST</li><li>Realtime chat</li><li>Streaming dữ liệu</li><li>Microservices</li></ul><h4>? JavaScript toàn stack</h4><p>Chỉ cần <strong>1 ngôn ngữ JavaScript</strong> cho cả:</p><ul><li>Frontend</li><li>Backend</li><li>Server</li></ul><p>Điều này giúp:</p><ul><li>Dễ học</li><li>Dễ bảo trì</li><li>Chia sẻ code giữa frontend và backend</li></ul><h4>? Hệ sinh thái npm khổng lồ</h4><p>Node.js sở hữu <strong>npm (Node Package Manager)</strong> – kho thư viện lớn nhất thế giới với hàng triệu package:</p><ul><li>Express</li><li>NestJS</li><li>Socket.IO</li><li>Sequelize, Prisma</li><li>JWT, bcrypt, multer,...</li></ul><h3>4. Node.js thường được dùng để làm gì?</h3><h4>? Xây dựng API Backend</h4><p>Node.js rất phổ biến trong việc xây dựng:</p><ul><li>RESTful API</li><li>GraphQL API</li><li>Backend cho mobile app &amp; web app</li></ul><h4>? Ứng dụng Realtime</h4><p>Nhờ WebSocket và Socket.IO, Node.js cực kỳ phù hợp cho:</p><ul><li>Chat realtime</li><li>Hệ thống thông báo</li><li>Game online</li><li>Ứng dụng livestream</li></ul><h4>? Microservices</h4><p>Node.js nhẹ, dễ scale nên được dùng rộng rãi trong kiến trúc <strong>microservices</strong>.</p><h3>5. So sánh Node.js với các backend truyền thống</h3><p>Tiêu chíNode.jsPHP / JavaNgôn ngữJavaScriptPHP / JavaHiệu suấtCaoTrung bìnhRealtimeRất tốtHạn chếKhả năng scaleTốtPhụ thuộc kiến trúcHọc nhanh✅❌</p><h3>6. Node.js có nhược điểm không?</h3><p>Bên cạnh ưu điểm, Node.js cũng có một số hạn chế:</p><ul><li>Không phù hợp cho tác vụ CPU nặng</li><li>Dễ gặp callback hell nếu code không tốt</li><li>Cần hiểu rõ async/await để tránh lỗi logic</li></ul><p>Tuy nhiên, với các framework hiện đại như <strong>NestJS</strong>, các nhược điểm này đã được khắc phục rất nhiều.</p><h3>7. Xu hướng phát triển Node.js hiện nay</h3><p>Node.js đang ngày càng mạnh hơn với:</p><ul><li>Hỗ trợ <strong>TypeScript</strong></li><li>Tích hợp tốt với cloud (AWS, Docker, Kubernetes)</li><li>Phù hợp cho AI backend, chatbot, API gateway</li></ul><p>Nhiều công ty lớn sử dụng Node.js:</p><ul><li>Netflix</li><li>PayPal</li><li>Uber</li><li>LinkedIn</li></ul><h3>8. Kết luận</h3><p>Node.js không chỉ là một công nghệ backend phổ biến mà còn là nền tảng mạnh mẽ cho các hệ thống hiện đại. Với hiệu năng cao, hệ sinh thái phong phú và cộng đồng lớn, Node.js là lựa chọn tuyệt vời cho cả người mới học lập trình lẫn các dự án quy mô lớn.</p><p>Nếu bạn đang tìm một công nghệ backend <strong>linh hoạt – nhanh – dễ mở rộng</strong>, Node.js chắc chắn là cái tên không thể bỏ qua.</p>','2025-12-27 03:07:30.429391','Node.js đã thay đổi hoàn toàn cách xây dựng ứng dụng web hiện đại khi cho phép JavaScript chạy ở phía server. Bài viết này sẽ giúp bạn hiểu rõ Node.js là gì, hoạt động ra sao và vì sao nó được sử dụng rộng rãi trong các hệ thống lớn.','/images/blog/1766779650430_nodejs.png','Node.js là gì? Vì sao Node.js trở thành nền tảng backend được ưa chuộng nhất hiện nay','2025-12-27 03:07:30.429391',1);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `laptop_id` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKapntgissnyav1j5oij9vl7gce` (`laptop_id`),
  KEY `FKb3354ee2xxvdrbyq9f42jdayd` (`user_id`),
  CONSTRAINT `FKapntgissnyav1j5oij9vl7gce` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`),
  CONSTRAINT `FKb3354ee2xxvdrbyq9f42jdayd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `review` varchar(255) DEFAULT NULL,
  `laptop_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnvdqhvj69u2ys2qghv9ds989o` (`laptop_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnvdqhvj69u2ys2qghv9ds989o` FOREIGN KEY (`laptop_id`) REFERENCES `laptops` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday` date DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'2025-12-28 14:40:11.129721','admin@gmail.com',NULL,'Shop Admin','$2a$10$dGqlzoChYTO0lNsWsgYy0.4sxPqilazIsH9oUGz2NbQAoY/dusWL2','0362501803','ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-02 20:40:04
