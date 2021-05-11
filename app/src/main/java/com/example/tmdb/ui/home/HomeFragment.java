package com.example.tmdb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.R;
import com.example.tmdb.models.MovieEntity;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    public static final int FILTER_MODE_PLAYING = 0;
    public static final int FILTER_MODE_POPULAR = 1;
    public static final int FILTER_MODE_FAVORITE = 2;

    private HomeViewModel homeViewModel;
    private HomeMovieAdapter movieAdapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        startRecyclerView();
        startViewModel();
        setButtons();

        return root;
    }

    private void setButtons()
    {
        root.findViewById(R.id.button_home_favorites).setOnClickListener(view -> setRVFilterMode(FILTER_MODE_FAVORITE));
        root.findViewById(R.id.button_home_now).setOnClickListener(view -> setRVFilterMode(FILTER_MODE_PLAYING));
        root.findViewById(R.id.button_home_popular).setOnClickListener(view -> setRVFilterMode(FILTER_MODE_POPULAR));
    }

    /**
     * Set the new filter.
     *
     * @param filterMode - the filter to change to.
     */
    private void setRVFilterMode(int filterMode)
    {
        movieAdapter.clearList();
        if (filterMode == FILTER_MODE_FAVORITE)
        {
            homeViewModel.setFilterMode(FILTER_MODE_FAVORITE);
            homeViewModel.getFavoriteList().observe(requireActivity(), movieEntities -> movieAdapter.addToMovieList((ArrayList<MovieEntity>) movieEntities));
        }
        else
        {
            homeViewModel.getFavoriteList().removeObservers(requireActivity());
            homeViewModel.switchFilterMode(filterMode);
        }
    }

    /**
     * Start the view model and get the movies.
     */
    private void startViewModel()
    {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getMoviesList().observe(requireActivity(), movieEntities -> movieAdapter.addToMovieList(movieEntities));
    }

    /**
     * Strat the recyclerView.
     */
    private void startRecyclerView()
    {
        movieAdapter = new HomeMovieAdapter()
        {
            @Override
            void pressBody(MovieEntity movieEntity)
            {
                super.pressBody(movieEntity);
                Navigation.findNavController(root).navigate(HomeFragmentDirections.actionNavHomeToNavMovie(movieEntity));
            }
        };
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_home_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() //Check if we got to the end (not in search mode) and get the next movies.
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && homeViewModel.getFilterMode() != FILTER_MODE_FAVORITE)
                    homeViewModel.getNextMovies();
            }
        });
    }
}