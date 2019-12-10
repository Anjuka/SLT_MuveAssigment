package com.anjukakoralage.muveassigment.ui.splash;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by  on 09,December,2019
 */
public interface SplashContract {

    interface view{

    }

    interface presenter {

        void screenChange(Context mContext, int SPLASH_DISPLAY_LENGTH, Activity activity);

    }
}
