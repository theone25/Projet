package com.mine.projet;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mine.projet.db.VolleyCallBack;
import com.mine.projet.models.Produit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class imgUtils {
    static ArrayList<Produit> wishlistImageUri = new ArrayList<>();
    static ArrayList<Produit> cartListImageUri = new ArrayList<>();
    public static ArrayList<Produit> prod1;
    public static ArrayList<Produit> prod2;
    public static ArrayList<Produit> prod3;
    public static ArrayList<Produit> prod4;
    public static ArrayList<Produit> prod5;
    public static ArrayList<Produit> prod6;

    public static void getProducts1( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod1=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "1");
            return params;
        }
        };
        queue.add(strreq);

    }
    public static void getProducts2( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod2=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "2");
            return params;
        }
        };
        queue.add(strreq);

    }
    public static void getProducts3( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod3=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "3");
            return params;
        }
        };
        queue.add(strreq);

    }
    public static void getProducts4( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod4=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "4");
            return params;
        }
        };
        queue.add(strreq);

    }
    public static void getProducts5( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod5=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "5");
            return params;
        }
        };
        queue.add(strreq);

    }
    public static void getProducts6( Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/produits.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(Response);
                            prod6=Produit.fromJson(json);
                            System.out.println(Response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("categorie", "6");
            return params;
        }
        };
        queue.add(strreq);

    }


    // Methods for Wishlist
    public void addWishlistProduit(Produit wishlistImageUri,Context ctx) {
        ajouterFavoris(wishlistImageUri.id,ctx);

    }

    public void removeWishlistProduit(int position) {
        this.wishlistImageUri.remove(position);
    }

    public ArrayList<Produit> getWishlistProduit(){ return this.wishlistImageUri; }

    // Methods for Cart
    public void addCartListProduit(Produit wishlistImageUri) {
        this.cartListImageUri.add(0,wishlistImageUri);
    }

    public void removeCartListProduit(int position) {
        this.cartListImageUri.remove(position);
    }

    public ArrayList<Produit> getCartListProduit(){ return this.cartListImageUri; }

    public Float getCartListProduitPrix(int pos){ return Float.parseFloat(this.cartListImageUri.get(pos).prix); }
    public void ajouterFavoris(int prodID,Context ctx){
        appPref appp = new appPref(ctx);
        int userID=appp.pref.getInt("id",-1);
        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/favoris.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(json);

                            for(int i=0; i < json.length(); i++) {
                                JSONObject respObj = json.getJSONObject(i);
                                wishlistImageUri=Produit.fromJson(json);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("user", String.valueOf(userID));
            params.put("produit", String.valueOf(prodID));
            return params;
        }
        };
        queue.add(strreq);
    }
}
