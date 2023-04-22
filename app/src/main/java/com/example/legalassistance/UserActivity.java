package com.example.legalassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    TextView tvFirstName,tvLastName,tvEmail,tvPhonenumber,tvCreatedAt;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhonenumber = findViewById(R.id.tvPhonenumber);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        btnLogout = findViewById(R.id.btnLogout);

        getUser();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }


    private void getUser() {

    }

    private void logout() {
        Intent intent = new Intent(UserActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}