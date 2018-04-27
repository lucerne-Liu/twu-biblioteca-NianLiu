package com.twu.biblioteca.core;

import com.twu.biblioteca.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class VideoStore {
    public static final String DIVIDER = "============================================================"
            + "===========================================\n";
    private List<Movie> moviesList;

    public VideoStore() {
        moviesList = new ArrayList<Movie>() {{
            add(new Movie("Avengers: Infinity War", "2018", "Anthony Russo, Joe Russo", "9.2"));
            add(new Movie("Kings", "2017", "Deniz Gamze Ergüven", "4.9"));
            add(new Movie("Disobedience", "2017", "Sebastián Lelioo", "6.4"));
            add(new Movie("Love & Bananas", "2018", "Ashley Bell"));
        }};
    }

    public void printMoviesList() {
        System.out.print(String.format("%-30s%-30s%-30s%-30s\n" + DIVIDER, "Name", "Year", "Director","Movie Rating"));
        moviesList.stream().filter(movie -> !movie.getRentedStatus()).forEach(item -> System.out.print(item.toString()));
        System.out.print(DIVIDER);
    }
}
