package com.example.music.Controller;

import com.example.music.Repository.UserMusicProjection;
import com.example.music.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users-music")
    public List<UserMusicProjection> getUsersMusic() {
        return userService.listUsersWithMusics();
    }
}