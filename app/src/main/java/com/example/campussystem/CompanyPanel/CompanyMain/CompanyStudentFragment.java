package com.example.campussystem.CompanyPanel.CompanyMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.R;
import com.example.campussystem.StudentAdapter;
import com.example.campussystem.UserMoreDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompanyStudentFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private List<UserMoreDetails> studentDetail;
    StudentAdapter adapter;
    DatabaseReference ref;
    public CompanyStudentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_company_student_list, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.profileRecyclerView);
        adapter=new StudentAdapter(getContext(), studentDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentDetail=new ArrayList<>();
        ref= FirebaseDatabase.getInstance().getReference().child("All post").child("users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserMoreDetails userMoreDetails = dataSnapshot.getValue(UserMoreDetails.class);
                if (userMoreDetails.getGenere().equals("Student")){
                    studentDetail.add(userMoreDetails);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for(UserMoreDetails aq:studentDetail) {
                    if (key.equals(aq.getId())) {
                        studentDetail.remove(aq);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
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
