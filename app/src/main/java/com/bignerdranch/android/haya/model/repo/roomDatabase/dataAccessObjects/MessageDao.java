package com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;

import java.util.List;


@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(MessageDB messageDB);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessages(MessageDB... messageDBS);

    @Query("update message set is_deleted = :isDeleted where id = :messageId")
    void updateIsDeleted(String messageId, boolean isDeleted);

    @Query("delete from message where message.subscriber_id = :subscriberId")
    void deleteAllMessagesForASubscriber(String subscriberId);

    @Query("delete from message where message.chat_id = :chatId")
    void deleteAllMessageOfChat(String chatId);

    @Query("update message set updated_at = :updateAt where id = :messageId")
    void updateUpdateAt(String messageId, String updateAt);

    @Query("delete from message where id = :messageId")
    void deleteMessageById(String messageId);

    @Query("select * from message where message.chat_id = :chatId")
    List<MessageDB> getAllMessageForChat(String chatId);

    @Query("select * from message where message.chat_id = :chatId order by message.created_at DESC limit 1 offset 0")
    MessageDB getLastMessageForChat(String chatId);
/*    @Query("select distinct subscriber_id from message where chat_id = :chatId")
    List<MessageDB> getDinstinctSubscriberId(String chatId);*/
}
