package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;

public class ExampleTest {
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
        bibliotecaApp.start();
        assertTrue(systemOut().startsWith("Welcome to use the Biblioteca!"));
    }

    private String systemOut(){
        return outputContent.toString();
    }
}
