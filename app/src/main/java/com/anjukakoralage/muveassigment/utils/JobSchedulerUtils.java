package com.anjukakoralage.muveassigment.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.anjukakoralage.muveassigment.services.JobServiceCustom;

/**
 * Created by  on 10,December,2019
 */
public class JobSchedulerUtils {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, JobServiceCustom.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(5 * 60 * 1000); // wait at least
        builder.setOverrideDeadline(5 * 60 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        //builder.setRequiresDeviceIdle(true);
        //builder.setRequiresCharging(false);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
