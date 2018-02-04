CREATE DATABASE  IF NOT EXISTS `partnerboerse` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `partnerboerse`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 173.194.224.212    Database: partnerboerse
-- ------------------------------------------------------
-- Server version	5.6.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auswahl`
--

DROP TABLE IF EXISTS `auswahl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auswahl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titel` varchar(100) NOT NULL,
  `eigenschaftsID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eigenschaftsID` (`eigenschaftsID`),
  CONSTRAINT `auswahl_ibfk_1` FOREIGN KEY (`eigenschaftsID`) REFERENCES `eigenschaft` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auswahl`
--

LOCK TABLES `auswahl` WRITE;
INSERT INTO `auswahl` VALUES (1,'Fleischesser',3),(2,'Veganer',3),(3,'Vegetarier',3),(4,'Frutarier',3),(5,'HipHop',4),(6,'Jazz',4),(7,'Techno',4),(8,'Electro',4),(9,'Schlager',4),(10,'Soul',4),(11,'Rock',4),(12,'Pop',4),(13,'Alternativ',4),(14,'Hauptschulabschluss',6),(15,'Mittlere Reife',6),(16,'Abitur',6),(17,'Bachelor',6),(18,'Master',6),(19,'Doktor',6),(20,'Professor',6);
UNLOCK TABLES;

--
-- Table structure for table `eigenschaft`
--

DROP TABLE IF EXISTS `eigenschaft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eigenschaft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `erlaueterung` varchar(100) DEFAULT NULL,
  `is_a` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eigenschaft`
--

LOCK TABLES `eigenschaft` WRITE;
INSERT INTO `eigenschaft` VALUES (1,'Deine Hobbies:','freitext'),(2,'Dein Lebensmotto:','freitext'),(3,'Deine Lebens- und Ernährungsweise:','auswahl'),(4,'Deine Lieblingsmusik:','auswahl'),(5,'Bescheibe dich in 3 Worten:','freitext'),(6,'Dein Bildungsgrad:','auswahl');
UNLOCK TABLES;

--
-- Table structure for table `freitext`
--

DROP TABLE IF EXISTS `freitext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `freitext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eigenschaftsID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eigenschaftsID` (`eigenschaftsID`),
  CONSTRAINT `freitext_ibfk_1` FOREIGN KEY (`eigenschaftsID`) REFERENCES `eigenschaft` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freitext`
--

LOCK TABLES `freitext` WRITE;
INSERT INTO `freitext` VALUES (1,1),(2,2),(3,5);
UNLOCK TABLES;

--
-- Table structure for table `info`
--

