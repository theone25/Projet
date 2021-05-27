package com.mine.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CompteActivity extends AppCompatActivity {
    TextView textViewviewallorder;
    ImageButton editProfileBtn;
    TextView profile_name;
    TextView profile_email;


    final int PROFILE_EDIT = 1;
    appPref appp;
    public static final String MY_PREFS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);


        appp = new appPref(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        int userID = prefs.getInt("id", -1);
        String userName = prefs.getString("username", "");
        String userPass = prefs.getString("password", "");
        String name = prefs.getString("name", "");
        System.out.println(userID);
        System.out.println(userName);
        System.out.println(userPass);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);


        profile_name.setText(name);
        profile_email.setText(userName);

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