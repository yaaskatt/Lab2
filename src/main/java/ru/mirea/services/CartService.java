package ru.mirea.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Component
public class CartService {


    private BalanceService balanceService;
    private Connect_db connect_db;

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init() {
        con = connect_db.getConnection();
        try {
            stmt = con.createStatement();
        } catch (Exception e) {e.printStackTrace();};
        }

    public List cart(int userId) {
        String q = "SELECT * FROM services WHERE userId = " + userId;
        return get(q);
    }

    public List getStuff(int userId) {
        String q = "SELECT services.* FROM services INNER JOIN stuff ON services.itemId = stuff.id WHERE services.userId = " + userId;
        return get(q);
    }

    public List getPets(int userId) {
        String q = "SELECT services.* FROM services INNER JOIN pets ON " +
                "services.itemId = pets.id WHERE services.userId = " + userId;
        return get(q);
    }

    public void put(int userId, int itemId) {
        boolean n = true;
        String check = "SELECT * FROM pets WHERE id = " + itemId + " UNION SELECT * FROM stuff WHERE id = " + itemId;
        try {
            rs = stmt.executeQuery(check);
        } catch (Exception e) {
        };

        try {
        n = rs.next();
        } catch (Exception e) {};
        if (n == false) throw new NullPointerException("No item found");
        String q = "INSERT INTO services(userId, itemId) VALUES(" + userId + "," + itemId + ")";
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {};

    }

    public void delete(int userId, int itemId) {
        String q = "DELETE FROM services WHERE userId = " + userId + " AND itemId = " + itemId;
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {
        };
    }

    private List get(String q) {
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

    public void post(int userId) {
        int petSum = 0;
        int stuffSum = 0;
        int totalPrice = 0;
        int bal = 0;
        String q = "SELECT SUM(pets.price) FROM pets INNER JOIN services ON services.itemId = pets.id WHERE services.userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            rs.next();
            petSum = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        };
        q = "SELECT SUM(stuff.price) FROM stuff INNER JOIN services ON services.itemId = stuff.id WHERE services.userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            rs.next();
            stuffSum = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        };
        totalPrice = petSum + stuffSum;
        q = "SELECT controllers FROM controllers WHERE userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            rs.next();
            bal = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        };
        if (bal >= totalPrice) {
            balanceService.putNewBal(userId, bal - totalPrice);
        }
        else throw new UnsupportedOperationException("Not enough controllers");
        q = "DELETE FROM services WHERE userId = " + userId;
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {};
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Autowired
    public void setConnect_db (Connect_db connect_db) {
        this.connect_db = connect_db;
    }
}
