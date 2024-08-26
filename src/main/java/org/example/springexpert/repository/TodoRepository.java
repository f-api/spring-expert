package org.example.springexpert.repository;

import org.example.springexpert.dto.todo.projection.TodoProjection;
import org.example.springexpert.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    // 코멘트를 모두 가져오지 않고, 개수만 가져와서 쿼리를 최적화
    @Query("SELECT new org.example.springexpert.dto.todo.projection.TodoProjection( " +
            "t.id, t.user, t.title, t.contents, SIZE(t.comments), t.createdAt, t.modifiedAt) " +
            "FROM Todo t " +
            "ORDER BY t.modifiedAt DESC")
    Page<TodoProjection> findTodosWithCommentCount(Pageable pageable);

    // findById의 Todo에서 User를 가져오면 쿼리가 2번 나가므로, JOIN FETCH를 사용하여 한번에 가져오기
    @Query("SELECT t FROM Todo t JOIN FETCH t.user WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
