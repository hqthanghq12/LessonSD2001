/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package thithu01_sd2006;

import java.util.Scanner;
import thithu01_sd2006.service.SanPhamService;

/**
 *
 * @author admin
 */
public class Main {
    public static void main(String[] args) {
        SanPhamService sv = new SanPhamService();
        Scanner sc = new Scanner(System.in);
        
        while (true) {            
            System.out.println("====MENU====");
            System.out.println("1. Nhap + Ghi file");
            System.out.println("2. Doc file");
            System.out.println("3. Luu vao DB");
            System.out.println("4. Cap nhat gia");
            System.out.println("0. Thoat");
            
            System.out.print("Chon :");
            int chon = Integer.parseInt(sc.nextLine());
            
            switch (chon) {
                case 1:
                    sv.nhapGhiFile();
                    break;
                case 2:
                    sv.docFile();
                    break;
                case 3:
                    sv.luuDB();
                    break;
                case 4:
                    sv.updateGia();
                    break;
                case 0:
                    System.out.println("Thoat....");
                    break;
                default:
                    System.out.println("Moi ban chon lai");
            }
        }
    }
}
