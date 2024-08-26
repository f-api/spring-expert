package org.example.springexpert.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(unique = true) // 이메일은 유니크해야 합니다
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void update(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
