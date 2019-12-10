package com.anjukakoralage.muveassigment.data.remote;

import com.anjukakoralage.muveassigment.models.CurrentLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anjuka Koralage on 10,December,2019
 */
public class DBHelper {


    public static void firebasaeHelper(LatLng location){
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("CurrentLocation");
        String id = databaseReference.push().getKey();
        CurrentLocation currentLocation = new CurrentLocation(location);
        databaseReference.child(id).setValue(currentLocation);
    }
}
