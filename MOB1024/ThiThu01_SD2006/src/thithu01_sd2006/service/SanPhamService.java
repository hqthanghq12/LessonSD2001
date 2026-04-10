/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thithu01_sd2006.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import thithu01_sd2006.model.SanPham;
import java.sql.*;
import thithu01_sd2006.util.DBConnect;

/**
 *
 * @author admin
 */
public class SanPhamService {
    private ArrayList<SanPham> list = new ArrayList<>();
    private final String FILE = "sanpham.dat";
    Scanner sc = new Scanner(System.in);
    
    // 1. Nhập + Ghi file
    public void nhapGhiFile () {
        list.clear();
        
        while (true) {            
            System.out.print("Nhap ID:");
            String id = sc.nextLine();
            
            System.out.print("Nhap ten:");
            String ten = sc.nextLine();
            
            System.out.print("Nhap gia:");
            double giaBan = Double.parseDouble(sc.nextLine());
            
            list.add(new SanPham(id, ten, giaBan));
            
            System.out.print("Nhap tiep (y/n):");
            if (!sc.nextLine().equalsIgnoreCase("y")) break;
        }
        
        // Ghi file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))){
            oos.writeObject(list);
            System.out.println("Ghi file thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 2. Đọc file + hiển thị + lambda sắp xếp
    public void docFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))){
            list = (ArrayList<SanPham>) ois.readObject();
            // Sắp xếp sản phẩm giảm dần theo giá bán
            list.sort((a, b) -> Double.compare(b.getGiaBan(), a.getGiaBan()));
            System.out.println("Danh sach san pham:");
            for (SanPham sanPham : list) {
                sanPham.inThongTin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 3. Lưu sản phẩm vào DB
    public void luuDB () {
        String sql = "INSERT INTO SanPham VALUES (?, ?, ?)";
        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
            ){
            int count = 0;
            for (SanPham sanPham : list) {
                ps.setString(1, sanPham.getId());
                ps.setString(2, sanPham.getTen());
                ps.setDouble(3, sanPham.getGiaBan());
                
                count += ps.executeUpdate();
            }
            System.out.println("Luu thanh cong " + count + " san pham");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Update sản phẩm
    public void updateGia() {
        String sql = "UPDATE SanPham SET giaBan = 1500";
        try (
                Connection conn = DBConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            int rows = ps.executeUpdate();
            
            System.out.println("Luu cap nhat " + rows + " san pham");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
