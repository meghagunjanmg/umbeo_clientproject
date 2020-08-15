package com.example.umbeo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.umbeo.response_data.CategoryModel;
import com.example.umbeo.response_data.shop.Shop;

import java.util.concurrent.Executors;

@Database(entities = {CartEntity.class,ProductEntity.class, CategoryModel.class,OrderEntity.class,UserEntity.class, ShopEntity.class}, version = 2)
    public abstract class AppDatabase extends RoomDatabase {

        public abstract CartDao cartDao();
        public abstract ProductDao productDao();
        public abstract OrderDao orderDao();
        public abstract PreferenceDao preferenceDao();

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "cartDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }

}
