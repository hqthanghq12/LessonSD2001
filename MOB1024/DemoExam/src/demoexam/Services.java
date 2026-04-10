package demoexam;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CLASS SERVICES - Xử lý toàn bộ nghiệp vụ:
 *   - Thao tác File (IO Stream): đọc, ghi danh sách NhanVien
 *   - Thao tác Database (JDBC): kết nối, insert, update, select
 *
 * SQL tạo bảng tương ứng (chạy trong SQL Server trước khi test):
 * -----------------------------------------------------------------
 *   CREATE DATABASE QLNhanVien;
 *   USE QLNhanVien;
 *   CREATE TABLE NhanVien (
 *       id       INT PRIMARY KEY,
 *       ten      NVARCHAR(100),
 *       luongGio FLOAT
 *   );
 * -----------------------------------------------------------------
 */
public class Services {

    // =============================================
    // THUỘC TÍNH
    // =============================================

    // Danh sách nhân viên lưu trên RAM (dùng chung cho file & DB)
    private ArrayList<NhanVien> list = new ArrayList<>();

    // Tên file lưu dữ liệu (nằm cùng thư mục chạy chương trình)
    private final String FILE_NAME = "nhanvien.dat";

    // Thông tin kết nối SQL Server - sinh viên sửa PASSWORD cho đúng máy mình
    private final String DB_URL  = "jdbc:sqlserver://localhost:1433;"
                                 + "databaseName=QLNhanVien;"
                                 + "encrypt=true;"
                                 + "trustServerCertificate=true";
    private final String DB_USER = "sa";
    private final String DB_PASS = "123";   // <-- Sửa thành password SQL Server của bạn

    // =============================================
    // GETTER danh sách (dùng khi Main cần truy cập)
    // =============================================
    public ArrayList<NhanVien> getList() {
        return list;
    }

    // =========================================================
    // CHỨC NĂNG 1: Nhập danh sách và ghi thẳng xuống File
    // =========================================================
    public void nhapDanhSachVaGhiFile(Scanner sc) {
        list.clear(); // Xoá danh sách cũ trên RAM trước khi nhập mới
        String tiepTuc;

        do {
            // --- Nhập ID (có xử lý lỗi nhập sai kiểu số) ---
            int id = -1;
            while (true) {
                try {
                    System.out.print("  Nhap id    : ");
                    id = Integer.parseInt(sc.nextLine().trim());
                    break; // Nhập đúng -> thoát vòng lặp kiểm tra
                } catch (NumberFormatException e) {
                    System.out.println("  [Loi] Id phai la so nguyen, nhap lai!");
                }
            }

            // --- Nhập Tên ---
            System.out.print("  Nhap ten   : ");
            String ten = sc.nextLine().trim();

            // --- Nhập Lương Giờ (có xử lý lỗi nhập sai kiểu số) ---
            double luongGio = -1;
            while (true) {
                try {
                    System.out.print("  Nhap luong gio: ");
                    luongGio = Double.parseDouble(sc.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  [Loi] Luong gio phai la so thuc, nhap lai!");
                }
            }

            // Tạo đối tượng và thêm vào danh sách RAM
            list.add(new NhanVien(id, ten, luongGio));

            System.out.print("  Nhap tiep khong? (c/k): ");
            tiepTuc = sc.nextLine().trim();

        } while (tiepTuc.equalsIgnoreCase("c"));

        // Sau khi nhập xong -> ghi xuống file ngay
        ghiFile();
        System.out.println(">> Da nhap va ghi xuong file thanh cong!");
    }

    // =========================================================
    // CHỨC NĂNG PHỤ: Ghi danh sách xuống File (IO Stream)
    // Dùng ObjectOutputStream để serialize toàn bộ ArrayList
    // =========================================================
    private void ghiFile() {
        // try-with-resources: tự động đóng stream sau khi xong (Java 7+)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);  // Ghi toàn bộ ArrayList<NhanVien> vào file
        } catch (IOException e) {
            System.out.println("[Loi] Ghi file: " + e.getMessage());
        }
    }

