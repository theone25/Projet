package com.mine.projet;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mine.projet.models.Commande;
import com.mine.projet.models.Produit;

import java.util.ArrayList;

import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.mine.projet.fragments.ImageListFragment.STRING_IMAGE_URI;

public class CommandeActivity extends AppCompatActivity {
    Button orderbtn;
    RecyclerView recyclerVieworder;
    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        orderbtn=findViewById(R.id.macommandebtn);
        recyclerVieworder=findViewById(R.id.recyclerviewcmd);

        mContext = CommandeActivity.this;
        ArrayList<Commande> coms = ProductActivity.mesComs;
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new CommandeActivity.SimpleStringRecyclerViewAdapter(recyclerView, coms));


        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(CommandeActivity.this, Main2Activity.class);
                startActivity(in);
            }
        });

    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CommandeActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Commande> mfavliste;
        private RecyclerView monRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public TextView tvprix,tvnom,tvdetails,dateAchat,Qte;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_wishlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                tvprix=(TextView) mLayoutItem.findViewById(R.id.favprix);
                tvdetails=(TextView) mLayoutItem.findViewById(R.id.favdetails);
                tvnom=(TextView) mLayoutItem.findViewById(R.id.favnom);
                dateAchat=(TextView) mLayoutItem.findViewById(R.id.dateAchat);
                Qte=(TextView) mLayoutItem.findViewById(R.id.Qte);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Commande> nfavliste) {
            mfavliste = nfavliste;
            monRecyclerView = recyclerView;
        }

        @Override
        public CommandeActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comitem, parent, false);
            return new CommandeActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CommandeActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            for(Produit pp : ProductActivity.allprods){
                if(pp.id==mfavliste.get(position).prodID){
                    final Uri uri = Uri.parse(pp.image);
                    holder.mImageView.setImageURI(uri);
                    holder.tvnom.setText(pp.nom);
                    holder.tvprix.setText((Integer.parseInt(pp.prix)*mfavliste.get(position).qte)+" MAD");
                    holder.tvdetails.setText(pp.details);
                    holder.dateAchat.setText(mfavliste.get(position).dateAchat);
                    holder.Qte.setText(mfavliste.get(position).qte);
                }
            }

        }

        @Override
        public void onViewRecycled(CommandeActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }


        @Override
        public int getItemCount() {
            return mfavliste.size();
        }
    }
}