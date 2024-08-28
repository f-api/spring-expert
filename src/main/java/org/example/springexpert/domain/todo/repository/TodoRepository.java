package org.example.springexpert.domain.todo.repository;

import org.example.springexpert.domain.todo.dto.projection.TodoProjection;
import org.example.springexpert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    // 코멘트를 모두 가져오지 않고, 개수만 가져와서 쿼리를 최적화
    @Query("SELECT new org.example.springexpert.domain.todo.dto.projection.TodoProjection( " +
            "t.id, t.user, t.title, t.contents, t.weather, SIZE(t.comments), t.createdAt, t.modifiedAt) " +
            "FROM Todo t " +
            "ORDER BY t.modifiedAt DESC")
    Page<TodoProjection> findTodosWithCommentCount(Pageable pageable);
}
