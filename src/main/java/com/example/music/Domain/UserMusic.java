package com.example.music.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(
        name = "user_music",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "music_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "music_id")
    private Music music;

    // ESSENCIAL para evitar duplicação de tabela e bugs de cache
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMusic)) return false;
        UserMusic that = (UserMusic) o;
        return user.equals(that.user) && music.equals(that.music);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, music);
    }
}
