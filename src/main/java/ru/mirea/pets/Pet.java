package ru.mirea.pets;

public class Pet {
    public int id;
    public String species;
    public String sex;
    public String color;
    public double price;

    Pet(int id, String species, String sex, String color, double price) {
        this.id = id;
        this.species = species;
        this.sex = sex;
        this.color = color;
        this.price = price;
    }
}
