
import java.util.*;
import java.util.function.*;


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
        // Sort bằng Anonymous Inner Class
        List<String> names = Arrays.asList("Lan", "An", "Binh");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        System.out.println(names);
        // Sort bằng Lambda
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
        System.out.println(names);
        // Cách cũ: anonymous inner class dài dòng
        // Cách mới: lambda chỉ giữ lại logic cốt lõi
        // Cú pháp Lambda
        // (tham_so) -> { than_ham }
        // Giải thích:
        //Bên trái ->: danh sách tham số
        //Bên phải: phần xử lý
        // Các trường hợp rút gọn:
        // 1 tham số có thể bỏ ()
        // 1 dòng lệnh có thể bỏ {} và return
        // Có ngoặc
        MathOperation add = (a, b) -> { return a + b; };
        // Rút gọn
        MathOperation sub = (a, b) -> a - b;
        // Một tham số
        Printer p = message -> System.out.println(message);
        System.out.println(add.operate(5, 3)); // 8
        System.out.println(sub.operate(5, 3)); // 2
        p.print("Xin chao Lambda");
        // Functional Interface
        MyMath sum = (a, b) -> a + b;
        MyMath multiply = (a, b) -> a * b;
        System.out.println(sum.calculate(4, 5));      // 9
        System.out.println(multiply.calculate(4, 5)); // 20
        // interface phổ biến
        // Predicate<T> – kiểm tra điều kiện, trả về boolean
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println(isEven.test(4)); // true
        System.out.println(isEven.test(7)); // false
        // Consumer<T> – nhận dữ liệu, xử lý, không trả về
        Consumer<String> printer = s -> System.out.println("Hello " + s);
        printer.accept("Java");
        // Function<T, R> – nhận kiểu T, trả về kiểu R
        Function<String, Integer> lengthFunc = s -> s.length();
        System.out.println(lengthFunc.apply("Lambda")); // 6
        // Supplier<T> – không nhận tham số, trả về dữ liệu
        Supplier<Double> randomizer = () -> Math.random();
        System.out.println(randomizer.get());
        // Method Reference: Nếu lambda chỉ gọi lại một method có sẵn, ta có thể viết gọn bằng ::.
        names.forEach(System.out::println);
        names.stream()
             .map(String::toUpperCase)
             .forEach(System.out::println);
        // Effectively Final: 
        // Dùng được biến ngoài lambda
        // Nhưng biến đó không được thay đổi sau khi gán
        // Gọi là final hoặc effectively final
        int factor = 2;
        Function<Integer, Integer> doubler = n -> n * factor;
        System.out.println(doubler.apply(5)); // 10
        // factor = 3; // lỗi nếu thay đổi sau khi lambda dùng nó
    }
    interface MathOperation {
    int operate(int a, int b);
    }
    interface Printer {
        void print(String message);
    }
    // Functional Interface
    // Functional Interface là interface chỉ có 1 abstract method. 
    //Lambda cần “biết” nó đang cài đặt method nào. Nếu interface có 2 abstract method thì Java không biết lambda này ứng với method nào.
    @FunctionalInterface
    interface MyMath {
        int calculate(int a, int b);
    }
}

