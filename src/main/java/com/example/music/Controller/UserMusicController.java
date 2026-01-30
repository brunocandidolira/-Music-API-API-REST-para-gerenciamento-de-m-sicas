package com.example.music.Controller;

import com.example.music.Service.UserMusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class UserMusicController {

    private final UserMusicService service;

    public UserMusicController(UserMusicService service) {
        this.service = service;
    }

    /**
     * Vincula uma música a um usuário
     * POST /api/music/{musicId}/user/{userId}
     */
    @PostMapping("/{musicId}/user/{userId}")
    public ResponseEntity<Void> attachMusicToUser(
            @PathVariable Long musicId,
            @PathVariable Long userId
    ) {
        service.attachMusicToUser(userId, musicId);
        return ResponseEntity.status(201).build();
    }

    /**
     * Lista todas as músicas de um usuário
     * GET /api/music/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<?>> getUserMusics(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(service.getMusicsByUser(userId));
    }

    /**
     * Remove uma música do usuário
     * DELETE /api/music/{musicId}/user/{userId}
     */
    @DeleteMapping("/{musicId}/user/{userId}")
    public ResponseEntity<Void> detachMusicFromUser(
            @PathVariable Long musicId,
            @PathVariable Long userId
    ) {
        service.detachMusicFromUser(userId, musicId);
        return ResponseEntity.noContent().build();
    }
}
