package ru.mirea.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;

@Component
public class BalanceService {

    private Connect_db connect_db;

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init() {

        con = Connect_db.getConnection();

        try {
            stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE balance(" +
                    "userId INT(10) PRIMARY KEY, balance INT(10)" +
                    ")");
            stmt.executeUpdate("INSERT INTO balance VALUES (1, 1000), (2, 4000), (3, 500), (4, 0), (5, 12000)");
        } catch (Exception e) {e.printStackTrace();};
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

    public void putNewBal(int userId, double newBal) {
        String q = "UPDATE balance SET balance = " + newBal + " WHERE userId = " + userId;
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("User not found");
        };
    }

    @Autowired
    public void setConnect_db (Connect_db connect_db) {
        this.connect_db = connect_db;
    }
}

