/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demotest;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
/**
 *
 * @author ADMIN
 */
public class Services {
    private ArrayList<NhanVien> list = new ArrayList<>();
    private final String FILE = "nhanvien.dat";
    private final Scanner sc = new Scanner(System.in);
    // 1. Nhập danh sách và ghi file
    public void nhapVaGhiFile() {
        list.clear();
        String tiep;
        do {
            System.out.print("Nhap id: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nhap ten: ");
            String ten = sc.nextLine();
            System.out.print("Nhap luong gio: ");
            double luong = Double.parseDouble(sc.nextLine());

            list.add(new NhanVien(id, ten, luong));
            System.out.print("Nhap tiep? (y/n): ");
            tiep = sc.nextLine();
        } while (tiep.equalsIgnoreCase("y"));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(list);
            System.out.println("Ghi file thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // 2. Đọc file, sắp xếp giảm dần theo lương giờ và xuất
    public void docFileVaXuat() {
        File f = new File(FILE);
        if (!f.exists()) {
            System.out.println("Chua co file, vui long nhap du lieu!");
            nhapVaGhiFile();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            list = (ArrayList<NhanVien>) ois.readObject();
            list.sort((a, b) -> Double.compare(b.getLuongGio(), a.getLuongGio())); // Lambda
            System.out.println("===== DANH SACH TU FILE =====");
            list.forEach(NhanVien::inThongTin);
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
        }
    }

    // Kiểm tra id đã tồn tại trong DB chưa
    private boolean trungID(int id) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE id = ?";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            System.out.println("Loi check ID: " + e.getMessage());
            return true;
        }
    }

    // 3. Đẩy toàn bộ danh sách từ file lên DB
    public void dayFileLenDB() {
        docFileVaXuat(); // đọc lại file trước khi đẩy
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?)";
        int dem = 0;

        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (NhanVien nv : list) {
                if (!trungID(nv.getId())) {
                    ps.setInt(1, nv.getId());
                    ps.setString(2, nv.getTen());
                    ps.setDouble(3, nv.getLuongGio());
                    dem += ps.executeUpdate();
                }
            }
            System.out.println("Day len DB thanh cong! So dong them: " + dem);
        } catch (Exception e) {
            System.out.println("Ket noi JDBC that bai: " + e.getMessage());
        }
    }

    // Hiển thị dữ liệu trong DB
    public void hienThiDB() {
        String sql = "SELECT * FROM NhanVien ORDER BY luongGio DESC";
        try (Connection con = getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("===== DANH SACH TRONG DB =====");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getDouble(3));
            }
        } catch (Exception e) {
            System.out.println("Loi doc DB: " + e.getMessage());
        }
    }

    // 4. Thêm mới 1 đối tượng vào DB, có check trùng
    public void themDB() {
        try {
            System.out.print("Nhap id: ");
            int id = Integer.parseInt(sc.nextLine());
            if (trungID(id)) {
                System.out.println("ID da ton tai!");
                return;
            }

            System.out.print("Nhap ten: ");
            String ten = sc.nextLine();
            System.out.print("Nhap luong gio: ");
            double luong = Double.parseDouble(sc.nextLine());

            String sql = "INSERT INTO NhanVien VALUES (?, ?, ?)";
            try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, ten);
                ps.setDouble(3, luong);
                System.out.println(ps.executeUpdate() > 0 ? "Them thanh cong!" : "Them that bai!");
            }
            hienThiDB(); // xác nhận lại
        } catch (Exception e) {
            System.out.println("Loi them DB: " + e.getMessage());
        }
    }

    // 5. Update lương giờ = 1500
    public void updateLuong() {
        String sql = "UPDATE NhanVien SET luongGio = 1500";
        try (Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("So dong da sua: " + ps.executeUpdate());
        } catch (Exception e) {
            System.out.println("Loi update: " + e.getMessage());
        }
    }
}
