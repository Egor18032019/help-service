package com.example.repository;

import com.example.store.GoodRepositoryImpl;
import com.example.utils.Const;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoodRepositoryTest {
    GoodRepositoryImpl goodRepository = new GoodRepositoryImpl();

    @AfterEach
    public void resetDb() {
        goodRepository.clear();
    }

    @Test
    public void shouldReturnDefault() {
        String phrase = goodRepository.getRandomPhrase();
        assertEquals(Const.default_phrase, phrase);
    }

    @Test
    public void shouldAddPhraseAndReturnNew() {
        goodRepository.add("+1");
        assertEquals(1, goodRepository.getSizeStorage());
        assertEquals("+1", goodRepository.getRandomPhrase());

    }
}
