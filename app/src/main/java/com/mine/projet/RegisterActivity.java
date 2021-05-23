package com.mine.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.mine.projet.db.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private EditText email;
    private EditText password;
    private EditText password_conf;
    String a,b,c,d,e;
    private Button registerButton;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nom=findViewById(R.id.et_nom);
        prenom=findViewById(R.id.et_prenom);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        password_conf=findViewById(R.id.et_password_conf);
        registerButton=findViewById(R.id.registerbutton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nom.getText().length()>0 && prenom.getText().length()>0 && email.getText().length()>0 && password.getText().length()>0 && password_conf.getText().length()>0){
                    if(password.getText()==password_conf.getText()){
                        try {
                            register(email.getText().toString(),password.getText().toString(),nom.getText()+" "+prenom.getText(),"");
                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });


    }

    private void register(String email,String pass,String name, String phone) throws SQLException, ClassNotFoundException {
        db=new database();
        Connection con=db.getExtraConnection();
        String sql="'INSERT INTO Users (id,email,password,name,) VALUES (?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "");
        statement.setString(2, email);
        statement.setString(3, pass);
        statement.setString(4, name);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            // to do, go to main page
            Intent i =new Intent(this, Main2Activity.class);
            startActivity(i);
            this.finish();
        }
        con.close();
    }

    public void register(String reqname,String reqemail, String reqpass){
        Intent log =new Intent(this, Main2Activity.class);
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

                            for(int i=0; i < json.length(); i++) {
                                JSONObject respObj = json.getJSONObject(i);
                                String name = respObj.getString("name");
                                int id = respObj.getInt("id");
                                String password = respObj.getString("password");
                                String email = respObj.getString("email");
                                log.putExtra("id",id);
                                log.putExtra("name",name);
                                log.putExtra("email",email);
                                startActivity(log);
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
            params.put("name", reqname);
            params.put("password", reqpass);
            return params;
        }
        };
        queue.add(strreq);
    }

}