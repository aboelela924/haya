package com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Subscriber;

import java.util.List;

@Dao
public interface Subscriber_dao {
    @Query("Select Chats.burnercode, Chats.secret_status, Chats.conversation_nickname, chats.admin_subscriber_id," +
            " chats.last_message_Date from Subscribers, Chats where Subscribers.chat_burnercode = Chats.burnercode and Subscribers.user_id = :user_id")
    List<Chat> select_all_chats_for_user(String user_id);
    @Query("Select * from Subscribers where chat_burnercode = :chatBurnerCode")
    List<Subscriber> select_all_subscribers_in_chat(String chatBurnerCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add_new_subscriber(Subscriber subscriber);
    @Delete
    void remove_subscriber(Subscriber subscriber);
    @Query("Delete from subscribers where subscriber_id = :id")
    void remove_subscriber_by_id(String id);
    @Query("Delete from subscribers where chat_burnercode = :burnercode")
    void remove_all_subscribers(String burnercode);
}
