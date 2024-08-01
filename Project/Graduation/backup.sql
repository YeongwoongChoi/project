CREATE DATABASE  IF NOT EXISTS `termproject` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `termproject`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: termproject
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `broadcast`
--

DROP TABLE IF EXISTS `broadcast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcast` (
  `name` varchar(50) NOT NULL,
  `broadcastingCompany` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `broadcast`
--

LOCK TABLES `broadcast` WRITE;
/*!40000 ALTER TABLE `broadcast` DISABLE KEYS */;
/*!40000 ALTER TABLE `broadcast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classify`
--

DROP TABLE IF EXISTS `classify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classify` (
  `styleName` varchar(30) NOT NULL,
  `dishIdentifier` varchar(10) NOT NULL,
  PRIMARY KEY (`styleName`,`dishIdentifier`),
  KEY `dishIdentifier` (`dishIdentifier`),
  CONSTRAINT `classify_ibfk_1` FOREIGN KEY (`styleName`) REFERENCES `style` (`styleName`),
  CONSTRAINT `classify_ibfk_2` FOREIGN KEY (`dishIdentifier`) REFERENCES `dish` (`dishIdentifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classify`
--

LOCK TABLES `classify` WRITE;
/*!40000 ALTER TABLE `classify` DISABLE KEYS */;
INSERT INTO `classify` VALUES ('American','F-001'),('American','F-002'),('Korean','F-003'),('Japanese','F-004'),('Korean','F-004'),('Korean','F-005'),('Chinese','F-006'),('Korean','F-006'),('Chinese','F-007'),('Japanese','F-007'),('Korean','F-007'),('Japanese','F-008'),('Chinese','F-009'),('Chinese','F-010'),('American','F-011'),('Chinese','F-011'),('Japanese','F-011'),('Korean','F-011'),('Mexican','F-011'),('Korean','F-012'),('Mexican','F-013'),('Mexican','F-014'),('Mexican','F-015'),('Japanese','F-016'),('Chinese','F-017'),('Korean','F-017'),('Japanese','F-018'),('Korean','F-018'),('American','F-019'),('American','F-020');
/*!40000 ALTER TABLE `classify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerIdentifier` varchar(15) NOT NULL,
  `customerPassword` binary(64) NOT NULL,
  `customerName` varchar(30) NOT NULL,
  `customerSex` char(7) NOT NULL,
  `customerAge` int DEFAULT NULL,
  `earnedPoint` int NOT NULL,
  PRIMARY KEY (`customerIdentifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('hero00',_binary 'cea3888c3547e16280b3906cc8adbeab446ba5780ec2386b973f13c65c28301e','Yeongwoong Choi','Male',25,5230),('test',_binary '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','테스트','Male',23,100);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customercontact`
--

DROP TABLE IF EXISTS `customercontact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customercontact` (
  `customerIdentifier` varchar(15) NOT NULL,
  `phoneNumber` char(13) NOT NULL,
  PRIMARY KEY (`customerIdentifier`,`phoneNumber`),
  CONSTRAINT `customercontact_ibfk_1` FOREIGN KEY (`customerIdentifier`) REFERENCES `customer` (`customerIdentifier`) ON DELETE CASCADE,
  CONSTRAINT `CHK_phoneNumberType` CHECK ((`phoneNumber` like _utf8mb4'%-%-%'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customercontact`
--

LOCK TABLES `customercontact` WRITE;
/*!40000 ALTER TABLE `customercontact` DISABLE KEYS */;
INSERT INTO `customercontact` VALUES ('hero00','010-2650-9477'),('test','010-1111-2222');
/*!40000 ALTER TABLE `customercontact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerorder`
--

DROP TABLE IF EXISTS `customerorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerorder` (
  `customerIdentifier` varchar(15) NOT NULL,
  `restaurantCode` varchar(10) NOT NULL,
  `dishIdentifier` varchar(10) NOT NULL,
  `totalDishes` int NOT NULL,
  `orderedTime` datetime NOT NULL,
  PRIMARY KEY (`customerIdentifier`,`restaurantCode`,`dishIdentifier`),
  KEY `dishIdentifier` (`dishIdentifier`),
  KEY `customerorder_ibfk_2` (`restaurantCode`),
  CONSTRAINT `customerorder_ibfk_1` FOREIGN KEY (`customerIdentifier`) REFERENCES `customer` (`customerIdentifier`) ON DELETE CASCADE,
  CONSTRAINT `customerorder_ibfk_2` FOREIGN KEY (`restaurantCode`) REFERENCES `restaurant` (`restaurantCode`) ON DELETE CASCADE,
  CONSTRAINT `customerorder_ibfk_3` FOREIGN KEY (`dishIdentifier`) REFERENCES `dish` (`dishIdentifier`),
  CONSTRAINT `CHK_totalDishes` CHECK ((`totalDishes` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerorder`
--

LOCK TABLES `customerorder` WRITE;
/*!40000 ALTER TABLE `customerorder` DISABLE KEYS */;
INSERT INTO `customerorder` VALUES ('hero00','R-0002','F-004',2,'2024-06-02 13:20:00'),('hero00','R-0009','F-022',2,'2024-06-02 14:00:00'),('hero00','R-0009','F-025',1,'2024-06-02 14:00:00'),('hero00','R-0010','F-009',1,'2024-06-02 19:00:00'),('test','R-0002','F-003',2,'2024-06-04 18:00:00'),('test','R-0002','F-012',1,'2024-06-04 18:00:00'),('test','R-0004','F-006',1,'2024-06-02 12:00:00'),('test','R-0004','F-017',1,'2024-06-02 12:00:00'),('test','R-0010','F-007',1,'2024-06-04 21:00:00'),('test','R-0010','F-010',2,'2024-06-04 21:00:00');
/*!40000 ALTER TABLE `customerorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `dishIdentifier` varchar(10) NOT NULL,
  `dishName` varchar(35) NOT NULL,
  `dishPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`dishIdentifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES ('F-001','Pork ribs',19.99),('F-002','Porterhouse Steak',34.99),('F-003','Korean BBQ',16.80),('F-004','Pork Cutlet',13.79),('F-005','Bibimbap',15.40),('F-006','Spicy noodles',13.50),('F-007','Dumplings',8.99),('F-008','Salmon Roll Sushi',15.99),('F-009','Lo Mein',14.70),('F-010','Orange Chicken',12.99),('F-011','Chef\'s Special',35.99),('F-012','Spicy Tofu Soup',16.99),('F-013','Enchiladas',10.40),('F-014','Quesadilla',12.50),('F-015','Fajita',16.69),('F-016','California Roll Sushi',9.99),('F-017','Beef Stir Fry',13.99),('F-018','Mackerel Deep Fry',15.20),('F-019','Cheeseburger',10.99),('F-020','Ribeye Steak',25.79),('F-021','Sausage Sandwich',9.50),('F-022','9\" Chicken Alfredo',14.75),('F-023','12\" House Special',19.75),('F-024','18\" NY Cheeseburger',26.75),('F-025','Boneless Chicken Bites',10.00);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner` (
  `RRN` char(15) NOT NULL,
  `ownerName` varchar(35) NOT NULL,
  `phoneNumber` char(12) DEFAULT NULL,
  PRIMARY KEY (`RRN`),
  CONSTRAINT `CHK_numberType` CHECK ((`phoneNumber` like _utf8mb4'%-%-%')),
  CONSTRAINT `owner_chk_1` CHECK ((`RRN` like _utf8mb4'%-%'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES ('000424-3012345','Yeongwoong Choi','325-895-7178'),('651005-2596043','Susanne Graham','385-203-3508'),('660725-2340514','Kuen Chang','203-720-8909'),('680810-2087392','Brenda Schultz','260-634-5852'),('700128-1945065','Alfonso Ortiz','806-297-8828'),('721004-1524325','Marc Goodman','402-598-9600'),('840102-1234517','John Smith',NULL),('871103-1193215','Michael Weiss','901-143-4844'),('930203-1123053','Dwayne Carpenter','580-201-1888'),('950129-2392911','Pearl Doyle','725-079-6085');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prefer`
--

DROP TABLE IF EXISTS `prefer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prefer` (
  `customerIdentifier` varchar(15) NOT NULL,
  `styleName` varchar(30) NOT NULL,
  PRIMARY KEY (`customerIdentifier`,`styleName`),
  KEY `styleName` (`styleName`),
  CONSTRAINT `prefer_ibfk_1` FOREIGN KEY (`customerIdentifier`) REFERENCES `customer` (`customerIdentifier`) ON DELETE CASCADE,
  CONSTRAINT `prefer_ibfk_2` FOREIGN KEY (`styleName`) REFERENCES `style` (`styleName`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prefer`
--

LOCK TABLES `prefer` WRITE;
/*!40000 ALTER TABLE `prefer` DISABLE KEYS */;
INSERT INTO `prefer` VALUES ('hero00','American'),('hero00','Chinese'),('hero00','Japanese'),('hero00','Korean'),('hero00','Mexican');
/*!40000 ALTER TABLE `prefer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserve`
--

DROP TABLE IF EXISTS `reserve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserve` (
  `customerIdentifier` varchar(15) NOT NULL,
  `restaurantCode` varchar(10) NOT NULL,
  `reservedTime` datetime NOT NULL,
  `numberOfPeople` int DEFAULT NULL,
  PRIMARY KEY (`customerIdentifier`,`restaurantCode`,`reservedTime`),
  UNIQUE KEY `UNQ_time` (`reservedTime`),
  KEY `restaurantCode` (`restaurantCode`),
  CONSTRAINT `reserve_ibfk_1` FOREIGN KEY (`customerIdentifier`) REFERENCES `customer` (`customerIdentifier`) ON DELETE CASCADE,
  CONSTRAINT `reserve_ibfk_2` FOREIGN KEY (`restaurantCode`) REFERENCES `restaurant` (`restaurantCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserve`
--

LOCK TABLES `reserve` WRITE;
/*!40000 ALTER TABLE `reserve` DISABLE KEYS */;
INSERT INTO `reserve` VALUES ('hero00','R-0002','2024-06-01 14:00:00',1),('hero00','R-0002','2024-06-01 14:30:00',3),('hero00','R-0004','2024-06-01 18:00:00',3),('hero00','R-0005','2023-11-21 17:50:00',2),('hero00','R-0005','2023-12-08 11:30:00',5),('hero00','R-0007','2024-06-07 14:00:00',6),('hero00','R-0009','2024-06-02 14:00:00',5),('test','R-0003','2024-05-28 20:00:00',5);
/*!40000 ALTER TABLE `reserve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `restaurantCode` varchar(10) NOT NULL,
  `typeOfDishes` varchar(35) DEFAULT NULL,
  `restaurantName` varchar(50) NOT NULL,
  `location` varchar(150) NOT NULL,
  `rating` decimal(3,2) DEFAULT NULL,
  `phoneNumber` char(12) DEFAULT NULL,
  `ownerRRN` char(15) NOT NULL,
  PRIMARY KEY (`restaurantCode`),
  KEY `ownerRRN` (`ownerRRN`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`ownerRRN`) REFERENCES `owner` (`RRN`) ON DELETE CASCADE,
  CONSTRAINT `CHK_phoneNumber` CHECK ((`phoneNumber` like _utf8mb4'%-%-%')),
  CONSTRAINT `CHK_rating` CHECK (((`rating` >= 0.0) and (`rating` <= 5.0)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES ('R-0001','American','Cork & Pig Tavern','San Angelo',3.90,'325-227-6988','840102-1234517'),('R-0002','Korean','Kogi Korean Grill','San Antonio',4.15,'210-558-2018','000424-3012345'),('R-0003','Mexican','La Esperanza','San Angelo',4.30,'325-223-0204','700128-1945065'),('R-0004','Chinese','Dragonlicious','San Angelo',3.80,'325-939-1888','680810-2087392'),('R-0005','Korean','BCD Tofu house','Los Angeles',4.30,'213-382-6677','000424-3012345'),('R-0006','American','SkinnyFats','Las Vegas',4.60,'702-577-3232','950129-2392911'),('R-0007','Japanese','Sushi House','San Angelo',4.50,'325-949-0800','930203-1123053'),('R-0008','Mexican','The Original Mex','San Angelo',4.40,'325-223-0171','651005-2596043'),('R-0009','Italian-American','Tony\'s La Pizzeria','Cedar Falls',4.30,'319-277-8669','721004-1524325'),('R-0010','Chinese','Hunan Palace','Sioux City',3.75,'712-222-1022','660725-2340514');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `customerIdentifier` varchar(15) NOT NULL,
  `restaurantCode` varchar(10) NOT NULL,
  `dishIdentifier` varchar(10) NOT NULL,
  `rating` decimal(3,2) NOT NULL,
  `reviewText` text,
  PRIMARY KEY (`customerIdentifier`,`restaurantCode`,`dishIdentifier`),
  CONSTRAINT `review_chk_1` CHECK (((`rating` >= 0.0) and (`rating` <= 5.0)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES ('hero00','R-0004','F-011',3.80,'It was quite expensive, but I think it was reasonable.'),('hero00','R-0009','F-022',4.40,'Good mood. Good managers. Decent foods. The pizza I ate in this place was excellent.'),('hero00','R-0010','F-009',3.60,'Not bad, not good.'),('test','R-0002','F-003',4.50,'Best Korean BBQ ever!'),('test','R-0002','F-012',3.80,'I am Korean. the soup was not as spicy as the one I thought. The staffs were kind.'),('test','R-0010','F-007',3.90,'Good mood, but the price was more expensive than what I thought.');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serve`
--

DROP TABLE IF EXISTS `serve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serve` (
  `restaurantCode` varchar(10) NOT NULL,
  `dishIdentifier` varchar(10) NOT NULL,
  PRIMARY KEY (`restaurantCode`,`dishIdentifier`),
  KEY `dishIdentifier` (`dishIdentifier`),
  CONSTRAINT `serve_ibfk_1` FOREIGN KEY (`restaurantCode`) REFERENCES `restaurant` (`restaurantCode`),
  CONSTRAINT `serve_ibfk_2` FOREIGN KEY (`dishIdentifier`) REFERENCES `dish` (`dishIdentifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serve`
--

LOCK TABLES `serve` WRITE;
/*!40000 ALTER TABLE `serve` DISABLE KEYS */;
INSERT INTO `serve` VALUES ('R-0001','F-001'),('R-0006','F-001'),('R-0001','F-002'),('R-0006','F-002'),('R-0002','F-003'),('R-0005','F-003'),('R-0002','F-004'),('R-0002','F-005'),('R-0005','F-005'),('R-0002','F-006'),('R-0004','F-006'),('R-0005','F-006'),('R-0002','F-007'),('R-0004','F-007'),('R-0005','F-007'),('R-0007','F-007'),('R-0010','F-007'),('R-0007','F-008'),('R-0004','F-009'),('R-0010','F-009'),('R-0004','F-010'),('R-0010','F-010'),('R-0001','F-011'),('R-0003','F-011'),('R-0004','F-011'),('R-0006','F-011'),('R-0007','F-011'),('R-0008','F-011'),('R-0010','F-011'),('R-0002','F-012'),('R-0005','F-012'),('R-0003','F-013'),('R-0008','F-013'),('R-0003','F-014'),('R-0008','F-014'),('R-0003','F-015'),('R-0008','F-015'),('R-0007','F-016'),('R-0002','F-017'),('R-0004','F-017'),('R-0005','F-017'),('R-0005','F-018'),('R-0001','F-019'),('R-0006','F-019'),('R-0006','F-020'),('R-0009','F-021'),('R-0009','F-022'),('R-0009','F-023'),('R-0009','F-024'),('R-0009','F-025');
/*!40000 ALTER TABLE `serve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `style`
--

DROP TABLE IF EXISTS `style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `style` (
  `styleName` varchar(30) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`styleName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `style`
--

LOCK TABLES `style` WRITE;
/*!40000 ALTER TABLE `style` DISABLE KEYS */;
INSERT INTO `style` VALUES ('American','including steak and burger'),('Chinese','including American-Chinese food like orange chicken'),('Japanese','including some types of sushi'),('Korean','including Korean BBQ and some dishes made with rice'),('Mexican','including Mexican food like enchilada and quesadilla');
/*!40000 ALTER TABLE `style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply`
--

DROP TABLE IF EXISTS `supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply` (
  `restaurantCode` varchar(10) NOT NULL,
  `vendorCode` varchar(6) NOT NULL,
  `suppliedDate` date NOT NULL,
  `suppliedQuantities` int NOT NULL,
  PRIMARY KEY (`restaurantCode`,`vendorCode`),
  KEY `vendorCode` (`vendorCode`),
  CONSTRAINT `supply_ibfk_1` FOREIGN KEY (`restaurantCode`) REFERENCES `restaurant` (`restaurantCode`),
  CONSTRAINT `supply_ibfk_2` FOREIGN KEY (`vendorCode`) REFERENCES `vendor` (`vendorCode`),
  CONSTRAINT `CHK_suppliedQuantities` CHECK ((`suppliedQuantities` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply`
--

LOCK TABLES `supply` WRITE;
/*!40000 ALTER TABLE `supply` DISABLE KEYS */;
INSERT INTO `supply` VALUES ('R-0001','V-005','2023-11-01',50),('R-0001','V-006','2023-10-04',100),('R-0001','V-008','2023-11-12',140),('R-0001','V-012','2023-11-18',75),('R-0002','V-001','2023-10-31',60),('R-0002','V-003','2023-10-30',28),('R-0002','V-006','2023-10-30',20),('R-0002','V-008','2023-11-13',15),('R-0002','V-009','2023-10-05',30),('R-0002','V-010','2023-10-07',38),('R-0002','V-012','2023-11-19',10),('R-0003','V-001','2023-10-30',40),('R-0003','V-006','2023-11-25',80),('R-0003','V-009','2023-11-13',35),('R-0004','V-001','2023-10-25',41),('R-0004','V-005','2023-11-02',10),('R-0004','V-006','2023-11-04',33),('R-0004','V-007','2023-11-17',20),('R-0004','V-009','2023-11-25',33),('R-0005','V-001','2023-11-09',15),('R-0005','V-002','2023-11-15',10),('R-0005','V-003','2023-11-08',15),('R-0005','V-004','2023-11-05',10),('R-0005','V-005','2023-11-22',10),('R-0005','V-007','2023-11-23',10),('R-0005','V-010','2023-10-27',50),('R-0005','V-011','2023-10-27',40),('R-0006','V-001','2023-11-16',35),('R-0006','V-002','2023-11-29',20),('R-0006','V-003','2023-11-03',45),('R-0006','V-005','2023-10-01',20),('R-0006','V-006','2023-11-26',100),('R-0006','V-007','2023-09-11',50),('R-0006','V-009','2023-09-16',230),('R-0006','V-012','2023-11-15',20);
/*!40000 ALTER TABLE `supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `televise`
--

DROP TABLE IF EXISTS `televise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `televise` (
  `broadcastName` varchar(50) NOT NULL,
  `restaurantCode` varchar(10) NOT NULL,
  `televisedDate` date NOT NULL,
  PRIMARY KEY (`broadcastName`,`restaurantCode`),
  KEY `restaurantCode` (`restaurantCode`),
  CONSTRAINT `televise_ibfk_1` FOREIGN KEY (`broadcastName`) REFERENCES `broadcast` (`name`),
  CONSTRAINT `televise_ibfk_2` FOREIGN KEY (`restaurantCode`) REFERENCES `restaurant` (`restaurantCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `televise`
--

LOCK TABLES `televise` WRITE;
/*!40000 ALTER TABLE `televise` DISABLE KEYS */;
/*!40000 ALTER TABLE `televise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor` (
  `vendorCode` varchar(6) NOT NULL,
  `vendorName` varchar(35) NOT NULL,
  `ingredients` varchar(30) DEFAULT NULL,
  `price` decimal(7,2) NOT NULL,
  PRIMARY KEY (`vendorCode`),
  CONSTRAINT `CHK_price` CHECK ((`price` > 0.00))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES ('V-001','Fresh & Cool','vegetables',24.62),('V-002','Agricultural Cooperation','mushroom',17.20),('V-003','Fresh Market','squid and octopus',31.82),('V-004','James fresh','mackerel and belt fish',18.20),('V-005','Angelo Poultry','poultry',13.03),('V-006','Meat Market','pork and beef',43.94),('V-007','Busan Fishcake','processed fish',14.32),('V-008','Brady Poultry','poultry and eggs',12.35),('V-009','Rice Mill','rice and millet',25.45),('V-010','Herbal Garden','herbs',14.70),('V-011','Another Rice Mill','rice and millet',25.91),('V-012','Meet Meat','poultry and pork',17.42);
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watch`
--

DROP TABLE IF EXISTS `watch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `watch` (
  `customerIdentifier` varchar(15) NOT NULL,
  `broadcastName` varchar(50) NOT NULL,
  PRIMARY KEY (`customerIdentifier`,`broadcastName`),
  KEY `broadcastName` (`broadcastName`),
  CONSTRAINT `watch_ibfk_1` FOREIGN KEY (`customerIdentifier`) REFERENCES `customer` (`customerIdentifier`) ON DELETE CASCADE,
  CONSTRAINT `watch_ibfk_2` FOREIGN KEY (`broadcastName`) REFERENCES `broadcast` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watch`
--

LOCK TABLES `watch` WRITE;
/*!40000 ALTER TABLE `watch` DISABLE KEYS */;
/*!40000 ALTER TABLE `watch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'termproject'
--

--
-- Dumping routines for database 'termproject'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30 14:22:02
