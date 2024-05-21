package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Step1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioPetOwner = findViewById(R.id.radioPetOwner);
        RadioButton radioPetAdopter = findViewById(R.id.radioPetAdopter);
        Button continueButton = findViewById(R.id.btn_continue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == radioPetOwner.getId()) {
                    openStep2Activity();
                } else if (selectedId == radioPetAdopter.getId()) {
                    openStep2Activity();
                }
            }
        });
    }

    private void openStep2Activity() {
        Intent intent = new Intent(this, Step2Activity.class);
        startActivity(intent);
    }
}
