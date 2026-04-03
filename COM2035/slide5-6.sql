/*
Stored Procedure là tập hợp các câu lệnh SQL 
được lưu trong cơ sở dữ liệu để tái sử dụng, 
hỗ trợ xử lý nhanh và chính xác.
Ưu điểm
- Tái sử dụng tốt
- Thực thi nhanh hơn việc gõ lại nhiều câu SQL
- Hỗ trợ bảo mật, phân quyền
- Có thể kết hợp SQL với IF...ELSE, WHILE
Cú pháp cơ bản
CREATE PROCEDURE TenThuTuc
    @ThamSo1 KieuDuLieu,
    @ThamSo2 KieuDuLieu = GiaTriMacDinh,
    @ThamSoOut KieuDuLieu OUTPUT
AS
BEGIN
    -- Các câu lệnh xử lý
END
Lưu ý: 
- CREATE PROCEDURE có thể viết tắt là CREATE PROC
- tên hàm, tên biến trong SQL Server thường không phân biệt hoa thường
- có tham số bắt buộc và tham số mặc định
Thực thi procedure
Dùng EXEC hoặc EXECUTE. Nếu biên dịch bằng F5 và gọi bằng EXEC
*/
-- Xem toàn bộ dữ liệu để sinh viên nắm cấu trúc
-- Xem danh sách phòng ban
SELECT * 
FROM PHONGBAN;

-- Xem danh sách nhân viên
SELECT * 
FROM NHANVIEN;
-- Nếu thủ tục đã tồn tại thì xóa để tạo lại (Có thể bỏ)
IF OBJECT_ID('sp_ThongTinNhanVien', 'P') IS NOT NULL
    DROP PROCEDURE sp_ThongTinNhanVien;
GO -- GO để tách batch.
-- Procedure lấy thông tin nhân viên theo mã nhân viên
CREATE PROCEDURE sp_ThongTinNhanVien
    @MaNV VARCHAR(10) -- Tham số đầu vào: mã nhân viên
AS
BEGIN
    -- Trả về thông tin nhân viên theo mã được truyền vào
    SELECT *
    FROM NHANVIEN
    WHERE MANV = @MaNV;
END;
GO
-- Gọi thủ tục
EXEC sp_ThongTinNhanVien 'NV005';
GO
-- Đây là 1 thủ tục lưu trữ rất cơ bản: 1 input → 1 result set
-- Khác với SELECT thường ở chỗ ta tái sử dụng được
-- Procedure đếm số lượng nhân viên của một phòng ban bằng OUTPUT
IF OBJECT_ID('sp_DemNhanVienTheoPhong', 'P') IS NOT NULL
    DROP PROCEDURE sp_DemNhanVienTheoPhong;
GO

CREATE PROCEDURE sp_DemNhanVienTheoPhong
    @IDPhong INT,           -- Tham số đầu vào
    @SoLuong INT OUTPUT     -- Tham số đầu ra
AS
BEGIN
    -- Gán số lượng nhân viên thuộc phòng ban vào biến OUTPUT
    SELECT @SoLuong = COUNT(*)
    FROM NHANVIEN
    WHERE IDPHG = @IDPhong;
END;
GO

-- Khai báo biến nhận kết quả
DECLARE @TongNhanVien INT;

-- Gọi thủ tục và đưa kết quả vào biến OUTPUT
EXEC sp_DemNhanVienTheoPhong 3, @TongNhanVien OUTPUT;

-- Hiển thị kết quả
PRINT N'Số lượng nhân viên của phòng 3 là: ' + CAST(@TongNhanVien AS NVARCHAR(10));
GO
/*
OUTPUT phù hợp khi cần trả ra một giá trị
Còn SELECT thì phù hợp khi trả ra nhiều dòng
*/
-- Procedure có RETURN
IF OBJECT_ID('sp_DemNhanVienTheoDiaChi', 'P') IS NOT NULL
    DROP PROCEDURE sp_DemNhanVienTheoDiaChi;
GO
CREATE PROCEDURE sp_DemNhanVienTheoDiaChi
    @TuKhoaDiaChi NVARCHAR(100) -- Ví dụ: N'Hà Nội', N'Hồ Chí Minh'
