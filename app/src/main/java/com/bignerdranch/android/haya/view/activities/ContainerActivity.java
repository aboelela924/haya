package com.bignerdranch.android.haya.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.view.fragments.BurnerCodeFragment;
import com.bignerdranch.android.haya.view.fragments.ConversationsFragment;
import com.bignerdranch.android.haya.view.fragments.RandomChatFragment;
import com.bignerdranch.android.haya.view.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity {
    private static final String USER = "USER";
    private User mUser;

    public static Intent newIntent(Context ctx, User user){
        Intent i = new Intent(ctx, ContainerActivity.class);
        i.putExtra(USER, user);

        CurrentUser.user = user;
        GetSocket.getInstance(user.getAccessToken());
        Log.i("User is: ", user.getAccessToken() + "-" + user.getId());


        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Intent i = getIntent();
        if(i != null){
            mUser = i.getParcelableExtra(USER);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment burnerCodeFragment = fm.findFragmentById(R.id.container_fragment);

        if(burnerCodeFragment == null){
            burnerCodeFragment = BurnerCodeFragment.newInstance(mUser);
            fm.beginTransaction()
                    .replace(R.id.container_fragment, burnerCodeFragment)
                    .commit();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment fragment = null;

                        switch(menuItem.getItemId()){
                            case R.id.burner_code_item:
                                fragment = BurnerCodeFragment.newInstance(mUser);
                                break;
                            case R.id.conversations_item:
                                fragment = ConversationsFragment.newInstance(mUser);
                                break;
                            case R.id.random_chat_item:
                                fragment = new RandomChatFragment();
                                break;
                            case R.id.settings_item:
                                fragment = new SettingsFragment();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
                        return true;
                    }
                };
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }
}
