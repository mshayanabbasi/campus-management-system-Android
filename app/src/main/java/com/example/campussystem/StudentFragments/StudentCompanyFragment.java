package com.example.campussystem.StudentFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.CompanyPanel.CompanyMoreDetails;
import com.example.campussystem.CompanyPanel.CompanyUserMoreDetail;
import com.example.campussystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentCompanyFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private ArrayList<CompanyUserMoreDetail> jobDetail;
    StudentCompanyAdapter adapter;
    DatabaseReference ref;

    public StudentCompanyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin_company_list, container, false);
        myRecyclerView=(RecyclerView)v.findViewById(R.id.recyclerViewCompany);
        adapter=new StudentCompanyAdapter(getContext(),jobDetail);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobDetail=new ArrayList<>();
        ref= FirebaseDatabase.getInstance().getReference().child("All post").child("users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CompanyUserMoreDetail companyUserMoreDetail = dataSnapshot.getValue(CompanyUserMoreDetail.class);
                if (companyUserMoreDetail.getGenere().equals("Company")) {
                    jobDetail.add(companyUserMoreDetail);
                    adapter.notifyDataSetChanged();
                }
                if (dataSnapshot.equals(null)) {
                    Toast.makeText(getContext(), "" +dataSnapshot, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                final String key = dataSnapshot.getKey();
                for (CompanyUserMoreDetail aq:jobDetail) {
                    if (key.equals(aq.getId())) {
                        jobDetail.remove(aq);
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
                Log.e("Tag", "error is"+databaseError);
            }
        });
    }
}
