package com.twu.biblioteca;

import com.twu.biblioteca.command.InputReader;

public class Main {
    public static void main(String[] args) {
        BibliotecaApp bibliotecaApp = new BibliotecaApp(new InputReader());
        bibliotecaApp.init();
    }
}
