package springboot.projects.OrderInterface.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://mydb-do-user-11291424-0.b.db.ondigitalocean.com:25060/test_shop?ssl-mode=REQUIRED";
    private static final String DB_USER = "doadmin";
    private static final String DB_PASSWORD = "AVNS_Qdbd9gjwtUyPcvJ";
    private static Connection conn;

    @SuppressWarnings("finally")
    public static Connection createDBConnection() {
        try {
            conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed Connection");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }
}
