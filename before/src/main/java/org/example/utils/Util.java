package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class Util {
    public static String convertToString(BufferedReader bufferedReader) throws IOException {
        String line = "";
        String text = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        text = stringBuilder.toString();
        return text;
    }
}
