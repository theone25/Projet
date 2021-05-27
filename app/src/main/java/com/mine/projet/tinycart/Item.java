package com.mine.projet.tinycart;

import com.mine.projet.models.Produit;

import java.math.BigDecimal;


public interface Item {
    BigDecimal getItemPrice();
    Produit getProduit();
    String getItemName();
}