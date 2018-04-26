package com.twu.biblioteca;

public class Book {
    private String name;
    private String author;
    private int publishedYear;

    public Book(String name, String author, int publishedYear) {
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return String.format("%-50s%-50s%-50s\n",name,author,publishedYear);
    }
}
