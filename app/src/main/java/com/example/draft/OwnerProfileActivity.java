package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OwnerProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView fullnameTextView;
    private TextView emailTextVIew;
    private TextView phoneNumberTextView;
    private Button messageButton;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_profile);

        profileImageView = findViewById(R.id.profile_image);
        fullnameTextView = findViewById(R.id.profile_name);
        emailTextVIew = findViewById(R.id.profile_email);
        phoneNumberTextView = findViewById(R.id.profile_phone);
        messageButton = findViewById(R.id.btnMessage);

        // Get owner email from intent
        String ownerEmail = getIntent().getStringExtra("OWNER_EMAIL");

        usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(ownerEmail);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("fullName").getValue(String.class);
                    String email = snapshot.getKey();
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String profileImageUrl = snapshot.child("profileImageUrl").getValue(String.class);

                    fullnameTextView.setText(fullName);
                    emailTextVIew.setText(email);
                    phoneNumberTextView.setText(phoneNumber);

                    Glide.with(OwnerProfileActivity.this)
                            .load(profileImageUrl)
                            .placeholder(R.drawable.profile_icon) // Placeholder image
                            .into(profileImageView);

                    messageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Navigate to the messaging activity
                            Intent intent = new Intent(OwnerProfileActivity.this, GmailMessage.class);
                            intent.putExtra("OWNER_EMAIL", email);
                            startActivity(intent);
                            finish();
                        }
                    });

                } else {
                    Toast.makeText(OwnerProfileActivity.this, "Error fetching owner data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OwnerProfileActivity", "Error fetching owner data");
            }
        });

    }
}