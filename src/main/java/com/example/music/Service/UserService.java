package com.example.music.Service;

import com.example.music.Repository.UserMusicProjection;
import com.example.music.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserMusicProjection> listUsersWithMusics() {
        return userRepository.getUsersWithMusics();
    }
}
