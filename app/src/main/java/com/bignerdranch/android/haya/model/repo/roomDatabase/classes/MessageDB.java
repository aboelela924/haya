package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Message;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "message",foreignKeys = {
                                                @ForeignKey(entity = ChatDB.class,
                                                        parentColumns = "id",
                                                        childColumns = "chat_id",
                                                        onDelete = CASCADE),
                                                @ForeignKey(entity = SubscriberDB.class,
                                                        parentColumns = "id",
                                                        childColumns = "subscriber_id")
                                            })
public class MessageDB {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public    String id;
    @ColumnInfo(name = "message")
    public   String message;
    @ColumnInfo(name = "updated_at")
    public  String updatedAt;
    @ColumnInfo(name = "created_at")
    public  String createdAt;
    @ColumnInfo(name = "type")
    public  String type;
    @ColumnInfo(name = "is_deleted")
    public   boolean isDeleted;
    @ColumnInfo(name = "chat_id")
    public   String chatId;
    @ColumnInfo(name = "subscriber_id")
    public String subScriberId;


    public static MessageDB fromMessage(Message message, String chatId, String subScriberId){
        MessageDB msgDB = new MessageDB();
        msgDB.id = message.getId();
        msgDB.message = message.getMessage();
        msgDB.updatedAt = message.getUpdated_at();
        msgDB.createdAt = message.getCreated_at();
        msgDB.type = message.getType();
        msgDB.isDeleted = Boolean.valueOf(message.getIsDeleted());
        msgDB.chatId = chatId;
        msgDB.subScriberId = subScriberId;
        return msgDB;
    }

}