AS
BEGIN
    DECLARE @SoLuong INT;

    -- Đếm số nhân viên có địa chỉ chứa từ khóa truyền vào
    SELECT @SoLuong = COUNT(*)
    FROM NHANVIEN
    WHERE DCHI LIKE N'%' + @TuKhoaDiaChi + N'%';

    -- RETURN chỉ trả về số nguyên
    RETURN @SoLuong;
END;
GO
DECLARE @KetQua INT;

EXEC @KetQua = sp_DemNhanVienTheoDiaChi N'Hà';
PRINT N'Số nhân viên có địa chỉ chứa từ "Hà" là: ' + CAST(@KetQua AS NVARCHAR(10));
GO
/*
RETURN: trả 1 số nguyên, thường dùng báo trạng thái hoặc số lượng nhỏ
OUTPUT: linh hoạt hơn trong thực tế
*/
-- Kết với vòng lặp và if else
-- nhập @n, in tổng và số lượng số chẵn từ 1 đến @n
IF OBJECT_ID('sp_TinhTongVaDemChan', 'P') IS NOT NULL
    DROP PROCEDURE sp_TinhTongVaDemChan;
GO

CREATE PROCEDURE sp_TinhTongVaDemChan
    @n INT -- Số nguyên đầu vào
AS
BEGIN
    -- Khai báo biến dùng cho xử lý
    DECLARE @i INT = 1;          -- Biến chạy từ 1 đến n
    DECLARE @Tong INT = 0;       -- Biến lưu tổng các số từ 1 đến n
    DECLARE @DemChan INT = 0;    -- Biến đếm số lượng số chẵn

    -- Kiểm tra dữ liệu đầu vào hợp lệ
    IF @n < 1
    BEGIN
        PRINT N'Giá trị n phải lớn hơn hoặc bằng 1.';
        RETURN;
    END

    -- Dùng vòng lặp WHILE để duyệt từ 1 đến n
    WHILE @i <= @n
    BEGIN
        -- Cộng dồn vào tổng
        SET @Tong = @Tong + @i;

        -- Nếu là số chẵn thì tăng biến đếm
        IF @i % 2 = 0
        BEGIN
            SET @DemChan = @DemChan + 1;
        END

        -- Tăng biến chạy
        SET @i = @i + 1;
    END

    -- Trả kết quả ra dưới dạng result set
    SELECT 
        @n AS GiaTriN,
        @Tong AS TongTu1DenN,
        @DemChan AS SoLuongSoChan;
END;
GO

EXEC sp_TinhTongVaDemChan 10;
GO
-- Bài tập in số lẻ, số nguyên tố
-- Procedure dùng IF...ELSE trên dữ liệu nhân sự
IF OBJECT_ID('sp_XepLoaiLuongNhanVien', 'P') IS NOT NULL
    DROP PROCEDURE sp_XepLoaiLuongNhanVien;
GO

CREATE PROCEDURE sp_XepLoaiLuongNhanVien
    @MaNV VARCHAR(10)
AS
BEGIN
    DECLARE @Luong DECIMAL(18,2);

    -- Lấy lương của nhân viên
    SELECT @Luong = LUONG
    FROM NHANVIEN
    WHERE MANV = @MaNV;

    -- Nếu không tồn tại mã nhân viên
    IF @Luong IS NULL
    BEGIN
        PRINT N'Không tìm thấy nhân viên.';
    END
    ELSE IF @Luong >= 14000000
    BEGIN
        PRINT N'Nhân viên có mức lương cao.';
    END
    ELSE IF @Luong >= 12000000
    BEGIN
        PRINT N'Nhân viên có mức lương khá.';
    END
    ELSE
    BEGIN
        PRINT N'Nhân viên có mức lương trung bình.';
    END
END;
GO

EXEC sp_XepLoaiLuongNhanVien 'NV003';
EXEC sp_XepLoaiLuongNhanVien 'NV010';
GO
/*
Trigger là một dạng thủ tục đặc biệt, 
tự động chạy khi có INSERT, UPDATE, DELETE trên bảng.
Trigger không gọi trực tiếp bằng EXEC
Trigger là một stored procedure không có tham số.
Các bảng Inserted và Deleted: 
Các trigger DML sử dụng hai loại bảng đặc biệt để
sửa đổi dữ liệu trong cơ sở dữ liệu.
inserted: chứa bản ghi mới của thao tác INSERT, UPDATE
deleted: chứa bản ghi cũ của thao tác DELETE, UPDATE
*/
-- Trigger chặn thêm nhân viên có lương dưới 11 triệu
IF OBJECT_ID('trg_CheckLuong_Insert', 'TR') IS NOT NULL
    DROP TRIGGER trg_CheckLuong_Insert;
