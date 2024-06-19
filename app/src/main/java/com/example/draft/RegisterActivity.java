package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText signupEmail;
    EditText signupPassword;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private boolean isPasswordVisible = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signUpEmail);
        signupPassword = findViewById(R.id.signUpPassword);
        signupButton = findViewById(R.id.btnSignUp);
        CheckBox checkBox = findViewById(R.id.checkBox);
        TextView termsAndConditionsTextView = findViewById(R.id.termsAndConditionsTextView);
        TextView signInTextView = findViewById(R.id.signInTextView);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    signupEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    signupPassword.setError("Password is required");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    addUserToDatabase(user.getUid(), email);
                                    openStep1Activity(email);
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        signupEmail.setError("Email is already registered");
                                        signupEmail.requestFocus();
                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

        termsAndConditionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Terms and Conditions clicked", Toast.LENGTH_SHORT).show();
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStep1Activity(null);
            }
        });

        signupPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (signupPassword.getRight() - signupPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addUserToDatabase(String userId, String email) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reference.child(userId).child("email").setValue(email);
    }

    private void openStep1Activity(String email) {
        Intent intent = new Intent(this, Step1Activity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            signupPassword.setTransformationMethod(new PasswordTransformationMethod());
            signupPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eyes_closed, 0);
        } else {
            signupPassword.setTransformationMethod(null);
            signupPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eyes_open, 0);
        }
        isPasswordVisible = !isPasswordVisible;
    }
}