    // =========================================================
    // CHỨC NĂNG 2: Đọc File và xuất ra màn hình
    //   - Nếu file chưa có -> yêu cầu nhập trước
    //   - Dùng Lambda Expression để sắp xếp giảm dần theo lương giờ
    // =========================================================
    @SuppressWarnings("unchecked")
    public void docFileVaXuat(Scanner sc) {
        File f = new File(FILE_NAME);

        // Kiểm tra file có tồn tại và có dữ liệu không
        if (!f.exists() || f.length() == 0) {
            System.out.println("[Canh bao] File chua co du lieu -> Yeu cau nhap truoc.");
            nhapDanhSachVaGhiFile(sc);
        }

        // Đọc file bằng ObjectInputStream (deserialize)
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {

            list = (ArrayList<NhanVien>) ois.readObject();

            // -------------------------------------------------------
            // LAMBDA EXPRESSION: Sắp xếp giảm dần theo lương giờ
            // Cú pháp: (thamSo1, thamSo2) -> bieu_thuc_tra_ve
            //   - a, b là 2 NhanVien đang so sánh
            //   - Double.compare(b, a) -> đảo thứ tự = giảm dần
            // -------------------------------------------------------
            list.sort((a, b) -> Double.compare(b.getLuongGio(), a.getLuongGio()));

            System.out.println("\n===== DANH SACH NHAN VIEN TU FILE (sap xep luong giam dan) =====");
            for (NhanVien nv : list) {
                nv.inThongTin();
            }
            System.out.println("=================================================================");

        } catch (FileNotFoundException e) {
            System.out.println("[Loi] Khong tim thay file.");
        } catch (IOException e) {
            System.out.println("[Loi] Doc file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Loi] Khong tim thay class khi doc file (class NhanVien bi thay doi?).");
        }
    }

