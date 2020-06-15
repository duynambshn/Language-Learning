-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: hns_lts
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dictionary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dictionary_name` varchar(45) NOT NULL,
  `release_flag` varchar(45) NOT NULL,
  `sentence_language1` varchar(45) NOT NULL,
  `sentence_language2` varchar(45) NOT NULL,
  `content_creater` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionary`
--

LOCK TABLES `dictionary` WRITE;
/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` VALUES (1,'English-Japanese','公開','English','Japanese','Nam');
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sentence`
--

DROP TABLE IF EXISTS `sentence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sentence` (
  `id` int NOT NULL AUTO_INCREMENT,
  `origin_sentence` varchar(200) DEFAULT NULL,
  `translate_sentence` varchar(200) DEFAULT NULL,
  `explanation` varchar(200) DEFAULT NULL,
  `sound_id` int DEFAULT NULL,
  `dictionary_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dic_idx` (`dictionary_id`),
  KEY `fk_sound_idx` (`sound_id`),
  CONSTRAINT `fk_dic` FOREIGN KEY (`dictionary_id`) REFERENCES `dictionary` (`id`),
  CONSTRAINT `fk_sound` FOREIGN KEY (`sound_id`) REFERENCES `sound` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sentence`
--

LOCK TABLES `sentence` WRITE;
/*!40000 ALTER TABLE `sentence` DISABLE KEYS */;
INSERT INTO `sentence` VALUES (1,'We must respect the will of the individual.','私はナムです。','respect    ,will    ,individual    ',1,1),(2,'Take it easy. I can assure you that everything will turn out fine.','    ','Take it easy    ,\nassure A (that)    ,\nturn out (to be)...  ',2,1),(3,'Let go of your negative outlook on life. Always maintain a positive attitude.','    ','let go of../let ... go    ,\nnegative    ,\noutlook    ,\nmaintain    ,\npositive    ,\nattitude(to/toward...)    ',3,1),(4,'You should be fair to everyone regardless of national origin, gender, or creed.','    ','fair    ,\nregardless of ...    ,\nnational    ,\norigin    ,\ngender    ,\ncreed    ',NULL,1),(5,'Equality is guaranteed by the Constitution.','    ','equality    ,\nguarantee...    ,\nconstitution    ',NULL,1),(6,'He leaned against the pillar and gazed at the Statue of Liberty.','    ','lean (...)    ,\npillar    ,\ngaze (at...)    ,\nstatue    ,\nliberty    ',NULL,1),(7,'A woman passed by me giving off a subtle scent of perfume. It reminded me of my ex-girlfriend.','    ','pass by (...)    ,\ngive off ...    ,\nsubtle    ,\nscent    ,\nperfume    ,\nremind...    ,\nex-...    ',NULL,1),(8,'\"Natto\" smells awful but tastes terrific.','    ','smell (...)    ,\nawful    ,\ntaste...    ,\nterrific',NULL,1),(9,'\"I\'m soaked with sweat.\" \"Stand back! You stink. Take a shower.\"','    ','soaked    ,\nsweat    ,\nstand back    ,\nstink    ,\ntake a shower    ',NULL,1),(10,'Bob was so beside himself that he could scarcely tell fact from fiction.','    ','be beside oneself (with...)    ,\nscarcely    ,\ntell A from B    ,\nfact    ,\nfiction    ',NULL,1),(11,'His new novel, which combines prose with his gift for poetry, is going to be published.','    ','novel    ,\ncombine    ,\nprose    ,\ngift    ,\npoetry    ,\npublish...    ',NULL,1),(12,'An up-to-date edition of the encyclopedia will come out next month.','    ','up-to-date...    ,\nedition    ,\nencyclopedia    ,\ncome out    ',NULL,1),(13,'Ms. Yamada translated the fascinating fairy tale into plain Japanese.','    ','translate (...)(from A into B)    ,\nfascinating    ,\nfairy    ,\ntale    ,\nplain    ',NULL,1),(14,'The following passage is quoted from a well-known fable.','    ','following    ,\npassage    ,\nquote (...)    ,\nwell-known    ,\nfable    ',NULL,1),(15,'\"Are you familiar with contemporary literature?\" \n\"I know next to nothing about it.\"','    ','familiar    ,\ncontemporary    ,\nliterature    ,\nnext to...    ',NULL,1),(16,'At times I confuse \"curve\" with \"carve\".','    ','at times    ,\nconfuse...    ,\ncurve    ,\ncarve (...)    ',NULL,1),(17,'Don\'t be shy. Your pronunciation is more or less correct.','    ','shy    ,\npronunciation    ,\nmore or less    ,\ncorrect    ',NULL,1),(18,'This article contains tips for those who are eager to increase their vocabulary.','    ','article    ,\ncontain...    ,\ntip    ,\nthose who~    ,\neager    ,\nincrease (...)    ,\nvocabulary    ',NULL,1),(19,'His latest works are on display at the city hall. They are fabulous beyond description.','    ','latest    ,\nwork    ,\ndisplay    ,\nhall    ,\nfabulous    ,\ndescription    ',NULL,1),(20,'\"Could you move over a little?\" \n\"Oh, sorry. I didn\'t realize I was taking up so much space.\"','    ','move over    ,\nrealize...    ,\ntake up...    ',NULL,1),(21,'\"What\'s this ugly object?\" \n\"This is a piece of abstract art!\"','    ','ugly    ,\nobject    ,\na piece of...    ,\nabstract    ',NULL,1),(22,'I begged Richie to lend me a hundred bucks, but he shook his head, saying, \"I\'m broke, too.\"','    ','beg (...)    ,\nlend ... A    ,\nbuck    ,\nshake one\'s head    ,\nbe (flat) broke    ',NULL,1),(23,'\"I\'m apt to buy things on impulse whenever something is on sale.\" \n\"So am I.\"','    ','apt    ,\nimpulse    ,\non sale    ,\nSo am I.    ',NULL,1),(24,'As it is, ordinary people cannot afford to purchase such luxuries.','    ','as it is    ,\nordinary    ,\ncan afford...    ,\npurchase...    ,\nluxury    ',NULL,1),(25,'As we anticipated, the unemployment rate has risen three quarters in a row.','    ','anticipate...    ,\nunemployment    ,\nrate    ,\nrise    ,\nquarter    ,\nrow    ',NULL,1),(26,'I have to cut down on my expenses, so from now on, I\'m going to keep track of them on a daily basis.','    ','cut down on...    ,\nexpense    ,\nfrom now on    ,\ntrack    ,\ndaily    ,\non a ... basis    ',NULL,1),(27,'In any case, the union has to compromise to a certain extent.','    ','case    ,\nunion    ,\ncompromise (...)    ,\ncertain    ,\nextent    ',NULL,1),(28,'Competent mechanics are in great demand, so they earn decent wages.','    ','competent    ,\nmechanic    ,\ndemand    ,\nearn...    ,\ndecent    ,\nwages    ',NULL,1),(29,'The president announced a concrete plan to carry out welfare reform.','    ','president    ,\nannounce...    ,\nconcrete    ,\ncarry ... out    ,\nwelfare    ,\nreform    ',NULL,1),(30,'His policy will no doubt lead to dismal consequences. It needs a thorough review.','    ','policy    ,\ndoubt    ,\nlead (...)    ,\ndismal    ,\nconsequence    ,\nthorough    ,\nreview',NULL,1),(31,'It goes without saying that the aging of society is inevitable.','    ','it goes without saying that ~    ,\naging    ,\ninevitable    ',NULL,1),(32,'Please take a look at this chart. It indicates that juvenile delinquency is on the increase at an alarming rate.','    ','take a look at ...    ,\nchart    ,\nindicate...    ,\njuvenile    ,\ndelinquency    ,\nbe on the increase    ,\nalarming    ,\nat a ... rate    ',NULL,1),(33,'In many business districts, there are a lot of vacant lots which have been sale for years.','    ','district    ,\na lot of.../lots of...    ,\nvacant    ,\nlot    ,\nbe for sale    ',NULL,1),(34,'The population density in the metropolis is gradually decreasing.','    ','population    ,\ndensity    ,\nmetropolis    ,\ngradually    ,\ndecrease (...)    ',NULL,1),(35,'In all likelihood, the birthrate will continue to decline steadily for years to come.','    ','in all likelihood    ,\nbirthrate    ,\ncontinue (...)    ,\ndecline (...)    ,\nsteadily    ,\n... to come    ',NULL,1),(36,'The power plant supplies the remote country with electricity.','    ','power    ,\nplant    ,\nsupply A (with B)    ,\nremote    ,\ncounty    ,\nelectricity    ',NULL,1),(37,'You are not allowed to operate this device without permission.','    ','allow...    ,\noperate (...)    ,\ndevice    ,\npermission    ',NULL,1),(38,'In fact, inhabitants have been exposed to radiation.','    ','in (actual) fact    ,\ninhabitant    ,\nexpose...    ,\nradiation    ',NULL,1),(39,'One cannot emphasize too much the potential danger of nuclear energy.','    ','cannot do ... too much    ,\nemphasize    ,\npotential    ,\ndanger    ,\nnuclear    ,\nenergy    ',NULL,1),(40,'For years the press overlooked the problem. But now, if anything, they are making too much of it.','    ','press    ,\noverlook...    ,\nif anything    ,\nmake...of A    ',NULL,1),(41,'As well as cultivating grain, the farmer runs a grocery store.','    ','as well as...    ,\ncultivate...    ,\ngrain    ,\nfarmer    ,\nrun (...)    ,\ngrocery store    ',NULL,1),(42,'The research institute was established in the late 1960s.','    ','research    ,\ninstitute    ,\nestablish...    ,\nlate    ,\n1960s    ',NULL,1),(43,'Why don\'t you consult Starr in person? He\'s by far the most prominent attorney around here.','    ','Why don\'t you do ...?    ,\nconsult ... (about A)    ,\nin person    ,\nby far    ,\nprominent    ,\nattorney    ',NULL,1),(44,'I\'ll write it down just in case, because I have a bad memory.','    ','write ... down    ,\njust in case    ,\nhave a bad memory    ',NULL,1),(45,'The lawyer recommended that his client take legal action against the insurance company.','    ','recommend...    ,\nclient    ,\nlegal    ,\naction    ,\ninsurance    ',NULL,1),(46,'They are entitled to be compensated for their injuries.','    ','entitle...    ,\ncompensate (A) for B    ,\ninjury    ',NULL,1),(47,'So far, no less than 200 people have died of the flu epidemic.','    ','so far    ,\nno less than...    ,\ndie of [from] ...    ,\nflu    ,\nepidemic    ',NULL,1),(48,'The effect of those pills is intense but brief.','    ','effect    ,\npill    ,\nintense    ,\nbrief    ',NULL,1),(49,'He has a habit of biting his nails. It\'s absolutely disgusting.','    ','habit    ,\nbite (...)    ,\nnail    ,\nabsolutely    ,\ndisgusting    ',NULL,1),(50,'My grandma strained her back when she bent down to hug my son.','    ','grandma    ,\nstrain (...)    ,\nback    ,\nbend (...)    ,\nhug...    ',NULL,1),(51,'If you have a stiff neck, try an herbal remedy.','    ','stiff    ,\nherbal    ,\nremedy    ',NULL,1),(52,'Medical breakthroughs have brought about great benefits for humanity as a whole.','    ','medical    ,\nbreakthrough    ,\nbring ... about    ,\nbenefit    ,\nhumanity    ,\n... as a whole    ',NULL,1),(53,'We can\'t apply cloning techniques to cattle, let alone human beings. It\'s forbidden.','    ','apply (...)    ,\nclone...    ,\ntechnique    ,\ncattle    ,\nlet alone...    ,\nhuman (being)    ,\nforbid...    ',NULL,1),(54,'Whales are classified as mammals.','    ','whale    ,\nclassify...    ,\nmammal    ',NULL,1),(55,'The theory of evolution is beyond the reach of my imagination.','    ','theory    ,\nevolution    ,\nbeyond [out of] the reach of...    ,\nimagination    ',NULL,1),(56,'The biologist is proud of his historic discovery and doesn\'t mind boasting about it.','    ','biologist    ,\nproud    ,\nhistoric    ,\ndiscovery    ,\nboast (...)    ',NULL,1),(57,'On ethical grounds, they are opposed to so-called gene therapy.','    ','ethical    ,\nground    ,\noppose...    ,\nso-called    ,\ngene    ,\ntherapy    ',NULL,1),(58,'The initial symptoms of the disease are fever and a sore throat.','    ','initial    ,\nsymptom    ,\ndisease    ,\nfever    ,\nsore    ,\nthroat    ',NULL,1),(59,'The structure of the brain is complex.','    ','structure    ,\nbrain    ,\ncomplex    ',NULL,1),(60,'Owing to illness, some representatives were absent from the annual conference.','    ','owing to...    ,\nillness    ,\nrepresentative    ,\nabsent    ,\nannual    ,\nconference    ',NULL,1),(61,'Little by little, my son-in-law is recovering from stomach cancer, and now he is in good spirits.','    ','little by little    ,\nson-in-law    ,\nrecover (...)    ,\nstomach    ,\ncancer    ,\nspirit    ',NULL,1),(62,'\"This is fake, isn\'t it?\" \"Hey, it\'s a genuine antique.\" \"No way!\"','    ','fake    ,\ngenuine    ,\nantique    ,\nNo way!    ',NULL,1),(63,'\"Anything else?\" \"That\'s it.\" \"For here or to go?\" \"To go.\"','    ','Anything else?    ,\nThat\'s it.    ,\nFor here or to go    ',NULL,1),(64,'\"How much is this rug?\" \"$100 including tax.\" \"OK, I\'ll take it.\"','    ','rug    ,\nincluding...    ,\ntax    ,\nI\'ll take it    ',NULL,1),(65,'I don\'t think those pants look good on you. Try these on. They\'re really in now!','    ','pants    ,\nlook good [nice] on...    ,\ntry ... on    ,\nbe in    ',NULL,1),(66,'He got out of the cab in haste, saying, \"Keep the change.\"','    ','get out (of ...)    ,\ncab    ,\nin haste    ,\nKeep the change.    ',NULL,1),(67,'Delivery service is available to our customers for a slight extra charge.','    ','delivery    ,\nservice    ,\navailable    ,\ncustomer    ,\nslight    ,\nextra    ,\ncharge    ',NULL,1),(68,'\"It\'s on me.\" \"No. You treat me every time we eat out.\" \"Well, okay. Let\'s split the check then.\"','    ','It\'s on me.    ,\ntreat...    ,\nevery time~    ,\neat out    ,\nsplit (...)    ,\ncheck    ',NULL,1),(69,'While I was hanging out at the mall, I ran into Ken.','    ','hang out    ,\nmall    ,\nrun into...    ',NULL,1),(70,'I was short of cash, so I withdrew the $100 that I had deposited in my bank account last week.','    ','be short of...    ,\nwithdraw (...)    ,\ndeposit...    ,\naccount    ',NULL,1),(71,'On her way home she was robbed of her purse.','    ','on the [one\'s] way (to...)    ,\nrob...    ,\npurse    ',NULL,1),(72,'\"That\'s odd! Tell me how it happened.\" \"I\'ll explain it to you later.\"','    ','odd    ,\nhappen    ,\nexplain (...)    ',NULL,1),(73,'My parents gave me a 6pm curfew as a punishment because I broke my promise.','    ','curfew    ,\npunishment    ,\nbreak one\'s promise [word]    ',NULL,1),(74,'As the proverb goes, \"The end justifies the means.\"','    ','proverb    ,\nend    ,\njustify...    ,\nmeans    ',NULL,1),(75,'Now that I\'ve found that there\'s no one to turn to, I\'ll have to stand on my own two feet.','    ','now (that)~    ,\nturn to...    ,\nstand on one\'s own two feet    ',NULL,1),(76,'The chairman cut me off, saying, \"Time to wind things up. Let\'s take a vote.\"','    ','chairman/chairwoman    ,\ncut ... off    ,\nwind (...)    ,\nvote    ',NULL,1),(77,'We exchanged frank opinions in the meeting, but consensus is yet to be reached regarding this matter.','    ','exchange...    ,\nfrank    ,\nopinion    ,\nmeeting    ,\nconsensus    ,\nhave [be] yet to do ...    ,\nreach (...)    ,\nregarding...    ,\nmatter    ',NULL,1),(78,'\"Do you agree or disagree with him?\" \"I\'m on his side.\"','    ','agree    ,\ndisagree    ,\nbe on one\'s side    ',NULL,1),(79,'Strictly speaking, his view differs somewhat from mine.','    ','strictly    ,\nview    ,\ndiffer    ,\nsomewhat    ',NULL,1),(80,'Could you go over it again? I couldn\'t make out what you were getting at.','    ','go over...    ,\nmake ... out    ,\nbe getting at...    ',NULL,1),(81,'No one backed me up at first, but eventually I talked everyone into going along with my plan.','    ','back (...) up    ,\nat first    ,\neventually    ,\ntalk A into doing ...    ,\ngo along with...    ',NULL,1),(82,'\"I admire your perseverance, courage and wisdom.\" \"You flatter me!\"','    ','admire...    ,\nperseverance    ,\ncourage    ,\nwisdom    ,\nflatter...    ',NULL,1),(83,'In making a decision, I rely not on logic but on instinct.','    ','in doing ...    ,\nmake a decision    ,\nrely on [upon] ...    ,\nnot A but B    ,\nlogic    ,\ninstinct    ',NULL,1),(84,'He came up with an ingenious, sensible solution and immediately put it into practice.','    ','come up with...    ,\ningenious    ,\nsensible    ,\nsolution (to...)    ,\nimmediately    ,\nput ... into practice    ',NULL,1),(85,'From an objective viewpoint, the former is inferior to the latter.','    ','objective    ,\nfrom ... viewpoint    ,\nformer    ,\ninferior    ,\nlatter    ',NULL,1),(86,'Your idea sounds marvelous in theory, yet I don\'t think it will work in practice.','    ','sound ...    ,\nmarvelous    ,\nin theory    ,\nyet    ,\nin practice    ',NULL,1),(87,'We expected him to defeat his opponent, but he failed to live up to our expectations.','    ','expect...    ,\ndefeat...    ,\nopponent    ,\nfail (...)    ,\nlive up to one\'s expectations    ',NULL,1),(88,'Ironically, despite their best endeavors, their mission resulted in complete failure.','    ','ironically    ,\ndespite...    ,\nendeavor    ,\nmission    ,\nresult in...    ,\ncomplete    ,\nfailure    ',NULL,1),(89,'Her genius makes up for her lack of firsthand experience.','    ','genius    ,\nmake up for...    ,\nlack    ,\nfirsthand    ,\nexperience    ',NULL,1),(90,'She possesses a great capacity for overcoming any obstacle.','    ','possess...    ,\ncapacity    ,\novercome...    ,\nobstacle    ',NULL,1),(91,'Without your solid support, the deal would have fallen through. I\'m grateful to you.','    ','without...    ,\nsolid    ,\nsupport    ,\ndeal    ,\nwould have done ...    ,\nfall through    ,\nbe grateful to... (for A)    ',NULL,1),(92,'Bringing flammable items into the cabin is prohibited.','    ','flammable    ,\nitem    ,\ncabin    ,\nprohibit...    ',NULL,1),(93,'Our flight to Vienna was delayed on account of a minor accident.','    ','flight    ,\nVienna    ,\ndelay (...)    ,\non account of...    ,\nminor    ,\naccident    ',NULL,1),(94,'The plane blew up and plunged into the ocean, killing all the people on board.','    ','plane    ,\nblow (...) up    ,\nplunge(...) (into A)    ,\nocean    ,\nboard    ',NULL,1),(95,'They are working around the clock looking into the cause of the crash.','    ','around the clock    ,\nlook into...    ,\ncause    ,\ncrash    ',NULL,1),(96,'Unfortunately, few passengers survived the catastrophe.','    ','unfortunately    ,\nfew    ,\npassenger    ,\nsurvive (...)    ,\ncatastrophe    ',NULL,1),(97,'The aviation expert analyzed the statistics in detail.','    ','aviation    ,\nexpert    ,\nanalyze...    ,\nstatistics    ,\ndetail    ',NULL,1),(98,'On behalf of all the staff, I\'d like to express our sympathy for the victims.','    ','on behalf of...    ,\nstaff    ,\nexpress...    ,\nsympathy    ,\nvictim    ',NULL,1),(99,'In a sense, he is to blame for the disaster.','    ','sense    ,\nblame...    ,\ndisaster    ',NULL,1),(100,'The vehicles are inspected for defects every three months.','    ','vehicle    ,\ninspect...    ,\ndefect    ,\nevery...    ',NULL,1),(101,'I am Nam.','私はナムです。','',NULL,1),(102,'I am Nam.','私はナムです。','',NULL,1);
/*!40000 ALTER TABLE `sentence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sound`
--

DROP TABLE IF EXISTS `sound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sound` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sound_url` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sound`
--

LOCK TABLES `sound` WRITE;
/*!40000 ALTER TABLE `sound` DISABLE KEYS */;
INSERT INTO `sound` VALUES (1,'/uploadDir/Eng_Jpn_0001.m4a'),(2,'/uploadDir/Eng_Jpn_0002.m4a'),(3,'/uploadDir/Eng_Jpn_0003.m4a');
/*!40000 ALTER TABLE `sound` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system` (
  `id` int NOT NULL AUTO_INCREMENT,
  `empty_content` varchar(45) DEFAULT NULL,
  `full_content` varchar(45) DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system`
--

LOCK TABLES `system` WRITE;
/*!40000 ALTER TABLE `system` DISABLE KEYS */;
/*!40000 ALTER TABLE `system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'nam','nam@gmail.com','$2a$10$gEhS9t5D4A.KFoV1qDQLNesyvtRNfAarj/zCg0MeeDh8hFw4XwKlu');
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

-- Dump completed on 2020-06-15 17:37:51
