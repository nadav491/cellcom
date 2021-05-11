package com.example.tmdb.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.database.Database;
import com.example.tmdb.database.MovieDao;
import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.models.MoviesInput;
import com.example.tmdb.network.RetrofitApiService;
import com.example.tmdb.network.RetrofitClientInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieRepository
{
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final RetrofitApiService api = RetrofitClientInstance.getInterface(); //The api of for the http request.
    private static final MutableLiveData<ArrayList<MovieEntity>> movies = new MutableLiveData<>(); //All the movies we downloaded or stored.
    private final MovieDao dao;
    private int pageNumber; //The current page loaded from the server.

    public MovieRepository(Application application)
    {
        Database database = Database.getInstance(application);
        dao = database.movieDao();

        pageNumber = 1;
        if (movies.getValue() == null)
            movies.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<MovieEntity>> getMovies()
    {
        return movies;
    }


    /**
     * Get the movies list
     */
    public void getPopularMovies()
    {
        Observable<MoviesInput> filter = api.getPopularMovies(RetrofitClientInstance.APP_KEY, RetrofitClientInstance.MOVIE_SORT_POPULARITY,
                RetrofitClientInstance.COUNTRY, pageNumber++);
        getNextMoviesByFilter(filter);
    }

    /**
     * Get the now playing movies list
     */
    public void getPlayingMovies()
    {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String today = timeStampFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String prevMonth = timeStampFormat.format(calendar.getTime());

        Observable<MoviesInput> filter = api.getPlayingMovies(RetrofitClientInstance.APP_KEY, prevMonth, today,
                RetrofitClientInstance.COUNTRY, pageNumber++);

        getNextMoviesByFilter(filter);
    }

    /**
     * Reset the page number
     */
    public void resetPageNumber()
    {
        pageNumber = 1;
        Objects.requireNonNull(movies.getValue()).clear();
    }

    /**
     * Get the next 20 movies from the server.
     *
     * @param filter - the Observable we got from the api call
     */
    private void getNextMoviesByFilter(Observable<MoviesInput> filter)
    {
        filter.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesInput>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.i("Error", "" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(MoviesInput moviesInput)
                    {
                        addMovies(moviesInput);
                    }
                });
    }

    /**
     * Add the incoming movies to the movies list in the live-data object.
     *
     * @param moviesInput - the input from server.
     */
    private void addMovies(MoviesInput moviesInput)
    {
        if (moviesInput == null || moviesInput.getResults().length <= 0)
            return;

        ArrayList<MovieEntity> tempList = movies.getValue();
        if (tempList == null)
            tempList = new ArrayList<>();
        tempList.addAll(Arrays.asList(moviesInput.getResults()));
        movies.setValue(tempList);
    }

    /**
     * Add a movie to the favorite list.
     *
     * @param movie - the movie to add.
     */
    public void addMovieToDatabase(MovieEntity movie)
    {
        new InsertMovieAsyncTask(dao).execute(movie);
    }

    /**
     * Remove a movie from favorite list.
     *
     * @param movie - the movie to remove.
     */
    public void removeMovieFromDatabase(MovieEntity movie)
    {
        new RemoveMovieAsyncTask(dao).execute(movie);
    }


    // A class to handle asynchronous tasks for removing an existing movie.
    private static class RemoveMovieAsyncTask extends AsyncTask<MovieEntity, Void, Void>
    {
        private final MovieDao dao;

        private RemoveMovieAsyncTask(MovieDao dao)
        {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movie)
        {
            dao.delete(movie[0]); //single note
            return null;
        }
    }

    // A class to handle asynchronous tasks for inserting new movie.
    private static class InsertMovieAsyncTask extends AsyncTask<MovieEntity, Void, Void>
    {
        private final MovieDao dao;

        private InsertMovieAsyncTask(MovieDao dao)
        {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movie)
        {
            dao.insert(movie[0]); //single note
            return null;
        }
    }

    /**
     * Get the favorite movie list
     * @return
     */
    public LiveData<List<MovieEntity>> getFavoriteMovieList()
    {
        return dao.getAllMovies();
    }

}