package org.example.springexpert.domain.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springexpert.domain.comment.entity.Comment;
import org.example.springexpert.domain.common.entity.Timestamped;
import org.example.springexpert.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String title;
    private String contents;
    private String weather;

    // 일정 만든 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Todo(User user, String title, String contents, String weather) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.weather = weather;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
