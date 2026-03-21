--Lý thuyết ngắn
--Ngầm định: SQL Server tự đổi kiểu khi cần.
--Tường minh: lập trình viên chủ động dùng CAST, CONVERT
-- Chuyển đổi ngầm
SELECT 100 * 0.5 AS KetQua; 
-- Kết quả là số thực vì 0.5 là kiểu số thập phân
-- Lỗi khi ghép chuỗi với ngày
SELECT N'Hôm nay là: ' + GETDATE();
-- Sẽ lỗi vì không ghép trực tiếp nvarchar với datetime. 
-- Đây cũng là ý trong slide ví dụ 'Today is ' + GETDATE().
-- CAST
SELECT N'Hôm nay là: ' + CAST(GETDATE() AS VARCHAR(30)) AS HomNay;
-- CONVERT định dạng ngày
SELECT 
    CONVERT(VARCHAR(10), GETDATE(), 103) AS DinhDangVN,
    CONVERT(VARCHAR(10), GETDATE(), 101) AS DinhDangUS,
    CONVERT(VARCHAR(11), GETDATE(), 107) AS DinhDangChu;
-- chuyển kiểu dữ liệu lương
SELECT 
    MANV,
    LUONG,
    CAST(LUONG AS INT) AS Luong_Int,
    CONVERT(VARCHAR(20), LUONG) AS Luong_Text
FROM NHANVIEN;
-- CAST gọn, chuẩn SQL.
-- CONVERT mạnh hơn khi cần định dạng, nhất là ngày tháng.
-- Hàm toán học: có các hàm PI, SQRT, SQUARE, CEILING, FLOOR, ROUND, ABS.
SELECT 
    PI() AS SoPi,
    SQRT(25) AS CanBacHai,
    SQUARE(3) AS BinhPhuong,
    CEILING(9 / 4.0) AS LamTronLen,
    FLOOR(9 / 4.0) AS LamTronXuong,
    ROUND(5.153745, 2) AS LamTron2ChuSo,
    ABS(-2500) AS GiaTriTuyetDoi;
-- ứng dụng trên bảng NHANVIEN
	SELECT 
    MANV,
    TENNV,
    LUONG,
    ROUND(LUONG / 1000000.0, 2) AS Luong_Trieu,
    ABS(LUONG - 13000000) AS DoLechSoVoi13Tr
FROM NHANVIEN
ORDER BY DoLechSoVoi13Tr;
-- Hàm xử lý chuỗi: LEN, LTRIM, RTRIM, LEFT, 
-- RIGHT, CHARINDEX, SUBSTRING, REPLACE, 
-- LOWER, UPPER, REVERSE, SPACE.
-- tạo họ tên đầy đủ
SELECT 
    MANV,
    HONV + N' ' + ISNULL(TENLOT + N' ', N'') + TENNV AS HoTenDayDu
FROM NHANVIEN;
-- độ dài chuỗi, in hoa/in thường
SELECT 
    MANV,
    TENNV,
    LEN(TENNV) AS DoDaiTen,
    UPPER(TENNV) AS TenHoa,
    LOWER(TENNV) AS TenThuong
FROM NHANVIEN;
-- lấy ký tự đầu/cuối địa chỉ
SELECT 
    MANV,
    DCHI,
    LEFT(DCHI, 3) AS BaKyTuDau,
    RIGHT(DCHI, 3) AS BaKyTuCuoi
FROM NHANVIEN;
-- tìm vị trí ký tự, cắt chuỗi
SELECT 
    DCHI,
    CHARINDEX(N' ', DCHI) AS ViTriKhoangTrangDauTien,
    SUBSTRING(DCHI, 1, 3) AS CatChuoi
FROM NHANVIEN;
-- chuẩn hóa dữ liệu
SELECT 
    REPLACE(DCHI, N'Hồ Chí Minh', N'TP HCM') AS DiaChiRutGon
FROM NHANVIEN;
-- tách họ và tên
SELECT 
    HONV + N' ' + ISNULL(TENLOT + N' ', N'') + TENNV AS HoTen,
    LEFT(HONV + N' ' + ISNULL(TENLOT + N' ', N'') + TENNV,
         CHARINDEX(N' ', HONV + N' ' + ISNULL(TENLOT + N' ', N'') + TENNV) - 1) AS Ho,
    TENNV AS Ten
