package com.example.campussystem.StudentFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campussystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewApplyActivity extends AppCompatActivity {
    TextView job,type,experience,shift,salary,status;
    Button btnApply;
    String jobId;
    String uid;
    String compId;
    DatabaseReference ref;
    String jid;
    String statusUser;
    String uidApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_apply);
        job=(TextView)findViewById(R.id.jobNew);
        type=(TextView)findViewById(R.id.typeJobNew);
        experience=(TextView)findViewById(R.id.experienceJobNew);
        shift=(TextView)findViewById(R.id.shiftJobNew);
        salary=(TextView)findViewById(R.id.salaryJobNew);
        btnApply=(Button)findViewById(R.id.btnApplyNew);
        status=(TextView)findViewById(R.id.statusNew);
        final Intent intent=getIntent();
        final String names=intent.getStringExtra("name");
        final String qualifications=intent.getStringExtra("qualification");
        final String university=intent.getStringExtra("university");
        jobId=intent.getStringExtra("jobId");
        uid=intent.getStringExtra("uid");
        compId=intent.getStringExtra("compId");
        String jobs=intent.getStringExtra("job");
        String types=intent.getStringExtra("type");
        String experiences=intent.getStringExtra("experience");
        String shifts=intent.getStringExtra("shift");
        String salarys=intent.getStringExtra("salary");
        job.setText(jobs);
        type.setText(types);
        experience.setText(experiences);
        shift.setText(shifts);
        salary.setText(salarys);
        ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(compId).child("jobs").child(jobId).child("Applied Students").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                statusUser = statusUser.valueOf(dataSnapshot.child("status").getValue());
                jid = jid.valueOf(dataSnapshot.child("jId").getValue());
                if (statusUser!= null) {
                    status.setText(statusUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jid.equals(jobId)) {
                    Toast.makeText(getApplicationContext(), "already applied", Toast.LENGTH_LONG).show();
                }
                else  {
                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(compId).child("jobs").child(jobId).child("Applied Students");
                    String appid = ref2.push().getKey();
                    ApplyJobActivity applyJobActivity = new ApplyJobActivity(compId, uid, names, qualifications, university, jobId, appid, "you have applied for this job and it is pending");
                    ref2.child(uid).setValue(applyJobActivity);
                    Toast.makeText(getApplicationContext(), "applied", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
