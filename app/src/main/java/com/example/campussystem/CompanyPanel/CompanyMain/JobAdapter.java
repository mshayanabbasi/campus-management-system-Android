package com.example.campussystem.CompanyPanel.CompanyMain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.R;
import com.example.campussystem.StudentFragments.NewApplyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {
    int i=0;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    Context context;
    List<JobActivity> mDatas;
    String names,qualifications,university;
    String compId,jobId;
    String job,type,experience,shift,salary;
    String uids;
    String jids;
    String uid;

    public JobAdapter(Context context, List<JobActivity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.custom_job_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.job.setText(mDatas.get(position).getJob());
        holder.type.setText(mDatas.get(position).getJobType());
        holder.experience.setText(mDatas.get(position).getJobExperience());
        holder.shift.setText(mDatas.get(position).getJobShift());
        holder.salary.setText(mDatas.get(position).getJobSalary());
        holder.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobId=mDatas.get(position).getjId();
                job=mDatas.get(position).getJob();
                type=mDatas.get(position).getJobType();
                shift=mDatas.get(position).getJobShift();
                salary=mDatas.get(position).getJobSalary();
                experience=mDatas.get(position).getJobExperience();
                mAuth=FirebaseAuth.getInstance();
                uid=mAuth.getCurrentUser().getUid();
                compId=mDatas.get(position).getId();
                jobId=mDatas.get(position).getjId();
                ref= FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(uid);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        names= names.valueOf(dataSnapshot.child("userName").getValue());
                        qualifications=qualifications.valueOf(dataSnapshot.child("qualification").getValue());
                        university=university.valueOf(dataSnapshot.child("university").getValue());
                        Intent intent = new Intent(context, NewApplyActivity.class);
                        intent.putExtra("jobId", jobId);
                        intent.putExtra("uid", uid);
                        intent.putExtra("compId", compId);
                        intent.putExtra("name", names);
                        intent.putExtra("qualification", qualifications);
                        intent.putExtra("university", university);
                        intent.putExtra("jobid",jobId);
                        intent.putExtra("job",job);
                        intent.putExtra("type",type);
                        intent.putExtra("shift",shift);
                        intent.putExtra("salary",salary);
                        intent.putExtra("experience",experience);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView job,type,experience,shift,salary;
        Button btnApply;
        TextView status;

        public MyViewHolder(View itemView) {
            super(itemView);
            job=(TextView)itemView.findViewById(R.id.job);
            type=(TextView)itemView.findViewById(R.id.typeJob);
            experience=(TextView)itemView.findViewById(R.id.experienceJob);
            shift=(TextView)itemView.findViewById(R.id.shiftJob);
            salary=(TextView)itemView.findViewById(R.id.salaryJob);
            btnApply=(Button)itemView.findViewById(R.id.btnApply);
            status=(TextView) itemView.findViewById(R.id.status);
        }
    }
}
