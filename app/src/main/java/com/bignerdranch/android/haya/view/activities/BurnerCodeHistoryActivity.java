package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;

import butterknife.ButterKnife;

public class BurnerCodeHistoryActivity extends AppCompatActivity {
    public static final String USER = "USER";

    public static Intent newIntent(Context ctx, User user){
        Intent i = new Intent(ctx, BurnerCodeHistoryActivity.class);
        i.putExtra(USER, user);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burner_code_history);
        ButterKnife.bind(this);
    }
}
