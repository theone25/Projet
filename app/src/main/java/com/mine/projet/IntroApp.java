package com.mine.projet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.indicator.DotIndicatorController;
import com.github.appintro.indicator.IndicatorController;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IntroApp extends AppIntro {
    appPref appp ;
    public static final String MY_PREFS = "SharedPreferences";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appp = new appPref(this);
        if (appp.isFirstTimeLaunch()==false) {
            int userID=appp.pref.getInt("id",-1);
            String userName=appp.pref.getString("username","");
            String userPass=appp.pref.getString("password","");
            if(userID != -1 && userName!="" && userPass!=""){
                login(userName,userPass);
            }
            else{
                Intent i =new Intent(this,LoginActivity.class);
                startActivity(i);
            }


            this.finish();
        }
        // full screen activity
        setImmersiveMode();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();


        // first intro page
        addSlide(AppIntroFragment.newInstance(
                "Retour d'argents en 7 jours",
                "retour de votre argents en 7 jours maximum ",
                R.drawable.money,
                Color.parseColor("#FE5C45"),Color.WHITE, Color.WHITE
        ));



        // second intro page
        addSlide(AppIntroFragment.newInstance(
                "Paiement securisé",
                "votre paiement est securisé",
                R.drawable.wallet,
                Color.parseColor("#FE5C45"),Color.WHITE, Color.WHITE
        ));

        //third page intro
        addSlide(AppIntroFragment.newInstance(
                "livraison au plus possible delais",
                "votre commande sera etre livré le plus proche possible",
                R.drawable.deliverytruck,
                Color.parseColor("#FE5C45"),Color.WHITE, Color.WHITE
        ));

        // chnager le text au fin de la page intro et text du botton skip
        setDoneText("Terminer");
        setSkipText("passer");

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Decide what to do when the user clicks on "Skip", for now it is finish
        //finish();
        appp.setFirstTimeLaunch(false);
        //startActivity(new Intent(WelcomeActivity.this, Login_account.class));
        Intent i =new Intent(this,LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Decide what to do when the user clicks on "Done", for now it is finish
        //finish();
        appp.setFirstTimeLaunch(false);
        Intent i =new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    public void login(String reqemail, String reqpass){
        Intent log =new Intent(this, Main2Activity.class);
        RequestQueue queue = Volley.newRequestQueue(IntroApp.this);
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
                                String name = respObj.getString("name");
                                int id = respObj.getInt("id");
                                String password = respObj.getString("password");
                                String email = respObj.getString("email");
                                log.putExtra("id",id);
                                log.putExtra("name",name);
                                log.putExtra("email",email);
                                startActivity(log);
                                imgUtils.getProducts1(getApplicationContext());
                                imgUtils.getProducts2(getApplicationContext());
                                imgUtils.getProducts3(getApplicationContext());
                                imgUtils.getProducts4(getApplicationContext());
                                imgUtils.getProducts5(getApplicationContext());
                                imgUtils.getProducts6(getApplicationContext());
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
