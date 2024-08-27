package org.example.springexpert.domain.todo.dto.request;

import lombok.Getter;

@Getter
public class TodoUpdateRequestDto {

    private Long userId;
    private String title;
    private String contents;
}
