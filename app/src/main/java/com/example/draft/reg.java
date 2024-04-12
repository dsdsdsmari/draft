package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class reg extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firstNameEditText = findViewById(R.id.editTextFirstName);
        passwordEditText = findViewById(R.id.editTextPassword);

        CheckBox checkBox = findViewById(R.id.checkBox);
        TextView termsAndConditionsTextView = findViewById(R.id.termsAndConditionsTextView);
        TextView signInTextView = findViewById(R.id.signInTextView);

        termsAndConditionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(reg.this, "Terms and Conditions clicked", Toast.LENGTH_SHORT).show();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        Button registerButton = findViewById(R.id.btnSignUp);
        registerButton.setOnClickListener(new    View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (firstName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(reg.this, "Please enter your first name and password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(reg.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    openLoginActivity();
                }
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}
