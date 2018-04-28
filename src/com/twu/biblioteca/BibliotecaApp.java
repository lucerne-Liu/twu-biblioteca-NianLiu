package com.twu.biblioteca;

import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.controller.BibliotecaController;
import com.twu.biblioteca.status.MainMenuOptionStatus;
import com.twu.biblioteca.status.UserAccountMenuOptionStatus;

public class BibliotecaApp {
    private static final String MAIN_MENU = "1. User Accounts\n"
            + "2. List Books\n"
            + "3. Checkout Book\n"
            + "4. Return Book\n"
            + "5. List Movies\n"
            + "6. Check-out Movie\n"
            + "7. Quit\n"
            + "Please enter your choice(1～7):\n";
    private static final String ACCOUNT_MENU = "1. Login\n"
            + "2. Back to Main Menu\n"
            + "Please enter your choice(1～2):\n";
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
        System.out.print(MAIN_MENU);
    }

    private void printUserMenu() {
        System.out.print(ACCOUNT_MENU);
    }

    private void printOptionWrongMessage() {
        System.out.print("Select a valid option! Please select again.\n");
    }

    public void proceedUserAccountMenu(){
        while(true){
            printUserMenu();
            String options = reader.readOption();
            if (options.equals(UserAccountMenuOptionStatus.LOG_IN)){
                bibliotecaController.logIn();
            } else if (options.equals(UserAccountMenuOptionStatus.BACK_TO_MAIN_MENU)) {
                break;
            } else {
                printOptionWrongMessage();
            }
        }
    }

    public boolean proceedMainMenu() {
        printMainMenu();
        switch (reader.readOption()) {
            case MainMenuOptionStatus.User_Accounts:
                proceedUserAccountMenu();
                return true;
            case MainMenuOptionStatus.List_Books:
                bibliotecaController.printBooksList();
                return true;
            case MainMenuOptionStatus.Checkout_Book:
                bibliotecaController.proceedCommandIfLoggedIn(MainMenuOptionStatus.Checkout_Book);
                return true;
            case MainMenuOptionStatus.Return_Book:
                bibliotecaController.proceedCommandIfLoggedIn(MainMenuOptionStatus.Return_Book);
                return true;
            case MainMenuOptionStatus.List_Movies:
                bibliotecaController.printMoviesList();
                return true;
            case MainMenuOptionStatus.Checkout_Movie:
                bibliotecaController.checkOutMovie();
                return true;
            case MainMenuOptionStatus.Quit:
                return false;
            default:
                printOptionWrongMessage();
                return true;
        }
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