DROP TABLE IF EXISTS `info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) DEFAULT NULL,
  `eigenschaftsID` int(11) NOT NULL,
  `epID` int(11) DEFAULT NULL,
  `suchprofilID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `epID` (`epID`),
  KEY `suchprofilID` (`suchprofilID`),
  KEY `eigenschaftsID` (`eigenschaftsID`),
  CONSTRAINT `info_ibfk_1` FOREIGN KEY (`epID`) REFERENCES `profil` (`id`),
  CONSTRAINT `info_ibfk_2` FOREIGN KEY (`suchprofilID`) REFERENCES `suchprofil` (`id`),
  CONSTRAINT `info_ibfk_3` FOREIGN KEY (`eigenschaftsID`) REFERENCES `eigenschaft` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info`
--

LOCK TABLES `info` WRITE;
INSERT INTO `info` VALUES (5,'Abitur',6,21,NULL),(6,'nett, offen, gesprächig',5,21,NULL),(7,'HipHop',4,21,NULL),(8,'Fleischesser',3,21,NULL),(9,'Tanzen, Fitness',1,21,NULL),(10,'You only live once!',2,21,NULL),(11,'Vegetarier',3,22,NULL),(12,'HipHop',4,22,NULL),(14,'Nigger',5,22,NULL),(15,'Vegetarier',3,NULL,42),(16,'Abitur',6,NULL,42),(17,'Tanzen, ausgehen, Saufen!',1,8,NULL),(18,'Leben am Limit!',2,8,NULL),(19,'Fleischesser',3,8,NULL),(20,'Schlager',4,8,NULL),(21,'freundlich, nett, aufgeschlossen',5,8,NULL),(22,'Mittlere Reife',6,8,NULL),(23,'Schlafen, programmieren',1,9,NULL),(24,'Ah, ist mir scheiß egal!',2,9,NULL),(25,'Fleischesser',3,9,NULL),(26,'Pop',4,9,NULL),(27,'nerdig, nett, ruhig',5,9,NULL),(28,'Abitur',6,9,NULL),(29,'Shoppen, lesen, ausgehen',1,10,NULL),(30,'Das Leben ist zu kurz um traurig zu sein!',2,10,NULL),(31,'Vegetarier',3,10,NULL),(32,'HipHop',4,10,NULL),(33,'super, duper, schön',5,10,NULL),(34,'Hauptschulabschluss',6,10,NULL),(35,'beten, zur Kirche gehen',1,11,NULL),(36,'Gott ist alles!',2,11,NULL),(37,'Veganer',3,11,NULL),(38,'Alternativ',4,11,NULL),(39,'ich liebe Gott!',5,11,NULL),(40,'Mittlere Reife',6,11,NULL),(41,'Singen, tanzen, yoga',1,12,NULL),(42,'Ich bin Verganerin!',2,12,NULL),(43,'Veganer',3,12,NULL),(44,'Jazz',4,12,NULL),(45,'Vegan, Buddha, Om',5,12,NULL),(46,'Bachelor',6,12,NULL),(47,'Feiern, tanzen, Kino',1,13,NULL),(48,'High Life!',2,13,NULL),(49,'Vegetarisch',3,13,NULL),(50,'Techno',4,13,NULL),(51,'techno is love!',5,13,NULL),(52,'Abitur',6,13,NULL),(53,'Basketball, Fußball, Fitness',1,14,NULL),(54,'Survival of the fittest!',2,14,NULL),(55,'Fleischesser',3,14,NULL),(56,'Elektro',4,14,NULL),(57,'stark, athletisch, fitnessfreak',5,14,NULL),(58,'Bachelor',6,14,NULL),(59,'lesen und Pflanzen erforschen',1,15,NULL),(60,'Die Biologie umgibt uns, immer!',2,15,NULL),(61,'Veganer',3,15,NULL),(62,'Soul',4,15,NULL),(63,'ziemlich biologisch abbaubar',5,15,NULL),(64,'Doktor',6,15,NULL),(65,'Autos, Frauen, Waffen',1,16,NULL),(66,'Wozu Geld und Autos, wenn am Ende nur die Seele bleibt?',2,16,NULL),(67,'Fleischesser',3,16,NULL),(68,'HipHop',4,16,NULL),(69,'Rapp is life',5,16,NULL),(70,'Hauptschuleabschluss',6,16,NULL),(71,'Kinder betreuen, spielen, Neues lernen',1,17,NULL),(72,'Verlass dich auf dein Herz, es hat schon geschlagen bevor du denken konntest!',2,17,NULL),(73,'Vegetarier',3,17,NULL),(74,'Soul',4,17,NULL),(75,'Nett, zuvorkommend, liebevoll',5,17,NULL),(76,'Mittlere Reife',6,17,NULL),(77,'Schauspiel',1,18,NULL),(78,'Das Leben ist zu kurz für irgendwann!',2,18,NULL),(79,'Veganer',3,18,NULL),(80,'Rock',4,18,NULL),(81,'einzigartig, klug, offen',5,18,NULL),(82,'Master',6,18,NULL),(83,'Segeln, plündern, Gold suchen',1,19,NULL),(84,'Warum ist der Rum alle?',2,19,NULL),(85,'Fleischesser',3,19,NULL),(86,'Rock',4,19,NULL),(87,'geschminkt, Meer, Fluch',5,19,NULL),(88,'Hauptschulabschluss',6,19,NULL),(89,'Fitness, schlafen, essen',1,20,NULL),(90,'Aufgeben ist leicht, das kann jeder!',2,20,NULL),(91,'Fleischesser',3,20,NULL),(92,'HipHop',4,20,NULL),(93,'sportlich, lieb, schüchtern',5,20,NULL),(94,'Bachelor',6,20,NULL),(95,'shoppen, meine Mädels treffen, feiern',1,27,NULL),(96,'Dein einziges Limit bist du!',2,27,NULL),(97,'Vegetarier',3,27,NULL),(98,'Schlager',4,27,NULL),(99,'süß, offen, geschwätzig',5,27,NULL),(100,'Abitur',6,27,NULL),(101,'Ausstellungen besuchen, Bücher lesen',1,28,NULL),(102,'Science > Opinion!',2,28,NULL),(103,'Veganer',3,28,NULL),(104,'Alternativ',4,28,NULL),(105,'Forscher, Professor, Physik',5,28,NULL),(106,'Professor',6,28,NULL),(107,'Kraftsport, Hanteln heben',1,23,NULL),(108,'No pain, no gain!',2,23,NULL),(109,'Fleischesser',3,23,NULL),(110,'Techno',4,23,NULL),(111,'sportlich, stark, groß',5,23,NULL),(112,'Abitur',6,23,NULL),(113,'Mädelsabende organisieren, Spaß haben',1,24,NULL),(114,'Leave it, love it or change it!',2,24,NULL),(115,'Vegetarier',3,24,NULL),(116,'Soul',4,24,NULL),(117,'schnell, offen, freundlich',5,24,NULL),(118,'Mittlere Reife',6,24,NULL),(119,'Menschen helfen, Ehrenamtlich arbeiten',1,25,NULL),(120,'Sei so wie du bist!',2,25,NULL),(121,'Fleischesser',3,25,NULL),(122,'Pop',4,25,NULL),(123,'hilfsbereit, aufgeschlossen, nett',5,25,NULL),(124,'Abitur',6,25,NULL),(125,'Reisen, neue Leute kennen lernen',1,26,NULL),(126,'Travel the world!',2,26,NULL),(127,'Vegetarier',3,26,NULL),(128,'Elektro',4,26,NULL),(129,'travel, hiking, people',5,26,NULL),(130,'Bachelor',6,26,NULL),(131,'Fitness',1,NULL,43),(132,'Vegetarier',3,NULL,43),(133,'HipHop',4,NULL,43),(134,'Abitur',6,NULL,43),(135,'Lesen, Sprachen lernen',1,29,NULL),(136,'Lass mal an uns selber glauben!',2,29,NULL),(137,'Vegetarier',3,29,NULL),(138,'Alternativ',4,29,NULL),(139,'intelligent, ruhig, schüchtern',5,29,NULL),(140,'Bachelor',6,29,NULL),(141,'Tauchen, Reiten',1,30,NULL),(142,'You only live once!',2,30,NULL),(143,'Fleischesser',3,30,NULL),(144,'Jazz',4,30,NULL),(145,'klein, frech, süß',5,30,NULL),(146,'Abitur',6,30,NULL);
UNLOCK TABLES;

--
-- Table structure for table `kontaktsperre`
--

DROP TABLE IF EXISTS `kontaktsperre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kontaktsperre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fpID` int(11) NOT NULL,
  `epID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fpID` (`fpID`),
  KEY `epID` (`epID`),
  CONSTRAINT `kontaktsperre_ibfk_1` FOREIGN KEY (`fpID`) REFERENCES `profil` (`id`),
  CONSTRAINT `kontaktsperre_ibfk_2` FOREIGN KEY (`epID`) REFERENCES `profil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kontaktsperre`
--

LOCK TABLES `kontaktsperre` WRITE;
INSERT INTO `kontaktsperre` VALUES (1,8,19),(4,16,9),(6,41,21),(8,46,22),(9,16,21);
UNLOCK TABLES;

--
-- Table structure for table `merkzettel`
--

DROP TABLE IF EXISTS `merkzettel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merkzettel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epID` int(11) NOT NULL,
  `fpID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `epID` (`epID`),
  KEY `fpID` (`fpID`),
  CONSTRAINT `merkzettel_ibfk_1` FOREIGN KEY (`epID`) REFERENCES `profil` (`id`),
  CONSTRAINT `merkzettel_ibfk_2` FOREIGN KEY (`fpID`) REFERENCES `profil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merkzettel`
