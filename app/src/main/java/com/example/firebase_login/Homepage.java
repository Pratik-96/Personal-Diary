package com.example.firebase_login;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_login.databinding.ActivityHomepageBinding;
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
    ActivityHomepageBinding binding;

    RecyclerView.LayoutManager layoutManager;

    TextView homepageTitle,nodata;
    FirebaseUser user;

    RecyclerView recyclerView;

    noteAdapter noteAdapter;

    EditText search;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage);
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        recyclerView=findViewById(R.id.recyclerView);
        profile=findViewById(R.id.profile);
        addNote=findViewById(R.id.floatingActionButton);
        homepageTitle=findViewById(R.id.homepageTitle);
        search = findViewById(R.id.search);





        Calendar c = Calendar.getInstance();
        int hrs = c.get(Calendar.HOUR_OF_DAY);

        if (hrs>=1 && hrs<=12)
        {
            Toast.makeText(this, "Good Morning..!!", Toast.LENGTH_SHORT).show();
        } else if (hrs>12 && hrs<=16) {
            Toast.makeText(this, "Good Afternoon!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Good Evening!!", Toast.LENGTH_SHORT).show();
        }
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Note.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Profile.class));


            }
        });  //Logout User


        search.clearFocus();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter_list(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setUpRecyclerView();


    }



    private void filter_list(String string) {

        if (!string.isEmpty())
        {
            Query query = Utility.getCollectionRef().whereEqualTo("title",string.toLowerCase());
            FirestoreRecyclerOptions<noteModel> options = new FirestoreRecyclerOptions.Builder<noteModel>().setQuery(query, noteModel.class).build();
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(gridLayoutManager);
            noteAdapter = new noteAdapter(options, this);

            recyclerView.setAdapter(noteAdapter);
            noteAdapter.startListening();
            noteAdapter.notifyDataSetChanged();
        }
        else {
            setUpRecyclerView();
        }

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
        noteAdapter.startListening();
        noteAdapter.notifyDataSetChanged();

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