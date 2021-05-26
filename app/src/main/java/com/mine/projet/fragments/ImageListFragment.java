package com.mine.projet.fragments;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.facebook.drawee.view.SimpleDraweeView;

import com.mine.projet.Main2Activity;
import com.mine.projet.ProductActivity;
import com.mine.projet.R;
import com.mine.projet.db.VolleyCallBack;
import com.mine.projet.imgUtils;
import com.mine.projet.models.Produit;

import java.util.ArrayList;
import java.util.List;

public class ImageListFragment  extends Fragment {
    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static Main2Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (Main2Activity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_rv_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        String[] items=null;
        ArrayList<Produit> prods=null;
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            prods=imgUtils.prod1;
        }else if (ImageListFragment.this.getArguments().getInt("type") == 2){
            prods=imgUtils.prod2;
        }else if (ImageListFragment.this.getArguments().getInt("type") == 3){
            prods=imgUtils.prod3;
        }else if (ImageListFragment.this.getArguments().getInt("type") == 4){
            prods=imgUtils.prod4;
        }else if (ImageListFragment.this.getArguments().getInt("type") == 5){
            prods=imgUtils.prod5;
        }else {
            prods=imgUtils.prod6;
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, prods));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Produit> mValues;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView tvnom;
            public final TextView tvdetails;
            public final TextView tvprix;
            public int idP;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_fav);
                tvnom= (TextView) view.findViewById(R.id.tvname);
                tvdetails= (TextView) view.findViewById(R.id.tvdetails);
                tvprix= (TextView) view.findViewById(R.id.tvprix);
                idP=0;
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Produit> items) {
            mValues = items;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final Uri uri = Uri.parse(mValues.get(position).image);
            holder.mImageView.setImageURI(uri);
            holder.tvnom.setText(mValues.get(position).nom);
            holder.tvdetails.setText(mValues.get(position).details);
            holder.tvprix.setText(mValues.get(position).prix+ "  MAD" );
            holder.idP=mValues.get(position).id;
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ProductActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mValues.get(position));
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgUtils images = new imgUtils();
                    images.addWishlistProduit(holder.idP);
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                    notifyDataSetChanged();

                    Toast.makeText(mActivity,"Produit Ajouté.",Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            if(mValues!= null)
            return mValues.size();
            else    return 0;
        }
    }
}