package com.mine.projet.models;

import com.mine.projet.tinycart.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Produit implements Item,Serializable {
    public int id;
    public int categorie;
    public String nom;
    public String prix;
    public String details;
    public String image;

    public Produit(int id, int categorie, String nom, String prix, String details, String image) {
        this.id=id;
        this.categorie=categorie;
        this.nom=nom;
        this.prix=prix;
        this.details=details;
        this.image=image;
    }


    public static ArrayList<Produit> fromJson(JSONArray array) throws JSONException {
        ArrayList<Produit> res = new ArrayList<Produit>();
        for(int i=0; i < array.length(); i++) {
            JSONObject respObj = array.getJSONObject(i);
            String nom = respObj.getString("nom");
            int id = Integer.parseInt(respObj.getString("id"));
            int categorie = Integer.parseInt(respObj.getString("categorie"));
            String details = respObj.getString("details");
            String prix = respObj.getString("prix");
            String image = respObj.getString("image");
            res.add(new Produit(id,categorie,nom,prix,details,image));
        }

        return res;
    }

    @Override
    public BigDecimal getItemPrice() {
        return BigDecimal.valueOf(Long.parseLong(this.prix));
    }

    @Override
    public Produit getProduit() {
        return this;
    }

    @Override
    public String getItemName() {
        return this.nom;
    }
}
