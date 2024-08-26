package org.example.springexpert.repository;

import org.example.springexpert.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByTodoId(Long todoId);

    // join 통해 한 번에 가져와서 최적화
    @Query("SELECT m FROM Manager m JOIN FETCH m.user WHERE m.todo.id = :todoId")
    List<Manager> findByTodoIdWithUser(@Param("todoId") Long todoId);

    boolean existsByTodoIdAndUserId(Long todoId, Long managerUserId);
}
