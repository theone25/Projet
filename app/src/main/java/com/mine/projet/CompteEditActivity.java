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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompteEditActivity extends AppCompatActivity {
    EditText etnom;
    EditText etprenom;
    EditText etemail;
    EditText profile_old_password;
    EditText profile_new_password;
    Button save_profile;
    Button cancel_btn;
    appPref appp;
    public static final String MY_PREFS = "SharedPreferences";

    final int PROFILE_EDIT = 1;
    public String userPass;
    public String email;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_compte_edit);
        super.onCreate(savedInstanceState);
        etnom = findViewById(R.id.nom);
        etprenom = findViewById(R.id.prenom);
        etemail = findViewById(R.id.email);
        save_profile = findViewById(R.id.save_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        profile_old_password = findViewById(R.id.password);
        profile_new_password = findViewById(R.id.password_new);

        appp = new appPref(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        int userID = prefs.getInt("id", -1);
         email = prefs.getString("username", "");
        userPass = prefs.getString("password", "");
         name = prefs.getString("name", "");
        etnom.setText(name);
        etprenom.setText(name);
        etemail.setText(email);

        save_profile.setOnClickListener(v -> {
            if (!profile_new_password.getText().toString().trim().isEmpty() &&
                    profile_old_password.getText().toString().equals(userPass)) {
                userPass = profile_new_password.getText().toString();
            }
            email = etemail.getText().toString().trim().isEmpty()? email:etemail.getText().toString();
            name = etnom.getText().toString().trim().isEmpty()? name:etnom.getText().toString();


            saveProfile(String.valueOf(userID), name,email, userPass);
            Intent i =new Intent();
            i.putExtra("username",email);
            i.putExtra("name",name);

            setResult(PROFILE_EDIT,i);
            finish();
        });
        cancel_btn.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    public void saveProfile(String id, String reqname, String reqemail, String reqpass) {
        Intent log = new Intent(this, Main2Activity.class);
        RequestQueue queue = Volley.newRequestQueue(CompteEditActivity.this);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/updateUser.php",
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
                                String name = respObj.getString("name");
                                int id = respObj.getInt("id");
                                String password = respObj.getString("password");
                                String email = respObj.getString("email");
                                log.putExtra("id", id);
                                log.putExtra("name", name);
                                log.putExtra("email", email);
                                saveLoggedInUId(id, email, password);
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
                params.put("name", reqname);
                params.put("password", reqpass);
                params.put("id", id);
                return params;
            }
        };
        queue.add(strreq);
    }

    private void saveLoggedInUId(int id, String username, String password) {
        SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor myEditor = settings.edit();
        myEditor.putInt("uid", id);
        myEditor.putString("username", username);
        myEditor.putString("password", password);
        myEditor.commit();
    }

}