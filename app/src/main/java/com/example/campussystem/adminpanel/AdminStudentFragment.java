package com.example.campussystem.adminpanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.LogInSignUp.LogIn;
import com.example.campussystem.R;
import com.example.campussystem.UserMoreDetails;
import com.example.campussystem.adminpanel.adminadapters.AdminStudentAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminStudentFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<UserMoreDetails> studentDetail;
    AdminStudentAdapter adapter;
    DatabaseReference ref;
    private static final String TAG = "AdminStudentFragment";
    public AdminStudentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin_student_list, container, false);
        myRecyclerView=(RecyclerView)v.findViewById(R.id.adminStudentRecyclerView);
        adapter=new AdminStudentAdapter(getContext(),studentDetail);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentDetail=new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "OnChildAdded: "+dataSnapshot.getValue().toString());
                UserMoreDetails userMoreDetails = dataSnapshot.getValue(UserMoreDetails.class);
                if (userMoreDetails.getGenere().equals("Student")) {
                    studentDetail.add(userMoreDetails);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Log.d(TAG, "onChildRemoved: "+dataSnapshot.getValue().toString());
                for (UserMoreDetails aq:studentDetail) {
                    if (key.equals(aq.getId())) {
                        studentDetail.remove(aq);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