GO

CREATE TRIGGER trg_CheckLuong_Insert
ON NHANVIEN
FOR INSERT
AS
BEGIN
    -- Nếu có bất kỳ nhân viên nào được chèn vào có lương nhỏ hơn 11 triệu
    IF EXISTS (
        SELECT 1
        FROM inserted
        WHERE LUONG < 11000000
    )
    BEGIN
        -- Thông báo lỗi
        PRINT N'Lương nhân viên phải lớn hơn hoặc bằng 11.000.000';

        -- Hủy toàn bộ giao dịch INSERT
        ROLLBACK TRANSACTION;
    END
END;
GO

-- Thử chèn dữ liệu sai
INSERT INTO NHANVIEN (MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG)
VALUES ('NV031', N'Test', N'Văn', N'Lỗi', '1998-01-01', N'Hà Nội', N'Nam', 9000000, 1);
GO
/*
Trigger lấy dữ liệu từ inserted
Nếu vi phạm thì ROLLBACK
*/
--Trigger chặn cập nhật lương xuống dưới 11 triệu
IF OBJECT_ID('trg_CheckLuong_Update', 'TR') IS NOT NULL
    DROP TRIGGER trg_CheckLuong_Update;
GO

CREATE TRIGGER trg_CheckLuong_Update
ON NHANVIEN
FOR UPDATE
AS
BEGIN
    -- Nếu trong dữ liệu mới có mức lương dưới chuẩn
    IF EXISTS (
        SELECT 1
        FROM inserted
        WHERE LUONG < 11000000
    )
    BEGIN
        PRINT N'Không được cập nhật lương nhỏ hơn 11.000.000';
        ROLLBACK TRANSACTION;
    END
END;
GO

-- Thử cập nhật sai
UPDATE NHANVIEN
SET LUONG = 10000000
WHERE MANV = 'NV003';
GO
-- Trigger không cho xóa nhân viên ở Hồ Chí Minh
IF OBJECT_ID('trg_KhongXoaNhanVienHCM', 'TR') IS NOT NULL
    DROP TRIGGER trg_KhongXoaNhanVienHCM;
GO

CREATE TRIGGER trg_KhongXoaNhanVienHCM
ON NHANVIEN
FOR DELETE
AS
BEGIN
    -- Nếu bản ghi bị xóa có địa chỉ là Hồ Chí Minh
    IF EXISTS (
        SELECT 1
        FROM deleted
        WHERE DCHI = N'Hồ Chí Minh'
    )
    BEGIN
        PRINT N'Không được xóa nhân viên có địa chỉ Hồ Chí Minh';
        ROLLBACK TRANSACTION;
    END
END;
GO

-- Thử xóa nhân viên ở TP.HCM
DELETE FROM NHANVIEN
WHERE MANV = 'NV004';
GO
-- Trigger ghi log số lượng nhân viên bị xóa (AFTER DELETE)
-- Tạo bảng log nếu chưa có
IF OBJECT_ID('LOG_XOA_NHANVIEN', 'U') IS NOT NULL
    DROP TABLE LOG_XOA_NHANVIEN;
GO

CREATE TABLE LOG_XOA_NHANVIEN
(
    ID INT IDENTITY(1,1) PRIMARY KEY,
    SoLuongBiXoa INT,
    ThoiDiemXoa DATETIME DEFAULT GETDATE()
);
GO

IF OBJECT_ID('trg_AfterDelete_NhanVien', 'TR') IS NOT NULL
    DROP TRIGGER trg_AfterDelete_NhanVien;
GO

CREATE TRIGGER trg_AfterDelete_NhanVien
ON NHANVIEN
AFTER DELETE
AS
BEGIN
    -- Ghi lại số lượng bản ghi vừa bị xóa
    INSERT INTO LOG_XOA_NHANVIEN(SoLuongBiXoa)
    SELECT COUNT(*) 
    FROM deleted;
END;
GO

-- Thử xóa một nhân viên không ở Hồ Chí Minh
DELETE FROM NHANVIEN
WHERE MANV = 'NV005';
GO

