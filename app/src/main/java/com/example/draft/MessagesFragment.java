package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesFragment extends Fragment {

    EditText editTextSubject, editTextContent, editTextToEmail;
    Button button;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        button = view.findViewById(R.id.btnSend);
        editTextSubject = view.findViewById(R.id.subject);
        editTextContent = view.findViewById(R.id.content);
        editTextToEmail = view.findViewById(R.id.to_email);

        Bundle args = getArguments();
        if (args != null) {
            String ownerEmail = args.getString("OWNER_EMAIL");
            editTextToEmail.setText(ownerEmail);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject, content, to_email;
                subject = editTextSubject.getText().toString();
                content = editTextContent.getText().toString();
                to_email = editTextToEmail.getText().toString();
                if (subject.isEmpty() && content.isEmpty() && to_email.isEmpty()) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(subject, content, to_email);
                }
            }
        });

        return view;
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


