package com.example.firebase_login;

import android.icu.text.SimpleDateFormat;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Utility {
    static CollectionReference getCollectionRef()
    {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid()).collection("myNotes");
    }

    static String timestampToString(Timestamp timestamp)
    {

        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());

    }
}