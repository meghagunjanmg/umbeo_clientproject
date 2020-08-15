package com.example.umbeo.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreferenceDao {
    @Query("SELECT * FROM shop")
    List<ShopEntity> loadAll();

    @Query("SELECT * FROM user")
    List<UserEntity> loadLoyaltyAll();


    @Query("DELETE FROM user")
    void nukeUserTable();


    @Query("DELETE FROM shop")
    void nukeShopTable();


    @Insert
    void insertLoyaltyAll(List<UserEntity> entities);


    @Insert
    void insertShopAll(List<ShopEntity> entities);
}
