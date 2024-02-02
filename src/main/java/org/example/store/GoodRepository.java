package org.example.store;

import org.example.utils.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GoodRepository {
    private static final Map<Integer, String> PHRASE_STORAGE = new HashMap<>();



    public static void add(String phrase) {
        PHRASE_STORAGE.putIfAbsent(PHRASE_STORAGE.size(), phrase);
    }

    public static String getRandomPhrase() {
        int size = PHRASE_STORAGE.size();

        if (size == 0) return Const.def;

        int key = new Random().nextInt(PHRASE_STORAGE.size());
        System.out.println(key);
        return PHRASE_STORAGE.get(key);
    }

    public static int getSizeStorage() {
        return PHRASE_STORAGE.size();
    }
}
