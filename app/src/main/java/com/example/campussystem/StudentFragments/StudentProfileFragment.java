package com.example.campussystem.StudentFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.String.valueOf;

public class StudentProfileFragment extends Fragment {
    TextView sName,sQualification,sUniversity,sEmail;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    String names,qualifications,university,email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_student_profile, container, false);
        sName=(TextView)v.findViewById(R.id.nameStudent);
        sQualification=(TextView)v.findViewById(R.id.qualificationStudent);
        sUniversity=(TextView)v.findViewById(R.id.universityStudent);
        sEmail=(TextView)v.findViewById(R.id.emailStudent);

        sName.setText(names);
        sQualification.setText(qualifications);
        sUniversity.setText(university);
        sEmail.setText(email);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names=valueOf(dataSnapshot.child("userName").getValue());
                qualifications=valueOf(dataSnapshot.child("qualification").getValue());
                university=valueOf(dataSnapshot.child("university").getValue());
                email=valueOf(dataSnapshot.child("email").getValue());
                sName.setText(names);
                sQualification.setText(qualifications);
                sUniversity.setText(university);
                sEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
