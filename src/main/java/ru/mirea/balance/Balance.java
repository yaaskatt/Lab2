package ru.mirea.balance;

public class Balance {
    public int id;
    public int userId;
    public double bal;

    public Balance(int id, int userId, int bal) {
        this.id = id;
        this.userId = userId;
        this.bal = bal;
    }
}
