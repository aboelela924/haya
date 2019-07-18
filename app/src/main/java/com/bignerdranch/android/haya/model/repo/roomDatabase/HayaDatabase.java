package com.bignerdranch.android.haya.model.repo.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Message;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Subscriber;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.TimeConverters;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.Chat_dao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.Message_dao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.Subscriber_dao;

@Database(entities = {Subscriber.class, Chat.class, Message.class}, version = 1)
@TypeConverters({TimeConverters.class})
public abstract class HayaDatabase extends RoomDatabase {
    public abstract Subscriber_dao subscriber_dao();
    public abstract Chat_dao chat_dao();
    public abstract Message_dao message_dao();
}
