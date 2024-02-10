package org.example.utils;




public enum Path {
    support("/v1/support"),
    another("/v1/another");

    private final String url;

    Path(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
