-- =========================================
-- TẠO CƠ SỞ DỮ LIỆU
-- =========================================
CREATE DATABASE COM2035_ThiThu01
GO

-- Chọn CSDL vừa tạo để làm việc
USE COM2035_ThiThu01
GO


-- =========================================
-- CÂU 1: TẠO CÁC BẢNG
-- =========================================

-- Bảng tài khoản
CREATE TABLE TaiKhoan(
	MaTaiKhoan VARCHAR(20) PRIMARY KEY,       -- Mã tài khoản: khóa chính
	TenTaiKhoan NVARCHAR(50) NOT NULL,        -- Tên tài khoản: không được rỗng
	MatKhau VARCHAR(50) NOT NULL,             -- Mật khẩu: không được rỗng
	HoTen NVARCHAR(100) NOT NULL,            -- Họ tên: không được rỗng
	PhanQuyen NVARCHAR(30) NOT NULL          -- Phân quyền: không được rỗng
)
GO

-- Bảng hóa đơn
CREATE TABLE HoaDon(
	MaHoaDon VARCHAR(20) PRIMARY KEY,         -- Mã hóa đơn: khóa chính
	MaTaiKhoan VARCHAR(20) NOT NULL,          -- Mã tài khoản: khóa ngoại liên kết bảng TaiKhoan
	NgayDatHang DATE NOT NULL,                -- Ngày đặt hàng
	TongTien DECIMAL(18,2) NOT NULL,          -- Tổng tiền
	FOREIGN KEY (MaTaiKhoan) REFERENCES TaiKhoan(MaTaiKhoan)
)
GO

-- Bảng hóa đơn chi tiết
CREATE TABLE HoaDonCT(
	MaHoaDon VARCHAR(20) NOT NULL,            -- Mã hóa đơn: khóa ngoại
	MaSanPham VARCHAR(20) NOT NULL,           -- Mã sản phẩm
	SoLuong INT NOT NULL,                     -- Số lượng
	DonGia DECIMAL(18,2) NOT NULL,            -- Đơn giá
	PRIMARY KEY (MaHoaDon,MaSanPham),         -- Khóa chính ghép
	FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon)
)
GO


-- =========================================
-- CÂU 2 PHẦN 1:
-- TẠO THỦ TỤC THÊM DỮ LIỆU CƠ BẢN
-- =========================================

-- Thủ tục thêm dữ liệu vào bảng TaiKhoan
CREATE PROC sp_ThemTaiKhoan
@MaTaiKhoan VARCHAR(20),@TenTaiKhoan NVARCHAR(50),@MatKhau VARCHAR(50),@HoTen NVARCHAR(100),@PhanQuyen NVARCHAR(30)
AS
-- Thêm trực tiếp 1 dòng mới vào bảng TaiKhoan
INSERT INTO TaiKhoan VALUES(@MaTaiKhoan,@TenTaiKhoan,@MatKhau,@HoTen,@PhanQuyen)
GO

-- Thủ tục thêm dữ liệu vào bảng HoaDon
CREATE PROC sp_ThemHoaDon
@MaHoaDon VARCHAR(20),@MaTaiKhoan VARCHAR(20),@NgayDatHang DATE,@TongTien DECIMAL(18,2)
AS
-- Thêm trực tiếp 1 dòng mới vào bảng HoaDon
INSERT INTO HoaDon VALUES(@MaHoaDon,@MaTaiKhoan,@NgayDatHang,@TongTien)
GO

-- Thủ tục thêm dữ liệu vào bảng HoaDonCT
CREATE PROC sp_ThemHoaDonCT
@MaHoaDon VARCHAR(20),@MaSanPham VARCHAR(20),@SoLuong INT,@DonGia DECIMAL(18,2)
AS
-- Thêm trực tiếp 1 dòng mới vào bảng HoaDonCT
INSERT INTO HoaDonCT VALUES(@MaHoaDon,@MaSanPham,@SoLuong,@DonGia)
GO


-- 3 lời gọi thành công cho thủ tục thêm TaiKhoan
EXEC sp_ThemTaiKhoan 'TK01',N'user01','123',N'Nguyễn Văn A',N'Khách hàng'
EXEC sp_ThemTaiKhoan 'TK02',N'user02','123',N'Trần Thị B',N'Khách hàng'
EXEC sp_ThemTaiKhoan 'TK03',N'admin01','123',N'Lê Văn C',N'Admin'
SELECT * FROM TaiKhoan
GO

