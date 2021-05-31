package com.mine.projet.models;

import com.mine.projet.ListAdresseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adresse {
    public int id;
    public int userID;
    public String ville;
    public String rue;
    public String appt;
    public String zip;
    public String region;
    public String nom;
    public String tel;

    public Adresse(int id, int userID, String ville, String rue, String appt, String zip, String region, String nom, String tel) {
        this.id = id;
        this.userID = userID;
        this.ville = ville;
        this.rue = rue;
        this.appt = appt;
        this.zip = zip;
        this.region = region;
        this.nom = nom;
        this.tel = tel;
    }

    public Adresse() {
    }

    public static ArrayList<Adresse> fromJson(JSONArray array) throws JSONException {
        ArrayList<Adresse> res = new ArrayList<Adresse>();
        for(int i=0; i < array.length(); i++) {
            JSONObject respObj = array.getJSONObject(i);

             int id=respObj.getInt("id");
             int userID=respObj.getInt("userID");;
             String ville=respObj.getString("ville");
             String rue=respObj.getString("rue");
             String appt=respObj.getString("appt");
             String zip=respObj.getString("zip");
             String region=respObj.getString("region");
             String nom=respObj.getString("nom");
             String tel=respObj.getString("telephone");;
            res.add(new Adresse(id,userID,ville,rue,appt,zip,region,nom,tel));
        }
        return res;
    }

    public static ArrayList<String> tostr(ArrayList<Adresse> array) {
        ArrayList<String> adrrr=new ArrayList<>();
        for(Adresse ad : array){
            adrrr.add(ad.toSTring());
        }
        return adrrr;
    }

    public String toSTring(){
        return ville+" "+rue+" "+appt+"\n"+zip+" "+region+"\n"+nom+" "+tel;
    }
}
