package org.example.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GoodRepository {
    private static final Map<Integer, String> PHRASE_STORAGE = new HashMap<>();

    public GoodRepository() {
    }

    public void add(String phrase) {
        PHRASE_STORAGE.putIfAbsent(PHRASE_STORAGE.size(), phrase);
    }

    public String getRandomPhrase() {
        int size = PHRASE_STORAGE.size();
        String foo = "У тебя всё получиться !";
        if (size == 0) return foo;

        int key = new Random().nextInt(PHRASE_STORAGE.size());
        return PHRASE_STORAGE.get(key);
    }

    public int getSizeStorage() {
        return PHRASE_STORAGE.size();
    }
}
