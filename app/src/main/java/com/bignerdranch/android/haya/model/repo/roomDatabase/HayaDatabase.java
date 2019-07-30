package com.bignerdranch.android.haya.model.repo.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.TimeConverters;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.ChatDao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.MessageDao;
import com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects.SubscriberDao;

@Database(entities = {SubscriberDB.class, ChatDB.class, MessageDB.class}, version = 1)
@TypeConverters({TimeConverters.class})
public abstract class HayaDatabase extends RoomDatabase {
    public abstract SubscriberDao subscriber_dao();
    public abstract ChatDao chat_dao();
    public abstract MessageDao message_dao();
    public static final String NAME = "HaYaDatabase";
}
