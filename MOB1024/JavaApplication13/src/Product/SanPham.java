/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Product;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class SanPham implements Serializable {
     private int id;
    private String ten;
    private int soLuong;
    private double giaBan;

    public SanPham() {
    }

    public SanPham(int id, String ten, int soLuong, double giaBan) {
        this.id = id;
        this.ten = ten;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public void inThongTin() {
        System.out.printf("%-5d | %-25s | %-10d | %-12.2f%n", id, ten, soLuong, giaBan);
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", soLuong=" + soLuong +
                ", giaBan=" + giaBan +
                '}';
    }
}
