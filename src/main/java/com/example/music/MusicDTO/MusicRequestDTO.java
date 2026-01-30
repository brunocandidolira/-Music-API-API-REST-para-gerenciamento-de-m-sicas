package com.example.music.MusicDTO;

    public record MusicRequestDTO(
            String title,
            String artist,
            String album,
            int year,
            boolean premium
    ) {}

