package io.github.maxsouldrake.filmoteka.film;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Film countries of origin")
public enum Country {
    UNITED_STATES("United States"),
    UNITED_KINGDOM("United Kingdom"),
    FRANCE("France"),
    GERMANY("Germany"),
    ITALY("Italy"),
    SPAIN("Spain"),
    POLAND("Poland"),
    RUSSIA("Russia"),
    SOVIET_UNION("Soviet Union"),
    JAPAN("Japan"),
    SOUTH_KOREA("South Korea"),
    CHINA("China"),
    INDIA("India"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    NEW_ZEALAND("New Zealand"),
    MEXICO("Mexico"),
    BRAZIL("Brazil"),
    ARGENTINA("Argentina");

    private final String jsonValue;

    Country(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }
}
