package com.twu.biblioteca.command;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        scanner = new Scanner(System.in);
    }

    public String readOption() {
        String inputString = scanner.next().trim();
        Pattern pattern = Pattern.compile("-?[0-9]*");
        return pattern.matcher(inputString).matches() ? inputString : "invalid";
    }

    public String readString() {
        scanner.useDelimiter("\n");
        String inputString = scanner.next().trim();
        return inputString;
    }
}
