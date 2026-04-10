/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package demotest;
import java.util.Scanner;
/**
 *
 * @author ADMIN
 */
public class DemoTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Services service = new Services();
        int chon;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Nhap danh sach va ghi file");
            System.out.println("2. Doc file va hien thi");
            System.out.println("3. Luu danh sach vao DB");
            System.out.println("4. Them moi vao DB");
            System.out.println("5. Update luong gio = 1500");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    service.nhapGhiFile();
                    break;
                case 2:
                    service.docFile();
                    break;
                case 3:
                    service.luuDB();
                    break;
                case 4:
                    service.themDB();
                    break;
                case 5:
                    service.updateLuong();
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
    
}
