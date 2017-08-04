package com.example.developer.backgroundservices.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.developer.backgroundservices.Priority;

/**
 * Created by developer on 4/8/17.
 */

public class SampleJob extends Job {

    public SampleJob(String params) {
        super(new Params(Priority.MID).requireNetwork().persist().groupBy("sample_jobs")
                .singleInstanceBy("sample-job"));
    }

    @Override
    public void onAdded() {
        Log.d("Job added", "Added");
    }

    @Override
    public void onRun() throws Throwable {
        Log.d("Job running", "Running");
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.d("Job cancelled", "Cancelled");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.RETRY;
    }
}
