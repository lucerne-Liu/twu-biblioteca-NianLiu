package com.twu.biblioteca.model;

public class User {
    private String libraryNumber;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public User(String libraryNumber, String password, String name, String email, String phoneNumber) {
        this.libraryNumber = libraryNumber;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    private String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        User anotherUser = (User) object;
        return getLibraryNumber().equals(anotherUser.getLibraryNumber()) && getPassword().equals(anotherUser.getPassword());
    }
}
