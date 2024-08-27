package org.example.springexpert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.domain.todo.dto.projection.TodoProjection;
import org.example.springexpert.domain.todo.dto.request.TodoSaveRequestDto;
import org.example.springexpert.domain.todo.dto.request.TodoUpdateRequestDto;
import org.example.springexpert.domain.todo.dto.response.TodoDetailResponseDto;
import org.example.springexpert.domain.todo.dto.response.TodoSaveResponseDto;
import org.example.springexpert.domain.todo.dto.response.TodoSimpleResponseDto;
import org.example.springexpert.domain.todo.dto.response.TodoUpdateResponseDto;
import org.example.springexpert.domain.manager.entity.Manager;
import org.example.springexpert.domain.todo.entity.Todo;
import org.example.springexpert.domain.user.entity.User;
import org.example.springexpert.domain.manager.repository.ManagerRepository;
import org.example.springexpert.domain.todo.repository.TodoRepository;
import org.example.springexpert.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public TodoSaveResponseDto saveTodo(TodoSaveRequestDto todoSaveRequestDto) {
        User user = userRepository.findById(todoSaveRequestDto.getUserId())
                .orElseThrow(() -> new NullPointerException("User not found"));

        Todo newTodo = new Todo(user, todoSaveRequestDto.getTitle(), todoSaveRequestDto.getContents());
        Todo savedTodo = todoRepository.save(newTodo);

        // 작성자는 일정에 자동 참여하여 담당자가 됩니다.
        managerRepository.save(new Manager(user, savedTodo));

        return new TodoSaveResponseDto(
                savedTodo.getId(),
                user,
                savedTodo.getTitle(),
                savedTodo.getContents(),
                savedTodo.getCreatedAt(),
                savedTodo.getModifiedAt()
        );
    }

    public Page<TodoDetailResponseDto> getTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

        return todos.map(todo -> new TodoDetailResponseDto(
                todo.getId(),
                todo.getUser(),
                todo.getTitle(),
                todo.getContents(),
                todo.getComments().size(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    public Page<TodoDetailResponseDto> getTodosOptimized(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<TodoProjection> todoProjectionPage = todoRepository.findTodosWithCommentCount(pageable);
        return todoProjectionPage.map(todoProjection ->
                new TodoDetailResponseDto(
                        todoProjection.getId(),
                        todoProjection.getUser(),
                        todoProjection.getTitle(),
                        todoProjection.getContents(),
                        todoProjection.getCommentCount(),
                        todoProjection.getCreatedAt(),
                        todoProjection.getModifiedAt()
                )
        );
    }

    public TodoSimpleResponseDto getTodo(Long todoId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NullPointerException("Todo not found"));

        return new TodoSimpleResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public TodoUpdateResponseDto updateTodo(Long todoId, TodoUpdateRequestDto todoUpdateRequestDto) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NullPointerException("Todo not found"));
        User user = userRepository.findById(todoUpdateRequestDto.getUserId())
                .orElseThrow(() -> new NullPointerException("User not found"));

        // 작성자 일치 여부 null-safe 비교
        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        todo.update(
                todoUpdateRequestDto.getTitle(),
                todoUpdateRequestDto.getContents()
        );

        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getUser(),
                todo.getTitle(),
                todo.getContents(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        // TODO: 작성자 확인 로직 추가 (인증 이후 작성 예정)
        todoRepository.deleteById(todoId);
    }
}
