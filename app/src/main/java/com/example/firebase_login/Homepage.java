package com.example.firebase_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Homepage extends AppCompatActivity {

    FirebaseAuth mAuth;

    FloatingActionButton addNote;

    RecyclerView.LayoutManager layoutManager;

    TextView homepageTitle,nodata;
    FirebaseUser user;

    RecyclerView recyclerView;

    noteAdapter noteAdapter;
    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        recyclerView=findViewById(R.id.recyclerView);
        logout=findViewById(R.id.logout);
        addNote=findViewById(R.id.floatingActionButton);
        homepageTitle=findViewById(R.id.homepageTitle);
        nodata=findViewById(R.id.nodata);
        Calendar c = Calendar.getInstance();
        int hrs = c.get(Calendar.HOUR_OF_DAY);

        if (hrs>=1 && hrs<=12)
        {
            nodata.setText("Good Morning!!");
        } else if (hrs>12 && hrs<=16) {
            nodata.setText("Good Afternoon!!");
        }
        else
        {
            nodata.setText("Good Evening!!");
        }
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Note.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), Log_in.class));
                                finish();

            }
        });  //Logout User

        setUpRecyclerView();


    }
    public void setUpRecyclerView() {

        Query query = Utility.getCollectionRef().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<noteModel> options = new FirestoreRecyclerOptions.Builder<noteModel>().setQuery(query, noteModel.class).build();
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        noteAdapter = new noteAdapter(options, this);

        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();


    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();


    }
}