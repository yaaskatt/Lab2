package ru.mirea;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect_db {
    private static Connection con = null;


    public static Connection getConnection() {
        if (con == null) con = getNewConnection();
        return con;
    }

    private static Connection getNewConnection() {
        String user = "root";
        String password = "root";
        String db = "lab2";
        String url = "jdbc:mysql://localhost:3306/" + db +
                "?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            con=DriverManager.getConnection(url, user, password);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
