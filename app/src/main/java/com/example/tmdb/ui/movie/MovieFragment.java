package com.example.tmdb.ui.movie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.tmdb.R;
import com.example.tmdb.models.MovieEntity;
import com.example.tmdb.network.RetrofitClientInstance;

import java.util.List;

public class MovieFragment extends Fragment
{

    private MovieViewModel movieViewModel;
    private View root;
    private MovieEntity movie;
    private ImageView favorite;
    private boolean isFavorite;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_movie, container, false);
        getMovie();
        startViewModel();
        setView();

        return root;
    }

    private void getMovie()
    {
        movie = MovieFragmentArgs.fromBundle(requireArguments()).getMovie();
        if (movie.getId().isEmpty())
        {
            Log.i("Error", "Not movie id");
            Navigation.findNavController(root).navigateUp();
        }
    }

    /**
     * Start the view model and get the movie.
     */
    private void startViewModel()
    {
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getMovieList().observe(requireActivity(), this::setFavorite);

    }

    private void setFavorite(List<MovieEntity> movieList)
    {
        isFavorite = checkIfFavorite(movieList);
        favorite = root.findViewById(R.id.imageView_movie_favorite);
        favorite.setOnClickListener(view -> startFavoriteAnimation());
        favorite.setBackgroundResource(isFavorite ? R.drawable.ic_favorite_24 : R.drawable.ic_favorite_border_24);

    }

    /**
     * Check if a movie is in the favorite list
     *
     * @param movieList - the movie list to check
     * @return true if exist (then its a favorite).
     */
    public boolean checkIfFavorite(List<MovieEntity> movieList)
    {
        if(movieList == null || movieList.isEmpty())
            return false;
        for (MovieEntity movieEntity : movieList)
            if (movieEntity.getId().equals(movie.getId()))
                return true;
        return false;

    }

    @SuppressLint("SetTextI18n")
    private void setView()
    {
        ((TextView) root.findViewById(R.id.textView_movie_title)).setText("Title: " + movie.getTitle());
        ((TextView) root.findViewById(R.id.textView_movie_desc)).setText("Full description" + movie.getOverview());
        ((TextView) root.findViewById(R.id.textView_movie_releaseDate)).setText("Release date: " + movie.getReleaseDate());
        ((TextView) root.findViewById(R.id.textView_movie_overview)).setText("overview: " + movie.getOverview());
        ((TextView) root.findViewById(R.id.textView_movie_vote_count)).setText("vote_count: " + movie.getVoteCount());
        ((TextView) root.findViewById(R.id.textView_movie_vote_average)).setText("vote_average: " + movie.getVoteAverage());
        Glide.with(root)
                .load(RetrofitClientInstance.IMAGE_PATH + movie.getPoster_path())
                .placeholder(R.drawable.progress_bar_drawable)
                .into((ImageView) root.findViewById(R.id.imageView_movie_poster));
    }


    /**
     * After the favorite button was pressed, Start the switching animation
     */
    private void startFavoriteAnimation()
    {
        favorite.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in));
        Animation close = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        close.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                switchFavoriteButton();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        close.setStartOffset(100);
        favorite.startAnimation(close);
    }

    /**
     * Change the image and alarm the database.
     */
    private void switchFavoriteButton()
    {
        isFavorite = !isFavorite;
        favorite.setBackgroundResource(isFavorite ? R.drawable.ic_favorite_24 : R.drawable.ic_favorite_border_24);
        if (isFavorite)
            movieViewModel.addToWatchlist(movie);
        else
            movieViewModel.removeFromWatchlist(movie);
    }
}