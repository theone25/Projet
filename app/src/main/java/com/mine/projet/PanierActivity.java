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

import java.util.ArrayList;


import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_URI;

public class PanierActivity extends AppCompatActivity {
    public static TextView panierCOUT;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        mContext = PanierActivity.this;
        panierCOUT=findViewById(R.id.text_action_bottom1);
        imgUtils imageUrlUtils = new imgUtils();
        ArrayList<String> cartlistImageUri =imageUrlUtils.getCartListImageUri();
        //Show cart layout based on items
        setCartLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new PanierActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<PanierActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<String> mCartlistImageUri;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<String> wishlistImageUri) {
            mCartlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
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

            final Uri uri = Uri.parse(mCartlistImageUri.get(position));
            holder.mImageView.setImageURI(uri);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,mCartlistImageUri.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);
                }
            });

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUtils imageUrlUtils = new imgUtils();
                    imageUrlUtils.removeCartListImageUri(position);
                    notifyDataSetChanged();
                    panierCOUT.setText("0 DH");
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
            return mCartlistImageUri.size();
        }
    }

    protected void setCartLayout(){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if(Main2Activity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

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