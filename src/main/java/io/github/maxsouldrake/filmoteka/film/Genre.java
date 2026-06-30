package io.github.maxsouldrake.filmoteka.film;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Film genre")
public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BIOGRAPHY("Biography"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DISASTER("Disaster"),
    DOCUMENTARY("Documentary"),
    DRAMA("Drama"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    NOIR("Noir"),
    HISTORY("History"),
    HORROR("Horror"),
    MUSIC("Music"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    SPORT("Sport"),
    SPY("Spy"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    private final String jsonValue;

    Genre(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }
}
