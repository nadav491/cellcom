package com.example.tmdb.ui.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.repositories.MovieRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieViewModel extends AndroidViewModel
{
    private LiveData<List<MovieEntity>> movieList;
    private final MovieRepository movieRepository;

    public MovieViewModel(@NonNull @NotNull Application application)
    {
        super(application);
        movieRepository = new MovieRepository(application);
        movieList = movieRepository.getFavoriteMovieList();
    }

    public LiveData<List<MovieEntity>> getMovieList()
    {
        return movieList;
    }


    public void removeFromWatchlist(MovieEntity movie)
    {
        movieRepository.removeMovieFromDatabase(movie);
    }

    public void addToWatchlist(MovieEntity movie)
    {
        movieRepository.addMovieToDatabase(movie);
    }
}
