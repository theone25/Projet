package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.mine.projet.fragments.ImageListFragment;
import com.mine.projet.models.Adresse;
import com.mine.projet.models.Commande;
import com.mine.projet.models.Produit;
import com.mine.projet.models.User;
import com.mine.projet.tinycart.Cart;
import com.mine.projet.tinycart.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    int imagePosition;
    Produit stringImageUri;
    public static final String MY_PREFS = "SharedPreferences";
    public static ArrayList<Commande> mesComs=new ArrayList<>();
    public static  ArrayList<Produit> allprods=new ArrayList<>();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        SimpleDraweeView mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        TextView tvprodnom = (TextView)findViewById(R.id.tvprodnom);
        TextView tvprodprix = (TextView)findViewById(R.id.tvprodprix);
        TextView tvproddetails = (TextView)findViewById(R.id.tvproddetails);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        user = gson.fromJson(json, User.class);

        if (getIntent() != null) {
            stringImageUri =(Produit) getIntent().getSerializableExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_POSITION,0);
        }
        Uri uri = Uri.parse(stringImageUri.image);
        mImageView.setImageURI(uri);
        tvprodnom.setText(stringImageUri.nom);
        tvprodprix.setText(stringImageUri.prix+" MAD");
        tvproddetails.setText(stringImageUri.details);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ProductActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", imagePosition);
                startActivity(intent);*/

            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUtils imageUrlUtils = new imgUtils();
                imageUrlUtils.addCartListProduit(stringImageUri);
                Cart cart = TinyCartHelper.getCart();
                    cart.addItem(stringImageUri,1);
                Toast.makeText(ProductActivity.this,"Ajouté au panier.",Toast.LENGTH_SHORT).show();
                Main2Activity.notificationCountCart++;
                // apres
                //NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commande comd=new Commande();
                comd.userID=user.id;
                comd.prodID=stringImageUri.id;
                comd.adressID=ListAdresseActivity.adres.get(0).id;
                comd.qte=1;
                saveCom(comd);
                // ajouter apres
                /*NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));*/

            }
        });

    }
    public void saveCom(Commande comm) {
        RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/order.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(json);
                            mesComs=Commande.fromJson(json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(comm.userID));
                params.put("qte", String.valueOf(comm.qte));
                params.put("prodID", String.valueOf(comm.prodID));
                params.put("adressID", String.valueOf(comm.adressID));
                return params;
            }
        };
        queue.add(strreq);
    }
}