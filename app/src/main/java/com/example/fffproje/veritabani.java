package com.example.fffproje;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Not.class}, version = 1)
public abstract class veritabani extends RoomDatabase {
    public abstract dao dao();
}
 