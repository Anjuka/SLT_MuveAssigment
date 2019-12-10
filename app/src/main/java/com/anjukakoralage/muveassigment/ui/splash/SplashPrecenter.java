package com.anjukakoralage.muveassigment.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.anjukakoralage.muveassigment.ui.main.MainActivity;

/**
 * Created by Anjuka Koralage  on 07,December,2019
 */
public class SplashPrecenter implements SplashContract.presenter {

    SplashContract.view view;

    public SplashPrecenter(SplashContract.view view) {
        this.view = view;
    }

    @Override
    public void screenChange(final Context mContext, int SPLASH_DISPLAY_LENGTH, final Activity activity) {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                activity.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
