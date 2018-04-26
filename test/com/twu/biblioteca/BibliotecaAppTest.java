package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BibliotecaAppTest {
    private BibliotecaApp bibliotecaApp;
    private ByteArrayOutputStream outputContent;
    private InputReader reader;

    @Before
    public void setUp() {
        bibliotecaApp = new BibliotecaApp();
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        reader = mock(InputReader.class);
    }

    @Test
    public void should_print_welcome_message_when_start_app(){
        bibliotecaApp.printWelcomeMessage();
        assertTrue(systemOut().startsWith("Welcome to use the Biblioteca!"));
//        assertThat(systemOut().startsWith("Welcome to use the Biblioteca!"));
    }
    
    @Test
    public void should_print_main_menu_after_welcome_message(){
        bibliotecaApp.printMainMenu();
        assertTrue(systemOut().endsWith("1. List Books\n2. Quit\nPlease enter your choice(1～2):"));
    }

    @Test
    public void should_prompt_wrong_message_and_print_main_menu_when_input_invalid(){
        when(reader.read()).thenReturn("3");
        bibliotecaApp.proceedMainMenu();
        assertTrue(systemOut().endsWith("Select a valid option! Please select again.\n"+
                "1. List Books\n2. Quit\nPlease enter your choice(1～2):"));
    }

    private String systemOut(){
        return outputContent.toString();
    }
}