--

LOCK TABLES `merkzettel` WRITE;
INSERT INTO `merkzettel` VALUES (14,10,8),(16,16,10),(17,19,11),(19,21,9),(22,21,51),(23,21,20),(25,22,50),(26,22,21),(27,21,22);
UNLOCK TABLES;

--
-- Table structure for table `profil`
--

DROP TABLE IF EXISTS `profil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `vorname` varchar(100) NOT NULL,
  `nachname` varchar(100) NOT NULL,
  `geburtstag` date NOT NULL,
  `haarfarbe` varchar(50) NOT NULL,
  `koerpergroesse` int(5) NOT NULL,
  `raucher` tinyint(1) NOT NULL,
  `religion` varchar(50) NOT NULL,
  `geschlecht` varchar(32) NOT NULL,
  `suche_nach` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profil`
--

LOCK TABLES `profil` WRITE;
INSERT INTO `profil` VALUES (8,'test@example.com','Simon','Burghardt','1996-02-02','braun',175,0,'buddhist','Mann','Männer'),(9,'test1@example.com','Siam','Gundermann','1985-05-20','schwarz',180,1,'katholisch','Mann','Frauen'),(10,'test3@example.com','Yasemin','Kiziloglu','1990-06-28','braun',170,0,'moslem','Frau','Männer'),(11,'test4@example.com','Alex','Hermann','1992-08-09','schwarz',169,1,'katholisch','Mann','Frauen'),(12,'test5@example.com','Nelly','Furtado','1988-12-29','blond',154,0,'buddhist','Frau','Frauen'),(13,'test6@example.com','Mareike','Schmidt','1990-02-14','braun',160,1,'katholisch','Frau','Männer'),(14,'test7@example.com','Burak','May','1978-11-12','braun',185,0,'moslem','Mann','Frauen'),(15,'test8@example.com','Saskia','Schober','1967-10-17','blond',167,1,'katholisch','Frau','Männer'),(16,'test9@example.com','Memmet','Baran','1988-10-25','schwarz',198,0,'moslem','Mann','Frauen'),(17,'test10@example.com','Cordula','McConner','1980-04-03','braun',155,1,'buddhist','Frau','Männer'),(18,'test11@example.com','Cameron','Diaz','1972-09-22','blond',176,0,'katholisch','Frau','Männer'),(19,'test12@example.com','Jack','Sparrow','1980-12-08','schwarz',175,1,'atheist','Mann','Frauen'),(20,'test13@example.com','Daniel','Braun','1995-03-11','braun',182,0,'katholisch','Mann','Frauen'),(21,'evelyn.hettmann@gmail.com','Evelyn','Hettmann','1994-08-04','blond',175,0,'evangelisch','Frau','Männer'),(22,'simon.burghardt@googlemail.com','Simon','Burghardt','1996-08-26','braun',182,0,'evangelisch','Mann','Frauen'),(23,'test14@example.com','Yusuf','Yildirim','1986-08-13','blond',188,1,'moslem','Mann','Frauen'),(24,'test15@example.com','Franziska','Biermann','1991-12-24','schwarz',156,0,'evangelisch','Frau','Männer'),(25,'test16@example.com','Andrea','Jäger','1974-04-02','schwarz',159,1,'katholisch','Frau','Männer'),(26,'test17@example.com','Steffen','Egger','1990-11-09','braun',180,0,'buddhist','Mann','Frauen'),(27,'test18@example.com','Jennifer','Dresdner','1983-02-19','blond',160,1,'katholisch','Frau','Frauen'),(28,'test19@example.com','Uwe','Schwarz','1980-05-14','braun',178,0,'evangelisch','Mann','Männer'),(29,'test20@example.com','Fatima','Cetin','1987-07-20','schwarz',166,1,'moslem','Frau','Männer'),(30,'test21@example.com','Stephanie','Schwartz','1966-01-11','schwarz',172,0,'evangelisch','Frau','Frauen'),(31,'test22@example.com','Doreen','Gottschalk','1971-09-09','blond',168,1,'katholisch','Frau','Männer'),(32,'test23@example.com','Maik','Unger','1969-11-10','braun',182,0,'evangelisch','Mann','Frauen'),(33,'test24@example.com','Sophie','Baader','1989-02-24','schwarz',170,1,'moslem','Frau','Männer'),(34,'test25@example.com','Dieter','Friedmann','1987-03-05','braun',189,1,'katholisch','Mann','Männer'),(35,'test26@example.com','Christine','Gloeckner','1993-06-22','blond',167,0,'atheist','Frau','Frauen'),(36,'test27@example.com','Ralf','Köhler','1976-04-29','schwarz',190,1,'atheist','Mann','Frauen'),(37,'test28@example.com','Markus','Pfaff','1990-07-25','blond',160,1,'buddhist','Mann','Frauen'),(38,'test29@example.com','Felix','Mönch','1993-11-18','schwarz',197,0,'atheist','Mann','Frauen'),(39,'test30@example.com','Mario','Weiß','1996-03-13','braun',188,1,'evangelisch','Mann','Frauen'),(40,'test31@example.com','Kristina','Holzmann','1979-09-21','blond',160,1,'katholisch','Frau','Männer'),(41,'test32@example.com','Marko','Bieber','1985-02-18','braun',170,0,'atheist','Mann','Frauen'),(42,'test33@example.com','Alexander','Hartmann','1999-01-01','schwarz',169,0,'evangelisch','Mann','Frauen'),(43,'test34@example.com','Mille','Christensen','1983-04-01','blond',180,1,'atheist','Frau','Männer'),(44,'test35@example.com','Aziza','Nuguse','1990-05-25','schwarz',184,1,'moslem','Mann','Frauen'),(45,'test36@example.com','Luna','Bollina','1978-12-06','braun',156,0,'katholisch','Frau','Mann'),(46,'test37@example.com','Eleonora','Pugliesi','1991-09-29','blond',162,1,'katholisch','Frau','Männer'),(47,'test38@example.com','Edoardo','Padovano','1982-06-23','schwarz',186,1,'buddhist','Mann','Frauen'),(48,'test39@example.com','Alijana','Baukman','1984-07-10','braun',170,0,'evangelisch','Frau','Männer'),(49,'test40@example.com','Lucas','Yermakov','1992-11-20','blond',167,1,'atheist','Mann','Frauen'),(50,'test41@example.com','Fearne','Hunter','1975-08-25','braun',164,0,'katholisch','Frau','Männer'),(51,'test42@example.com','Thomas','Gottschalk','1994-08-05','blond',175,0,'evangelisch','Mann','Frauen');
UNLOCK TABLES;

