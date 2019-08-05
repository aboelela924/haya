package com.bignerdranch.android.haya.model.repo.roomDatabase.dataAccessObjects;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNewChat(ChatDB newChatDB);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChats(List<ChatDB> chatDBS);

    @Delete
    void deleteChat(ChatDB chatDB);

    @Query("update chat set status = :status where id = :id")
    void updateStatus(String id, String status);

    @Query("update chat set updated_at = :updateAt where token = :chatToken")
    void updateUpdatedAt(String updateAt, String chatToken);

    @Query("Select * from chat order by updated_at DESC")
    List<ChatDB> selectAllChats();
}
