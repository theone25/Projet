package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mine.projet.fragments.ImageListFragment;
import com.mine.projet.models.Produit;
import com.mine.projet.tinycart.Cart;
import com.mine.projet.tinycart.TinyCartHelper;

import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    int imagePosition;
    Produit stringImageUri;
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
                imgUtils imageUrlUtils = new imgUtils();
                imageUrlUtils.addCartListProduit(stringImageUri);
                Main2Activity.notificationCountCart++;

                // ajouter apres
                /*NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));*/

            }
        });

    }
}