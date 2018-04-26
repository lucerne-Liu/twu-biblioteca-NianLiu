package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;

public class BibliotecaAppTest {
    private BibliotecaApp bibliotecaApp;
    private ByteArrayOutputStream outputContent;

    @Before
    public void setUp() {
        bibliotecaApp = new BibliotecaApp();
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void should_print_welcome_message_when_start_app(){
        bibliotecaApp.printWelcomeMessage();
        assertTrue(systemOut().startsWith("Welcome to use the Biblioteca!"));
    }
    
    @Test
    public void should_print_main_menu_after_welcome_message(){
        bibliotecaApp.printMainMenu();
        assertTrue(systemOut().endsWith("1. List Books\n2. Quit\nPlease enter your choice(1～2):"));

    }

    private String systemOut(){
        return outputContent.toString();
    }
}
