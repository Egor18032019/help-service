package org.example.store;

import org.example.utils.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GoodRepositoryImpl implements GoodRepository {
    private static final Map<Integer, String> PHRASE_STORAGE = new HashMap<>();

    @Override
    public void add(String phrase) {
        PHRASE_STORAGE.putIfAbsent(PHRASE_STORAGE.size(), phrase);
    }

    public   String getRandomPhrase() {
        int size = PHRASE_STORAGE.size();

        if (size == 0) return Const.default_phrase;

        int key = new Random().nextInt(PHRASE_STORAGE.size());

        return PHRASE_STORAGE.get(key);
    }

    public   int getSizeStorage() {
        return PHRASE_STORAGE.size();
    }
}
