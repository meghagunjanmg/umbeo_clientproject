package com.example.umbeo.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products WHERE isActive =:active")
    LiveData<List<ProductEntity>> getAll(Boolean active);

    @Query("SELECT * FROM products WHERE isActive =:active")
    List<ProductEntity> loadAll(Boolean active);



    @Query("SELECT * FROM categorymodel")
    List<CategoryModel> loadAllCategory();


    @Query("SELECT * FROM categorymodel")
    LiveData<List<CategoryModel>> liveLoadAllCategory();



    @Query("SELECT * FROM products WHERE categoryId =:id AND isActive =:active")
    List<ProductEntity> findById(String id,Boolean active);


    @Query("SELECT * FROM products WHERE isRecommended =:id AND isActive =:active")
    List<ProductEntity> findByRecommended(Boolean id,Boolean active);

    @Query("SELECT * FROM products WHERE isTrending =:id AND isActive =:active")
    List<ProductEntity> findByTrending(Boolean id,Boolean active);

    @Query("SELECT * FROM products WHERE isFeatured =:id AND isActive =:active")
    List<ProductEntity> findByFeature(Boolean id,Boolean active);

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