-- 3 lời gọi thành công cho thủ tục thêm HoaDon
EXEC sp_ThemHoaDon 'HD01','TK01','2026-04-01',500000
EXEC sp_ThemHoaDon 'HD02','TK01','2026-04-02',700000
EXEC sp_ThemHoaDon 'HD03','TK02','2026-04-03',300000
SELECT * FROM HoaDon
GO

-- 3 lời gọi thành công cho thủ tục thêm HoaDonCT
EXEC sp_ThemHoaDonCT 'HD01','SP01',2,100000
EXEC sp_ThemHoaDonCT 'HD01','SP02',1,300000
EXEC sp_ThemHoaDonCT 'HD02','SP03',5,140000
SELECT * FROM HoaDonCT
GO


-- =========================================
-- CÂU 2 PHẦN 2:
-- THỦ TỤC THÊM DỮ LIỆU CÓ KIỂM TRA
-- =========================================

-- Thủ tục thêm TaiKhoan có kiểm tra dữ liệu đầu vào
CREATE PROC sp_ThemTaiKhoan_KT
@MaTaiKhoan VARCHAR(20),@TenTaiKhoan NVARCHAR(50),@MatKhau VARCHAR(50),@HoTen NVARCHAR(100),@PhanQuyen NVARCHAR(30)
AS
BEGIN
	-- Kiểm tra dữ liệu có bị rỗng không
	IF @MaTaiKhoan='' OR @TenTaiKhoan='' OR @MatKhau='' OR @HoTen='' OR @PhanQuyen=''
	BEGIN
		PRINT N'Dữ liệu không hợp lệ'
		RETURN
	END

	-- Kiểm tra mã tài khoản đã tồn tại chưa
	IF EXISTS(SELECT * FROM TaiKhoan WHERE MaTaiKhoan=@MaTaiKhoan)
	BEGIN
		PRINT N'Mã tài khoản đã tồn tại'
		RETURN
	END

	-- Nếu hợp lệ thì thêm vào bảng
	INSERT INTO TaiKhoan VALUES(@MaTaiKhoan,@TenTaiKhoan,@MatKhau,@HoTen,@PhanQuyen)
END
GO

-- Thủ tục thêm HoaDon có kiểm tra dữ liệu đầu vào
CREATE PROC sp_ThemHoaDon_KT
@MaHoaDon VARCHAR(20),@MaTaiKhoan VARCHAR(20),@NgayDatHang DATE,@TongTien DECIMAL(18,2)
AS
BEGIN
	-- Kiểm tra dữ liệu đầu vào
	IF @MaHoaDon='' OR @MaTaiKhoan='' OR @NgayDatHang IS NULL OR @TongTien<0
	BEGIN
		PRINT N'Dữ liệu không hợp lệ'
		RETURN
	END

	-- Kiểm tra mã hóa đơn đã tồn tại chưa
	IF EXISTS(SELECT * FROM HoaDon WHERE MaHoaDon=@MaHoaDon)
	BEGIN
		PRINT N'Mã hóa đơn đã tồn tại'
		RETURN
	END

	-- Kiểm tra mã tài khoản có tồn tại trong bảng TaiKhoan không
	IF NOT EXISTS(SELECT * FROM TaiKhoan WHERE MaTaiKhoan=@MaTaiKhoan)
	BEGIN
		PRINT N'Mã tài khoản không tồn tại'
		RETURN
	END

	-- Nếu hợp lệ thì thêm vào bảng
	INSERT INTO HoaDon VALUES(@MaHoaDon,@MaTaiKhoan,@NgayDatHang,@TongTien)
END
GO

-- Thủ tục thêm HoaDonCT có kiểm tra dữ liệu đầu vào
CREATE PROC sp_ThemHoaDonCT_KT
@MaHoaDon VARCHAR(20),@MaSanPham VARCHAR(20),@SoLuong INT,@DonGia DECIMAL(18,2)
AS
BEGIN
	-- Kiểm tra dữ liệu đầu vào
	IF @MaHoaDon='' OR @MaSanPham='' OR @SoLuong<=0 OR @DonGia<=0
	BEGIN
		PRINT N'Dữ liệu không hợp lệ'
		RETURN
	END

	-- Kiểm tra mã hóa đơn có tồn tại hay không
	IF NOT EXISTS(SELECT * FROM HoaDon WHERE MaHoaDon=@MaHoaDon)
	BEGIN
		PRINT N'Mã hóa đơn không tồn tại'
		RETURN
	END

	-- Kiểm tra sản phẩm trong hóa đơn đã tồn tại chưa
	IF EXISTS(SELECT * FROM HoaDonCT WHERE MaHoaDon=@MaHoaDon AND MaSanPham=@MaSanPham)
	BEGIN
		PRINT N'Sản phẩm đã tồn tại'
		RETURN
	END

	-- Nếu hợp lệ thì thêm vào bảng
	INSERT INTO HoaDonCT VALUES(@MaHoaDon,@MaSanPham,@SoLuong,@DonGia)
