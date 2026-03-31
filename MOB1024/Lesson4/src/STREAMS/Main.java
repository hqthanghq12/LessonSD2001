/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package STREAMS;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public class Main {
    /*
    Input Stream dùng để đọc dữ liệu từ nguồn vào chương trình
    Output Stream dùng để ghi dữ liệu từ chương trình ra đích
    Nguồn/đích có thể là file, bàn phím, bộ nhớ, thiết bị ngoại vi.
    Ví dụ
    - Đọc file danh sách sinh viên
    - Ghi hóa đơn ra file
    - Lưu dữ liệu trước khi tắt chương trình
    Vai trò của I/O
    I/O giúp lưu trữ dữ liệu lâu dài, đọc lại khi cần, và là nền tảng cho file, database, network
    Nếu không có file:tắt chương trình là mất dữ liệu
    Nếu có file:lần sau mở lại vẫn còn
    Phân biệt byte stream và character stream
    Byte streams: InputStream, OutputStream
    Character streams: Reader, Writer
    Byte stream: phù hợp file nhị phân, ảnh, audio, object
    Character stream: phù hợp văn bản, chuỗi ký tự
    */
    // ĐỌC GHI FILE VĂN BẢN
     public static void main(String[] args) {
         // Ghi file bằng FileWriter
//        try {
//            // Tạo đối tượng FileWriter để ghi dữ liệu vào file output.txt
//            FileWriter writer = new FileWriter("output.txt"); // Nếu chưa có file sẽ tự tạo sau khi chạy
//
//            // Ghi chuỗi văn bản vào file
//            writer.write("Xin chao FPT Polytechnic!\n");
//            writer.write("Day la bai hoc ve Java I/O Streams.");
//
//            // Đóng luồng để giải phóng tài nguyên
//            writer.close();
//
//            // Thông báo ghi thành công
//            System.out.println("Ghi file thành công.");
//
//        } catch (IOException e) {
//            // Xử lý lỗi đọc/ghi file
//            System.out.println("Lỗi khi ghi file: " + e.getMessage());
//        }
        // Đọc file bằng FileReader
//        try {
//            // Tạo đối tượng FileReader để đọc dữ liệu từ file output.txt
//            FileReader reader = new FileReader("output.txt");
//
//            // Biến lưu từng ký tự đọc được
//            int ch;
//
//            // Đọc lần lượt từng ký tự cho đến khi gặp -1 (kết thúc file)
//            while ((ch = reader.read()) != -1) {
//                // Ép int sang char để hiển thị ký tự
//                System.out.print((char) ch);
//            }
//
//            // Đóng luồng sau khi đọc xong
//            reader.close();
//
//        } catch (IOException e) {
//            // Xử lý lỗi khi đọc file
//            System.out.println("Lỗi khi đọc file: " + e.getMessage());
//        }
       // Object Stream là cơ chế cho phép lưu trữ và khôi phục đối tượng Java thông qua file; dữ liệu lưu ở dạng nhị phân.
       // Khi nào dùng?
       // - Lưu danh sách sinh viên
       // - Lưu thông tin nhân viên
       // - Lưu trạng thái chương trình
       // để ghi object xuống file bằng object stream thì lớp phải implements Serializable; nếu không sẽ phát sinh lỗi.
         // Tạo service xử lý file
        StudentFileService service = new StudentFileService();

        // Tạo đối tượng Student
        Student s1 = new Student("SV01", "Nguyen Van A", 3.6);

        // Ghi object xuống file
        service.writeStudent(s1, "student.csv");

        // Đọc object từ file
        Student result = service.readStudent("student.dat");

        // Hiển thị object đọc được
        if (result != null) {
            System.out.println("Dữ liệu đọc từ file:");
            System.out.println(result);
        }
    }
    
}
