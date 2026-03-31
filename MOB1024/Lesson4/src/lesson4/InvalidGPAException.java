/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson4;

/**
 *
 * @author ADMIN
 */
// Tạo ngoại lệ tùy chỉnh dùng cho lỗi GPA không hợp lệ
public class InvalidGPAException extends Exception {
    // Constructor nhận message lỗi
    public InvalidGPAException(String message) {
        super(message);
    }
}