END
GO


-- 3 lời gọi thành công cho thủ tục có kiểm tra: TaiKhoan
EXEC sp_ThemTaiKhoan_KT 'TK11',N'user11','123',N'Phạm Văn D',N'Khách hàng'
EXEC sp_ThemTaiKhoan_KT 'TK12',N'user12','123',N'Hoàng Thị E',N'Khách hàng'
EXEC sp_ThemTaiKhoan_KT 'TK13',N'admin02','123',N'Đỗ Văn F',N'Admin'
GO

-- 3 lời gọi thành công cho thủ tục có kiểm tra: HoaDon
EXEC sp_ThemHoaDon_KT 'HD11','TK11','2026-04-05',250000
EXEC sp_ThemHoaDon_KT 'HD12','TK12','2026-04-06',450000
EXEC sp_ThemHoaDon_KT 'HD13','TK13','2026-04-07',900000
GO

-- 3 lời gọi thành công cho thủ tục có kiểm tra: HoaDonCT
EXEC sp_ThemHoaDonCT_KT 'HD11','SP11',2,120000
EXEC sp_ThemHoaDonCT_KT 'HD11','SP12',1,130000
EXEC sp_ThemHoaDonCT_KT 'HD12','SP13',3,150000
GO


-- =========================================
-- CÂU 3: TẠO VIEW CHI TIẾT HÓA ĐƠN
-- =========================================
CREATE VIEW vw_ChiTietHoaDon
AS
-- Lấy thông tin chi tiết hóa đơn gồm:
-- mã sản phẩm, mã hóa đơn, ngày đặt hàng, số lượng, đơn giá, thành tiền
SELECT MaSanPham,ct.MaHoaDon,CONVERT(VARCHAR(10),NgayDatHang,103) NgayDatHang,SoLuong,DonGia,SoLuong*DonGia ThanhTien
FROM HoaDonCT ct JOIN HoaDon hd ON ct.MaHoaDon=hd.MaHoaDon
GO


-- =========================================
-- CÂU 4: TẠO VIEW TOP 5 TÀI KHOẢN MUA NHIỀU NHẤT
-- =========================================
CREATE VIEW vw_Top5TaiKhoanMuaNhieu
AS
-- Thống kê top 5 tài khoản có số lần mua nhiều nhất
SELECT TOP 5 tk.MaTaiKhoan,TenTaiKhoan,COUNT(MaHoaDon) TongSoLanMua
FROM TaiKhoan tk JOIN HoaDon hd ON tk.MaTaiKhoan=hd.MaTaiKhoan
GROUP BY tk.MaTaiKhoan,TenTaiKhoan
ORDER BY COUNT(MaHoaDon) DESC
GO
SELECT * FROM vw_ChiTietHoaDon
GO


-- =========================================
-- CÂU 5: HÀM TRẢ VỀ TỔNG SỐ MẶT HÀNG
-- =========================================
CREATE FUNCTION fn_TongSoMatHang(@MaHoaDon VARCHAR(20))
RETURNS INT
AS
BEGIN
	-- Biến lưu tổng số lượng sản phẩm
	DECLARE @Tong INT

	-- Tính tổng số lượng theo mã hóa đơn truyền vào
	SELECT @Tong=ISNULL(SUM(SoLuong),0) FROM HoaDonCT WHERE MaHoaDon=@MaHoaDon

	-- Trả kết quả về
	RETURN @Tong
END
GO
SELECT dbo.fn_TongSoMatHang('HD01') AS TongSoMatHang
GO

-- =========================================
-- CÂU 6: THỦ TỤC XÓA HÓA ĐƠN CÓ GIAO DỊCH
-- =========================================
CREATE PROC sp_XoaHoaDon @MaHoaDon VARCHAR(20)
AS
BEGIN
	BEGIN TRY
		-- Bắt đầu giao dịch
		BEGIN TRAN

			-- Xóa chi tiết hóa đơn trước
			DELETE FROM HoaDonCT WHERE MaHoaDon=@MaHoaDon

			-- Sau đó xóa hóa đơn
			DELETE FROM HoaDon WHERE MaHoaDon=@MaHoaDon

		-- Nếu thành công thì lưu thay đổi
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		-- Nếu lỗi thì quay lui dữ liệu
		ROLLBACK TRAN
	END CATCH
END
GO
EXEC sp_XoaHoaDon 'HD03'