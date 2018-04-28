package com.twu.biblioteca.validator;

import java.util.Arrays;

public class LoginValidator {

    private static final int NUM_COUNT = 7;

    public boolean validateLibraryNumber(String libraryNumber) {
        return libraryNumber.indexOf("-") == 3 && Arrays.stream(libraryNumber.split(""))
                .filter(item -> !item.equals("-")).map(Integer::parseInt).count() == NUM_COUNT;
    }

    public void validateUser(String libraryNumber, String password) {
    }
}
