package com.example.campussystem.CompanyPanel.CompanyMain;

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

public class CompanyProfileFragment extends Fragment {
    TextView cName, cType, cAddress;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    String names, types, address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_company_profile_tab, container, false);
        cName = (TextView)v.findViewById(R.id.companyName);
        cType = (TextView)v.findViewById(R.id.companyType);
        cAddress = (TextView)v.findViewById(R.id.address);
        cName.setText(names);
        cType.setText(types);
        cAddress.setText(address);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names= valueOf(dataSnapshot.child("name").getValue());
                types= valueOf(dataSnapshot.child("companyType").getValue());
                address = valueOf(dataSnapshot.child("address").getValue());
                cName.setText(names);
                cType.setText(types);
                cAddress.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
