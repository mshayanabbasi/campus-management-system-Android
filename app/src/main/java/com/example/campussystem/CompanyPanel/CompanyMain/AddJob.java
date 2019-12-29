package com.example.campussystem.CompanyPanel.CompanyMain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJob extends AppCompatActivity {
    String uid;
    FirebaseAuth mAuth;
    String id;
    DatabaseReference ref;
    EditText edtJob,edtJobType,edtJobExperience,edtJobShift,edtJobSalary;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        mAuth=FirebaseAuth.getInstance();
        uid= mAuth.getCurrentUser().getUid();
        edtJob=(EditText)findViewById(R.id.job);
        edtJobType=(EditText)findViewById(R.id.jobType);
        edtJobExperience=(EditText)findViewById(R.id.jobExperience);
        edtJobShift=(EditText)findViewById(R.id.jobShift);
        edtJobSalary=(EditText)findViewById(R.id.jobSalary);
        btnSubmit=(Button)findViewById(R.id.jobSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobs();
            }
        });

    }

    private void addJobs() {
        String job = edtJob.getText().toString().trim();
        String type = edtJobType.getText().toString().trim();
        String experience = edtJobExperience.getText().toString().trim();
        String shift = edtJobShift.getText().toString().trim();
        String salary = edtJobSalary.getText().toString().trim();
        if (job.isEmpty()) {
            edtJob.setError("Job Required");
            edtJob.requestFocus();
        }
        if (type.isEmpty()) {
            edtJobType.setError("Type Required");
            edtJobType.requestFocus();
        }
        if (experience.isEmpty()) {
            edtJobExperience.setError("Experience Required");
            edtJobExperience.requestFocus();
        }
        if (shift.isEmpty()) {
            edtJobShift.setError("Shift Required");
            edtJobShift.requestFocus();
        }
        if (salary.isEmpty()) {
            edtJobSalary.setError("Salary Required");
            edtJobSalary.requestFocus();
        }
        else {
            ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(uid).child("jobs");
            String id = ref.push().getKey();
            JobActivity userActivitys = new JobActivity(id, uid, job, type, experience, shift, salary);
            ref.child(id).setValue(userActivitys);
            finish();
        }
    }
}
