package ru.mirea.pets;

import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;
import ru.mirea.Inc;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PetService {

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init(){
        con = Connect_db.getConnection();

        String query = "CREATE TABLE pets (" +
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

    public List pets() {
        String q = "SELECT * FROM pets";
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
        //String q = "INSERT INTO pets(id, name, price) VALUES(" + Inc.inc() + " , \"" + name + " , " + price + ")";
        String sql="INSERT INTO pets (id , name , price) " +
                " VALUES (?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Inc.inc());
            pstmt.setString(2, name);
            pstmt.setInt(3,price);
            pstmt.execute();
        } catch (Exception e) {e.printStackTrace();};


    }

}
