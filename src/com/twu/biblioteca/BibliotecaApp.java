package com.twu.biblioteca;

public class BibliotecaApp {

    public void start() {
        printWelcomeMessage();
        printMainMenu();
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to use the Biblioteca!");
    }

    public void printMainMenu() {
        System.out.print("1. List Books\n2. Quit\nPlease enter your choice(1～2):");
    }
}
