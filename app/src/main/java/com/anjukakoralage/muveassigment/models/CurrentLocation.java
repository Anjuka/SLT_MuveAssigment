package com.anjukakoralage.muveassigment.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anjuka Koralage on 10,December,2019
 */
public class CurrentLocation {

    LatLng latLngLocation;

    CurrentLocation(){

    }

    public CurrentLocation(LatLng latLngLocation) {
        this.latLngLocation = latLngLocation;
    }

    public LatLng getLatLngLocation() {
        return latLngLocation;
    }

    public void setLatLngLocation(LatLng latLngLocation) {
        this.latLngLocation = latLngLocation;
    }
}
