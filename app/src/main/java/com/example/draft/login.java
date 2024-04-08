package com.example.draft;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private TextView signUpTextView; // Add TextView for "Sign up here"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        signUpTextView = findViewById(R.id.textView3); // Initialize TextView

        // Set underline for "Sign up here" TextView programmatically
        signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform login logic here
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check credentials (this is a basic example)
                if (isValidCredentials(username, password)) {
                    // Successful login
                    openHomeActivity();
                } else {
                    // Failed login
                    Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for "Sign up here" TextView
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity(); // Open registration activity
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        // Add your authentication logic here (e.g., check against a database or server)
        // For simplicity, this example uses hardcoded credentials
        return username.equals("example") && password.equals("password");
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
        finish();
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(this, reg.class);
        startActivity(intent);
    }
}
