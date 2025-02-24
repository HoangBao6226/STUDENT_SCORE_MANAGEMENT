create database STUDENT_SCORE;

use student_score;

-- Bảng Admin
CREATE TABLE Admin (
    MaAdmin INT PRIMARY KEY AUTO_INCREMENT,
    TenAdmin NVARCHAR(50) NOT NULL,
    Email VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng Giảng Viên
CREATE TABLE GiangVien (
    MaGV INT PRIMARY KEY AUTO_INCREMENT,
    TenGV VARCHAR(50) NOT NULL,
    Email VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng Sinh Viên
CREATE TABLE SinhVien (
    MaSV INT PRIMARY KEY AUTO_INCREMENT,
    TenSV VARCHAR(50) NOT NULL,
    Email VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng Môn Học
CREATE TABLE MonHoc (
    MaMH INT PRIMARY KEY AUTO_INCREMENT,
    TenMH VARCHAR(100) NOT NULL,
    SoTinChi INT NOT NULL,
    MaGV INT,
    FOREIGN KEY (MaGV) REFERENCES GiangVien(MaGV)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng Điểm
CREATE TABLE Diem (
    MaSV INT,
    MaMH INT,
    Diem DECIMAL(4,2) CHECK (Diem BETWEEN 0 AND 10),
    PRIMARY KEY (MaSV, MaMH),
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV),
    FOREIGN KEY (MaMH) REFERENCES MonHoc(MaMH)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Bảng TaiKhoan
CREATE TABLE TaiKhoan (
    MaTK INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'GiangVien', 'SinhVien') NOT NULL,
    MaAdmin INT,
    MaGV INT,
    MaSV INT,
    -- Ràng buộc khóa ngoại tùy loại tài khoản
    FOREIGN KEY (MaAdmin) REFERENCES Admin(MaAdmin) ON DELETE CASCADE,
    FOREIGN KEY (MaGV) REFERENCES GiangVien(MaGV) ON DELETE CASCADE,
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV) ON DELETE CASCADE,
    -- Đảm bảo mỗi tài khoản chỉ liên kết với một loại người dùng
    CONSTRAINT CHK_TaiKhoan CHECK (
        (Role = 'Admin' AND MaAdmin IS NOT NULL AND MaGV IS NULL AND MaSV IS NULL) OR
        (Role = 'GiangVien' AND MaGV IS NOT NULL AND MaAdmin IS NULL AND MaSV IS NULL) OR
        (Role = 'SinhVien' AND MaSV IS NOT NULL AND MaAdmin IS NULL AND MaGV IS NULL)
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------------- --

-- Thêm dữ liệu vào bảng Admin
INSERT INTO Admin (TenAdmin, Email) VALUES
('Nguyễn Hoàng Bảo', 'yoonhoang6226@gmail.com');

-- Thêm dữ liệu vào bảng GiangVien
INSERT INTO GiangVien (TenGV, Email) VALUES
('Hà Thị Mỹ Châu', 'chau28400@gmail.com'),
('Nguyễn Văn Huy', 'vanhuy27062003@gmail.com');

-- Thêm dữ liệu vào bảng SinhVien
INSERT INTO SinhVien (TenSV, Email) VALUES
('Phạm Minh Anh', 'dh52110568@student.stu.edu.vn'),
('Nguyễn Thành Công', 'dh52110649@student.stu.edu.vn'),
('Huy Nguyễn', 'dh52107926@student.stu.edu.vn'),
('Châu Hà', 'dh52110640@student.stu.edu.vn'),
('Bảo Nguyễn', 'dh52110602@student.stu.edu.vn');

-- Thêm dữ liệu vào bảng MonHoc
INSERT INTO MonHoc (TenMH, SoTinChi, MaGV) VALUES
('Lập trình Cơ bản', 3, 1),
('Cấu trúc Dữ liệu & Giải thuật', 4, 2),
('Mạng Máy tính', 3, 1),
('Hệ điều hành', 3, 2),
('Tiếng Anh chuyên ngành CNTT', 2, 1),
('Xác suất Thống kê', 3, 2),
('Đồ họa Máy tính', 3, 1),
('Trí tuệ Nhân tạo', 4, 1),
('Quản trị Dự án Phần mềm', 3, 2);

-- Thêm dữ liệu vào bảng TaiKhoan
INSERT INTO TaiKhoan (Username, Password, Role, MaAdmin, MaGV, MaSV) VALUES
-- Tài khoản Admin
('admin1', '123', 'Admin', 1, NULL, NULL),

-- Tài khoản Giảng viên
('gv1', '123', 'GiangVien', NULL, 1, NULL),
('gv2', '123', 'GiangVien', NULL, 2, NULL),

-- Tài khoản Sinh viên
('sv1', '123', 'SinhVien', NULL, NULL, 1),
('sv2', '123', 'SinhVien', NULL, NULL, 2),
('sv3', '123', 'SinhVien', NULL, NULL, 3),
('sv4', '123', 'SinhVien', NULL, NULL, 4),
('sv5', '123', 'SinhVien', NULL, NULL, 4);