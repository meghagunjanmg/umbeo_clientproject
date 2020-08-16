package com.example.umbeo.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAll();

    @Query("SELECT * FROM products")
    List<ProductEntity> loadAll();



    @Query("SELECT * FROM categorymodel")
    List<CategoryModel> loadAllCategory();


    @Query("SELECT * FROM categorymodel")
    LiveData<List<CategoryModel>> liveLoadAllCategory();



    @Query("SELECT * FROM products WHERE categoryId =:id")
    List<ProductEntity> findById(String id);


    @Query("SELECT * FROM products WHERE isRecommended =:id")
    List<ProductEntity> findByRecommended(Boolean id);

    @Query("SELECT * FROM products WHERE isTrending =:id")
    List<ProductEntity> findByTrending(Boolean id);

    @Query("SELECT * FROM products WHERE isFeatured =:id")
    List<ProductEntity> findByFeature(Boolean id);

    @Insert
    void insertAll(List<ProductEntity> entities);


    @Insert
    void insertAllCategory(List<CategoryModel> entities);

    @Insert
    void insertOne(ProductEntity entity);

    @Delete
    void delete(ProductEntity entity);


    @Query("DELETE FROM products")
    void nukeTable();


    @Query("DELETE FROM categorymodel")
    void nukeCategory();

    @Query("Update products SET isRecommended =:trueR WHERE id =:prodId")
    void UpdateRecommended(String prodId,Boolean trueR);

    @Query("Update products SET isTrending =:trueR WHERE id =:prodId")
    void UpdateTrending(String prodId,Boolean trueR);
}
