/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connectsql;

import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class ConnectSQL {

    /**
     * @param args the command line arguments
     */
    // Tài khoản đăng nhập SQL Server
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "1433";
    private static final String DBNAME = "QLNHANSU";
    public static Connection getConnection(){
        // Kiểm tra kết nối
        try {
            String URL = "jdbc:sqlserver://"+HOSTNAME+":"+PORT+";databaseName="+DBNAME+";encrypt=true;trustServerCertificate=true";
            // Nạp driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Tạo kết nối
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Lỗi không tìm thấy driver JDBC
            System.out.println("Không tìm thấy Driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            // Lỗi liên quan tới database
            System.out.println("Kết nối CSDL thất bại!");
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        // TODO code application logic here
//        Connection conn = null;
//        // Kiểm tra kết nối
//        try {
//            String URL = "jdbc:sqlserver://"+HOSTNAME+":"+PORT+";databaseName="+DBNAME+";encrypt=true;trustServerCertificate=true";
//            // Nạp driver SQL Server
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//            // Tạo kết nối
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//
//            System.out.println("Kết nối CSDL thành công!");
//        } catch (ClassNotFoundException e) {
//            // Lỗi không tìm thấy driver JDBC
//            System.out.println("Không tìm thấy Driver JDBC!");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // Lỗi liên quan tới database
//            System.out.println("Kết nối CSDL thất bại!");
//            e.printStackTrace();
//        }


    }
    
}
