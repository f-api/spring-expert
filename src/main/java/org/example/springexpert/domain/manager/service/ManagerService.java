package org.example.springexpert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.springexpert.domain.manager.dto.request.ManagerSaveRequestDto;
import org.example.springexpert.domain.manager.dto.response.ManagerSimpleResponseDto;
import org.example.springexpert.domain.manager.entity.Manager;
import org.example.springexpert.domain.todo.entity.Todo;
import org.example.springexpert.domain.user.entity.User;
import org.example.springexpert.domain.manager.repository.ManagerRepository;
import org.example.springexpert.domain.todo.repository.TodoRepository;
import org.example.springexpert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveManager(Long todoId, ManagerSaveRequestDto managerSaveRequestDto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NullPointerException("Todo not found"));

        User todoUser = userRepository.findById(managerSaveRequestDto.getTodoUserId())
                .orElseThrow(() -> new NullPointerException("Todo user not found"));
        User newTodoUser = userRepository.findById(managerSaveRequestDto.getManagerUserId())
                .orElseThrow(() -> new NullPointerException("Manager user not found"));

        // 일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다.
        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(todoUser.getId(), todo.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        // 일정에 이미 참가한 유저는 중복 참가할 수 없습니다.
        if (managerRepository.existsByTodoIdAndUserId(todoId, managerSaveRequestDto.getManagerUserId())) {
            throw new IllegalArgumentException("이미 참가한 유저는 중복 참가할 수 없습니다.");
        }

        Manager newManager = new Manager(newTodoUser, todo);
        managerRepository.save(newManager);
    }

    public List<ManagerSimpleResponseDto> getManagers(Long todoId) {
        List<Manager> managers = managerRepository.findByTodoId(todoId);

        List<ManagerSimpleResponseDto> dtoList = new ArrayList<>();
        for (Manager manager : managers) {
            ManagerSimpleResponseDto managerSimpleResponseDto = new ManagerSimpleResponseDto(
                    manager.getId(),
                    manager.getUser().getUsername(),
                    manager.getUser().getEmail()
            );
            dtoList.add(managerSimpleResponseDto);
        }
        return dtoList;
    }

    public List<ManagerSimpleResponseDto> getManagersOptimized(Long todoId) {
        List<Manager> managers = managerRepository.findByTodoIdWithUser(todoId);

        List<ManagerSimpleResponseDto> dtoList = new ArrayList<>();
        for (Manager manager : managers) {
            ManagerSimpleResponseDto managerSimpleResponseDto = new ManagerSimpleResponseDto(
                    manager.getId(),
                    manager.getUser().getUsername(),
                    manager.getUser().getEmail()
            );
            dtoList.add(managerSimpleResponseDto);
        }
        return dtoList;
    }
}
