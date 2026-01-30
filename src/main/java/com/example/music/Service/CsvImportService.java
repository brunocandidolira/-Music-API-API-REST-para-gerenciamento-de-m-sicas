package com.example.music.Service;

import com.example.music.Domain.Music;
import com.example.music.Repository.MusicRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvImportService {

    private final MusicRepository musicRepository;

    public void importCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
        )) {
            String[] line;
            List<Music> musicList = new ArrayList<>();

            // Pula o cabeçalho
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                // Certifica que a linha tem todas as colunas necessárias
                if (line.length < 5) continue;

                try {
                    String title = line[0].trim();
                    String artist = line[1].trim();
                    String album = line[2].trim();
                    int year = Integer.parseInt(line[3].trim());
                    boolean premium = Boolean.parseBoolean(line[4].trim());

                    // Verifica se já existe no banco
                    boolean exists = musicRepository.existsByTitleAndArtist(title, artist);
                    if (exists) continue;

                    Music music = new Music();
                    music.setTitle(title);
                    music.setArtist(artist);
                    music.setAlbum(album);
                    music.setYear(year);
                    music.setPremium(premium);

                    musicList.add(music);

                } catch (NumberFormatException e) {
                    // Linha com erro de número (ex: year inválido) será ignorada
                    System.out.println("Linha inválida ignorada: " + String.join(",", line));
                }
            }

            if (!musicList.isEmpty()) {
                musicRepository.saveAll(musicList);
            }

        } catch (CsvValidationException e) {
            throw new RuntimeException("Erro de validação CSV", e);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler CSV", e);
        }
    }
}
