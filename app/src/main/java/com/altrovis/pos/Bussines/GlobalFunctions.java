package com.altrovis.pos.Bussines;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

/**
 * Created by ricki on 13-May-16.
 */
public class GlobalFunctions {

    public static String SetUpCurrentCity(Context context) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);

        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getPostalCode() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
                return address;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
