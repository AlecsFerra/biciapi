-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 14, 2019 at 01:49 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bici2`
--

-- --------------------------------------------------------

--
-- Table structure for table `biciclette`
--

CREATE TABLE `biciclette` (
  `id` bigint(20) NOT NULL,
  `tagrfid` varchar(5) NOT NULL,
  `id_stazione_corrente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `biciclette`
--

INSERT INTO `biciclette` (`id`, `tagrfid`, `id_stazione_corrente`) VALUES
(1, 'AAAAA', NULL),
(2, 'AAAAB', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `noleggi`
--

CREATE TABLE `noleggi` (
  `id` bigint(20) NOT NULL,
  `data_ora_consegna` datetime DEFAULT NULL,
  `data_ora_prelievo` datetime NOT NULL,
  `id_bicicletta` bigint(20) NOT NULL,
  `id_stazione_consegna` bigint(20) DEFAULT NULL,
  `id_stazione_prelievo` bigint(20) NOT NULL,
  `id_utente` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `noleggi`
--

INSERT INTO `noleggi` (`id`, `data_ora_consegna`, `data_ora_prelievo`, `id_bicicletta`, `id_stazione_consegna`, `id_stazione_prelievo`, `id_utente`) VALUES
(1, '2019-04-14 10:34:48', '2019-04-14 10:00:32', 1, 3, 3, 1),
(2, NULL, '2019-04-14 10:03:40', 2, NULL, 3, 1),
(3, NULL, '2019-04-14 11:24:43', 1, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `stazioni`
--

CREATE TABLE `stazioni` (
  `id` bigint(20) NOT NULL,
  `indirizzo` varchar(40) NOT NULL,
  `num_posti_totale` bigint(20) NOT NULL,
  `pos_latitudine` float NOT NULL,
  `pos_longitudine` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stazioni`
--

INSERT INTO `stazioni` (`id`, `indirizzo`, `num_posti_totale`, `pos_latitudine`, `pos_longitudine`) VALUES
(1, 'Via Sarca,15', 10, 45.42, 10.96),
(2, 'Corso Milano, 23', 10, 45.44, 10.97),
(3, 'Piazza Bra', 15, 45.44, 10.99),
(4, 'Piazza Vittorio Veneto', 10, 45.45, 10.99);

-- --------------------------------------------------------

--
-- Table structure for table `utenti`
--

CREATE TABLE `utenti` (
  `id` bigint(20) NOT NULL,
  `cognome` varchar(40) NOT NULL,
  `id_card` bigint(20) DEFAULT NULL,
  `nome` varchar(40) NOT NULL,
  `password` varchar(60) NOT NULL,
  `ruolo` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utenti`
--

INSERT INTO `utenti` (`id`, `cognome`, `id_card`, `nome`, `password`, `ruolo`, `username`) VALUES
(1, 'ferrarini', 373271740, 'alessio', '$2a$10$wTbZI1tf/rPQ9xm8BsVxjeQZ1Gn4fF5g8D8eUknD4IMrK5q/Luk0i', 'user', 'ferrarini'),
(2, 'Sistema', NULL, 'Amministratore', '$2a$10$DQ5Drl73gFB4RhfgIHj7guLYEp7DwVOjTI8x1GVSF51v156YKSwtK', 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `biciclette`
--
ALTER TABLE `biciclette`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gggt3ve8xyvo26agrj53e2b69` (`tagrfid`),
  ADD KEY `FKk1qglen7acdnlpakl69cn0cs6` (`id_stazione_corrente`);

--
-- Indexes for table `noleggi`
--
ALTER TABLE `noleggi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjrdd2rm8lcc0iskklf3c1focd` (`id_bicicletta`),
  ADD KEY `FKajp9ipw7s3x9fcq2l4fsuitm8` (`id_stazione_consegna`),
  ADD KEY `FKdk3iti7e5lu0lijs9im2dd8jh` (`id_stazione_prelievo`),
  ADD KEY `FKprdiwsgc63fdct20cnib4cad5` (`id_utente`);

--
-- Indexes for table `stazioni`
--
ALTER TABLE `stazioni`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_tn8mwk6h2wn28yyj7fco65gls` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `biciclette`
--
ALTER TABLE `biciclette`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `noleggi`
--
ALTER TABLE `noleggi`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `stazioni`
--
ALTER TABLE `stazioni`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `utenti`
--
ALTER TABLE `utenti`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `biciclette`
--
ALTER TABLE `biciclette`
  ADD CONSTRAINT `FKk1qglen7acdnlpakl69cn0cs6` FOREIGN KEY (`id_stazione_corrente`) REFERENCES `stazioni` (`id`);

--
-- Constraints for table `noleggi`
--
ALTER TABLE `noleggi`
  ADD CONSTRAINT `FKajp9ipw7s3x9fcq2l4fsuitm8` FOREIGN KEY (`id_stazione_consegna`) REFERENCES `stazioni` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKdk3iti7e5lu0lijs9im2dd8jh` FOREIGN KEY (`id_stazione_prelievo`) REFERENCES `stazioni` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKjrdd2rm8lcc0iskklf3c1focd` FOREIGN KEY (`id_bicicletta`) REFERENCES `biciclette` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKprdiwsgc63fdct20cnib4cad5` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
