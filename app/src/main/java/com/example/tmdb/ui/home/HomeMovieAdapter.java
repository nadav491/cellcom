package com.example.tmdb.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb.R;
import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.network.RetrofitClientInstance;

import java.util.ArrayList;

public class HomeMovieAdapter extends RecyclerView.Adapter<HomeMovieAdapter.MovieViewHolder>
{


    private final ArrayList<MovieEntity> moviesList; //The main movie list for the adapter.

    public HomeMovieAdapter()
    {
        moviesList = new ArrayList<>();
    }

    @Override
    public @NonNull
    MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        MovieEntity movieEntity = moviesList.get(position);
        holder.title.setText(movieEntity.getTitle());
        holder.description.setText(movieEntity.getOverview());
        holder.body.setOnClickListener(v -> pressBody(moviesList.get(position)));
        Glide.with(holder.itemView)
                .load(RetrofitClientInstance.IMAGE_PATH + movieEntity.getPoster_path())
                .placeholder(R.drawable.progress_bar_drawable)
                .into(holder.poster);
    }

    void pressBody(MovieEntity movieEntity)
    {
    }

    /**
     * Add new movies to the list.
     *
     * @param addedList - the movies to add.
     */
    public void addToMovieList(ArrayList<MovieEntity> addedList)
    {
        if(addedList == null || addedList.isEmpty())
            return;
        int position = this.moviesList.size();
        moviesList.addAll(addedList);
        notifyItemRangeInserted(position, addedList.size());
    }


    @Override
    public int getItemCount()
    {
        return moviesList.size();
    }

    public void clearList()
    {
        moviesList.clear();
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView title;
        public final TextView description;
        public final ImageView poster;
        public final ConstraintLayout body;

        public MovieViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.textView_movieItem_title);
            description = view.findViewById(R.id.textView_movieItem_desc);
            poster = view.findViewById(R.id.imageView_movieItem_poster);
            body = view.findViewById(R.id.constraintLayout_movieItem_main);
        }
    }
}
