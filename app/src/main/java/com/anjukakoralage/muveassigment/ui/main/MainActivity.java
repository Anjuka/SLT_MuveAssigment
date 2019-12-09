package com.anjukakoralage.muveassigment.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.anjukakoralage.muveassigment.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.view, OnMapReadyCallback, View.OnClickListener {

    MainContract.presenter mainPresenter;

    private static final String TAG = "BookingFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static int LOCATION_PERMISSION_CODE = 1234;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    private EditText mSearchText, mCurrentText;
    private ImageView mGps;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPrecenter(this);

        mCurrentText = (EditText) findViewById(R.id.edt_pickup_location_address);
        mSearchText = (EditText) findViewById(R.id.edt_drop_location_address);
        mGps = (ImageView) findViewById(R.id.ic_gps);

        getLocationPermission();
    }

    private void init() {
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        hideSoftKeyboard();
    }
        private void geoLocate () {
            Log.d(TAG, "geoLocate: geolocating");

            String searchString = mSearchText.getText().toString();

            Geocoder geocoder = new Geocoder(MainActivity.this);
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(searchString, 1);
            } catch (IOException e) {
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            }

            if (list.size() > 0) {
                Address address = list.get(0);

                Log.d(TAG, "geoLocate: found a location: " + address.toString());
                //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                        address.getAddressLine(0));
            }
        }

        private void getDeviceLocation () {
            Log.d(TAG, "getDeviceLocation: getting the devices current location");

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            try {
                if (mLocationPermissionsGranted) {

                    final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: found location!");
                                Location currentLocation = (Location) task.getResult();
                                double latitude = (new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()).latitude);
                                double longitude = (new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()).longitude);

                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        16,
                                        "My Location");

                                getAddress(latitude, longitude);
                                setMarkerDeviceLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                            } else {
                                Log.d(TAG, "onComplete: current location is null");
                                Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (SecurityException e) {
                Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
            }
        }

        private void moveCamera (LatLng latLng,float zoom, String title){
            Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

            if (!title.equals("My Location")) {
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(title);
                mMap.addMarker(options);
            }

            hideSoftKeyboard();
        }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


        private void hideSoftKeyboard(){
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            Log.v("IGA", "Address" + add);

            mCurrentText.setText(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setMarkerDeviceLocation(LatLng latLng){
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_radio_button_checked_black_24dp));
        mMap.addMarker(options);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {

    }
}
