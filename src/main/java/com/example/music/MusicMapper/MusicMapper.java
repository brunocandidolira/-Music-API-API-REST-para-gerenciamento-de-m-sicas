package com.example.music.MusicMapper;


import com.example.music.Domain.Music;
import com.example.music.MusicDTO.MusicRequestDTO;
import com.example.music.MusicDTO.MusicResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MusicMapper {



    public Music toEntity(MusicRequestDTO dto) {
        Music music = new Music();
        music.setTitle(dto.title());
        music.setArtist(dto.artist());
        music.setAlbum(dto.album());
        music.setYear(dto.year());
        music.setPremium(dto.premium());
        return music;
    }


    public MusicResponseDTO toDTO(Music music) {
        return new MusicResponseDTO(
                music.getId(),
                music.getTitle(),
                music.getArtist()
        );
    }
}