package com.bignerdranch.android.haya.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.view.fragments.BurnerCodeFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.ServerSocket;

public class ContainerActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.burner_code_item:

                    case R.id.conversations_item:
                    case R.id.random_chat_item:
                    case R.id.settings_item:
                    default: return false;
                }
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        Fragment burnerCodeFragment = fm.findFragmentById(R.id.container_fragment);

        if(burnerCodeFragment == null){
            burnerCodeFragment = new BurnerCodeFragment();
            fm.beginTransaction()
                    .replace(R.id.container_fragment, burnerCodeFragment)
                    .commit();
        }
    }
}
