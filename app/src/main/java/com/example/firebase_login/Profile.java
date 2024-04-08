package com.example.firebase_login;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebase_login.databinding.ActivityProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Profile extends AppCompatActivity {


    ActivityProfileBinding binding;

    String selectedTime="";

    private void setNotificationTime(int i) {
        Intent alarmIntent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11); // 10:00 PM
        calendar.set(Calendar.MINUTE, 37);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);


    }
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

        binding.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String[] time = {"19:00","20:00","21:00","22:00","23:00"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this)
                        .setTitle("Select reminder time: ")
                        .setSingleChoiceItems(time, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                selectedTime = time[position];

                            }
                        }).setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int time=0;
                                switch (selectedTime){
                                    case "19:00":time=19;
                                    break;
                                    case "20:00":time=20;
                                    break;
                                    case "21:00":time=21;
                                    break;
                                    case "22:00":time=22;
                                    break;
                                    case "23:00":time=23;
                                    break;
                                }
                                setNotificationTime(time);
                                Toast.makeText(Profile.this, "Reminder set at "+selectedTime, Toast.LENGTH_SHORT).show();


                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

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