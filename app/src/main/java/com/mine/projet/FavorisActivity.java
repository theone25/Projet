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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mine.projet.models.Produit;

import java.util.ArrayList;

import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_URI;

public class FavorisActivity extends AppCompatActivity {
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        mContext = FavorisActivity.this;

        imgUtils imageUrlUtils = new imgUtils();
        ArrayList<Produit> wishlistImageUri =imageUrlUtils.getWishlistProduit();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, wishlistImageUri));
    }
    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<FavorisActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Produit> mWishlistImageUri;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public TextView tvprix,tvnom,tvdetails;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_wishlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                tvprix=(TextView) mLayoutItem.findViewById(R.id.favprix);
                tvdetails=(TextView) mLayoutItem.findViewById(R.id.favdetails);
                tvnom=(TextView) mLayoutItem.findViewById(R.id.favnom);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Produit> wishlistImageUri) {
            mWishlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
        }

        @Override
        public FavorisActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fav_item, parent, false);
            return new FavorisActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final FavorisActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(mWishlistImageUri.get(position).image);
            holder.mImageView.setImageURI(uri);
            holder.tvnom.setText(mWishlistImageUri.get(position).nom);
            holder.tvprix.setText(mWishlistImageUri.get(position).prix+" MAD");
            holder.tvdetails.setText(mWishlistImageUri.get(position).details);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,mWishlistImageUri.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);
                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUtils imageUrlUtils = new imgUtils();
                    imageUrlUtils.removeWishlistProduit(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mWishlistImageUri.size();
        }
    }
}