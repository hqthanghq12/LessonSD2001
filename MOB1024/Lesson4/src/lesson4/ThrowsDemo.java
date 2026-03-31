/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson4;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author ADMIN
 */
public class ThrowsDemo {
      // Khai báo phương thức có thể phát sinh FileNotFoundException
    public static void readFile() throws FileNotFoundException {
        // Nếu file không tồn tại, Java sẽ ném FileNotFoundException
        FileInputStream fis = new FileInputStream("data.txt");

        // Nếu mở được file thì in thông báo
        System.out.println("Mở file thành công.");
    }

    public static void main(String[] args) {
        try {
            // Gọi hàm có throws
            readFile();
        } catch (FileNotFoundException e) {
            // Xử lý lỗi tại nơi gọi hàm
            System.out.println("Lỗi: Không tìm thấy file.");
        }
    }
}
