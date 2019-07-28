package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;

public class GroupChatSettingsAdminActivity extends AppCompatActivity {

    private static final String IS_PRIVATE = "IS_PRIVATE";
    private static final String USER = "USER";
    private static final String ROOM = "ROOM";

    public static Intent newIntent(Context ctx, boolean isPrivate, User user, Room room){
        Intent i = new Intent(ctx, GroupChatSettingsAdminActivity.class);
        i.putExtra(USER, user);
        i.putExtra(ROOM, room);
        i.putExtra(IS_PRIVATE, isPrivate);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_settings_admin);

    }
}
