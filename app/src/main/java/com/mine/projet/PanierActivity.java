package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mine.projet.customwidgets.HorizontalNumberPicker;
import com.mine.projet.models.Produit;

import java.util.ArrayList;


import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_URI;

public class PanierActivity extends AppCompatActivity {
    public static TextView panierCOUT;
    private static Context mContext;
    public static float prix;
    public static TextView  textaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        mContext = PanierActivity.this;
        imgUtils imageUrlUtils = new imgUtils();
        textaction = (TextView) findViewById(R.id.text_action_bottom1);
        ArrayList<Produit> panierProds =imageUrlUtils.getCartListProduit();
        prix=0;
        for(Produit p : panierProds){
            prix=prix+Float.parseFloat(p.prix);
        }

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
            public HorizontalNumberPicker np_channel_nr;

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
                np_channel_nr = pickerlinlay.findViewById(R.id.np_channel_nr);

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
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(maPanierProd.get(position).image);
            holder.mImageView.setImageURI(uri);
            holder.tvnom.setText(maPanierProd.get(position).nom);
            holder.tvprix.setText(maPanierProd.get(position).prix+" MAD");
            textaction.setText(String.valueOf(PanierActivity.prix)+" MAD");
            holder.np_channel_nr.setValue(1);
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

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUtils imageUrlUtils = new imgUtils();
                    System.out.println(position);

                    imageUrlUtils.removeCartListProduit(position);
                    notifyDataSetChanged();
                    PanierActivity.prix=PanierActivity.prix-imageUrlUtils.getCartListProduitPrix(position);
                    textaction.setText(PanierActivity.prix+" MAD");
                    //panierCOUT.setText("0 DH");
                    //Decrease notification count
                    Main2Activity.notificationCountCart--;

                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

        if(imgUtils.listPanierProds.size() >0){
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

}