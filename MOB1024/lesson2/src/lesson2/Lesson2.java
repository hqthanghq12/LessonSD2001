/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lesson2;

/**
 *
 * @author ADMIN
 */

public class Lesson2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Lấy giá trị trong enum
        TrangThai trangThai = TrangThai.GIAO_HANG;
        System.out.println("Trang thai don hang: "+ trangThai);
        // lấy toán bộ danh sách: values()
        for (TrangThai tt : TrangThai.values()){
            System.out.println("Trang thai don hang: "+ tt);
        }
        DonHang dh = new DonHang(1, "Nguyen Văn A", trangThai);
        dh.thoiGianTrangThai();
        
    }
    
}
