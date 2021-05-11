package com.example.tmdb.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tmdb.models.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);

    @Query("SELECT * from movieList ORDER BY id ASC")
    LiveData<List<MovieEntity>> getAllMovies();
}
