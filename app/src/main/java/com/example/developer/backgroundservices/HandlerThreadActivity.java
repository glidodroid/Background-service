package com.example.developer.backgroundservices;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlerThreadActivity extends AppCompatActivity {

    private TestHandlerThread handlerThread;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        Button button = (Button) findViewById(R.id.button_start);
        textView = (TextView) findViewById(R.id.text_status);
        handlerThread = new TestHandlerThread("Handler_thread");
        handlerThread.start();
        handlerThread.prepareHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        // long operation
                        for (int i = 0; i < 1000; i++) {
                            Log.d("First task", String.valueOf(i));

                            // update in ui thread.
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Finished first task");
                                }
                            });

                        }
                    }
                };

                Runnable secondTask = new Runnable() {
                    @Override
                    public void run() {
                        // long operation
                        for (int i = 0; i < 1000; i++) {
                            Log.d("Second task", String.valueOf(i));
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Finished second task");
                                }
                            });

                        }
                    }
                };
                handlerThread.addTask(task); // first time
                handlerThread.addTask(secondTask); // second time
            }
        });
    }

    private class TestHandlerThread extends HandlerThread {

        private Handler handler;

        TestHandlerThread(String name) {
            super(name);
        }

        // when you want to set priority
        TestHandlerThread(String name, int priority) {
            super(name, priority);

        }

        void prepareHandler() {
            handler = new Handler(getLooper());
        }

        void addTask(Runnable runnable) {
            handler.post(runnable);
        }

    }

    @Override
    protected void onDestroy() {
        handlerThread.quit();
        //handlerThread.quitSafely();  // depends on api level.
        super.onDestroy();
    }
}