-- Xem log
SELECT * 
FROM LOG_XOA_NHANVIEN;
GO
-- Trigger theo dõi thay đổi lương bằng inserted và deleted
IF OBJECT_ID('LOG_CAPNHAT_LUONG', 'U') IS NOT NULL
    DROP TABLE LOG_CAPNHAT_LUONG;
GO

CREATE TABLE LOG_CAPNHAT_LUONG
(
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MANV VARCHAR(10),
    LuongCu DECIMAL(18,2),
    LuongMoi DECIMAL(18,2),
    ThoiDiem DATETIME DEFAULT GETDATE()
);
GO

IF OBJECT_ID('trg_LogUpdateLuong', 'TR') IS NOT NULL
    DROP TRIGGER trg_LogUpdateLuong;
GO

CREATE TRIGGER trg_LogUpdateLuong
ON NHANVIEN
AFTER UPDATE
AS
BEGIN
    -- Chỉ ghi log khi có thay đổi cột lương
    IF UPDATE(LUONG)
    BEGIN
        INSERT INTO LOG_CAPNHAT_LUONG(MANV, LuongCu, LuongMoi)
        SELECT 
            d.MANV,      -- Lương cũ từ bảng deleted
            d.LUONG,
            i.LUONG      -- Lương mới từ bảng inserted
        FROM deleted d
        INNER JOIN inserted i ON d.ID = i.ID;
    END
END;
GO

-- Thử cập nhật lương hợp lệ
UPDATE NHANVIEN
SET LUONG = 15100000
WHERE MANV = 'NV017';
GO

SELECT * 
FROM LOG_CAPNHAT_LUONG;
GO
-- Trigger INSTEAD OF INSERT để chặn mã nhân viên trùng quy tắc riêng
IF OBJECT_ID('trg_InsteadOfInsert_NhanVien', 'TR') IS NOT NULL
    DROP TRIGGER trg_InsteadOfInsert_NhanVien;
GO

CREATE TRIGGER trg_InsteadOfInsert_NhanVien
ON NHANVIEN
INSTEAD OF INSERT
AS
BEGIN
    -- Nếu có mã nhân viên đã tồn tại thì không cho chèn
    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN NHANVIEN n ON i.MANV = n.MANV
    )
    BEGIN
        PRINT N'Mã nhân viên đã tồn tại, không thể thêm.';
    END
    ELSE
    BEGIN
        -- Nếu hợp lệ thì tự tay chèn dữ liệu từ inserted vào bảng thật
        INSERT INTO NHANVIEN (MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG)
        SELECT 
            MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG
        FROM inserted;
        
        PRINT N'Thêm nhân viên thành công.';
    END
END;
GO

-- Thử thêm trùng MANV
INSERT INTO NHANVIEN (MANV, HONV, TENLOT, TENNV, NGSINH, DCHI, PHAI, LUONG, IDPHG)
VALUES ('NV001', N'Trùng', N'Văn', N'Mã', '1999-01-01', N'Hà Nội', N'Nam', 12000000, 1);
GO
/*
So sánh Stored Procedure và Trigger
Stored Procedure
Chủ động gọi bằng EXEC
Có thể có tham số vào/ra
Phù hợp cho nghiệp vụ cần người dùng hoặc chương trình chủ động thực hiện
Trigger
Tự động chạy khi có sự kiện trên bảng
Không gọi trực tiếp bằng EXEC
Phù hợp để kiểm soát ràng buộc nghiệp vụ, log, kiểm tra dữ liệu tự động
*/
/* 
Hàm người dùng định nghĩa và View
hàm người dùng tự định nghĩa là một đối tượng CSDL chứa các câu lệnh SQL,
được biên dịch sẵn và lưu trong CSDL; 
hàm thực hiện tính toán hay xử lý logic và trả về một giá trị. 
Giá trị trả về có thể là:
- Giá trị vô hướng
- Một bảng
Các em hiểu đơn giản:
Nếu cùng một phép tính phải dùng đi dùng lại nhiều lần, ta đóng gói nó thành function
Sau đó chỉ cần gọi tên hàm trong câu lệnh SQL
Ví dụ:
Tính tuổi theo năm sinh
Đếm số lượng nhân viên theo giới tính
Trả về danh sách nhân viên của một phòng ban
So sánh hàm với stored procedure
Giống stored procedure ở chỗ đều là đối tượng CSDL chứa lệnh SQL và được lưu sẵn trong CSDL.
Khác ở chỗ:
Hàm luôn phải trả về một giá trị bằng RETURN
Hàm không có tham số đầu ra
Hàm không được phép cập nhật trực tiếp dữ liệu trên bảng/view đang tồn tại bằng INSERT, UPDATE, DELETE
Nhưng có thể thao tác trên biến bảng hay cấu trúc tạm được tạo trong thân hàm.
Cách nói ngắn gọn trên lớp
Procedure: thiên về xử lý nghiệp vụ
Function: thiên về tính toán, trả về dữ liệu để dùng trong truy vấn
Các loại hàm:
Hàm giá trị vô hướng: trả về một giá trị đơn
Hàm giá trị bảng đơn giản / nội tuyến: trả về bảng từ một câu lệnh SELECT
Hàm giá trị bảng đa câu lệnh: trả về bảng sau nhiều bước xử lý
*/
-- Hàm vô hướng tính tuổi từ năm sinh
CREATE FUNCTION dbo.fn_TinhTuoi
(
    @NamSinh INT -- Tham số đầu vào là năm sinh
)
RETURNS INT
AS
BEGIN
    -- Trả về tuổi = năm hiện tại - năm sinh
    RETURN YEAR(GETDATE()) - @NamSinh;
