package org.example.schemas;
//todo версии отдельно в pom вынести
public class HelpRequest {

    final String phrase;

    public HelpRequest(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}