package com.mine.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    public static final String MY_PREFS = "SharedPreferences";
    private dbAdapter dba;
    private EditText user;
    private EditText pass;
    private Button loginButton;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();

        dba = new dbAdapter(this);
        dba.open();

        setContentView(R.layout.activity_login);
        initControls();

        SpannableString accounttext=new SpannableString("Don't have an account ?  Sign Up");
        Intent i =new Intent(this, Main2Activity.class);
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
    private void initControls() {
        //Set the activity layout.
        user = (EditText) findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextTextPassword);
        loginButton = (Button) findViewById(R.id.loginbutton);
        tv=findViewById(R.id.textView3);

        //Create touch listeners for all buttons.
        loginButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View v){
                LogMeIn(v);
            }
        });



        //Handle remember password preferences.
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");
        boolean rememberMe = prefs.getBoolean("remember", false);
        if(rememberMe) {
            user.setText(username);
            pass.setText(password);
        }

    }



    private void ClearForm() {
        saveLoggedInUId(0,"","");
        user.setText("");
        pass.setText("");
    }


    private void LogMeIn(View v) {
        //Get the username and password
        String thisUsername = user.getText().toString();
        String thisPassword = pass.getText().toString();

        //Assign the hash to the password
        thisPassword = md5(thisPassword);

        // Check the existing user name and password database
        Cursor theUser = dba.fetchUser(thisUsername, thisPassword);
        if (theUser != null) {
            startManagingCursor(theUser);
            if (theUser.getCount() > 0) {
                saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(dbAdapter.COL_ID)), thisUsername, pass.getText().toString());
                stopManagingCursor(theUser);
                theUser.close();
                Intent i = new Intent(v.getContext(), Main2Activity.class);
                startActivity(i);
            }

            //Returns appropriate message if no match is made
            else {
                Toast.makeText(getApplicationContext(),
                        "email ou mot de pass incorrect.",
                        Toast.LENGTH_SHORT).show();
                saveLoggedInUId(0, "", "");
            }
            stopManagingCursor(theUser);
            theUser.close();
        }

        else {
            Toast.makeText(getApplicationContext(),
                    "erreur DB",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void Register(View v)
    {
        /*Intent i = new Intent(v.getContext(), Create_account.class);
        startActivity(i);*/
    }

    private void saveLoggedInUId(long id, String username, String password) {
        SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences.Editor myEditor = settings.edit();
        myEditor.putLong("uid", id);
        myEditor.putString("username", username);
        myEditor.putString("password", password);
        myEditor.commit();
    }

    private String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }

        catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
}