END;
GO

-- Gọi thử hàm
PRINT N'Tuổi của người sinh năm 2000 là: ' 
    + CAST(dbo.fn_TinhTuoi(2000) AS NVARCHAR(10));
GO
-- Hàm vô hướng đếm tổng số nhân viên
CREATE FUNCTION dbo.fn_DemNhanVien()
RETURNS INT
AS
BEGIN
    -- Đếm toàn bộ số dòng trong bảng NHANVIEN
    RETURN (
        SELECT COUNT(*)
        FROM NHANVIEN
    );
END;
GO

-- Cách 1: In trực tiếp kết quả
PRINT N'Tổng số nhân viên là: ' 
    + CAST(dbo.fn_DemNhanVien() AS NVARCHAR(10));
GO

-- Cách 2: Gán vào biến rồi xuất ra
DECLARE @TongNV INT;
SET @TongNV = dbo.fn_DemNhanVien();

PRINT N'Số nhân viên hiện có: ' + CAST(@TongNV AS NVARCHAR(10));
GO
-- BT: Hàm vô hướng đếm số nhân viên theo giới tính
-- Hàm giá trị bảng đơn giản
CREATE FUNCTION dbo.fn_DanhSachNhanVienTheoPhong
(
    @IDPhong INT -- Mã phòng ban cần lọc
)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        MANV,              -- Mã nhân viên
        HONV,              -- Họ nhân viên
        TENLOT,            -- Tên lót
        TENNV,             -- Tên chính
        PHAI,              -- Giới tính
        LUONG,             -- Lương
        IDPHG              -- Mã phòng ban
    FROM NHANVIEN
    WHERE IDPHG = @IDPhong
);
GO

-- Gọi hàm như gọi 1 bảng
SELECT *
FROM dbo.fn_DanhSachNhanVienTheoPhong(3);
GO
/*Đây là hàm trả về bảng
Có thể dùng trực tiếp trong FROM
Thường rất tiện để đóng gói các điều kiện lọc lặp đi lặp lại*/
-- Hàm giá trị bảng đa câu lệnh
CREATE FUNCTION dbo.fn_ThongTinPhongBan
(
    @IDPhong INT -- Mã phòng ban, có thể NULL
)
RETURNS @BangKetQua TABLE
(
    ID INT,                    -- Mã phòng ban
    TENPHONG NVARCHAR(100)     -- Tên phòng ban
)
AS
BEGIN
    -- Nếu tham số truyền vào là NULL
    IF @IDPhong IS NULL
    BEGIN
        -- Chèn toàn bộ phòng ban vào bảng kết quả
        INSERT INTO @BangKetQua(ID, TENPHONG)
        SELECT ID, TENPHONG
        FROM PHONGBAN;
    END
    ELSE
    BEGIN
        -- Chèn đúng phòng ban theo mã truyền vào
        INSERT INTO @BangKetQua(ID, TENPHONG)
        SELECT ID, TENPHONG
        FROM PHONGBAN
        WHERE ID = @IDPhong;
    END

    -- Trả về bảng kết quả
    RETURN;
