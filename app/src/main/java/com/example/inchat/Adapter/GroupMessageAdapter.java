package com.example.inchat.Adapter;

import android.content.Context;
import android.text.Layout;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Models.GroupModelForMessages;
import com.example.inchat.Models.Message;
import com.example.inchat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GroupMessageAdapter extends RecyclerView.Adapter{

    ArrayList<GroupModelForMessages> groupmessages;
    Context context;
    int senderviewtype = 1;
    int recieverviewtype = 2;
    String curusr;

    public GroupMessageAdapter(ArrayList<GroupModelForMessages> groupmessages, Context context) {
        this.groupmessages = groupmessages;
        this.context = context;
    }

    public GroupMessageAdapter(String curusr){
        this.curusr = curusr;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == senderviewtype){
            View view = LayoutInflater.from(context).inflate(R.layout.sender, parent, false);
            return new gsenderviewholder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.reciever, parent, false);
            return new grecieverviewholder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(groupmessages.get(position)
                .getSenid()
                .equals(FirebaseAuth.getInstance().getUid()))
            return senderviewtype;
        else
            return recieverviewtype;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupModelForMessages messag = groupmessages.get(position);
        if(holder.getClass() == gsenderviewholder.class){
            ((gsenderviewholder)holder).snrmsg.setText(messag.getMsg());
        }else{
            ((grecieverviewholder)holder).recievermsg.setText(messag.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return groupmessages.size();
    }

    public class grecieverviewholder extends RecyclerView.ViewHolder{
        TextView recievermsg;

        public grecieverviewholder(@NonNull View itemView) {
            super(itemView);
            recievermsg = itemView.findViewById(R.id.recievertext);
        }
    }

    public class gsenderviewholder extends RecyclerView.ViewHolder{
        TextView snrmsg;
        public gsenderviewholder(@NonNull View itemView) {
            super(itemView);
            snrmsg = itemView.findViewById(R.id.sendertext);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh-mm", cal).toString();
        return date;
    }
}
