package com.example.store;


import com.example.utils.Const;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class GoodRepositoryImpl implements GoodRepository {
    private final Set<String> PHRASE_STORAGE = new CopyOnWriteArraySet<>();

    @Override
    public void add(String phrase) {
        PHRASE_STORAGE.add(phrase);

    }
    @Override
    public String getRandomPhrase() {
        int size = PHRASE_STORAGE.size();
        System.out.println("PHRASE_STORAGE..." + PHRASE_STORAGE.size());
        if (size == 0) return Const.default_phrase;

        return PHRASE_STORAGE.stream().toList().get(
                ThreadLocalRandom.current().nextInt(size) % size
        );
    }
    @Override
    public boolean isPhraseContainsInStorage(String phrase) {
        return PHRASE_STORAGE.contains(phrase);
    }

    @Override
    public void clear() {
        PHRASE_STORAGE.clear();
    }

    @Override
    public int getSizeStorage() {
        return PHRASE_STORAGE.size();
    }
}
