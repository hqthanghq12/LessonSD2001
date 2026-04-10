-- Câu 1: 
/*TaiKhoan(MaTaiKhoan, TenTaiKhoan, MatKhau, HoTen,
PhanQuyen) HoaDon(MaHoaDon, MaTaiKhoan, NgayDatHang,
TongTien) HoaDonCT(MaHoaDon, MaSanPham, SoLuong, DonGia)
Trường in đậm không được NULL. Trường in nghiêng là khóa ngoại.
Trường gạch chân là khóa chính*/
CREATE DATABASE COM2035_ThiThu01
CREATE TABLE TaiKhoan (
MaTaiKhoan VARCHAR(20) PRIMARY KEY, 
TenTaiKhoan NVARCHAR(50) NOT NULL, 
MatKhau VARCHAR(50) NOT NULL, 
HoTen NVARCHAR(100) NOT NULL,
PhanQuyen NVARCHAR(50) NOT NULL
)
GO
CREATE TABLE HoaDon(
MaHoaDon VARCHAR(20) PRIMARY KEY, 
MaTaiKhoan VARCHAR(20) NOT NULL, 
NgayDatHang DATE NOT NULL,
TongTien DECIMAL(18,2),
FOREIGN KEY (MaTaiKhoan) REFERENCES TaiKhoan(MaTaiKhoan)
)
GO
CREATE TABLE HoaDonCT(
MaHoaDon VARCHAR(20) NOT NULL, 
MaSanPham VARCHAR(20) NOT NULL, 
SoLuong INT NOT NULL, 
DonGia DECIMAL(18,2) NOT NULL,
PRIMARY KEY (MaHoaDon, MaSanPham),
FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon)
)
GO
-- CÂU 2
-- TaiKhoan
CREATE PROC sp_ThemTk
@MaTaiKhoan VARCHAR(20), 
@TenTaiKhoan NVARCHAR(50), 
@MatKhau VARCHAR(50), 
@HoTen NVARCHAR(100),
@PhanQuyen NVARCHAR(50)
AS
INSERT INTO TaiKhoan VALUES (@MaTaiKhoan, @TenTaiKhoan, @MatKhau, @HoTen, @PhanQuyen)
GO
-- Them du lieu
EXEC sp_ThemTk 'TK001', N'Nguoi dung 1', '123', N'Nguyen Van A', N'Khach hang'
EXEC sp_ThemTk 'TK002', N'Nguoi dung 2', '123', N'Nguyen Van B', N'Khach hang'
EXEC sp_ThemTk 'TK003', N'Quan tri vien', '123', N'Nguyen Van C', N'Admin'
SELECT * FROM TaiKhoan
GO
CREATE PROC sp_ThemHD 
@MaHoaDon VARCHAR(20), 
@MaTaiKhoan VARCHAR(20), 
@NgayDatHang DATE,
@TongTien DECIMAL(18,2)
AS
INSERT INTO HoaDon VALUES (@MaHoaDon, @MaTaiKhoan, @NgayDatHang, @TongTien)
GO
EXEC sp_ThemHD 'HD001', 'TK001', '2026-04-01', 500000
EXEC sp_ThemHD 'HD002', 'TK002', '2026-04-02', 700000
EXEC sp_ThemHD 'HD003', 'TK001', '2026-04-03', 300000
SELECT * FROM HoaDon
GO
CREATE PROC sp_ThemHDCT
@MaHoaDon VARCHAR(20), 
@MaSanPham VARCHAR(20), 
@SoLuong INT, 
@DonGia DECIMAL(18,2)
AS
INSERT INTO HoaDonCT VALUES (@MaHoaDon, @MaSanPham, @SoLuong, @DonGia)
GO
EXEC sp_ThemHDCT 'HD001', 'SP001', 2, 100000
EXEC sp_ThemHDCT 'HD001', 'SP002', 1, 300000
EXEC sp_ThemHDCT 'HD002', 'SP003', 1, 700000
SELECT * FROM HoaDonCT
GO
--  CAU 3
/*Tạo và sử dụng Khung nhìn thực hiện:
Hiển thị chi tiết hóa đơn gồm: Mã sản phẩm, Mã hóa đơn, Ngày đặt hàng,
Số lượng mua, đơn giá, Thành tiền (= Số lượng x đơn giá).
Yêu cầu: Ngày đặt hàng hiển thị dạng dd/mm/yyyy.
*/
CREATE VIEW vw_ChiTietHD
AS
SELECT ct.MaSanPham, ct.MaHoaDon, CONVERT( varchar(10), hd.NgayDatHang, 103) AS NgayDatHang,
ct.SoLuong, ct.DonGia, ct.SoLuong * ct.DonGia AS ThanhTien 
FROM HoaDonCT ct JOIN HoaDon hd 
ON ct.MaHoaDon = hd.MaHoaDon
GO 
SELECT *  FROM vw_ChiTietHD
GO
-- CAU 4
/*Tạo và sử dụng Khung nhìn thực hiện:
Hiển thị top 5 tài khoản mua hàng nhiều nhất: MaTaiKhoan,
TenTaiKhoan, tổng số lần mua.*/
CREATE VIEW vw_Top5Mua
AS
SELECT TOP 5 tk.MaTaiKhoan, tk.TenTaiKhoan,COUNT(MaHoaDon) AS TongLanMua
FROM TaiKhoan tk JOIN HoaDon hd 
ON tk.MaTaiKhoan = hd.MaTaiKhoan
GROUP BY tk.MaTaiKhoan, tk.TenTaiKhoan
ORDER BY COUNT(MaHoaDon) DESC
GO
SELECT *  FROM vw_Top5Mua
GO
-- cau 5
/*Tạo và sử dụng Hàm có đầu vào là MaHoaDon. Hàm trả về tổng số mặt
hàng của Mã Hóa Đơn truyền vào.*/
CREATE FUNCTION fn_TSMH (@MaHoaDon VARCHAR(20))
RETURNS INT 
AS
BEGIN 
DECLARE @Tong INT
SELECT @Tong = ISNULL(SUM(SoLuong), 0) FROM HoaDonCT  WHERE MaHoaDon = @MaHoaDon
RETURN @Tong
END;
GO 
SELECT dbo.fn_TSMH('HD001') AS TongSoMH
GO
-- Cau 6:
/*Tạo và sử dụng Thủ tục có đầu vào là MaHoaDon.
Thực hiện Xóa các HoaDon thỏa mãn tham số đầu vào.
Lưu ý: Phải thực hiện xóa thông tin trên bảng liên quan.
Yêu cầu: Sử dụng giao dịch trong thân SP, để đảm bảo tính toàn vẹn dữ liệu khi
một thao tác xóa thực hiện không thành công.*/
CREATE PROC sp_XoaHD  @MaHoaDon VARCHAR(20)
AS
BEGIN 
 BEGIN TRY 
	BEGIN TRAN 
	DELETE HoaDonCT WHERE MaHoaDon = @MaHoaDon
	DELETE HoaDon WHERE MaHoaDon = @MaHoaDon
	COMMIT TRAN
END TRY
BEGIN CATCH 
ROLLBACK TRAN
END CATCH
END
EXEC sp_XoaHD 'HD002'
SELECT * FROM HoaDonCT
SELECT * FROM HoaDon