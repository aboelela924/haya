package com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;

import java.util.List;

@Dao
public interface SubscriberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubscriber(SubscriberDB subscriberDB);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubscribers(SubscriberDB... subscriberDBS);

    @Query("delete from subscriber where chat_id = :chatId")
    void deleteSubscribersOfChat(String chatId);

    @Query("delete from subscriber where id = :id")
    void deleteSubscriberById(String id);

    @Query("update subscriber set is_leave = :isLeave where id = :subscriberId ")
    void updateIsLeave(String subscriberId, boolean isLeave);

    @Query("update subscriber set is_secret = :isSecret where id = :subscriberId")
    void updateIsSecret(String subscriberId, boolean isSecret);

    @Query("update subscriber set custom_room_name = :customName where id = :subscriberId")
    void updateCustomName(String subscriberId, String customName);

    @Query("select * from subscriber where chat_id = :chatId")
    List<SubscriberDB> getAllSubscribersOfChat(String chatId);

    @Query("select * from subscriber where id = :subscriberId")
    SubscriberDB getSubscriberId(String subscriberId);

}
