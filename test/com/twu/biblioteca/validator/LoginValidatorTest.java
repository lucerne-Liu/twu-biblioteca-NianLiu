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
        assertFalse(validator.validateLibraryNumber("111213"));
    }

}