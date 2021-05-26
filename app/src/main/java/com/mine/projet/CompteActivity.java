package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CompteActivity extends AppCompatActivity {
    TextView textViewviewallorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        textViewviewallorder=findViewById(R.id.ttsommandes);
        textViewviewallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(CompteActivity.this,ListCommandesActivity.class);
                startActivity(in);
            }
        });
    }

}