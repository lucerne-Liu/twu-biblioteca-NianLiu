package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.fest.assertions.api.Assertions.assertThat;

public class InputReaderTest {
    private InputReader reader;

    @Before
    public void setUp(){
        reader = new InputReader();
    }

    private void setInputStream(String input) throws NoSuchFieldException, IllegalAccessException {
        Field scannerField = reader.getClass().getDeclaredField("scanner");
        Scanner scannerWithMockStream = new Scanner(new ByteArrayInputStream(input.getBytes()));
        scannerField.setAccessible(true);
        scannerField.set(reader, scannerWithMockStream);
    }
    
    @Test
    public void should_return_input_option_when_is_right() throws NoSuchFieldException, IllegalAccessException {
        setInputStream("1");
        assertThat(reader.readOption()).isEqualTo("1");
    }

    @Test
    public void should_return_invalid_when_inpu_option_non_digit() throws NoSuchFieldException, IllegalAccessException {
        setInputStream("a");
        assertThat(reader.readOption()).isEqualTo("invalid");
    }
}
