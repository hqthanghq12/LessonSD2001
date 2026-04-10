/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author ADMIN
 */
public class DBConnect {
     private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QLNV_JAVA2;encrypt=true;trustServerCertificate=true";
    private final String USER = "sa";
    private final String PASS = "123";

    public Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
