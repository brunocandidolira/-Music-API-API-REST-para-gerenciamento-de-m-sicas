package com.example.music.Domain.Rule;


import java.time.Year;

public class MusicYear {

    private final int year;

    public MusicYear(int year) {
        this.year = year;
        validate();
    }

    private void validate() {
        int currentYear = Year.now().getValue();

        if (year > currentYear) {
            throw new IllegalArgumentException(
                    "Ano da música não pode ser no futuro"
            );
        }
    }

    public int getValue() {
        return year;
    }
}
