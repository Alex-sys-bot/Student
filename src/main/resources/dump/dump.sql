-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: student
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number_course` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_group`
--

DROP TABLE IF EXISTS `course_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cipher` varchar(255) NOT NULL,
  `learning_year` varchar(255) NOT NULL,
  `course_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi70g07q6x0gvh3b698xfk91tp` (`course_id`),
  KEY `FK7kpadc7g02w7uuhc8t5c9fst4` (`group_id`),
  CONSTRAINT `FK7kpadc7g02w7uuhc8t5c9fst4` FOREIGN KEY (`group_id`) REFERENCES `grup` (`id`),
  CONSTRAINT `FKi70g07q6x0gvh3b698xfk91tp` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_group`
--

LOCK TABLES `course_group` WRITE;
/*!40000 ALTER TABLE `course_group` DISABLE KEYS */;
INSERT INTO `course_group` VALUES (1,'КБ33-12тБ','с 2017-09-01 по 2018-05-23',2,1),(2,'КБ26-11тБ','с 2017-09-01 по 2018-06-14',1,2),(3,'4-Б9-12-1','2021-2021',2,3),(4,'КБ33-12тБ','с 2017-09-01 по 2018-05-23',1,4);
/*!40000 ALTER TABLE `course_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descipline_semester_has_teachers`
--

DROP TABLE IF EXISTS `descipline_semester_has_teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descipline_semester_has_teachers` (
  `teachers_id` int NOT NULL,
  `discipline_semester_id` int NOT NULL,
  PRIMARY KEY (`teachers_id`,`discipline_semester_id`),
  KEY `FK4p042k3agsdxfelreclurk57n` (`discipline_semester_id`),
  CONSTRAINT `FK4p042k3agsdxfelreclurk57n` FOREIGN KEY (`discipline_semester_id`) REFERENCES `discipline_semester` (`id`),
  CONSTRAINT `FK4sctqd5n8a6hbkg57xkw0w9v6` FOREIGN KEY (`teachers_id`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descipline_semester_has_teachers`
--

