package com.example.inchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class navactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navactivity);

        BottomNavigationView bottomnav = findViewById(R.id.navview);
        bottomnav.setOnItemSelectedListener(navlistener);
    }

    private NavigationBarView.OnItemSelectedListener navlistener =
            new BottomNavigationView.OnItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment newfrag = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            newfrag = new homenewfragmenter();
                            break;

                        case R.id.navigation_dashboard:
                            newfrag = new serachboxnewfragmenter();
                            break;

                        case R.id.navigation_notifications:
                            newfrag = new statusnewfragmenter();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.boxfragment,newfrag).commit();
                    return true;
                }
            };
}