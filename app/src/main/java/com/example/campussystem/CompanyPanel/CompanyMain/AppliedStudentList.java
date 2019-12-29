package com.example.campussystem.CompanyPanel.CompanyMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.campussystem.R;
import com.example.campussystem.StudentFragments.ApplyJobActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppliedStudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<ApplyJobActivity> studentDetail;
    AppliedAdapters adapters;
    FirebaseAuth mAuth;
    DatabaseReference dr;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_student_list);

        recyclerView=(RecyclerView)findViewById(R.id.appliedRecyclerView);
        studentDetail= new ArrayList<>();
        Intent intent =  getIntent();
        final String compId = intent.getStringExtra("CId");
        final String jId = intent.getStringExtra("JId");
        Toast.makeText(AppliedStudentList.this, "", Toast.LENGTH_LONG).show();
        adapters=new AppliedAdapters(AppliedStudentList.this, studentDetail);
        recyclerView.setAdapter(adapters);
        manager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        dr = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(compId).child("jobs").child(jId).child("Applied Students");
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ApplyJobActivity userMoreDetails = dataSnapshot.getValue(ApplyJobActivity.class);
                studentDetail.add(userMoreDetails);
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
