package com.mine.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.gk.emon.lovelyLoading.LoadingPopup;
import com.google.gson.Gson;
import com.mine.projet.customwidgets.LoadingDialog;
import com.mine.projet.models.User;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IntroApp extends AppIntro {
    appPref appp ;
    public static Context ctx ;
    public static final String MY_PREFS = "SharedPreferences";
    Intent log ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=IntroApp.this;

        appp = new appPref(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        log=new Intent(this, Main2Activity.class);
        if (appp.isFirstTimeLaunch()==false) {
            Gson gson = new Gson();
            String json = prefs.getString("user", "");
            User user = gson.fromJson(json, User.class);
            if(user!=null){
                if(user.id != -1 && user.email!="" && user.password!=""){
                    LoadingDialog loadingDialog = new LoadingDialog(this);
                    loadingDialog.show();
                    imgUtils.getProducts1(getApplicationContext());
                    imgUtils.getProducts2(getApplicationContext());
                    imgUtils.getProducts3(getApplicationContext());
                    imgUtils.getProducts4(getApplicationContext());
                    imgUtils.getProducts5(getApplicationContext());
                    imgUtils.getProducts6(getApplicationContext());
                    imgUtils.getAllProduits(getApplicationContext());
                    login(user.email,user.password);

                    loadingDialog.dismiss();

                }
            }
            else{
                Intent i =new Intent(this,LoginActivity.class);
                startActivity(i);
                this.finish();
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
                                imgUtils.getfavs(user.id,IntroApp.this);
                                imgUtils.getAdr(user.id,IntroApp.this);
                                imgUtils.getcoms(user.id,IntroApp.this);
                                startActivity(log);

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
