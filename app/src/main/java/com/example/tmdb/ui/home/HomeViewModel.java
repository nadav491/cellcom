package com.example.tmdb.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel
{
    private final MovieRepository movieRepository;
    private final MutableLiveData<ArrayList<MovieEntity>> moviesList;
    private final LiveData<List<MovieEntity>> favoriteList;
    private int filterMode;

    public HomeViewModel(@NonNull Application application)
    {
        super(application);
        movieRepository = new MovieRepository(application);
        moviesList = movieRepository.getMovies();
        filterMode = HomeFragment.FILTER_MODE_POPULAR;
        favoriteList = movieRepository.getFavoriteMovieList();
    }

    public LiveData<List<MovieEntity>> getFavoriteList()
    {
        return favoriteList;
    }

    public MutableLiveData<ArrayList<MovieEntity>> getMoviesList()
    {
        getNextMovies();
        return moviesList;
    }

    public void getNextMovies()
    {
        switch (filterMode)
        {
            case HomeFragment.FILTER_MODE_PLAYING:
                movieRepository.getPlayingMovies();
                break;
            case HomeFragment.FILTER_MODE_POPULAR:
                movieRepository.getPopularMovies();
                break;
            default:
                Log.i("Error", "Filter mode not found " + filterMode);
        }
    }

    public void switchFilterMode(int mode)
    {
        filterMode = mode;
        movieRepository.resetPageNumber();
        getNextMovies();
    }

    public int getFilterMode()
    {
        return filterMode;
    }

    public void setFilterMode(int filterMode)
    {
        this.filterMode = filterMode;
    }
}