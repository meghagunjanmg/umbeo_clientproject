package com.example.umbeo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executors;

@Database(entities = {CartEntity.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract CartDao cartDao();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "cartDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }

}
