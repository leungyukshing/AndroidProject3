package com.example.storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText newPassword;
    EditText confirmPassword;
    Button ok;
    Button clear;
    SharedPreferences sharedPreferences;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        ok = findViewById(R.id.ok);
        clear = findViewById(R.id.clear);
        sharedPreferences = MainActivity.this.getSharedPreferences("PASSWORD", MODE_PRIVATE);
        password = sharedPreferences.getString("PASSWORD", null);

        if (password != null) {
            confirmPassword.setHint("Password");
            newPassword.setVisibility(View.INVISIBLE);
        }

        // ok Button onClick Listener
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need to register
                if (password == null) {
                    String s1 = newPassword.getText().toString();
                    String s2 = confirmPassword.getText().toString();
                    // One of the passwords is empty
                    if (s1.equals("") || s2.equals("")) {
                        Toast.makeText(v.getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    // Two Password don't match
                    else if (!s1.equals(s2)) {
                        Toast.makeText(v.getContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                    // go to edit page
                    else {
                        // Store password in sharedpreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("PASSWORD", s1);
                        editor.commit();
                        // Jump to EditPage
                        startActivity(new Intent(MainActivity.this, EditPage.class));
                    }
                }
                // need to login
                else {
                    String s1 = confirmPassword.getText().toString();
                    if (s1.equals(password)) {
                        startActivity(new Intent(MainActivity.this, EditPage.class));
                    }
                    else {
                        Toast.makeText(v.getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // clear Button onClick Listener
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear all password
                newPassword.setText("");
                confirmPassword.setText("");
            }
        });
    }
}
