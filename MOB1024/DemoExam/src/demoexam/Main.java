package demoexam;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * MAIN - Điểm khởi đầu chương trình
 *
 * Hiển thị Menu dạng vòng lặp do-while + switch-case
 * Mỗi case gọi đúng 1 chức năng tương ứng trong class Services
 */
public class Main {

    public static void main(String[] args) {

        // Ép System.out dùng UTF-8 -> hiển thị tiếng Việt đúng trên console
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Ép Scanner đọc input theo UTF-8 -> nhận tiếng Việt từ bàn phím đúng
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        Services sv = new Services();   // Tạo 1 đối tượng Services dùng cho mọi chức năng
        int chon;

        do {
            // ===== HIỆN MENU =====
            System.out.println("\n========================================");
            System.out.println("       QUAN LY NHAN VIEN - MENU         ");
            System.out.println("========================================");
            System.out.println("  1. Nhap danh sach va ghi xuong File   ");
            System.out.println("  2. Doc danh sach tu File va xuat man hinh");
            System.out.println("  3. Day danh sach tu File len Database  ");
            System.out.println("  4. Them moi nhan vien vao Database     ");
            System.out.println("  5. Cap nhat luong gio = 1500 trong DB  ");
            System.out.println("  0. Thoat                               ");
            System.out.println("========================================");
            System.out.print("  Moi chon (0-5): ");

            // Đọc lựa chọn - dùng try-catch để tránh crash khi nhập chữ
            try {
                chon = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Vui long nhap so tu 0 den 5!");
                chon = -1;   // Gán giá trị không hợp lệ -> vòng lặp tiếp tục
                continue;
            }

            // ===== XỬ LÝ LỰA CHỌN =====
            switch (chon) {
                case 1:
                    // Chức năng 1: Nhập và ghi thẳng xuống file
                    sv.nhapDanhSachVaGhiFile(sc);
                    break;

                case 2:
                    // Chức năng 2: Đọc file + sắp xếp Lambda + xuất màn hình
                    sv.docFileVaXuat(sc);
                    break;

                case 3:
                    // Chức năng 3: JDBC - đẩy toàn bộ danh sách từ file lên DB
                    sv.dayDanhSachTuFileLenDB();
                    break;

                case 4:
                    // Chức năng 4: Thêm mới 1 nhân viên vào DB (có check trùng)
                    sv.themMoiVaoDB(sc);
                    break;

                case 5:
                    // Chức năng 5: Update lương giờ = 1500, in ra số dòng thay đổi
                    sv.capNhatLuong1500();
                    break;

                case 0:
                    // Thoát chương trình
                    System.out.println(">> Tam biet! Thoat chuong trinh.");
                    break;

                default:
                    // Chọn số ngoài khoảng 0-5
                    System.out.println("[Loi] Chon sai chuc nang. Vui long chon tu 0 den 5.");
                    break;
            }

        } while (chon != 0);  // Lặp đến khi người dùng chọn 0

        sc.close(); // Đóng Scanner khi thoát
    }
}
