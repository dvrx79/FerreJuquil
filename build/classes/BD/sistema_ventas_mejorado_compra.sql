-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sistema_ventas_mejorado
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `id_compra` int NOT NULL AUTO_INCREMENT,
  `monto_total` decimal(10,2) DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `fecha_compra` date DEFAULT NULL,
  `estado` enum('pendiente','recibido','cancelado') DEFAULT 'pendiente',
  `id_producto` int DEFAULT NULL,
  `id_proveedor` int DEFAULT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `id_producto` (`id_producto`),
  KEY `id_proveedor` (`id_proveedor`),
  CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (1,850.00,100,'2025-04-01','recibido',1,1),(2,3000.00,200,'2025-04-02','recibido',2,2),(3,1500.00,150,'2025-04-03','pendiente',3,3),(4,4200.00,120,'2025-04-04','pendiente',4,4),(5,2400.00,300,'2025-04-05','recibido',5,5),(6,901.44,100,'2025-04-28','recibido',6,303),(7,255.41,15,'2025-04-28','recibido',7,15),(8,350.56,10,'2025-04-28','recibido',8,311),(9,225.36,15,'2025-04-28','recibido',9,603),(10,100.16,20,'2025-04-28','recibido',10,24),(11,240.38,20,'2025-04-28','recibido',11,604),(12,464.00,20,'2025-04-28','recibido',12,49),(13,4060.00,100,'2025-04-28','recibido',13,324),(14,1160.00,50,'2025-04-28','recibido',14,9),(15,696.00,50,'2025-04-28','recibido',15,596),(16,928.00,100,'2025-04-28','recibido',16,605),(17,348.00,100,'2025-04-28','recibido',17,307),(18,2900.00,250,'2025-04-28','recibido',18,226),(19,870.00,50,'2025-04-28','recibido',19,309),(20,1856.00,200,'2025-04-28','recibido',20,1),(21,1218.00,150,'2025-04-28','recibido',21,2),(22,580.00,100,'2025-04-28','recibido',22,2),(23,1160.00,25,'2025-05-05','recibido',23,3),(24,348.00,100,'2025-05-05','recibido',24,1),(25,696.00,100,'2025-05-05','recibido',25,1),(26,348.00,100,'2025-05-05','recibido',26,1),(27,3480.00,50,'2025-05-05','recibido',27,2),(28,1160.00,100,'2025-05-05','recibido',28,2),(29,522.00,30,'2025-05-05','recibido',29,1),(30,232.00,20,'2025-05-05','recibido',30,4),(31,464.00,20,'2025-05-05','recibido',31,3),(32,2320.00,50,'2025-05-05','recibido',32,301),(33,696.00,30,'2025-05-05','recibido',33,302),(34,348.00,20,'2025-05-05','recibido',34,1),(35,2900.00,50,'2025-05-05','recibido',35,4),(36,1392.00,10,'2025-05-05','recibido',36,2),(37,290.00,50,'2025-05-05','recibido',37,1),(38,417.60,30,'2025-05-05','recibido',38,2),(39,174.00,10,'2025-05-05','recibido',39,4),(40,11600.00,100,'2025-05-05','recibido',40,3),(41,3480.00,100,'2025-05-05','recibido',41,3),(42,1392.00,40,'2025-05-05','recibido',42,4),(43,5800.00,100,'2025-05-05','recibido',43,3),(44,3480.00,50,'2025-05-05','recibido',44,8),(45,435.00,25,'2025-05-05','recibido',45,3),(46,348.00,12,'2025-05-05','recibido',46,3),(47,928.00,40,'2025-05-05','recibido',47,1),(48,406.00,10,'2025-05-05','recibido',48,7),(49,5800.00,50,'2025-05-05','recibido',49,2),(50,609.00,15,'2025-05-05','recibido',50,9),(51,1160.00,100,'2025-05-05','recibido',51,303),(52,870.00,25,'2025-05-05','recibido',52,3),(53,1044.00,30,'2025-05-05','recibido',53,1),(54,17400.00,250,'2025-05-05','recibido',54,3),(55,928.00,100,'2025-05-05','recibido',55,1),(56,435.00,15,'2025-05-05','recibido',56,3),(57,27840.00,300,'2025-05-05','recibido',57,2),(58,3480.00,300,'2025-05-05','recibido',58,1),(59,5220.00,300,'2025-05-05','recibido',59,3),(60,3897.60,280,'2025-05-05','recibido',60,3),(61,146160.00,840,'2025-05-05','recibido',61,3),(62,1044.00,5,'2025-05-05','recibido',62,3),(63,986.00,5,'2025-05-05','recibido',63,6),(64,4060.00,100,'2025-05-05','recibido',64,1),(65,1392.00,200,'2025-05-05','recibido',65,3),(66,928.00,100,'2025-05-05','recibido',66,7),(67,928.00,100,'2025-05-05','recibido',67,7),(68,1566.00,150,'2025-05-05','recibido',68,7),(69,2320.00,200,'2025-05-05','recibido',69,4),(70,4176.00,300,'2025-05-05','recibido',70,3),(71,1044.00,300,'2025-05-05','recibido',71,3),(72,1392.00,300,'2025-05-05','recibido',72,3),(73,1740.00,300,'2025-05-05','recibido',73,3),(74,870.00,50,'2025-05-05','recibido',74,9),(75,1160.00,50,'2025-05-05','recibido',75,4),(76,2320.00,100,'2025-05-05','recibido',76,2),(77,609.00,15,'2025-05-05','recibido',77,2),(78,348.00,30,'2025-05-05','recibido',78,2),(79,580.00,50,'2025-05-05','recibido',79,2),(80,928.00,100,'2025-05-05','recibido',80,2),(81,5220.00,30,'2025-05-05','recibido',81,302),(82,3480.00,500,'2025-05-05','recibido',82,9),(83,1740.00,100,'2025-05-05','recibido',83,3),(84,1856.00,20,'2025-05-05','recibido',84,303),(85,1856.00,10,'2025-05-05','recibido',85,306),(86,1740.00,30,'2025-05-05','recibido',86,3),(87,3712.00,20,'2025-05-05','recibido',87,14),(88,1392.00,200,'2025-05-05','recibido',88,4),(89,1392.00,40,'2025-05-05','recibido',89,2),(90,3480.00,10,'2025-05-05','recibido',90,7),(91,3480.00,15,'2025-05-05','recibido',91,7),(92,3480.00,15,'2025-05-05','recibido',92,306),(93,2088.00,40,'2025-05-05','recibido',93,9),(94,2088.00,10,'2025-05-05','recibido',94,13),(95,8120.00,100,'2025-05-05','recibido',95,319),(96,4408.00,20,'2025-05-05','recibido',96,4),(97,1160.00,50,'2025-05-05','recibido',97,600),(98,2784.00,200,'2025-05-05','recibido',98,5),(99,3480.00,100,'2025-05-05','recibido',99,6),(100,2320.00,100,'2025-05-05','recibido',100,4),(101,4640.00,400,'2025-05-05','recibido',101,597);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-06 14:00:19
