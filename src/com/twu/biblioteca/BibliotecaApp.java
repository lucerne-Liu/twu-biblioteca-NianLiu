package com.twu.biblioteca;

import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.controller.BibliotecaController;

public class BibliotecaApp {
    private InputReader reader;
    private BibliotecaController bibliotecaController;

    public BibliotecaApp(InputReader reader) {
        this.reader = reader;
        bibliotecaController = new BibliotecaController(reader);
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
                bibliotecaController.printBooksList();
                return true;
            case OptionStatus.Checkout_Book:
                bibliotecaController.checkOutBook();
                return true;
            case OptionStatus.Return_Book:
                bibliotecaController.returnBook();
                return true;
            case OptionStatus.Quit:
                return false;
            default:
                System.out.print("Select a valid option! Please select again.\n");
                return true;
        }
    }

    public void printGoodbyeMessage() {
        System.out.print("Goodbye! See you next time!\n");
    }

    public void init() {
        printWelcomeMessage();
        while (proceedMainMenu()) { }
        printGoodbyeMessage();
    }

}
