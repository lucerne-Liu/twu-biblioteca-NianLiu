package com.twu.biblioteca;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    public String readOption() {
        String inputString = scanner.next();
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(inputString).matches() ? inputString : "invalid";
    }

    public String readName() {
        return null;
    }
}
