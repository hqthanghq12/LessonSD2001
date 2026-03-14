
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
public class Lambda {
    // Trước Java 8, Java thiên về OOP.
    // Lambda cho phép truyền hành vi như tham số.
    //Lợi ích:
    //- code ngắn hơn
    //- dễ đọc hơn nếu dùng đúng
    //- hỗ trợ xử lý song song tốt hơn
    public static void main(String[] args) {
        // So sánh mẫu
        List<String> names = new ArrayList<>(Arrays.asList("Nam", "An", "Binh", "Cuong"));

        // Cách cũ: Anonymous Inner Class
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        System.out.println("Sau khi sort theo cách cũ: " + names);

        // Trộn lại dữ liệu
        names = new ArrayList<>(Arrays.asList("Nam", "An", "Binh", "Cuong"));

        // Cách mới: Lambda
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));

        System.out.println("Sau khi sort bằng lambda: " + names);
        // Cú pháp : (tham_số) -> { thân_hàm }
        // Lưu ý: 
        // - 1 tham số có thể bỏ ngoặc đơn
        // 1 dòng lệnh có thể bỏ ngoặc nhọn và return
        // 1 tham số, 1 biểu thức
        Function<String, Integer> f1 = s -> s.length(); 

        // nhiều tham số
        BinaryOperator<Integer> add = (a, b) -> a + b;

        // nhiều dòng lệnh
        Function<Integer, Integer> square = n -> {
            int result = n * n;
            return result;
        };

        System.out.println(f1.apply("Java"));
        System.out.println(add.apply(3, 5));
        System.out.println(square.apply(4));
    }
}
