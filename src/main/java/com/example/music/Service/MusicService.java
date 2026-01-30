package com.example.music.Service;

import com.example.music.Domain.Music;
import com.example.music.Domain.Rule.MusicYear;
import com.example.music.MusicDTO.MusicRequestDTO;
import com.example.music.MusicDTO.MusicResponseDTO;
import com.example.music.MusicMapper.MusicMapper;
import com.example.music.Repository.MusicRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MusicService {

    private final MusicRepository repository;
    private final MusicMapper mapper;

    public MusicService(MusicRepository repository, MusicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE
    public MusicResponseDTO create(MusicRequestDTO dto) {

        MusicYear validYear = new MusicYear(dto.year());

        if (repository.existsByTitleAndArtist(dto.title(), dto.artist())) {
            throw new RuntimeException("Música duplicada");
        }

        Music music = mapper.toEntity(dto);
        music.setYear(validYear.getValue());

        repository.save(music);

        return mapper.toDTO(music);
    }

    // GET ALL
    public List<MusicResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // GET BY ID
    public MusicResponseDTO findById(Long id) {
        Music music = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        return mapper.toDTO(music);
    }

    // UPDATE
    public MusicResponseDTO update(Long id, MusicRequestDTO dto) {

        Music music = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        MusicYear validYear = new MusicYear(dto.year());

        // evita duplicar ao atualizar
        boolean duplicated = repository.existsByTitleAndArtist(dto.title(), dto.artist());
        if (duplicated &&
                (!music.getTitle().equals(dto.title())
                        || !music.getArtist().equals(dto.artist()))) {
            throw new RuntimeException("Música duplicada");
        }

        music.setTitle(dto.title());
        music.setArtist(dto.artist());
        music.setAlbum(dto.album());
        music.setPremium(dto.premium());
        music.setYear(validYear.getValue());

        return mapper.toDTO(music);
    }

    // DELETE
    public void delete(Long id) {

        Music music = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        repository.delete(music);
    }
}
