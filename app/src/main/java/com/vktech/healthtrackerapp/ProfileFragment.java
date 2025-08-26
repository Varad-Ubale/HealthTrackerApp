package com.vktech.healthtrackerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText etName, etAge, etHeight, etWeight;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = v.findViewById(R.id.etName);
        etAge = v.findViewById(R.id.etAge);
        etHeight = v.findViewById(R.id.etHeight);
        etWeight = v.findViewById(R.id.etWeight);
        btnSave = v.findViewById(R.id.btnSaveProfile);

        loadProfile();

        btnSave.setOnClickListener(view -> saveProfile());

        return v;
    }

    private void loadProfile() {
        SharedPreferences prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        etName.setText(prefs.getString("name", ""));

        int age = prefs.getInt("age", 0);
        etAge.setText(age > 0 ? String.valueOf(age) : "");

        float height = prefs.getFloat("height", 0f);
        etHeight.setText(height > 0 ? String.valueOf(height) : "");

        float weight = prefs.getFloat("weight", 0f);
        etWeight.setText(weight > 0 ? String.valueOf(weight) : "");
    }

    private void saveProfile() {
        SharedPreferences prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("name", etName.getText().toString());
        editor.putInt("age", parseInt(etAge.getText().toString()));
        editor.putFloat("height", parseFloat(etHeight.getText().toString()));
        editor.putFloat("weight", parseFloat(etWeight.getText().toString()));
        editor.apply();

        Toast.makeText(getContext(), "Profile Saved!", Toast.LENGTH_SHORT).show();
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return 0f;
        }
    }
}