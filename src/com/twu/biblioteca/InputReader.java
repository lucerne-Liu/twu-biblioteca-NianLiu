package com.twu.biblioteca;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    public String readOption() {
        return scanner.next();
    }

    public String readName() {
        return null;
    }
}
