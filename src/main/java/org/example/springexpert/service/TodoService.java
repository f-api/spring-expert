package org.example.springexpert.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.dto.todo.request.TodoSaveRequestDto;
import org.example.springexpert.dto.todo.request.TodoUpdateRequestDto;
import org.example.springexpert.dto.todo.response.TodoDetailResponseDto;
import org.example.springexpert.dto.todo.response.TodoSimpleResponseDto;
import org.example.springexpert.dto.todo.response.TodoSaveResponseDto;
import org.example.springexpert.dto.todo.response.TodoUpdateResponseDto;
import org.example.springexpert.entity.Todo;
import org.example.springexpert.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoSaveResponseDto saveTodo(TodoSaveRequestDto todoSaveRequestDto) {

        Todo newTodo = new Todo(
                todoSaveRequestDto.getUsername(),
                todoSaveRequestDto.getTitle(),
                todoSaveRequestDto.getContents()
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponseDto(
                savedTodo.getId(),
                savedTodo.getUsername(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                savedTodo.getCreatedAt(),
                savedTodo.getModifiedAt()
        );
    }

    public Page<TodoSimpleResponseDto> getTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

        return todos.map(todo -> new TodoSimpleResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getComments().size(),
                todo.getCreatedAt(),
                todo.getModifiedAt(),
                todo.getUsername()
        ));
    }

    public Page<TodoSimpleResponseDto> getTodosOptimized(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return todoRepository.findTodosWithCommentCount(pageable);
    }

    public TodoDetailResponseDto getTodo(Long todoId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NullPointerException("Todo not found"));

        return new TodoDetailResponseDto(
                todo.getId(),
                todo.getUsername(),
                todo.getTitle(),
                todo.getContents(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public TodoUpdateResponseDto updateTodo(Long todoId, TodoUpdateRequestDto todoUpdateRequestDto) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NullPointerException("Todo not found"));

        todo.update(
                todoUpdateRequestDto.getTitle(),
                todoUpdateRequestDto.getContents()
        );

        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getUsername(),
                todo.getTitle(),
                todo.getContents(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
