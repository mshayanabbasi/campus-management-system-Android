package com.example.campussystem.StudentFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.CompanyPanel.CompanyMain.JobActivity;
import com.example.campussystem.CompanyPanel.CompanyMain.JobAdapter;
import com.example.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class StudentCompanyList extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<JobActivity> jobDetail;
    JobAdapter adapter;
    DatabaseReference ref;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_students_company_list, container, false);
        return v;
    }
}
