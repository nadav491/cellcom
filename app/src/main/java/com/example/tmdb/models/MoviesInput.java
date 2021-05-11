package com.example.tmdb.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesInput //The input info from getPopularMovies
{
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private MovieEntity[] results;

    @SerializedName("total_results")
    @Expose
    private int total_results;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public MovieEntity[] getResults()
    {
        return results;
    }

    public void setResults(MovieEntity[] results)
    {
        this.results = results;
    }

    public int getTotal_results()
    {
        return total_results;
    }

    public void setTotal_results(int total_results)
    {
        this.total_results = total_results;
    }

    public int getTotal_pages()
    {
        return total_pages;
    }

    public void setTotal_pages(int total_pages)
    {
        this.total_pages = total_pages;
    }
}
