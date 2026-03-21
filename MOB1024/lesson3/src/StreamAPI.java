/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
import java.util.*;
import java.util.stream.Stream;
public class StreamAPI {
    // Stream không phải nơi lưu dữ liệu. Stream là một luồng xử lý dữ liệu.
    // Collection = cái kho chứa hàng
    // Stream = dây chuyền xử lý hàng
    // So sánh Collection và Stream
    // Collection lưu trữ dữ liệu
    // Stream không lưu, chỉ xử lý
    // Collection eager
    // Stream lazy
    // Stream không làm đổi source ban đầu.
    // Cấu trúc pipeline
    // Công thức: source -> intermediate operations -> terminal operation
    public static void main(String[] args) {
        // Cách tạo Stream
        List<String> list = Arrays.asList("A", "B");
        Stream<String> s1 = list.stream();
        String[] arr = {"X", "Y"};
        Stream<String> s2 = Arrays.stream(arr);
        Stream<Integer> s3 = Stream.of(1, 2, 3);
        System.out.println("Da tao stream thanh cong");
        // Filter
        // Nhận vào Predicate<T>
        // Trả về stream mới chỉ gồm phần tử thỏa điều kiện
        List<Integer> numbers = Arrays.asList(5, 12, 8, 20, 7, 15);
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .filter(n -> n > 10)
               .forEach(System.out::println);
        // Map
        // map() dùng để biến đổi 1 phần tử này thành 1 phần tử khác
        // Input thường là Function<T, R>
        // Ví dụ: danh sách sinh viên -> danh sách tên sinh viên
        // Chuyển sang in hoa
         List<String> names = Arrays.asList("java", "python", "cpp");
        names.stream()
             .map(String::toUpperCase)
             .map(s -> "Language: " + s)
             .forEach(System.out::println);
        // Lấy tên sinh viên từ danh sách đối tượng
        List<Student> students = Arrays.asList(
            new Student("An", 8.5),
            new Student("Binh", 4.0),
            new Student("Chi", 7.0)
        );
        students.stream()
                .map(s -> s.name)
                .forEach(System.out::println);
        // Kết hợp Filter + Map thành pipeline
        students.stream()
                .filter(s -> s.score >= 5)
                .map(s -> s.name.toUpperCase())
                .forEach(System.out::println);
        // Lazy Evaluation + lỗi hay gặp
        Stream<String> s = list.stream()
                .filter(x -> {
                    System.out.println("Dang filter: " + x);
                    return true;
                });
        System.out.println("Bat dau...");
        s.forEach(x -> System.out.println("Output: " + x));
        // Giải thích:
        // Trước forEach(), phần filter() chưa thực sự chạy.
        // Khi gọi forEach(), stream mới chạy qua từng phần tử.
        // Stream dùng lại được không?
        // Stream chỉ dùng một lần
        // Dùng xong muốn dùng lại thì tạo stream mới từ source
    }
}
