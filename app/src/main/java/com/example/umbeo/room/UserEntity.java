package com.example.umbeo.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Boolean loyalty;

    public UserEntity(Boolean loyalty) {
        this.loyalty = loyalty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Boolean loyalty) {
        this.loyalty = loyalty;
    }
}
