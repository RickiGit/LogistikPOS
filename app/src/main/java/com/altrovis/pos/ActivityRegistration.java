package com.altrovis.pos;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityRegistration extends AppCompatActivity {

    Button buttonSignUp;
    TextView textViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register Your Phone Number");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get Layout
        inisialisasiLayout();

        // Button Sign Up Click
        buttonSignUpToActivation();
    }

    public void inisialisasiLayout()
    {
        buttonSignUp = (Button)findViewById(R.id.ButtonSignUp);
        textViewSignIn = (TextView)findViewById(R.id.TextViewSignIn);
    }

    public void textViewSignInToActivitySignIn(View view){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    public void buttonSignUpToActivation()
    {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegistration.this, ActivityActivation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
