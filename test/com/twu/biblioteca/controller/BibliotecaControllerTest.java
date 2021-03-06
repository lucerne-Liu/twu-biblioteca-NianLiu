package com.twu.biblioteca.controller;

import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.status.MainMenuOptionStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class BibliotecaControllerTest {
    private static final String EXIST_BOOK_NAME = "Head First Java";
    private static final String ABSENT_BOOK_NAME = "Head First Python";
    private static final String CORRECT_BOOK_NAME_WITH_NO_SPACES = "HeadFirstJava";
    private static final String EXIST_MOVIE_NAME = "Avengers: Infinity War";
    private static final String EXIST_MOVIE_NAME_WITH_NO_SPACES = "Avengers:InfinityWar";
    private static final String ABSENT_MOVIE_NAME = "A Quiet Place";
    private static final String EXIST_USER_NUMBER = "111-1111";
    private static final String EXIST_USER_PWD = "123456";
    private static final String EXIST_USER_NAME = "Kate";
    private static final String NOT_EXIST_PWD = "58555";
    private BibliotecaController bibliotecaController;
    private ByteArrayOutputStream outputContent;
    private InputReader reader;

    @Before
    public void setUp() {
        reader = mock(InputReader.class);
        bibliotecaController = new BibliotecaController(reader);
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void should_print_book_list() {
        bibliotecaController.printBooksList();
        assertThat(systemOut()).isEqualTo(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Head First Java                                   Kent Belt                                         2003                                              \n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");
    }

    @Test
    public void should_prompt_successful_msg_when_checked_out_book() {
        when(reader.readString()).thenReturn(EXIST_BOOK_NAME);
        bibliotecaController.checkOutBook();
        assertThat(systemOut().contains("Thank you! Enjoy the book")).isTrue();
    }

    @Test
    public void should_prompt_successful_checked_out_msg_when_book_name_spelling_right_but_no_spaces() {
        when(reader.readString()).thenReturn(CORRECT_BOOK_NAME_WITH_NO_SPACES);
        bibliotecaController.checkOutBook();
        assertThat(systemOut().contains("Thank you! Enjoy the book")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_check_out_book_not_on_the_list() {
        when(reader.readString()).thenReturn(ABSENT_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaController.checkOutBook();
        assertThat(systemOut().contains("That book is not available.\n"
                + "Please input the book name you want to check out:")).isTrue();
    }

    @Test
    public void should_print_successful_return_message_when_book_in_the_library() {
        when(reader.readString()).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaController.checkOutBook();
        bibliotecaController.returnBook();
        assertThat(systemOut().endsWith("Thank you for returning the book.\n")).isTrue();
    }

    @Test
    public void should_print_successful_return_message_when_book_name_correct_but_no_spaces() {
        when(reader.readString()).thenReturn(EXIST_BOOK_NAME).thenReturn(CORRECT_BOOK_NAME_WITH_NO_SPACES);
        bibliotecaController.checkOutBook();
        bibliotecaController.returnBook();
        assertThat(systemOut().contains("Thank you for returning the book.\n")).isTrue();
        verify(reader, times(2)).readString();
    }

    @Test
    public void should_print_unsuccessful_return_message_when_book_name_wrong() {
        when(reader.readString()).thenReturn(EXIST_BOOK_NAME).thenReturn(ABSENT_BOOK_NAME);
        bibliotecaController.checkOutBook();
        bibliotecaController.returnBook();
        assertThat(systemOut().endsWith("That is not a valid book to return.\n")).isTrue();
    }

    @Test
    public void should_print_movies_list() {
        bibliotecaController.printMoviesList();
        assertThat(systemOut()).isEqualTo("Name                          Year                          Director                      Movie Rating                  \n" +
                "=======================================================================================================\n" +
                "Avengers: Infinity War        2018                          Anthony Russo, Joe Russo      9.2                           \n" +
                "Kings                         2017                          Deniz Gamze Ergüven           4.9                           \n" +
                "Disobedience                  2017                          Sebastián Lelioo              6.4                           \n" +
                "Love & Bananas                2018                          Ashley Bell                   unrated                       \n" +
                "=======================================================================================================\n");
    }

    @Test
    public void should_prompt_successful_msg_when_checked_out_movie() {
        when(reader.readString()).thenReturn(EXIST_MOVIE_NAME);
        bibliotecaController.checkOutMovie();
        assertThat(systemOut().startsWith("Please input the movie name you want to check out:")).isTrue();
        assertThat(systemOut().endsWith("Thank you! Enjoy the Movie.\n")).isTrue();
    }

    @Test
    public void should_prompt_successful_msg_when_checked_out_movie_name_with_no_spaces() {
        when(reader.readString()).thenReturn(EXIST_MOVIE_NAME_WITH_NO_SPACES);
        bibliotecaController.checkOutMovie();
        assertThat(systemOut().endsWith("Thank you! Enjoy the Movie.\n")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_msg_when_checked_out_movie_name_not_exist() {
        when(reader.readString()).thenReturn(ABSENT_MOVIE_NAME);
        bibliotecaController.checkOutMovie();
        assertThat(systemOut().endsWith("That Movie is not available.\n")).isTrue();
    }

    @Test
    public void should_prompt_successful_login_message_when_login_right() {
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD);
        bibliotecaController.logIn();
        assertThat(systemOut().startsWith("Please enter your library number(xxx-xxxx):\n")).isTrue();
        assertThat(systemOut().contains("Please enter your password:\n")).isTrue();
        assertThat(systemOut().endsWith("Login Successful!\n"
                +"Welcome " + EXIST_USER_NAME + "! Now you can check-out and return books.\n")).isTrue();
    }

    @Test
    public void should_prompt_message_when_login_library_number_in_wrong_format() {
        when(reader.readString()).thenReturn("11111-11").thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD);
        bibliotecaController.logIn();
        assertThat(systemOut().contains("The library number format is wrong!\n"
                + "Please enter your library number(xxx-xxxx):\n")).isTrue();
        assertThat(systemOut().endsWith("Login Successful!\n"
                +"Welcome " + EXIST_USER_NAME + "! Now you can check-out and return books.\n")).isTrue();
        verify(reader,times(3)).readString();
    }

    @Test
    public void should_prompt_message_when_user_account_not_exist() {
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(NOT_EXIST_PWD);
        bibliotecaController.logIn();
        assertThat(systemOut().startsWith("Please enter your library number(xxx-xxxx):\n")).isTrue();
        assertThat(systemOut().endsWith("Login failed! Please check your library number and password.\n")).isTrue();
        verify(reader,times(2)).readString();
    }
    
    @Test
    public void should_prompt_message_when_check_out_or_return_book_without_login(){
        bibliotecaController.proceedCommandIfLoggedIn(MainMenuOptionStatus.Checkout_Book);
        assertThat(systemOut().startsWith("Please login first!")).isTrue();
        bibliotecaController.proceedCommandIfLoggedIn(MainMenuOptionStatus.Return_Book);
        assertThat(systemOut().startsWith("Please login first!")).isTrue();
    }

    @Test
    public void should_print_user_information_of_the_logged_in_user(){
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD);
        bibliotecaController.logIn();
        bibliotecaController.printUserInformation();
        assertThat(systemOut().endsWith("=================================================\n" +
                "name: Kate\n" +
                "email address: 111@foxmail.com\n" +
                "phone number: 111\n" +
                "=================================================\n")).isTrue();
    }

    private String systemOut() {
        return outputContent.toString();
    }
}