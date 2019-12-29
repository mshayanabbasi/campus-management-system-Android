package com.example.campussystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    Context mContext;
    List<UserMoreDetails> mData;
    public StudentAdapter(Context context, List<UserMoreDetails> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_student_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sName.setText(mData.get(position).getUserName());
        holder.sQualification.setText(mData.get(position).getQualification());
        holder.sUnivesity.setText(mData.get(position).getUniversity());
        holder.sGpa.setText(mData.get(position).getGpa());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sName,sQualification,sUnivesity,sGpa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sName=(TextView)itemView.findViewById(R.id.nameStudent);
            sQualification=(TextView)itemView.findViewById(R.id.qualificationStudent);
            sUnivesity=(TextView)itemView.findViewById(R.id.universityStudent);
            sGpa=(TextView)itemView.findViewById(R.id.studentGpa);
        }
    }
}
