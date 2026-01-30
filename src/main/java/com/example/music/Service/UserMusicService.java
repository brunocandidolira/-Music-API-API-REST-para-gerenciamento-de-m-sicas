package com.example.music.Service;


import com.example.music.Domain.Music;
import com.example.music.Domain.User;
import com.example.music.Domain.UserMusic;
import com.example.music.Repository.MusicRepository;
import com.example.music.Repository.UserMusicRepository;
import com.example.music.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserMusicService {

    private final UserRepository userRepository;
    private final MusicRepository musicRepository;
    private final UserMusicRepository userMusicRepository;

    public UserMusicService(
            UserRepository userRepository,
            MusicRepository musicRepository,
            UserMusicRepository userMusicRepository
    ) {
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
        this.userMusicRepository = userMusicRepository;
    }

    /**
     * Vincula música ao usuário
     */
    public void attachMusicToUser(Long userId, Long musicId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        if (userMusicRepository.existsByUserAndMusic(user, music))
            throw new RuntimeException("Usuário já possui essa música");

        if (userMusicRepository.countByUser(user) >= 100)
            throw new RuntimeException("Limite de músicas excedido");

        if (music.isPremium() && !user.isPremium())
            throw new RuntimeException("Usuário não é premium");

        if (music.getYear() > 2000 && user.getAge() < 18)
            throw new RuntimeException("Usuário menor de idade");

        UserMusic userMusic = new UserMusic();
        userMusic.setUser(user);
        userMusic.setMusic(music);

        userMusicRepository.save(userMusic);
    }

    /**
     * Lista músicas de um usuário
     */
    public List<Music> getMusicsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return userMusicRepository.findByUser(user)
                .stream()
                .map(UserMusic::getMusic)
                .collect(Collectors.toList());
    }

    /**
     * Remove música do usuário
     */
    public void detachMusicFromUser(Long userId, Long musicId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        UserMusic userMusic = userMusicRepository
                .findByUserAndMusic(user, music)
                .orElseThrow(() -> new RuntimeException("Relacionamento não encontrado"));

        userMusicRepository.delete(userMusic);
    }
}
