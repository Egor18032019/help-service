package org.example.store;

import org.example.utils.Const;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GoodRepositoryImpl implements GoodRepository {
    private static final Map<UUID, String> PHRASE_STORAGE = new ConcurrentHashMap<>();

    @Override
    public void add(String phrase) {
        PHRASE_STORAGE.putIfAbsent(UUID.randomUUID(), phrase);
    }

    public   String getRandomPhrase() {
        int size = PHRASE_STORAGE.size();

        if (size == 0) return Const.default_phrase;

        int random = new Random().nextInt(PHRASE_STORAGE.size());
        return PHRASE_STORAGE.values().stream().skip(random).findFirst().orElse(null);

    }

    public   int getSizeStorage() {
        return PHRASE_STORAGE.size();
    }
}
