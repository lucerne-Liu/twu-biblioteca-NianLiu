package com.twu.biblioteca;

public class BibliotecaApp {
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
        System.out.print("1. List Books\n2. Checkout Book\n3. Quit\nPlease enter your choice(1～3):\n");
    }

    public boolean proceedMainMenu() {
        printMainMenu();
        switch (Integer.parseInt(reader.read())) {
            case 1:
                printBooksList();
                return true;
            case 2:
                return true;
            case 3:
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

    public void init() {
        printWelcomeMessage();
        while (proceedMainMenu()) {

        }
        printGoodbyeMessage();
    }
}
