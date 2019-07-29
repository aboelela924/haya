package com.bignerdranch.android.haya.model.repo.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Message;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Subscriber;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.TimeConverters;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.ChatDao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.MessageDao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.SubscriberDao;

@Database(entities = {Subscriber.class, Chat.class, Message.class}, version = 1)
@TypeConverters({TimeConverters.class})
public abstract class HayaDatabase extends RoomDatabase {
    public abstract SubscriberDao subscriber_dao();
    public abstract ChatDao chat_dao();
    public abstract MessageDao message_dao();
    public static final String NAME = "HaYaDatabase";
}