FROM NHANVIEN;
-- Hàm ngày tháng năm
-- GETDATE, YEAR, MONTH, DAY, DATENAME
-- lấy ngày giờ hiện tại
SELECT 
    GETDATE() AS NgayGioHeThong,
    CONVERT(DATE, GETDATE()) AS ChiLayNgay,
    CONVERT(TIME, GETDATE()) AS ChiLayGio;
-- tách năm, tháng, ngày
SELECT 
    YEAR(GETDATE()) AS Nam,
    MONTH(GETDATE()) AS Thang,
    DAY(GETDATE()) AS Ngay;
-- lấy thông tin từ ngày sinh
SELECT 
    MANV,
    TENNV,
    NGSINH,
    YEAR(NGSINH) AS NamSinh,
    MONTH(NGSINH) AS ThangSinh,
    DATENAME(WEEKDAY, NGSINH) AS ThuSinh
FROM NHANVIEN;
-- tính tuổi gần đúng
SELECT 
    MANV,
    TENNV,
    NGSINH,
    YEAR(GETDATE()) - YEAR(NGSINH) AS TuoiGanDung
FROM NHANVIEN;
-- Nhắc lỗi thường gặp
-- Nên nhập ngày theo chuẩn YYYY-MM-DD.
-- Khi hiển thị cho người Việt, dùng CONVERT(..., 103).
-- Điều kiện: IF...ELSE, IIF, CASE
-- IF...ELSE
-- Dùng khi cần ra quyết định trong khối lệnh.
-- Nếu nhiều lệnh thì dùng BEGIN...END
-- kiểm tra mức lương cao
DECLARE @Luong DECIMAL(18,2);
SET @Luong = 15000000;
IF @Luong >= 13000000
    PRINT N'Lương khá cao';
ELSE
    PRINT N'Lương chưa cao'
-- kiểm tra có nhân viên lương trên 14 triệu không
IF EXISTS (SELECT 1 FROM NHANVIEN WHERE LUONG > 14000000)
BEGIN
    PRINT N'Có nhân viên lương trên 14 triệu';
    SELECT MANV, TENNV, LUONG
    FROM NHANVIEN
    WHERE LUONG > 14000000;
END
ELSE
BEGIN
    PRINT N'Không có nhân viên nào lương trên 14 triệu';
END
-- IIF
SELECT 
    MANV,
    TENNV,
    LUONG,
    IIF(LUONG >= 13000000, N'Lương cao', N'Lương trung bình') AS XepLoaiLuong
FROM NHANVIEN;
-- IIF viết nhanh.
-- Chỉ nên dùng với điều kiện đơn giản.
-- CASE
-- Simple CASE theo giới tính
SELECT 
    MANV,
    HONV + N' ' + ISNULL(TENLOT + N' ', N'') + TENNV AS HoTen,
    CASE PHAI
        WHEN N'Nam' THEN N'Ông'
        WHEN N'Nữ' THEN N'Bà'
        ELSE N'Khác'
    END AS XungHo
FROM NHANVIEN;
-- Searched CASE theo lương
SELECT 
    MANV,
    TENNV,
    LUONG,
    CASE
        WHEN LUONG < 11500000 THEN N'Thấp'
        WHEN LUONG >= 11500000 AND LUONG < 13000000 THEN N'Trung bình'
        WHEN LUONG >= 13000000 AND LUONG < 14500000 THEN N'Khá'
        ELSE N'Cao'
    END AS MucLuong
FROM NHANVIEN;
-- đếm nhân viên từng phòng ban và đánh giá
SELECT 
    PB.TENPHONG,
    COUNT(NV.ID) AS SoLuongNhanVien,
    CASE
        WHEN COUNT(NV.ID) < 3 THEN N'Thiếu nhân viên'
        WHEN COUNT(NV.ID) < 5 THEN N'Đủ nhân viên'
        ELSE N'Đông nhân viên'
    END AS DanhGia
