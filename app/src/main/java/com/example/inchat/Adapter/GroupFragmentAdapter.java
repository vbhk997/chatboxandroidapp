package com.example.inchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Activities.GroupMessage;
import com.example.inchat.Models.Group;
import com.example.inchat.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentAdapter.ViewHolder>{

    private Context mcontext;
    private List<Group> group;
    FirebaseUser firebaseUser;
    DatabaseReference ref;
    String keymap, gname;

    public GroupFragmentAdapter(Context mcontext, List<Group> group) {
        this.group = group;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public GroupFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.groupfragmenttoolitem, parent, false);
        return new GroupFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupFragmentAdapter.ViewHolder holder, int position) {
        Group groupnew = group.get(position);
        holder.username.setText(groupnew.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, GroupMessage.class);
                intent.putExtra("gtky", groupnew.getKey());
                intent.putExtra("grnm", groupnew.getName());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return group.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView add, usrepic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.grusrename);
            add = itemView.findViewById(R.id.gaddtogroup);
            usrepic = itemView.findViewById(R.id.grusrepic);
        }
    }

    //public class ViewHolder extends RecyclerView.ViewHolder {
      //  public ViewHolder(@NonNull View itemView) {
        //    super(itemView);
       // }
   // }
}
