package com.example.tmdb.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movieList")
public class MovieEntity implements Parcelable
{
    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>()
    {
        @Override
        public MovieEntity createFromParcel(Parcel in)
        {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size)
        {
            return new MovieEntity[size];
        }
    };
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    @NonNull
    private String id;

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    @Expose
    private double popularity;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    @Expose
    private String poster_path;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    @Expose
    private String title;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    @Expose
    private int voteCount;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    @Expose
    private float voteAverage;

    protected MovieEntity(Parcel in)
    {
        id = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readFloat();
    }

    public MovieEntity(@NonNull String id, double popularity, String poster_path, String title, String overview, String releaseDate, int voteCount, float voteAverage)
    {
        this.id = id;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    @NonNull
    public String getId()
    {
        return id;
    }

    public void setId(@NonNull String id)
    {
        this.id = id;
    }

    public double getPopularity()
    {
        return popularity;
    }

    public void setPopularity(double popularity)
    {
        this.popularity = popularity;
    }

    public String getPoster_path()
    {
        return poster_path;
    }

    public void setPoster_path(String poster_path)
    {
        this.poster_path = poster_path;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(int voteCount)
    {
        this.voteCount = voteCount;
    }

    public float getVoteAverage()
    {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage)
    {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
        parcel.writeDouble(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(voteCount);
        parcel.writeFloat(voteAverage);

    }
}
