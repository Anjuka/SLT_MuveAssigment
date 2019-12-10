package com.anjukakoralage.muveassigment.services;

import android.app.job.JobParameters;
import android.location.Location;
import android.util.Log;

import com.anjukakoralage.muveassigment.data.remote.DBHelper;
import com.anjukakoralage.muveassigment.utils.JobSchedulerUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by  on 10,December,2019
 */
public class JobServiceCustom extends android.app.job.JobService {

    private static final String TAG = "SyncService";
    private FusedLocationProviderClient client;

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "*** onStartJob: Job Started...");
        JobSchedulerUtils.scheduleJob(getApplicationContext());

        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //save latlng in firebase database
                //DBHelper.firebasaeHelper(new LatLng(location.getLongitude(), location.getLongitude()));
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "*** onStopJob: Job Stopped...");
        return true;
    }
}
