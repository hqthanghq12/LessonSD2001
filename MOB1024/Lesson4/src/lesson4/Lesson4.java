/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lesson4;

/**
 *
 * @author ADMIN
 */
public class Lesson4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Exception là lỗi xảy ra lúc chạy chương trình, không phải lỗi cú pháp. 
        // ví dụ:
        // - chia cho 0
        // - truy cập phần tử mảng vượt chỉ số
        // - đọc file không tồn tại
        // - dữ liệu người dùng nhập không hợp lệ
        // Ví dụ thực tế
        // ATM đang rút tiền nhưng tài khoản không đủ tiền
        // Form đăng ký nhập tuổi âm
        // Chương trình đọc file nhưng file bị xóa
        // Phân loại Exception
        // Exception thành 2 loại chính: Checked Exception và Unchecked Exception.
        // Checked Exception
        // Kiểm tra ở thời điểm biên dịch
        // Bắt buộc phải xử lý bằng try-catch hoặc throws
        // Thường liên quan file, database, network
        // Ví dụ: IOException, SQLException
        // Unchecked Exception
        // - Xảy ra khi runtime
        // - Không bắt buộc xử lý nhưng nên xử lý
        // - Thường do lỗi logic
        // - Ví dụ: ArithmeticException, NullPointerException, ArrayIndexOutOfBoundsException
        // Cách nói dễ hiểu
        // Checked: Java “nhắc trước”, bắt buộc xử lý
        // Unchecked: Java không chặn lúc compile, nhưng chạy có thể nổ\
        // Xử lý ngoại lệ với try-catch-finally
        // Cú pháp
        /*
        try {
            // Code có thể gây lỗi
        } catch (ExceptionType e) {
            // Code xử lý lỗi
        } finally {
            // Luôn chạy, thường dùng để đóng tài nguyên
        }
        Ý nghĩa
        try: chứa đoạn code nghi ngờ có lỗi
        catch: bắt lỗi để chương trình không bị dừng đột ngột
        finally: dọn dẹp tài nguyên, gần như luôn chạy
        */
        // Code demo 1: 
         try {
            // Khai báo 2 biến số nguyên
            int a = 10;
            int b = 0;

            // Thực hiện phép chia cho 0 -> gây ArithmeticException
            int result = a / b;

            // Dòng này sẽ không được chạy nếu exception xảy ra
            System.out.println("Kết quả: " + result);

        } catch (ArithmeticException e) {
            // Bắt lỗi chia cho 0
            System.out.println("Lỗi: Không thể chia cho 0.");
            System.out.println("Chi tiết lỗi: " + e.getMessage());
        } finally {
            // Khối này luôn chạy, dù có lỗi hay không
            System.out.println("Kết thúc chương trình.");
        }
         // Code demo 2 - Bắt nhiều loại exception
          try {
            // Tạo mảng số nguyên
            int[] numbers = {10, 20, 0};

            // Truy cập phần tử vượt ngoài phạm vi mảng -> có thể gây ArrayIndexOutOfBoundsException
            System.out.println("Phần tử tại index 5: " + numbers[5]);

            // Phép chia cho 0 -> có thể gây ArithmeticException
            int result = numbers[0] / numbers[2];
            System.out.println("Kết quả chia: " + result);

        } catch (ArrayIndexOutOfBoundsException e) {
            // Bắt lỗi truy cập sai chỉ số mảng
            System.out.println("Lỗi: Vượt quá chỉ số mảng.");
        } catch (ArithmeticException e) {
            // Bắt lỗi chia cho 0
            System.out.println("Lỗi: Không thể chia cho 0.");
        } catch (Exception e) {
            // Bắt các lỗi còn lại
            System.out.println("Lỗi khác: " + e.getMessage());
        } finally {
            // Luôn thực hiện
            System.out.println("Đã xử lý xong.");
        }
          // throw và throws
          /*
          ĐN:
          throw dùng để chủ động phát sinh ngoại lệ
          throws dùng để khai báo phương thức có thể phát sinh ngoại lệ và đẩy ra ngoài nơi gọi.
          Phân biệt nhanh
          - throw: ném 1 lỗi cụ thể
          - throws: khai báo có thể ném lỗi
          */
          // Custom Exception
          //custom exception là ngoại lệ do 
          //  lập trình viên tự định nghĩa để biểu diễn lỗi nghiệp vụ cụ thể, giúp code rõ nghĩa hơn so với exception chung chung
           try {
            // Tạo dữ liệu GPA không hợp lệ
            double gpa = 4.5;

            // Gọi hàm kiểm tra
            StudentService.validateGPA(gpa);

            // Nếu không có lỗi
            Student student = new Student("Nguyen Van A", gpa);
            System.out.println("Tạo sinh viên thành công: " + student.getName());

        } catch (InvalidGPAException e) {
            // Bắt và hiển thị lỗi nghiệp vụ
            System.out.println("Lỗi nghiệp vụ: " + e.getMessage());
        }
    }
    
}
