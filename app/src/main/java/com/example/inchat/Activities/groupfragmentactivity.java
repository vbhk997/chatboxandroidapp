package com.example.inchat.Activities;

import android.os.Bundle;

import com.example.inchat.Fragments.groupfragment;

public class groupfragmentactivity extends navactivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new groupfragment()).commit();}
        }
    }

