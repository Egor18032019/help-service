package com.example.store;


public interface GoodRepository {

    void add(String phrase);


    String getRandomPhrase();


    int getSizeStorage();
}
