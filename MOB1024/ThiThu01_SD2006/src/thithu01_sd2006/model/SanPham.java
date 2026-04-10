/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thithu01_sd2006.model;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class SanPham implements Serializable{
    private String id;
    private String ten;
    private double giaBan;

    public SanPham() {
    }

    public SanPham(String id, String ten, double giaBan) {
        this.id = id;
        this.ten = ten;
        this.giaBan = giaBan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    
    // In ra thông tin
    public void inThongTin() {
        System.out.println(id + " - " + ten + " - " + giaBan);
    }
}
