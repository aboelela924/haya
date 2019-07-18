package com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Message;

import java.util.List;


@Dao
public interface Message_dao {
    @Query("Select * from Messages where sent_status = 0 and subscriber_id = :subscriberId")
    List<Message> get_all_unsent_messages(String subscriberId);

    @Query("Select * from Messages where chat_burnercode = :chatBurnerCode")
    List<Message> get_all_chat_messages(String chatBurnerCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add_new_message(Message message);

    @Query("Update Messages set sent_status = 1 where id = :message_id")
    void update_sent_status(int message_id);

    @Delete
    void delete_message(Message message);

    @Query("Delete from Messages where subscriber_id = :subscriberID and chat_burnercode = :chatBurnerCode")
    void delete_all_messages_for_subscriber(String subscriberID, String chatBurnerCode);

    @Query("Delete from Messages where chat_burnercode = :chatBurnerCode")
    void delete_all_messages_in_chat(String chatBurnerCode);

}
