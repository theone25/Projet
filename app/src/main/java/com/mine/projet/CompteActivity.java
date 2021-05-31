package com.mine.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mine.projet.models.Adresse;
import com.mine.projet.models.User;

public class CompteActivity extends AppCompatActivity {
    TextView textViewviewallorder;
    TextView textViewviewadress;
    ImageButton editProfileBtn;
    TextView profile_name;
    TextView profile_email;
    TextView profile_tel;
    ListView adrList;
    LinearLayout linearlayoutmyddress;

    final int PROFILE_EDIT = 1;
    appPref appp;
    public static final String MY_PREFS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);


        appp = new appPref(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = gson.fromJson(json, User.class);
        LinearLayout lin=findViewById(R.id.linearlayoutaccountprofile);
        getSupportActionBar().setTitle("Mon Compte");

        profile_name = lin.findViewById(R.id.profile_name);
        profile_email = lin.findViewById(R.id.profile_email);
        profile_tel = lin.findViewById(R.id.profile_tel);
        linearlayoutmyddress=findViewById(R.id.linearlayoutmyddress);

        adrList=linearlayoutmyddress.findViewById(R.id.adrList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Adresse.tostr(ListAdresseActivity.adres));

        profile_name.setText(user.nom);
        profile_email.setText(user.email);
        profile_tel.setText(user.tel);
        adrList.setAdapter(adapter);
        textViewviewadress=findViewById(R.id.addanaddress);
        textViewviewadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CompteActivity.this, ListAdresseActivity.class);
                startActivity(in);
            }
        });

        textViewviewallorder = findViewById(R.id.ttsommandes);
        textViewviewallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CompteActivity.this, ListCommandesActivity.class);
                startActivity(in);
            }
        });

        editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, CompteEditActivity.class);
            startActivityForResult(i, PROFILE_EDIT);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Annulé", Toast.LENGTH_SHORT).show();

        } else if (resultCode == PROFILE_EDIT) {
            profile_name.setText(data.getStringExtra("name"));
            profile_email.setText(data.getStringExtra("username"));
            Toast.makeText(this, "Profile mis à jour", Toast.LENGTH_SHORT).show();
        }
    }
}