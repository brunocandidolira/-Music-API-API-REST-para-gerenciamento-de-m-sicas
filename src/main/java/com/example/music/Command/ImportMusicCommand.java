package com.example.music.Command;


import com.example.music.MusicDTO.MusicRequestDTO;
import com.example.music.Service.MusicService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class ImportMusicCommand implements CommandLineRunner {

    private final MusicService service;

    public ImportMusicCommand(MusicService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length < 2) return;
        if (!args[0].equals("import")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                service.create(
                        new MusicRequestDTO(
                                data[0],                 // title
                                data[1],                 // artist
                                data[2],                 // album
                                Integer.parseInt(data[3]), // year
                                false                    // premium
                        )
                );
            }
        }
    }
}
