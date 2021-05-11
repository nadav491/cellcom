package com.example.tmdb.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tmdb.models.MovieEntity;


@androidx.room.Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase
{
    private static Database instance; //only one interface
    public abstract MovieDao movieDao();

    public static synchronized Database getInstance(Context context)
    {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "TMDB")
                    .build();
        return instance;
    }
}