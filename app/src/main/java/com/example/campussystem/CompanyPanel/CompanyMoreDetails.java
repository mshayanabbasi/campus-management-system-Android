package com.example.campussystem.CompanyPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campussystem.LogInSignUp.LogIn;
import com.example.campussystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CompanyMoreDetails extends AppCompatActivity {
    TextView company,comName;
    EditText address,companyType,contacts;
    Button btnReg;
    DatabaseReference dr;
    FirebaseAuth mAuth;
    String name;
    String type;
    String email;
    String password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_more_details);
        progressBar=(ProgressBar) findViewById(R.id.progressbarCompany);
        mAuth=FirebaseAuth.getInstance();
        company=(TextView)findViewById(R.id.emailCompany);
        address=(EditText)findViewById(R.id.address);
        companyType=(EditText)findViewById(R.id.companyType);
        contacts=(EditText)findViewById(R.id.editTextContact);
        comName=(TextView)findViewById(R.id.cName);
        btnReg=(Button)findViewById(R.id.buttonRegister);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        type = intent.getStringExtra("Type");
        email = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");
        company.setText(email);
        comName.setText(name);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void add() {
        final String companyTypes = companyType.getText().toString().trim();
        final String contact = contacts.getText().toString().trim();
        final String cAddress = address.getText().toString().trim();
        if (companyTypes.isEmpty()) {
            companyType.setError("Name Required");
            companyType.requestFocus();
        }
        if (contact.isEmpty()) {
            contacts.setError("Contact Required");
            contacts.requestFocus();
        }
        if (cAddress.isEmpty()) {
            address.setError("Address Required");
            address.requestFocus();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "you are already register", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        String userID = mAuth.getCurrentUser().getUid();
                        dr = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(userID);
                        CompanyUserMoreDetail detailCompany = new CompanyUserMoreDetail(userID, name, email, cAddress, companyTypes, contacts, type, password);
                        dr.setValue(detailCompany);
                        finish();
                        startActivity(new Intent(CompanyMoreDetails.this, LogIn.class));
                    }
                }

            });
        }
    }
}
