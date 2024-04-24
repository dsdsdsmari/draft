package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText passwordEditText;
    private boolean isPasswordVisible = false;

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
                Toast.makeText(RegisterActivity.this, "Terms and Conditions clicked", Toast.LENGTH_SHORT).show();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStep1Activity();
            }
        });

        Button registerButton = findViewById(R.id.btnSignUp);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (firstName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your first name and password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    openStep1Activity();
                }
            }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void openStep1Activity() {
        Intent intent = new Intent(this, Step1Activity.class);
        startActivity(intent);
        finish();
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eyes_closed, 0);
        } else {
            passwordEditText.setTransformationMethod(null);
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eyes_open, 0);
        }
        isPasswordVisible = !isPasswordVisible;
    }
}
