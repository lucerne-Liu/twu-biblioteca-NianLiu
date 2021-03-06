package com.twu.biblioteca.validator;

import com.twu.biblioteca.controller.UserController;
import com.twu.biblioteca.model.User;

import java.util.Arrays;

public class LoginValidator {

    private static final int NUM_COUNT = 7;

    public boolean validateLibraryNumber(String libraryNumber) {
        return libraryNumber.indexOf("-") == 3 && Arrays.stream(libraryNumber.split(""))
                .filter(item -> !item.equals("-")).map(Integer::parseInt).count() == NUM_COUNT;
    }

    public boolean validateUser(String libraryNumber, String password) {
        User inputUser = new User(libraryNumber,password,"","","");
        return UserController.userList.stream().anyMatch(user -> user.equals(inputUser));
    }
}
