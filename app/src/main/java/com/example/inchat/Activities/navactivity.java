package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.inchat.Fragments.groupfragmenter;
import com.example.inchat.R;
import com.example.inchat.Fragments.homenewfragmenter;
import com.example.inchat.Fragments.serachboxnewfragmenter;
import com.example.inchat.Fragments.statusnewfragmenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class navactivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navactivity);

        BottomNavigationView bottomnav = findViewById(R.id.navview);
        bottomnav.setOnItemSelectedListener(navlistener);


        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutbuttonclick:
                mAuth.signOut();
                startActivity(new Intent(navactivity.this, MainActivity.class));
                finish();
                break;
            case R.id.profilepageclick:
                startActivity(new Intent(navactivity.this, Profile.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

                        case R.id.navigation_group:
                            newfrag = new groupfragmenter();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.boxfragment,newfrag).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
    }
}