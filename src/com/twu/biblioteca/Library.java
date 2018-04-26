package com.twu.biblioteca;

import org.fest.assertions.internal.Booleans;

import java.util.ArrayList;
import java.util.List;

public class Library {
    public static final String DIVIDER = "============================================================"
            + "=======================================================\n";
    private List<Book> bookList;

    public Library() {
        bookList = new ArrayList<Book>(){{
            add(new Book("Head First Java", "Kent Belt",2003));
            add(new Book("Test-Driven Development", "Kent Belt",2004));
            add(new Book("Refactoring: Improving the Design", "Martin Fowler",2010));
            add(new Book("Head First Android Development", "Dawn Griffiths",2016));
            add(new Book("Head First JavaScript", "Eric T. Freeman",2017));
        }};
    }

    public void printBookList(){
        System.out.print(String.format("%-50s%-50s%-50s\n" + DIVIDER,"Name","Author","Year Published"));
        bookList.forEach(item -> System.out.print(item.toString()));
    }

    public boolean checkOutBook(String name) {
        Boolean hasThisBook = bookList.stream().map(Book::getName).anyMatch(item -> item == name);
        bookList.removeIf(item -> item.getName() == name);
        return hasThisBook;
    }

}
