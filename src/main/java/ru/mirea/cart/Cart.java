package ru.mirea.cart;

public class Cart {
    public int id;
    public int userId;
    public int itemId;

    public Cart(int id, int userId, int itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }
}
