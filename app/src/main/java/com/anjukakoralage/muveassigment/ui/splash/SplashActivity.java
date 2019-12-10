package com.anjukakoralage.muveassigment.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anjukakoralage.muveassigment.R;
import com.anjukakoralage.muveassigment.ui.main.MainActivity;

/**
 * Created by Anjuka Koralage  on 07,December,2019
 */
public class SplashActivity extends AppCompatActivity implements SplashContract.view {

    SplashContract.presenter splashPreceneter;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPreceneter = new SplashPrecenter(this);
        splashPreceneter.screenChange(getApplicationContext(), SPLASH_DISPLAY_LENGTH, this);

    }
}
