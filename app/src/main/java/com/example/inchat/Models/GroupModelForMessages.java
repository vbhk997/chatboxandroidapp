package com.example.inchat.Models;

public class GroupModelForMessages {
    private String Msg, senid;
    private long tmstmp;
    public GroupModelForMessages(String Msg, String senid, long tmstmp){
        this.Msg = Msg;
        this.senid = senid;
        this.tmstmp = tmstmp;
    }

    public GroupModelForMessages(){}

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getSenid() {
        return senid;
    }

    public void setSenid(String senid) {
        this.senid = senid;
    }

    public long getT() {
        return tmstmp;
    }

    public void setT(long t) {
        this.tmstmp = tmstmp;
    }
}
