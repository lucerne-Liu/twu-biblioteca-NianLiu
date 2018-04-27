package com.twu.biblioteca.controller;

import com.twu.biblioteca.core.Library;
import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.core.VideoStore;

public class BibliotecaController {
    private InputReader reader;
    private Library library;
    private VideoStore videoStore;

    public BibliotecaController(InputReader reader) {
        this.reader = reader;
        library = new Library();
        videoStore = new VideoStore();
    }

    public void printBooksList() {
        library.printMediaList();
    }

    public void checkOutBook() {
        while (true) {
            System.out.println("Please input the book name you want to check out:");
            if (library.checkOutMedia(reader.readName())) {
                System.out.println("Thank you! Enjoy the book");
                break;
            }else{
                System.out.print("That book is not available.\n");
            }
        }
    }

    public void returnBook() {
        System.out.println("Please input the book name you want to return:");
        System.out.print(library.returnMedia(reader.readName()) ? "Thank you for returning the book.\n" : "That is not a valid book to return.\n");
    }

    public void printMoviesList() {
        videoStore.printMediaList();
    }

    public void checkOutMovie() {
        System.out.println("Please input the movie name you want to check out:");
        if (videoStore.checkOutMedia(reader.readName())) {
            System.out.print("Thank you! Enjoy the Movie.\n");
        }else{
            System.out.print("That Movie is not available.\n");
        }
    }
}
