/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson4;

/**
 *
 * @author ADMIN
 */
public class ThrowDemo {
     // Hàm kiểm tra tuổi
    public static void checkAge(int age) {
        // Nếu tuổi nhỏ hơn 18 thì chủ động ném ra ngoại lệ
        if (age < 18) {
            throw new IllegalArgumentException("Tuổi phải >= 18");
        }

        // Nếu không lỗi thì in ra thông báo hợp lệ
        System.out.println("Tuổi hợp lệ.");
    }

    public static void main(String[] args) {
        try {
            // Gọi hàm kiểm tra tuổi
            checkAge(16);
        } catch (IllegalArgumentException e) {
            // Bắt lỗi được ném từ hàm checkAge
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
