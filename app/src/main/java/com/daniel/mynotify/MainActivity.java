package com.daniel.mynotify;

import android.app.Activity;
import android.app.Notification;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private NotificationUtils notify;
    ProgressBar progressBar;
    Button startButton;
    TextView textView;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notify = new NotificationUtils(this);
        startButton = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        //handle the click event
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Downloader().execute();
            }
        });
    }

    class Downloader extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate();

            progressBar.setProgress(values[0]);
            String ii = Integer.toString(i++);
            textView.setText(ii + "%");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), "100% - Completed", Toast.LENGTH_LONG).show();
            i = 0;

            String title = "IN THE NEWS!";
            String text = "Go to the Guardian periodical for more news about the happenings all around the globe.";

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(text)) {
                Notification.Builder nb = notify.
                        getAndroidChannelNotification(title, text);

                notify.getManager().notify(101, nb.build());
            }

        }
    }
}
