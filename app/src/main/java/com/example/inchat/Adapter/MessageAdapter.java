package com.example.inchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Models.Message;
import com.example.inchat.R;
import com.example.inchat.databinding.RecieverBinding;
import com.example.inchat.databinding.SenderBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    ArrayList<Message> messagemodel;
    Context context;
    int senderviewtype = 1;
    int recieverviewtype = 2;

    public MessageAdapter(ArrayList<Message> messagemodel, Context context) {
        this.messagemodel = messagemodel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == senderviewtype){
            View view = LayoutInflater.from(context).inflate(R.layout.sender, parent, false);
            return new senderviewholder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.reciever, parent, false);
            return new recieverviewholder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(messagemodel.get(position).getSenderid().equals(FirebaseAuth.getInstance().getUid()))
            return senderviewtype;
        else
            return recieverviewtype;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messagemodel.get(position);
        if(holder.getClass() == senderviewholder.class)
        ((senderviewholder)holder).sendermsg.setText(message.getMessage());
        else
        ((recieverviewholder)holder).recievermsg.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagemodel.size() ;
    }

    public class recieverviewholder extends RecyclerView.ViewHolder{

        TextView recievermsg, recievertime;

        public recieverviewholder(@NonNull View itemView) {
            super(itemView);
            recievermsg = itemView.findViewById(R.id.recievertext);
            recievertime = itemView.findViewById(R.id.recievertime);
        }
    }

    public class senderviewholder extends RecyclerView.ViewHolder{

        TextView sendermsg, sendertime;

        public senderviewholder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sendertext);
            sendertime = itemView.findViewById(R.id.sendertime);
        }
    }

}
