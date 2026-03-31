/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package STREAMS;

import java.io.Serializable;

// Lớp Student muốn ghi xuống file thì phải implements Serializable
public class Student implements Serializable{
    
    // serialVersionUID giúp kiểm soát phiên bản của lớp khi serialize
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double gpa;

    // Constructor khởi tạo đối tượng sinh viên
    public Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    // Override toString để in object đẹp hơn
    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "', gpa=" + gpa + "}";
    }
}
