package com.mine.projet.tinycart;

public class TinyCartHelper {
    private static Cart cart = new Cart();

    //Usage: Retrieves the cart anywhere by calling the method. Make sure you retrieve before performing any cart operations
    public static Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }
}