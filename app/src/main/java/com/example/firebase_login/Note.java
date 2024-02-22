package com.example.firebase_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_login.databinding.ActivityNoteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Note extends AppCompatActivity {

    EditText title,context;
    ImageView save;

    TextView pageTitle,delete;

    boolean isEditMode=false;
    String newPageTitle,content,docId;

    ActivityNoteBinding binding;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        title=findViewById(R.id.title_note);
        context=findViewById(R.id.context);
        save=findViewById(R.id.save);
        save.setOnClickListener((view -> saveNote()));
        pageTitle=findViewById(R.id.pageTitle);
        delete=findViewById(R.id.delete);




        newPageTitle = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        title.setText(newPageTitle);
        context.setText(content);

        if (docId!=null )
        {
            isEditMode=true;
        }
        if (isEditMode)
        {
            pageTitle.setText("Edit Note");
            delete.setVisibility(View.VISIBLE);

        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference;
                documentReference = Utility.getCollectionRef().document(docId);

                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {

                            Toast.makeText(Note.this, "Note Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(Note.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    public void saveNote()
    {
        String Title = title.getText().toString();
        String Context = context.getText().toString();
        if (Title.isEmpty())
        {
            title.setError("Title is Required!!");
            return;
        }
        noteModel noteModel = new noteModel();
        android.icu.text.SimpleDateFormat ts = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new Date();
        String timestamp = ts.format(date);
        noteModel.setTITLE(Title);
        noteModel.setCONTEXT(Context);
       noteModel.setTimestamp(timestamp);

        saveToDB(noteModel);

    }
    public void saveToDB(noteModel noteModel)
    {
       DocumentReference documentReference;
       if (isEditMode)
       {
           documentReference = Utility.getCollectionRef().document(docId);

       }
       else
       {
           documentReference = Utility.getCollectionRef().document();

       }
       documentReference.set(noteModel).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful())
               {
                   Toast.makeText(Note.this, "Note Added Successfully!!", Toast.LENGTH_SHORT).show();
                   finish();
               }
               else {
                   Toast.makeText(Note.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       });

    }


}