/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Product;
import java.util.Scanner;
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
        Services service = new Services();
        int chon;

        do {
            System.out.println("\n============== MENU ==============");
            System.out.println("1. Nhap danh sach doi tuong va ghi thang xuong File");
            System.out.println("2. Doc danh sach doi tuong tu File va xuat ra man hinh");
            System.out.println("3. Ket noi JDBC va day toan bo danh sach vua doc tu File len CSDL");
            System.out.println("4. Them moi doi tuong vao DB thanh cong");
            System.out.println("5. Tim kiem san pham theo ten");
            System.out.println("6. Cap nhat doi tuong vao DB theo ID");
            System.out.println("7. Cap nhat gia ban < 1500 thanh 1500");
            System.out.println("8. Xoa doi tuong trong DB theo ID");
            System.out.println("0. Thoat");
            System.out.print("Moi ban chon: ");

            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    service.nhapDanhSachVaGhiFile(sc);
                    break;
                case 2:
                    service.docFileVaXuat(sc);
                    break;
                case 3:
                    service.dayDanhSachTuFileLenDB();
                    break;
                case 4:
                    service.themMoiVaoDB(sc);
                    break;
                case 5:
                    System.out.print("Nhap ten san pham can tim: ");
                    String ten = sc.nextLine();
                    service.timTheoTen(ten);
                    break;
                case 6:
                    service.capNhatTheoId(sc);
                    break;
                case 7:
                    service.capNhatGiaThanh1500();
                    break;
                case 8:
                    System.out.print("Nhap ID can xoa: ");
                    int id = Integer.parseInt(sc.nextLine());
                    service.xoaTheoId(id);
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }

        } while (chon != 0);

        sc.close();
    }
    
}