FROM PHONGBAN PB
LEFT JOIN NHANVIEN NV ON PB.ID = NV.IDPHG
GROUP BY PB.TENPHONG;
-- tính thuế theo mức lương
SELECT 
    MANV,
    TENNV,
    LUONG,
    CASE
        WHEN LUONG < 11500000 THEN LUONG * 0.05
        WHEN LUONG < 12500000 THEN LUONG * 0.08
        WHEN LUONG < 13500000 THEN LUONG * 0.10
        WHEN LUONG < 14500000 THEN LUONG * 0.12
        ELSE LUONG * 0.15
    END AS THUE
FROM NHANVIEN;
-- Vòng lặp WHILE, BREAK, CONTINUE
-- WHILE cơ bản
DECLARE @i INT = 1;
WHILE @i <= 5
BEGIN
    PRINT N'Lần lặp thứ: ' + CAST(@i AS VARCHAR(10));
    SET @i = @i + 1;
END
-- Tính tổng số chẵn từ 1 đến 10
DECLARE @n INT = 1;
DECLARE @tong INT = 0;

WHILE @n <= 10
BEGIN
    IF @n % 2 = 0
        SET @tong = @tong + @n;
    SET @n = @n + 1;
END

PRINT N'Tổng số chẵn từ 1 đến 10 là: ' + CAST(@tong AS VARCHAR(10));
-- Bỏ qua số 4 bằng CONTINUE
WHILE @n <= 10
BEGIN
    IF @n = 4
    BEGIN
        SET @n = @n + 1;
        CONTINUE;
    END

    IF @n % 2 = 0
        SET @tong = @tong + @n;

    SET @n = @n + 1;
END
PRINT N'Tổng số chẵn từ 1 đến 10, bỏ số 4 là: ' + CAST(@tong AS VARCHAR(10));
-- BREAK
WHILE @n <= 10
BEGIN
    IF @n = 6
        BREAK;

    PRINT @n;
    SET @n = @n + 1;
END
-- trong SQL thực tế, ưu tiên xử lý theo tập dữ liệu hơn là vòng lặp.
-- WHILE dùng khi cần mô phỏng thuật toán hoặc xử lý từng bước.
-- Quản lý lỗi: TRY...CATCH, RAISERROR
-- TRY...CATCH cơ bản
BEGIN TRY
    SELECT 10 / 0 AS KetQua;
END TRY
BEGIN CATCH
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage,
        ERROR_LINE() AS ErrorLine;
END CATCH
-- Bắt lỗi chuyển kiểu
BEGIN TRY
    SELECT CAST(N'abc' AS INT) AS GiaTri;
END TRY
BEGIN CATCH
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage;
END CATCH
-- Kiểm tra dữ liệu trước khi thêm nhân viên
BEGIN TRY
    INSERT INTO NHANVIEN (MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG)
    VALUES ('NV999', N'Test', N'Van', N'Loi', '1999-01-01', N'Hà Nội', N'Nam', 10000000, 99);

    PRINT N'Thêm dữ liệu thành công';
END TRY
BEGIN CATCH
    PRINT N'Thêm dữ liệu thất bại';
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage;
END CATCH
-- Chủ động báo lỗi với RAISERROR
DECLARE @LuongNhap DECIMAL(18,2) = -5000000;

BEGIN TRY
    IF @LuongNhap <= 0
        RAISERROR(N'Lương phải lớn hơn 0', 16, 1);

    PRINT N'Dữ liệu hợp lệ';
END TRY
BEGIN CATCH
    SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_MESSAGE() AS ErrorMessage,
        ERROR_SEVERITY() AS ErrorSeverity,
        ERROR_STATE() AS ErrorState;
END CATCH
-- ví dụ: 
DECLARE @MaPhong INT = 10;

BEGIN TRY
    IF NOT EXISTS (SELECT 1 FROM PHONGBAN WHERE ID = @MaPhong)
        RAISERROR(N'Mã phòng ban không tồn tại', 16, 1);

    PRINT N'Phòng ban hợp lệ';
END TRY
BEGIN CATCH
    PRINT N'Có lỗi dữ liệu đầu vào';
    SELECT ERROR_MESSAGE() AS Loi;
END CATCH
-- TRY...CATCH để bắt lỗi hệ thống.
-- RAISERROR để báo lỗi nghiệp vụ do mình kiểm soát.