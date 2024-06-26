package com.example.clicker;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtTimer; //Timer countdown text

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnListener listener = new BtnListener(); //Button Listener for A, B, C, D
        ((Button) findViewById(R.id.btnA)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnB)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnC)).setOnClickListener(listener);
        ((Button) findViewById(R.id.btnD)).setOnClickListener(listener);
    }

    private class BtnListener implements OnClickListener {
        // On-click event handler for all the buttons
        @Override
        public void onClick(View v) {
            // Determine which button was clicked
            String choice = "";
            if (v.getId() == R.id.btnA) {
                choice = "A";
            } else if (v.getId() == R.id.btnB) {
                choice = "B";
            } else if (v.getId() == R.id.btnC) {
                choice = "C";
            } else if (v.getId() == R.id.btnD) {
                choice = "D";
            }

            // Construct the URL with the choice parameter
            //final String urlString = "http://localhost:9999/hello/HelloHome"
            final String baseUrl = "http://10.0.2.2:9999/clicker/select";
            final String urlString = baseUrl + "?choice=" + choice;
            // Run the HTTP request in a background thread, separating from the main UI thread
            new HttpTask().execute(urlString);
        }

        // AsyncTask to perform HTTP request asynchronously
        private class HttpTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strURLs) {
                URL url = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(strURLs[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    // Get the HTTP response code (e.g., 200 for "OK", 404 for "Not found")
                    // and pass a string description in result to onPostExecute(String result)
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