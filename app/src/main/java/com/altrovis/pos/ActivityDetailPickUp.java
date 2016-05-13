package com.altrovis.pos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Locale;

public class ActivityDetailPickUp extends AppCompatActivity {

    EditText editTextFrom;
    Button buttonEstimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pick_up);

        // Set Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Pick Up");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get Layout
        inisialisasiLayout();

        // Button Estimate to Main
        setButtonEstimateToMain();
    }

    public void inisialisasiLayout(){
        editTextFrom = (EditText)findViewById(R.id.EditTextFrom);
        buttonEstimate = (Button)findViewById(R.id.ButtonEstimate);
    }

    public void setButtonEstimateToMain()
    {
        buttonEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetailPickUp.this, ActivityMain.class);
                startActivity(intent);
                finish();
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
