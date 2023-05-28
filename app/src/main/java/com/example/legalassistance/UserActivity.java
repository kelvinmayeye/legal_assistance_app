package com.example.legalassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        String url = getString(R.string.api_server)+"/user";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(UserActivity.this,url);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        String json = http.getResponse();
                        if (code == 200){
                            Log.d("Resp234323once:", json);
                            try {
                                JSONObject response = new JSONObject(json);
                                String firstname = response.getString("firstname");
                                String lastname = response.getString("lastname");
                                String email = response.getString("email");
                                String phonenumber = response.getString("phonenumber");
                                tvFirstName.setText(firstname);
                                tvLastName.setText(lastname);
                                tvEmail.setText(email);
                                tvPhonenumber.setText(phonenumber);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                        else{
                            Toast.makeText(UserActivity.this,"Error"+code,Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        }).start();
    }

    private void logout() {
        String url = getString(R.string.api_server)+"/logout";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(UserActivity.this, url);
                http.setMethod("post");
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 200){
                            Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(UserActivity.this,"Error "+code,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
}