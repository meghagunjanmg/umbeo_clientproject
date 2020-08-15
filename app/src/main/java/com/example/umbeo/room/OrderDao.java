package com.example.umbeo.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    List<OrderEntity> loadAll();

    @Query("SELECT * FROM orders WHERE status !=3")
    List<OrderEntity> loadCurrentAll();

    @Query("SELECT * FROM orders WHERE status ==3")
    List<OrderEntity> loadHistoryAll();

    @Query("DELETE FROM orders")
    void nukeTable();

    @Insert
    void insertAll(List<OrderEntity> entities);


}
