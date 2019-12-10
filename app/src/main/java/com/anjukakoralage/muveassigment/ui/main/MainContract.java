package com.anjukakoralage.muveassigment.ui.main;

import android.content.Context;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anjuka Koralage  on 07,December,2019
 */
public interface MainContract {

    interface view {

        void hideKeyBoard();

    }

    interface presenter {

        void getAddress(double lat, double lng, EditText view);

        void setMarkerDeviceLocation(LatLng latLng, GoogleMap map);

        void moveCamera(LatLng latLng, float zoom, String title, GoogleMap mMap);
    }
}
