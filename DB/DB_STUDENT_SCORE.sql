-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th3 08, 2025 lúc 10:49 AM
-- Phiên bản máy phục vụ: 8.3.0
-- Phiên bản PHP: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `student_score`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `MaAdmin` int NOT NULL AUTO_INCREMENT,
  `TenAdmin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaAdmin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`MaAdmin`, `TenAdmin`, `Email`) VALUES
(1, 'Nguyễn Hoàng Bảo', 'yoonhoang6226@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `diem`
--

DROP TABLE IF EXISTS `diem`;
CREATE TABLE IF NOT EXISTS `diem` (
  `maDiem` int NOT NULL AUTO_INCREMENT,
  `maSV` int DEFAULT NULL,
  `maMH` int DEFAULT NULL,
  `diem` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`maDiem`),
  KEY `MaSV` (`maSV`),
  KEY `MaMH` (`maMH`)
) ;

--
-- Đang đổ dữ liệu cho bảng `diem`
--

INSERT INTO `diem` (`maDiem`, `maSV`, `maMH`, `diem`) VALUES
(1, 1, 1, 7.50),
(2, 1, 2, 8.00),
(3, 1, 3, 6.50),
(4, 2, 1, 9.00),
(5, 2, 4, 7.00),
(6, 3, 2, 8.50),
(7, 3, 5, 6.00),
(8, 4, 3, 7.80),
(9, 4, 6, 9.20),
(10, 5, 2, 5.50),
(11, 5, 4, 8.30),
(12, 5, 7, 7.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giangvien`
--

DROP TABLE IF EXISTS `giangvien`;
CREATE TABLE IF NOT EXISTS `giangvien` (
  `MaGV` int NOT NULL AUTO_INCREMENT,
  `TenGV` varchar(50) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaGV`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `giangvien`
--

INSERT INTO `giangvien` (`MaGV`, `TenGV`, `Email`) VALUES
(1, 'Hà Thị Mỹ Châu', 'chau28400@gmail.com'),
(2, 'Nguyễn Văn Huy', 'vanhuy27062003@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monhoc`
--

DROP TABLE IF EXISTS `monhoc`;
CREATE TABLE IF NOT EXISTS `monhoc` (
  `MaMH` int NOT NULL AUTO_INCREMENT,
  `TenMH` varchar(100) NOT NULL,
  `SoTinChi` int NOT NULL,
  `MaGV` int DEFAULT NULL,
  PRIMARY KEY (`MaMH`),
  KEY `MaGV` (`MaGV`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `monhoc`
--

INSERT INTO `monhoc` (`MaMH`, `TenMH`, `SoTinChi`, `MaGV`) VALUES
(1, 'Lập trình Cơ bản', 3, 1),
(2, 'Cấu trúc Dữ liệu & Giải thuật', 4, 2),
(3, 'Mạng Máy tính', 3, 1),
(4, 'Hệ điều hành', 3, 2),
(5, 'Tiếng Anh chuyên ngành CNTT', 2, 1),
(6, 'Xác suất Thống kê', 3, 2),
(7, 'Đồ họa Máy tính', 3, 1),
(8, 'Trí tuệ Nhân tạo', 4, 1),
(9, 'Quản trị Dự án Phần mềm', 3, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinhvien`
--

DROP TABLE IF EXISTS `sinhvien`;
CREATE TABLE IF NOT EXISTS `sinhvien` (
  `MaSV` int NOT NULL AUTO_INCREMENT,
  `TenSV` varchar(50) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaSV`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `sinhvien`
--

INSERT INTO `sinhvien` (`MaSV`, `TenSV`, `Email`) VALUES
(1, 'Phạm Minh Anh', 'dh52110568@student.stu.edu.vn'),
(2, 'Nguyễn Thành Công', 'dh52110649@student.stu.edu.vn'),
(3, 'Huy Nguyễn', 'dh52107926@student.stu.edu.vn'),
(4, 'Châu Hà', 'dh52110640@student.stu.edu.vn'),
(5, 'Bảo Nguyễn', 'dh52110602@student.stu.edu.vn');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

DROP TABLE IF EXISTS `taikhoan`;
CREATE TABLE IF NOT EXISTS `taikhoan` (
  `MaTK` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` enum('Admin','GiangVien','SinhVien') NOT NULL,
  `MaAdmin` int DEFAULT NULL,
  `MaGV` int DEFAULT NULL,
  `MaSV` int DEFAULT NULL,
  PRIMARY KEY (`MaTK`),
  UNIQUE KEY `Username` (`Username`),
  KEY `MaAdmin` (`MaAdmin`),
  KEY `MaGV` (`MaGV`),
  KEY `MaSV` (`MaSV`)
) ;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`MaTK`, `Username`, `Password`, `Role`, `MaAdmin`, `MaGV`, `MaSV`) VALUES
(1, 'admin1', '123', 'Admin', 1, NULL, NULL),
(2, 'gv1', '123', 'GiangVien', NULL, 1, NULL),
(3, 'gv2', '123', 'GiangVien', NULL, 2, NULL),
(4, 'sv1', '123', 'SinhVien', NULL, NULL, 1),
(5, 'sv2', '123', 'SinhVien', NULL, NULL, 2),
(6, 'sv3', '123', 'SinhVien', NULL, NULL, 3),
(7, 'sv4', '123', 'SinhVien', NULL, NULL, 4),
(8, 'sv5', '123', 'SinhVien', NULL, NULL, 4);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `diem`
--
ALTER TABLE `diem`
  ADD CONSTRAINT `diem_ibfk_1` FOREIGN KEY (`maSV`) REFERENCES `sinhvien` (`MaSV`) ON DELETE CASCADE,
  ADD CONSTRAINT `diem_ibfk_2` FOREIGN KEY (`maMH`) REFERENCES `monhoc` (`MaMH`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `monhoc`
--
ALTER TABLE `monhoc`
  ADD CONSTRAINT `monhoc_ibfk_1` FOREIGN KEY (`MaGV`) REFERENCES `giangvien` (`MaGV`);

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`MaAdmin`) REFERENCES `admin` (`MaAdmin`) ON DELETE CASCADE,
  ADD CONSTRAINT `taikhoan_ibfk_2` FOREIGN KEY (`MaGV`) REFERENCES `giangvien` (`MaGV`) ON DELETE CASCADE,
  ADD CONSTRAINT `taikhoan_ibfk_3` FOREIGN KEY (`MaSV`) REFERENCES `sinhvien` (`MaSV`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
