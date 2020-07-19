package com.vb.model;

public enum VeniceBeachStudio {

    KA_SOUTH("Karlsruhe Südstadt", "lifestyle-fitness-plus/karlsruhe/"),
    KA_POST("Karlsruhe Postgalerie", "premium-fitness/karlsruhe-postgalerie/");

    private final String namePretty;
    private final String uri;

    VeniceBeachStudio(String namePretty, String uri) {
        this.namePretty = namePretty;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public String getNamePretty() {
        return namePretty;
    }
}
