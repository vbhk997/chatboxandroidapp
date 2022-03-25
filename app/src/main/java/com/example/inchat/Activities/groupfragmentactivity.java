package com.example.inchat.Activities;

import android.os.Bundle;

import com.example.inchat.Adapter.GroupFragmentAdapter;
import com.example.inchat.Fragments.groupfragmenter;

public class groupfragmentactivity extends navactivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new groupfragmenter()).commit();}
        }
    }

