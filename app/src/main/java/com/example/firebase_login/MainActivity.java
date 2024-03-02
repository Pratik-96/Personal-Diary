package com.example.firebase_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_login.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
EditText name,Pass1,Pass2,email;
TextView reg;
CheckBox chkbox;

ProgressBar progressBar;

ActivityMainBinding binding;

Button button;
FirebaseAuth mAuth;

TextInputLayout passly,mailly,cnfpassly;


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent home=new Intent(getApplicationContext(),Homepage.class);
            startActivity(home);
            finish();
        }
    }

    final int[] checked = new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.name);
        chkbox = findViewById(R.id.checkBox);
        reg=findViewById(R.id.noacc);
        mailly=findViewById(R.id.emailLayout);
        cnfpassly=findViewById(R.id.cnfpassLayout);
        Pass1=(EditText) findViewById(R.id.password);
        Pass2=(EditText) findViewById(R.id.cnfpass);
        passly=findViewById(R.id.pasLayout);
        email = (EditText)findViewById(R.id.email);
        progressBar=findViewById(R.id.progressBar);
        String txt = "<b>I agree to the <a href='www.google.com'>Terms & Conditions</a></b>";
        chkbox.setText(Html.fromHtml(txt));
        String txt2 = "<b>Already have an account ? <a href=''>Log In</a></b>";
        reg.setText(Html.fromHtml(txt2));
        button=findViewById(R.id.button);
        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)  //If check Box is Checked or not
                {
                    checked[0] = 1;
                } else {
                    checked[0] = 0;
                }

            }
        });


        Pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String pass = charSequence.toString();
                    if(pass.length()>=8)
                    {
                        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                        Matcher matcher = pattern.matcher(pass);
                        boolean iscontainspc=matcher.find();
                        if (iscontainspc)
                        {
                           Pass1.setError(null);

                        }
                        else {
                            Pass1.setError("Weak Password!!");
                        }
                    }
                    else
                    {
                        Pass1.setError("Password Length Must be atleast 8 Characters long");
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
      Pass2.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              String key = Pass1.getText().toString();
              String key2 = Pass2.getText().toString();
              if (!key.equals(key2)) {         // Password checking
                  Pass2.setError("Passwords are not matching!!");
              } else {
                  Pass2.setError(null);
              }
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });
      email.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              String mail = charSequence.toString();
              if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                  email.setError(null);
              } else {
                 email.setError("Invalid Email!!"); }
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });

    }
    public void register(View v){
        Intent act2 = new Intent(this,Log_in.class);            // Login Listener
        startActivity(act2);
        finish();
    }
    public void signup(View view) {
//        Check box Checking && Name checking
        boolean flag = true;
        String key = Pass1.getText().toString();
        String key2 = Pass2.getText().toString();
       inProgress(true);
        if (!key.equals(key2)) {         // Password checking
            flag = false;
//            progressBar.setVisibility(View.GONE);
        } else {
            flag = true;
        }
       String mail=email.getText().toString();
        // Email Validation
     
        boolean mailchk = false;

        if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailchk = true;
        } else {
            mailchk = false;
//            progressBar.setVisibility(View.GONE);
        }
String str = name.getText().toString();
//


        if (checked[0] == 1 && flag && mailchk && !str.isEmpty()) {

            String User_Name = name.getText().toString();
            String User_Email = email.getText().toString();
            String User_Pass = Pass2.getText().toString();


            String password = Pass2.getText().toString();
           
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {


                                if (FirebaseAuth.getInstance().getCurrentUser()!=null)
                                {

                                    if (!User_Name.isEmpty())
                                    {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(User_Name.toString())
                                                .build();
                                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Log.d("uname", "Name updated..: ");
                                                }
                                            }
                                        });
                                    }
                                }


                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(MainActivity.this, "Signing Up..", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(getApplicationContext(), Log_in.class);
                                startActivity(home);
                                finish();
                                inProgress(false);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                inProgress(false);

                            }
                        }
                    });

            
        }
//                else
//                {
//                    Toast.makeText(this, "SignUp Failed..", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else
//            {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(this, "User Already Exists , Please Login to continue..", Toast.LENGTH_SHORT).show();
//            }


//                    Log.d("btn", "onClick: Button is working properly..");


        else if (checked[0] == 0) {
            Toast.makeText(MainActivity.this, "Please Accept our Terms And Conditions.", Toast.LENGTH_SHORT).show();
//                    Log.e("error", "onClick: Button is not working properly.." );

            inProgress(true);



        }
        else {
            button.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Please Enter all the fields Correctly!!", Toast.LENGTH_SHORT).show();
            button.setVisibility(View.VISIBLE);
        }
    }
    public void inProgress(boolean progress)
    {
        if (progress)
        {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

}