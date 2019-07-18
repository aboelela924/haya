package com.bignerdranch.android.haya.view;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.repository.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.Message;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.Subscriber;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static HayaDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Chat chat;
        Subscriber subscriber;

        db = Room.databaseBuilder(this, HayaDatabase.class, "db").allowMainThreadQueries().build();
        Calendar c = Calendar.getInstance();

        Date d = c.getTime();
        chat = new Chat("ABC", "Test", null, d);
        db.chat_dao().add_new_chat(chat);

        chat = new Chat("ABCD", "Test2", null, d);
        db.chat_dao().add_new_chat(chat);

        chat = new Chat("ABCDE", "Test2", null, d);
        db.chat_dao().add_new_chat(chat);

        subscriber = new Subscriber("1", "1", "n", "ABC");
        db.subscriber_dao().add_new_subscriber(subscriber);
        subscriber = new Subscriber("2", "1", "m", "ABCD");
        db.subscriber_dao().add_new_subscriber(subscriber);

        List<Chat> lstChats = db.subscriber_dao().select_all_chats_for_user("1");
        int x= 3;
    }
}
