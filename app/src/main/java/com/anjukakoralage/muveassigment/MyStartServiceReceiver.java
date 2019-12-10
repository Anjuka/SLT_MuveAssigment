package com.anjukakoralage.muveassigment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anjukakoralage.muveassigment.utils.JobSchedulerUtils;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        JobSchedulerUtils.scheduleJob(context);
    }
}
