package org.example.store;

import org.example.annotation.Logged;

public interface GoodRepository {
    @Logged
    void add(String phrase);

    @Logged
    String getRandomPhrase();

    @Logged
    int getSizeStorage();
}
