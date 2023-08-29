package com.dishdash.restaurantservice.enums;

public enum Cuisine {
    ITALIAN("Italian"),
    CHINESE("Chinese"),
    MEXICAN("Mexican"),
    JAPANESE("Japanese"),
    INDIAN("Indian"),
    AMERICAN("American"),
    FRENCH("French"),
    THAI("Thai"),
    GREEK("Greek"),
    SPANISH("Spanish"),
    OTHER("Other");

    private final String displayName;

    Cuisine(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}

