package org.example.springexpert.domain.todo.dto.request;

import lombok.Getter;

@Getter
public class TodoSaveRequestDto {
    private Long userId;
    private String title;
    private String contents;
}
