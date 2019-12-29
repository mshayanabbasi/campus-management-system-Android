package com.example.campussystem.LogInSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.campussystem.CompanyPanel.CompanyMoreDetails;
import com.example.campussystem.R;
import com.example.campussystem.StudentsMoreDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUp extends AppCompatActivity {
    EditText editUser,editEmail,editPassword;
    Button btnLogin,btnRegister;
    Spinner spinnerGeneres;
    FirebaseAuth mAuth;
    DatabaseReference dataRef;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressbarSignUp);
        editUser=(EditText)findViewById(R.id.userName);
        editEmail=(EditText)findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        spinnerGeneres=(Spinner)findViewById(R.id.spinnerGenres);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });
    }

    private void Add() {
        final String userName = editUser.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String genre=spinnerGeneres.getSelectedItem().toString();
        if (userName.isEmpty()) {
            editUser.setError("Name Required");
            editUser.requestFocus();
        }
        if (email.isEmpty()) {
            editEmail.setError("Email Required");
        }
        if(genre.isEmpty())
        {
            Toast.makeText(SignUp.this,"please select spinner",Toast.LENGTH_LONG).show();
            editEmail.setError("please enter type");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("please enter valid email");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editPassword.setError("Password Required");
        }
        if (password.length() < 6) {
            editPassword.setError("please enter 6 digits");
            editPassword.requestFocus();
            return;
        }
        if(genre.equals("Student")){
            Intent intent=new Intent(SignUp.this, StudentsMoreDetail.class);
            intent.putExtra("Name",userName);
            intent.putExtra("Type",genre);
            intent.putExtra("Email",email);
            intent.putExtra("Password",password);
            finish();
            startActivity(intent);}
        else {
            Intent intent=new Intent(SignUp.this, CompanyMoreDetails.class);
            intent.putExtra("Name",userName);
            intent.putExtra("Type",genre);
            intent.putExtra("Email",email);
            intent.putExtra("Password",password);
            finish();
            startActivity(intent);
        }
    }

}
