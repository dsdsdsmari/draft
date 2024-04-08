package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class reg extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, addressEditText, dobEditText;
    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        addressEditText = findViewById(R.id.editTextAddress);
        dobEditText = findViewById(R.id.editTextDOB);
        usernameEditText = findViewById(R.id.editTextNewUsername);
        passwordEditText = findViewById(R.id.editTextNewPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);

        Button registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform registration logic here
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String dob = dobEditText.getText().toString();
                String newUsername = usernameEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Check if registration is successful (this is a basic example)
                if (registerUser(firstName, lastName, address, dob, newUsername, newPassword, confirmPassword)) {
                    // Successful registration
                    Toast.makeText(reg.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    openLoginActivity();
                } else {
                    // Failed registration
                    Toast.makeText(reg.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean registerUser(String firstName, String lastName, String address, String dob, String newUsername, String newPassword, String confirmPassword) {
        // Add your registration logic here (e.g., store in a database or send to a server)
        // For simplicity, this example considers registration successful if the fields are not empty
        return !firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() &&
                !dob.isEmpty() && !newUsername.isEmpty() && !newPassword.isEmpty() &&
                newPassword.equals(confirmPassword);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}
