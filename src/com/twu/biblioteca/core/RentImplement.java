package com.twu.biblioteca.core;

public interface RentImplement {
    void printMediaList();

    boolean checkOut(String name, String rentedBy);

    boolean returnMedia(String name);
}
