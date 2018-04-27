package com.twu.biblioteca.model;


public class Movie {
    private String name;
    private String year;
    private String director;
    private String rating = "unrated";
    private boolean isRented = false;

    public Movie(String name, String year, String director, String rating) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
    }

    public Movie(String name, String year, String director) {
        this.name = name;
        this.year = year;
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public boolean getRentedStatus() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    private String removeSpacesInName(String name) {
        return name.replaceAll(" ", "");
    }

    @Override
    public String toString() {
        return String.format("%-30s%-30s%-30s%-30s\n", name, year, director, rating);
    }

    @Override
    public boolean equals(Object object) {
        Movie anotherMovie = (Movie) object;
        return removeSpacesInName(name).equals(removeSpacesInName(anotherMovie.getName()));
    }

}
