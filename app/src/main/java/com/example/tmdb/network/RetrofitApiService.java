package com.example.tmdb.network;

import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.models.MoviesInput;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitApiService
{
    @GET("discover/movie")
        //I used discover for filtering by popularity.
    Observable<MoviesInput> getPopularMovies(@Query("api_key") String appKey,
                                             @Query("sort_by") String sortBy,
                                             @Query("certification_country") String certificationCountry,
                                             @Query("page") int pageNumber);
    @GET("discover/movie")
    Observable<MoviesInput> getPlayingMovies(@Query("api_key") String appKey,
                                             @Query("primary_release_date.gte") String releaseDateGte,
                                             @Query("primary_release_date.lte") String releaseDateLte,
                                             @Query("certification_country") String certificationCountry,
                                             @Query("page") int pageNumber);
}
