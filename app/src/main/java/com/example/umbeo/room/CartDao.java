package com.example.umbeo.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart_items_table")
    LiveData<List<CartEntity>> getAll();

    @Query("SELECT * FROM cart_items_table")
    List<CartEntity> loadAll();

    @Query("SELECT * FROM cart_items_table WHERE item_name =:name")
    CartEntity findByName(String name);

    @Insert
    void insertAll(List<CartEntity> entities);

    @Insert
    void insertOne(CartEntity entity);

    @Delete
    void delete(CartEntity user);


    @Query("DELETE FROM cart_items_table")
    void nukeTable();



    @Query("DELETE FROM cart_items_table WHERE item_name =:name")
    void deleteOne(String name);
}
