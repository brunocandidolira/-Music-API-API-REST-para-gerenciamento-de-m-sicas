package com.example.music.Controller;

import com.example.music.MusicDTO.MusicRequestDTO;
import com.example.music.MusicDTO.MusicResponseDTO;
import com.example.music.Service.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musics")
@CrossOrigin(origins = "http://localhost:3000")
public class MusicController {

    private final MusicService service;

    public MusicController(MusicService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<MusicResponseDTO> create(
            @RequestBody MusicRequestDTO dto
    ) {
        MusicResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> update(
            @PathVariable Long id,
            @RequestBody MusicRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
