package com.mine.projet.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Commande {
    public int id;
    public int userID;
    public int adressID;
    public int prodID;
    public int qte;
    public String dateAchat;

    public Commande() {
    }

    public Commande(int id, int userID, int adressID, int prodID, int qte, String dateAchat) {
        this.id = id;
        this.userID = userID;
        this.adressID = adressID;
        this.prodID = prodID;
        this.qte = qte;
        this.dateAchat = dateAchat;
    }


    public static ArrayList<Commande> fromJson(JSONArray array) throws JSONException {
        ArrayList<Commande> res = new ArrayList<Commande>();
        for(int i=0; i < array.length(); i++) {
            JSONObject respObj = array.getJSONObject(i);

            int id=respObj.getInt("id");
            int userID=respObj.getInt("userID");
            int adressID=respObj.getInt("adressID");
            int prodID=respObj.getInt("prodID");
            int qte=respObj.getInt("qte");
            String dateA=respObj.getString("dateAchat");

            res.add(new Commande(id,userID,adressID,prodID,qte,dateA));
        }
        return res;
    }


}
