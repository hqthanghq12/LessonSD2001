/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lesson2;

/**
 *
 * @author ADMIN
 */
public enum TrangThai {
// Enum là gì? 
// Enum (Enumeration) là một kiểu dữ liệu đặc biệt trong Java, dùng để biểu diễn một tập các giá trị cố định, có ý nghĩa.
// Các giá trị trong Enum được xác định ngay tại thời điểm biên dịch và không thể thay đổi trong quá trình chạy chương trình. 
// Enum giúp: 
//+ Tránh sử dụng các giá trị rời rạc (số, chuỗi khó kiểm soát) 
//+ Tăng tính rõ ràng và an toàn cho chương trình
//Khai báo Enum
//    NHAN_DON,
//    THUC_HIEN,
//    GIAO_HANG;
    NHAN_DON(5),
    THUC_HIEN(10),
    GIAO_HANG(10);
// Enum có thể được dùng:
//+ Là kiểu dữ liệu của thuộc tính
//+ Là tham số của phương thức
//+ Trong câu lệnh switch-case
    public int thoiGian;
    TrangThai (int thoiGian){
        this.thoiGian = thoiGian;
    }
}
