package com.example.clicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void btnLoginHandler(View v) {

        String phoneInput = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        String pwInput = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        if (phoneInput.equals("99999999") && pwInput.equals("xxxx")) {
            Intent intent = new Intent(this, LecturerActivity.class);
            text = "Login successful hahaha";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            startActivity(intent);
        } else if (pwInput.equals("student")){
            Intent intent = new Intent(this, StudentActivity.class);
            text = "Login successful hahaha";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            startActivity(intent);
        } else {
            text = "Incorrect Credentials.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}