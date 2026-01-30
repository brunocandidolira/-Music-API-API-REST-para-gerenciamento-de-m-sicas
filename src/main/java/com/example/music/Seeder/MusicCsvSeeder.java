package com.example.music.Seeder;

import com.example.music.Domain.Music;
import com.example.music.Repository.MusicRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MusicCsvSeeder {

    private static final Logger log = LoggerFactory.getLogger(MusicCsvSeeder.class);
    private final MusicRepository musicRepository;

    @PostConstruct
    public void seed() {
        if (musicRepository.count() > 0) {
            log.info("Músicas já existem no banco, seed ignorado.");
            return;
        }
        importCsv();
    }

    private void importCsv() {
        try (
                CSVReader csvReader = new CSVReader(
                        new InputStreamReader(
                                getClass().getClassLoader().getResourceAsStream("seed/music.csv"),
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            String[] line;
            List<Music> musicList = new ArrayList<>();

            // pula cabeçalho
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {

                // title,artist,album,year,premium
                if (line.length < 5) {
                    log.warn("Linha inválida ignorada");
                    continue;
                }

                Music music = new Music();
                music.setTitle(line[0].trim());
                music.setArtist(line[1].trim());
                music.setAlbum(line[2].trim());

                try {
                    music.setYear(Integer.parseInt(line[3].trim()));
                } catch (NumberFormatException e) {
                    log.warn("Ano inválido: {}", line[3]);
                    continue;
                }

                music.setPremium(Boolean.parseBoolean(line[4].trim()));

                musicList.add(music);
            }

            musicRepository.saveAll(musicList);
            log.info("Seed de músicas concluído! {} músicas adicionadas.", musicList.size());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler CSV", e);
        }
    }
}
