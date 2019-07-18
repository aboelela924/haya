package com.bignerdranch.android.haya.repository.roomDatabase.dataAccessObjects;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.bignerdranch.android.haya.repository.roomDatabase.classes.Chat;

import java.util.List;

@Dao
public interface Chat_dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add_new_chat(Chat new_chat);

    @Query("Update Chats set conversation_nickname = :new_nickname where burnercode = :chat_burnercode")
    void update_conversation_nickname(String new_nickname, String chat_burnercode);

    @Query("Update Chats set secret_status = :new_status where burnercode = :chat_burnercode")
    void update_secret_status(String chat_burnercode, boolean new_status);

    @Delete
    void delete_chat(Chat chat);

    //For testing
    @Query("Select * from Chats")
    List<Chat> select_all_chats();
}
