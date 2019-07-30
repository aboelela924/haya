package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

import com.bignerdranch.android.haya.model.repo.Message;

public class SyncMessageMaster {
    private Message[] messages;

    public Message[] getMessages ()
    {
        return messages;
    }

    public void setMessages (Message[] messages)
    {
        this.messages = messages;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [messages = "+messages+"]";
    }
}
