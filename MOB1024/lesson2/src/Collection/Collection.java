/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
public class Collection {
      public static void main(String[] args) {
        // Mảng một tập hợp các phần tử có cùng kiểu dữ liệu, được lưu trữ liên tiếp trong bộ nhớ. 
        // Mỗi phần tử trong mảng được truy cập thông qua chỉ số (index), bắt đầu từ 0. 
        // Mảng có kích thước cố định, nghĩa là sau khi khởi tạo, bạn không thể thay đổi số lượng phần tử.
        // Khai báo mảng: kiểu_dữ_liệu[] tên_mảng;
        int[] numbers; // Khai báo mảng số nguyên
        numbers = new int[5]; // Mảng 5 phần tử, mặc định giá trị là 0
        numbers[0] = 1;
        numbers[1] = 2;
        System.out.println(numbers[0]); // In ra 1
        System.out.println(numbers[1]); // In ra 2
        // Mảng có kích thước cố định
        // Collection là tập hợp các đối tượng được lưu trữ và quản lý trong Java.
        //- Collection cho phép lưu trữ nhiều phần tử cùng kiểu dữ liệu.
        //- Collection giúp thao tác dữ liệu linh hoạt hơn mảng
        //Các nhánh chính:
        //List: có thứ tự, cho phép trùng lặp
        //Set: không cho phép trùng lặp
        //Queue: vào trước ra trước
        // Với bài này, trọng tâm của buổi học là: List, Set, Map
        // List là một loại Collection dùng để lưu trữ các phần tử theo thứ tự.
        //- Các phần tử trong List:
        //+ Có thể trùng lặp
        //+ Được sắp xếp theo thứ tự thêm vào
        //+ Có thể truy cập thông qua chỉ số (index)
        //- List rất phù hợp để quản lý danh sách dữ liệu trong chương trình Java.
        List<String> names = new ArrayList<>();
        names.add("An");
        names.add("Bình");
        names.add("Cường");
        names.add("An");
        System.out.println("Phần tử tại index 1: " + names.get(1)); 
        names.set(1, "Bình Nguyễn");
        System.out.println("Sau khi cập nhật: " + names);

        names.remove("An"); // xóa phần tử An đầu tiên
        System.out.println("Sau khi xóa: " + names);

        System.out.println("Có chứa Cường không? " + names.contains("Cường"));
        System.out.println("Danh sách:");
        for (String name : names) {
            System.out.println(name);
        }
        // Set là một loại Collection dùng để lưu trữ các phần tử không trùng lặp.
        // - Các phần tử trong Set:
        // không cho phép phần tử trùng lặp (xử lý tự động, không cần lập trình thêm)
        // không có index
        // phù hợp khi cần dữ liệu duy nhất
        Set<String> studentCodes = new HashSet<>();

        studentCodes.add("SV01");
        studentCodes.add("SV02");
        studentCodes.add("SV03");
        studentCodes.add("SV01"); // trùng

        System.out.println("Danh sách mã sinh viên:");
        for (String code : studentCodes) {
            System.out.println(code);
        }

        System.out.println("Có SV02 không? " + studentCodes.contains("SV02"));

        studentCodes.remove("SV03");
        System.out.println("Sau khi xóa SV03: " + studentCodes);
        // Demo quản lý sinh viên bằng List
        StudentManager manager = new StudentManager();

        manager.addStudent(new Student("SV01", "An", 8.5));
        manager.addStudent(new Student("SV02", "Bình", 7.0));
        manager.addStudent(new Student("SV03", "Cường", 9.0));

        System.out.println("=== Danh sách ban đầu ===");
        manager.displayAll();

        System.out.println("\n=== Tìm SV02 ===");
        System.out.println(manager.findById("SV02"));

        System.out.println("\n=== Cập nhật SV02 ===");
        manager.updateStudent("SV02", "Bình Nguyễn", 8.0);
        manager.displayAll();

        System.out.println("\n=== Xóa SV01 ===");
        manager.removeStudent("SV01");
        manager.displayAll();
        // Nói: List giống như dãy số thứ tự. Muốn lấy phần tử thì phải nhớ vị trí. 
        // Map thì giống cuốn từ điển: tra bằng khóa. 
        // Map là cấu trúc dữ liệu dùng để lưu trữ dữ liệu theo cặp key – value.
        // Ví dụ: 
            // SV01 -> Nguyễn Văn A
            // SV02 -> Trần Thị B
        // - Các phần tử trong Map:
        // Mỗi key là duy nhất
        // value có thể trùng
        // Truy xuất bằng key, không dùng index
        // HashMap là loại hay dùng nhất
        // HashMap cơ bản
        Map<String, String> students = new HashMap<>();
        students.put("SV01", "Nguyễn Văn A");
        students.put("SV02", "Trần Thị B");
        students.put("SV03", "Lê Văn C");
        System.out.println("Tên của SV02: " + students.get("SV02"));

        System.out.println("Danh sách toàn bộ:");
        for (Map.Entry<String, String> entry : students.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        students.put("SV02", "Trần Thị B Updated");
        System.out.println("Sau cập nhật SV02: " + students.get("SV02"));

        students.remove("SV03");
        System.out.println("Sau khi xóa SV03: " + students);

        System.out.println("Có key SV01 không? " + students.containsKey("SV01"));
        // Map với đối tượng
        Map<String, Student> studentMap = new HashMap<>();

        studentMap.put("SV01", new Student("SV01", "An", 8.5));
        studentMap.put("SV02", new Student("SV02", "Bình", 7.2));
        studentMap.put("SV03", new Student("SV03", "Cường", 9.1));

        Student s = studentMap.get("SV02");
        if (s != null) {
            System.out.println("Tìm thấy: " + s);
        }

        System.out.println("=== Toàn bộ sinh viên ===");
        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
