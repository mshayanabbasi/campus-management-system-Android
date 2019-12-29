package com.example.campussystem.adminpanel.adminadapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campussystem.CompanyPanel.CompanyMain.JobActivity;
import com.example.campussystem.CompanyPanel.CompanyUserMoreDetail;
import com.example.campussystem.R;
import com.example.campussystem.adminpanel.AdminCompanyFragment;
import com.example.campussystem.adminpanel.AdminJobActivity;
import com.example.campussystem.adminpanel.AdminJobFragments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCompanyAdapter extends RecyclerView.Adapter<AdminCompanyAdapter.MyViewHolder> {
    Activity activity;
    Context context;
    List<CompanyUserMoreDetail> mData=new ArrayList<>();
    FirebaseAuth nAuth;
    public AdminCompanyAdapter(Context context, List<CompanyUserMoreDetail> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_company_list, parent, false);
        AdminCompanyAdapter.MyViewHolder viewHolder = new AdminCompanyAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String id=mData.get(position).getId();
        holder.cName.setText(mData.get(position).getName());
        holder.cType.setText(mData.get(position).getCompanyType());
        holder.cAddress.setText(mData.get(position).getAddress());
        holder.cContact.setText(mData.get(position).getContacts());
        holder.buttonJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminJobActivity.class);
                intent.putExtra("Id", mData.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(mData.get(position).getEmail(),position,mData.get(position).getPassword());
                notifyDataSetChanged();

            }
        });
    }

    private void deleteArtist(String emails, final int position, final String password) {
        nAuth = FirebaseAuth.getInstance();
        nAuth.signInWithEmailAndPassword(emails, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.activity_update, null);
                    builder.setView(dialogView);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    String uid = nAuth.getCurrentUser().getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child("company").child(uid);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String key =  dataSnapshot.child("name").getValue(String.class);
                            builder.setTitle("Delete"+key);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Button no = (Button)dialogView.findViewById(R.id.btnNo);
                    Button dels = (Button)dialogView.findViewById(R.id.btnDeletes);
                    dels.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider.getCredential(nAuth.getCurrentUser().getEmail(), password);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.delete();
                                        delete(mData.get(position).getId());
                                        alertDialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(context.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context.getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void delete(final String id) {
        final DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(id).child("jobs");
        ref3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JobActivity activity = dataSnapshot.getValue(JobActivity.class);
                if (activity.getId().equals(id)) {
                    String idd = activity.getjId();
                    DatabaseReference ref4=FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(id).child("jobs").child(idd).child("Applied Students");
                    ref4.removeValue();
                    Toast.makeText(context, ""+id, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(id).child("jobs");
        ref1.removeValue();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("All post").child("users").child(id);
        ref.removeValue();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cName,cType,cAddress,cContact;
        Button del,buttonJobs;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cName=(TextView)itemView.findViewById(R.id.nameCompanys);
            cType=(TextView)itemView.findViewById(R.id.typeCompanys);
            cAddress=(TextView)itemView.findViewById(R.id.addressCompanys);
            cContact=(TextView)itemView.findViewById(R.id.contactCompanys);
            del=(Button)itemView.findViewById(R.id.btnDelete);
            buttonJobs=(Button)itemView.findViewById(R.id.buttonJobs);
        }
    }
}