--
-- Table structure for table `suchprofil`
--

DROP TABLE IF EXISTS `suchprofil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suchprofil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titel` varchar(128) NOT NULL,
  `religion` text NOT NULL,
  `haarfarbe` varchar(100) NOT NULL,
  `koerpergroesse` int(20) NOT NULL,
  `raucher` tinyint(1) NOT NULL,
  `age` int(20) NOT NULL,
  `epID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `epID` (`epID`),
  CONSTRAINT `suchprofil_ibfk_1` FOREIGN KEY (`epID`) REFERENCES `profil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suchprofil`
--

LOCK TABLES `suchprofil` WRITE;
INSERT INTO `suchprofil` VALUES (7,'suchprofil1','katholisch','braun',2,1,20,10),(8,'suchprofil3','katholisch','braun',2,1,20,10),(9,'aaaa','katholisch','schwarz',180,1,120,11),(10,'geil','katholisch','schwarz',150,1,20,11),(11,'jüdische Frauen','katholisch','schwarz',150,1,20,15),(12,'hallo','katholisch','schwarz',170,1,20,16),(13,'jüdische frauen','moslem','schwarz',150,1,20,16),(14,'aa','katholisch','schwarz',150,1,20,16),(15,'bb','katholisch','schwarz',150,1,20,16),(16,'kkkk','katholisch','schwarz',150,1,20,16),(17,'bbb','katholisch','schwarz',150,1,20,16),(18,'ss','katholisch','schwarz',150,1,20,16),(19,'nate','katholisch','schwarz',150,1,20,18),(20,'ddd','katholisch','schwarz',150,1,20,16),(21,'123','katholisch','schwarz',150,1,20,16),(22,'name1','hindu','blond',160,0,30,19),(23,'','katholisch','blond',150,0,20,16),(24,'freundin','evangelisch','blond',150,0,50,16),(25,'fsf','katholisch','schwarz',150,1,20,16),(42,'Suchprofil','katholisch','blond',160,0,20,22),(43,'NiceBoys','katholisch','braun',170,0,20,21);
UNLOCK TABLES;

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `epID` int(11) NOT NULL,
  `fpID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `epID` (`epID`),
  KEY `fpID` (`fpID`),
  CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`epID`) REFERENCES `profil` (`id`),
  CONSTRAINT `visit_ibfk_2` FOREIGN KEY (`fpID`) REFERENCES `profil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--

LOCK TABLES `visit` WRITE;
INSERT INTO `visit` VALUES (1,16,19),(2,16,17),(3,16,18),(4,16,14),(5,16,14),(6,16,12),(7,16,12),(8,16,18),(9,16,18),(10,16,12),(11,16,14),(12,16,18),(13,16,12),(14,16,12),(15,16,14),(16,16,12),(17,16,12),(18,16,12),(19,16,12),(20,16,12),(21,16,12),(22,16,14),(23,16,12),(24,16,18),(25,16,12),(26,16,12),(27,16,12),(28,16,14),(29,21,15),(30,21,12),(31,21,12),(32,21,13),(33,21,18),(37,21,11),(38,21,33),(40,21,11),(41,21,9),(42,21,20),(43,21,16),(44,21,20),(45,21,26),(46,21,9),(47,21,23),(48,21,20),(49,21,22),(50,21,22),(51,21,16),(52,21,51),(53,21,51),(57,21,51),(58,21,20),(59,21,51),(60,21,41),(61,22,21),(62,22,46),(63,22,50),(64,21,32),(65,21,14);
UNLOCK TABLES;

--
-- Dumping routines for database 'partnerboerse'
--

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
