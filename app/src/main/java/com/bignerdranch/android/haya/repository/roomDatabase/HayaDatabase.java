package com.bignerdranch.android.haya.repository.roomDatabase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.bignerdranch.android.haya.repository.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.Message;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.Subscriber;
import com.bignerdranch.android.haya.repository.roomDatabase.classes.TimeConverters;
import com.bignerdranch.android.haya.repository.roomDatabase.dataAccessObjects.Chat_dao;
import com.bignerdranch.android.haya.repository.roomDatabase.dataAccessObjects.Message_dao;
import com.bignerdranch.android.haya.repository.roomDatabase.dataAccessObjects.Subscriber_dao;

@Database(entities = {Subscriber.class, Chat.class, Message.class}, version = 1)
@TypeConverters({TimeConverters.class})
public abstract class HayaDatabase extends RoomDatabase {
    public abstract Subscriber_dao subscriber_dao();
    public abstract Chat_dao chat_dao();
    public abstract Message_dao message_dao();
}
