package com.example.firebase_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;

public class Homepage extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView txt;
    Button logout;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth= FirebaseAuth.getInstance();
        txt=findViewById(R.id.textView);
        user=mAuth.getCurrentUser();
        logout=findViewById(R.id.button);
        if (user==null)
        {
            Intent intent = new Intent(getApplicationContext(), Log_in.class);
            startActivity(intent);
            finish();
        }
        else {
            txt.setText(user.getEmail());
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Log_in.class);
                startActivity(intent);
                finish();
            }
        });
    }

}