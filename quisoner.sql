-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 16, 2021 at 06:06 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quisoner`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_anak`
--

CREATE TABLE `tb_anak` (
  `id_anak` varchar(255) NOT NULL,
  `id_user` varchar(255) NOT NULL,
  `nama_anak` varchar(255) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') NOT NULL,
  `umur` int(2) NOT NULL,
  `foto_anak` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_anak`
--

INSERT INTO `tb_anak` (`id_anak`, `id_user`, `nama_anak`, `tanggal_lahir`, `jenis_kelamin`, `umur`, `foto_anak`) VALUES
('ANK00001', 'USR00001', 'Azez', '1999-12-30', 'Laki-laki', 6, 'default.jpeg'),
('ANK00002', 'USR00001', 'Tes', '2021-05-05', 'Perempuan', 2, 'default.jpeg'),
('ANK00003', 'USR00001', 'Ler', '2021-07-06', 'Laki-laki', 0, 'default.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_hasil`
--

CREATE TABLE `tb_detail_hasil` (
  `id_detail_hasil` varchar(255) NOT NULL,
  `id_hasil` varchar(255) NOT NULL,
  `id_soal` varchar(255) NOT NULL,
  `jawaban` int(1) NOT NULL COMMENT '0 = tidak, 1 = iya'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_hasil`
--

CREATE TABLE `tb_hasil` (
  `id_hasil` varchar(255) NOT NULL,
  `id_anak` varchar(255) NOT NULL,
  `id_user` varchar(255) NOT NULL,
  `total_point` float NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_paket`
--

CREATE TABLE `tb_paket` (
  `id_paket` varchar(255) NOT NULL,
  `umurawal` int(2) NOT NULL,
  `umurakhir` int(2) NOT NULL,
  `jenis` int(11) NOT NULL COMMENT '0=kpsp,1=tdd,2=tdl\r\n'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_soal`
--

CREATE TABLE `tb_soal` (
  `id_soal` varchar(255) NOT NULL,
  `soal` varchar(255) NOT NULL,
  `id_paket` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id_user` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `foto` varchar(255) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `is_aktif` int(11) NOT NULL,
  `status` enum('Orang tua balita','Bidan','Kader Posyandu') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id_user`, `username`, `password`, `nama`, `email`, `foto`, `created_at`, `is_aktif`, `status`) VALUES
('USR00001', 'ova', '202cb962ac59075b964b07152d234b70', 'istifadatul maulania', 'istifadatulmaulania@gmail.com', 'default.jpeg', '2021-07-13', 1, 'Kader Posyandu'),
('USR00002', 'Vinz', '202cb962ac59075b964b07152d234b70', 'Luqman', 'luqman.simdig05@gmail.com', 'default.jpeg', '2021-07-14', 1, 'Bidan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_anak`
--
ALTER TABLE `tb_anak`
  ADD PRIMARY KEY (`id_anak`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `tb_detail_hasil`
--
ALTER TABLE `tb_detail_hasil`
  ADD PRIMARY KEY (`id_detail_hasil`),
  ADD KEY `hasil` (`id_hasil`),
  ADD KEY `soal` (`id_soal`);

--
-- Indexes for table `tb_hasil`
--
ALTER TABLE `tb_hasil`
  ADD PRIMARY KEY (`id_hasil`),
  ADD KEY `anak` (`id_anak`),
  ADD KEY `user` (`id_user`);

--
-- Indexes for table `tb_paket`
--
ALTER TABLE `tb_paket`
  ADD PRIMARY KEY (`id_paket`);

--
-- Indexes for table `tb_soal`
--
ALTER TABLE `tb_soal`
  ADD PRIMARY KEY (`id_soal`),
  ADD KEY `id_paket` (`id_paket`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id_user`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_anak`
--
ALTER TABLE `tb_anak`
  ADD CONSTRAINT `tb_anak_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`);

--
-- Constraints for table `tb_detail_hasil`
--
ALTER TABLE `tb_detail_hasil`
  ADD CONSTRAINT `hasil` FOREIGN KEY (`id_hasil`) REFERENCES `tb_hasil` (`id_hasil`),
  ADD CONSTRAINT `soal` FOREIGN KEY (`id_soal`) REFERENCES `tb_soal` (`id_soal`);

--
-- Constraints for table `tb_hasil`
--
ALTER TABLE `tb_hasil`
  ADD CONSTRAINT `anak` FOREIGN KEY (`id_anak`) REFERENCES `tb_anak` (`id_anak`),
  ADD CONSTRAINT `user` FOREIGN KEY (`id_user`) REFERENCES `tb_user` (`id_user`);

--
-- Constraints for table `tb_soal`
--
ALTER TABLE `tb_soal`
  ADD CONSTRAINT `tb_soal_ibfk_1` FOREIGN KEY (`id_paket`) REFERENCES `tb_paket` (`id_paket`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
