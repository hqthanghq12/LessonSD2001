/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package views;
import java.util.Scanner;
import service.NhanVienServices;
/**
 *
 * @author ADMIN
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NhanVienServices sv = new NhanVienServices();
        int chon;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Nhap danh sach doi tuong va ghi file");
            System.out.println("2. Doc danh sach tu file va xuat ra man hinh");
            System.out.println("3. Ket noi JDBC va day toan bo danh sach tu file len DB");
            System.out.println("4. Them moi doi tuong vao DB");
            System.out.println("5. Update luong gio = 1500 trong DB");
            System.out.println("0. Thoat");
            System.out.print("Moi chon: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    sv.nhapVaGhiFile();
                    break;
                case 2:
                    sv.docFileVaXuat();
                    break;
                case 3:
                    sv.dayFileLenDB();
                    break;
                case 4:
                    sv.themMoiDB();
                    break;
                case 5:
                    sv.updateLuong1500();
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
