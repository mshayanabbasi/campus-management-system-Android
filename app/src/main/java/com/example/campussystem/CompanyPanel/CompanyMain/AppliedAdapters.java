package com.example.campussystem.CompanyPanel.CompanyMain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.R;
import com.example.campussystem.StudentFragments.ApplyJobActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppliedAdapters extends RecyclerView.Adapter<AppliedAdapters.MyViewHolder> {
    Context context;
    List<ApplyJobActivity> mDatas;
    public AppliedAdapters(Context context, List<ApplyJobActivity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @Override
    public AppliedAdapters.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.company_applied_student, parent, false);
        AppliedAdapters.MyViewHolder vHolder = new AppliedAdapters.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sName.setText(mDatas.get(position).getName());
        holder.sQualification.setText(mDatas.get(position).getQualification());
        holder.sUnivesity.setText(mDatas.get(position).getUniversity());
        holder.sGpa.setText(mDatas.get(position).getStatus());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appId = mDatas.get(position).getAppId();
                String compId = mDatas.get(position).getCompId();
                String jId = mDatas.get(position).getjId();
                String name = mDatas.get(position).getName();
                String qualification = mDatas.get(position).getQualification();
                String status = mDatas.get(position).getStatus();
                String uid = mDatas.get(position).getUid();
                String university = mDatas.get(position).getUniversity();
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(compId).child("jobs").child(jId).child("Applied Students").child(uid);
                ApplyJobActivity accept = new ApplyJobActivity(compId, uid, name, qualification, university, jId, appId, "Rejected by the company");
                dr.setValue(accept);
                ((Activity)context).finish();
                Toast.makeText(context, "Rejected Job", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sName,sQualification,sUnivesity,sGpa;
        Button btnAccept,btnReject;
        public MyViewHolder(View itemView) {
            super(itemView);
            sName=(TextView)itemView.findViewById(R.id.nameStudentComp);
            sQualification=(TextView)itemView.findViewById(R.id.qualificationStudentComp);
            sUnivesity=(TextView)itemView.findViewById(R.id.universityStudentComp);
            sGpa=(TextView)itemView.findViewById(R.id.studentGpaComp);
            btnAccept=(Button)itemView.findViewById(R.id.btnAccept);
            btnReject=(Button)itemView.findViewById(R.id.btnReject);
        }
    }
}
