package com.mine.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mine.projet.customwidgets.LoadingDialog;
import com.mine.projet.db.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mine.projet.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    public static final String MY_PREFS = "SharedPreferences";
    private EditText user;
    private EditText pass;
    private Button loginButton;
    TextView tv;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    //private String url = "https://fptandroid.000webhostapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();

        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        loginButton = (Button) findViewById(R.id.loginbutton);
        tv=findViewById(R.id.textView3);

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //postDataUsingVolley(user.getText().toString(),pass.getText().toString());
                LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
                loadingDialog.show();
                imgUtils.getProducts1(getApplicationContext());
                imgUtils.getProducts2(getApplicationContext());
                imgUtils.getProducts3(getApplicationContext());
                imgUtils.getProducts4(getApplicationContext());
                imgUtils.getProducts5(getApplicationContext());
                imgUtils.getProducts6(getApplicationContext());
                imgUtils.getAllProduits(getApplicationContext());
                login(user.getText().toString(),pass.getText().toString());
            }
        });

        SpannableString accounttext=new SpannableString("Don't have an account ?  Sign Up");
        Intent i =new Intent(this, RegisterActivity.class);
        accounttext.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // what to do when Sign Up is clicked
                System.out.println("testing click function");
                startActivity(i);
            }
        },25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(accounttext);
        // text span click won't be called if this line isn't added
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }





    private void saveLoggedInUId(User user) {
        SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor prefsEditor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("user", json);
        prefsEditor.commit();
        imgUtils.getfavs(user.id,LoginActivity.this);
        imgUtils.getAdr(user.id,LoginActivity.this);
        imgUtils.getcoms(user.id,LoginActivity.this);
    }



    /*private void postDataUsingVolley(String reqemail, String reqpass) {
        // url to post our data
        String url = "https://fptandroid.000webhostapp.com/";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty


                // on below line we are displaying a success toast message.
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONArray json = new JSONArray(response);
                    System.out.println(json);

                    for(int i=0; i < json.length(); i++) {
                        JSONObject respObj = json.getJSONObject(i);
                        String name = respObj.getString("name");
                        int id = respObj.getInt("id");
                        String password = respObj.getString("password");
                        String email = respObj.getString("email");
                        System.out.println(name);
                        System.out.println(id);
                        System.out.println(password);
                        System.out.println(email);
                    }

                    // below are the strings which we
                    // extract from our json object.

                    //
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("email", reqemail);
                params.put("password", reqpass);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }*/
    
    public void login(String reqemail, String reqpass){
        Intent log =new Intent(this, Main2Activity.class);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest strreq = new StringRequest(Request.Method.POST,
                "https://fptandroid.000webhostapp.com/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        try {
                            // on below line we are passing our response
                            // to json object to extract data from it.
                            JSONArray json = new JSONArray(Response);
                            System.out.println(json);

                            for(int i=0; i < json.length(); i++) {
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
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("email", reqemail);
            params.put("password", reqpass);
            return params;
        }
        };
        queue.add(strreq);
    }
}