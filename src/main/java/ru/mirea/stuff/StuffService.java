package ru.mirea.stuff;

import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Component
public class StuffService {

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init(){
        con = Connect_db.getConnection();
        try {
            stmt = con.createStatement();
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
}
