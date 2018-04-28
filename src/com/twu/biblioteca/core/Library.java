package com.twu.biblioteca.core;


import com.twu.biblioteca.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library implements RentImplement {
    private static final String DIVIDER = "============================================================"
            + "=======================================================\n";
    private List<Book> bookList;

    public Library() {
        bookList = new ArrayList<Book>() {{
            add(new Book("Head First Java", "Kent Belt", 2003));
            add(new Book("Test-Driven Development", "Kent Belt", 2004));
            add(new Book("Refactoring: Improving the Design", "Martin Fowler", 2010));
            add(new Book("Head First Android Development", "Dawn Griffiths", 2016));
            add(new Book("Head First JavaScript", "Eric T. Freeman", 2017));
        }};
    }

    @Override
    public void printMediaList() {
        System.out.print(String.format("%-50s%-50s%-50s\n" + DIVIDER, "Name", "Author", "Year Published"));
        bookList.stream().filter(book -> !book.getRentedStatus()).forEach(item -> System.out.print(item.toString()));
    }

    public boolean changeBookRentStatus(String name, boolean isRent, String rentedBy) {
        Book checkOutBook = new Book(name, "", 0);
        Boolean hasThisBook = bookList.stream().anyMatch(item -> item.equals(checkOutBook) && item.getRentedStatus() == !isRent);
        if (hasThisBook) {
            bookList.stream().filter(book -> book.equals(checkOutBook)).forEach(book -> {
                book.setRented(isRent);
                book.setRentedBy(rentedBy);
            });
        }
        return hasThisBook;
    }

    public String findRentedUserByBookName(String bookName){
        Book checkOutBook = new Book(bookName, "", 0);
        return bookList.stream().filter(book -> book.equals(checkOutBook)).map(Book::getRentedBy).collect(Collectors.joining());
    }

    @Override
    public boolean checkOut(String name, String rentedBy) {
        return changeBookRentStatus(name, true, rentedBy);
    }

    @Override
    public boolean returnMedia(String name) {
        return changeBookRentStatus(name, false, "");
    }
}
