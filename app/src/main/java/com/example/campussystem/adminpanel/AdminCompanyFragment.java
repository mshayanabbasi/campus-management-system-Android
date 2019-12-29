package com.example.campussystem.adminpanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.CompanyPanel.CompanyUserMoreDetail;
import com.example.campussystem.R;
import com.example.campussystem.adminpanel.adminadapters.AdminCompanyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminCompanyFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    List<CompanyUserMoreDetail> jobDetail;
    AdminCompanyAdapter adapter;
    DatabaseReference ref;
    FirebaseAuth mAuth;

    public AdminCompanyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin_company_list, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewCompany);
        adapter = new AdminCompanyAdapter(getContext(), jobDetail);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (jobDetail == null) {
            jobDetail = new ArrayList<>();
        }
        ref= FirebaseDatabase.getInstance().getReference().child("All post").child("userrs");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CompanyUserMoreDetail companyUserMoreDetail = dataSnapshot.getValue(CompanyUserMoreDetail.class);
                if (companyUserMoreDetail.getGenere().equals("Company")) {
                    jobDetail.add(companyUserMoreDetail);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (CompanyUserMoreDetail detail:jobDetail) {
                    if (key.equals(detail.getId())) {
                        int index = jobDetail.indexOf(detail);
                        jobDetail.remove(index);
                        adapter.notifyItemRemoved(index);
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
