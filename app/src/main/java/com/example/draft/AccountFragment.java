package com.example.draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {


    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button editProfile = view.findViewById(R.id.editProfile);
        Button petPreferences = view.findViewById(R.id.petPreferences);

        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        });

        petPreferences.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetPreferences.class);
            startActivity(intent);
        });

        return view;
    }


}
