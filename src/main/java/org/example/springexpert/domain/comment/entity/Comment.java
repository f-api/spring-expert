package org.example.springexpert.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springexpert.domain.common.entity.Timestamped;
import org.example.springexpert.domain.todo.entity.Todo;
import org.example.springexpert.domain.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    public Comment(String contents, User user, Todo todo) {
        this.contents = contents;
        this.user = user;
        this.todo = todo;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
