package com.altrovis.pos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.altrovis.pos.Bussines.GlobalFunctions;
import com.altrovis.pos.Bussines.HandleMapsTouch;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentLogistic extends Fragment {

    GoogleMap googleMap;
    View view;
    EditText editTextToLocation;
    EditText editTextFromLocation;
    TextView textViewNamePlace;
    ProgressBar progressBarNamePlace;
    LinearLayout linearLayoutNamePlace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        try {
            if(view == null){
                view = inflater.inflate(R.layout.fragment_logistic, container, false);
            }
        } catch (InflateException e) {
            Toast.makeText(getActivity(), "Masih ada bug", Toast.LENGTH_LONG).show();
        }

        // Get Layout
        Button buttonOrder = (Button)view.findViewById(R.id.ButtonOrder);
        editTextToLocation = (EditText)view.findViewById(R.id.EditTextToLocation);
        editTextFromLocation = (EditText)view.findViewById(R.id.EditTextFromLocation);
        textViewNamePlace = (TextView)view.findViewById(R.id.TextViewNamePlace);
        //progressBarNamePlace = (ProgressBar) view.findViewById(R.id.ProgressBarNamePlace);
        //linearLayoutNamePlace = (LinearLayout) view.findViewById(R.id.LinearLayoutNamePlace);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.ScrollViewLogistic);

        // Set Visible Gone
        //linearLayoutNamePlace.setVisibility(View.GONE);
        textViewNamePlace.setVisibility(View.GONE);

        // Set Default Google Map
        try {
            if (googleMap == null) {
                googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.Map)).getMap();

                // Handle Touch Maps
                ((HandleMapsTouch) getChildFragmentManager().findFragmentById(R.id.Map)).setListener(new HandleMapsTouch.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scroll.requestDisallowInterceptTouchEvent(true);
                    }
                });

                // Set Maps and Provider for Detect My Location
                googleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);

                // Check Permission for Google Maps
                if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                Location myLocation = locationManager.getLastKnownLocation(provider);

                if (myLocation != null) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    final double latitude = myLocation.getLatitude();
                    final double longitude = myLocation.getLongitude();
                    LatLng locationMe = new LatLng(latitude, longitude);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(locationMe).zoom(16).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {

                            final Handler handlerGetLocation = new Handler();
                            final Runnable updateLocation = new Runnable() {
                                public void run() {

                                    LatLng lokasiCamera = googleMap.getCameraPosition().target;
                                    double latitude = lokasiCamera.latitude;
                                    double longitude = lokasiCamera.longitude;

                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                        final String address = addresses.get(0).getAddressLine(0);
                                        final String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        final String country = addresses.get(0).getCountryName();
                                        final String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();

                                        // Set Location Destination to EditText
                                        final Handler handler = new Handler();

                                        final Runnable updateEditTextDestination = new Runnable() {
                                            public void run() {
                                                textViewNamePlace.setVisibility(View.VISIBLE);
                                                textViewNamePlace.setText(postalCode + ", " + city + ", " + country);

                                                textViewNamePlace.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        editTextToLocation.setText(postalCode + ", " + city + ", " + country);
                                                    }
                                                });
                                            }
                                        };

                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                handler.post(updateEditTextDestination);
                                            }
                                        }, 2000);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            Timer timerGetLocation = new Timer();
                            timerGetLocation.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handlerGetLocation.post(updateLocation);
                                }
                            }, 1000);
                        }
                    });
                } else if (myLocation == null) {
                    Toast.makeText(view.getContext(), "Posisi anda tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Button Order to Detail Pick Up
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityDetailPickUp.class);
                startActivity(intent);
            }
        });

        // Set My Location to EditText
        String myAddress = GlobalFunctions.SetUpCurrentCity(getActivity());
        editTextFromLocation.setText(myAddress);

        return view;
    }
}
