package com.example.developer.backgroundservices;

import android.app.Application;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;

/**
 * Created by developer on 4/8/17.
 */

public class BackgroundServicesApp extends Application {

    private static final String TAG = BackgroundServicesApp.class.getSimpleName();
    private JobManager jobManager;
    private static BackgroundServicesApp instance;

    public BackgroundServicesApp() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getJobManager();
    }

    private void configureJobManager() {
        Configuration.Builder builder = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        //Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        //Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        //Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
                .minConsumerCount(1) // always keep at least one consumer alive.
                .maxConsumerCount(3) // up to 3 consumers at a time.
                .loadFactor(3) // 3 jobs per consumer.
                .consumerKeepAlive(60); // wait 1 min
        jobManager = new JobManager(builder.build());
    }

    public synchronized JobManager getJobManager() {
        if (jobManager == null) {
            configureJobManager();
        }
        return jobManager;
    }

    public static BackgroundServicesApp getInstance() {
        return instance;
    }

}
