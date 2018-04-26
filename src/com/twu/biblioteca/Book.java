package com.twu.biblioteca;

public class Book {
    private String name;
    private String author;
    private int publishedYear;
    private boolean isRented;

    public Book(String name, String author, int publishedYear) {
        this.name = name;
        this.author = author;
        this.publishedYear = publishedYear;
        isRented = false;
    }

    public String getName() {
        return name;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public boolean getRentedStatus() {
        return isRented;
    }

    public String removeSpacesInName(String name) {
        return name.replaceAll(" ", "");
    }

    @Override
    public String toString(){
        return String.format("%-50s%-50s%-50s\n",name,author,publishedYear);
    }

    @Override
    public boolean equals(Object object){
        Book anotherBook = (Book)object;
        return removeSpacesInName(name).equals(removeSpacesInName(anotherBook.getName()));
    }

}
