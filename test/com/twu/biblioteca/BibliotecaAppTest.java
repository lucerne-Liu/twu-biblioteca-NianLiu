package com.twu.biblioteca;


import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.status.MainMenuOptionStatus;
import com.twu.biblioteca.status.UserAccountMenuOptionStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BibliotecaAppTest {
    private static final String USER_ACCOUNTS_OPTION = MainMenuOptionStatus.User_Accounts;
    private static final String BOOK_LIST_OPTION = MainMenuOptionStatus.List_Books;
    private static final String CHECK_OUT_BOOK_OPTION = MainMenuOptionStatus.Checkout_Book;
    private static final String RETURN_BOOK_OPTION = MainMenuOptionStatus.Return_Book;
    private static final String MOVIE_LIST_OPTION = MainMenuOptionStatus.List_Movies;
    private static final String CHECK_OUT_MOVIE_OPTION = MainMenuOptionStatus.Checkout_Movie;
    private static final String QUIT_OPTION = MainMenuOptionStatus.Quit;
    private static final String INVALID_OPTION = MainMenuOptionStatus.INVALID_OPTION;
    private static final String LOGIN_OPTION = UserAccountMenuOptionStatus.LOG_IN;
    private static final String USER_INFORMATION_OPTION = UserAccountMenuOptionStatus.USER_INFORMATION;
    private static final String BACK_TO_MAIN_MENU_OPTION = UserAccountMenuOptionStatus.BACK_TO_MAIN_MENU;
    private static final String MAIN_MENU = "1. User Accounts\n"
            + "2. List Books\n"
            + "3. Checkout Book\n"
            + "4. Return Book\n"
            + "5. List Movies\n"
            + "6. Check-out Movie\n"
            + "7. Quit\n"
            + "Please enter your choice(1～7):\n";
    private static final String ACCOUNT_MENU_WITHOUT_LOGIN = "1. Login\n"
            + "2. Back to Main Menu\n"
            + "Please enter your choice(1～2):\n";
    private static final String ACCOUNT_MENU= "1. Login\n"
            + "2. User information\n"
            + "3. Back to Main Menu\n"
            + "Please enter your choice(1～3):\n";
    private static final String EXIST_BOOK_NAME = "Head First Java";
    private static final String ANOTHER_EXIST_BOOK_NAME = "Head First JavaScript";
    private static final String ABSENT_BOOK_NAME = "Head First Python";
    private static final String CORRECT_BOOK_NAME_WITH_NO_SPACES = "HeadFirstJava";
    private static final String EXIST_MOVIE_NAME = "Avengers: Infinity War";
    private static final String EXIST_USER_NUMBER = "111-1111";
    private static final String EXIST_USER_PWD = "123456";
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
    public void should_print_welcome_message_when_start_app() {
        bibliotecaApp.printWelcomeMessage();
        assertThat(systemOut().startsWith("Welcome to use the Biblioteca!")).isTrue();
    }

    @Test
    public void should_print_main_menu_after_welcome_message() {
        bibliotecaApp.printMainMenu();
        assertThat(systemOut().endsWith(MAIN_MENU)).isTrue();
    }

    @Test
    public void should_return_true_and_prompt_message_when_input_not_found() {
        when(reader.readOption()).thenReturn(INVALID_OPTION);
        assertTrue(bibliotecaApp.proceedMainMenu());
        assertThat(systemOut().endsWith("Select a valid option! Please select again.\n")).isTrue();
    }

    @Test
    public void should_return_false_and_prompt_message_when_input_non_digit() {
        when(reader.readOption()).thenReturn("aa");
        assertTrue(bibliotecaApp.proceedMainMenu());
        assertThat(systemOut().endsWith("Select a valid option! Please select again.\n")).isTrue();
    }

    @Test
    public void should_return_false_and_stop_app_when_choose_Quit() {
        when(reader.readOption()).thenReturn(QUIT_OPTION);
        assertFalse(bibliotecaApp.proceedMainMenu());
        bibliotecaApp.init();
        assertThat(systemOut().endsWith("Goodbye! See you next time!\n")).isTrue();
    }

    @Test
    public void should_print_main_menu_again_when_input_invalid() {
        when(reader.readOption()).thenReturn(INVALID_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertTrue(systemOut().contains(MAIN_MENU));
        verify(reader, times(2)).readOption();
    }

    @Test
    public void should_print_book_list_when_choose_List_Books() {
        when(reader.readOption()).thenReturn(BOOK_LIST_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertTrue(systemOut().contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Head First Java                                   Kent Belt                                         2003                                              \n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n"));
        verify(reader, times(2)).readOption();
    }

    @Test
    public void should_not_show_checked_out_books_after_check_out() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(BOOK_LIST_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n")).isTrue();
    }

    @Test
    public void should_prompt_successful_msg_when_checked_out() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Thank you! Enjoy the book")).isTrue();
        verify(reader, times(5)).readOption();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_check_out_book_not_on_the_list() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(ABSENT_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That book is not available.\n"
                + "Please input the book name you want to check out:")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_input_book_already_checked_out() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME).thenReturn(ANOTHER_EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That book is not available.\n"
                + "Please input the book name you want to check out:")).isTrue();
        verify(reader, times(5)).readString();
    }

    @Test
    public void should_show_returned_book_in_list_when_returned() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(RETURN_BOOK_OPTION).thenReturn(BOOK_LIST_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Head First Java                                   Kent Belt                                         2003                                              \n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n")).isTrue();
        ;
        verify(reader, times(4)).readString();
    }

    @Test
    public void should_print_successful_return_message_when_book_name_correct_but_no_spaces() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(RETURN_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME).thenReturn(CORRECT_BOOK_NAME_WITH_NO_SPACES);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Thank you for returning the book.\n")).isTrue();
        verify(reader, times(6)).readOption();
    }

    @Test
    public void should_print_unsuccessful_return_message_when_book_name_wrong() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(RETURN_BOOK_OPTION).thenReturn(BOOK_LIST_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME).thenReturn(ABSENT_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That is not a valid book to return.\n")).isTrue();
        assertThat(systemOut().contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n")).isTrue();

        verify(reader, times(7)).readOption();
    }

    @Test
    public void should_print_unsuccessful_return_message_when_book_name_right_but_not_rent_from_this_library() {
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(RETURN_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That is not a valid book to return.\n")).isTrue();
    }

    @Test
    public void should_print_movie_list_when_choose_List_Movies_option() {
        when(reader.readOption()).thenReturn(MOVIE_LIST_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Name                          Year                          Director                      Movie Rating                  \n" +
                "=======================================================================================================\n" +
                "Avengers: Infinity War        2018                          Anthony Russo, Joe Russo      9.2                           \n" +
                "Kings                         2017                          Deniz Gamze Ergüven           4.9                           \n" +
                "Disobedience                  2017                          Sebastián Lelioo              6.4                           \n" +
                "Love & Bananas                2018                          Ashley Bell                   unrated                       \n" +
                "=======================================================================================================\n")).isTrue();
    }

    @Test
    public void should_not_show_checked_out_movies_in_movie_list() {
        when(reader.readOption()).thenReturn(CHECK_OUT_MOVIE_OPTION).thenReturn(MOVIE_LIST_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_MOVIE_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Thank you! Enjoy the Movie.\n")).isTrue();
        assertThat(systemOut().contains("Name                          Year                          Director                      Movie Rating                  \n" +
                "=======================================================================================================\n" +
                "Kings                         2017                          Deniz Gamze Ergüven           4.9                           \n" +
                "Disobedience                  2017                          Sebastián Lelioo              6.4                           \n" +
                "Love & Bananas                2018                          Ashley Bell                   unrated                       \n" +
                "=======================================================================================================\n")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_input_movie_already_checked_out() {
        when(reader.readOption()).thenReturn(CHECK_OUT_MOVIE_OPTION).thenReturn(CHECK_OUT_MOVIE_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_MOVIE_NAME).thenReturn(EXIST_MOVIE_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That Movie is not available.\n")).isTrue();
        verify(reader, times(2)).readString();
    }

    @Test
    public void should_not_check_out_book_when_not_login(){
        when(reader.readOption()).thenReturn(CHECK_OUT_BOOK_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Please login first!")).isTrue();
        assertThat(systemOut().contains("Please input the book name you want to check out:")).isFalse();
    }

    @Test
    public void should_not_return_books_when_not_login(){
        when(reader.readOption()).thenReturn(RETURN_BOOK_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Please login first!")).isTrue();
        assertThat(systemOut().contains("Please input the book name you want to return:")).isFalse();
    }
    
    @Test
    public void should_not_show_user_information_option_when_not_login(){
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(QUIT_OPTION);
        bibliotecaApp.init();
        assertThat(systemOut().contains(ACCOUNT_MENU_WITHOUT_LOGIN)).isTrue();
    }

    @Test
    public void should_show_user_information_option_when_logged_in(){
        when(reader.readOption()).thenReturn(USER_ACCOUNTS_OPTION).thenReturn(LOGIN_OPTION).thenReturn(BACK_TO_MAIN_MENU_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readString()).thenReturn(EXIST_USER_NUMBER).thenReturn(EXIST_USER_PWD);
        bibliotecaApp.init();
        assertThat(systemOut().contains(ACCOUNT_MENU)).isTrue();
    }

    private String systemOut() {
        return outputContent.toString();
    }
}
