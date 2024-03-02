package com.example.firebase_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebase_login.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {


    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            binding.mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null)
                {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), Log_in.class));
                    finish();

                }
            }
        });



    }
}