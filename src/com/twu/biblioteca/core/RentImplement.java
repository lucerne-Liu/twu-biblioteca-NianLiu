package com.twu.biblioteca.core;

public interface RentImplement {
    void printMediaList();
    boolean checkOutMedia(String name);
    boolean returnMedia(String name);
}
