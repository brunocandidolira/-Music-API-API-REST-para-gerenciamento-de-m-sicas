package com.example.music.Repository;

import com.example.music.Domain.User;
import com.example.music.Repository.UserMusicProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = """
        SELECT 
            u.id   AS userId,
            u.name AS userName,
            m.title AS musicTitle
        FROM users u
        LEFT JOIN user_music um ON u.id = um.user_id
        LEFT JOIN music m ON um.music_id = m.id
        """, nativeQuery = true)
    List<UserMusicProjection> getUsersWithMusics();
}
