package com.example.developer.backgroundservices;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.example.developer.backgroundservices.job.SampleJob;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private Handler handler;
    JobManager jobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_hello);
        handler = new Handler();// this handler got ui-thread looper.
        jobManager = BackgroundServicesApp.getInstance().getJobManager();
        doJob();
        doJob();
        doJob();
        doJob();
        doJob();
        //doLongOperation();
        //doVeryLongOperation();
    }

    private void doJob() {
        jobManager.addJobInBackground(new SampleJob("sample"));
    }

    private void doLongOperation() {
        WorkerThread workerThread = new WorkerThread();
        workerThread.start();
    }

    private void doVeryLongOperation() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Log.d("Print values", String.valueOf(i));
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Completed");
                    }
                });
            }
        };
        new Thread(task).start();
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                Log.d("Printing values", String.valueOf(i));
            }
        }
    }
}
