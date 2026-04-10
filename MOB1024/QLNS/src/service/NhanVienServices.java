/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.NhanVien;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import utility.DBConnect;
/**
 *
 * @author ADMIN
 */
public class NhanVienServices {
    private ArrayList<NhanVien> list = new ArrayList<>();
    private final String FILE_NAME = "nhanvien.dat";
    private Scanner sc = new Scanner(System.in);

    DBConnect db = new DBConnect();

    // 1. Nhập danh sách và ghi file
    public void nhapVaGhiFile() {
        list.clear();

        while (true) {
            System.out.print("Nhap id: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nhap ten: ");
            String ten = sc.nextLine();

            System.out.print("Nhap luong gio: ");
            double luongGio = Double.parseDouble(sc.nextLine());

            list.add(new NhanVien(id, ten, luongGio));

            System.out.print("Nhap tiep? (y/n): ");
            if (!sc.nextLine().equalsIgnoreCase("y")) {
                break;
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
            System.out.println("Ghi file thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // 2. Đọc file, sắp xếp giảm dần theo lương giờ bằng Lambda và xuất
    public void docFileVaXuat() {
        File f = new File(FILE_NAME);

        if (!f.exists()) {
            System.out.println("File chua ton tai, vui long nhap danh sach truoc!");
            nhapVaGhiFile();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            list = (ArrayList<NhanVien>) ois.readObject();

            // Lambda Expression
            list.sort((a, b) -> Double.compare(b.getLuongGio(), a.getLuongGio()));

            System.out.println("===== DANH SACH NHAN VIEN =====");
            for (NhanVien nv : list) {
                nv.inThongTin();
            }
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
        }
    }

    // Kiểm tra trùng ID trong DB
    public boolean kiemTraTrungId(int id) {
        String sql = "SELECT * FROM NhanVien WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Loi kiem tra trung: " + e.getMessage());
            return false;
        }
    }

    // 3. Đẩy toàn bộ danh sách từ file lên DB
    public void dayFileLenDB() {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            System.out.println("Chua co file de day len DB!");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            list = (ArrayList<NhanVien>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO NhanVien(id, ten, luongGio) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int dem = 0;

            for (NhanVien nv : list) {
                if (!kiemTraTrungId(nv.getId())) {
                    ps.setInt(1, nv.getId());
                    ps.setString(2, nv.getTen());
                    ps.setDouble(3, nv.getLuongGio());
                    dem += ps.executeUpdate();
                }
            }

            System.out.println("Ket noi DB thanh cong!");
            System.out.println("Da them " + dem + " dong vao DB.");

        } catch (Exception e) {
            System.out.println("Ket noi that bai!");
            System.out.println("Nguyen nhan: " + e.getMessage());
        }
    }

    // 4. Thêm mới 1 đối tượng vào DB
    public void themMoiDB() {
        try {
            System.out.print("Nhap id: ");
            int id = Integer.parseInt(sc.nextLine());

            if (kiemTraTrungId(id)) {
                System.out.println("ID da ton tai trong DB!");
                return;
            }

            System.out.print("Nhap ten: ");
            String ten = sc.nextLine();

            System.out.print("Nhap luong gio: ");
            double luongGio = Double.parseDouble(sc.nextLine());

            String sql = "INSERT INTO NhanVien(id, ten, luongGio) VALUES (?, ?, ?)";

            try (Connection con = db.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.setString(2, ten);
                ps.setDouble(3, luongGio);

                int kq = ps.executeUpdate();
                if (kq > 0) {
                    System.out.println("Them moi thanh cong!");
                }
            }

            // gọi lại ý 3 để xác nhận
            System.out.println("=== Goi lai chuc nang 3 de xac nhan ===");
            dayFileLenDB();

        } catch (Exception e) {
            System.out.println("Loi them moi: " + e.getMessage());
        }
    }

    // 5. Update lương giờ = 1500
    public void updateLuong1500() {
        String sql = "UPDATE NhanVien SET luongGio = 1500 WHERE luongGio <> 1500";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int soDong = ps.executeUpdate();
            System.out.println("So dong da sua thanh cong: " + soDong);

        } catch (Exception e) {
            System.out.println("Loi update: " + e.getMessage());
        }
    }
}
