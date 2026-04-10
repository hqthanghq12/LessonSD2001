package demoexam;

import java.io.Serializable;

/**
 * CLASS MODEL - Đại diện cho thực thể Nhân Viên
 *
 * Implements Serializable để có thể ghi/đọc đối tượng ra File (IO Stream)
 * Nếu không implements Serializable -> sẽ bị lỗi NotSerializableException khi ghi file
 */
public class NhanVien implements Serializable {

    // ========================
    // THUỘC TÍNH (private)
    // ========================
    private int    id;        // Mã nhân viên
    private String ten;       // Tên nhân viên
    private double luongGio;  // Lương theo giờ

    // ========================
    // CONSTRUCTOR KHÔNG THAM SỐ (bắt buộc có theo yêu cầu đề thi)
    // ========================
    public NhanVien() {
    }

    // ========================
    // CONSTRUCTOR CÓ THAM SỐ
    // ========================
    public NhanVien(int id, String ten, double luongGio) {
        this.id       = id;
        this.ten      = ten;
        this.luongGio = luongGio;
    }

    // ========================
    // GETTER & SETTER (Property)
    // ========================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getLuongGio() {
        return luongGio;
    }

    public void setLuongGio(double luongGio) {
        this.luongGio = luongGio;
    }

    // ========================
    // PHƯƠNG THỨC inThongTin() - Yêu cầu đề thi
    // ========================
    public void inThongTin() {
        System.out.printf("  ID: %-5d | Ten: %-20s | Luong Gio: %.2f%n",
                id, ten, luongGio);
    }

    // ========================
    // toString() - Dùng để debug / log nhanh
    // ========================
    @Override
    public String toString() {
        return "NhanVien{id=" + id + ", ten='" + ten + "', luongGio=" + luongGio + "}";
    }
}
