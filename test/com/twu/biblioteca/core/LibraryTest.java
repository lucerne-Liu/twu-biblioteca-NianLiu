package com.twu.biblioteca.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryTest {
    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void should_return_book_renter_name_when_input_book_name() {
        String bookName = "Head First Java", userName = "Mary";
        library.checkOut(bookName, userName);
        assertEquals(library.findRentedUserByBookName(bookName), userName);
    }

}