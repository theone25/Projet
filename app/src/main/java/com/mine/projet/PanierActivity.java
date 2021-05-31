package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.mine.projet.models.Commande;
import com.mine.projet.models.Produit;
import com.mine.projet.models.User;
import com.mine.projet.tinycart.Cart;
import com.mine.projet.tinycart.TinyCartHelper;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_URI;

public class PanierActivity extends AppCompatActivity {
    public static TextView panierCOUT;
    private static Context mContext;
    public static float prix;
    public static final String MY_PREFS = "SharedPreferences";
    User user;
    public static TextView  textaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        mContext = PanierActivity.this;
        imgUtils imageUrlUtils = new imgUtils();
        textaction = (TextView) findViewById(R.id.text_action_bottom1);
        getSupportActionBar().setTitle("Mon Panier");
        //ArrayList<Produit> panierProds = imageUrlUtils.getCartListProduit();
        Cart cart = TinyCartHelper.getCart();
        ArrayList<Produit> panierProds = cart.getAllItems();
        prix=0;
        for(Produit p : panierProds){
            prix=prix+Float.parseFloat(p.prix);
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        user = gson.fromJson(json, User.class);


        setPanierLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new PanierActivity.SimpleStringRecyclerViewAdapter(recyclerView, panierProds));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Produit> maPanierProd;
        private RecyclerView monRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;
            public TextView tvprix,tvnom,tvdetails;
            public LinearLayout pickerlinlay;
            public NumberPicker np_channel_nr;


            public ViewHolder(View view) {
                super(view);
                mView = view;

                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);

                tvprix=(TextView) mLayoutItem.findViewById(R.id.panprix);
                tvdetails=(TextView) mLayoutItem.findViewById(R.id.pandetails);
                tvnom=(TextView) mLayoutItem.findViewById(R.id.pannom);
                pickerlinlay=(LinearLayout) mLayoutItem.findViewById(R.id.pickerlinearlayout);
                np_channel_nr = (NumberPicker) pickerlinlay.findViewById(R.id.number_picker);
                np_channel_nr.setMin(0);


            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Produit> wishlistImageUri) {
            maPanierProd = wishlistImageUri;
            monRecyclerView = recyclerView;
        }

        @Override
        public PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_panier_item, parent, false);
            return new PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
            }
        }

        @Override
        public void onBindViewHolder(final PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(maPanierProd.get(position).image);
            holder.mImageView.setImageURI(uri);
            holder.tvnom.setText(maPanierProd.get(position).nom);
            holder.tvprix.setText(maPanierProd.get(position).prix+" MAD");
            Cart cart = TinyCartHelper.getCart();
            textaction.setText(cart.getTotalPrice()+" MAD");
            holder.np_channel_nr.setValue(cart.getItemQty(maPanierProd.get(position)));
            holder.tvdetails.setText(maPanierProd.get(position).details);

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,maPanierProd.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);
                }
            });

            //supp click
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUtils imageUrlUtils = new imgUtils();
                    System.out.println(position);

                    imageUrlUtils.removeCartListProduit(position);
                    notifyDataSetChanged();
                    if(maPanierProd.size()>0){
                        PanierActivity.prix=PanierActivity.prix-imageUrlUtils.getCartListProduitPrix(position);
                        textaction.setText(PanierActivity.prix+" MAD");
                    }
                    //panierCOUT.setText("0 DH");
                    //Decrease notification count
                    Main2Activity.notificationCountCart--;

                }
            });

            //edition click
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("----val>"+holder.np_channel_nr.getValue());
                    if(holder.np_channel_nr.getValue()==0){
                        cart.removeItem(maPanierProd.get(position));
                        textaction.setText(cart.getTotalPrice()+" MAD");

                    }else{
                        cart.updateItem(maPanierProd.get(position),holder.np_channel_nr.getValue());
                        textaction.setText(cart.getTotalPrice()+" MAD");
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return maPanierProd.size();
        }
    }



    protected void setPanierLayout(){
        LinearLayout layoutpanierItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutpanierPaie = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutpanierNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        TextView textaction2 = (TextView) layoutpanierPaie.findViewById(R.id.text_action_bottom2);
        textaction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListAdresseActivity.adres.size()==0 || ListAdresseActivity.adres==null){
                    Toast.makeText(PanierActivity.this, "Vous dever ajouter une Adresse", Toast.LENGTH_SHORT).show();
                    Intent log =new Intent(PanierActivity.this, ListAdresseActivity.class);
                    startActivity(log);
                    finish();
                }
                else{
                    saveCom();
                }

            }
        });
        Cart cart = TinyCartHelper.getCart();
        ArrayList<Produit> panierProds = cart.getAllItems();
        if(panierProds.size() >0){
            layoutpanierNoItems.setVisibility(View.GONE);
            layoutpanierItems.setVisibility(View.VISIBLE);
            layoutpanierPaie.setVisibility(View.VISIBLE);
        }else {
            layoutpanierNoItems.setVisibility(View.VISIBLE);
            layoutpanierItems.setVisibility(View.GONE);
            layoutpanierPaie.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public void saveCom() {
        Cart cart = TinyCartHelper.getCart();
        RequestQueue queue = Volley.newRequestQueue(PanierActivity.this);
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
                            ProductActivity.mesComs=Commande.fromJson(json);

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
                JSONArray mycoms = new JSONArray();
                ArrayList<Commande> coms=new ArrayList<>();
                for(Produit p : cart.getAllItems()){
                    coms.add(new Commande(0,user.id,ListAdresseActivity.adres.get(0).id,p.id,cart.getItemQty(p),""));
                }
                for(int i=0; i < coms.size(); i++) {
                    mycoms.put(coms.get(i));   // create array and add items into that
                }
                //send json array of commands
                params.put("prods", String.valueOf(mycoms));
                System.out.println(mycoms);
                return params;
            }
        };
        queue.add(strreq);
    }

}