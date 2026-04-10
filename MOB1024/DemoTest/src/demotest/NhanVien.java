/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demotest;

import java.io.*;

/**
 *
 * @author ADMIN
 */
public class NhanVien implements Serializable{
    // Thuộc tính private
    private int id;
    private String ten;
    private double luongGio;

    // Constructor không tham số
    public NhanVien() {
    }

    // Constructor có tham số
    public NhanVien(int id, String ten, double luongGio) {
        this.id = id;
        this.ten = ten;
        this.luongGio = luongGio;
    }

    // Getter và Setter
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

    // Phương thức in thông tin
    public void inThongTin() {
        System.out.println("ID: " + id + " | Ten: " + ten + " | Luong gio: " + luongGio);
    }
}
