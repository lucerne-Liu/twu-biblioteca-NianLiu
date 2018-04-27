package com.twu.biblioteca;

public class BibliotecaApp {
    public static final String DEFAULT_WRONG_INPUT = "-999";
    private InputReader reader;
    private Library library;

    public BibliotecaApp(InputReader reader) {
        this.reader = reader;
        library = new Library();
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to use the Biblioteca!");
    }

    public void printMainMenu() {
        System.out.print("1. List Books\n2. Checkout Book\n3. Return Book\n4. Quit\nPlease enter your choice(1～4):\n");
    }

    public boolean proceedMainMenu() {
        printMainMenu();
        switch (reader.readOption()) {
            case OptionStatus.List_Books:
                printBooksList();
                return true;
            case OptionStatus.Checkout_Book:
                checkOutBook();
                return true;
            case OptionStatus.Return_Book:
                returnBook();
                return true;
            case OptionStatus.Quit:
                return false;
            default:
                System.out.print("Select a valid option! Please select again.\n");
                return true;
        }
    }

    public void printBooksList() {
        library.printBookList();
    }

    public void printGoodbyeMessage() {
        System.out.print("Goodbye! See you next time!\n");
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

    public void init() {
        printWelcomeMessage();
        while (proceedMainMenu()) { }
        printGoodbyeMessage();
    }

}
