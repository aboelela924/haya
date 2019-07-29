package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Chats")
public class Chat {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
         String id;
    @ColumnInfo(name = "name")
         String name;
    @ColumnInfo(name = "updated_at")
         boolean updatedAt;
    @ColumnInfo(name = "created_at")
         String createdAt;
    @ColumnInfo(name = "type")
        String type;
    @ColumnInfo(name="status")
        String status;
    @ColumnInfo(name = "token")
        String token;
}
