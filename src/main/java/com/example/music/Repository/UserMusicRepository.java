package com.example.music.Repository;

import com.example.music.Domain.Music;
import com.example.music.Domain.User;
import com.example.music.Domain.UserMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMusicRepository extends JpaRepository<UserMusic, Long> {



    boolean existsByUserAndMusic(User user, Music music);
    long countByUser(User user);
    List<UserMusic> findByUser(User user);
    Optional<UserMusic> findByUserAndMusic(User user, Music music);
}