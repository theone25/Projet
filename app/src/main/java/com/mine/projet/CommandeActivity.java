package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommandeActivity extends AppCompatActivity {
    Button orderbtn;
    RecyclerView recyclerVieworder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        orderbtn=findViewById(R.id.macommandebtn);
        recyclerVieworder=findViewById(R.id.recyclerviewcmd);
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(CommandeActivity.this, Main2Activity.class);
                startActivity(in);
            }
        });

    }
}