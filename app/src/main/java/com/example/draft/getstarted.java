package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class getstarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        Button signInButton = findViewById(R.id.signIn);
        Button signUpButton = findViewById(R.id.signUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegActivity();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    private void openRegActivity() {
        Intent intent = new Intent(this, reg.class);
        startActivity(intent);
    }
}
