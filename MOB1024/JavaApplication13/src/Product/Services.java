/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Product;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author ADMIN
 */
public class Services {
    private final ArrayList<SanPham> ds = new ArrayList<>();
    private final String FILE_NAME = "sanpham.dat";

    // SQL Server
    private final String URL =
            "jdbc:sqlserver://localhost:1433;" +
            "databaseName=quanlysanpham;" +
            "encrypt=true;" +
            "trustServerCertificate=true;" +
            "sendStringParametersAsUnicode=true";

    private final String USER = "sa";
    private final String PASSWORD = "123456";

    public ArrayList<SanPham> getDs() {
        return ds;
    }

    // ===================== NHAP DANH SACH VA GHI FILE =====================
    public void nhapDanhSachVaGhiFile(Scanner sc) {
        ds.clear();
        String tiepTuc;

        do {
            SanPham sp = nhap1SanPham(sc);
            ds.add(sp);

            System.out.print("Ban co muon nhap tiep khong? (y/n): ");
            tiepTuc = sc.nextLine();
        } while (tiepTuc.equalsIgnoreCase("y"));

        ghiFile();
        System.out.println("Da ghi danh sach xuong file thanh cong.");
    }

    public SanPham nhap1SanPham(Scanner sc) {
        int id;
        int soLuong;
        double giaBan;
        String ten;

        System.out.print("Nhap ID: ");
        id = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap ten san pham: ");
        ten = sc.nextLine();

        System.out.print("Nhap so luong: ");
        soLuong = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap gia ban: ");
        giaBan = Double.parseDouble(sc.nextLine());

        return new SanPham(id, ten, soLuong, giaBan);
    }

