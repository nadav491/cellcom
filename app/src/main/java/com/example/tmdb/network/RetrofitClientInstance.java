package com.example.tmdb.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit = getClient();
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String APP_KEY = "2c46288716a18fb7aadcc2a801f3fc6b";
    public static final String MOVIE_SORT_POPULARITY = "popularity.desc";
    public static final String COUNTRY = "ISR";
    public static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitApiService getInterface() {
        return retrofit.create(RetrofitApiService.class);
    }
}
