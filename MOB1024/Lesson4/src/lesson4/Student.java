/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson4;

/**
 *
 * @author ADMIN
 */
public class Student {
     private String name;
    private double gpa;

    // Constructor khởi tạo đối tượng Student
    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    // Getter lấy tên sinh viên
    public String getName() {
        return name;
    }

    // Getter lấy GPA
    public double getGpa() {
        return gpa;
    }
}
