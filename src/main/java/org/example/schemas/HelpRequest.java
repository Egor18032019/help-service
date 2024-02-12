package org.example.schemas;

public class HelpRequest {
    private String phrase;

    public HelpRequest() {
    }

    public HelpRequest(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
