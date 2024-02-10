package org.example.utils;




public enum Path {
    support("/v1/support");

    private final String url;

    Path(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
