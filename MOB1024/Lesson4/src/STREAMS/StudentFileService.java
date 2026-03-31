/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package STREAMS;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ADMIN
 */
public class StudentFileService {
     // Hàm ghi một đối tượng Student xuống file
    public void writeStudent(Student student, String fileName) {
        try {
            // Tạo luồng ghi object ra file
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));

            // Ghi object student xuống file
            oos.writeObject(student);

            // Đóng luồng
            oos.close();

            System.out.println("Ghi object Student thành công.");

        } catch (IOException e) {
            // Xử lý lỗi ghi file
            System.out.println("Lỗi khi ghi object: " + e.getMessage());
        }
    }
    // Hàm đọc một đối tượng Student từ file
    public Student readStudent(String fileName) {
        try {
            // Tạo luồng đọc object từ file
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));

            // Đọc object và ép kiểu về Student
            Student student = (Student) ois.readObject();

            // Đóng luồng
            ois.close();

            // Trả về đối tượng đọc được
            return student;

        } catch (IOException e) {
            // Xử lý lỗi file
            System.out.println("Lỗi khi đọc object: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Xử lý lỗi không tìm thấy class khi deserialize
            System.out.println("Lỗi không tìm thấy class: " + e.getMessage());
        }

        // Nếu lỗi thì trả về null
        return null;
    }
}
