/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lesson1;

/**
 *
 * @author ADMIN
 */
public class Lesson1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Person ps1 = new Person();
//        ps1.speak();
        Person p1 = new Person(); 
        p1.setName("Alice");
        p1.setAge(-5); // Sẽ in ra lỗi vì <= 0

        Person p2 = new Person("Bob", 20);
        System.out.println("Sinh viên: " + p2.getName() + ", Tuổi: " + p2.getAge());
    }
    
}
