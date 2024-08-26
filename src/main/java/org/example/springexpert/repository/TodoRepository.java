package org.example.springexpert.repository;

import org.example.springexpert.dto.todo.response.TodoSimpleResponseDto;
import org.example.springexpert.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    // 코멘트를 모두 가져오지 않고, 개수만 가져와서 쿼리를 최적화
    @Query("SELECT new org.example.springexpert.dto.todo.response.TodoSimpleResponseDto( " +
            "t.id, t.title, t.contents, SIZE(t.comments), t.createdAt, t.modifiedAt, t.username) " +
            "FROM Todo t " +
            "ORDER BY t.modifiedAt DESC")
    Page<TodoSimpleResponseDto> findTodosWithCommentCount(Pageable pageable);
}
