package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mine.projet.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private EditText email;
    private EditText tel;
    private EditText password;
    private EditText password_conf;
    private Button registerButton;
    public static final String MY_PREFS = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nom = findViewById(R.id.et_nom);
        prenom = findViewById(R.id.et_prenom);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        password_conf = findViewById(R.id.et_password_conf);
        tel = findViewById(R.id.et_phone);

        registerButton = findViewById(R.id.registerbutton);

        registerButton.setOnClickListener(v -> {
            if (nom.getText().length() > 0 && prenom.getText().length() > 0 && email.getText().length() > 0 && password.getText().length() > 0 && password_conf.getText().length() > 0 && tel.getText().length()>0) {
                if (password.getText().toString().equals(password_conf.getText().toString()) ) {
                    register(prenom.getText().toString().trim() , nom.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString(),tel.getText().toString().trim());
                }
            }
        });


    }

    public void register(String reqfirst_name,String reqlast_name, String reqemail, String reqpass,String reqphone) {
        Intent log = new Intent(this, Main2Activity.class);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(json);

                            for (int i = 0; i < json.length(); i++) {
                                JSONObject respObj = json.getJSONObject(i);
                                String first_name = respObj.getString("first_name");
                                String last_name = respObj.getString("last_name");
                                String phone = respObj.getString("phone");
                                int id = respObj.getInt("id");
                                String password = respObj.getString("password");
                                String email = respObj.getString("email");
                                User user=new User();
                                user.email=email;
                                user.id=id;
                                user.nom=last_name;
                                user.prenom=first_name;
                                user.tel=phone;
                                user.password=password;
                                log.putExtra("id", id);
                                log.putExtra("first_name", first_name);
                                log.putExtra("last_name", last_name);
                                log.putExtra("phone", phone);
                                log.putExtra("email", email);
                                startActivity(log);
                                saveLoggedInUId(user);
                                finish();
                            }
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
                params.put("email", reqemail);
                params.put("first_name", reqfirst_name);
                params.put("last_name", reqlast_name);
                params.put("phone", reqphone);
                params.put("password", reqpass);
                return params;
            }
        };
        queue.add(strreq);
    }

    private void saveLoggedInUId(User user) {
        SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("user", json);
        prefsEditor.commit();

    }

}