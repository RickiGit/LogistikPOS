package com.altrovis.pos;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityActivation extends AppCompatActivity {

    Button buttonEnter;
    TextView textViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        // Set Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pos Indonesia");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get Layout
        inisialisasiLayout();

        // Button Enter Activation to Main
        buttonEnterToMain();
    }

    public void inisialisasiLayout()
    {
        buttonEnter = (Button)findViewById(R.id.ButtonEnter);
        textViewSignIn = (TextView)findViewById(R.id.TextViewSignIn);
    }

    public void buttonEnterToMain(){
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityActivation.this, ActivityMain.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void textViewSignInToActivitySignIn(View view){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
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
