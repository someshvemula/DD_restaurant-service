package com.dishdash.restaurantservice.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
public enum Cuisine {
//    Cuisines
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
    ASIAN("Asian"),
    MEDITERRANEAN("Mediterranean"),
    GERMAN("German"),
    BRAZILIAN("Brazilian"),
    VIETNAMESE("Vietnamese"),
    KOREAN("Korean"),
    CAJUN("Cajun"),
    CARIBBEAN("Caribbean"),
    MOROCCAN("Moroccan"),
    AUSTRALIAN("Australian"),
    ANDHRA("Andhra"),
    TAMIL("Tamil");

    private final String displayName;

    Cuisine(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}