    // ===================== FILE IO =====================
    public void ghiFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(ds);
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public boolean docFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ds.clear();
            ds.addAll((ArrayList<SanPham>) ois.readObject());
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Loi doc file: " + e.getMessage());
            return false;
        }
    }

    // ===================== CHUC NANG 2 =====================
    public void docFileVaXuat(Scanner sc) {
        boolean ketQua = docFile();

        if (!ketQua) {
            System.out.println("File chua ton tai, vui long nhap danh sach!");
            nhapDanhSachVaGhiFile(sc);
            ketQua = docFile();
        }

        if (ketQua) {
            if (ds.isEmpty()) {
                System.out.println("Danh sach trong.");
                return;
            }

            // Lambda Expression: sap xep giam dan theo gia ban
            ds.sort((sp1, sp2) -> Double.compare(sp2.getGiaBan(), sp1.getGiaBan()));

            System.out.println("===== DANH SACH SAN PHAM TU FILE =====");
            System.out.printf("%-5s | %-25s | %-10s | %-12s%n", "ID", "Ten", "SoLuong", "GiaBan");
            for (SanPham sp : ds) {
                sp.inThongTin();
            }
        }
    }

    // ===================== JDBC =====================
    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new Exception("Khong tim thay Driver JDBC SQL Server. Hay them thu vien mssql-jdbc.");
        } catch (SQLException e) {
            throw new Exception("Ket noi CSDL that bai: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 3 =====================
    public void dayDanhSachTuFileLenDB() {
        boolean ketQua = docFile();

        if (!ketQua) {
            System.out.println("Khong co file de doc du lieu.");
            return;
        }

        if (ds.isEmpty()) {
            System.out.println("Danh sach trong, khong co du lieu de day len DB.");
            return;
        }

        String sqlCheck = "SELECT COUNT(*) FROM sanpham WHERE id = ?";
        String sqlInsert = "INSERT INTO sanpham(id, ten, soluong, giaban) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck);
             PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            int demThem = 0;
            int demBoQua = 0;

            for (SanPham sp : ds) {
                psCheck.setInt(1, sp.getId());
                ResultSet rs = psCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    psInsert.setInt(1, sp.getId());
                    psInsert.setNString(2, sp.getTen()); // tieng Viet
                    psInsert.setInt(3, sp.getSoLuong());
                    psInsert.setDouble(4, sp.getGiaBan());
                    psInsert.executeUpdate();
                    demThem++;
                } else {
                    demBoQua++;
                }
            }

            System.out.println("Day du lieu len DB thanh cong.");
            System.out.println("So ban ghi them moi: " + demThem);
            System.out.println("So ban ghi bi bo qua do trung ID: " + demBoQua);

            hienThiTatCaTrongDB();

        } catch (Exception e) {
            System.out.println("Loi day du lieu len DB: " + e.getMessage());
        }
    }

    // ===================== HIEN THI TOAN BO DB =====================
    public void hienThiTatCaTrongDB() {
        String sql = "SELECT * FROM sanpham";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("===== DANH SACH SAN PHAM TRONG DATABASE =====");
            System.out.printf("%-5s | %-25s | %-10s | %-12s%n", "ID", "Ten", "SoLuong", "GiaBan");

            boolean coDuLieu = false;
            while (rs.next()) {
                coDuLieu = true;
                System.out.printf("%-5d | %-25s | %-10d | %-12.2f%n",
                        rs.getInt("id"),
                        rs.getNString("ten"),
                        rs.getInt("soluong"),
                        rs.getDouble("giaban"));
            }

            if (!coDuLieu) {
                System.out.println("Bang sanpham hien dang rong.");
            }

        } catch (Exception e) {
            System.out.println("Loi hien thi DB: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 4 =====================
    public void themMoiVaoDB(Scanner sc) {
        SanPham sp = nhap1SanPham(sc);

        String sqlCheck = "SELECT COUNT(*) FROM sanpham WHERE id = ?";
        String sqlInsert = "INSERT INTO sanpham(id, ten, soluong, giaban) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck);
             PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            psCheck.setInt(1, sp.getId());
            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                System.out.println("Them that bai! ID nay da ton tai trong DB.");
                return;
            }

            psInsert.setInt(1, sp.getId());
            psInsert.setNString(2, sp.getTen()); // tieng Viet
            psInsert.setInt(3, sp.getSoLuong());
            psInsert.setDouble(4, sp.getGiaBan());

            int rows = psInsert.executeUpdate();
            if (rows > 0) {
                System.out.println("Them moi vao DB thanh cong.");
                hienThiTatCaTrongDB();
            }

        } catch (Exception e) {
            System.out.println("Loi them moi vao DB: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 5 =====================
    public void timTheoTen(String tenCanTim) {
        String sql = "SELECT * FROM sanpham WHERE ten LIKE ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setNString(1, "%" + tenCanTim + "%"); // tim kiem tieng Viet
            ResultSet rs = ps.executeQuery();

            boolean timThay = false;
            System.out.println("===== KET QUA TIM KIEM =====");
            System.out.printf("%-5s | %-25s | %-10s | %-12s%n", "ID", "Ten", "SoLuong", "GiaBan");

            while (rs.next()) {
                timThay = true;
                System.out.printf("%-5d | %-25s | %-10d | %-12.2f%n",
                        rs.getInt("id"),
                        rs.getNString("ten"),
                        rs.getInt("soluong"),
                        rs.getDouble("giaban"));
            }

            if (!timThay) {
                System.out.println("Khong co san pham do.");
            }

        } catch (Exception e) {
            System.out.println("Loi tim kiem: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 6 =====================
    public void capNhatTheoId(Scanner sc) {
        System.out.print("Nhap ID can cap nhat: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap ten moi: ");
        String ten = sc.nextLine();

        System.out.print("Nhap so luong moi: ");
        int soLuong = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap gia ban moi: ");
        double giaBan = Double.parseDouble(sc.nextLine());

        String sql = "UPDATE sanpham SET ten = ?, soluong = ?, giaban = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setNString(1, ten); // cap nhat tieng Viet
            ps.setInt(2, soLuong);
            ps.setDouble(3, giaBan);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Cap nhat thanh cong.");
                hienThiTatCaTrongDB();
            } else {
                System.out.println("Khong tim thay san pham co ID = " + id);
            }

        } catch (Exception e) {
            System.out.println("Loi cap nhat: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 7 =====================
    public void capNhatGiaThanh1500() {
        String sql = "UPDATE sanpham SET giaban = 1500 WHERE giaban < 1500";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int rows = ps.executeUpdate();
            System.out.println("So dong da sua thanh cong: " + rows);
            hienThiTatCaTrongDB();

        } catch (Exception e) {
            System.out.println("Loi cap nhat gia: " + e.getMessage());
        }
    }

    // ===================== CHUC NANG 8 =====================
    public void xoaTheoId(int id) {
        String sql = "DELETE FROM sanpham WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Xoa thanh cong.");
                hienThiTatCaTrongDB();
            } else {
                System.out.println("Khong tim thay san pham co ID = " + id);
            }

        } catch (Exception e) {
            System.out.println("Loi xoa: " + e.getMessage());
        }
    }
    
}