    // =========================================================
    // CHỨC NĂNG 3: Kết nối JDBC và đẩy toàn bộ danh sách từ File lên DB
    //   - Nếu list rỗng -> thử đọc từ file trước
    //   - Bỏ qua các id đã tồn tại trong DB (check trùng)
    //   - Nếu kết nối thất bại -> chỉ ra nguyên nhân lỗi
    // =========================================================
    public void dayDanhSachTuFileLenDB() {
        // Nếu list trên RAM chưa có dữ liệu -> đọc từ file trước
        if (list.isEmpty()) {
            try {
                docFileKhongCanNhap();
                System.out.println(">> Da doc du lieu tu file de day len DB.");
            } catch (Exception e) {
                System.out.println("[Canh bao] Chua co du lieu trong file. Hay chon chuc nang 1 truoc.");
                return;
            }
        }

        String sql = "INSERT INTO NhanVien(id, ten, luongGio) VALUES (?, ?, ?)";

        // Mở 1 Connection dùng chung cho cả vòng lặp (tối ưu hơn mở/đóng nhiều lần)
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println(">> Ket noi JDBC thanh cong!");
            int soLuongInsert = 0;

            for (NhanVien nv : list) {
                // Check trùng: nếu id đã có trong DB thì bỏ qua
                if (kiemTraTonTai(con, nv.getId())) {
                    System.out.println("   [Bo qua] ID " + nv.getId() + " da ton tai trong DB.");
                    continue;
                }

                // Set tham số cho PreparedStatement (? thứ 1, 2, 3)
                ps.setInt(1, nv.getId());
                ps.setString(2, nv.getTen());
                ps.setDouble(3, nv.getLuongGio());
                soLuongInsert += ps.executeUpdate();
            }

            System.out.println(">> Da day " + soLuongInsert + " dong len Database thanh cong.");

        } catch (Exception e) {
            // Kết nối thất bại -> in ra nguyên nhân lỗi (yêu cầu đề thi)
            System.out.println("[Loi] Ket noi JDBC that bai!");
            System.out.println("  Nguyen nhan: " + e.getMessage());
        }
    }

    // =========================================================
    // CHỨC NĂNG 4: Thêm mới 1 nhân viên vào DB
    //   - Check trùng ID trước khi thêm
    //   - Thêm xong -> gọi hienThiTatCaTuDB() để xác nhận (yêu cầu đề thi)
    // =========================================================
    public void themMoiVaoDB(Scanner sc) {
        // --- Nhập ID (có xử lý lỗi) ---
        int id = -1;
        while (true) {
            try {
                System.out.print("  Nhap id moi: ");
                id = Integer.parseInt(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("  [Loi] Id phai la so nguyen, nhap lai!");
            }
        }

        // Mở kết nối 1 lần để dùng cho cả check trùng lẫn insert
        try (Connection con = getConnection()) {

            // Check trùng ID trong DB
            if (kiemTraTonTai(con, id)) {
                System.out.println("[Canh bao] ID " + id + " da ton tai trong DB. Khong the them trung!");
                return;
            }

            // --- Nhập Tên ---
            System.out.print("  Nhap ten moi: ");
            String ten = sc.nextLine().trim();

            // --- Nhập Lương Giờ ---
            double luongGio = -1;
            while (true) {
                try {
                    System.out.print("  Nhap luong gio moi: ");
                    luongGio = Double.parseDouble(sc.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  [Loi] Luong gio phai la so thuc, nhap lai!");
                }
            }

            String sql = "INSERT INTO NhanVien(id, ten, luongGio) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, ten);
                ps.setDouble(3, luongGio);

                int ketQua = ps.executeUpdate();
                if (ketQua > 0) {
                    System.out.println(">> Them moi vao DB thanh cong!");
                    // Gọi lại hienThiTatCaTuDB() để xác nhận - yêu cầu đề thi
                    System.out.println("-- Xac nhan danh sach hien tai trong DB --");
                    hienThiTatCaTuDB(con);  // Dùng lại connection đã mở (không tốn thêm kết nối)
                } else {
                    System.out.println("[Loi] Them moi that bai.");
                }
            }

        } catch (Exception e) {
            System.out.println("[Loi] Them moi: " + e.getMessage());
        }
    }

    // =========================================================
    // CHỨC NĂNG 5: Cập nhật lương giờ = 1500 cho tất cả nhân viên chưa bằng 1500
    //   - Chỉ ra số dòng đã sửa thành công (yêu cầu đề thi)
    // =========================================================
    public void capNhatLuong1500() {
        // Chỉ update những dòng CHƯA bằng 1500 (tránh update thừa)
        String sql = "UPDATE NhanVien SET luongGio = 1500 WHERE luongGio <> 1500";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int soDong = ps.executeUpdate();
            System.out.println(">> So dong da sua thanh cong: " + soDong);

        } catch (Exception e) {
            System.out.println("[Loi] Cap nhat DB: " + e.getMessage());
        }
    }

    // =========================================================
    // PHƯƠNG THỨC PHỤ: Lấy kết nối JDBC
    //   - Load driver SQL Server
    //   - Trả về Connection để các method khác dùng
    // =========================================================
    public Connection getConnection() throws Exception {
        // Load driver SQL Server (có thể bỏ qua nếu dùng JDBC 4.0+, tự tìm driver)
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // =========================================================
    // PHƯƠNG THỨC PHỤ: Đọc file mà không hỏi nhập lại
    //   - Dùng bởi chức năng 3 khi list rỗng
    // =========================================================
    @SuppressWarnings("unchecked")
    public void docFileKhongCanNhap() throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {
            list = (ArrayList<NhanVien>) ois.readObject();
        }
    }

    // =========================================================
    // PHƯƠNG THỨC PHỤ: Kiểm tra ID đã tồn tại trong DB chưa
    //   - Nhận Connection từ bên ngoài -> tránh mở kết nối thừa
    //   - Trả về true = đã tồn tại, false = chưa có
    // =========================================================
    private boolean kiemTraTonTai(Connection con, int id) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 = đã tồn tại
            }
        } catch (Exception e) {
            System.out.println("[Loi] Kiem tra trung: " + e.getMessage());
        }
        return false;
    }

    // =========================================================
    // PHƯƠNG THỨC PHỤ: Hiển thị toàn bộ nhân viên từ DB
    //   - Overload: nhận Connection từ bên ngoài (dùng lại connection đang mở)
    // =========================================================
    private void hienThiTatCaTuDB(Connection con) {
        String sql = "SELECT id, ten, luongGio FROM NhanVien";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== DANH SACH NHAN VIEN TRONG DB =====");
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("id"),
                        rs.getString("ten"),
                        rs.getDouble("luongGio")
                );
                nv.inThongTin();
            }
            System.out.println("=========================================");

        } catch (Exception e) {
            System.out.println("[Loi] Hien thi DB: " + e.getMessage());
        }
    }

    // Overload public: dùng khi gọi từ bên ngoài (tự mở connection mới)
    public void hienThiTatCaTuDB() {
        try (Connection con = getConnection()) {
            hienThiTatCaTuDB(con);
        } catch (Exception e) {
            System.out.println("[Loi] Ket noi DB: " + e.getMessage());
        }
    }
}
