package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Messages",foreignKeys = {
                                                @ForeignKey(entity = Chat.class,
                                                        parentColumns = "id",
                                                        childColumns = "chat_id",
                                                        onDelete = CASCADE),
                                                @ForeignKey(entity = Subscriber.class,
                                                        parentColumns = "id",
                                                        childColumns = "subscriber_id")
                                            })
public class Message {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
        String id;
    @ColumnInfo(name = "message")
        Date message;
    @ColumnInfo(name = "updated_at")
        String updatedAt;
    @ColumnInfo(name = "created_at")
         boolean createdAt;
    @ColumnInfo(name = "type")
         String type;
    @ColumnInfo(name = "is_deleted")
         boolean isDeleted;
    @ColumnInfo(name = "chat_id")
        String chatId;
    @ColumnInfo(name = "subscriber_id")
        String subScriberId;

}
