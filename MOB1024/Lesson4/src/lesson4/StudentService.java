/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson4;

/**
 *
 * @author ADMIN
 */
public class StudentService {
    // Hàm kiểm tra GPA có hợp lệ hay không
    public static void validateGPA(double gpa) throws InvalidGPAException {
        // GPA hợp lệ trong khoảng 0 đến 4.0
        if (gpa < 0 || gpa > 4.0) {
            // Chủ động ném custom exception
            throw new InvalidGPAException("GPA phải nằm trong khoảng từ 0 đến 4.0");
        }
    }
}
