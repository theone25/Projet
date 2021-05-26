package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListCommandesActivity extends AppCompatActivity {
    Button ttcomandesbtn;
    RecyclerView recyclerViewviewall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_commandes);
        recyclerViewviewall=findViewById(R.id.recyclerviewviewttcmd);
        ttcomandesbtn=(Button) findViewById(R.id.ttcomandesbtn);
        ttcomandesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ListCommandesActivity.this, Main2Activity.class);
                startActivity(in);
            }
        });
    }

}