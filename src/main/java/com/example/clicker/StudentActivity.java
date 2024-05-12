package com.example.clicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    private TextView txtTimer; //Timer countdown text

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        BtnListener listener = new BtnListener(); //Button Listener for A, B, C, D
        ((Button) findViewById(R.id.btnA)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnB)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnC)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnD)).setOnClickListener(listener);

        txtTimer = (TextView)  findViewById(R.id.txtTimer);
        // Count down from 30 sec. onTick() every second. Values in milliseconds
        new CountDownTimer(15000, 1000) {
            public void onTick(long millisRemaining) {
                txtTimer.setText(String.valueOf(millisRemaining / 1000));
            }
            public void onFinish() {
                txtTimer.setText("Time Up!");
            }
        }.start();
    }

    public void btnLogoutHandler(View v) {
        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        Intent intent = new Intent(this, LoginActivity.class);
        text = "Goodbye";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        startActivity(intent);
    }


    private class BtnListener implements OnClickListener {
        // On-click event handler for all the buttons
        @Override
        public void onClick(View v) {
            // Determine which button was clicked
            String choice = "";
            if (v.getId() == R.id.btnA) {
                choice = "a";
            } else if (v.getId() == R.id.btnB) {
                choice = "b";
            } else if (v.getId() == R.id.btnC) {
                choice = "c";
            } else if (v.getId() == R.id.btnD) {
                choice = "d";
            }

            final String baseUrl = "http://10.0.2.2:9999/clicker/select";
            final String urlString = baseUrl + "?choice=" + choice;
            // Run  HTTP request in a background thread, separating from the main UI thread
            new HttpTask().execute(urlString);
        }

        private class HttpTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strURLs) {
                URL url = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(strURLs[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    // get HTTP response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "OK (" + responseCode + ")"; //200
                    } else {
                        return "Fail (" + responseCode + ")"; //404 or Not found
                    }
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid.";
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Show a toast message based on the result
                Context context = getApplicationContext();
                CharSequence text;
                if (result.startsWith("OK")) {
                    text = "Request successful hahaha: " + result;
                } else {
                    text = "Request failed: " + result;
                }
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}