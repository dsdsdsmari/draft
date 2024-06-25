package com.example.draft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private EditText messageEditText;
    private Button sendButton;

    private DatabaseReference messagesRef;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter;
    private String ownerEmail;
    private String ownerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Get owner's details from Intent
        ownerEmail = getIntent().getStringExtra("OWNER_EMAIL");
        ownerName = getIntent().getStringExtra("OWNER_NAME");

        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Scrolls to bottom of list
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(messagesRef, Message.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull Message message) {
                messageViewHolder.bind(message);
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message, parent, false);
                return new MessageViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            }
        });

    }

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void sendMessage(String message) {
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String messageId = messagesRef.push().getKey();

        Message msg = new Message(currentUserEmail, ownerEmail, message, System.currentTimeMillis());

        messagesRef.child(messageId).setValue(msg)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            messageEditText.setText("");
                            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        } else {
                            Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.text_view_message);
        }

        public void bind(Message message) {
            messageTextView.setText(message.getMessage());
        }
    }

}