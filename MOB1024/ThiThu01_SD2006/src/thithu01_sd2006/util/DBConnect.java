/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thithu01_sd2006.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author admin
 */
public class DBConnect {
    // Cấu hình kết nối
    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DBNAME = "QLSanPham";
    private static final String USER = "sa";
    private static final String PASS = "123";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://" + HOST + ":" + PORT
                    + ";databaseName=" + DBNAME
                    + ";encrypt=false";

            Connection conn = DriverManager.getConnection(url, USER, PASS);
            
            return conn;

        } catch (Exception e) {
            System.out.println("Ket noi that bai!");
            e.printStackTrace();
            return null;
        }
    }
}
