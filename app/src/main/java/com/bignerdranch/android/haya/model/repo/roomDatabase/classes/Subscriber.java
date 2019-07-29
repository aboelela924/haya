package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Subscribers", foreignKeys = @ForeignKey(entity = Chat.class,
                                                            parentColumns = "id",
                                                            childColumns = "chat_id",
                                                            onDelete = CASCADE))
public class Subscriber {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
        String id;
    @ColumnInfo(name = "name")
        String name;
    @ColumnInfo(name = "created_at")
        String createdAt;
    @ColumnInfo(name = "updated_at")
        String updatedAt;
    @ColumnInfo(name = "is_admin")
        boolean isAdmin;
    @ColumnInfo(name = "is_leave")
        boolean isLeave;
    @ColumnInfo(name = "is_secret")
        boolean isSecret;
    @ColumnInfo(name = "user_id")
        String userId;
    @ColumnInfo(name = "custom_room_name")
        String customRoomName;
    @ColumnInfo(name = "chat_id")
        String chatId;
}