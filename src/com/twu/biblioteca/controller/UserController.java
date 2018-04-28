package com.twu.biblioteca.controller;

import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.model.User;
import com.twu.biblioteca.validator.LoginValidator;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static List<User> userList = new ArrayList<User>() {
        {
            add(new User("111-1111", "123456", "Kate", "111@foxmail.com", "111"));
            add(new User("222-1111", "222", "Mary", "222@foxmail.com", "222222"));
            add(new User("111-3333", "qqq", "Tiffany", "333@foxmail.com", "156989899"));
        }
    };
    private boolean isLogined;
    private String loginUserNumber;
    private InputReader reader;
    private LoginValidator validator = new LoginValidator();

    public UserController(InputReader reader) {
        this.reader = reader;
    }

    public boolean isLogined() {
        return isLogined;
    }

    private void setLogined(boolean logined) {
        isLogined = logined;
    }

    private void setLoginUserNumber(String loginUserNumber) {
        this.loginUserNumber = loginUserNumber;
    }

    public void logIn() {
        String libraryNumber;
        while (true) {
            System.out.println("Please enter your library number(xxx-xxxx):\n");
            libraryNumber = reader.readString();
            if (validator.validateLibraryNumber(libraryNumber)) {
                break;
            } else {
                System.out.print("The library number format is wrong!\n");
            }
        }
        System.out.println("Please enter your password:\n");
        validator.validateUser(libraryNumber, reader.readString());
        System.out.print("Login Successful! Now you can check-out and return books.\n");


    }
}
