package ru.mirea.stuff;

import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;
import ru.mirea.Inc;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class StuffService {

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init(){
        con = Connect_db.getConnection();

        String query = "CREATE TABLE stuff (" +
                "id INT(10) PRIMARY KEY," +
                "name VARCHAR(50)," +
                "price INT(10))";

        try {
            stmt = con.createStatement();
        } catch (Exception e) {e.printStackTrace();};

        try {
            stmt.executeUpdate(query);
        } catch (Exception e) {e.printStackTrace();};
    }

    public List stuff() {
        String q = "SELECT * FROM stuff";
        List result = null;
        try {
            rs = stmt.executeQuery(q);
        } catch (Exception e) {e.printStackTrace();};
        try {
            result = Convertion.resultSetToArrayList(rs);
        } catch (Exception e) {e.printStackTrace();};
        return result;
    }

    public void put(String name, int price) {
        String q = "INSERT INTO stuff VALUES(" + Inc.inc() + ", " + name + ", " + price + ")";
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {e.printStackTrace();};
    }
}
