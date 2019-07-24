package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

public class SyncMessageMaster {
    private SyncMessage[] messages;

    public SyncMessage[] getMessages ()
    {
        return messages;
    }

    public void setMessages (SyncMessage[] messages)
    {
        this.messages = messages;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [messages = "+messages+"]";
    }
}
