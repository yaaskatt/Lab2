package ru.mirea.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mirea.Connect_db;
import ru.mirea.Convertion;
import ru.mirea.Inc;
import ru.mirea.balance.BalanceService;
import ru.mirea.pets.Pet;
import ru.mirea.pets.PetService;
import ru.mirea.stuff.Stuff;
import ru.mirea.stuff.StuffService;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CartService {


    private BalanceService balanceService;

    private static Connection con = null;
    private static Statement stmt;
    private static ResultSet rs;

    @PostConstruct
    public void init() {
        con = Connect_db.getConnection();

        String query = "CREATE TABLE cart (" +
                "id INT(10) AUTO_INCREMENT PRIMARY KEY," +
                "userId INT(10)," +
                "balance INT(10))";

        try {
            stmt = con.createStatement();
        } catch (Exception e) {e.printStackTrace();};

        try {
            stmt.executeUpdate(query);
        } catch (Exception e) {e.printStackTrace();};

    }

    public List cart(int userId) {
        String q = "SELECT * FROM cart WHERE userId = " + userId;
        return get(q);
    }

    public List getStuff(int userId) {
        String q = "SELECT * FROM cart LEFT JOIN stuff ON cart.itemId = stuff.id WHERE cart.userId = " + userId;
        return get(q);
    }

    public List getPets(int userId) {
        String q = "SELECT * FROM cart LEFT JOIN pets ON " +
                "cart.itemId = pets.id WHERE cart.userId = " + userId;
        return get(q);
    }

    public void put(int userId, int itemId) {
        String check = "SELECT * FROM pets UNION SELECT * FROM stuff";
        try {
            rs = stmt.executeQuery(check);
        } catch (Exception e) {
        };
        if (rs == null) throw new NullPointerException("No item found");
        String q = "INSERT INTO cart(userId, itemId) VALUES(" + userId + "," + itemId + ")";
        try {
            stmt.executeUpdate(q);
        } catch (Exception e) {
        };
    }

    public void delete(int userId, int itemId) {
        String q = "DELETE FROM cart WHERE userId = " + userId + " AND itemId = " + itemId;
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
        String q = "SELECT SUM(pets.price) FROM pets RIGHT JOIN cart ON cart.itemId = pets.id WHERE cart.userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            petSum = rs.getInt("price");
        } catch (Exception e) {
            e.printStackTrace();
        };
        q = "SELECT SUM(stuff.price) FROM stuff RIGHT JOIN cart ON cart.itemId = stuff.id WHERE cart.userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            stuffSum = rs.getInt("price");
        } catch (Exception e) {
            e.printStackTrace();
        };
        totalPrice = petSum + stuffSum;
        q = "SELECT balance FROM balance WHERE userId = " + userId;
        try {
            rs = stmt.executeQuery(q);
            bal = rs.getInt("price");
        } catch (Exception e) {
            e.printStackTrace();
        };
        if (bal >= totalPrice) {
            balanceService.update(userId, bal - totalPrice);
        }
        else throw new UnsupportedOperationException("Not enough balance");
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }
}
