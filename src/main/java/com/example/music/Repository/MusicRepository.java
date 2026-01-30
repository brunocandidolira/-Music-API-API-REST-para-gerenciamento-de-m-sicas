package com.example.music.Repository;
import com.example.music.Domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

    boolean existsByTitleAndArtist(String title, String artist);

}