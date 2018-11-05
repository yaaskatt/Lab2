package ru.mirea;

public class Inc {
    private static int id = 0;

    public static synchronized int inc() {
        return ++id;
    }
}
