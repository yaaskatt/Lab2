package ru.mirea.balance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;

@Component
public class BalanceService {

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init() {

        con = Connect_db.getConnection();

        String query = "CREATE TABLE balance (" +
                "userId INT(10) PRIMARY KEY," +
                "balance INT(10))";

        try {
            stmt = con.createStatement();
        } catch (Exception e) {e.printStackTrace();};

        try {
            stmt.executeUpdate(query);
        } catch (Exception e) {e.printStackTrace();};

        put(1,1000);
        put(2,3000);
        put(3,805);
        put(4,0);
        put(5,500);
    }

    public List balance(int id) {
        String q = "SELECT * FROM balance WHERE userId = " + id;
        List result = null;
        rs = null;
        try {
            rs = stmt.executeQuery(q);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("User not found");
            };

        try {
            result = Convertion.resultSetToArrayList(rs);
        } catch (Exception e) {e.printStackTrace();};
        return result;
    }

    public void put(int userId, double bal) {
        String q = "INSERT INTO balance(userId, balance) VALUES(" + userId + "," + bal + ")";
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {
            update(userId, bal);
        };
    }

    public void update(int userId, double newBal) {
        String q = "UPDATE balance SET balance = " + newBal + " WHERE userId = " + userId;
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("User not found");
        };
    }
}

