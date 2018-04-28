package com.twu.biblioteca.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginValidatorTest {
    private LoginValidator validator;

    @Before
    public void setUp(){
        validator = new LoginValidator();
    }

    @Test
    public void should_return_true_when_library_number_in_right_format(){
        assertTrue(validator.validateLibraryNumber("111-1111"));
    }

    @Test
    public void should_return_false_when_library_number_in_wrong_format(){
        assertFalse(validator.validateLibraryNumber("1112134"));
        assertFalse(validator.validateLibraryNumber("111"));
        assertFalse(validator.validateLibraryNumber("111-11113"));
        assertFalse(validator.validateLibraryNumber("11111-11"));
    }

    @Test
    public void should_return_true_when_user_number_and_password_all_correct(){
        assertTrue(validator.validateUser("111-1111", "123456"));
        assertTrue(validator.validateUser("222-1111", "222"));
    }

    @Test
    public void should_return_false_when_user_account_no_exist(){
        assertFalse(validator.validateUser("111-1111", "123"));
        assertFalse(validator.validateUser("222-1111", "123456"));
    }

}