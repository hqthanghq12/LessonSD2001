-- Tạo CSDL nếu cần
CREATE DATABASE QLNHANSU;
 GO
 USE QLNHANSU;
 GO

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('NHANVIEN', 'U') IS NOT NULL
    DROP TABLE NHANVIEN;
IF OBJECT_ID('PHONGBAN', 'U') IS NOT NULL
    DROP TABLE PHONGBAN;
GO

-- 1. Tạo bảng PHONGBAN
CREATE TABLE PHONGBAN (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    TENPHONG NVARCHAR(100) NOT NULL
);
GO

-- 2. Tạo bảng NHANVIEN
CREATE TABLE NHANVIEN (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MANV VARCHAR(10) NOT NULL UNIQUE,
    HONV NVARCHAR(30) NOT NULL,
    TENLOT NVARCHAR(30) NULL,
    TENNV NVARCHAR(30) NOT NULL,
    NGSINH DATE,
    DCHI NVARCHAR(200),
    PHAI NVARCHAR(5),
    LUONG DECIMAL(18,2),
    IDPHG INT
);
GO

-- 3. Dùng ALTER để tạo khóa ngoại
ALTER TABLE NHANVIEN
ADD CONSTRAINT FK_NHANVIEN_PHONGBAN
FOREIGN KEY (IDPHG) REFERENCES PHONGBAN(ID);
GO

-- 4. Nhập dữ liệu cho bảng PHONGBAN (5 dòng)
-- Không cần chèn cột ID vì ID tự tăng
INSERT INTO PHONGBAN (TENPHONG) VALUES
(N'Phòng Kế toán'),
(N'Phòng Nhân sự'),
(N'Phòng Kỹ thuật'),
(N'Phòng Kinh doanh'),
(N'Phòng Marketing');
GO

-- 5. Nhập dữ liệu cho bảng NHANVIEN (30 dòng)
-- Không cần chèn cột ID vì ID tự tăng
INSERT INTO NHANVIEN (MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG) VALUES
('NV001', N'Nguyễn', N'Văn', N'An',   '1990-01-15', N'Hà Nội',      N'Nam', 12000000, 1),
('NV002', N'Trần',   N'Thị', N'Bình', '1992-03-22', N'Hải Phòng',   N'Nữ',  11500000, 2),
('NV003', N'Lê',     N'Văn', N'Cường','1989-07-10', N'Đà Nẵng',     N'Nam', 15000000, 3),
('NV004', N'Phạm',   N'Thị', N'Dung', '1993-11-05', N'Hồ Chí Minh', N'Nữ',  13000000, 4),
('NV005', N'Hoàng',  N'Văn', N'Em',   '1991-09-18', N'Cần Thơ',     N'Nam', 11000000, 5),
('NV006', N'Vũ',     N'Thị', N'Giang','1994-02-12', N'Hà Nam',      N'Nữ',  12500000, 1),
('NV007', N'Đặng',   N'Văn', N'Hải',  '1988-06-30', N'Nghệ An',     N'Nam', 14500000, 2),
('NV008', N'Bùi',    N'Thị', N'Hạnh', '1995-12-25', N'Thanh Hóa',   N'Nữ',  11800000, 3),
('NV009', N'Đỗ',     N'Văn', N'Khánh','1990-04-08', N'Nam Định',    N'Nam', 13200000, 4),
('NV010', N'Hồ',     N'Thị', N'Lan',  '1996-08-14', N'Quảng Ninh',  N'Nữ',  10800000, 5),

('NV011', N'Ngô',    N'Văn', N'Minh', '1987-05-19', N'Bắc Ninh',    N'Nam', 15500000, 1),
('NV012', N'Dương',  N'Thị', N'Nga',  '1993-10-01', N'Hưng Yên',    N'Nữ',  11700000, 2),
('NV013', N'Lý',     N'Văn', N'Phong','1992-01-27', N'Hà Tĩnh',     N'Nam', 14000000, 3),
('NV014', N'Tạ',     N'Thị', N'Quỳnh','1994-07-09', N'Quảng Bình',  N'Nữ',  12100000, 4),
('NV015', N'Chu',    N'Văn', N'Sơn',  '1989-12-11', N'Huế',         N'Nam', 13600000, 5),
('NV016', N'Trịnh',  N'Thị', N'Thảo', '1991-06-21', N'Quảng Nam',   N'Nữ',  11900000, 1),
('NV017', N'Mai',    N'Văn', N'Tiến', '1988-03-03', N'Bình Định',   N'Nam', 14800000, 2),
('NV018', N'Đinh',   N'Thị', N'Trang','1995-09-16', N'Phú Yên',     N'Nữ',  11400000, 3),
('NV019', N'Kiều',   N'Văn', N'Uy',   '1990-11-28', N'Khánh Hòa',   N'Nam', 12900000, 4),
('NV020', N'La',     N'Thị', N'Vân',  '1996-04-13', N'Ninh Thuận',  N'Nữ',  11100000, 5),

('NV021', N'Tống',   N'Văn', N'Xuân', '1987-08-07', N'Bình Thuận',  N'Nam', 15200000, 1),
('NV022', N'Châu',   N'Thị', N'Yến',  '1993-02-24', N'Đồng Nai',    N'Nữ',  11600000, 2),
('NV023', N'Quách',  N'Văn', N'Anh',  '1991-05-30', N'Bình Dương',  N'Nam', 13800000, 3),
('NV024', N'Tô',     N'Thị', N'Bích', '1994-12-02', N'Tây Ninh',    N'Nữ',  12200000, 4),
('NV025', N'Cao',    N'Văn', N'Châu', '1989-07-17', N'Long An',     N'Nam', 13500000, 5),
('NV026', N'Văn',    N'Thị', N'Diệp', '1992-09-29', N'Tiền Giang',  N'Nữ',  11300000, 1),
('NV027', N'Thái',   N'Văn', N'Giỏi', '1988-10-20', N'Bến Tre',     N'Nam', 14700000, 2),
('NV028', N'Lam',    N'Thị', N'Hoa',  '1995-01-08', N'Vĩnh Long',   N'Nữ',  12000000, 3),
('NV029', N'Ôn',     N'Văn', N'Khoa', '1990-06-14', N'Trà Vinh',    N'Nam', 13400000, 4),
('NV030', N'Phan',   N'Thị', N'Linh', '1994-03-26', N'Sóc Trăng',   N'Nữ',  11850000, 5);
GO
