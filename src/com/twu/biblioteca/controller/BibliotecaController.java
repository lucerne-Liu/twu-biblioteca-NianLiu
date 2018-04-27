package com.twu.biblioteca.controller;

import com.twu.biblioteca.Library;
import com.twu.biblioteca.command.InputReader;

public class BibliotecaController {
    private InputReader reader;
    private Library library;

    public BibliotecaController(InputReader reader) {
        this.reader = reader;
        library = new Library();
    }

    public void printBooksList() {
        library.printBookList();
    }

    public void checkOutBook() {
        while (true) {
            System.out.println("Please input the book name you want to check out:");
            if (library.checkOutBook(reader.readName())) {
                System.out.println("Thank you! Enjoy the book");
                break;
            }else{
                System.out.print("That book is not available.\n");
            }
        }
    }

    public void returnBook() {
        System.out.println("Please input the book name you want to return:");
        System.out.print(library.returnBook(reader.readName()) ? "Thank you for returning the book.\n" : "That is not a valid book to return.\n");
    }
}
