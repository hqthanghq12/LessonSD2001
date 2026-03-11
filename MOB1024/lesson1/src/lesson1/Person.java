/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson1;

/**
 *
 * @author ADMIN
 */
public class Person {
//    Lớp và đối tượng
//    Thuộc tính
    private String name;
    private int age;
//    Phương thức
//    public void speak(){
//        System.out.println("Xin chao 123");
//    }
  // Hàm khởi tạo (Constructor) không tham số
    public Person() {}

    // Hàm khởi tạo có tham số, dùng từ khóa 'this'
    public Person(String name, int age) {
        this.name = name;
        this.age = age; // 'this.age' là thuộc tính, 'age' là tham số
    }
    // Getter lấy giá trị
    public String getName() { return name; }

    // Setter gán giá trị
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }

    // Setter có kiểm soát (validation)
    public void setAge(int age) {
        if (age > 0 && age <= 120) {
            this.age = age;
        } else {
            System.out.println("Tuổi không hợp lệ! Đừng đùa thầy!");
        }
    }

}
