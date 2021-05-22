package com.mine.projet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.indicator.DotIndicatorController;
import com.github.appintro.indicator.IndicatorController;

import org.jetbrains.annotations.Nullable;

public class IntroApp extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        /*Intent i =new Intent(this,loginActivity.class);
        startActivity(i);
        this.finish();*/
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Decide what to do when the user clicks on "Done", for now it is finish
        //finish();

        /*Intent i =new Intent(this, loginActivity.class);
        startActivity(i);
        this.finish();*/
    }
}
