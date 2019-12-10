package com.anjukakoralage.muveassigment.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by  on 10,December,2019
 */
public class CurrentLocation {

    String locaionName;
    LatLng latLngLocation;

    CurrentLocation(){

    }

    public CurrentLocation(String locaionName, LatLng latLngLocation) {
        this.locaionName = locaionName;
        this.latLngLocation = latLngLocation;
    }

    public String getLocaionName() {
        return locaionName;
    }

    public LatLng getLatLngLocation() {
        return latLngLocation;
    }

    public void setLocaionName(String locaionName) {
        this.locaionName = locaionName;
    }

    public void setLatLngLocation(LatLng latLngLocation) {
        this.latLngLocation = latLngLocation;
    }
}
