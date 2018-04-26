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
        reader = mock(InputReader.class);
        bibliotecaApp = new BibliotecaApp(reader);
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void should_print_welcome_message_when_start_app(){
        bibliotecaApp.printWelcomeMessage();
        assertThat(systemOut().startsWith("Welcome to use the Biblioteca!")).isTrue();
    }
    
    @Test
    public void should_print_main_menu_after_welcome_message(){
        bibliotecaApp.printMainMenu();
        assertThat(systemOut().endsWith("1. List Books\n2. Quit\nPlease enter your choice(1～2):\n")).isTrue();
    }

    @Test
    public void should_return_true_and_prompt_message_when_input_invalid(){
        when(reader.read()).thenReturn("3");
        assertTrue(bibliotecaApp.proceedMainMenu());
        assertThat(systemOut().endsWith("Select a valid option! Please select again.\n")).isTrue();
    }

    @Test
    public void should_return_false_and_stop_app_when_choose_Quit(){
        when(reader.read()).thenReturn("2");
        assertFalse(bibliotecaApp.proceedMainMenu());
        bibliotecaApp.init();
        assertThat(systemOut().endsWith("Goodbye! See you next time!\n")).isTrue();
    }

    @Test
    public void should_print_main_menu_again_when_input_invalid(){
        when(reader.read()).thenReturn("3").thenReturn("2");
        bibliotecaApp.init();
        assertTrue(systemOut().contains("1. List Books\n2. Quit\nPlease enter your choice(1～2):\n"));
        verify(reader,times(2)).read();
    }

    @Test
    public void should_print_book_list(){
        bibliotecaApp.printBooksList();
        assertThat(systemOut()).isEqualTo(
                "Name                                              Author                                            Year Published                                    \n" +
                "===================================================================================================================\n" +
                "Head First Java                                   Kent Belt                                         2003                                              \n" +
                "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");
    }

    private String systemOut(){
        return outputContent.toString();
    }
}
