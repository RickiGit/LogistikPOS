package com.altrovis.pos;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityLogin extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Get Layout
        inisialisasiLayout();

        // Go to Main Activity
        buttonLoginGoToMain();

    }

    public void inisialisasiLayout()
    {
        editTextEmail = (EditText)findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        buttonLogin = (Button)findViewById(R.id.ButtonLogin);
        textViewSignUp = (TextView)findViewById(R.id.TextViewSignUp);
    }

    public void buttonLoginGoToMain()
    {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void textViewGoToRegistration(View view){
        Intent intent = new Intent(this, ActivityRegistration.class);
        startActivity(intent);
    }
}