LOCK TABLES `descipline_semester_has_teachers` WRITE;
/*!40000 ALTER TABLE `descipline_semester_has_teachers` DISABLE KEYS */;
INSERT INTO `descipline_semester_has_teachers` VALUES (7,1),(1,2);
/*!40000 ALTER TABLE `descipline_semester_has_teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline`
--

DROP TABLE IF EXISTS `discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discipline` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline`
--

LOCK TABLES `discipline` WRITE;
/*!40000 ALTER TABLE `discipline` DISABLE KEYS */;
INSERT INTO `discipline` VALUES (1,'Математика'),(2,'Физическая культура'),(3,'Физика'),(4,'Русский язык'),(5,'Литература'),(6,'ОБЖ'),(7,'Обществознание'),(8,'Экономика'),(9,'История'),(10,'Химия'),(11,'Информатика'),(12,'Иностранный язык'),(13,'Астрономия'),(14,'География'),(15,'Биология'),(16,'Геометрия'),(17,'Психология'),(18,'Право'),(19,'Архитектура ПО'),(20,'Алгоритмизация'),(21,'МДК-03');
/*!40000 ALTER TABLE `discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline_learning_plan`
--

DROP TABLE IF EXISTS `discipline_learning_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discipline_learning_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `audit_hours` int NOT NULL,
  `max_hours` int NOT NULL,
  `discipline_id` int NOT NULL,
  `learning_plan_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vy03xf6hl3ywx5wgl47x9p5l` (`discipline_id`),
  KEY `FKdom31vcpwfi926bbans493n1u` (`learning_plan_id`),
  CONSTRAINT `FK8vy03xf6hl3ywx5wgl47x9p5l` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`id`),
  CONSTRAINT `FKdom31vcpwfi926bbans493n1u` FOREIGN KEY (`learning_plan_id`) REFERENCES `learning_plan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline_learning_plan`
--

LOCK TABLES `discipline_learning_plan` WRITE;
/*!40000 ALTER TABLE `discipline_learning_plan` DISABLE KEYS */;
INSERT INTO `discipline_learning_plan` VALUES (1,60,70,1,1),(2,28,28,2,1),(3,90,100,1,2),(4,4,10,6,2),(5,90,100,1,1);
/*!40000 ALTER TABLE `discipline_learning_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline_semester`
--

DROP TABLE IF EXISTS `discipline_semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discipline_semester` (
  `id` int NOT NULL AUTO_INCREMENT,
  `audit_hours` int NOT NULL,
  `discipline_learning_plan_id` int NOT NULL,
  `semester_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKduy4pdh01rhcmqten9p1kjkxg` (`discipline_learning_plan_id`),
  KEY `FKbqbciwxgo63qhhq8kql77yk1y` (`semester_id`),
  CONSTRAINT `FKbqbciwxgo63qhhq8kql77yk1y` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`),
  CONSTRAINT `FKduy4pdh01rhcmqten9p1kjkxg` FOREIGN KEY (`discipline_learning_plan_id`) REFERENCES `discipline_learning_plan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline_semester`
--

LOCK TABLES `discipline_semester` WRITE;
/*!40000 ALTER TABLE `discipline_semester` DISABLE KEYS */;
INSERT INTO `discipline_semester` VALUES (1,30,1,1),(2,14,2,1),(3,13,3,2),(4,4,4,3),(5,36,3,3);
/*!40000 ALTER TABLE `discipline_semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grup`
--

DROP TABLE IF EXISTS `grup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `number_order` int NOT NULL,
  `nameGroup` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grup`
--

LOCK TABLES `grup` WRITE;
/*!40000 ALTER TABLE `grup` DISABLE KEYS */;
INSERT INTO `grup` VALUES (1,'2017-08-11',1,'12т'),(2,'2017-08-09',2,'11т'),(3,'2021-05-22',3,'4в'),(4,'2017-08-10',5,'10к');
/*!40000 ALTER TABLE `grup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `learning_plan`
--

DROP TABLE IF EXISTS `learning_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `learning_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `year_of_admission` date NOT NULL,
  `qualification_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk4c5jul9bbk8sakhepqya1tmm` (`qualification_id`),
  CONSTRAINT `FKk4c5jul9bbk8sakhepqya1tmm` FOREIGN KEY (`qualification_id`) REFERENCES `qualification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `learning_plan`
--

LOCK TABLES `learning_plan` WRITE;
/*!40000 ALTER TABLE `learning_plan` DISABLE KEYS */;
INSERT INTO `learning_plan` VALUES (1,'2017-06-14',2),(2,'2021-04-05',1),(3,'2021-05-31',2);
/*!40000 ALTER TABLE `learning_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateLesson` date NOT NULL,
  `themeLesson` varchar(255) DEFAULT NULL,
  `disciplineSemester_id` int NOT NULL,
  `typeLesson_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9kmp8nmxu3r94c2xr5mxmy6e9` (`typeLesson_id`),
  KEY `FKagl6wa6nh6cnkxcx48in5ra43` (`disciplineSemester_id`),
  CONSTRAINT `FK9kmp8nmxu3r94c2xr5mxmy6e9` FOREIGN KEY (`typeLesson_id`) REFERENCES `typelesson` (`id`),
  CONSTRAINT `FKagl6wa6nh6cnkxcx48in5ra43` FOREIGN KEY (`disciplineSemester_id`) REFERENCES `discipline_semester` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'2017-09-02','Функции',1,2),(2,'2017-09-02','Прыжки',2,8),(3,'2017-09-03','Интегралы',1,2),(4,'2017-09-13','Интегралы',1,2),(5,'2021-06-11','Логарифмы',3,7),(6,'2021-06-02','Тест',4,2);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress`
--

DROP TABLE IF EXISTS `progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progress` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rating` int DEFAULT NULL,
  `lesson_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq39mk0q3p0lfj0oxts0fcsluh` (`student_id`),
  KEY `FKrxjx4dgn7kfag4l8pdvsphlwu` (`lesson_id`),
  CONSTRAINT `FKq39mk0q3p0lfj0oxts0fcsluh` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKrxjx4dgn7kfag4l8pdvsphlwu` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress`
--

LOCK TABLES `progress` WRITE;
/*!40000 ALTER TABLE `progress` DISABLE KEYS */;
INSERT INTO `progress` VALUES (1,4,1,1),(2,4,2,1),(3,4,3,1),(4,5,1,2),(5,4,4,1),(6,4,2,2),(7,4,3,2),(8,4,5,4),(9,4,4,4),(10,5,5,1),(11,5,5,2),(12,5,2,2),(13,4,6,1),(14,5,6,5),(15,4,6,5);
/*!40000 ALTER TABLE `progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qualification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `specialty_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6053f8s6os5yegiquix796i9b` (`specialty_id`),
  CONSTRAINT `FK6053f8s6os5yegiquix796i9b` FOREIGN KEY (`specialty_id`) REFERENCES `specialty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` VALUES (1,'Программирование',1),(2,'Информационные системы',1),(3,'1 разряд',2),(4,'Компьютерная графика',5),(5,'тест',6);
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`role`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Администратор'),(2,'Преподаватель'),(3,'Секретарь УЧ'),(4,'Директор'),(5,'Зам Директора'),(6,'Разработчик');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semester` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number_semester` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrvmw9h6lxnuhu44uv45m7fsw6` (`course_id`),
  CONSTRAINT `FKrvmw9h6lxnuhu44uv45m7fsw6` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
INSERT INTO `semester` VALUES (1,1,1),(2,2,1),(3,3,2),(4,4,2),(5,5,3),(6,6,3),(7,7,4),(8,8,4);
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialty`
--

DROP TABLE IF EXISTS `specialty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialty`
--

LOCK TABLES `specialty` WRITE;
/*!40000 ALTER TABLE `specialty` DISABLE KEYS */;
INSERT INTO `specialty` VALUES (1,'09.02.07','Информационные системы и программирование'),(2,'33014','Слесарь-сантехник'),(3,'12.42.12','Инженер-технолог'),(5,'123435','Анимация'),(6,'12345','тест');
/*!40000 ALTER TABLE `specialty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_birthday` date NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `number_credit_book` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `receipt_date` date NOT NULL,
  `course_group_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhmje3etu0o2swl8sjx77b57sk` (`course_group_id`),
  CONSTRAINT `FKhmje3etu0o2swl8sjx77b57sk` FOREIGN KEY (`course_group_id`) REFERENCES `course_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'2001-03-26','Алексей','Серебряков','1','Александрович','8(908)-734-08-57','2017-08-14',1),(2,'2000-10-31','Данил','Стожков','2','Алексеевич','8(800)-055-16-23','2017-08-13',1),(3,'2001-04-17','Александр','Есменеев','3','Викторович','8(903)-068-17-23','2017-08-13',2),(4,'2021-04-27','Виктор ','Синицын','4','Константинович','234234235','2021-04-26',2),(5,'2021-05-27','Александр','Себиряк','5','Дмитреевич','','2021-05-27',3),(6,'2021-06-08','Иван','Иванов','1234','Иванович','нет','2021-06-08',3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Иван','Иванов','Иванович'),(2,'Ольга','Голубев','Петровна'),(3,'Дмитрий','Медведев','Анатольчевич'),(4,'Михаил','Васильев','Петрович'),(5,'Александр','Киселёв','Анатольевич'),(6,'Иван','Иванов','Иванович'),(7,'Михаил','Синицин','Петрович'),(8,'Виктор','Корягин','Дмитриевич'),(9,'Алексей','Синицин','Дмитриевич');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typelesson`
--

DROP TABLE IF EXISTS `typelesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typelesson` (
  `id` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typelesson`
--

LOCK TABLES `typelesson` WRITE;
/*!40000 ALTER TABLE `typelesson` DISABLE KEYS */;
INSERT INTO `typelesson` VALUES (1,'Беседа'),(2,'Лекция'),(3,'Экскурсия'),(4,'Киноурок'),(5,'Самостоятельная работа'),(6,'Лаболаторная работа'),(7,'Практическая работа'),(8,'Занятия в спортивном зале');
/*!40000 ALTER TABLE `typelesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Alex','1234',6),(2,'Victor','2',2);
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

-- Dump completed on 2021-06-16 10:04:25
