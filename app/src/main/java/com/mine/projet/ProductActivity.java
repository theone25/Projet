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

public class ProductActivity extends AppCompatActivity {

    int imagePosition;
    String stringImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        SimpleDraweeView mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);

        //Getting image uri from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI,0);
        }
        Uri uri = Uri.parse(stringImageUri);
        mImageView.setImageURI(uri);
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
                imageUrlUtils.addCartListImageUri(stringImageUri);
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
                imageUrlUtils.addCartListImageUri(stringImageUri);
                Main2Activity.notificationCountCart++;

                // ajouter apres
                /*NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));*/

            }
        });

    }
}