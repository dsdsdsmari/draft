package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GmailMessage extends AppCompatActivity {

    EditText editTextSubject, editTextContent, editTextToEmail;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gmail_message);

        button = findViewById(R.id.btnSend);
        editTextSubject = findViewById(R.id.subject);
        editTextContent = findViewById(R.id.content);
        editTextToEmail = findViewById(R.id.to_email);

        String ownerEmail = getIntent().getStringExtra("OWNER_EMAIL");
        if (ownerEmail != null) {
            editTextToEmail.setText(ownerEmail);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject, content, to_email;
                subject = editTextSubject.getText().toString();
                content = editTextContent.getText().toString();
                to_email = editTextToEmail.getText().toString();
                if (subject.equals("") && content.equals("") && to_email.equals("")) {
                    Toast.makeText(GmailMessage.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendEmail(subject, content, to_email);
                }
            }
        });

    }

    public void sendEmail(String subject, String content, String to_email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to_email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose email client:"));
    }
}