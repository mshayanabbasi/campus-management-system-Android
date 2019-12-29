package com.example.campussystem.LogInSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.campussystem.CompanyPanel.CompanyMain.Main2Activity;
import com.example.campussystem.R;
import com.example.campussystem.StudentsActivity;
import com.example.campussystem.adminpanel.Admins;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText editEmail,editPassword;
    Button btnLogin,btnSignUp;
    DatabaseReference dataRef;
    ProgressBar progressBar;
    String admin="admin@gmail.com";
    String pass="123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        progressBar=(ProgressBar)findViewById(R.id.progressbarLogin);
        editEmail=(EditText)findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.editPassword);
        btnLogin=(Button)findViewById(R.id.loginBtn);
        btnSignUp=(Button)findViewById(R.id.signUpBtn);
        mAuth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("Email Required");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("please enter valid email");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editPassword.setError("Password Required");
            editPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editPassword.setError("please enter 6 digit");
            editPassword.requestFocus();
            return;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(final Task<AuthResult> task) {
                    final String id;
                    if (task.isSuccessful()) {
                        id = mAuth.getCurrentUser().getUid();
                        dataRef = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(id);
                        dataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    String key = dataSnapshot.child("genere").getValue(String.class);
                                    if (key.equals("Admin")) {
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                        startActivity(new Intent(LogIn.this, Admins.class));
                                    }
                                    if (key.equals("Student")) {
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                        startActivity(new Intent(LogIn.this, StudentsActivity.class));
                                    }
                                    if (key.equals("Company")) {
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                        startActivity(new Intent(LogIn.this, Main2Activity.class));
                                    }
                                    else  {

                                    }
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("TAG", String.valueOf(task.getException()));
                            }
                        });
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        editEmail.setText("");
                        editPassword.setText("");
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
