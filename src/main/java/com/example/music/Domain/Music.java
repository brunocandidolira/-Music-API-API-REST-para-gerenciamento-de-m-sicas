package com.example.music.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Music {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String artist;
        private String album;
        private int year;
        private boolean premium;

        @OneToMany(
                mappedBy = "music",
                cascade = CascadeType.ALL,
                orphanRemoval = true,
                fetch = FetchType.LAZY
        )
        private Set<UserMusic> userMusics = new HashSet<>();
}