END;
GO

-- Gọi thử 1: lấy toàn bộ phòng ban
SELECT *
FROM dbo.fn_ThongTinPhongBan(NULL);
GO

-- Gọi thử 2: lấy 1 phòng ban cụ thể
SELECT *
FROM dbo.fn_ThongTinPhongBan(2);
GO
/* 
View là khung nhìn dữ liệu, thường dùng để:
Che giấu và bảo mật dữ liệu
Hiển thị dữ liệu theo nhu cầu từng người dùng
Lưu trữ truy vấn phức tạp dùng thường xuyên
Đảm bảo tính toàn vẹn khi cập nhật qua view trong một số trường hợp
Lợi ích của View
Che dấu và bảo mật dữ liệu
Không cho người dùng xem toàn bộ cột hoặc toàn bộ bảng.
Hiển thị tùy biến
Mỗi nhóm người dùng có thể có một view riêng.
Lưu truy vấn phức tạp thường dùng
Có thể thực thi nhanh hơn do được biên dịch sẵn theo nội dung slide.
*/
-- Tạo view hiển thị tên nhân viên và tên phòng ban
CREATE VIEW dbo.vw_NhanVien_PhongBan
AS
SELECT
    NV.MANV,                                           -- Mã nhân viên
    NV.HONV + N' ' + ISNULL(NV.TENLOT, N'') + N' ' + NV.TENNV AS HoTen, -- Họ tên đầy đủ
    PB.TENPHONG                                        -- Tên phòng ban
FROM NHANVIEN NV
INNER JOIN PHONGBAN PB
    ON NV.IDPHG = PB.ID;
GO

-- Xem dữ liệu từ view
SELECT *
FROM dbo.vw_NhanVien_PhongBan;
GO
/*
View giúp giấu việc JOIN khỏi người dùng cuối
Người dùng chỉ cần SELECT * FROM vw_NhanVien_PhongBan
*/
-- View có thể cập nhật được
CREATE VIEW dbo.vw_PhongKyThuat
AS
SELECT
    ID,          -- Mã phòng
    TENPHONG     -- Tên phòng
FROM PHONGBAN
WHERE ID = 3;
GO

-- Kiểm tra dữ liệu trước khi cập nhật
SELECT *
FROM dbo.vw_PhongKyThuat;
GO

-- Cập nhật dữ liệu thông qua view
UPDATE dbo.vw_PhongKyThuat
SET TENPHONG = N'Phòng Công nghệ';
GO

-- Kiểm tra lại sau cập nhật
SELECT *
FROM dbo.vw_PhongKyThuat;
GO

-- Kiểm tra bảng gốc để thấy dữ liệu đã đổi
SELECT *
FROM PHONGBAN
WHERE ID = 3;
GO
-- View chỉ đọc
CREATE VIEW dbo.vw_ThongTinNhanVien_Tuoi
AS
SELECT
    MANV,                                            -- Mã nhân viên
    HONV + N' ' + ISNULL(TENLOT, N'') + N' ' + TENNV AS HoTen, -- Họ tên
    LUONG,                                           -- Lương
    YEAR(GETDATE()) - YEAR(NGSINH) AS Tuoi          -- Cột tính toán
FROM NHANVIEN
WHERE YEAR(GETDATE()) - YEAR(NGSINH) < 40;
GO

-- Xem dữ liệu
SELECT *
FROM dbo.vw_ThongTinNhanVien_Tuoi;
GO
-- Sửa và xóa View
ALTER VIEW dbo.vw_NhanVien_PhongBan
AS
SELECT
    NV.MANV,
    NV.HONV + N' ' + ISNULL(NV.TENLOT, N'') + N' ' + NV.TENNV AS HoTen,
    PB.TENPHONG,
    NV.LUONG
FROM NHANVIEN NV
INNER JOIN PHONGBAN PB
    ON NV.IDPHG = PB.ID
WHERE NV.LUONG > 13000000;
GO

-- Kiểm tra sau khi sửa
SELECT *
FROM dbo.vw_NhanVien_PhongBan;
GO

-- =============================================
-- Xóa view nếu không còn sử dụng
-- =============================================
DROP VIEW dbo.vw_NhanVien_PhongBan;
GO