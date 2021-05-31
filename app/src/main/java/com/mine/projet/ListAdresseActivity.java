package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mine.projet.models.Adresse;
import com.mine.projet.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdresseActivity extends AppCompatActivity {
    private EditText villeed, rueed, appted, ziped, reged, nomed, teled, teled2;
    private RadioGroup radadr;
    private RadioButton radm, radb;
    private TextView saveDetailsButton;
    Activity activity;
    public static final String MY_PREFS = "SharedPreferences";
    public static ArrayList<Adresse> adres=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adresse);
        activity = this;
        initViews();
        getSupportActionBar().setTitle("Addresse");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = gson.fromJson(json, User.class);
        saveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (villeed.getText().toString().equals("") || rueed.getText().toString().equals("") ||
                        appted.getText().toString().equals("") || ziped.getText().toString().equals("") ||
                        reged.getText().toString().equals("") || nomed.getText().toString().equals("") ||
                        teled.getText().toString().equals("") || teled2.getText().toString().equals("")) {
                    villeed.setError("ville doit etre remplie");
                    rueed.setError("rue doit etre remplie");
                    appted.setError("appt doit etre remplie");
                    ziped.setError("code zip doit etre remplie");
                    nomed.setError("nom doit etre remplie");
                    reged.setError("region doit etre remplie");
                    teled.setError("telephone doit etre remplie");
                }
                else{
                    Adresse adr=new Adresse();
                    adr.ville=villeed.getText().toString();
                    adr.appt=appted.getText().toString();
                    adr.rue=rueed.getText().toString();
                    adr.zip=ziped.getText().toString();
                    adr.nom=nomed.getText().toString();
                    adr.tel=teled.getText().toString();
                    adr.region=reged.getText().toString();
                    adr.userID=user.id;
                    saveAd(adr);
                    Intent i = new Intent(ListAdresseActivity.this, CompteActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    private void initViews() {
        villeed=findViewById(R.id.villeed);
        rueed=findViewById(R.id.rueed);
        appted=findViewById(R.id.appted);
        nomed = findViewById(R.id.nomed);
        ziped = findViewById(R.id.ziped);
        teled = findViewById(R.id.teled);
        reged=findViewById(R.id.reged);
        teled2=findViewById(R.id.teled2);
        radadr = findViewById(R.id.radadr);
        radm = findViewById(R.id.radm);
        radb = findViewById(R.id.radb);
        saveDetailsButton = findViewById(R.id.saveDetailsButton);

    }
    public void saveAd(Adresse adr) {
        RequestQueue queue = Volley.newRequestQueue(ListAdresseActivity.this);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/adresses.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println("---------->"+json);
                            adres=Adresse.fromJson(json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                System.out.println("---> "+adr);
                params.put("id", String.valueOf(adr.userID));
                params.put("ville", adr.ville);
                params.put("rue", adr.rue);
                params.put("appt", adr.appt);
                params.put("region", adr.region);
                params.put("tel", adr.tel);
                params.put("zip", adr.zip);
                params.put("nom", adr.nom);
                return params;
            }
        };
        queue.add(strreq);
    }
}