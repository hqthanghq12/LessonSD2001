/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package STREAMS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
 // BufferedReader và BufferedWriter
        // đây là lớp bọc cho FileReader, FileWriter
        // dùng buffer để tăng hiệu suất
        // BufferedReader tiện vì hỗ trợ đọc từng dòng.
public class BufferedDemo {
     public static void main(String[] args) {
        try {
            // =========================
            // GHI DỮ LIỆU RA FILE
            // =========================

            // Tạo BufferedWriter để ghi dữ liệu hiệu quả hơn
            BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"));

            // Ghi từng dòng dữ liệu
            bw.write("SV01 - Nguyen Van A");
            bw.newLine(); // Xuống dòng

            bw.write("SV02 - Tran Thi B");
            bw.newLine();

            bw.write("SV03 - Le Van C");
            bw.newLine();

            // Đóng luồng ghi
            bw.close();

            System.out.println("Ghi file students.txt thành công.");

            // =========================
            // ĐỌC DỮ LIỆU TỪ FILE
            // =========================

            // Tạo BufferedReader để đọc từng dòng
            BufferedReader br = new BufferedReader(new FileReader("students.txt"));

            String line;

            // Đọc lần lượt từng dòng cho đến khi hết file
            while ((line = br.readLine()) != null) {
                System.out.println("Đọc được: " + line);
            }

            // Đóng luồng đọc
            br.close();

        } catch (IOException e) {
            // Xử lý lỗi thao tác file
            System.out.println("Lỗi khi thao tác file: " + e.getMessage());
        }
    }
}
