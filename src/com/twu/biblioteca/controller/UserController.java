package com.twu.biblioteca.controller;

import com.twu.biblioteca.command.InputReader;
import com.twu.biblioteca.model.User;
import com.twu.biblioteca.validator.LoginValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    private static final String DIVIDER = "=================================================\n";
    public static List<User> userList = new ArrayList<User>() {
        {
            add(new User("111-1111", "123456", "Kate", "111@foxmail.com", "111"));
            add(new User("222-1111", "222", "Mary", "222@foxmail.com", "222222"));
            add(new User("111-3333", "qqq", "Tiffany", "333@foxmail.com", "156989899"));
        }
    };
    private boolean isLogin = false;
    private String loginUserNumber;
    private InputReader reader;
    private LoginValidator validator = new LoginValidator();

    public UserController(InputReader reader) {
        this.reader = reader;
    }

    public boolean checkLoginStatus() {
        return isLogin;
    }

    private void setLoginStatus(boolean isLoggedIn, String loginUserNumber) {
        this.isLogin = isLoggedIn;
        this.loginUserNumber = loginUserNumber;
    }

    public String getLoginUserNumber() {
        return loginUserNumber;
    }

    private String findUserNameByLibraryNumber(String libraryNumber) {
        return userList.stream().filter(user -> user.getLibraryNumber().equals(libraryNumber)).map(User::getName).collect(Collectors.joining());
    }

    public String logIn() {
        String libraryNumber;
        while (true) {
            System.out.print("Please enter your library number(xxx-xxxx):\n");
            libraryNumber = reader.readString();
            if (validator.validateLibraryNumber(libraryNumber)) {
                break;
            } else {
                System.out.print("The library number format is wrong!\n");
            }
        }
        System.out.print("Please enter your password:\n");
        if (validator.validateUser(libraryNumber, reader.readString())) {
            setLoginStatus(true,libraryNumber);
            return findUserNameByLibraryNumber(libraryNumber);
        } else {
            return "false";
        }
    }

    public void printUserInformationByUserLibraryNumber(){
        System.out.print(DIVIDER);
        userList.stream().filter(user -> user.getLibraryNumber().equals(getLoginUserNumber())).forEach(user -> System.out.print(user.toString()));
        System.out.print(DIVIDER);
    }
}
