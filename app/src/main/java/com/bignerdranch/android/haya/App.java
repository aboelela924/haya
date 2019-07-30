package com.bignerdranch.android.haya;

import android.app.Application;

import androidx.room.Room;

import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;

public class App extends Application {
    private HayaDatabase myDatabase;
    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        myDatabase = Room.databaseBuilder(this,
                HayaDatabase.class,
                HayaDatabase.NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

    }

    public static App getInstance(){
        return app;
    }

    public HayaDatabase getMyDatabase(){
        return myDatabase;
    }
}
