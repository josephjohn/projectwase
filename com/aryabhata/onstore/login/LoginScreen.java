package com.aryabhata.onstore.login;

/**
 * Created by Sainath on 2/27/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.productinfo.ProductInfo;
import com.aryabhata.onstore.shopping.ShoppingActivity;
import com.aryabhata.onstore.utilities.ProgressGenerator;
import com.aryabhata.onstore.utilities.SqlLiteDbHelper;
import com.dd.processbutton.iml.ActionProcessButton;

import java.io.IOException;


public class LoginScreen extends Activity implements ProgressGenerator.OnCompleteListener {


    SharedPreferences mPrefs;
    final String Product_Info = "Product_Info";

    ProgressGenerator progressGenerator;
    ActionProcessButton btnSignIn;
    EditText editEmail;
    EditText editPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean Product_Info_Done = mPrefs.getBoolean(Product_Info, false);

       if (!Product_Info_Done) {
            /* */
            Intent ProductTour = new Intent(LoginScreen.this,ProductInfo.class);
            startActivity(ProductTour);

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(Product_Info, true);
            editor.commit(); // Very important to save the preference
       }

        setContentView(R.layout.sign_in);

        SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getApplication());
        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        /* Login Check - ToDo */


        progressGenerator = new ProgressGenerator(this);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressGenerator.start(btnSignIn);
                    btnSignIn.setEnabled(false);
                    editEmail.setEnabled(false);
                    editPassword.setEnabled(false);
            }
        });
    }

    @Override
    protected void onResume() {
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setEnabled(true);
        editEmail.setEnabled(true);
        editPassword.setEnabled(true);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setEnabled(true);
        editEmail.setEnabled(true);
        editPassword.setEnabled(true);
    }

    @Override
    public void onComplete() {
        //Toast.makeText(this, R.string.welcome_note, Toast.LENGTH_LONG).show();
        Intent home = new Intent(LoginScreen.this,ShoppingActivity.class);
        startActivity(home);
    }
}

