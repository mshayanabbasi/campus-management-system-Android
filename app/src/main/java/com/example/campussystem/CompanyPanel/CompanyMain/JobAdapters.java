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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class JobAdapters extends RecyclerView.Adapter<JobAdapters.MyViewHolder> {
    FirebaseAuth mAuth;
    DatabaseReference ref;
    Context context;
    List<JobActivity> mDatas;
    String names,qualifications,university;
    String comId,jId;
    String uid;

    public JobAdapters(Context context, List<JobActivity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public JobAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.custom_job_list_company, parent, false);
        JobAdapters.MyViewHolder viewHolder = new JobAdapters.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.jobs.setText(mDatas.get(position).getJob());

        holder.types.setText(mDatas.get(position).getJobType());
        holder.experiences.setText(mDatas.get(position).getJobExperience());
        holder.shifts.setText(mDatas.get(position).getJobShift());
        holder.salarys.setText(mDatas.get(position).getJobSalary());
        holder.btnStudentAppliedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comId= mDatas.get(position).getId();
                jId=mDatas.get(position).getjId();
                Intent intent=new Intent(context,AppliedStudentList.class);
                intent.putExtra("CId",comId);
                intent.putExtra("JId",jId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView jobs,types,experiences,shifts,salarys;

        Button btnStudentAppliedList;
        public MyViewHolder(View itemView) {
            super(itemView);
            jobs=(TextView)itemView.findViewById(R.id.jobs);
            types=(TextView)itemView.findViewById(R.id.typeJobs);
            experiences=(TextView)itemView.findViewById(R.id.experienceJobs);
            shifts=(TextView)itemView.findViewById(R.id.shiftJobs);
            salarys=(TextView)itemView.findViewById(R.id.salaryJobs);
            btnStudentAppliedList=(Button)itemView.findViewById(R.id.listAppliedStudents);
        }
    }
}
