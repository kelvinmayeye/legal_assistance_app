package com.example.legalassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText etFirstName,etLastName,etEmail,etPassword,etPhonenumber,etConfirmation;
    Button btnRegister;
    String firstname,lastname,email,password,confirmation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Register account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhonenumber = findViewById(R.id.etPhonenumber);
        etConfirmation = findViewById(R.id.etConfirmation);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRegister();
            }
        });
    }

    private void checkRegister() {
        firstname = etFirstName.getText().toString();
        lastname = etLastName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        confirmation = etConfirmation.getText().toString();
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()){
            alertFail("names,email and password are required");
        }else if(!password.equals(confirmation)){
            alertFail("Passwords didnt match");
        }else {
            sendRegister();
        }
    }

    private void sendRegister() {
        alertSuccess("Register successfully");
    }

    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setIcon(R.drawable.ic_check)
                .setMessage(s)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                })
                .show();

    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}