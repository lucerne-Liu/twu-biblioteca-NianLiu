package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BibliotecaAppTest {
    public static final String ANOTHER_EXIST_BOOK_NAME = "Head First JavaScript";
    private static final String BOOK_LIST_OPTION = OptionStatus.List_Books;
    private static final String CHECK_OUT_OPTION = OptionStatus.Checkout_Book;
    private static final String RETURN_BOOK_OPTION = OptionStatus.Return_Book;
    private static final String QUIT_OPTION = OptionStatus.Quit;
    private static final String INVALID_OPTION = "5";
    private static final String MAIN_MENU = "1. List Books\n2. Checkout Book\n3. Return Book\n4. Quit\nPlease enter your choice(1～4):\n";
    private static final String EXIST_BOOK_NAME = "Head First Java";
    private static final String ABSENT_BOOK_NAME = "Head First Python";
    private static final String CORRECT_BOOK_NAME_WITH_NO_SPACES = "HeadFirstJava";
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
        assertThat(systemOut()).contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Head First Java                                   Kent Belt                                         2003                                              \n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");
        verify(reader, times(2)).readOption();
    }

    @Test
    public void should_not_show_checked_out_books_after_check_out() {
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.checkOutBook();
        bibliotecaApp.printBooksList();
        assertThat(systemOut()).contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");
    }

    @Test
    public void should_prompt_successful_msg_when_checked_out() {
        when(reader.readOption()).thenReturn(CHECK_OUT_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Thank you! Enjoy the book")).isTrue();
        verify(reader, times(2)).readOption();
    }

    @Test
    public void should_prompt_successful_checked_out_msg_when_book_name_spelling_right_but_no_spaces() {
        when(reader.readName()).thenReturn(CORRECT_BOOK_NAME_WITH_NO_SPACES);
        bibliotecaApp.checkOutBook();
        assertThat(systemOut().contains("Thank you! Enjoy the book")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_check_out_book_not_on_the_list() {
        when(reader.readOption()).thenReturn(CHECK_OUT_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readName()).thenReturn(ABSENT_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.init();
        assertThat(systemOut().contains("That book is not available.\n"
                + "Please input the book name you want to check out:")).isTrue();
    }

    @Test
    public void should_prompt_unsuccessful_message_when_input_book_already_checked_out() {
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME).thenReturn(ANOTHER_EXIST_BOOK_NAME);
        bibliotecaApp.checkOutBook();
        bibliotecaApp.checkOutBook();
        assertThat(systemOut().contains("That book is not available.\n"
                + "Please input the book name you want to check out:")).isTrue();
        verify(reader, times(3)).readName();
    }

    @Test
    public void should_show_returned_book_in_list_when_returned() {
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.checkOutBook();
        bibliotecaApp.returnBook();
        bibliotecaApp.printBooksList();
        assertThat(systemOut()).contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Head First Java                                   Kent Belt                                         2003                                              \n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");
        verify(reader, times(2)).readName();
    }

    @Test
    public void should_print_successful_return_message_when_book_in_the_library(){
       when(reader.readName()).thenReturn(EXIST_BOOK_NAME).thenReturn(EXIST_BOOK_NAME);
        bibliotecaApp.checkOutBook();
        bibliotecaApp.returnBook();
        assertThat(systemOut().endsWith("Thank you for returning the book.\n")).isTrue();
    }

    @Test
    public void should_print_successful_return_message_when_book_name_correct_but_no_spaces(){
        when(reader.readOption()).thenReturn(CHECK_OUT_OPTION).thenReturn(RETURN_BOOK_OPTION).thenReturn(QUIT_OPTION);
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME).thenReturn(CORRECT_BOOK_NAME_WITH_NO_SPACES);
        bibliotecaApp.init();
        assertThat(systemOut().contains("Thank you for returning the book.\n")).isTrue();
        verify(reader, times(3)).readOption();
    }

    @Test
    public void should_print_unsuccessful_return_message_when_book_name_wrong(){
        when(reader.readName()).thenReturn(EXIST_BOOK_NAME).thenReturn(ABSENT_BOOK_NAME);
        bibliotecaApp.checkOutBook();
        bibliotecaApp.returnBook();
        assertThat(systemOut().endsWith("That is not a valid book to return.\n")).isTrue();
        bibliotecaApp.printBooksList();
        assertThat(systemOut()).contains(
                "Name                                              Author                                            Year Published                                    \n" +
                        "===================================================================================================================\n" +
                        "Test-Driven Development                           Kent Belt                                         2004                                              \n" +
                        "Refactoring: Improving the Design                 Martin Fowler                                     2010                                              \n" +
                        "Head First Android Development                    Dawn Griffiths                                    2016                                              \n" +
                        "Head First JavaScript                             Eric T. Freeman                                   2017                                              \n");

    }

    @Test
    public void should_print_unsuccessful_return_message_when_book_name_right_but_not_rent_from_this_library(){
        when(reader.readName()).thenReturn(ABSENT_BOOK_NAME);
        bibliotecaApp.returnBook();
        assertThat(systemOut().endsWith("That is not a valid book to return.\n")).isTrue();
    }


    private String systemOut() {
        return outputContent.toString();
    }
}
